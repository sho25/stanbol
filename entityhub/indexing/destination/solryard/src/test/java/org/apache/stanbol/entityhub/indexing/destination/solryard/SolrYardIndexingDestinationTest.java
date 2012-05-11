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
name|indexing
operator|.
name|destination
operator|.
name|solryard
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
name|FileInputStream
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
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
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
name|Properties
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ServiceLoader
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
name|zip
operator|.
name|ZipEntry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|zip
operator|.
name|ZipFile
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
name|stanbol
operator|.
name|commons
operator|.
name|solr
operator|.
name|managed
operator|.
name|ManagedSolrServer
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
name|managed
operator|.
name|standalone
operator|.
name|StandaloneManagedSolrServer
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
name|indexing
operator|.
name|core
operator|.
name|IndexingDestination
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
name|indexing
operator|.
name|core
operator|.
name|config
operator|.
name|IndexingConfig
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
name|indexing
operator|.
name|core
operator|.
name|destination
operator|.
name|OsgiConfigurationUtil
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
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|model
operator|.
name|rdf
operator|.
name|RdfResourceEnum
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
name|yard
operator|.
name|Yard
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
name|yard
operator|.
name|YardException
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
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYard
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
comment|/**  * What to test:  *  - correct initialisation  *    - special schema initialisation  *    - default schema initialisation  *  - finalisation  *    - writing of the IndexFieldConfiguration  *    - creating of the {name}.solrindex.zip  *    - creating of the {name}.solrindex.ref  *      * Indexing needs not to be tested, because this is the responsibility of the  * Unit Tests for the used Yard implementation.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|SolrYardIndexingDestinationTest
block|{
specifier|public
specifier|static
specifier|final
name|Collection
argument_list|<
name|String
argument_list|>
name|EXPECTED_INDEX_ARCHIVE_FILE_NAMES
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"schema.xml"
argument_list|,
literal|"solrconfig.xml"
argument_list|,
literal|"segments.gen"
argument_list|)
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
name|SolrYardIndexingDestinationTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|CONFIG_ROOT
init|=
name|FilenameUtils
operator|.
name|separatorsToSystem
argument_list|(
literal|"testConfigs/"
argument_list|)
decl_stmt|;
comment|/**      * mvn copies the resources in "src/test/resources" to target/test-classes.      * This folder is than used as classpath.<p>      * "/target/test-files/" does not exist, but is created by the      * {@link IndexingConfig}.      */
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
literal|"/target/test-files"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|String
name|userDir
decl_stmt|;
specifier|private
specifier|static
name|String
name|testRoot
decl_stmt|;
comment|/**      * The methods resets the "user.dir" system property      */
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|initTestRootFolder
parameter_list|()
block|{
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
name|baseDir
operator|+
name|TEST_ROOT
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"ConfigTest Root : "
operator|+
name|testRoot
argument_list|)
expr_stmt|;
comment|//set the user.dir to the testRoot (needed to test loading of missing
comment|//configurations via classpath
comment|//store the current user.dir and reset it after the tests
name|System
operator|.
name|setProperty
argument_list|(
literal|"user.dir"
argument_list|,
name|testRoot
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
name|After
specifier|public
name|void
name|close
parameter_list|()
block|{
comment|//after each test we need ensure to shutdown the default ManagedSolrServer
comment|//because different tests use different Directories and therefore a new
comment|//instance needs to be created
name|StandaloneManagedSolrServer
operator|.
name|shutdownManagedServer
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testMissingBoostConfig
parameter_list|()
block|{
name|IndexingConfig
name|config
init|=
operator|new
name|IndexingConfig
argument_list|(
name|CONFIG_ROOT
operator|+
literal|"missingBoostConfig"
argument_list|,
name|CONFIG_ROOT
operator|+
literal|"missingBoostConfig"
argument_list|)
block|{}
decl_stmt|;
name|config
operator|.
name|getIndexingDestination
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testInvalidBoostConfig
parameter_list|()
block|{
name|IndexingConfig
name|config
init|=
operator|new
name|IndexingConfig
argument_list|(
name|CONFIG_ROOT
operator|+
literal|"invalidBoostConfig"
argument_list|,
name|CONFIG_ROOT
operator|+
literal|"invalidBoostConfig"
argument_list|)
block|{}
decl_stmt|;
name|config
operator|.
name|getIndexingDestination
argument_list|()
expr_stmt|;
block|}
comment|/**      * Tests that the Solr configuration is required, but the name of the config      * file is the default. The referenced directory is missing      */
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testMissingDefaultSolrSchemaConfig
parameter_list|()
block|{
name|IndexingConfig
name|config
init|=
operator|new
name|IndexingConfig
argument_list|(
name|CONFIG_ROOT
operator|+
literal|"missingDefaultSolrConf"
argument_list|,
name|CONFIG_ROOT
operator|+
literal|"missingDefaultSolrConf"
argument_list|)
block|{}
decl_stmt|;
name|config
operator|.
name|getIndexingDestination
argument_list|()
expr_stmt|;
block|}
comment|/**      * Tests that the Solr configuration is required and the name of the config      * file is specified. The referenced directory is missing      */
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testMissingSolrSchemaConfig
parameter_list|()
block|{
name|IndexingConfig
name|config
init|=
operator|new
name|IndexingConfig
argument_list|(
name|CONFIG_ROOT
operator|+
literal|"missingSolrConf"
argument_list|,
name|CONFIG_ROOT
operator|+
literal|"missingSolrConf"
argument_list|)
block|{}
decl_stmt|;
name|config
operator|.
name|getIndexingDestination
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSimple
parameter_list|()
throws|throws
name|YardException
throws|,
name|IOException
block|{
name|IndexingConfig
name|config
init|=
operator|new
name|IndexingConfig
argument_list|(
name|CONFIG_ROOT
operator|+
literal|"simple"
argument_list|,
name|CONFIG_ROOT
operator|+
literal|"simple"
argument_list|)
block|{}
decl_stmt|;
name|validateSolrDestination
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testWithSolrConf
parameter_list|()
throws|throws
name|YardException
throws|,
name|IOException
block|{
name|IndexingConfig
name|config
init|=
operator|new
name|IndexingConfig
argument_list|(
name|CONFIG_ROOT
operator|+
literal|"withSolrConf"
argument_list|,
name|CONFIG_ROOT
operator|+
literal|"withSolrConf"
argument_list|)
block|{}
decl_stmt|;
name|validateSolrDestination
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
comment|/**      * Checks if the SolrYardIndexingDestination returned by the       * {@link IndexingConfig} is valid and functional      * @param config the configuration      * @throws YardException indicates problems while working with the {@link SolrYard}      * returned by {@link IndexingDestination#getYard()}      * @throws IOException indicates problems while validating the SolrArchives      * created by the {@link IndexingDestination#finalise()} method      */
specifier|private
name|void
name|validateSolrDestination
parameter_list|(
name|IndexingConfig
name|config
parameter_list|)
throws|throws
name|YardException
throws|,
name|IOException
block|{
comment|//get the destination
name|IndexingDestination
name|destination
init|=
name|config
operator|.
name|getIndexingDestination
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|destination
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|destination
operator|.
name|getClass
argument_list|()
argument_list|,
name|SolrYardIndexingDestination
operator|.
name|class
argument_list|)
expr_stmt|;
comment|//initialise
name|assertTrue
argument_list|(
name|destination
operator|.
name|needsInitialisation
argument_list|()
argument_list|)
expr_stmt|;
name|destination
operator|.
name|initialise
argument_list|()
expr_stmt|;
comment|//test that the returned Yard instance is functional
name|Yard
name|yard
init|=
name|destination
operator|.
name|getYard
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|yard
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|yard
operator|.
name|getClass
argument_list|()
argument_list|,
name|SolrYard
operator|.
name|class
argument_list|)
expr_stmt|;
name|Representation
name|rep
init|=
name|yard
operator|.
name|getValueFactory
argument_list|()
operator|.
name|createRepresentation
argument_list|(
literal|"http://www.example.com/entity#123"
argument_list|)
decl_stmt|;
name|rep
operator|.
name|add
argument_list|(
name|NamespaceEnum
operator|.
name|rdfs
operator|+
literal|"label"
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|rep
operator|.
name|add
argument_list|(
name|NamespaceEnum
operator|.
name|rdfs
operator|+
literal|"description"
argument_list|,
literal|"Representation to test storage while indexing"
argument_list|)
expr_stmt|;
name|rep
operator|.
name|add
argument_list|(
name|RdfResourceEnum
operator|.
name|entityRank
operator|.
name|getUri
argument_list|()
argument_list|,
name|Float
operator|.
name|valueOf
argument_list|(
literal|0.8f
argument_list|)
argument_list|)
expr_stmt|;
name|yard
operator|.
name|store
argument_list|(
name|rep
argument_list|)
expr_stmt|;
comment|//finalise
name|destination
operator|.
name|finalise
argument_list|()
expr_stmt|;
comment|//test the archives
name|File
name|expectedSolrArchiveFile
init|=
operator|new
name|File
argument_list|(
name|config
operator|.
name|getDistributionFolder
argument_list|()
argument_list|,
name|config
operator|.
name|getName
argument_list|()
operator|+
literal|".solrindex.zip"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|expectedSolrArchiveFile
operator|.
name|isFile
argument_list|()
argument_list|)
expr_stmt|;
comment|// validate the archive
name|ZipFile
name|archive
init|=
operator|new
name|ZipFile
argument_list|(
name|expectedSolrArchiveFile
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|expected
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|EXPECTED_INDEX_ARCHIVE_FILE_NAMES
argument_list|)
decl_stmt|;
for|for
control|(
name|Enumeration
argument_list|<
name|?
extends|extends
name|ZipEntry
argument_list|>
name|entries
init|=
name|archive
operator|.
name|entries
argument_list|()
init|;
name|entries
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|ZipEntry
name|entry
init|=
name|entries
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Validate Entry : "
operator|+
name|entry
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
comment|//the name of the index MUST be the root folder within the Archive!
name|assertTrue
argument_list|(
name|entry
operator|.
name|getName
argument_list|()
operator|.
name|startsWith
argument_list|(
name|config
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|expected
operator|.
name|remove
argument_list|(
name|FilenameUtils
operator|.
name|getName
argument_list|(
name|entry
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
literal|"missing Files in index archive: "
operator|+
name|expected
argument_list|,
name|expected
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
comment|//TODO: reimplement to validate the created bundle!
comment|//        //check for the solrArchive reference file and validate required properties
comment|//        File expectedSolrArchiveReferenceFile =
comment|//            new File(,config.getName()+".solrindex.ref");
comment|//        assertTrue(expectedSolrArchiveReferenceFile.isFile());
comment|//        Properties solrRefProperties = new Properties();
comment|//        solrRefProperties.load(new FileInputStream(expectedSolrArchiveReferenceFile));
comment|//        assertTrue(solrRefProperties.getProperty("Index-Archive").equals(expectedSolrArchiveFile.getName()));
comment|//        assertTrue(solrRefProperties.getProperty("Name") != null);
block|}
block|}
end_class

end_unit

