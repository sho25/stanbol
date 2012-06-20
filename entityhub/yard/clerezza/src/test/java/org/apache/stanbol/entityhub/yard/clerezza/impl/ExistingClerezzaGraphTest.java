begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|yard
operator|.
name|clerezza
operator|.
name|impl
package|;
end_package

begin_import
import|import static
name|java
operator|.
name|util
operator|.
name|Collections
operator|.
name|singletonMap
import|;
end_import

begin_import
import|import static
name|junit
operator|.
name|framework
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|junit
operator|.
name|framework
operator|.
name|Assert
operator|.
name|assertNotNull
import|;
end_import

begin_import
import|import static
name|junit
operator|.
name|framework
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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
import|;
end_import

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|Assert
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
name|Language
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
name|Triple
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
name|TcManager
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
name|impl
operator|.
name|PlainLiteralImpl
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
name|impl
operator|.
name|SimpleMGraph
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
name|impl
operator|.
name|TripleImpl
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
name|impl
operator|.
name|graphmatching
operator|.
name|GraphMatcher
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
name|ontologies
operator|.
name|RDF
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
name|ontologies
operator|.
name|SKOS
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
name|entityhub
operator|.
name|core
operator|.
name|model
operator|.
name|InMemoryValueFactory
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
name|entityhub
operator|.
name|model
operator|.
name|clerezza
operator|.
name|RdfRepresentation
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
name|entityhub
operator|.
name|model
operator|.
name|clerezza
operator|.
name|RdfValueFactory
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|yard
operator|.
name|YardException
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

begin_comment
comment|/**  * Unit tests for testing {@link ClerezzaYard} initialisation and usage in   * cases the configured {@link ClerezzaYardConfig#getGraphUri()} points to  * already existing Clerezza {@link MGraph}s and {@link Graph} instances.<p>  * This basically tests features added with STANBOL-662 and STANBOL-663  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|ExistingClerezzaGraphTest
block|{
specifier|private
specifier|static
name|TcManager
name|tcManager
decl_stmt|;
specifier|private
specifier|static
name|Language
name|EN
init|=
operator|new
name|Language
argument_list|(
literal|"en"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Language
name|DE
init|=
operator|new
name|Language
argument_list|(
literal|"de"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|UriRef
argument_list|,
name|TripleCollection
argument_list|>
name|entityData
init|=
operator|new
name|HashMap
argument_list|<
name|UriRef
argument_list|,
name|TripleCollection
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|UriRef
name|READ_ONLY_GRAPH_URI
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://www.test.org/read-only-grpah"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|UriRef
name|READ_WRITEGRAPH_URI
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://www.test.org/read-write-grpah"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|ClerezzaYard
name|readwriteYard
decl_stmt|;
specifier|private
specifier|static
name|ClerezzaYard
name|readonlyYard
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
specifier|final
name|void
name|initYard
parameter_list|()
block|{
name|initTestData
argument_list|()
expr_stmt|;
comment|//create the graphs in Clerezza
name|tcManager
operator|=
name|TcManager
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|MGraph
name|graph
init|=
name|tcManager
operator|.
name|createMGraph
argument_list|(
name|READ_WRITEGRAPH_URI
argument_list|)
decl_stmt|;
comment|//add the test data to the MGrpah
for|for
control|(
name|TripleCollection
name|tc
range|:
name|entityData
operator|.
name|values
argument_list|()
control|)
block|{
name|graph
operator|.
name|addAll
argument_list|(
name|tc
argument_list|)
expr_stmt|;
block|}
comment|//create the read only graph
name|tcManager
operator|.
name|createGraph
argument_list|(
name|READ_ONLY_GRAPH_URI
argument_list|,
name|graph
argument_list|)
expr_stmt|;
comment|//init the ClerezzaYards for the created Clerezza graphs
name|ClerezzaYardConfig
name|readWriteConfig
init|=
operator|new
name|ClerezzaYardConfig
argument_list|(
literal|"readWriteYardId"
argument_list|)
decl_stmt|;
name|readWriteConfig
operator|.
name|setName
argument_list|(
literal|"Clerezza read/write Yard"
argument_list|)
expr_stmt|;
name|readWriteConfig
operator|.
name|setDescription
argument_list|(
literal|"Tests config with pre-existing MGraph"
argument_list|)
expr_stmt|;
name|readWriteConfig
operator|.
name|setGraphUri
argument_list|(
name|READ_WRITEGRAPH_URI
argument_list|)
expr_stmt|;
name|readwriteYard
operator|=
operator|new
name|ClerezzaYard
argument_list|(
name|readWriteConfig
argument_list|)
expr_stmt|;
name|ClerezzaYardConfig
name|readOnlyYardConfig
init|=
operator|new
name|ClerezzaYardConfig
argument_list|(
literal|"readOnlyYardId"
argument_list|)
decl_stmt|;
name|readOnlyYardConfig
operator|.
name|setName
argument_list|(
literal|"Clerezza read-only Yard"
argument_list|)
expr_stmt|;
name|readOnlyYardConfig
operator|.
name|setDescription
argument_list|(
literal|"Tests config with pre-existing Graph"
argument_list|)
expr_stmt|;
name|readOnlyYardConfig
operator|.
name|setGraphUri
argument_list|(
name|READ_ONLY_GRAPH_URI
argument_list|)
expr_stmt|;
name|readonlyYard
operator|=
operator|new
name|ClerezzaYard
argument_list|(
name|readOnlyYardConfig
argument_list|)
expr_stmt|;
block|}
comment|/**      * Checks that the {@link #entityData} are correctly accessible via the      * ClerezzaYard initialised over {@link #READ_ONLY_GRAPH_URI} and      * {@link #READ_WRITEGRAPH_URI}.      */
annotation|@
name|Test
specifier|public
name|void
name|testRetrival
parameter_list|()
block|{
for|for
control|(
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|TripleCollection
argument_list|>
name|entity
range|:
name|entityData
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|validateEntity
argument_list|(
name|readonlyYard
argument_list|,
name|entity
argument_list|)
expr_stmt|;
name|validateEntity
argument_list|(
name|readwriteYard
argument_list|,
name|entity
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testModificationsOnReadWirteYard
parameter_list|()
throws|throws
name|YardException
block|{
name|RdfRepresentation
name|rep
init|=
name|RdfValueFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createRepresentation
argument_list|(
literal|"http://www.test.org/addedEntity"
argument_list|)
decl_stmt|;
name|rep
operator|.
name|addReference
argument_list|(
name|RDF
operator|.
name|type
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
name|SKOS
operator|.
name|Concept
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
name|rep
operator|.
name|addNaturalText
argument_list|(
name|SKOS
operator|.
name|prefLabel
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
literal|"added Entity"
argument_list|,
literal|"en"
argument_list|)
expr_stmt|;
name|rep
operator|.
name|addNaturalText
argument_list|(
name|SKOS
operator|.
name|prefLabel
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
literal|"hinzugefüte Entity"
argument_list|,
literal|"de"
argument_list|)
expr_stmt|;
name|readwriteYard
operator|.
name|store
argument_list|(
name|rep
argument_list|)
expr_stmt|;
comment|//test that the data where added and modified in the read/wirte grpah
name|validateEntity
argument_list|(
name|readwriteYard
argument_list|,
name|singletonMap
argument_list|(
name|rep
operator|.
name|getNode
argument_list|()
argument_list|,
name|rep
operator|.
name|getRdfGraph
argument_list|()
argument_list|)
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|readwriteYard
operator|.
name|remove
argument_list|(
name|rep
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
literal|"Representation "
operator|+
name|rep
operator|.
name|getId
argument_list|()
operator|+
literal|" was not correctly "
operator|+
literal|"deleted from "
operator|+
name|readwriteYard
operator|.
name|getId
argument_list|()
argument_list|,
name|readwriteYard
operator|.
name|getRepresentation
argument_list|(
name|rep
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|YardException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testStoreOnReadOnlyYard
parameter_list|()
throws|throws
name|YardException
block|{
name|RdfRepresentation
name|rep
init|=
name|RdfValueFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createRepresentation
argument_list|(
literal|"http://www.test.org/addedEntity"
argument_list|)
decl_stmt|;
name|rep
operator|.
name|addReference
argument_list|(
name|RDF
operator|.
name|type
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
name|SKOS
operator|.
name|Concept
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
name|rep
operator|.
name|addNaturalText
argument_list|(
name|SKOS
operator|.
name|prefLabel
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
literal|"added Entity"
argument_list|,
literal|"en"
argument_list|)
expr_stmt|;
name|rep
operator|.
name|addNaturalText
argument_list|(
name|SKOS
operator|.
name|prefLabel
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
literal|"hinzugefüte Entity"
argument_list|,
literal|"de"
argument_list|)
expr_stmt|;
name|readonlyYard
operator|.
name|store
argument_list|(
name|rep
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|YardException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testRemovalOnReadOnlyYard
parameter_list|()
throws|throws
name|YardException
block|{
name|readonlyYard
operator|.
name|remove
argument_list|(
name|entityData
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Used by {@link #testRetrival()} to validate that an Entity is correctly      * retrieved by the tested {@link ClerezzaYard}s.      * @param entity key - URI; value - expected RDF data      */
specifier|private
name|void
name|validateEntity
parameter_list|(
name|ClerezzaYard
name|yard
parameter_list|,
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|TripleCollection
argument_list|>
name|entity
parameter_list|)
block|{
name|Representation
name|rep
init|=
name|yard
operator|.
name|getRepresentation
argument_list|(
name|entity
operator|.
name|getKey
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"The Representation for "
operator|+
name|entity
operator|.
name|getKey
argument_list|()
operator|+
literal|"is missing in the "
operator|+
name|yard
operator|.
name|getId
argument_list|()
argument_list|,
name|rep
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"RdfRepresentation expected"
argument_list|,
name|rep
operator|instanceof
name|RdfRepresentation
argument_list|)
expr_stmt|;
name|TripleCollection
name|repGraph
init|=
operator|(
operator|(
name|RdfRepresentation
operator|)
name|rep
operator|)
operator|.
name|getRdfGraph
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|triples
init|=
name|entity
operator|.
name|getValue
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|triples
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Triple
name|triple
init|=
name|triples
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
literal|"Data of Representation "
operator|+
name|entity
operator|.
name|getKey
argument_list|()
operator|+
literal|"is missing the triple "
operator|+
name|triple
argument_list|,
name|repGraph
operator|.
name|remove
argument_list|(
name|triple
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|repGraph
operator|.
name|size
argument_list|()
operator|+
literal|" unexpected Triples are present in the "
operator|+
literal|"Representation of Entity "
operator|+
name|entity
operator|.
name|getKey
argument_list|()
argument_list|,
name|repGraph
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Initialises the {@link #entityData} used for this test (called in BeforeClass)      */
specifier|private
specifier|static
name|void
name|initTestData
parameter_list|()
block|{
name|UriRef
name|entity1
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://www.test.org/entity1"
argument_list|)
decl_stmt|;
name|MGraph
name|entity1Data
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|entity1Data
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entity1
argument_list|,
name|RDF
operator|.
name|type
argument_list|,
name|SKOS
operator|.
name|Concept
argument_list|)
argument_list|)
expr_stmt|;
name|entity1Data
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entity1
argument_list|,
name|SKOS
operator|.
name|prefLabel
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
literal|"test"
argument_list|,
name|EN
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entity1Data
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entity1
argument_list|,
name|SKOS
operator|.
name|prefLabel
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
literal|"Test"
argument_list|,
name|DE
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entityData
operator|.
name|put
argument_list|(
name|entity1
argument_list|,
name|entity1Data
argument_list|)
expr_stmt|;
name|MGraph
name|entity2Data
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|UriRef
name|entity2
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://www.test.org/entity2"
argument_list|)
decl_stmt|;
name|entity2Data
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entity2
argument_list|,
name|RDF
operator|.
name|type
argument_list|,
name|SKOS
operator|.
name|Concept
argument_list|)
argument_list|)
expr_stmt|;
name|entity2Data
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entity2
argument_list|,
name|SKOS
operator|.
name|prefLabel
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
literal|"sub-test"
argument_list|,
name|EN
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entity2Data
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entity2
argument_list|,
name|SKOS
operator|.
name|prefLabel
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
literal|"Untertest"
argument_list|,
name|DE
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entity2Data
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entity2
argument_list|,
name|SKOS
operator|.
name|broader
argument_list|,
name|entity1
argument_list|)
argument_list|)
expr_stmt|;
name|entityData
operator|.
name|put
argument_list|(
name|entity2
argument_list|,
name|entity2Data
argument_list|)
expr_stmt|;
block|}
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|cleanup
parameter_list|()
block|{
name|tcManager
operator|.
name|deleteTripleCollection
argument_list|(
name|READ_ONLY_GRAPH_URI
argument_list|)
expr_stmt|;
name|tcManager
operator|.
name|deleteTripleCollection
argument_list|(
name|READ_WRITEGRAPH_URI
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

