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
name|httpqueryheaders
operator|.
name|it
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|it
operator|.
name|DbpediaDefaultdataConstants
operator|.
name|DBPEDIA_SITE_ID
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|it
operator|.
name|DbpediaDefaultdataConstants
operator|.
name|DBPEDIA_SITE_PATH
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|test
operator|.
name|it
operator|.
name|AssertEntityhubJson
operator|.
name|assertEntity
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
name|util
operator|.
name|Collections
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
name|entityhub
operator|.
name|it
operator|.
name|DbpediaDefaultdataConstants
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
name|test
operator|.
name|it
operator|.
name|EntityhubTestBase
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
name|junit
operator|.
name|Test
import|;
end_import

begin_comment
comment|/**  * Test overriding of Accept headers by using the Entityhub dbpedia.org  * referenced site  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|HttpQueryHeaderGetTest
extends|extends
name|EntityhubTestBase
block|{
specifier|public
name|HttpQueryHeaderGetTest
parameter_list|()
block|{
name|super
argument_list|(
name|Collections
operator|.
name|singleton
argument_list|(
name|DbpediaDefaultdataConstants
operator|.
name|DBPEDIA_SITE_ID
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Validates that parsing rdf+xml normally as Accept header and that      * JSON is the default if no header is parsed.       * NOTE: This does not actually test the http query header, but checks       * the assumptions behind all the following tests.      * @throws IOException      * @throws JSONException       */
annotation|@
name|Test
specifier|public
name|void
name|testGetAccept
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//first a normal request with application/rdf+xml
name|String
name|id
init|=
literal|"http://dbpedia.org/resource/Paris"
decl_stmt|;
name|RequestExecutor
name|re
init|=
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|DBPEDIA_SITE_PATH
operator|+
literal|"/entity"
argument_list|,
literal|"id"
argument_list|,
name|id
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/rdf+xml"
argument_list|)
argument_list|)
decl_stmt|;
name|re
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|re
operator|.
name|assertContentType
argument_list|(
literal|"application/rdf+xml"
argument_list|)
expr_stmt|;
name|re
operator|.
name|assertContentContains
argument_list|(
literal|"<rdf:Description rdf:about=\"http://dbpedia.org/resource/Paris\">"
argument_list|,
literal|"<rdfs:label xml:lang=\"en\">Paris</rdfs:label>"
argument_list|)
expr_stmt|;
comment|//now test the default Accept
name|re
operator|=
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|DBPEDIA_SITE_PATH
operator|+
literal|"/entity"
argument_list|,
literal|"id"
argument_list|,
name|id
argument_list|)
argument_list|)
expr_stmt|;
name|re
operator|.
name|assertContentType
argument_list|(
literal|"application/json"
argument_list|)
expr_stmt|;
name|re
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|assertEntity
argument_list|(
name|re
operator|.
name|getContent
argument_list|()
argument_list|,
name|id
argument_list|,
name|DBPEDIA_SITE_ID
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSetAccept
parameter_list|()
throws|throws
name|IOException
block|{
comment|//first a normal request with application/rdf+xml
name|String
name|id
init|=
literal|"http://dbpedia.org/resource/Paris"
decl_stmt|;
name|RequestExecutor
name|re
init|=
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|DBPEDIA_SITE_PATH
operator|+
literal|"/entity"
argument_list|,
literal|"id"
argument_list|,
name|id
argument_list|,
literal|"header_Accept"
argument_list|,
literal|"text/rdf+nt"
argument_list|)
argument_list|)
decl_stmt|;
comment|//parse the rdf+nt format as query parameter
name|re
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|re
operator|.
name|assertContentType
argument_list|(
literal|"text/rdf+nt"
argument_list|)
expr_stmt|;
name|re
operator|.
name|assertContentContains
argument_list|(
literal|"<http://dbpedia.org/resource/Paris> "
operator|+
literal|"<http://www.w3.org/2000/01/rdf-schema#label> "
operator|+
literal|"\"Paris\"@en ."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testOverrideAccept
parameter_list|()
throws|throws
name|IOException
block|{
comment|//first a normal request with application/rdf+xml
name|String
name|id
init|=
literal|"http://dbpedia.org/resource/Paris"
decl_stmt|;
name|RequestExecutor
name|re
init|=
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|DBPEDIA_SITE_PATH
operator|+
literal|"/entity"
argument_list|,
literal|"id"
argument_list|,
name|id
argument_list|,
literal|"header_Accept"
argument_list|,
literal|"text/rdf+nt"
argument_list|)
comment|//parse the rdf+nt format as query parameter
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/rdf+xml"
argument_list|)
argument_list|)
decl_stmt|;
comment|//MUST override the rdf+xml
name|re
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|re
operator|.
name|assertContentType
argument_list|(
literal|"text/rdf+nt"
argument_list|)
expr_stmt|;
name|re
operator|.
name|assertContentContains
argument_list|(
literal|"<http://dbpedia.org/resource/Paris> "
operator|+
literal|"<http://www.w3.org/2000/01/rdf-schema#label> "
operator|+
literal|"\"Paris\"@en ."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testRemovalOfAccept
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//now test the removal of headers
comment|//first a normal request with application/rdf+xml
name|String
name|id
init|=
literal|"http://dbpedia.org/resource/Paris"
decl_stmt|;
name|RequestExecutor
name|re
init|=
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|DBPEDIA_SITE_PATH
operator|+
literal|"/entity"
argument_list|,
literal|"id"
argument_list|,
name|id
argument_list|,
literal|"header_Accept"
argument_list|,
literal|""
argument_list|)
comment|//empty value to remove
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/rdf+xml"
argument_list|)
argument_list|)
decl_stmt|;
comment|//MUST override the rdf+xml
name|re
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
comment|//The default format (JSON) is expected
name|assertEntity
argument_list|(
name|re
operator|.
name|getContent
argument_list|()
argument_list|,
name|id
argument_list|,
name|DBPEDIA_SITE_ID
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

