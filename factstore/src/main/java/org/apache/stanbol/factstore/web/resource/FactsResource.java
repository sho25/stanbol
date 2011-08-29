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
name|core
operator|.
name|MediaType
operator|.
name|TEXT_HTML
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
name|resource
operator|.
name|BaseStanbolResource
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

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|view
operator|.
name|Viewable
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
name|BaseStanbolResource
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
parameter_list|()
block|{
return|return
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
name|TEXT_HTML
argument_list|)
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
name|getFactSchema
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"factSchemaURN"
argument_list|)
name|String
name|factSchemaURN
parameter_list|)
block|{
name|Response
name|validationResponse
init|=
name|standardValidation
argument_list|(
name|factSchemaURN
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
return|return
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
name|build
argument_list|()
return|;
block|}
return|return
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
argument_list|)
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
parameter_list|)
block|{
name|Response
name|validationResponse
init|=
name|standardValidation
argument_list|(
name|factSchemaURN
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
return|return
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
return|return
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
return|return
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
return|return
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
name|CREATED
argument_list|)
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
return|return
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
name|build
argument_list|()
return|;
block|}
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
return|return
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
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
return|return
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
name|build
argument_list|()
return|;
block|}
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
return|return
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
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
return|return
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
name|build
argument_list|()
return|;
block|}
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
name|build
argument_list|()
return|;
block|}
specifier|private
name|Response
name|standardValidation
parameter_list|(
name|String
name|factSchemaURN
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
return|return
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
return|return
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
return|return
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

