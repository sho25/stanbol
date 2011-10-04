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
name|servicesapi
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
name|servicesapi
operator|.
name|search
operator|.
name|engine
operator|.
name|SearchEngine
import|;
end_import

begin_comment
comment|/**  * The interface to represent an individual resource (instance of an ontology class), and its related  * resources. Related resources are attached to the {@link IndividualResource} as the {@link SearchEngine}s  * execute.  *   * @author cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|IndividualResource
extends|extends
name|KeywordRelated
block|{
comment|/**      * Retrieves the {@link ClassResource}s which this {@link IndividualResource} is related to.      *       * @return List of related Ontology Classes of this resource.      */
name|List
argument_list|<
name|ClassResource
argument_list|>
name|getRelatedClasses
parameter_list|()
function_decl|;
comment|/**      * Retrieves the {@link IndividualResource}s which this {@link IndividualResource} is related to.      *       * @return List of related Ontology Individuals (Class instances) of this resource.      */
name|List
argument_list|<
name|IndividualResource
argument_list|>
name|getRelatedIndividuals
parameter_list|()
function_decl|;
comment|/**      * Adds a {@link ClassResource} as related to this {@link IndividualResource}.      *       * @param classResource      *            The {@link ClassResource} which is related to this {@link IndividualResource}.      */
name|void
name|addRelatedClass
parameter_list|(
name|ClassResource
name|classResource
parameter_list|)
function_decl|;
comment|/**      * Adds an {@link IndividualResource} as related to this {@link IndividualResource}.      *       * @param individualResource      *            The {@link IndividualResource} which is related to this {@link IndividualResource}.      */
name|void
name|addRelatedIndividual
parameter_list|(
name|IndividualResource
name|individualResource
parameter_list|)
function_decl|;
comment|/**      * Returns the URI of this ontology individual within the ontology it resides.      *       * @return The URI of this {@link IndividualResource}.      */
name|String
name|getIndividualURI
parameter_list|()
function_decl|;
comment|/**      * Returns the dereferenceable URI of this ontology individual. Dereferenceable URI can be accessed      * through HTTP and presents this {@link IndividualResource}.      *       * @return The dereferenceable URI of this {@link IndividualResource}.      */
name|String
name|getDereferenceableURI
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

