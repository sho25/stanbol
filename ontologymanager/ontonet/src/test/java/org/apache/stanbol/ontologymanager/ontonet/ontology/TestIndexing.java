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
name|ontonet
operator|.
name|ontology
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
name|ontonet
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
name|assertTrue
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
name|api
operator|.
name|io
operator|.
name|OntologyInputSource
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
name|io
operator|.
name|ParentPathInputSource
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
name|OntologyIndex
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
name|OntologyScope
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
name|OntologySpace
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
name|owl
operator|.
name|OWLOntologyManagerFactory
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
name|IRI
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
name|OWLOntologyCreationException
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
name|TestIndexing
block|{
specifier|private
specifier|static
name|IRI
name|iri_minor
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontologies/pcomics/minorcharacters.owl"
argument_list|)
decl_stmt|,
name|iri_main
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontologies/pcomics/maincharacters.owl"
argument_list|)
decl_stmt|,
name|scopeIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/scope/IndexingTest"
argument_list|)
decl_stmt|,
name|testRegistryIri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontologies/registries/onmtest.owl"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|OWLOntologyManager
name|mgr
decl_stmt|;
specifier|private
specifier|static
name|ONManager
name|onm
decl_stmt|;
specifier|private
specifier|static
name|OntologyScope
name|scope
init|=
literal|null
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setup
parameter_list|()
block|{
specifier|final
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|emptyConfig
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
specifier|final
name|OfflineConfiguration
name|offline
init|=
operator|new
name|OfflineConfigurationImpl
argument_list|(
name|emptyConfig
argument_list|)
decl_stmt|;
comment|// RegistryManager regman = new RegistryManagerImpl(emptyConfig);
comment|// An ONManagerImpl with no store and default settings
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
name|emptyConfig
argument_list|)
expr_stmt|;
name|mgr
operator|=
name|OWLOntologyManagerFactory
operator|.
name|createOWLOntologyManager
argument_list|(
name|offline
operator|.
name|getOntologySourceLocations
argument_list|()
operator|.
name|toArray
argument_list|(
operator|new
name|IRI
index|[
literal|0
index|]
argument_list|)
argument_list|)
expr_stmt|;
comment|// Since it is registered, this scope must be unique, or subsequent
comment|// tests will fail on duplicate ID exceptions!
name|scopeIri
operator|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/scope/IndexingTest"
argument_list|)
expr_stmt|;
name|IRI
name|coreroot
init|=
name|IRI
operator|.
name|create
argument_list|(
name|scopeIri
operator|+
literal|"/core/root.owl"
argument_list|)
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|OWLOntology
name|oParent
init|=
literal|null
decl_stmt|;
try|try
block|{
name|oParent
operator|=
name|mgr
operator|.
name|createOntology
argument_list|(
name|coreroot
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e1
parameter_list|)
block|{
comment|// Uncomment if annotated with @BeforeClass instead of @Before
name|fail
argument_list|(
literal|"Could not create core root ontology."
argument_list|)
expr_stmt|;
block|}
comment|// The factory call also invokes loadRegistriesEager() and
comment|// gatherOntologies() so no need to test them individually.
comment|// try {
comment|// scope = onm.getOntologyScopeFactory().createOntologyScope(
comment|// scopeIri,
comment|// new RegistryIRISource(testRegistryIri, onm.getOwlCacheManager(), onm
comment|// .getRegistryLoader(), null
comment|// // new RootOntologySource(oParent
comment|// ));
comment|//
comment|// onm.getScopeRegistry().registerScope(scope);
comment|// } catch (DuplicateIDException e) {
comment|// // Uncomment if annotated with @BeforeClass instead of @Before ,
comment|// // comment otherwise.
comment|// fail("DuplicateID exception caught when creating test scope.");
comment|// }
block|}
annotation|@
name|After
specifier|public
name|void
name|cleanup
parameter_list|()
block|{
name|reset
argument_list|()
expr_stmt|;
block|}
comment|// @Test
specifier|public
name|void
name|testAddOntology
parameter_list|()
throws|throws
name|Exception
block|{
name|OntologyIndex
name|index
init|=
name|onm
operator|.
name|getOntologyIndex
argument_list|()
decl_stmt|;
comment|// Load communities ODP (and its import closure) from local resource.
name|URL
name|url
init|=
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"/ontologies/characters_all.owl"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|url
argument_list|)
expr_stmt|;
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|url
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|f
argument_list|)
expr_stmt|;
name|OntologyInputSource
name|commSrc
init|=
operator|new
name|ParentPathInputSource
argument_list|(
name|f
argument_list|)
decl_stmt|;
name|OntologySpace
name|cust
init|=
name|scope
operator|.
name|getCustomSpace
argument_list|()
decl_stmt|;
name|cust
operator|.
name|addOntology
argument_list|(
name|commSrc
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|index
operator|.
name|isOntologyLoaded
argument_list|(
name|iri_minor
argument_list|)
argument_list|)
expr_stmt|;
name|url
operator|=
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"/ontologies/minorcharacters.owl"
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|url
argument_list|)
expr_stmt|;
name|f
operator|=
operator|new
name|File
argument_list|(
name|url
operator|.
name|toURI
argument_list|()
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|f
argument_list|)
expr_stmt|;
name|cust
operator|.
name|removeOntology
argument_list|(
name|iri_minor
argument_list|)
expr_stmt|;
comment|// cust.removeOntology(commSrc);
name|assertFalse
argument_list|(
name|index
operator|.
name|isOntologyLoaded
argument_list|(
name|iri_minor
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetOntology
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Load the original objectRole ODP
name|OWLOntology
name|oObjRole
init|=
name|mgr
operator|.
name|loadOntology
argument_list|(
name|iri_main
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|oObjRole
argument_list|)
expr_stmt|;
comment|// Compare it against the one indexed.
comment|// FIXME reinstate these checks
comment|// OntologyIndex index = onm.getOntologyIndex();
comment|// assertNotNull(index.getOntology(objrole));
comment|// // assertSame() would fail.
comment|// assertEquals(index.getOntology(objrole), oObjRole);
block|}
annotation|@
name|Test
specifier|public
name|void
name|testIsOntologyLoaded
parameter_list|()
block|{
name|OntologyIndex
name|index
init|=
name|onm
operator|.
name|getOntologyIndex
argument_list|()
decl_stmt|;
name|IRI
name|coreroot
init|=
name|IRI
operator|.
name|create
argument_list|(
name|scopeIri
operator|+
literal|"/core/root.owl"
argument_list|)
decl_stmt|;
name|IRI
name|dne
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.ontologydesignpatterns.org/cp/owl/doesnotexist.owl"
argument_list|)
decl_stmt|;
name|IRI
name|objrole
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl"
argument_list|)
decl_stmt|;
comment|// FIXME reinstate these checks
comment|// assertTrue(index.isOntologyLoaded(coreroot));
comment|// assertTrue(index.isOntologyLoaded(objrole));
comment|// TODO : find a way to index anonymous ontologies
name|assertTrue
argument_list|(
operator|!
name|index
operator|.
name|isOntologyLoaded
argument_list|(
name|dne
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

