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
name|io
operator|.
name|OriginOrInputSource
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
name|scope
operator|.
name|OntologyScope
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
name|scope
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
name|scope
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
name|scope
operator|.
name|ScopeRegistry
import|;
end_import

begin_comment
comment|/**  * An Ontology Network Manager holds all references and tools for creating, modifying and deleting the logical  * realms that store Web Ontologies, as well as offer facilities for handling the ontologies contained  * therein.<br>  *<br>  * Note that since this object is both a {@link ScopeRegistry} and an {@link OntologyScopeFactory}, the call  * to {@link ScopeRegistry#registerScope(OntologyScope)} or its overloads after  * {@link OntologyScopeFactory#createOntologyScope(String, OriginOrInputSource...)} is unnecessary, as the  * ONManager automatically registers newly created scopes.  *   * @author alexdma, anuzzolese  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ONManager
extends|extends
name|ScopeRegistry
extends|,
name|OntologyScopeFactory
block|{
comment|/**      * The key used to configure the path of the ontology network configuration.      */
name|String
name|CONFIG_ONTOLOGY_PATH
init|=
literal|"org.apache.stanbol.ontologymanager.ontonet.onconfig"
decl_stmt|;
comment|/**      * The key used to configure the connectivity policy.      */
name|String
name|CONNECTIVITY_POLICY
init|=
literal|"org.apache.stanbol.ontologymanager.ontonet.connectivity"
decl_stmt|;
comment|/**      * The key used to configure the simple identifier of the scope registry (which should also be      * concatenated with the base namespace to obtain the registry's HTTP endpoint URI).      */
name|String
name|ID_SCOPE_REGISTRY
init|=
literal|"org.apache.stanbol.ontologymanager.ontonet.scopeRegistry.id"
decl_stmt|;
comment|/**      * Returns the offline configuration set for this ontology network manager, if any.      *       * @return the offline configuration, or null if none was set.      */
name|OfflineConfiguration
name|getOfflineConfiguration
parameter_list|()
function_decl|;
comment|/**      * Implementations should be able to create a {@link File} object from this path.      *       * @return the local path of the ontology storing the ontology network configuration.      */
name|String
name|getOntologyNetworkConfigurationPath
parameter_list|()
function_decl|;
comment|/**      * Returns the base namespace to be used for the Stanbol ontology network (e.g. for the creation of new      * scopes). For convenience, it is returned as a string so that it can be concatenated to form IRIs.      *       * @deprecated please use {@link OfflineConfiguration#getDefaultOntologyNetworkNamespace()} to obtain the      *             namespace      *       * @return the base namespace of the Stanbol ontology network.      */
name|String
name|getOntologyNetworkNamespace
parameter_list|()
function_decl|;
comment|/**      * Returns the ontology scope factory that was created along with the manager context.      *       * @deprecated This methods now returns the current object, which is also an {@link OntologyScopeFactory}.      * @return the default ontology scope factory      */
name|OntologyScopeFactory
name|getOntologyScopeFactory
parameter_list|()
function_decl|;
comment|/**      * Returns the ontology space factory that was created along with the manager context.<br>      *<br>      * Note: Because this can be backend-dependent, this method is not deprecated yet.      *       * @return the default ontology space factory.      */
name|OntologySpaceFactory
name|getOntologySpaceFactory
parameter_list|()
function_decl|;
comment|/**      * Returns the unique ontology scope registry for this context.      *       * @deprecated This methods now returns the current object, which is also a {@link ScopeRegistry}.      * @return the ontology scope registry.      */
name|ScopeRegistry
name|getScopeRegistry
parameter_list|()
function_decl|;
comment|/**      * Sets the IRI that will be the base namespace for all ontology scopes and collectors created by this      * object.      *       * @deprecated {@link ONManager} should set its namespace to be the same as      *             {@link OfflineConfiguration#getDefaultOntologyNetworkNamespace()} whenever it changes on      *             the object obtained by calling {@link #getOfflineConfiguration()}.      *       * @param namespace      *            the base namespace.      */
name|void
name|setOntologyNetworkNamespace
parameter_list|(
name|String
name|namespace
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

