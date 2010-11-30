begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|semion
operator|.
name|util
package|;
end_package

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
name|OWLClassAssertionAxiom
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
name|OWLDataProperty
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
name|OWLDataPropertyAssertionAxiom
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
name|OWLIndividual
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
name|OWLObjectPropertyAssertionAxiom
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|ontologies
operator|.
name|DBS_L1
import|;
end_import

begin_class
specifier|public
class|class
name|SemionUriRefGenerator
block|{
specifier|protected
name|UriRef
name|createTypedResource
parameter_list|(
name|MGraph
name|mGraph
parameter_list|,
name|String
name|resourceURI
parameter_list|,
name|UriRef
name|type
parameter_list|)
block|{
name|UriRef
name|uriRef
init|=
operator|new
name|UriRef
argument_list|(
name|resourceURI
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
name|mGraph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|uriRef
argument_list|,
name|DBS_L1
operator|.
name|RDF_TYPE
argument_list|,
name|type
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|uriRef
return|;
block|}
specifier|protected
name|OWLClassAssertionAxiom
name|createOWLClassAssertionAxiom
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|,
name|IRI
name|owlClassIRI
parameter_list|,
name|IRI
name|individualIRI
parameter_list|)
block|{
name|OWLClass
name|owlClass
init|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|owlClassIRI
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|individual
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|individualIRI
argument_list|)
decl_stmt|;
name|OWLClassAssertionAxiom
name|classAssertion
init|=
name|factory
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|owlClass
argument_list|,
name|individual
argument_list|)
decl_stmt|;
return|return
name|classAssertion
return|;
block|}
specifier|protected
name|OWLObjectPropertyAssertionAxiom
name|createOWLObjectPropertyAssertionAxiom
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|,
name|IRI
name|objectPropertyIRI
parameter_list|,
name|IRI
name|subjectIndividualIRI
parameter_list|,
name|IRI
name|objectIndividualIRI
parameter_list|)
block|{
name|OWLObjectProperty
name|objectProperty
init|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|objectPropertyIRI
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|subjectIndividual
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|subjectIndividualIRI
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|objectIndividual
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|objectIndividualIRI
argument_list|)
decl_stmt|;
return|return
name|factory
operator|.
name|getOWLObjectPropertyAssertionAxiom
argument_list|(
name|objectProperty
argument_list|,
name|subjectIndividual
argument_list|,
name|objectIndividual
argument_list|)
return|;
block|}
specifier|protected
name|OWLDataPropertyAssertionAxiom
name|createOWLDataPropertyAssertionAxiom
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|,
name|IRI
name|dataPropertyIRI
parameter_list|,
name|IRI
name|subjectIndividualIRI
parameter_list|,
name|int
name|data
parameter_list|)
block|{
name|OWLDataProperty
name|dataProperty
init|=
name|factory
operator|.
name|getOWLDataProperty
argument_list|(
name|dataPropertyIRI
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|subjectIndividual
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|subjectIndividualIRI
argument_list|)
decl_stmt|;
return|return
name|factory
operator|.
name|getOWLDataPropertyAssertionAxiom
argument_list|(
name|dataProperty
argument_list|,
name|subjectIndividual
argument_list|,
name|data
argument_list|)
return|;
block|}
specifier|protected
name|OWLDataPropertyAssertionAxiom
name|createOWLDataPropertyAssertionAxiom
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|,
name|IRI
name|dataPropertyIRI
parameter_list|,
name|IRI
name|subjectIndividualIRI
parameter_list|,
name|double
name|data
parameter_list|)
block|{
name|OWLDataProperty
name|dataProperty
init|=
name|factory
operator|.
name|getOWLDataProperty
argument_list|(
name|dataPropertyIRI
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|subjectIndividual
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|subjectIndividualIRI
argument_list|)
decl_stmt|;
return|return
name|factory
operator|.
name|getOWLDataPropertyAssertionAxiom
argument_list|(
name|dataProperty
argument_list|,
name|subjectIndividual
argument_list|,
name|data
argument_list|)
return|;
block|}
specifier|protected
name|OWLDataPropertyAssertionAxiom
name|createOWLDataPropertyAssertionAxiom
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|,
name|IRI
name|dataPropertyIRI
parameter_list|,
name|IRI
name|subjectIndividualIRI
parameter_list|,
name|float
name|data
parameter_list|)
block|{
name|OWLDataProperty
name|dataProperty
init|=
name|factory
operator|.
name|getOWLDataProperty
argument_list|(
name|dataPropertyIRI
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|subjectIndividual
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|subjectIndividualIRI
argument_list|)
decl_stmt|;
return|return
name|factory
operator|.
name|getOWLDataPropertyAssertionAxiom
argument_list|(
name|dataProperty
argument_list|,
name|subjectIndividual
argument_list|,
name|data
argument_list|)
return|;
block|}
specifier|protected
name|OWLDataPropertyAssertionAxiom
name|createOWLDataPropertyAssertionAxiom
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|,
name|IRI
name|dataPropertyIRI
parameter_list|,
name|IRI
name|subjectIndividualIRI
parameter_list|,
name|boolean
name|data
parameter_list|)
block|{
name|OWLDataProperty
name|dataProperty
init|=
name|factory
operator|.
name|getOWLDataProperty
argument_list|(
name|dataPropertyIRI
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|subjectIndividual
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|subjectIndividualIRI
argument_list|)
decl_stmt|;
return|return
name|factory
operator|.
name|getOWLDataPropertyAssertionAxiom
argument_list|(
name|dataProperty
argument_list|,
name|subjectIndividual
argument_list|,
name|data
argument_list|)
return|;
block|}
specifier|protected
name|OWLDataPropertyAssertionAxiom
name|createOWLDataPropertyAssertionAxiom
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|,
name|IRI
name|dataPropertyIRI
parameter_list|,
name|IRI
name|subjectIndividualIRI
parameter_list|,
name|String
name|data
parameter_list|)
block|{
name|OWLDataProperty
name|dataProperty
init|=
name|factory
operator|.
name|getOWLDataProperty
argument_list|(
name|dataPropertyIRI
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|subjectIndividual
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|subjectIndividualIRI
argument_list|)
decl_stmt|;
return|return
name|factory
operator|.
name|getOWLDataPropertyAssertionAxiom
argument_list|(
name|dataProperty
argument_list|,
name|subjectIndividual
argument_list|,
name|data
argument_list|)
return|;
block|}
block|}
end_class

end_unit

