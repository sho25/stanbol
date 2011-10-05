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
name|core
operator|.
name|utils
package|;
end_package

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
name|vocabulary
operator|.
name|SearchVocabulary
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Resource
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|util
operator|.
name|iterator
operator|.
name|Filter
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|vocabulary
operator|.
name|RDF
import|;
end_import

begin_comment
comment|/**  *   * @author cihan  *   */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|Filters
block|{
specifier|public
specifier|static
specifier|final
name|Filter
argument_list|<
name|Resource
argument_list|>
name|CLASS_RESOURCE_FILTER
init|=
operator|new
name|Filter
argument_list|<
name|Resource
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|Resource
name|o
parameter_list|)
block|{
return|return
name|o
operator|.
name|hasProperty
argument_list|(
name|RDF
operator|.
name|type
argument_list|,
name|SearchVocabulary
operator|.
name|CLASS_RESOURCE
argument_list|)
return|;
block|}
block|}
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Filter
argument_list|<
name|Resource
argument_list|>
name|INDIVIDUAL_RESOURCE_FILTER
init|=
operator|new
name|Filter
argument_list|<
name|Resource
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|Resource
name|o
parameter_list|)
block|{
return|return
name|o
operator|.
name|hasProperty
argument_list|(
name|RDF
operator|.
name|type
argument_list|,
name|SearchVocabulary
operator|.
name|INDIVIDUAL_RESOURCE
argument_list|)
return|;
block|}
block|}
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Filter
argument_list|<
name|Resource
argument_list|>
name|DOCUMENT_RESOURCE_FILTER
init|=
operator|new
name|Filter
argument_list|<
name|Resource
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|Resource
name|o
parameter_list|)
block|{
return|return
name|o
operator|.
name|hasProperty
argument_list|(
name|RDF
operator|.
name|type
argument_list|,
name|SearchVocabulary
operator|.
name|DOCUMENT_RESOURCE
argument_list|)
return|;
block|}
block|}
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Filter
argument_list|<
name|Resource
argument_list|>
name|EXTERNAL_RESOURCE_FILTER
init|=
operator|new
name|Filter
argument_list|<
name|Resource
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|Resource
name|o
parameter_list|)
block|{
return|return
name|o
operator|.
name|hasProperty
argument_list|(
name|RDF
operator|.
name|type
argument_list|,
name|SearchVocabulary
operator|.
name|EXTERNAL_RESOURCE
argument_list|)
return|;
block|}
block|}
decl_stmt|;
specifier|private
name|Filters
parameter_list|()
block|{      }
block|}
end_class

end_unit
