begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (c) 2011 Salzburg Research.  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|template
operator|.
name|engine
package|;
end_package

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
name|model
operator|.
name|freemarker
operator|.
name|TemplateNodeModel
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
name|model
operator|.
name|freemarker
operator|.
name|TemplateStackModel
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
name|Template
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

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

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
name|Writer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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

begin_comment
comment|/**  * Add file description here!  *<p/>  * Author: Sebastian Schaffert  */
end_comment

begin_class
specifier|public
class|class
name|TemplateEngine
parameter_list|<
name|Node
parameter_list|>
block|{
specifier|private
name|Configuration
name|freemarker
decl_stmt|;
specifier|private
name|RDFBackend
argument_list|<
name|Node
argument_list|>
name|backend
decl_stmt|;
specifier|public
name|TemplateEngine
parameter_list|(
name|RDFBackend
argument_list|<
name|Node
argument_list|>
name|backend
parameter_list|)
block|{
name|this
operator|.
name|backend
operator|=
name|backend
expr_stmt|;
name|freemarker
operator|=
operator|new
name|Configuration
argument_list|()
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
block|}
comment|/**      * Allow setting a different template loader. Custom template loaders can be implemented in addition to      * those provided by FreeMarker.      *      * @param loader      */
specifier|public
name|void
name|setTemplateLoader
parameter_list|(
name|TemplateLoader
name|loader
parameter_list|)
block|{
name|freemarker
operator|.
name|setTemplateLoader
argument_list|(
name|loader
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setDirectoryForTemplateLoading
parameter_list|(
name|File
name|dir
parameter_list|)
throws|throws
name|IOException
block|{
name|freemarker
operator|.
name|setDirectoryForTemplateLoading
argument_list|(
name|dir
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setServletContextForTemplateLoading
parameter_list|(
name|Object
name|sctxt
parameter_list|,
name|String
name|path
parameter_list|)
block|{
name|freemarker
operator|.
name|setServletContextForTemplateLoading
argument_list|(
name|sctxt
argument_list|,
name|path
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setClassForTemplateLoading
parameter_list|(
name|Class
name|clazz
parameter_list|,
name|String
name|pathPrefix
parameter_list|)
block|{
name|freemarker
operator|.
name|setClassForTemplateLoading
argument_list|(
name|clazz
argument_list|,
name|pathPrefix
argument_list|)
expr_stmt|;
block|}
comment|/**      * Process the template with the given name forn the given context node and write the result to the given      * output writer. The way the template is retrieved depends on the template loader, which can be set using the      * setTemplateLoader() method.      *      * @param context the  initial context node to apply this template to      * @param templateName the name of the template      * @param out          where to write the results      * @throws IOException      * @throws TemplateException      */
specifier|public
name|void
name|processFileTemplate
parameter_list|(
name|Node
name|context
parameter_list|,
name|String
name|templateName
parameter_list|,
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
throws|,
name|TemplateException
block|{
name|processTemplate
argument_list|(
name|context
argument_list|,
name|freemarker
operator|.
name|getTemplate
argument_list|(
name|templateName
argument_list|)
argument_list|,
literal|null
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
comment|/**      * Process the template with the given name forn the given context node and write the result to the given      * output writer. The initial environment is passed over to the invocation of the template. The way the template      * is retrieved depends on the template loader, which can be set using the setTemplateLoader() method.      *      * @param context the  initial context node to apply this template to      * @param templateName the name of the template      * @param initialEnv   an initial root environment for processing the template      * @param out          where to write the results      * @throws IOException      * @throws TemplateException      */
specifier|public
name|void
name|processFileTemplate
parameter_list|(
name|Node
name|context
parameter_list|,
name|String
name|templateName
parameter_list|,
name|Map
name|initialEnv
parameter_list|,
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
throws|,
name|TemplateException
block|{
name|processTemplate
argument_list|(
name|context
argument_list|,
name|freemarker
operator|.
name|getTemplate
argument_list|(
name|templateName
argument_list|)
argument_list|,
name|initialEnv
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|processTemplate
parameter_list|(
name|Node
name|context
parameter_list|,
name|Template
name|template
parameter_list|,
name|Map
name|initialEnv
parameter_list|,
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
throws|,
name|TemplateException
block|{
name|Map
name|root
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
if|if
condition|(
name|initialEnv
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Map
operator|.
name|Entry
name|entry
range|:
operator|(
name|Set
argument_list|<
name|Map
operator|.
name|Entry
argument_list|>
operator|)
name|initialEnv
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|root
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|root
operator|.
name|put
argument_list|(
literal|"namespace"
argument_list|,
operator|new
name|NamespaceDirective
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|put
argument_list|(
literal|"evalLDPath"
argument_list|,
operator|new
name|LDPathMethod
argument_list|(
name|backend
argument_list|)
argument_list|)
expr_stmt|;
name|root
operator|.
name|put
argument_list|(
literal|"ldpath"
argument_list|,
operator|new
name|LDPathDirective
argument_list|(
name|backend
argument_list|)
argument_list|)
expr_stmt|;
name|TemplateStackModel
name|contexts
init|=
operator|new
name|TemplateStackModel
argument_list|()
decl_stmt|;
name|contexts
operator|.
name|push
argument_list|(
operator|new
name|TemplateNodeModel
argument_list|(
name|context
argument_list|,
name|backend
argument_list|)
argument_list|)
expr_stmt|;
name|root
operator|.
name|put
argument_list|(
literal|"context"
argument_list|,
name|contexts
argument_list|)
expr_stmt|;
name|template
operator|.
name|process
argument_list|(
name|root
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

