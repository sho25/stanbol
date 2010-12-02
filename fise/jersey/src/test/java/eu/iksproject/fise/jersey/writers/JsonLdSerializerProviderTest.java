begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|jersey
operator|.
name|writers
package|;
end_package

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
name|ByteArrayOutputStream
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
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Calendar
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TimeZone
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

begin_class
specifier|public
class|class
name|JsonLdSerializerProviderTest
block|{
specifier|private
name|JsonLdSerializerProvider
name|jsonldProvider
decl_stmt|;
specifier|private
name|String
name|formatIdentifier
init|=
literal|"application/json"
decl_stmt|;
annotation|@
name|Before
specifier|public
name|void
name|setup
parameter_list|()
block|{
name|this
operator|.
name|jsonldProvider
operator|=
operator|new
name|JsonLdSerializerProvider
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testWrongFormat
parameter_list|()
block|{
name|String
name|context
init|=
literal|"Dr. Patrick Marshall (1869 - November 1950) was a geologist who lived in New Zealand and worked at the University of Otago."
decl_stmt|;
name|ContentItem
name|ci
init|=
name|getContentItem
argument_list|(
literal|"urn:iks-project:fise:test:content-item:person"
argument_list|,
name|context
argument_list|)
decl_stmt|;
name|OutputStream
name|serializedGraph
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|jsonldProvider
operator|.
name|serialize
argument_list|(
name|serializedGraph
argument_list|,
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
literal|"application/format+notsupported"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSingleSubjectSerializeNoNs
parameter_list|()
block|{
name|String
name|context
init|=
literal|"Dr. Patrick Marshall (1869 - November 1950) was a geologist who lived in New Zealand and worked at the University of Otago."
decl_stmt|;
name|ContentItem
name|ci
init|=
name|getContentItem
argument_list|(
literal|"urn:iks-project:fise:test:content-item:person"
argument_list|,
name|context
argument_list|)
decl_stmt|;
name|getTextAnnotation
argument_list|(
name|ci
argument_list|,
literal|"Person"
argument_list|,
literal|"Patrick Marshall"
argument_list|,
name|context
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
argument_list|)
expr_stmt|;
name|OutputStream
name|serializedGraph
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|this
operator|.
name|jsonldProvider
operator|.
name|setIndentation
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|this
operator|.
name|jsonldProvider
operator|.
name|serialize
argument_list|(
name|serializedGraph
argument_list|,
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|formatIdentifier
argument_list|)
expr_stmt|;
name|String
name|expected
init|=
literal|"{\"@\":\"<urn:iks-project:fise:test:text-annotation:Person>\",\"a\":[\"<http:\\/\\/fise.iks-project.eu\\/ontology\\/Enhancement>\",\"<http:\\/\\/fise.iks-project.eu\\/ontology\\/TextAnnotation>\"],\"http:\\/\\/fise.iks-project.eu\\/ontology\\/end\":\"\\\"20\\\"^^<http:\\/\\/www.w3.org\\/2001\\/XMLSchema#int>\",\"http:\\/\\/fise.iks-project.eu\\/ontology\\/selected-text\":\"\\\"Patrick Marshall\\\"^^<http:\\/\\/www.w3.org\\/2001\\/XMLSchema#string>\",\"http:\\/\\/fise.iks-project.eu\\/ontology\\/selection-context\":\"\\\"Dr. Patrick Marshall (1869 - November 1950) was a geologist who lived in New Zealand and worked at the University of Otago.\\\"^^<http:\\/\\/www.w3.org\\/2001\\/XMLSchema#string>\",\"http:\\/\\/fise.iks-project.eu\\/ontology\\/start\":\"\\\"4\\\"^^<http:\\/\\/www.w3.org\\/2001\\/XMLSchema#int>\",\"http:\\/\\/purl.org\\/dc\\/terms\\/created\":\"\\\"2010-10-27T14:00:00+02:00\\\"^^<http:\\/\\/www.w3.org\\/2001\\/XMLSchema#dateTime>\",\"http:\\/\\/purl.org\\/dc\\/terms\\/creator\":\"<urn:iks-project:fise:test:dummyEngine>\",\"http:\\/\\/purl.org\\/dc\\/terms\\/type\":\"<http:\\/\\/dbpedia.org\\/ontology\\/Person>\"}"
decl_stmt|;
name|String
name|result
init|=
name|serializedGraph
operator|.
name|toString
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSingleSubjectSerializeNoNsWithIndent
parameter_list|()
block|{
name|String
name|context
init|=
literal|"Dr. Patrick Marshall (1869 - November 1950) was a geologist who lived in New Zealand and worked at the University of Otago."
decl_stmt|;
name|ContentItem
name|ci
init|=
name|getContentItem
argument_list|(
literal|"urn:iks-project:fise:test:content-item:person"
argument_list|,
name|context
argument_list|)
decl_stmt|;
name|getTextAnnotation
argument_list|(
name|ci
argument_list|,
literal|"Person"
argument_list|,
literal|"Patrick Marshall"
argument_list|,
name|context
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
argument_list|)
expr_stmt|;
name|OutputStream
name|serializedGraph
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|this
operator|.
name|jsonldProvider
operator|.
name|serialize
argument_list|(
name|serializedGraph
argument_list|,
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|formatIdentifier
argument_list|)
expr_stmt|;
name|String
name|expected
init|=
literal|"{\n  \"@\": \"<urn:iks-project:fise:test:text-annotation:Person>\",\n  \"a\": [\n    \"<http:\\/\\/fise.iks-project.eu\\/ontology\\/Enhancement>\",\n    \"<http:\\/\\/fise.iks-project.eu\\/ontology\\/TextAnnotation>\"\n  ],\n  \"http:\\/\\/fise.iks-project.eu\\/ontology\\/end\": \"\\\"20\\\"^^<http:\\/\\/www.w3.org\\/2001\\/XMLSchema#int>\",\n  \"http:\\/\\/fise.iks-project.eu\\/ontology\\/selected-text\": \"\\\"Patrick Marshall\\\"^^<http:\\/\\/www.w3.org\\/2001\\/XMLSchema#string>\",\n  \"http:\\/\\/fise.iks-project.eu\\/ontology\\/selection-context\": \"\\\"Dr. Patrick Marshall (1869 - November 1950) was a geologist who lived in New Zealand and worked at the University of Otago.\\\"^^<http:\\/\\/www.w3.org\\/2001\\/XMLSchema#string>\",\n  \"http:\\/\\/fise.iks-project.eu\\/ontology\\/start\": \"\\\"4\\\"^^<http:\\/\\/www.w3.org\\/2001\\/XMLSchema#int>\",\n  \"http:\\/\\/purl.org\\/dc\\/terms\\/created\": \"\\\"2010-10-27T14:00:00+02:00\\\"^^<http:\\/\\/www.w3.org\\/2001\\/XMLSchema#dateTime>\",\n  \"http:\\/\\/purl.org\\/dc\\/terms\\/creator\": \"<urn:iks-project:fise:test:dummyEngine>\",\n  \"http:\\/\\/purl.org\\/dc\\/terms\\/type\": \"<http:\\/\\/dbpedia.org\\/ontology\\/Person>\"\n}"
decl_stmt|;
name|String
name|result
init|=
name|serializedGraph
operator|.
name|toString
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSingleSubjectSerializeWithNs
parameter_list|()
block|{
name|String
name|context
init|=
literal|"Dr. Patrick Marshall (1869 - November 1950) was a geologist who lived in New Zealand and worked at the University of Otago."
decl_stmt|;
name|ContentItem
name|ci
init|=
name|getContentItem
argument_list|(
literal|"urn:iks-project:fise:test:content-item:person"
argument_list|,
name|context
argument_list|)
decl_stmt|;
name|getTextAnnotation
argument_list|(
name|ci
argument_list|,
literal|"Person"
argument_list|,
literal|"Patrick Marshall"
argument_list|,
name|context
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
argument_list|)
expr_stmt|;
name|OutputStream
name|serializedGraph
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|nsMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|nsMap
operator|.
name|put
argument_list|(
literal|"http://fise.iks-project.eu/ontology/"
argument_list|,
literal|"fise"
argument_list|)
expr_stmt|;
name|nsMap
operator|.
name|put
argument_list|(
literal|"http://www.w3.org/2001/XMLSchema#"
argument_list|,
literal|"xmlns"
argument_list|)
expr_stmt|;
name|nsMap
operator|.
name|put
argument_list|(
literal|"http://dbpedia.org/ontology/"
argument_list|,
literal|"dbpedia"
argument_list|)
expr_stmt|;
name|nsMap
operator|.
name|put
argument_list|(
literal|"http://purl.org/dc/terms"
argument_list|,
literal|"dcterms"
argument_list|)
expr_stmt|;
name|this
operator|.
name|jsonldProvider
operator|.
name|setIndentation
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|this
operator|.
name|jsonldProvider
operator|.
name|setNamespacePrefixMap
argument_list|(
name|nsMap
argument_list|)
expr_stmt|;
name|this
operator|.
name|jsonldProvider
operator|.
name|serialize
argument_list|(
name|serializedGraph
argument_list|,
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|formatIdentifier
argument_list|)
expr_stmt|;
name|String
name|expected
init|=
literal|"{\"#\":{\"dbpedia\":\"http:\\/\\/dbpedia.org\\/ontology\\/\",\"dcterms\":\"http:\\/\\/purl.org\\/dc\\/terms\",\"fise\":\"http:\\/\\/fise.iks-project.eu\\/ontology\\/\",\"xmlns\":\"http:\\/\\/www.w3.org\\/2001\\/XMLSchema#\"},\"@\":\"<urn:iks-project:fise:test:text-annotation:Person>\",\"a\":[\"<fise:Enhancement>\",\"<fise:TextAnnotation>\"],\"dcterms:\\/created\":\"\\\"2010-10-27T14:00:00+02:00\\\"^^<xmlns:dateTime>\",\"dcterms:\\/creator\":\"<urn:iks-project:fise:test:dummyEngine>\",\"dcterms:\\/type\":\"<dbpedia:Person>\",\"fise:end\":\"\\\"20\\\"^^<xmlns:int>\",\"fise:selected-text\":\"\\\"Patrick Marshall\\\"^^<xmlns:string>\",\"fise:selection-context\":\"\\\"Dr. Patrick Marshall (1869 - November 1950) was a geologist who lived in New Zealand and worked at the University of Otago.\\\"^^<xmlns:string>\",\"fise:start\":\"\\\"4\\\"^^<xmlns:int>\"}"
decl_stmt|;
name|String
name|result
init|=
name|serializedGraph
operator|.
name|toString
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSingleSubjectSerializeWithNsWithIndent
parameter_list|()
block|{
name|String
name|context
init|=
literal|"Dr. Patrick Marshall (1869 - November 1950) was a geologist who lived in New Zealand and worked at the University of Otago."
decl_stmt|;
name|ContentItem
name|ci
init|=
name|getContentItem
argument_list|(
literal|"urn:iks-project:fise:test:content-item:person"
argument_list|,
name|context
argument_list|)
decl_stmt|;
name|getTextAnnotation
argument_list|(
name|ci
argument_list|,
literal|"Person"
argument_list|,
literal|"Patrick Marshall"
argument_list|,
name|context
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
argument_list|)
expr_stmt|;
name|OutputStream
name|serializedGraph
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|nsMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|nsMap
operator|.
name|put
argument_list|(
literal|"http://fise.iks-project.eu/ontology/"
argument_list|,
literal|"fise"
argument_list|)
expr_stmt|;
name|nsMap
operator|.
name|put
argument_list|(
literal|"http://www.w3.org/2001/XMLSchema#"
argument_list|,
literal|"xmlns"
argument_list|)
expr_stmt|;
name|nsMap
operator|.
name|put
argument_list|(
literal|"http://dbpedia.org/ontology/"
argument_list|,
literal|"dbpedia"
argument_list|)
expr_stmt|;
name|nsMap
operator|.
name|put
argument_list|(
literal|"http://purl.org/dc/terms"
argument_list|,
literal|"dcterms"
argument_list|)
expr_stmt|;
name|this
operator|.
name|jsonldProvider
operator|.
name|setIndentation
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|this
operator|.
name|jsonldProvider
operator|.
name|setNamespacePrefixMap
argument_list|(
name|nsMap
argument_list|)
expr_stmt|;
name|this
operator|.
name|jsonldProvider
operator|.
name|serialize
argument_list|(
name|serializedGraph
argument_list|,
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|formatIdentifier
argument_list|)
expr_stmt|;
name|String
name|expected
init|=
literal|"{\n    \"#\": {\n        \"dbpedia\": \"http:\\/\\/dbpedia.org\\/ontology\\/\",\n        \"dcterms\": \"http:\\/\\/purl.org\\/dc\\/terms\",\n        \"fise\": \"http:\\/\\/fise.iks-project.eu\\/ontology\\/\",\n        \"xmlns\": \"http:\\/\\/www.w3.org\\/2001\\/XMLSchema#\"\n    },\n    \"@\": \"<urn:iks-project:fise:test:text-annotation:Person>\",\n    \"a\": [\n        \"<fise:Enhancement>\",\n        \"<fise:TextAnnotation>\"\n    ],\n    \"dcterms:\\/created\": \"\\\"2010-10-27T14:00:00+02:00\\\"^^<xmlns:dateTime>\",\n    \"dcterms:\\/creator\": \"<urn:iks-project:fise:test:dummyEngine>\",\n    \"dcterms:\\/type\": \"<dbpedia:Person>\",\n    \"fise:end\": \"\\\"20\\\"^^<xmlns:int>\",\n    \"fise:selected-text\": \"\\\"Patrick Marshall\\\"^^<xmlns:string>\",\n    \"fise:selection-context\": \"\\\"Dr. Patrick Marshall (1869 - November 1950) was a geologist who lived in New Zealand and worked at the University of Otago.\\\"^^<xmlns:string>\",\n    \"fise:start\": \"\\\"4\\\"^^<xmlns:int>\"\n}"
decl_stmt|;
name|String
name|result
init|=
name|serializedGraph
operator|.
name|toString
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
specifier|private
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
specifier|private
name|void
name|getTextAnnotation
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|String
name|annotationURNExtension
parameter_list|,
name|String
name|namedEntity
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
comment|// should never happen anyway!
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
literal|"urn:iks-project:fise:test:text-annotation:"
operator|+
name|annotationURNExtension
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
name|Calendar
name|myCal
init|=
name|Calendar
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|myCal
operator|.
name|set
argument_list|(
literal|2010
argument_list|,
literal|9
argument_list|,
literal|27
argument_list|,
literal|12
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|myCal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MILLISECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|myCal
operator|.
name|setTimeZone
argument_list|(
name|TimeZone
operator|.
name|getTimeZone
argument_list|(
literal|"Germany/Berlin"
argument_list|)
argument_list|)
expr_stmt|;
name|testAnnotation
operator|.
name|setCreated
argument_list|(
name|myCal
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
name|testAnnotation
operator|.
name|setSelectedText
argument_list|(
name|namedEntity
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
name|namedEntity
argument_list|)
decl_stmt|;
if|if
condition|(
name|start
operator|<
literal|0
condition|)
block|{
comment|// if not found in the content set start to 42
name|start
operator|=
literal|42
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
name|namedEntity
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|private
name|void
name|toConsole
parameter_list|(
name|String
name|result
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|String
name|s
init|=
name|result
decl_stmt|;
name|s
operator|=
name|s
operator|.
name|replaceAll
argument_list|(
literal|"\\\\"
argument_list|,
literal|"\\\\\\\\"
argument_list|)
expr_stmt|;
name|s
operator|=
name|s
operator|.
name|replace
argument_list|(
literal|"\""
argument_list|,
literal|"\\\""
argument_list|)
expr_stmt|;
name|s
operator|=
name|s
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|"\\n"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

