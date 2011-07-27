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
name|registry
operator|.
name|api
operator|.
name|RegistryLoader
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
name|RegistryLoaderImpl
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
name|util
operator|.
name|RegistryUtils
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
name|io
operator|.
name|LibrarySource
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
name|OWLImportsDeclaration
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
comment|/**  * This class tests the correct loading of ontology libraries from an OWL repository. It also checks that  * ontologies referred to only by other libraries in the same repository are not loaded.  */
end_comment

begin_class
specifier|public
class|class
name|TestOntologyLibrary
block|{
specifier|private
name|String
name|registryResource
init|=
literal|"/ontologies/registry/onmtest.owl"
decl_stmt|;
specifier|private
specifier|static
name|ONManager
name|onm
decl_stmt|;
specifier|private
specifier|static
name|RegistryLoader
name|loader
decl_stmt|;
specifier|private
name|OWLOntologyManager
name|virginOntologyManager
decl_stmt|;
comment|/**      * Sets the ontology network manager and registry loader before running the tests.      */
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setupTest
parameter_list|()
throws|throws
name|Exception
block|{
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
name|RegistryManager
name|regman
init|=
operator|new
name|RegistryManagerImpl
argument_list|(
name|offline
argument_list|,
name|config
argument_list|)
decl_stmt|;
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
name|config
argument_list|)
expr_stmt|;
name|loader
operator|=
operator|new
name|RegistryLoaderImpl
argument_list|(
name|regman
argument_list|,
name|onm
argument_list|)
expr_stmt|;
block|}
comment|/**      * Resets the {@link OWLOntologyManager} used for tests, since caching phenomena across tests could bias      * the results.      *       * @throws Exception      *             if any error occurs;      */
annotation|@
name|Before
specifier|public
name|void
name|resetOntologyManager
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
literal|"/ontologies"
argument_list|)
decl_stmt|;
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
name|url
operator|=
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"/ontologies/registry"
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
comment|// *Not* adding mappers to empty resource directories.
comment|// It seems the Maven surefire plugin won't copy them.
comment|// url = getClass().getResource("/ontologies/odp");
comment|// virginOntologyManager.addIRIMapper(new AutoIRIMapper(new File(url.toURI()), true));
block|}
comment|/**      * Uses a plain {@link RegistryLoader} to load a single ontology library and checks for its expected hits      * and misses.      *       * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testLibraryLoad
parameter_list|()
throws|throws
name|Exception
block|{
name|IRI
name|localTestRegistry
init|=
name|IRI
operator|.
name|create
argument_list|(
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
name|registryResource
argument_list|)
argument_list|)
decl_stmt|;
name|Registry
name|reg
init|=
name|loader
operator|.
name|loadRegistry
argument_list|(
name|localTestRegistry
argument_list|,
name|virginOntologyManager
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|reg
operator|.
name|hasChildren
argument_list|()
argument_list|)
expr_stmt|;
name|Library
name|lib
init|=
literal|null
decl_stmt|;
comment|// Look for test #Library2
for|for
control|(
name|RegistryItem
name|item
range|:
name|reg
operator|.
name|getChildren
argument_list|()
control|)
block|{
if|if
condition|(
name|Locations
operator|.
name|LIBRARY_TEST2
operator|.
name|equals
argument_list|(
name|item
operator|.
name|getIRI
argument_list|()
argument_list|)
condition|)
block|{
name|lib
operator|=
operator|(
name|Library
operator|)
name|item
expr_stmt|;
break|break;
block|}
block|}
name|assertNotNull
argument_list|(
name|lib
argument_list|)
expr_stmt|;
comment|// Should be in the library.
name|boolean
name|hasShould
init|=
name|RegistryUtils
operator|.
name|containsOntologyRecursive
argument_list|(
name|lib
argument_list|,
name|Locations
operator|.
name|CHAR_DROPPED
argument_list|)
decl_stmt|;
comment|// Should NOT be in the library (belongs to another library in the same registry).
name|boolean
name|hasShouldNot
init|=
name|RegistryUtils
operator|.
name|containsOntologyRecursive
argument_list|(
name|lib
argument_list|,
name|Locations
operator|.
name|CHAR_ACTIVE
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|hasShould
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|hasShouldNot
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests the creation of an ontology input source from a single library. Because the test is run offline,      * import statements might be file URIs, so tests should not fail on this.      *       * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testLibrarySourceCreation
parameter_list|()
throws|throws
name|Exception
block|{
name|IRI
name|localTestRegistry
init|=
name|IRI
operator|.
name|create
argument_list|(
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
name|registryResource
argument_list|)
argument_list|)
decl_stmt|;
name|OntologyInputSource
name|src
init|=
operator|new
name|LibrarySource
argument_list|(
name|Locations
operator|.
name|LIBRARY_TEST1
argument_list|,
name|localTestRegistry
argument_list|,
name|virginOntologyManager
argument_list|,
name|loader
argument_list|)
decl_stmt|;
name|OWLOntology
name|o
init|=
name|src
operator|.
name|getRootOntology
argument_list|()
decl_stmt|;
name|boolean
name|hasImporting
init|=
literal|false
decl_stmt|,
name|hasImported
init|=
literal|false
decl_stmt|;
for|for
control|(
name|OWLImportsDeclaration
name|ax
range|:
name|o
operator|.
name|getImportsDeclarations
argument_list|()
control|)
block|{
comment|// Since we added a local IRI mapping, import statements might be using file: IRIs instead of
comment|// HTTP, in which case IRI equality would fail. So it is enough here to just check the filename.
name|String
name|tmpstr
init|=
name|ax
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|hasImporting
operator|&&
name|tmpstr
operator|.
name|endsWith
argument_list|(
literal|"characters_all.owl"
argument_list|)
condition|)
name|hasImporting
operator|=
literal|true
expr_stmt|;
elseif|else
if|if
condition|(
operator|!
name|hasImported
operator|&&
name|tmpstr
operator|.
name|endsWith
argument_list|(
literal|"maincharacters.owl"
argument_list|)
condition|)
name|hasImported
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|hasImporting
operator|&&
name|hasImported
condition|)
break|break;
block|}
name|assertTrue
argument_list|(
name|hasImporting
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|hasImported
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
