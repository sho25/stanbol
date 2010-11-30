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
name|StructureTest
block|{
specifier|private
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
decl_stmt|;
specifier|private
specifier|static
name|OWLOntologyManager
name|ontMgr
init|=
literal|null
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setUp
parameter_list|()
block|{
try|try
block|{
comment|//new Activator().start(null);
name|ontMgr
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Bundle activator could not be started"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testOWLManagerCreation
parameter_list|()
block|{
name|assertNotNull
argument_list|(
name|ontMgr
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testOntologyCreation
parameter_list|()
block|{
try|try
block|{
name|assertNotNull
argument_list|(
name|ontMgr
operator|.
name|createOntology
argument_list|(
name|baseIri
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
literal|"An empty ontology manager failed to create ontology with base IRI "
operator|+
name|baseIri
operator|+
literal|" !"
argument_list|)
expr_stmt|;
block|}
block|}
comment|// @Test
comment|// public void testReasoner() {
comment|// OWLOntology ont = null;
comment|// ;
comment|// try {
comment|// ont = ontMgr.createOntology(baseIri);
comment|// } catch (OWLOntologyCreationException e) {
comment|// fail("Could not create ontology with base IRI " + Constants.base);
comment|// }
comment|// OWLReasoner reasoner = ManagerContext.get().getReasonerFactory()
comment|// .createReasoner(ont);
comment|// assertNotNull(reasoner.getRootOntology());
comment|// assertTrue(true);
comment|// }
block|}
end_class

end_unit

