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
name|junit
operator|.
name|Test
import|;
end_import

begin_comment
comment|/** Test the stateless text enhancement engines */
end_comment

begin_class
specifier|public
class|class
name|StatelessEngineTest
extends|extends
name|StanbolTestBase
block|{
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
literal|"/engines"
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
literal|"http://purl.org/dc/terms/creator.*MetaxaEngine"
argument_list|,
literal|"http://purl.org/dc/terms/creator.*LangIdEnhancementEngine"
argument_list|,
literal|"http://purl.org/dc/terms/language.*en"
argument_list|,
literal|"http://purl.org/dc/terms/creator.*LocationEnhancementEngine"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-label.*Paris"
argument_list|,
literal|"http://purl.org/dc/terms/creator.*NamedEntityExtractionEnhancementEngine"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-label.*Bob Marley"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

