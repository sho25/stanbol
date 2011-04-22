begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Dictionary
import|;
end_import

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
name|servlet
operator|.
name|ServletException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Reference
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
name|reasoners
operator|.
name|base
operator|.
name|api
operator|.
name|ConsistentRefactorer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|BundleContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|http
operator|.
name|HttpService
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|http
operator|.
name|NamespaceException
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
name|spi
operator|.
name|container
operator|.
name|servlet
operator|.
name|ServletContainer
import|;
end_import

begin_comment
comment|/**  * Jersey-based RESTful endpoint for KReS.  *   * This OSGi component serves as a bridge between the OSGi context and the Servlet context available to JAX-RS  * resources.  *   * @author andrea.nuzzolese  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
specifier|public
class|class
name|JerseyEndpoint
block|{
specifier|private
specifier|final
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
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"/reasoner"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|ALIAS_PROPERTY
init|=
literal|"org.apache.stanbol.reasoner.web.jersey.alias"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"/reasoner/static"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|STATIC_RESOURCES_URL_ROOT_PROPERTY
init|=
literal|"org.apache.stanbol.reasoner.web.jersey.static.url"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"/META-INF/static"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|STATIC_RESOURCES_CLASSPATH_PROPERTY
init|=
literal|"org.apache.stanbol.reasoner.web.jersey.static.classpath"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"/META-INF/templates"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|FREEMARKER_TEMPLATE_CLASSPATH_PROPERTY
init|=
literal|"org.apache.stanbol.reasoner.web.jersey.templates.classpath"
decl_stmt|;
annotation|@
name|Reference
name|HttpService
name|httpService
decl_stmt|;
annotation|@
name|Reference
name|ConsistentRefactorer
name|consistentRefactorer
decl_stmt|;
specifier|protected
name|ServletContext
name|servletContext
decl_stmt|;
specifier|public
name|Dictionary
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getInitParams
parameter_list|()
block|{
comment|// pass configuration for Jersey resource
comment|// TODO: make the list of enabled JAX-RS resources and providers
comment|// configurable using an OSGi service
name|Dictionary
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|initParams
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|initParams
operator|.
name|put
argument_list|(
literal|"javax.ws.rs.Application"
argument_list|,
name|JerseyEndpointApplication
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
comment|// make jersey automatically turn resources into Viewable models and
comment|// hence lookup matching freemarker templates
name|initParams
operator|.
name|put
argument_list|(
literal|"com.sun.jersey.config.feature.ImplicitViewables"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
return|return
name|initParams
return|;
block|}
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|IOException
throws|,
name|ServletException
throws|,
name|NamespaceException
block|{
comment|// register the JAX-RS resources as a servlet under configurable alias
name|ServletContainer
name|container
init|=
operator|new
name|ServletContainer
argument_list|()
decl_stmt|;
name|String
name|alias
init|=
operator|(
name|String
operator|)
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|ALIAS_PROPERTY
argument_list|)
decl_stmt|;
name|String
name|staticUrlRoot
init|=
operator|(
name|String
operator|)
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|STATIC_RESOURCES_URL_ROOT_PROPERTY
argument_list|)
decl_stmt|;
name|String
name|staticClasspath
init|=
operator|(
name|String
operator|)
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|STATIC_RESOURCES_CLASSPATH_PROPERTY
argument_list|)
decl_stmt|;
name|String
name|freemakerTemplates
init|=
operator|(
name|String
operator|)
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|FREEMARKER_TEMPLATE_CLASSPATH_PROPERTY
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Registering servlets with HTTP service "
operator|+
name|httpService
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|ClassLoader
name|classLoader
init|=
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getContextClassLoader
argument_list|()
decl_stmt|;
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|setContextClassLoader
argument_list|(
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|httpService
operator|.
name|registerServlet
argument_list|(
name|alias
argument_list|,
name|container
argument_list|,
name|getInitParams
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|httpService
operator|.
name|registerResources
argument_list|(
name|staticUrlRoot
argument_list|,
name|staticClasspath
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|setContextClassLoader
argument_list|(
name|classLoader
argument_list|)
expr_stmt|;
block|}
comment|// forward the main KReS OSGi components to the servlet context so that
comment|// they can be looked up by the JAX-RS resources
name|servletContext
operator|=
name|container
operator|.
name|getServletContext
argument_list|()
expr_stmt|;
name|servletContext
operator|.
name|setAttribute
argument_list|(
name|BundleContext
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|ctx
operator|.
name|getBundleContext
argument_list|()
argument_list|)
expr_stmt|;
comment|// Register components
name|servletContext
operator|.
name|setAttribute
argument_list|(
name|ConsistentRefactorer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|consistentRefactorer
argument_list|)
expr_stmt|;
comment|// Register templates
name|servletContext
operator|.
name|setAttribute
argument_list|(
name|STATIC_RESOURCES_URL_ROOT_PROPERTY
argument_list|,
name|staticUrlRoot
argument_list|)
expr_stmt|;
comment|//        servletContext
comment|//                .setAttribute(ViewProcessorImpl.FREEMARKER_TEMPLATE_PATH_INIT_PARAM, freemakerTemplates);
name|log
operator|.
name|info
argument_list|(
literal|"Jersey servlet registered at {}"
argument_list|,
name|alias
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Deactivating jersey bundle"
argument_list|)
expr_stmt|;
name|String
name|alias
init|=
operator|(
name|String
operator|)
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|ALIAS_PROPERTY
argument_list|)
decl_stmt|;
name|httpService
operator|.
name|unregister
argument_list|(
name|alias
argument_list|)
expr_stmt|;
name|servletContext
operator|=
literal|null
expr_stmt|;
block|}
specifier|protected
name|void
name|bindHttpService
parameter_list|(
name|HttpService
name|httpService
parameter_list|)
block|{
name|this
operator|.
name|httpService
operator|=
name|httpService
expr_stmt|;
block|}
specifier|protected
name|void
name|unbindHttpService
parameter_list|(
name|HttpService
name|httpService
parameter_list|)
block|{
name|this
operator|.
name|httpService
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_class

end_unit

