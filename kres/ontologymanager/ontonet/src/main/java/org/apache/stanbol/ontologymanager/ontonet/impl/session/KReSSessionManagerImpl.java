begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|KReSSession
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
name|KReSSessionIDGenerator
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
name|KReSSessionManager
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
name|KReSSession
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
name|impl
operator|.
name|ontology
operator|.
name|OntologyStorage
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
name|OWLOntology
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
comment|/**  *   * Calls to<code>getSessionListeners()</code> return a {@link Set} of  * listeners.  *   * TODO: implement storage (using persistence layer).  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|KReSSessionManagerImpl
implements|implements
name|KReSSessionManager
block|{
specifier|private
name|Map
argument_list|<
name|IRI
argument_list|,
name|KReSSession
argument_list|>
name|sessionsByID
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
specifier|protected
name|KReSSessionIDGenerator
name|idgen
decl_stmt|;
specifier|protected
name|ScopeRegistry
name|scopeRegistry
decl_stmt|;
specifier|protected
name|OntologyStorage
name|store
decl_stmt|;
specifier|public
name|KReSSessionManagerImpl
parameter_list|(
name|IRI
name|baseIri
parameter_list|,
name|ScopeRegistry
name|scopeRegistry
parameter_list|,
name|OntologyStorage
name|store
parameter_list|)
block|{
name|idgen
operator|=
operator|new
name|TimestampedSessionIDGenerator
argument_list|(
name|baseIri
argument_list|)
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
name|IRI
argument_list|,
name|KReSSession
argument_list|>
argument_list|()
expr_stmt|;
name|this
operator|.
name|scopeRegistry
operator|=
name|scopeRegistry
expr_stmt|;
name|this
operator|.
name|store
operator|=
name|store
expr_stmt|;
block|}
comment|/* 	 * (non-Javadoc) 	 *  	 * @see 	 * eu.iksproject.kres.api.manager.session.SessionManager#addSessionListener 	 * (eu.iksproject.kres.api.manager.session.SessionListener) 	 */
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
comment|/* 	 * (non-Javadoc) 	 *  	 * @see 	 * eu.iksproject.kres.api.manager.session.SessionManager#clearSessionListeners 	 * () 	 */
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
comment|/* 	 * (non-Javadoc) 	 *  	 * @see 	 * eu.iksproject.kres.api.manager.session.SessionManager#createSession() 	 */
annotation|@
name|Override
specifier|public
name|KReSSession
name|createSession
parameter_list|()
block|{
name|Set
argument_list|<
name|IRI
argument_list|>
name|exclude
init|=
name|getRegisteredSessionIDs
argument_list|()
decl_stmt|;
name|KReSSession
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
name|getDulicateID
argument_list|()
argument_list|)
expr_stmt|;
continue|continue;
block|}
return|return
name|session
return|;
block|}
comment|/* 	 * (non-Javadoc) 	 *  	 * @see 	 * eu.iksproject.kres.api.manager.session.SessionManager#createSession(org 	 * .semanticweb.owlapi.model.IRI) 	 */
annotation|@
name|Override
specifier|public
name|KReSSession
name|createSession
parameter_list|(
name|IRI
name|sessionID
parameter_list|)
throws|throws
name|DuplicateSessionIDException
block|{
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
argument_list|)
throw|;
name|KReSSession
name|session
init|=
operator|new
name|KReSSessionImpl
argument_list|(
name|sessionID
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
comment|/* 	 * (non-Javadoc) 	 *  	 * @see 	 * eu.iksproject.kres.api.manager.session.SessionManager#destroySession( 	 * org.semanticweb.owlapi.model.IRI) 	 */
annotation|@
name|Override
specifier|public
name|void
name|destroySession
parameter_list|(
name|IRI
name|sessionID
parameter_list|)
block|{
try|try
block|{
name|KReSSession
name|ses
init|=
name|sessionsByID
operator|.
name|get
argument_list|(
name|sessionID
argument_list|)
decl_stmt|;
name|ses
operator|.
name|close
argument_list|()
expr_stmt|;
if|if
condition|(
name|ses
operator|instanceof
name|KReSSessionImpl
condition|)
operator|(
operator|(
name|KReSSessionImpl
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
literal|"KReS :: tried to kick a dead horse on session "
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
comment|/* 	 * (non-Javadoc) 	 *  	 * @see 	 * eu.iksproject.kres.api.manager.session.SessionManager#getSession(org. 	 * semanticweb.owlapi.model.IRI) 	 */
annotation|@
name|Override
specifier|public
name|KReSSession
name|getSession
parameter_list|(
name|IRI
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
name|Set
argument_list|<
name|IRI
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
specifier|protected
name|void
name|fireSessionCreated
parameter_list|(
name|KReSSession
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
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
operator|.
name|error
argument_list|(
literal|"KReS :: Exception occurred while attempting to fire session creation event for session "
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
name|KReSSession
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
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
operator|.
name|error
argument_list|(
literal|"KReS :: Exception occurred while attempting to fire session destruction event for session "
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
name|addSession
parameter_list|(
name|KReSSession
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
specifier|protected
name|void
name|removeSession
parameter_list|(
name|KReSSession
name|session
parameter_list|)
block|{
name|IRI
name|id
init|=
name|session
operator|.
name|getID
argument_list|()
decl_stmt|;
name|KReSSession
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
comment|/* 	 * (non-Javadoc) 	 *  	 * @see 	 * eu.iksproject.kres.api.manager.session.SessionManager#getSessionListeners 	 * () 	 */
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
comment|/* 	 * (non-Javadoc) 	 *  	 * TODO : optimize with indexing. 	 *  	 * @see 	 * eu.iksproject.kres.api.manager.session.SessionManager#getSessionSpaces 	 * (org.semanticweb.owlapi.model.IRI) 	 */
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|SessionOntologySpace
argument_list|>
name|getSessionSpaces
parameter_list|(
name|IRI
name|sessionID
parameter_list|)
throws|throws
name|NonReferenceableSessionException
block|{
name|Set
argument_list|<
name|SessionOntologySpace
argument_list|>
name|result
init|=
operator|new
name|HashSet
argument_list|<
name|SessionOntologySpace
argument_list|>
argument_list|()
decl_stmt|;
comment|// Brute force search
for|for
control|(
name|OntologyScope
name|scope
range|:
name|scopeRegistry
operator|.
name|getRegisteredScopes
argument_list|()
control|)
block|{
name|SessionOntologySpace
name|space
init|=
name|scope
operator|.
name|getSessionSpace
argument_list|(
name|sessionID
argument_list|)
decl_stmt|;
if|if
condition|(
name|space
operator|!=
literal|null
condition|)
name|result
operator|.
name|add
argument_list|(
name|space
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
comment|/* 	 * (non-Javadoc) 	 *  	 * @see 	 * eu.iksproject.kres.api.manager.session.SessionManager#removeSessionListener 	 * (eu.iksproject.kres.api.manager.session.SessionListener) 	 */
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
comment|/* 	 * (non-Javadoc) 	 *  	 * TODO : storage not implemented yet 	 *  	 * @see 	 * eu.iksproject.kres.api.manager.session.SessionManager#storeSession(org 	 * .semanticweb.owlapi.model.IRI, java.io.OutputStream) 	 */
annotation|@
name|Override
specifier|public
name|void
name|storeSession
parameter_list|(
name|IRI
name|sessionID
parameter_list|,
name|OutputStream
name|out
parameter_list|)
throws|throws
name|NonReferenceableSessionException
block|{
for|for
control|(
name|SessionOntologySpace
name|so
range|:
name|getSessionSpaces
argument_list|(
name|sessionID
argument_list|)
control|)
for|for
control|(
name|OWLOntology
name|o
range|:
name|so
operator|.
name|getOntologies
argument_list|()
control|)
name|store
operator|.
name|store
argument_list|(
name|o
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

