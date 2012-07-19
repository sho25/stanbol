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
operator|.
name|session
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

begin_comment
comment|/**  * Objects that want to listen to events affecting sessions should implement this interface and add themselves  * as listener to a manager.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SessionListener
block|{
comment|/**      * Called whenever a scope is appended to a session.      *       * @param session      *            the affected session      * @param scopeId      *            the identifier of the scope that was attached.      */
name|void
name|scopeAppended
parameter_list|(
name|Session
name|session
parameter_list|,
name|String
name|scopeId
parameter_list|)
function_decl|;
comment|/**      * Called whenever a scope is detached from a session.      *       * @param session      *            the affected session      * @param scopeId      *            the identifier of the scope that was attached. Note that the corresponding      *            {@link OntologyScope} could be null if detachment occurred as a consequence of a scope      *            deletion.      * */
name|void
name|scopeDetached
parameter_list|(
name|Session
name|session
parameter_list|,
name|String
name|scopeId
parameter_list|)
function_decl|;
comment|/**      * Called whenever an event affecting a session is fired. This method encompasses all and only the event      * where it only interesting to know the affected session.      *       * @param event      *            the session event.      */
name|void
name|sessionChanged
parameter_list|(
name|SessionEvent
name|event
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

