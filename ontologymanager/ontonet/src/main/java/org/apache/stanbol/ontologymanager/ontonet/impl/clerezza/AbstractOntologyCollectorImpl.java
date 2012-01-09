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
name|clerezza
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
name|Collections
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
name|SimpleMGraph
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
name|ontonet
operator|.
name|api
operator|.
name|io
operator|.
name|OntologyInputSource
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
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|OntologyCollectorListener
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
name|OntologyCollectorModificationException
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
name|OntologyInputSourceHandler
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
name|OntologySpace
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
name|UnmodifiableOntologyCollectorException
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
name|OWLUtils
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

begin_comment
comment|/**  * A basic Clerezza-native implementation of an ontology collector.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|AbstractOntologyCollectorImpl
implements|implements
name|OntologyCollector
implements|,
name|Lockable
implements|,
name|OntologyInputSourceHandler
implements|,
name|OWLExportable
block|{
specifier|protected
name|String
name|_id
init|=
literal|null
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|OntologyCollectorListener
argument_list|>
name|listeners
init|=
operator|new
name|HashSet
argument_list|<
name|OntologyCollectorListener
argument_list|>
argument_list|()
decl_stmt|;
comment|/**      * Indicates whether this ontology space is marked as read-only. Default value is false.      */
specifier|protected
specifier|volatile
name|boolean
name|locked
init|=
literal|false
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
comment|/**      * The identifier of the ontologies directly managed by this collector (i.e. that were directly added to      * this space, hence not including those just pulled in via import statements).      *       * TODO make it a set again and have the ontology provider manage the mapping?      */
specifier|protected
name|Set
argument_list|<
name|IRI
argument_list|>
name|managedOntologies
decl_stmt|;
specifier|protected
name|IRI
name|namespace
init|=
literal|null
decl_stmt|;
specifier|protected
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|ontologyProvider
decl_stmt|;
specifier|protected
name|Set
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|supportedTypes
decl_stmt|;
specifier|public
name|AbstractOntologyCollectorImpl
parameter_list|(
name|String
name|id
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
comment|// Supports OWL API and Clerezza
name|supportedTypes
operator|=
operator|new
name|HashSet
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|supportedTypes
operator|.
name|add
argument_list|(
name|OWLOntology
operator|.
name|class
argument_list|)
expr_stmt|;
name|supportedTypes
operator|.
name|add
argument_list|(
name|TripleCollection
operator|.
name|class
argument_list|)
expr_stmt|;
name|setID
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|setNamespace
argument_list|(
name|namespace
argument_list|)
expr_stmt|;
name|this
operator|.
name|ontologyProvider
operator|=
name|ontologyProvider
expr_stmt|;
name|this
operator|.
name|managedOntologies
operator|=
operator|new
name|HashSet
argument_list|<
name|IRI
argument_list|>
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|addListener
parameter_list|(
name|OntologyCollectorListener
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
specifier|synchronized
name|String
name|addOntology
parameter_list|(
name|OntologyInputSource
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|ontologySource
parameter_list|)
throws|throws
name|UnmodifiableOntologyCollectorException
block|{
name|long
name|before
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
if|if
condition|(
name|locked
condition|)
throw|throw
operator|new
name|UnmodifiableOntologyCollectorException
argument_list|(
name|this
argument_list|)
throw|;
name|log
operator|.
name|debug
argument_list|(
literal|"Adding ontology {} to space {}"
argument_list|,
name|ontologySource
operator|!=
literal|null
condition|?
name|ontologySource
else|:
literal|"<NULL>"
argument_list|,
name|getNamespace
argument_list|()
operator|+
name|getID
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|ontologySource
operator|==
literal|null
operator|||
operator|!
name|ontologySource
operator|.
name|hasRootOntology
argument_list|()
condition|)
comment|// No ontology to add
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Ontology source cannot be null and must provide an ontology object."
argument_list|)
throw|;
name|Object
name|o
init|=
name|ontologySource
operator|.
name|getRootOntology
argument_list|()
decl_stmt|;
name|UriRef
name|uri
decl_stmt|;
comment|/*          * Note for the developer: make sure the call to guessOntologyIdentifier() is only performed once          * during all the storage process, otherwise multiple calls could return different results for          * anonymous ontologies.          */
if|if
condition|(
name|o
operator|instanceof
name|TripleCollection
condition|)
block|{
name|uri
operator|=
name|OWLUtils
operator|.
name|guessOntologyIdentifier
argument_list|(
operator|(
name|TripleCollection
operator|)
name|o
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|o
operator|instanceof
name|OWLOntology
condition|)
block|{
name|uri
operator|=
operator|new
name|UriRef
argument_list|(
name|OWLUtils
operator|.
name|guessOntologyIdentifier
argument_list|(
operator|(
name|OWLOntology
operator|)
name|o
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"This ontology space implementation cannot handle "
operator|+
name|o
operator|.
name|getClass
argument_list|()
operator|+
literal|" objects."
argument_list|)
throw|;
comment|// Now for the actual storage. We pass the ontology object directly.
name|String
name|key
init|=
literal|null
decl_stmt|;
name|key
operator|=
name|ontologyProvider
operator|.
name|loadInStore
argument_list|(
name|o
argument_list|,
name|uri
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|/*          * Actually we are not interested in knowing the key here (ontology collectors are not concerned with          * them), but knowing it is non-null and non-empty indicates the operation was successful.          */
if|if
condition|(
name|key
operator|!=
literal|null
operator|&&
operator|!
name|key
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// add to index
name|managedOntologies
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|uri
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Note that imported ontologies are not considered as managed! TODO should we change this?
name|log
operator|.
name|debug
argument_list|(
literal|"Add ontology completed in {} ms."
argument_list|,
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|before
operator|)
argument_list|)
expr_stmt|;
comment|// fire the event
name|fireOntologyAdded
argument_list|(
name|uri
argument_list|)
expr_stmt|;
block|}
return|return
name|key
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
parameter_list|<
name|O
parameter_list|>
name|O
name|export
parameter_list|(
name|Class
argument_list|<
name|O
argument_list|>
name|returnType
parameter_list|,
name|boolean
name|merge
parameter_list|)
block|{
if|if
condition|(
name|OWLOntology
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|returnType
argument_list|)
condition|)
return|return
operator|(
name|O
operator|)
name|exportToOWLOntology
argument_list|(
name|merge
argument_list|)
return|;
if|if
condition|(
name|TripleCollection
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|returnType
argument_list|)
condition|)
block|{
if|if
condition|(
name|merge
condition|)
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Merge not implemented yet for Clerezza triple collections."
argument_list|)
throw|;
comment|// No need to store, give it a name, or anything.
name|TripleCollection
name|root
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|UriRef
name|iri
init|=
operator|new
name|UriRef
argument_list|(
name|namespace
operator|+
name|_id
argument_list|)
decl_stmt|;
comment|// Add the import declarations for directly managed ontologies.
if|if
condition|(
name|root
operator|!=
literal|null
condition|)
block|{
name|String
name|base
init|=
name|URIUtils
operator|.
name|upOne
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|namespace
operator|+
name|getID
argument_list|()
argument_list|)
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
name|UriRef
name|physIRI
init|=
operator|new
name|UriRef
argument_list|(
name|base
operator|+
name|ontologyIri
argument_list|)
decl_stmt|;
name|root
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
return|return
operator|(
name|O
operator|)
name|root
return|;
block|}
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Cannot export to "
operator|+
name|returnType
argument_list|)
throw|;
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
return|return
name|export
argument_list|(
name|OWLOntology
operator|.
name|class
argument_list|,
name|merge
argument_list|)
return|;
block|}
specifier|private
name|OWLOntology
name|exportToOWLOntology
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
literal|"Failed to assemble root ontology for scope "
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
literal|"Failed to assemble root ontology for scope "
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
name|URIUtils
operator|.
name|upOne
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|namespace
operator|+
name|getID
argument_list|()
argument_list|)
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
literal|"OWL export of {} completed in {} ms."
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
name|clearListeners
parameter_list|()
block|{
name|listeners
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
comment|/**      * Notifies all ontology space listeners that an ontology has been added to this space.      *       * @param ontologyIri      *            the identifier of the ontology that was added to this space.      */
specifier|protected
name|void
name|fireOntologyAdded
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|)
block|{
for|for
control|(
name|OntologyCollectorListener
name|listener
range|:
name|listeners
control|)
name|listener
operator|.
name|onOntologyAdded
argument_list|(
name|this
operator|.
name|getID
argument_list|()
argument_list|,
name|ontologyIri
argument_list|)
expr_stmt|;
block|}
comment|/**      * Notifies all ontology space listeners that an ontology has been added to this space.      *       * @param ontologyIri      *            the identifier of the ontology that was added to this space.      */
specifier|protected
name|void
name|fireOntologyAdded
parameter_list|(
name|UriRef
name|ontologyIri
parameter_list|)
block|{
name|fireOntologyAdded
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ontologyIri
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Notifies all ontology space listeners that an ontology has been removed from this space.      *       * @param ontologyIri      *            the identifier of the ontology that was removed from this space.      */
specifier|protected
name|void
name|fireOntologyRemoved
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|)
block|{
for|for
control|(
name|OntologyCollectorListener
name|listener
range|:
name|listeners
control|)
name|listener
operator|.
name|onOntologyRemoved
argument_list|(
name|this
operator|.
name|getID
argument_list|()
argument_list|,
name|ontologyIri
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|fireOntologyRemoved
parameter_list|(
name|UriRef
name|ontologyIri
parameter_list|)
block|{
name|fireOntologyRemoved
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ontologyIri
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getID
parameter_list|()
block|{
return|return
name|_id
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|OntologyCollectorListener
argument_list|>
name|getListeners
parameter_list|()
block|{
return|return
name|listeners
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
name|namespace
return|;
block|}
comment|/**      * FIXME not including closure yet.      *       * @see OntologySpace#getOntologies(boolean)      */
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|(
name|boolean
name|withClosure
parameter_list|)
block|{
comment|// if (withClosure) throw new UnsupportedOperationException(
comment|// "Closure support not implemented efficiently yet. Please call getOntologies(false) and compute the closure union for the OWLOntology objects in the set.");
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
init|=
operator|new
name|HashSet
argument_list|<
name|OWLOntology
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|IRI
name|id
range|:
name|managedOntologies
control|)
block|{
comment|// FIXME temporary fix is to merge instead of including closure
name|ontologies
operator|.
name|add
argument_list|(
name|getOntology
argument_list|(
name|id
argument_list|,
name|withClosure
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|ontologies
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getOntologyCount
parameter_list|(
name|boolean
name|withClosure
parameter_list|)
block|{
if|if
condition|(
name|withClosure
condition|)
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Closure support not implemented efficiently yet. Please call getOntologyCount(false)."
argument_list|)
throw|;
return|return
name|managedOntologies
operator|.
name|size
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|getSupportedOntologyTypes
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|supportedTypes
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasOntology
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|)
block|{
return|return
name|managedOntologies
operator|.
name|contains
argument_list|(
name|ontologyIri
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isLocked
parameter_list|()
block|{
return|return
name|locked
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeListener
parameter_list|(
name|OntologyCollectorListener
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
name|removeOntology
parameter_list|(
name|IRI
name|ontologyId
parameter_list|)
throws|throws
name|OntologyCollectorModificationException
block|{
if|if
condition|(
name|locked
condition|)
throw|throw
operator|new
name|UnmodifiableOntologyCollectorException
argument_list|(
name|this
argument_list|)
throw|;
try|try
block|{
name|managedOntologies
operator|.
name|remove
argument_list|(
name|ontologyId
argument_list|)
expr_stmt|;
name|fireOntologyRemoved
argument_list|(
name|ontologyId
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|OntologyCollectorModificationException
argument_list|(
name|this
argument_list|,
name|ex
argument_list|)
throw|;
block|}
block|}
specifier|protected
specifier|abstract
name|void
name|setID
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/**      * @param namespace      *            The OntoNet namespace that will prefix the space ID in Web references. This implementation      *            only allows non-null and non-empty IRIs, with no query or fragment. Hash URIs are not      *            allowed, slash URIs are preferred. If neither, a slash will be concatenated and a warning      *            will be logged.      *       * @see OntologySpace#setNamespace(IRI)      */
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
name|namespace
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|namespace
operator|+
literal|"/"
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|namespace
operator|=
name|namespace
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|this
operator|.
name|locked
operator|=
literal|true
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|this
operator|.
name|locked
operator|=
literal|false
expr_stmt|;
block|}
block|}
end_class

end_unit

