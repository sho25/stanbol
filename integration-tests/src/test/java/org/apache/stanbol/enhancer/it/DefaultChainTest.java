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
name|enhancer
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
name|stanbol
operator|.
name|commons
operator|.
name|testing
operator|.
name|http
operator|.
name|RequestDocumentor
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
comment|/** Test that the default chain is called by requesting the "/enhancer" endpoint. */
end_comment

begin_class
specifier|public
class|class
name|DefaultChainTest
extends|extends
name|EnhancerTestBase
block|{
specifier|private
specifier|final
name|RequestDocumentor
name|documentor
init|=
operator|new
name|RequestDocumentor
argument_list|(
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
comment|/**      * Contains values grouped by three elements: Accept header,       * Expected content-type, Expected regexp      */
specifier|public
specifier|final
specifier|static
name|String
index|[]
name|ACCEPT_FORMAT_TEST_DATA
init|=
operator|new
name|String
index|[]
block|{
literal|"application/json"
block|,
literal|"application/json"
block|,
comment|//now JSON LD uses application/json
literal|"\"creator\": \"org.apache.stanbol.enhancer.engines.langid.LangIdEnhancementEngine\","
block|,
literal|"application/rdf+xml"
block|,
literal|"application/rdf+xml"
block|,
literal|"xmlns:rdf=.http://www.w3.org/1999/02/22-rdf-syntax-ns"
block|,
literal|"application/rdf+json"
block|,
literal|"application/rdf+json"
block|,
literal|"\\{.*value.*ontology.*TextAnnotation.*type.*uri.*}"
block|,
literal|"text/turtle"
block|,
literal|"text/turtle"
block|,
literal|"a.*ontology/TextAnnotation.*ontology/Enhancement.*;"
block|,
literal|"text/rdf+nt"
block|,
literal|"text/rdf+nt"
block|,
literal|"<urn:enhancement.*www.w3.org/1999/02/22-rdf-syntax-ns#type.*ontology/TextAnnotation>"
block|,     }
decl_stmt|;
specifier|public
name|DefaultChainTest
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|DefaultChainTest
parameter_list|(
name|String
name|endpoint
parameter_list|)
block|{
name|super
argument_list|(
name|endpoint
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|DefaultChainTest
parameter_list|(
name|String
name|endpoint
parameter_list|,
name|String
modifier|...
name|assertEngines
parameter_list|)
block|{
name|super
argument_list|(
name|endpoint
argument_list|,
name|assertEngines
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSimpleEnhancement
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
name|buildPostRequest
argument_list|(
name|getEndpoint
argument_list|()
operator|+
literal|"?executionmetadata=true"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/rdf+nt"
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"The Stanbol enhancer can detect famous cities such as Paris and people such as Bob Marley."
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentRegexp
argument_list|(
comment|//check execution metadata
literal|"http://stanbol.apache.org/ontology/enhancer/executionMetadata#executionPart"
argument_list|,
comment|//check execution of metaxa& if executionPlan is incuded
literal|"http://stanbol.apache.org/ontology/enhancer/executionplan#engine.*metaxa"
argument_list|,
literal|"http://purl.org/dc/terms/creator.*LangIdEnhancementEngine"
argument_list|,
literal|"http://purl.org/dc/terms/language.*en"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-label.*Paris"
argument_list|,
literal|"http://purl.org/dc/terms/creator.*org.apache.stanbol.enhancer.engines.opennlp.*EngineCore"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-label.*Bob Marley"
argument_list|)
operator|.
name|generateDocumentation
argument_list|(
name|documentor
argument_list|,
literal|"title"
argument_list|,
literal|"Stateless text analysis"
argument_list|,
literal|"description"
argument_list|,
literal|"A POST request to ${request.path} (TODO should be replaced by actual path) returns triples representing enhancements "
operator|+
literal|" of the POSTed text. Output format is defined by the Accept header."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testOutputFormats
parameter_list|()
throws|throws
name|Exception
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|ACCEPT_FORMAT_TEST_DATA
operator|.
name|length
condition|;
name|i
operator|+=
literal|3
control|)
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
name|getEndpoint
argument_list|()
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
name|ACCEPT_FORMAT_TEST_DATA
index|[
name|i
index|]
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"Nothing"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentType
argument_list|(
name|ACCEPT_FORMAT_TEST_DATA
index|[
name|i
operator|+
literal|1
index|]
argument_list|)
operator|.
name|assertContentRegexp
argument_list|(
name|ACCEPT_FORMAT_TEST_DATA
index|[
name|i
operator|+
literal|2
index|]
argument_list|)
operator|.
name|generateDocumentation
argument_list|(
name|documentor
argument_list|,
literal|"title"
argument_list|,
literal|"Output format: "
operator|+
name|ACCEPT_FORMAT_TEST_DATA
index|[
name|i
index|]
argument_list|,
literal|"description"
argument_list|,
literal|"Demonstrate "
operator|+
name|ACCEPT_FORMAT_TEST_DATA
index|[
name|i
index|]
operator|+
literal|" output"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testInvalidFormat
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
name|buildPostRequest
argument_list|(
name|getEndpoint
argument_list|()
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"INVALID_FORMAT"
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"Nothing"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|500
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
