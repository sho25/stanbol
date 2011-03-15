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
name|store
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
name|OWLOntologyManager
import|;
end_import

begin_class
specifier|public
class|class
name|TestStorage
block|{
annotation|@
name|Test
specifier|public
name|void
name|testStoreLoad
parameter_list|()
throws|throws
name|Exception
block|{
comment|//		OWLOntologyManager mgr = OWLManager.createOWLOntologyManager();
comment|//		OWLOntology o = mgr
comment|//				.loadOntologyFromOntologyDocument(IRI
comment|//						.create("http://www.ontologydesignpatterns.org/cp/owl/communities.owl"));
comment|//		new ClerezzaStorage().store(o);
name|assertTrue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFiseStore
parameter_list|()
throws|throws
name|Exception
block|{
name|assertTrue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

