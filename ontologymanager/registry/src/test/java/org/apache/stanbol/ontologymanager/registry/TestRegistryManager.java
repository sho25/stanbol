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
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|registry
operator|.
name|MockOsgiContext
operator|.
name|parser
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
name|ontologymanager
operator|.
name|registry
operator|.
name|MockOsgiContext
operator|.
name|reset
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
name|ontologymanager
operator|.
name|registry
operator|.
name|MockOsgiContext
operator|.
name|tcManager
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
name|assertNotSame
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
name|assertSame
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
name|api
operator|.
name|ontology
operator|.
name|OntologyProvider
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
name|ontonet
operator|.
name|impl
operator|.
name|clerezza
operator|.
name|ClerezzaOntologyProvider
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
name|Library
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
name|api
operator|.
name|model
operator|.
name|RegistryItem
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
name|OWLOntology
import|;
end_import

begin_comment
comment|/**  * Test the correct creation and setup of {@link RegistryManager} implementations.  */
end_comment

begin_class
specifier|public
class|class
name|TestRegistryManager
block|{
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
comment|/*      * This can very well stay the same across tests.      */
specifier|private
specifier|static
name|OfflineConfiguration
name|offline
decl_stmt|;
comment|/**      * Resets all configurations (the offline and registry manager ones).      *       * @throws Exception      */
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
comment|// We need this to make sure the local meta.owl (which does not import codolight) is loaded.
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
name|reset
argument_list|()
expr_stmt|;
block|}
specifier|private
name|RegistryManager
name|regman
decl_stmt|;
specifier|private
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|provider
decl_stmt|;
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
name|After
specifier|public
name|void
name|cleanup
parameter_list|()
throws|throws
name|Exception
block|{
name|reset
argument_list|()
expr_stmt|;
name|provider
operator|=
operator|new
name|ClerezzaOntologyProvider
argument_list|(
name|tcManager
argument_list|,
name|offline
argument_list|,
name|parser
argument_list|)
expr_stmt|;
block|}
comment|/**      * Verifies that by instantiating a new {@link RegistryManager} with a centralised caching policy and      * loading two registries, they share the same cache ontology manager.      *       * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testCachingCentralised
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
name|provider
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
name|Library
argument_list|>
name|it
init|=
name|regman
operator|.
name|getLibraries
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|OntologyProvider
argument_list|<
name|?
argument_list|>
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
comment|// Now "touch" a library.
name|Registry
name|reg
decl_stmt|;
do|do
name|reg
operator|=
name|regman
operator|.
name|getRegistries
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
expr_stmt|;
do|while
condition|(
operator|!
name|reg
operator|.
name|hasChildren
argument_list|()
condition|)
do|;
name|assertNotNull
argument_list|(
name|reg
argument_list|)
expr_stmt|;
comment|// There has to be at least one non-empty lib from the test ontologies.
name|Library
name|lib
init|=
literal|null
decl_stmt|;
name|RegistryItem
index|[]
name|children
init|=
name|reg
operator|.
name|getChildren
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
name|children
operator|.
name|length
operator|&&
name|lib
operator|==
literal|null
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|children
index|[
name|i
index|]
operator|instanceof
name|Library
condition|)
name|lib
operator|=
call|(
name|Library
call|)
argument_list|(
name|children
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|lib
argument_list|)
expr_stmt|;
comment|// Touch the library. Also test that the listener system works.
name|assertFalse
argument_list|(
name|lib
operator|.
name|getOntologies
argument_list|(
name|OWLOntology
operator|.
name|class
argument_list|)
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Verifies that by instantiating a new {@link RegistryManager} with a distributed caching policy and      * loading two registries, they have different ontology managers.      *       * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testCachingDistributed
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
name|provider
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
name|Library
argument_list|>
name|it
init|=
name|regman
operator|.
name|getLibraries
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|OntologyProvider
argument_list|<
name|?
argument_list|>
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
comment|// Just checking against the first in the list.
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
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
comment|/**      * Verifies that by setting the loading policy to eager (LAZY_LOADING = false), any random library will      * respond true to a call to {@link Library#isLoaded()} without ever "touching" its content.      *       * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testLoadingEager
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
name|configuration
operator|.
name|put
argument_list|(
name|RegistryManager
operator|.
name|LAZY_LOADING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|regman
operator|=
operator|new
name|RegistryManagerImpl
argument_list|(
name|offline
argument_list|,
name|provider
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
comment|// Now pick a library.
name|Registry
name|reg
decl_stmt|;
do|do
name|reg
operator|=
name|regman
operator|.
name|getRegistries
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
expr_stmt|;
do|while
condition|(
operator|!
name|reg
operator|.
name|hasChildren
argument_list|()
condition|)
do|;
name|assertNotNull
argument_list|(
name|reg
argument_list|)
expr_stmt|;
comment|// There has to be at least one non-empty library from the test registries...
name|Library
name|lib
init|=
literal|null
decl_stmt|;
name|RegistryItem
index|[]
name|children
init|=
name|reg
operator|.
name|getChildren
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
name|children
operator|.
name|length
operator|&&
name|lib
operator|==
literal|null
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|children
index|[
name|i
index|]
operator|instanceof
name|Library
condition|)
name|lib
operator|=
call|(
name|Library
call|)
argument_list|(
name|children
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|lib
argument_list|)
expr_stmt|;
comment|// ...and its ontologies must already be loaded without having to request them.
name|assertTrue
argument_list|(
name|lib
operator|.
name|isLoaded
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Verifies that by setting the loading policy to lazy (LAZY_LOADING = true), any random library will      * respond false to a call to {@link Library#isLoaded()}, until its content is "touched" via a call to      * {@link Library#getOntologies()}, only after which will it return true.      *       * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testLoadingLazy
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
name|configuration
operator|.
name|put
argument_list|(
name|RegistryManager
operator|.
name|LAZY_LOADING
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|regman
operator|=
operator|new
name|RegistryManagerImpl
argument_list|(
name|offline
argument_list|,
name|provider
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
comment|// Now pick a library.
name|Registry
name|reg
decl_stmt|;
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
do|do
name|reg
operator|=
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
comment|// We need a registry with at least 2 libraries to check that only one will be loaded.
do|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
operator|&&
operator|!
name|reg
operator|.
name|hasChildren
argument_list|()
operator|||
name|reg
operator|.
name|getChildren
argument_list|()
operator|.
name|length
operator|<
literal|2
condition|)
do|;
name|assertNotNull
argument_list|(
name|reg
argument_list|)
expr_stmt|;
comment|// There has to be at least one library with 2 children or more from the test registries...
name|Library
name|lib1
init|=
literal|null
decl_stmt|,
name|lib2
init|=
literal|null
decl_stmt|;
name|RegistryItem
index|[]
name|children
init|=
name|reg
operator|.
name|getChildren
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|children
operator|.
name|length
operator|>=
literal|2
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|children
operator|.
name|length
operator|-
literal|1
operator|&&
name|lib1
operator|==
literal|null
operator|&&
name|lib2
operator|==
literal|null
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|children
index|[
name|i
index|]
operator|instanceof
name|Library
condition|)
name|lib1
operator|=
call|(
name|Library
call|)
argument_list|(
name|children
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|children
index|[
name|i
operator|+
literal|1
index|]
operator|instanceof
name|Library
condition|)
name|lib2
operator|=
call|(
name|Library
call|)
argument_list|(
name|children
index|[
name|i
operator|+
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
name|assertFalse
argument_list|(
name|lib1
operator|==
name|lib2
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|lib1
argument_list|)
expr_stmt|;
comment|// ...but its ontologies must not be loaded yet.
name|assertFalse
argument_list|(
name|lib1
operator|.
name|isLoaded
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|lib2
operator|.
name|isLoaded
argument_list|()
argument_list|)
expr_stmt|;
comment|// Touch the library. Also test that the listener system works.
name|assertFalse
argument_list|(
name|lib1
operator|.
name|getOntologies
argument_list|(
name|OWLOntology
operator|.
name|class
argument_list|)
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|lib1
operator|.
name|isLoaded
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|lib2
operator|.
name|isLoaded
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

