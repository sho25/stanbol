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
import|import
name|java
operator|.
name|util
operator|.
name|Properties
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
name|jarexec
operator|.
name|JarExecutor
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
name|OWLOntologyCreationException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|client
operator|.
name|Client
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|client
operator|.
name|WebResource
import|;
end_import

begin_comment
comment|/**  *  * @author elvio  */
end_comment

begin_class
specifier|public
class|class
name|ClassifyTest
block|{
specifier|public
name|ClassifyTest
parameter_list|()
block|{     }
specifier|public
specifier|static
specifier|final
name|int
name|__PORT
init|=
literal|9999
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|__JAR_FOLDER
init|=
literal|"target"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|__PROP_JAR_NAME_REGEXP
init|=
literal|"org.apache.stanbol.reasoner.*jar$"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|__TEST_URI
init|=
literal|"http://localhost:"
operator|+
name|__PORT
operator|+
literal|"/"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|__ROOT_URI
init|=
name|__TEST_URI
operator|+
literal|"classify"
decl_stmt|;
specifier|private
specifier|static
name|JarExecutor
name|server
decl_stmt|;
specifier|private
name|Client
name|client
decl_stmt|;
specifier|private
name|WebResource
name|webres
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setUpClass
parameter_list|()
throws|throws
name|Exception
block|{
name|Properties
name|properties
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|JarExecutor
operator|.
name|PROP_SERVER_PORT
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|__PORT
argument_list|)
argument_list|)
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|JarExecutor
operator|.
name|PROP_JAR_FOLDER
argument_list|,
name|__JAR_FOLDER
argument_list|)
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|JarExecutor
operator|.
name|PROP_JAR_NAME_REGEXP
argument_list|,
name|__PROP_JAR_NAME_REGEXP
argument_list|)
expr_stmt|;
name|server
operator|=
name|JarExecutor
operator|.
name|getInstance
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|server
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|setUp
parameter_list|()
block|{
comment|//RuleStore store = new KReSRuleStore();
comment|//server.setAttribute("kresRuleStore", store);
name|client
operator|=
name|Client
operator|.
name|create
argument_list|()
expr_stmt|;
name|webres
operator|=
name|client
operator|.
name|resource
argument_list|(
name|__ROOT_URI
argument_list|)
expr_stmt|;
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

