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
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|servicesapi
operator|.
name|ontology
operator|.
name|OntologyLoadingException
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

begin_comment
comment|/**  * Test suite for the correctness of {@link OntologyInputSource} implementations based on Clerezza.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|TestClerezzaInputSources
block|{
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
comment|//nothing to do
block|}
specifier|private
name|String
name|dummy_RdfXml
init|=
literal|"/ontologies/dummy-01.rdfxml.rdf"
decl_stmt|;
specifier|private
name|String
name|dummy_Turtle
init|=
literal|"/ontologies/dummy-01.turtle.rdf"
decl_stmt|;
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
specifier|private
name|OntologyInputSource
argument_list|<
name|TripleCollection
argument_list|>
name|src
decl_stmt|;
annotation|@
name|Before
specifier|public
name|void
name|bind
parameter_list|()
throws|throws
name|Exception
block|{
name|src
operator|=
literal|null
expr_stmt|;
block|}
specifier|private
name|void
name|checkOntology
parameter_list|(
name|boolean
name|usesTcProvider
parameter_list|)
throws|throws
name|Exception
block|{
name|assertNotNull
argument_list|(
name|src
argument_list|)
expr_stmt|;
if|if
condition|(
name|usesTcProvider
condition|)
name|assertNotNull
argument_list|(
name|src
operator|.
name|getOrigin
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|assertNull
argument_list|(
name|src
operator|.
name|getOrigin
argument_list|()
argument_list|)
expr_stmt|;
name|TripleCollection
name|o
init|=
name|src
operator|.
name|getRootOntology
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|o
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Ontology loaded, is a {}"
argument_list|,
name|o
operator|.
name|getClass
argument_list|()
operator|.
name|getCanonicalName
argument_list|()
argument_list|)
expr_stmt|;
name|assertSame
argument_list|(
literal|5
argument_list|,
name|o
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
comment|// The owl:Ontology declaration and versionInfo also count as triples.
block|}
annotation|@
name|After
specifier|public
name|void
name|cleanup
parameter_list|()
block|{
comment|//nothing to do
block|}
comment|/*      * If the format is specificed and correct, the ontology source should be created as expected.      */
annotation|@
name|Test
specifier|public
name|void
name|fromInputStreamWithFormat
parameter_list|()
throws|throws
name|Exception
block|{
name|InputStream
name|in
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|dummy_Turtle
argument_list|)
decl_stmt|;
name|src
operator|=
operator|new
name|GraphContentInputSource
argument_list|(
name|in
argument_list|,
name|SupportedFormat
operator|.
name|TURTLE
argument_list|)
expr_stmt|;
name|checkOntology
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
comment|/*      * An ontology input source created using a custom TC Provider should create a non-null Origin (i.e. the      * name of the generated graph) and increase the triple collection count by 1.      */
annotation|@
name|Test
specifier|public
name|void
name|fromInputStreamInSimpleTcProvider
parameter_list|()
throws|throws
name|Exception
block|{
name|InputStream
name|in
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|dummy_RdfXml
argument_list|)
decl_stmt|;
name|TcProvider
name|tcp
init|=
operator|new
name|SimpleTcProvider
argument_list|()
decl_stmt|;
name|assertSame
argument_list|(
literal|0
argument_list|,
name|tcp
operator|.
name|listTripleCollections
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|before
init|=
name|tcp
operator|.
name|listTripleCollections
argument_list|()
operator|.
name|size
argument_list|()
decl_stmt|;
name|src
operator|=
operator|new
name|GraphContentInputSource
argument_list|(
name|in
argument_list|,
name|tcp
argument_list|)
expr_stmt|;
name|checkOntology
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|assertSame
argument_list|(
name|before
operator|+
literal|1
argument_list|,
name|tcp
operator|.
name|listTripleCollections
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/*      * An ontology input source created using the Clerezza TC Manager should create a non-null Origin (i.e.      * the name of the generated graph) and increase the triple collection count by 1.      */
annotation|@
name|Test
specifier|public
name|void
name|fromInputStreamInTcManager
parameter_list|()
throws|throws
name|Exception
block|{
name|InputStream
name|in
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|dummy_RdfXml
argument_list|)
decl_stmt|;
name|int
name|before
init|=
name|tcManager
operator|.
name|listTripleCollections
argument_list|()
operator|.
name|size
argument_list|()
decl_stmt|;
name|src
operator|=
operator|new
name|GraphContentInputSource
argument_list|(
name|in
argument_list|,
name|tcManager
argument_list|)
expr_stmt|;
name|checkOntology
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|assertSame
argument_list|(
name|before
operator|+
literal|1
argument_list|,
name|tcManager
operator|.
name|listTripleCollections
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/*      * If the format is unspecificed, input source creation should still succeed if the resource is in the      * preferred format (RDF/XML). In all other cases it is OK whether it fails or succeeds.      */
annotation|@
name|Test
specifier|public
name|void
name|fromInputStreamNoFormat
parameter_list|()
throws|throws
name|Exception
block|{
comment|// This should be successful as the RDF/XML parser is tried first.
name|InputStream
name|in
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|dummy_RdfXml
argument_list|)
decl_stmt|;
name|src
operator|=
operator|new
name|GraphContentInputSource
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|checkOntology
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|// This should fail unless the input stream can be reset.
name|in
operator|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|dummy_Turtle
argument_list|)
expr_stmt|;
try|try
block|{
name|src
operator|=
operator|new
name|GraphContentInputSource
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"Unexpected behaviour: no {} caught."
argument_list|,
name|OntologyLoadingException
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"Will check if loading was successful."
argument_list|)
expr_stmt|;
name|checkOntology
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OntologyLoadingException
name|ex
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Caught expected {}"
argument_list|,
name|ex
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
name|src
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
name|src
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|src
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

