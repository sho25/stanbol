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
name|java
operator|.
name|io
operator|.
name|File
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
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|OntologyIndex
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
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|OntologyScopeFactory
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
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|OntologySpaceFactory
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
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|ScopeRegistry
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
name|ontonet
operator|.
name|api
operator|.
name|session
operator|.
name|SessionManager
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
name|ontonet
operator|.
name|impl
operator|.
name|io
operator|.
name|ClerezzaOntologyStorage
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
name|ontonet
operator|.
name|impl
operator|.
name|ontology
operator|.
name|OWLOntologyManagerFactoryImpl
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
name|OWLDataFactory
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
name|OWLOntologyAlreadyExistsException
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
comment|/**  * An Ontology Network Manager holds all references and tools for creating, modifying and deleting the logical  * realms that store Web Ontologies, as well as offer facilities for handling the ontologies contained  * therein.  */
end_comment

begin_interface
specifier|public
interface|interface
name|ONManager
block|{
comment|/**      * The key used to configure the path of the ontology network configuration.      */
name|String
name|CONFIG_ONTOLOGY_PATH
init|=
literal|"org.apache.stanbol.ontologymanager.ontonet.onconfig"
decl_stmt|;
comment|/**      * The key used to configure the ID of the ontology network manager.      */
name|String
name|ID
init|=
literal|"org.apache.stanbol.ontologymanager.ontonet.id"
decl_stmt|;
comment|/**      * The key used to configure the base namespace of the ontology network.      */
name|String
name|ONTOLOGY_NETWORK_NS
init|=
literal|"org.apache.stanbol.ontologymanager.ontonet.ns"
decl_stmt|;
comment|/**      * Returns the ID of the ontology network manager.      *       * @return the ID of the ontology network manager.      */
name|String
name|getID
parameter_list|()
function_decl|;
comment|/**      * Returns the default object that automatically indexes ontologies as they are loaded within scopes.      *       * @return the default ontology index.      */
name|OntologyIndex
name|getOntologyIndex
parameter_list|()
function_decl|;
name|OWLOntologyManagerFactoryImpl
name|getOntologyManagerFactory
parameter_list|()
function_decl|;
comment|/**      * Implementations should be able to create a {@link File} object from this path.      *       * @return the local path of the ontology storing the ontology network configuration.      */
name|String
name|getOntologyNetworkConfigurationPath
parameter_list|()
function_decl|;
comment|/**      * Returns the base namespace to be used for the Stanbol ontology network (e.g. for the creation of new      * scopes). For convenience, it is returned as a string so that it can be concatenated to form IRIs.      *       * @return the base namespace of the Stanbol ontology network.      */
name|String
name|getOntologyNetworkNamespace
parameter_list|()
function_decl|;
comment|/**      * Returns the ontology scope factory that was created along with the manager context.      *       * @return the default ontology scope factory      */
name|OntologyScopeFactory
name|getOntologyScopeFactory
parameter_list|()
function_decl|;
comment|/**      * Returns the ontology space factory that was created along with the manager context.      *       * @return the default ontology space factory.      */
name|OntologySpaceFactory
name|getOntologySpaceFactory
parameter_list|()
function_decl|;
comment|/**      * Returns the default ontology storage system for this KReS instance.      *       * @return the default ontology store.      */
name|ClerezzaOntologyStorage
name|getOntologyStore
parameter_list|()
function_decl|;
comment|/**      * Returns an OWL Ontology Manager that is never cleared of its ontologies, so it can be used for caching      * ontologies without having to reload them using other managers. It is sufficient to catch      * {@link OWLOntologyAlreadyExistsException}s and obtain the ontology with that same ID from this manager.      *       * @return the OWL Ontology Manager used for caching ontologies.      */
name|OWLOntologyManager
name|getOwlCacheManager
parameter_list|()
function_decl|;
comment|/**      * Returns a factory object that can be used for obtaining OWL API objects.      *       * @return the default OWL data factory      */
name|OWLDataFactory
name|getOwlFactory
parameter_list|()
function_decl|;
comment|/**      * Returns the unique ontology scope registry for this context.      *       * @return the ontology scope registry.      */
name|ScopeRegistry
name|getScopeRegistry
parameter_list|()
function_decl|;
comment|/**      * Returns the unique KReS session manager for this context.      *       * @return the KreS session manager.      */
name|SessionManager
name|getSessionManager
parameter_list|()
function_decl|;
comment|/**      * Returns the list of IRIs that identify scopes that should be activated on startup,<i>if they      * exist</i>.      *       * @return the list of scope IDs to activate.      */
name|String
index|[]
name|getUrisToActivate
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

