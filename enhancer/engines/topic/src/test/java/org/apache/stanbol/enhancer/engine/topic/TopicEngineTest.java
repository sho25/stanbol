begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
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
name|engine
operator|.
name|topic
package|;
end_package

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
name|fail
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileOutputStream
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
name|io
operator|.
name|InputStream
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
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|ParserConfigurationException
import|;
end_import

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|TestCase
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|FileUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|IOUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrQuery
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrServerException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|embedded
operator|.
name|EmbeddedSolrServer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|response
operator|.
name|QueryResponse
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|common
operator|.
name|params
operator|.
name|CommonParams
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|core
operator|.
name|CoreContainer
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
name|solr
operator|.
name|utils
operator|.
name|StreamQueryRequest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
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

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_class
specifier|public
class|class
name|TopicEngineTest
block|{
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
name|TopicEngineTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TEST_SOLR_CORE_ID
init|=
literal|"test"
decl_stmt|;
name|EmbeddedSolrServer
name|solrServer
decl_stmt|;
name|File
name|solrHome
decl_stmt|;
annotation|@
name|Before
specifier|public
name|void
name|makeEmptyEmbeddedSolrServer
parameter_list|()
throws|throws
name|IOException
throws|,
name|ParserConfigurationException
throws|,
name|SAXException
block|{
name|solrHome
operator|=
name|File
operator|.
name|createTempFile
argument_list|(
literal|"topicEngineTest_"
argument_list|,
literal|"_solr_folder"
argument_list|)
expr_stmt|;
name|solrHome
operator|.
name|delete
argument_list|()
expr_stmt|;
name|solrHome
operator|.
name|mkdir
argument_list|()
expr_stmt|;
comment|// solr conf file
name|File
name|solrFile
init|=
operator|new
name|File
argument_list|(
name|solrHome
argument_list|,
literal|"solr.xml"
argument_list|)
decl_stmt|;
name|InputStream
name|is
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"/test_solr.xml"
argument_list|)
decl_stmt|;
name|TestCase
operator|.
name|assertNotNull
argument_list|(
literal|"missing test solr.xml file"
argument_list|,
name|is
argument_list|)
expr_stmt|;
name|IOUtils
operator|.
name|copy
argument_list|(
name|is
argument_list|,
operator|new
name|FileOutputStream
argument_list|(
name|solrFile
argument_list|)
argument_list|)
expr_stmt|;
comment|// solr conf folder with schema
name|File
name|solrCoreFolder
init|=
operator|new
name|File
argument_list|(
name|solrHome
argument_list|,
name|TEST_SOLR_CORE_ID
argument_list|)
decl_stmt|;
name|solrCoreFolder
operator|.
name|mkdir
argument_list|()
expr_stmt|;
name|File
name|solrConfFolder
init|=
operator|new
name|File
argument_list|(
name|solrCoreFolder
argument_list|,
literal|"conf"
argument_list|)
decl_stmt|;
name|solrConfFolder
operator|.
name|mkdir
argument_list|()
expr_stmt|;
name|File
name|schemaFile
init|=
operator|new
name|File
argument_list|(
name|solrConfFolder
argument_list|,
literal|"schema.xml"
argument_list|)
decl_stmt|;
name|is
operator|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"/test_schema.xml"
argument_list|)
expr_stmt|;
name|TestCase
operator|.
name|assertNotNull
argument_list|(
literal|"missing test solr schema.xml file"
argument_list|,
name|is
argument_list|)
expr_stmt|;
name|IOUtils
operator|.
name|copy
argument_list|(
name|is
argument_list|,
operator|new
name|FileOutputStream
argument_list|(
name|schemaFile
argument_list|)
argument_list|)
expr_stmt|;
name|File
name|solrConfigFile
init|=
operator|new
name|File
argument_list|(
name|solrConfFolder
argument_list|,
literal|"solrconfig.xml"
argument_list|)
decl_stmt|;
name|is
operator|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"/test_solrconfig.xml"
argument_list|)
expr_stmt|;
name|TestCase
operator|.
name|assertNotNull
argument_list|(
literal|"missing test solrconfig.xml file"
argument_list|,
name|is
argument_list|)
expr_stmt|;
name|IOUtils
operator|.
name|copy
argument_list|(
name|is
argument_list|,
operator|new
name|FileOutputStream
argument_list|(
name|solrConfigFile
argument_list|)
argument_list|)
expr_stmt|;
comment|// create the embedded server
name|CoreContainer
name|coreContainer
init|=
operator|new
name|CoreContainer
argument_list|(
name|solrHome
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
name|solrFile
argument_list|)
decl_stmt|;
name|solrServer
operator|=
operator|new
name|EmbeddedSolrServer
argument_list|(
name|coreContainer
argument_list|,
name|TEST_SOLR_CORE_ID
argument_list|)
expr_stmt|;
block|}
annotation|@
name|After
specifier|public
name|void
name|cleanupEmbeddedSolrServer
parameter_list|()
block|{
name|FileUtils
operator|.
name|deleteQuietly
argument_list|(
name|solrHome
argument_list|)
expr_stmt|;
name|solrHome
operator|=
literal|null
expr_stmt|;
name|solrServer
operator|=
literal|null
expr_stmt|;
block|}
specifier|protected
name|void
name|loadSampleTopicsFromTSV
parameter_list|()
throws|throws
name|IOException
throws|,
name|SolrServerException
block|{
name|assertNotNull
argument_list|(
name|solrHome
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|solrServer
argument_list|)
expr_stmt|;
name|String
name|topicSnippetsPath
init|=
literal|"/topics_abstracts_snippet.tsv"
decl_stmt|;
name|InputStream
name|is
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|topicSnippetsPath
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"Could not find test resource: "
operator|+
name|topicSnippetsPath
argument_list|,
name|is
argument_list|)
expr_stmt|;
comment|// Build a query for the CSV importer
name|SolrQuery
name|query
init|=
operator|new
name|SolrQuery
argument_list|()
decl_stmt|;
name|query
operator|.
name|setQueryType
argument_list|(
literal|"/update/csv"
argument_list|)
expr_stmt|;
name|query
operator|.
name|set
argument_list|(
literal|"commit"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|query
operator|.
name|set
argument_list|(
literal|"separator"
argument_list|,
literal|"\t"
argument_list|)
expr_stmt|;
name|query
operator|.
name|set
argument_list|(
literal|"headers"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|query
operator|.
name|set
argument_list|(
literal|"fieldnames"
argument_list|,
literal|"topic,popularity,paths,text"
argument_list|)
expr_stmt|;
name|query
operator|.
name|set
argument_list|(
name|CommonParams
operator|.
name|STREAM_CONTENTTYPE
argument_list|,
literal|"text/plan;charset=utf-8"
argument_list|)
expr_stmt|;
name|query
operator|.
name|set
argument_list|(
name|CommonParams
operator|.
name|STREAM_BODY
argument_list|,
name|IOUtils
operator|.
name|toString
argument_list|(
name|is
argument_list|,
literal|"utf-8"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Upload an index
name|QueryResponse
name|response
init|=
operator|new
name|StreamQueryRequest
argument_list|(
name|query
argument_list|)
operator|.
name|process
argument_list|(
name|solrServer
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|response
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Indexed test topics in %dms"
argument_list|,
name|response
operator|.
name|getElapsedTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getDefaultConfigParams
parameter_list|()
block|{
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
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
name|config
operator|.
name|put
argument_list|(
name|TopicClassificationEngine
operator|.
name|ENGINE_ID
argument_list|,
literal|"test-engine"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|TopicClassificationEngine
operator|.
name|SOLR_CORE
argument_list|,
name|solrServer
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|TopicClassificationEngine
operator|.
name|TOPIC_URI_FIELD
argument_list|,
literal|"topic"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|TopicClassificationEngine
operator|.
name|SIMILARTITY_FIELD
argument_list|,
literal|"text"
argument_list|)
expr_stmt|;
return|return
name|config
return|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEngineConfiguation
parameter_list|()
throws|throws
name|ConfigurationException
block|{
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
init|=
name|getDefaultConfigParams
argument_list|()
decl_stmt|;
name|TopicClassificationEngine
name|engine
init|=
name|TopicClassificationEngine
operator|.
name|fromParameters
argument_list|(
name|config
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|engine
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|engine
operator|.
name|engineId
argument_list|,
literal|"test-engine"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|engine
operator|.
name|solrServer
argument_list|,
name|solrServer
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|engine
operator|.
name|topicUriField
argument_list|,
literal|"topic"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|engine
operator|.
name|similarityField
argument_list|,
literal|"text"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|engine
operator|.
name|acceptedLanguages
argument_list|,
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
comment|// check some required attributes
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configWithMissingTopicField
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
name|configWithMissingTopicField
operator|.
name|putAll
argument_list|(
name|config
argument_list|)
expr_stmt|;
name|configWithMissingTopicField
operator|.
name|remove
argument_list|(
name|TopicClassificationEngine
operator|.
name|TOPIC_URI_FIELD
argument_list|)
expr_stmt|;
try|try
block|{
name|TopicClassificationEngine
operator|.
name|fromParameters
argument_list|(
name|configWithMissingTopicField
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Should have raised a ConfigurationException"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ConfigurationException
name|e
parameter_list|)
block|{}
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configWithMissingEngineId
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
name|configWithMissingEngineId
operator|.
name|putAll
argument_list|(
name|config
argument_list|)
expr_stmt|;
name|configWithMissingEngineId
operator|.
name|remove
argument_list|(
name|TopicClassificationEngine
operator|.
name|ENGINE_ID
argument_list|)
expr_stmt|;
try|try
block|{
name|TopicClassificationEngine
operator|.
name|fromParameters
argument_list|(
name|configWithMissingEngineId
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Should have raised a ConfigurationException"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ConfigurationException
name|e
parameter_list|)
block|{}
comment|// check accept language optional param
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configWithAcceptLangage
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
name|configWithAcceptLangage
operator|.
name|putAll
argument_list|(
name|config
argument_list|)
expr_stmt|;
name|configWithAcceptLangage
operator|.
name|put
argument_list|(
name|TopicClassificationEngine
operator|.
name|LANGUAGE
argument_list|,
literal|"en, fr"
argument_list|)
expr_stmt|;
name|engine
operator|=
name|TopicClassificationEngine
operator|.
name|fromParameters
argument_list|(
name|configWithAcceptLangage
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|engine
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|engine
operator|.
name|acceptedLanguages
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"en"
argument_list|,
literal|"fr"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEmptyIndexTopicClassification
parameter_list|()
throws|throws
name|Exception
block|{
name|TopicClassificationEngine
name|engine
init|=
name|TopicClassificationEngine
operator|.
name|fromParameters
argument_list|(
name|getDefaultConfigParams
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|TopicSuggestion
argument_list|>
name|suggestedTopics
init|=
name|engine
operator|.
name|suggestTopics
argument_list|(
literal|"This is a test."
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|suggestedTopics
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|suggestedTopics
operator|.
name|size
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testTopicClassification
parameter_list|()
throws|throws
name|Exception
block|{
name|loadSampleTopicsFromTSV
argument_list|()
expr_stmt|;
name|TopicClassificationEngine
name|engine
init|=
name|TopicClassificationEngine
operator|.
name|fromParameters
argument_list|(
name|getDefaultConfigParams
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|TopicSuggestion
argument_list|>
name|suggestedTopics
init|=
name|engine
operator|.
name|suggestTopics
argument_list|(
literal|"The Man Who Shot Liberty Valance is a 1962"
operator|+
literal|" American Western film directed by John Ford,"
operator|+
literal|" narrated by Charlton Heston and starring James"
operator|+
literal|" Stewart, John Wayne and Vivien Leigh."
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|suggestedTopics
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|suggestedTopics
operator|.
name|size
argument_list|()
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|TopicSuggestion
name|bestSuggestion
init|=
name|suggestedTopics
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|bestSuggestion
operator|.
name|uri
argument_list|,
literal|"Category:American_films"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

