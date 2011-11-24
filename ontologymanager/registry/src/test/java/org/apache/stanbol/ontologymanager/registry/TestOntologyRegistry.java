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
name|net
operator|.
name|URL
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
name|Dictionary
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
name|serializedform
operator|.
name|Parser
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
name|serializedform
operator|.
name|Serializer
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
name|simple
operator|.
name|storage
operator|.
name|SimpleTcProvider
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
name|ONManager
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
name|ONManagerImpl
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
name|api
operator|.
name|model
operator|.
name|RegistryOntology
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
name|apibinding
operator|.
name|OWLManager
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

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|util
operator|.
name|AutoIRIMapper
import|;
end_import

begin_comment
comment|/**  * Verifies the correct setup of ontology registries.  */
end_comment

begin_class
specifier|public
class|class
name|TestOntologyRegistry
block|{
specifier|private
name|String
name|scopeIri
init|=
literal|"Scope"
decl_stmt|;
specifier|private
specifier|static
name|ONManager
name|onm
decl_stmt|;
specifier|private
specifier|static
name|RegistryManager
name|regman
decl_stmt|;
specifier|private
name|OWLOntologyManager
name|virginOntologyManager
decl_stmt|;
comment|/**      * Sets the registry and ontology network managers, which are immutable across tests.      */
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setup
parameter_list|()
block|{
comment|// We use a single Dictionary for storing all configurations.
specifier|final
name|Dictionary
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
name|OfflineConfiguration
name|offline
init|=
operator|new
name|OfflineConfigurationImpl
argument_list|(
name|config
argument_list|)
decl_stmt|;
comment|// The registry manager can be updated via calls to createModel()
name|regman
operator|=
operator|new
name|RegistryManagerImpl
argument_list|(
name|offline
argument_list|,
operator|new
name|ClerezzaOntologyProvider
argument_list|(
operator|new
name|SimpleTcProvider
argument_list|()
argument_list|,
name|offline
argument_list|,
operator|new
name|Parser
argument_list|()
argument_list|)
argument_list|,
name|config
argument_list|)
expr_stmt|;
comment|// An ONManager with no storage support and same offline settings as the registry manager.
name|onm
operator|=
operator|new
name|ONManagerImpl
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
name|offline
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
comment|/**      * Resets the virgin ontology manager for each test.      *       * @throws Exception      */
annotation|@
name|Before
specifier|public
name|void
name|setupSources
parameter_list|()
throws|throws
name|Exception
block|{
name|virginOntologyManager
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
expr_stmt|;
name|URL
name|url
init|=
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"/ontologies/registry"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|url
argument_list|)
expr_stmt|;
name|virginOntologyManager
operator|.
name|addIRIMapper
argument_list|(
operator|new
name|AutoIRIMapper
argument_list|(
operator|new
name|File
argument_list|(
name|url
operator|.
name|toURI
argument_list|()
argument_list|)
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
comment|// Population is lazy; no need to add other mappers.
block|}
comment|/**      * Verifies that a call to {@link RegistryManager#createModel(Set)} with a registry location creates the      * object model accordingly.      *       * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testPopulateRegistry
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Create the model from a single registry.
name|OWLOntology
name|oReg
init|=
name|virginOntologyManager
operator|.
name|loadOntology
argument_list|(
name|Locations
operator|.
name|_REGISTRY_TEST
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Registry
argument_list|>
name|rs
init|=
name|regman
operator|.
name|createModel
argument_list|(
name|Collections
operator|.
name|singleton
argument_list|(
name|oReg
argument_list|)
argument_list|)
decl_stmt|;
comment|// There has to be a single registry, with the expected number of children.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|rs
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Registry
name|r
init|=
name|rs
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|r
operator|.
name|hasChildren
argument_list|()
argument_list|)
expr_stmt|;
comment|// The nonexistent library should also be included, if using the more powerful algorithm.
name|int
name|count
init|=
literal|3
decl_stmt|;
comment|// set to 2 if using the less powerful algorithm.
name|assertEquals
argument_list|(
name|count
argument_list|,
name|r
operator|.
name|getChildren
argument_list|()
operator|.
name|length
argument_list|)
expr_stmt|;
comment|// There are no libreries without ontologies in the test registry.
for|for
control|(
name|RegistryItem
name|ri
range|:
name|r
operator|.
name|getChildren
argument_list|()
control|)
name|assertTrue
argument_list|(
name|ri
operator|.
name|hasChildren
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Verifies that, when loading multiple registries that add library information to each other, the overall      * model reflects the union of these registries.      *       * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testRegistryUnion
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Create the model from two overlapping registries.
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|regs
init|=
operator|new
name|HashSet
argument_list|<
name|OWLOntology
argument_list|>
argument_list|()
decl_stmt|;
name|regs
operator|.
name|add
argument_list|(
name|virginOntologyManager
operator|.
name|loadOntology
argument_list|(
name|Locations
operator|.
name|_REGISTRY_TEST
argument_list|)
argument_list|)
expr_stmt|;
name|regs
operator|.
name|add
argument_list|(
name|virginOntologyManager
operator|.
name|loadOntology
argument_list|(
name|Locations
operator|.
name|_REGISTRY_TEST_ADDITIONS
argument_list|)
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|Registry
argument_list|>
name|rs
init|=
name|regman
operator|.
name|createModel
argument_list|(
name|regs
argument_list|)
decl_stmt|;
for|for
control|(
name|Registry
name|r
range|:
name|rs
control|)
block|{
comment|// The nonexistent library should also be included, if using the more powerful algorithm.
name|int
name|count
init|=
literal|3
decl_stmt|;
comment|// set to 2 if using the less powerful algorithm.
if|if
condition|(
name|Locations
operator|.
name|_REGISTRY_TEST
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getIRI
argument_list|()
argument_list|)
condition|)
name|assertEquals
argument_list|(
name|count
argument_list|,
name|r
operator|.
name|getChildren
argument_list|()
operator|.
name|length
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|Locations
operator|.
name|_REGISTRY_TEST_ADDITIONS
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getIRI
argument_list|()
argument_list|)
condition|)
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|r
operator|.
name|getChildren
argument_list|()
operator|.
name|length
argument_list|)
expr_stmt|;
comment|// Check that we find the expected ontology in the expected library.
for|for
control|(
name|RegistryItem
name|lib
range|:
name|r
operator|.
name|getChildren
argument_list|()
control|)
block|{
if|if
condition|(
name|Locations
operator|.
name|LIBRARY_TEST1
operator|.
name|equals
argument_list|(
name|lib
operator|.
name|getIRI
argument_list|()
argument_list|)
condition|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|RegistryItem
name|child
range|:
name|lib
operator|.
name|getChildren
argument_list|()
control|)
block|{
if|if
condition|(
name|child
operator|instanceof
name|RegistryOntology
operator|&&
name|Locations
operator|.
name|ONT_TEST1
operator|.
name|equals
argument_list|(
name|child
operator|.
name|getIRI
argument_list|()
argument_list|)
condition|)
block|{
name|found
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
name|assertTrue
argument_list|(
name|found
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
comment|// /**
comment|// * Verifies that the addition of a null or valid registry source to a session space works.
comment|// */
comment|// @Test
comment|// public void testAddRegistryToSessionSpace() throws Exception {
comment|// SessionOntologySpace space = null;
comment|// space = onm.getOntologySpaceFactory().createSessionOntologySpace(scopeIri);
comment|// space.setUp();
comment|// // space.addOntology(new
comment|// // OntologyRegistryIRISource(testRegistryIri,onm.getOwlCacheManager(),onm.getRegistryLoader()));
comment|// space.addOntology(ontologySource);
comment|// // FIXME : no longer use the top ontology?
comment|// assertTrue(space.asOWLOntology() != null);
comment|// assertTrue(space.getOntologies(true).contains(space.asOWLOntology()));
comment|// }
comment|//
comment|// /**
comment|// * Verifies that an ontology scope with a null or valid registry source is created correctly.
comment|// */
comment|// @Test
comment|// public void testScopeCreationWithRegistry() throws Exception {
comment|// OntologyScope scope = null;
comment|// // The input source instantiation automatically loads the entire content of a registry, no need to
comment|// // test loading methods individually.
comment|// scope = onm.getOntologyScopeFactory().createOntologyScope(scopeIri, ontologySource);
comment|// assertTrue(scope != null&& scope.getCoreSpace().asOWLOntology() != null);
comment|// }
comment|//
comment|// /**
comment|// * Verifies that an ontology space with a null or valid registry source is created correctly.
comment|// */
comment|// @Test
comment|// public void testSpaceCreationWithRegistry() throws Exception {
comment|// // setupOfflineMapper();
comment|// CoreOntologySpace space = null;
comment|// // The input source instantiation automatically loads the entire content of a registry, no need to
comment|// // test loading methods individually.
comment|// space = onm.getOntologySpaceFactory().createCoreOntologySpace(scopeIri, ontologySource);
comment|// assertTrue(space != null&& space.asOWLOntology() != null);
comment|// }
block|}
end_class

end_unit

