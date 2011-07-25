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
name|ontologymanager
operator|.
name|registry
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
name|*
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
name|Hashtable
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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|OfflineConfiguration
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|impl
operator|.
name|OfflineConfigurationImpl
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
name|ontologymanager
operator|.
name|registry
operator|.
name|api
operator|.
name|RegistryManager
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
name|ontologymanager
operator|.
name|registry
operator|.
name|api
operator|.
name|model
operator|.
name|CachingPolicy
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
name|ontologymanager
operator|.
name|registry
operator|.
name|api
operator|.
name|model
operator|.
name|Registry
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
name|ontologymanager
operator|.
name|registry
operator|.
name|impl
operator|.
name|RegistryManagerImpl
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
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntologyManager
import|;
end_import

begin_class
specifier|public
class|class
name|TestRegistryManager
block|{
specifier|private
name|RegistryManager
name|regman
decl_stmt|;
comment|/*      * This is the registry manager configuration (which varies across tests).      */
specifier|private
specifier|static
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
decl_stmt|;
comment|/*      * This can very well stay the same across      */
specifier|private
specifier|static
name|OfflineConfiguration
name|offline
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setup
parameter_list|()
throws|throws
name|Exception
block|{
name|configuration
operator|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
expr_stmt|;
name|configuration
operator|.
name|put
argument_list|(
name|OfflineConfiguration
operator|.
name|ONTOLOGY_PATHS
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"/ontologies"
block|,
literal|"/ontologies/registry"
block|}
argument_list|)
expr_stmt|;
name|configuration
operator|.
name|put
argument_list|(
name|RegistryManager
operator|.
name|REGISTRY_LOCATIONS
argument_list|,
operator|new
name|String
index|[]
block|{
name|TestRegistryManager
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"/ontologies/registry/onmtest.owl"
argument_list|)
operator|.
name|toString
argument_list|()
block|,
name|TestRegistryManager
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"/ontologies/registry/onmtest_additions.owl"
argument_list|)
operator|.
name|toString
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|offline
operator|=
operator|new
name|OfflineConfigurationImpl
argument_list|(
name|configuration
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|setupTests
parameter_list|()
throws|throws
name|Exception
block|{}
annotation|@
name|Test
specifier|public
name|void
name|testCentralisedCaching
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Change the caching policy and setup a new registry manager.
name|configuration
operator|.
name|put
argument_list|(
name|RegistryManager
operator|.
name|CACHING_POLICY
argument_list|,
name|CachingPolicy
operator|.
name|CENTRALISED
argument_list|)
expr_stmt|;
name|regman
operator|=
operator|new
name|RegistryManagerImpl
argument_list|(
name|offline
argument_list|,
name|configuration
argument_list|)
expr_stmt|;
comment|// Check that the configuration was set.
name|assertNotNull
argument_list|(
name|regman
argument_list|)
expr_stmt|;
name|assertSame
argument_list|(
name|CachingPolicy
operator|.
name|CENTRALISED
argument_list|,
name|regman
operator|.
name|getCachingPolicy
argument_list|()
argument_list|)
expr_stmt|;
comment|// All registries must have the same cache.
name|Iterator
argument_list|<
name|Registry
argument_list|>
name|it
init|=
name|regman
operator|.
name|getRegistries
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|OWLOntologyManager
name|cache
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getCache
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
name|assertSame
argument_list|(
name|cache
argument_list|,
name|it
operator|.
name|next
argument_list|()
operator|.
name|getCache
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testDistributedCaching
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Change the caching policy and setup a new registry manager.
name|configuration
operator|.
name|put
argument_list|(
name|RegistryManager
operator|.
name|CACHING_POLICY
argument_list|,
name|CachingPolicy
operator|.
name|DISTRIBUTED
argument_list|)
expr_stmt|;
name|regman
operator|=
operator|new
name|RegistryManagerImpl
argument_list|(
name|offline
argument_list|,
name|configuration
argument_list|)
expr_stmt|;
comment|// Check that the configuration was set.
name|assertNotNull
argument_list|(
name|regman
argument_list|)
expr_stmt|;
name|assertSame
argument_list|(
name|CachingPolicy
operator|.
name|DISTRIBUTED
argument_list|,
name|regman
operator|.
name|getCachingPolicy
argument_list|()
argument_list|)
expr_stmt|;
comment|// Each registry must have its own distinct cache.
name|Iterator
argument_list|<
name|Registry
argument_list|>
name|it
init|=
name|regman
operator|.
name|getRegistries
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|OWLOntologyManager
name|cache
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getCache
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
name|assertNotSame
argument_list|(
name|cache
argument_list|,
name|it
operator|.
name|next
argument_list|()
operator|.
name|getCache
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

