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

begin_comment
comment|/**  *  * @author elvio  */
end_comment

begin_class
specifier|public
class|class
name|RuleTest
block|{
comment|/*     public RuleTest() {     }      public static final int __PORT = 9999;     public static final String __TEST_URI = "http://localhost:" + __PORT + "/";     public static final String _ROOT_URI = __TEST_URI + "rule";     private static JettyServer server;         @BeforeClass     public static void setUpClass() throws Exception {         server = new JettyServer();         server.start(__TEST_URI);     }      @AfterClass     public static void tearDownClass() throws Exception {         server.stop();     }      @Before     public void setUp() {         //RuleStore store = new KReSRuleStore();         //server.setAttribute("kresRuleStore", store);      }  */
comment|/**      * Test of getRule method, of class Rule.      */
comment|/*     @Test     public void testGetRule() {         System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::");         Client client = Client.create();         WebResource webresget = client.resource(_ROOT_URI);         webresget = webresget.path("http://kres.iks-project.eu/ontology/meta/rmi_config.owl#ProvaParentRule");                  webresget.get(String.class);         ClientResponse head = webresget.head();         System.err.println("get a single rule "+head);         int status = head.getStatus();         head.close();         client.destroy();         if(status==200){             assertEquals(200,status);         }else{             fail("Some errors occurred");         }     } */
comment|/**      * Test of getRecipe method, of class GetRecipe.      */
comment|/*     @Test     public void testGetAllRules() {         System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::");         Client client = Client.create();         WebResource webresall = client.resource(_ROOT_URI + "/all");                  webresall.get(String.class);         ClientResponse head = webresall.head();         System.err.println("get all rules "+head);          int status = head.getStatus();         head.close();         client.destroy();         if(status==200){             assertEquals(200,status);         }else{             fail("Some errors occurred");         }     } */
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

