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
name|entitylinking
operator|.
name|config
package|;
end_package

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationTargetException
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
name|EnumSet
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|opennlp
operator|.
name|TextAnalyzer
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
name|entitylinking
operator|.
name|engine
operator|.
name|EntityLinkingEngine
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
name|pos
operator|.
name|LexicalCategory
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
name|pos
operator|.
name|Pos
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
name|utils
operator|.
name|LanguageConfiguration
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

begin_class
specifier|public
class|class
name|TextProcessingConfig
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
name|TextProcessingConfig
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * If enabled only {@link Pos#ProperNoun}, {@link Pos#Foreign} and {@link Pos#Acronym} are Matched. If      * deactivated all Tokens with the category {@link LexicalCategory#Noun} and       * {@link LexicalCategory#Residual} are considered for matching.<p>      * This property allows an easy configuration of the matching that is sufficient for most usage scenarios.      * Users that need to have more control can configure language specific mappings by using      * {@link #PARAM_LEXICAL_CATEGORIES}, {@link #PARAM_POS_TYPES}, {@link #PARAM_POS_TAG} and      * {@link #PARAM_POS_PROBABILITY} in combination with the {@link #PROCESSED_LANGUAGES}      * configuration.<p>      * The {@link #DEFAULT_PROCESS_ONLY_PROPER_NOUNS_STATE default} if this is<code>false</code>      */
specifier|public
specifier|static
specifier|final
name|String
name|PROCESS_ONLY_PROPER_NOUNS_STATE
init|=
literal|"enhancer.engines.linking.properNounsState"
decl_stmt|;
comment|/**      * Default for the {@link #PROCESS_ONLY_PROPER_NOUNS_STATE} (false)      */
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_PROCESS_ONLY_PROPER_NOUNS_STATE
init|=
literal|false
decl_stmt|;
comment|/**      * Allows to configure the processed languages by using the syntax supported by {@link LanguageConfiguration}.      * In addition this engine supports language specific configurations for matched {@link LexicalCategory}      * {@link Pos} and String POS tags as well as Pos annotation probabilities by using the parameters      * {@link #PARAM_LEXICAL_CATEGORIES}, {@link #PARAM_POS_TYPES}, {@link #PARAM_POS_TAG} and      * {@link #PARAM_POS_PROBABILITY}.<p>      * See the documentation of {@link LanguageConfiguration} for details of the Syntax.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROCESSED_LANGUAGES
init|=
literal|"enhancer.engines.linking.processedLanguages"
decl_stmt|;
comment|/*      * Parameters used for language specific text processing configurations      */
comment|// (1) PHRASE level
comment|/**      * Allows to configure the processed Chunk type (the default is      *<code>cc={@link LexicalCategory#Noun Noun}</code> to process only      * Noun Phrases). If set to<code>cc</code> (empty value) processing      * of chunks is deactivated.      */
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_PHRASE_CATEGORIES
init|=
literal|"pc"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_PHRASE_TAG
init|=
literal|"ptag"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_PHRASE_PROBABILITY
init|=
literal|"pprob"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_LINK_MULTI_MATCHABLE_TOKEN_IN_PHRASE
init|=
literal|"lmmtip"
decl_stmt|;
comment|//(2) TOKEN level
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_LEXICAL_CATEGORIES
init|=
literal|"lc"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_POS_TYPES
init|=
literal|"pos"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_POS_TAG
init|=
literal|"tag"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_POS_PROBABILITY
init|=
literal|"prob"
decl_stmt|;
comment|/**      * Parameter used to configure how to deal with upper case tokens      */
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_UPPER_CASE
init|=
literal|"uc"
decl_stmt|;
comment|/**      * Enumeration defining valued for the {@link EntityLinkingEngine#PARAM_UPPER_CASE} parameter      */
specifier|public
specifier|static
enum|enum
name|UPPER_CASE_MODE
block|{
name|NONE
block|,
name|MATCH
block|,
name|LINK
block|}
empty_stmt|;
comment|/**      * The default state to dereference entities set to<code>true</code>.      */
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_DEREFERENCE_ENTITIES_STATE
init|=
literal|true
decl_stmt|;
comment|/**      * Default set of languages. This is an empty set indicating that texts in any      * language are processed.       */
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|DEFAULT_LANGUAGES
init|=
name|Collections
operator|.
name|emptySet
argument_list|()
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|double
name|DEFAULT_MIN_POS_TAG_PROBABILITY
init|=
literal|0.6667
decl_stmt|;
comment|/**      * The languages this engine is configured to enhance. An empty List is      * considered as active for any language      */
specifier|private
name|LanguageConfiguration
name|languages
init|=
operator|new
name|LanguageConfiguration
argument_list|(
name|PROCESSED_LANGUAGES
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"*"
block|}
argument_list|)
decl_stmt|;
specifier|private
name|LanguageProcessingConfig
name|defaultConfig
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|LanguageProcessingConfig
argument_list|>
name|languageConfigs
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|LanguageProcessingConfig
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|TextProcessingConfig
parameter_list|()
block|{
name|this
operator|.
name|defaultConfig
operator|=
operator|new
name|LanguageProcessingConfig
argument_list|()
expr_stmt|;
block|}
specifier|public
name|LanguageProcessingConfig
name|getDefaults
parameter_list|()
block|{
return|return
name|defaultConfig
return|;
block|}
comment|/**      * Getter for the language specific configuration.      * @param language      * @return the configuration sepcific to the parsed language or<code>null</code>      * if none.      */
specifier|public
name|LanguageProcessingConfig
name|getLanguageSpecificConfig
parameter_list|(
name|String
name|language
parameter_list|)
block|{
return|return
name|languageConfigs
operator|.
name|get
argument_list|(
name|language
argument_list|)
return|;
block|}
comment|/**      * Creates a language specific configuration by copying the currently configured      * defaults.      * @param language the language      * @return the specific configuration      * @throws IllegalStateException if a language specific configuration for the      * parsed language already exists.      */
specifier|public
name|LanguageProcessingConfig
name|createLanguageSpecificConfig
parameter_list|(
name|String
name|language
parameter_list|)
block|{
if|if
condition|(
name|languageConfigs
operator|.
name|containsKey
argument_list|(
name|language
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"A specific configuration for the language '"
operator|+
name|language
operator|+
literal|"' does already exist!"
argument_list|)
throw|;
block|}
name|LanguageProcessingConfig
name|conf
init|=
name|defaultConfig
operator|.
name|clone
argument_list|()
decl_stmt|;
name|languageConfigs
operator|.
name|put
argument_list|(
name|language
argument_list|,
name|conf
argument_list|)
expr_stmt|;
return|return
name|conf
return|;
block|}
comment|/**      * Removes the language specific configuration for the parsed language      * @param language the language      * @return the removed configuration      */
specifier|public
name|LanguageProcessingConfig
name|removeLanguageSpecificConfig
parameter_list|(
name|String
name|language
parameter_list|)
block|{
return|return
name|languageConfigs
operator|.
name|remove
argument_list|(
name|language
argument_list|)
return|;
block|}
comment|/**      * The {@link LanguageProcessingConfig} for the parsed language      * or<code>null</code> if the language is not included in the      * configuration. This will return the {@link #getDefaults()} if      * the parsed language does not have a specific configuration.<p>      * To obtain just language specific configuration use      * {@link #getLanguageSpecificConfig(String)}      * @param language the language      * @return the configuration or<code>null</code> if the language is      * not configured to be processed.      */
specifier|public
name|LanguageProcessingConfig
name|getConfiguration
parameter_list|(
name|String
name|language
parameter_list|)
block|{
if|if
condition|(
name|languages
operator|.
name|isLanguage
argument_list|(
name|language
argument_list|)
condition|)
block|{
name|LanguageProcessingConfig
name|lpc
init|=
name|languageConfigs
operator|.
name|get
argument_list|(
name|language
argument_list|)
decl_stmt|;
return|return
name|lpc
operator|==
literal|null
condition|?
name|defaultConfig
else|:
name|lpc
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Initialise the {@link TextAnalyzer} component.<p>      * Currently this includes the following configurations:<ul>      *<li>{@link #PROCESSED_LANGUAGES}: If no configuration is present the      * default (process all languages) is used.      *<li> {@value #MIN_POS_TAG_PROBABILITY}: If no configuration is      * present the #DEFAULT_MIN_POS_TAG_PROBABILITY is used      * languages based on the value of the      *       * @param configuration the OSGI component configuration      */
specifier|public
specifier|final
specifier|static
name|TextProcessingConfig
name|createInstance
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|TextProcessingConfig
name|tpc
init|=
operator|new
name|TextProcessingConfig
argument_list|()
decl_stmt|;
comment|//Parse the default text processing configuration
comment|//set the default LexicalTypes
name|Object
name|value
init|=
name|configuration
operator|.
name|get
argument_list|(
name|PROCESS_ONLY_PROPER_NOUNS_STATE
argument_list|)
decl_stmt|;
name|boolean
name|properNounState
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|Boolean
condition|)
block|{
name|properNounState
operator|=
operator|(
operator|(
name|Boolean
operator|)
name|value
operator|)
operator|.
name|booleanValue
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|properNounState
operator|=
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|properNounState
operator|=
name|DEFAULT_PROCESS_ONLY_PROPER_NOUNS_STATE
expr_stmt|;
block|}
if|if
condition|(
name|properNounState
condition|)
block|{
name|tpc
operator|.
name|defaultConfig
operator|.
name|setLinkedLexicalCategories
argument_list|(
name|Collections
operator|.
name|EMPTY_SET
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|defaultConfig
operator|.
name|setLinkedPos
argument_list|(
name|LanguageProcessingConfig
operator|.
name|DEFAULT_LINKED_POS
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> ProperNoun matching activated (matched Pos: {})"
argument_list|,
name|tpc
operator|.
name|defaultConfig
operator|.
name|getLinkedPos
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|tpc
operator|.
name|defaultConfig
operator|.
name|setLinkedLexicalCategories
argument_list|(
name|LanguageProcessingConfig
operator|.
name|DEFAULT_LINKED_LEXICAL_CATEGORIES
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|defaultConfig
operator|.
name|setLinkedPos
argument_list|(
name|Collections
operator|.
name|EMPTY_SET
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> Noun matching activated (matched LexicalCategories: {})"
argument_list|,
name|tpc
operator|.
name|defaultConfig
operator|.
name|getLinkedLexicalCategories
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//parse the language configuration
name|value
operator|=
name|configuration
operator|.
name|get
argument_list|(
name|PROCESSED_LANGUAGES
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|String
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROCESSED_LANGUAGES
argument_list|,
literal|"Comma separated String "
operator|+
literal|"is not supported for configurung the processed languages for the because "
operator|+
literal|"the comma is used as separator for values of the parameters '"
operator|+
name|PARAM_LEXICAL_CATEGORIES
operator|+
literal|"', '"
operator|+
name|PARAM_POS_TYPES
operator|+
literal|"'and'"
operator|+
name|PARAM_POS_TAG
operator|+
literal|"! Users need to use String[] or Collection<?> instead!"
argument_list|)
throw|;
block|}
name|tpc
operator|.
name|languages
operator|.
name|setConfiguration
argument_list|(
name|configuration
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|defaultConfig
init|=
name|tpc
operator|.
name|languages
operator|.
name|getDefaultParameters
argument_list|()
decl_stmt|;
comment|//apply the default parameters (parameter set for the '*' or '' (empty) language
if|if
condition|(
operator|!
name|defaultConfig
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|applyLanguageParameter
argument_list|(
name|tpc
operator|.
name|defaultConfig
argument_list|,
literal|null
argument_list|,
name|defaultConfig
argument_list|)
expr_stmt|;
block|}
comment|//apply language specific configurations
for|for
control|(
name|String
name|lang
range|:
name|tpc
operator|.
name|languages
operator|.
name|getExplicitlyIncluded
argument_list|()
control|)
block|{
name|LanguageProcessingConfig
name|lpc
init|=
name|tpc
operator|.
name|defaultConfig
operator|.
name|clone
argument_list|()
decl_stmt|;
name|applyLanguageParameter
argument_list|(
name|lpc
argument_list|,
name|lang
argument_list|,
name|tpc
operator|.
name|languages
operator|.
name|getParameters
argument_list|(
name|lang
argument_list|)
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|languageConfigs
operator|.
name|put
argument_list|(
name|lang
argument_list|,
name|lpc
argument_list|)
expr_stmt|;
block|}
return|return
name|tpc
return|;
block|}
specifier|private
specifier|static
name|void
name|applyLanguageParameter
parameter_list|(
name|LanguageProcessingConfig
name|tpc
parameter_list|,
name|String
name|language
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|config
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"> parse language Configuration for language: {}"
argument_list|,
name|language
operator|==
literal|null
condition|?
literal|"default"
else|:
name|language
argument_list|)
expr_stmt|;
comment|//parse Phrase level configuration
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|chunkCats
init|=
name|parseEnumParam
argument_list|(
name|config
argument_list|,
name|PROCESSED_LANGUAGES
argument_list|,
name|language
argument_list|,
name|PARAM_PHRASE_CATEGORIES
argument_list|,
name|LexicalCategory
operator|.
name|class
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|chunkTags
init|=
name|parseStringTags
argument_list|(
name|config
operator|.
name|get
argument_list|(
name|PARAM_PHRASE_TAG
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|chunkCats
operator|.
name|isEmpty
argument_list|()
operator|&&
name|config
operator|.
name|containsKey
argument_list|(
name|PARAM_PHRASE_CATEGORIES
argument_list|)
operator|&&
name|chunkTags
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"   + enable ignorePhrase"
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setIgnoreChunksState
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setProcessedPhraseCategories
argument_list|(
name|Collections
operator|.
name|EMPTY_SET
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|tpc
operator|.
name|setIgnoreChunksState
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|chunkCats
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"   + set processable Phrase cat {}"
argument_list|,
name|chunkCats
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setProcessedPhraseCategories
argument_list|(
name|chunkCats
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"   - use processable Phrase cats {}"
argument_list|,
name|tpc
operator|.
name|getProcessedPhraseCategories
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|chunkTags
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"   + set processable Phrase tags {}"
argument_list|,
name|chunkTags
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setProcessedPhraseTags
argument_list|(
name|chunkTags
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"   - use processable Phrase tags {}"
argument_list|,
name|tpc
operator|.
name|getProcessedPhraseTags
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|Double
name|chunkProb
init|=
name|parseNumber
argument_list|(
name|config
argument_list|,
name|PROCESSED_LANGUAGES
argument_list|,
name|language
argument_list|,
name|PARAM_PHRASE_PROBABILITY
argument_list|,
name|Double
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|chunkProb
operator|!=
literal|null
operator|||
comment|//if explicitly set
name|config
operator|.
name|containsKey
argument_list|(
name|PARAM_PHRASE_PROBABILITY
argument_list|)
condition|)
block|{
comment|//set to empty value (set default)
name|log
operator|.
name|info
argument_list|(
literal|"   + set min ChunkTag probability: {}"
argument_list|,
name|chunkProb
operator|==
literal|null
condition|?
literal|"default"
else|:
name|chunkProb
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setMinPhraseAnnotationProbability
argument_list|(
name|chunkProb
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setMinExcludePhraseAnnotationProbability
argument_list|(
name|chunkProb
operator|==
literal|null
condition|?
literal|null
else|:
name|chunkProb
operator|/
literal|2
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"   - use min PhraseTag probability: {}"
argument_list|,
name|tpc
operator|.
name|getMinPhraseAnnotationProbability
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//link multiple matchable Tokens within Chunks
name|Boolean
name|lmmticState
init|=
name|parseState
argument_list|(
name|config
argument_list|,
name|PARAM_LINK_MULTI_MATCHABLE_TOKEN_IN_PHRASE
argument_list|)
decl_stmt|;
if|if
condition|(
name|lmmticState
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"   + set the link multi matchable tokens in Phrase state to : {}"
argument_list|,
name|lmmticState
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setLinkMultiMatchableTokensInChunkState
argument_list|(
name|lmmticState
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"   - use the link multi matchable tokens in Phrase state to : {}"
argument_list|,
name|tpc
operator|.
name|isLinkMultiMatchableTokensInChunk
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//parse Token level configuration
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|lexCats
init|=
name|parseEnumParam
argument_list|(
name|config
argument_list|,
name|PROCESSED_LANGUAGES
argument_list|,
name|language
argument_list|,
name|PARAM_LEXICAL_CATEGORIES
argument_list|,
name|LexicalCategory
operator|.
name|class
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Pos
argument_list|>
name|pos
init|=
name|parseEnumParam
argument_list|(
name|config
argument_list|,
name|PROCESSED_LANGUAGES
argument_list|,
name|language
argument_list|,
name|PARAM_POS_TYPES
argument_list|,
name|Pos
operator|.
name|class
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|tags
init|=
name|parseStringTags
argument_list|(
name|config
operator|.
name|get
argument_list|(
name|PARAM_POS_TAG
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|config
operator|.
name|containsKey
argument_list|(
name|PARAM_LEXICAL_CATEGORIES
argument_list|)
operator|||
name|config
operator|.
name|containsKey
argument_list|(
name|PARAM_POS_TYPES
argument_list|)
operator|||
name|config
operator|.
name|containsKey
argument_list|(
name|PARAM_POS_TAG
argument_list|)
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"   + set Linkable Tokens: cat: {}, pos: {}, tags {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|lexCats
block|,
name|pos
block|,
name|tags
block|}
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setLinkedLexicalCategories
argument_list|(
name|lexCats
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setLinkedPos
argument_list|(
name|pos
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setLinkedPosTags
argument_list|(
name|tags
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"   - use Linkable Tokens: cat: {}, pos: {}, tags {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|tpc
operator|.
name|getLinkedLexicalCategories
argument_list|()
block|,
name|tpc
operator|.
name|getLinkedPos
argument_list|()
block|,
name|tpc
operator|.
name|getLinkedPos
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
comment|//min POS tag probability
name|Double
name|prob
init|=
name|parseNumber
argument_list|(
name|config
argument_list|,
name|PROCESSED_LANGUAGES
argument_list|,
name|language
argument_list|,
name|PARAM_POS_PROBABILITY
argument_list|,
name|Double
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|prob
operator|!=
literal|null
operator|||
comment|//explicitly set
name|config
operator|.
name|containsKey
argument_list|(
name|PARAM_POS_PROBABILITY
argument_list|)
condition|)
block|{
comment|//set to empty value (set default)
name|log
operator|.
name|info
argument_list|(
literal|"   + set minimum POS tag probability: {}"
argument_list|,
name|prob
operator|==
literal|null
condition|?
literal|"default"
else|:
name|prob
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setMinPosAnnotationProbability
argument_list|(
name|prob
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setMinExcludePosAnnotationProbability
argument_list|(
name|prob
operator|==
literal|null
condition|?
literal|null
else|:
name|prob
operator|/
literal|2d
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"   - use minimum POS tag probability: {}"
argument_list|,
name|tpc
operator|.
name|getMinPosAnnotationProbability
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//parse upper case
name|Set
argument_list|<
name|UPPER_CASE_MODE
argument_list|>
name|ucMode
init|=
name|parseEnumParam
argument_list|(
name|config
argument_list|,
name|PROCESSED_LANGUAGES
argument_list|,
name|language
argument_list|,
name|PARAM_UPPER_CASE
argument_list|,
name|UPPER_CASE_MODE
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|ucMode
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROCESSED_LANGUAGES
argument_list|,
literal|"Parameter 'uc' (Upper case mode) MUST NOT be multi valued (langauge: "
operator|+
operator|(
name|language
operator|==
literal|null
condition|?
literal|"default"
else|:
name|language
operator|)
operator|+
literal|", parsed value='"
operator|+
name|config
operator|.
name|get
argument_list|(
name|PARAM_UPPER_CASE
argument_list|)
operator|+
literal|"')!"
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|ucMode
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|UPPER_CASE_MODE
name|mode
init|=
name|ucMode
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"   + set upper case token mode to {}"
argument_list|,
name|mode
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|mode
condition|)
block|{
case|case
name|NONE
case|:
name|tpc
operator|.
name|setMatchUpperCaseTokensState
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setLinkUpperCaseTokensState
argument_list|(
literal|false
argument_list|)
expr_stmt|;
break|break;
case|case
name|MATCH
case|:
name|tpc
operator|.
name|setMatchUpperCaseTokensState
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setLinkUpperCaseTokensState
argument_list|(
literal|false
argument_list|)
expr_stmt|;
break|break;
case|case
name|LINK
case|:
name|tpc
operator|.
name|setMatchUpperCaseTokensState
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setLinkUpperCaseTokensState
argument_list|(
literal|true
argument_list|)
expr_stmt|;
break|break;
default|default:
name|log
operator|.
name|warn
argument_list|(
literal|"Unsupported {} entry {} -> set defaults"
argument_list|,
name|UPPER_CASE_MODE
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|mode
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setMatchUpperCaseTokensState
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|tpc
operator|.
name|setLinkUpperCaseTokensState
argument_list|(
literal|null
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"   - use upper case token mode: match={}, link={}"
argument_list|,
name|tpc
operator|.
name|isMatchUpperCaseTokens
argument_list|()
argument_list|,
name|tpc
operator|.
name|isLinkUpperCaseTokens
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|Boolean
name|parseState
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|config
parameter_list|,
name|String
name|param
parameter_list|)
block|{
name|String
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|param
argument_list|)
decl_stmt|;
return|return
name|value
operator|==
literal|null
operator|&&
name|config
operator|.
name|containsKey
argument_list|(
name|param
argument_list|)
condition|?
name|Boolean
operator|.
name|TRUE
else|:
name|value
operator|!=
literal|null
condition|?
operator|new
name|Boolean
argument_list|(
name|value
argument_list|)
else|:
literal|null
return|;
block|}
specifier|private
specifier|static
parameter_list|<
name|T
extends|extends
name|Number
parameter_list|>
name|T
name|parseNumber
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|config
parameter_list|,
name|String
name|property
parameter_list|,
name|String
name|language
parameter_list|,
name|String
name|param
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|String
name|paramVal
init|=
name|config
operator|.
name|get
argument_list|(
name|PARAM_POS_PROBABILITY
argument_list|)
decl_stmt|;
if|if
condition|(
name|paramVal
operator|!=
literal|null
operator|&&
operator|!
name|paramVal
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
comment|//all Number subclasses do have a String constructor!
return|return
name|clazz
operator|.
name|getConstructor
argument_list|(
name|String
operator|.
name|class
argument_list|)
operator|.
name|newInstance
argument_list|(
name|paramVal
operator|.
name|trim
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"Unable to parse "
operator|+
name|clazz
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|" from Parameter '"
operator|+
name|PARAM_POS_PROBABILITY
operator|+
literal|"="
operator|+
name|paramVal
operator|.
name|trim
argument_list|()
operator|+
literal|"' from the "
operator|+
operator|(
name|language
operator|==
literal|null
condition|?
literal|"default"
else|:
name|language
operator|)
operator|+
literal|" language configuration"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to create new "
operator|+
name|clazz
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"("
operator|+
name|paramVal
operator|.
name|trim
argument_list|()
operator|+
literal|"::String)"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|SecurityException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to create new "
operator|+
name|clazz
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"("
operator|+
name|paramVal
operator|.
name|trim
argument_list|()
operator|+
literal|"::String)"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|InstantiationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to create new "
operator|+
name|clazz
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"("
operator|+
name|paramVal
operator|.
name|trim
argument_list|()
operator|+
literal|"::String)"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IllegalAccessException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to create new "
operator|+
name|clazz
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"("
operator|+
name|paramVal
operator|.
name|trim
argument_list|()
operator|+
literal|"::String)"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|InvocationTargetException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to create new "
operator|+
name|clazz
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"("
operator|+
name|paramVal
operator|.
name|trim
argument_list|()
operator|+
literal|"::String)"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to create new "
operator|+
name|clazz
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"("
operator|+
name|paramVal
operator|.
name|trim
argument_list|()
operator|+
literal|"::String)"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
return|return
literal|null
return|;
block|}
specifier|private
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|parseStringTags
parameter_list|(
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|emptySet
argument_list|()
return|;
block|}
else|else
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|tags
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|entry
range|:
name|value
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
name|entry
operator|=
name|entry
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|entry
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|tags
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|tags
return|;
block|}
block|}
comment|/**      * Utility to parse Enum members out of a comma separated string      * @param config the config      * @param property the property (only used for error handling)      * @param param the key of the config used to obtain the config      * @param enumClass the {@link Enum} class      * @return the configured members of the Enum or an empty set if none       * @throws ConfigurationException if a configured value was not part of the enum      */
specifier|private
specifier|static
parameter_list|<
name|T
extends|extends
name|Enum
argument_list|<
name|T
argument_list|>
parameter_list|>
name|Set
argument_list|<
name|T
argument_list|>
name|parseEnumParam
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|config
parameter_list|,
name|String
name|property
parameter_list|,
name|String
name|language
parameter_list|,
comment|//params used for logging
name|String
name|param
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|enumClass
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Set
argument_list|<
name|T
argument_list|>
name|enumSet
decl_stmt|;
name|String
name|val
init|=
name|config
operator|.
name|get
argument_list|(
name|param
argument_list|)
decl_stmt|;
if|if
condition|(
name|val
operator|==
literal|null
condition|)
block|{
name|enumSet
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|enumSet
operator|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|enumClass
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|entry
range|:
name|val
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
name|entry
operator|=
name|entry
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|entry
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|enumSet
operator|.
name|add
argument_list|(
name|Enum
operator|.
name|valueOf
argument_list|(
name|enumClass
argument_list|,
name|entry
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"'"
operator|+
name|entry
operator|+
literal|"' of param '"
operator|+
name|param
operator|+
literal|"' for language '"
operator|+
operator|(
name|language
operator|==
literal|null
condition|?
literal|"default"
else|:
name|language
operator|)
operator|+
literal|"'is not a member of the enum "
operator|+
name|enumClass
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"(configured : '"
operator|+
name|val
operator|+
literal|"')!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
block|}
return|return
name|enumSet
return|;
block|}
block|}
end_class

end_unit

