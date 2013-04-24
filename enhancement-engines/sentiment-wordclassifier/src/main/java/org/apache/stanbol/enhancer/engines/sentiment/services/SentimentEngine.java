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
name|sentiment
operator|.
name|services
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
name|nlp
operator|.
name|NlpAnnotations
operator|.
name|SENTIMENT_ANNOTATION
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
name|getAnalysedText
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
name|ReferenceCardinality
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
name|ReferencePolicy
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
name|ReferenceStrategy
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
name|engines
operator|.
name|sentiment
operator|.
name|api
operator|.
name|SentimentClassifier
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
comment|/**  * A Stanbol engine that associates sentiment values with the tokens created by the POS tagging engine.  * Sentiment values are added to the POSContentPart of the content item and can by further analysed by other  * engines, e.g. to compute sentiment values for the whole content item or in relation to certain nouns.  *<p/>  * The configuration allows specifying whether to analyse all words or only adjectives and nouns (a typical case).  *<p/>  * Currently, sentiment analysis is available for English and for German language. It uses the following word lists:  *<ul>  *<li>English: SentiWordNet (http://wordnet.princeton.edu/), license allows commercial use</li>  *<li>German: SentiWS (http://wortschatz.informatik.uni-leipzig.de/download/), license does NOT allow commercial use</li>  *</ul>  *<p/>  * Author: Sebastian Schaffert  */
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
literal|"sentiment-wordclassifier"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SentimentEngine
operator|.
name|CONFIG_LANGUAGES
argument_list|,
name|value
operator|=
block|{
name|SentimentEngine
operator|.
name|DEFAULT_LANGUAGE_CONFIG
block|}
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SentimentEngine
operator|.
name|CONFIG_ADJECTIVES
argument_list|,
name|boolValue
operator|=
name|SentimentEngine
operator|.
name|DEFAULT_PROCESS_ADJECTIVES_ONLY
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SentimentEngine
operator|.
name|CONFIG_MIN_POS_CONFIDENCE
argument_list|,
name|doubleValue
operator|=
name|SentimentEngine
operator|.
name|DEFAULT_MIN_POS_CONFIDNECE
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
name|SentimentEngine
extends|extends
name|AbstractEnhancementEngine
argument_list|<
name|RuntimeException
argument_list|,
name|RuntimeException
argument_list|>
implements|implements
name|ServiceProperties
block|{
comment|/**      * Language configuration. Takes a list of ISO language codes of supported languages. Currently supported      * are the languages given as default value.      */
specifier|public
specifier|static
specifier|final
name|String
name|CONFIG_LANGUAGES
init|=
literal|"org.apache.stanbol.enhancer.sentiment.languages"
decl_stmt|;
comment|/**      * When set to true, only adjectives and nouns will be considered in sentiment analysis.      */
specifier|public
specifier|static
specifier|final
name|String
name|CONFIG_ADJECTIVES
init|=
literal|"org.apache.stanbol.enhancer.sentiment.adjectives"
decl_stmt|;
comment|/**      * POS tags that are not selected by {@link SentimentClassifier#isAdjective(PosTag)}      * or {@link SentimentClassifier#isNoun(PosTag)} are ignored if their confidence      * is&gt= the configured values. If there are multiple POS tag suggestions,       * that Words that do have a suitable TAG are still considered if the      * confidence of the fitting tag is&gt;= {min-pos-confidence}/2      */
specifier|public
specifier|static
specifier|final
name|String
name|CONFIG_MIN_POS_CONFIDENCE
init|=
literal|"org.apache.stanbol.enhancer.sentiment.min-pos-confidence"
decl_stmt|;
name|boolean
name|debugSentiments
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_LANGUAGE_CONFIG
init|=
literal|"*"
decl_stmt|;
specifier|private
name|LanguageConfiguration
name|langaugeConfig
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
name|DEFAULT_LANGUAGE_CONFIG
block|}
argument_list|)
decl_stmt|;
comment|/**      * The minimum confidence of POS tags so that a token is NOT processed if      * the {@link LexicalCategory} is NOT {@link LexicalCategory#Adjective} (or      * {@link LexicalCategory#Noun Noun} if {@link #CONFIG_ADJECTIVES} is      * deactivated) - default: 0.8<p>      */
specifier|public
specifier|static
specifier|final
name|double
name|DEFAULT_MIN_POS_CONFIDNECE
init|=
literal|0.8
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_PROCESS_ADJECTIVES_ONLY
init|=
literal|false
decl_stmt|;
comment|/**      * Service Properties used by this Engine      */
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|SERVICE_PROPERTIES
decl_stmt|;
static|static
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|props
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|props
operator|.
name|put
argument_list|(
name|ServiceProperties
operator|.
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|,
name|ServiceProperties
operator|.
name|ORDERING_NLP_POS
operator|-
literal|1
argument_list|)
expr_stmt|;
comment|//after POS tagging
name|props
operator|.
name|put
argument_list|(
name|NlpServiceProperties
operator|.
name|ENHANCEMENT_ENGINE_NLP_ROLE
argument_list|,
name|NlpProcessingRole
operator|.
name|SentimentTagging
argument_list|)
expr_stmt|;
name|SERVICE_PROPERTIES
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|props
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SentimentEngine
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * {@link SentimentClassifier} are now OSGI services and injected via events      * (calls to {@link #bindClassifier(SentimentClassifier)} and       * {@link #unbindClassifier(SentimentClassifier)}) as soon as they become      * (un)available.      */
annotation|@
name|Reference
argument_list|(
name|referenceInterface
operator|=
name|SentimentClassifier
operator|.
name|class
argument_list|,
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_MULTIPLE
argument_list|,
name|bind
operator|=
literal|"bindClassifier"
argument_list|,
name|unbind
operator|=
literal|"unbindClassifier"
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|,
name|strategy
operator|=
name|ReferenceStrategy
operator|.
name|EVENT
argument_list|)
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|SentimentClassifier
argument_list|>
name|classifiers
init|=
name|Collections
operator|.
name|synchronizedMap
argument_list|(
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|SentimentClassifier
argument_list|>
argument_list|()
argument_list|)
decl_stmt|;
comment|/** bind method for {@link #classifiers} */
specifier|protected
name|void
name|bindClassifier
parameter_list|(
name|SentimentClassifier
name|classifier
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"  ... bind Sentiment Classifier {} for language {}"
argument_list|,
name|classifier
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|classifier
operator|.
name|getLanguage
argument_list|()
argument_list|)
expr_stmt|;
synchronized|synchronized
init|(
name|classifiers
init|)
block|{
name|SentimentClassifier
name|old
init|=
name|classifiers
operator|.
name|put
argument_list|(
name|classifier
operator|.
name|getLanguage
argument_list|()
argument_list|,
name|classifier
argument_list|)
decl_stmt|;
if|if
condition|(
name|old
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Replaced Sentiment Classifier for language {} (old: {}, new: {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|old
operator|.
name|getLanguage
argument_list|()
block|,
name|old
block|,
name|classifier
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/** unbind method for {@link #classifiers} */
specifier|protected
name|void
name|unbindClassifier
parameter_list|(
name|SentimentClassifier
name|classifier
parameter_list|)
block|{
name|String
name|lang
init|=
name|classifier
operator|.
name|getLanguage
argument_list|()
decl_stmt|;
synchronized|synchronized
init|(
name|classifiers
init|)
block|{
name|SentimentClassifier
name|current
init|=
name|classifiers
operator|.
name|remove
argument_list|(
name|lang
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|classifier
operator|.
name|equals
argument_list|(
name|current
argument_list|)
comment|//the current is not the parsed one
operator|&&
name|current
operator|!=
literal|null
condition|)
block|{
name|classifiers
operator|.
name|put
argument_list|(
name|lang
argument_list|,
name|current
argument_list|)
expr_stmt|;
comment|//re-add the value
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"  ... unbind Sentiment Classifier {} for language {}"
argument_list|,
name|classifier
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|lang
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * The processed {@link LexicalCategory LexicalCategories}.      */
name|boolean
name|adjectivesOnly
init|=
name|DEFAULT_PROCESS_ADJECTIVES_ONLY
decl_stmt|;
comment|/**      * The minimum {@link PosTag} value {@link Value#probability() confidence}.<p>      * This means that if the {@link Value#probability() confidence} of a      * {@link NlpAnnotations#POS_ANNOTATION}s (returned by      * {@link Token#getAnnotations(Annotation)}) is greater than       * {@link #minPOSConfidence} that the result of       * {@link SentimentClassifier#isAdjective(PosTag)} (and       * {@link SentimentClassifier#isNoun(PosTag)}  - if #CONFIG_ADJECTIVES is       * deactivated) is used to decide if a Token needs to be processed or not.      * Otherwise further {@link NlpAnnotations#POS_ANNOTATION}s are analysed for      * processable POS tags. Processable POS tags are accepted until      *<code>{@link #minPOSConfidence}/2</code>.        */
specifier|private
name|double
name|minPOSConfidence
init|=
name|DEFAULT_MIN_POS_CONFIDNECE
decl_stmt|;
comment|/**      * Indicate if this engine can enhance supplied ContentItem, and if it      * suggests enhancing it synchronously or asynchronously. The      * {@link org.apache.stanbol.enhancer.servicesapi.EnhancementJobManager} can force sync/async mode if desired, it is      * just a suggestion from the engine.      *<p/>      * Returns {@link EnhancementEngine}#ENHANCE_ASYNC if<ul>      *<li> the {@link AnalysedText} content part is present      *<li> the language of the content is known      *<li> the language is active based on the language configuration and      *<li> a sentiment classifier is available for the language      *</ul>      *      * @throws org.apache.stanbol.enhancer.servicesapi.EngineException      *          if the introspecting process of the content item      *          fails      */
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
if|if
condition|(
name|getAnalysedText
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
literal|false
argument_list|)
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
name|classifiers
operator|.
name|containsKey
argument_list|(
name|language
argument_list|)
condition|)
block|{
return|return
name|ENHANCE_ASYNC
return|;
block|}
else|else
block|{
return|return
name|CANNOT_ENHANCE
return|;
block|}
block|}
comment|/**      * Compute enhancements for supplied ContentItem. The results of the process      * are expected to be stored in the metadata of the content item.      *<p/>      * The client (usually an {@link org.apache.stanbol.enhancer.servicesapi.EnhancementJobManager}) should take care of      * persistent storage of the enhanced {@link org.apache.stanbol.enhancer.servicesapi.ContentItem}.      *      * @throws org.apache.stanbol.enhancer.servicesapi.EngineException      *          if the underlying process failed to work as      *          expected      */
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
name|analysedText
init|=
name|getAnalysedText
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
literal|true
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
name|SentimentClassifier
name|classifier
init|=
name|classifiers
operator|.
name|get
argument_list|(
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|classifier
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Sentiment Classifier for language '"
operator|+
name|language
operator|+
literal|"' not available. As this is also checked in "
operator|+
literal|" canEnhance this may indicate an Bug in the used "
operator|+
literal|"EnhancementJobManager!"
argument_list|)
throw|;
block|}
comment|//TODO: locking for AnalysedText not yet defined
comment|//        ci.getLock().writeLock().lock();
comment|//        try {
name|Iterator
argument_list|<
name|Token
argument_list|>
name|tokens
init|=
name|analysedText
operator|.
name|getTokens
argument_list|()
decl_stmt|;
while|while
condition|(
name|tokens
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Token
name|token
init|=
name|tokens
operator|.
name|next
argument_list|()
decl_stmt|;
name|boolean
name|process
init|=
operator|!
name|adjectivesOnly
decl_stmt|;
if|if
condition|(
operator|!
name|process
condition|)
block|{
comment|//check POS types
name|Iterator
argument_list|<
name|Value
argument_list|<
name|PosTag
argument_list|>
argument_list|>
name|posTags
init|=
name|token
operator|.
name|getAnnotations
argument_list|(
name|NlpAnnotations
operator|.
name|POS_ANNOTATION
argument_list|)
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|boolean
name|ignore
init|=
literal|false
decl_stmt|;
while|while
condition|(
operator|!
name|ignore
operator|&&
operator|!
name|process
operator|&&
name|posTags
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Value
argument_list|<
name|PosTag
argument_list|>
name|value
init|=
name|posTags
operator|.
name|next
argument_list|()
decl_stmt|;
name|PosTag
name|tag
init|=
name|value
operator|.
name|value
argument_list|()
decl_stmt|;
name|boolean
name|state
init|=
name|classifier
operator|.
name|isAdjective
argument_list|(
name|tag
argument_list|)
operator|||
name|classifier
operator|.
name|isNoun
argument_list|(
name|tag
argument_list|)
decl_stmt|;
name|ignore
operator|=
operator|!
name|state
operator|&&
operator|(
name|value
operator|.
name|probability
argument_list|()
operator|==
name|Value
operator|.
name|UNKNOWN_PROBABILITY
operator|||
name|value
operator|.
name|probability
argument_list|()
operator|>=
name|minPOSConfidence
operator|)
expr_stmt|;
name|process
operator|=
name|state
operator|&&
operator|(
name|value
operator|.
name|probability
argument_list|()
operator|==
name|Value
operator|.
name|UNKNOWN_PROBABILITY
operator|||
name|value
operator|.
name|probability
argument_list|()
operator|>=
operator|(
name|minPOSConfidence
operator|/
literal|2.0
operator|)
operator|)
expr_stmt|;
block|}
block|}
comment|//else process all tokens ... no POS tag checking needed
if|if
condition|(
name|process
condition|)
block|{
name|double
name|sentiment
init|=
name|classifier
operator|.
name|classifyWord
argument_list|(
name|token
operator|.
name|getSpan
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|sentiment
operator|!=
literal|0.0
condition|)
block|{
name|token
operator|.
name|addAnnotation
argument_list|(
name|SENTIMENT_ANNOTATION
argument_list|,
operator|new
name|Value
argument_list|<
name|Double
argument_list|>
argument_list|(
name|sentiment
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//else do not set sentiments with 0.0
block|}
block|}
comment|//        } finally {
comment|//            ci.getLock().writeLock().unlock();
comment|//        }
block|}
comment|/**      * Activate and read the properties. Configures and initialises a ChunkerHelper for each language configured in      * CONFIG_LANGUAGES.      *      * @param ce the {@link org.osgi.service.component.ComponentContext}      */
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
comment|//parse the configured languages
name|langaugeConfig
operator|.
name|setConfiguration
argument_list|(
name|properties
argument_list|)
expr_stmt|;
comment|//set the processed lexical categories
name|Object
name|value
init|=
name|properties
operator|.
name|get
argument_list|(
name|CONFIG_ADJECTIVES
argument_list|)
decl_stmt|;
name|adjectivesOnly
operator|=
name|value
operator|instanceof
name|Boolean
condition|?
operator|(
name|Boolean
operator|)
name|value
else|:
name|value
operator|!=
literal|null
condition|?
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
else|:
name|DEFAULT_PROCESS_ADJECTIVES_ONLY
expr_stmt|;
comment|//set minimum POS confidence
name|value
operator|=
name|properties
operator|.
name|get
argument_list|(
name|CONFIG_MIN_POS_CONFIDENCE
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|Number
condition|)
block|{
name|minPOSConfidence
operator|=
operator|(
operator|(
name|Number
operator|)
name|value
operator|)
operator|.
name|doubleValue
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
try|try
block|{
name|minPOSConfidence
operator|=
name|Double
operator|.
name|parseDouble
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
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
name|CONFIG_MIN_POS_CONFIDENCE
argument_list|,
literal|"Unable to parsed minimum POS confidence value from '"
operator|+
name|value
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
else|else
block|{
name|minPOSConfidence
operator|=
name|DEFAULT_MIN_POS_CONFIDNECE
expr_stmt|;
block|}
if|if
condition|(
name|minPOSConfidence
operator|<=
literal|0
operator|||
name|minPOSConfidence
operator|>=
literal|1
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|CONFIG_MIN_POS_CONFIDENCE
argument_list|,
literal|"The configured minimum POS confidence value '"
operator|+
name|minPOSConfidence
operator|+
literal|"' MUST BE> 0 and< 1!"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
comment|//remove remaining classifiers
name|this
operator|.
name|classifiers
operator|.
name|clear
argument_list|()
expr_stmt|;
name|langaugeConfig
operator|.
name|setDefault
argument_list|()
expr_stmt|;
name|super
operator|.
name|deactivate
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
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
name|SERVICE_PROPERTIES
return|;
block|}
block|}
end_class

end_unit

