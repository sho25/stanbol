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
name|entityhub
operator|.
name|yard
operator|.
name|solr
package|;
end_package

begin_import
import|import static
name|junit
operator|.
name|framework
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|junit
operator|.
name|framework
operator|.
name|Assert
operator|.
name|assertFalse
import|;
end_import

begin_import
import|import static
name|junit
operator|.
name|framework
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
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|Map
operator|.
name|Entry
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
name|SolrDirectoryManagerTest
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
name|SolrDirectoryManagerTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|SolrDirectoryManager
name|solrDirectoryManager
decl_stmt|;
specifier|private
specifier|static
name|File
name|expectedManagedDirectory
decl_stmt|;
specifier|private
specifier|static
name|Collection
argument_list|<
name|String
argument_list|>
name|expectedIndexNames
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"entityhub"
argument_list|,
literal|"cache"
argument_list|)
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|init
parameter_list|()
block|{
comment|//set to "${basedir}/some/rel/path" to test if property substitution works!
name|String
name|solrServerDir
init|=
literal|"${basedir}"
operator|+
name|SolrYardTest
operator|.
name|TEST_INDEX_REL_PATH
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"configured directory: "
operator|+
name|solrServerDir
argument_list|)
expr_stmt|;
name|System
operator|.
name|setProperty
argument_list|(
name|SolrDirectoryManager
operator|.
name|MANAGED_SOLR_DIR_PROPERTY
argument_list|,
name|solrServerDir
argument_list|)
expr_stmt|;
comment|//store the expected managed directory for later testing
name|expectedManagedDirectory
operator|=
operator|new
name|File
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"basedir"
argument_list|)
argument_list|,
name|SolrYardTest
operator|.
name|TEST_INDEX_REL_PATH
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"expected managed directory: "
operator|+
name|expectedManagedDirectory
argument_list|)
expr_stmt|;
comment|//create the SolrDirectoryManager used for the tests
name|Iterator
argument_list|<
name|SolrDirectoryManager
argument_list|>
name|providerIt
init|=
name|ServiceLoader
operator|.
name|load
argument_list|(
name|SolrDirectoryManager
operator|.
name|class
argument_list|,
name|SolrDirectoryManager
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|)
operator|.
name|iterator
argument_list|()
decl_stmt|;
if|if
condition|(
name|providerIt
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|solrDirectoryManager
operator|=
name|providerIt
operator|.
name|next
argument_list|()
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to instantiate "
operator|+
name|SolrDirectoryManager
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|" service by using "
operator|+
name|ServiceLoader
operator|.
name|class
operator|.
name|getName
argument_list|()
operator|+
literal|"!"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testManagedDirectoryInitialisation
parameter_list|()
block|{
comment|//the managed directory must be set based on the
name|expectedManagedDirectory
operator|.
name|equals
argument_list|(
name|solrDirectoryManager
operator|.
name|getManagedDirectory
argument_list|()
argument_list|)
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
name|testNullIndexName
parameter_list|()
block|{
name|solrDirectoryManager
operator|.
name|getSolrDirectory
argument_list|(
literal|null
argument_list|)
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
name|testEmptyIndexName
parameter_list|()
block|{
name|solrDirectoryManager
operator|.
name|getSolrDirectory
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetManagedIndexes
parameter_list|()
block|{
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
name|expectedIndexNames
argument_list|)
decl_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|File
argument_list|>
name|index
range|:
name|solrDirectoryManager
operator|.
name|getManagedIndices
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|expected
operator|.
name|remove
argument_list|(
name|index
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
comment|//test that the index dir is the expected location
name|File
name|expectedLocation
init|=
operator|new
name|File
argument_list|(
name|expectedManagedDirectory
argument_list|,
name|index
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expectedLocation
argument_list|,
name|index
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//test that the expected indexes where returned
name|assertTrue
argument_list|(
name|expected
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testIsManagedIndex
parameter_list|()
block|{
for|for
control|(
name|String
name|name
range|:
name|expectedIndexNames
control|)
block|{
name|assertTrue
argument_list|(
name|solrDirectoryManager
operator|.
name|isManagedIndex
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|assertFalse
argument_list|(
name|solrDirectoryManager
operator|.
name|isManagedIndex
argument_list|(
literal|"notAnIndex"
operator|+
name|System
operator|.
name|currentTimeMillis
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testIndexInitialisation
parameter_list|()
block|{
comment|//this is actually tested already by the initialisation of the
comment|//SolrYardTest ...
name|String
name|indexName
init|=
literal|"testIndexInitialisation_"
operator|+
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|File
name|indexDir
init|=
name|solrDirectoryManager
operator|.
name|getSolrDirectory
argument_list|(
name|indexName
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
operator|new
name|File
argument_list|(
name|expectedManagedDirectory
argument_list|,
name|indexName
argument_list|)
argument_list|,
name|indexDir
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|indexDir
operator|.
name|isDirectory
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

