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
name|writers
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
name|HttpHeaders
operator|.
name|CONTENT_TYPE
import|;
end_import

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
name|APPLICATION_OCTET_STREAM
import|;
end_import

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
name|TEXT_PLAIN
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
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|clerezza
operator|.
name|commons
operator|.
name|rdf
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
name|Serializer
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

begin_comment
comment|//TODO check if clerezza rdf.jaxrs prvoder fits the purpose?
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
comment|// @Produces({TEXT_PLAIN, N3, N_TRIPLE, RDF_XML, TURTLE, X_TURTLE, RDF_JSON, APPLICATION_JSON})
specifier|public
class|class
name|GraphWriter
implements|implements
name|MessageBodyWriter
argument_list|<
name|Graph
argument_list|>
block|{
comment|/**      * The media type for JSON-LD (<code>application/ld+json</code>)      */
specifier|private
specifier|static
name|String
name|APPLICATION_LD_JSON
init|=
literal|"application/ld+json"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Charset
name|UTF8
init|=
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|GraphWriter
operator|.
name|class
argument_list|)
decl_stmt|;
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
name|TEXT_PLAIN
argument_list|)
expr_stmt|;
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
name|types
operator|.
name|add
argument_list|(
name|APPLICATION_OCTET_STREAM
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|APPLICATION_LD_JSON
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
specifier|public
specifier|static
specifier|final
name|String
name|ENCODING
init|=
literal|"UTF-8"
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|Serializer
name|serializer
decl_stmt|;
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
name|String
name|mediaTypeString
init|=
name|mediaType
operator|.
name|getType
argument_list|()
operator|+
literal|'/'
operator|+
name|mediaType
operator|.
name|getSubtype
argument_list|()
decl_stmt|;
return|return
name|Graph
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
name|mediaTypeString
argument_list|)
return|;
block|}
specifier|public
name|long
name|getSize
parameter_list|(
name|Graph
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
specifier|public
name|void
name|writeTo
parameter_list|(
name|Graph
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
name|String
name|mediaTypeString
init|=
name|mediaType
operator|.
name|getType
argument_list|()
operator|+
literal|'/'
operator|+
name|mediaType
operator|.
name|getSubtype
argument_list|()
decl_stmt|;
if|if
condition|(
name|mediaType
operator|.
name|isWildcardType
argument_list|()
operator|||
name|TEXT_PLAIN
operator|.
name|equals
argument_list|(
name|mediaTypeString
argument_list|)
operator|||
name|APPLICATION_OCTET_STREAM
operator|.
name|equals
argument_list|(
name|mediaTypeString
argument_list|)
condition|)
block|{
name|mediaTypeString
operator|=
name|APPLICATION_LD_JSON
expr_stmt|;
block|}
name|httpHeaders
operator|.
name|putSingle
argument_list|(
name|CONTENT_TYPE
argument_list|,
name|mediaTypeString
operator|+
literal|";charset="
operator|+
name|UTF8
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|serializer
operator|.
name|serialize
argument_list|(
name|entityStream
argument_list|,
name|t
argument_list|,
name|mediaTypeString
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Serialized {} in {}ms"
argument_list|,
name|t
operator|.
name|size
argument_list|()
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

