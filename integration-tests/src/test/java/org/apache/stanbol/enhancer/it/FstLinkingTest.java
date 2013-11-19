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

begin_class
specifier|public
class|class
name|FstLinkingTest
extends|extends
name|EnhancerTestBase
block|{
comment|//NOTE: adapted text as part of STANBOL-1211 to avoid a single noun phrase
comment|//"SPD candidate Peer Steinbrueck" avoiding the linking of SPD in this
comment|//Text.
specifier|public
specifier|static
specifier|final
name|String
name|TEST_TEXT
init|=
literal|"There has been a worried response in "
operator|+
literal|"Greece to the Sunday's election in Germany. The win of Chancellor "
operator|+
literal|"Angela Merkel means that there will not be a radical change in "
operator|+
literal|"European policy. Greeks would have preferred Peer Steinbrueck the"
operator|+
literal|"candidate of the SPD, whose party lost Sunday."
decl_stmt|;
comment|/**      *       */
specifier|public
name|FstLinkingTest
parameter_list|()
block|{
name|super
argument_list|(
name|getChainEndpoint
argument_list|(
literal|"dbpedia-fst-linking"
argument_list|)
argument_list|,
literal|"langdetect"
argument_list|,
literal|" LanguageDetectionEnhancementEngine"
argument_list|,
literal|"opennlp-sentence"
argument_list|,
literal|" OpenNlpSentenceDetectionEngine"
argument_list|,
literal|"opennlp-token"
argument_list|,
literal|" OpenNlpTokenizerEngine"
argument_list|,
literal|"opennlp-pos"
argument_list|,
literal|"OpenNlpPosTaggingEngine"
argument_list|,
literal|"dbpedia-fst-linking"
argument_list|,
literal|"FstLinkingEngine"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFstLinkingEnhancement
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
literal|"text/rdf+nt"
argument_list|)
operator|.
name|withContent
argument_list|(
name|TEST_TEXT
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
comment|// it MUST detect the language
literal|"http://purl.org/dc/terms/creator.*LanguageDetectionEnhancementEngine"
argument_list|,
literal|"http://purl.org/dc/terms/language.*en"
argument_list|,
comment|//and the entityLinkingEngine
literal|"http://purl.org/dc/terms/creator.*FstLinkingEngine"
argument_list|,
comment|//needs to suggest the following Entities
literal|"http://fise.iks-project.eu/ontology/entity-reference.*http://dbpedia.org/resource/Angela_Merkel"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-reference.*http://dbpedia.org/resource/Greece"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-reference.*http://dbpedia.org/resource/Germany"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-reference.*http://dbpedia.org/resource/Social_Democratic_Party_of_Germany"
argument_list|,
comment|//for the following sections within the text
literal|"http://fise.iks-project.eu/ontology/selected-text.*Angela Merkel"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/selected-text.*Greece"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/selected-text.*Germany"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/selected-text.*SPD"
argument_list|)
comment|//with STANBOL-1211 Chancellor MUST NOT be found as "Chancellor" does not
comment|//select more as 50% of the tokens of the chunk "Chancellor Angela Merkel"
operator|.
name|assertContentRegexp
argument_list|(
literal|false
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-reference.*http://dbpedia.org/resource/Chancellor"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/selected-text.*Chancellor"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

