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
name|multiplexer
operator|.
name|clerezza
operator|.
name|impl
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
name|LinkedList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|TripleCollection
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|impl
operator|.
name|TripleImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|ontologies
operator|.
name|OWL
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
name|core
operator|.
name|scope
operator|.
name|ScopeManagerImpl
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
name|servicesapi
operator|.
name|scope
operator|.
name|Scope
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
name|scope
operator|.
name|ScopeManager
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
name|servicesapi
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
name|servicesapi
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
name|servicesapi
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
name|servicesapi
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
name|semanticweb
operator|.
name|owlapi
operator|.
name|apibinding
operator|.
name|OWLManager
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
name|AddImport
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
name|OWLDataFactory
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
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntologyChange
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
name|OWLOntologyCreationException
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
name|OWLOntologyID
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
name|OWLOntologyManager
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
name|OWLOntologySetProvider
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
name|util
operator|.
name|OWLOntologyMerger
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
comment|/**  * Standard implementation of the {@link Session} interface. A SessionImpl is initially inactive and creates  * its own identifier.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|SessionImpl
extends|extends
name|AbstractOntologyCollectorImpl
implements|implements
name|Session
block|{
specifier|protected
name|Set
argument_list|<
name|String
argument_list|>
name|attachedScopes
decl_stmt|;
specifier|protected
name|Set
argument_list|<
name|SessionListener
argument_list|>
name|listeners
decl_stmt|;
comment|/**      * A session knows its own state.      */
specifier|public
name|State
name|state
init|=
name|State
operator|.
name|HALTED
decl_stmt|;
comment|/**      * Utility constructor for enforcing a given IRI as a session ID. It will not throw duplication      * exceptions, since a KReS session does not know about other sessions.      *       * @param sessionID      *            the IRI to be set as unique identifier for this session      */
specifier|public
name|SessionImpl
parameter_list|(
name|String
name|sessionID
parameter_list|,
name|IRI
name|namespace
parameter_list|,
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|ontologyProvider
parameter_list|)
block|{
name|super
argument_list|(
name|sessionID
argument_list|,
name|namespace
argument_list|,
name|ontologyProvider
argument_list|)
expr_stmt|;
name|backwardPathLength
operator|=
literal|0
expr_stmt|;
comment|// setNamespace(namespace);
name|attachedScopes
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
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
annotation|@
name|Override
specifier|public
name|void
name|attachScope
parameter_list|(
name|String
name|scopeId
parameter_list|)
block|{
name|attachedScopes
operator|.
name|add
argument_list|(
name|scopeId
argument_list|)
expr_stmt|;
name|fireScopeAppended
argument_list|(
name|scopeId
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|attachScopeImportsClerezza
parameter_list|(
name|TripleCollection
name|target
parameter_list|,
name|IRI
name|prefix
parameter_list|)
block|{
name|UriRef
name|iri
init|=
operator|new
name|UriRef
argument_list|(
name|prefix
operator|+
name|_id
argument_list|)
decl_stmt|;
name|String
name|scopePrefix
init|=
name|prefix
operator|.
name|toString
argument_list|()
decl_stmt|;
name|scopePrefix
operator|=
name|scopePrefix
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|scopePrefix
operator|.
name|lastIndexOf
argument_list|(
literal|"/"
operator|+
name|shortName
operator|+
literal|"/"
argument_list|)
argument_list|)
operator|+
literal|"/ontology/"
expr_stmt|;
for|for
control|(
name|String
name|scopeID
range|:
name|attachedScopes
control|)
block|{
name|UriRef
name|physIRI
init|=
operator|new
name|UriRef
argument_list|(
name|scopePrefix
operator|+
name|scopeID
argument_list|)
decl_stmt|;
name|target
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|iri
argument_list|,
name|OWL
operator|.
name|imports
argument_list|,
name|physIRI
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|attachScopeImportsOwlApi
parameter_list|(
name|OWLOntology
name|target
parameter_list|,
name|IRI
name|prefix
parameter_list|)
block|{
if|if
condition|(
operator|!
name|attachedScopes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|scopePrefix
init|=
name|prefix
operator|.
name|toString
argument_list|()
decl_stmt|;
name|scopePrefix
operator|=
name|scopePrefix
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|scopePrefix
operator|.
name|lastIndexOf
argument_list|(
literal|"/"
operator|+
name|shortName
operator|+
literal|"/"
argument_list|)
argument_list|)
operator|+
literal|"/ontology/"
expr_stmt|;
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|changes
init|=
operator|new
name|LinkedList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
decl_stmt|;
name|OWLOntologyManager
name|ontologyManager
init|=
name|target
operator|.
name|getOWLOntologyManager
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|df
init|=
name|ontologyManager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
comment|// Add import declarations for attached scopes.
for|for
control|(
name|String
name|scopeID
range|:
name|attachedScopes
control|)
block|{
name|IRI
name|physIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|scopePrefix
operator|+
name|scopeID
argument_list|)
decl_stmt|;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|target
argument_list|,
name|df
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|physIRI
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Commit
name|ontologyManager
operator|.
name|applyChanges
argument_list|(
name|changes
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|clearScopes
parameter_list|()
block|{
name|attachedScopes
operator|.
name|clear
argument_list|()
expr_stmt|;
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
name|void
name|close
parameter_list|()
throws|throws
name|NonReferenceableSessionException
block|{
comment|// if (getSessionState() == State.ZOMBIE)
comment|// throw new NonReferenceableSessionException();
comment|// state = State.ZOMBIE;
name|this
operator|.
name|setActive
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|detachScope
parameter_list|(
name|String
name|scopeId
parameter_list|)
block|{
if|if
condition|(
operator|!
name|attachedScopes
operator|.
name|contains
argument_list|(
name|scopeId
argument_list|)
condition|)
return|return;
name|attachedScopes
operator|.
name|remove
argument_list|(
name|scopeId
argument_list|)
expr_stmt|;
name|fireScopeDetached
argument_list|(
name|scopeId
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|arg0
parameter_list|)
block|{
if|if
condition|(
name|arg0
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
operator|(
name|arg0
operator|instanceof
name|Session
operator|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|this
operator|==
name|arg0
condition|)
return|return
literal|true
return|;
name|log
operator|.
name|warn
argument_list|(
literal|"{} only implements weak equality, i.e. managed ontologies are only checked by public key, not by content."
argument_list|,
name|getClass
argument_list|()
argument_list|)
expr_stmt|;
name|Session
name|coll
init|=
operator|(
name|Session
operator|)
name|arg0
decl_stmt|;
return|return
name|super
operator|.
name|equals
argument_list|(
name|arg0
argument_list|)
operator|&&
name|this
operator|.
name|getAttachedScopes
argument_list|()
operator|.
name|equals
argument_list|(
name|coll
operator|.
name|getAttachedScopes
argument_list|()
argument_list|)
operator|&&
name|this
operator|.
name|getAttachedScopes
argument_list|()
operator|.
name|equals
argument_list|(
name|coll
operator|.
name|getAttachedScopes
argument_list|()
argument_list|)
operator|&&
name|this
operator|.
name|getSessionState
argument_list|()
operator|.
name|equals
argument_list|(
name|coll
operator|.
name|getSessionState
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|MGraph
name|exportToMGraph
parameter_list|(
name|boolean
name|merge
parameter_list|,
name|IRI
name|universalPrefix
parameter_list|)
block|{
name|MGraph
name|mg
init|=
name|super
operator|.
name|exportToMGraph
argument_list|(
name|merge
argument_list|,
name|universalPrefix
argument_list|)
decl_stmt|;
name|attachScopeImportsClerezza
argument_list|(
name|mg
argument_list|,
name|universalPrefix
argument_list|)
expr_stmt|;
return|return
name|mg
return|;
block|}
comment|/**      * TODO support merging for attached scopes as well?      */
annotation|@
name|Override
specifier|protected
name|OWLOntology
name|exportToOWLOntology
parameter_list|(
name|boolean
name|merge
parameter_list|,
name|IRI
name|universalPrefix
parameter_list|)
block|{
name|OWLOntology
name|o
init|=
name|super
operator|.
name|exportToOWLOntology
argument_list|(
name|merge
argument_list|,
name|universalPrefix
argument_list|)
decl_stmt|;
name|IRI
name|iri
init|=
name|o
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
decl_stmt|;
if|if
condition|(
name|merge
condition|)
block|{
comment|// Re-merge
name|ScopeManager
name|onm
init|=
name|ScopeManagerImpl
operator|.
name|get
argument_list|()
decl_stmt|;
comment|// FIXME try to avoid this.
specifier|final
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|set
init|=
operator|new
name|HashSet
argument_list|<
name|OWLOntology
argument_list|>
argument_list|()
decl_stmt|;
name|set
operator|.
name|add
argument_list|(
name|o
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|scopeID
range|:
name|attachedScopes
control|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" ... Merging with attached scope {}."
argument_list|,
name|scopeID
argument_list|)
expr_stmt|;
name|Scope
name|sc
init|=
name|onm
operator|.
name|getScope
argument_list|(
name|scopeID
argument_list|)
decl_stmt|;
if|if
condition|(
name|sc
operator|!=
literal|null
condition|)
name|set
operator|.
name|add
argument_list|(
name|sc
operator|.
name|export
argument_list|(
name|OWLOntology
operator|.
name|class
argument_list|,
name|merge
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|OWLOntologyID
name|ontologyId
range|:
name|managedOntologies
control|)
block|{
name|set
operator|.
name|add
argument_list|(
name|getOntology
argument_list|(
name|ontologyId
argument_list|,
name|OWLOntology
operator|.
name|class
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|OWLOntologySetProvider
name|provider
init|=
operator|new
name|OWLOntologySetProvider
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|()
block|{
return|return
name|set
return|;
block|}
block|}
decl_stmt|;
name|OWLOntologyMerger
name|merger
init|=
operator|new
name|OWLOntologyMerger
argument_list|(
name|provider
argument_list|)
decl_stmt|;
try|try
block|{
name|o
operator|=
name|merger
operator|.
name|createMergedOntology
argument_list|(
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|,
name|iri
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to merge imports for ontology "
operator|+
name|iri
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|o
operator|=
literal|null
expr_stmt|;
block|}
block|}
block|}
else|else
name|attachScopeImportsOwlApi
argument_list|(
name|o
argument_list|,
name|universalPrefix
argument_list|)
expr_stmt|;
return|return
name|o
return|;
block|}
specifier|protected
name|void
name|fireClosed
parameter_list|()
block|{
name|SessionEvent
name|e
init|=
literal|null
decl_stmt|;
try|try
block|{
name|e
operator|=
operator|new
name|SessionEvent
argument_list|(
name|this
argument_list|,
name|OperationType
operator|.
name|CLOSE
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
literal|"Could not close session "
operator|+
name|getID
argument_list|()
argument_list|,
name|e1
argument_list|)
expr_stmt|;
return|return;
block|}
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
specifier|protected
name|void
name|fireScopeAppended
parameter_list|(
name|String
name|scopeId
parameter_list|)
block|{
for|for
control|(
name|SessionListener
name|l
range|:
name|listeners
control|)
name|l
operator|.
name|scopeAppended
argument_list|(
name|this
argument_list|,
name|scopeId
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|fireScopeDetached
parameter_list|(
name|String
name|scopeId
parameter_list|)
block|{
for|for
control|(
name|SessionListener
name|l
range|:
name|listeners
control|)
name|l
operator|.
name|scopeDetached
argument_list|(
name|this
argument_list|,
name|scopeId
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getAttachedScopes
parameter_list|()
block|{
return|return
name|attachedScopes
return|;
block|}
annotation|@
name|Override
specifier|protected
name|MGraph
name|getOntologyAsMGraph
parameter_list|(
name|OWLOntologyID
name|ontologyId
parameter_list|,
name|boolean
name|merge
parameter_list|,
name|IRI
name|universalPrefix
parameter_list|)
block|{
name|MGraph
name|o
init|=
name|super
operator|.
name|getOntologyAsMGraph
argument_list|(
name|ontologyId
argument_list|,
name|merge
argument_list|,
name|universalPrefix
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|getConnectivityPolicy
argument_list|()
condition|)
block|{
case|case
name|LOOSE
case|:
break|break;
case|case
name|TIGHT
case|:
name|attachScopeImportsClerezza
argument_list|(
name|o
argument_list|,
name|universalPrefix
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
return|return
name|o
return|;
block|}
annotation|@
name|Override
specifier|protected
name|OWLOntology
name|getOntologyAsOWLOntology
parameter_list|(
name|OWLOntologyID
name|ontologyId
parameter_list|,
name|boolean
name|merge
parameter_list|,
name|IRI
name|universalPrefix
parameter_list|)
block|{
name|OWLOntology
name|o
init|=
name|super
operator|.
name|getOntologyAsOWLOntology
argument_list|(
name|ontologyId
argument_list|,
name|merge
argument_list|,
name|universalPrefix
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|getConnectivityPolicy
argument_list|()
condition|)
block|{
case|case
name|LOOSE
case|:
break|break;
case|case
name|TIGHT
case|:
name|attachScopeImportsOwlApi
argument_list|(
name|o
argument_list|,
name|universalPrefix
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
return|return
name|o
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
name|State
name|getSessionState
parameter_list|()
block|{
return|return
name|state
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isActive
parameter_list|()
block|{
return|return
name|state
operator|==
name|State
operator|.
name|ACTIVE
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|open
parameter_list|()
throws|throws
name|NonReferenceableSessionException
block|{
name|setActive
argument_list|(
literal|true
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
name|State
name|setActive
parameter_list|(
name|boolean
name|active
parameter_list|)
throws|throws
name|NonReferenceableSessionException
block|{
if|if
condition|(
name|getSessionState
argument_list|()
operator|==
name|State
operator|.
name|ZOMBIE
condition|)
throw|throw
operator|new
name|NonReferenceableSessionException
argument_list|()
throw|;
else|else
name|state
operator|=
name|active
condition|?
name|State
operator|.
name|ACTIVE
else|:
name|State
operator|.
name|HALTED
expr_stmt|;
return|return
name|getSessionState
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|setID
parameter_list|(
name|String
name|id
parameter_list|)
block|{
comment|// TODO check form of ID
name|this
operator|.
name|_id
operator|=
name|id
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getID
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit
