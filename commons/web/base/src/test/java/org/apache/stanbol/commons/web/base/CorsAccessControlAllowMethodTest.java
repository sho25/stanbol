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
name|commons
operator|.
name|web
operator|.
name|base
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
name|OPTIONS
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
name|util
operator|.
name|Arrays
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|HttpMethod
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
name|Cookie
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
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
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
name|core
operator|.
name|util
operator|.
name|MultivaluedMapImpl
import|;
end_import

begin_comment
comment|/**  * Tests issue reported/fix for STANBOL-616  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|CorsAccessControlAllowMethodTest
block|{
annotation|@
name|Test
specifier|public
name|void
name|testAccessControlAllowMethodTest
parameter_list|()
block|{
name|ServletContext
name|context
init|=
operator|new
name|MockServletContext
argument_list|()
decl_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
name|JerseyEndpoint
operator|.
name|CORS_ORIGIN
argument_list|,
name|Collections
operator|.
name|singleton
argument_list|(
literal|"*"
argument_list|)
argument_list|)
expr_stmt|;
name|MultivaluedMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|header
init|=
operator|new
name|MultivaluedMapImpl
argument_list|()
decl_stmt|;
name|header
operator|.
name|add
argument_list|(
literal|"Origin"
argument_list|,
literal|"https://issues.apache.org/jira/browse/STANBOL-616"
argument_list|)
expr_stmt|;
name|header
operator|.
name|put
argument_list|(
literal|"Access-Control-Request-Headers"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Origin"
argument_list|,
literal|"Content-Type"
argument_list|,
literal|"Accept"
argument_list|)
argument_list|)
expr_stmt|;
name|header
operator|.
name|add
argument_list|(
literal|"Access-Control-Request-Method"
argument_list|,
literal|"PUT"
argument_list|)
expr_stmt|;
name|HttpHeaders
name|requestHeaders
init|=
operator|new
name|MockHttpHeaders
argument_list|(
name|header
argument_list|)
decl_stmt|;
name|ResponseBuilder
name|builder
init|=
name|Response
operator|.
name|ok
argument_list|(
literal|"Test"
argument_list|)
decl_stmt|;
name|CorsHelper
operator|.
name|enableCORS
argument_list|(
name|context
argument_list|,
name|builder
argument_list|,
name|requestHeaders
argument_list|,
name|OPTIONS
argument_list|,
name|GET
argument_list|,
name|POST
argument_list|,
name|PUT
argument_list|)
expr_stmt|;
name|Response
name|response
init|=
name|builder
operator|.
name|build
argument_list|()
decl_stmt|;
name|MultivaluedMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|metadata
init|=
name|response
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"'Access-Control-Allow-Headers' expected"
argument_list|,
name|metadata
operator|.
name|containsKey
argument_list|(
literal|"Access-Control-Allow-Headers"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|value
init|=
operator|(
name|String
operator|)
name|metadata
operator|.
name|getFirst
argument_list|(
literal|"Access-Control-Allow-Headers"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"'Access-Control-Allow-Headers' does not contain the expected values"
argument_list|,
name|value
operator|.
name|contains
argument_list|(
literal|"Origin"
argument_list|)
operator|&&
name|value
operator|.
name|contains
argument_list|(
literal|"Content-Type"
argument_list|)
operator|&&
name|value
operator|.
name|contains
argument_list|(
literal|"Accept"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"'Access-Control-Allow-Origin' expected"
argument_list|,
name|metadata
operator|.
name|containsKey
argument_list|(
literal|"Access-Control-Allow-Origin"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"'Access-Control-Allow-Origin' does not have the expected value '*'"
argument_list|,
literal|"*"
argument_list|,
name|metadata
operator|.
name|getFirst
argument_list|(
literal|"Access-Control-Allow-Origin"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"'Access-Control-Allow-Methods' expected"
argument_list|,
name|metadata
operator|.
name|containsKey
argument_list|(
literal|"Access-Control-Allow-Methods"
argument_list|)
argument_list|)
expr_stmt|;
name|value
operator|=
operator|(
name|String
operator|)
name|metadata
operator|.
name|getFirst
argument_list|(
literal|"Access-Control-Allow-Methods"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"'Access-Control-Allow-Methods' does not contain the expected values"
argument_list|,
name|value
operator|.
name|contains
argument_list|(
name|OPTIONS
argument_list|)
operator|&&
name|value
operator|.
name|contains
argument_list|(
name|GET
argument_list|)
operator|&&
name|value
operator|.
name|contains
argument_list|(
name|POST
argument_list|)
operator|&&
name|value
operator|.
name|contains
argument_list|(
name|PUT
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

