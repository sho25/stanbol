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
name|RegistryItem
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
name|OWLOntologyCreationException
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
name|OWLOntologyManager
import|;
end_import

begin_comment
comment|/**  * A registry loader is a toolkit for loading all ontologies indexed by an ontology registry, or those  * referenced by one of the libraries within a registry.<br/>  *<br/>  * TODO will be dismissed along with its implementation in favor of the new registry management.  */
end_comment

begin_interface
annotation|@
name|Deprecated
specifier|public
interface|interface
name|RegistryLoader
block|{
comment|/**      * Loads all the OWL ontologies referenced by<code>registryItem</code>.      *       * @deprecated      *       * @param registryItem      *            the parent registry item.      * @param manager      *            the OWL ontology manager to use for loading (e.g. to avoid reloading ontologies).      * @param recurse      *            if true, load also ontologies that are indirectly referenced (e.g. if      *<code>registryItem</code> is a {@link Registry}).      * @return      * @throws OWLOntologyCreationException      */
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|gatherOntologies
parameter_list|(
name|RegistryItem
name|registryItem
parameter_list|,
name|OWLOntologyManager
name|manager
parameter_list|,
name|boolean
name|recurse
parameter_list|)
throws|throws
name|OWLOntologyCreationException
function_decl|;
comment|/**      * @deprecated obsolete. Refer to {@link Registry#getChild(IRI)} instead.      */
name|Library
name|getLibrary
parameter_list|(
name|Registry
name|reg
parameter_list|,
name|IRI
name|libraryID
parameter_list|)
function_decl|;
comment|/**      * @deprecated obsolete. Refer to {@link RegistryItem#getParent(IRI)} instead.      */
name|Object
name|getParent
parameter_list|(
name|Object
name|child
parameter_list|)
function_decl|;
comment|/**      * @deprecated obsolete. Refer to {@link RegistryItem#hasChildren()} instead.      */
name|boolean
name|hasChildren
parameter_list|(
name|Object
name|parent
parameter_list|)
function_decl|;
comment|/**      * @deprecated obsolete. Refer to {@link Registry#getChild(IRI)} instead.      */
name|boolean
name|hasLibrary
parameter_list|(
name|Registry
name|reg
parameter_list|,
name|IRI
name|libraryID
parameter_list|)
function_decl|;
comment|/**      * Only extracts the ontologies belonging to the library specified, if found in the registry at the      * supplied location.      *       *       * @deprecated This method does not what is supposed to do (ontology loading is selective, not model      *             construction). Calls to this method should be replaced by the sequence:      *             {@link RegistryManager#createModel(Set)} and {@link RegistryManager#getRegistry(IRI)}.      *       * @param registryPhysicalRIRI      * @param libraryID      * @return      */
name|Registry
name|loadLibrary
parameter_list|(
name|IRI
name|registryPhysicalIRI
parameter_list|,
name|IRI
name|libraryID
parameter_list|)
function_decl|;
comment|/**      * @deprecated obsolete      * @throws RegistryContentException      */
name|void
name|loadLocations
parameter_list|()
throws|throws
name|RegistryContentException
function_decl|;
comment|/**      * The ontology at<code>physicalIRI</code> may in turn include more than one library.      *       * @deprecated Calls to this method should be replaced by the sequence:      *             {@link RegistryManager#createModel(Set)} and {@link RegistryManager#getRegistry(IRI)}.      *       * @param physicalIRI      * @return      */
name|Registry
name|loadRegistry
parameter_list|(
name|IRI
name|registryPhysicalIRI
parameter_list|,
name|OWLOntologyManager
name|mgr
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

