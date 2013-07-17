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
name|classification
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
name|OntologicalClasses
operator|.
name|SKOS_CONCEPT
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
name|DC_RELATION
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
name|Properties
operator|.
name|ENHANCER_ENTITY_LABEL
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
name|ENHANCER_ENTITY_REFERENCE
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
name|ENHANCER_ENTITY_TYPE
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
name|rdf
operator|.
name|core
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
name|PropertyUnbounded
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
literal|"celiClassification"
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
name|CeliClassificationEnhancementEngine
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
comment|/** 	 * This ensures that no connections to external services are made if Stanbol is started in offline mode  	 * as the OnlineMode service will only be available if OfflineMode is deactivated.  	 */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
comment|//it's not unused!
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
literal|"en"
argument_list|)
expr_stmt|;
name|supportedLangs
operator|.
name|add
argument_list|(
literal|"fr"
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
literal|"it"
argument_list|)
expr_stmt|;
name|supportedLangs
operator|.
name|add
argument_list|(
literal|"es"
argument_list|)
expr_stmt|;
name|supportedLangs
operator|.
name|add
argument_list|(
literal|"pt"
argument_list|)
expr_stmt|;
name|supportedLangs
operator|.
name|add
argument_list|(
literal|"pl"
argument_list|)
expr_stmt|;
name|supportedLangs
operator|.
name|add
argument_list|(
literal|"nl"
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * The literal factory used to create types literals 	 */
specifier|private
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
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
comment|/** 	 * The default value for the Execution of this Engine. Currently set to 	 * {@link ServiceProperties#ORDERING_CONTENT_EXTRACTION} 	 */
specifier|public
specifier|static
specifier|final
name|Integer
name|defaultOrder
init|=
name|ORDERING_CONTENT_EXTRACTION
decl_stmt|;
comment|/** 	 * Currently used as fise:entity-type for TopicAnnotations 	 */
specifier|private
specifier|static
specifier|final
name|UriRef
name|OWL_CLASS
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://www.w3.org/2002/07/owl#Class"
argument_list|)
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
comment|//NOTE: one CAN NOT store the language as member, as EnhancementEngines
comment|//      can be called in parallel by multiple threads!
comment|//private String language = null;
comment|/** 	 * This contains the only MIME type directly supported by this enhancement 	 * engine. 	 */
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
literal|"http://linguagrid.org/LSGrid/ws/dbpedia-classification"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|SERVICE_URL
init|=
literal|"org.apache.stanbol.enhancer.engines.celi.classification.url"
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
name|ClassificationClientHTTP
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
name|ClassificationClientHTTP
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
comment|//canEnhance should inform if it can not enhance a ContentItem because
comment|//of an potential error in the EnhancementChain configuration, but not
comment|//throw runtime exceptions.
comment|//		if (language == null) {
comment|//			throw new IllegalStateException("Unable to extract Language for " + "ContentItem " + ci.getUri() + ": This is also checked in the canEnhance " + "method! -> This indicated an Bug in the implementation of the " + "EnhancementJobManager!");
comment|//		}
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
literal|" Please check that a language identification engine is active in this EnhancementChain."
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
block|{
comment|//NOTE: ENHANCE_ASYNC indicates that the computeEnhancements Method
comment|//      correctly applies read/write locks to the contentItem
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
comment|//NOTE: in the computeEnhancements Method on can check metadata already
comment|//      checked within the canEnhance method. THis is not required, but it
comment|//      may help to identify potential bugs in the EnhancementJobManager
comment|//      implementation
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
literal|": This is also checked in the canEnhance "
operator|+
literal|"method! -> This indicates an Bug in the implementation of "
operator|+
literal|"the EnhancementJobManager!"
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
comment|//NOTE: EnhancementEngine implementations should pass all Exceptions
comment|//      (RuntimeExceptions as is and others wrapped as EngineExceptions).
comment|//      The EnhancementJobManager implementation has to catch and
comment|//      process all those. Handling depends on the configuration of the
comment|//      EnhancementChain (e.g. if this engine is optional enhancement of
comment|//      the ContentItem will continue).
comment|//      This is important as otherwise Users would get "200 ok" replies
comment|//      for failed enhancement requests that have failed!
comment|//
comment|//      This means that:
comment|//      * Http clients should pass on IOExceptions and SOAPExceptions
comment|//      * No try/catch that also includes RuntimeExceptions
name|List
argument_list|<
name|Concept
argument_list|>
name|lista
decl_stmt|;
try|try
block|{
name|lista
operator|=
name|this
operator|.
name|client
operator|.
name|extractConcepts
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
comment|//re-throw exceptions as EngineException
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"Error while calling the CELI classification"
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
literal|"response to the CELI classification service!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
if|if
condition|(
name|lista
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//not topics found
return|return;
comment|//nothing to do
block|}
name|MGraph
name|g
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
comment|//NOTE: EnhancementEngines that use "ENHANCE_ASYNC" need to acquire a
comment|//      writeLock before modifications to the enhancement metadata
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
comment|//see STANBOL-617 for rules how to encode extracted topics
comment|//we need a single TextAnnotation to link all TopicAnnotations
name|UriRef
name|textAnnotation
init|=
name|createTextEnhancement
argument_list|(
name|ci
argument_list|,
name|this
argument_list|)
decl_stmt|;
comment|// add the dc:type skos:Concept
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|DC_TYPE
argument_list|,
name|SKOS_CONCEPT
argument_list|)
argument_list|)
expr_stmt|;
comment|//not create the fise:TopicAnnotations
for|for
control|(
name|Concept
name|ne
range|:
name|lista
control|)
block|{
name|UriRef
name|topicAnnotation
init|=
name|EnhancementEngineHelper
operator|.
name|createTopicEnhancement
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
name|topicAnnotation
argument_list|,
name|ENHANCER_ENTITY_REFERENCE
argument_list|,
name|ne
operator|.
name|getUri
argument_list|()
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
name|topicAnnotation
argument_list|,
name|ENHANCER_ENTITY_LABEL
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|ne
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//TODO: currently I use owl:class as entity-type, because that is
comment|//      what the linked dbpedia ontology resources are.
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|topicAnnotation
argument_list|,
name|ENHANCER_ENTITY_TYPE
argument_list|,
name|OWL_CLASS
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
name|topicAnnotation
argument_list|,
name|ENHANCER_CONFIDENCE
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|ne
operator|.
name|getConfidence
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//link to the TextAnnotation
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|topicAnnotation
argument_list|,
name|DC_RELATION
argument_list|,
name|textAnnotation
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

