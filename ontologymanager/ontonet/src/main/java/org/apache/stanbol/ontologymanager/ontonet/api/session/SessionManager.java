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
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

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
name|ontonet
operator|.
name|api
operator|.
name|NamedResource
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
name|OWLOntologyStorageException
import|;
end_import

begin_comment
comment|/**  * Manages session objects via CRUD-like operations. A<code>SessionManager</code> maintains in-memory storage  * of sessions, creates new ones and either destroys or stores existing ones persistently. All sessions are  * managed via unique identifiers of the<code>org.semanticweb.owlapi.model.IRI</code> type.<br>  *<br>  * NOTE: implementations should be synchronized, or document whenever they are not.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SessionManager
extends|extends
name|NamedResource
extends|,
name|SessionListenable
block|{
comment|/**      * The key used to configure the base namespace of the ontology network.      */
name|String
name|ID
init|=
literal|"org.apache.stanbol.ontologymanager.ontonet.session_mgr_id"
decl_stmt|;
comment|/**      * The key used to configure the base namespace of the ontology network.      */
name|String
name|MAX_ACTIVE_SESSIONS
init|=
literal|"org.apache.stanbol.ontologymanager.ontonet.session_limit"
decl_stmt|;
comment|/**      * The key used to configure the base namespace of the ontology network.      */
name|String
name|SESSIONS_NS
init|=
literal|"org.apache.stanbol.ontologymanager.ontonet.session_ns"
decl_stmt|;
comment|/**      * Generates<b>and registers</b> a new session and assigns a unique session ID generated internally. This      * will not cause {@link DuplicateSessionIDException}s to be thrown.      *       * @return the generated session      */
name|Session
name|createSession
parameter_list|()
throws|throws
name|SessionLimitException
function_decl|;
comment|/**      * Generates<b>and registers</b> a new session and tries to assign it the supplied session ID. If a      * session with that ID is already registered, the new session is<i>not</i> created and a      *<code>DuplicateSessionIDException</code> is thrown.      *       * @param sessionID      *            the IRI that uniquely identifies the session      * @return the generated session      * @throws DuplicateSessionIDException      *             if a session with that sessionID is already registered      */
name|Session
name|createSession
parameter_list|(
name|String
name|sessionID
parameter_list|)
throws|throws
name|DuplicateSessionIDException
throws|,
name|SessionLimitException
function_decl|;
comment|/**      * Deletes the session identified by the supplied sessionID and releases its resources.      *       * @param sessionID      *            the IRI that uniquely identifies the session      */
name|void
name|destroySession
parameter_list|(
name|String
name|sessionID
parameter_list|)
function_decl|;
comment|/**      * Gets the number of sessions that can be simultaneously active and managed by this session manager.      *       * @return the session limit.      */
name|int
name|getActiveSessionLimit
parameter_list|()
function_decl|;
comment|/**      * Returns the set of strings that identify registered sessions, whatever their state.      *       * @return the IDs of all registered sessions.      */
name|Set
argument_list|<
name|String
argument_list|>
name|getRegisteredSessionIDs
parameter_list|()
function_decl|;
comment|/**      * Retrieves the unique session identified by<code>sessionID</code>.      *       * @param sessionID      *            the IRI that uniquely identifies the session      * @return the unique session identified by<code>sessionID</code>      */
name|Session
name|getSession
parameter_list|(
name|String
name|sessionID
parameter_list|)
function_decl|;
comment|/**      * Sets the maximum allowed number of active sessions managed by this manager simultaneously. A negative      * value denotes no limit.      *       * Note that it is possible to set this value to zero, thus preventing Stanbol from creating session at      * all.      *       * @param limit      */
name|void
name|setActiveSessionLimit
parameter_list|(
name|int
name|limit
parameter_list|)
function_decl|;
comment|/**      * Stores the session identified by<code>sessionID</code> using the output stream<code>out</code>.      *       * @param sessionID      *            the IRI that uniquely identifies the session      * @param out      *            the output stream to store the session      * @throws OWLOntologyStorageException      */
name|void
name|storeSession
parameter_list|(
name|String
name|sessionID
parameter_list|,
name|OutputStream
name|out
parameter_list|)
throws|throws
name|NonReferenceableSessionException
throws|,
name|OWLOntologyStorageException
function_decl|;
block|}
end_interface

end_unit

