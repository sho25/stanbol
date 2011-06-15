begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * To change this template, choose Tools | Templates  * and open the template in the editor.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reasoners
operator|.
name|web
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
name|fail
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
name|commons
operator|.
name|testing
operator|.
name|stanbol
operator|.
name|StanbolTestBase
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
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
name|OWLOntologyCreationException
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
comment|/**  *  * @author elvio, alberto musetti  */
end_comment

begin_class
specifier|public
class|class
name|ClassifyTest
extends|extends
name|StanbolTestBase
block|{
specifier|static
name|boolean
name|enginesReady
decl_stmt|;
specifier|static
name|boolean
name|timedOut
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ClassifyTest
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Before
specifier|public
name|void
name|checkEnginesReady
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Check only once per test run
if|if
condition|(
name|enginesReady
condition|)
block|{
return|return;
block|}
comment|// If we timed out previously, don't waste time checking again
if|if
condition|(
name|timedOut
condition|)
block|{
name|fail
argument_list|(
literal|"Timeout in previous check of enhancement engines, cannot run tests"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|After
specifier|public
name|void
name|tearDown
parameter_list|()
block|{     }
comment|/**      * Test of getClassify method, of class Classify.      * FIXME - This test is commented bracuse it needs another service to be run. So it is classifyed as integration test.      */
annotation|@
name|Test
specifier|public
name|void
name|testOntologyClassify
parameter_list|()
throws|throws
name|OWLOntologyCreationException
block|{
comment|//        Form form = new Form();
comment|//        File inputfile = new File("./src/main/resources/TestFile/ProvaParent.owl");
comment|//        String scopeiri = "http://150.146.88.63:9090/kres/ontology/User";
comment|//        String recipeiri ="http://localhost:9999/recipe/http://kres.iks-project.eu/ontology/meta/rmi_config.owl%23ProvaParentRecipe";
comment|//
comment|//        //form.add("scope", scopeiri);
comment|//        form.add("recipe", recipeiri);
comment|//        form.add("file", inputfile);
comment|//
comment|//        ClientResponse response = webres.type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class,form);
comment|//
comment|//        System.out.println(response);
comment|//        if(response.getStatus()==200){
comment|//        OWLOntology model = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(response.getEntityInputStream());
comment|//        Iterator<OWLAxiom> ax = model.getAxioms().iterator();
comment|//        System.out.println("AXIOM COUNT:"+model.getAxiomCount());
comment|//        while(ax.hasNext())
comment|//            System.out.println(ax.next());
comment|//
comment|//            assertEquals(200,response.getStatus());
comment|//        }else
comment|//            fail("Some errors occurred");
block|}
comment|/**      * Test of getConsistencyCheckViaURL method, of class Classify.      * FIXME - This test is commented bracuse it needs another service to be run. So it is classifyed as integration test.      */
annotation|@
name|Test
specifier|public
name|void
name|testOntologyClassifyViaURL
parameter_list|()
throws|throws
name|OWLOntologyCreationException
block|{
comment|//        Form form = new Form();
comment|//        String inputiri = "http://www.ontologydesignpatterns.org/cp/owl/agentrole.owl";
comment|//        String scopeiri = "http://150.146.88.63:9090/kres/ontology/User";
comment|//        String recipeiri ="http://localhost:9999/recipe/http://kres.iks-project.eu/ontology/meta/rmi_config.owl%23ProvaParentRecipe";
comment|//
comment|//        //form.add("scope", scopeiri);
comment|//        form.add("recipe",recipeiri);
comment|//        form.add("input-graph", inputiri);
comment|//
comment|//        ClientResponse response = webres.type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class,form);
comment|//
comment|//        System.out.println(response);
comment|//        if(response.getStatus()==200){
comment|//        OWLOntology model = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(response.getEntityInputStream());
comment|//        Iterator<OWLAxiom> ax = model.getAxioms().iterator();
comment|//        System.out.println("AXIOM COUNT:"+model.getAxiomCount());
comment|//        while(ax.hasNext())
comment|//            System.out.println(ax.next());
comment|//
comment|//            assertEquals(200,response.getStatus());
comment|//        }else
comment|//            fail("Some errors occurred");
block|}
block|}
end_class

end_unit

