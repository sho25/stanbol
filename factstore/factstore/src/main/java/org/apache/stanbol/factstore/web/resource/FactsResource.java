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
name|factstore
operator|.
name|web
operator|.
name|resource
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
name|HttpMethod
operator|.
name|GET
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
name|HttpMethod
operator|.
name|POST
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
name|HttpMethod
operator|.
name|PUT
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLEncoder
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
name|Consumes
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
name|GET
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
name|OPTIONS
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
name|POST
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
name|PUT
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
name|Path
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
name|PathParam
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
name|Response
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
name|ResponseBuilder
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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|jsonld
operator|.
name|JsonLd
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
name|jsonld
operator|.
name|JsonLdParser
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
name|jsonld
operator|.
name|JsonLdProfile
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
name|jsonld
operator|.
name|JsonLdProfileParser
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
name|viewable
operator|.
name|Viewable
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
name|CorsHelper
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
name|factstore
operator|.
name|api
operator|.
name|FactStore
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
name|factstore
operator|.
name|model
operator|.
name|Fact
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
name|factstore
operator|.
name|model
operator|.
name|FactSchema
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
name|Path
argument_list|(
literal|"/factstore/facts"
argument_list|)
specifier|public
class|class
name|FactsResource
extends|extends
name|BaseFactStoreResource
block|{
specifier|private
specifier|static
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|FactsResource
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|FactStore
name|factStore
decl_stmt|;
specifier|public
name|FactsResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
name|this
operator|.
name|factStore
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|FactStore
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|OPTIONS
specifier|public
name|Response
name|handleCorsPreflightFacts
parameter_list|(
annotation|@
name|Context
name|HttpHeaders
name|requestHeaders
parameter_list|)
block|{
name|ResponseBuilder
name|res
init|=
name|Response
operator|.
name|ok
argument_list|()
decl_stmt|;
name|CorsHelper
operator|.
name|enableCORS
argument_list|(
name|servletContext
argument_list|,
name|res
argument_list|,
name|requestHeaders
argument_list|,
name|GET
argument_list|,
name|POST
argument_list|)
expr_stmt|;
return|return
name|res
operator|.
name|build
argument_list|()
return|;
block|}
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
specifier|public
name|Response
name|get
parameter_list|(
annotation|@
name|Context
name|HttpHeaders
name|requestHeaders
parameter_list|)
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"index"
argument_list|,
name|this
argument_list|)
argument_list|,
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|)
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
specifier|public
name|Response
name|postFacts
parameter_list|(
name|String
name|jsonLdFacts
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|requestHeaders
parameter_list|)
block|{
name|JsonLd
name|jsonLd
init|=
literal|null
decl_stmt|;
try|try
block|{
name|jsonLd
operator|=
name|JsonLdParser
operator|.
name|parse
argument_list|(
name|jsonLdFacts
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|/* ignore here */
block|}
if|if
condition|(
name|jsonLd
operator|==
literal|null
condition|)
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Could not parse provided JSON-LD structure."
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
name|int
name|factId
init|=
operator|-
literal|1
decl_stmt|;
if|if
condition|(
name|jsonLd
operator|.
name|getResourceSubjects
argument_list|()
operator|.
name|size
argument_list|()
operator|<
literal|2
condition|)
block|{
comment|// post a single fact
name|Fact
name|fact
init|=
name|Fact
operator|.
name|factFromJsonLd
argument_list|(
name|jsonLd
argument_list|)
decl_stmt|;
if|if
condition|(
name|fact
operator|!=
literal|null
condition|)
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Request for posting new fact for {}"
argument_list|,
name|fact
operator|.
name|getFactSchemaURN
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|factId
operator|=
name|this
operator|.
name|factStore
operator|.
name|addFact
argument_list|(
name|fact
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error adding new fact"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Could not extract fact from JSON-LD input."
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
name|String
name|schemaEncoded
init|=
literal|null
decl_stmt|;
try|try
block|{
name|schemaEncoded
operator|=
name|URLEncoder
operator|.
name|encode
argument_list|(
name|fact
operator|.
name|getFactSchemaURN
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Could not encode fact schema URI"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|OK
argument_list|)
operator|.
name|header
argument_list|(
literal|"Location"
argument_list|,
name|this
operator|.
name|getPublicBaseUri
argument_list|()
operator|+
literal|"factstore/facts/"
operator|+
name|schemaEncoded
operator|+
literal|"/"
operator|+
name|factId
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
comment|// post multiple facts
name|Set
argument_list|<
name|Fact
argument_list|>
name|facts
init|=
name|Fact
operator|.
name|factsFromJsonLd
argument_list|(
name|jsonLd
argument_list|)
decl_stmt|;
if|if
condition|(
name|facts
operator|!=
literal|null
condition|)
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Request for posting a set of new facts"
argument_list|)
expr_stmt|;
try|try
block|{
name|this
operator|.
name|factStore
operator|.
name|addFacts
argument_list|(
name|facts
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error adding new facts"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Could not extract facts from JSON-LD input."
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|OK
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
annotation|@
name|OPTIONS
annotation|@
name|Path
argument_list|(
literal|"/{factSchemaURN}"
argument_list|)
specifier|public
name|Response
name|handleCorsPreflightFactSchema
parameter_list|(
annotation|@
name|Context
name|HttpHeaders
name|requestHeaders
parameter_list|)
block|{
name|ResponseBuilder
name|res
init|=
name|Response
operator|.
name|ok
argument_list|()
decl_stmt|;
name|CorsHelper
operator|.
name|enableCORS
argument_list|(
name|servletContext
argument_list|,
name|res
argument_list|,
name|requestHeaders
argument_list|,
name|GET
argument_list|,
name|PUT
argument_list|)
expr_stmt|;
return|return
name|res
operator|.
name|build
argument_list|()
return|;
block|}
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
literal|"/{factSchemaURN}"
argument_list|)
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|)
specifier|public
name|Response
name|getFactSchemaAsJSON
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"factSchemaURN"
argument_list|)
name|String
name|factSchemaURN
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|requestHeaders
parameter_list|)
block|{
name|Response
name|validationResponse
init|=
name|standardValidation
argument_list|(
name|factSchemaURN
argument_list|,
name|requestHeaders
argument_list|)
decl_stmt|;
if|if
condition|(
name|validationResponse
operator|!=
literal|null
condition|)
block|{
return|return
name|validationResponse
return|;
block|}
name|logger
operator|.
name|info
argument_list|(
literal|"Request for getting existing fact schema {}"
argument_list|,
name|factSchemaURN
argument_list|)
expr_stmt|;
name|FactSchema
name|factSchema
init|=
name|this
operator|.
name|factStore
operator|.
name|getFactSchema
argument_list|(
name|factSchemaURN
argument_list|)
decl_stmt|;
if|if
condition|(
name|factSchema
operator|==
literal|null
condition|)
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Could not find fact schema "
operator|+
name|factSchemaURN
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|ok
argument_list|(
name|factSchema
operator|.
name|toJsonLdProfile
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
literal|"/{factSchemaURN}"
argument_list|)
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
specifier|public
name|Response
name|getFactSchemaAsHtml
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"factSchemaURN"
argument_list|)
name|String
name|factSchemaURN
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|requestHeaders
parameter_list|)
block|{
name|Response
name|validationResponse
init|=
name|standardValidation
argument_list|(
name|factSchemaURN
argument_list|,
name|requestHeaders
argument_list|)
decl_stmt|;
if|if
condition|(
name|validationResponse
operator|!=
literal|null
condition|)
block|{
return|return
name|validationResponse
return|;
block|}
name|logger
operator|.
name|info
argument_list|(
literal|"Request for getting existing fact schema {}"
argument_list|,
name|factSchemaURN
argument_list|)
expr_stmt|;
name|FactSchema
name|factSchema
init|=
name|this
operator|.
name|factStore
operator|.
name|getFactSchema
argument_list|(
name|factSchemaURN
argument_list|)
decl_stmt|;
if|if
condition|(
name|factSchema
operator|==
literal|null
condition|)
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Could not find fact schema "
operator|+
name|factSchemaURN
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|model
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|model
operator|.
name|put
argument_list|(
literal|"it"
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|model
operator|.
name|put
argument_list|(
literal|"factSchemaURN"
argument_list|,
name|factSchemaURN
argument_list|)
expr_stmt|;
name|model
operator|.
name|put
argument_list|(
literal|"factschema"
argument_list|,
name|factSchema
operator|.
name|toJsonLdProfile
argument_list|()
operator|.
name|toString
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"factschema"
argument_list|,
name|model
argument_list|)
argument_list|,
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
annotation|@
name|PUT
annotation|@
name|Path
argument_list|(
literal|"/{factSchemaURN}"
argument_list|)
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|)
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
specifier|public
name|Response
name|putFactSchema
parameter_list|(
name|String
name|jsonLdProfileString
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"factSchemaURN"
argument_list|)
name|String
name|factSchemaURN
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|requestHeaders
parameter_list|)
block|{
name|Response
name|validationResponse
init|=
name|standardValidation
argument_list|(
name|factSchemaURN
argument_list|,
name|requestHeaders
argument_list|)
decl_stmt|;
if|if
condition|(
name|validationResponse
operator|!=
literal|null
condition|)
block|{
return|return
name|validationResponse
return|;
block|}
name|logger
operator|.
name|info
argument_list|(
literal|"Request for putting new fact schema {}"
argument_list|,
name|factSchemaURN
argument_list|)
expr_stmt|;
name|JsonLdProfile
name|profile
init|=
literal|null
decl_stmt|;
try|try
block|{
name|profile
operator|=
name|JsonLdProfileParser
operator|.
name|parseProfile
argument_list|(
name|jsonLdProfileString
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|/* ignore this exception here - it was logged by the parser */
block|}
if|if
condition|(
name|profile
operator|==
literal|null
condition|)
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Could not parse provided JSON-LD Profile structure."
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
try|try
block|{
if|if
condition|(
name|this
operator|.
name|factStore
operator|.
name|existsFactSchema
argument_list|(
name|factSchemaURN
argument_list|)
condition|)
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|CONFLICT
argument_list|)
operator|.
name|entity
argument_list|(
literal|"The fact schema "
operator|+
name|factSchemaURN
operator|+
literal|" already exists."
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Error while checking existence of fact schema "
operator|+
name|factSchemaURN
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
try|try
block|{
name|this
operator|.
name|factStore
operator|.
name|createFactSchema
argument_list|(
name|FactSchema
operator|.
name|fromJsonLdProfile
argument_list|(
name|factSchemaURN
argument_list|,
name|profile
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error creating new fact schema {}"
argument_list|,
name|factSchemaURN
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Error while creating new fact in database."
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|CREATED
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
literal|"/{factSchemaURN}/{factId}"
argument_list|)
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|)
specifier|public
name|Response
name|getFact
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"factId"
argument_list|)
name|int
name|factId
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"factSchemaURN"
argument_list|)
name|String
name|factSchemaURN
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|requestHeaders
parameter_list|)
block|{
name|Response
name|validationResponse
init|=
name|standardValidation
argument_list|(
name|factSchemaURN
argument_list|,
name|requestHeaders
argument_list|)
decl_stmt|;
if|if
condition|(
name|validationResponse
operator|!=
literal|null
condition|)
block|{
return|return
name|validationResponse
return|;
block|}
name|logger
operator|.
name|info
argument_list|(
literal|"Request for getting fact {} of schema {}"
argument_list|,
name|factId
argument_list|,
name|factSchemaURN
argument_list|)
expr_stmt|;
name|Fact
name|fact
init|=
literal|null
decl_stmt|;
try|try
block|{
name|fact
operator|=
name|this
operator|.
name|factStore
operator|.
name|getFact
argument_list|(
name|factId
argument_list|,
name|factSchemaURN
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error while loading fact {}"
argument_list|,
name|factId
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Error while loading fact "
operator|+
name|factId
operator|+
literal|" of fact schema "
operator|+
name|factSchemaURN
operator|+
literal|" from database"
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
if|if
condition|(
name|fact
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|"Fact {} for fact schema {} not found"
argument_list|,
name|factId
argument_list|,
name|factSchemaURN
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Could not find fact with ID "
operator|+
name|factId
operator|+
literal|" for fact schema "
operator|+
name|factSchemaURN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
name|JsonLd
name|factAsJsonLd
init|=
name|fact
operator|.
name|factToJsonLd
argument_list|()
decl_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|OK
argument_list|)
operator|.
name|entity
argument_list|(
name|factAsJsonLd
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
block|}
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
literal|"/{factSchemaURN}/{factId}"
argument_list|)
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
specifier|public
name|Response
name|getFactAsHtml
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"factId"
argument_list|)
name|int
name|factId
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"factSchemaURN"
argument_list|)
name|String
name|factSchemaURN
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|requestHeaders
parameter_list|)
block|{
name|Response
name|validationResponse
init|=
name|standardValidation
argument_list|(
name|factSchemaURN
argument_list|,
name|requestHeaders
argument_list|)
decl_stmt|;
if|if
condition|(
name|validationResponse
operator|!=
literal|null
condition|)
block|{
return|return
name|validationResponse
return|;
block|}
name|logger
operator|.
name|info
argument_list|(
literal|"Request for getting fact {} of schema {}"
argument_list|,
name|factId
argument_list|,
name|factSchemaURN
argument_list|)
expr_stmt|;
name|Fact
name|fact
init|=
literal|null
decl_stmt|;
try|try
block|{
name|fact
operator|=
name|this
operator|.
name|factStore
operator|.
name|getFact
argument_list|(
name|factId
argument_list|,
name|factSchemaURN
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error while loading fact {}"
argument_list|,
name|factId
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Error while loading fact "
operator|+
name|factId
operator|+
literal|" of fact schema "
operator|+
name|factSchemaURN
operator|+
literal|" from database"
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
if|if
condition|(
name|fact
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|"Fact {} for fact schema {} not found"
argument_list|,
name|factId
argument_list|,
name|factSchemaURN
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Could not find fact with ID "
operator|+
name|factId
operator|+
literal|" for fact schema "
operator|+
name|factSchemaURN
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
name|JsonLd
name|factAsJsonLd
init|=
name|fact
operator|.
name|factToJsonLd
argument_list|()
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<html><body>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<pre>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|factAsJsonLd
operator|.
name|toString
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</pre>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</body></html>"
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|OK
argument_list|)
operator|.
name|entity
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
block|}
specifier|private
name|Response
name|standardValidation
parameter_list|(
name|String
name|factSchemaURN
parameter_list|,
name|HttpHeaders
name|requestHeaders
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|factStore
operator|==
literal|null
condition|)
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
literal|"The FactStore is not configured properly"
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
if|if
condition|(
name|factSchemaURN
operator|==
literal|null
operator|||
name|factSchemaURN
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"No fact schema URN specified."
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
if|if
condition|(
name|factSchemaURN
operator|.
name|length
argument_list|()
operator|>
name|this
operator|.
name|factStore
operator|.
name|getMaxFactSchemaURNLength
argument_list|()
condition|)
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"The fact schema URN "
operator|+
name|factSchemaURN
operator|+
literal|" is too long. A maximum of "
operator|+
name|this
operator|.
name|factStore
operator|.
name|getMaxFactSchemaURNLength
argument_list|()
operator|+
literal|" characters is allowed"
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|requestHeaders
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

