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
name|java
operator|.
name|io
operator|.
name|IOException
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

begin_class
specifier|public
class|class
name|EnhancerConfigurationTest
extends|extends
name|EnhancerTestBase
block|{
annotation|@
name|Test
specifier|public
name|void
name|testEnhancerConfig
parameter_list|()
throws|throws
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|getEndpoint
argument_list|()
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/rdf+xml"
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
literal|"<rdf:Description rdf:about=\"http://localhost:.*/enhancer\">"
argument_list|,
literal|"<rdf:type rdf:resource=\"http://stanbol.apache.org/ontology/enhancer/enhancer#Enhancer\"/>"
argument_list|,
literal|"<j.0:hasEngine rdf:resource=\"http://localhost:.*/enhancer/engine/dbpediaLinking\"/>"
argument_list|,
literal|"<j.0:hasEngine rdf:resource=\"http://localhost:.*/enhancer/engine/langid\"/>"
argument_list|,
literal|"<j.0:hasEngine rdf:resource=\"http://localhost:.*/enhancer/engine/langdetect\"/>"
argument_list|,
literal|"<j.0:hasEngine rdf:resource=\"http://localhost:.*/enhancer/engine/tika\"/>"
argument_list|,
literal|"<j.0:hasEngine rdf:resource=\"http://localhost:.*/enhancer/engine/ner\"/>"
argument_list|,
literal|"<j.0:hasChain rdf:resource=\"http://localhost:.*/enhancer/chain/default\"/>"
argument_list|,
literal|"<j.0:hasChain rdf:resource=\"http://localhost:.*/enhancer/chain/language\"/>"
argument_list|,
literal|"<rdf:type rdf:resource=\"http://stanbol.apache.org/ontology/enhancer/enhancer#EnhancementChain\"/>"
argument_list|,
literal|"<rdf:type rdf:resource=\"http://stanbol.apache.org/ontology/enhancer/enhancer#EnhancementEngine\"/>"
argument_list|,
literal|"<rdfs:label>ner</rdfs:label>"
argument_list|,
literal|"<rdfs:label>language</rdfs:label>"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEngineConfig
parameter_list|()
throws|throws
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|getEndpoint
argument_list|()
operator|+
literal|"/engine"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/rdf+xml"
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
literal|"<rdf:Description rdf:about=\"http://localhost:.*/enhancer\">"
argument_list|,
literal|"<rdf:type rdf:resource=\"http://stanbol.apache.org/ontology/enhancer/enhancer#Enhancer\"/>"
argument_list|,
literal|"<j.0:hasEngine rdf:resource=\"http://localhost:.*/enhancer/engine/dbpediaLinking\"/>"
argument_list|,
literal|"<j.0:hasEngine rdf:resource=\"http://localhost:.*/enhancer/engine/langid\"/>"
argument_list|,
literal|"<j.0:hasEngine rdf:resource=\"http://localhost:.*/enhancer/engine/langdetect\"/>"
argument_list|,
literal|"<j.0:hasEngine rdf:resource=\"http://localhost:.*/enhancer/engine/tika\"/>"
argument_list|,
literal|"<j.0:hasEngine rdf:resource=\"http://localhost:.*/enhancer/engine/ner\"/>"
argument_list|,
literal|"<rdf:type rdf:resource=\"http://stanbol.apache.org/ontology/enhancer/enhancer#EnhancementEngine\"/>"
argument_list|,
literal|"<rdfs:label>ner</rdfs:label>"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testChainConfig
parameter_list|()
throws|throws
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|getEndpoint
argument_list|()
operator|+
literal|"/chain"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/rdf+xml"
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
literal|"<rdf:Description rdf:about=\"http://localhost:.*/enhancer\">"
argument_list|,
literal|"<rdf:type rdf:resource=\"http://stanbol.apache.org/ontology/enhancer/enhancer#Enhancer\"/>"
argument_list|,
literal|"<j.0:hasChain rdf:resource=\"http://localhost:.*/enhancer/chain/default\"/>"
argument_list|,
literal|"<j.0:hasChain rdf:resource=\"http://localhost:.*/enhancer/chain/language\"/>"
argument_list|,
literal|"<rdf:type rdf:resource=\"http://stanbol.apache.org/ontology/enhancer/enhancer#EnhancementChain\"/>"
argument_list|,
literal|"<rdfs:label>language</rdfs:label>"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSparqlConfig
parameter_list|()
throws|throws
name|IOException
block|{
name|StringBuilder
name|query
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"PREFIX enhancer:<http://stanbol.apache.org/ontology/enhancer/enhancer#>"
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"SELECT distinct ?name ?chain "
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"WHERE {"
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"?chain a enhancer:EnhancementChain ."
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"?chain rdfs:label ?name ."
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"}"
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"ORDER BY ASC(?name)"
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
name|getEndpoint
argument_list|()
operator|+
literal|"/sparql"
argument_list|,
literal|"query"
argument_list|,
name|query
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/sparql-results+xml"
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
literal|"<sparql xmlns=\"http://www.w3.org/2005/sparql-results#\">"
argument_list|,
literal|"<head>"
argument_list|,
literal|"<variable name=\"chain\"/>"
argument_list|,
literal|"<variable name=\"name\"/>"
argument_list|,
literal|"</head>"
argument_list|,
literal|"<results>"
argument_list|,
literal|"<result>"
argument_list|,
literal|"<binding name=\"chain\">"
argument_list|,
literal|"<uri>http://localhost:.*/enhancer/chain/default</uri>"
argument_list|,
literal|"<uri>http://localhost:.*/enhancer/chain/language</uri>"
argument_list|,
literal|"<binding name=\"name\">"
argument_list|,
literal|"<literal>default</literal>"
argument_list|,
literal|"<literal>language</literal>"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testExecutionPlan
parameter_list|()
throws|throws
name|IOException
block|{
comment|//We need not to validate the executionplan data.
comment|//This is already done by other tests.
comment|//only check for the rdf:types to check if the correct RDF data are returned
name|String
index|[]
name|validate
init|=
operator|new
name|String
index|[]
block|{
literal|"<rdf:type rdf:resource=\"http://stanbol.apache.org/ontology/enhancer/executionplan#ExecutionNode\"/>"
block|,
literal|"<rdf:type rdf:resource=\"http://stanbol.apache.org/ontology/enhancer/executionplan#ExecutionPlan\"/>"
block|}
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|getEndpoint
argument_list|()
operator|+
literal|"/ep"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/rdf+xml"
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
name|validate
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
name|getEndpoint
argument_list|()
operator|+
literal|"/chain/language/ep"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/rdf+xml"
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
name|validate
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
name|getEndpoint
argument_list|()
operator|+
literal|"/chain/engine/tika"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/rdf+xml"
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
name|validate
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

