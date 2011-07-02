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
name|renderers
package|;
end_package

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
name|io
operator|.
name|RDFXMLOntologyFormat
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
name|io
operator|.
name|StringDocumentTarget
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
name|AddAxiom
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
name|OWLDatatype
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
name|OWLLiteral
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
name|OWLOntologyStorageException
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

begin_class
specifier|public
class|class
name|SessionRenderer
block|{
specifier|private
specifier|static
name|OWLDataFactory
name|__factory
init|=
name|OWLManager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|IRI
name|_sessionIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/ontology/onm/meta.owl#Session"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|IRI
name|_hasIdIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/ontology/onm/meta.owl#hasID"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|OWLClass
name|cSession
init|=
name|__factory
operator|.
name|getOWLClass
argument_list|(
name|_sessionIri
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|OWLDataProperty
name|hasId
init|=
name|__factory
operator|.
name|getOWLDataProperty
argument_list|(
name|_hasIdIri
argument_list|)
decl_stmt|;
static|static
block|{  	}
annotation|@
name|Deprecated
specifier|public
specifier|static
name|String
name|getSessionMetadataRDF
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|OWLOntologyManager
name|mgr
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|OWLOntology
name|ont
init|=
literal|null
decl_stmt|;
try|try
block|{
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
name|session
operator|.
name|getID
argument_list|()
operator|+
literal|"/meta.owl"
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
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ScopeSetRenderer
operator|.
name|class
argument_list|)
operator|.
name|error
argument_list|(
literal|"KReS :: could not create empty ontology for rendering sesion metadata."
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
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
name|OWLNamedIndividual
name|iSes
init|=
name|__factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|session
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
name|AddAxiom
argument_list|(
name|ont
argument_list|,
name|__factory
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|cSession
argument_list|,
name|iSes
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|OWLDatatype
name|anyURI
init|=
name|__factory
operator|.
name|getOWLDatatype
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.w3.org/2001/XMLSchema#anyURI"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLLiteral
name|hasIdValue
init|=
name|__factory
operator|.
name|getOWLTypedLiteral
argument_list|(
name|session
operator|.
name|getID
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|anyURI
argument_list|)
decl_stmt|;
name|additions
operator|.
name|add
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|ont
argument_list|,
name|__factory
operator|.
name|getOWLDataPropertyAssertionAxiom
argument_list|(
name|hasId
argument_list|,
name|iSes
argument_list|,
name|hasIdValue
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|mgr
operator|.
name|applyChanges
argument_list|(
name|additions
argument_list|)
expr_stmt|;
name|StringDocumentTarget
name|tgt
init|=
operator|new
name|StringDocumentTarget
argument_list|()
decl_stmt|;
try|try
block|{
name|mgr
operator|.
name|saveOntology
argument_list|(
name|ont
argument_list|,
operator|new
name|RDFXMLOntologyFormat
argument_list|()
argument_list|,
name|tgt
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ScopeSetRenderer
operator|.
name|class
argument_list|)
operator|.
name|error
argument_list|(
literal|"KReS :: could not save session metadata ontology."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|tgt
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|OWLOntology
name|getSessionMetadataRDFasOntology
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|OWLOntologyManager
name|mgr
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|OWLOntology
name|ont
init|=
literal|null
decl_stmt|;
try|try
block|{
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
name|session
operator|.
name|getID
argument_list|()
operator|+
literal|"/meta.owl"
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
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ScopeSetRenderer
operator|.
name|class
argument_list|)
operator|.
name|error
argument_list|(
literal|"KReS :: could not create empty ontology for rendering sesion metadata."
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
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
name|OWLNamedIndividual
name|iSes
init|=
name|__factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|session
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
name|AddAxiom
argument_list|(
name|ont
argument_list|,
name|__factory
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|cSession
argument_list|,
name|iSes
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|OWLDatatype
name|anyURI
init|=
name|__factory
operator|.
name|getOWLDatatype
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.w3.org/2001/XMLSchema#anyURI"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLLiteral
name|hasIdValue
init|=
name|__factory
operator|.
name|getOWLTypedLiteral
argument_list|(
name|session
operator|.
name|getID
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|anyURI
argument_list|)
decl_stmt|;
name|additions
operator|.
name|add
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|ont
argument_list|,
name|__factory
operator|.
name|getOWLDataPropertyAssertionAxiom
argument_list|(
name|hasId
argument_list|,
name|iSes
argument_list|,
name|hasIdValue
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|mgr
operator|.
name|applyChanges
argument_list|(
name|additions
argument_list|)
expr_stmt|;
name|StringDocumentTarget
name|tgt
init|=
operator|new
name|StringDocumentTarget
argument_list|()
decl_stmt|;
try|try
block|{
name|mgr
operator|.
name|saveOntology
argument_list|(
name|ont
argument_list|,
operator|new
name|RDFXMLOntologyFormat
argument_list|()
argument_list|,
name|tgt
argument_list|)
expr_stmt|;
return|return
name|ont
return|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ScopeSetRenderer
operator|.
name|class
argument_list|)
operator|.
name|error
argument_list|(
literal|"KReS :: could not save session metadata ontology."
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

