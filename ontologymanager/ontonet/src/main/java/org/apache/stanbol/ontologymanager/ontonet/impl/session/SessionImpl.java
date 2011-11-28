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
name|util
operator|.
name|ArrayList
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
name|OWLExportable
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
name|impl
operator|.
name|clerezza
operator|.
name|AbstractOntologyCollectorImpl
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
name|owl
operator|.
name|util
operator|.
name|URIUtils
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
name|OWLImportsDeclaration
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
name|OWLOntologyAlreadyExistsException
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
name|model
operator|.
name|RemoveImport
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
name|Map
argument_list|<
name|String
argument_list|,
name|OntologyScope
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
comment|// setNamespace(namespace);
name|attachedScopes
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|OntologyScope
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
name|OWLOntology
name|asOWLOntology
parameter_list|(
name|boolean
name|merge
parameter_list|)
block|{
name|OWLOntology
name|o
init|=
name|super
operator|.
name|asOWLOntology
argument_list|(
name|merge
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
operator|&&
operator|!
name|merge
condition|)
block|{
name|OWLOntologyManager
name|ontologyManager
init|=
name|o
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
comment|// Add import declarations for attached scopes.
for|for
control|(
name|String
name|scopeID
range|:
name|getAttachedScopes
argument_list|()
control|)
block|{
name|IRI
name|physIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|namespace
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
name|o
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
name|ontologyManager
operator|.
name|applyChanges
argument_list|(
name|changes
argument_list|)
expr_stmt|;
block|}
return|return
name|o
return|;
block|}
comment|/**      * FIXME not merging yet FIXME not including imported ontologies unless they are merged *before* storage.      *       * @see OWLExportable#asOWLOntology(boolean)      */
comment|// @Override
specifier|public
name|OWLOntology
name|asOWLOntology2
parameter_list|(
name|boolean
name|merge
parameter_list|)
block|{
name|long
name|before
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
comment|// Create a new ontology
name|OWLOntology
name|root
decl_stmt|;
name|OWLOntologyManager
name|ontologyManager
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|IRI
name|iri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|namespace
operator|+
name|_id
argument_list|)
decl_stmt|;
try|try
block|{
name|root
operator|=
name|ontologyManager
operator|.
name|createOntology
argument_list|(
name|iri
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyAlreadyExistsException
name|e
parameter_list|)
block|{
comment|// It should be impossible, but just in case.
name|ontologyManager
operator|.
name|removeOntology
argument_list|(
name|ontologyManager
operator|.
name|getOntology
argument_list|(
name|iri
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|root
operator|=
name|ontologyManager
operator|.
name|createOntology
argument_list|(
name|iri
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyAlreadyExistsException
name|e1
parameter_list|)
block|{
name|root
operator|=
name|ontologyManager
operator|.
name|getOntology
argument_list|(
name|iri
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e1
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to assemble root ontology for session "
operator|+
name|_id
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|root
operator|=
literal|null
expr_stmt|;
block|}
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
literal|"Failed to assemble root ontology for session "
operator|+
name|_id
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|root
operator|=
literal|null
expr_stmt|;
block|}
comment|// Add the import declarations for directly managed ontologies.
if|if
condition|(
name|root
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|merge
condition|)
block|{
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
name|log
operator|.
name|debug
argument_list|(
literal|"Merging {} with its imports."
argument_list|,
name|root
argument_list|)
expr_stmt|;
name|set
operator|.
name|add
argument_list|(
name|root
argument_list|)
expr_stmt|;
for|for
control|(
name|IRI
name|ontologyIri
range|:
name|managedOntologies
control|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Merging {} with {}."
argument_list|,
name|ontologyIri
argument_list|,
name|root
argument_list|)
expr_stmt|;
name|set
operator|.
name|add
argument_list|(
name|getOntology
argument_list|(
name|ontologyIri
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
name|root
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
name|root
operator|=
literal|null
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// Add the import declarations for directly managed ontologies.
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
name|OWLDataFactory
name|df
init|=
name|ontologyManager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|String
name|base
init|=
name|IRI
operator|.
name|create
argument_list|(
name|namespace
operator|+
name|getID
argument_list|()
argument_list|)
operator|+
literal|"/"
decl_stmt|;
comment|// The key set of managedOntologies contains the ontology IRIs, not their storage keys.
for|for
control|(
name|IRI
name|ontologyIri
range|:
name|managedOntologies
control|)
block|{
name|IRI
name|physIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|base
operator|+
name|ontologyIri
argument_list|)
decl_stmt|;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|root
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
comment|// Add import declarations for attached scopes.
for|for
control|(
name|String
name|scopeID
range|:
name|getAttachedScopes
argument_list|()
control|)
block|{
name|IRI
name|physIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|namespace
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
name|root
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
name|log
operator|.
name|debug
argument_list|(
literal|"OWL export of session {} completed in {} ms."
argument_list|,
name|getID
argument_list|()
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|before
argument_list|)
expr_stmt|;
return|return
name|root
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|attachScope
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
block|{
name|attachedScopes
operator|.
name|put
argument_list|(
name|scope
operator|.
name|getID
argument_list|()
argument_list|,
name|scope
argument_list|)
expr_stmt|;
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
name|attachedScopes
operator|.
name|remove
argument_list|(
name|scopeId
argument_list|)
expr_stmt|;
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
operator|.
name|keySet
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntology
name|getOntology
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|,
name|boolean
name|merge
parameter_list|)
block|{
name|long
name|before
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
comment|// Remove the check below. It might be an unmanaged dependency (TODO remove from collector and
comment|// reintroduce check?).
comment|// if (!hasOntology(ontologyIri)) return null;
name|OWLOntology
name|o
decl_stmt|;
name|o
operator|=
operator|(
name|OWLOntology
operator|)
name|ontologyProvider
operator|.
name|getStoredOntology
argument_list|(
name|ontologyIri
argument_list|,
name|OWLOntology
operator|.
name|class
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|// Rewrite import statements
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|df
init|=
name|o
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
comment|/*          * TODO manage import rewrites better once the container ID is fully configurable (i.e. instead of          * going upOne() add "session" or "ontology" if needed).          */
for|for
control|(
name|OWLImportsDeclaration
name|oldImp
range|:
name|o
operator|.
name|getImportsDeclarations
argument_list|()
control|)
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|RemoveImport
argument_list|(
name|o
argument_list|,
name|oldImp
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|s
init|=
name|oldImp
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|s
operator|=
name|s
operator|.
name|substring
argument_list|(
name|s
operator|.
name|indexOf
argument_list|(
literal|"::"
argument_list|)
operator|+
literal|2
argument_list|,
name|s
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|managed
init|=
name|managedOntologies
operator|.
name|contains
argument_list|(
name|oldImp
operator|.
name|getIRI
argument_list|()
argument_list|)
decl_stmt|;
name|IRI
name|target
init|=
name|IRI
operator|.
name|create
argument_list|(
operator|(
name|managed
condition|?
name|getNamespace
argument_list|()
operator|+
name|getID
argument_list|()
operator|+
literal|"/"
else|:
name|URIUtils
operator|.
name|upOne
argument_list|(
name|getNamespace
argument_list|()
argument_list|)
operator|+
literal|"/"
operator|)
operator|+
name|s
argument_list|)
decl_stmt|;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|o
argument_list|,
name|df
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|target
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|o
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|applyChanges
argument_list|(
name|changes
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"OWL export of session "
operator|+
name|getID
argument_list|()
operator|+
literal|" ontology {} completed in {} ms."
argument_list|,
name|ontologyIri
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|before
argument_list|)
expr_stmt|;
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

