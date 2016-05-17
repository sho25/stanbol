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
name|engines
operator|.
name|celi
operator|.
name|sentimentanalysis
operator|.
name|impl
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|DC_LANGUAGE
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|DC_TYPE
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
name|enhancer
operator|.
name|test
operator|.
name|helper
operator|.
name|EnhancementStructureHelper
operator|.
name|validateAllTextAnnotations
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
name|Dictionary
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|LiteralFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|RDFTerm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|IRI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|impl
operator|.
name|utils
operator|.
name|PlainLiteralImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|impl
operator|.
name|utils
operator|.
name|TripleImpl
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
name|contentitem
operator|.
name|inmemory
operator|.
name|InMemoryContentItemFactory
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
name|engines
operator|.
name|celi
operator|.
name|CeliConstants
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
name|engines
operator|.
name|celi
operator|.
name|testutils
operator|.
name|MockComponentContext
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
name|engines
operator|.
name|celi
operator|.
name|testutils
operator|.
name|TestUtils
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
name|servicesapi
operator|.
name|ContentItem
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
name|servicesapi
operator|.
name|ContentItemFactory
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
name|servicesapi
operator|.
name|EngineException
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
name|servicesapi
operator|.
name|EnhancementEngine
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
name|servicesapi
operator|.
name|impl
operator|.
name|StringSource
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
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
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
name|test
operator|.
name|helper
operator|.
name|EnhancementStructureHelper
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
name|test
operator|.
name|helper
operator|.
name|RemoteServiceHelper
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

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|cm
operator|.
name|ConfigurationException
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
name|CeliSentimentAnalysisEngineTest
block|{
specifier|public
specifier|static
specifier|final
name|String
name|CELI_SENTIMENT_ANALYSIS_SERVICE_URL
init|=
literal|"http://linguagrid.org/LSGrid/ws/sentiment-analysis"
decl_stmt|;
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
name|CeliSentimentAnalysisEngine
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|static
name|CeliSentimentAnalysisEngine
name|sentimentAnalysisEngine
init|=
operator|new
name|CeliSentimentAnalysisEngine
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|ContentItemFactory
name|ciFactory
init|=
name|InMemoryContentItemFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|TEXT_it
init|=
literal|"Io amo Torino ma odio le zanzare"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|TEXT_fr
init|=
literal|"J'aime Turin mais je déteste les moustiques"
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setUpServices
parameter_list|()
throws|throws
name|IOException
throws|,
name|ConfigurationException
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|,
literal|"celiSentimentAnalysis"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|CeliConstants
operator|.
name|CELI_TEST_ACCOUNT
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|CeliSentimentAnalysisEngine
operator|.
name|SERVICE_URL
argument_list|,
name|CELI_SENTIMENT_ANALYSIS_SERVICE_URL
argument_list|)
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|CeliSentimentAnalysisEngine
operator|.
name|SUPPORTED_LANGUAGES
argument_list|,
literal|"fr;it"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|CeliConstants
operator|.
name|CELI_CONNECTION_TIMEOUT
argument_list|,
literal|"5"
argument_list|)
expr_stmt|;
name|MockComponentContext
name|context
init|=
operator|new
name|MockComponentContext
argument_list|(
name|properties
argument_list|)
decl_stmt|;
name|sentimentAnalysisEngine
operator|.
name|activate
argument_list|(
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|shutdownServices
parameter_list|()
block|{
name|sentimentAnalysisEngine
operator|.
name|deactivate
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|ContentItem
name|wrapAsContentItem
parameter_list|(
specifier|final
name|String
name|text
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|ciFactory
operator|.
name|createContentItem
argument_list|(
operator|new
name|StringSource
argument_list|(
name|text
argument_list|)
argument_list|)
return|;
block|}
specifier|private
name|void
name|testInput
parameter_list|(
name|String
name|txt
parameter_list|,
name|String
name|lang
parameter_list|)
throws|throws
name|EngineException
throws|,
name|IOException
block|{
name|ContentItem
name|ci
init|=
name|wrapAsContentItem
argument_list|(
name|txt
argument_list|)
decl_stmt|;
try|try
block|{
comment|// add a simple triple to statically define the language of the test content
name|ci
operator|.
name|getMetadata
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|DC_LANGUAGE
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|lang
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|sentimentAnalysisEngine
operator|.
name|computeEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|TestUtils
operator|.
name|logEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|RDFTerm
argument_list|>
name|expectedValues
init|=
operator|new
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|RDFTerm
argument_list|>
argument_list|()
decl_stmt|;
name|expectedValues
operator|.
name|put
argument_list|(
name|Properties
operator|.
name|ENHANCER_EXTRACTED_FROM
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
expr_stmt|;
name|expectedValues
operator|.
name|put
argument_list|(
name|Properties
operator|.
name|DC_CREATOR
argument_list|,
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createTypedLiteral
argument_list|(
name|sentimentAnalysisEngine
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|expectedValues
operator|.
name|put
argument_list|(
name|DC_TYPE
argument_list|,
name|CeliConstants
operator|.
name|SENTIMENT_EXPRESSION
argument_list|)
expr_stmt|;
name|int
name|textAnnoNum
init|=
name|validateAllTextAnnotations
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|txt
argument_list|,
name|expectedValues
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
name|textAnnoNum
operator|+
literal|" TextAnnotations found ..."
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"2 sentiment expressions should be recognized in: "
operator|+
name|txt
argument_list|,
name|textAnnoNum
operator|==
literal|2
argument_list|)
expr_stmt|;
name|int
name|entityAnnoNum
init|=
name|EnhancementStructureHelper
operator|.
name|validateAllEntityAnnotations
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|expectedValues
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"0 entity annotations should be recognized in: "
operator|+
name|txt
argument_list|,
name|entityAnnoNum
operator|==
literal|0
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|EngineException
name|e
parameter_list|)
block|{
name|RemoteServiceHelper
operator|.
name|checkServiceUnavailable
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|tesetEngine
parameter_list|()
throws|throws
name|Exception
block|{
name|this
operator|.
name|testInput
argument_list|(
name|CeliSentimentAnalysisEngineTest
operator|.
name|TEXT_it
argument_list|,
literal|"it"
argument_list|)
expr_stmt|;
name|this
operator|.
name|testInput
argument_list|(
name|CeliSentimentAnalysisEngineTest
operator|.
name|TEXT_fr
argument_list|,
literal|"fr"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

