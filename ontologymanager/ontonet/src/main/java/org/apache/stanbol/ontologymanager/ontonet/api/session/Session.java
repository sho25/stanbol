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
name|session
package|;
end_package

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
comment|/**  * Note that KReS sessions are possibly disjoint with HTTP sessions or the like.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|Session
extends|extends
name|SessionListenable
block|{
comment|/** 	 * The states a KReS session can be in: ACTIVE (for running sessions), 	 * HALTED (for inactive sessions that may later be activated, e.g. when a 	 * user logs in), ZOMBIE (inactive and bound for destruction, no longer 	 * referenceable). 	 *  	 * @author alessandro 	 *  	 */
enum|enum
name|State
block|{
comment|/** 		 * Running session 		 */
name|ACTIVE
block|,
comment|/** 		 * inactive sessions that may later be activated 		 */
name|HALTED
block|,
comment|/** 		 * Inactive and bound for destruction, no longer referenceable 		 */
name|ZOMBIE
block|}
comment|/** 	 * Closes this KReS Session irreversibly. Most likely includes setting the 	 * state to ZOMBIE. 	 */
name|void
name|close
parameter_list|()
throws|throws
name|NonReferenceableSessionException
function_decl|;
comment|/** 	 * Returns the unique Internationalized Resource Identifier (IRI) that 	 * identifies this KReS session.<br> 	 *<br> 	 * NOTE: There is no set method for the session ID as it is assumed to be 	 * set in its constructor once and for all. 	 *  	 * @return the IRI that identifies this session 	 */
name|IRI
name|getID
parameter_list|()
function_decl|;
comment|/** 	 * Returns the current state of this KReS session. 	 *  	 * @return the state of this session 	 */
name|State
name|getSessionState
parameter_list|()
function_decl|;
comment|/** 	 * Equivalent to<code>getState() == State.ACTIVE</code>. 	 *  	 * @return true iff this session is in the ACTIVE state 	 */
name|boolean
name|isActive
parameter_list|()
function_decl|;
name|void
name|open
parameter_list|()
throws|throws
name|NonReferenceableSessionException
function_decl|;
comment|/** 	 * Sets the KReS session as ACTIVE if<code>active</code> is true, INACTIVE 	 * otherwise. The state set is returned, which should match the input state 	 * unless an error occurs.<br> 	 *<br> 	 * Should throw an exception if this session is in a ZOMBIE state. 	 *  	 * @param active 	 *            the desired activity state for this session 	 * @return the resulting state of this KReS session 	 */
name|State
name|setActive
parameter_list|(
name|boolean
name|active
parameter_list|)
throws|throws
name|NonReferenceableSessionException
function_decl|;
block|}
end_interface

end_unit

