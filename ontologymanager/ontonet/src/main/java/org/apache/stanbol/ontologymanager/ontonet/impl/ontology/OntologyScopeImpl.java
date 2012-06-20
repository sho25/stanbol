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
name|ontology
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
name|Iterator
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
name|Graph
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
name|NonLiteral
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
name|Resource
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
name|Triple
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
name|clerezza
operator|.
name|rdf
operator|.
name|ontologies
operator|.
name|RDF
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
name|ontonet
operator|.
name|api
operator|.
name|collector
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
name|collector
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
name|scope
operator|.
name|CoreOntologySpace
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
name|scope
operator|.
name|CustomOntologySpace
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
name|scope
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
name|scope
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
name|scope
operator|.
name|OntologySpaceFactory
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
comment|/**  * The default implementation of an ontology scope.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|OntologyScopeImpl
implements|implements
name|OntologyScope
implements|,
name|OntologyCollectorListener
block|{
comment|/**      * The core ontology space for this scope, always set as default.      */
specifier|protected
name|CoreOntologySpace
name|coreSpace
decl_stmt|;
comment|/**      * The custom ontology space for this scope. This is optional, but cannot be set after the scope has been      * setup.      */
specifier|protected
name|CustomOntologySpace
name|customSpace
decl_stmt|;
comment|/**      * The unique identifier for this scope.      */
specifier|protected
name|String
name|id
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
comment|/**      * An ontology scope knows whether it's write-locked or not. Initially it is not.      */
specifier|protected
specifier|volatile
name|boolean
name|locked
init|=
literal|false
decl_stmt|;
specifier|private
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
name|IRI
name|namespace
init|=
literal|null
decl_stmt|;
specifier|public
name|OntologyScopeImpl
parameter_list|(
name|String
name|id
parameter_list|,
name|IRI
name|namespace
parameter_list|,
name|OntologySpaceFactory
name|factory
parameter_list|,
name|OntologyInputSource
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
modifier|...
name|coreOntologies
parameter_list|)
block|{
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
name|coreSpace
operator|=
name|factory
operator|.
name|createCoreOntologySpace
argument_list|(
name|id
comment|/* , coreOntologies */
argument_list|)
expr_stmt|;
name|this
operator|.
name|coreSpace
operator|.
name|addOntologyCollectorListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|// Set listener before adding core ontologies
for|for
control|(
name|OntologyInputSource
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|src
range|:
name|coreOntologies
control|)
name|this
operator|.
name|coreSpace
operator|.
name|addOntology
argument_list|(
name|src
argument_list|)
expr_stmt|;
comment|// let's just lock it. Once the core space is done it's done.
name|this
operator|.
name|coreSpace
operator|.
name|setUp
argument_list|()
expr_stmt|;
try|try
block|{
name|setCustomSpace
argument_list|(
name|factory
operator|.
name|createCustomOntologySpace
argument_list|(
name|id
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologyCollectorException
name|e
parameter_list|)
block|{
comment|// Cannot happen unless the factory or space implementations are really naughty.
name|log
operator|.
name|warn
argument_list|(
literal|"Ontology scope "
operator|+
name|id
operator|+
literal|" was denied creation of its own custom space upon initialization! This should not happen."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|customSpace
operator|.
name|addOntologyCollectorListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|addOntologyCollectorListener
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
name|void
name|clearOntologyCollectorListeners
parameter_list|()
block|{
name|listeners
operator|.
name|clear
argument_list|()
expr_stmt|;
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
block|{
return|return
operator|(
name|O
operator|)
name|exportToOWLOntology
argument_list|(
name|merge
argument_list|)
return|;
block|}
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
name|TripleCollection
name|root
init|=
name|exportToMGraph
argument_list|(
name|merge
argument_list|)
decl_stmt|;
comment|// A Clerezza graph has to be cast properly.
if|if
condition|(
name|returnType
operator|==
name|Graph
operator|.
name|class
condition|)
name|root
operator|=
operator|(
operator|(
name|MGraph
operator|)
name|root
operator|)
operator|.
name|getGraph
argument_list|()
expr_stmt|;
elseif|else
if|if
condition|(
name|returnType
operator|==
name|MGraph
operator|.
name|class
condition|)
block|{}
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
literal|"Cannot export scope "
operator|+
name|getID
argument_list|()
operator|+
literal|" to a "
operator|+
name|returnType
argument_list|)
throw|;
block|}
comment|/**      * Get a Clerezza {@link MGraph} representation of the scope.      *       * @param merge      *            if true the core and custom spaces will be recursively merged with the scope graph,      *            otherwise owl:imports statements will be added.      * @return the RDF representation of the scope as a modifiable graph.      */
specifier|protected
name|MGraph
name|exportToMGraph
parameter_list|(
name|boolean
name|merge
parameter_list|)
block|{
comment|// No need to store, give it a name, or anything.
name|MGraph
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
name|getNamespace
argument_list|()
operator|+
name|getID
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|root
operator|!=
literal|null
condition|)
block|{
comment|// Set the ontology ID
name|root
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|iri
argument_list|,
name|RDF
operator|.
name|type
argument_list|,
name|OWL
operator|.
name|Ontology
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|merge
condition|)
block|{
name|Graph
name|custom
decl_stmt|,
name|core
decl_stmt|;
comment|// Get the subjects of "bad" triples (those with subjects of type owl:Ontology).
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
decl_stmt|;
name|Set
argument_list|<
name|NonLiteral
argument_list|>
name|ontologies
init|=
operator|new
name|HashSet
argument_list|<
name|NonLiteral
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Resource
argument_list|>
name|importTargets
init|=
operator|new
name|HashSet
argument_list|<
name|Resource
argument_list|>
argument_list|()
decl_stmt|;
name|custom
operator|=
name|this
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|export
argument_list|(
name|Graph
operator|.
name|class
argument_list|,
name|merge
argument_list|)
expr_stmt|;
comment|// root.addAll(space);
name|it
operator|=
name|custom
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|RDF
operator|.
name|type
argument_list|,
name|OWL
operator|.
name|Ontology
argument_list|)
expr_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
name|ontologies
operator|.
name|add
argument_list|(
name|it
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
name|it
operator|=
name|custom
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|OWL
operator|.
name|imports
argument_list|,
literal|null
argument_list|)
expr_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
name|importTargets
operator|.
name|add
argument_list|(
name|it
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
argument_list|)
expr_stmt|;
name|core
operator|=
name|this
operator|.
name|getCoreSpace
argument_list|()
operator|.
name|export
argument_list|(
name|Graph
operator|.
name|class
argument_list|,
name|merge
argument_list|)
expr_stmt|;
comment|// root.addAll(space);
name|it
operator|=
name|core
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|RDF
operator|.
name|type
argument_list|,
name|OWL
operator|.
name|Ontology
argument_list|)
expr_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
name|ontologies
operator|.
name|add
argument_list|(
name|it
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
name|it
operator|=
name|core
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|OWL
operator|.
name|imports
argument_list|,
literal|null
argument_list|)
expr_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
name|importTargets
operator|.
name|add
argument_list|(
name|it
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
argument_list|)
expr_stmt|;
comment|// Make sure the scope itself is not in the "bad" subjects.
name|ontologies
operator|.
name|remove
argument_list|(
name|iri
argument_list|)
expr_stmt|;
for|for
control|(
name|NonLiteral
name|nl
range|:
name|ontologies
control|)
name|log
operator|.
name|debug
argument_list|(
literal|"{} -related triples will not be added to {}"
argument_list|,
name|nl
argument_list|,
name|iri
argument_list|)
expr_stmt|;
comment|// Merge the two spaces, skipping the "bad" triples.
name|log
operator|.
name|debug
argument_list|(
literal|"Merging custom space of {}."
argument_list|,
name|getID
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Triple
name|t
range|:
name|custom
control|)
if|if
condition|(
operator|!
name|ontologies
operator|.
name|contains
argument_list|(
name|t
operator|.
name|getSubject
argument_list|()
argument_list|)
condition|)
name|root
operator|.
name|add
argument_list|(
name|t
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Merging core space of {}."
argument_list|,
name|getID
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Triple
name|t
range|:
name|core
control|)
if|if
condition|(
operator|!
name|ontologies
operator|.
name|contains
argument_list|(
name|t
operator|.
name|getSubject
argument_list|()
argument_list|)
condition|)
name|root
operator|.
name|add
argument_list|(
name|t
argument_list|)
expr_stmt|;
comment|/*                  * Reinstate import statements, though. If imported ontologies were not merged earlier, we are                  * not doing it now anyway.                  */
for|for
control|(
name|Resource
name|target
range|:
name|importTargets
control|)
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
name|target
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|UriRef
name|physIRI
init|=
operator|new
name|UriRef
argument_list|(
name|this
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|getDocumentIRI
argument_list|()
operator|.
name|toString
argument_list|()
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
name|physIRI
operator|=
operator|new
name|UriRef
argument_list|(
name|this
operator|.
name|getCoreSpace
argument_list|()
operator|.
name|getDocumentIRI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
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
name|root
return|;
block|}
comment|/**      * Get an OWL API {@link OWLOntology} representation of the scope.      *       * @param merge      *            if true the core and custom spaces will be recursively merged with the scope ontology,      *            otherwise owl:imports statements will be added.      * @return the OWL representation of the scope.      */
specifier|protected
name|OWLOntology
name|exportToOWLOntology
parameter_list|(
name|boolean
name|merge
parameter_list|)
block|{
comment|// if (merge) throw new UnsupportedOperationException(
comment|// "Ontology merging only implemented for managed ontologies, not for collectors. "
comment|// + "Please set merge parameter to false.");
comment|// Create an ontology manager on the fly. We don't really need a permanent one.
name|OWLOntologyManager
name|mgr
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|df
init|=
name|mgr
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|OWLOntology
name|ont
init|=
literal|null
decl_stmt|;
try|try
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
literal|"Merging custom space of {}."
argument_list|,
name|getID
argument_list|()
argument_list|)
expr_stmt|;
name|set
operator|.
name|add
argument_list|(
name|this
operator|.
name|getCustomSpace
argument_list|()
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
name|log
operator|.
name|debug
argument_list|(
literal|"Merging core space of {}."
argument_list|,
name|getID
argument_list|()
argument_list|)
expr_stmt|;
name|set
operator|.
name|add
argument_list|(
name|this
operator|.
name|getCoreSpace
argument_list|()
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
name|ont
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
name|IRI
operator|.
name|create
argument_list|(
name|getNamespace
argument_list|()
operator|+
name|getID
argument_list|()
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
name|log
operator|.
name|error
argument_list|(
literal|"Failed to merge imports for ontology."
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|ont
operator|=
literal|null
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// The root ontology ID is in the form [namespace][scopeId]
name|ont
operator|=
name|mgr
operator|.
name|createOntology
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
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|additions
init|=
operator|new
name|LinkedList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
decl_stmt|;
comment|// Add the import statement for the custom space, if existing and not empty
name|OntologySpace
name|spc
init|=
name|getCustomSpace
argument_list|()
decl_stmt|;
if|if
condition|(
name|spc
operator|!=
literal|null
operator|&&
name|spc
operator|.
name|listManagedOntologies
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|IRI
name|spaceIri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|getNamespace
argument_list|()
operator|+
name|spc
operator|.
name|getID
argument_list|()
argument_list|)
decl_stmt|;
name|additions
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|ont
argument_list|,
name|df
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|spaceIri
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Add the import statement for the core space, if existing and not empty
name|spc
operator|=
name|getCoreSpace
argument_list|()
expr_stmt|;
if|if
condition|(
name|spc
operator|!=
literal|null
operator|&&
name|spc
operator|.
name|listManagedOntologies
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|IRI
name|spaceIri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|getNamespace
argument_list|()
operator|+
name|spc
operator|.
name|getID
argument_list|()
argument_list|)
decl_stmt|;
name|additions
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|ont
argument_list|,
name|df
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|spaceIri
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|mgr
operator|.
name|applyChanges
argument_list|(
name|additions
argument_list|)
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
literal|"Failed to generate an OWL form of scope "
operator|+
name|getID
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|ont
operator|=
literal|null
expr_stmt|;
block|}
return|return
name|ont
return|;
block|}
specifier|protected
name|void
name|fireOntologyAdded
parameter_list|(
name|OntologySpace
name|space
parameter_list|,
name|OWLOntologyID
name|addedOntology
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
name|space
argument_list|,
name|addedOntology
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|fireOntologyRemoved
parameter_list|(
name|OntologySpace
name|space
parameter_list|,
name|OWLOntologyID
name|removedOntology
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
name|space
argument_list|,
name|removedOntology
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|OntologySpace
name|getCoreSpace
parameter_list|()
block|{
return|return
name|coreSpace
return|;
block|}
annotation|@
name|Override
specifier|public
name|OntologySpace
name|getCustomSpace
parameter_list|()
block|{
return|return
name|customSpace
return|;
block|}
annotation|@
name|Override
specifier|public
name|IRI
name|getDocumentIRI
parameter_list|()
block|{
return|return
name|IRI
operator|.
name|create
argument_list|(
name|getNamespace
argument_list|()
operator|+
name|getID
argument_list|()
argument_list|)
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
name|this
operator|.
name|namespace
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|OntologyCollectorListener
argument_list|>
name|getOntologyCollectorListeners
parameter_list|()
block|{
return|return
name|listeners
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
name|onOntologyAdded
parameter_list|(
name|OntologyCollector
name|collector
parameter_list|,
name|OWLOntologyID
name|addedOntology
parameter_list|)
block|{
comment|// Propagate events to scope listeners
if|if
condition|(
name|collector
operator|instanceof
name|OntologySpace
condition|)
name|fireOntologyAdded
argument_list|(
operator|(
name|OntologySpace
operator|)
name|collector
argument_list|,
name|addedOntology
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onOntologyRemoved
parameter_list|(
name|OntologyCollector
name|collector
parameter_list|,
name|OWLOntologyID
name|removedOntology
parameter_list|)
block|{
comment|// Propagate events to scope listeners
if|if
condition|(
name|collector
operator|instanceof
name|OntologySpace
condition|)
name|fireOntologyRemoved
argument_list|(
operator|(
name|OntologySpace
operator|)
name|collector
argument_list|,
name|removedOntology
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeOntologyCollectorListener
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
specifier|synchronized
name|void
name|setCustomSpace
parameter_list|(
name|OntologySpace
name|customSpace
parameter_list|)
throws|throws
name|UnmodifiableOntologyCollectorException
block|{
if|if
condition|(
name|this
operator|.
name|customSpace
operator|!=
literal|null
operator|&&
name|this
operator|.
name|customSpace
operator|.
name|isLocked
argument_list|()
condition|)
throw|throw
operator|new
name|UnmodifiableOntologyCollectorException
argument_list|(
name|getCustomSpace
argument_list|()
argument_list|)
throw|;
elseif|else
if|if
condition|(
operator|!
operator|(
name|customSpace
operator|instanceof
name|CustomOntologySpace
operator|)
condition|)
throw|throw
operator|new
name|ClassCastException
argument_list|(
literal|"supplied object is not a CustomOntologySpace instance."
argument_list|)
throw|;
else|else
block|{
name|this
operator|.
name|customSpace
operator|=
operator|(
name|CustomOntologySpace
operator|)
name|customSpace
expr_stmt|;
name|this
operator|.
name|customSpace
operator|.
name|addOntologyCollectorListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|setID
parameter_list|(
name|String
name|id
parameter_list|)
block|{
if|if
condition|(
name|id
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Scope ID cannot be null."
argument_list|)
throw|;
name|id
operator|=
name|id
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Scope ID cannot be empty."
argument_list|)
throw|;
if|if
condition|(
operator|!
name|id
operator|.
name|matches
argument_list|(
literal|"[\\w-\\.]+"
argument_list|)
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Illegal scope ID "
operator|+
name|id
operator|+
literal|" - Must be an alphanumeric sequence, with optional underscores, dots or dashes."
argument_list|)
throw|;
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
block|}
comment|/**      * @param namespace      *            The OntoNet namespace that will prefix the scope ID in Web references. This implementation      *            only allows non-null and non-empty IRIs, with no query or fragment. Hash URIs are not      *            allowed, slash URIs are preferred. If neither, a slash will be concatenated and a warning      *            will be logged.      *       * @see OntologyScope#setNamespace(IRI)      */
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
specifier|synchronized
name|void
name|setUp
parameter_list|()
block|{
if|if
condition|(
name|locked
operator|||
operator|(
name|customSpace
operator|!=
literal|null
operator|&&
operator|!
name|customSpace
operator|.
name|isLocked
argument_list|()
operator|)
condition|)
return|return;
name|this
operator|.
name|coreSpace
operator|.
name|addOntologyCollectorListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|this
operator|.
name|coreSpace
operator|.
name|setUp
argument_list|()
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|customSpace
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|customSpace
operator|.
name|addOntologyCollectorListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|this
operator|.
name|customSpace
operator|.
name|setUp
argument_list|()
expr_stmt|;
block|}
name|locked
operator|=
literal|true
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|tearDown
parameter_list|()
block|{
comment|// this.coreSpace.addOntologySpaceListener(this);
name|this
operator|.
name|coreSpace
operator|.
name|tearDown
argument_list|()
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|customSpace
operator|!=
literal|null
condition|)
block|{
comment|// this.customSpace.addOntologySpaceListener(this);
name|this
operator|.
name|customSpace
operator|.
name|tearDown
argument_list|()
expr_stmt|;
block|}
name|locked
operator|=
literal|false
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
name|getNamespace
argument_list|()
operator|+
name|getID
argument_list|()
return|;
block|}
block|}
end_class

end_unit

