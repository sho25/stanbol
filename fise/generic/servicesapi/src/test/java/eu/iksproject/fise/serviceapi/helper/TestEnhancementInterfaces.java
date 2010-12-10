begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|serviceapi
operator|.
name|helper
package|;
end_package

begin_import
import|import static
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
operator|.
name|*
import|;
end_import

begin_import
import|import static
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
operator|.
name|FISE_TEXTANNOTATION
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
name|Date
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
name|Literal
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
name|Resource
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
name|junit
operator|.
name|Test
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
name|EntityAnnotation
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
name|EnhancementEngineHelper
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
name|TechnicalClasses
import|;
end_import

begin_comment
comment|/**  * Tests if the enhancement interfaces can be used to write valid enhancement.  *  * @author westei  */
end_comment

begin_class
specifier|public
class|class
name|TestEnhancementInterfaces
block|{
specifier|public
specifier|static
specifier|final
name|String
name|SINGLE_SENTENCE
init|=
literal|"Dr. Patrick Marshall (1869 - November 1950) was a"
operator|+
literal|" geologist who lived in New Zealand and worked at the University of Otago."
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|TEST_ENHANCEMENT_ENGINE_URI
init|=
operator|new
name|UriRef
argument_list|(
literal|"urn:test:dummyEnhancementEngine"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|ContentItem
name|wrapAsContentItem
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
annotation|@
name|Test
specifier|public
name|void
name|testEnhancementInterfaces
parameter_list|()
throws|throws
name|Exception
block|{
name|ContentItem
name|ci
init|=
name|wrapAsContentItem
argument_list|(
literal|"urn:contentItem-"
operator|+
name|EnhancementEngineHelper
operator|.
name|randomUUID
argument_list|()
argument_list|,
name|SINGLE_SENTENCE
argument_list|)
decl_stmt|;
name|UriRef
name|ciUri
init|=
operator|new
name|UriRef
argument_list|(
name|ci
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
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
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
comment|//create an Text Annotation representing an extracted Person
name|TextAnnotation
name|personAnnotation
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|createEnhancementURI
argument_list|()
argument_list|,
name|TextAnnotation
operator|.
name|class
argument_list|)
decl_stmt|;
name|personAnnotation
operator|.
name|setCreator
argument_list|(
name|TEST_ENHANCEMENT_ENGINE_URI
argument_list|)
expr_stmt|;
name|personAnnotation
operator|.
name|setCreated
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|personAnnotation
operator|.
name|setExtractedFrom
argument_list|(
name|ciUri
argument_list|)
expr_stmt|;
name|personAnnotation
operator|.
name|getDcType
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://fise.iks-project.eu/cv/annotatation-types/text#Person"
argument_list|)
argument_list|)
expr_stmt|;
name|personAnnotation
operator|.
name|setConfidence
argument_list|(
literal|0.8
argument_list|)
expr_stmt|;
name|personAnnotation
operator|.
name|setSelectedText
argument_list|(
literal|"Patrick Marshall"
argument_list|)
expr_stmt|;
name|personAnnotation
operator|.
name|setStart
argument_list|(
name|SINGLE_SENTENCE
operator|.
name|indexOf
argument_list|(
name|personAnnotation
operator|.
name|getSelectedText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|personAnnotation
operator|.
name|setEnd
argument_list|(
name|personAnnotation
operator|.
name|getStart
argument_list|()
operator|+
name|personAnnotation
operator|.
name|getSelectedText
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|personAnnotation
operator|.
name|setSelectionContext
argument_list|(
name|SINGLE_SENTENCE
argument_list|)
expr_stmt|;
comment|//create an Text Annotation representing an extracted Location
name|TextAnnotation
name|locationAnnotation
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|createEnhancementURI
argument_list|()
argument_list|,
name|TextAnnotation
operator|.
name|class
argument_list|)
decl_stmt|;
name|locationAnnotation
operator|.
name|setCreator
argument_list|(
name|TEST_ENHANCEMENT_ENGINE_URI
argument_list|)
expr_stmt|;
name|locationAnnotation
operator|.
name|setCreated
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|locationAnnotation
operator|.
name|setExtractedFrom
argument_list|(
name|ciUri
argument_list|)
expr_stmt|;
name|locationAnnotation
operator|.
name|getDcType
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://fise.iks-project.eu/cv/annotatation-types/text#Location"
argument_list|)
argument_list|)
expr_stmt|;
name|locationAnnotation
operator|.
name|setConfidence
argument_list|(
literal|0.78
argument_list|)
expr_stmt|;
name|locationAnnotation
operator|.
name|setSelectedText
argument_list|(
literal|"New Zealand"
argument_list|)
expr_stmt|;
name|locationAnnotation
operator|.
name|setStart
argument_list|(
name|SINGLE_SENTENCE
operator|.
name|indexOf
argument_list|(
name|locationAnnotation
operator|.
name|getSelectedText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|locationAnnotation
operator|.
name|setEnd
argument_list|(
name|locationAnnotation
operator|.
name|getStart
argument_list|()
operator|+
name|locationAnnotation
operator|.
name|getSelectedText
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|locationAnnotation
operator|.
name|setSelectionContext
argument_list|(
name|SINGLE_SENTENCE
argument_list|)
expr_stmt|;
comment|//create an Text Annotation representing an extracted Organisation
name|TextAnnotation
name|orgAnnotation
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|createEnhancementURI
argument_list|()
argument_list|,
name|TextAnnotation
operator|.
name|class
argument_list|)
decl_stmt|;
name|orgAnnotation
operator|.
name|setCreator
argument_list|(
name|TEST_ENHANCEMENT_ENGINE_URI
argument_list|)
expr_stmt|;
name|orgAnnotation
operator|.
name|setCreated
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|orgAnnotation
operator|.
name|setExtractedFrom
argument_list|(
name|ciUri
argument_list|)
expr_stmt|;
name|orgAnnotation
operator|.
name|getDcType
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://fise.iks-project.eu/cv/annotatation-types/text#Organisation"
argument_list|)
argument_list|)
expr_stmt|;
name|orgAnnotation
operator|.
name|setConfidence
argument_list|(
literal|0.78
argument_list|)
expr_stmt|;
name|orgAnnotation
operator|.
name|setSelectedText
argument_list|(
literal|"University of Otago"
argument_list|)
expr_stmt|;
name|orgAnnotation
operator|.
name|setStart
argument_list|(
name|SINGLE_SENTENCE
operator|.
name|indexOf
argument_list|(
name|orgAnnotation
operator|.
name|getSelectedText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|orgAnnotation
operator|.
name|setEnd
argument_list|(
name|orgAnnotation
operator|.
name|getStart
argument_list|()
operator|+
name|orgAnnotation
operator|.
name|getSelectedText
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|orgAnnotation
operator|.
name|setSelectionContext
argument_list|(
name|SINGLE_SENTENCE
argument_list|)
expr_stmt|;
comment|// create an Entity Annotation for the person TextAnnotation
name|EntityAnnotation
name|patrickMarshall
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|createEnhancementURI
argument_list|()
argument_list|,
name|EntityAnnotation
operator|.
name|class
argument_list|)
decl_stmt|;
name|patrickMarshall
operator|.
name|setCreator
argument_list|(
name|TEST_ENHANCEMENT_ENGINE_URI
argument_list|)
expr_stmt|;
name|patrickMarshall
operator|.
name|setCreated
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|patrickMarshall
operator|.
name|setExtractedFrom
argument_list|(
name|ciUri
argument_list|)
expr_stmt|;
name|patrickMarshall
operator|.
name|getDcType
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://fise.iks-project.eu/cv/annotatation-types/entity#Entity"
argument_list|)
argument_list|)
expr_stmt|;
name|patrickMarshall
operator|.
name|setConfidence
argument_list|(
literal|0.56
argument_list|)
expr_stmt|;
name|patrickMarshall
operator|.
name|getRelations
argument_list|()
operator|.
name|add
argument_list|(
name|personAnnotation
argument_list|)
expr_stmt|;
name|patrickMarshall
operator|.
name|setEntityLabel
argument_list|(
literal|"Patrick Marshall"
argument_list|)
expr_stmt|;
name|patrickMarshall
operator|.
name|setEntityReference
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/rdf/en/patrick_marshall"
argument_list|)
argument_list|)
expr_stmt|;
name|patrickMarshall
operator|.
name|getEntityTypes
argument_list|()
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/people.person"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/common.topic"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/education.academic"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// and an other for New Zealand
name|EntityAnnotation
name|newZealand
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|createEnhancementURI
argument_list|()
argument_list|,
name|EntityAnnotation
operator|.
name|class
argument_list|)
decl_stmt|;
name|newZealand
operator|.
name|setCreator
argument_list|(
name|TEST_ENHANCEMENT_ENGINE_URI
argument_list|)
expr_stmt|;
name|newZealand
operator|.
name|setCreated
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|newZealand
operator|.
name|setExtractedFrom
argument_list|(
name|ciUri
argument_list|)
expr_stmt|;
name|newZealand
operator|.
name|getDcType
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://fise.iks-project.eu/cv/annotatation-types/entity#Entity"
argument_list|)
argument_list|)
expr_stmt|;
name|newZealand
operator|.
name|setConfidence
argument_list|(
literal|0.98
argument_list|)
expr_stmt|;
name|newZealand
operator|.
name|getRelations
argument_list|()
operator|.
name|add
argument_list|(
name|locationAnnotation
argument_list|)
expr_stmt|;
name|newZealand
operator|.
name|setEntityLabel
argument_list|(
literal|"New Zealand"
argument_list|)
expr_stmt|;
name|newZealand
operator|.
name|setEntityReference
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/rdf/en/new_zealand"
argument_list|)
argument_list|)
expr_stmt|;
name|newZealand
operator|.
name|getEntityTypes
argument_list|()
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/location.location"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/common.topic"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/location.country"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// and an other option for New Zealand
name|EntityAnnotation
name|airNewZealand
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|createEnhancementURI
argument_list|()
argument_list|,
name|EntityAnnotation
operator|.
name|class
argument_list|)
decl_stmt|;
name|airNewZealand
operator|.
name|setCreator
argument_list|(
name|TEST_ENHANCEMENT_ENGINE_URI
argument_list|)
expr_stmt|;
name|airNewZealand
operator|.
name|setCreated
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|airNewZealand
operator|.
name|setExtractedFrom
argument_list|(
name|ciUri
argument_list|)
expr_stmt|;
name|airNewZealand
operator|.
name|getDcType
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://fise.iks-project.eu/cv/annotatation-types/entity#Entity"
argument_list|)
argument_list|)
expr_stmt|;
name|airNewZealand
operator|.
name|setConfidence
argument_list|(
literal|0.36
argument_list|)
expr_stmt|;
name|airNewZealand
operator|.
name|getRelations
argument_list|()
operator|.
name|add
argument_list|(
name|locationAnnotation
argument_list|)
expr_stmt|;
name|airNewZealand
operator|.
name|setEntityLabel
argument_list|(
literal|"New Zealand"
argument_list|)
expr_stmt|;
name|airNewZealand
operator|.
name|setEntityReference
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/rdf/en/air_new_zealand"
argument_list|)
argument_list|)
expr_stmt|;
name|airNewZealand
operator|.
name|getEntityTypes
argument_list|()
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/business.sponsor"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/common.topic"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/travel.transport_operator"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/aviation.airline"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/aviation.aircraft_owner"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/business.employer"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/freebase.apps.hosts.com.appspot.acre.juggle.juggle"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://rdf.freebase.com/ns/business.company"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"creation time "
operator|+
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
operator|)
operator|+
literal|"ms"
argument_list|)
expr_stmt|;
comment|//now test the enhancement
name|int
name|numberOfTextAnnotations
init|=
name|checkAllTextAnnotations
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|numberOfTextAnnotations
argument_list|)
expr_stmt|;
name|int
name|numberOfEntityAnnotations
init|=
name|checkAllEntityAnnotations
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|numberOfEntityAnnotations
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|UriRef
name|createEnhancementURI
parameter_list|()
block|{
comment|//TODO: add some Utility to create Instances to the RdfEntityFactory
comment|//      this should create a new URI by some default Algorithm
return|return
operator|new
name|UriRef
argument_list|(
literal|"urn:enhancement-"
operator|+
name|EnhancementEngineHelper
operator|.
name|randomUUID
argument_list|()
argument_list|)
return|;
block|}
comment|/*      * -----------------------------------------------------------------------      * Helper Methods to check Text and EntityAnnotations      * -----------------------------------------------------------------------      */
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
specifier|private
name|int
name|checkAllTextAnnotations
parameter_list|(
name|MGraph
name|g
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|textAnnotationIterator
init|=
name|g
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|RDF_TYPE
argument_list|,
name|FISE_TEXTANNOTATION
argument_list|)
decl_stmt|;
comment|// test if a textAnnotation is present
name|assertTrue
argument_list|(
literal|"Expecting non-empty textAnnotationIterator"
argument_list|,
name|textAnnotationIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|textAnnotationCount
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|textAnnotationIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|UriRef
name|textAnnotation
init|=
operator|(
name|UriRef
operator|)
name|textAnnotationIterator
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
decl_stmt|;
comment|// test if selected Text is added
name|checkTextAnnotation
argument_list|(
name|g
argument_list|,
name|textAnnotation
argument_list|)
expr_stmt|;
name|textAnnotationCount
operator|++
expr_stmt|;
block|}
return|return
name|textAnnotationCount
return|;
block|}
comment|/**      * Checks if a text annotation is valid.      */
specifier|private
name|void
name|checkTextAnnotation
parameter_list|(
name|MGraph
name|g
parameter_list|,
name|UriRef
name|textAnnotation
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|selectedTextIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|textAnnotation
argument_list|,
name|FISE_SELECTED_TEXT
argument_list|,
literal|null
argument_list|)
decl_stmt|;
comment|// check if the selected text is added
name|assertTrue
argument_list|(
name|selectedTextIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|// test if the selected text is part of the TEXT_TO_TEST
name|Resource
name|object
init|=
name|selectedTextIterator
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|object
operator|instanceof
name|Literal
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|SINGLE_SENTENCE
operator|.
name|contains
argument_list|(
operator|(
operator|(
name|Literal
operator|)
name|object
operator|)
operator|.
name|getLexicalForm
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// test if context is added
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|selectionContextIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|textAnnotation
argument_list|,
name|FISE_SELECTION_CONTEXT
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|selectionContextIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|// test if the selected text is part of the TEXT_TO_TEST
name|object
operator|=
name|selectionContextIterator
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
expr_stmt|;
name|assertTrue
argument_list|(
name|object
operator|instanceof
name|Literal
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|SINGLE_SENTENCE
operator|.
name|contains
argument_list|(
operator|(
operator|(
name|Literal
operator|)
name|object
operator|)
operator|.
name|getLexicalForm
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Checks if an entity annotation is valid.      */
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
name|relationToTextAnnotationIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|entityAnnotation
argument_list|,
name|DC_RELATION
argument_list|,
literal|null
argument_list|)
decl_stmt|;
comment|// check if the relation to the text annotation is set
name|assertTrue
argument_list|(
name|relationToTextAnnotationIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|relationToTextAnnotationIterator
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
name|relationToTextAnnotationIterator
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
name|RDF_TYPE
argument_list|,
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

