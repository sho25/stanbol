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
name|langid
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
name|DC_LANGUAGE
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
name|NIE_PLAINTEXTCONTENT
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
name|Properties
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
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|InvalidContentException
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
name|ServiceProperties
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
name|knallgrau
operator|.
name|utils
operator|.
name|textcat
operator|.
name|TextCategorizer
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
comment|/**  * {@link LangIdEnhancementEngine} provides functionality to enhance document  * with their language.  *  * @author Joerg Steffen, DFKI  * @version $Id$  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|LangIdEnhancementEngine
implements|implements
name|EnhancementEngine
implements|,
name|ServiceProperties
block|{
comment|/**      * The default value for the Execution of this Engine. Currently set to      * {@link ServiceProperties#ORDERING_PRE_PROCESSING}      */
specifier|public
specifier|static
specifier|final
name|Integer
name|defaultOrder
init|=
name|ORDERING_PRE_PROCESSING
operator|-
literal|2
decl_stmt|;
comment|/**      * This contains the only MIME type directly supported by this enhancement engine.      */
specifier|private
specifier|static
specifier|final
name|String
name|TEXT_PLAIN_MIMETYPE
init|=
literal|"text/plain"
decl_stmt|;
comment|/**      * This contains the logger.      */
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
name|LangIdEnhancementEngine
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|LANGUAGE_MAP_DEFAULT
init|=
literal|"languageLabelsMap.txt"
decl_stmt|;
specifier|private
name|Properties
name|languageLabelsMap
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
comment|/**      * This contains the language identifier.      */
specifier|private
name|TextCategorizer
name|languageIdentifier
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|int
name|PROBE_LENGTH_DEFAULT
init|=
literal|400
decl_stmt|;
comment|/**      * a configurable value of the text segment length to check      */
annotation|@
name|Property
specifier|public
specifier|static
specifier|final
name|String
name|PROBE_LENGTH_PROP
init|=
literal|"org.apache.stanbol.enhancer.engines.langid.probe-length"
decl_stmt|;
comment|/**      * this allows to specify the path to a configuration file that specifies the language models and how they map to language labels      */
annotation|@
name|Property
specifier|public
specifier|static
specifier|final
name|String
name|MODEL_CONFIGURATION_FILE_PROP
init|=
literal|"org.apache.stanbol.enhancer.engines.langid.model-configuration-file"
decl_stmt|;
comment|/**      * How much text should be used for testing: If the value is 0 or smaller, the complete text will be used. Otherwise a text probe of the given length is taken from the middle of the text. The default length is 400 characters.      */
specifier|private
name|int
name|probeLength
init|=
name|PROBE_LENGTH_DEFAULT
decl_stmt|;
comment|/**      * The activate method.      *      * @param ce the {@link ComponentContext}      */
specifier|protected
name|void
name|activate
parameter_list|(
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|ComponentContext
name|ce
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|confFile
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|ce
operator|!=
literal|null
condition|)
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|properties
init|=
name|ce
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|String
name|lengthVal
init|=
name|properties
operator|.
name|get
argument_list|(
name|PROBE_LENGTH_PROP
argument_list|)
decl_stmt|;
name|probeLength
operator|=
name|lengthVal
operator|==
literal|null
condition|?
name|PROBE_LENGTH_DEFAULT
else|:
name|Integer
operator|.
name|parseInt
argument_list|(
name|lengthVal
argument_list|)
expr_stmt|;
name|confFile
operator|=
name|properties
operator|.
name|get
argument_list|(
name|MODEL_CONFIGURATION_FILE_PROP
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|confFile
operator|!=
literal|null
condition|)
block|{
name|languageIdentifier
operator|=
operator|new
name|TextCategorizer
argument_list|(
name|confFile
argument_list|)
expr_stmt|;
if|if
condition|(
name|languageIdentifier
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Could not initialize from configuration file: "
operator|+
name|confFile
argument_list|)
throw|;
block|}
block|}
else|else
block|{
name|languageIdentifier
operator|=
operator|new
name|TextCategorizer
argument_list|()
expr_stmt|;
name|InputStream
name|in
init|=
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|LANGUAGE_MAP_DEFAULT
argument_list|)
decl_stmt|;
if|if
condition|(
name|in
operator|!=
literal|null
condition|)
block|{
name|languageLabelsMap
operator|.
name|load
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
block|}
comment|/**      * The deactivate method.      *      * @param ce the {@link ComponentContext}      */
specifier|protected
name|void
name|deactivate
parameter_list|(
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|ComponentContext
name|ce
parameter_list|)
block|{
name|languageIdentifier
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|int
name|canEnhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
name|String
name|mimeType
init|=
name|ci
operator|.
name|getMimeType
argument_list|()
operator|.
name|split
argument_list|(
literal|";"
argument_list|,
literal|2
argument_list|)
index|[
literal|0
index|]
decl_stmt|;
if|if
condition|(
name|TEXT_PLAIN_MIMETYPE
operator|.
name|equalsIgnoreCase
argument_list|(
name|mimeType
argument_list|)
condition|)
block|{
return|return
name|ENHANCE_SYNCHRONOUS
return|;
block|}
comment|// TODO: check whether there is the graph contains the text
name|UriRef
name|subj
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
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
operator|.
name|filter
argument_list|(
name|subj
argument_list|,
name|NIE_PLAINTEXTCONTENT
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
return|return
name|ENHANCE_SYNCHRONOUS
return|;
block|}
return|return
name|CANNOT_ENHANCE
return|;
block|}
specifier|public
name|void
name|computeEnhancements
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
name|String
name|text
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|TEXT_PLAIN_MIMETYPE
operator|.
name|equals
argument_list|(
name|ci
operator|.
name|getMimeType
argument_list|()
argument_list|)
condition|)
block|{
try|try
block|{
name|text
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
throw|throw
operator|new
name|InvalidContentException
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
else|else
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
operator|.
name|filter
argument_list|(
operator|new
name|UriRef
argument_list|(
name|ci
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
name|NIE_PLAINTEXTCONTENT
argument_list|,
literal|null
argument_list|)
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|text
operator|+=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|text
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"no text found"
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// truncate text to some piece from the middle if probeLength> 0
name|int
name|checkLength
init|=
name|probeLength
decl_stmt|;
if|if
condition|(
name|checkLength
operator|>
literal|0
operator|&&
name|text
operator|.
name|length
argument_list|()
operator|>
name|checkLength
condition|)
block|{
name|text
operator|=
name|text
operator|.
name|substring
argument_list|(
name|text
operator|.
name|length
argument_list|()
operator|/
literal|2
operator|-
name|checkLength
operator|/
literal|2
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|/
literal|2
operator|+
name|checkLength
operator|/
literal|2
argument_list|)
expr_stmt|;
block|}
name|String
name|language
init|=
name|languageIdentifier
operator|.
name|categorize
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|language
operator|=
name|languageLabelsMap
operator|.
name|getProperty
argument_list|(
name|language
argument_list|,
name|language
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"language identified as "
operator|+
name|language
argument_list|)
expr_stmt|;
comment|// add language to metadata
name|MGraph
name|g
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|UriRef
name|textEnhancement
init|=
name|EnhancementEngineHelper
operator|.
name|createTextEnhancement
argument_list|(
name|ci
argument_list|,
name|this
argument_list|)
decl_stmt|;
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textEnhancement
argument_list|,
name|DC_LANGUAGE
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|language
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|getProbeLength
parameter_list|()
block|{
return|return
name|probeLength
return|;
block|}
specifier|public
name|void
name|setProbeLength
parameter_list|(
name|int
name|probeLength
parameter_list|)
block|{
name|this
operator|.
name|probeLength
operator|=
name|probeLength
expr_stmt|;
block|}
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getServiceProperties
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|Collections
operator|.
name|singletonMap
argument_list|(
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|,
operator|(
name|Object
operator|)
name|defaultOrder
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

