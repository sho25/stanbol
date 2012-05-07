begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements. See the NOTICE file distributed with this  * work for additional information regarding copyright ownership. The ASF  * licenses this file to You under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the  * License for the specific language governing permissions and limitations under  * the License.  */
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
name|it
package|;
end_package

begin_import
import|import static
name|junit
operator|.
name|framework
operator|.
name|Assert
operator|.
name|assertNotSame
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
name|ArrayList
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
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|HttpEntityEnclosingRequest
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
name|http
operator|.
name|client
operator|.
name|methods
operator|.
name|HttpRequestBase
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
name|entity
operator|.
name|InputStreamEntity
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
name|servicesapi
operator|.
name|defaults
operator|.
name|NamespaceEnum
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
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|test
operator|.
name|query
operator|.
name|FieldQueryTestCase
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
name|query
operator|.
name|FindQueryTestCase
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
name|query
operator|.
name|QueryTestBase
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
name|JSONArray
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
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONObject
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

begin_comment
comment|/**  * Tests the RESTful service provided by the Entityhub. Note that this   * extends the QueryTestBase. By that generic tests for the query interface  * (e.g. illegal requests, usage of default values ...) are already covered.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|EntityhubTest
extends|extends
name|QueryTestBase
block|{
specifier|public
name|EntityhubTest
parameter_list|()
block|{
name|super
argument_list|(
literal|"/entityhub"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
comment|/**      * TODO: define      */
specifier|public
specifier|static
specifier|final
name|String
name|SINGLE_IMPORT_ENTITY_ID
init|=
literal|""
decl_stmt|;
annotation|@
name|Override
specifier|protected
name|String
name|getDefaultFindQueryField
parameter_list|()
block|{
return|return
name|NamespaceEnum
operator|.
name|entityhub
operator|+
literal|"label"
return|;
block|}
comment|/*      * First the CRUD interface      */
comment|//    @Test
specifier|public
name|void
name|testEntityCreation
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|singleImportResource
init|=
literal|""
decl_stmt|;
comment|//create a POST request with a test RDF file
name|RequestExecutor
name|test
init|=
name|executor
operator|.
name|execute
argument_list|(
name|buildImportRdfData
argument_list|(
name|singleImportResource
argument_list|,
literal|true
argument_list|,
name|SINGLE_IMPORT_ENTITY_ID
argument_list|)
argument_list|)
decl_stmt|;
comment|//assert that the entity was created
name|test
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
comment|//NOTE: the check for the imported ID(s) is part of the
comment|// testEntityRetrieval test Method
block|}
comment|//    @Test
specifier|public
name|void
name|testMultipleEntityCreation
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|multipleImportResource
init|=
literal|""
decl_stmt|;
comment|//create a POST request with a test RDF file
name|RequestExecutor
name|test
init|=
name|executor
operator|.
name|execute
argument_list|(
name|buildImportRdfData
argument_list|(
name|multipleImportResource
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|)
argument_list|)
decl_stmt|;
comment|//assert that the entity was created
name|test
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
comment|//NOTE: the check for the imported ID(s) is part of the
comment|// testEntityRetrieval test Method
block|}
comment|//    @Test
specifier|public
name|void
name|testEntityRetrieval
parameter_list|()
block|{
comment|//make a lookup for ID(s) of entities imported by the
comment|//testEntityCreation and testMultipleEntityCreation test method
block|}
comment|//    @Test
specifier|public
name|void
name|testEntityUpdates
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|singleUpdateResource
init|=
literal|""
decl_stmt|;
comment|//create a POST request with a test RDF file that contains updated data
comment|//of the one used for testEntityCreation
comment|//create a POST request with a test RDF file
name|RequestExecutor
name|test
init|=
name|executor
operator|.
name|execute
argument_list|(
name|buildImportRdfData
argument_list|(
name|singleUpdateResource
argument_list|,
literal|false
argument_list|,
name|SINGLE_IMPORT_ENTITY_ID
argument_list|)
argument_list|)
decl_stmt|;
name|test
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
comment|//TODO: validate that the entity was updated
block|}
comment|//    @Test
specifier|public
name|void
name|testMultipleEntityUpdates
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|nultipleUpdateResource
init|=
literal|""
decl_stmt|;
comment|//create a POST request with a test RDF file that contains updated data
comment|//of the one used for testMultipleEntityCreation
name|RequestExecutor
name|test
init|=
name|executor
operator|.
name|execute
argument_list|(
name|buildImportRdfData
argument_list|(
name|nultipleUpdateResource
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
argument_list|)
decl_stmt|;
name|test
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
comment|//TODO: validate that the entity was updated
comment|//check for the updates to be applied
comment|//check the metadata to be updated (creation date != modification date)
block|}
comment|//    @Test
specifier|public
name|void
name|testEntityDeletion
parameter_list|()
block|{
comment|// delete an entity previously imported
comment|// by making a retrieval for this ID check that the removal was successfull
block|}
comment|//    @Test
specifier|public
name|void
name|testEntityLookup
parameter_list|()
block|{
comment|//lookup is importing entities from referenced sites
comment|//lookup entities from the dbpedia site also used by the other integration
comment|//tests
block|}
comment|//    @Test
specifier|public
name|void
name|testEntityImport
parameter_list|()
block|{
comment|//import some entities from referenced sites
comment|//here we shall also use some entities from the dbpedia referenced site
block|}
comment|//    @Test
specifier|public
name|void
name|testFindNameQuery
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//typical find by name query tests bases on created and imported entities
block|}
comment|//    @Test
specifier|public
name|void
name|testFindLimitAndOffsetQuery
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//typical tests based on created and imported entities
block|}
comment|//    @Test
specifier|public
name|void
name|testFindLanguageQuery
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//typical tests based on created and imported entities
block|}
comment|//    @Test
specifier|public
name|void
name|testFindWildcards
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//typical tests based on created and imported entities
block|}
comment|//    @Test
specifier|public
name|void
name|testFindSpecificFieldQuery
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//typical tests based on created and imported entities
block|}
comment|//    @Test
specifier|public
name|void
name|testFieldQueryRangeConstraints
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//typical tests based on created and imported entities
block|}
comment|//    @Test
specifier|public
name|void
name|testFieldQueryTextConstraints
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//typical tests based on created and imported entities
block|}
comment|//    @Test
specifier|public
name|void
name|testFieldQueryValueConstraints
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//typical tests based on created and imported entities
block|}
comment|/**      * Imports/updates RDF data of the file to the entityhub with the possibility      * to restrict imports/updates to the parsed uri      * @param file the file with the RDF data (needs to be in the classpath)      * @param create if<code>true</code> the data are created (POST) otherwise      * updated (PUT).       * @param uri if not<code>null</code> only data of this URI are imported by      * specifying the id parameter      */
specifier|protected
name|Request
name|buildImportRdfData
parameter_list|(
name|String
name|file
parameter_list|,
name|boolean
name|create
parameter_list|,
name|String
name|uri
parameter_list|)
block|{
name|Request
name|request
decl_stmt|;
name|String
name|path
decl_stmt|;
if|if
condition|(
name|uri
operator|!=
literal|null
condition|)
block|{
name|path
operator|=
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/entity"
argument_list|,
literal|"id"
argument_list|,
name|uri
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|path
operator|=
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/entity"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|create
condition|)
block|{
name|request
operator|=
name|builder
operator|.
name|buildPostRequest
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|request
operator|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPut
argument_list|(
name|path
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//set the HttpEntity (both PUT and POST are HttpEntityEnclosingRequests)
operator|(
operator|(
name|HttpEntityEnclosingRequest
operator|)
name|request
operator|.
name|getRequest
argument_list|()
operator|)
operator|.
name|setEntity
argument_list|(
operator|new
name|InputStreamEntity
argument_list|(
name|EntityhubTest
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|file
argument_list|)
argument_list|,
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
comment|//finally set the correct content-type of the provided data
comment|//currently fixed to "application/rdf+xml"
name|request
operator|.
name|getRequest
argument_list|()
operator|.
name|setHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/rdf+xml"
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
block|}
end_class

end_unit

