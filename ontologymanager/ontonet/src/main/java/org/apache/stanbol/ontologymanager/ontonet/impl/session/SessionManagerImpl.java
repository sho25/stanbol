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
name|impl
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
name|IOException
import|;
end_import

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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Dictionary
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Deactivate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Reference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|ontology
operator|.
name|OntologyProvider
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
name|ontology
operator|.
name|ScopeRegistry
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
name|ontology
operator|.
name|SessionOntologySpace
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
name|session
operator|.
name|DuplicateSessionIDException
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
name|session
operator|.
name|NonReferenceableSessionException
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
name|session
operator|.
name|Session
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
name|session
operator|.
name|Session
operator|.
name|State
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
name|session
operator|.
name|SessionEvent
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
name|session
operator|.
name|SessionEvent
operator|.
name|OperationType
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
name|session
operator|.
name|SessionIDGenerator
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
name|session
operator|.
name|SessionLimitException
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
name|session
operator|.
name|SessionListener
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
name|session
operator|.
name|SessionManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
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

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  *   * Calls to<code>getSessionListeners()</code> return a {@link Set} of listeners.  *   * TODO: implement storage (using persistence layer).  *   * @author alexdma  *   */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Service
argument_list|(
name|SessionManager
operator|.
name|class
argument_list|)
specifier|public
class|class
name|SessionManagerImpl
implements|implements
name|SessionManager
block|{
specifier|public
specifier|static
specifier|final
name|String
name|_ID_DEFAULT
init|=
literal|"session"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|_MAX_ACTIVE_SESSIONS_DEFAULT
init|=
operator|-
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|_ONTOLOGY_NETWORK_NS_DEFAULT
init|=
literal|"http://localhost:8080/ontonet/"
decl_stmt|;
comment|/**      * Concatenated with the sessionManager ID, it identifies the Web endpoint and default base URI for all      * sessions.      */
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SessionManager
operator|.
name|SESSIONS_NS
argument_list|,
name|value
operator|=
name|_ONTOLOGY_NETWORK_NS_DEFAULT
argument_list|)
specifier|private
name|String
name|baseNS
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SessionManager
operator|.
name|ID
argument_list|,
name|value
operator|=
name|_ID_DEFAULT
argument_list|)
specifier|protected
name|String
name|id
decl_stmt|;
specifier|protected
name|SessionIDGenerator
name|idgen
decl_stmt|;
specifier|protected
name|Set
argument_list|<
name|SessionListener
argument_list|>
name|listeners
decl_stmt|;
specifier|protected
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SessionManager
operator|.
name|MAX_ACTIVE_SESSIONS
argument_list|,
name|intValue
operator|=
name|_MAX_ACTIVE_SESSIONS_DEFAULT
argument_list|)
specifier|private
name|int
name|maxSessions
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|ontologyProvider
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Session
argument_list|>
name|sessionsByID
decl_stmt|;
comment|/**      * This default constructor is<b>only</b> intended to be used by the OSGI environment with Service      * Component Runtime support.      *<p>      * DO NOT USE to manually create instances - the ReengineerManagerImpl instances do need to be configured!      * YOU NEED TO USE {@link #SessionManagerImpl(OntologyProvider, Dictionary)} to parse the configuration      * and then initialise the rule store if running outside an OSGI environment.      */
specifier|public
name|SessionManagerImpl
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|listeners
operator|=
operator|new
name|HashSet
argument_list|<
name|SessionListener
argument_list|>
argument_list|()
expr_stmt|;
name|sessionsByID
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Session
argument_list|>
argument_list|()
expr_stmt|;
block|}
comment|/**      * @deprecated In non-OSGi+DS environments, please invoke      *             {@link #SessionManagerImpl(IRI, OntologyProvider, Dictionary)}. With this constructor,      *             baseIri and scopeRegistry are ignored.      *       * @param baseIri      * @param scopeRegistry      * @param ontologyProvider      */
specifier|public
name|SessionManagerImpl
parameter_list|(
name|IRI
name|baseIri
parameter_list|,
name|ScopeRegistry
name|scopeRegistry
parameter_list|,
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|ontologyProvider
parameter_list|)
block|{
name|this
argument_list|(
name|ontologyProvider
argument_list|,
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * To be invoked by non-OSGi environments.      *       * @param the      *            ontology provider that will store and provide ontologies for this session manager.      * @param configuration      */
specifier|public
name|SessionManagerImpl
parameter_list|(
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|ontologyProvider
parameter_list|,
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
block|{
name|this
argument_list|()
expr_stmt|;
name|this
operator|.
name|ontologyProvider
operator|=
name|ontologyProvider
expr_stmt|;
try|try
block|{
name|activate
argument_list|(
name|configuration
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to access servlet context."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Used to configure an instance within an OSGi container.      *       * @throws IOException      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|IOException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|SessionManagerImpl
operator|.
name|class
operator|+
literal|" activate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"No valid"
operator|+
name|ComponentContext
operator|.
name|class
operator|+
literal|" parsed in activate!"
argument_list|)
throw|;
block|}
name|activate
argument_list|(
operator|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|context
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Called within both OSGi and non-OSGi environments.      *       * @param configuration      * @throws IOException      */
specifier|protected
name|void
name|activate
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Parse configuration
name|id
operator|=
operator|(
name|String
operator|)
name|configuration
operator|.
name|get
argument_list|(
name|SessionManager
operator|.
name|ID
argument_list|)
expr_stmt|;
if|if
condition|(
name|id
operator|==
literal|null
condition|)
name|id
operator|=
name|_ID_DEFAULT
expr_stmt|;
name|String
name|s
init|=
literal|null
decl_stmt|;
try|try
block|{
name|s
operator|=
operator|(
name|String
operator|)
name|configuration
operator|.
name|get
argument_list|(
name|SessionManager
operator|.
name|SESSIONS_NS
argument_list|)
expr_stmt|;
name|setNamespace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Invalid namespace {}. Setting to default value {}"
argument_list|,
name|s
argument_list|,
name|_ONTOLOGY_NETWORK_NS_DEFAULT
argument_list|)
expr_stmt|;
name|setNamespace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|_ONTOLOGY_NETWORK_NS_DEFAULT
argument_list|)
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|s
operator|=
operator|(
name|String
operator|)
name|configuration
operator|.
name|get
argument_list|(
name|SessionManager
operator|.
name|MAX_ACTIVE_SESSIONS
argument_list|)
expr_stmt|;
name|maxSessions
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Invalid session limit {}. Setting to default value {}"
argument_list|,
name|s
argument_list|,
name|_MAX_ACTIVE_SESSIONS_DEFAULT
argument_list|)
expr_stmt|;
name|maxSessions
operator|=
name|_MAX_ACTIVE_SESSIONS_DEFAULT
expr_stmt|;
block|}
if|if
condition|(
name|id
operator|==
literal|null
operator|||
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"The Ontology Network Manager configuration does not define a ID for the Ontology Network Manager"
argument_list|)
expr_stmt|;
block|}
name|idgen
operator|=
operator|new
name|TimestampedSessionIDGenerator
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|getNamespace
argument_list|()
operator|+
name|getID
argument_list|()
operator|+
literal|"/"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|protected
specifier|synchronized
name|void
name|addSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|sessionsByID
operator|.
name|put
argument_list|(
name|session
operator|.
name|getID
argument_list|()
argument_list|,
name|session
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|addSessionListener
parameter_list|(
name|SessionListener
name|listener
parameter_list|)
block|{
name|listeners
operator|.
name|add
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|checkSessionLimit
parameter_list|()
throws|throws
name|SessionLimitException
block|{
if|if
condition|(
name|maxSessions
operator|>=
literal|0
operator|&&
name|sessionsByID
operator|.
name|size
argument_list|()
operator|>=
name|maxSessions
condition|)
throw|throw
operator|new
name|SessionLimitException
argument_list|(
name|maxSessions
argument_list|,
literal|"Cannot create new session. Limit of "
operator|+
name|maxSessions
operator|+
literal|" already raeached."
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|clearSessionListeners
parameter_list|()
block|{
name|listeners
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Session
name|createSession
parameter_list|()
throws|throws
name|SessionLimitException
block|{
name|checkSessionLimit
argument_list|()
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|exclude
init|=
name|getRegisteredSessionIDs
argument_list|()
decl_stmt|;
name|Session
name|session
init|=
literal|null
decl_stmt|;
while|while
condition|(
name|session
operator|==
literal|null
condition|)
try|try
block|{
name|session
operator|=
name|createSession
argument_list|(
name|idgen
operator|.
name|createSessionID
argument_list|(
name|exclude
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DuplicateSessionIDException
name|e
parameter_list|)
block|{
name|exclude
operator|.
name|add
argument_list|(
name|e
operator|.
name|getDuplicateID
argument_list|()
argument_list|)
expr_stmt|;
continue|continue;
block|}
return|return
name|session
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|synchronized
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
block|{
comment|/*          * Throw the duplicate ID exception first, in case developers decide to reuse the existing session          * before creating a new one.          */
if|if
condition|(
name|sessionsByID
operator|.
name|containsKey
argument_list|(
name|sessionID
argument_list|)
condition|)
throw|throw
operator|new
name|DuplicateSessionIDException
argument_list|(
name|sessionID
operator|.
name|toString
argument_list|()
argument_list|)
throw|;
name|checkSessionLimit
argument_list|()
expr_stmt|;
name|IRI
name|ns
init|=
name|IRI
operator|.
name|create
argument_list|(
name|getNamespace
argument_list|()
operator|+
name|getID
argument_list|()
operator|+
literal|"/"
argument_list|)
decl_stmt|;
name|Session
name|session
init|=
operator|new
name|SessionImpl
argument_list|(
name|sessionID
argument_list|,
name|ns
argument_list|,
name|ontologyProvider
argument_list|)
decl_stmt|;
name|addSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|fireSessionCreated
argument_list|(
name|session
argument_list|)
expr_stmt|;
return|return
name|session
return|;
block|}
comment|/**      * Deactivation of the ONManagerImpl resets all its resources.      */
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|id
operator|=
literal|null
expr_stmt|;
name|baseNS
operator|=
literal|null
expr_stmt|;
name|maxSessions
operator|=
literal|0
expr_stmt|;
comment|// No sessions allowed for an inactive component.
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|SessionManagerImpl
operator|.
name|class
operator|+
literal|" deactivate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|destroySession
parameter_list|(
name|String
name|sessionID
parameter_list|)
block|{
try|try
block|{
name|Session
name|ses
init|=
name|sessionsByID
operator|.
name|get
argument_list|(
name|sessionID
argument_list|)
decl_stmt|;
if|if
condition|(
name|ses
operator|==
literal|null
condition|)
name|log
operator|.
name|warn
argument_list|(
literal|"Tried to destroy nonexisting session {} . Could it have been previously destroyed?"
argument_list|,
name|sessionID
argument_list|)
expr_stmt|;
else|else
block|{
name|ses
operator|.
name|close
argument_list|()
expr_stmt|;
if|if
condition|(
name|ses
operator|instanceof
name|SessionImpl
condition|)
operator|(
operator|(
name|SessionImpl
operator|)
name|ses
operator|)
operator|.
name|state
operator|=
name|State
operator|.
name|ZOMBIE
expr_stmt|;
comment|// Make session no longer referenceable
name|removeSession
argument_list|(
name|ses
argument_list|)
expr_stmt|;
name|fireSessionDestroyed
argument_list|(
name|ses
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|NonReferenceableSessionException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Tried to kick a dead horse on session "
operator|+
name|sessionID
operator|+
literal|" which was already in a zombie state."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|fireSessionCreated
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|SessionEvent
name|e
decl_stmt|;
try|try
block|{
name|e
operator|=
operator|new
name|SessionEvent
argument_list|(
name|session
argument_list|,
name|OperationType
operator|.
name|CREATE
argument_list|)
expr_stmt|;
for|for
control|(
name|SessionListener
name|l
range|:
name|listeners
control|)
name|l
operator|.
name|sessionChanged
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e1
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"An error occurred while attempting to fire session creation event for session "
operator|+
name|session
operator|.
name|getID
argument_list|()
argument_list|,
name|e1
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
specifier|protected
name|void
name|fireSessionDestroyed
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|SessionEvent
name|e
decl_stmt|;
try|try
block|{
name|e
operator|=
operator|new
name|SessionEvent
argument_list|(
name|session
argument_list|,
name|OperationType
operator|.
name|KILL
argument_list|)
expr_stmt|;
for|for
control|(
name|SessionListener
name|l
range|:
name|listeners
control|)
name|l
operator|.
name|sessionChanged
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e1
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"An error occurred while attempting to fire session destruction event for session "
operator|+
name|session
operator|.
name|getID
argument_list|()
argument_list|,
name|e1
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
annotation|@
name|Override
specifier|public
name|int
name|getActiveSessionLimit
parameter_list|()
block|{
return|return
name|maxSessions
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getID
parameter_list|()
block|{
return|return
name|id
return|;
block|}
annotation|@
name|Override
specifier|public
name|IRI
name|getNamespace
parameter_list|()
block|{
return|return
name|IRI
operator|.
name|create
argument_list|(
name|baseNS
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getRegisteredSessionIDs
parameter_list|()
block|{
return|return
name|sessionsByID
operator|.
name|keySet
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Session
name|getSession
parameter_list|(
name|String
name|sessionID
parameter_list|)
block|{
return|return
name|sessionsByID
operator|.
name|get
argument_list|(
name|sessionID
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|SessionListener
argument_list|>
name|getSessionListeners
parameter_list|()
block|{
return|return
name|listeners
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|SessionOntologySpace
argument_list|>
name|getSessionSpaces
parameter_list|(
name|String
name|sessionID
parameter_list|)
throws|throws
name|NonReferenceableSessionException
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Session Manager is now agnostic to scopes, and session spaces are deprecated. Please perform CRUD operations on Session objects directly."
argument_list|)
throw|;
comment|// Set<SessionOntologySpace> result = new HashSet<SessionOntologySpace>();
comment|// // Brute force search
comment|// for (OntologyScope scope : scopeRegistry.getRegisteredScopes()) {
comment|// SessionOntologySpace space = scope.getSessionSpace(sessionID);
comment|// if (space != null) result.add(space);
comment|// }
comment|// return result;
block|}
specifier|protected
specifier|synchronized
name|void
name|removeSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|String
name|id
init|=
name|session
operator|.
name|getID
argument_list|()
decl_stmt|;
name|Session
name|s2
init|=
name|sessionsByID
operator|.
name|get
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
name|s2
condition|)
name|sessionsByID
operator|.
name|remove
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeSessionListener
parameter_list|(
name|SessionListener
name|listener
parameter_list|)
block|{
name|listeners
operator|.
name|remove
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setActiveSessionLimit
parameter_list|(
name|int
name|limit
parameter_list|)
block|{
name|this
operator|.
name|maxSessions
operator|=
name|limit
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setNamespace
parameter_list|(
name|IRI
name|namespace
parameter_list|)
block|{
if|if
condition|(
name|namespace
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Namespace cannot be null."
argument_list|)
throw|;
if|if
condition|(
name|namespace
operator|.
name|toURI
argument_list|()
operator|.
name|getQuery
argument_list|()
operator|!=
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"URI Query is not allowed in OntoNet namespaces."
argument_list|)
throw|;
if|if
condition|(
name|namespace
operator|.
name|toURI
argument_list|()
operator|.
name|getFragment
argument_list|()
operator|!=
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"URI Fragment is not allowed in OntoNet namespaces."
argument_list|)
throw|;
if|if
condition|(
name|namespace
operator|.
name|toString
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"#"
argument_list|)
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"OntoNet namespaces must not end with a hash ('#') character."
argument_list|)
throw|;
if|if
condition|(
operator|!
name|namespace
operator|.
name|toString
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Namespace {} does not end with slash character ('/'). It will be added automatically."
argument_list|,
name|namespace
argument_list|)
expr_stmt|;
name|this
operator|.
name|baseNS
operator|=
name|namespace
operator|+
literal|"/"
expr_stmt|;
return|return;
block|}
name|this
operator|.
name|baseNS
operator|=
name|namespace
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
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
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Session content is always stored by default in the current implementation."
argument_list|)
throw|;
comment|/*          * For each gession space in the session save all the ontologies contained in the space.          */
comment|// for (SessionOntologySpace so : getSessionSpaces(sessionID)) {
comment|// for (OWLOntology owlOntology : so.getOntologies(true)) {
comment|//
comment|// // store.store(owlOntology);
comment|//
comment|// }
comment|// }
block|}
block|}
end_class

end_unit

