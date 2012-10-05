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
name|reasoners
operator|.
name|web
operator|.
name|input
operator|.
name|provider
operator|.
name|impl
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
name|Iterator
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
name|commons
operator|.
name|owl
operator|.
name|transformation
operator|.
name|JenaToOwlConvert
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
name|obsolete
operator|.
name|api
operator|.
name|ONManager
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
name|SessionManager
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
name|reasoners
operator|.
name|servicesapi
operator|.
name|ReasoningServiceInputProvider
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
name|MissingImportEvent
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
name|MissingImportListener
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
name|OWLAxiom
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
name|OWLOntologyLoaderListener
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

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|OntModel
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Statement
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|StmtIterator
import|;
end_import

begin_comment
comment|/**  * An input provider which binds the reasoners input to Ontonet  *   * @author enridaga  *   */
end_comment

begin_class
specifier|public
class|class
name|OntologyManagerInputProvider
implements|implements
name|ReasoningServiceInputProvider
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|UrlInputProvider
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|String
name|scopeId
decl_stmt|;
specifier|private
name|String
name|sessionId
decl_stmt|;
specifier|private
name|ONManager
name|onManager
decl_stmt|;
specifier|private
name|SessionManager
name|sessionManager
decl_stmt|;
comment|/**      * Contructor, if the input is a Scope      *       * @param onManager      * @param scopeId      */
specifier|public
name|OntologyManagerInputProvider
parameter_list|(
name|ONManager
name|onManager
parameter_list|,
name|String
name|scopeId
parameter_list|)
block|{
name|this
argument_list|(
name|onManager
argument_list|,
literal|null
argument_list|,
name|scopeId
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructor, if the input are a Scope and a Session      *       * @param onManager      * @param scopeId      * @param sessionId      */
specifier|public
name|OntologyManagerInputProvider
parameter_list|(
name|ONManager
name|onManager
parameter_list|,
name|SessionManager
name|sessionManager
parameter_list|,
name|String
name|scopeId
parameter_list|,
name|String
name|sessionId
parameter_list|)
block|{
name|this
operator|.
name|onManager
operator|=
name|onManager
expr_stmt|;
name|this
operator|.
name|scopeId
operator|=
name|scopeId
expr_stmt|;
name|this
operator|.
name|sessionManager
operator|=
name|sessionManager
expr_stmt|;
name|this
operator|.
name|sessionId
operator|=
name|sessionId
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
parameter_list|<
name|T
parameter_list|>
name|Iterator
argument_list|<
name|T
argument_list|>
name|getInput
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
throws|throws
name|IOException
block|{
comment|// This ontology is already a merged version, no need to iterate over imported ones
specifier|final
name|OWLOntology
name|o
init|=
name|getFromOntoMgr
argument_list|()
decl_stmt|;
if|if
condition|(
name|type
operator|.
name|isAssignableFrom
argument_list|(
name|OWLAxiom
operator|.
name|class
argument_list|)
condition|)
block|{
specifier|final
name|Iterator
argument_list|<
name|OWLAxiom
argument_list|>
name|iterator
init|=
name|o
operator|.
name|getAxioms
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
return|return
operator|new
name|Iterator
argument_list|<
name|T
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|iterator
operator|.
name|hasNext
argument_list|()
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|public
name|T
name|next
parameter_list|()
block|{
return|return
operator|(
name|T
operator|)
name|iterator
operator|.
name|next
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|()
block|{
comment|// This iterator is read-only
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Cannot remove statements from the iterator"
argument_list|)
throw|;
block|}
block|}
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|isAssignableFrom
argument_list|(
name|Statement
operator|.
name|class
argument_list|)
condition|)
block|{
specifier|final
name|OntModel
name|input
init|=
operator|new
name|JenaToOwlConvert
argument_list|()
operator|.
name|ModelOwlToJenaConvert
argument_list|(
name|o
argument_list|,
literal|"RDF/XML"
argument_list|)
decl_stmt|;
specifier|final
name|StmtIterator
name|iterator
init|=
name|input
operator|.
name|listStatements
argument_list|()
decl_stmt|;
return|return
operator|new
name|Iterator
argument_list|<
name|T
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|iterator
operator|.
name|hasNext
argument_list|()
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|public
name|T
name|next
parameter_list|()
block|{
return|return
operator|(
name|T
operator|)
name|iterator
operator|.
name|next
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|()
block|{
comment|// This iterator is read-only
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Cannot remove statements from the iterator"
argument_list|)
throw|;
block|}
block|}
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"This provider does not adapt to the given type"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
parameter_list|<
name|T
parameter_list|>
name|boolean
name|adaptTo
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|.
name|isAssignableFrom
argument_list|(
name|OWLAxiom
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|isAssignableFrom
argument_list|(
name|Statement
operator|.
name|class
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|private
name|OWLOntology
name|getFromOntoMgr
parameter_list|()
throws|throws
name|IOException
block|{
try|try
block|{
name|Scope
name|scope
init|=
literal|null
decl_stmt|;
synchronized|synchronized
init|(
name|onManager
init|)
block|{
name|scope
operator|=
name|onManager
operator|.
name|getScope
argument_list|(
name|this
operator|.
name|scopeId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|scope
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Scope {} cannot be retrieved"
argument_list|,
name|this
operator|.
name|scopeId
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Scope "
operator|+
name|this
operator|.
name|scopeId
operator|+
literal|" cannot be retrieved"
argument_list|)
throw|;
block|}
name|Session
name|session
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|sessionManager
operator|!=
literal|null
condition|)
synchronized|synchronized
init|(
name|sessionManager
init|)
block|{
name|session
operator|=
name|sessionManager
operator|.
name|getSession
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|session
operator|==
literal|null
condition|)
name|log
operator|.
name|warn
argument_list|(
literal|"Session {} cannot be retrieved. Ignoring."
argument_list|,
name|this
operator|.
name|sessionId
argument_list|)
expr_stmt|;
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
name|scope
operator|.
name|export
argument_list|(
name|OWLOntology
operator|.
name|class
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|session
operator|!=
literal|null
condition|)
name|set
operator|.
name|add
argument_list|(
name|session
operator|.
name|export
argument_list|(
name|OWLOntology
operator|.
name|class
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|set
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
return|return
name|set
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
return|;
name|OWLOntologyMerger
name|merger
init|=
operator|new
name|OWLOntologyMerger
argument_list|(
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
argument_list|)
decl_stmt|;
return|return
name|merger
operator|.
name|createMergedOntology
argument_list|(
name|createOWLOntologyManager
argument_list|()
argument_list|,
name|IRI
operator|.
name|create
argument_list|(
literal|"reasoners:input-"
operator|+
name|System
operator|.
name|currentTimeMillis
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
name|String
name|message
init|=
literal|"The network for scope/session cannot be retrieved"
decl_stmt|;
name|log
operator|.
name|error
argument_list|(
name|message
operator|+
literal|":"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|message
argument_list|)
throw|;
block|}
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"deprecation"
argument_list|)
specifier|private
name|OWLOntologyManager
name|createOWLOntologyManager
parameter_list|()
block|{
comment|// We isolate here the creation of the temporary manager
comment|// TODO How to behave when resolving owl:imports?
comment|// We should set the manager to use a service to lookup for ontologies,
comment|// instead of trying on the web directly
name|OWLOntologyManager
name|manager
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
comment|// FIXME Which is the other way of doing this?
comment|// Maybe -> OWLOntologyManagerProperties();
name|manager
operator|.
name|setSilentMissingImportsHandling
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// Listening for missing imports
name|manager
operator|.
name|addMissingImportListener
argument_list|(
operator|new
name|MissingImportListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|importMissing
parameter_list|(
name|MissingImportEvent
name|arg0
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Missing import {} "
argument_list|,
name|arg0
operator|.
name|getImportedOntologyURI
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|manager
operator|.
name|addOntologyLoaderListener
argument_list|(
operator|new
name|OWLOntologyLoaderListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|finishedLoadingOntology
parameter_list|(
name|LoadingFinishedEvent
name|arg0
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Finished loading {} (imported: {})"
argument_list|,
name|arg0
operator|.
name|getOntologyID
argument_list|()
argument_list|,
name|arg0
operator|.
name|isImported
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|startedLoadingOntology
parameter_list|(
name|LoadingStartedEvent
name|arg0
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Started loading {} (imported: {}) ..."
argument_list|,
name|arg0
operator|.
name|getOntologyID
argument_list|()
argument_list|,
name|arg0
operator|.
name|isImported
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|" ... from {}"
argument_list|,
name|arg0
operator|.
name|getDocumentIRI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
return|return
name|manager
return|;
block|}
block|}
end_class

end_unit
