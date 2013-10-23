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
name|registry
operator|.
name|api
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
name|ontologymanager
operator|.
name|registry
operator|.
name|api
operator|.
name|model
operator|.
name|Library
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
name|ontologymanager
operator|.
name|registry
operator|.
name|api
operator|.
name|model
operator|.
name|Registry
import|;
end_import

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
comment|/**  * Objects that keep track of known libraries, registries and ontologies can implement this interface.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RegistryItemIndex
block|{
comment|/**      * Returns all the libraries managed by this object.      *       * @return the set of all managed libraries.      */
name|Set
argument_list|<
name|Library
argument_list|>
name|getLibraries
parameter_list|()
function_decl|;
comment|/**      * Returns all the libraries that contain the ontology with the given identifier.      *       * @param ontologyID      *            the ontology identifier.      * @return the set of libraries that contain the ontology.      */
name|Set
argument_list|<
name|Library
argument_list|>
name|getLibraries
parameter_list|(
name|IRI
name|ontologyID
parameter_list|)
function_decl|;
comment|/**      * Returns the library with the given identifier, if present.<br/>      *<br/>      * NOTE THAT IF THE REGISTRY ITEM EXIST BUT IS NOT A LIBRARY, THIS METHOD WILL RETURN NULL.      *       * @param id      *            the library identifier.      * @return the library with the given identifier, or null if not present or not a library.      */
name|Library
name|getLibrary
parameter_list|(
name|IRI
name|id
parameter_list|)
function_decl|;
comment|/**      * Returns all the registries managed by this object.      *       * @return the set of all managed registries.      */
name|Set
argument_list|<
name|Registry
argument_list|>
name|getRegistries
parameter_list|()
function_decl|;
comment|/**      * Returns all the registries that reference the library with the given identifier.      *       * @return the set of all managed registries.      */
name|Set
argument_list|<
name|Registry
argument_list|>
name|getRegistries
parameter_list|(
name|IRI
name|libraryID
parameter_list|)
function_decl|;
comment|/**      * Returns the registry with the given identifier, if present.<br/>      *<br/>      * NOTE THAT IF THE REGISTRY ITEM EXIST BUT IS NOT A REGISTRY, THIS METHOD WILL RETURN NULL.      *       * @param id      *            the registry identifier.      * @return the registry with the given identifier, or null if not present.      */
name|Registry
name|getRegistry
parameter_list|(
name|IRI
name|id
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

