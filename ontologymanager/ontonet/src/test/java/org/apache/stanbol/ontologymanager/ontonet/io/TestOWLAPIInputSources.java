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
name|io
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
name|FileWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
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
name|stanbol
operator|.
name|ontologymanager
operator|.
name|ontonet
operator|.
name|Constants
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
name|io
operator|.
name|RootOntologyIRISource
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
name|io
operator|.
name|WriterDocumentTarget
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
name|OWLClass
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
name|OWLDataFactory
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
name|OWLIndividual
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
name|OWLNamedIndividual
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
name|OWLOntologyIRIMapper
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

begin_class
specifier|public
class|class
name|TestOWLAPIInputSources
block|{
specifier|private
specifier|static
name|OWLDataFactory
name|df
decl_stmt|;
specifier|private
specifier|static
name|ONManager
name|onm
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setUp
parameter_list|()
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|onmconf
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
name|onm
operator|=
operator|new
name|ONManagerImpl
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
operator|new
name|OfflineConfigurationImpl
argument_list|(
name|onmconf
argument_list|)
argument_list|,
name|onmconf
argument_list|)
expr_stmt|;
name|df
operator|=
name|OWLManager
operator|.
name|getOWLDataFactory
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testAutoIRIMapper
parameter_list|()
throws|throws
name|Exception
block|{
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
name|assertNotNull
argument_list|(
name|url
argument_list|)
expr_stmt|;
name|File
name|file
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
name|assertTrue
argument_list|(
name|file
operator|.
name|exists
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|file
operator|.
name|isDirectory
argument_list|()
argument_list|)
expr_stmt|;
name|OWLOntologyIRIMapper
name|mapper
init|=
operator|new
name|AutoIRIMapper
argument_list|(
name|file
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|IRI
name|dummyiri
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontologies/peanuts/dummycharacters.owl"
argument_list|)
decl_stmt|;
comment|// Cleanup may be required if previous tests have failed.
if|if
condition|(
name|mapper
operator|.
name|getDocumentIRI
argument_list|(
name|dummyiri
argument_list|)
operator|!=
literal|null
condition|)
block|{
operator|new
name|File
argument_list|(
name|mapper
operator|.
name|getDocumentIRI
argument_list|(
name|dummyiri
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
operator|.
name|delete
argument_list|()
expr_stmt|;
operator|(
operator|(
name|AutoIRIMapper
operator|)
name|mapper
operator|)
operator|.
name|update
argument_list|()
expr_stmt|;
block|}
name|assertFalse
argument_list|(
name|dummyiri
operator|.
name|equals
argument_list|(
name|mapper
operator|.
name|getDocumentIRI
argument_list|(
name|dummyiri
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Create a new ontology in the test resources.
name|OWLOntologyManager
name|mgr
init|=
name|OWLOntologyManagerFactory
operator|.
name|createOWLOntologyManager
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|OWLOntology
name|o
init|=
name|mgr
operator|.
name|createOntology
argument_list|(
name|dummyiri
argument_list|)
decl_stmt|;
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|URI
operator|.
name|create
argument_list|(
name|url
operator|.
name|toString
argument_list|()
operator|+
literal|"/dummycharacters.owl"
argument_list|)
argument_list|)
decl_stmt|;
name|mgr
operator|.
name|saveOntology
argument_list|(
name|o
argument_list|,
operator|new
name|WriterDocumentTarget
argument_list|(
operator|new
name|FileWriter
argument_list|(
name|f
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|f
operator|.
name|exists
argument_list|()
argument_list|)
expr_stmt|;
operator|(
operator|(
name|AutoIRIMapper
operator|)
name|mapper
operator|)
operator|.
name|update
argument_list|()
expr_stmt|;
comment|// The old mapper should be able to locate the new ontology.
name|assertFalse
argument_list|(
name|dummyiri
operator|.
name|equals
argument_list|(
name|mapper
operator|.
name|getDocumentIRI
argument_list|(
name|dummyiri
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// A new mapper too
name|OWLOntologyIRIMapper
name|mapper2
init|=
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
decl_stmt|;
name|assertFalse
argument_list|(
name|dummyiri
operator|.
name|equals
argument_list|(
name|mapper2
operator|.
name|getDocumentIRI
argument_list|(
name|dummyiri
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// cleanup
name|f
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
comment|/**      * Uses a {@link ParentPathInputSource} to load an ontology importing a modified FOAF, both located in the      * same resource directory.      *       * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testOfflineImport
parameter_list|()
throws|throws
name|Exception
block|{
name|URL
name|url
init|=
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"/ontologies/maincharacters.owl"
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
argument_list|<
name|OWLOntology
argument_list|>
name|coreSource
init|=
operator|new
name|ParentPathInputSource
argument_list|(
name|f
argument_list|)
decl_stmt|;
comment|// Check that all the imports closure is made of local files
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|closure
init|=
name|coreSource
operator|.
name|getImports
argument_list|(
literal|true
argument_list|)
decl_stmt|;
for|for
control|(
name|OWLOntology
name|o
range|:
name|closure
control|)
name|assertEquals
argument_list|(
literal|"file"
argument_list|,
name|o
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOntologyDocumentIRI
argument_list|(
name|o
argument_list|)
operator|.
name|getScheme
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|coreSource
operator|.
name|getRootOntology
argument_list|()
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
argument_list|,
name|IRI
operator|.
name|create
argument_list|(
name|Constants
operator|.
name|PEANUTS_MAIN_BASE
argument_list|)
argument_list|)
expr_stmt|;
comment|// Linus is stated to be a foaf:Person
name|OWLNamedIndividual
name|iLinus
init|=
name|df
operator|.
name|getOWLNamedIndividual
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|Constants
operator|.
name|PEANUTS_MAIN_BASE
operator|+
literal|"#Linus"
argument_list|)
argument_list|)
decl_stmt|;
comment|// Lucy is stated to be a foaf:Perzon
name|OWLNamedIndividual
name|iLucy
init|=
name|df
operator|.
name|getOWLNamedIndividual
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|Constants
operator|.
name|PEANUTS_MAIN_BASE
operator|+
literal|"#Lucy"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLClass
name|cPerzon
init|=
name|df
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://xmlns.com/foaf/0.1/Perzon"
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLIndividual
argument_list|>
name|instances
init|=
name|cPerzon
operator|.
name|getIndividuals
argument_list|(
name|coreSource
operator|.
name|getRootOntology
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
operator|!
name|instances
operator|.
name|contains
argument_list|(
name|iLinus
argument_list|)
operator|&&
name|instances
operator|.
name|contains
argument_list|(
name|iLucy
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Loads a modified FOAF by resolving a URI from a resource directory.      *       * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testOfflineSingleton
parameter_list|()
throws|throws
name|Exception
block|{
name|URL
name|url
init|=
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"/ontologies/mockfoaf.rdf"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|url
argument_list|)
expr_stmt|;
name|OntologyInputSource
argument_list|<
name|OWLOntology
argument_list|>
name|coreSource
init|=
operator|new
name|RootOntologyIRISource
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|url
argument_list|)
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|df
argument_list|)
expr_stmt|;
comment|/*          * To check it fetched the correct ontology, we look for a declaration of the bogus class foaf:Perzon          * (added in the local FOAF)          */
name|OWLClass
name|cPerzon
init|=
name|df
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://xmlns.com/foaf/0.1/Perzon"
argument_list|)
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|coreSource
operator|.
name|getRootOntology
argument_list|()
operator|.
name|getClassesInSignature
argument_list|()
operator|.
name|contains
argument_list|(
name|cPerzon
argument_list|)
argument_list|)
expr_stmt|;
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
block|}
end_class

end_unit

