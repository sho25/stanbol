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
name|featured
package|;
end_package

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
name|DocumentResult
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
name|FacetResult
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

begin_class
specifier|public
class|class
name|FeaturedSearchResult
implements|implements
name|SearchResult
block|{
specifier|private
name|List
argument_list|<
name|DocumentResult
argument_list|>
name|resultantDocuments
decl_stmt|;
specifier|private
name|List
argument_list|<
name|FacetResult
argument_list|>
name|facetResults
decl_stmt|;
specifier|private
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
name|relatedKeywords
decl_stmt|;
specifier|public
name|FeaturedSearchResult
parameter_list|(
name|List
argument_list|<
name|DocumentResult
argument_list|>
name|resultantDocuments
parameter_list|,
name|List
argument_list|<
name|FacetResult
argument_list|>
name|facetResults
parameter_list|,
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
name|relatedKeywords
parameter_list|)
block|{
name|this
operator|.
name|resultantDocuments
operator|=
name|resultantDocuments
expr_stmt|;
name|this
operator|.
name|facetResults
operator|=
name|facetResults
expr_stmt|;
name|this
operator|.
name|relatedKeywords
operator|=
name|relatedKeywords
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|DocumentResult
argument_list|>
name|getResultantDocuments
parameter_list|()
block|{
return|return
name|this
operator|.
name|resultantDocuments
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|FacetResult
argument_list|>
name|getFacets
parameter_list|()
block|{
return|return
name|this
operator|.
name|facetResults
return|;
block|}
annotation|@
name|Override
specifier|public
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
name|getRelatedKeywords
parameter_list|()
block|{
return|return
name|this
operator|.
name|relatedKeywords
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setDocuments
parameter_list|(
name|List
argument_list|<
name|DocumentResult
argument_list|>
name|resultantDocuments
parameter_list|)
block|{
name|this
operator|.
name|resultantDocuments
operator|=
name|resultantDocuments
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setFacets
parameter_list|(
name|List
argument_list|<
name|FacetResult
argument_list|>
name|facets
parameter_list|)
block|{
name|this
operator|.
name|facetResults
operator|=
name|facets
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setRelatedKeywords
parameter_list|(
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
name|relatedKeywords
parameter_list|)
block|{
name|this
operator|.
name|relatedKeywords
operator|=
name|relatedKeywords
expr_stmt|;
block|}
block|}
end_class

end_unit

