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
name|smartcn
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
name|Map
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
name|commons
operator|.
name|io
operator|.
name|input
operator|.
name|CharSequenceReader
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
name|lucene
operator|.
name|analysis
operator|.
name|TokenStream
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|Tokenizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|cn
operator|.
name|smart
operator|.
name|SentenceTokenizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|cn
operator|.
name|smart
operator|.
name|WordTokenFilter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|tokenattributes
operator|.
name|CharTermAttribute
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|tokenattributes
operator|.
name|OffsetAttribute
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|tokenattributes
operator|.
name|TypeAttribute
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
comment|/**  * Sentence detection and word tokenizer for Chinese based on the Solr/Lucene  * smartcn analysers.  *   * @author Rupert Westenthaler  */
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
literal|"smartcn-token"
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
literal|0
argument_list|)
comment|//give the default instance a ranking< 0
block|}
argument_list|)
specifier|public
class|class
name|SmartcnTokenizerEngine
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
name|ORDERING_NLP_TOKENIZING
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
name|Tokenizing
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
name|SmartcnTokenizerEngine
operator|.
name|class
argument_list|)
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
literal|"zh"
operator|.
name|equals
argument_list|(
name|language
argument_list|)
operator|||
operator|(
name|language
operator|!=
literal|null
operator|&&
name|language
operator|.
name|startsWith
argument_list|(
literal|"zh-"
argument_list|)
operator|)
condition|)
block|{
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
else|else
block|{
return|return
name|CANNOT_ENHANCE
return|;
block|}
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
specifier|final
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
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
operator|(
literal|"zh"
operator|.
name|equals
argument_list|(
name|language
argument_list|)
operator|||
operator|(
name|language
operator|!=
literal|null
operator|&&
name|language
operator|.
name|startsWith
argument_list|(
literal|"zh-"
argument_list|)
operator|)
operator|)
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"The detected language is NOT 'zh'! "
operator|+
literal|"As this is also checked within the #canEnhance(..) method this "
operator|+
literal|"indicates an Bug in the used EnhancementJobManager implementation. "
operator|+
literal|"Please report this on the dev@apache.stanbol.org or create an "
operator|+
literal|"JIRA issue about this."
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|at
operator|.
name|getSentences
argument_list|()
operator|.
name|hasNext
argument_list|()
condition|)
block|{
comment|//no sentences  ... use this engine to detect
comment|//first the sentences
name|TokenStream
name|sentences
init|=
operator|new
name|SentenceTokenizer
argument_list|(
operator|new
name|CharSequenceReader
argument_list|(
name|at
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
try|try
block|{
while|while
condition|(
name|sentences
operator|.
name|incrementToken
argument_list|()
condition|)
block|{
name|OffsetAttribute
name|offset
init|=
name|sentences
operator|.
name|addAttribute
argument_list|(
name|OffsetAttribute
operator|.
name|class
argument_list|)
decl_stmt|;
name|Sentence
name|s
init|=
name|at
operator|.
name|addSentence
argument_list|(
name|offset
operator|.
name|startOffset
argument_list|()
argument_list|,
name|offset
operator|.
name|endOffset
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|log
operator|.
name|isTraceEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|trace
argument_list|(
literal|"detected {}:{}"
argument_list|,
name|s
argument_list|,
name|s
operator|.
name|getSpan
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|String
name|message
init|=
name|String
operator|.
name|format
argument_list|(
literal|"IOException while reading from "
operator|+
literal|"CharSequenceReader of AnalyzedText for ContentItem %s"
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
name|log
operator|.
name|error
argument_list|(
name|message
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|EngineException
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
name|message
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|//now the tokens
name|TokenStream
name|tokens
init|=
operator|new
name|WordTokenFilter
argument_list|(
operator|new
name|AnalyzedTextSentenceTokenizer
argument_list|(
name|at
argument_list|)
argument_list|)
decl_stmt|;
try|try
block|{
while|while
condition|(
name|tokens
operator|.
name|incrementToken
argument_list|()
condition|)
block|{
name|OffsetAttribute
name|offset
init|=
name|tokens
operator|.
name|addAttribute
argument_list|(
name|OffsetAttribute
operator|.
name|class
argument_list|)
decl_stmt|;
name|Token
name|t
init|=
name|at
operator|.
name|addToken
argument_list|(
name|offset
operator|.
name|startOffset
argument_list|()
argument_list|,
name|offset
operator|.
name|endOffset
argument_list|()
argument_list|)
decl_stmt|;
name|log
operator|.
name|trace
argument_list|(
literal|"detected {}"
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|String
name|message
init|=
name|String
operator|.
name|format
argument_list|(
literal|"IOException while reading from "
operator|+
literal|"CharSequenceReader of AnalyzedText for ContentItem %s"
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
name|log
operator|.
name|error
argument_list|(
name|message
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|EngineException
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
name|message
argument_list|,
name|e
argument_list|)
throw|;
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
literal|"activating smartcn tokenizing engine"
argument_list|)
expr_stmt|;
name|super
operator|.
name|activate
argument_list|(
name|ce
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
name|super
operator|.
name|deactivate
argument_list|(
name|context
argument_list|)
expr_stmt|;
block|}
comment|/**      * This is an internal helper class that avoids to execute sentences      * using the {@link SentenceTokenizer} twice.      * @author Rupert Westenthaler      *      */
specifier|protected
specifier|final
class|class
name|AnalyzedTextSentenceTokenizer
extends|extends
name|Tokenizer
block|{
specifier|private
specifier|final
name|AnalysedText
name|at
decl_stmt|;
specifier|private
specifier|final
name|CharTermAttribute
name|termAtt
init|=
name|addAttribute
argument_list|(
name|CharTermAttribute
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|OffsetAttribute
name|offsetAtt
init|=
name|addAttribute
argument_list|(
name|OffsetAttribute
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|TypeAttribute
name|typeAtt
init|=
name|addAttribute
argument_list|(
name|TypeAttribute
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Iterator
argument_list|<
name|Sentence
argument_list|>
name|sentences
decl_stmt|;
specifier|private
name|Sentence
name|sentence
init|=
literal|null
decl_stmt|;
specifier|protected
name|AnalyzedTextSentenceTokenizer
parameter_list|(
name|AnalysedText
name|at
parameter_list|)
block|{
name|this
operator|.
name|at
operator|=
name|at
expr_stmt|;
name|sentences
operator|=
name|at
operator|.
name|getSentences
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|incrementToken
parameter_list|()
throws|throws
name|IOException
block|{
if|if
condition|(
name|sentences
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|sentence
operator|=
name|sentences
operator|.
name|next
argument_list|()
expr_stmt|;
name|termAtt
operator|.
name|setEmpty
argument_list|()
operator|.
name|append
argument_list|(
name|sentence
operator|.
name|getSpan
argument_list|()
argument_list|)
expr_stmt|;
name|offsetAtt
operator|.
name|setOffset
argument_list|(
name|sentence
operator|.
name|getStart
argument_list|()
argument_list|,
name|sentence
operator|.
name|getEnd
argument_list|()
argument_list|)
expr_stmt|;
name|typeAtt
operator|.
name|setType
argument_list|(
literal|"sentence"
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|end
parameter_list|()
throws|throws
name|IOException
block|{
comment|// set final offset
name|offsetAtt
operator|.
name|setOffset
argument_list|(
name|at
operator|.
name|getEnd
argument_list|()
argument_list|,
name|at
operator|.
name|getEnd
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|reset
parameter_list|()
throws|throws
name|IOException
block|{
name|super
operator|.
name|reset
argument_list|()
expr_stmt|;
name|sentences
operator|=
name|at
operator|.
name|getSentences
argument_list|()
expr_stmt|;
name|termAtt
operator|.
name|setEmpty
argument_list|()
expr_stmt|;
name|offsetAtt
operator|.
name|setOffset
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|typeAtt
operator|.
name|setType
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

