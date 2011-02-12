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
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
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
name|MGraph
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
name|query
operator|.
name|QueryResultList
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
comment|/**  * TODO: Replace with Serializer infrastructure similar to {@link Serializer}  */
end_comment

begin_class
annotation|@
name|Provider
annotation|@
name|Produces
argument_list|(
block|{
name|APPLICATION_JSON
block|,
name|N3
block|,
name|N_TRIPLE
block|,
name|RDF_XML
block|,
name|TURTLE
block|,
name|X_TURTLE
block|,
name|RDF_JSON
block|}
argument_list|)
specifier|public
class|class
name|QueryResultListWriter
implements|implements
name|MessageBodyWriter
argument_list|<
name|QueryResultList
argument_list|<
name|?
argument_list|>
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
name|QueryResultListWriter
operator|.
name|class
argument_list|)
decl_stmt|;
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
name|QueryResultList
argument_list|<
name|?
argument_list|>
name|result
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
comment|//TODO: The type is also parsed as genericType ... so we can only check
comment|//for the type :(
return|return
name|QueryResultList
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
return|;
comment|//       if(QueryResultList.class.isAssignableFrom(type)&&
comment|//               genericType != null&&  //QueryResult is always a generic Type
comment|//               genericType instanceof Class<?>){ //and such types do not use generics
comment|//           //This writer supports String, Representation and all types of Signs
comment|//           Class<?> genericClass  = (Class<?>) genericType;
comment|//
comment|//           if(String.class.isAssignableFrom(genericClass) ||
comment|//                   Representation.class.isAssignableFrom(genericClass) ||
comment|//                   Sign.class.isAssignableFrom(genericClass)){
comment|//               //maybe we need further checks if we do not support all data types
comment|//               //for all generic types! But currently all different types of
comment|//               //QueryResultList support all the different MediaTypes!
comment|//               return true;
comment|//           }
comment|//       }
comment|//       log.info("Request for not writeable combination: type="+type+"|genericType="+genericType+"|mediaType="+mediaType);
comment|//       return false;
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeTo
parameter_list|(
name|QueryResultList
argument_list|<
name|?
argument_list|>
name|resultList
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|__doNotUse
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
name|Class
argument_list|<
name|?
argument_list|>
name|genericClass
init|=
operator|(
name|Class
argument_list|<
name|?
argument_list|>
operator|)
name|genericType
decl_stmt|;
if|if
condition|(
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
name|QueryResultsToJSON
operator|.
name|toJSON
argument_list|(
name|resultList
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
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
else|else
block|{
comment|//RDF
comment|/*              * TODO: We would need to add the query to the RDF Result.              *       Currently not implemented, because I do not want to create              *       a triple version of the query and there is not yet String              *       representation defined for FieldQuery              */
name|MGraph
name|resultGraph
init|=
name|QueryResultsToRDF
operator|.
name|toRDF
argument_list|(
name|resultList
argument_list|)
decl_stmt|;
name|getSerializer
argument_list|()
operator|.
name|serialize
argument_list|(
name|entityStream
argument_list|,
name|resultGraph
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

