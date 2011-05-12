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
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|Constants
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
name|ParentPathInputSource
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
name|ONManagerImpl
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
name|PEANUTS_MAIN_BASE
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
name|PEANUTS_MINOR_BASE
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
name|OWLAxiom
name|linusIsHuman
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|ONManager
name|onm
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
name|OntologyInputSource
name|ontSrc
decl_stmt|,
name|ont2Src
decl_stmt|,
name|pizzaSrc
decl_stmt|,
name|colleSrc
decl_stmt|;
specifier|private
specifier|static
name|OntologySpaceFactory
name|spaceFactory
decl_stmt|;
specifier|private
specifier|static
name|OntologyInputSource
name|getLocalSource
parameter_list|(
name|String
name|resourcePath
parameter_list|,
name|OWLOntologyManager
name|mgr
parameter_list|)
throws|throws
name|OWLOntologyCreationException
throws|,
name|URISyntaxException
block|{
name|URL
name|url
init|=
name|TestOntologySpaces
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|resourcePath
argument_list|)
decl_stmt|;
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|url
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|ParentPathInputSource
argument_list|(
name|f
argument_list|,
name|mgr
operator|!=
literal|null
condition|?
name|mgr
else|:
name|onm
operator|.
name|getOntologyManagerFactory
argument_list|()
operator|.
name|createOntologyManager
argument_list|(
literal|true
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setup
parameter_list|()
throws|throws
name|Exception
block|{
comment|// An ONManagerImpl with no store and default settings
name|onm
operator|=
operator|new
name|ONManagerImpl
argument_list|(
literal|null
argument_list|,
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
name|onm
operator|.
name|getOntologyManagerFactory
argument_list|()
operator|.
name|createOntologyManager
argument_list|(
literal|true
argument_list|)
decl_stmt|;
name|OWLDataFactory
name|df
init|=
name|mgr
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
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
name|pizzaSrc
operator|=
name|getLocalSource
argument_list|(
literal|"/ontologies/pizza.owl"
argument_list|,
name|mgr
argument_list|)
expr_stmt|;
name|colleSrc
operator|=
name|getLocalSource
argument_list|(
literal|"/ontologies/odp/collectionentity.owl"
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
name|colleSrc
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
name|ont2Src
argument_list|)
expr_stmt|;
name|space
operator|.
name|addOntology
argument_list|(
name|colleSrc
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
for|for
control|(
name|OWLOntology
name|o
range|:
name|space
operator|.
name|getOntologies
argument_list|()
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"ah "
operator|+
name|o
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
name|logicalId
operator|=
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
expr_stmt|;
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
name|testCreateSpace
parameter_list|()
throws|throws
name|Exception
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
name|pizzaSrc
argument_list|)
decl_stmt|;
name|IRI
name|logicalId
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
name|colleSrc
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
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|pizzaId
argument_list|)
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|wineId
argument_list|)
expr_stmt|;
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
name|colleSrc
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
name|colleSrc
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

