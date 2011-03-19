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
name|registry
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
name|model
operator|.
name|IRI
import|;
end_import

begin_class
specifier|public
class|class
name|TestRegistry
block|{
specifier|private
specifier|static
name|KReSONManager
name|onm
decl_stmt|;
specifier|private
specifier|static
name|IRI
name|testRegistryIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.ontologydesignpatterns.org/registry/krestest.owl"
argument_list|)
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|private
specifier|static
name|IRI
name|submissionsIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.ontologydesignpatterns.org/registry/submissions.owl"
argument_list|)
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
block|}
annotation|@
name|Test
specifier|public
name|void
name|testAddRegistryToSessionSpace
parameter_list|()
block|{
name|IRI
name|scopeIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://fise.iks-project.eu/scopone"
argument_list|)
decl_stmt|;
name|SessionOntologySpace
name|space
init|=
literal|null
decl_stmt|;
name|space
operator|=
name|onm
operator|.
name|getOntologySpaceFactory
argument_list|()
operator|.
name|createSessionOntologySpace
argument_list|(
name|scopeIri
argument_list|)
expr_stmt|;
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
literal|"Adding libraries to session space failed. "
operator|+
literal|"This should not happen for active session spaces."
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|space
operator|.
name|getTopOntology
argument_list|()
operator|!=
literal|null
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|space
operator|.
name|getOntologies
argument_list|()
operator|.
name|contains
argument_list|(
name|space
operator|.
name|getTopOntology
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testScopeCreationWithRegistry
parameter_list|()
block|{
name|IRI
name|scopeIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://fise.iks-project.eu/scopone"
argument_list|)
decl_stmt|;
name|OntologyScope
name|scope
init|=
literal|null
decl_stmt|;
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
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DuplicateIDException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"DuplicateID exception caught when creating test scope."
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|scope
operator|!=
literal|null
operator|&&
name|scope
operator|.
name|getCoreSpace
argument_list|()
operator|.
name|getTopOntology
argument_list|()
operator|!=
literal|null
argument_list|)
expr_stmt|;
comment|// OntologyUtils.printOntology(scope.getCoreSpace().getTopOntology(),
comment|// System.err);
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSpaceCreationWithRegistry
parameter_list|()
block|{
name|IRI
name|scopeIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://fise.iks-project.eu/scopone"
argument_list|)
decl_stmt|;
name|CoreOntologySpace
name|space
init|=
literal|null
decl_stmt|;
comment|// The factory call also invokes loadRegistriesEager() and
comment|// gatherOntologies() so no need to test them individually.
name|space
operator|=
name|onm
operator|.
name|getOntologySpaceFactory
argument_list|()
operator|.
name|createCoreOntologySpace
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
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|space
operator|!=
literal|null
operator|&&
name|space
operator|.
name|getTopOntology
argument_list|()
operator|!=
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

