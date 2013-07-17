begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
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
name|sentence
operator|.
name|impl
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
name|Map
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
comment|/**  * EnhancementEngine that uses the OpenNLP {@link SentenceDetector} to  * add {@link Sentence} annotations to the {@link AnalysedText}  * content part of the parsed {@link ContentItem}.<p>  * While the opennlp-pos engine does also support adding of {@link Sentence}  * annotations this engine can be used in cases where no POS tagging is  * needed. In addition this engine also allows to configure custom  * {@link SentenceModel}s with by using the {@link #MODEL_NAME_PARAM}  * with the language configuration  *<code><pre>  *     {lang};model={model-name}  *</pre></code>  */
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
literal|"opennlp-sentence"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|OpenNlpSentenceDetectionEngine
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
name|OpenNlpSentenceDetectionEngine
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
name|ORDERING_NLP_SENTENCE_DETECTION
argument_list|)
expr_stmt|;
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
name|SentenceDetection
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
comment|/**      * Language configuration. Takes a list of ISO language codes of supported languages. Currently supported      * are the languages given as default value.      */
specifier|public
specifier|static
specifier|final
name|String
name|CONFIG_LANGUAGES
init|=
literal|"org.apache.stanbol.enhancer.sentence.languages"
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
name|OpenNlpSentenceDetectionEngine
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
annotation|@
name|Reference
specifier|private
name|OpenNLP
name|openNLP
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
name|getSentenceDetector
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
literal|"> can NOT enhance ContentItem {} because no sentence "
operator|+
literal|"deteciton model for language {} is available."
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
name|SentenceDetector
name|sentenceDetector
init|=
name|getSentenceDetector
argument_list|(
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|sentenceDetector
operator|!=
literal|null
condition|)
block|{
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
comment|//detect sentences and add it to the AnalyzedText.
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
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"SentenceDetector model for language {} is no longer available. "
operator|+
literal|"This might happen if the model becomes unavailable during enhancement. "
operator|+
literal|"If this happens more often it might also indicate an bug in the used "
operator|+
literal|"EnhancementJobManager implementation as the availability is also checked "
operator|+
literal|"in the canEnhance(..) method of this Enhancement Engine."
argument_list|)
expr_stmt|;
block|}
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
comment|/**      * Obtains the {@link SentenceDetectorME} model for the given      * language form the {@link #openNLP} service. If a custom      * model is configured for the parsed language than it is      * loaded by using {@link OpenNLP#getModel(Class, String, Map)}      * otherwise the default model {@link OpenNLP#getSentenceDetector(String)}      * is retrieved      * @param language the language      * @return the model of<code>null</code> if non is available or      * an exception was encountered while loading      */
specifier|private
name|SentenceDetector
name|getSentenceDetector
parameter_list|(
name|String
name|language
parameter_list|)
block|{
name|SentenceModel
name|model
decl_stmt|;
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
if|if
condition|(
name|modelName
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|model
operator|=
name|openNLP
operator|.
name|getSentenceModel
argument_list|(
name|language
argument_list|)
expr_stmt|;
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
literal|"Unable to load default Sentence Detection model for language '"
operator|+
name|language
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
else|else
block|{
try|try
block|{
name|model
operator|=
name|openNLP
operator|.
name|getModel
argument_list|(
name|SentenceModel
operator|.
name|class
argument_list|,
name|modelName
argument_list|,
literal|null
argument_list|)
expr_stmt|;
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
literal|"Unable to load Sentence Detection model for language '"
operator|+
name|language
operator|+
literal|"' from the configured model '"
operator|+
name|modelName
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
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
block|}
end_class

end_unit

