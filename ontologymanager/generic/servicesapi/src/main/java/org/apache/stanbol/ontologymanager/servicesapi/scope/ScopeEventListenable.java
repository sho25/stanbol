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
name|Collection
import|;
end_import

begin_comment
comment|/**  * Implementations of this interface are able to fire events related to the modification of an ontology scope,  * not necessarily including its ontologies.<br/>  *<br/>  * This interface adds support for CRUD operations on scope event listeners.  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ScopeEventListenable
block|{
comment|/**      * Registers a listener to scope-related events fired by this object.      *       * @param listener      *            the listener to be registered.      */
name|void
name|addScopeEventListener
parameter_list|(
name|ScopeEventListener
name|listener
parameter_list|)
function_decl|;
comment|/**      * Unregisters all the scope event listeners registered with this object.      */
name|void
name|clearScopeEventListeners
parameter_list|()
function_decl|;
comment|/**      * Gets all the scope event listeners registered with this object.      *       * @return the registered scope event listeners.      */
name|Collection
argument_list|<
name|ScopeEventListener
argument_list|>
name|getScopeEventListeners
parameter_list|()
function_decl|;
comment|/**      * Unregisters a listener to scope-related events fired by this object. Has no effect if the supplied      * listener was not registered with this object in the first place.      *       * @param listener      *            the listener to be unregistered.      */
name|void
name|removeScopeEventListener
parameter_list|(
name|ScopeEventListener
name|listener
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

