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
name|session
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
name|Lockable
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
name|OntologyCollector
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
name|ontology
operator|.
name|OWLExportable
import|;
end_import

begin_comment
comment|/**  * Note that sessions are possibly disjoint with HTTP sessions or the like.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|Session
extends|extends
name|OntologyCollector
extends|,
name|OWLExportable
extends|,
name|Lockable
extends|,
name|SessionListenable
block|{
specifier|static
specifier|final
name|String
name|shortName
init|=
literal|"session"
decl_stmt|;
comment|/**      * The states a session can be in: ACTIVE (for running sessions), HALTED (for inactive sessions that may      * later be activated, e.g. when a user logs in), ZOMBIE (inactive and bound for destruction, no longer      * referenceable).      *       * @author alexdma      *       */
enum|enum
name|State
block|{
comment|/**          * Running session          */
name|ACTIVE
block|,
comment|/**          * inactive sessions that may later be activated          */
name|HALTED
block|,
comment|/**          * Inactive and bound for destruction, no longer referenceable          */
name|ZOMBIE
block|}
comment|/**      * Instructs the session to reference the supplied ontology scope. This way, whenever session data are      * processed, scope data will be considered as well.      *       * @param scope      *            the ontology scope to be referenced.      */
name|void
name|attachScope
parameter_list|(
name|String
name|scopeId
parameter_list|)
function_decl|;
comment|/**      * Removes all references to ontology scopes, thus leaving the session data as standalone.      */
name|void
name|clearScopes
parameter_list|()
function_decl|;
comment|/**      * Closes this Session irreversibly. Most likely includes setting the state to ZOMBIE.      */
name|void
name|close
parameter_list|()
function_decl|;
comment|/**      * Instructs the session to no longer reference the supplied ontology scope. If a scope with the supplied      * identifier was not attached, this method has no effect.      *       * @param scope      *            the identifer of the ontology scope to be detached.      */
name|void
name|detachScope
parameter_list|(
name|String
name|scopeId
parameter_list|)
function_decl|;
comment|/**      * Gets the identifiers of the scopes currently attached to this session.      *       * @return the attached scope identifiers      */
name|Set
argument_list|<
name|String
argument_list|>
name|getAttachedScopes
parameter_list|()
function_decl|;
comment|/**      * Returns the current state of this KReS session.      *       * @return the state of this session      */
name|State
name|getSessionState
parameter_list|()
function_decl|;
comment|/**      * Equivalent to<code>getState() == State.ACTIVE</code>.      *       * @return true iff this session is in the ACTIVE state      */
name|boolean
name|isActive
parameter_list|()
function_decl|;
comment|/**      * Sets this session as active      *       * @throws NonReferenceableSessionException      */
name|void
name|open
parameter_list|()
function_decl|;
comment|/**      * Sets the session as ACTIVE if<code>active</code> is true, INACTIVE otherwise. The state set is      * returned, which should match the input state unless an error occurs.<br>      *<br>      * Should throw an exception if this session is in a ZOMBIE state.      *       * @param active      *            the desired activity state for this session      * @return the resulting state of this KReS session      */
name|State
name|setActive
parameter_list|(
name|boolean
name|active
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

