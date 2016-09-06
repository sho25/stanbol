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
name|core
operator|.
name|destination
package|;
end_package

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
name|net
operator|.
name|URISyntaxException
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
name|*
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
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|IndexerFactory
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

begin_class
specifier|public
class|class
name|OsgiConfigurationUtilTest
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
name|OsgiConfigurationUtilTest
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
literal|"testOsgiConfiguration/"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|IndexerFactory
name|factory
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
annotation|@
name|Test
specifier|public
name|void
name|testOsgiConfigurationGeneration
parameter_list|()
throws|throws
name|URISyntaxException
throws|,
name|IOException
block|{
name|String
name|name
init|=
name|CONFIG_ROOT
operator|+
literal|"bundlebuild"
decl_stmt|;
comment|//copy test destination folder to mock-up the destination "config" folder creation operation
name|String
name|destConfFolder
init|=
name|name
operator|+
literal|"/indexing/destination/"
operator|+
literal|"config"
decl_stmt|;
name|File
name|destConfSource
init|=
operator|new
name|File
argument_list|(
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"/"
operator|+
name|destConfFolder
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|File
name|destConfTarget
init|=
operator|new
name|File
argument_list|(
name|testRoot
operator|+
literal|"/"
operator|+
name|destConfFolder
argument_list|)
decl_stmt|;
name|FileUtils
operator|.
name|copyDirectory
argument_list|(
name|destConfSource
argument_list|,
name|destConfTarget
argument_list|)
expr_stmt|;
name|File
name|distFolder
init|=
operator|new
name|File
argument_list|(
name|testRoot
operator|+
literal|'/'
operator|+
name|name
operator|+
literal|"/indexing/dist"
argument_list|)
decl_stmt|;
name|File
name|bundle
init|=
operator|new
name|File
argument_list|(
name|distFolder
argument_list|,
literal|"org.apache.stanbol.data.site.simple-1.0.0.jar"
argument_list|)
decl_stmt|;
name|IndexingConfig
name|config
init|=
operator|new
name|IndexingConfig
argument_list|(
name|name
argument_list|,
name|name
argument_list|)
block|{}
decl_stmt|;
name|OsgiConfigurationUtil
operator|.
name|createBundle
argument_list|(
name|config
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"Configuration bundle '"
operator|+
name|bundle
operator|+
literal|"' was not created!"
argument_list|,
name|bundle
operator|.
name|isFile
argument_list|()
argument_list|)
expr_stmt|;
comment|//if choose to get .createBundle throwing exceptions, this test may be better :
comment|//        try{
comment|//        	OsgiConfigurationUtil.createBundle(config);
comment|//        }catch(Exception npe){
comment|//        	fail("NPE fired when try to build the osgi configuration");
comment|//        }
block|}
block|}
end_class

end_unit

