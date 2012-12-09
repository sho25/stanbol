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
name|langdetect
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
name|TechnicalClasses
operator|.
name|DCTERMS_LINGUISTIC_SYSTEM
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
name|Dictionary
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
name|Chain
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

begin_import
import|import
name|com
operator|.
name|cybozu
operator|.
name|labs
operator|.
name|langdetect
operator|.
name|LangDetectException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|cybozu
operator|.
name|labs
operator|.
name|langdetect
operator|.
name|Language
import|;
end_import

begin_comment
comment|/**  * {@link LanguageDetectionEnhancementEngine} provides functionality to enhance document  * with their language.  *  * @author Walter Kasper, DFKI  */
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
name|inherit
operator|=
literal|true
argument_list|)
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
literal|"langdetect"
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|LanguageDetectionEnhancementEngine
extends|extends
name|AbstractEnhancementEngine
argument_list|<
name|LangDetectException
argument_list|,
name|RuntimeException
argument_list|>
implements|implements
name|EnhancementEngine
implements|,
name|ServiceProperties
block|{
comment|/**      * a configurable value of the text segment length to check      */
annotation|@
name|Property
argument_list|(
name|intValue
operator|=
name|LanguageDetectionEnhancementEngine
operator|.
name|PROBE_LENGTH_DEFAULT
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|PROBE_LENGTH_PROP
init|=
literal|"org.apache.stanbol.enhancer.engines.langdetect.probe-length"
decl_stmt|;
comment|/**      * a configurable value of the maximum number of suggested languages      */
annotation|@
name|Property
argument_list|(
name|intValue
operator|=
name|LanguageDetectionEnhancementEngine
operator|.
name|DEFAULT_MAX_SUGGESTED_LANGUAGES
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|MAX_SUGGESTED_PROP
init|=
literal|"org.apache.stanbol.enhancer.engines.langdetect.max-suggested"
decl_stmt|;
comment|/**      * The default value for the Execution of this Engine (      * {@link ServiceProperties#ORDERING_NLP_LANGAUGE_DETECTION})<p>      * NOTE: this information is used by the default and weighed {@link Chain}      * implementation to determine the processing order of       * {@link EnhancementEngine}s. Other {@link Chain} implementation do not      * use this information.      */
specifier|public
specifier|static
specifier|final
name|Integer
name|defaultOrder
init|=
name|ServiceProperties
operator|.
name|ORDERING_NLP_LANGAUGE_DETECTION
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
comment|/**      * Set containing the only supported mime type {@link #TEXT_PLAIN_MIMETYPE}      */
specifier|private
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|SUPPORTED_MIMTYPES
init|=
name|Collections
operator|.
name|singleton
argument_list|(
name|TEXT_PLAIN_MIMETYPE
argument_list|)
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
name|LanguageDetectionEnhancementEngine
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/*      * NOTE: Checked the Documentation: The tool already supports the taking      * of several shorter samples randomly distributed over the parsed text      * to imrpove results and reduce noise. See      * http://code.google.com/p/language-detection/wiki/FrequentlyAskedQuestion      * "Each detected language differs for the same document" for a hint.       */
specifier|private
specifier|static
specifier|final
name|int
name|PROBE_LENGTH_DEFAULT
init|=
operator|-
literal|1
decl_stmt|;
comment|/**      * Default value for the maximum number of suggested Languages      */
specifier|private
specifier|static
specifier|final
name|int
name|DEFAULT_MAX_SUGGESTED_LANGUAGES
init|=
literal|3
decl_stmt|;
comment|/**      * How much text should be used for testing: If the value is 0 or smaller,      * the complete text will be used. Otherwise a text probe of the given length      * is taken from the middle of the text. The default length is 1000.      */
specifier|private
name|int
name|probeLength
init|=
name|PROBE_LENGTH_DEFAULT
decl_stmt|;
specifier|private
name|int
name|maxSuggestedLanguages
init|=
name|DEFAULT_MAX_SUGGESTED_LANGUAGES
decl_stmt|;
comment|/**      * The literal factory      */
specifier|private
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
name|LanguageIdentifier
name|languageIdentifier
decl_stmt|;
comment|/**      * Initialize the language identifier model and load the prop length bound if      * provided as a property.      *       * @param ce      *            the {@link ComponentContext}      */
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ce
parameter_list|)
throws|throws
name|ConfigurationException
throws|,
name|LangDetectException
block|{
name|super
operator|.
name|activate
argument_list|(
name|ce
argument_list|)
expr_stmt|;
if|if
condition|(
name|ce
operator|!=
literal|null
condition|)
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
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
name|Object
name|value
init|=
name|properties
operator|.
name|get
argument_list|(
name|PROBE_LENGTH_PROP
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|Number
condition|)
block|{
name|probeLength
operator|=
operator|(
operator|(
name|Number
operator|)
name|value
operator|)
operator|.
name|intValue
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
name|probeLength
operator|=
name|Integer
operator|.
name|parseInt
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
name|PROBE_LENGTH_PROP
argument_list|,
literal|"The parsed 'proble length' MUST be a valid Integer"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
else|else
block|{
name|probeLength
operator|=
name|PROBE_LENGTH_DEFAULT
expr_stmt|;
block|}
name|value
operator|=
name|properties
operator|.
name|get
argument_list|(
name|MAX_SUGGESTED_PROP
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|Number
condition|)
block|{
name|maxSuggestedLanguages
operator|=
operator|(
operator|(
name|Number
operator|)
name|value
operator|)
operator|.
name|intValue
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
name|maxSuggestedLanguages
operator|=
name|Integer
operator|.
name|parseInt
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
name|MAX_SUGGESTED_PROP
argument_list|,
literal|"The parsed number of the maximum suggested lanugages "
operator|+
literal|"MUST BE a valid Integer"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
if|if
condition|(
name|maxSuggestedLanguages
operator|<
literal|1
condition|)
block|{
name|maxSuggestedLanguages
operator|=
name|DEFAULT_MAX_SUGGESTED_LANGUAGES
expr_stmt|;
block|}
block|}
name|languageIdentifier
operator|=
operator|new
name|LanguageIdentifier
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ce
parameter_list|)
block|{
name|super
operator|.
name|deactivate
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|this
operator|.
name|languageIdentifier
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|maxSuggestedLanguages
operator|=
operator|-
literal|1
expr_stmt|;
name|this
operator|.
name|probeLength
operator|=
operator|-
literal|1
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
return|return
name|ENHANCE_ASYNC
return|;
comment|//Langid now supports async processing
block|}
else|else
block|{
return|return
name|CANNOT_ENHANCE
return|;
block|}
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
name|String
name|text
init|=
literal|""
decl_stmt|;
try|try
block|{
name|text
operator|=
name|ContentItemHelper
operator|.
name|getText
argument_list|(
name|contentPart
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
name|info
argument_list|(
literal|"No text contained in ContentPart {} of ContentItem {}"
argument_list|,
name|contentPart
operator|.
name|getKey
argument_list|()
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
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
name|List
argument_list|<
name|Language
argument_list|>
name|languages
init|=
literal|null
decl_stmt|;
try|try
block|{
name|languages
operator|=
name|languageIdentifier
operator|.
name|getLanguages
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"language identified: {}"
argument_list|,
name|languages
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|LangDetectException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Could not identify language"
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
literal|"Could not identify language"
argument_list|,
name|e
argument_list|)
throw|;
block|}
comment|// add language to metadata
if|if
condition|(
name|languages
operator|!=
literal|null
condition|)
block|{
name|MGraph
name|g
init|=
name|ci
operator|.
name|getMetadata
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
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|maxSuggestedLanguages
operator|&&
name|i
operator|<
name|languages
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
comment|// add a hypothesis
name|Language
name|hypothesis
init|=
name|languages
operator|.
name|get
argument_list|(
name|i
argument_list|)
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
name|hypothesis
operator|.
name|lang
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textEnhancement
argument_list|,
name|ENHANCER_CONFIDENCE
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|hypothesis
operator|.
name|prob
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textEnhancement
argument_list|,
name|DC_TYPE
argument_list|,
name|DCTERMS_LINGUISTIC_SYSTEM
argument_list|)
argument_list|)
expr_stmt|;
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textEnhancement
argument_list|,
name|ENHANCER_CONFIDENCE
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|hypothesis
operator|.
name|prob
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
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
name|singletonMap
argument_list|(
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|,
operator|(
name|Object
operator|)
name|defaultOrder
argument_list|)
return|;
block|}
block|}
end_class

end_unit

