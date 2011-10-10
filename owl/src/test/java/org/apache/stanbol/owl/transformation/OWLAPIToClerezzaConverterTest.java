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
name|owl
operator|.
name|transformation
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
name|junit
operator|.
name|BeforeClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
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
name|OWLClass
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
name|OWLNamedIndividual
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
name|OWLObjectProperty
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
name|vocabulary
operator|.
name|RDF
import|;
end_import

begin_comment
comment|/**  * It is a JUnit test class.<br>  * It tests the methods of the class {@link OWLAPIToClerezzaConverter}.  *   * @author andrea.nuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|OWLAPIToClerezzaConverterTest
block|{
specifier|private
specifier|static
name|OWLOntology
name|ontology
decl_stmt|;
specifier|private
specifier|static
name|MGraph
name|mGraph
decl_stmt|;
specifier|private
specifier|static
name|String
name|ns
init|=
literal|"http://incubator.apache.org/stanbol/owl#"
decl_stmt|;
specifier|private
specifier|static
name|String
name|foaf
init|=
literal|"http://xmlns.com/foaf/0.1/"
decl_stmt|;
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|OWLAPIToClerezzaConverterTest
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setupClass
parameter_list|()
block|{
comment|/*          * Set-up the OWL ontology for the test. Simply add the axioms: AndreaNuzzolese isA Person -> class          * assertion axiom EnricoDaga isA Person -> class assertion axiom AndreaNuzzolese knows EnricoDaga ->          * object property assertion axiom          */
name|OWLOntologyManager
name|manager
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|factory
init|=
name|manager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
try|try
block|{
name|ontology
operator|=
name|manager
operator|.
name|createOntology
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ns
operator|+
literal|"testOntology"
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
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ontology
operator|!=
literal|null
condition|)
block|{
name|OWLClass
name|personClass
init|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|foaf
operator|+
literal|"Person"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|andreaNuzzoleseOWL
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ns
operator|+
literal|"AndreaNuzzolese"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|enricoDagaOWL
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ns
operator|+
literal|"EnricoDaga"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLObjectProperty
name|knowsOWL
init|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|foaf
operator|+
literal|"knows"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLAxiom
name|axiom
init|=
name|factory
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|personClass
argument_list|,
name|andreaNuzzoleseOWL
argument_list|)
decl_stmt|;
name|manager
operator|.
name|addAxiom
argument_list|(
name|ontology
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
name|axiom
operator|=
name|factory
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|personClass
argument_list|,
name|enricoDagaOWL
argument_list|)
expr_stmt|;
name|manager
operator|.
name|addAxiom
argument_list|(
name|ontology
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
name|axiom
operator|=
name|factory
operator|.
name|getOWLObjectPropertyAssertionAxiom
argument_list|(
name|knowsOWL
argument_list|,
name|andreaNuzzoleseOWL
argument_list|,
name|enricoDagaOWL
argument_list|)
expr_stmt|;
name|manager
operator|.
name|addAxiom
argument_list|(
name|ontology
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
block|}
comment|/*          * Set-up the Clerezza model for the test. As before simply add the triples: AndreaNuzzolese isA          * Person EnricoDaga isA Person AndreaNuzzolese knows EnricoDaga          */
name|mGraph
operator|=
operator|new
name|SimpleMGraph
argument_list|()
expr_stmt|;
name|UriRef
name|knowsInClerezza
init|=
operator|new
name|UriRef
argument_list|(
name|ns
operator|+
literal|"knows"
argument_list|)
decl_stmt|;
name|UriRef
name|rdfType
init|=
operator|new
name|UriRef
argument_list|(
name|RDF
operator|.
name|getURI
argument_list|()
operator|+
literal|"type"
argument_list|)
decl_stmt|;
name|UriRef
name|foafPersonInClerezza
init|=
operator|new
name|UriRef
argument_list|(
name|foaf
operator|+
literal|"Person"
argument_list|)
decl_stmt|;
name|NonLiteral
name|andreaNuzzoleseInClerezza
init|=
operator|new
name|UriRef
argument_list|(
name|ns
operator|+
literal|"AndreaNuzzolese"
argument_list|)
decl_stmt|;
name|NonLiteral
name|enricoDagaInClerezza
init|=
operator|new
name|UriRef
argument_list|(
name|ns
operator|+
literal|"EnricoDaga"
argument_list|)
decl_stmt|;
name|Triple
name|triple
init|=
operator|new
name|TripleImpl
argument_list|(
name|andreaNuzzoleseInClerezza
argument_list|,
name|rdfType
argument_list|,
name|foafPersonInClerezza
argument_list|)
decl_stmt|;
name|mGraph
operator|.
name|add
argument_list|(
name|triple
argument_list|)
expr_stmt|;
name|triple
operator|=
operator|new
name|TripleImpl
argument_list|(
name|enricoDagaInClerezza
argument_list|,
name|rdfType
argument_list|,
name|foafPersonInClerezza
argument_list|)
expr_stmt|;
name|mGraph
operator|.
name|add
argument_list|(
name|triple
argument_list|)
expr_stmt|;
name|triple
operator|=
operator|new
name|TripleImpl
argument_list|(
name|andreaNuzzoleseInClerezza
argument_list|,
name|knowsInClerezza
argument_list|,
name|enricoDagaInClerezza
argument_list|)
expr_stmt|;
name|mGraph
operator|.
name|add
argument_list|(
name|triple
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMGraphToOWLOntology
parameter_list|()
block|{
comment|/*          * Transform the Clerezza MGraph to an OWLOntology.          */
name|OWLOntology
name|ontology
init|=
name|OWLAPIToClerezzaConverter
operator|.
name|clerezzaGraphToOWLOntology
argument_list|(
name|mGraph
argument_list|)
decl_stmt|;
comment|/*          * Print the number of axioms contained in the OWLOntology.          */
name|int
name|axiomCount
init|=
name|ontology
operator|.
name|getAxiomCount
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"The ontology contatins "
operator|+
name|axiomCount
operator|+
literal|" axioms: "
argument_list|)
expr_stmt|;
comment|/*          * Print the axioms contained in the OWLOntology.          */
name|Set
argument_list|<
name|OWLAxiom
argument_list|>
name|axioms
init|=
name|ontology
operator|.
name|getAxioms
argument_list|()
decl_stmt|;
for|for
control|(
name|OWLAxiom
name|axiom
range|:
name|axioms
control|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"    "
operator|+
name|axiom
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testOWLOntologyToMGraph
parameter_list|()
block|{
comment|/*          * Transform the OWLOntology into a Clerezza MGraph.          */
name|TripleCollection
name|mGraph
init|=
name|OWLAPIToClerezzaConverter
operator|.
name|owlOntologyToClerezzaMGraph
argument_list|(
name|ontology
argument_list|)
decl_stmt|;
comment|/*          * Print all the triples contained in the Clerezza MGraph.          */
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|tripleIt
init|=
name|mGraph
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|tripleIt
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Triple
name|triple
init|=
name|tripleIt
operator|.
name|next
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
name|triple
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testOWLOntologyToTriples
parameter_list|()
block|{
comment|/*          * Transform the OWLOntology into a collection of Clerezza triples.          */
name|Collection
argument_list|<
name|Triple
argument_list|>
name|triples
init|=
name|OWLAPIToClerezzaConverter
operator|.
name|owlOntologyToClerezzaTriples
argument_list|(
name|ontology
argument_list|)
decl_stmt|;
comment|/*          * Print the collection of Clerezza triples.          */
for|for
control|(
name|Triple
name|triple
range|:
name|triples
control|)
block|{
name|log
operator|.
name|info
argument_list|(
name|triple
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

