begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|contenthub
operator|.
name|search
operator|.
name|solr
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrQuery
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrServer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrServerException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|request
operator|.
name|LukeRequest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|util
operator|.
name|ClientUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|common
operator|.
name|params
operator|.
name|CommonParams
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|common
operator|.
name|params
operator|.
name|SolrParams
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|common
operator|.
name|util
operator|.
name|NamedList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|contenthub
operator|.
name|servicesapi
operator|.
name|store
operator|.
name|vocabulary
operator|.
name|SolrVocabulary
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|contenthub
operator|.
name|servicesapi
operator|.
name|store
operator|.
name|vocabulary
operator|.
name|SolrVocabulary
operator|.
name|SolrFieldName
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  *   * @author anil.pacaci  *   */
end_comment

begin_class
specifier|public
class|class
name|SolrQueryUtil
block|{
specifier|private
specifier|final
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SolrQueryUtil
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CONTENT_FIELD
init|=
literal|"content_t"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ID_FIELD
init|=
literal|"id"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SCORE_FIELD
init|=
literal|"score"
decl_stmt|;
specifier|public
specifier|final
specifier|static
name|String
name|and
init|=
literal|"AND"
decl_stmt|;
specifier|public
specifier|final
specifier|static
name|String
name|or
init|=
literal|"OR"
decl_stmt|;
specifier|public
specifier|final
specifier|static
name|String
name|facetDelimiter
init|=
literal|":"
decl_stmt|;
specifier|public
specifier|final
specifier|static
name|char
name|quotation
init|=
literal|'"'
decl_stmt|;
specifier|public
specifier|final
specifier|static
name|List
argument_list|<
name|Character
argument_list|>
name|queryDelimiters
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|' '
argument_list|,
literal|','
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|SolrQuery
name|keywordQueryWithFacets
parameter_list|(
name|String
name|keyword
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|constraints
parameter_list|)
block|{
name|SolrQuery
name|query
init|=
operator|new
name|SolrQuery
argument_list|()
decl_stmt|;
name|query
operator|.
name|setQuery
argument_list|(
name|keyword
argument_list|)
expr_stmt|;
if|if
condition|(
name|constraints
operator|!=
literal|null
condition|)
block|{
try|try
block|{
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|entry
range|:
name|constraints
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|fieldName
init|=
name|ClientUtils
operator|.
name|escapeQueryChars
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Object
name|value
range|:
name|entry
operator|.
name|getValue
argument_list|()
control|)
block|{
if|if
condition|(
name|SolrVocabulary
operator|.
name|isNameRangeField
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|query
operator|.
name|addFilterQuery
argument_list|(
name|fieldName
operator|+
name|facetDelimiter
operator|+
operator|(
name|String
operator|)
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|query
operator|.
name|addFilterQuery
argument_list|(
name|fieldName
operator|+
name|facetDelimiter
operator|+
name|quotation
operator|+
name|ClientUtils
operator|.
name|escapeQueryChars
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
operator|+
name|quotation
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Facet constraints could not be added to Query"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|query
return|;
block|}
specifier|private
specifier|static
name|String
name|removeFacetConstraints
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|int
name|delimiteri
init|=
name|query
operator|.
name|indexOf
argument_list|(
name|facetDelimiter
argument_list|)
decl_stmt|;
while|while
condition|(
name|delimiteri
operator|>
operator|-
literal|1
condition|)
block|{
name|int
name|starti
init|=
name|delimiteri
decl_stmt|;
for|for
control|(
name|starti
operator|=
name|delimiteri
init|;
name|starti
operator|>=
literal|0
operator|&&
operator|!
name|queryDelimiters
operator|.
name|contains
argument_list|(
name|query
operator|.
name|charAt
argument_list|(
name|starti
argument_list|)
argument_list|)
condition|;
name|starti
operator|--
control|)
empty_stmt|;
operator|++
name|starti
expr_stmt|;
name|int
name|endi
init|=
name|delimiteri
operator|+
literal|1
decl_stmt|;
if|if
condition|(
name|endi
operator|<
name|query
operator|.
name|length
argument_list|()
condition|)
block|{
if|if
condition|(
name|query
operator|.
name|charAt
argument_list|(
name|endi
argument_list|)
operator|==
name|quotation
condition|)
block|{
operator|++
name|endi
expr_stmt|;
for|for
control|(
init|;
name|endi
operator|<
name|query
operator|.
name|length
argument_list|()
operator|&&
name|query
operator|.
name|charAt
argument_list|(
name|endi
argument_list|)
operator|!=
name|quotation
condition|;
name|endi
operator|++
control|)
empty_stmt|;
operator|++
name|endi
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
init|;
name|endi
operator|<
name|query
operator|.
name|length
argument_list|()
operator|&&
operator|!
name|queryDelimiters
operator|.
name|contains
argument_list|(
name|query
operator|.
name|charAt
argument_list|(
name|endi
argument_list|)
argument_list|)
condition|;
name|endi
operator|++
control|)
empty_stmt|;
block|}
block|}
name|query
operator|=
name|query
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|starti
argument_list|)
operator|+
operator|(
name|endi
operator|>=
name|query
operator|.
name|length
argument_list|()
condition|?
literal|""
else|:
name|query
operator|.
name|substring
argument_list|(
name|endi
argument_list|)
operator|)
expr_stmt|;
name|delimiteri
operator|=
name|query
operator|.
name|indexOf
argument_list|(
name|facetDelimiter
argument_list|)
expr_stmt|;
block|}
name|query
operator|=
name|query
operator|.
name|replaceAll
argument_list|(
name|and
operator|+
literal|'|'
operator|+
name|or
operator|+
literal|'|'
operator|+
name|and
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|'|'
operator|+
name|or
operator|.
name|toLowerCase
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
name|String
name|removeSpecialCharacter
parameter_list|(
name|String
name|query
parameter_list|,
name|char
name|ch
parameter_list|)
block|{
name|int
name|starti
init|=
name|query
operator|.
name|indexOf
argument_list|(
name|ch
argument_list|)
decl_stmt|;
while|while
condition|(
name|starti
operator|>
operator|-
literal|1
condition|)
block|{
name|int
name|endi
init|=
operator|-
literal|1
decl_stmt|;
for|for
control|(
name|endi
operator|=
name|starti
init|;
name|endi
operator|<
name|query
operator|.
name|length
argument_list|()
operator|&&
operator|!
name|queryDelimiters
operator|.
name|contains
argument_list|(
name|query
operator|.
name|charAt
argument_list|(
name|endi
argument_list|)
argument_list|)
condition|;
name|endi
operator|++
control|)
empty_stmt|;
name|query
operator|=
name|query
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|starti
argument_list|)
operator|+
operator|(
name|endi
operator|>=
name|query
operator|.
name|length
argument_list|()
condition|?
literal|""
else|:
name|query
operator|.
name|substring
argument_list|(
name|endi
argument_list|)
operator|)
expr_stmt|;
name|starti
operator|=
name|query
operator|.
name|indexOf
argument_list|(
name|ch
argument_list|)
expr_stmt|;
block|}
return|return
name|query
return|;
block|}
specifier|private
specifier|static
name|String
name|removeSpecialCharacters
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|query
operator|=
name|query
operator|.
name|replaceAll
argument_list|(
literal|"[+|\\-&!\\(\\)\\{\\}\\[\\]\\*\\?\\\\]"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|query
operator|=
name|removeSpecialCharacter
argument_list|(
name|query
argument_list|,
literal|'^'
argument_list|)
expr_stmt|;
name|query
operator|=
name|removeSpecialCharacter
argument_list|(
name|query
argument_list|,
literal|'~'
argument_list|)
expr_stmt|;
return|return
name|query
return|;
block|}
specifier|public
specifier|static
name|String
name|extractQueryTermFromSolrQuery
parameter_list|(
name|SolrParams
name|solrQuery
parameter_list|)
block|{
name|String
name|queryFull
init|=
name|solrQuery
operator|instanceof
name|SolrQuery
condition|?
operator|(
operator|(
name|SolrQuery
operator|)
name|solrQuery
operator|)
operator|.
name|getQuery
argument_list|()
else|:
name|solrQuery
operator|.
name|get
argument_list|(
name|CommonParams
operator|.
name|Q
argument_list|)
decl_stmt|;
name|queryFull
operator|=
name|removeSpecialCharacters
argument_list|(
name|queryFull
argument_list|)
expr_stmt|;
name|queryFull
operator|=
name|removeFacetConstraints
argument_list|(
name|queryFull
argument_list|)
expr_stmt|;
return|return
name|queryFull
operator|.
name|trim
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|SolrQuery
name|prepareFacetedSolrQuery
parameter_list|(
name|String
name|queryTerm
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|allAvailableFacetNames
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|constraints
parameter_list|)
block|{
name|SolrQuery
name|solrQuery
init|=
name|keywordQueryWithFacets
argument_list|(
name|queryTerm
argument_list|,
name|constraints
argument_list|)
decl_stmt|;
name|setDefaultQueryParameters
argument_list|(
name|solrQuery
argument_list|,
name|allAvailableFacetNames
argument_list|)
expr_stmt|;
return|return
name|solrQuery
return|;
block|}
specifier|private
specifier|static
name|void
name|setDefaultQueryParameters
parameter_list|(
name|SolrQuery
name|solrQuery
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|allAvailableFacetNames
parameter_list|)
block|{
name|solrQuery
operator|.
name|setFields
argument_list|(
literal|"*"
argument_list|,
name|SCORE_FIELD
argument_list|)
expr_stmt|;
name|solrQuery
operator|.
name|setFacet
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|solrQuery
operator|.
name|setFacetMinCount
argument_list|(
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|allAvailableFacetNames
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|facetName
range|:
name|allAvailableFacetNames
control|)
block|{
if|if
condition|(
name|SolrFieldName
operator|.
name|CREATIONDATE
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|facetName
argument_list|)
operator|||
operator|(
operator|!
name|SolrFieldName
operator|.
name|isNameReserved
argument_list|(
name|facetName
argument_list|)
operator|&&
operator|!
name|SolrVocabulary
operator|.
name|isNameExcluded
argument_list|(
name|facetName
argument_list|)
operator|)
condition|)
block|{
name|solrQuery
operator|.
name|addFacetField
argument_list|(
name|facetName
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
specifier|static
name|SolrQuery
name|prepareDefaultSolrQuery
parameter_list|(
name|SolrServer
name|solrServer
parameter_list|,
name|String
name|queryTerm
parameter_list|)
throws|throws
name|SolrServerException
throws|,
name|IOException
block|{
name|SolrQuery
name|solrQuery
init|=
operator|new
name|SolrQuery
argument_list|()
decl_stmt|;
name|solrQuery
operator|.
name|setQuery
argument_list|(
name|queryTerm
argument_list|)
expr_stmt|;
name|setDefaultQueryParameters
argument_list|(
name|solrQuery
argument_list|,
name|getFacetNames
argument_list|(
name|solrServer
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|solrQuery
return|;
block|}
specifier|public
specifier|static
name|SolrQuery
name|prepareDefaultSolrQuery
parameter_list|(
name|String
name|queryTerm
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|allAvailableFacetNames
parameter_list|)
block|{
name|SolrQuery
name|solrQuery
init|=
operator|new
name|SolrQuery
argument_list|()
decl_stmt|;
name|solrQuery
operator|.
name|setQuery
argument_list|(
name|queryTerm
argument_list|)
expr_stmt|;
name|setDefaultQueryParameters
argument_list|(
name|solrQuery
argument_list|,
name|allAvailableFacetNames
argument_list|)
expr_stmt|;
return|return
name|solrQuery
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|getFacetNames
parameter_list|(
name|SolrServer
name|solrServer
parameter_list|)
throws|throws
name|SolrServerException
throws|,
name|IOException
block|{
name|List
argument_list|<
name|String
argument_list|>
name|facetNames
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|LukeRequest
name|qr
init|=
operator|new
name|LukeRequest
argument_list|()
decl_stmt|;
name|NamedList
argument_list|<
name|Object
argument_list|>
name|qresp
init|=
name|solrServer
operator|.
name|request
argument_list|(
name|qr
argument_list|)
decl_stmt|;
name|Object
name|fields
init|=
name|qresp
operator|.
name|get
argument_list|(
literal|"fields"
argument_list|)
decl_stmt|;
if|if
condition|(
name|fields
operator|instanceof
name|NamedList
argument_list|<
name|?
argument_list|>
condition|)
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|NamedList
argument_list|<
name|Object
argument_list|>
name|fieldsList
init|=
operator|(
name|NamedList
argument_list|<
name|Object
argument_list|>
operator|)
name|fields
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fieldsList
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|facetNames
operator|.
name|add
argument_list|(
name|fieldsList
operator|.
name|getName
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Fields container is not a NamedList, so there is no facet information available"
argument_list|)
expr_stmt|;
block|}
return|return
name|facetNames
return|;
block|}
end_class

unit|}
end_unit

