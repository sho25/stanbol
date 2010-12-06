begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|engines
operator|.
name|geonames
operator|.
name|impl
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
name|assertTrue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
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
name|net
operator|.
name|SocketTimeoutException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|UnknownHostException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|Iterator
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
name|commons
operator|.
name|io
operator|.
name|IOUtils
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

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|ContentItem
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|EngineException
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|TextAnnotation
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|RdfEntityFactory
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|OntologicalClasses
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
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
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|TechnicalClasses
import|;
end_import

begin_class
specifier|public
class|class
name|TestLocationEnhancementEngine
block|{
specifier|private
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|TestLocationEnhancementEngine
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/** 	 * The context for the tests (same as in TestOpenNLPEnhancementEngine) 	 */
specifier|public
specifier|static
specifier|final
name|String
name|CONTEXT
init|=
literal|"Dr. Patrick Marshall (1869 - November 1950) was a"
operator|+
literal|" geologist who lived in New Zealand and worked at the University of Otago."
decl_stmt|;
comment|/** 	 * The person for the tests (same as in TestOpenNLPEnhancementEngine) 	 */
specifier|public
specifier|static
specifier|final
name|String
name|PERSON
init|=
literal|"Patrick Marshall"
decl_stmt|;
comment|/** 	 * The organisation for the tests (same as in TestOpenNLPEnhancementEngine) 	 */
specifier|public
specifier|static
specifier|final
name|String
name|ORGANISATION
init|=
literal|"University of Otago"
decl_stmt|;
comment|/** 	 * The place for the tests (same as in TestOpenNLPEnhancementEngine) 	 */
specifier|public
specifier|static
specifier|final
name|String
name|PLACE
init|=
literal|"New Zealand"
decl_stmt|;
specifier|static
name|LocationEnhancementEngine
name|locationEnhancementEngine
init|=
operator|new
name|LocationEnhancementEngine
argument_list|()
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setUpServices
parameter_list|()
throws|throws
name|IOException
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
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
name|MockComponentContext
name|context
init|=
operator|new
name|MockComponentContext
argument_list|(
name|properties
argument_list|)
decl_stmt|;
name|locationEnhancementEngine
operator|.
name|activate
argument_list|(
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|shutdownServices
parameter_list|()
block|{
name|locationEnhancementEngine
operator|.
name|deactivate
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|ContentItem
name|getContentItem
parameter_list|(
specifier|final
name|String
name|id
parameter_list|,
specifier|final
name|String
name|text
parameter_list|)
block|{
return|return
operator|new
name|ContentItem
argument_list|()
block|{
name|SimpleMGraph
name|metadata
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
specifier|public
name|InputStream
name|getStream
parameter_list|()
block|{
return|return
operator|new
name|ByteArrayInputStream
argument_list|(
name|text
operator|.
name|getBytes
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getMimeType
parameter_list|()
block|{
return|return
literal|"text/plain"
return|;
block|}
specifier|public
name|MGraph
name|getMetadata
parameter_list|()
block|{
return|return
name|metadata
return|;
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
block|}
return|;
block|}
specifier|public
specifier|static
name|void
name|getTextAnnotation
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|context
parameter_list|,
name|UriRef
name|type
parameter_list|)
block|{
name|String
name|content
decl_stmt|;
try|try
block|{
name|content
operator|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|ci
operator|.
name|getStream
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|//should never happen anyway!
name|content
operator|=
literal|""
expr_stmt|;
block|}
name|RdfEntityFactory
name|factory
init|=
name|RdfEntityFactory
operator|.
name|createInstance
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|)
decl_stmt|;
name|TextAnnotation
name|testAnnotation
init|=
name|factory
operator|.
name|getProxy
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"urn:iks-project:fise:test:text-annotation:person"
argument_list|)
argument_list|,
name|TextAnnotation
operator|.
name|class
argument_list|)
decl_stmt|;
name|testAnnotation
operator|.
name|setCreator
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"urn:iks-project:fise:test:dummyEngine"
argument_list|)
argument_list|)
expr_stmt|;
name|testAnnotation
operator|.
name|setCreated
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|testAnnotation
operator|.
name|setSelectedText
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|testAnnotation
operator|.
name|setSelectionContext
argument_list|(
name|context
argument_list|)
expr_stmt|;
name|testAnnotation
operator|.
name|getDcType
argument_list|()
operator|.
name|add
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|Integer
name|start
init|=
name|content
operator|.
name|indexOf
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|start
operator|<
literal|0
condition|)
block|{
comment|//if not found in the content
comment|//set some random numbers for start/end
name|start
operator|=
operator|(
name|int
operator|)
name|Math
operator|.
name|random
argument_list|()
operator|*
literal|100
expr_stmt|;
block|}
name|testAnnotation
operator|.
name|setStart
argument_list|(
name|start
argument_list|)
expr_stmt|;
name|testAnnotation
operator|.
name|setEnd
argument_list|(
name|start
operator|+
name|name
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testLocationEnhancementEngine
parameter_list|()
block|{
comment|//throws Exception{
comment|//create a content item
name|ContentItem
name|ci
init|=
name|getContentItem
argument_list|(
literal|"urn:iks-project:fise:text:content-item:person"
argument_list|,
name|CONTEXT
argument_list|)
decl_stmt|;
comment|//add three text annotations to be consumed by this test
name|getTextAnnotation
argument_list|(
name|ci
argument_list|,
name|PERSON
argument_list|,
name|CONTEXT
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
argument_list|)
expr_stmt|;
name|getTextAnnotation
argument_list|(
name|ci
argument_list|,
name|ORGANISATION
argument_list|,
name|CONTEXT
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_ORGANISATION
argument_list|)
expr_stmt|;
name|getTextAnnotation
argument_list|(
name|ci
argument_list|,
name|PLACE
argument_list|,
name|CONTEXT
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_PLACE
argument_list|)
expr_stmt|;
comment|//perform the computation of the enhancements
try|try
block|{
name|locationEnhancementEngine
operator|.
name|computeEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|EngineException
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getCause
argument_list|()
operator|instanceof
name|UnknownHostException
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to test LocationEnhancemetEngine when offline! -> skipping this test"
argument_list|,
name|e
operator|.
name|getCause
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getCause
argument_list|()
operator|instanceof
name|SocketTimeoutException
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Seams like the geonames.org webservice is currently unavailable -> skipping this test"
argument_list|,
name|e
operator|.
name|getCause
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
comment|// ... and test the results
comment|/*     	 * TODO: rw 20100617      	 *  - Expected results depend on the used Index.     	 *  - Use an example where the Organisation, Person and Place is part     	 *    of the index     	 */
name|int
name|entityAnnotationCount
init|=
name|checkAllEntityAnnotations
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|)
decl_stmt|;
comment|//two suggestions for New Zealand and one hierarchy entry for the first
comment|//suggestion
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|entityAnnotationCount
argument_list|)
expr_stmt|;
block|}
comment|/*      * -----------------------------------------------------------------------      * Helper Methods to check Text and EntityAnnotations      * -----------------------------------------------------------------------      */
comment|/**      * @param g      * @return      */
specifier|private
name|int
name|checkAllEntityAnnotations
parameter_list|(
name|MGraph
name|g
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|entityAnnotationIterator
init|=
name|g
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|Properties
operator|.
name|RDF_TYPE
argument_list|,
name|TechnicalClasses
operator|.
name|FISE_ENTITYANNOTATION
argument_list|)
decl_stmt|;
name|int
name|entityAnnotationCount
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|entityAnnotationIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|UriRef
name|entityAnnotation
init|=
operator|(
name|UriRef
operator|)
name|entityAnnotationIterator
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
decl_stmt|;
comment|// test if selected Text is added
name|checkEntityAnnotation
argument_list|(
name|g
argument_list|,
name|entityAnnotation
argument_list|)
expr_stmt|;
name|entityAnnotationCount
operator|++
expr_stmt|;
block|}
return|return
name|entityAnnotationCount
return|;
block|}
comment|/**      * Checks if an entity annotation is valid      *      * @param g      * @param textAnnotation      */
specifier|private
name|void
name|checkEntityAnnotation
parameter_list|(
name|MGraph
name|g
parameter_list|,
name|UriRef
name|entityAnnotation
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|relationIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|entityAnnotation
argument_list|,
name|Properties
operator|.
name|DC_RELATION
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|requiresIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|entityAnnotation
argument_list|,
name|Properties
operator|.
name|DC_REQUIRES
argument_list|,
literal|null
argument_list|)
decl_stmt|;
comment|// check if the relation or an requires annotation set
name|assertTrue
argument_list|(
name|relationIterator
operator|.
name|hasNext
argument_list|()
operator|||
name|requiresIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|relationIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
comment|// test if the referred annotations are text annotations
name|UriRef
name|referredTextAnnotation
init|=
operator|(
name|UriRef
operator|)
name|relationIterator
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|g
operator|.
name|filter
argument_list|(
name|referredTextAnnotation
argument_list|,
name|Properties
operator|.
name|RDF_TYPE
argument_list|,
name|TechnicalClasses
operator|.
name|FISE_TEXTANNOTATION
argument_list|)
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// test if an entity is referred
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|entityReferenceIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|entityAnnotation
argument_list|,
name|Properties
operator|.
name|FISE_ENTITY_REFERENCE
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|entityReferenceIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|// test if the reference is an URI
name|assertTrue
argument_list|(
name|entityReferenceIterator
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
operator|instanceof
name|UriRef
argument_list|)
expr_stmt|;
comment|// test if there is only one entity referred
name|assertFalse
argument_list|(
name|entityReferenceIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|// finally test if the entity label is set
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|entityLabelIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|entityAnnotation
argument_list|,
name|Properties
operator|.
name|FISE_ENTITY_LABEL
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|entityLabelIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

