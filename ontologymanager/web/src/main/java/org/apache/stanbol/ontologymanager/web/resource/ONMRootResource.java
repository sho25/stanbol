begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|web
operator|.
name|resource
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletContext
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
name|DELETE
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
name|DefaultValue
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
name|GET
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
name|Path
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
name|PathParam
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
name|Produces
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
name|QueryParam
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
name|Context
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
name|HttpHeaders
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
name|Response
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|access
operator|.
name|TcManager
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ONManager
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|OntologyScope
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|ScopeRegistry
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|impl
operator|.
name|ONManagerImpl
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|impl
operator|.
name|io
operator|.
name|ClerezzaOntologyStorage
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|impl
operator|.
name|renderers
operator|.
name|ScopeSetRenderer
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
name|OWLOntology
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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
import|;
end_import

begin_comment
comment|/**  * The main Web resource of the KReS ontology manager. All the scopes, sessions and ontologies are accessible  * as subresources of ONMRootResource.<br>  *<br>  * This resource allows a GET method for obtaining an RDF representation of the set of registered scopes and a  * DELETE method for clearing the scope set and ontology store accordingly.  *   * @author alessandro  *   */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/ontology"
argument_list|)
specifier|public
class|class
name|ONMRootResource
extends|extends
name|NavigationMixin
block|{
specifier|private
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
comment|/*      * Placeholder for the ONManager to be fetched from the servlet context.      */
specifier|protected
name|ONManager
name|onm
decl_stmt|;
specifier|protected
name|ClerezzaOntologyStorage
name|storage
decl_stmt|;
specifier|protected
name|ServletContext
name|servletContext
decl_stmt|;
specifier|public
name|ONMRootResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|this
operator|.
name|servletContext
operator|=
name|servletContext
expr_stmt|;
name|this
operator|.
name|onm
operator|=
operator|(
name|ONManager
operator|)
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|ONManager
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
comment|//      this.storage = (OntologyStorage) servletContext
comment|//      .getAttribute(OntologyStorage.class.getName());
comment|// Contingency code for missing components follows.
comment|/*  * FIXME! The following code is required only for the tests. This should  * be removed and the test should work without this code.  */
if|if
condition|(
name|onm
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"No KReSONManager in servlet context. Instantiating manually..."
argument_list|)
expr_stmt|;
name|onm
operator|=
operator|new
name|ONManagerImpl
argument_list|(
operator|new
name|TcManager
argument_list|()
argument_list|,
literal|null
argument_list|,
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|storage
operator|=
name|onm
operator|.
name|getOntologyStore
argument_list|()
expr_stmt|;
if|if
condition|(
name|storage
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"No OntologyStorage in servlet context. Instantiating manually..."
argument_list|)
expr_stmt|;
name|storage
operator|=
operator|new
name|ClerezzaOntologyStorage
argument_list|(
operator|new
name|TcManager
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * RESTful DELETE method that clears the entire scope registry and managed ontology store.      */
annotation|@
name|DELETE
specifier|public
name|void
name|clearOntologies
parameter_list|()
block|{
comment|// First clear the registry...
name|ScopeRegistry
name|reg
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
for|for
control|(
name|OntologyScope
name|scope
range|:
name|reg
operator|.
name|getRegisteredScopes
argument_list|()
control|)
name|reg
operator|.
name|deregisterScope
argument_list|(
name|scope
argument_list|)
expr_stmt|;
comment|// ...then clear the store.
comment|// TODO : the other way around?
name|onm
operator|.
name|getOntologyStore
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
literal|"/{param:.+}"
argument_list|)
specifier|public
name|Response
name|echo
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"param"
argument_list|)
name|String
name|s
parameter_list|)
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
name|s
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|/**      * Default GET method for obtaining the set of (both active and, optionally, inactive) ontology scopes      * currently registered with this instance of KReS.      *       * @param inactive      *            if true, both active and inactive scopes will be included. Default is false.      * @param headers      *            the HTTP headers, supplied by the REST call.      * @param servletContext      *            the servlet context, supplied by the REST call.      * @return a string representation of the requested scope set, in a format acceptable by the client.      */
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|value
operator|=
block|{
name|KRFormat
operator|.
name|RDF_XML
block|,
name|KRFormat
operator|.
name|OWL_XML
block|,
name|KRFormat
operator|.
name|TURTLE
block|,
name|KRFormat
operator|.
name|FUNCTIONAL_OWL
block|,
name|KRFormat
operator|.
name|MANCHESTER_OWL
block|,
name|KRFormat
operator|.
name|RDF_JSON
block|}
argument_list|)
specifier|public
name|Response
name|getScopes
parameter_list|(
annotation|@
name|DefaultValue
argument_list|(
literal|"false"
argument_list|)
annotation|@
name|QueryParam
argument_list|(
literal|"with-inactive"
argument_list|)
name|boolean
name|inactive
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|,
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|ScopeRegistry
name|reg
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|OntologyScope
argument_list|>
name|scopes
init|=
name|inactive
condition|?
name|reg
operator|.
name|getRegisteredScopes
argument_list|()
else|:
name|reg
operator|.
name|getActiveScopes
argument_list|()
decl_stmt|;
name|OWLOntology
name|ontology
init|=
name|ScopeSetRenderer
operator|.
name|getScopes
argument_list|(
name|scopes
argument_list|)
decl_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|(
name|ontology
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|// @Path("upload")
comment|// @Consumes(MediaType.MULTIPART_FORM_DATA)
comment|// @POST
comment|// public void uploadDumb(@FormParam("file") InputStream is) {
comment|// Writer writer = new StringWriter();
comment|//
comment|// char[] buffer = new char[1024];
comment|//
comment|// try {
comment|//
comment|// Reader reader = new BufferedReader(
comment|//
comment|// new InputStreamReader(is, "UTF-8"));
comment|//
comment|// int n;
comment|//
comment|// while ((n = reader.read(buffer)) != -1) {
comment|//
comment|// writer.write(buffer, 0, n);
comment|//
comment|// }
comment|// } catch (IOException ex) {
comment|// throw new WebApplicationException(ex);
comment|// } finally {
comment|//
comment|// try {
comment|// is.close();
comment|// } catch (IOException e) {
comment|// throw new WebApplicationException(e);
comment|// }
comment|//
comment|// }
comment|// System.out.println(writer.toString());
comment|// }
comment|//
comment|// @Path("formdata")
comment|// @Consumes(MediaType.MULTIPART_FORM_DATA)
comment|// @POST
comment|// public void uploadUrlFormData(
comment|// @FormDataParam("file") List<FormDataBodyPart> parts,
comment|// @FormDataParam("submit") FormDataBodyPart submit)
comment|// throws IOException, ParseException {
comment|//
comment|// System.out.println("XXXX: " + submit.getMediaType());
comment|// System.out.println("XXXX: "
comment|// + submit.getHeaders().getFirst("Content-Type"));
comment|//
comment|// for (FormDataBodyPart bp : parts) {
comment|// System.out.println(bp.getMediaType());
comment|// System.out.println(bp.getHeaders().get("Content-Disposition"));
comment|// System.out.println(bp.getParameterizedHeaders().getFirst(
comment|// "Content-Disposition").getParameters().get("name"));
comment|// bp.cleanup();
comment|// }
comment|// }
block|}
end_class

end_unit

