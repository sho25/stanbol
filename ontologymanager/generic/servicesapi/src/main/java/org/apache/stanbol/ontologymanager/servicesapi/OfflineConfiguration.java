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
name|servicesapi
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
name|ontologymanager
operator|.
name|servicesapi
operator|.
name|scope
operator|.
name|ScopeManager
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
comment|/**  * Provides the configuration needed for the {@link ScopeManager}. A configuration should only be handled  * internally by the {@link ScopeManager} implementation.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|OfflineConfiguration
block|{
comment|/**      * The key used to configure the base namespace of the ontology network.      */
name|String
name|DEFAULT_NS
init|=
literal|"org.apache.stanbol.ontologymanager.ontonet.ns"
decl_stmt|;
comment|/**      * The key used to configure the paths of local ontologies.      */
name|String
name|ONTOLOGY_PATHS
init|=
literal|"org.apache.stanbol.ontologymanager.ontonet.ontologypaths"
decl_stmt|;
comment|/**      * Returns the namespace that will be used as the prefix for all named artifacts when exported to RDF/OWL,      * unless overridden manually (e.g. by calling the RESTful API on another base IRI).      *       * @return the default ontology network namespace      */
name|IRI
name|getDefaultOntologyNetworkNamespace
parameter_list|()
function_decl|;
comment|/**      * Returns the paths of all the directories where the ontology network manager will try to locate      * ontologies. These directories will be prioritaire if the engine is set to run in offline mode. This      * list is ordered in that the higher-ordered directories generally override lower-ordered ones, that is,      * any ontologies found in the directories belonging to the tail of this list will supersede any      * ontologies with the same ID found in the directories belonging to its head.      *       * @return an ordered list of directory paths for offline ontologies.      */
name|List
argument_list|<
name|IRI
argument_list|>
name|getOntologySourceLocations
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

