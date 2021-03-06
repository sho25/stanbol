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
name|reasoners
operator|.
name|web
operator|.
name|writers
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
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
name|Service
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
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Model
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|RDFWriter
import|;
end_import

begin_comment
comment|/**  * Writer for jena Model  *   * @author enridaga  *  */
end_comment

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
name|Provider
annotation|@
name|Produces
argument_list|(
block|{
literal|"application/rdf+xml"
block|,
literal|"text/turtle"
block|,
literal|"text/n3"
block|,
literal|"text/plain"
block|,
literal|"application/turtle"
block|}
argument_list|)
specifier|public
class|class
name|JenaModelWriter
implements|implements
name|MessageBodyWriter
argument_list|<
name|Model
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
name|getClass
argument_list|()
argument_list|)
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
name|Model
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
return|;
block|}
specifier|private
name|ByteArrayOutputStream
name|stream
init|=
literal|null
decl_stmt|;
annotation|@
name|Override
specifier|public
name|long
name|getSize
parameter_list|(
name|Model
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
name|log
operator|.
name|debug
argument_list|(
literal|"Called size of item"
argument_list|)
expr_stmt|;
name|stream
operator|=
name|toStream
argument_list|(
name|t
argument_list|,
name|mediaType
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Returning {} bytes"
argument_list|,
name|stream
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|Integer
operator|.
name|valueOf
argument_list|(
name|stream
operator|.
name|toByteArray
argument_list|()
operator|.
name|length
argument_list|)
operator|.
name|longValue
argument_list|()
return|;
block|}
specifier|public
name|ByteArrayOutputStream
name|toStream
parameter_list|(
name|Model
name|t
parameter_list|,
name|String
name|mediaType
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Serializing model to {}. Statements are {}"
argument_list|,
name|mediaType
argument_list|,
name|t
operator|.
name|listStatements
argument_list|()
operator|.
name|toSet
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|ByteArrayOutputStream
name|stream
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
if|if
condition|(
name|mediaType
operator|.
name|equals
argument_list|(
literal|"application/rdf+xml"
argument_list|)
condition|)
block|{
name|t
operator|.
name|write
argument_list|(
name|stream
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|equals
argument_list|(
literal|"application/turtle"
argument_list|)
condition|)
block|{
comment|// t.write(stream, "TURTLE");
name|RDFWriter
name|writer
init|=
name|t
operator|.
name|getWriter
argument_list|(
literal|"TURTLE"
argument_list|)
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Writer for TURTLE: {}"
argument_list|,
name|writer
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|t
argument_list|,
name|stream
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|equals
argument_list|(
literal|"text/turtle"
argument_list|)
condition|)
block|{
name|t
operator|.
name|write
argument_list|(
name|stream
argument_list|,
literal|"TURTLE"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|equals
argument_list|(
literal|"text/plain"
argument_list|)
condition|)
block|{
name|t
operator|.
name|write
argument_list|(
name|stream
argument_list|,
literal|"TURTLE"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|equals
argument_list|(
literal|"text/n3"
argument_list|)
condition|)
block|{
name|t
operator|.
name|write
argument_list|(
name|stream
argument_list|,
literal|"N3"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Written {} bytes to stream"
argument_list|,
name|stream
operator|.
name|toByteArray
argument_list|()
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
return|return
name|stream
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeTo
parameter_list|(
name|Model
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
if|if
condition|(
name|stream
operator|==
literal|null
condition|)
block|{
name|toStream
argument_list|(
name|t
argument_list|,
name|mediaType
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|writeTo
argument_list|(
name|entityStream
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|stream
operator|.
name|writeTo
argument_list|(
name|entityStream
argument_list|)
expr_stmt|;
name|stream
operator|=
literal|null
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

