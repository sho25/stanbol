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
name|viewable
operator|.
name|writer
operator|.
name|impl
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
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStreamWriter
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
name|lang
operator|.
name|annotation
operator|.
name|Annotation
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Type
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
name|WebApplicationException
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
name|MediaType
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
name|MultivaluedMap
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
name|ext
operator|.
name|MessageBodyWriter
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
name|ext
operator|.
name|Provider
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
name|viewable
operator|.
name|Viewable
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
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
import|;
end_import

begin_class
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|Object
operator|.
name|class
argument_list|)
annotation|@
name|Property
argument_list|(
name|name
operator|=
literal|"javax.ws.rs"
argument_list|,
name|boolValue
operator|=
literal|true
argument_list|)
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
annotation|@
name|Provider
specifier|public
class|class
name|ViewableWriter
implements|implements
name|MessageBodyWriter
argument_list|<
name|Viewable
argument_list|>
block|{
annotation|@
name|Reference
specifier|private
name|TemplateLoader
name|templateLoader
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|isWriteable
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|,
name|Type
name|genericType
parameter_list|,
name|Annotation
index|[]
name|annotations
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
block|{
return|return
name|Viewable
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getSize
parameter_list|(
name|Viewable
name|t
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|,
name|Type
name|genericType
parameter_list|,
name|Annotation
index|[]
name|annotations
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
block|{
return|return
operator|-
literal|1
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeTo
parameter_list|(
specifier|final
name|Viewable
name|t
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|,
name|Type
name|genericType
parameter_list|,
name|Annotation
index|[]
name|annotations
parameter_list|,
name|MediaType
name|mediaType
parameter_list|,
name|MultivaluedMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|httpHeaders
parameter_list|,
name|OutputStream
name|entityStream
parameter_list|)
throws|throws
name|IOException
throws|,
name|WebApplicationException
block|{
name|Writer
name|out
init|=
operator|new
name|OutputStreamWriter
argument_list|(
name|entityStream
argument_list|,
literal|"utf-8"
argument_list|)
decl_stmt|;
name|renderPojo
argument_list|(
operator|new
name|Wrapper
argument_list|(
name|t
operator|.
name|getPojo
argument_list|()
argument_list|)
argument_list|,
literal|"html/"
operator|+
name|t
operator|.
name|getTemplatePath
argument_list|()
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
comment|/**      * Old school classical freemarker rendering, no LD here      */
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
name|setLocalizedLookup
argument_list|(
literal|false
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
specifier|static
specifier|public
class|class
name|Wrapper
block|{
specifier|private
name|Object
name|wrapped
decl_stmt|;
specifier|public
name|Wrapper
parameter_list|(
name|Object
name|wrapped
parameter_list|)
block|{
name|this
operator|.
name|wrapped
operator|=
name|wrapped
expr_stmt|;
block|}
specifier|public
name|Object
name|getIt
parameter_list|()
block|{
return|return
name|wrapped
return|;
block|}
block|}
block|}
end_class

end_unit

