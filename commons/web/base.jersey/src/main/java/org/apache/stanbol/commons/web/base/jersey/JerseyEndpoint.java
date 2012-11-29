begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
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
name|jersey
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|List
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
name|Activate
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
name|Deactivate
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
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ReferenceCardinality
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
name|ReferencePolicy
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
name|resource
operator|.
name|BaseStanbolResource
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
name|Bundle
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
name|cm
operator|.
name|ConfigurationException
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
name|BundleHttpContext
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
name|DefaultApplication
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
name|LinkResource
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
name|NavigationLink
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
name|ScriptResource
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
name|WebFragment
import|;
end_import

begin_comment
comment|/**  * Jersey-based RESTful endpoint for the Stanbol Enhancer engines and store.  *<p>  * This OSGi component serves as a bridge between the OSGi context and the Servlet context available to JAX-RS  * resources.  */
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
annotation|@
name|Reference
argument_list|(
name|name
operator|=
literal|"webFragment"
argument_list|,
name|referenceInterface
operator|=
name|WebFragment
operator|.
name|class
argument_list|,
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_MULTIPLE
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
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
literal|"/"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|ALIAS_PROPERTY
init|=
literal|"org.apache.stanbol.commons.web.alias"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"/static"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|STATIC_RESOURCES_URL_ROOT_PROPERTY
init|=
literal|"org.apache.stanbol.commons.web.static.url"
decl_stmt|;
comment|/**      * The origins allowed for multi-host requests      */
annotation|@
name|Property
argument_list|(
name|cardinality
operator|=
literal|100
argument_list|,
name|value
operator|=
block|{
literal|"*"
block|}
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|CORS_ORIGIN
init|=
literal|"org.apache.stanbol.commons.web.cors.origin"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|cardinality
operator|=
literal|100
argument_list|,
name|value
operator|=
block|{
literal|"Location"
block|}
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|CORS_ACCESS_CONTROL_EXPOSE_HEADERS
init|=
literal|"org.apache.stanbol.commons.web.cors.access_control_expose_headers"
decl_stmt|;
annotation|@
name|Reference
name|HttpService
name|httpService
decl_stmt|;
specifier|protected
name|ComponentContext
name|componentContext
decl_stmt|;
specifier|protected
name|ServletContext
name|servletContext
decl_stmt|;
specifier|protected
specifier|final
name|List
argument_list|<
name|WebFragment
argument_list|>
name|webFragments
init|=
operator|new
name|ArrayList
argument_list|<
name|WebFragment
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|registeredAliases
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|Set
argument_list|<
name|String
argument_list|>
name|corsOrigins
decl_stmt|;
specifier|protected
name|Set
argument_list|<
name|String
argument_list|>
name|exposedHeaders
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
annotation|@
name|Activate
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
throws|,
name|ConfigurationException
block|{
name|componentContext
operator|=
name|ctx
expr_stmt|;
comment|// init corsOrigins
name|Object
name|values
init|=
name|componentContext
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|CORS_ORIGIN
argument_list|)
decl_stmt|;
if|if
condition|(
name|values
operator|instanceof
name|String
operator|&&
operator|!
operator|(
operator|(
name|String
operator|)
name|values
operator|)
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|corsOrigins
operator|=
name|Collections
operator|.
name|singleton
argument_list|(
operator|(
name|String
operator|)
name|values
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|values
operator|instanceof
name|String
index|[]
condition|)
block|{
name|corsOrigins
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|(
name|String
index|[]
operator|)
name|values
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|values
operator|instanceof
name|Iterable
argument_list|<
name|?
argument_list|>
condition|)
block|{
name|corsOrigins
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Object
name|value
operator|:
operator|(
name|Iterable
argument_list|<
name|?
argument_list|>
operator|)
name|values
control|)
block|{
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|corsOrigins
operator|.
name|add
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|CORS_ORIGIN
argument_list|,
literal|"CORS origin(s) MUST be a String, String[], Iterable<String> (value:"
operator|+
name|values
operator|+
literal|")"
argument_list|)
throw|;
block|}
comment|// parse headers to be exposed
name|values
operator|=
name|componentContext
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|CORS_ACCESS_CONTROL_EXPOSE_HEADERS
argument_list|)
expr_stmt|;
if|if
condition|(
name|values
operator|instanceof
name|String
operator|&&
operator|!
operator|(
operator|(
name|String
operator|)
name|values
operator|)
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|exposedHeaders
operator|=
name|Collections
operator|.
name|singleton
argument_list|(
operator|(
name|String
operator|)
name|values
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|values
operator|instanceof
name|String
index|[]
condition|)
block|{
name|exposedHeaders
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|(
name|String
index|[]
operator|)
name|values
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|values
operator|instanceof
name|Iterable
argument_list|<
name|?
argument_list|>
condition|)
block|{
name|exposedHeaders
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Object
name|value
operator|:
operator|(
name|Iterable
argument_list|<
name|?
argument_list|>
operator|)
name|values
control|)
block|{
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|exposedHeaders
operator|.
name|add
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

begin_else
else|else
block|{
name|exposedHeaders
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
block|}
end_else

begin_if
if|if
condition|(
operator|!
name|webFragments
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|initJersey
argument_list|()
expr_stmt|;
block|}
end_if

begin_comment
unit|}
comment|/** Initialize the Jersey subsystem */
end_comment

begin_function
unit|private
specifier|synchronized
name|void
name|initJersey
parameter_list|()
throws|throws
name|NamespaceException
throws|,
name|ServletException
block|{
if|if
condition|(
name|componentContext
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" ... can not init Jersey Endpoint - Component not yet activated!"
argument_list|)
expr_stmt|;
comment|//throw new IllegalStateException("Null ComponentContext, not activated?");
return|return;
block|}
name|shutdownJersey
argument_list|()
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"(Re)initializing the Stanbol Jersey subsystem"
argument_list|)
expr_stmt|;
comment|// register all the JAX-RS resources into a a JAX-RS application and bind it to a configurable URL
comment|// prefix
name|DefaultApplication
name|app
init|=
operator|new
name|DefaultApplication
argument_list|()
decl_stmt|;
name|String
name|staticUrlRoot
init|=
operator|(
name|String
operator|)
name|componentContext
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
name|applicationAlias
init|=
operator|(
name|String
operator|)
name|componentContext
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|ALIAS_PROPERTY
argument_list|)
decl_stmt|;
comment|// incrementally contribute fragment resources
name|List
argument_list|<
name|LinkResource
argument_list|>
name|linkResources
init|=
operator|new
name|ArrayList
argument_list|<
name|LinkResource
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|ScriptResource
argument_list|>
name|scriptResources
init|=
operator|new
name|ArrayList
argument_list|<
name|ScriptResource
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|NavigationLink
argument_list|>
name|navigationLinks
init|=
operator|new
name|ArrayList
argument_list|<
name|NavigationLink
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|WebFragment
name|fragment
range|:
name|webFragments
control|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Registering web fragment '{}' into jaxrs application"
argument_list|,
name|fragment
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|linkResources
operator|.
name|addAll
argument_list|(
name|fragment
operator|.
name|getLinkResources
argument_list|()
argument_list|)
expr_stmt|;
name|scriptResources
operator|.
name|addAll
argument_list|(
name|fragment
operator|.
name|getScriptResources
argument_list|()
argument_list|)
expr_stmt|;
name|navigationLinks
operator|.
name|addAll
argument_list|(
name|fragment
operator|.
name|getNavigationLinks
argument_list|()
argument_list|)
expr_stmt|;
name|app
operator|.
name|contributeClasses
argument_list|(
name|fragment
operator|.
name|getJaxrsResourceClasses
argument_list|()
argument_list|)
expr_stmt|;
name|app
operator|.
name|contributeSingletons
argument_list|(
name|fragment
operator|.
name|getJaxrsResourceSingletons
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|staticPath
init|=
name|fragment
operator|.
name|getStaticResourceClassPath
argument_list|()
decl_stmt|;
if|if
condition|(
name|staticPath
operator|!=
literal|null
condition|)
block|{
name|String
name|resourceAlias
init|=
operator|(
name|applicationAlias
operator|.
name|endsWith
argument_list|(
literal|"/"
argument_list|)
condition|?
name|applicationAlias
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|applicationAlias
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
else|:
name|applicationAlias
operator|)
operator|+
name|staticUrlRoot
operator|+
literal|'/'
operator|+
name|fragment
operator|.
name|getName
argument_list|()
decl_stmt|;
name|httpService
operator|.
name|registerResources
argument_list|(
name|resourceAlias
argument_list|,
name|staticPath
argument_list|,
operator|new
name|BundleHttpContext
argument_list|(
name|fragment
argument_list|)
argument_list|)
expr_stmt|;
name|registeredAliases
operator|.
name|add
argument_list|(
name|resourceAlias
argument_list|)
expr_stmt|;
block|}
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|linkResources
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|scriptResources
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|navigationLinks
argument_list|)
expr_stmt|;
comment|// bind the aggregate JAX-RS application to a dedicated servlet
name|ServletContainer
name|container
init|=
operator|new
name|ServletContainer
argument_list|(
name|app
argument_list|)
decl_stmt|;
name|Bundle
name|appBundle
init|=
name|componentContext
operator|.
name|getBundleContext
argument_list|()
operator|.
name|getBundle
argument_list|()
decl_stmt|;
name|httpService
operator|.
name|registerServlet
argument_list|(
name|applicationAlias
argument_list|,
name|container
argument_list|,
name|getInitParams
argument_list|()
argument_list|,
operator|new
name|BundleHttpContext
argument_list|(
name|appBundle
argument_list|)
argument_list|)
expr_stmt|;
name|registeredAliases
operator|.
name|add
argument_list|(
name|applicationAlias
argument_list|)
expr_stmt|;
comment|// forward the main Stanbol OSGi runtime context so that JAX-RS resources can lookup arbitrary
comment|// services
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
name|componentContext
operator|.
name|getBundleContext
argument_list|()
argument_list|)
expr_stmt|;
name|servletContext
operator|.
name|setAttribute
argument_list|(
name|BaseStanbolResource
operator|.
name|ROOT_URL
argument_list|,
name|applicationAlias
argument_list|)
expr_stmt|;
name|servletContext
operator|.
name|setAttribute
argument_list|(
name|BaseStanbolResource
operator|.
name|STATIC_RESOURCES_ROOT_URL
argument_list|,
name|staticUrlRoot
argument_list|)
expr_stmt|;
name|servletContext
operator|.
name|setAttribute
argument_list|(
name|BaseStanbolResource
operator|.
name|LINK_RESOURCES
argument_list|,
name|linkResources
argument_list|)
expr_stmt|;
name|servletContext
operator|.
name|setAttribute
argument_list|(
name|BaseStanbolResource
operator|.
name|SCRIPT_RESOURCES
argument_list|,
name|scriptResources
argument_list|)
expr_stmt|;
name|servletContext
operator|.
name|setAttribute
argument_list|(
name|BaseStanbolResource
operator|.
name|NAVIGATION_LINKS
argument_list|,
name|navigationLinks
argument_list|)
expr_stmt|;
name|servletContext
operator|.
name|setAttribute
argument_list|(
name|CORS_ORIGIN
argument_list|,
name|corsOrigins
argument_list|)
expr_stmt|;
name|servletContext
operator|.
name|setAttribute
argument_list|(
name|CORS_ACCESS_CONTROL_EXPOSE_HEADERS
argument_list|,
name|exposedHeaders
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"JerseyEndpoint servlet registered at {}"
argument_list|,
name|applicationAlias
argument_list|)
expr_stmt|;
block|}
end_function

begin_comment
comment|/** Shutdown Jersey, if there's anything to do */
end_comment

begin_function
specifier|private
specifier|synchronized
name|void
name|shutdownJersey
parameter_list|()
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Unregistering aliases {}"
argument_list|,
name|registeredAliases
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|alias
range|:
name|registeredAliases
control|)
block|{
name|httpService
operator|.
name|unregister
argument_list|(
name|alias
argument_list|)
expr_stmt|;
block|}
name|registeredAliases
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
end_function

begin_function
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|shutdownJersey
argument_list|()
expr_stmt|;
name|servletContext
operator|=
literal|null
expr_stmt|;
name|componentContext
operator|=
literal|null
expr_stmt|;
block|}
end_function

begin_function
specifier|protected
name|void
name|bindWebFragment
parameter_list|(
name|WebFragment
name|webFragment
parameter_list|)
throws|throws
name|IOException
throws|,
name|ServletException
throws|,
name|NamespaceException
block|{
comment|// TODO: support some ordering for jax-rs resource and template overrides?
name|webFragments
operator|.
name|add
argument_list|(
name|webFragment
argument_list|)
expr_stmt|;
name|initJersey
argument_list|()
expr_stmt|;
block|}
end_function

begin_function
specifier|protected
name|void
name|unbindWebFragment
parameter_list|(
name|WebFragment
name|webFragment
parameter_list|)
throws|throws
name|IOException
throws|,
name|ServletException
throws|,
name|NamespaceException
block|{
name|webFragments
operator|.
name|remove
argument_list|(
name|webFragment
argument_list|)
expr_stmt|;
name|initJersey
argument_list|()
expr_stmt|;
block|}
end_function

begin_function
specifier|public
name|List
argument_list|<
name|WebFragment
argument_list|>
name|getWebFragments
parameter_list|()
block|{
return|return
name|webFragments
return|;
block|}
end_function

unit|}
end_unit

