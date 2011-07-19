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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|OntologyResourceHelper
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|mapping
operator|.
name|BridgeDefinitions
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|DObjectAdapter
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccess
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccessException
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccessManager
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
name|ontology
operator|.
name|OntModel
import|;
end_import

begin_comment
comment|/**  * {@link MappingEngine} is a wrapper that governs a mapping environment and able to start extraction process  * on request.<br/>  * Also provides two helper classer to processors that can reduce the work done by each processor. These  * helper classes are:  *<ol>  *<li>  * {@link DObjectAdapter}: Processor may want to access some properties that are not listed in plain CMS  * models from package {@linkplain org.apache.stanbol.cmsadapter.servicesapi.model.web}. This adapter can wrap  * plain CMS Objects into Decorated CMS Objects which can silently access a remote repository an fetch data  * not provided by plain CMS Objects.</li>  *<li>  * {@link OntologyResourceHelper}: When processors are extract new triples, they need to create OWL classes,  * individuals and properties. When these entity directly corresponds to a CMS node, type or property, need  * for a generic mapper that maps OWL entities to CMS objects arise. Using {@link OntologyResourceHelper}  * processors can create the OWL entities that corresponds any CMS object without having to keeping track of  * the created classes</li>  *</ol>  *   *   * @author Suat  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|MappingEngine
block|{
comment|/**      * When extraction process includes {@link BridgeDefinitions} (i.e. registering a new bridge definition or      * updating an existing one), this method is called. This function necessarily accesses a CMS repository.      *       * @param conf      *            Configuration that defines mapping/extraction environment.      * @throws RepositoryAccessException      *       *       */
name|void
name|mapCR
parameter_list|(
name|MappingConfiguration
name|conf
parameter_list|)
throws|throws
name|RepositoryAccessException
function_decl|;
comment|/**      * This method is called when a list of CMS objects are posted for lifting for the first time.      *       * @param conf      *            Configuration that defines mapping/extraction environment.      */
name|void
name|createModel
parameter_list|(
name|MappingConfiguration
name|conf
parameter_list|)
function_decl|;
comment|/**      * This method is called when a list of previously submitted CMS objects are posted for updating.      *       * @param conf      *            Configuration that defines mapping/extraction environment.      */
name|void
name|updateModel
parameter_list|(
name|MappingConfiguration
name|conf
parameter_list|)
function_decl|;
comment|/**      * This method is called when a list of previously submitted CMS objects are posted for removal. After      * execution all processors are expected to delete previously generated triples by themselves from the      * extracted ontology model.      *       * @param conf      *            Configuration that defines mapping/extraction environment.      */
name|void
name|deleteModel
parameter_list|(
name|MappingConfiguration
name|conf
parameter_list|)
function_decl|;
comment|/**      *       * @return The URI of the ontology which will be generated in lifting process.      */
name|String
name|getOntologyURI
parameter_list|()
function_decl|;
comment|/**      *       * @return Ontology which is being generated in the lifting process.      */
name|OntModel
name|getOntModel
parameter_list|()
function_decl|;
comment|/**      * Getter for {@link DObjectAdapter} in this lifting context.      *       */
name|DObjectAdapter
name|getDObjectAdapter
parameter_list|()
function_decl|;
comment|/**      * Getter for {@link OntologyResourceHelper} in this lifting context.      *       */
name|OntologyResourceHelper
name|getOntologyResourceHelper
parameter_list|()
function_decl|;
comment|/**      * Getter for CMS Session in this lifting context.      */
name|Object
name|getSession
parameter_list|()
function_decl|;
comment|/**      * Getter for {@link BridgeDefinitions} (if any) in this lifting context.      */
name|BridgeDefinitions
name|getBridgeDefinitions
parameter_list|()
function_decl|;
comment|/**      * Getter for {@link RepositoryAccessManager} in this lifting context.      */
name|RepositoryAccessManager
name|getRepositoryAccessManager
parameter_list|()
function_decl|;
comment|/**      * Getter for {@link RepositoryAccess} in this lifting context.      */
name|RepositoryAccess
name|getRepositoryAccess
parameter_list|()
function_decl|;
comment|/**      * Getter for {@link NamingStrategy} in this lifting context.      */
name|NamingStrategy
name|getNamingStrategy
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

