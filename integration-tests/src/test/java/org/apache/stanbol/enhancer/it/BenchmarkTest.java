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
name|net
operator|.
name|URLEncoder
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
name|BenchmarkTest
extends|extends
name|EnhancerTestBase
block|{
specifier|final
name|String
name|BENCHMARK
init|=
literal|"= INPUT = \n"
operator|+
literal|"Bob Marley was born in Kingston, Jamaica. Marley's music was heavily "
operator|+
literal|"influenced by the social issues of his homeland.\n"
operator|+
literal|"= EXPECT = \n"
operator|+
literal|"Description: Jamaica must be found \n"
operator|+
literal|"http://fise.iks-project.eu/ontology/entity-reference URI http://dbpedia.org/resource/Jamaica\n"
operator|+
literal|"\n"
operator|+
literal|"Description: Bob Marley must be found as a musical artist\n"
operator|+
literal|"http://fise.iks-project.eu/ontology/entity-type URI http://dbpedia.org/ontology/MusicalArtist\n"
operator|+
literal|"http://fise.iks-project.eu/ontology/entity-reference URI http://dbpedia.org/resource/Bob_Marley\n"
operator|+
literal|"\n"
operator|+
literal|"= COMPLAIN =\n"
operator|+
literal|"Description: Miles Davis must not be found\n"
operator|+
literal|"http://fise.iks-project.eu/ontology/entity-type URI http://dbpedia.org/ontology/MusicalArtist\n"
operator|+
literal|"http://fise.iks-project.eu/ontology/entity-reference URI http://dbpedia.org/resource/Miles_Davis\n"
decl_stmt|;
annotation|@
name|Test
specifier|public
name|void
name|testBenchmark
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
literal|"/benchmark?content="
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
name|BENCHMARK
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
name|BENCHMARK
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentContains
argument_list|(
literal|"SUCCESSFUL"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

