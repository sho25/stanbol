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
comment|/**  *  * @author elvio, alberto musetti  */
end_comment

begin_class
specifier|public
class|class
name|ConsistencyCheckTest
extends|extends
name|StanbolTestBase
block|{
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
name|ConsistencyCheckTest
operator|.
name|class
argument_list|)
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
name|this
operator|.
name|serverBaseUrl
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

