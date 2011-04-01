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
name|RuleTest
block|{
specifier|public
name|RuleTest
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
name|_ROOT_URI
init|=
name|__TEST_URI
operator|+
literal|"rule"
decl_stmt|;
specifier|private
specifier|static
name|JettyServer
name|server
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
block|}
comment|/**      * Test of getRule method, of class Rule.      */
annotation|@
name|Test
specifier|public
name|void
name|testGetRule
parameter_list|()
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"::::::::::::::::::::::::::::::::::::::::::::::::"
argument_list|)
expr_stmt|;
name|Client
name|client
init|=
name|Client
operator|.
name|create
argument_list|()
decl_stmt|;
name|WebResource
name|webresget
init|=
name|client
operator|.
name|resource
argument_list|(
name|_ROOT_URI
argument_list|)
decl_stmt|;
name|webresget
operator|=
name|webresget
operator|.
name|path
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/rmi_config.owl#ProvaParentRule"
argument_list|)
expr_stmt|;
name|webresget
operator|.
name|get
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
name|ClientResponse
name|head
init|=
name|webresget
operator|.
name|head
argument_list|()
decl_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"get a single rule "
operator|+
name|head
argument_list|)
expr_stmt|;
name|int
name|status
init|=
name|head
operator|.
name|getStatus
argument_list|()
decl_stmt|;
name|head
operator|.
name|close
argument_list|()
expr_stmt|;
name|client
operator|.
name|destroy
argument_list|()
expr_stmt|;
if|if
condition|(
name|status
operator|==
literal|200
condition|)
block|{
name|assertEquals
argument_list|(
literal|200
argument_list|,
name|status
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Some errors occurred"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Test of getRecipe method, of class GetRecipe.      */
annotation|@
name|Test
specifier|public
name|void
name|testGetAllRules
parameter_list|()
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"::::::::::::::::::::::::::::::::::::::::::::::::"
argument_list|)
expr_stmt|;
name|Client
name|client
init|=
name|Client
operator|.
name|create
argument_list|()
decl_stmt|;
name|WebResource
name|webresall
init|=
name|client
operator|.
name|resource
argument_list|(
name|_ROOT_URI
operator|+
literal|"/all"
argument_list|)
decl_stmt|;
name|webresall
operator|.
name|get
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
name|ClientResponse
name|head
init|=
name|webresall
operator|.
name|head
argument_list|()
decl_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"get all rules "
operator|+
name|head
argument_list|)
expr_stmt|;
name|int
name|status
init|=
name|head
operator|.
name|getStatus
argument_list|()
decl_stmt|;
name|head
operator|.
name|close
argument_list|()
expr_stmt|;
name|client
operator|.
name|destroy
argument_list|()
expr_stmt|;
if|if
condition|(
name|status
operator|==
literal|200
condition|)
block|{
name|assertEquals
argument_list|(
literal|200
argument_list|,
name|status
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Some errors occurred"
argument_list|)
expr_stmt|;
block|}
block|}
comment|//     /**
comment|//     * Test of addRuleToRecipe method, of class Rule.
comment|//     */
comment|//    @Test
comment|//    public void testAddRuleToRecipe_2() {
comment|//        System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::");
comment|//        Client client = Client.create();
comment|//        WebResource webrespath_2 = client.resource(_ROOT_URI);
comment|//        Form form_2 = new Form();
comment|//        String recipenew = "http://kres.iks-project.eu/ontology/meta/rmi_config.owl#ProvaParentRecipe";
comment|//        String rulenew = "http://kres.iks-project.eu/ontology/meta/rmi_config.owl#MyRuleA";
comment|//
comment|//        form_2.add("recipe",recipenew);
comment|//        form_2.add("rule",rulenew);
comment|//
comment|//        ClientResponse response = webrespath_2.type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class,form_2);
comment|//
comment|//        System.err.println("add exist rule "+response);
comment|//
comment|//        int status = response.getStatus();
comment|//        form_2.remove("recipe");
comment|//        form_2.remove("rule");
comment|//        form_2.clear();
comment|//        response.close();
comment|//        client.destroy();
comment|//        if(status==200)
comment|//            assertEquals(200,status);
comment|//        else
comment|//            fail("Some errors occurred");
comment|//    }
comment|//
comment|// /**
comment|//    * Test of addRuleToRecipe method, of class Rule.
comment|//     */
comment|//    @Test
comment|//    public void testAddRuleToRecipe() {
comment|//        System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::");
comment|//        Client client = Client.create();
comment|//        WebResource webrespath_1 = client.resource(_ROOT_URI);
comment|//        Form form = new Form();
comment|//        String recipe = "http://kres.iks-project.eu/ontology/meta/rmi_config.owl#ProvaParentRecipe";
comment|//        String rule = "http://kres.iks-project.eu/ontology/meta/rmi_config.owl#ProvaParentNewRule";
comment|//        String kres_syntax = "Body -> Head";
comment|//        String description = "Prova aggiunta regola";
comment|//        form.add("recipe",recipe);
comment|//        form.add("rule",rule);
comment|//        form.add("kres-syntax",kres_syntax);
comment|//        form.add("description",description);
comment|//
comment|//        ClientResponse response = webrespath_1.type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class,form);
comment|//
comment|//        System.err.println("add a new rule "+response);
comment|//        int status = response.getStatus();
comment|//        form.remove("recipe");
comment|//        form.remove("rule");
comment|//        form.remove("kres-syntax");
comment|//        form.remove("description");
comment|//        form.clear();
comment|//        response.close();
comment|//        client.destroy();
comment|//        if(status==200)
comment|//            assertEquals(200,status);
comment|//        else
comment|//            fail("Some errors occurred");
comment|//
comment|//
comment|//    }
comment|//
comment|//    /**
comment|//     * Test of removeRule method, of class Rule.
comment|//     */
comment|//    @Test
comment|//    public void testRemoveRule() {
comment|//        System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::");
comment|//        Client client = Client.create();
comment|//        WebResource webresdel = client.resource(_ROOT_URI);
comment|//        String recipedel = "http://kres.iks-project.eu/ontology/meta/rmi_config.owl#ProvaParentRecipe";
comment|//        String ruledel = "http://kres.iks-project.eu/ontology/meta/rmi_config.owl#MyRuleA";
comment|//
comment|//       webresdel = webresdel.queryParam("recipe", recipedel).queryParam("rule", ruledel);
comment|//
comment|//       webresdel.delete();
comment|//
comment|//        ClientResponse response = webresdel.head();
comment|//        System.err.println("removeRule "+response);
comment|//        int status = response.getStatus();
comment|//        response.close();
comment|//        client.destroy();
comment|//        if((status==200)||status == 405)
comment|//            assertTrue(true);
comment|//        else
comment|//            fail("Some errors occurred");
comment|//    }
comment|//
comment|//     /**
comment|//     * Test of removeRule method, of class Rule.
comment|//     */
comment|//    @Test
comment|//    public void testRemoveRule_2() {
comment|//        System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::");
comment|//        Client client = Client.create();
comment|//        WebResource webresdel = client.resource(_ROOT_URI);
comment|//        String recipedel = "http://kres.iks-project.eu/ontology/meta/rmi_config.owl#ProvaParentRecipe";
comment|//        String ruledel = "http://kres.iks-project.eu/ontology/meta/rmi_config.owl#ProvaParentNewRule";
comment|//        webresdel = webresdel.queryParam("recipe", recipedel).queryParam("rule", ruledel);
comment|//
comment|//       webresdel.delete();
comment|//
comment|//        ClientResponse response = webresdel.head();
comment|//        System.err.println("removeRule "+response);
comment|//        int status = response.getStatus();
comment|//        response.close();
comment|//        client.destroy();
comment|//        if((status==200)||status == 405)
comment|//            assertTrue(true);
comment|//        else
comment|//            fail("Some errors occurred");
comment|//    }
comment|//
comment|//     /**
comment|//     * Test of removeRule method, of class Rule.
comment|//     */
comment|//    @Test
comment|//    public void testRemoveSingleRule() {
comment|//        System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::");
comment|//        Client client = Client.create();
comment|//        WebResource webresdel = client.resource(_ROOT_URI);
comment|//        String ruledel = "http://kres.iks-project.eu/ontology/meta/rmi_config.owl#ProvaParentNewRule";
comment|//        webresdel = webresdel.queryParam("rule", ruledel);
comment|//
comment|//       webresdel.delete();
comment|//
comment|//        ClientResponse response = webresdel.head();
comment|//        System.err.println("removeRule "+response);
comment|//        int status = response.getStatus();
comment|//        response.close();
comment|//        client.destroy();
comment|//        if((status==200)||status == 405)
comment|//            assertTrue(true);
comment|//        else
comment|//            fail("Some errors occurred");
comment|//    }
block|}
end_class

end_unit

