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
name|sentiment
operator|.
name|summarize
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
name|PHRASE_ANNOTATION
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
name|servicesapi
operator|.
name|ServiceProperties
operator|.
name|ENHANCEMENT_ENGINE_ORDERING
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
name|ServiceProperties
operator|.
name|ORDERING_EXTRACTION_ENHANCEMENT
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
name|helper
operator|.
name|EnhancementEngineHelper
operator|.
name|createTextEnhancement
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
name|Collections
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
name|NavigableMap
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
name|phrase
operator|.
name|PhraseTag
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
name|utils
operator|.
name|NIFHelper
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
name|NamespaceEnum
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
comment|/**  * {@link EnhancementEngine} that summarizes {@link Token} level  * Sentiment tags for NounPhraces, Sentences and the whole  * Content.  * @author Rupert Westenthaler  *  */
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
argument_list|,
name|configurationFactory
operator|=
literal|true
argument_list|)
comment|//allow multiple instances to be configured
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
name|SentimentSummarizationEngine
operator|.
name|DEFAULT_ENGINE_NAME
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
name|SentimentSummarizationEngine
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
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_ENGINE_NAME
init|=
literal|"sentiment-summarization"
decl_stmt|;
comment|//TODO: change this to a real sentiment ontology
comment|/**      * The property used to write the sum of all positive classified words      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|POSITIVE_SENTIMENT_PROPERTY
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"positive-sentiment"
argument_list|)
decl_stmt|;
comment|/**      * The property used to write the sum of all negative classified words      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|NEGATIVE_SENTIMENT_PROPERTY
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"negative-sentiment"
argument_list|)
decl_stmt|;
comment|/**      * The sentiment of the section (sum of positive and negative classifications)      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|SENTIMENT_PROPERTY
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"sentiment"
argument_list|)
decl_stmt|;
comment|/**      * The dc:type value used for fise:TextAnnotations indicating a Sentiment      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|SENTIMENT_TYPE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"Sentiment"
argument_list|)
decl_stmt|;
name|boolean
name|writeNounPhraseSentiments
init|=
literal|true
decl_stmt|;
name|boolean
name|writeSentenceSentimets
init|=
literal|true
decl_stmt|;
name|boolean
name|writeTextSectionSentiments
init|=
literal|true
decl_stmt|;
name|boolean
name|wirteDocumentSentiments
init|=
literal|true
decl_stmt|;
name|boolean
name|writeTextSentiments
init|=
literal|true
decl_stmt|;
specifier|private
specifier|final
name|LiteralFactory
name|lf
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
annotation|@
name|Override
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|log
operator|.
name|info
argument_list|(
literal|" activate {} with config {}"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|ctx
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
name|super
operator|.
name|activate
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
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
name|int
name|canEnhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
return|return
name|NlpEngineHelper
operator|.
name|getAnalysedText
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
literal|false
argument_list|)
operator|!=
literal|null
condition|?
name|ENHANCE_ASYNC
else|:
name|CANNOT_ENHANCE
return|;
block|}
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
name|NlpEngineHelper
operator|.
name|getAnalysedText
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
literal|true
argument_list|)
decl_stmt|;
comment|//configure the spanTypes based on the configuration
name|EnumSet
argument_list|<
name|Span
operator|.
name|SpanTypeEnum
argument_list|>
name|spanTypes
init|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|SpanTypeEnum
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|writeNounPhraseSentiments
condition|)
block|{
name|spanTypes
operator|.
name|add
argument_list|(
name|SpanTypeEnum
operator|.
name|Chunk
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|writeSentenceSentimets
condition|)
block|{
name|spanTypes
operator|.
name|add
argument_list|(
name|SpanTypeEnum
operator|.
name|Sentence
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|writeTextSectionSentiments
condition|)
block|{
name|spanTypes
operator|.
name|add
argument_list|(
name|SpanTypeEnum
operator|.
name|TextSection
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|writeTextSentiments
condition|)
block|{
name|spanTypes
operator|.
name|add
argument_list|(
name|SpanTypeEnum
operator|.
name|Text
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|SentimentInfo
argument_list|>
name|sentimentInfos
init|=
name|summarizeSentiments
argument_list|(
name|at
argument_list|,
name|spanTypes
argument_list|)
decl_stmt|;
name|String
name|detectedLang
init|=
name|EnhancementEngineHelper
operator|.
name|getLanguage
argument_list|(
name|ci
argument_list|)
decl_stmt|;
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|writeSentimentEnhancements
argument_list|(
name|ci
argument_list|,
name|sentimentInfos
argument_list|,
name|at
argument_list|,
name|detectedLang
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|Language
argument_list|(
name|detectedLang
argument_list|)
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
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
name|Collections
operator|.
name|singletonMap
argument_list|(
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|,
operator|(
name|Object
operator|)
name|ORDERING_EXTRACTION_ENHANCEMENT
argument_list|)
return|;
block|}
comment|/**      *       * @param at      * @return      */
specifier|private
name|List
argument_list|<
name|SentimentInfo
argument_list|>
name|summarizeSentiments
parameter_list|(
name|AnalysedText
name|at
parameter_list|,
name|EnumSet
argument_list|<
name|SpanTypeEnum
argument_list|>
name|spanTypes
parameter_list|)
block|{
name|spanTypes
operator|.
name|add
argument_list|(
name|SpanTypeEnum
operator|.
name|Token
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|Span
argument_list|>
name|tokenIt
init|=
name|at
operator|.
name|getEnclosed
argument_list|(
name|spanTypes
argument_list|)
decl_stmt|;
comment|// use double array of length 1 as value to avoid final double values
comment|//List with the section that contain sentiments
name|List
argument_list|<
name|SentimentInfo
argument_list|>
name|sentimentInfos
init|=
operator|new
name|ArrayList
argument_list|<
name|SentimentInfo
argument_list|>
argument_list|()
decl_stmt|;
name|NavigableMap
argument_list|<
name|Span
argument_list|,
name|SentimentInfo
argument_list|>
name|activeSpans
init|=
operator|new
name|TreeMap
argument_list|<
name|Span
argument_list|,
name|SentimentInfo
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|spanTypes
operator|.
name|contains
argument_list|(
name|SpanTypeEnum
operator|.
name|Text
argument_list|)
condition|)
block|{
name|activeSpans
operator|.
name|put
argument_list|(
name|at
argument_list|,
operator|new
name|SentimentInfo
argument_list|(
name|at
argument_list|)
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
name|tokenIt
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Span
name|span
init|=
name|tokenIt
operator|.
name|next
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|span
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|Token
case|:
name|Value
argument_list|<
name|Double
argument_list|>
name|sentiment
init|=
name|span
operator|.
name|getAnnotation
argument_list|(
name|SENTIMENT_ANNOTATION
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Entry
argument_list|<
name|Span
argument_list|,
name|SentimentInfo
argument_list|>
argument_list|>
name|entries
init|=
name|activeSpans
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
if|if
condition|(
name|sentiment
operator|!=
literal|null
condition|)
block|{
while|while
condition|(
name|entries
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Entry
argument_list|<
name|Span
argument_list|,
name|SentimentInfo
argument_list|>
name|entry
init|=
name|entries
operator|.
name|next
argument_list|()
decl_stmt|;
comment|//if(span.getEnd()> entry.getKey().getEnd()){ //fully enclosed
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|getEnd
argument_list|()
operator|>
name|span
operator|.
name|getStart
argument_list|()
condition|)
block|{
comment|//partly enclosed
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|addSentiment
argument_list|(
name|sentiment
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// span has completed
if|if
condition|(
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|hasSentiment
argument_list|()
condition|)
block|{
comment|//if a sentiment was found
comment|//add it to the list
name|sentimentInfos
operator|.
name|add
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|entries
operator|.
name|remove
argument_list|()
expr_stmt|;
comment|// remove completed
block|}
block|}
block|}
break|break;
case|case
name|Chunk
case|:
name|Value
argument_list|<
name|PhraseTag
argument_list|>
name|phraseTag
init|=
name|span
operator|.
name|getAnnotation
argument_list|(
name|PHRASE_ANNOTATION
argument_list|)
decl_stmt|;
if|if
condition|(
name|phraseTag
operator|.
name|value
argument_list|()
operator|.
name|getCategory
argument_list|()
operator|==
name|LexicalCategory
operator|.
name|Noun
condition|)
block|{
comment|//noun phrase
name|activeSpans
operator|.
name|put
argument_list|(
name|span
argument_list|,
operator|new
name|SentimentInfo
argument_list|(
operator|(
name|Section
operator|)
name|span
argument_list|)
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|Sentence
case|:
name|activeSpans
operator|.
name|put
argument_list|(
name|span
argument_list|,
operator|new
name|SentimentInfo
argument_list|(
operator|(
name|Section
operator|)
name|span
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|TextSection
case|:
name|activeSpans
operator|.
name|put
argument_list|(
name|span
argument_list|,
operator|new
name|SentimentInfo
argument_list|(
operator|(
name|Section
operator|)
name|span
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
block|}
comment|//finally cleanup still active Sections
for|for
control|(
name|SentimentInfo
name|sentInfo
range|:
name|activeSpans
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|sentInfo
operator|.
name|hasSentiment
argument_list|()
condition|)
block|{
name|sentimentInfos
operator|.
name|add
argument_list|(
name|sentInfo
argument_list|)
expr_stmt|;
block|}
comment|//else no sentiment in that section
block|}
return|return
name|sentimentInfos
return|;
block|}
specifier|private
name|void
name|writeSentimentEnhancements
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|List
argument_list|<
name|SentimentInfo
argument_list|>
name|sentimentInfos
parameter_list|,
name|AnalysedText
name|at
parameter_list|,
name|Language
name|lang
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
name|MGraph
name|metadata
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
for|for
control|(
name|SentimentInfo
name|sentInfo
range|:
name|sentimentInfos
control|)
block|{
name|UriRef
name|enh
init|=
name|createTextEnhancement
argument_list|(
name|ci
argument_list|,
name|this
argument_list|)
decl_stmt|;
if|if
condition|(
name|sentInfo
operator|.
name|getSection
argument_list|()
operator|.
name|getType
argument_list|()
operator|==
name|SpanTypeEnum
operator|.
name|Chunk
condition|)
block|{
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enh
argument_list|,
name|ENHANCER_SELECTED_TEXT
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|sentInfo
operator|.
name|getSection
argument_list|()
operator|.
name|getSpan
argument_list|()
argument_list|,
name|lang
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enh
argument_list|,
name|ENHANCER_SELECTION_CONTEXT
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|getSelectionContext
argument_list|(
name|at
operator|.
name|getSpan
argument_list|()
argument_list|,
name|sentInfo
operator|.
name|getSection
argument_list|()
operator|.
name|getSpan
argument_list|()
argument_list|,
name|sentInfo
operator|.
name|getSection
argument_list|()
operator|.
name|getStart
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//NOTE: fall through intended!
block|}
elseif|else
if|if
condition|(
name|sentInfo
operator|.
name|getSection
argument_list|()
operator|.
name|getType
argument_list|()
operator|!=
name|SpanTypeEnum
operator|.
name|Text
condition|)
block|{
comment|//sentence, textsection
comment|//For longer selections it does not make sense to include selection context
comment|//and the selected text.
comment|//We can add prefix, suffix, selection-start, selection-end
comment|//as soon as we use the new TextAnnotation model
block|}
comment|//add start/end positions
if|if
condition|(
name|sentInfo
operator|.
name|getSection
argument_list|()
operator|.
name|getType
argument_list|()
operator|!=
name|SpanTypeEnum
operator|.
name|Text
condition|)
block|{
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enh
argument_list|,
name|ENHANCER_START
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|sentInfo
operator|.
name|getSection
argument_list|()
operator|.
name|getStart
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enh
argument_list|,
name|ENHANCER_END
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|sentInfo
operator|.
name|getSection
argument_list|()
operator|.
name|getEnd
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//else do not add start/end pos for sentiment of the whole text
comment|//add the sentiment information
if|if
condition|(
name|sentInfo
operator|.
name|getPositive
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enh
argument_list|,
name|POSITIVE_SENTIMENT_PROPERTY
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|sentInfo
operator|.
name|getPositive
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|sentInfo
operator|.
name|getNegative
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enh
argument_list|,
name|NEGATIVE_SENTIMENT_PROPERTY
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|sentInfo
operator|.
name|getNegative
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enh
argument_list|,
name|SENTIMENT_PROPERTY
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|sentInfo
operator|.
name|getSentiment
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//add the Sentiment type as well as the type of the SSO Ontology
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enh
argument_list|,
name|DC_TYPE
argument_list|,
name|SENTIMENT_TYPE
argument_list|)
argument_list|)
expr_stmt|;
name|UriRef
name|ssoType
init|=
name|NIFHelper
operator|.
name|SPAN_TYPE_TO_SSO_TYPE
operator|.
name|get
argument_list|(
name|sentInfo
operator|.
name|getSection
argument_list|()
operator|.
name|getType
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|ssoType
operator|!=
literal|null
condition|)
block|{
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enh
argument_list|,
name|DC_TYPE
argument_list|,
name|ssoType
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * The maximum size of the preix/suffix for the selection context      */
specifier|private
specifier|static
specifier|final
name|int
name|DEFAULT_SELECTION_CONTEXT_PREFIX_SUFFIX_SIZE
init|=
literal|50
decl_stmt|;
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
block|}
end_class

end_unit
