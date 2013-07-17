begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|engines
operator|.
name|textannotationnewmodel
operator|.
name|impl
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|RDF_TYPE
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|TechnicalClasses
operator|.
name|ENHANCER_TEXTANNOTATION
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|HashMap
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
name|NonLiteral
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
name|PlainLiteral
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
name|TypedLiteral
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
name|clerezza
operator|.
name|rdf
operator|.
name|jena
operator|.
name|parser
operator|.
name|JenaParserProvider
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
name|commons
operator|.
name|indexedgraph
operator|.
name|IndexedMGraph
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
name|enhancer
operator|.
name|contentitem
operator|.
name|inmemory
operator|.
name|InMemoryContentItemFactory
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentItem
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentItemFactory
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|EngineException
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|EnhancementEngine
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|EnhancementEngineHelper
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|impl
operator|.
name|StringSource
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|TechnicalClasses
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
name|enhancer
operator|.
name|test
operator|.
name|helper
operator|.
name|EnhancementStructureHelper
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
name|AfterClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
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
name|osgi
operator|.
name|service
operator|.
name|cm
operator|.
name|ConfigurationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
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
name|Resource
import|;
end_import

begin_class
specifier|public
class|class
name|TextAnnotationNewModelEngineTest
block|{
specifier|public
specifier|static
specifier|final
name|String
name|SINGLE_SENTENCE
init|=
literal|"Dr Patrick Marshall (1869 - November 1950) was a"
operator|+
literal|" geologist who lived in New Zealand and worked at the University of Otago."
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|TEST_ENHANCEMENTS
init|=
literal|"enhancement-results.rdf"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|JenaParserProvider
name|rdfParser
init|=
operator|new
name|JenaParserProvider
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|MGraph
name|origEnhancements
decl_stmt|;
specifier|private
specifier|static
name|UriRef
name|ciUri
decl_stmt|;
specifier|private
name|ContentItem
name|contentItem
decl_stmt|;
specifier|private
specifier|static
name|TextAnnotationsNewModelEngine
name|engine
decl_stmt|;
specifier|private
specifier|final
name|ContentItemFactory
name|ciFactory
init|=
name|InMemoryContentItemFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|ComponentContext
name|ctx
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|init
parameter_list|()
throws|throws
name|IOException
throws|,
name|ConfigurationException
block|{
name|InputStream
name|in
init|=
name|TextAnnotationNewModelEngineTest
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|TEST_ENHANCEMENTS
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
literal|"Unable to load reaource '"
operator|+
name|TEST_ENHANCEMENTS
operator|+
literal|"' via Classpath"
argument_list|,
name|in
argument_list|)
expr_stmt|;
name|origEnhancements
operator|=
operator|new
name|IndexedMGraph
argument_list|()
expr_stmt|;
name|rdfParser
operator|.
name|parse
argument_list|(
name|origEnhancements
argument_list|,
name|in
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|origEnhancements
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
comment|//parse the ID of the ContentItem form the enhancements
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|origEnhancements
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|Properties
operator|.
name|ENHANCER_EXTRACTED_FROM
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|it
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|Resource
name|id
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|id
operator|instanceof
name|UriRef
argument_list|)
expr_stmt|;
name|ciUri
operator|=
operator|(
name|UriRef
operator|)
name|id
expr_stmt|;
comment|//validate that the enhancements in the file are valid
name|EnhancementStructureHelper
operator|.
name|validateAllTextAnnotations
argument_list|(
name|origEnhancements
argument_list|,
name|SINGLE_SENTENCE
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|//those do not yet contain fise:selection-prefix/suffix values
comment|//init the engine
name|engine
operator|=
operator|new
name|TextAnnotationsNewModelEngine
argument_list|()
expr_stmt|;
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
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|,
literal|"test-engine"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|TextAnnotationsNewModelEngine
operator|.
name|PROPERTY_PREFIX_SUFFIX_SIZE
argument_list|,
name|Integer
operator|.
name|valueOf
argument_list|(
literal|10
argument_list|)
argument_list|)
expr_stmt|;
name|ctx
operator|=
operator|new
name|MockComponentContext
argument_list|(
name|config
argument_list|)
expr_stmt|;
name|engine
operator|.
name|activate
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|initTest
parameter_list|()
throws|throws
name|IOException
block|{
name|contentItem
operator|=
name|ciFactory
operator|.
name|createContentItem
argument_list|(
name|ciUri
argument_list|,
operator|new
name|StringSource
argument_list|(
name|SINGLE_SENTENCE
argument_list|)
argument_list|,
operator|new
name|IndexedMGraph
argument_list|(
name|origEnhancements
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testTextAnnotationNewModel
parameter_list|()
throws|throws
name|EngineException
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|EnhancementEngine
operator|.
name|ENHANCE_ASYNC
argument_list|,
name|engine
operator|.
name|canEnhance
argument_list|(
name|contentItem
argument_list|)
argument_list|)
expr_stmt|;
name|engine
operator|.
name|computeEnhancements
argument_list|(
name|contentItem
argument_list|)
expr_stmt|;
comment|//validate
name|MGraph
name|g
init|=
name|contentItem
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|g
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|RDF_TYPE
argument_list|,
name|ENHANCER_TEXTANNOTATION
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|it
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|NonLiteral
name|ta
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|ta
operator|instanceof
name|UriRef
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|UriRef
argument_list|,
name|Resource
argument_list|>
name|expected
init|=
operator|new
name|HashMap
argument_list|<
name|UriRef
argument_list|,
name|Resource
argument_list|>
argument_list|()
decl_stmt|;
name|expected
operator|.
name|put
argument_list|(
name|Properties
operator|.
name|ENHANCER_EXTRACTED_FROM
argument_list|,
name|contentItem
operator|.
name|getUri
argument_list|()
argument_list|)
expr_stmt|;
name|EnhancementStructureHelper
operator|.
name|validateTextAnnotation
argument_list|(
name|g
argument_list|,
operator|(
name|UriRef
operator|)
name|ta
argument_list|,
name|SINGLE_SENTENCE
argument_list|,
name|expected
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|After
specifier|public
name|void
name|afterTest
parameter_list|()
block|{
name|contentItem
operator|=
literal|null
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
name|engine
operator|.
name|deactivate
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

