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
name|entityhub
operator|.
name|web
operator|.
name|writer
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
name|java
operator|.
name|util
operator|.
name|Iterator
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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|entityhub
operator|.
name|web
operator|.
name|ModelWriter
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
name|entityhub
operator|.
name|web
operator|.
name|ModelWriterRegistry
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
name|ServiceReference
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

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
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
name|Provider
specifier|public
class|class
name|RepresentationWriter
implements|implements
name|MessageBodyWriter
argument_list|<
name|Representation
argument_list|>
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
name|RepresentationWriter
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Reference
specifier|protected
name|ModelWriterRegistry
name|writerRegistry
decl_stmt|;
annotation|@
name|Override
specifier|public
name|long
name|getSize
parameter_list|(
name|Representation
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
if|if
condition|(
name|Representation
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
condition|)
block|{
if|if
condition|(
name|mediaType
operator|.
name|isWildcardType
argument_list|()
operator|&&
name|mediaType
operator|.
name|isWildcardSubtype
argument_list|()
condition|)
block|{
name|mediaType
operator|=
name|ModelWriter
operator|.
name|DEFAULT_MEDIA_TYPE
expr_stmt|;
block|}
return|return
name|writerRegistry
operator|.
name|isWriteable
argument_list|(
name|getMatchType
argument_list|(
name|mediaType
argument_list|)
argument_list|,
literal|null
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeTo
parameter_list|(
name|Representation
name|rep
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
comment|//check for wildcard
if|if
condition|(
name|mediaType
operator|.
name|isWildcardType
argument_list|()
operator|&&
name|mediaType
operator|.
name|isWildcardSubtype
argument_list|()
condition|)
block|{
name|mediaType
operator|=
name|ModelWriter
operator|.
name|DEFAULT_MEDIA_TYPE
expr_stmt|;
block|}
name|String
name|charset
init|=
name|mediaType
operator|.
name|getParameters
argument_list|()
operator|.
name|get
argument_list|(
literal|"charset"
argument_list|)
decl_stmt|;
if|if
condition|(
name|charset
operator|==
literal|null
condition|)
block|{
name|charset
operator|=
name|ModelWriter
operator|.
name|DEFAULT_CHARSET
expr_stmt|;
name|mediaType
operator|=
name|mediaType
operator|.
name|withCharset
argument_list|(
name|charset
argument_list|)
expr_stmt|;
name|httpHeaders
operator|.
name|putSingle
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|mediaType
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Iterator
argument_list|<
name|ServiceReference
argument_list|>
name|refs
init|=
name|writerRegistry
operator|.
name|getModelWriters
argument_list|(
name|getMatchType
argument_list|(
name|mediaType
argument_list|)
argument_list|,
name|rep
operator|.
name|getClass
argument_list|()
argument_list|)
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|ModelWriter
name|writer
init|=
literal|null
decl_stmt|;
name|MediaType
name|selectedMediaType
init|=
literal|null
decl_stmt|;
while|while
condition|(
operator|(
name|writer
operator|==
literal|null
operator|||
name|selectedMediaType
operator|==
literal|null
operator|)
operator|&&
name|refs
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|writer
operator|=
name|writerRegistry
operator|.
name|getService
argument_list|(
name|refs
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|writer
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|mediaType
operator|.
name|isWildcardType
argument_list|()
operator|||
name|mediaType
operator|.
name|isWildcardSubtype
argument_list|()
condition|)
block|{
name|selectedMediaType
operator|=
name|writer
operator|.
name|getBestMediaType
argument_list|(
name|mediaType
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|selectedMediaType
operator|=
name|mediaType
expr_stmt|;
block|}
block|}
block|}
name|selectedMediaType
operator|=
name|selectedMediaType
operator|.
name|withCharset
argument_list|(
name|charset
argument_list|)
expr_stmt|;
name|httpHeaders
operator|.
name|putSingle
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|mediaType
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|writer
operator|==
literal|null
operator|||
name|selectedMediaType
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
literal|"Unable to serialize "
operator|+
name|rep
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" to "
operator|+
name|mediaType
argument_list|)
throw|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"serialize {} with ModelWriter {}"
argument_list|,
name|rep
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|writer
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|rep
argument_list|,
name|entityStream
argument_list|,
name|selectedMediaType
argument_list|)
expr_stmt|;
block|}
comment|/**      * Strips all parameters from the parsed mediaType      * @param mediaType      */
specifier|protected
name|MediaType
name|getMatchType
parameter_list|(
name|MediaType
name|mediaType
parameter_list|)
block|{
specifier|final
name|MediaType
name|matchType
decl_stmt|;
if|if
condition|(
operator|!
name|mediaType
operator|.
name|getParameters
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|matchType
operator|=
operator|new
name|MediaType
argument_list|(
name|mediaType
operator|.
name|getType
argument_list|()
argument_list|,
name|mediaType
operator|.
name|getSubtype
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|matchType
operator|=
name|mediaType
expr_stmt|;
block|}
return|return
name|matchType
return|;
block|}
block|}
end_class

end_unit

