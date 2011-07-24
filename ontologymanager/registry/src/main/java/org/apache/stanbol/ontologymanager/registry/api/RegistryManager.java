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
name|CachingPolicy
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
name|RegistryOntology
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
name|OWLOntology
import|;
end_import

begin_comment
comment|/**  * Replacement for {@link RegistryLoader}. Also indexes libraries (and ontologies?).  */
end_comment

begin_interface
specifier|public
interface|interface
name|RegistryManager
block|{
comment|/**      * The key used to configure the caching policy of the registry manager.      */
specifier|public
name|String
name|CACHING_POLICY
init|=
literal|"org.apache.stanbol.ontologymanager.registry.cachingPolicy"
decl_stmt|;
comment|/**      * The key used to configure the ontology loading policy of the registry manager.      */
specifier|public
name|String
name|LAZY_LOADING
init|=
literal|"org.apache.stanbol.ontologymanager.registry.laziness"
decl_stmt|;
comment|/**      * The key used to configure the locations of the registries to be scanned by the registry manager.      */
specifier|public
name|String
name|REGISTRY_LOCATIONS
init|=
literal|"org.apache.stanbol.ontologymanager.registry.locations"
decl_stmt|;
comment|/**      * Adds a registry to the set of ontology registries managed by this object.      *       * @param registry      *            the ontology registry to be added.      */
name|void
name|addRegistry
parameter_list|(
name|Registry
name|registry
parameter_list|)
function_decl|;
comment|/**      * Clears the set of ontology registries managed by this object.      */
name|void
name|clearRegistries
parameter_list|()
function_decl|;
comment|/**      * Constructs the models of all the registry items discovered by scanning the supplied ontologies that are      * supposed to denote registries.<br/>      *<br/>      * To denote a registry, an ontology must instantiate the metamodel at<a      * href="http://www.ontologydesignpatterns.org/schemas/meta.owl"      *>http://www.ontologydesignpatterns.org/schemas/meta.owl</a><br/>      *<br/>      * Depending on implementations, this method may or may not be automatically invoked after a call to      * methods that determine changes to the model.      *       * @param registryOntologies      *            the source OWL ontologies that describe the registries. If any of these denotes an invalid      *            registry, a {@link RegistryContentException} will be thrown. If it does not denote a      *            registry at all, it will be skipped.      * @return the ontology registries that are the parent items of the entire model.      */
name|Set
argument_list|<
name|Registry
argument_list|>
name|createModel
parameter_list|(
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|registryOntologies
parameter_list|)
function_decl|;
comment|/**      * Returns the ontology caching policy currently adopted by the registry manager.      *       * @return the caching policy.      */
name|CachingPolicy
name|getCachingPolicy
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
comment|/**      * Returns the set of registries managed by this object.      *       * @return the set of all managed registries.      */
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
comment|/**      * Determines if the registry manager is set to load its resources only when a specific request for them      * (e.g. by a call to {@link RegistryOntology#asOWLOntology()} or {@link Library#getOntologies()} is      * issued).      *       * @return true if set to load resources only upon request, false if set to load all resources eagerly      *         when the model has been built.      */
name|boolean
name|isLazyLoading
parameter_list|()
function_decl|;
comment|/**      * Removes a registry from the set of ontology registries managed by this object.      *       * @param registry      *            the ontology registry to be removed.      */
name|void
name|removeRegistry
parameter_list|(
name|IRI
name|registryId
parameter_list|)
function_decl|;
comment|/**      * Sets the resource loading policy of this registry manager. There is no guarantee that setting a policy      * after a model has already been created will affect the existing model (i.e. unload all its ontologies      * if true, load them if false), but it will affect any subsequent calls to {@link #createModel(Set)}.      *       * @param lazy      *            if true, the registry manager will be set to load resources only upon request, otherwise it      *            will be set to load all resources eagerly when the model has been built.      */
name|void
name|setLazyLoading
parameter_list|(
name|boolean
name|lazy
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

