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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
package|;
end_package

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
import|;
end_import

begin_comment
comment|/**  * Denotes any API artifact that has an identifier. Both the identifier and the concatenation of the  * namespace with the identifier should be unique in the system. Having both a non-null namespace and ID is  * optional, but at least one of them should be non-null.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|NamedArtifact
block|{
comment|/**      * Returns the namespace that should prefix the ID of this resource and all the resources managed thereby.      * This is also used by ontology collectors to dynamically generate import statements.      *       * @return the default namespace for this resources.      */
name|IRI
name|getDefaultNamespace
parameter_list|()
function_decl|;
comment|/**      * Returns a string (assumed to be unique in the system) that identifies this resource. For instance, a      * parent IRI of the base IRIs for the ontologies within an ontology space. Naming schemes are      * implementation-dependent.<br>      *<br>      * Note that in general a named resource is not aware of its uniqueness. It is not even implied that      * objects of different types cannot have the same ID. These aspects should be ensured by registries,      * indexers etc.<br>      *<br>      * TODO check if a type other than String (e.g. URI, IRI, UriRef) should be used.      *       * @return an identifier for this resource.      */
name|String
name|getID
parameter_list|()
function_decl|;
comment|/**      * @deprecated use {@link #getDefaultNamespace()} instead.      * @return      */
name|IRI
name|getNamespace
parameter_list|()
function_decl|;
comment|/**      * Sets the namespace that should prefix the ID of this resource and all the resources managed thereby.      * This is also used by ontology collectors to dynamically generate import statements.      *       * @param namespace      *            the new default namespace for this resources.      */
name|void
name|setDefaultNamespace
parameter_list|(
name|IRI
name|namespace
parameter_list|)
function_decl|;
comment|/**      * @deprecated use {@link #setDefaultNamespace(IRI)} instead.      * @param namespace      */
name|void
name|setNamespace
parameter_list|(
name|IRI
name|namespace
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

