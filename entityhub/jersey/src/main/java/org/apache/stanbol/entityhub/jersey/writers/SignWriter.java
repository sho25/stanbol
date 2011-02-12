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
name|entityhub
operator|.
name|jersey
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
name|Collections
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
name|core
operator|.
name|Response
operator|.
name|Status
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|serializedform
operator|.
name|Serializer
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
name|serializedform
operator|.
name|SupportedFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|IOUtils
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
name|Sign
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONException
import|;
end_import

begin_comment
comment|/**  * TODO: Replace with Serializer infrastrucutre similar to  * {@link Serializer}  * @author Rupert Westenthaler  *  */
end_comment

begin_class
annotation|@
name|Provider
annotation|@
name|Produces
argument_list|(
block|{
name|MediaType
operator|.
name|APPLICATION_JSON
block|,
name|SupportedFormat
operator|.
name|N3
block|,
name|SupportedFormat
operator|.
name|N_TRIPLE
block|,
name|SupportedFormat
operator|.
name|RDF_XML
block|,
name|SupportedFormat
operator|.
name|TURTLE
block|,
name|SupportedFormat
operator|.
name|X_TURTLE
block|,
name|SupportedFormat
operator|.
name|RDF_JSON
block|}
argument_list|)
specifier|public
class|class
name|SignWriter
implements|implements
name|MessageBodyWriter
argument_list|<
name|Sign
argument_list|>
block|{
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|supportedMediaTypes
decl_stmt|;
static|static
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|types
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|types
operator|.
name|add
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|SupportedFormat
operator|.
name|N3
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|SupportedFormat
operator|.
name|N_TRIPLE
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|SupportedFormat
operator|.
name|RDF_JSON
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|SupportedFormat
operator|.
name|TURTLE
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|SupportedFormat
operator|.
name|X_TURTLE
argument_list|)
expr_stmt|;
name|supportedMediaTypes
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|types
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Context
specifier|protected
name|ServletContext
name|servletContext
decl_stmt|;
specifier|protected
name|Serializer
name|getSerializer
parameter_list|()
block|{
return|return
operator|(
name|Serializer
operator|)
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|Serializer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getSize
parameter_list|(
name|Sign
name|Sign
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
comment|//to hard to calculate
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
return|return
name|Sign
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
operator|&&
name|supportedMediaTypes
operator|.
name|contains
argument_list|(
name|mediaType
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeTo
parameter_list|(
name|Sign
name|sign
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
name|mediaType
operator|==
literal|null
operator|||
name|MediaType
operator|.
name|APPLICATION_JSON
operator|.
name|equals
argument_list|(
name|mediaType
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
try|try
block|{
name|IOUtils
operator|.
name|write
argument_list|(
name|SignToJSON
operator|.
name|toJSON
argument_list|(
name|sign
argument_list|)
operator|.
name|toString
argument_list|(
literal|4
argument_list|)
argument_list|,
name|entityStream
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
else|else
block|{
comment|//RDF
name|getSerializer
argument_list|()
operator|.
name|serialize
argument_list|(
name|entityStream
argument_list|,
name|SignToRDF
operator|.
name|toRDF
argument_list|(
name|sign
argument_list|)
argument_list|,
name|mediaType
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

