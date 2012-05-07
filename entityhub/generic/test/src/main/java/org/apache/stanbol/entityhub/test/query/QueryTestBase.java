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
name|entityhub
operator|.
name|test
operator|.
name|query
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
name|test
operator|.
name|it
operator|.
name|AssertEntityhubJson
operator|.
name|assertResponseQuery
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
name|assertSelectedField
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
name|*
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertNotNull
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
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
name|java
operator|.
name|util
operator|.
name|UUID
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
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
comment|/**  *<p>  *  This tests do not depend on a specific dataset but only test  *</p>  *<ul>  *<li> Correct Errors on Illegal Requests  *<li> Missing but required parameter  *<li> Correct default values for optional parameters  *</ul>  *<p>  *  This set of tests should be tested against all service endpoints that  *  support queries. Typically this is done by extending this class  *  and configuring it to run against the according endpoint.  *</p><p>  *  Please make sure that the data set this tests are executed against does  *  not contain any information using the "http://www.test.org/test#"  *  namespace.  *</p>  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|QueryTestBase
extends|extends
name|EntityhubTestBase
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
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RDFS_LABEL
init|=
name|NamespaceEnum
operator|.
name|rdfs
operator|+
literal|"label"
decl_stmt|;
specifier|protected
specifier|final
name|String
name|endpointPath
decl_stmt|;
comment|/**      * Constructs a FieldQueryServiceTest      * @param servicePath the path to the service (e.g. /entityhub/sites/query)      * @param referencedSiteId if the       * @param log      */
specifier|public
name|QueryTestBase
parameter_list|(
name|String
name|servicePath
parameter_list|,
name|String
name|referencedSiteId
parameter_list|)
block|{
name|super
argument_list|(
name|referencedSiteId
operator|==
literal|null
condition|?
literal|null
else|:
name|Collections
operator|.
name|singleton
argument_list|(
name|referencedSiteId
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|servicePath
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The path to the FieldQuery endpoint MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|referencedSiteId
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|servicePath
operator|.
name|contains
argument_list|(
name|referencedSiteId
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The parsed referenceSiteId %s is not contained within the parsed servicePath %s"
argument_list|,
name|referencedSiteId
argument_list|,
name|servicePath
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|//remove tailing '/'
if|if
condition|(
name|servicePath
operator|.
name|charAt
argument_list|(
name|servicePath
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'/'
condition|)
block|{
name|servicePath
operator|=
name|servicePath
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|servicePath
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
comment|//we need a leading '/'
if|if
condition|(
name|servicePath
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|!=
literal|'/'
condition|)
block|{
name|servicePath
operator|=
literal|'/'
operator|+
name|servicePath
expr_stmt|;
block|}
name|this
operator|.
name|endpointPath
operator|=
name|servicePath
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"created FieldQueryTest for Service {}"
argument_list|,
name|servicePath
argument_list|)
expr_stmt|;
block|}
comment|/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -      *  Utility Methods      * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -      */
comment|/**      * Executes a {@link QueryTestCase} by sending the       * {@link QueryTestCase#getContent() query} as an POST request to the       *<code>{@link #endpointPath}/{@link QueryTestCase#getServicePath()}</Code>.      * @param path the path to perform the field query. "/query" is added to the      * parsed value      * @param test the field query test      * @return the result executor used for the test      * @throws IOException on any exception while connecting to the entityhub      * @throws JSONException if the returned results are not valid JSON      */
specifier|protected
name|RequestExecutor
name|executeQuery
parameter_list|(
name|QueryTestCase
name|test
parameter_list|)
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|Request
name|request
init|=
name|builder
operator|.
name|buildPostRequest
argument_list|(
name|endpointPath
operator|+
name|test
operator|.
name|getServicePath
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|header
range|:
name|test
operator|.
name|getHeaders
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|request
operator|.
name|withHeader
argument_list|(
name|header
operator|.
name|getKey
argument_list|()
argument_list|,
name|header
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|withContent
argument_list|(
name|test
operator|.
name|getContent
argument_list|()
argument_list|)
expr_stmt|;
name|RequestExecutor
name|re
init|=
name|executor
operator|.
name|execute
argument_list|(
name|request
argument_list|)
decl_stmt|;
name|assertQueryResults
argument_list|(
name|re
argument_list|,
name|test
argument_list|)
expr_stmt|;
return|return
name|re
return|;
block|}
comment|/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -      *  Find Query Test Methods:      * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -      */
annotation|@
name|Test
specifier|public
name|void
name|testMissingNameParameter
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FindQueryTestCase
name|test
init|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|null
argument_list|,
literal|400
argument_list|)
decl_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEmptyNameParameter
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FindQueryTestCase
name|test
init|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|""
argument_list|,
literal|400
argument_list|)
decl_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests the defaults for the text constraint used for find queries. This      * includes:<ul>      *<li> limit set to an value> 0      *<li> offset set to 0      *<li> selected set to rdfs:label      *<li> constraint.patternType set to wildcard      *<li> constraint.field set to rdfs:label      *<li> constraint.type set to text      *</ul>      * @throws IOException      * @throws JSONException      */
annotation|@
name|Test
specifier|public
name|void
name|testDefaultsParameter
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FindQueryTestCase
name|test
init|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"non_existant_"
operator|+
name|UUID
operator|.
name|randomUUID
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|RequestExecutor
name|re
init|=
name|executeQuery
argument_list|(
name|test
argument_list|)
decl_stmt|;
name|JSONObject
name|jQuery
init|=
name|assertResponseQuery
argument_list|(
name|re
operator|.
name|getContent
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"Result Query does not contain Limit property"
argument_list|,
name|jQuery
operator|.
name|has
argument_list|(
literal|"limit"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"Returned limit is<= 0"
argument_list|,
name|jQuery
operator|.
name|getInt
argument_list|(
literal|"limit"
argument_list|)
operator|>
literal|0
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"Result Query does not contain offset property"
argument_list|,
name|jQuery
operator|.
name|has
argument_list|(
literal|"offset"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"Returned offset is != 0"
argument_list|,
name|jQuery
operator|.
name|getInt
argument_list|(
literal|"offset"
argument_list|)
operator|==
literal|0
argument_list|)
expr_stmt|;
name|assertSelectedField
argument_list|(
name|jQuery
argument_list|,
name|getDefaultFindQueryField
argument_list|()
argument_list|)
expr_stmt|;
name|JSONArray
name|jConstraints
init|=
name|jQuery
operator|.
name|optJSONArray
argument_list|(
literal|"constraints"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"Result Query is missing the 'constraints' property"
argument_list|,
name|jConstraints
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Result Query is expected to have a single constraint"
argument_list|,
literal|1
argument_list|,
name|jConstraints
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|JSONObject
name|constraint
init|=
name|jConstraints
operator|.
name|optJSONObject
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"'constraints' array does not contain a JSONObject but "
operator|+
name|jConstraints
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|,
name|constraint
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"The 'type' of the Constraint is not 'text' but "
operator|+
name|constraint
operator|.
name|opt
argument_list|(
literal|"type"
argument_list|)
argument_list|,
literal|"text"
argument_list|,
name|constraint
operator|.
name|optString
argument_list|(
literal|"type"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"The 'patternType' of the Constraint is not 'wildcard' but "
operator|+
name|constraint
operator|.
name|opt
argument_list|(
literal|"patternType"
argument_list|)
argument_list|,
literal|"wildcard"
argument_list|,
name|constraint
operator|.
name|optString
argument_list|(
literal|"patternType"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"The 'field' of the Constraint is not "
operator|+
name|getDefaultFindQueryField
argument_list|()
operator|+
literal|" but "
operator|+
name|constraint
operator|.
name|opt
argument_list|(
literal|"field"
argument_list|)
argument_list|,
name|getDefaultFindQueryField
argument_list|()
argument_list|,
name|constraint
operator|.
name|optString
argument_list|(
literal|"field"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Getter for the default field used for find queries of the 'field' parameter      * is not defined.<p>      * This default is different for the '/entityhub' and the other service      * endpoints that support find queries.      * @return the default field      */
specifier|protected
specifier|abstract
name|String
name|getDefaultFindQueryField
parameter_list|()
function_decl|;
annotation|@
name|Test
specifier|public
name|void
name|testCustomFieldParameter
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FindQueryTestCase
name|test
init|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"non_existant_"
operator|+
name|UUID
operator|.
name|randomUUID
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|String
name|testField
init|=
literal|"http://www.test.org/test#test_"
operator|+
name|UUID
operator|.
name|randomUUID
argument_list|()
decl_stmt|;
name|test
operator|.
name|setField
argument_list|(
name|testField
argument_list|)
expr_stmt|;
name|RequestExecutor
name|re
init|=
name|executeQuery
argument_list|(
name|test
argument_list|)
decl_stmt|;
name|JSONObject
name|jQuery
init|=
name|assertResponseQuery
argument_list|(
name|re
operator|.
name|getContent
argument_list|()
argument_list|)
decl_stmt|;
name|assertSelectedField
argument_list|(
name|jQuery
argument_list|,
name|testField
argument_list|)
expr_stmt|;
block|}
comment|/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -      *  Field Query Test Methods:      * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -      */
annotation|@
name|Test
specifier|public
name|void
name|testIllegalJSON
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
literal|"'text': 'Paris' "
operator|+
comment|//NOTE: here the comma is missing here!
literal|"'patternType' : 'none', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field', "
operator|+
literal|"'dataTypes': ['http:\\/\\/stanbol.apache.org\\/ontology\\/entityhub\\/entityhub#text'] "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMissingConstrints
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'offset': '0', "
operator|+
literal|"'limit': '3', "
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEmptyFieldProperty
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
literal|"'text': 'Paris', "
operator|+
literal|"'patternType' : 'none', "
operator|+
literal|"'field': '', "
operator|+
literal|"'dataTypes': ['http:\\/\\/stanbol.apache.org\\/ontology\\/entityhub\\/entityhub#text'] "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testUnknownConstraintType
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'unknownConstraintType', "
operator|+
literal|"'test': 'dummy' "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMultipleConstraintsForSameField
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
literal|"'text': 'Paris', "
operator|+
literal|"'patternType' : 'none', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field', "
operator|+
literal|"'dataTypes': ['http:\\/\\/stanbol.apache.org\\/ontology\\/entityhub\\/entityhub#text'] "
operator|+
literal|"},{ "
operator|+
literal|"'type': 'reference', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field', "
operator|+
literal|"'value': 'http:\\/\\/dbpedia.org\\/ontology\\/Person', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testOffsetNoNumber
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'offset': 'abc', "
operator|+
literal|"'limit': '3', "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'reference', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field', "
operator|+
literal|"'value': 'http:\\/\\/www.test.org\\/test#Test', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testOffsetNegativeNumber
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'offset': '-3', "
operator|+
literal|"'limit': '3', "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'reference', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#non-existing-field', "
operator|+
literal|"'value': 'http:\\/\\/www.test.org\\/test#NonExistingValue', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|//success but no result
comment|//now execute the test
name|RequestExecutor
name|re
init|=
name|executeQuery
argument_list|(
name|test
argument_list|)
decl_stmt|;
name|JSONObject
name|jQuery
init|=
name|assertResponseQuery
argument_list|(
name|re
operator|.
name|getContent
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"Result Query does not contain offset property"
argument_list|,
name|jQuery
operator|.
name|has
argument_list|(
literal|"offset"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"Returned offset is != 0"
argument_list|,
name|jQuery
operator|.
name|getInt
argument_list|(
literal|"offset"
argument_list|)
operator|==
literal|0
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testLimitNoNumber
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'offset': '0', "
operator|+
literal|"'limit': 'abc', "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'reference', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field', "
operator|+
literal|"'value': 'http:\\/\\/www.test.org\\/test#Test', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testLimitNegativeNumber
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'offset': '0', "
operator|+
literal|"'limit': '-1', "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'reference', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#non-existing-field', "
operator|+
literal|"'value': 'http:\\/\\/www.test.org\\/test#NonExistingValue', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|//success but no result
comment|//now execute the test
name|RequestExecutor
name|re
init|=
name|executeQuery
argument_list|(
name|test
argument_list|)
decl_stmt|;
comment|//test the of the limit was set correctly set to the default (> 0)
name|JSONObject
name|jQuery
init|=
name|assertResponseQuery
argument_list|(
name|re
operator|.
name|getContent
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"Result Query does not contain Limit property"
argument_list|,
name|jQuery
operator|.
name|has
argument_list|(
literal|"limit"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"Returned limit is<= 0"
argument_list|,
name|jQuery
operator|.
name|getInt
argument_list|(
literal|"limit"
argument_list|)
operator|>
literal|0
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMissingConstraintFieldProperty
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
literal|"'text': 'Paris', "
operator|+
literal|"'patternType' : 'none', "
operator|+
comment|//"'field': 'http:\\/\\/www.test.org\\/test#field', " +
literal|"'dataTypes': ['http:\\/\\/stanbol.apache.org\\/ontology\\/entityhub\\/entityhub#text'] "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMissingConstraintTypeProperty
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
comment|//"'type': 'text', " +
literal|"'test': 'dummy' "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMissingReferenceConstraintValueProperty
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'reference', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field' "
operator|+
comment|//"'value': 'http:\\/\\/dbpedia.org\\/ontology\\/Person', " +
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMissinValueConstraintValueProperty
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'value',"
operator|+
comment|//"'value': 'Paris'," +
literal|"'field': 'http:\\/\\/www.test.org\\/test#field'"
operator|+
literal|"}],"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testValueConstraintDefaultDataType
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'value',"
operator|+
literal|"'value': 'Paris',"
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field'"
operator|+
literal|"}],"
operator|+
literal|"}"
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|RequestExecutor
name|re
init|=
name|executeQuery
argument_list|(
name|test
argument_list|)
decl_stmt|;
name|JSONObject
name|jQuery
init|=
name|assertResponseQuery
argument_list|(
name|re
operator|.
name|getContent
argument_list|()
argument_list|)
decl_stmt|;
name|JSONArray
name|jConstraints
init|=
name|jQuery
operator|.
name|optJSONArray
argument_list|(
literal|"constraints"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"Result Query does not contain the constraints Array"
argument_list|,
name|jConstraints
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"Result Query Constraint Array does not contain the expected Constraint"
argument_list|,
name|jConstraints
operator|.
name|length
argument_list|()
operator|==
literal|1
argument_list|)
expr_stmt|;
name|JSONObject
name|jConstraint
init|=
name|jConstraints
operator|.
name|optJSONObject
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"Constraint Array does not contain JSONObjects"
argument_list|,
name|jConstraint
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"Returned Query does not contain the default data type"
argument_list|,
name|jConstraint
operator|.
name|has
argument_list|(
literal|"datatype"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMissingTextConstraintTextProperty
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
comment|//"'text': 'Paris', " +
literal|"'patternType' : 'none', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEmptyTextConstraintTextProperty
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//1. empty string
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
literal|"'text': '', "
operator|+
literal|"'patternType' : 'none', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
comment|//2 empty Array
name|test
operator|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
literal|"'text': [], "
operator|+
literal|"'patternType' : 'none', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
expr_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
comment|//3 Array with empty string
name|test
operator|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
literal|"'text': [''], "
operator|+
literal|"'patternType' : 'none', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
expr_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testDefaultTextConstraintPatternTypeProperty
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
literal|"'text': 'Paris', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|RequestExecutor
name|re
init|=
name|executeQuery
argument_list|(
name|test
argument_list|)
decl_stmt|;
name|JSONObject
name|jQuery
init|=
name|assertResponseQuery
argument_list|(
name|re
operator|.
name|getContent
argument_list|()
argument_list|)
decl_stmt|;
name|JSONArray
name|jConstraints
init|=
name|jQuery
operator|.
name|optJSONArray
argument_list|(
literal|"constraints"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"Result Query does not contain the constraints Array"
argument_list|,
name|jConstraints
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"Result Query Constraint Array does not contain the expected Constraint"
argument_list|,
name|jConstraints
operator|.
name|length
argument_list|()
operator|==
literal|1
argument_list|)
expr_stmt|;
name|JSONObject
name|jConstraint
init|=
name|jConstraints
operator|.
name|optJSONObject
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"Constraint Array does not contain JSONObjects"
argument_list|,
name|jConstraint
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"The 'patternType' property MUST BE set for returned TextConstraints"
argument_list|,
name|jConstraint
operator|.
name|has
argument_list|(
literal|"patternType"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Default for patternType MUST BE 'none'"
argument_list|,
literal|"none"
argument_list|,
name|jConstraint
operator|.
name|getString
argument_list|(
literal|"patternType"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testUnknownTextConstraintPatternType
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
literal|"'text': 'Paris', "
operator|+
literal|"'patternType' : 'unknownPatternType', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testRangeConstraintNoBoundsProperties
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'range', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field',"
operator|+
comment|//"'lowerBound': '1946-01-01T00:00:00.000Z'," +
comment|//"'upperBound': '1946-12-31T23:59:59.999Z'," +
literal|"'inclusive': true,"
operator|+
literal|"'datatype': 'xsd:dateTime'"
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|400
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testDefaultRangeConstraintDatatypeProperty
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': [ 'http:\\/\\/www.test.org\\/test#field'], "
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'range', "
operator|+
literal|"'field': 'http:\\/\\/www.test.org\\/test#field', "
operator|+
literal|"'lowerBound': 1000,"
operator|+
literal|"'inclusive': true,"
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|//expect BadRequest
comment|//now execute the test
name|RequestExecutor
name|re
init|=
name|executeQuery
argument_list|(
name|test
argument_list|)
decl_stmt|;
name|JSONObject
name|jQuery
init|=
name|assertResponseQuery
argument_list|(
name|re
operator|.
name|getContent
argument_list|()
argument_list|)
decl_stmt|;
name|JSONArray
name|jConstraints
init|=
name|jQuery
operator|.
name|optJSONArray
argument_list|(
literal|"constraints"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"Result Query does not contain the constraints Array"
argument_list|,
name|jConstraints
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"Result Query Constraint Array does not contain the expected Constraint"
argument_list|,
name|jConstraints
operator|.
name|length
argument_list|()
operator|==
literal|1
argument_list|)
expr_stmt|;
name|JSONObject
name|jConstraint
init|=
name|jConstraints
operator|.
name|optJSONObject
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"Constraint Array does not contain JSONObjects"
argument_list|,
name|jConstraint
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"Returned Query does not contain the default data type"
argument_list|,
name|jConstraint
operator|.
name|has
argument_list|(
literal|"datatype"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

