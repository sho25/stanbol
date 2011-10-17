begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
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
operator|.
name|ontology
package|;
end_package

begin_comment
comment|/**  * Objects that want to listen to the registration of ontology scopes should  * implement this interface and add themselves as listener to a scope registry.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ScopeEventListener
block|{
comment|/** 	 * Called<i>after</i> an ontology scope, assuming it is already registered 	 * somewhere, is activated. 	 *  	 * @param scope 	 *            the activated ontology scope 	 */
name|void
name|scopeActivated
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
function_decl|;
comment|/** 	 * Called<i>after</i> a new ontology scope has been created. 	 *  	 * @param scope 	 *            the created ontology scope 	 */
name|void
name|scopeCreated
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
function_decl|;
comment|/** 	 * Called<i>after</i> an ontology scope, assuming it is already registered 	 * somewhere, is deactivated. If the deactivation of a scope implies 	 * deregistering of it, a separate event should be fired for deregistration. 	 *  	 * @param scope 	 *            the deactivated ontology scope 	 */
name|void
name|scopeDeactivated
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
function_decl|;
comment|/** 	 * Called<i>after</i> an ontology scope is removed from the scope registry. 	 *  	 * @param scope 	 *            the deregistered ontology scope 	 */
name|void
name|scopeDeregistered
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
function_decl|;
comment|/** 	 * Called<i>after</i> an ontology scope is added to the scope registry. 	 *  	 * @param scope 	 *            the registered ontology scope 	 */
name|void
name|scopeRegistered
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

