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
name|rules
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
name|fail
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
name|ClientResponse
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
name|RecipeTest
block|{
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
name|_ROOT_URI
init|=
name|__TEST_URI
operator|+
literal|"recipe"
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
specifier|private
name|WebResource
name|webresall
decl_stmt|;
specifier|public
name|RecipeTest
parameter_list|()
block|{     }
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
name|_ROOT_URI
argument_list|)
expr_stmt|;
name|webresall
operator|=
name|client
operator|.
name|resource
argument_list|(
name|_ROOT_URI
operator|+
literal|"/all"
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
comment|/**      * Test of getRecipe method, of class GetRecipe.      */
annotation|@
name|Test
specifier|public
name|void
name|testGetRecipe
parameter_list|()
block|{
name|WebResource
name|webresget
init|=
name|webres
operator|.
name|path
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/rmi_config.owl#ProvaParentRecipe"
argument_list|)
decl_stmt|;
comment|//        System.out.println(webresget);
comment|//OWLOntology owl = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(webres.get(String.class));
comment|//        System.out.println(webresget.get(String.class));
name|ClientResponse
name|head
init|=
name|webresget
operator|.
name|head
argument_list|()
decl_stmt|;
if|if
condition|(
name|head
operator|.
name|getStatus
argument_list|()
operator|==
literal|200
condition|)
name|assertEquals
argument_list|(
literal|200
argument_list|,
name|head
operator|.
name|getStatus
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|fail
argument_list|(
literal|"Some errors occurred"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Test of getRecipe method, of class GetRecipe.      */
annotation|@
name|Test
specifier|public
name|void
name|testGetAllRecipes
parameter_list|()
block|{
comment|//        System.out.println(webresall);
comment|//
comment|//        System.out.println(webresall.get(String.class));
name|ClientResponse
name|head
init|=
name|webresall
operator|.
name|head
argument_list|()
decl_stmt|;
if|if
condition|(
name|head
operator|.
name|getStatus
argument_list|()
operator|==
literal|200
condition|)
name|assertEquals
argument_list|(
literal|200
argument_list|,
name|head
operator|.
name|getStatus
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|fail
argument_list|(
literal|"Some errors occurred"
argument_list|)
expr_stmt|;
block|}
comment|//   /**
comment|//     * Test of getRecipe method, of class GetRecipe.
comment|//     */
comment|//    @Test
comment|//    public void testAddRecipe() {
comment|//
comment|//        Form form = new Form();
comment|//        form.add("recipe","http://demo/myrecipe");//"http://kres.iks-project.eu/ontology/meta/rmi_config.owl#ProvaAddRecipe");
comment|//        form.add("description","Try to add a recipe");
comment|//        ClientResponse response = webres.type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class,form);
comment|//
comment|////        System.out.println(webres);
comment|//
comment|//        if(response.getStatus()==200)
comment|//            assertEquals(200,response.getStatus());
comment|//        else
comment|//            fail("Some errors occurred");
comment|//    }
comment|//
comment|///**
comment|//     * Test of getRecipe method, of class GetRecipe.
comment|//     */
comment|//    @Test
comment|//    public void testGetRecipe_2() {
comment|//        WebResource webresget = webres.path("http://demo/myrecipe");
comment|//        System.out.println(webresget);
comment|////        //OWLOntology owl = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(webres.get(String.class));
comment|////
comment|////        System.out.println(webresget.get(String.class));
comment|//        ClientResponse head = webresget.head();
comment|//
comment|//        if(head.getStatus()==200)
comment|//            assertEquals(200,head.getStatus());
comment|//        else
comment|//            fail("Some errors occurred");
comment|//    }
comment|//
comment|//    /**
comment|//     * Test of getRecipe method, of class GetRecipe.
comment|//     */
comment|//    @Test
comment|//    public void testGetAllRecipes_2() {
comment|//
comment|////        System.out.println(webresall);
comment|////
comment|////        System.out.println(webresall.get(String.class));
comment|//        ClientResponse head = webresall.head();
comment|//
comment|//        if(head.getStatus()==200)
comment|//            assertEquals(200,head.getStatus());
comment|//        else
comment|//            fail("Some errors occurred");
comment|//    }
comment|//
comment|//    /**
comment|//     * Test of getRecipe method, of class GetRecipe.
comment|//     */
comment|//    @Test
comment|//    public void testDeleteRecipe() {
comment|//
comment|//        String recipe = "http://demo/myrecipe";//http://kres.iks-project.eu/ontology/meta/rmi_config.owl#ProvaAddRecipe";
comment|//
comment|//        WebResource webresdel = webres.queryParam("recipe", recipe);
comment|//
comment|//        webresdel.delete();
comment|//
comment|//        ClientResponse response = webresdel.head();
comment|//        int status = response.getStatus();
comment|////        System.out.println(response);
comment|//        if((status==200)||status == 405)
comment|//            assertTrue(true);
comment|//        else
comment|//            fail("Some errors occurred");
comment|//    }
block|}
end_class

end_unit

