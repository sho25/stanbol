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
name|web
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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|commons
operator|.
name|io
operator|.
name|IOUtils
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
name|response
operator|.
name|FacetField
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
name|Constants
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
name|ldpath
operator|.
name|LDProgramCollection
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
name|search
operator|.
name|featured
operator|.
name|SearchResult
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
name|search
operator|.
name|featured
operator|.
name|ResultantDocument
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
name|search
operator|.
name|related
operator|.
name|RelatedKeyword
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
name|solr
operator|.
name|SolrContentItem
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
name|store
operator|.
name|solr
operator|.
name|util
operator|.
name|ContentItemIDOrganizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONArray
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONObject
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
comment|/**  *   * @author anil.sinaci  * @author meric  *   */
end_comment

begin_class
specifier|public
class|class
name|JSONUtils
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|JSONUtils
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * This function only operates on one-level JSON objects. Nested constraints cannot be processed.      *       * @param jsonFields      * @return      */
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|convertToMap
parameter_list|(
name|String
name|jsonFields
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|fieldMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|jsonFields
operator|==
literal|null
condition|)
return|return
name|fieldMap
return|;
try|try
block|{
name|JSONObject
name|jObject
init|=
operator|new
name|JSONObject
argument_list|(
name|jsonFields
argument_list|)
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|Iterator
argument_list|<
name|String
argument_list|>
name|itr
init|=
name|jObject
operator|.
name|keys
argument_list|()
decl_stmt|;
while|while
condition|(
name|itr
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|Object
argument_list|>
name|valueSet
init|=
operator|new
name|ArrayList
argument_list|<
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|String
name|jFieldKey
init|=
name|itr
operator|.
name|next
argument_list|()
decl_stmt|;
name|Object
name|jFieldValue
init|=
name|jObject
operator|.
name|get
argument_list|(
name|jFieldKey
argument_list|)
decl_stmt|;
if|if
condition|(
name|jFieldValue
operator|instanceof
name|JSONArray
condition|)
block|{
name|JSONArray
name|jArray
init|=
operator|(
name|JSONArray
operator|)
name|jFieldValue
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
name|jArray
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|jArray
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|equals
argument_list|(
literal|null
argument_list|)
condition|)
block|{
name|valueSet
operator|.
name|add
argument_list|(
name|jArray
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
if|if
condition|(
operator|!
name|jFieldValue
operator|.
name|equals
argument_list|(
literal|null
argument_list|)
condition|)
block|{
name|valueSet
operator|.
name|add
argument_list|(
name|jFieldValue
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|jFieldKey
operator|!=
literal|null
operator|&&
operator|!
name|jFieldKey
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|fieldMap
operator|.
name|put
argument_list|(
name|jFieldKey
argument_list|,
name|valueSet
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|fieldMap
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Cannot parse Json in generating the search constraints"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|String
name|convertToString
parameter_list|(
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
name|JSONObject
name|jObject
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|constraints
operator|!=
literal|null
condition|)
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
name|constaint
range|:
name|constraints
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|Collection
argument_list|<
name|Object
argument_list|>
name|collection
init|=
operator|new
name|ArrayList
argument_list|<
name|Object
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
name|obj
range|:
name|constaint
operator|.
name|getValue
argument_list|()
control|)
block|{
name|collection
operator|.
name|add
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|constaint
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|jObject
operator|.
name|put
argument_list|(
name|constaint
operator|.
name|getKey
argument_list|()
argument_list|,
name|collection
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Cannot parse values for key {}"
argument_list|,
name|constaint
operator|.
name|getKey
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|jObject
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|JSONObject
name|toJSON
parameter_list|(
name|ResultantDocument
name|resultantDocument
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONObject
name|jObj
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|resultantDocument
operator|!=
literal|null
condition|)
block|{
name|jObj
operator|.
name|put
argument_list|(
literal|"uri"
argument_list|,
name|resultantDocument
operator|.
name|getDereferencableURI
argument_list|()
argument_list|)
expr_stmt|;
name|jObj
operator|.
name|put
argument_list|(
literal|"localid"
argument_list|,
name|resultantDocument
operator|.
name|getLocalId
argument_list|()
argument_list|)
expr_stmt|;
name|jObj
operator|.
name|put
argument_list|(
literal|"mimetype"
argument_list|,
name|resultantDocument
operator|.
name|getMimetype
argument_list|()
argument_list|)
expr_stmt|;
name|jObj
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|resultantDocument
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|jObj
operator|.
name|put
argument_list|(
literal|"enhancementcount"
argument_list|,
name|resultantDocument
operator|.
name|getEnhancementCount
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|jObj
return|;
block|}
specifier|private
specifier|static
parameter_list|<
name|T
parameter_list|>
name|JSONArray
name|toJSON
parameter_list|(
name|List
argument_list|<
name|T
argument_list|>
name|list
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONArray
name|jArr
init|=
operator|new
name|JSONArray
argument_list|()
decl_stmt|;
if|if
condition|(
name|list
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|T
name|element
range|:
name|list
control|)
block|{
if|if
condition|(
name|ResultantDocument
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|element
operator|.
name|getClass
argument_list|()
argument_list|)
condition|)
block|{
name|jArr
operator|.
name|put
argument_list|(
name|toJSON
argument_list|(
operator|(
name|ResultantDocument
operator|)
name|element
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|FacetField
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|element
operator|.
name|getClass
argument_list|()
argument_list|)
condition|)
block|{
name|jArr
operator|.
name|put
argument_list|(
name|toJSON
argument_list|(
operator|(
name|FacetField
operator|)
name|element
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|FacetField
operator|.
name|Count
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|element
operator|.
name|getClass
argument_list|()
argument_list|)
condition|)
block|{
name|jArr
operator|.
name|put
argument_list|(
name|toJSON
argument_list|(
operator|(
name|FacetField
operator|.
name|Count
operator|)
name|element
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|RelatedKeyword
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|element
operator|.
name|getClass
argument_list|()
argument_list|)
condition|)
block|{
name|jArr
operator|.
name|put
argument_list|(
name|toJSON
argument_list|(
operator|(
name|RelatedKeyword
operator|)
name|element
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|jArr
return|;
block|}
specifier|private
specifier|static
name|JSONObject
name|toJSON
parameter_list|(
name|RelatedKeyword
name|relatedKeyword
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONObject
name|jObj
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|relatedKeyword
operator|!=
literal|null
condition|)
block|{
name|jObj
operator|.
name|put
argument_list|(
literal|"keyword"
argument_list|,
name|relatedKeyword
operator|.
name|getKeyword
argument_list|()
argument_list|)
expr_stmt|;
name|jObj
operator|.
name|put
argument_list|(
literal|"score"
argument_list|,
name|relatedKeyword
operator|.
name|getScore
argument_list|()
argument_list|)
expr_stmt|;
comment|// no need to put the source because it is already indicated at the start of the map
comment|// jObj.put("source", relatedKeyword.getSource());
block|}
return|return
name|jObj
return|;
block|}
specifier|private
specifier|static
name|JSONObject
name|toJSON
parameter_list|(
name|FacetField
operator|.
name|Count
name|count
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONObject
name|jObj
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|count
operator|!=
literal|null
condition|)
block|{
name|jObj
operator|.
name|put
argument_list|(
literal|"name"
argument_list|,
name|count
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|jObj
operator|.
name|put
argument_list|(
literal|"count"
argument_list|,
name|count
operator|.
name|getCount
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|jObj
return|;
block|}
specifier|private
specifier|static
name|JSONObject
name|toJSON
parameter_list|(
name|FacetField
name|facet
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONObject
name|jObj
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|facet
operator|!=
literal|null
condition|)
block|{
name|jObj
operator|.
name|put
argument_list|(
literal|"name"
argument_list|,
name|facet
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|jObj
operator|.
name|put
argument_list|(
literal|"values"
argument_list|,
name|toJSON
argument_list|(
name|facet
operator|.
name|getValues
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|jObj
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|private
specifier|static
parameter_list|<
name|V
parameter_list|>
name|JSONArray
name|toJSON
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|V
argument_list|>
name|map
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONArray
name|jArr
init|=
operator|new
name|JSONArray
argument_list|()
decl_stmt|;
if|if
condition|(
name|map
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|V
argument_list|>
name|entry
range|:
name|map
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|JSONObject
name|jObj
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|Map
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|getClass
argument_list|()
argument_list|)
condition|)
block|{
name|jObj
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|toJSON
argument_list|(
operator|(
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
operator|)
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|List
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|getClass
argument_list|()
argument_list|)
condition|)
block|{
name|jObj
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|toJSON
argument_list|(
operator|(
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
operator|)
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|jArr
operator|.
name|put
argument_list|(
name|jObj
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|jArr
return|;
block|}
specifier|public
specifier|static
name|String
name|createJSONString
parameter_list|(
name|SearchResult
name|searchResult
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONObject
name|jObj
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|searchResult
operator|!=
literal|null
condition|)
block|{
name|jObj
operator|.
name|put
argument_list|(
literal|"documents"
argument_list|,
name|toJSON
argument_list|(
name|searchResult
operator|.
name|getResultantDocuments
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|jObj
operator|.
name|put
argument_list|(
literal|"facets"
argument_list|,
name|toJSON
argument_list|(
name|searchResult
operator|.
name|getFacets
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|jObj
operator|.
name|put
argument_list|(
literal|"relatedkeywords"
argument_list|,
name|toJSON
argument_list|(
name|searchResult
operator|.
name|getRelatedKeywords
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|jObj
operator|.
name|toString
argument_list|(
literal|4
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|createJSONString
parameter_list|(
name|SolrContentItem
name|sci
parameter_list|)
throws|throws
name|JSONException
block|{
name|String
name|content
init|=
literal|null
decl_stmt|;
try|try
block|{
name|content
operator|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|sci
operator|.
name|getStream
argument_list|()
argument_list|,
name|Constants
operator|.
name|DEFAULT_ENCODING
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Cannot read the content."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
name|JSONObject
name|jObj
init|=
operator|new
name|JSONObject
argument_list|(
name|sci
operator|.
name|getConstraints
argument_list|()
argument_list|)
decl_stmt|;
name|jObj
operator|.
name|put
argument_list|(
literal|"content"
argument_list|,
name|content
argument_list|)
expr_stmt|;
name|jObj
operator|.
name|put
argument_list|(
literal|"mimeType"
argument_list|,
name|sci
operator|.
name|getMimeType
argument_list|()
argument_list|)
expr_stmt|;
name|jObj
operator|.
name|put
argument_list|(
literal|"id"
argument_list|,
name|ContentItemIDOrganizer
operator|.
name|detachBaseURI
argument_list|(
name|sci
operator|.
name|getUri
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|jObj
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|sci
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|jObj
operator|.
name|toString
argument_list|(
literal|4
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|createJSONString
parameter_list|(
name|LDProgramCollection
name|ldpc
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONObject
name|jObj
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
range|:
name|ldpc
operator|.
name|asMap
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|jObj
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|jObj
operator|.
name|toString
argument_list|(
literal|4
argument_list|)
return|;
block|}
block|}
end_class

end_unit
