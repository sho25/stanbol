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
name|sources
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
name|stanbol
operator|.
name|ontologymanager
operator|.
name|sources
operator|.
name|clerezza
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
name|sources
operator|.
name|clerezza
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
name|sources
operator|.
name|clerezza
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
name|assertNotNull
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
name|TripleCollection
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
name|UriRef
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
name|servicesapi
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
name|TestClerezzaInputSources
block|{
specifier|private
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|loadGraphs
parameter_list|()
throws|throws
name|Exception
block|{
name|reset
argument_list|()
expr_stmt|;
block|}
specifier|private
name|OntologyInputSource
argument_list|<
name|TripleCollection
argument_list|>
name|gis
decl_stmt|;
annotation|@
name|Before
specifier|public
name|void
name|bind
parameter_list|()
throws|throws
name|Exception
block|{      }
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
comment|// TODO move this test where we have access to the Clerezza implementation.
comment|//
comment|// @Test
comment|// public void testGraphContentSource() throws Exception {
comment|// // Make sure the tc manager has been reset
comment|// assertEquals(1, tcManager.listTripleCollections().size());
comment|//
comment|// OntologyProvider<TcProvider> provider = new ClerezzaOntologyProvider(tcManager,
comment|// new OfflineConfigurationImpl(new Hashtable<String,Object>()), parser);
comment|// int tcs = tcManager.listTripleCollections().size();
comment|// InputStream content = TestClerezzaInputSources.class
comment|// .getResourceAsStream("/ontologies/droppedcharacters.owl");
comment|// OntologyInputSource<?> src = new GraphContentInputSource(content, SupportedFormat.RDF_XML,
comment|// ontologyProvider.getStore(), parser);
comment|//
comment|// log.info("After input source creation, TcManager has {} graphs. ", tcManager.listTripleCollections()
comment|// .size());
comment|// for (UriRef name : tcManager.listTripleCollections())
comment|// log.info("-- {} (a {})", name, tcManager.getTriples(name).getClass().getSimpleName());
comment|// assertEquals(tcs + 1, tcManager.listTripleCollections().size());
comment|// Space spc = new CoreSpaceImpl(TestClerezzaInputSources.class.getSimpleName(),
comment|// IRI.create("http://stanbol.apache.org/ontologies/"), provider);
comment|// spc.addOntology(src);
comment|// log.info("After addition to space, TcManager has {} graphs. ", tcManager.listTripleCollections()
comment|// .size());
comment|//
comment|// for (UriRef name : tcManager.listTripleCollections())
comment|// log.info("-- {} (a {})", name, tcManager.getTriples(name).getClass().getSimpleName());
comment|// // Adding the ontology from the same storage should not create new graphs
comment|// assertEquals(tcs + 1, tcManager.listTripleCollections().size());
comment|//
comment|// }
annotation|@
name|Test
specifier|public
name|void
name|testGraphSource
parameter_list|()
throws|throws
name|Exception
block|{
name|UriRef
name|uri
init|=
operator|new
name|UriRef
argument_list|(
name|Locations
operator|.
name|CHAR_ACTIVE
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|InputStream
name|inputStream
init|=
name|TestClerezzaInputSources
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"/ontologies/characters_all.owl"
argument_list|)
decl_stmt|;
name|parser
operator|.
name|parse
argument_list|(
name|tcManager
operator|.
name|createMGraph
argument_list|(
name|uri
argument_list|)
argument_list|,
name|inputStream
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|,
name|uri
argument_list|)
expr_stmt|;
name|uri
operator|=
operator|new
name|UriRef
argument_list|(
name|Locations
operator|.
name|CHAR_MAIN
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|inputStream
operator|=
name|TestClerezzaInputSources
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"/ontologies/maincharacters.owl"
argument_list|)
expr_stmt|;
name|parser
operator|.
name|parse
argument_list|(
name|tcManager
operator|.
name|createMGraph
argument_list|(
name|uri
argument_list|)
argument_list|,
name|inputStream
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|,
name|uri
argument_list|)
expr_stmt|;
name|uri
operator|=
operator|new
name|UriRef
argument_list|(
name|Locations
operator|.
name|CHAR_MINOR
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|inputStream
operator|=
name|TestClerezzaInputSources
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"/ontologies/minorcharacters.owl"
argument_list|)
expr_stmt|;
name|parser
operator|.
name|parse
argument_list|(
name|tcManager
operator|.
name|createMGraph
argument_list|(
name|uri
argument_list|)
argument_list|,
name|inputStream
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|,
name|uri
argument_list|)
expr_stmt|;
name|gis
operator|=
operator|new
name|GraphSource
argument_list|(
operator|new
name|UriRef
argument_list|(
name|Locations
operator|.
name|CHAR_ACTIVE
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|gis
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|gis
operator|.
name|getRootOntology
argument_list|()
argument_list|)
expr_stmt|;
comment|// Set<TripleCollection> imported = gis.getImports(false);
comment|// // Number of stored graphs minus the importing one minus the reserved graph = imported graphs
comment|// assertEquals(tcManager.listTripleCollections().size() - 2, imported.size());
comment|// for (TripleCollection g : imported)
comment|// assertNotNull(g);
block|}
block|}
end_class

end_unit

