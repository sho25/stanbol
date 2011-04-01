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
name|assertEquals
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
name|assertNotNull
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
name|DuplicateIDException
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
name|ontology
operator|.
name|OntologyIndex
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
name|io
operator|.
name|OntologyRegistryIRISource
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
name|TestIndexing
block|{
specifier|private
specifier|static
name|ONManager
name|onm
decl_stmt|;
specifier|private
specifier|static
name|OWLOntologyManager
name|mgr
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|IRI
name|semionXmlIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://ontologydesignpatterns.org/ont/iks/oxml.owl"
argument_list|)
decl_stmt|,
name|communitiesCpIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.ontologydesignpatterns.org/cp/owl/communities.owl"
argument_list|)
decl_stmt|,
name|topicCpIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.ontologydesignpatterns.org/cp/owl/topic.owl"
argument_list|)
decl_stmt|,
name|objrole
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl"
argument_list|)
decl_stmt|,
name|scopeIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://fise.iks-project.eu/TestIndexing"
argument_list|)
decl_stmt|,
comment|// submissionsIri = IRI
comment|// .create("http://www.ontologydesignpatterns.org/registry/submissions.owl"),
name|testRegistryIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.ontologydesignpatterns.org/registry/krestest.owl"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|OntologyScope
name|scope
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
comment|// Since it is registered, this scope must be unique, or subsequent
comment|// tests will fail on duplicate ID exceptions!
name|scopeIri
operator|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://fise.iks-project.eu/TestIndexing"
argument_list|)
expr_stmt|;
name|IRI
name|coreroot
init|=
name|IRI
operator|.
name|create
argument_list|(
name|scopeIri
operator|+
literal|"/core/root.owl"
argument_list|)
decl_stmt|;
name|OWLOntology
name|oParent
init|=
literal|null
decl_stmt|;
try|try
block|{
name|oParent
operator|=
name|mgr
operator|.
name|createOntology
argument_list|(
name|coreroot
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e1
parameter_list|)
block|{
comment|// Uncomment if annotated with @BeforeClass instead of @Before
name|fail
argument_list|(
literal|"Could not create core root ontology."
argument_list|)
expr_stmt|;
block|}
comment|// The factory call also invokes loadRegistriesEager() and
comment|// gatherOntologies() so no need to test them individually.
try|try
block|{
name|scope
operator|=
name|onm
operator|.
name|getOntologyScopeFactory
argument_list|()
operator|.
name|createOntologyScope
argument_list|(
name|scopeIri
argument_list|,
operator|new
name|OntologyRegistryIRISource
argument_list|(
name|testRegistryIri
argument_list|,
name|onm
operator|.
name|getOwlCacheManager
argument_list|()
argument_list|,
name|onm
operator|.
name|getRegistryLoader
argument_list|()
argument_list|,
literal|null
comment|// new RootOntologySource(oParent)
argument_list|)
argument_list|)
expr_stmt|;
name|onm
operator|.
name|getScopeRegistry
argument_list|()
operator|.
name|registerScope
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DuplicateIDException
name|e
parameter_list|)
block|{
comment|// Uncomment if annotated with @BeforeClass instead of @Before ,
comment|// comment otherwise.
name|fail
argument_list|(
literal|"DuplicateID exception caught when creating test scope."
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
name|OntologyIndex
name|index
init|=
name|onm
operator|.
name|getOntologyIndex
argument_list|()
decl_stmt|;
try|try
block|{
name|scope
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|addOntology
argument_list|(
operator|new
name|RootOntologyIRISource
argument_list|(
name|communitiesCpIri
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|index
operator|.
name|isOntologyLoaded
argument_list|(
name|communitiesCpIri
argument_list|)
argument_list|)
expr_stmt|;
name|scope
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|addOntology
argument_list|(
operator|new
name|RootOntologyIRISource
argument_list|(
name|topicCpIri
argument_list|)
argument_list|)
expr_stmt|;
name|scope
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|removeOntology
argument_list|(
operator|new
name|RootOntologyIRISource
argument_list|(
name|communitiesCpIri
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologySpaceException
name|e1
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Unit test Failed to modify seemingly unlocked ontology scope "
operator|+
name|scope
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e1
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Unit test Failed to load ontology "
operator|+
name|communitiesCpIri
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
literal|"Unit test Failed to remove ontology "
operator|+
name|communitiesCpIri
argument_list|)
expr_stmt|;
block|}
name|assertFalse
argument_list|(
name|index
operator|.
name|isOntologyLoaded
argument_list|(
name|communitiesCpIri
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetOntology
parameter_list|()
block|{
name|OWLOntology
name|oObjRole
init|=
literal|null
decl_stmt|;
try|try
block|{
name|oObjRole
operator|=
name|mgr
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|objrole
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
literal|"Could not instantiate other ObjectRole ontology for comparison"
argument_list|)
expr_stmt|;
block|}
name|OntologyIndex
name|index
init|=
name|onm
operator|.
name|getOntologyIndex
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|index
operator|.
name|getOntology
argument_list|(
name|objrole
argument_list|)
argument_list|)
expr_stmt|;
comment|// assertSame() would fail.
name|assertEquals
argument_list|(
name|index
operator|.
name|getOntology
argument_list|(
name|objrole
argument_list|)
argument_list|,
name|oObjRole
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testIsOntologyLoaded
parameter_list|()
block|{
name|OntologyIndex
name|index
init|=
name|onm
operator|.
name|getOntologyIndex
argument_list|()
decl_stmt|;
name|IRI
name|coreroot
init|=
name|IRI
operator|.
name|create
argument_list|(
name|scopeIri
operator|+
literal|"/core/root.owl"
argument_list|)
decl_stmt|;
name|IRI
name|dne
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.ontologydesignpatterns.org/cp/owl/doesnotexist.owl"
argument_list|)
decl_stmt|;
name|IRI
name|objrole
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|index
operator|.
name|isOntologyLoaded
argument_list|(
name|coreroot
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|index
operator|.
name|isOntologyLoaded
argument_list|(
name|objrole
argument_list|)
argument_list|)
expr_stmt|;
comment|// TODO : find a way to index anonymous ontologies
name|assertTrue
argument_list|(
operator|!
name|index
operator|.
name|isOntologyLoaded
argument_list|(
name|dne
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

