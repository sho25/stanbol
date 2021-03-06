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
name|nlp
operator|.
name|json
operator|.
name|valuetype
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
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|enhancer
operator|.
name|nlp
operator|.
name|NlpAnnotations
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
name|enhancer
operator|.
name|nlp
operator|.
name|coref
operator|.
name|CorefFeature
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|AnalysedText
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Sentence
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Span
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Token
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|annotation
operator|.
name|Value
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|BeforeClass
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
name|CorefFeatureSupportTest
extends|extends
name|ValueTypeSupportTest
block|{
specifier|private
specifier|static
specifier|final
name|String
name|sentenceText1
init|=
literal|"Obama visited China."
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|sentenceText2
init|=
literal|" He met with the Chinese prime-minister."
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|text
init|=
name|sentenceText1
operator|+
name|sentenceText2
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|jsonCorefCheckObama
init|=
literal|"{"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"type\" : \"Token\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"start\" : 0,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"end\" : 5,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"stanbol.enhancer.nlp.coref\" : {"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"isRepresentative\" : true,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"mentions\" : [ {"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"        \"type\" : \"Token\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"        \"start\" : 21,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"        \"end\" : 23"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      } ],"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"class\" : \"org.apache.stanbol.enhancer.nlp.coref.CorefFeature\""
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    }"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"  }"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|jsonCorefCheckHe
init|=
literal|"{"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"type\" : \"Token\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"start\" : 21,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"end\" : 23,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"stanbol.enhancer.nlp.coref\" : {"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"isRepresentative\" : false,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"mentions\" : [ {"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"        \"type\" : \"Token\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"        \"start\" : 0,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"        \"end\" : 5"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      } ],"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"class\" : \"org.apache.stanbol.enhancer.nlp.coref.CorefFeature\""
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    }"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"  }"
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setup
parameter_list|()
throws|throws
name|IOException
block|{
name|setupAnalysedText
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|initCorefAnnotations
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSerializationAndParse
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|serialized
init|=
name|getSerializedString
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
name|jsonCorefCheckObama
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
name|jsonCorefCheckHe
argument_list|)
argument_list|)
expr_stmt|;
name|AnalysedText
name|parsedAt
init|=
name|getParsedAnalysedText
argument_list|(
name|serialized
argument_list|)
decl_stmt|;
name|assertAnalysedTextEquality
argument_list|(
name|parsedAt
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|void
name|initCorefAnnotations
parameter_list|()
block|{
name|Sentence
name|sentence1
init|=
name|at
operator|.
name|addSentence
argument_list|(
literal|0
argument_list|,
name|sentenceText1
operator|.
name|indexOf
argument_list|(
literal|"."
argument_list|)
operator|+
literal|1
argument_list|)
decl_stmt|;
name|Token
name|obama
init|=
name|sentence1
operator|.
name|addToken
argument_list|(
literal|0
argument_list|,
literal|"Obama"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|Sentence
name|sentence2
init|=
name|at
operator|.
name|addSentence
argument_list|(
name|sentenceText1
operator|.
name|indexOf
argument_list|(
literal|"."
argument_list|)
operator|+
literal|2
argument_list|,
name|sentenceText2
operator|.
name|indexOf
argument_list|(
literal|"."
argument_list|)
operator|+
literal|1
argument_list|)
decl_stmt|;
name|int
name|heStartIdx
init|=
name|sentence2
operator|.
name|getSpan
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|"He"
argument_list|)
decl_stmt|;
name|Token
name|he
init|=
name|sentence2
operator|.
name|addToken
argument_list|(
name|heStartIdx
argument_list|,
name|heStartIdx
operator|+
literal|"He"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Span
argument_list|>
name|obamaMentions
init|=
operator|new
name|HashSet
argument_list|<
name|Span
argument_list|>
argument_list|()
decl_stmt|;
name|obamaMentions
operator|.
name|add
argument_list|(
name|he
argument_list|)
expr_stmt|;
name|obama
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|COREF_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
operator|new
name|CorefFeature
argument_list|(
literal|true
argument_list|,
name|obamaMentions
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|Span
argument_list|>
name|heMentions
init|=
operator|new
name|HashSet
argument_list|<
name|Span
argument_list|>
argument_list|()
decl_stmt|;
name|heMentions
operator|.
name|add
argument_list|(
name|obama
argument_list|)
expr_stmt|;
name|he
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|COREF_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
operator|new
name|CorefFeature
argument_list|(
literal|false
argument_list|,
name|heMentions
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

