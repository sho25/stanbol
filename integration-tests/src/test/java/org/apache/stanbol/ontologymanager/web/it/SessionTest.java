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
name|ontologymanager
operator|.
name|web
operator|.
name|it
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|methods
operator|.
name|HttpDelete
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|methods
operator|.
name|HttpPut
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
name|testing
operator|.
name|http
operator|.
name|RequestExecutor
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
name|testing
operator|.
name|stanbol
operator|.
name|StanbolTestBase
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
name|format
operator|.
name|KRFormat
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
specifier|public
class|class
name|SessionTest
extends|extends
name|StanbolTestBase
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SessionTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ROOT_URI
init|=
literal|"/ontonet"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SESSION_URI
init|=
name|ROOT_URI
operator|+
literal|"/session"
decl_stmt|;
specifier|private
name|String
name|encodeURI
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|StringBuilder
name|o
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|char
name|ch
range|:
name|s
operator|.
name|toCharArray
argument_list|()
control|)
block|{
if|if
condition|(
name|isUnsafe
argument_list|(
name|ch
argument_list|)
condition|)
block|{
name|o
operator|.
name|append
argument_list|(
literal|'%'
argument_list|)
expr_stmt|;
name|o
operator|.
name|append
argument_list|(
name|toHex
argument_list|(
name|ch
operator|/
literal|16
argument_list|)
argument_list|)
expr_stmt|;
name|o
operator|.
name|append
argument_list|(
name|toHex
argument_list|(
name|ch
operator|%
literal|16
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
name|o
operator|.
name|append
argument_list|(
name|ch
argument_list|)
expr_stmt|;
block|}
return|return
name|o
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
name|boolean
name|isUnsafe
parameter_list|(
name|char
name|ch
parameter_list|)
block|{
if|if
condition|(
name|ch
operator|>
literal|128
operator|||
name|ch
operator|<
literal|0
condition|)
return|return
literal|true
return|;
return|return
literal|" %$&+,/:;=?@<>#%"
operator|.
name|indexOf
argument_list|(
name|ch
argument_list|)
operator|>=
literal|0
return|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testCRUD
parameter_list|()
throws|throws
name|Exception
block|{
name|RequestExecutor
name|request
decl_stmt|;
comment|// The needed Web resources to GET from.
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|SESSION_URI
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
name|KRFormat
operator|.
name|TURTLE
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Request: "
operator|+
name|SESSION_URI
operator|+
literal|" ... DONE"
argument_list|)
expr_stmt|;
name|String
name|tempScopeUri
init|=
name|SESSION_URI
operator|+
literal|"/"
operator|+
name|getClass
argument_list|()
operator|.
name|getCanonicalName
argument_list|()
operator|+
literal|"-"
operator|+
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
comment|// Scope should not be there
name|request
operator|=
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|tempScopeUri
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
name|KRFormat
operator|.
name|TURTLE
argument_list|)
argument_list|)
expr_stmt|;
name|request
operator|.
name|assertStatus
argument_list|(
literal|404
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Request: "
operator|+
name|tempScopeUri
operator|+
literal|" (should return 404) ... DONE"
argument_list|)
expr_stmt|;
comment|// Create scope
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPut
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
name|tempScopeUri
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"PUT Request: "
operator|+
name|tempScopeUri
operator|+
literal|" ... DONE"
argument_list|)
expr_stmt|;
comment|// Scope should be there now
name|request
operator|=
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|tempScopeUri
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
name|KRFormat
operator|.
name|TURTLE
argument_list|)
argument_list|)
expr_stmt|;
name|request
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentContains
argument_list|(
name|tempScopeUri
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Request: "
operator|+
name|tempScopeUri
operator|+
literal|" ... DONE"
argument_list|)
expr_stmt|;
comment|// TODO the U of CRUD
comment|// Delete scope
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpDelete
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
name|tempScopeUri
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"DELETE Request: "
operator|+
name|tempScopeUri
operator|+
literal|" ... DONE"
argument_list|)
expr_stmt|;
comment|// Scope should not be there
name|request
operator|=
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|tempScopeUri
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
name|KRFormat
operator|.
name|TURTLE
argument_list|)
argument_list|)
expr_stmt|;
name|request
operator|.
name|assertStatus
argument_list|(
literal|404
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Request: "
operator|+
name|tempScopeUri
operator|+
literal|" (should return 404) ... DONE"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testLocking
parameter_list|()
throws|throws
name|Exception
block|{
comment|// TODO first we need some offline content to POST
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSupportedOWLFormats
parameter_list|()
throws|throws
name|Exception
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|SESSION_URI
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
name|KRFormat
operator|.
name|OWL_XML
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Request: "
operator|+
name|SESSION_URI
operator|+
literal|" (Accept: "
operator|+
name|KRFormat
operator|.
name|OWL_XML
operator|+
literal|")"
operator|+
literal|" ... DONE"
argument_list|)
expr_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|SESSION_URI
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
name|KRFormat
operator|.
name|MANCHESTER_OWL
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Request: "
operator|+
name|SESSION_URI
operator|+
literal|" (Accept: "
operator|+
name|KRFormat
operator|.
name|MANCHESTER_OWL
operator|+
literal|")"
operator|+
literal|" ... DONE"
argument_list|)
expr_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|SESSION_URI
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
name|KRFormat
operator|.
name|FUNCTIONAL_OWL
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Request: "
operator|+
name|SESSION_URI
operator|+
literal|" (Accept: "
operator|+
name|KRFormat
operator|.
name|FUNCTIONAL_OWL
operator|+
literal|")"
operator|+
literal|" ... DONE"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSupportedRDFFormats
parameter_list|()
throws|throws
name|Exception
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|SESSION_URI
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
name|KRFormat
operator|.
name|RDF_XML
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Request: "
operator|+
name|SESSION_URI
operator|+
literal|" (Accept: "
operator|+
name|KRFormat
operator|.
name|RDF_XML
operator|+
literal|")"
operator|+
literal|" ... DONE"
argument_list|)
expr_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|SESSION_URI
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
name|KRFormat
operator|.
name|RDF_JSON
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Request: "
operator|+
name|SESSION_URI
operator|+
literal|" (Accept: "
operator|+
name|KRFormat
operator|.
name|RDF_JSON
operator|+
literal|")"
operator|+
literal|" ... DONE"
argument_list|)
expr_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|SESSION_URI
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
name|KRFormat
operator|.
name|TURTLE
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Request: "
operator|+
name|SESSION_URI
operator|+
literal|" (Accept: "
operator|+
name|KRFormat
operator|.
name|TURTLE
operator|+
literal|")"
operator|+
literal|" ... DONE"
argument_list|)
expr_stmt|;
block|}
specifier|private
name|char
name|toHex
parameter_list|(
name|int
name|ch
parameter_list|)
block|{
return|return
call|(
name|char
call|)
argument_list|(
name|ch
operator|<
literal|10
condition|?
literal|'0'
operator|+
name|ch
else|:
literal|'A'
operator|+
name|ch
operator|-
literal|10
argument_list|)
return|;
block|}
block|}
end_class

end_unit

