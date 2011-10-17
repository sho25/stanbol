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
name|assertNull
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
name|io
operator|.
name|InputStream
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
name|Hashtable
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
name|Graph
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
name|MGraph
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
name|SupportedFormat
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
name|BlankOntologySource
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
name|GraphSource
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
name|api
operator|.
name|ontology
operator|.
name|CoreOntologySpace
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
name|CustomOntologySpace
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
name|api
operator|.
name|ontology
operator|.
name|OntologySpaceFactory
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
name|ScopeRegistry
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
name|SessionOntologySpace
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
name|SpaceType
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
name|UnmodifiableOntologyCollectorException
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
name|ClerezzaUtils
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
name|OntologySpaceFactoryImpl
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
name|ontology
operator|.
name|ScopeRegistryImpl
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
name|apache
operator|.
name|stanbol
operator|.
name|owl
operator|.
name|util
operator|.
name|OWLUtils
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
name|util
operator|.
name|URIUtils
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
name|OWLAxiom
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
name|TestClerezzaSpaces
block|{
specifier|public
specifier|static
name|IRI
name|baseIri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|Constants
operator|.
name|PEANUTS_MAIN_BASE
argument_list|)
decl_stmt|,
name|baseIri2
init|=
name|IRI
operator|.
name|create
argument_list|(
name|Constants
operator|.
name|PEANUTS_MINOR_BASE
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|OWLAxiom
name|linusIsHuman
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|OntologySpaceFactory
name|factory
decl_stmt|;
specifier|private
specifier|static
name|OntologyInputSource
argument_list|<
name|?
argument_list|>
name|inMemorySrc
decl_stmt|,
name|minorSrc
decl_stmt|,
name|dropSrc
decl_stmt|,
name|nonexSrc
decl_stmt|;
specifier|private
specifier|static
name|OfflineConfiguration
name|offline
decl_stmt|;
specifier|private
specifier|static
name|OntologyInputSource
argument_list|<
name|Graph
argument_list|>
name|getLocalSource
parameter_list|(
name|String
name|resourcePath
parameter_list|)
block|{
name|InputStream
name|is
init|=
name|TestOntologySpaces
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|resourcePath
argument_list|)
decl_stmt|;
return|return
operator|new
name|GraphSource
argument_list|(
name|parser
operator|.
name|parse
argument_list|(
name|is
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|)
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|OntologyInputSource
argument_list|<
name|?
argument_list|>
name|getLocalSource
parameter_list|(
name|String
name|resourcePath
parameter_list|,
name|OWLOntologyManager
name|mgr
parameter_list|)
throws|throws
name|OWLOntologyCreationException
throws|,
name|URISyntaxException
block|{
name|URL
name|url
init|=
name|TestOntologySpaces
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|resourcePath
argument_list|)
decl_stmt|;
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
return|return
operator|new
name|ParentPathInputSource
argument_list|(
name|f
argument_list|,
name|mgr
operator|!=
literal|null
condition|?
name|mgr
else|:
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
argument_list|)
return|;
block|}
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
name|offline
operator|=
operator|new
name|OfflineConfigurationImpl
argument_list|(
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|ScopeRegistry
name|reg
init|=
operator|new
name|ScopeRegistryImpl
argument_list|()
decl_stmt|;
comment|// This one is created from scratch
name|MGraph
name|ont2
init|=
name|ClerezzaUtils
operator|.
name|createOntology
argument_list|(
name|baseIri2
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|minorSrc
operator|=
operator|new
name|GraphSource
argument_list|(
name|ont2
operator|.
name|getGraph
argument_list|()
argument_list|)
expr_stmt|;
name|dropSrc
operator|=
name|getLocalSource
argument_list|(
literal|"/ontologies/droppedcharacters.owl"
argument_list|)
expr_stmt|;
name|nonexSrc
operator|=
name|getLocalSource
argument_list|(
literal|"/ontologies/nonexistentcharacters.owl"
argument_list|)
expr_stmt|;
name|inMemorySrc
operator|=
operator|new
name|RootOntologyIRISource
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|TestClerezzaSpaces
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"/ontologies/maincharacters.owl"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|OWLDataFactory
name|df
init|=
name|OWLManager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|OWLClass
name|cHuman
init|=
name|df
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|baseIri
operator|+
literal|"/"
operator|+
name|Constants
operator|.
name|humanBeing
argument_list|)
argument_list|)
decl_stmt|;
name|OWLIndividual
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
name|baseIri
operator|+
literal|"/"
operator|+
name|Constants
operator|.
name|linus
argument_list|)
argument_list|)
decl_stmt|;
name|linusIsHuman
operator|=
name|df
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|cHuman
argument_list|,
name|iLinus
argument_list|)
expr_stmt|;
name|factory
operator|=
operator|new
name|OntologySpaceFactoryImpl
argument_list|(
name|reg
argument_list|,
name|tcManager
argument_list|,
name|offline
argument_list|,
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontology/"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|scopeId
init|=
literal|"Comics"
decl_stmt|;
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|cleanup
parameter_list|()
block|{
name|reset
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testAddOntology
parameter_list|()
throws|throws
name|Exception
block|{
name|CustomOntologySpace
name|space
init|=
literal|null
decl_stmt|;
name|IRI
name|logicalId
init|=
name|IRI
operator|.
name|create
argument_list|(
name|OWLUtils
operator|.
name|guessOntologyIdentifier
argument_list|(
name|nonexSrc
operator|.
name|getRootOntology
argument_list|()
argument_list|)
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
decl_stmt|;
name|space
operator|=
name|factory
operator|.
name|createCustomOntologySpace
argument_list|(
name|scopeId
argument_list|,
name|dropSrc
argument_list|,
name|minorSrc
argument_list|)
expr_stmt|;
name|space
operator|.
name|addOntology
argument_list|(
name|minorSrc
argument_list|)
expr_stmt|;
name|space
operator|.
name|addOntology
argument_list|(
name|nonexSrc
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|space
operator|.
name|hasOntology
argument_list|(
name|logicalId
argument_list|)
argument_list|)
expr_stmt|;
name|logicalId
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|OWLUtils
operator|.
name|guessOntologyIdentifier
argument_list|(
name|dropSrc
operator|.
name|getRootOntology
argument_list|()
argument_list|)
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|space
operator|.
name|hasOntology
argument_list|(
name|logicalId
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testCoreLock
parameter_list|()
throws|throws
name|Exception
block|{
name|CoreOntologySpace
name|space
init|=
name|factory
operator|.
name|createCoreOntologySpace
argument_list|(
name|scopeId
argument_list|,
name|inMemorySrc
argument_list|)
decl_stmt|;
name|space
operator|.
name|setUp
argument_list|()
expr_stmt|;
try|try
block|{
name|space
operator|.
name|addOntology
argument_list|(
name|minorSrc
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Modification was permitted on locked ontology space."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologyCollectorException
name|e
parameter_list|)
block|{
name|assertSame
argument_list|(
name|space
argument_list|,
name|e
operator|.
name|getOntologyCollector
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testCreateSpace
parameter_list|()
throws|throws
name|Exception
block|{
name|CustomOntologySpace
name|space
init|=
name|factory
operator|.
name|createCustomOntologySpace
argument_list|(
name|scopeId
argument_list|,
name|dropSrc
argument_list|)
decl_stmt|;
name|IRI
name|logicalId
init|=
name|IRI
operator|.
name|create
argument_list|(
name|OWLUtils
operator|.
name|guessOntologyIdentifier
argument_list|(
name|dropSrc
operator|.
name|getRootOntology
argument_list|()
argument_list|)
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|space
operator|.
name|hasOntology
argument_list|(
name|logicalId
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testCustomLock
parameter_list|()
throws|throws
name|Exception
block|{
name|CustomOntologySpace
name|space
init|=
name|factory
operator|.
name|createCustomOntologySpace
argument_list|(
name|scopeId
argument_list|,
name|inMemorySrc
argument_list|)
decl_stmt|;
name|space
operator|.
name|setUp
argument_list|()
expr_stmt|;
try|try
block|{
name|space
operator|.
name|addOntology
argument_list|(
name|minorSrc
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Modification was permitted on locked ontology space."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologyCollectorException
name|e
parameter_list|)
block|{
name|assertSame
argument_list|(
name|space
argument_list|,
name|e
operator|.
name|getOntologyCollector
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Checks whether attempting to create ontology spaces with invalid identifiers or namespaces results in      * the appropriate exceptions being thrown.      *       * @throws Exception      *             if an unexpected error occurs.      */
annotation|@
name|Test
specifier|public
name|void
name|testIdentifiers
parameter_list|()
throws|throws
name|Exception
block|{
name|OntologySpace
name|shouldBeNull
init|=
literal|null
decl_stmt|,
name|shouldBeNotNull
init|=
literal|null
decl_stmt|;
comment|/* First test space identifiers. */
comment|// Null identifier (invalid).
try|try
block|{
name|shouldBeNull
operator|=
name|factory
operator|.
name|createOntologySpace
argument_list|(
literal|null
argument_list|,
name|SpaceType
operator|.
name|CORE
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Expected IllegalArgumentException not thrown despite null scope identifier."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{}
name|assertNull
argument_list|(
name|shouldBeNull
argument_list|)
expr_stmt|;
comment|// More than one slash in identifier (invalid).
try|try
block|{
name|shouldBeNull
operator|=
name|factory
operator|.
name|createOntologySpace
argument_list|(
literal|"Sc0/p3"
argument_list|,
name|SpaceType
operator|.
name|CORE
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Expected IllegalArgumentException not thrown despite null scope identifier."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{}
name|assertNull
argument_list|(
name|shouldBeNull
argument_list|)
expr_stmt|;
comment|/* Now test namespaces. */
comment|// Null namespace (invalid).
name|factory
operator|.
name|setNamespace
argument_list|(
literal|null
argument_list|)
expr_stmt|;
try|try
block|{
name|shouldBeNull
operator|=
name|factory
operator|.
name|createOntologySpace
argument_list|(
literal|"Sc0p3"
argument_list|,
name|SpaceType
operator|.
name|CORE
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Expected IllegalArgumentException not thrown despite null OntoNet namespace."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{}
name|assertNull
argument_list|(
name|shouldBeNull
argument_list|)
expr_stmt|;
comment|// Namespace with query (invalid).
name|factory
operator|.
name|setNamespace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontology/?query=true"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|shouldBeNull
operator|=
name|factory
operator|.
name|createOntologySpace
argument_list|(
literal|"Sc0p3"
argument_list|,
name|SpaceType
operator|.
name|CORE
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Expected IllegalArgumentException not thrown despite query in OntoNet namespace."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{}
name|assertNull
argument_list|(
name|shouldBeNull
argument_list|)
expr_stmt|;
comment|// Namespace with fragment (invalid).
name|factory
operator|.
name|setNamespace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontology#fragment"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|shouldBeNull
operator|=
name|factory
operator|.
name|createOntologySpace
argument_list|(
literal|"Sc0p3"
argument_list|,
name|SpaceType
operator|.
name|CORE
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Expected IllegalArgumentException not thrown despite fragment in OntoNet namespace."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{}
name|assertNull
argument_list|(
name|shouldBeNull
argument_list|)
expr_stmt|;
comment|// Namespace ending with hash (invalid).
name|factory
operator|.
name|setNamespace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontology#"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|shouldBeNull
operator|=
name|factory
operator|.
name|createOntologySpace
argument_list|(
literal|"Sc0p3"
argument_list|,
name|SpaceType
operator|.
name|CORE
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Expected IllegalArgumentException not thrown despite fragment in OntoNet namespace."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{}
name|assertNull
argument_list|(
name|shouldBeNull
argument_list|)
expr_stmt|;
comment|// Namespace ending with neither (valid, should automatically add slash).
name|factory
operator|.
name|setNamespace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontology"
argument_list|)
argument_list|)
expr_stmt|;
name|shouldBeNotNull
operator|=
name|factory
operator|.
name|createOntologySpace
argument_list|(
literal|"Sc0p3"
argument_list|,
name|SpaceType
operator|.
name|CORE
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|shouldBeNotNull
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|shouldBeNotNull
operator|.
name|getNamespace
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"/"
argument_list|)
argument_list|)
expr_stmt|;
name|shouldBeNotNull
operator|=
literal|null
expr_stmt|;
comment|// Namespace ending with slash (valid).
name|factory
operator|.
name|setNamespace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontology/"
argument_list|)
argument_list|)
expr_stmt|;
name|shouldBeNotNull
operator|=
name|factory
operator|.
name|createOntologySpace
argument_list|(
literal|"Sc0p3"
argument_list|,
name|SpaceType
operator|.
name|CORE
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|shouldBeNotNull
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testRemoveCustomOntology
parameter_list|()
throws|throws
name|Exception
block|{
name|CustomOntologySpace
name|space
init|=
literal|null
decl_stmt|;
name|space
operator|=
name|factory
operator|.
name|createCustomOntologySpace
argument_list|(
name|scopeId
argument_list|,
name|dropSrc
argument_list|)
expr_stmt|;
name|IRI
name|dropId
init|=
name|URIUtils
operator|.
name|createIRI
argument_list|(
name|OWLUtils
operator|.
name|guessOntologyIdentifier
argument_list|(
name|dropSrc
operator|.
name|getRootOntology
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|IRI
name|nonexId
init|=
name|URIUtils
operator|.
name|createIRI
argument_list|(
name|OWLUtils
operator|.
name|guessOntologyIdentifier
argument_list|(
name|nonexSrc
operator|.
name|getRootOntology
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|space
operator|.
name|addOntology
argument_list|(
name|inMemorySrc
argument_list|)
expr_stmt|;
name|space
operator|.
name|addOntology
argument_list|(
name|nonexSrc
argument_list|)
expr_stmt|;
comment|// The other remote ontologies may change base IRI...
comment|// baseIri is maincharacters
name|assertTrue
argument_list|(
name|space
operator|.
name|hasOntology
argument_list|(
name|baseIri
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|space
operator|.
name|hasOntology
argument_list|(
name|dropId
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|space
operator|.
name|hasOntology
argument_list|(
name|nonexId
argument_list|)
argument_list|)
expr_stmt|;
name|space
operator|.
name|removeOntology
argument_list|(
name|dropId
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|space
operator|.
name|hasOntology
argument_list|(
name|dropId
argument_list|)
argument_list|)
expr_stmt|;
name|space
operator|.
name|removeOntology
argument_list|(
name|nonexId
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|space
operator|.
name|hasOntology
argument_list|(
name|nonexId
argument_list|)
argument_list|)
expr_stmt|;
comment|// OntologyUtils.printOntology(space.getTopOntology(), System.err);
block|}
comment|//    @Test
specifier|public
name|void
name|testSessionModification
parameter_list|()
throws|throws
name|Exception
block|{
name|SessionOntologySpace
name|space
init|=
name|factory
operator|.
name|createSessionOntologySpace
argument_list|(
name|scopeId
argument_list|)
decl_stmt|;
name|space
operator|.
name|setUp
argument_list|()
expr_stmt|;
try|try
block|{
comment|// First add an in-memory ontology with a few axioms.
name|space
operator|.
name|addOntology
argument_list|(
name|inMemorySrc
argument_list|)
expr_stmt|;
comment|// Now add a real online ontology
name|space
operator|.
name|addOntology
argument_list|(
name|dropSrc
argument_list|)
expr_stmt|;
comment|// The in-memory ontology must be in the space.
name|assertTrue
argument_list|(
name|space
operator|.
name|hasOntology
argument_list|(
name|baseIri
argument_list|)
argument_list|)
expr_stmt|;
comment|// The in-memory ontology must still have its axioms.
name|assertTrue
argument_list|(
name|space
operator|.
name|getOntology
argument_list|(
name|baseIri
argument_list|)
operator|.
name|containsAxiom
argument_list|(
name|linusIsHuman
argument_list|)
argument_list|)
expr_stmt|;
comment|// // The top ontology must still have axioms from in-memory
comment|// // ontologies. NO LONGER
comment|// assertTrue(space.getTopOntology().containsAxiom(linusIsHuman));
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologyCollectorException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Modification was denied on unlocked ontology space."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

