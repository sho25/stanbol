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
name|QueryParam
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
name|Query
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
name|FactResultSet
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
literal|"factstore/query"
argument_list|)
specifier|public
class|class
name|QueryResource
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
name|QueryResource
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
name|QueryResource
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
name|APPLICATION_JSON
argument_list|)
specifier|public
name|Response
name|query
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
literal|"q"
argument_list|)
name|String
name|q
parameter_list|)
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Query for fact: {}"
argument_list|,
name|q
argument_list|)
expr_stmt|;
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
name|q
operator|==
literal|null
operator|||
name|q
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
literal|"No query sent."
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|JsonLd
name|jsonLdQuery
init|=
literal|null
decl_stmt|;
try|try
block|{
name|jsonLdQuery
operator|=
name|JsonLdParser
operator|.
name|parse
argument_list|(
name|q
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
name|info
argument_list|(
literal|"Could not parse query"
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
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Could not parse query: "
operator|+
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
name|Query
name|query
init|=
literal|null
decl_stmt|;
try|try
block|{
name|query
operator|=
name|Query
operator|.
name|toQueryFromJsonLd
argument_list|(
name|jsonLdQuery
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
name|info
argument_list|(
literal|"Could not extract Query from JSON-LD"
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
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Could not extract FactStore query from JSON-LD: "
operator|+
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
name|FactResultSet
name|rs
init|=
literal|null
decl_stmt|;
try|try
block|{
name|rs
operator|=
name|this
operator|.
name|factStore
operator|.
name|query
argument_list|(
name|query
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
name|info
argument_list|(
literal|"Error while performing the query."
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
literal|"Error while performing the query. "
operator|+
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
if|if
condition|(
name|rs
operator|!=
literal|null
condition|)
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
name|rs
operator|.
name|toJSON
argument_list|()
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|Response
operator|.
name|ok
argument_list|()
operator|.
name|build
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

