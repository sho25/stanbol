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
name|resource
package|;
end_package

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
name|UnsupportedEncodingException
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
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|List
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
name|TreeMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|UriBuilder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|UriInfo
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
name|LiteralFactory
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
name|serializedform
operator|.
name|Serializer
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
name|core
operator|.
name|sparql
operator|.
name|ParseException
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
name|sparql
operator|.
name|QueryParser
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
name|sparql
operator|.
name|ResultSet
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
name|sparql
operator|.
name|SolutionMapping
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
name|sparql
operator|.
name|query
operator|.
name|SelectQuery
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
name|utils
operator|.
name|GraphNode
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
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|view
operator|.
name|ImplicitProduces
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

begin_class
annotation|@
name|ImplicitProduces
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
operator|+
literal|";qs=2"
argument_list|)
specifier|public
class|class
name|ContentItemResource
extends|extends
name|NavigationMixin
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|private
specifier|final
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
comment|// TODO make this configurable trough a property
specifier|public
specifier|static
specifier|final
name|UriRef
name|SUMMARY
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://www.w3.org/2000/01/rdf-schema#comment"
argument_list|)
decl_stmt|;
comment|// TODO make this configurable trough a property
specifier|public
specifier|static
specifier|final
name|UriRef
name|THUMBNAIL
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://dbpedia.org/ontology/thumbnail"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Map
argument_list|<
name|UriRef
argument_list|,
name|String
argument_list|>
name|DEFAULT_THUMBNAILS
init|=
operator|new
name|HashMap
argument_list|<
name|UriRef
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
static|static
block|{
name|DEFAULT_THUMBNAILS
operator|.
name|put
argument_list|(
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
argument_list|,
literal|"/static/images/user_48.png"
argument_list|)
expr_stmt|;
name|DEFAULT_THUMBNAILS
operator|.
name|put
argument_list|(
name|OntologicalClasses
operator|.
name|DBPEDIA_ORGANISATION
argument_list|,
literal|"/static/images/organization_48.png"
argument_list|)
expr_stmt|;
name|DEFAULT_THUMBNAILS
operator|.
name|put
argument_list|(
name|OntologicalClasses
operator|.
name|DBPEDIA_PLACE
argument_list|,
literal|"/static/images/compass_48.png"
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|ContentItem
name|contentItem
decl_stmt|;
specifier|protected
name|String
name|localId
decl_stmt|;
specifier|protected
name|String
name|textContent
decl_stmt|;
specifier|protected
name|URI
name|imageSrc
decl_stmt|;
specifier|protected
name|URI
name|downloadHref
decl_stmt|;
specifier|protected
name|URI
name|metadataHref
decl_stmt|;
specifier|protected
specifier|final
name|TcManager
name|tcManager
decl_stmt|;
specifier|protected
specifier|final
name|Serializer
name|serializer
decl_stmt|;
specifier|protected
name|String
name|serializationFormat
init|=
name|SupportedFormat
operator|.
name|RDF_XML
decl_stmt|;
specifier|protected
specifier|final
name|TripleCollection
name|remoteEntityCache
decl_stmt|;
specifier|protected
name|Collection
argument_list|<
name|EntityExtractionSummary
argument_list|>
name|people
decl_stmt|;
specifier|protected
name|Collection
argument_list|<
name|EntityExtractionSummary
argument_list|>
name|organizations
decl_stmt|;
specifier|protected
name|Collection
argument_list|<
name|EntityExtractionSummary
argument_list|>
name|places
decl_stmt|;
specifier|public
name|ContentItemResource
parameter_list|(
name|String
name|localId
parameter_list|,
name|ContentItem
name|ci
parameter_list|,
name|TripleCollection
name|remoteEntityCache
parameter_list|,
name|UriInfo
name|uriInfo
parameter_list|,
name|TcManager
name|tcManager
parameter_list|,
name|Serializer
name|serializer
parameter_list|)
throws|throws
name|IOException
block|{
name|this
operator|.
name|contentItem
operator|=
name|ci
expr_stmt|;
name|this
operator|.
name|localId
operator|=
name|localId
expr_stmt|;
name|this
operator|.
name|uriInfo
operator|=
name|uriInfo
expr_stmt|;
name|this
operator|.
name|tcManager
operator|=
name|tcManager
expr_stmt|;
name|this
operator|.
name|serializer
operator|=
name|serializer
expr_stmt|;
name|this
operator|.
name|remoteEntityCache
operator|=
name|remoteEntityCache
expr_stmt|;
if|if
condition|(
name|localId
operator|!=
literal|null
condition|)
block|{
name|URI
name|rawURI
init|=
name|UriBuilder
operator|.
name|fromPath
argument_list|(
literal|"/store/raw/"
operator|+
name|localId
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
if|if
condition|(
name|ci
operator|.
name|getMimeType
argument_list|()
operator|.
name|equals
argument_list|(
literal|"text/plain"
argument_list|)
condition|)
block|{
name|this
operator|.
name|textContent
operator|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|ci
operator|.
name|getStream
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|ci
operator|.
name|getMimeType
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"image/"
argument_list|)
condition|)
block|{
name|this
operator|.
name|imageSrc
operator|=
name|rawURI
expr_stmt|;
block|}
name|this
operator|.
name|downloadHref
operator|=
name|rawURI
expr_stmt|;
name|this
operator|.
name|metadataHref
operator|=
name|UriBuilder
operator|.
name|fromPath
argument_list|(
literal|"/store/metadata/"
operator|+
name|localId
argument_list|)
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|getRdfMetadata
parameter_list|(
name|String
name|mediatype
parameter_list|)
throws|throws
name|UnsupportedEncodingException
block|{
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|serializer
operator|.
name|serialize
argument_list|(
name|out
argument_list|,
name|contentItem
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|mediatype
argument_list|)
expr_stmt|;
return|return
name|out
operator|.
name|toString
argument_list|(
literal|"utf-8"
argument_list|)
return|;
block|}
specifier|public
name|String
name|getRdfMetadata
parameter_list|()
throws|throws
name|UnsupportedEncodingException
block|{
return|return
name|getRdfMetadata
argument_list|(
name|serializationFormat
argument_list|)
return|;
block|}
specifier|public
name|ContentItem
name|getContentItem
parameter_list|()
block|{
return|return
name|contentItem
return|;
block|}
specifier|public
name|String
name|getLocalId
parameter_list|()
block|{
return|return
name|localId
return|;
block|}
specifier|public
name|String
name|getTextContent
parameter_list|()
block|{
return|return
name|textContent
return|;
block|}
specifier|public
name|URI
name|getImageSrc
parameter_list|()
block|{
return|return
name|imageSrc
return|;
block|}
specifier|public
name|URI
name|getDownloadHref
parameter_list|()
block|{
return|return
name|downloadHref
return|;
block|}
specifier|public
name|URI
name|getMetadataHref
parameter_list|()
block|{
return|return
name|metadataHref
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|EntityExtractionSummary
argument_list|>
name|getPersonOccurrences
parameter_list|()
throws|throws
name|ParseException
block|{
if|if
condition|(
name|people
operator|==
literal|null
condition|)
block|{
name|people
operator|=
name|getOccurrences
argument_list|(
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
argument_list|)
expr_stmt|;
block|}
return|return
name|people
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|EntityExtractionSummary
argument_list|>
name|getOrganizationOccurrences
parameter_list|()
throws|throws
name|ParseException
block|{
if|if
condition|(
name|organizations
operator|==
literal|null
condition|)
block|{
name|organizations
operator|=
name|getOccurrences
argument_list|(
name|OntologicalClasses
operator|.
name|DBPEDIA_ORGANISATION
argument_list|)
expr_stmt|;
block|}
return|return
name|organizations
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|EntityExtractionSummary
argument_list|>
name|getPlaceOccurrences
parameter_list|()
throws|throws
name|ParseException
block|{
if|if
condition|(
name|places
operator|==
literal|null
condition|)
block|{
name|places
operator|=
name|getOccurrences
argument_list|(
name|OntologicalClasses
operator|.
name|DBPEDIA_PLACE
argument_list|)
expr_stmt|;
block|}
return|return
name|places
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|EntityExtractionSummary
argument_list|>
name|getOccurrences
parameter_list|(
name|UriRef
name|type
parameter_list|)
throws|throws
name|ParseException
block|{
name|MGraph
name|graph
init|=
name|contentItem
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|String
name|q
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/> "
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/> "
operator|+
literal|"SELECT ?textAnnotation ?text ?entity ?entity_label ?confidence WHERE { "
operator|+
literal|"  ?textAnnotation a fise:TextAnnotation ."
operator|+
literal|"  ?textAnnotation dc:type %s ."
operator|+
literal|"  ?textAnnotation fise:selected-text ?text ."
operator|+
literal|" OPTIONAL {"
operator|+
literal|"   ?entityAnnotation dc:relation ?textAnnotation ."
operator|+
literal|"   ?entityAnnotation a fise:EntityAnnotation . "
operator|+
literal|"   ?entityAnnotation fise:entity-reference ?entity ."
operator|+
literal|"   ?entityAnnotation fise:entity-label ?entity_label ."
operator|+
literal|"   ?entityAnnotation fise:confidence ?confidence . }"
operator|+
literal|"} ORDER BY ?text "
decl_stmt|;
name|q
operator|=
name|String
operator|.
name|format
argument_list|(
name|q
argument_list|,
name|type
argument_list|)
expr_stmt|;
name|SelectQuery
name|query
init|=
operator|(
name|SelectQuery
operator|)
name|QueryParser
operator|.
name|getInstance
argument_list|()
operator|.
name|parse
argument_list|(
name|q
argument_list|)
decl_stmt|;
name|ResultSet
name|result
init|=
name|tcManager
operator|.
name|executeSparqlQuery
argument_list|(
name|query
argument_list|,
name|graph
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|EntityExtractionSummary
argument_list|>
name|occurenceMap
init|=
operator|new
name|TreeMap
argument_list|<
name|String
argument_list|,
name|EntityExtractionSummary
argument_list|>
argument_list|()
decl_stmt|;
name|LiteralFactory
name|lf
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
while|while
condition|(
name|result
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|SolutionMapping
name|mapping
init|=
name|result
operator|.
name|next
argument_list|()
decl_stmt|;
name|UriRef
name|textAnnotationUri
init|=
operator|(
name|UriRef
operator|)
name|mapping
operator|.
name|get
argument_list|(
literal|"textAnnotation"
argument_list|)
decl_stmt|;
if|if
condition|(
name|graph
operator|.
name|filter
argument_list|(
name|textAnnotationUri
argument_list|,
name|Properties
operator|.
name|DC_RELATION
argument_list|,
literal|null
argument_list|)
operator|.
name|hasNext
argument_list|()
condition|)
block|{
comment|// this is not the most specific occurrence of this name: skip
continue|continue;
block|}
comment|// TODO: collect the selected text and contexts of subsumed
comment|// annotations
name|TypedLiteral
name|textLiteral
init|=
operator|(
name|TypedLiteral
operator|)
name|mapping
operator|.
name|get
argument_list|(
literal|"text"
argument_list|)
decl_stmt|;
name|String
name|text
init|=
name|lf
operator|.
name|createObject
argument_list|(
name|String
operator|.
name|class
argument_list|,
name|textLiteral
argument_list|)
decl_stmt|;
name|EntityExtractionSummary
name|entity
init|=
name|occurenceMap
operator|.
name|get
argument_list|(
name|text
argument_list|)
decl_stmt|;
if|if
condition|(
name|entity
operator|==
literal|null
condition|)
block|{
name|entity
operator|=
operator|new
name|EntityExtractionSummary
argument_list|(
name|text
argument_list|,
name|type
argument_list|)
expr_stmt|;
name|occurenceMap
operator|.
name|put
argument_list|(
name|text
argument_list|,
name|entity
argument_list|)
expr_stmt|;
block|}
name|UriRef
name|entityUri
init|=
operator|(
name|UriRef
operator|)
name|mapping
operator|.
name|get
argument_list|(
literal|"entity"
argument_list|)
decl_stmt|;
if|if
condition|(
name|entityUri
operator|!=
literal|null
condition|)
block|{
name|String
name|label
init|=
name|lf
operator|.
name|createObject
argument_list|(
name|String
operator|.
name|class
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|mapping
operator|.
name|get
argument_list|(
literal|"entity_label"
argument_list|)
argument_list|)
decl_stmt|;
name|Double
name|confidence
init|=
name|lf
operator|.
name|createObject
argument_list|(
name|Double
operator|.
name|class
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|mapping
operator|.
name|get
argument_list|(
literal|"confidence"
argument_list|)
argument_list|)
decl_stmt|;
name|Graph
name|properties
init|=
operator|new
name|GraphNode
argument_list|(
name|entityUri
argument_list|,
name|remoteEntityCache
argument_list|)
operator|.
name|getNodeContext
argument_list|()
decl_stmt|;
name|entity
operator|.
name|addSuggestion
argument_list|(
name|entityUri
argument_list|,
name|label
argument_list|,
name|confidence
argument_list|,
name|properties
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|occurenceMap
operator|.
name|values
argument_list|()
return|;
block|}
specifier|public
specifier|static
class|class
name|EntityExtractionSummary
implements|implements
name|Comparable
argument_list|<
name|EntityExtractionSummary
argument_list|>
block|{
specifier|protected
specifier|final
name|String
name|name
decl_stmt|;
specifier|protected
specifier|final
name|UriRef
name|type
decl_stmt|;
specifier|protected
name|List
argument_list|<
name|EntitySuggestion
argument_list|>
name|suggestions
init|=
operator|new
name|ArrayList
argument_list|<
name|EntitySuggestion
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|List
argument_list|<
name|String
argument_list|>
name|mentions
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|EntityExtractionSummary
parameter_list|(
name|String
name|name
parameter_list|,
name|UriRef
name|type
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
name|mentions
operator|.
name|add
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addSuggestion
parameter_list|(
name|UriRef
name|uri
parameter_list|,
name|String
name|label
parameter_list|,
name|Double
name|confidence
parameter_list|,
name|TripleCollection
name|properties
parameter_list|)
block|{
name|EntitySuggestion
name|suggestion
init|=
operator|new
name|EntitySuggestion
argument_list|(
name|uri
argument_list|,
name|type
argument_list|,
name|label
argument_list|,
name|confidence
argument_list|,
name|properties
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|suggestions
operator|.
name|contains
argument_list|(
name|suggestion
argument_list|)
condition|)
block|{
name|suggestions
operator|.
name|add
argument_list|(
name|suggestion
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|suggestions
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
name|EntitySuggestion
name|bestGuess
init|=
name|getBestGuess
argument_list|()
decl_stmt|;
if|if
condition|(
name|bestGuess
operator|!=
literal|null
condition|)
block|{
return|return
name|bestGuess
operator|.
name|getLabel
argument_list|()
return|;
block|}
return|return
name|name
return|;
block|}
specifier|public
name|String
name|getUri
parameter_list|()
block|{
name|EntitySuggestion
name|bestGuess
init|=
name|getBestGuess
argument_list|()
decl_stmt|;
if|if
condition|(
name|bestGuess
operator|!=
literal|null
condition|)
block|{
return|return
name|bestGuess
operator|.
name|getUri
argument_list|()
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|getSummary
parameter_list|()
block|{
if|if
condition|(
name|suggestions
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|""
return|;
block|}
return|return
name|suggestions
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getSummary
argument_list|()
return|;
block|}
specifier|public
name|String
name|getThumbnailSrc
parameter_list|()
block|{
if|if
condition|(
name|suggestions
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|DEFAULT_THUMBNAILS
operator|.
name|get
argument_list|(
name|type
argument_list|)
return|;
block|}
return|return
name|suggestions
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getThumbnailSrc
argument_list|()
return|;
block|}
specifier|public
name|String
name|getMissingThumbnailSrc
parameter_list|()
block|{
return|return
name|DEFAULT_THUMBNAILS
operator|.
name|get
argument_list|(
name|type
argument_list|)
return|;
block|}
specifier|public
name|EntitySuggestion
name|getBestGuess
parameter_list|()
block|{
if|if
condition|(
name|suggestions
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
return|return
name|suggestions
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
specifier|public
name|List
argument_list|<
name|EntitySuggestion
argument_list|>
name|getSuggestions
parameter_list|()
block|{
return|return
name|suggestions
return|;
block|}
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getMentions
parameter_list|()
block|{
return|return
name|mentions
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|EntityExtractionSummary
name|o
parameter_list|)
block|{
return|return
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|EntitySuggestion
implements|implements
name|Comparable
argument_list|<
name|EntitySuggestion
argument_list|>
block|{
specifier|protected
specifier|final
name|UriRef
name|uri
decl_stmt|;
specifier|protected
specifier|final
name|UriRef
name|type
decl_stmt|;
specifier|protected
specifier|final
name|String
name|label
decl_stmt|;
specifier|protected
specifier|final
name|Double
name|confidence
decl_stmt|;
specifier|protected
name|TripleCollection
name|entityProperties
decl_stmt|;
specifier|public
name|EntitySuggestion
parameter_list|(
name|UriRef
name|uri
parameter_list|,
name|UriRef
name|type
parameter_list|,
name|String
name|label
parameter_list|,
name|Double
name|confidence
parameter_list|,
name|TripleCollection
name|entityProperties
parameter_list|)
block|{
name|this
operator|.
name|uri
operator|=
name|uri
expr_stmt|;
name|this
operator|.
name|label
operator|=
name|label
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
name|this
operator|.
name|confidence
operator|=
name|confidence
expr_stmt|;
name|this
operator|.
name|entityProperties
operator|=
name|entityProperties
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|EntitySuggestion
name|o
parameter_list|)
block|{
comment|// order suggestions by decreasing confidence
return|return
operator|-
name|confidence
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|confidence
argument_list|)
return|;
block|}
specifier|public
name|String
name|getUri
parameter_list|()
block|{
return|return
name|uri
operator|.
name|getUnicodeString
argument_list|()
return|;
block|}
specifier|public
name|Double
name|getConfidence
parameter_list|()
block|{
return|return
name|confidence
return|;
block|}
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|label
return|;
block|}
specifier|public
name|String
name|getThumbnailSrc
parameter_list|()
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|abstracts
init|=
name|entityProperties
operator|.
name|filter
argument_list|(
name|uri
argument_list|,
name|THUMBNAIL
argument_list|,
literal|null
argument_list|)
decl_stmt|;
while|while
condition|(
name|abstracts
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Resource
name|object
init|=
name|abstracts
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|object
operator|instanceof
name|UriRef
condition|)
block|{
return|return
operator|(
operator|(
name|UriRef
operator|)
name|object
operator|)
operator|.
name|getUnicodeString
argument_list|()
return|;
block|}
block|}
return|return
name|DEFAULT_THUMBNAILS
operator|.
name|get
argument_list|(
name|type
argument_list|)
return|;
block|}
specifier|public
name|String
name|getMissingThumbnailSrc
parameter_list|()
block|{
return|return
name|DEFAULT_THUMBNAILS
operator|.
name|get
argument_list|(
name|type
argument_list|)
return|;
block|}
specifier|public
name|String
name|getSummary
parameter_list|()
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|abstracts
init|=
name|entityProperties
operator|.
name|filter
argument_list|(
name|uri
argument_list|,
name|SUMMARY
argument_list|,
literal|null
argument_list|)
decl_stmt|;
while|while
condition|(
name|abstracts
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Resource
name|object
init|=
name|abstracts
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|object
operator|instanceof
name|PlainLiteral
condition|)
block|{
name|PlainLiteral
name|abstract_
init|=
operator|(
name|PlainLiteral
operator|)
name|object
decl_stmt|;
if|if
condition|(
name|abstract_
operator|.
name|getLanguage
argument_list|()
operator|.
name|equals
argument_list|(
operator|new
name|Language
argument_list|(
literal|"en"
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|abstract_
operator|.
name|getLexicalForm
argument_list|()
return|;
block|}
block|}
block|}
return|return
literal|""
return|;
block|}
comment|// consider entities with same URI as equal even if we have alternate
comment|// label values
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
specifier|final
name|int
name|prime
init|=
literal|31
decl_stmt|;
name|int
name|result
init|=
literal|1
decl_stmt|;
name|result
operator|=
name|prime
operator|*
name|result
operator|+
operator|(
operator|(
name|uri
operator|==
literal|null
operator|)
condition|?
literal|0
else|:
name|uri
operator|.
name|hashCode
argument_list|()
operator|)
expr_stmt|;
return|return
name|result
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|obj
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|obj
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getClass
argument_list|()
operator|!=
name|obj
operator|.
name|getClass
argument_list|()
condition|)
return|return
literal|false
return|;
name|EntitySuggestion
name|other
init|=
operator|(
name|EntitySuggestion
operator|)
name|obj
decl_stmt|;
if|if
condition|(
name|uri
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|uri
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
block|}
elseif|else
if|if
condition|(
operator|!
name|uri
operator|.
name|equals
argument_list|(
name|other
operator|.
name|uri
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
block|}
specifier|public
name|void
name|setRdfSerializationFormat
parameter_list|(
name|String
name|format
parameter_list|)
block|{
name|this
operator|.
name|serializationFormat
operator|=
name|format
expr_stmt|;
block|}
comment|/**      * @return an RDF/JSON descriptions of places for the word map widget      */
specifier|public
name|String
name|getPlacesAsJSON
parameter_list|()
throws|throws
name|ParseException
throws|,
name|UnsupportedEncodingException
block|{
name|MGraph
name|g
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
if|if
condition|(
name|remoteEntityCache
operator|!=
literal|null
condition|)
block|{
name|LiteralFactory
name|lf
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
for|for
control|(
name|EntityExtractionSummary
name|p
range|:
name|getPlaceOccurrences
argument_list|()
control|)
block|{
name|EntitySuggestion
name|bestGuess
init|=
name|p
operator|.
name|getBestGuess
argument_list|()
decl_stmt|;
if|if
condition|(
name|bestGuess
operator|==
literal|null
condition|)
block|{
continue|continue;
block|}
name|UriRef
name|uri
init|=
operator|new
name|UriRef
argument_list|(
name|bestGuess
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|latitudes
init|=
name|remoteEntityCache
operator|.
name|filter
argument_list|(
name|uri
argument_list|,
name|Properties
operator|.
name|GEO_LAT
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|latitudes
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|g
operator|.
name|add
argument_list|(
name|latitudes
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|longitutes
init|=
name|remoteEntityCache
operator|.
name|filter
argument_list|(
name|uri
argument_list|,
name|Properties
operator|.
name|GEO_LONG
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|longitutes
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|g
operator|.
name|add
argument_list|(
name|longitutes
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|uri
argument_list|,
name|Properties
operator|.
name|RDFS_LABEL
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|bestGuess
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|serializer
operator|.
name|serialize
argument_list|(
name|out
argument_list|,
name|g
argument_list|,
name|SupportedFormat
operator|.
name|RDF_JSON
argument_list|)
expr_stmt|;
return|return
name|out
operator|.
name|toString
argument_list|(
literal|"utf-8"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

