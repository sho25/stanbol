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
import|import static
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
name|STANBOLRESERVED_PREFIX
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
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|response
operator|.
name|FacetField
operator|.
name|Count
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
name|store
operator|.
name|vocabulary
operator|.
name|SolrVocabulary
import|;
end_import

begin_class
specifier|public
class|class
name|FacetResultImpl
implements|implements
name|FacetResult
block|{
specifier|private
name|FacetField
name|facetField
decl_stmt|;
specifier|public
name|FacetResultImpl
parameter_list|(
name|FacetField
name|facetField
parameter_list|)
block|{
name|this
operator|.
name|facetField
operator|=
name|facetField
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|facetField
operator|.
name|getName
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getHtmlName
parameter_list|()
block|{
name|String
name|name
init|=
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|startsWith
argument_list|(
name|STANBOLRESERVED_PREFIX
argument_list|)
condition|)
block|{
return|return
name|name
operator|.
name|substring
argument_list|(
name|STANBOLRESERVED_PREFIX
operator|.
name|length
argument_list|()
argument_list|)
return|;
block|}
name|int
name|lastUnderscore
init|=
name|name
operator|.
name|lastIndexOf
argument_list|(
literal|'_'
argument_list|)
decl_stmt|;
if|if
condition|(
name|lastUnderscore
operator|!=
operator|-
literal|1
condition|)
block|{
name|String
name|underScoreExtension
init|=
name|name
operator|.
name|substring
argument_list|(
name|lastUnderscore
argument_list|)
decl_stmt|;
if|if
condition|(
name|SolrVocabulary
operator|.
name|DYNAMIC_FIELD_EXTENSIONS
operator|.
name|contains
argument_list|(
name|underScoreExtension
argument_list|)
condition|)
block|{
name|name
operator|=
name|name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|lastUnderscore
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|name
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|Count
argument_list|>
name|getValues
parameter_list|()
block|{
return|return
name|facetField
operator|.
name|getValues
argument_list|()
return|;
block|}
block|}
end_class

end_unit

