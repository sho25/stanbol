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
name|search
operator|.
name|execution
package|;
end_package

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
name|Collections
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
name|stanbol
operator|.
name|contenthub
operator|.
name|core
operator|.
name|utils
operator|.
name|Filters
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
name|execution
operator|.
name|ClassResource
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
name|execution
operator|.
name|DocumentResource
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
name|execution
operator|.
name|ExternalResource
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
name|execution
operator|.
name|IndividualResource
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
name|execution
operator|.
name|Keyword
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
name|execution
operator|.
name|QueryKeyword
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
name|enhanced
operator|.
name|EnhGraph
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
name|graph
operator|.
name|Node
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
name|vocabulary
operator|.
name|RDF
import|;
end_import

begin_comment
comment|/**  * Implementation of {@link Keyword}.  *   * @author cihan  *   */
end_comment

begin_class
specifier|public
class|class
name|KeywordImpl
extends|extends
name|AbstractScored
implements|implements
name|Keyword
block|{
specifier|protected
specifier|final
name|SearchContextFactoryImpl
name|factory
decl_stmt|;
name|KeywordImpl
parameter_list|(
name|Node
name|n
parameter_list|,
name|EnhGraph
name|g
parameter_list|,
name|String
name|keyword
parameter_list|,
name|Double
name|weight
parameter_list|,
name|Double
name|score
parameter_list|,
name|SearchContextFactoryImpl
name|factory
parameter_list|)
block|{
name|super
argument_list|(
name|n
argument_list|,
name|g
argument_list|,
name|weight
argument_list|,
name|score
argument_list|)
expr_stmt|;
name|this
operator|.
name|factory
operator|=
name|factory
expr_stmt|;
name|this
operator|.
name|addLiteral
argument_list|(
name|SearchVocabulary
operator|.
name|KEYWORD_STRING
argument_list|,
name|keyword
argument_list|)
expr_stmt|;
name|this
operator|.
name|addProperty
argument_list|(
name|RDF
operator|.
name|type
argument_list|,
name|SearchVocabulary
operator|.
name|KEYWORD
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getKeyword
parameter_list|()
block|{
return|return
name|this
operator|.
name|getPropertyValue
argument_list|(
name|SearchVocabulary
operator|.
name|KEYWORD_STRING
argument_list|)
operator|.
name|asLiteral
argument_list|()
operator|.
name|getLexicalForm
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|QueryKeyword
name|getRelatedQueryKeyword
parameter_list|()
block|{
return|return
name|factory
operator|.
name|getQueryKeyword
argument_list|(
name|getPropertyValue
argument_list|(
name|SearchVocabulary
operator|.
name|RELATED_QUERY_KEYWORD
argument_list|)
operator|.
name|asResource
argument_list|()
operator|.
name|getURI
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|ClassResource
argument_list|>
name|getRelatedClassResources
parameter_list|()
block|{
name|List
argument_list|<
name|ClassResource
argument_list|>
name|resources
init|=
operator|new
name|ArrayList
argument_list|<
name|ClassResource
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Resource
name|res
range|:
name|this
operator|.
name|getModel
argument_list|()
operator|.
name|listResourcesWithProperty
argument_list|(
name|SearchVocabulary
operator|.
name|RELATED_KEYWORD
argument_list|,
name|this
argument_list|)
operator|.
name|filterKeep
argument_list|(
name|Filters
operator|.
name|CLASS_RESOURCE_FILTER
argument_list|)
operator|.
name|toList
argument_list|()
control|)
block|{
name|resources
operator|.
name|add
argument_list|(
name|factory
operator|.
name|getClassResource
argument_list|(
name|res
operator|.
name|getPropertyResourceValue
argument_list|(
name|SearchVocabulary
operator|.
name|CLASS_URI
argument_list|)
operator|.
name|getURI
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|resources
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|IndividualResource
argument_list|>
name|getRelatedIndividualResources
parameter_list|()
block|{
name|List
argument_list|<
name|IndividualResource
argument_list|>
name|resources
init|=
operator|new
name|ArrayList
argument_list|<
name|IndividualResource
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Resource
name|res
range|:
name|this
operator|.
name|getModel
argument_list|()
operator|.
name|listResourcesWithProperty
argument_list|(
name|SearchVocabulary
operator|.
name|RELATED_KEYWORD
argument_list|,
name|this
argument_list|)
operator|.
name|filterKeep
argument_list|(
name|Filters
operator|.
name|INDIVIDUAL_RESOURCE_FILTER
argument_list|)
operator|.
name|toList
argument_list|()
control|)
block|{
name|resources
operator|.
name|add
argument_list|(
name|factory
operator|.
name|getIndividualResource
argument_list|(
name|res
operator|.
name|getPropertyResourceValue
argument_list|(
name|SearchVocabulary
operator|.
name|INDIVIDUAL_URI
argument_list|)
operator|.
name|getURI
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|resources
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|DocumentResource
argument_list|>
name|getRelatedDocumentResources
parameter_list|()
block|{
name|List
argument_list|<
name|DocumentResource
argument_list|>
name|resources
init|=
operator|new
name|ArrayList
argument_list|<
name|DocumentResource
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Resource
name|res
range|:
name|this
operator|.
name|getModel
argument_list|()
operator|.
name|listResourcesWithProperty
argument_list|(
name|SearchVocabulary
operator|.
name|RELATED_KEYWORD
argument_list|,
name|this
argument_list|)
operator|.
name|filterKeep
argument_list|(
name|Filters
operator|.
name|DOCUMENT_RESOURCE_FILTER
argument_list|)
operator|.
name|toList
argument_list|()
control|)
block|{
name|resources
operator|.
name|add
argument_list|(
name|factory
operator|.
name|getDocumentResource
argument_list|(
name|res
operator|.
name|getURI
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|resources
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|ExternalResource
argument_list|>
name|getRelatedExternalResources
parameter_list|()
block|{
name|List
argument_list|<
name|ExternalResource
argument_list|>
name|resources
init|=
operator|new
name|ArrayList
argument_list|<
name|ExternalResource
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Resource
name|res
range|:
name|this
operator|.
name|getModel
argument_list|()
operator|.
name|listResourcesWithProperty
argument_list|(
name|SearchVocabulary
operator|.
name|RELATED_KEYWORD
argument_list|,
name|this
argument_list|)
operator|.
name|filterKeep
argument_list|(
name|Filters
operator|.
name|EXTERNAL_RESOURCE_FILTER
argument_list|)
operator|.
name|toList
argument_list|()
control|)
block|{
name|resources
operator|.
name|add
argument_list|(
name|factory
operator|.
name|getExternalResource
argument_list|(
name|res
operator|.
name|getURI
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|resources
argument_list|)
return|;
block|}
block|}
end_class

end_unit
