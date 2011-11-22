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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|Assert
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
name|FileUtils
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
name|HttpGet
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
name|HttpPost
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
name|Request
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
name|junit
operator|.
name|AfterClass
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
name|FactStoreTest
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
name|FactStoreTest
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|cleanDatabase
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|workingDirName
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"jar.executor.workingdirectory"
argument_list|)
decl_stmt|;
if|if
condition|(
name|workingDirName
operator|!=
literal|null
condition|)
block|{
name|File
name|workingDir
init|=
operator|new
name|File
argument_list|(
name|workingDirName
argument_list|)
decl_stmt|;
name|File
name|factstore
init|=
operator|new
name|File
argument_list|(
name|workingDir
argument_list|,
literal|"factstore"
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Deleting integration test FactStore "
operator|+
name|factstore
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
name|FileUtils
operator|.
name|deleteDirectory
argument_list|(
name|factstore
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|maximumSchemaURNLength
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.test.de/this/urn/is/a/bit/too/long/to/be/used/in/this/fact/store/implementation/with/derby"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"@types\":{\"organization\":\"iks:organization\",\"person\":\"iks:person\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|400
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|createSimpleFactSchema
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r
init|=
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
literal|"/factstore/facts/TestFactSchema"
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"@types\":{\"organization\":\"iks:organization\",\"person\":\"iks:person\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|createURNFactSchema
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.iks-project.eu/ont/test"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"@types\":{\"organization\":\"iks:organization\",\"person\":\"iks:person\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|getFactSchemaByURN
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r1
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.iks-project.eu/ont/test2"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"@types\":{\"organization\":\"iks:organization\",\"person\":\"iks:person\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r1
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|Request
name|r2
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpGet
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.iks-project.eu/ont/test2"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|String
name|actual
init|=
name|executor
operator|.
name|execute
argument_list|(
name|r2
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|getContent
argument_list|()
decl_stmt|;
name|String
name|expected
init|=
literal|"{\"@context\":{\"@types\":{\"organization\":\"http://iks-project.eu/ont/organization\",\"person\":\"http://iks-project.eu/ont/person\"}}}"
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|doubleCreateFactSchema
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.iks-project.eu/ont/double"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"@types\":{\"organization\":\"iks:organization\",\"person\":\"iks:person\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|409
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|createSchemaMultiTypes
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.schema.org/attendees"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"@types\":{\"organization\":\"http://iks-project.eu/ont/organization\",\"person\":[\"http://iks-project.eu/ont/person\",\"http://www.schema.org/Person\"]}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|getSchemaMultiTypes
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r1
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.schema.org/Event.attendees"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"@types\":{\"organization\":\"http://iks-project.eu/ont/organization\",\"person\":[\"http://iks-project.eu/ont/person\",\"http://www.schema.org/Person\"]}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r1
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|Request
name|r2
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpGet
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.schema.org/Event.attendees"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|String
name|actual
init|=
name|executor
operator|.
name|execute
argument_list|(
name|r2
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|getContent
argument_list|()
decl_stmt|;
name|String
name|expected
init|=
literal|"{\"@context\":{\"@types\":{\"organization\":\"http://iks-project.eu/ont/organization\",\"person\":[\"http://iks-project.eu/ont/person\",\"http://www.schema.org/Person\"]}}}"
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|postSingleFact
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r1
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://iks-project.eu/ont/employeeOf1"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"@types\":{\"person\":\"iks:person\",\"organization\":\"iks:organization\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r1
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|Request
name|r2
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPost
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"upb\":\"http://upb.de/persons/\"},\"@profile\":\"iks:employeeOf1\",\"person\":{\"@iri\":\"upb:bnagel\"},\"organization\":{\"@iri\":\"http://uni-paderborn.de\"}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r2
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertHeader
argument_list|(
literal|"Location"
argument_list|,
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/http%3A%2F%2Fiks-project.eu%2Font%2FemployeeOf1/1"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|postSingleFactNeg
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r1
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://iks-project.eu/ont/employeeOf1Neg"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"@types\":{\"person\":\"iks:person\",\"organization\":\"iks:organization\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r1
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|Request
name|r2
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPost
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"upb\":\"http://upb.de/persons/\"},\"@profile\":\"iks:employeeOf1Wrong\",\"person\":{\"@iri\":\"upb:bnagel\"},\"organization\":{\"@iri\":\"http://uni-paderborn.de\"}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r2
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|500
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|postSingleFactNeg2
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r1
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://iks-project.eu/ont/employeeOf2Neg"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"@types\":{\"person\":\"iks:person\",\"organization\":\"iks:organization\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r1
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|Request
name|r2
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPost
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"upb\":\"http://upb.de/persons/\"},\"@profile\":\"iks:employeeOf2Neg\",\"people\":{\"@iri\":\"upb:bnagel\"},\"organization\":{\"@iri\":\"http://uni-paderborn.de\"}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r2
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|500
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|postMultiFactsMultiTypes
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r1
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://iks-project.eu/ont/employeeOf2"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"@types\":{\"person\":\"iks:person\",\"organization\":\"iks:organization\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r1
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|Request
name|r2
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://iks-project.eu/ont/friendOf2"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"@types\":{\"person\":\"iks:person\",\"friend\":\"iks:person\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r2
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|Request
name|r3
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPost
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"upb\":\"http://upb.de/persons/\"},\"@subject\":[{\"@profile\":\"iks:employeeOf2\",\"person\":{\"@iri\":\"upb:bnagel\"},\"organization\":{\"@iri\":\"http://uni-paderborn.de\"}},{\"@profile\":\"iks:friendOf2\",\"person\":{\"@iri\":\"upb:bnagel\"},\"friend\":{\"@iri\":\"upb:fchrist\"}}]}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r3
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|postMultiFactsMultiTypesNeg
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r1
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://iks-project.eu/ont/employeeOf3Neg"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"@types\":{\"person\":\"iks:person\",\"organization\":\"iks:organization\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r1
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|Request
name|r2
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://iks-project.eu/ont/friendOf3Neg"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"@types\":{\"person\":\"iks:person\",\"friend\":\"iks:person\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r2
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|Request
name|r3
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPost
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"upb\":\"http://upb.de/persons/\"},\"@subject\":[{\"@profile\":\"iks:employeeOf3NegWrong\",\"person\":{\"@iri\":\"upb:bnagel\"},\"organization\":{\"@iri\":\"http://uni-paderborn.de\"}},{\"@profile\":\"iks:friendOf3Neg\",\"person\":{\"@iri\":\"upb:bnagel\"},\"friend\":{\"@iri\":\"upb:fchrist\"}}]}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r3
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|500
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|querySingleFact
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r1
init|=
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
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://iks-project.eu/ont/employeeOf"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"@types\":{\"person\":\"iks:person\",\"organization\":\"iks:organization\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r1
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|Request
name|r2
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPost
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"upb\":\"http://upb.de/persons/\"},\"@profile\":\"iks:employeeOf\",\"person\":{\"@iri\":\"upb:bnagel\"},\"organization\":{\"@iri\":\"http://upb.de\"}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r2
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|String
name|queryString
init|=
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\"},\"select\":[\"person\"],\"from\":\"iks:employeeOf\",\"where\":[{\"=\":{\"organization\":{\"@iri\":\"http://upb.de\"}}}]}"
decl_stmt|;
name|Request
name|q
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPost
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/query/"
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
name|queryString
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|String
name|expected
init|=
literal|"{\"person\":\"http://upb.de/persons/bnagel\"}"
decl_stmt|;
name|String
name|actual
init|=
name|executor
operator|.
name|execute
argument_list|(
name|q
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|getContent
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
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
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|private
name|void
name|toConsole
parameter_list|(
name|String
name|actual
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|actual
argument_list|)
expr_stmt|;
name|String
name|s
init|=
name|actual
decl_stmt|;
name|s
operator|=
name|s
operator|.
name|replaceAll
argument_list|(
literal|"\\\\"
argument_list|,
literal|"\\\\\\\\"
argument_list|)
expr_stmt|;
name|s
operator|=
name|s
operator|.
name|replace
argument_list|(
literal|"\""
argument_list|,
literal|"\\\""
argument_list|)
expr_stmt|;
name|s
operator|=
name|s
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|"\\n"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

