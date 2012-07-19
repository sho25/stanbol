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
name|impl
operator|.
name|clerezza
package|;
end_package

begin_import
import|import static
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
operator|.
name|RDF_XML
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
name|InputStream
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
name|access
operator|.
name|TcProvider
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
name|OWLClassExpression
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
comment|/**  * Tests the correct behaviour of the Clerezza-based implementation of {@link OntologyProvider}, regardless of  * virtual ontology network setups.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|TestClerezzaProvider
block|{
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|TestClerezzaProvider
operator|.
name|class
argument_list|)
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
name|reset
argument_list|()
expr_stmt|;
block|}
specifier|private
name|String
name|fn1
init|=
literal|"/ontologies/versiontest_v1.owl"
decl_stmt|,
name|fn2
init|=
literal|"/ontologies/versiontest_v2.owl"
decl_stmt|;
specifier|private
name|String
name|oiri
init|=
literal|"http://stanbol.apache.org/ontologies/versiontest"
decl_stmt|;
specifier|private
name|OntologyProvider
argument_list|<
name|TcProvider
argument_list|>
name|ontologyProvider
decl_stmt|;
specifier|private
name|OfflineConfiguration
name|offline
init|=
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
decl_stmt|;
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
annotation|@
name|Before
specifier|public
name|void
name|setupTest
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Must be reset due to the internal key mapper.
name|ontologyProvider
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
annotation|@
name|Test
specifier|public
name|void
name|testVersionIRISplit
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Check the first version
name|InputStream
name|data
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|fn1
argument_list|)
decl_stmt|;
name|String
name|key1
init|=
name|ontologyProvider
operator|.
name|loadInStore
argument_list|(
name|data
argument_list|,
name|RDF_XML
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|key1
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|key1
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
comment|// Check the second version
name|data
operator|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|fn2
argument_list|)
expr_stmt|;
name|String
name|key2
init|=
name|ontologyProvider
operator|.
name|loadInStore
argument_list|(
name|data
argument_list|,
name|RDF_XML
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|key2
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|key2
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
comment|// Must be 2 different graphs
name|assertFalse
argument_list|(
name|key1
operator|.
name|equals
argument_list|(
name|key2
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|ontologyProvider
operator|.
name|getPublicKeys
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
comment|// Ontologies must not be tainting each other
name|OWLOntology
name|o1
init|=
name|ontologyProvider
operator|.
name|getStoredOntology
argument_list|(
name|key1
argument_list|,
name|OWLOntology
operator|.
name|class
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|OWLOntology
name|o2
init|=
name|ontologyProvider
operator|.
name|getStoredOntology
argument_list|(
name|key2
argument_list|,
name|OWLOntology
operator|.
name|class
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|oAll
init|=
operator|new
name|HashSet
argument_list|<
name|OWLOntology
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|OWLOntology
index|[]
block|{
name|o1
block|,
name|o2
block|}
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|OWLNamedIndividual
name|i
range|:
name|o1
operator|.
name|getIndividualsInSignature
argument_list|()
control|)
block|{
name|Set
argument_list|<
name|OWLClassExpression
argument_list|>
name|tAll
init|=
name|i
operator|.
name|getTypes
argument_list|(
name|oAll
argument_list|)
decl_stmt|,
name|t1
init|=
name|i
operator|.
name|getTypes
argument_list|(
name|o1
argument_list|)
decl_stmt|,
name|t2
init|=
name|i
operator|.
name|getTypes
argument_list|(
name|o2
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|tAll
operator|.
name|containsAll
argument_list|(
name|t1
argument_list|)
argument_list|)
expr_stmt|;
comment|// Should be obvious from the OWL API
name|assertTrue
argument_list|(
name|tAll
operator|.
name|containsAll
argument_list|(
name|t2
argument_list|)
argument_list|)
expr_stmt|;
comment|// Should be obvious from the OWL API
name|assertFalse
argument_list|(
name|t1
operator|.
name|containsAll
argument_list|(
name|t2
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|t2
operator|.
name|containsAll
argument_list|(
name|t1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

