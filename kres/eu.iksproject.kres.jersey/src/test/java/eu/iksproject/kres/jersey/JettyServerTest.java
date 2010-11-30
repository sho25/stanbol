begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|jersey
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
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|WebApplicationException
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
name|representation
operator|.
name|Form
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|format
operator|.
name|KReSFormat
import|;
end_import

begin_class
specifier|public
class|class
name|JettyServerTest
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
literal|"ontology"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ONT_FOAF_URI
init|=
literal|"http://xmlns.com/foaf/spec/index.rdf"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ONT_PIZZA_URI
init|=
literal|"http://www.co-ode.org/ontologies/pizza/2007/02/12/pizza.owl"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ONT_WINE_URI
init|=
literal|"http://www.schemaweb.info/webservices/rest/GetRDFByID.aspx?id=62"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|REG_TEST_URI
init|=
literal|"http://www.ontologydesignpatterns.org/registry/krestest.owl"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SCOPE_BIZ_URI
init|=
name|_ROOT_URI
operator|+
literal|"/"
operator|+
literal|"Biz"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SCOPE_DRUNK_URI
init|=
name|_ROOT_URI
operator|+
literal|"/"
operator|+
literal|"Drunk"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SCOPE_USER_URI
init|=
name|_ROOT_URI
operator|+
literal|"/"
operator|+
literal|"User"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SCOPE1_URI
init|=
name|_ROOT_URI
operator|+
literal|"/"
operator|+
literal|"Pippo%20Baudo"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SCOPE2_URI
init|=
name|_ROOT_URI
operator|+
literal|"/"
operator|+
literal|"TestScope2"
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
name|startServer
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
name|stopServer
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
specifier|private
name|Client
name|client
decl_stmt|;
specifier|private
name|WebResource
name|ontologyResource
decl_stmt|,
name|scopeResourceTest1
decl_stmt|,
name|scopeResourceTest2
decl_stmt|;
annotation|@
name|Before
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
comment|// simulate OSGi runtime by registering the components to test manually
name|server
operator|.
name|setAttribute
argument_list|(
literal|""
argument_list|,
literal|null
argument_list|)
expr_stmt|;
comment|// Serializer serializer = new Serializer();
comment|// serializer.bindSerializingProvider(new JenaSerializerProvider());
comment|// serializer.bindSerializingProvider(new RdfJsonSerializingProvider());
comment|// server.setAttribute(Serializer.class.getName(), serializer);
comment|//
comment|// TcManager tcManager = new TcManager();
comment|// server.setAttribute(TcManager.class.getName(), tcManager);
comment|// server.setAttribute(
comment|// FreemarkerViewProcessor.FREEMARKER_TEMPLATE_PATH_INIT_PARAM,
comment|// "/META-INF/templates");
name|client
operator|=
name|Client
operator|.
name|create
argument_list|()
expr_stmt|;
name|ontologyResource
operator|=
name|client
operator|.
name|resource
argument_list|(
name|_ROOT_URI
argument_list|)
expr_stmt|;
name|scopeResourceTest1
operator|=
name|client
operator|.
name|resource
argument_list|(
name|SCOPE1_URI
argument_list|)
expr_stmt|;
name|scopeResourceTest2
operator|=
name|client
operator|.
name|resource
argument_list|(
name|SCOPE2_URI
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Tests that the creation of active and inactive scopes is reflected in the 	 * RDF version of the scope set, whether it is set to display all scopes or 	 * only the active ones. 	 *  	 * @throws Exception 	 */
comment|//	@Test
specifier|public
name|void
name|testActiveVsAll
parameter_list|()
throws|throws
name|Exception
block|{
comment|// The needed Web resources to GET from.
name|WebResource
name|resActive
init|=
name|client
operator|.
name|resource
argument_list|(
name|_ROOT_URI
argument_list|)
decl_stmt|;
name|WebResource
name|resAllScopes
init|=
name|client
operator|.
name|resource
argument_list|(
name|_ROOT_URI
operator|+
literal|"?with-inactive=true"
argument_list|)
decl_stmt|;
comment|// Put a simple, inactive scope.
name|client
operator|.
name|resource
argument_list|(
name|SCOPE_USER_URI
operator|+
literal|"?coreont="
operator|+
name|ONT_FOAF_URI
argument_list|)
operator|.
name|put
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
comment|// Check that it is in the list of all scopes.
name|String
name|r
init|=
name|resAllScopes
operator|.
name|get
argument_list|(
name|String
operator|.
name|class
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|r
operator|.
name|contains
argument_list|(
name|SCOPE_USER_URI
argument_list|)
argument_list|)
expr_stmt|;
comment|// Check that it is not in the list of active scopes.
name|r
operator|=
name|resActive
operator|.
name|get
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|!
name|r
operator|.
name|contains
argument_list|(
name|SCOPE_USER_URI
argument_list|)
argument_list|)
expr_stmt|;
comment|// Now create a scope that is active on startup.
name|client
operator|.
name|resource
argument_list|(
name|SCOPE_BIZ_URI
operator|+
literal|"?coreont="
operator|+
name|ONT_PIZZA_URI
operator|+
literal|"&activate=true"
argument_list|)
operator|.
name|put
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
comment|// Check that it appears in both sets.
name|r
operator|=
name|resAllScopes
operator|.
name|get
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|r
operator|.
name|contains
argument_list|(
name|SCOPE_BIZ_URI
argument_list|)
argument_list|)
expr_stmt|;
name|r
operator|=
name|resActive
operator|.
name|get
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|r
operator|.
name|contains
argument_list|(
name|SCOPE_BIZ_URI
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetScopes
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|r
init|=
name|ontologyResource
operator|.
name|accept
argument_list|(
name|KReSFormat
operator|.
name|RDF_XML
argument_list|)
operator|.
name|get
argument_list|(
name|String
operator|.
name|class
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|r
operator|.
name|contains
argument_list|(
literal|"<imports rdf:resource=\"http://www.ontologydesignpatterns.org/schemas/meta.owl\"/>"
argument_list|)
argument_list|)
expr_stmt|;
name|r
operator|=
name|ontologyResource
operator|.
name|accept
argument_list|(
name|KReSFormat
operator|.
name|TURTLE
argument_list|)
operator|.
name|get
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|r
operator|.
name|contains
argument_list|(
literal|"[ owl:imports<http://www.ontologydesignpatterns.org/schemas/meta.owl>\n] ."
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testRemoval
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|wineId
init|=
literal|"http://www.w3.org/TR/2003/WD-owl-guide-20030331/wine"
decl_stmt|;
name|client
operator|.
name|resource
argument_list|(
name|SCOPE_DRUNK_URI
operator|+
literal|"?corereg="
operator|+
name|REG_TEST_URI
argument_list|)
operator|.
name|put
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
comment|// // Request entities in DELETE methods are unsupported...
comment|// Form f = new Form();
comment|// f.add("ontology",
comment|// "http://www.w3.org/TR/2003/WD-owl-guide-20030331/wine");
comment|// drunkRes.delete(f);
name|client
operator|.
name|resource
argument_list|(
name|SCOPE_DRUNK_URI
operator|+
literal|"?ontology="
operator|+
name|wineId
argument_list|)
operator|.
name|delete
argument_list|()
expr_stmt|;
name|assertTrue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|//	@Test
specifier|public
name|void
name|testLocking
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Create a scope with a core ontology and a custom registry.
name|String
name|r
decl_stmt|;
comment|// String PIZZA_URI =
comment|// "http://www.co-ode.org/ontologies/pizza/2007/02/12/pizza.owl";
name|WebResource
name|ts2res
init|=
name|client
operator|.
name|resource
argument_list|(
name|SCOPE2_URI
operator|+
literal|"?customont="
operator|+
name|ONT_PIZZA_URI
operator|+
literal|"&corereg="
operator|+
name|REG_TEST_URI
argument_list|)
decl_stmt|;
name|ts2res
operator|.
name|put
argument_list|()
expr_stmt|;
name|r
operator|=
name|scopeResourceTest2
operator|.
name|accept
argument_list|(
name|KReSFormat
operator|.
name|RDF_XML
argument_list|)
operator|.
name|get
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
comment|// System.err.println(r);
comment|// Now add an ontology
try|try
block|{
name|Form
name|f
init|=
operator|new
name|Form
argument_list|()
decl_stmt|;
name|f
operator|.
name|add
argument_list|(
literal|"location"
argument_list|,
name|ONT_PIZZA_URI
argument_list|)
expr_stmt|;
name|f
operator|.
name|add
argument_list|(
literal|"registry"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|scopeResourceTest2
operator|.
name|post
argument_list|(
name|String
operator|.
name|class
argument_list|,
name|f
argument_list|)
expr_stmt|;
name|r
operator|=
name|scopeResourceTest2
operator|.
name|accept
argument_list|(
name|KReSFormat
operator|.
name|RDF_XML
argument_list|)
operator|.
name|get
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
comment|// fail("Addition succeded on existing scope with supposedly locked core space!");
block|}
catch|catch
parameter_list|(
name|WebApplicationException
name|ex
parameter_list|)
block|{
name|assertTrue
argument_list|(
name|r
operator|!=
literal|null
argument_list|)
expr_stmt|;
return|return;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|assertTrue
argument_list|(
name|r
operator|!=
literal|null
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
comment|//	@Test
specifier|public
name|void
name|testSessionCreation
parameter_list|()
block|{
name|WebResource
name|resource
init|=
name|client
operator|.
name|resource
argument_list|(
name|__TEST_URI
operator|+
literal|"session"
argument_list|)
decl_stmt|;
name|String
name|r
init|=
name|resource
operator|.
name|accept
argument_list|(
name|KReSFormat
operator|.
name|RDF_XML
argument_list|)
operator|.
name|post
argument_list|(
name|String
operator|.
name|class
argument_list|)
decl_stmt|;
comment|//System.err.println(r);
name|assertTrue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|//	@Test
specifier|public
name|void
name|testScopeManagement
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|rootIdToken
init|=
literal|"rdf:about=\"http://localhost:9999/ontology/Pippo%20Baudo/custom/root.owl\""
decl_stmt|;
name|String
name|importORToken
init|=
literal|"<owl:imports rdf:resource=\"http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl\"/>"
decl_stmt|;
comment|// Create the new scope with a supplied ontology registry.
name|String
name|r
init|=
name|client
operator|.
name|resource
argument_list|(
name|SCOPE1_URI
operator|+
literal|"?coreont=http://xmlns.com/foaf/spec/index.rdf"
comment|// + "?corereg="+REG_TEST_URI
comment|// + "&customreg="+REG_TEST_URI
operator|+
literal|"&activate=true"
argument_list|)
operator|.
name|put
argument_list|(
name|String
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// Check that it appears in the scope set
name|r
operator|=
name|ontologyResource
operator|.
name|accept
argument_list|(
name|KReSFormat
operator|.
name|RDF_XML
argument_list|)
operator|.
name|get
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
comment|// System.err.println(r);
name|assertTrue
argument_list|(
name|r
operator|.
name|contains
argument_list|(
name|SCOPE1_URI
argument_list|)
operator|&&
name|r
operator|.
name|contains
argument_list|(
literal|"rdf:type rdf:resource=\"http://kres.iks-project.eu/ontology/onm/meta.owl#Scope\""
argument_list|)
argument_list|)
expr_stmt|;
comment|// Check that the top ontology has the correct ID and imports objectrole
name|r
operator|=
name|scopeResourceTest1
operator|.
name|accept
argument_list|(
name|KReSFormat
operator|.
name|RDF_XML
argument_list|)
operator|.
name|get
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
comment|//System.err.println(r);
name|assertTrue
argument_list|(
name|r
operator|.
name|contains
argument_list|(
name|rootIdToken
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|r
operator|.
name|contains
argument_list|(
literal|"http://xmlns.com/foaf/spec/index.rdf"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Now add an ontology
name|Form
name|f
init|=
operator|new
name|Form
argument_list|()
decl_stmt|;
name|f
operator|.
name|add
argument_list|(
literal|"location"
argument_list|,
literal|"http://www.co-ode.org/ontologies/pizza/2007/02/12/pizza.owl"
argument_list|)
expr_stmt|;
name|f
operator|.
name|add
argument_list|(
literal|"registry"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|scopeResourceTest1
operator|.
name|post
argument_list|(
name|String
operator|.
name|class
argument_list|,
name|f
argument_list|)
expr_stmt|;
name|r
operator|=
name|scopeResourceTest1
operator|.
name|accept
argument_list|(
name|KReSFormat
operator|.
name|RDF_XML
argument_list|)
operator|.
name|get
argument_list|(
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
comment|// System.err.println(r);
block|}
block|}
end_class

end_unit

