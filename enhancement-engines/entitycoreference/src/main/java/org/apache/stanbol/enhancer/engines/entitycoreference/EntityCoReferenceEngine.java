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
name|entitycoreference
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
name|enhancer
operator|.
name|engines
operator|.
name|entitycoreference
operator|.
name|datamodel
operator|.
name|NounPhrase
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
name|entitycoreference
operator|.
name|impl
operator|.
name|CoreferenceFinder
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
name|entitycoreference
operator|.
name|impl
operator|.
name|NounPhraseFilterer
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
name|ner
operator|.
name|NerTag
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|Entityhub
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
operator|.
name|SiteManager
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
comment|/**  * This engine extracts references in the given text of noun phrases which point to NERs. The coreference is  * performed based on matching several of the named entity's dbpedia/yago properties to the noun phrase  * tokens.  *   * TODO - Be able to detect possessive coreferences such as Germany's prime minister   * TODO - be able to detect products and their developer such as Iphone 7 and Apple's new device.   * TODO - provide the ability via config for the user to also allow coreferencing of 1 word noun phrases based   * solely on comparison with entity class type?  *   * @author Cristian Petroaca  *   */
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
argument_list|)
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|EnhancementEngine
operator|.
name|class
argument_list|)
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
literal|"entity-coreference"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityCoReferenceEngine
operator|.
name|CONFIG_LANGUAGES
argument_list|,
name|value
operator|=
literal|"en"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityCoReferenceEngine
operator|.
name|REFERENCED_SITE_ID
argument_list|,
name|value
operator|=
literal|"entity-coref-dbpedia"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityCoReferenceEngine
operator|.
name|ENTITY_URI_BASE
argument_list|,
name|value
operator|=
literal|"http://dbpedia.org/resource/"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityCoReferenceEngine
operator|.
name|MAX_DISTANCE
argument_list|,
name|intValue
operator|=
name|Constants
operator|.
name|MAX_DISTANCE_DEFAULT_VALUE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityCoReferenceEngine
operator|.
name|SPATIAL_ATTR_FOR_PERSON
argument_list|,
name|value
operator|=
name|Constants
operator|.
name|DEFAULT_SPATIAL_ATTR_FOR_PERSON
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityCoReferenceEngine
operator|.
name|SPATIAL_ATTR_FOR_ORGANIZATION
argument_list|,
name|value
operator|=
name|Constants
operator|.
name|DEFAULT_SPATIAL_ATTR_FOR_ORGANIZATION
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityCoReferenceEngine
operator|.
name|SPATIAL_ATTR_FOR_PLACE
argument_list|,
name|value
operator|=
name|Constants
operator|.
name|DEFAULT_SPATIAL_ATTR_FOR_PLACE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityCoReferenceEngine
operator|.
name|ORG_ATTR_FOR_PERSON
argument_list|,
name|value
operator|=
name|Constants
operator|.
name|DEFAULT_ORG_ATTR_FOR_PERSON
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityCoReferenceEngine
operator|.
name|ENTITY_CLASSES_TO_EXCLUDE
argument_list|,
name|value
operator|=
name|Constants
operator|.
name|DEFAULT_ENTITY_CLASSES_TO_EXCLUDE
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|EntityCoReferenceEngine
extends|extends
name|AbstractEnhancementEngine
argument_list|<
name|RuntimeException
argument_list|,
name|RuntimeException
argument_list|>
implements|implements
name|EnhancementEngine
implements|,
name|ServiceProperties
block|{
specifier|private
specifier|static
specifier|final
name|Integer
name|ENGINE_ORDERING
init|=
name|ServiceProperties
operator|.
name|ORDERING_POST_PROCESSING
operator|+
literal|91
decl_stmt|;
comment|/**      * Language configuration. Takes a list of ISO language codes of supported languages. Currently supported      * are the languages given as default value.      */
specifier|protected
specifier|static
specifier|final
name|String
name|CONFIG_LANGUAGES
init|=
literal|"enhancer.engine.entitycoreference.languages"
decl_stmt|;
comment|/**      * Referenced site configuration. Defaults to dbpedia.      */
specifier|protected
specifier|static
specifier|final
name|String
name|REFERENCED_SITE_ID
init|=
literal|"enhancer.engine.entitycoreference.referencedSiteId"
decl_stmt|;
comment|/**      *       */
specifier|protected
specifier|static
specifier|final
name|String
name|ENTITY_URI_BASE
init|=
literal|"enhancer.engine.entitycoreference.entity.uri.base"
decl_stmt|;
comment|/**      * Maximum sentence distance between the ner and the noun phrase which mentions it. -1 means no distance      * constraint.      */
specifier|protected
specifier|static
specifier|final
name|String
name|MAX_DISTANCE
init|=
literal|"enhancer.engine.entitycoreference.maxDistance"
decl_stmt|;
comment|/**      * Attributes used for spatial coreference when dealing with a person entity.      */
specifier|protected
specifier|static
specifier|final
name|String
name|SPATIAL_ATTR_FOR_PERSON
init|=
literal|"enhancer.engine.entitycoreference.spatial.attr.person"
decl_stmt|;
comment|/**      * Attributes used for spatial coreference when dealing with an organization entity.      */
specifier|protected
specifier|static
specifier|final
name|String
name|SPATIAL_ATTR_FOR_ORGANIZATION
init|=
literal|"enhancer.engine.entitycoreference.spatial.attr.org"
decl_stmt|;
comment|/**      * Attributes used for spatial coreference when dealing with a place entity.      */
specifier|protected
specifier|static
specifier|final
name|String
name|SPATIAL_ATTR_FOR_PLACE
init|=
literal|"enhancer.engine.entitycoreference.spatial.attr.place"
decl_stmt|;
comment|/**      * Attributes used for organisational membership coreference when dealing with a person entity.      */
specifier|protected
specifier|static
specifier|final
name|String
name|ORG_ATTR_FOR_PERSON
init|=
literal|"enhancer.engine.entitycoreference.org.attr.person"
decl_stmt|;
comment|/**      * Entity classes which will be excluded when doing the entity class type matching       * because they are too general in nature.      */
specifier|protected
specifier|static
specifier|final
name|String
name|ENTITY_CLASSES_TO_EXCLUDE
init|=
literal|"enhancer.engine.entitycoreference.entity.classes.excluded"
decl_stmt|;
comment|/**      * Logger      */
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|EntityCoReferenceEngine
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Service of the Entityhub that manages all the active referenced Site. This Service is used to lookup      * the configured Referenced Site when we need to enhance a content item.      */
annotation|@
name|Reference
specifier|protected
name|SiteManager
name|siteManager
decl_stmt|;
comment|/**      * Used to lookup Entities if the {@link #REFERENCED_SITE_ID} property is set to "entityhub" or "local"      */
annotation|@
name|Reference
specifier|protected
name|Entityhub
name|entityhub
decl_stmt|;
comment|/**      * Specialized class which filters out bad noun phrases based on the language.      */
specifier|private
name|NounPhraseFilterer
name|nounPhraseFilterer
decl_stmt|;
comment|/**      * Performs the logic needed to find corefs based on the NERs and noun phrases in the text.      */
specifier|private
name|CoreferenceFinder
name|corefFinder
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
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
name|config
init|=
name|ctx
operator|.
name|getProperties
argument_list|()
decl_stmt|;
comment|/* Step 1 - initialize the {@link NounPhraseFilterer} with the language config */
name|String
name|languages
init|=
operator|(
name|String
operator|)
name|config
operator|.
name|get
argument_list|(
name|CONFIG_LANGUAGES
argument_list|)
decl_stmt|;
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
name|CONFIG_LANGUAGES
argument_list|,
literal|"The Languages Config is a required Parameter and MUST NOT be NULL or an empty String!"
argument_list|)
throw|;
block|}
name|nounPhraseFilterer
operator|=
operator|new
name|NounPhraseFilterer
argument_list|(
name|languages
operator|.
name|split
argument_list|(
literal|","
argument_list|)
argument_list|)
expr_stmt|;
comment|/* Step 2 - initialize the {@link CoreferenceFinder} */
name|String
name|referencedSiteID
init|=
literal|null
decl_stmt|;
name|Object
name|referencedSiteIDfromConfig
init|=
name|config
operator|.
name|get
argument_list|(
name|REFERENCED_SITE_ID
argument_list|)
decl_stmt|;
if|if
condition|(
name|referencedSiteIDfromConfig
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|REFERENCED_SITE_ID
argument_list|,
literal|"The ID of the Referenced Site is a required Parameter and MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|referencedSiteID
operator|=
name|referencedSiteIDfromConfig
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|referencedSiteID
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|REFERENCED_SITE_ID
argument_list|,
literal|"The ID of the Referenced Site is a required Parameter and MUST NOT be an empty String!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|Entityhub
operator|.
name|ENTITYHUB_IDS
operator|.
name|contains
argument_list|(
name|referencedSiteID
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Init NamedEntityTaggingEngine instance for the Entityhub"
argument_list|)
expr_stmt|;
name|referencedSiteID
operator|=
literal|null
expr_stmt|;
block|}
name|int
name|maxDistance
decl_stmt|;
name|Object
name|maxDistanceFromConfig
init|=
name|config
operator|.
name|get
argument_list|(
name|MAX_DISTANCE
argument_list|)
decl_stmt|;
if|if
condition|(
name|maxDistanceFromConfig
operator|==
literal|null
condition|)
block|{
name|maxDistance
operator|=
name|Constants
operator|.
name|MAX_DISTANCE_DEFAULT_VALUE
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|maxDistanceFromConfig
operator|instanceof
name|Number
condition|)
block|{
name|maxDistance
operator|=
operator|(
operator|(
name|Number
operator|)
name|maxDistanceFromConfig
operator|)
operator|.
name|intValue
argument_list|()
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|maxDistance
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|maxDistanceFromConfig
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|nfe
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|MAX_DISTANCE
argument_list|,
literal|"The Max Distance parameter must be a number"
argument_list|)
throw|;
block|}
block|}
if|if
condition|(
name|maxDistance
operator|<
operator|-
literal|1
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|MAX_DISTANCE
argument_list|,
literal|"The Max Distance parameter must not be smaller than -1"
argument_list|)
throw|;
block|}
name|String
name|entityUriBase
init|=
operator|(
name|String
operator|)
name|config
operator|.
name|get
argument_list|(
name|ENTITY_URI_BASE
argument_list|)
decl_stmt|;
if|if
condition|(
name|entityUriBase
operator|==
literal|null
operator|||
name|entityUriBase
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|ENTITY_URI_BASE
argument_list|,
literal|"The Entity Uri Base parameter cannot be empty"
argument_list|)
throw|;
block|}
name|String
name|spatialAttrForPerson
init|=
operator|(
name|String
operator|)
name|config
operator|.
name|get
argument_list|(
name|SPATIAL_ATTR_FOR_PERSON
argument_list|)
decl_stmt|;
name|String
name|spatialAttrForOrg
init|=
operator|(
name|String
operator|)
name|config
operator|.
name|get
argument_list|(
name|SPATIAL_ATTR_FOR_ORGANIZATION
argument_list|)
decl_stmt|;
name|String
name|spatialAttrForPlace
init|=
operator|(
name|String
operator|)
name|config
operator|.
name|get
argument_list|(
name|SPATIAL_ATTR_FOR_PLACE
argument_list|)
decl_stmt|;
name|String
name|orgAttrForPerson
init|=
operator|(
name|String
operator|)
name|config
operator|.
name|get
argument_list|(
name|ORG_ATTR_FOR_PERSON
argument_list|)
decl_stmt|;
name|String
name|entityClassesToExclude
init|=
operator|(
name|String
operator|)
name|config
operator|.
name|get
argument_list|(
name|ENTITY_CLASSES_TO_EXCLUDE
argument_list|)
decl_stmt|;
name|corefFinder
operator|=
operator|new
name|CoreferenceFinder
argument_list|(
name|languages
operator|.
name|split
argument_list|(
literal|","
argument_list|)
argument_list|,
name|siteManager
argument_list|,
name|entityhub
argument_list|,
name|referencedSiteID
argument_list|,
name|maxDistance
argument_list|,
name|entityUriBase
argument_list|,
name|spatialAttrForPerson
argument_list|,
name|spatialAttrForOrg
argument_list|,
name|spatialAttrForPlace
argument_list|,
name|orgAttrForPerson
argument_list|,
name|entityClassesToExclude
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"activate {}[name:{}]"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|getName
argument_list|()
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
name|ENGINE_ORDERING
argument_list|)
argument_list|)
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
name|log
operator|.
name|debug
argument_list|(
literal|"Engine {} ignores ContentItem {} becuase language {} is not detected."
argument_list|,
operator|new
name|Object
index|[]
block|{
name|getName
argument_list|()
block|,
name|ci
operator|.
name|getUri
argument_list|()
block|,
name|language
block|}
argument_list|)
expr_stmt|;
return|return
name|CANNOT_ENHANCE
return|;
block|}
if|if
condition|(
operator|!
name|nounPhraseFilterer
operator|.
name|supportsLanguage
argument_list|(
name|language
argument_list|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Engine {} does not support language {}."
argument_list|,
operator|new
name|Object
index|[]
block|{
name|getName
argument_list|()
block|,
name|language
block|}
argument_list|)
expr_stmt|;
return|return
name|CANNOT_ENHANCE
return|;
block|}
return|return
name|ENHANCE_SYNCHRONOUS
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
comment|/*          * Step 1 - Build the NER list and the noun phrase list.          *           * TODO - the noun phrases need to be lemmatized.          */
name|Map
argument_list|<
name|Integer
argument_list|,
name|List
argument_list|<
name|Span
argument_list|>
argument_list|>
name|ners
init|=
operator|new
name|HashMap
argument_list|<
name|Integer
argument_list|,
name|List
argument_list|<
name|Span
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|NounPhrase
argument_list|>
name|nounPhrases
init|=
operator|new
name|ArrayList
argument_list|<
name|NounPhrase
argument_list|>
argument_list|()
decl_stmt|;
name|extractNersAndNounPhrases
argument_list|(
name|ci
argument_list|,
name|ners
argument_list|,
name|nounPhrases
argument_list|)
expr_stmt|;
comment|/*          * If there are no NERs to reference there's nothing to do but exit.          */
if|if
condition|(
name|ners
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Did not find any NERs for which to do the coreferencing"
argument_list|)
expr_stmt|;
return|return;
block|}
comment|/*          * Step 2 - Filter out bad noun phrases.          */
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
name|log
operator|.
name|info
argument_list|(
literal|"Could not detect the language of the text"
argument_list|)
expr_stmt|;
return|return;
block|}
name|nounPhraseFilterer
operator|.
name|filter
argument_list|(
name|nounPhrases
argument_list|,
name|language
argument_list|)
expr_stmt|;
comment|/*          * If there are no good noun phrases there's nothing to do but exit.          */
if|if
condition|(
name|nounPhrases
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Did not find any noun phrases with which to do the coreferencing"
argument_list|)
expr_stmt|;
return|return;
block|}
comment|/*          * Step 3 - Extract corefs and write them as {@link NlpAnnotations.COREF_ANNOTATION}s in the {@link          * Span}s          */
name|corefFinder
operator|.
name|extractCorefs
argument_list|(
name|ners
argument_list|,
name|nounPhrases
argument_list|,
name|language
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
name|ctx
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"deactivate {}[name:{}]"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|nounPhraseFilterer
operator|=
literal|null
expr_stmt|;
name|corefFinder
operator|=
literal|null
expr_stmt|;
name|super
operator|.
name|deactivate
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
block|}
comment|/**      * Extracts the NERs and the noun phrases from the given text and puts them in the given lists.      *       * @param ci      * @param ners      * @param nounPhrases      */
specifier|private
name|void
name|extractNersAndNounPhrases
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|Map
argument_list|<
name|Integer
argument_list|,
name|List
argument_list|<
name|Span
argument_list|>
argument_list|>
name|ners
parameter_list|,
name|List
argument_list|<
name|NounPhrase
argument_list|>
name|nounPhrases
parameter_list|)
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
name|Iterator
argument_list|<
name|?
extends|extends
name|Section
argument_list|>
name|sections
init|=
name|at
operator|.
name|getSentences
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|sections
operator|.
name|hasNext
argument_list|()
condition|)
block|{
comment|// process as single sentence
name|sections
operator|=
name|Collections
operator|.
name|singleton
argument_list|(
name|at
argument_list|)
operator|.
name|iterator
argument_list|()
expr_stmt|;
block|}
name|int
name|sentenceCnt
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|sections
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|sentenceCnt
operator|++
expr_stmt|;
name|Section
name|section
init|=
name|sections
operator|.
name|next
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|NounPhrase
argument_list|>
name|sectionNounPhrases
init|=
operator|new
name|ArrayList
argument_list|<
name|NounPhrase
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Span
argument_list|>
name|sectionNers
init|=
operator|new
name|ArrayList
argument_list|<
name|Span
argument_list|>
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Span
argument_list|>
name|chunks
init|=
name|section
operator|.
name|getEnclosed
argument_list|(
name|EnumSet
operator|.
name|of
argument_list|(
name|SpanTypeEnum
operator|.
name|Chunk
argument_list|)
argument_list|)
decl_stmt|;
while|while
condition|(
name|chunks
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Span
name|chunk
init|=
name|chunks
operator|.
name|next
argument_list|()
decl_stmt|;
name|Value
argument_list|<
name|NerTag
argument_list|>
name|ner
init|=
name|chunk
operator|.
name|getAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|NER_ANNOTATION
argument_list|)
decl_stmt|;
if|if
condition|(
name|ner
operator|!=
literal|null
condition|)
block|{
name|sectionNers
operator|.
name|add
argument_list|(
name|chunk
argument_list|)
expr_stmt|;
block|}
name|Value
argument_list|<
name|PhraseTag
argument_list|>
name|phrase
init|=
name|chunk
operator|.
name|getAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|PHRASE_ANNOTATION
argument_list|)
decl_stmt|;
if|if
condition|(
name|phrase
operator|!=
literal|null
operator|&&
name|phrase
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
name|sectionNounPhrases
operator|.
name|add
argument_list|(
operator|new
name|NounPhrase
argument_list|(
name|chunk
argument_list|,
name|sentenceCnt
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|NounPhrase
name|nounPhrase
range|:
name|sectionNounPhrases
control|)
block|{
name|Iterator
argument_list|<
name|Span
argument_list|>
name|tokens
init|=
name|section
operator|.
name|getEnclosed
argument_list|(
name|EnumSet
operator|.
name|of
argument_list|(
name|SpanTypeEnum
operator|.
name|Token
argument_list|)
argument_list|)
decl_stmt|;
while|while
condition|(
name|tokens
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Span
name|token
init|=
name|tokens
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|nounPhrase
operator|.
name|containsSpan
argument_list|(
name|token
argument_list|)
condition|)
block|{
name|nounPhrase
operator|.
name|addToken
argument_list|(
name|token
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Span
name|sectionNer
range|:
name|sectionNers
control|)
block|{
if|if
condition|(
name|nounPhrase
operator|.
name|containsSpan
argument_list|(
name|sectionNer
argument_list|)
condition|)
block|{
name|nounPhrase
operator|.
name|addNerChunk
argument_list|(
name|sectionNer
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|nounPhrases
operator|.
name|addAll
argument_list|(
name|sectionNounPhrases
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|sectionNers
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ners
operator|.
name|put
argument_list|(
name|sentenceCnt
argument_list|,
name|sectionNers
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

