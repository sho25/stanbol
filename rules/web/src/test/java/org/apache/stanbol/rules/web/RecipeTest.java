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
name|RecipeTest
block|{
comment|/*     public static final int __PORT = 9999;     public static final String __TEST_URI = "http://localhost:" + __PORT + "/";     public static final String _ROOT_URI = __TEST_URI + "recipe";     private static JettyServer server;     private Client client;     private WebResource webres;     private WebResource webresall;      public RecipeTest() {     }      @BeforeClass     public static void setUpClass() throws Exception {         server = new JettyServer();         server.start(__TEST_URI);     }      @AfterClass     public static void tearDownClass() throws Exception {         server.stop();     }      @Before     public void setUp() {         //RuleStore store = new KReSRuleStore();         //server.setAttribute("kresRuleStore", store);          client = Client.create(); 	webres = client.resource(_ROOT_URI);         webresall = client.resource(_ROOT_URI+"/all");                      }      @After     public void tearDown() {     }  */
comment|/**      * Test of getRecipe method, of class GetRecipe.      */
comment|/*     @Test     public void testGetRecipe() {         WebResource webresget = webres.path("http://kres.iks-project.eu/ontology/meta/rmi_config.owl#ProvaParentRecipe"); //        System.out.println(webresget);         //OWLOntology owl = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(webres.get(String.class));  //        System.out.println(webresget.get(String.class));         ClientResponse head = webresget.head();          if(head.getStatus()==200)             assertEquals(200,head.getStatus());         else             fail("Some errors occurred");     } */
comment|/**      * Test of getRecipe method, of class GetRecipe.      */
comment|/*     @Test     public void testGetAllRecipes() {  //        System.out.println(webresall); // //        System.out.println(webresall.get(String.class));         ClientResponse head = webresall.head();          if(head.getStatus()==200)             assertEquals(200,head.getStatus());         else             fail("Some errors occurred");     } */
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

