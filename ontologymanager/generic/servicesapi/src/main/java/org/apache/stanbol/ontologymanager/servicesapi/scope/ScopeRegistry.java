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
operator|.
name|scope
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
name|servicesapi
operator|.
name|collector
operator|.
name|DuplicateIDException
import|;
end_import

begin_comment
comment|/**  * A registry that keeps track of the active ontology scopes in a running KReS instance.<br>  *<br>  * TODO deprecate scope registration methods and manage registration automcatically.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ScopeRegistry
block|{
comment|/**      * Adds a scope registration listener to this registry. If the listener was already added, this should      * result in no effect.      *       * @param listener      *            the listener to be added      */
name|void
name|addScopeRegistrationListener
parameter_list|(
name|ScopeEventListener
name|listener
parameter_list|)
function_decl|;
comment|/**      * Removes all registered scope registration listeners.      */
name|void
name|clearScopeRegistrationListeners
parameter_list|()
function_decl|;
comment|/**      *       * @param scopeID      * @return true iff an ontology scope with ID<code>scopeID</code> is registered.      */
name|boolean
name|containsScope
parameter_list|(
name|String
name|scopeID
parameter_list|)
function_decl|;
comment|/**      * Removes an ontology scope from this registry, thus deactivating the scope and all of its associated      * spaces. All attached listeners should hear this deregistration on their      *<code>scopeDeregistered()</code> method.      *       * @param scope      *            the ontology scope to be removed      */
name|void
name|deregisterScope
parameter_list|(
name|Scope
name|scope
parameter_list|)
function_decl|;
name|Set
argument_list|<
name|Scope
argument_list|>
name|getActiveScopes
parameter_list|()
function_decl|;
comment|/**      * Returns the set of registered ontology scopes.      *       * @return the set of ontology scopes      */
name|Set
argument_list|<
name|Scope
argument_list|>
name|getRegisteredScopes
parameter_list|()
function_decl|;
comment|/**      * Returns the unique ontology scope identified by the given ID.      *       * @param scopeID      *            the scope identifier      * @return the ontology scope with that ID, or null if no scope with such ID is registered      */
name|Scope
name|getScope
parameter_list|(
name|String
name|scopeID
parameter_list|)
function_decl|;
comment|/**      * Returns the set of registered scope registration listeners, in no particular order.      *       * @return the set of scope registration listeners      */
name|Set
argument_list|<
name|ScopeEventListener
argument_list|>
name|getScopeRegistrationListeners
parameter_list|()
function_decl|;
name|boolean
name|isScopeActive
parameter_list|(
name|String
name|scopeID
parameter_list|)
function_decl|;
comment|/**      * Equivalent to<code>registerScope(scope, false)</code>.      *       * @param scope      *            the ontology scope to be added      */
name|void
name|registerScope
parameter_list|(
name|Scope
name|scope
parameter_list|)
throws|throws
name|DuplicateIDException
function_decl|;
comment|/**      * Adds an ontology scope to this registry, thus activating the scope if<code>activate</code> is set and      * (at a bare minumum) its core space. All attached listeners should hear this registration on their      *<code>scopeRegistered()</code> method.      *       * @param scope      *            the ontology scope to be added      */
name|void
name|registerScope
parameter_list|(
name|Scope
name|scope
parameter_list|,
name|boolean
name|activate
parameter_list|)
throws|throws
name|DuplicateIDException
function_decl|;
comment|/**      * Removes a scope registration listener from this registry. If the listener was not previously added,      * this should result in no effect.      *       * @param listener      *            the listener to be removed      */
name|void
name|removeScopeRegistrationListener
parameter_list|(
name|ScopeEventListener
name|listener
parameter_list|)
function_decl|;
name|void
name|setScopeActive
parameter_list|(
name|String
name|scopeID
parameter_list|,
name|boolean
name|active
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

