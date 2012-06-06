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
name|ner
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
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|HashSet
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
name|NoConvertorException
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
name|Resource
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
name|BundleContext
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
literal|"celiNer"
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
block|}
argument_list|)
specifier|public
class|class
name|CeliNamedEntityExtractionEnhancementEngine
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
annotation|@
name|Reference
specifier|private
name|OnlineMode
name|onlineMode
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
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|UriRef
argument_list|>
name|entityTypes
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|UriRef
argument_list|>
argument_list|()
decl_stmt|;
static|static
block|{
name|entityTypes
operator|.
name|put
argument_list|(
literal|"pers"
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
argument_list|)
expr_stmt|;
name|entityTypes
operator|.
name|put
argument_list|(
literal|"PER"
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
argument_list|)
expr_stmt|;
name|entityTypes
operator|.
name|put
argument_list|(
literal|"loc"
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_PLACE
argument_list|)
expr_stmt|;
name|entityTypes
operator|.
name|put
argument_list|(
literal|"GPE"
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_PLACE
argument_list|)
expr_stmt|;
name|entityTypes
operator|.
name|put
argument_list|(
literal|"org"
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_ORGANISATION
argument_list|)
expr_stmt|;
name|entityTypes
operator|.
name|put
argument_list|(
literal|"time"
argument_list|,
name|OntologicalClasses
operator|.
name|SKOS_CONCEPT
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * The supported languages (configured via the {@link #SUPPORTED_LANGUAGES} 	 * configuration. 	 */
specifier|private
name|Collection
argument_list|<
name|String
argument_list|>
name|supportedLangs
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
name|CeliNamedEntityExtractionEnhancementEngine
operator|.
name|class
argument_list|)
decl_stmt|;
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
literal|"http://linguagrid.org/LSGrid/ws/com.celi-france.linguagrid.namedentityrecognition.v0u0.demo"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|SERVICE_URL
init|=
literal|"org.apache.stanbol.enhancer.engines.celi.ner.url"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
block|{
literal|"fr"
block|,
literal|"it"
block|}
argument_list|,
name|cardinality
operator|=
literal|1000
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|SUPPORTED_LANGUAGES
init|=
literal|"org.apache.stanbol.enhancer.engines.celi.ner.languages"
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
name|NERserviceClientHTTP
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
name|log
operator|.
name|info
argument_list|(
literal|"Activate CELI NER engine:"
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"> name: {}"
argument_list|,
name|getName
argument_list|()
argument_list|)
expr_stmt|;
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
name|this
operator|.
name|client
operator|=
operator|new
name|NERserviceClientHTTP
argument_list|(
name|this
operator|.
name|serviceURL
argument_list|,
name|this
operator|.
name|licenseKey
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"> CELI service: {}"
argument_list|,
name|serviceURL
argument_list|)
expr_stmt|;
comment|//init the supported languages (now configurable)
name|Object
name|languageObject
init|=
name|properties
operator|.
name|get
argument_list|(
name|SUPPORTED_LANGUAGES
argument_list|)
decl_stmt|;
name|HashSet
argument_list|<
name|String
argument_list|>
name|languages
decl_stmt|;
if|if
condition|(
name|languageObject
operator|instanceof
name|String
condition|)
block|{
comment|//support splitting multiple languages with ';'
name|languages
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|languageObject
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|languages
operator|.
name|remove
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Languages configuration '{}' contained empty language -> removed"
argument_list|,
name|languageObject
argument_list|)
expr_stmt|;
block|}
comment|//empty not allowed
block|}
elseif|else
if|if
condition|(
name|languageObject
operator|instanceof
name|Iterable
argument_list|<
name|?
argument_list|>
condition|)
block|{
comment|//does not work for arrays :(
name|languages
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Object
name|o
operator|:
operator|(
name|Iterable
argument_list|<
name|Object
argument_list|>
operator|)
name|languageObject
control|)
block|{
if|if
condition|(
name|o
operator|!=
literal|null
operator|&&
operator|!
name|o
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|languages
operator|.
name|add
argument_list|(
name|o
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Language configuration '{}' contained illegal value '{}' -> removed"
argument_list|,
name|languageObject
argument_list|,
name|o
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|languageObject
operator|.
name|getClass
argument_list|()
operator|.
name|isArray
argument_list|()
condition|)
block|{
name|languages
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Object
name|langObj
range|:
operator|(
name|Object
index|[]
operator|)
name|languageObject
control|)
block|{
if|if
condition|(
name|langObj
operator|!=
literal|null
condition|)
block|{
name|languages
operator|.
name|add
argument_list|(
name|langObj
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Language configuration '{}' contained illegal value '{}' -> removed"
argument_list|,
name|Arrays
operator|.
name|toString
argument_list|(
operator|(
name|Object
index|[]
operator|)
name|languageObject
argument_list|)
argument_list|,
name|langObj
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|languages
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|languages
operator|==
literal|null
operator|||
name|languages
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|SUPPORTED_LANGUAGES
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"Missing or invalid configuration of the supported languages (config :'%s'"
argument_list|,
name|languageObject
operator|!=
literal|null
operator|&&
name|languageObject
operator|.
name|getClass
argument_list|()
operator|.
name|isArray
argument_list|()
condition|?
name|Arrays
operator|.
name|toString
argument_list|(
operator|(
name|Object
index|[]
operator|)
name|languageObject
argument_list|)
else|:
comment|//nicer logging for arrays
name|languageObject
argument_list|)
argument_list|)
throw|;
block|}
name|this
operator|.
name|supportedLangs
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|languages
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"> supported languages: {}"
argument_list|,
name|supportedLangs
argument_list|)
expr_stmt|;
block|}
end_class

begin_function
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
name|this
operator|.
name|supportedLangs
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|client
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|serviceURL
operator|=
literal|null
expr_stmt|;
block|}
end_function

begin_function
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
name|info
argument_list|(
literal|"Unable to extract language annotation for ContentItem  -> will not enhance"
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|CANNOT_ENHANCE
return|;
block|}
elseif|else
if|if
condition|(
operator|!
name|isLangSupported
argument_list|(
name|language
argument_list|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Language '{}' of contentItem {} is not supported (supported: {}) -> will not enhance"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|language
block|,
name|ci
operator|.
name|getUri
argument_list|()
block|,
name|supportedLangs
block|}
argument_list|)
expr_stmt|;
return|return
name|CANNOT_ENHANCE
return|;
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
condition|)
return|return
name|ENHANCE_ASYNC
return|;
else|else
name|log
operator|.
name|debug
argument_list|(
literal|"No Content of type {} found in ConentItem {} -> will not enhance"
argument_list|,
name|SUPPORTED_MIMTYPES
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|CANNOT_ENHANCE
return|;
block|}
end_function

begin_function
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
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to extract Language for "
operator|+
literal|"ContentItem "
operator|+
name|ci
operator|.
name|getUri
argument_list|()
operator|+
literal|": This is also checked in the canEnhance "
operator|+
literal|"method! -> This indicated an Bug in the implementation of the "
operator|+
literal|"EnhancementJobManager!"
argument_list|)
throw|;
block|}
name|Language
name|lang
init|=
operator|new
name|Language
argument_list|(
name|language
argument_list|)
decl_stmt|;
comment|//used for the palin literals in TextAnnotations
try|try
block|{
name|List
argument_list|<
name|NamedEntity
argument_list|>
name|lista
init|=
name|this
operator|.
name|client
operator|.
name|extractEntities
argument_list|(
name|text
argument_list|,
name|language
argument_list|)
decl_stmt|;
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|MGraph
name|g
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
for|for
control|(
name|NamedEntity
name|ne
range|:
name|lista
control|)
block|{
try|try
block|{
name|UriRef
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
comment|//add selected text as PlainLiteral in the language extracted from the text
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
name|ne
operator|.
name|getFormKind
argument_list|()
argument_list|,
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
name|textAnnotation
argument_list|,
name|DC_TYPE
argument_list|,
name|getEntityRefForType
argument_list|(
name|ne
operator|.
name|type
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|ne
operator|.
name|getFrom
argument_list|()
operator|!=
literal|null
operator|&&
name|ne
operator|.
name|getTo
argument_list|()
operator|!=
literal|null
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
name|ne
operator|.
name|getFrom
argument_list|()
operator|.
name|intValue
argument_list|()
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
name|ne
operator|.
name|getTo
argument_list|()
operator|.
name|intValue
argument_list|()
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
name|ne
operator|.
name|getFormKind
argument_list|()
argument_list|,
name|ne
operator|.
name|getFrom
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
argument_list|,
name|lang
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|NoConvertorException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
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
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"Error while calling the CELI NER (Named Entity Recognition)"
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
literal|"response to the CELI NER (Named Entity Recognition) service!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
end_function

begin_function
specifier|private
name|boolean
name|isLangSupported
parameter_list|(
name|String
name|language
parameter_list|)
block|{
return|return
name|supportedLangs
operator|.
name|contains
argument_list|(
name|language
argument_list|)
return|;
block|}
end_function

begin_function
specifier|private
name|Resource
name|getEntityRefForType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
if|if
condition|(
operator|!
name|entityTypes
operator|.
name|containsKey
argument_list|(
name|type
argument_list|)
condition|)
return|return
name|OntologicalClasses
operator|.
name|SKOS_CONCEPT
return|;
else|else
return|return
name|entityTypes
operator|.
name|get
argument_list|(
name|type
argument_list|)
return|;
block|}
end_function

begin_function
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
end_function

unit|}
end_unit

