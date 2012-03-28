begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one  * or more contributor license agreements.  See the NOTICE file  * distributed with this work for additional information  * regarding copyright ownership.  The ASF licenses this file  * to you under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance  * with the License.  You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing,  * software distributed under the License is distributed on an  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY  * KIND, either express or implied.  See the License for the  * specific language governing permissions and limitations  * under the License.  */
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
name|readers
package|;
end_package

begin_import
import|import static
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
operator|.
name|APPLICATION_JSON
import|;
end_import

begin_import
import|import static
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
operator|.
name|N3
import|;
end_import

begin_import
import|import static
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
operator|.
name|N_TRIPLE
import|;
end_import

begin_import
import|import static
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
operator|.
name|RDF_JSON
import|;
end_import

begin_import
import|import static
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
operator|.
name|RDF_XML
import|;
end_import

begin_import
import|import static
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
operator|.
name|TURTLE
import|;
end_import

begin_import
import|import static
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
operator|.
name|X_TURTLE
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
name|InputStream
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
name|ext
operator|.
name|MessageBodyReader
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
name|Graph
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
name|Parser
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
name|ContextHelper
import|;
end_import

begin_comment
comment|/**  * JAX-RS provider that parses RDF by fetching the OSGi parsing service from the ServletContext.  */
end_comment

begin_class
annotation|@
name|Provider
specifier|public
class|class
name|GraphReader
implements|implements
name|MessageBodyReader
argument_list|<
name|Graph
argument_list|>
block|{
comment|// TODO: make the clerezza Parser service able to describe the supported media types instead of hard
comment|// coding runtime assumptions as static field.
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|SUPPORTED_MEDIA_TYPES
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
name|N3
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|N_TRIPLE
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|RDF_XML
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|TURTLE
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|X_TURTLE
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|RDF_JSON
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|APPLICATION_JSON
argument_list|)
expr_stmt|;
name|SUPPORTED_MEDIA_TYPES
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
name|Parser
name|getParser
parameter_list|()
block|{
return|return
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|Parser
operator|.
name|class
argument_list|,
name|servletContext
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isReadable
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
name|SUPPORTED_MEDIA_TYPES
operator|.
name|contains
argument_list|(
name|mediaType
operator|.
name|toString
argument_list|()
argument_list|)
operator|&&
name|type
operator|.
name|isAssignableFrom
argument_list|(
name|Graph
operator|.
name|class
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Graph
name|readFrom
parameter_list|(
name|Class
argument_list|<
name|Graph
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
name|String
argument_list|>
name|httpHeaders
parameter_list|,
name|InputStream
name|entityStream
parameter_list|)
throws|throws
name|IOException
throws|,
name|WebApplicationException
block|{
name|Parser
name|parser
init|=
name|getParser
argument_list|()
decl_stmt|;
if|if
condition|(
name|parser
operator|==
literal|null
condition|)
block|{
name|RuntimeException
name|e
init|=
operator|new
name|RuntimeException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Could not find RDF Parser implementing '%s' in OSGi context."
argument_list|,
name|Parser
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|)
throw|;
block|}
return|return
name|parser
operator|.
name|parse
argument_list|(
name|entityStream
argument_list|,
name|mediaType
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit
