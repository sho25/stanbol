begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (c) 2012 Sebastian Schaffert  *  *  Licensed under the Apache License, Version 2.0 (the "License");  *  you may not use this file except in compliance with the License.  *  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
end_comment

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
name|opennlp
operator|.
name|pos
operator|.
name|services
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
import|import static
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
name|NlpAnnotations
operator|.
name|POS_ANNOTATION
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
name|nlp
operator|.
name|utils
operator|.
name|NlpEngineHelper
operator|.
name|getLanguage
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
name|nlp
operator|.
name|utils
operator|.
name|NlpEngineHelper
operator|.
name|initAnalysedText
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
name|ArrayList
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
name|Map
operator|.
name|Entry
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
name|opennlp
operator|.
name|tools
operator|.
name|postag
operator|.
name|POSModel
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|postag
operator|.
name|POSTagger
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|postag
operator|.
name|POSTaggerME
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|sentdetect
operator|.
name|SentenceDetector
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|sentdetect
operator|.
name|SentenceDetectorME
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|sentdetect
operator|.
name|SentenceModel
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|tokenize
operator|.
name|Tokenizer
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|util
operator|.
name|Sequence
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
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
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
name|ConfigurationPolicy
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
name|Deactivate
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
name|Properties
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
name|Reference
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
name|commons
operator|.
name|opennlp
operator|.
name|OpenNLP
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
name|opennlp
operator|.
name|pos
operator|.
name|model
operator|.
name|PosTagSetRegistry
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
name|NlpAnnotations
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
name|TagSet
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
name|nlp
operator|.
name|model
operator|.
name|Section
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
name|Sentence
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
name|Span
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
name|Span
operator|.
name|SpanTypeEnum
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
name|Token
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
name|annotation
operator|.
name|Value
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
name|PosTag
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
name|NlpEngineHelper
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
name|AbstractEnhancementEngine
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Constants
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
comment|/**  * A german language POS tagger. Requires that the content item has a text/plain part and a  * language id of "de". Adds a POSContentPart to the content item that can be used for further  * processing by other modules.  *   * @author Sebastian Schaffert  */
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
argument_list|,
name|configurationFactory
operator|=
literal|true
argument_list|,
comment|//allow multiple instances
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|OPTIONAL
argument_list|)
comment|//create a default instance with the default configuration
annotation|@
name|Service
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|,
name|value
operator|=
literal|"opennlp-pos"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|POSTaggingEngine
operator|.
name|CONFIG_LANGUAGES
argument_list|,
name|value
operator|=
block|{
literal|"*"
block|}
argument_list|,
name|cardinality
operator|=
name|Integer
operator|.
name|MAX_VALUE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|,
name|intValue
operator|=
operator|-
literal|100
argument_list|)
comment|//give the default instance a ranking< 0
block|}
argument_list|)
specifier|public
class|class
name|POSTaggingEngine
extends|extends
name|AbstractEnhancementEngine
argument_list|<
name|RuntimeException
argument_list|,
name|RuntimeException
argument_list|>
block|{
comment|/**      * Language configuration. Takes a list of ISO language codes of supported languages. Currently supported      * are the languages given as default value.      */
specifier|public
specifier|static
specifier|final
name|String
name|CONFIG_LANGUAGES
init|=
literal|"org.apache.stanbol.enhancer.pos.languages"
decl_stmt|;
comment|/**      * The parameter name used to configure the name of the OpenNLP model used for pos tagging      */
specifier|private
specifier|static
specifier|final
name|String
name|MODEL_NAME_PARAM
init|=
literal|"model"
decl_stmt|;
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|POSTaggingEngine
operator|.
name|class
argument_list|)
decl_stmt|;
comment|//Langauge configuration
specifier|private
name|LanguageConfiguration
name|languageConfig
init|=
operator|new
name|LanguageConfiguration
argument_list|(
name|CONFIG_LANGUAGES
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"*"
block|}
argument_list|)
decl_stmt|;
comment|//    private Set<String> configuredLanguages;
comment|//    private Set<String> excludedLanguages;
comment|//    private boolean allowAll;
annotation|@
name|Reference
specifier|private
name|OpenNLP
name|openNLP
decl_stmt|;
comment|/**      * Provides known {@link TagSet}s used by OpenNLP      */
specifier|private
name|PosTagSetRegistry
name|tagSetRegistry
init|=
name|PosTagSetRegistry
operator|.
name|getInstance
argument_list|()
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|AnalysedTextFactory
name|analysedTextFactory
decl_stmt|;
comment|/**      * Indicate if this engine can enhance supplied ContentItem, and if it      * suggests enhancing it synchronously or asynchronously. The      * {@link org.apache.stanbol.enhancer.servicesapi.EnhancementJobManager} can force sync/async mode if desired, it is      * just a suggestion from the engine.      *<p/>      * Returns ENHANCE_ASYNC in case there is a text/plain content part and a tagger for the language identified for      * the content item, CANNOT_ENHANCE otherwise.      *      * @throws org.apache.stanbol.enhancer.servicesapi.EngineException      *          if the introspecting process of the content item      *          fails      */
annotation|@
name|Override
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
comment|// check if content is present
name|Map
operator|.
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|Blob
argument_list|>
name|entry
init|=
name|NlpEngineHelper
operator|.
name|getPlainText
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|==
literal|null
operator|||
name|entry
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
name|CANNOT_ENHANCE
return|;
block|}
name|String
name|language
init|=
name|getLanguage
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|language
operator|==
literal|null
condition|)
block|{
return|return
name|CANNOT_ENHANCE
return|;
block|}
if|if
condition|(
operator|!
name|languageConfig
operator|.
name|isLanguage
argument_list|(
name|language
argument_list|)
condition|)
block|{
name|log
operator|.
name|trace
argument_list|(
literal|"> can NOT enhance ContentItem {} because language {} is "
operator|+
literal|"not enabled by this engines configuration"
argument_list|,
name|ci
argument_list|,
name|language
argument_list|)
expr_stmt|;
return|return
name|CANNOT_ENHANCE
return|;
block|}
if|if
condition|(
name|getPOSTagger
argument_list|(
name|language
argument_list|)
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|trace
argument_list|(
literal|"> can NOT enhance ContentItem {} because no POSTagger is"
operator|+
literal|"is present for language {}"
argument_list|,
name|ci
argument_list|,
name|language
argument_list|)
expr_stmt|;
return|return
name|CANNOT_ENHANCE
return|;
block|}
name|log
operator|.
name|trace
argument_list|(
literal|"> can enhance ContentItem {} with language {}"
argument_list|,
name|ci
argument_list|,
name|language
argument_list|)
expr_stmt|;
return|return
name|ENHANCE_ASYNC
return|;
block|}
comment|/**      * Compute enhancements for supplied ContentItem. The results of the process      * are expected to be stored in the metadata of the content item.      *<p/>      * The client (usually an {@link org.apache.stanbol.enhancer.servicesapi.EnhancementJobManager}) should take care of      * persistent storage of the enhanced {@link org.apache.stanbol.enhancer.servicesapi.ContentItem}.      *<p/>      * This method creates a new POSContentPart using {@link org.apache.stanbol.enhancer.engines.pos.api.POSTaggerHelper#createContentPart} from a text/plain part and      * stores it as a new part in the content item. The metadata is not changed.      *      * @throws org.apache.stanbol.enhancer.servicesapi.EngineException      *          if the underlying process failed to work as      *          expected      */
annotation|@
name|Override
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
name|AnalysedText
name|at
init|=
name|initAnalysedText
argument_list|(
name|this
argument_list|,
name|analysedTextFactory
argument_list|,
name|ci
argument_list|)
decl_stmt|;
name|String
name|language
init|=
name|getLanguage
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|POSTagger
name|posTagger
init|=
name|getPOSTagger
argument_list|(
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|posTagger
operator|==
literal|null
condition|)
block|{
comment|//this means that the POS tagger became unavailable in-between
comment|//the call to canEnhance and computeEnhancement
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"PosTagger for langauge '"
operator|+
name|language
operator|+
literal|"is not available."
argument_list|)
throw|;
block|}
name|TagSet
argument_list|<
name|PosTag
argument_list|>
name|tagSet
init|=
name|tagSetRegistry
operator|.
name|getTagSet
argument_list|(
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|tagSet
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"No POS TagSet registered for Language '{}'. Will build an "
operator|+
literal|"adhoc set based on encountered Tags!"
argument_list|,
name|language
argument_list|)
expr_stmt|;
comment|//for now only created to avoid checks for tagSet == null
comment|//TODO: in future we might want to automatically create posModels based
comment|//on tagged texts. However this makes no sense as long we can not
comment|//persist TagSets.
name|tagSet
operator|=
operator|new
name|TagSet
argument_list|<
name|PosTag
argument_list|>
argument_list|(
literal|"dummy"
argument_list|,
name|language
argument_list|)
expr_stmt|;
block|}
comment|//holds PosTags created for POS tags that where not part of the posModel
comment|//(will hold all PosTags in case tagSet is NULL
name|Map
argument_list|<
name|String
argument_list|,
name|PosTag
argument_list|>
name|adhocTags
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|PosTag
argument_list|>
argument_list|()
decl_stmt|;
comment|//(1) Sentence detection
comment|//Try to read existing Sentence Annotations
name|Iterator
argument_list|<
name|Sentence
argument_list|>
name|sentences
init|=
name|at
operator|.
name|getSentences
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Section
argument_list|>
name|sentenceList
decl_stmt|;
if|if
condition|(
operator|!
name|sentences
operator|.
name|hasNext
argument_list|()
condition|)
block|{
comment|//if non try to detect sentences
name|log
operator|.
name|trace
argument_list|(
literal|"> detect sentences for {}"
argument_list|,
name|at
argument_list|)
expr_stmt|;
name|sentenceList
operator|=
name|detectSentences
argument_list|(
name|at
argument_list|,
name|language
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|sentences
operator|.
name|hasNext
argument_list|()
condition|)
block|{
comment|//check if we have detected sentences
name|log
operator|.
name|trace
argument_list|(
literal|"> use existing Sentence annotations for {}"
argument_list|,
name|at
argument_list|)
expr_stmt|;
name|sentenceList
operator|=
operator|new
name|ArrayList
argument_list|<
name|Section
argument_list|>
argument_list|()
expr_stmt|;
name|AnalysedTextUtils
operator|.
name|appandToList
argument_list|(
name|sentences
argument_list|,
name|sentenceList
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//no sentence detected ... treat the whole text as a single sentence
comment|//TODO: maybe apply here a limit to the text size!
name|log
operator|.
name|trace
argument_list|(
literal|"> unable to detect Sentences for {} (langauge: {})"
argument_list|,
name|at
argument_list|,
name|language
argument_list|)
expr_stmt|;
name|sentenceList
operator|=
name|Collections
operator|.
name|singletonList
argument_list|(
operator|(
name|Section
operator|)
name|at
argument_list|)
expr_stmt|;
block|}
comment|//for all sentences (or the whole Text - if no sentences available)
for|for
control|(
name|Section
name|sentence
range|:
name|sentenceList
control|)
block|{
comment|//(2) Tokenize Sentences
name|List
argument_list|<
name|Token
argument_list|>
name|tokenList
decl_stmt|;
comment|//check if there are already tokens
name|Iterator
argument_list|<
name|Token
argument_list|>
name|tokens
init|=
name|sentence
operator|.
name|getTokens
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|tokens
operator|.
name|hasNext
argument_list|()
condition|)
block|{
comment|//no tokens present -> tokenize
name|log
operator|.
name|trace
argument_list|(
literal|"> tokenize {}"
argument_list|,
name|sentence
argument_list|)
expr_stmt|;
name|tokenList
operator|=
name|tokenize
argument_list|(
name|sentence
argument_list|,
name|language
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//use existing
name|log
operator|.
name|trace
argument_list|(
literal|"> use existing Tokens for {}"
argument_list|,
name|sentence
argument_list|)
expr_stmt|;
name|tokenList
operator|=
operator|new
name|ArrayList
argument_list|<
name|Token
argument_list|>
argument_list|()
expr_stmt|;
comment|//ensure an ArrayList is used
name|AnalysedTextUtils
operator|.
name|appandToList
argument_list|(
name|tokens
argument_list|,
name|tokenList
argument_list|)
expr_stmt|;
block|}
comment|//(3) POS Tagging
name|posTag
argument_list|(
name|tokenList
argument_list|,
name|posTagger
argument_list|,
name|tagSet
argument_list|,
name|adhocTags
argument_list|)
expr_stmt|;
block|}
name|logAnnotations
argument_list|(
name|at
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|logAnnotations
parameter_list|(
name|AnalysedText
name|at
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Span
argument_list|>
name|it
init|=
name|at
operator|.
name|getEnclosed
argument_list|(
name|EnumSet
operator|.
name|of
argument_list|(
name|SpanTypeEnum
operator|.
name|Sentence
argument_list|,
name|SpanTypeEnum
operator|.
name|Token
argument_list|)
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
name|Span
name|span
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"> {}"
argument_list|,
name|span
argument_list|)
expr_stmt|;
for|for
control|(
name|Value
argument_list|<
name|PosTag
argument_list|>
name|value
range|:
name|span
operator|.
name|getAnnotations
argument_list|(
name|POS_ANNOTATION
argument_list|)
control|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"   - {}"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * POS tags the parsed tokens by using the pos tagger. Annotations are      * added based on the posModel and already created adhoc tags.      * @param tokenList      * @param posTagger      * @param posModel      * @param adhocTags      */
specifier|private
name|void
name|posTag
parameter_list|(
name|List
argument_list|<
name|Token
argument_list|>
name|tokenList
parameter_list|,
name|POSTagger
name|posTagger
parameter_list|,
name|TagSet
argument_list|<
name|PosTag
argument_list|>
name|posModel
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|PosTag
argument_list|>
name|adhocTags
parameter_list|)
block|{
name|String
index|[]
name|tokenTexts
init|=
operator|new
name|String
index|[
name|tokenList
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|tokenList
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|tokenTexts
index|[
name|i
index|]
operator|=
name|tokenList
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getSpan
argument_list|()
expr_stmt|;
block|}
comment|//get the topK POS tags and props and copy it over to the 2dim Arrays
name|Sequence
index|[]
name|posSequences
init|=
name|posTagger
operator|.
name|topKSequences
argument_list|(
name|tokenTexts
argument_list|)
decl_stmt|;
comment|//extract the POS tags and props for the current token from the
comment|//posSequences.
comment|//NOTE: Sequence includes always POS tags for all Tokens. If
comment|//      less then posSequences.length are available it adds the
comment|//      best match for all followings.
comment|//      We do not want such copies.
name|PosTag
index|[]
name|actPos
init|=
operator|new
name|PosTag
index|[
name|posSequences
operator|.
name|length
index|]
decl_stmt|;
name|double
index|[]
name|actProp
init|=
operator|new
name|double
index|[
name|posSequences
operator|.
name|length
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|tokenTexts
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|Token
name|token
init|=
name|tokenList
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|boolean
name|done
init|=
literal|false
decl_stmt|;
name|int
name|j
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|j
operator|<
name|posSequences
operator|.
name|length
operator|&&
operator|!
name|done
condition|)
block|{
name|String
name|p
init|=
name|posSequences
index|[
name|j
index|]
operator|.
name|getOutcomes
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|done
operator|=
name|j
operator|>
literal|0
operator|&&
name|p
operator|.
name|equals
argument_list|(
name|actPos
index|[
literal|0
index|]
operator|.
name|getTag
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|done
condition|)
block|{
name|actPos
index|[
name|j
index|]
operator|=
name|getPosTag
argument_list|(
name|posModel
argument_list|,
name|adhocTags
argument_list|,
name|p
argument_list|)
expr_stmt|;
name|actProp
index|[
name|j
index|]
operator|=
name|posSequences
index|[
name|j
index|]
operator|.
name|getProbs
argument_list|()
index|[
name|i
index|]
expr_stmt|;
name|j
operator|++
expr_stmt|;
block|}
block|}
comment|//create the POS values
name|token
operator|.
name|addAnnotations
argument_list|(
name|POS_ANNOTATION
argument_list|,
name|Value
operator|.
name|values
argument_list|(
name|actPos
argument_list|,
name|actProp
argument_list|,
name|j
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|PosTag
name|getPosTag
parameter_list|(
name|TagSet
argument_list|<
name|PosTag
argument_list|>
name|model
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|PosTag
argument_list|>
name|adhocTags
parameter_list|,
name|String
name|tag
parameter_list|)
block|{
name|PosTag
name|posTag
init|=
name|model
operator|.
name|getTag
argument_list|(
name|tag
argument_list|)
decl_stmt|;
if|if
condition|(
name|posTag
operator|!=
literal|null
condition|)
block|{
return|return
name|posTag
return|;
block|}
name|posTag
operator|=
name|adhocTags
operator|.
name|get
argument_list|(
name|tag
argument_list|)
expr_stmt|;
if|if
condition|(
name|posTag
operator|!=
literal|null
condition|)
block|{
return|return
name|posTag
return|;
block|}
name|posTag
operator|=
operator|new
name|PosTag
argument_list|(
name|tag
argument_list|)
expr_stmt|;
name|adhocTags
operator|.
name|put
argument_list|(
name|tag
argument_list|,
name|posTag
argument_list|)
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"Encountered unknown POS tag '{}' for langauge '{}'"
argument_list|,
name|tag
argument_list|,
name|model
operator|.
name|getLanguages
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|posTag
return|;
block|}
specifier|private
name|List
argument_list|<
name|Token
argument_list|>
name|tokenize
parameter_list|(
name|Section
name|section
parameter_list|,
name|String
name|langauge
parameter_list|)
block|{
name|Tokenizer
name|tokenizer
init|=
name|getTokenizer
argument_list|(
name|langauge
argument_list|)
decl_stmt|;
name|String
name|text
init|=
name|section
operator|.
name|getSpan
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Token
argument_list|>
name|tokens
init|=
operator|new
name|ArrayList
argument_list|<
name|Token
argument_list|>
argument_list|(
name|text
operator|.
name|length
argument_list|()
operator|/
literal|5
argument_list|)
decl_stmt|;
comment|//assume avr. token length is 5
name|opennlp
operator|.
name|tools
operator|.
name|util
operator|.
name|Span
index|[]
name|tokenSpans
init|=
name|tokenizer
operator|.
name|tokenizePos
argument_list|(
name|section
operator|.
name|getSpan
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|tokenSpans
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|Token
name|token
init|=
name|section
operator|.
name|addToken
argument_list|(
name|tokenSpans
index|[
name|i
index|]
operator|.
name|getStart
argument_list|()
argument_list|,
name|tokenSpans
index|[
name|i
index|]
operator|.
name|getEnd
argument_list|()
argument_list|)
decl_stmt|;
name|log
operator|.
name|trace
argument_list|(
literal|"> add {}"
argument_list|,
name|token
argument_list|)
expr_stmt|;
name|tokens
operator|.
name|add
argument_list|(
name|token
argument_list|)
expr_stmt|;
block|}
return|return
name|tokens
return|;
block|}
specifier|private
name|List
argument_list|<
name|Section
argument_list|>
name|detectSentences
parameter_list|(
name|AnalysedText
name|at
parameter_list|,
name|String
name|language
parameter_list|)
block|{
name|SentenceDetector
name|sentenceDetector
init|=
name|getSentenceDetector
argument_list|(
name|language
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Section
argument_list|>
name|sentences
decl_stmt|;
if|if
condition|(
name|sentenceDetector
operator|!=
literal|null
condition|)
block|{
name|sentences
operator|=
operator|new
name|ArrayList
argument_list|<
name|Section
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|opennlp
operator|.
name|tools
operator|.
name|util
operator|.
name|Span
name|sentSpan
range|:
name|sentenceDetector
operator|.
name|sentPosDetect
argument_list|(
name|at
operator|.
name|getSpan
argument_list|()
argument_list|)
control|)
block|{
name|Sentence
name|sentence
init|=
name|at
operator|.
name|addSentence
argument_list|(
name|sentSpan
operator|.
name|getStart
argument_list|()
argument_list|,
name|sentSpan
operator|.
name|getEnd
argument_list|()
argument_list|)
decl_stmt|;
name|log
operator|.
name|trace
argument_list|(
literal|"> add {}"
argument_list|,
name|sentence
argument_list|)
expr_stmt|;
name|sentences
operator|.
name|add
argument_list|(
name|sentence
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|sentences
operator|=
literal|null
expr_stmt|;
block|}
return|return
name|sentences
return|;
block|}
comment|/**      * Activate and read the properties. Configures and initialises a POSTagger for each language configured in      * CONFIG_LANGUAGES.      *      * @param ce the {@link org.osgi.service.component.ComponentContext}      */
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ce
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"activating POS tagging engine"
argument_list|)
expr_stmt|;
name|super
operator|.
name|activate
argument_list|(
name|ce
argument_list|)
expr_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
init|=
name|ce
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|languageConfig
operator|.
name|setConfiguration
argument_list|(
name|properties
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|languageConfig
operator|.
name|setDefault
argument_list|()
expr_stmt|;
name|super
operator|.
name|deactivate
argument_list|(
name|context
argument_list|)
expr_stmt|;
block|}
specifier|private
name|SentenceDetector
name|getSentenceDetector
parameter_list|(
name|String
name|language
parameter_list|)
block|{
try|try
block|{
name|SentenceModel
name|model
init|=
name|openNLP
operator|.
name|getSentenceModel
argument_list|(
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|model
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Sentence Detection Model {} for lanugage '{}' version: {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|model
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
block|,
name|model
operator|.
name|getLanguage
argument_list|()
block|,
name|model
operator|.
name|getVersion
argument_list|()
operator|!=
literal|null
condition|?
name|model
operator|.
name|getVersion
argument_list|()
else|:
literal|"undefined"
block|}
argument_list|)
expr_stmt|;
return|return
operator|new
name|SentenceDetectorME
argument_list|(
name|model
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{         }
name|log
operator|.
name|debug
argument_list|(
literal|"Sentence Detection Model for Language '{}' not available."
argument_list|,
name|language
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
specifier|private
name|POSTagger
name|getPOSTagger
parameter_list|(
name|String
name|language
parameter_list|)
block|{
name|String
name|modelName
init|=
name|languageConfig
operator|.
name|getParameter
argument_list|(
name|language
argument_list|,
name|MODEL_NAME_PARAM
argument_list|)
decl_stmt|;
try|try
block|{
name|POSModel
name|model
decl_stmt|;
if|if
condition|(
name|modelName
operator|==
literal|null
condition|)
block|{
comment|//use the default
name|model
operator|=
name|openNLP
operator|.
name|getPartOfSpeachModel
argument_list|(
name|language
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|model
operator|=
name|openNLP
operator|.
name|getModel
argument_list|(
name|POSModel
operator|.
name|class
argument_list|,
name|modelName
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|model
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"POS Tagger Model {} for lanugage '{}' version: {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|model
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
block|,
name|model
operator|.
name|getLanguage
argument_list|()
block|,
name|model
operator|.
name|getVersion
argument_list|()
operator|!=
literal|null
condition|?
name|model
operator|.
name|getVersion
argument_list|()
else|:
literal|"undefined"
block|}
argument_list|)
expr_stmt|;
return|return
operator|new
name|POSTaggerME
argument_list|(
name|model
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to load POS model for language '"
operator|+
name|language
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"POS tagging Model for Language '{}' not available."
argument_list|,
name|language
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
specifier|private
name|Tokenizer
name|getTokenizer
parameter_list|(
name|String
name|language
parameter_list|)
block|{
return|return
name|openNLP
operator|.
name|getTokenizer
argument_list|(
name|language
argument_list|)
return|;
block|}
block|}
end_class

end_unit

