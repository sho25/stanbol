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
name|related
package|;
end_package

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
name|concurrent
operator|.
name|CopyOnWriteArrayList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Reference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ReferenceCardinality
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ReferencePolicy
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ReferenceStrategy
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|search
operator|.
name|related
operator|.
name|ontologyresource
operator|.
name|OntologyResourceSearch
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
name|search
operator|.
name|related
operator|.
name|referencedsite
operator|.
name|ReferencedSiteSearch
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
name|search
operator|.
name|related
operator|.
name|wordnet
operator|.
name|WordnetSearch
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
name|SearchException
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
name|search
operator|.
name|related
operator|.
name|RelatedKeywordSearch
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
name|RelatedKeywordSearchManager
import|;
end_import

begin_class
annotation|@
name|Component
annotation|@
name|Service
specifier|public
class|class
name|RelatedKeywordSearchManagerImpl
implements|implements
name|RelatedKeywordSearchManager
block|{
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_MULTIPLE
argument_list|,
name|referenceInterface
operator|=
name|RelatedKeywordSearch
operator|.
name|class
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|,
name|strategy
operator|=
name|ReferenceStrategy
operator|.
name|EVENT
argument_list|,
name|bind
operator|=
literal|"bindRelatedKeywordSearch"
argument_list|,
name|unbind
operator|=
literal|"unbindRelatedKeywordSearch"
argument_list|)
specifier|private
name|List
argument_list|<
name|RelatedKeywordSearch
argument_list|>
name|rkwSearchers
init|=
operator|new
name|CopyOnWriteArrayList
argument_list|<
name|RelatedKeywordSearch
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|void
name|bindRelatedKeywordSearch
parameter_list|(
name|RelatedKeywordSearch
name|relatedKeywordSearch
parameter_list|)
block|{
name|rkwSearchers
operator|.
name|add
argument_list|(
name|relatedKeywordSearch
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|unbindRelatedKeywordSearch
parameter_list|(
name|RelatedKeywordSearch
name|relatedKeywordSearch
parameter_list|)
block|{
name|rkwSearchers
operator|.
name|remove
argument_list|(
name|relatedKeywordSearch
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|SearchResult
name|getRelatedKeywordsFromAllSources
parameter_list|(
name|String
name|keyword
parameter_list|)
throws|throws
name|SearchException
block|{
return|return
name|getRelatedKeywordsFromAllSources
argument_list|(
name|keyword
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|SearchResult
name|getRelatedKeywordsFromAllSources
parameter_list|(
name|String
name|keyword
parameter_list|,
name|String
name|ontologyURI
parameter_list|)
throws|throws
name|SearchException
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
name|relatedKeywords
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|RelatedKeywordSearch
name|searcher
range|:
name|rkwSearchers
control|)
block|{
name|relatedKeywords
operator|.
name|putAll
argument_list|(
name|searcher
operator|.
name|search
argument_list|(
name|keyword
argument_list|,
name|ontologyURI
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
argument_list|>
name|relatedKeywordsMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|relatedKeywordsMap
operator|.
name|put
argument_list|(
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
return|return
operator|new
name|RelatedKeywordSearchResult
argument_list|(
name|relatedKeywordsMap
argument_list|)
return|;
block|}
specifier|private
name|SearchResult
name|getRelatedKeywordsFrom
parameter_list|(
name|String
name|keyword
parameter_list|,
name|Class
argument_list|<
name|?
extends|extends
name|RelatedKeywordSearch
argument_list|>
name|cls
parameter_list|,
name|String
modifier|...
name|ontologyURI
parameter_list|)
throws|throws
name|SearchException
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
name|relatedKeywords
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|RelatedKeywordSearch
name|searcher
range|:
name|rkwSearchers
control|)
block|{
if|if
condition|(
name|cls
operator|.
name|isAssignableFrom
argument_list|(
name|searcher
operator|.
name|getClass
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|ontologyURI
operator|!=
literal|null
operator|&&
name|ontologyURI
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|relatedKeywords
operator|.
name|putAll
argument_list|(
name|searcher
operator|.
name|search
argument_list|(
name|keyword
argument_list|,
name|ontologyURI
index|[
literal|0
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|relatedKeywords
operator|.
name|putAll
argument_list|(
name|searcher
operator|.
name|search
argument_list|(
name|keyword
argument_list|)
argument_list|)
expr_stmt|;
block|}
break|break;
block|}
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
argument_list|>
name|relatedKeywordsMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|relatedKeywordsMap
operator|.
name|put
argument_list|(
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
return|return
operator|new
name|RelatedKeywordSearchResult
argument_list|(
name|relatedKeywordsMap
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|SearchResult
name|getRelatedKeywordsFromWordnet
parameter_list|(
name|String
name|keyword
parameter_list|)
throws|throws
name|SearchException
block|{
return|return
name|getRelatedKeywordsFrom
argument_list|(
name|keyword
argument_list|,
name|WordnetSearch
operator|.
name|class
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|SearchResult
name|getRelatedKeywordsFromOntology
parameter_list|(
name|String
name|keyword
parameter_list|,
name|String
name|ontologyURI
parameter_list|)
throws|throws
name|SearchException
block|{
return|return
name|getRelatedKeywordsFrom
argument_list|(
name|keyword
argument_list|,
name|OntologyResourceSearch
operator|.
name|class
argument_list|,
name|ontologyURI
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|SearchResult
name|getRelatedKeywordsFromReferencedSites
parameter_list|(
name|String
name|keyword
parameter_list|)
throws|throws
name|SearchException
block|{
return|return
name|getRelatedKeywordsFrom
argument_list|(
name|keyword
argument_list|,
name|ReferencedSiteSearch
operator|.
name|class
argument_list|)
return|;
block|}
block|}
end_class

end_unit
