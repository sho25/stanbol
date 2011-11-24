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
name|ArrayList
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
name|ModelFactory
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
comment|/**  * Input provider which accept a url string as input.  *   * @author enridaga  *  */
end_comment

begin_class
specifier|public
class|class
name|UrlInputProvider
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
name|url
decl_stmt|;
comment|/**      * Constructor      *       * @param url      */
specifier|public
name|UrlInputProvider
parameter_list|(
name|String
name|url
parameter_list|)
block|{
name|this
operator|.
name|url
operator|=
name|url
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
comment|// We add additional axioms
name|OWLOntology
name|fromUrl
decl_stmt|;
try|try
block|{
name|fromUrl
operator|=
name|createOWLOntologyManager
argument_list|()
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|url
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|e
argument_list|)
throw|;
block|}
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|all
init|=
name|fromUrl
operator|.
name|getImportsClosure
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|OWLAxiom
argument_list|>
name|axiomList
init|=
operator|new
name|ArrayList
argument_list|<
name|OWLAxiom
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|OWLOntology
name|o
range|:
name|all
control|)
block|{
name|axiomList
operator|.
name|addAll
argument_list|(
name|o
operator|.
name|getAxioms
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|final
name|Iterator
argument_list|<
name|OWLAxiom
argument_list|>
name|iterator
init|=
name|axiomList
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
name|ModelFactory
operator|.
name|createOntologyModel
argument_list|()
decl_stmt|;
synchronized|synchronized
init|(
name|url
init|)
block|{
try|try
block|{
comment|// FIXME: use instead:
comment|// FileManager.get().loadModel
name|input
operator|.
name|read
argument_list|(
name|url
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
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
comment|// instead of trying on the web
comment|// directly
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
