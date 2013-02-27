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
name|assertFalse
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
name|File
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
name|java
operator|.
name|util
operator|.
name|Calendar
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|GregorianCalendar
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
name|Hashtable
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
name|java
operator|.
name|util
operator|.
name|TimeZone
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
name|FilenameUtils
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
name|stanbol
operator|.
name|enhancer
operator|.
name|topic
operator|.
name|Batch
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
name|topic
operator|.
name|EmbeddedSolrHelper
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
name|topic
operator|.
name|UTCTimeStamper
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
name|topic
operator|.
name|training
operator|.
name|Example
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
name|topic
operator|.
name|training
operator|.
name|SolrTrainingSet
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
name|topic
operator|.
name|training
operator|.
name|TrainingSetException
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
name|AfterClass
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
name|TrainingSetTest
extends|extends
name|EmbeddedSolrHelper
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
name|TrainingSetTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|TEST_ROOT
init|=
name|FilenameUtils
operator|.
name|separatorsToSystem
argument_list|(
literal|"/target/triningtest-files"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|String
name|userDir
decl_stmt|;
specifier|private
specifier|static
name|File
name|testRoot
decl_stmt|;
specifier|private
specifier|static
name|int
name|testCounter
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TOPIC_1
init|=
literal|"http://example.com/topics/topic1"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TOPIC_2
init|=
literal|"http://example.com/topics/topic2"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TOPIC_3
init|=
literal|"http://example.com/topics/topic3"
decl_stmt|;
specifier|protected
name|EmbeddedSolrServer
name|trainingsetSolrServer
decl_stmt|;
specifier|protected
name|File
name|solrHome
decl_stmt|;
specifier|protected
name|SolrTrainingSet
name|trainingSet
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|initTestFolder
parameter_list|()
throws|throws
name|IOException
block|{
comment|//basedir - if present - is the project base folder
name|String
name|baseDir
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"basedir"
argument_list|)
decl_stmt|;
if|if
condition|(
name|baseDir
operator|==
literal|null
condition|)
block|{
comment|//if missing fall back to user.dir
name|baseDir
operator|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.dir"
argument_list|)
expr_stmt|;
block|}
comment|//store the current user.dir
name|userDir
operator|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.dir"
argument_list|)
expr_stmt|;
name|testRoot
operator|=
operator|new
name|File
argument_list|(
name|baseDir
argument_list|,
name|TEST_ROOT
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Topic TrainingSet Test Folder : "
operator|+
name|testRoot
argument_list|)
expr_stmt|;
if|if
condition|(
name|testRoot
operator|.
name|exists
argument_list|()
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|" ... delete files from previouse test"
argument_list|)
expr_stmt|;
name|FileUtils
operator|.
name|deleteDirectory
argument_list|(
name|testRoot
argument_list|)
expr_stmt|;
block|}
name|FileUtils
operator|.
name|forceMkdir
argument_list|(
name|testRoot
argument_list|)
expr_stmt|;
name|System
operator|.
name|setProperty
argument_list|(
literal|"user.dir"
argument_list|,
name|testRoot
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * resets the "user.dir" system property the the original value      */
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|cleanup
parameter_list|()
block|{
name|System
operator|.
name|setProperty
argument_list|(
literal|"user.dir"
argument_list|,
name|userDir
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|setup
parameter_list|()
throws|throws
name|IOException
throws|,
name|ParserConfigurationException
throws|,
name|SAXException
throws|,
name|ConfigurationException
block|{
name|solrHome
operator|=
operator|new
name|File
argument_list|(
name|testRoot
argument_list|,
literal|"test"
operator|+
name|testCounter
argument_list|)
expr_stmt|;
name|testCounter
operator|++
expr_stmt|;
name|assertTrue
argument_list|(
literal|"Unable to create solr.home directory '"
operator|+
name|solrHome
operator|+
literal|"'!"
argument_list|,
name|solrHome
operator|.
name|mkdir
argument_list|()
argument_list|)
expr_stmt|;
name|trainingsetSolrServer
operator|=
name|makeEmbeddedSolrServer
argument_list|(
name|solrHome
argument_list|,
literal|"trainingsetserver"
argument_list|,
literal|"default-topic-trainingset"
argument_list|,
literal|"default-topic-trainingset"
argument_list|)
expr_stmt|;
name|trainingSet
operator|=
operator|new
name|SolrTrainingSet
argument_list|()
expr_stmt|;
name|trainingSet
operator|.
name|configure
argument_list|(
name|getDefaultConfigParams
argument_list|()
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
comment|//FileUtils.deleteQuietly(solrHome);
name|solrHome
operator|=
literal|null
expr_stmt|;
name|trainingsetSolrServer
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testDateSerialization
parameter_list|()
throws|throws
name|Exception
block|{
name|log
operator|.
name|info
argument_list|(
literal|" --- testDateSerialization --- "
argument_list|)
expr_stmt|;
name|GregorianCalendar
name|timeUtc
init|=
operator|new
name|GregorianCalendar
argument_list|(
name|TimeZone
operator|.
name|getTimeZone
argument_list|(
literal|"UTC"
argument_list|)
argument_list|)
decl_stmt|;
name|timeUtc
operator|.
name|set
argument_list|(
literal|2012
argument_list|,
literal|23
argument_list|,
literal|12
argument_list|,
literal|06
argument_list|,
literal|43
argument_list|,
literal|00
argument_list|)
expr_stmt|;
name|timeUtc
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MILLISECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"2013-12-12T06:43:00.000Z"
argument_list|,
name|UTCTimeStamper
operator|.
name|utcIsoString
argument_list|(
name|timeUtc
operator|.
name|getTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|GregorianCalendar
name|timeCet
init|=
operator|new
name|GregorianCalendar
argument_list|(
name|TimeZone
operator|.
name|getTimeZone
argument_list|(
literal|"CET"
argument_list|)
argument_list|)
decl_stmt|;
name|timeCet
operator|.
name|set
argument_list|(
literal|2012
argument_list|,
literal|23
argument_list|,
literal|12
argument_list|,
literal|06
argument_list|,
literal|43
argument_list|,
literal|00
argument_list|)
expr_stmt|;
name|timeCet
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MILLISECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"2013-12-12T05:43:00.000Z"
argument_list|,
name|UTCTimeStamper
operator|.
name|utcIsoString
argument_list|(
name|timeCet
operator|.
name|getTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEmptyTrainingSet
parameter_list|()
throws|throws
name|TrainingSetException
block|{
name|log
operator|.
name|info
argument_list|(
literal|" --- testEmptyTrainingSet --- "
argument_list|)
expr_stmt|;
name|Batch
argument_list|<
name|Example
argument_list|>
name|examples
init|=
name|trainingSet
operator|.
name|getPositiveExamples
argument_list|(
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
name|examples
operator|=
name|trainingSet
operator|.
name|getNegativeExamples
argument_list|(
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
name|examples
operator|=
name|trainingSet
operator|.
name|getPositiveExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
name|examples
operator|=
name|trainingSet
operator|.
name|getPositiveExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_2
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
name|examples
operator|=
name|trainingSet
operator|.
name|getNegativeExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_2
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testStoringExamples
parameter_list|()
throws|throws
name|ConfigurationException
throws|,
name|TrainingSetException
block|{
name|log
operator|.
name|info
argument_list|(
literal|" --- testStoringExamples --- "
argument_list|)
expr_stmt|;
name|trainingSet
operator|.
name|registerExample
argument_list|(
literal|"example1"
argument_list|,
literal|"Text of example1."
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|)
argument_list|)
expr_stmt|;
name|trainingSet
operator|.
name|registerExample
argument_list|(
literal|"example2"
argument_list|,
literal|"Text of example2."
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_2
argument_list|)
argument_list|)
expr_stmt|;
name|trainingSet
operator|.
name|registerExample
argument_list|(
literal|"example3"
argument_list|,
literal|"Text of example3."
argument_list|,
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|Batch
argument_list|<
name|Example
argument_list|>
name|examples
init|=
name|trainingSet
operator|.
name|getPositiveExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_2
argument_list|)
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|examples
operator|.
name|items
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getContentString
argument_list|()
argument_list|,
literal|"Text of example2."
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
name|examples
operator|=
name|trainingSet
operator|.
name|getPositiveExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_3
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|examples
operator|.
name|items
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getContentString
argument_list|()
argument_list|,
literal|"Text of example1."
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|examples
operator|.
name|items
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|.
name|getContentString
argument_list|()
argument_list|,
literal|"Text of example2."
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
name|examples
operator|=
name|trainingSet
operator|.
name|getNegativeExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|examples
operator|.
name|items
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getContentString
argument_list|()
argument_list|,
literal|"Text of example3."
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
comment|// Test example update by adding topic3 to example1. The results of the previous query should remain
comment|// the same (inplace update).
name|trainingSet
operator|.
name|registerExample
argument_list|(
literal|"example1"
argument_list|,
literal|"Text of example1."
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_3
argument_list|)
argument_list|)
expr_stmt|;
name|examples
operator|=
name|trainingSet
operator|.
name|getPositiveExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_3
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|examples
operator|.
name|items
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getContentString
argument_list|()
argument_list|,
literal|"Text of example1."
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|examples
operator|.
name|items
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|.
name|getContentString
argument_list|()
argument_list|,
literal|"Text of example2."
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
comment|// Test example removal
name|trainingSet
operator|.
name|registerExample
argument_list|(
literal|"example1"
argument_list|,
literal|null
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_3
argument_list|)
argument_list|)
expr_stmt|;
name|examples
operator|=
name|trainingSet
operator|.
name|getPositiveExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_3
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|examples
operator|.
name|items
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getContentString
argument_list|()
argument_list|,
literal|"Text of example2."
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
name|trainingSet
operator|.
name|registerExample
argument_list|(
literal|"example2"
argument_list|,
literal|null
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_3
argument_list|)
argument_list|)
expr_stmt|;
name|examples
operator|=
name|trainingSet
operator|.
name|getPositiveExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_3
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testBatchingPositiveExamples
parameter_list|()
throws|throws
name|ConfigurationException
throws|,
name|TrainingSetException
block|{
name|log
operator|.
name|info
argument_list|(
literal|" --- testBatchingPositiveExamples --- "
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|expectedCollectedIds
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|expectedCollectedText
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|collectedIds
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|collectedText
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
literal|28
condition|;
name|i
operator|++
control|)
block|{
name|String
name|id
init|=
literal|"example-"
operator|+
name|i
decl_stmt|;
name|String
name|text
init|=
literal|"Text of example"
operator|+
name|i
operator|+
literal|"."
decl_stmt|;
name|trainingSet
operator|.
name|registerExample
argument_list|(
name|id
argument_list|,
name|text
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|)
argument_list|)
expr_stmt|;
name|expectedCollectedIds
operator|.
name|add
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|expectedCollectedText
operator|.
name|add
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
name|trainingSet
operator|.
name|setBatchSize
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|Batch
argument_list|<
name|Example
argument_list|>
name|examples
init|=
name|trainingSet
operator|.
name|getPositiveExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_2
argument_list|)
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|10
argument_list|,
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Example
name|example
range|:
name|examples
operator|.
name|items
control|)
block|{
name|collectedIds
operator|.
name|add
argument_list|(
name|example
operator|.
name|id
argument_list|)
expr_stmt|;
name|collectedText
operator|.
name|add
argument_list|(
name|example
operator|.
name|getContentString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
name|examples
operator|=
name|trainingSet
operator|.
name|getPositiveExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_2
argument_list|)
argument_list|,
name|examples
operator|.
name|nextOffset
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|10
argument_list|,
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Example
name|example
range|:
name|examples
operator|.
name|items
control|)
block|{
name|collectedIds
operator|.
name|add
argument_list|(
name|example
operator|.
name|id
argument_list|)
expr_stmt|;
name|collectedText
operator|.
name|add
argument_list|(
name|example
operator|.
name|getContentString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
name|examples
operator|=
name|trainingSet
operator|.
name|getPositiveExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_2
argument_list|)
argument_list|,
name|examples
operator|.
name|nextOffset
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|8
argument_list|,
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Example
name|example
range|:
name|examples
operator|.
name|items
control|)
block|{
name|collectedIds
operator|.
name|add
argument_list|(
name|example
operator|.
name|id
argument_list|)
expr_stmt|;
name|collectedText
operator|.
name|add
argument_list|(
name|example
operator|.
name|getContentString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|assertFalse
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedCollectedIds
argument_list|,
name|collectedIds
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedCollectedText
argument_list|,
name|collectedText
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testBatchingNegativeExamplesAndAutoId
parameter_list|()
throws|throws
name|ConfigurationException
throws|,
name|TrainingSetException
block|{
name|log
operator|.
name|info
argument_list|(
literal|" --- testBatchingNegativeExamplesAndAutoId --- "
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|expectedCollectedIds
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|expectedCollectedText
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|collectedIds
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|collectedText
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
literal|17
condition|;
name|i
operator|++
control|)
block|{
name|String
name|text
init|=
literal|"Text of example"
operator|+
name|i
operator|+
literal|"."
decl_stmt|;
name|String
name|id
init|=
name|trainingSet
operator|.
name|registerExample
argument_list|(
literal|null
argument_list|,
name|text
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|)
argument_list|)
decl_stmt|;
name|expectedCollectedIds
operator|.
name|add
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|expectedCollectedText
operator|.
name|add
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
name|trainingSet
operator|.
name|setBatchSize
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|Batch
argument_list|<
name|Example
argument_list|>
name|examples
init|=
name|trainingSet
operator|.
name|getNegativeExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_2
argument_list|)
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|10
argument_list|,
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Example
name|example
range|:
name|examples
operator|.
name|items
control|)
block|{
name|collectedIds
operator|.
name|add
argument_list|(
name|example
operator|.
name|id
argument_list|)
expr_stmt|;
name|collectedText
operator|.
name|add
argument_list|(
name|example
operator|.
name|getContentString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
name|examples
operator|=
name|trainingSet
operator|.
name|getNegativeExamples
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_2
argument_list|)
argument_list|,
name|examples
operator|.
name|nextOffset
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|7
argument_list|,
name|examples
operator|.
name|items
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Example
name|example
range|:
name|examples
operator|.
name|items
control|)
block|{
name|collectedIds
operator|.
name|add
argument_list|(
name|example
operator|.
name|id
argument_list|)
expr_stmt|;
name|collectedText
operator|.
name|add
argument_list|(
name|example
operator|.
name|getContentString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|assertFalse
argument_list|(
name|examples
operator|.
name|hasMore
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedCollectedIds
argument_list|,
name|collectedIds
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedCollectedText
argument_list|,
name|collectedText
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testHasChangedSince
parameter_list|()
throws|throws
name|Exception
block|{
name|log
operator|.
name|info
argument_list|(
literal|" --- testHasChangedSince --- "
argument_list|)
expr_stmt|;
name|Date
name|date0
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
name|assertFalse
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|)
argument_list|,
name|date0
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_2
argument_list|)
argument_list|,
name|date0
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_3
argument_list|)
argument_list|,
name|date0
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_2
argument_list|)
argument_list|,
name|date0
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_3
argument_list|)
argument_list|,
name|date0
argument_list|)
argument_list|)
expr_stmt|;
name|trainingSet
operator|.
name|registerExample
argument_list|(
literal|"example1"
argument_list|,
literal|"Text of example1."
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|)
argument_list|)
expr_stmt|;
name|trainingSet
operator|.
name|registerExample
argument_list|(
literal|"example2"
argument_list|,
literal|"Text of example2."
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_2
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|)
argument_list|,
name|date0
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_2
argument_list|)
argument_list|,
name|date0
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_3
argument_list|)
argument_list|,
name|date0
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_2
argument_list|)
argument_list|,
name|date0
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_3
argument_list|)
argument_list|,
name|date0
argument_list|)
argument_list|)
expr_stmt|;
comment|// check that the new registration look as compared to a new date (who are stored up to the
comment|// millisecond precision):
name|Thread
operator|.
name|sleep
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|Date
name|date1
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
name|assertFalse
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|)
argument_list|,
name|date1
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_2
argument_list|)
argument_list|,
name|date1
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_3
argument_list|)
argument_list|,
name|date1
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_2
argument_list|)
argument_list|,
name|date1
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|trainingSet
operator|.
name|hasChangedSince
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|TOPIC_1
argument_list|,
name|TOPIC_3
argument_list|)
argument_list|,
name|date1
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
name|SolrTrainingSet
operator|.
name|SOLR_CORE
argument_list|,
name|trainingsetSolrServer
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|SolrTrainingSet
operator|.
name|TRAINING_SET_NAME
argument_list|,
literal|"test-training-set"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|SolrTrainingSet
operator|.
name|EXAMPLE_ID_FIELD
argument_list|,
literal|"id"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|SolrTrainingSet
operator|.
name|EXAMPLE_TEXT_FIELD
argument_list|,
literal|"text"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|SolrTrainingSet
operator|.
name|TOPICS_URI_FIELD
argument_list|,
literal|"topics"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|SolrTrainingSet
operator|.
name|MODIFICATION_DATE_FIELD
argument_list|,
literal|"modification_dt"
argument_list|)
expr_stmt|;
return|return
name|config
return|;
block|}
block|}
end_class

end_unit

