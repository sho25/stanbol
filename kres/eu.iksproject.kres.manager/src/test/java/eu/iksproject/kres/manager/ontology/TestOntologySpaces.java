begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|manager
operator|.
name|ontology
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertFalse
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertSame
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|fail
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|KReSONManager
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
name|io
operator|.
name|RootOntologyIRISource
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
name|RootOntologySource
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
name|ontology
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
name|ontology
operator|.
name|OntologySpaceFactory
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
name|OntologySpaceModificationException
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
name|ontology
operator|.
name|UnmodifiableOntologySpaceException
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
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|manager
operator|.
name|Constants
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
name|manager
operator|.
name|ONManager
import|;
end_import

begin_class
specifier|public
class|class
name|TestOntologySpaces
block|{
specifier|public
specifier|static
name|IRI
name|baseIri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|Constants
operator|.
name|base
argument_list|)
decl_stmt|,
name|baseIri2
init|=
name|IRI
operator|.
name|create
argument_list|(
name|Constants
operator|.
name|base2
argument_list|)
decl_stmt|,
name|scopeIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/scope/Peanuts"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|IRI
name|pizzaIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.co-ode.org/ontologies/pizza/2007/02/12/pizza.owl"
argument_list|)
decl_stmt|,
name|wineIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.schemaweb.info/webservices/rest/GetRDFByID.aspx?id=62"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|OWLOntology
name|ont
init|=
literal|null
decl_stmt|,
name|ont2
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|KReSONManager
name|onm
decl_stmt|;
specifier|private
specifier|static
name|OntologyInputSource
name|ontSrc
decl_stmt|,
name|ont2Src
decl_stmt|,
name|pizzaSrc
decl_stmt|,
name|wineSrc
decl_stmt|;
specifier|private
specifier|static
name|OntologySpaceFactory
name|spaceFactory
decl_stmt|;
specifier|private
specifier|static
name|OWLAxiom
name|linusIsHuman
init|=
literal|null
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setup
parameter_list|()
block|{
comment|// An ONManager with no store and default settings
name|onm
operator|=
operator|new
name|ONManager
argument_list|(
literal|null
argument_list|,
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|spaceFactory
operator|=
name|onm
operator|.
name|getOntologySpaceFactory
argument_list|()
expr_stmt|;
if|if
condition|(
name|spaceFactory
operator|==
literal|null
condition|)
name|fail
argument_list|(
literal|"Could not instantiate ontology space factory"
argument_list|)
expr_stmt|;
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
try|try
block|{
name|ont
operator|=
name|mgr
operator|.
name|createOntology
argument_list|(
name|baseIri
argument_list|)
expr_stmt|;
name|ontSrc
operator|=
operator|new
name|RootOntologySource
argument_list|(
name|ont
argument_list|,
literal|null
argument_list|)
expr_stmt|;
comment|// Let's state that Linus is a human being
name|OWLClass
name|cHuman
init|=
name|df
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|baseIri
operator|+
literal|"/"
operator|+
name|Constants
operator|.
name|humanBeing
argument_list|)
argument_list|)
decl_stmt|;
name|OWLIndividual
name|iLinus
init|=
name|df
operator|.
name|getOWLNamedIndividual
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|baseIri
operator|+
literal|"/"
operator|+
name|Constants
operator|.
name|linus
argument_list|)
argument_list|)
decl_stmt|;
name|linusIsHuman
operator|=
name|df
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|cHuman
argument_list|,
name|iLinus
argument_list|)
expr_stmt|;
name|mgr
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|ont
argument_list|,
name|linusIsHuman
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
name|fail
argument_list|(
literal|"Could not setup ontology with base IRI "
operator|+
name|baseIri
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|ont2
operator|=
name|mgr
operator|.
name|createOntology
argument_list|(
name|baseIri2
argument_list|)
expr_stmt|;
name|ont2Src
operator|=
operator|new
name|RootOntologySource
argument_list|(
name|ont2
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Could not setup ontology with base IRI "
operator|+
name|baseIri2
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|pizzaSrc
operator|=
operator|new
name|RootOntologyIRISource
argument_list|(
name|pizzaIRI
argument_list|,
name|mgr
argument_list|)
expr_stmt|;
name|wineSrc
operator|=
operator|new
name|RootOntologyIRISource
argument_list|(
name|wineIRI
argument_list|,
name|mgr
argument_list|)
expr_stmt|;
name|ont2Src
operator|=
operator|new
name|RootOntologySource
argument_list|(
name|ont2
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Could not setup ontology with base IRI "
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testAddOntology
parameter_list|()
block|{
name|CustomOntologySpace
name|space
init|=
literal|null
decl_stmt|;
name|IRI
name|logicalId
init|=
name|wineSrc
operator|.
name|getRootOntology
argument_list|()
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
decl_stmt|;
try|try
block|{
name|space
operator|=
name|spaceFactory
operator|.
name|createCustomOntologySpace
argument_list|(
name|scopeIri
argument_list|,
name|pizzaSrc
argument_list|)
expr_stmt|;
name|space
operator|.
name|addOntology
argument_list|(
name|wineSrc
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologySpaceException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Add operation on "
operator|+
name|scopeIri
operator|+
literal|" custom space was denied due to unexpected lock."
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|space
operator|.
name|containsOntology
argument_list|(
name|logicalId
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testCoreLock
parameter_list|()
block|{
name|CoreOntologySpace
name|space
init|=
name|spaceFactory
operator|.
name|createCoreOntologySpace
argument_list|(
name|scopeIri
argument_list|,
name|ontSrc
argument_list|)
decl_stmt|;
name|space
operator|.
name|setUp
argument_list|()
expr_stmt|;
try|try
block|{
name|space
operator|.
name|addOntology
argument_list|(
name|ont2Src
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Modification was permitted on locked ontology space."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologySpaceException
name|e
parameter_list|)
block|{
name|assertSame
argument_list|(
name|space
argument_list|,
name|e
operator|.
name|getSpace
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testRemoveCustomOntology
parameter_list|()
block|{
name|CustomOntologySpace
name|space
init|=
literal|null
decl_stmt|;
name|space
operator|=
name|spaceFactory
operator|.
name|createCustomOntologySpace
argument_list|(
name|scopeIri
argument_list|,
name|pizzaSrc
argument_list|)
expr_stmt|;
name|IRI
name|pizzaId
init|=
name|pizzaSrc
operator|.
name|getRootOntology
argument_list|()
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
decl_stmt|;
name|IRI
name|wineId
init|=
name|wineSrc
operator|.
name|getRootOntology
argument_list|()
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
decl_stmt|;
try|try
block|{
name|space
operator|.
name|addOntology
argument_list|(
name|ontSrc
argument_list|)
expr_stmt|;
name|space
operator|.
name|addOntology
argument_list|(
name|wineSrc
argument_list|)
expr_stmt|;
comment|// The other remote ontologies may change base IRI...
name|assertTrue
argument_list|(
name|space
operator|.
name|containsOntology
argument_list|(
name|ont
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
argument_list|)
operator|&&
name|space
operator|.
name|containsOntology
argument_list|(
name|pizzaId
argument_list|)
operator|&&
name|space
operator|.
name|containsOntology
argument_list|(
name|wineId
argument_list|)
argument_list|)
expr_stmt|;
name|space
operator|.
name|removeOntology
argument_list|(
name|pizzaSrc
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|space
operator|.
name|containsOntology
argument_list|(
name|pizzaId
argument_list|)
argument_list|)
expr_stmt|;
name|space
operator|.
name|removeOntology
argument_list|(
name|wineSrc
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|space
operator|.
name|containsOntology
argument_list|(
name|wineId
argument_list|)
argument_list|)
expr_stmt|;
comment|// OntologyUtils.printOntology(space.getTopOntology(), System.err);
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologySpaceException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Modification was disallowed on non-locked ontology space."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OntologySpaceModificationException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Modification failed on ontology space "
operator|+
name|e
operator|.
name|getSpace
argument_list|()
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testCustomLock
parameter_list|()
block|{
name|CustomOntologySpace
name|space
init|=
name|spaceFactory
operator|.
name|createCustomOntologySpace
argument_list|(
name|scopeIri
argument_list|,
name|ontSrc
argument_list|)
decl_stmt|;
name|space
operator|.
name|setUp
argument_list|()
expr_stmt|;
try|try
block|{
name|space
operator|.
name|addOntology
argument_list|(
name|ont2Src
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Modification was permitted on locked ontology space."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologySpaceException
name|e
parameter_list|)
block|{
name|assertSame
argument_list|(
name|space
argument_list|,
name|e
operator|.
name|getSpace
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSessionModification
parameter_list|()
block|{
name|SessionOntologySpace
name|space
init|=
name|spaceFactory
operator|.
name|createSessionOntologySpace
argument_list|(
name|scopeIri
argument_list|)
decl_stmt|;
name|space
operator|.
name|setUp
argument_list|()
expr_stmt|;
try|try
block|{
comment|// First add an in-memory ontology with a few axioms.
name|space
operator|.
name|addOntology
argument_list|(
name|ontSrc
argument_list|)
expr_stmt|;
comment|// Now add a real online ontology
name|space
operator|.
name|addOntology
argument_list|(
name|pizzaSrc
argument_list|)
expr_stmt|;
comment|// The in-memory ontology must be in the space.
name|assertTrue
argument_list|(
name|space
operator|.
name|getOntologies
argument_list|()
operator|.
name|contains
argument_list|(
name|ont
argument_list|)
argument_list|)
expr_stmt|;
comment|// The in-memory ontology must still have its axioms.
name|assertTrue
argument_list|(
name|space
operator|.
name|getOntology
argument_list|(
name|ont
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
argument_list|)
operator|.
name|containsAxiom
argument_list|(
name|linusIsHuman
argument_list|)
argument_list|)
expr_stmt|;
comment|// The top ontology must still have axioms from in-memory
comment|// ontologies.
name|assertTrue
argument_list|(
name|space
operator|.
name|getTopOntology
argument_list|()
operator|.
name|containsAxiom
argument_list|(
name|linusIsHuman
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologySpaceException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Modification was denied on unlocked ontology space."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

