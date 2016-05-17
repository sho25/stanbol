begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
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
name|nlp2rdf
operator|.
name|engine
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
name|servicesapi
operator|.
name|helper
operator|.
name|EnhancementEngineHelper
operator|.
name|DEFAULT_PREFIX_SUFFIX_LENGTH
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
name|RDF_TYPE
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
name|commons
operator|.
name|rdf
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
name|commons
operator|.
name|rdf
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
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
name|commons
operator|.
name|rdf
operator|.
name|impl
operator|.
name|utils
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
name|commons
operator|.
name|rdf
operator|.
name|impl
operator|.
name|utils
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
name|nif
operator|.
name|Nif20
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
comment|//allow multiple configuration
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|OPTIONAL
argument_list|)
comment|//create a default instance
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
literal|"nif20"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Nif20MetadataEngine
operator|.
name|PROP_SELECTOR_STATE
argument_list|,
name|boolValue
operator|=
name|Nif20MetadataEngine
operator|.
name|DEFAULT_SELECTOR_STATE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Nif20MetadataEngine
operator|.
name|PROP_CONTEXT_ONLY_URI_SCHEME
argument_list|,
name|boolValue
operator|=
name|Nif20MetadataEngine
operator|.
name|DEFAULT_CONTEXT_ONLY_URI_SCHEME
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Nif20MetadataEngine
operator|.
name|PROP_WRITE_STRING_TYPE
argument_list|,
name|boolValue
operator|=
name|Nif20MetadataEngine
operator|.
name|DEFAULT_WRITE_STRING_TYPE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Nif20MetadataEngine
operator|.
name|PROP_HIERARCHY_LINKS_STATE
argument_list|,
name|boolValue
operator|=
name|Nif20MetadataEngine
operator|.
name|DEFAULT_HIERARCHY_LINKS_STATE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Nif20MetadataEngine
operator|.
name|PROP_PREVIOUSE_NEXT_LINKS_STATE
argument_list|,
name|boolValue
operator|=
name|Nif20MetadataEngine
operator|.
name|DEFAULT_PREVIOUSE_NEXT_LINKS_STATE
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|Nif20MetadataEngine
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
comment|/**      * Switch that allows to enable/disable writing of hierarchical links. This      * includes<code>olia:sentence</code>,<code>olia:superString</code> and      *<code>olia:subString</code> properties.      */
specifier|public
specifier|final
specifier|static
name|String
name|PROP_HIERARCHY_LINKS_STATE
init|=
literal|"enhancer.engines.nlp2rdf.hierarchy"
decl_stmt|;
comment|/**      * switch that allows to enable/disable writing of next and previous links       * between words, sentences ...      */
specifier|public
specifier|final
specifier|static
name|String
name|PROP_PREVIOUSE_NEXT_LINKS_STATE
init|=
literal|"enhancer.engines.nlp2rdf.previousNext"
decl_stmt|;
comment|/**      * If enabled Selector related properties such as begin-/end-index, before/after, ...      * are written. If disabled only the URI is generated (which is sufficient for      * clients that know about the semantics of how the URI is build). Deactivating      * this will greatly decrease the triple count.      */
specifier|public
specifier|final
specifier|static
name|String
name|PROP_SELECTOR_STATE
init|=
literal|"enhancer.engines.nlp2rdf.selector"
decl_stmt|;
comment|/**      * If enabled the {@link Nif20#URIScheme nif:URIScheme} ( typically       * {@link Nif20#RFC5147String nif:RFC5147String}) type will only be added       * for the {@link Nif20#Context nif:Context} and not      * all {@link Nif20#String nif:String} instances. If enabled clients need       * follow the {@link Nif20#referenceContext nif:referenceContext} to the       * {@link Nif20#Context nif:Context} for getting the used       * {@link Nif20#URIScheme nif:URIScheme}<p>      */
specifier|public
specifier|final
specifier|static
name|String
name|PROP_CONTEXT_ONLY_URI_SCHEME
init|=
literal|"enhancer.engines.nlp2rdf.cotextOnlyUriScheme"
decl_stmt|;
comment|/**      * If enabled the {@link Nif20#String nif:String} type is added to all written      * String. If disabled it is only written if their is no more specific type (      * such as {@link Nif20#Sentence nif:Sentence} or {@link Nif20#Word nif:Word}.      */
specifier|public
specifier|final
specifier|static
name|String
name|PROP_WRITE_STRING_TYPE
init|=
literal|"enhancer.engines.nlp2rdf.writeStringType"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_HIERARCHY_LINKS_STATE
init|=
literal|true
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_PREVIOUSE_NEXT_LINKS_STATE
init|=
literal|true
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_SELECTOR_STATE
init|=
literal|true
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_CONTEXT_ONLY_URI_SCHEME
init|=
literal|false
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_WRITE_STRING_TYPE
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|writeHierary
init|=
name|DEFAULT_HIERARCHY_LINKS_STATE
decl_stmt|;
specifier|private
name|boolean
name|writePrevNext
init|=
name|DEFAULT_PREVIOUSE_NEXT_LINKS_STATE
decl_stmt|;
specifier|private
name|boolean
name|writeSelectors
init|=
name|DEFAULT_SELECTOR_STATE
decl_stmt|;
specifier|private
name|boolean
name|contextOnlyUriScheme
init|=
name|DEFAULT_CONTEXT_ONLY_URI_SCHEME
decl_stmt|;
specifier|private
name|boolean
name|writeStringType
init|=
name|DEFAULT_WRITE_STRING_TYPE
decl_stmt|;
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|Nif20MetadataEngine
operator|.
name|class
argument_list|)
decl_stmt|;
comment|//TODO: replace this with a reald ontology
specifier|private
specifier|final
specifier|static
name|IRI
name|SENTIMENT_PROPERTY
init|=
operator|new
name|IRI
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"sentiment-value"
argument_list|)
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
name|props
init|=
name|ce
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|writeHierary
operator|=
name|getState
argument_list|(
name|props
argument_list|,
name|PROP_HIERARCHY_LINKS_STATE
argument_list|,
name|DEFAULT_HIERARCHY_LINKS_STATE
argument_list|)
expr_stmt|;
name|writePrevNext
operator|=
name|getState
argument_list|(
name|props
argument_list|,
name|PROP_PREVIOUSE_NEXT_LINKS_STATE
argument_list|,
name|DEFAULT_PREVIOUSE_NEXT_LINKS_STATE
argument_list|)
expr_stmt|;
name|writeSelectors
operator|=
name|getState
argument_list|(
name|props
argument_list|,
name|PROP_SELECTOR_STATE
argument_list|,
name|DEFAULT_SELECTOR_STATE
argument_list|)
expr_stmt|;
name|contextOnlyUriScheme
operator|=
name|getState
argument_list|(
name|props
argument_list|,
name|PROP_CONTEXT_ONLY_URI_SCHEME
argument_list|,
name|DEFAULT_CONTEXT_ONLY_URI_SCHEME
argument_list|)
expr_stmt|;
name|writeStringType
operator|=
name|getState
argument_list|(
name|props
argument_list|,
name|PROP_WRITE_STRING_TYPE
argument_list|,
name|DEFAULT_WRITE_STRING_TYPE
argument_list|)
expr_stmt|;
block|}
specifier|private
name|boolean
name|getState
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|props
parameter_list|,
name|String
name|prop
parameter_list|,
name|boolean
name|def
parameter_list|)
block|{
name|Object
name|val
init|=
name|props
operator|.
name|get
argument_list|(
name|prop
argument_list|)
decl_stmt|;
name|boolean
name|state
init|=
name|val
operator|==
literal|null
condition|?
name|def
else|:
name|val
operator|instanceof
name|Boolean
condition|?
operator|(
operator|(
name|Boolean
operator|)
name|val
operator|)
operator|.
name|booleanValue
argument_list|()
else|:
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|val
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|" - {}: {}"
argument_list|,
name|prop
argument_list|,
name|state
argument_list|)
expr_stmt|;
return|return
name|state
return|;
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
name|lang
init|=
name|EnhancementEngineHelper
operator|.
name|getLanguage
argument_list|(
name|ci
argument_list|)
decl_stmt|;
name|Language
name|language
init|=
name|lang
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|Language
argument_list|(
name|lang
argument_list|)
decl_stmt|;
comment|//now iterate over the AnalysedText data and create the RDF representation
comment|//TODO: make configureable
name|boolean
name|sentences
init|=
literal|true
decl_stmt|;
name|boolean
name|phrases
init|=
literal|true
decl_stmt|;
name|boolean
name|words
init|=
literal|true
decl_stmt|;
name|EnumSet
argument_list|<
name|SpanTypeEnum
argument_list|>
name|activeTypes
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
name|sentences
condition|)
block|{
name|activeTypes
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
name|phrases
condition|)
block|{
name|activeTypes
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
name|words
condition|)
block|{
name|activeTypes
operator|.
name|add
argument_list|(
name|SpanTypeEnum
operator|.
name|Token
argument_list|)
expr_stmt|;
block|}
name|Graph
name|metadata
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|IRI
name|base
init|=
name|ci
operator|.
name|getUri
argument_list|()
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
comment|//write the context
name|IRI
name|text
init|=
name|writeSpan
argument_list|(
name|metadata
argument_list|,
name|base
argument_list|,
name|at
argument_list|,
name|language
argument_list|,
name|at
argument_list|)
decl_stmt|;
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|text
argument_list|,
name|Nif20
operator|.
name|sourceUrl
operator|.
name|getUri
argument_list|()
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|Span
argument_list|>
name|spans
init|=
name|at
operator|.
name|getEnclosed
argument_list|(
name|activeTypes
argument_list|)
decl_stmt|;
name|IRI
name|sentence
init|=
literal|null
decl_stmt|;
name|IRI
name|phrase
init|=
literal|null
decl_stmt|;
name|IRI
name|word
init|=
literal|null
decl_stmt|;
name|boolean
name|firstWordInSentence
init|=
literal|true
decl_stmt|;
while|while
condition|(
name|spans
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Span
name|span
init|=
name|spans
operator|.
name|next
argument_list|()
decl_stmt|;
comment|//TODO: filter Spans based on additional requirements
comment|//(1) write generic information about the span
name|IRI
name|current
init|=
name|writeSpan
argument_list|(
name|metadata
argument_list|,
name|base
argument_list|,
name|at
argument_list|,
name|language
argument_list|,
name|span
argument_list|)
decl_stmt|;
comment|//write the context
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|current
argument_list|,
name|Nif20
operator|.
name|referenceContext
operator|.
name|getUri
argument_list|()
argument_list|,
name|text
argument_list|)
argument_list|)
expr_stmt|;
comment|//(2) add the relations between the different spans
switch|switch
condition|(
name|span
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|Sentence
case|:
if|if
condition|(
name|sentence
operator|!=
literal|null
operator|&&
name|writePrevNext
condition|)
block|{
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|sentence
argument_list|,
name|Nif20
operator|.
name|nextSentence
operator|.
name|getUri
argument_list|()
argument_list|,
name|current
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
name|current
argument_list|,
name|Nif20
operator|.
name|previousSentence
operator|.
name|getUri
argument_list|()
argument_list|,
name|sentence
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|word
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
name|sentence
argument_list|,
name|Nif20
operator|.
name|lastWord
operator|.
name|getUri
argument_list|()
argument_list|,
name|word
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|sentence
operator|=
name|current
expr_stmt|;
name|firstWordInSentence
operator|=
literal|true
expr_stmt|;
break|break;
case|case
name|Chunk
case|:
if|if
condition|(
name|sentence
operator|!=
literal|null
operator|&&
name|writeHierary
condition|)
block|{
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|current
argument_list|,
name|Nif20
operator|.
name|superString
operator|.
name|getUri
argument_list|()
argument_list|,
name|sentence
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|phrase
operator|=
name|current
expr_stmt|;
break|break;
case|case
name|Token
case|:
if|if
condition|(
name|sentence
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|writeHierary
condition|)
block|{
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|current
argument_list|,
name|Nif20
operator|.
name|sentence
operator|.
name|getUri
argument_list|()
argument_list|,
name|sentence
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//metadata.add(new TripleImpl(sentence, Nif20.word.getUri(), current));
if|if
condition|(
name|firstWordInSentence
condition|)
block|{
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|sentence
argument_list|,
name|Nif20
operator|.
name|firstWord
operator|.
name|getUri
argument_list|()
argument_list|,
name|current
argument_list|)
argument_list|)
expr_stmt|;
name|firstWordInSentence
operator|=
literal|false
expr_stmt|;
block|}
block|}
if|if
condition|(
name|writeHierary
operator|&&
name|phrase
operator|!=
literal|null
operator|&&
operator|!
name|phrase
operator|.
name|equals
argument_list|(
name|current
argument_list|)
condition|)
block|{
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|current
argument_list|,
name|Nif20
operator|.
name|subString
operator|.
name|getUri
argument_list|()
argument_list|,
name|phrase
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|word
operator|!=
literal|null
operator|&&
name|writePrevNext
condition|)
block|{
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|word
argument_list|,
name|Nif20
operator|.
name|nextWord
operator|.
name|getUri
argument_list|()
argument_list|,
name|current
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
name|current
argument_list|,
name|Nif20
operator|.
name|previousWord
operator|.
name|getUri
argument_list|()
argument_list|,
name|word
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|word
operator|=
name|current
expr_stmt|;
break|break;
default|default:
break|break;
block|}
comment|//(3) add specific information such as POS, chunk type ...
name|Nif20Helper
operator|.
name|writePhrase
argument_list|(
name|metadata
argument_list|,
name|span
argument_list|,
name|current
argument_list|)
expr_stmt|;
name|Nif20Helper
operator|.
name|writePos
argument_list|(
name|metadata
argument_list|,
name|span
argument_list|,
name|current
argument_list|)
expr_stmt|;
comment|//TODO: sentiment support
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
name|NlpAnnotations
operator|.
name|SENTIMENT_ANNOTATION
argument_list|)
decl_stmt|;
if|if
condition|(
name|sentiment
operator|!=
literal|null
operator|&&
name|sentiment
operator|.
name|value
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
name|current
argument_list|,
name|SENTIMENT_PROPERTY
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|sentiment
operator|.
name|value
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
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
name|ServiceProperties
operator|.
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|,
operator|(
name|Object
operator|)
name|ServiceProperties
operator|.
name|ORDERING_POST_PROCESSING
argument_list|)
return|;
block|}
comment|/**      * Writes basic information of the parsed span by using NIF 1.0 including the      * {@link SsoOntology} Sentence/Phrase/Word type based on       * the {@link Span#getType()}<p>      * As {@link AnalysedText} is based on the plain text version of the ContentItem      * this uses the {@link StringOntology#OffsetBasedString} notation.<p>      *<i>NOTE:</i> This DOES NOT write string relations, lemma, pos ... information      * that might be stored as {@link Annotation} with the parsed {@link Span}.      * @param graph the graph to add the triples      * @param base the base URI      * @param text the {@link AnalysedText}      * @param language the {@link Language} or<code>null</code> if not known      * @param span the {@link Span} to write.      * @return the {@link IRI} representing the parsed {@link Span} in the      * graph      */
specifier|public
name|IRI
name|writeSpan
parameter_list|(
name|Graph
name|graph
parameter_list|,
name|IRI
name|base
parameter_list|,
name|AnalysedText
name|text
parameter_list|,
name|Language
name|language
parameter_list|,
name|Span
name|span
parameter_list|)
block|{
name|IRI
name|segment
init|=
name|Nif20Helper
operator|.
name|getNifRFC5147URI
argument_list|(
name|base
argument_list|,
name|span
operator|.
name|getStart
argument_list|()
argument_list|,
name|span
operator|.
name|getType
argument_list|()
operator|==
name|SpanTypeEnum
operator|.
name|Text
condition|?
operator|-
literal|1
else|:
name|span
operator|.
name|getEnd
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|contextOnlyUriScheme
operator|||
name|span
operator|.
name|getType
argument_list|()
operator|==
name|SpanTypeEnum
operator|.
name|Text
condition|)
block|{
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|segment
argument_list|,
name|RDF_TYPE
argument_list|,
name|Nif20
operator|.
name|RFC5147String
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|writeSelectors
condition|)
block|{
if|if
condition|(
name|span
operator|.
name|getEnd
argument_list|()
operator|-
name|span
operator|.
name|getStart
argument_list|()
operator|<
literal|100
condition|)
block|{
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|segment
argument_list|,
name|Nif20
operator|.
name|anchorOf
operator|.
name|getUri
argument_list|()
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|span
operator|.
name|getSpan
argument_list|()
argument_list|,
name|language
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|segment
argument_list|,
name|Nif20
operator|.
name|head
operator|.
name|getUri
argument_list|()
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|span
operator|.
name|getSpan
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|10
argument_list|)
argument_list|,
name|language
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|segment
argument_list|,
name|Nif20
operator|.
name|beginIndex
operator|.
name|getUri
argument_list|()
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|span
operator|.
name|getStart
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|segment
argument_list|,
name|Nif20
operator|.
name|endIndex
operator|.
name|getUri
argument_list|()
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|span
operator|.
name|getEnd
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|content
init|=
name|text
operator|.
name|getSpan
argument_list|()
decl_stmt|;
if|if
condition|(
name|span
operator|.
name|getType
argument_list|()
operator|!=
name|SpanTypeEnum
operator|.
name|Text
condition|)
block|{
comment|//prefix and suffix
name|int
name|prefixStart
init|=
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|span
operator|.
name|getStart
argument_list|()
operator|-
name|DEFAULT_PREFIX_SUFFIX_LENGTH
argument_list|)
decl_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|segment
argument_list|,
name|Nif20
operator|.
name|before
operator|.
name|getUri
argument_list|()
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|content
operator|.
name|substring
argument_list|(
name|prefixStart
argument_list|,
name|span
operator|.
name|getStart
argument_list|()
argument_list|)
argument_list|,
name|language
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|suffixEnd
init|=
name|Math
operator|.
name|min
argument_list|(
name|span
operator|.
name|getEnd
argument_list|()
operator|+
name|DEFAULT_PREFIX_SUFFIX_LENGTH
argument_list|,
name|text
operator|.
name|getEnd
argument_list|()
argument_list|)
decl_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|segment
argument_list|,
name|Nif20
operator|.
name|after
operator|.
name|getUri
argument_list|()
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|content
operator|.
name|substring
argument_list|(
name|span
operator|.
name|getEnd
argument_list|()
argument_list|,
name|suffixEnd
argument_list|)
argument_list|,
name|language
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|writeStringType
condition|)
block|{
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|segment
argument_list|,
name|RDF_TYPE
argument_list|,
name|Nif20
operator|.
name|String
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|segment
argument_list|,
name|RDF_TYPE
argument_list|,
name|Nif20
operator|.
name|Word
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|Chunk
case|:
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|segment
argument_list|,
name|RDF_TYPE
argument_list|,
name|Nif20
operator|.
name|Phrase
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|Sentence
case|:
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|segment
argument_list|,
name|RDF_TYPE
argument_list|,
name|Nif20
operator|.
name|Sentence
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|Text
case|:
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|segment
argument_list|,
name|RDF_TYPE
argument_list|,
name|Nif20
operator|.
name|Context
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
if|if
condition|(
operator|!
name|writeStringType
condition|)
block|{
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|segment
argument_list|,
name|RDF_TYPE
argument_list|,
name|Nif20
operator|.
name|String
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//string type was already added
block|}
return|return
name|segment
return|;
block|}
block|}
end_class

end_unit

