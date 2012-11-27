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
name|nlp
operator|.
name|utils
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
name|singleton
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
name|UriRef
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
name|nlp
operator|.
name|NlpProcessingRole
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
name|nlp
operator|.
name|NlpServiceProperties
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
name|nlp
operator|.
name|model
operator|.
name|AnalysedText
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
name|nlp
operator|.
name|model
operator|.
name|AnalysedTextFactory
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
name|nlp
operator|.
name|model
operator|.
name|AnalysedTextUtils
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
comment|/**  * Utility class for {@link EnhancementEngine} implementations that  * do use the {@link AnalysedText} content part  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|NlpEngineHelper
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
name|NlpEngineHelper
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|NlpEngineHelper
parameter_list|()
block|{}
comment|/**      * Getter for the AnalysedText for a ContentItem      * @param engine the EnhancementEngine calling this method (used for logging)      * @param ci the ContentItem      * @param exception<code>false</code> id used in {@link #canEnhance(ContentItem)}      * and<code>true</code> when called from {@link #computeEnhancements(ContentItem)}      * @return the AnalysedText or<code>null</code> if not found.      * @throws IllegalStateException if exception is<code>true</code> and the      * {@link AnalysedText} could not be retrieved from the parsed {@link ContentItem}.      */
specifier|public
specifier|static
name|AnalysedText
name|getAnalysedText
parameter_list|(
name|EnhancementEngine
name|engine
parameter_list|,
name|ContentItem
name|ci
parameter_list|,
name|boolean
name|exception
parameter_list|)
block|{
name|AnalysedText
name|at
decl_stmt|;
try|try
block|{
name|at
operator|=
name|AnalysedTextUtils
operator|.
name|getAnalysedText
argument_list|(
name|ci
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to retrieve AnalysedText for ContentItem "
operator|+
name|ci
operator|+
literal|"because of an "
operator|+
name|e
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|" with message "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|at
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|at
operator|!=
literal|null
condition|)
block|{
return|return
name|at
return|;
block|}
if|if
condition|(
name|exception
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to retrieve AnalysedText from ContentItem "
operator|+
name|ci
operator|+
literal|". As this is also checked in canEnhancer this may indicate an Bug in the "
operator|+
literal|"used EnhancementJobManager!"
argument_list|)
throw|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"The Enhancement Engine '{} (impl: {})' CAN NOT enhance "
operator|+
literal|"ContentItem {} because the AnalysedText ContentPart is "
operator|+
literal|"missing. Users might want to add an EnhancementEngine that "
operator|+
literal|"creates the AnalysedText ContentPart such as the "
operator|+
literal|"POSTaggingEngine (o.a.stanbol.enhancer.engines.opennlp.pos)!"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|engine
operator|.
name|getName
argument_list|()
block|,
name|engine
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
block|,
name|ci
block|}
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Getter for the language of the content      * @param ci the ContentItem      * @param exception<code>false</code> id used in {@link #canEnhance(ContentItem)}      * and<code>true</code> when called from {@link #computeEnhancements(ContentItem)}      * @return the AnalysedText or<code>null</code> if not found.      * @throws IllegalStateException if exception is<code>true</code> and the      * language could not be retrieved from the parsed {@link ContentItem}.      */
specifier|public
specifier|static
name|String
name|getLanguage
parameter_list|(
name|EnhancementEngine
name|engine
parameter_list|,
name|ContentItem
name|ci
parameter_list|,
name|boolean
name|exception
parameter_list|)
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
name|language
operator|!=
literal|null
condition|)
block|{
return|return
name|language
return|;
block|}
if|if
condition|(
name|exception
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to retrieve the detected language for ContentItem "
operator|+
name|ci
operator|+
literal|". As this is also checked in canEnhancer this may indicate an Bug in the "
operator|+
literal|"used EnhancementJobManager!"
argument_list|)
throw|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"The Enhancement Engine '{} (impl: {})' CAN NOT enhance "
operator|+
literal|"ContentItem {} because the langauge of "
operator|+
literal|"this ContentItem is unknown. Users might want to add a "
operator|+
literal|"Language Identification EnhancementEngine to the current "
operator|+
literal|"EnhancementChain!"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|engine
operator|.
name|getName
argument_list|()
block|,
name|engine
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
block|,
name|ci
block|}
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Used in {@link #canEnhance(ContentItem)} to check if a {@link ContentItem}      * should be processed based on the language configuration of this engine.      * @param engine the {@link EnhancementEngine} calling this method      * @param languageConfiguration the language configuration      * @param language the language      * @param exception<code>false</code> id used in {@link #canEnhance(ContentItem)}      * and<code>true</code> when called from {@link #computeEnhancements(ContentItem)}      * @return the state      * @throws IllegalStateException if exception is<code>true</code> and the      * language is not configured as beeing processed.      */
specifier|public
specifier|static
name|boolean
name|isLangaugeConfigured
parameter_list|(
name|EnhancementEngine
name|engine
parameter_list|,
name|LanguageConfiguration
name|languageConfiguration
parameter_list|,
name|String
name|language
parameter_list|,
name|boolean
name|exception
parameter_list|)
block|{
name|boolean
name|state
init|=
name|languageConfiguration
operator|.
name|isLanguage
argument_list|(
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|state
operator|&&
name|exception
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Language "
operator|+
name|language
operator|+
literal|" is not included "
operator|+
literal|"by the LanguageConfiguration of this engine (name "
operator|+
name|engine
operator|.
name|getName
argument_list|()
operator|+
literal|"). As this is also checked in canEnhancer this may indicate an Bug in the "
operator|+
literal|"used EnhancementJobManager!"
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|state
return|;
block|}
block|}
comment|/**      * Retrieves - or if not present - creates the {@link AnalysedText} content      * part for the parsed {@link ContentItem}. If the {@link Blob} with the      * mime type '<code>text/plain</code>' is present this method      * throws an {@link IllegalStateException} (this method internally uses      * {@link #getPlainText(EnhancementEngine, ContentItem, boolean)} with      *<code>true</code> as third parameters. Users of this method should call      * this method with<code>false</code> as third parameter in their       * {@link EnhancementEngine#canEnhance(ContentItem)} implementation.<p>      *<i>NOTE:</i> This method is intended for Engines that want to create an      * empty {@link AnalysedText} content part. Engines that assume that this      * content part is already present (e.g. if the consume already existing      * annotations) should use the       * {@link #getAnalysedText(EnhancementEngine, ContentItem, boolean)}      * method instead.      * @param engine the EnhancementEngine calling this method (used for logging)      * @param analysedTextFactory the {@link AnalysedTextFactory} used to create      * the {@link AnalysedText} instance (if not present).      * @param ci the {@link ContentItem}      * @return the AnalysedText      * @throws EngineException on any exception while accessing the       * '<code>text/plain</code>' Blob      * @throws IllegalStateException if no '<code>text/plain</code>' Blob is      * present as content part of the parsed {@link ContentItem} or the parsed      * {@link AnalysedTextFactory} is<code>null</code>.<i>NOTE</i> that       * {@link IllegalStateException} are only thrown if the {@link AnalysedText}      * ContentPart is not yet present in the parsed {@link ContentItem}      */
specifier|public
specifier|static
name|AnalysedText
name|initAnalysedText
parameter_list|(
name|EnhancementEngine
name|engine
parameter_list|,
name|AnalysedTextFactory
name|analysedTextFactory
parameter_list|,
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
name|AnalysedText
name|at
init|=
name|AnalysedTextUtils
operator|.
name|getAnalysedText
argument_list|(
name|ci
argument_list|)
decl_stmt|;
if|if
condition|(
name|at
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|analysedTextFactory
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to initialise AnalysedText"
operator|+
literal|"ContentPart because the parsed AnalysedTextFactory is NULL"
argument_list|)
throw|;
block|}
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|Blob
argument_list|>
name|textBlob
init|=
name|getPlainText
argument_list|(
name|engine
argument_list|,
name|ci
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|" ... create new AnalysedText instance for Engine {}"
argument_list|,
name|engine
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|at
operator|=
name|analysedTextFactory
operator|.
name|createAnalysedText
argument_list|(
name|ci
argument_list|,
name|textBlob
operator|.
name|getValue
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
name|EngineException
argument_list|(
literal|"Unable to create AnalysetText instance for Blob "
operator|+
name|textBlob
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
operator|+
literal|"!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" ... use existing AnalysedText instance for Engine {}"
argument_list|,
name|engine
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|at
return|;
block|}
comment|/**      * Getter for the language of the content      * @param ci the ContentItem      * @param exception<code>false</code> id used in {@link #canEnhance(ContentItem)}      * and<code>true</code> when called from {@link #computeEnhancements(ContentItem)}      * @return the AnalysedText or<code>null</code> if not found.      * @throws IllegalStateException if exception is<code>true</code> and the      * language could not be retrieved from the parsed {@link ContentItem}.      */
specifier|public
specifier|static
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|Blob
argument_list|>
name|getPlainText
parameter_list|(
name|EnhancementEngine
name|engine
parameter_list|,
name|ContentItem
name|ci
parameter_list|,
name|boolean
name|exception
parameter_list|)
block|{
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|Blob
argument_list|>
name|textBlob
init|=
name|ContentItemHelper
operator|.
name|getBlob
argument_list|(
name|ci
argument_list|,
name|singleton
argument_list|(
literal|"text/plain"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|textBlob
operator|!=
literal|null
condition|)
block|{
return|return
name|textBlob
return|;
block|}
if|if
condition|(
name|exception
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to retrieve 'text/plain' ContentPart for ContentItem "
operator|+
name|ci
operator|+
literal|". As this is also checked in canEnhancer this may indicate an Bug in the "
operator|+
literal|"used EnhancementJobManager!"
argument_list|)
throw|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"The Enhancement Engine '{} (impl: {})' CAN NOT enhance "
operator|+
literal|"ContentItem {} because no 'text/plain' ContentPart is "
operator|+
literal|"present in this ContentItem. Users that need to enhance "
operator|+
literal|"non-plain-text Content need to add an EnhancementEngine "
operator|+
literal|"that supports the conversion of '{}' files to plain text "
operator|+
literal|"to the current EnhancementChain!"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|engine
operator|.
name|getName
argument_list|()
block|,
name|engine
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
block|,
name|ci
block|,
name|ci
operator|.
name|getMimeType
argument_list|()
block|}
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Parsed the {@link NlpProcessingRole} typically provided by the       * {@link ServiceProperties#getServiceProperties()} provided by some      * EnhancementEngines.<p>      * This supports both {@link NlpProcessingRole} as well as String values      * using the {@link NlpProcessingRole#name()}.      * @param properties the properties (typically retrieved from the      * {@link ServiceProperties#getServiceProperties()} method)      * @return the NLP processing role or<code>null</code> if not present OR      * an error while parsing.      */
specifier|public
specifier|static
name|NlpProcessingRole
name|getNlpProcessingRole
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
parameter_list|)
block|{
name|Object
name|value
init|=
name|properties
operator|.
name|get
argument_list|(
name|NlpServiceProperties
operator|.
name|ENHANCEMENT_ENGINE_NLP_ROLE
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|NlpProcessingRole
condition|)
block|{
return|return
operator|(
name|NlpProcessingRole
operator|)
name|value
return|;
block|}
elseif|else
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
try|try
block|{
return|return
name|NlpProcessingRole
operator|.
name|valueOf
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
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unknown NLP processing role {} -> return null"
argument_list|,
name|value
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit
