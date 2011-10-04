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
name|Set
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
comment|/**  * The interface to represent entities which resides in another place outside the Stanbol Contenthub.  * {@link ExternalResource}s are created when an external entity is encountered within a {@link SearchEngine}.  *   * @author cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ExternalResource
extends|extends
name|KeywordRelated
block|{
comment|/**      * Retrieves the URI of this resource. This URI is an external reference, it should be dereferenceable.      * Hence, returns the same value as {@link #getDereferenceableURI()}.      *       * @return Dereferenceable URI of this resource.      */
name|String
name|getReference
parameter_list|()
function_decl|;
comment|/**      * If there exist any types associated with this {@link ExternalResource}, they are returned as a      * {@link Set<String>}. Types are determined through<code>rdf:type</code> property for      * {@link ExternalResource}s.      *       * @return Set of types associated with this {@link ExternalResource}.      */
name|Set
argument_list|<
name|String
argument_list|>
name|getTypes
parameter_list|()
function_decl|;
comment|/**      * Associates a new type with this {@link ExternalResource}.      *       * @param type      *            The type to be added.      */
name|void
name|addType
parameter_list|(
name|String
name|type
parameter_list|)
function_decl|;
comment|/**      * If this {@link ExternalResource} causes any {@link DocumentResource} to be added to the      * {@link SearchContext}, then they are related. This function returns the {@link DocumentResource}s,      * which are added to the {@link SearchContext} by means of this {@link ExternalResource}.      *       * @return A list of {@link DocumentResource}s.      */
name|Set
argument_list|<
name|DocumentResource
argument_list|>
name|getRelatedDocuments
parameter_list|()
function_decl|;
comment|/**      * Associates a new document with this {@link ExternalResource}.      *       * @param documentResources      *            The {@link DocumentResource} representing the document to be associated with this      *            {@link ExternalResource}.      */
name|void
name|addRelatedDocument
parameter_list|(
name|DocumentResource
name|documentResources
parameter_list|)
function_decl|;
comment|/**      * Retrieves the URI of this resource. This URI is an external reference and the returned URI is      * dereferenceable.      *       * @return Dereferenceable URI of this resource.      */
name|String
name|getDereferenceableURI
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

