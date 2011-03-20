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
name|ClientResponse
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
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
name|representation
operator|.
name|Form
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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|kres
operator|.
name|jersey
operator|.
name|JettyServer
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
name|AfterClass
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

begin_comment
comment|/**  *  * @author elvio  */
end_comment

begin_class
specifier|public
class|class
name|ConsistencyCheckTest
block|{
specifier|public
name|ConsistencyCheckTest
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
literal|"check-consistency"
decl_stmt|;
specifier|private
specifier|static
name|JettyServer
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
name|server
operator|=
operator|new
name|JettyServer
argument_list|()
expr_stmt|;
name|server
operator|.
name|start
argument_list|(
name|__TEST_URI
argument_list|)
expr_stmt|;
block|}
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|tearDownClass
parameter_list|()
throws|throws
name|Exception
block|{
name|server
operator|.
name|stop
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
comment|/**      * Test of GetSimpleConsistencyCheck method, of class ConsistencyCheck.      *      */
annotation|@
name|Test
specifier|public
name|void
name|testGetSimpleConsistencyCheck
parameter_list|()
block|{
comment|//         WebResource webresget = webres.path("http://www.ontologydesignpatterns.org/cp/owl/agentrole.owl");//"http://www.loa-cnr.it/ontologies/DUL.owl");//"http://150.146.88.63:9090/kres/ontology/User");
comment|//
comment|//        System.out.println(webresget.get(String.class));
comment|//        ClientResponse head = webresget.head();
comment|//        System.out.println(head);
comment|//        if(head.getStatus()==200)
comment|//            assertEquals(200,head.getStatus());
comment|//        else
comment|//            fail("Some errors occurred");
block|}
comment|/**      * Test of getConsistencyCheck method, of class ConsistencyCheck.      * FIXME - This test is commented bracuse it needs another service to be run. So it is classifyed as integration test.      */
annotation|@
name|Test
specifier|public
name|void
name|testGetConsistencyCheck
parameter_list|()
block|{
comment|//        Form form = new Form();
comment|//        File inputfile = new File("./src/main/resources/TestFile/ProvaParent.owl");
comment|//        String scopeiri = "http://150.146.88.63:9090/kres/ontology/User";
comment|//        String recipeiri ="http://localhost:9999/recipe/http://kres.iks-project.eu/ontology/meta/rmi_config.owl%23ProvaParentRecipe";
comment|//
comment|//        //form.add("scope", scopeiri);
comment|//        form.add("recipe",recipeiri);
comment|//        form.add("file", inputfile);
comment|//
comment|//        ClientResponse response = webres.type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class,form);
comment|//        System.out.println(response);
comment|//        if(response.getStatus()==200)
comment|//            assertEquals(200,response.getStatus());
comment|//        else
comment|//            fail("Some errors occurred");
block|}
comment|/**      * Test of getConsistencyCheckViaURL method, of class ConsistencyCheck.      * FIXME - This test is commented bracuse it needs another service to be run. So it is classifyed as integration test.      */
annotation|@
name|Test
specifier|public
name|void
name|testGetConsistencyCheckViaURL
parameter_list|()
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
comment|//        System.out.println(response);
comment|//        if(response.getStatus()==200)
comment|//            assertEquals(200,response.getStatus());
comment|//        else
comment|//            fail("Some errors occurred");
block|}
annotation|@
name|Test
specifier|public
name|void
name|testCONFLICT
parameter_list|()
block|{
comment|//        Form form = new Form();
comment|//        File inputfile = new File("./src/main/resources/TestFile/ProvaParent.owl");
comment|//        String inputiri = "http://www.loa-cnr.it/ontologies/DUL.owl";
comment|//        String scopeiri = "http://150.146.88.63:9090/kres/ontology/User";
comment|//        String recipeiri ="http://localhost:9999/recipe/http://kres.iks-project.eu/ontology/meta/rmi_config.owl%23ProvaParentRecipe";
comment|//
comment|//        //form.add("scope", scopeiri);
comment|//        form.add("recipe",recipeiri);
comment|//        form.add("input-graph", inputiri);
comment|//        form.add("file", inputfile);
comment|//
comment|//        ClientResponse response = webres.type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class,form);
comment|//        System.out.println(response);
comment|//        if(response.getStatus()==409)
comment|//            assertEquals(409,response.getStatus());
comment|//        else
comment|//            fail("Some errors occurred");
block|}
comment|//    @Test
comment|//    public void testCONECTION_OWL_LINK(){
comment|//
comment|//        Form form = new Form();
comment|//        File inputfile = new File("./src/main/resources/TestFile/ProvaParent.owl");
comment|//        String reasonerurl = "http://150.146.88.63:9090/kres/ontology/User";
comment|//        String scopeiri = "http://150.146.88.63:9090/kres/ontology/User";
comment|//        String recipeiri ="http://localhost:9999/recipe/http://kres.iks-project.eu/ontology/meta/rmi_config.owl%23ProvaParentRecipe";
comment|//
comment|//        form.add("scope", scopeiri);
comment|//        form.add("recipe",recipeiri);
comment|//        form.add("owllink-endpoint", reasonerurl);
comment|//        form.add("file", inputfile);
comment|//
comment|//        ClientResponse response = webres.type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class,form);
comment|//        System.out.println(response);
comment|//        if(response.getStatus()==200)
comment|//            assertEquals(200,response.getStatus());
comment|//        else
comment|//            fail("Some errors occurred");
comment|//
comment|//    }
block|}
end_class

end_unit

