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
name|celi
operator|.
name|lemmatizer
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
name|engines
operator|.
name|celi
operator|.
name|utils
operator|.
name|Utils
operator|.
name|getSelectionContext
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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|soap
operator|.
name|SOAPException
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
name|commons
operator|.
name|rdf
operator|.
name|Literal
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
name|stanboltools
operator|.
name|offline
operator|.
name|OnlineMode
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
name|celi
operator|.
name|CeliConstants
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
name|celi
operator|.
name|CeliMorphoFeatures
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
name|celi
operator|.
name|CeliTagSetRegistry
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
name|celi
operator|.
name|utils
operator|.
name|Utils
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
name|tag
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
name|morpho
operator|.
name|Case
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
name|morpho
operator|.
name|Gender
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
name|morpho
operator|.
name|NumberFeature
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
name|morpho
operator|.
name|Person
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
name|morpho
operator|.
name|Tense
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
name|morpho
operator|.
name|TenseTag
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
name|morpho
operator|.
name|VerbMood
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
literal|"celiLemmatizer"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|CeliConstants
operator|.
name|CELI_LICENSE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|CeliConstants
operator|.
name|CELI_TEST_ACCOUNT
argument_list|,
name|boolValue
operator|=
literal|false
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|CeliConstants
operator|.
name|CELI_CONNECTION_TIMEOUT
argument_list|,
name|intValue
operator|=
name|CeliConstants
operator|.
name|DEFAULT_CONECTION_TIMEOUT
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|CeliLemmatizerEnhancementEngine
extends|extends
name|AbstractEnhancementEngine
argument_list|<
name|IOException
argument_list|,
name|RuntimeException
argument_list|>
implements|implements
name|EnhancementEngine
implements|,
name|ServiceProperties
block|{
comment|// TODO: check if it is OK to define new properties in the FISE namespace
specifier|public
specifier|static
specifier|final
name|IRI
name|hasLemmaForm
init|=
operator|new
name|IRI
argument_list|(
literal|"http://fise.iks-project.eu/ontology/hasLemmaForm"
argument_list|)
decl_stmt|;
comment|/**      * This ensures that no connections to external services are made if Stanbol is started in offline mode as the OnlineMode service will only be available if OfflineMode is deactivated.      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
annotation|@
name|Reference
specifier|private
name|OnlineMode
name|onlineMode
decl_stmt|;
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|supportedLangs
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
static|static
block|{
name|supportedLangs
operator|.
name|add
argument_list|(
literal|"it"
argument_list|)
expr_stmt|;
name|supportedLangs
operator|.
name|add
argument_list|(
literal|"da"
argument_list|)
expr_stmt|;
name|supportedLangs
operator|.
name|add
argument_list|(
literal|"de"
argument_list|)
expr_stmt|;
name|supportedLangs
operator|.
name|add
argument_list|(
literal|"ru"
argument_list|)
expr_stmt|;
name|supportedLangs
operator|.
name|add
argument_list|(
literal|"ro"
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * The literal representing the LangIDEngine as creator. 	 */
specifier|public
specifier|static
specifier|final
name|Literal
name|LANG_ID_ENGINE_NAME
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createTypedLiteral
argument_list|(
literal|"org.apache.stanbol.enhancer.engines.celi.langid.impl.CeliLanguageIdentifierEnhancementEngine"
argument_list|)
decl_stmt|;
comment|/** 	 * The default value for the Execution of this Engine. Currently set to {@link ServiceProperties#ORDERING_CONTENT_EXTRACTION} 	 */
specifier|public
specifier|static
specifier|final
name|Integer
name|defaultOrder
init|=
name|ServiceProperties
operator|.
name|ORDERING_CONTENT_EXTRACTION
decl_stmt|;
specifier|private
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
comment|/** 	 * This contains the only MIME type directly supported by this enhancement engine. 	 */
specifier|private
specifier|static
specifier|final
name|String
name|TEXT_PLAIN_MIMETYPE
init|=
literal|"text/plain"
decl_stmt|;
comment|/** 	 * Set containing the only supported mime type {@link #TEXT_PLAIN_MIMETYPE} 	 */
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
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"http://linguagrid.org/LSGrid/ws/morpho-analyser"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|SERVICE_URL
init|=
literal|"org.apache.stanbol.enhancer.engines.celi.lemmatizer.url"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|boolValue
operator|=
literal|false
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|MORPHOLOGICAL_ANALYSIS
init|=
literal|"org.apache.stanbol.enhancer.engines.celi.lemmatizer.morphoAnalysis"
decl_stmt|;
specifier|private
name|String
name|licenseKey
decl_stmt|;
specifier|private
name|URL
name|serviceURL
decl_stmt|;
specifier|private
name|boolean
name|completeMorphoAnalysis
decl_stmt|;
specifier|private
name|LemmatizerClientHTTP
name|client
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
name|IOException
throws|,
name|ConfigurationException
block|{
name|super
operator|.
name|activate
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
init|=
name|ctx
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|this
operator|.
name|licenseKey
operator|=
name|Utils
operator|.
name|getLicenseKey
argument_list|(
name|properties
argument_list|,
name|ctx
operator|.
name|getBundleContext
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|url
init|=
operator|(
name|String
operator|)
name|properties
operator|.
name|get
argument_list|(
name|SERVICE_URL
argument_list|)
decl_stmt|;
if|if
condition|(
name|url
operator|==
literal|null
operator|||
name|url
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|SERVICE_URL
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"%s : please configure the URL of the CELI Web Service (e.g. by"
operator|+
literal|"using the 'Configuration' tab of the Apache Felix Web Console)."
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
name|this
operator|.
name|serviceURL
operator|=
operator|new
name|URL
argument_list|(
name|url
argument_list|)
expr_stmt|;
try|try
block|{
name|this
operator|.
name|completeMorphoAnalysis
operator|=
operator|(
name|Boolean
operator|)
name|properties
operator|.
name|get
argument_list|(
name|MORPHOLOGICAL_ANALYSIS
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|this
operator|.
name|completeMorphoAnalysis
operator|=
literal|false
expr_stmt|;
block|}
name|int
name|conTimeout
init|=
name|Utils
operator|.
name|getConnectionTimeout
argument_list|(
name|properties
argument_list|,
name|ctx
operator|.
name|getBundleContext
argument_list|()
argument_list|)
decl_stmt|;
name|this
operator|.
name|client
operator|=
operator|new
name|LemmatizerClientHTTP
argument_list|(
name|this
operator|.
name|serviceURL
argument_list|,
name|this
operator|.
name|licenseKey
argument_list|,
name|conTimeout
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
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to enhance ContentItem {} because language of the Content is unknown."
operator|+
literal|"Please check that a language identification engine is active in this EnhancementChain)."
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
operator|&&
name|this
operator|.
name|isLangSupported
argument_list|(
name|language
argument_list|)
condition|)
return|return
name|ENHANCE_ASYNC
return|;
else|else
return|return
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
operator|!
name|isLangSupported
argument_list|(
name|language
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Call to computeEnhancement with unsupported language '"
operator|+
name|language
operator|+
literal|" for ContentItem "
operator|+
name|ci
operator|.
name|getUri
argument_list|()
operator|+
literal|": This is also checked "
operator|+
literal|"in the canEnhance method! -> This indicated an Bug in the "
operator|+
literal|"implementation of the "
operator|+
literal|"EnhancementJobManager!"
argument_list|)
throw|;
block|}
name|Entry
argument_list|<
name|IRI
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
literal|"No text contained in ContentPart {"
operator|+
name|contentPart
operator|.
name|getKey
argument_list|()
operator|+
literal|"} of ContentItem {"
operator|+
name|ci
operator|.
name|getUri
argument_list|()
operator|+
literal|"}"
argument_list|)
expr_stmt|;
return|return;
block|}
name|Graph
name|graph
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|completeMorphoAnalysis
condition|)
block|{
name|this
operator|.
name|addMorphoAnalysisEnhancement
argument_list|(
name|ci
argument_list|,
name|text
argument_list|,
name|language
argument_list|,
name|graph
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|addLemmatizationEnhancement
argument_list|(
name|ci
argument_list|,
name|text
argument_list|,
name|language
argument_list|,
name|graph
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|addMorphoAnalysisEnhancement
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|String
name|text
parameter_list|,
name|String
name|language
parameter_list|,
name|Graph
name|g
parameter_list|)
throws|throws
name|EngineException
block|{
name|Language
name|lang
init|=
operator|new
name|Language
argument_list|(
name|language
argument_list|)
decl_stmt|;
comment|// clerezza language for PlainLiterals
name|List
argument_list|<
name|LexicalEntry
argument_list|>
name|terms
decl_stmt|;
try|try
block|{
name|terms
operator|=
name|this
operator|.
name|client
operator|.
name|performMorfologicalAnalysis
argument_list|(
name|text
argument_list|,
name|language
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
literal|"Error while calling the CELI Lemmatizer"
operator|+
literal|" service (configured URL: "
operator|+
name|serviceURL
operator|+
literal|")!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|SOAPException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"Error wile encoding/decoding the request/"
operator|+
literal|"response to the CELI lemmatizer service!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
comment|// get a write lock before writing the enhancements
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
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
for|for
control|(
name|LexicalEntry
name|le
range|:
name|terms
control|)
block|{
name|List
argument_list|<
name|CeliMorphoFeatures
argument_list|>
name|mFeatures
init|=
name|this
operator|.
name|convertLexicalEntryToMorphFeatures
argument_list|(
name|le
argument_list|,
name|language
argument_list|)
decl_stmt|;
for|for
control|(
name|CeliMorphoFeatures
name|feat
range|:
name|mFeatures
control|)
block|{
comment|// Create a text annotation for each interpretation produced by the morphological analyzer
name|IRI
name|textAnnotation
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
name|textAnnotation
argument_list|,
name|ENHANCER_SELECTED_TEXT
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|le
operator|.
name|getWordForm
argument_list|()
argument_list|,
name|lang
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|le
operator|.
name|from
operator|>=
literal|0
operator|&&
name|le
operator|.
name|to
operator|>
literal|0
condition|)
block|{
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_START
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|le
operator|.
name|from
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
name|textAnnotation
argument_list|,
name|ENHANCER_END
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|le
operator|.
name|to
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
name|textAnnotation
argument_list|,
name|ENHANCER_SELECTION_CONTEXT
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|getSelectionContext
argument_list|(
name|text
argument_list|,
name|le
operator|.
name|getWordForm
argument_list|()
argument_list|,
name|le
operator|.
name|from
argument_list|)
argument_list|,
name|lang
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|g
operator|.
name|addAll
argument_list|(
name|feat
operator|.
name|featuresAsTriples
argument_list|(
name|textAnnotation
argument_list|,
name|lang
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
specifier|private
name|void
name|addLemmatizationEnhancement
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|String
name|text
parameter_list|,
name|String
name|language
parameter_list|,
name|Graph
name|g
parameter_list|)
throws|throws
name|EngineException
block|{
name|Language
name|lang
init|=
operator|new
name|Language
argument_list|(
name|language
argument_list|)
decl_stmt|;
comment|// clerezza language for PlainLiterals
name|String
name|lemmatizedContents
decl_stmt|;
try|try
block|{
name|lemmatizedContents
operator|=
name|this
operator|.
name|client
operator|.
name|lemmatizeContents
argument_list|(
name|text
argument_list|,
name|language
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
literal|"Error while calling the CELI Lemmatizer"
operator|+
literal|" service (configured URL: "
operator|+
name|serviceURL
operator|+
literal|")!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|SOAPException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"Error wile encoding/decoding the request/"
operator|+
literal|"response to the CELI lemmatizer service!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
comment|// get a write lock before writing the enhancements
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
name|IRI
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
name|CeliLemmatizerEnhancementEngine
operator|.
name|hasLemmaForm
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|lemmatizedContents
argument_list|,
name|lang
argument_list|)
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
specifier|private
name|List
argument_list|<
name|CeliMorphoFeatures
argument_list|>
name|convertLexicalEntryToMorphFeatures
parameter_list|(
name|LexicalEntry
name|le
parameter_list|,
name|String
name|lang
parameter_list|)
block|{
name|List
argument_list|<
name|CeliMorphoFeatures
argument_list|>
name|result
init|=
operator|new
name|Vector
argument_list|<
name|CeliMorphoFeatures
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|le
operator|.
name|termReadings
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Reading
name|r
range|:
name|le
operator|.
name|termReadings
control|)
block|{
name|CeliMorphoFeatures
name|morphoFeature
init|=
name|CeliMorphoFeatures
operator|.
name|parseFrom
argument_list|(
name|r
argument_list|,
name|lang
argument_list|)
decl_stmt|;
if|if
condition|(
name|morphoFeature
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|morphoFeature
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|result
return|;
block|}
specifier|private
name|boolean
name|isLangSupported
parameter_list|(
name|String
name|language
parameter_list|)
block|{
if|if
condition|(
name|supportedLangs
operator|.
name|contains
argument_list|(
name|language
argument_list|)
condition|)
return|return
literal|true
return|;
else|else
return|return
literal|false
return|;
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

