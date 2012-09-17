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
name|dbpspotlight
operator|.
name|utils
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PARAM_URL_KEY
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PROPERTY_CONTEXTUAL_SCORE
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PROPERTY_FINAL_SCORE
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PROPERTY_PERCENTAGE_OF_SECOND_RANK
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PROPERTY_PRIOR_SCORE
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PROPERTY_SIMILARITY_SCORE
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PROPERTY_SUPPORT
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|SUPPORTED_LANGUAGES
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|SUPPORTED_MIMTYPES
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|TEXT_PLAIN_MIMETYPE
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
name|Properties
operator|.
name|DC_RELATION
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
name|Properties
operator|.
name|DC_TYPE
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
name|Properties
operator|.
name|ENHANCER_CONFIDENCE
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
name|Properties
operator|.
name|ENHANCER_END
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
name|Properties
operator|.
name|ENHANCER_ENTITY_LABEL
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
name|Properties
operator|.
name|ENHANCER_ENTITY_REFERENCE
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
name|Properties
operator|.
name|ENHANCER_ENTITY_TYPE
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
name|Properties
operator|.
name|ENHANCER_SELECTED_TEXT
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
name|Properties
operator|.
name|ENHANCER_SELECTION_CONTEXT
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
name|Properties
operator|.
name|ENHANCER_START
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
name|net
operator|.
name|MalformedURLException
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
name|Dictionary
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
name|TripleImpl
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
name|engines
operator|.
name|dbpspotlight
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
name|enhancer
operator|.
name|engines
operator|.
name|dbpspotlight
operator|.
name|model
operator|.
name|Annotation
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|model
operator|.
name|CandidateResource
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|model
operator|.
name|SurfaceForm
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
name|Blob
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
name|ContentItemHelper
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
comment|/**  * Shared utilities for the Spotlight Enhancement Engines.  */
end_comment

begin_class
specifier|public
class|class
name|SpotlightEngineUtils
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SpotlightEngineUtils
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|int
name|DEFAULT_SELECTION_CONTEXT_PREFIX_SUFFIX_SIZE
init|=
literal|50
decl_stmt|;
specifier|public
specifier|static
name|boolean
name|canProcess
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
block|{
if|if
condition|(
name|ContentItemHelper
operator|.
name|getBlob
argument_list|(
name|ci
argument_list|,
name|SUPPORTED_MIMTYPES
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|language
init|=
name|EnhancementEngineHelper
operator|.
name|getLanguage
argument_list|(
name|ci
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|SUPPORTED_LANGUAGES
operator|.
name|contains
argument_list|(
name|language
argument_list|)
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"DBpedia Spotlight can not process ContentItem {} "
operator|+
literal|"because language {} is not supported (supported: {})"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|ci
operator|.
name|getUri
argument_list|()
block|,
name|language
block|,
name|SUPPORTED_LANGUAGES
block|}
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
else|else
block|{
return|return
literal|true
return|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"DBpedia Spotlight can not process ContentItem {} "
operator|+
literal|"because it does not have 'plain/text' content"
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
specifier|public
specifier|static
name|Language
name|getContentLanguage
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
block|{
name|String
name|lang
init|=
name|EnhancementEngineHelper
operator|.
name|getLanguage
argument_list|(
name|ci
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|SUPPORTED_LANGUAGES
operator|.
name|contains
argument_list|(
name|lang
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Langage '"
operator|+
name|lang
operator|+
literal|"' as annotated for ContentItem "
operator|+
name|ci
operator|.
name|getUri
argument_list|()
operator|+
literal|" is not supported by this Engine: "
operator|+
literal|"This is also checked in the canEnhance method! -> This "
operator|+
literal|"indicated an Bug in the implementation of the "
operator|+
literal|"EnhancementJobManager!"
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|lang
operator|==
literal|null
operator|||
name|lang
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
operator|new
name|Language
argument_list|(
name|lang
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
name|String
name|getPlainContent
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|Blob
argument_list|>
name|contentPart
init|=
name|ContentItemHelper
operator|.
name|getBlob
argument_list|(
name|ci
argument_list|,
name|SUPPORTED_MIMTYPES
argument_list|)
decl_stmt|;
if|if
condition|(
name|contentPart
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"No ContentPart with Mimetype '"
operator|+
name|TEXT_PLAIN_MIMETYPE
operator|+
literal|"' found for ContentItem "
operator|+
name|ci
operator|.
name|getUri
argument_list|()
operator|+
literal|": This is also checked in the canEnhance method! -> This "
operator|+
literal|"indicated an Bug in the implementation of the "
operator|+
literal|"EnhancementJobManager!"
argument_list|)
throw|;
block|}
try|try
block|{
return|return
name|ContentItemHelper
operator|.
name|getText
argument_list|(
name|contentPart
operator|.
name|getValue
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"Unable to read plain text content form"
operator|+
literal|"contentpart "
operator|+
name|contentPart
operator|.
name|getKey
argument_list|()
operator|+
literal|" of ContentItem "
operator|+
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
throw|;
block|}
block|}
comment|/** 	 * Parses the URL from the {@link Constants#PARAM_URL_KEY} 	 * @param properties the configuration of the engine 	 * @return the URL of the service 	 * @throws ConfigurationException if the configuration is missing, 	 * empty or not a valid URL 	 */
specifier|public
specifier|static
name|URL
name|parseSpotlightServiceURL
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Object
name|value
init|=
name|properties
operator|.
name|get
argument_list|(
name|PARAM_URL_KEY
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PARAM_URL_KEY
argument_list|,
literal|"The URL with the DBpedia "
operator|+
literal|"Spotlight Annotate RESTful Service MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
else|else
block|{
try|try
block|{
return|return
operator|new
name|URL
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PARAM_URL_KEY
argument_list|,
literal|"The parsed URL for the "
operator|+
literal|"DBpedia Spotlight Annotate RESTful Service is illegal formatted!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
comment|/**      * Extracts the selection context based on the content, selection and      * the start char offset of the selection      * @param content the content      * @param selection the selected text      * @param selectionStartPos the start char position of the selection      * @return the context      */
specifier|public
specifier|static
name|String
name|getSelectionContext
parameter_list|(
name|String
name|content
parameter_list|,
name|String
name|selection
parameter_list|,
name|int
name|selectionStartPos
parameter_list|)
block|{
comment|//extract the selection context
name|int
name|beginPos
decl_stmt|;
if|if
condition|(
name|selectionStartPos
operator|<=
name|DEFAULT_SELECTION_CONTEXT_PREFIX_SUFFIX_SIZE
condition|)
block|{
name|beginPos
operator|=
literal|0
expr_stmt|;
block|}
else|else
block|{
name|int
name|start
init|=
name|selectionStartPos
operator|-
name|DEFAULT_SELECTION_CONTEXT_PREFIX_SUFFIX_SIZE
decl_stmt|;
name|beginPos
operator|=
name|content
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|,
name|start
argument_list|)
expr_stmt|;
if|if
condition|(
name|beginPos
operator|<
literal|0
operator|||
name|beginPos
operator|>=
name|selectionStartPos
condition|)
block|{
comment|//no words
name|beginPos
operator|=
name|start
expr_stmt|;
comment|//begin within a word
block|}
block|}
name|int
name|endPos
decl_stmt|;
if|if
condition|(
name|selectionStartPos
operator|+
name|selection
operator|.
name|length
argument_list|()
operator|+
name|DEFAULT_SELECTION_CONTEXT_PREFIX_SUFFIX_SIZE
operator|>=
name|content
operator|.
name|length
argument_list|()
condition|)
block|{
name|endPos
operator|=
name|content
operator|.
name|length
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|int
name|start
init|=
name|selectionStartPos
operator|+
name|selection
operator|.
name|length
argument_list|()
operator|+
name|DEFAULT_SELECTION_CONTEXT_PREFIX_SUFFIX_SIZE
decl_stmt|;
name|endPos
operator|=
name|content
operator|.
name|lastIndexOf
argument_list|(
literal|' '
argument_list|,
name|start
argument_list|)
expr_stmt|;
if|if
condition|(
name|endPos
operator|<=
name|selectionStartPos
operator|+
name|selection
operator|.
name|length
argument_list|()
condition|)
block|{
name|endPos
operator|=
name|start
expr_stmt|;
comment|//end within a word;
block|}
block|}
return|return
name|content
operator|.
name|substring
argument_list|(
name|beginPos
argument_list|,
name|endPos
argument_list|)
return|;
block|}
comment|/**      * Creates a fise:TextAnnotation for the parsed parameters and      * adds it the the {@link ContentItem#getMetadata()}.<p>      * This method assumes a write lock on the parsed content item.      * @param occ the SurfaceForm      * @param engine the Engine      * @param ci the ContentITem      * @param content the content       * @param lang the language of the content or<code>null</code>      * @return the URI of the created fise:TextAnnotation      */
specifier|public
specifier|static
name|UriRef
name|createTextEnhancement
parameter_list|(
name|SurfaceForm
name|occ
parameter_list|,
name|EnhancementEngine
name|engine
parameter_list|,
name|ContentItem
name|ci
parameter_list|,
name|String
name|content
parameter_list|,
name|Language
name|lang
parameter_list|)
block|{
name|MGraph
name|model
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|UriRef
name|textAnnotation
init|=
name|EnhancementEngineHelper
operator|.
name|createTextEnhancement
argument_list|(
name|ci
argument_list|,
name|engine
argument_list|)
decl_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_SELECTED_TEXT
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|occ
operator|.
name|name
argument_list|,
name|lang
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_START
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|occ
operator|.
name|offset
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_END
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|occ
operator|.
name|offset
operator|+
name|occ
operator|.
name|name
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|occ
operator|.
name|type
operator|!=
literal|null
operator|&&
operator|!
name|occ
operator|.
name|type
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|DC_TYPE
argument_list|,
operator|new
name|UriRef
argument_list|(
name|occ
operator|.
name|type
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_SELECTION_CONTEXT
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|getSelectionContext
argument_list|(
name|content
argument_list|,
name|occ
operator|.
name|name
argument_list|,
name|occ
operator|.
name|offset
argument_list|)
argument_list|,
name|lang
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|textAnnotation
return|;
block|}
comment|/** 	 * Creates a fise:EntityAnnotation for the parsed parameters and      * adds it the the {@link ContentItem#getMetadata()}.<p>      * This method assumes a write lock on the parsed content item. 	 * @param resource the candidate resource 	 * @param engine the engine 	 * @param ci the content item 	 * @param textAnnotation the fise:TextAnnotation to dc:relate the 	 * created fise:EntityAnnotation 	 * @return the URI of the created fise:TextAnnotation 	 */
specifier|public
specifier|static
name|UriRef
name|createEntityAnnotation
parameter_list|(
name|CandidateResource
name|resource
parameter_list|,
name|EnhancementEngine
name|engine
parameter_list|,
name|ContentItem
name|ci
parameter_list|,
name|UriRef
name|textAnnotation
parameter_list|)
block|{
name|UriRef
name|entityAnnotation
init|=
name|EnhancementEngineHelper
operator|.
name|createEntityEnhancement
argument_list|(
name|ci
argument_list|,
name|engine
argument_list|)
decl_stmt|;
name|MGraph
name|model
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|Literal
name|label
init|=
operator|new
name|PlainLiteralImpl
argument_list|(
name|resource
operator|.
name|label
argument_list|,
operator|new
name|Language
argument_list|(
literal|"en"
argument_list|)
argument_list|)
decl_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|DC_RELATION
argument_list|,
name|textAnnotation
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|ENHANCER_ENTITY_LABEL
argument_list|,
name|label
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|ENHANCER_ENTITY_REFERENCE
argument_list|,
operator|new
name|UriRef
argument_list|(
name|resource
operator|.
name|uri
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|PROPERTY_CONTEXTUAL_SCORE
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|resource
operator|.
name|contextualScore
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|PROPERTY_PERCENTAGE_OF_SECOND_RANK
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|resource
operator|.
name|percentageOfSecondRank
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|PROPERTY_SUPPORT
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|resource
operator|.
name|support
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|PROPERTY_PRIOR_SCORE
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|resource
operator|.
name|priorScore
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|PROPERTY_FINAL_SCORE
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|resource
operator|.
name|finalScore
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|entityAnnotation
return|;
block|}
comment|/** 	 * Creates a fise:EntityAnnotation for the parsed parameter and      * adds it the the {@link ContentItem#getMetadata()}.<p>      * This method assumes a write lock on the parsed content item. 	 * @param annotation the Annotation 	 * @param engine the engine 	 * @param ci the language 	 * @param textAnnotation the TextAnnotation the created 	 * EntityAnnotation links by using dc:relation 	 * @param language the language of the label of the referenced 	 * Entity (or<code>null</code> if none). 	 */
specifier|public
specifier|static
name|void
name|createEntityAnnotation
parameter_list|(
name|Annotation
name|annotation
parameter_list|,
name|EnhancementEngine
name|engine
parameter_list|,
name|ContentItem
name|ci
parameter_list|,
name|UriRef
name|textAnnotation
parameter_list|,
name|Language
name|language
parameter_list|)
block|{
name|MGraph
name|model
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|UriRef
name|entityAnnotation
init|=
name|EnhancementEngineHelper
operator|.
name|createEntityEnhancement
argument_list|(
name|ci
argument_list|,
name|engine
argument_list|)
decl_stmt|;
name|Literal
name|label
init|=
operator|new
name|PlainLiteralImpl
argument_list|(
name|annotation
operator|.
name|surfaceForm
operator|.
name|name
argument_list|,
name|language
argument_list|)
decl_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|DC_RELATION
argument_list|,
name|textAnnotation
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|ENHANCER_ENTITY_LABEL
argument_list|,
name|label
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|ENHANCER_ENTITY_REFERENCE
argument_list|,
name|annotation
operator|.
name|uri
argument_list|)
argument_list|)
expr_stmt|;
comment|//set the fise:entity-type
for|for
control|(
name|String
name|type
range|:
name|annotation
operator|.
name|getTypeNames
argument_list|()
control|)
block|{
name|UriRef
name|annotationType
init|=
operator|new
name|UriRef
argument_list|(
name|type
argument_list|)
decl_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|ENHANCER_ENTITY_TYPE
argument_list|,
name|annotationType
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//TODO (rwesten): Pleas check: I use the similarityScore as fise:confidence value
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|ENHANCER_CONFIDENCE
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|annotation
operator|.
name|similarityScore
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//add spotlight specific information
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|PROPERTY_PERCENTAGE_OF_SECOND_RANK
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|annotation
operator|.
name|percentageOfSecondRank
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|PROPERTY_SUPPORT
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|annotation
operator|.
name|support
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|PROPERTY_SIMILARITY_SCORE
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|annotation
operator|.
name|similarityScore
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

