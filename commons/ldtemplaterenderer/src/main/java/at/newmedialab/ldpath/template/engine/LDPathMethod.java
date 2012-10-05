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
name|LDPath
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
name|exception
operator|.
name|LDPathParseException
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
name|model
operator|.
name|Constants
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
name|TemplateWrapperModel
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
name|transformers
operator|.
name|*
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|core
operator|.
name|Environment
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|TemplateDateModel
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|TemplateMethodModel
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|TemplateModelException
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
name|HashMap
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
name|Map
import|;
end_import

begin_comment
comment|/**  * Add file description here!  *<p/>  * Author: Sebastian Schaffert  */
end_comment

begin_class
specifier|public
class|class
name|LDPathMethod
parameter_list|<
name|Node
parameter_list|>
implements|implements
name|TemplateMethodModel
block|{
specifier|private
name|LDPath
argument_list|<
name|Node
argument_list|>
name|ldpath
decl_stmt|;
specifier|private
name|RDFBackend
argument_list|<
name|Node
argument_list|>
name|backend
decl_stmt|;
specifier|public
name|LDPathMethod
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
name|ldpath
operator|=
operator|new
name|LDPath
argument_list|<
name|Node
argument_list|>
argument_list|(
name|backend
argument_list|)
expr_stmt|;
name|this
operator|.
name|backend
operator|=
name|backend
expr_stmt|;
comment|// register custom freemarker transformers for the parser so we get the results immediately in the freemarker model
name|ldpath
operator|.
name|registerTransformer
argument_list|(
name|Constants
operator|.
name|NS_XSD
operator|+
literal|"string"
argument_list|,
operator|new
name|TemplateScalarTransformer
argument_list|<
name|Node
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|ldpath
operator|.
name|registerTransformer
argument_list|(
name|Constants
operator|.
name|NS_XSD
operator|+
literal|"decimal"
argument_list|,
operator|new
name|TemplateLongTransformer
argument_list|<
name|Node
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|ldpath
operator|.
name|registerTransformer
argument_list|(
name|Constants
operator|.
name|NS_XSD
operator|+
literal|"integer"
argument_list|,
operator|new
name|TemplateIntegerTransformer
argument_list|<
name|Node
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|ldpath
operator|.
name|registerTransformer
argument_list|(
name|Constants
operator|.
name|NS_XSD
operator|+
literal|"long"
argument_list|,
operator|new
name|TemplateLongTransformer
argument_list|<
name|Node
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|ldpath
operator|.
name|registerTransformer
argument_list|(
name|Constants
operator|.
name|NS_XSD
operator|+
literal|"short"
argument_list|,
operator|new
name|TemplateIntegerTransformer
argument_list|<
name|Node
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|ldpath
operator|.
name|registerTransformer
argument_list|(
name|Constants
operator|.
name|NS_XSD
operator|+
literal|"double"
argument_list|,
operator|new
name|TemplateDoubleTransformer
argument_list|<
name|Node
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|ldpath
operator|.
name|registerTransformer
argument_list|(
name|Constants
operator|.
name|NS_XSD
operator|+
literal|"float"
argument_list|,
operator|new
name|TemplateFloatTransformer
argument_list|<
name|Node
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|ldpath
operator|.
name|registerTransformer
argument_list|(
name|Constants
operator|.
name|NS_XSD
operator|+
literal|"dateTime"
argument_list|,
operator|new
name|TemplateDateTransformer
argument_list|<
name|Node
argument_list|>
argument_list|(
name|TemplateDateModel
operator|.
name|DATETIME
argument_list|)
argument_list|)
expr_stmt|;
name|ldpath
operator|.
name|registerTransformer
argument_list|(
name|Constants
operator|.
name|NS_XSD
operator|+
literal|"date"
argument_list|,
operator|new
name|TemplateDateTransformer
argument_list|<
name|Node
argument_list|>
argument_list|(
name|TemplateDateModel
operator|.
name|DATE
argument_list|)
argument_list|)
expr_stmt|;
name|ldpath
operator|.
name|registerTransformer
argument_list|(
name|Constants
operator|.
name|NS_XSD
operator|+
literal|"time"
argument_list|,
operator|new
name|TemplateDateTransformer
argument_list|<
name|Node
argument_list|>
argument_list|(
name|TemplateDateModel
operator|.
name|TIME
argument_list|)
argument_list|)
expr_stmt|;
name|ldpath
operator|.
name|registerTransformer
argument_list|(
name|Constants
operator|.
name|NS_XSD
operator|+
literal|"boolean"
argument_list|,
operator|new
name|TemplateBooleanTransformer
argument_list|<
name|Node
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|ldpath
operator|.
name|registerTransformer
argument_list|(
name|Constants
operator|.
name|NS_XSD
operator|+
literal|"anyURI"
argument_list|,
operator|new
name|TemplateScalarTransformer
argument_list|<
name|Node
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Executes a method call. All arguments passed to the method call are      * coerced to strings before being passed, if the FreeMarker rules allow      * the coercion. If some of the passed arguments can not be coerced to a      * string, an exception will be raised in the engine and the method will      * not be called. If your method would like to act on actual data model      * objects instead of on their string representations, implement the      * {@link freemarker.template.TemplateMethodModelEx} instead.      *      * @param arguments a<tt>List</tt> of<tt>String</tt> objects      *                  containing the values of the arguments passed to the method.      * @return the return value of the method, or null. If the returned value      *         does not implement {@link freemarker.template.TemplateModel}, it will be automatically      *         wrapped using the {@link freemarker.core.Environment#getObjectWrapper() environment      *         object wrapper}.      */
annotation|@
name|Override
specifier|public
name|Object
name|exec
parameter_list|(
name|List
name|arguments
parameter_list|)
throws|throws
name|TemplateModelException
block|{
name|Environment
name|env
init|=
name|Environment
operator|.
name|getCurrentEnvironment
argument_list|()
decl_stmt|;
name|TemplateStackModel
name|contextStack
init|=
operator|(
name|TemplateStackModel
operator|)
name|env
operator|.
name|getVariable
argument_list|(
literal|"context"
argument_list|)
decl_stmt|;
if|if
condition|(
name|contextStack
operator|==
literal|null
operator|||
name|contextStack
operator|.
name|empty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|TemplateModelException
argument_list|(
literal|"error; no context node available"
argument_list|)
throw|;
block|}
name|TemplateNodeModel
argument_list|<
name|Node
argument_list|>
name|context
init|=
operator|(
name|TemplateNodeModel
argument_list|<
name|Node
argument_list|>
operator|)
name|contextStack
operator|.
name|peek
argument_list|()
decl_stmt|;
name|String
name|path
decl_stmt|;
if|if
condition|(
name|arguments
operator|.
name|size
argument_list|()
operator|!=
literal|1
condition|)
block|{
throw|throw
operator|new
name|TemplateModelException
argument_list|(
literal|"the directive has been called without a path parameter"
argument_list|)
throw|;
block|}
else|else
block|{
name|path
operator|=
operator|(
name|String
operator|)
name|arguments
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|path
operator|.
name|contains
argument_list|(
literal|"::"
argument_list|)
condition|)
block|{
name|path
operator|=
name|path
operator|+
literal|" :: xsd:string"
expr_stmt|;
block|}
block|}
name|TemplateWrapperModel
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
name|namespacesWrapped
init|=
operator|(
name|TemplateWrapperModel
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
operator|)
name|env
operator|.
name|getGlobalVariable
argument_list|(
literal|"namespaces"
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|namespaces
decl_stmt|;
if|if
condition|(
name|namespacesWrapped
operator|==
literal|null
condition|)
block|{
name|namespaces
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|namespacesWrapped
operator|=
operator|new
name|TemplateWrapperModel
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
argument_list|(
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|env
operator|.
name|setGlobalVariable
argument_list|(
literal|"namespaces"
argument_list|,
name|namespacesWrapped
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|namespaces
operator|=
name|namespacesWrapped
operator|.
name|getAdaptedObject
argument_list|(
name|Map
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|arguments
operator|.
name|size
argument_list|()
operator|!=
literal|1
condition|)
block|{
throw|throw
operator|new
name|TemplateModelException
argument_list|(
literal|"wrong number of arguments for method call"
argument_list|)
throw|;
block|}
try|try
block|{
name|Collection
name|result
init|=
name|ldpath
operator|.
name|pathTransform
argument_list|(
name|context
operator|.
name|getNode
argument_list|()
argument_list|,
name|path
argument_list|,
name|namespaces
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
return|return
name|result
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
catch|catch
parameter_list|(
name|LDPathParseException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|TemplateModelException
argument_list|(
literal|"could not parse path expression '"
operator|+
name|path
operator|+
literal|"'"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit
