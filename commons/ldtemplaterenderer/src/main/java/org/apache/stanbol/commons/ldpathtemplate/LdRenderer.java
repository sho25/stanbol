begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
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
name|ldpathtemplate
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
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Writer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|Resource
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
name|utils
operator|.
name|GraphNode
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
name|Service
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
name|ldpath
operator|.
name|clerezza
operator|.
name|ClerezzaBackend
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
name|BundleEvent
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
name|BundleListener
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
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|backend
operator|.
name|RDFBackend
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|template
operator|.
name|engine
operator|.
name|TemplateEngine
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|cache
operator|.
name|TemplateLoader
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|Configuration
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|DefaultObjectWrapper
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|TemplateException
import|;
end_import

begin_comment
comment|/**  * This service renders a GraphNode to a Writer given the   * path of an ldpath template  *  */
end_comment

begin_class
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|LdRenderer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|LdRenderer
block|{
specifier|private
specifier|static
specifier|final
name|String
name|TEMPLATES_PATH_IN_BUNDLES
init|=
literal|"templates/"
decl_stmt|;
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
name|LdRenderer
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|Collection
argument_list|<
name|Bundle
argument_list|>
name|bundles
init|=
operator|new
name|HashSet
argument_list|<
name|Bundle
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|BundleListener
name|bundleListener
init|=
operator|new
name|BundleListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|bundleChanged
parameter_list|(
name|BundleEvent
name|event
parameter_list|)
block|{
if|if
condition|(
operator|(
name|event
operator|.
name|getType
argument_list|()
operator|==
name|BundleEvent
operator|.
name|STARTED
operator|)
operator|&&
name|containsTemplates
argument_list|(
name|event
operator|.
name|getBundle
argument_list|()
argument_list|)
condition|)
block|{
name|bundles
operator|.
name|add
argument_list|(
name|event
operator|.
name|getBundle
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|bundles
operator|.
name|remove
argument_list|(
name|event
operator|.
name|getBundle
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
decl_stmt|;
specifier|private
name|TemplateLoader
name|templateLoader
init|=
operator|new
name|TemplateLoader
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Reader
name|getReader
parameter_list|(
name|Object
name|templateSource
parameter_list|,
name|String
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
name|URL
name|templateUrl
init|=
operator|(
name|URL
operator|)
name|templateSource
decl_stmt|;
return|return
operator|new
name|InputStreamReader
argument_list|(
name|templateUrl
operator|.
name|openStream
argument_list|()
argument_list|,
name|encoding
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getLastModified
parameter_list|(
name|Object
name|templateSource
parameter_list|)
block|{
comment|// not known
return|return
operator|-
literal|1
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|findTemplateSource
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
operator|!
name|name
operator|.
name|endsWith
argument_list|(
literal|".ftl"
argument_list|)
condition|)
block|{
name|name
operator|=
name|name
operator|+
literal|".ftl"
expr_stmt|;
block|}
specifier|final
name|String
name|path
init|=
name|TEMPLATES_PATH_IN_BUNDLES
operator|+
name|name
decl_stmt|;
for|for
control|(
name|Bundle
name|bundle
range|:
name|bundles
control|)
block|{
name|URL
name|res
init|=
name|bundle
operator|.
name|getResource
argument_list|(
name|path
argument_list|)
decl_stmt|;
if|if
condition|(
name|res
operator|!=
literal|null
condition|)
block|{
return|return
name|res
return|;
block|}
block|}
name|log
operator|.
name|warn
argument_list|(
literal|"Template "
operator|+
name|name
operator|+
literal|" not known"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|closeTemplateSource
parameter_list|(
name|Object
name|templateSource
parameter_list|)
throws|throws
name|IOException
block|{  			 		}
block|}
decl_stmt|;
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
specifier|final
name|ComponentContext
name|context
parameter_list|)
block|{
specifier|final
name|Bundle
index|[]
name|registeredBundles
init|=
name|context
operator|.
name|getBundleContext
argument_list|()
operator|.
name|getBundles
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|registeredBundles
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|registeredBundles
index|[
name|i
index|]
operator|.
name|getState
argument_list|()
operator|==
name|Bundle
operator|.
name|ACTIVE
operator|)
operator|&&
name|containsTemplates
argument_list|(
name|registeredBundles
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|bundles
operator|.
name|add
argument_list|(
name|registeredBundles
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
name|context
operator|.
name|getBundleContext
argument_list|()
operator|.
name|addBundleListener
argument_list|(
name|bundleListener
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
specifier|final
name|ComponentContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|getBundleContext
argument_list|()
operator|.
name|removeBundleListener
argument_list|(
name|bundleListener
argument_list|)
expr_stmt|;
block|}
specifier|private
name|boolean
name|containsTemplates
parameter_list|(
name|Bundle
name|bundle
parameter_list|)
block|{
return|return
name|bundle
operator|.
name|getResource
argument_list|(
name|TEMPLATES_PATH_IN_BUNDLES
argument_list|)
operator|!=
literal|null
return|;
block|}
comment|/** 	 * Renders a GraphNode with a template located in the templates 	 * folder of any active bundle 	 *  	 * @param node the GraphNode to be rendered 	 * @param templatePath the freemarker path to the template 	 * @param out where the result is written to 	 */
specifier|public
name|void
name|render
parameter_list|(
name|GraphNode
name|node
parameter_list|,
specifier|final
name|String
name|templatePath
parameter_list|,
name|Writer
name|out
parameter_list|)
block|{
comment|//A GraphNode backend could be graph unspecific, so the same engine could be
comment|//reused, possibly being signifantly more performant (caching, etc.)
name|RDFBackend
argument_list|<
name|Resource
argument_list|>
name|backend
init|=
operator|new
name|ClerezzaBackend
argument_list|(
name|node
operator|.
name|getGraph
argument_list|()
argument_list|)
decl_stmt|;
name|Resource
name|context
init|=
name|node
operator|.
name|getNode
argument_list|()
decl_stmt|;
name|TemplateEngine
argument_list|<
name|Resource
argument_list|>
name|engine
init|=
operator|new
name|TemplateEngine
argument_list|<
name|Resource
argument_list|>
argument_list|(
name|backend
argument_list|)
decl_stmt|;
name|engine
operator|.
name|setTemplateLoader
argument_list|(
name|templateLoader
argument_list|)
expr_stmt|;
try|try
block|{
name|engine
operator|.
name|processFileTemplate
argument_list|(
name|context
argument_list|,
name|templatePath
argument_list|,
literal|null
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|TemplateException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
comment|/** 	 * Old school classical freemarker rendering, no LD here 	 */
specifier|public
name|void
name|renderPojo
parameter_list|(
name|Object
name|pojo
parameter_list|,
specifier|final
name|String
name|templatePath
parameter_list|,
name|Writer
name|out
parameter_list|)
block|{
name|Configuration
name|freemarker
init|=
operator|new
name|Configuration
argument_list|()
decl_stmt|;
name|freemarker
operator|.
name|setDefaultEncoding
argument_list|(
literal|"utf-8"
argument_list|)
expr_stmt|;
name|freemarker
operator|.
name|setOutputEncoding
argument_list|(
literal|"utf-8"
argument_list|)
expr_stmt|;
name|freemarker
operator|.
name|setObjectWrapper
argument_list|(
operator|new
name|DefaultObjectWrapper
argument_list|()
argument_list|)
expr_stmt|;
name|freemarker
operator|.
name|setTemplateLoader
argument_list|(
name|templateLoader
argument_list|)
expr_stmt|;
try|try
block|{
comment|//should root be a map instead?
name|freemarker
operator|.
name|getTemplate
argument_list|(
name|templatePath
argument_list|)
operator|.
name|process
argument_list|(
name|pojo
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|TemplateException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

