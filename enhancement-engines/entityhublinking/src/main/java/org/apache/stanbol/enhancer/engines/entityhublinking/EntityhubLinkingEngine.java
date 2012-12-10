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
name|entityhublinking
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
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|CASE_SENSITIVE
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|DEFAULT_CASE_SENSITIVE_MATCHING_STATE
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|DEFAULT_DEREFERENCE_ENTITIES_STATE
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|DEFAULT_MATCHING_LANGUAGE
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|DEFAULT_MIN_SEARCH_TOKEN_LENGTH
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|DEFAULT_MIN_TOKEN_SCORE
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|DEFAULT_SUGGESTIONS
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|DEREFERENCE_ENTITIES
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|DEREFERENCE_ENTITIES_FIELDS
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|MIN_SEARCH_TOKEN_LENGTH
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|MIN_TOKEN_SCORE
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|NAME_FIELD
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|REDIRECT_FIELD
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|REDIRECT_MODE
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|SUGGESTIONS
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|TYPE_FIELD
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
operator|.
name|TYPE_MAPPINGS
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|TextProcessingConfig
operator|.
name|DEFAULT_PROCESS_ONLY_PROPER_NOUNS_STATE
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|TextProcessingConfig
operator|.
name|PROCESSED_LANGUAGES
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|TextProcessingConfig
operator|.
name|PROCESS_ONLY_PROPER_NOUNS_STATE
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
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Constants
operator|.
name|SERVICE_RANKING
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
name|Hashtable
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
name|PropertyOption
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
name|stanbol
operator|.
name|commons
operator|.
name|namespaceprefix
operator|.
name|NamespacePrefixService
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
name|entitylinking
operator|.
name|LabelTokenizer
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
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
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
name|entitylinking
operator|.
name|config
operator|.
name|TextProcessingConfig
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
name|entitylinking
operator|.
name|engine
operator|.
name|EntityLinkingEngine
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
name|framework
operator|.
name|ServiceReference
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
name|ServiceRegistration
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
name|osgi
operator|.
name|util
operator|.
name|tracker
operator|.
name|ServiceTrackerCustomizer
import|;
end_import

begin_comment
comment|/**  * The EntityhubLinkingEngine in NOT an {@link EnhancementEngine} but only an  * OSGI {@link Component} that allows to configure instances of the  * {@link EntityLinkingEngine} using an {@link ReferencedSiteSearcher} or  * {@link EntityhubSearcher} to link entities.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|configurationFactory
operator|=
literal|true
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|REQUIRE
argument_list|,
comment|// the baseUri is required!
name|specVersion
operator|=
literal|"1.1"
argument_list|,
name|metatype
operator|=
literal|true
argument_list|,
name|immediate
operator|=
literal|true
argument_list|,
name|inherit
operator|=
literal|true
argument_list|)
annotation|@
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
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PROPERTY_NAME
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityhubLinkingEngine
operator|.
name|SITE_ID
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|NAME_FIELD
argument_list|,
name|value
operator|=
literal|"rdfs:label"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|CASE_SENSITIVE
argument_list|,
name|boolValue
operator|=
name|DEFAULT_CASE_SENSITIVE_MATCHING_STATE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|TYPE_FIELD
argument_list|,
name|value
operator|=
literal|"rdf:type"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|REDIRECT_FIELD
argument_list|,
name|value
operator|=
literal|"rdfs:seeAlso"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|REDIRECT_MODE
argument_list|,
name|options
operator|=
block|{
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|'%'
operator|+
name|REDIRECT_MODE
operator|+
literal|".option.ignore"
argument_list|,
name|name
operator|=
literal|"IGNORE"
argument_list|)
block|,
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|'%'
operator|+
name|REDIRECT_MODE
operator|+
literal|".option.addValues"
argument_list|,
name|name
operator|=
literal|"ADD_VALUES"
argument_list|)
block|,
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|'%'
operator|+
name|REDIRECT_MODE
operator|+
literal|".option.follow"
argument_list|,
name|name
operator|=
literal|"FOLLOW"
argument_list|)
block|}
argument_list|,
name|value
operator|=
literal|"IGNORE"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|MIN_SEARCH_TOKEN_LENGTH
argument_list|,
name|intValue
operator|=
name|DEFAULT_MIN_SEARCH_TOKEN_LENGTH
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|MIN_TOKEN_SCORE
argument_list|,
name|floatValue
operator|=
name|DEFAULT_MIN_TOKEN_SCORE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SUGGESTIONS
argument_list|,
name|intValue
operator|=
name|DEFAULT_SUGGESTIONS
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PROCESS_ONLY_PROPER_NOUNS_STATE
argument_list|,
name|boolValue
operator|=
name|DEFAULT_PROCESS_ONLY_PROPER_NOUNS_STATE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PROCESSED_LANGUAGES
argument_list|,
name|cardinality
operator|=
name|Integer
operator|.
name|MAX_VALUE
argument_list|,
name|value
operator|=
block|{
literal|"*;lmmtip;uc=LINK;prop=0.75;pprob=0.75"
block|,
comment|// link multiple matchable tokens in chunks; link upper case words
literal|"de;uc=MATCH"
block|,
comment|//in German all Nouns are upper case
literal|"es;lc=Noun"
block|,
comment|//the OpenNLP POS tagger for Spanish does not support ProperNouns
literal|"nl;lc=Noun"
block|}
argument_list|)
block|,
comment|//same for Dutch
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|DEFAULT_MATCHING_LANGUAGE
argument_list|,
name|value
operator|=
literal|""
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|TYPE_MAPPINGS
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
name|DEREFERENCE_ENTITIES
argument_list|,
name|boolValue
operator|=
name|DEFAULT_DEREFERENCE_ENTITIES_STATE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|DEREFERENCE_ENTITIES_FIELDS
argument_list|,
name|cardinality
operator|=
name|Integer
operator|.
name|MAX_VALUE
argument_list|,
name|value
operator|=
block|{
literal|"http://www.w3.org/2000/01/rdf-schema#comment"
block|,
literal|"http://www.w3.org/2003/01/geo/wgs84_pos#lat"
block|,
literal|"http://www.w3.org/2003/01/geo/wgs84_pos#long"
block|,
literal|"http://xmlns.com/foaf/0.1/depiction"
block|,
literal|"http://dbpedia.org/ontology/thumbnail"
block|}
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SERVICE_RANKING
argument_list|,
name|intValue
operator|=
literal|0
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|EntityhubLinkingEngine
implements|implements
name|ServiceTrackerCustomizer
block|{
comment|//private final Logger log = LoggerFactory.getLogger(EntityhubLinkingEngine.class);
annotation|@
name|Reference
name|NamespacePrefixService
name|prefixService
decl_stmt|;
comment|/**      * The id of the Entityhub Site (Referenced or Managed Site) used for matching.<p>      * To match against the Entityhub use "entityhub" as value.      */
specifier|public
specifier|static
specifier|final
name|String
name|SITE_ID
init|=
literal|"enhancer.engines.entityhublinking.siteId"
decl_stmt|;
comment|/**      * The engine initialised based on the configuration of this component      */
specifier|protected
name|EntityLinkingEngine
name|entityLinkingEngine
decl_stmt|;
specifier|protected
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|engineMetadata
decl_stmt|;
comment|/**      * The service registration for the {@link #entityLinkingEngine}      */
specifier|protected
name|ServiceRegistration
name|engineRegistration
decl_stmt|;
comment|/**      * The EntitySearcher used for the {@link #entityLinkingEngine}      */
specifier|private
name|TrackingEntitySearcher
argument_list|<
name|?
argument_list|>
name|entitySearcher
decl_stmt|;
name|int
name|trackedServiceCount
init|=
literal|0
decl_stmt|;
comment|/**      * the MainLabelTokenizer      */
annotation|@
name|Reference
specifier|protected
name|LabelTokenizer
name|labelTokenizer
decl_stmt|;
comment|/**      * The name of the reference site ('local' or 'entityhub') if the      * Entityhub is used for enhancing      */
specifier|protected
name|String
name|siteName
decl_stmt|;
specifier|private
name|BundleContext
name|bundleContext
decl_stmt|;
comment|/**      * Default constructor as used by OSGI. This expects that       * {@link #activate(ComponentContext)} is called before usage      */
specifier|public
name|EntityhubLinkingEngine
parameter_list|()
block|{     }
annotation|@
name|Activate
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
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
name|bundleContext
operator|=
name|ctx
operator|.
name|getBundleContext
argument_list|()
expr_stmt|;
name|EntityLinkerConfig
name|linkerConfig
init|=
name|EntityLinkerConfig
operator|.
name|createInstance
argument_list|(
name|properties
argument_list|,
name|prefixService
argument_list|)
decl_stmt|;
name|TextProcessingConfig
name|textProcessingConfig
init|=
name|TextProcessingConfig
operator|.
name|createInstance
argument_list|(
name|properties
argument_list|)
decl_stmt|;
name|Object
name|value
init|=
name|properties
operator|.
name|get
argument_list|(
name|SITE_ID
argument_list|)
decl_stmt|;
comment|//init the EntitySource
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|SITE_ID
argument_list|,
literal|"The ID of the Referenced Site is a required Parameter and MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|siteName
operator|=
name|value
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|siteName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|SITE_ID
argument_list|,
literal|"The ID of the Referenced Site is a required Parameter and MUST NOT be an empty String!"
argument_list|)
throw|;
block|}
comment|//get the metadata later set to the enhancement engine
name|String
name|engineName
decl_stmt|;
name|engineMetadata
operator|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
expr_stmt|;
name|value
operator|=
name|properties
operator|.
name|get
argument_list|(
name|PROPERTY_NAME
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_NAME
argument_list|,
literal|"The EnhancementEngine name MUST BE configured!"
argument_list|)
throw|;
block|}
else|else
block|{
name|engineName
operator|=
name|value
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|engineMetadata
operator|.
name|put
argument_list|(
name|PROPERTY_NAME
argument_list|,
name|value
argument_list|)
expr_stmt|;
name|value
operator|=
name|properties
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|)
expr_stmt|;
name|engineMetadata
operator|.
name|put
argument_list|(
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|,
name|value
operator|==
literal|null
condition|?
name|Integer
operator|.
name|valueOf
argument_list|(
literal|0
argument_list|)
else|:
name|value
argument_list|)
expr_stmt|;
comment|//init the tracking entity searcher
name|trackedServiceCount
operator|=
literal|0
expr_stmt|;
if|if
condition|(
name|Entityhub
operator|.
name|ENTITYHUB_IDS
operator|.
name|contains
argument_list|(
name|siteName
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|entitySearcher
operator|=
operator|new
name|EntityhubSearcher
argument_list|(
name|bundleContext
argument_list|,
literal|10
argument_list|,
name|this
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entitySearcher
operator|=
operator|new
name|ReferencedSiteSearcher
argument_list|(
name|bundleContext
argument_list|,
name|siteName
argument_list|,
literal|10
argument_list|,
name|this
argument_list|)
expr_stmt|;
block|}
comment|//create the engine
name|entityLinkingEngine
operator|=
operator|new
name|EntityLinkingEngine
argument_list|(
name|engineName
argument_list|,
name|entitySearcher
argument_list|,
comment|//the searcher might not be available
name|textProcessingConfig
argument_list|,
name|linkerConfig
argument_list|,
name|labelTokenizer
argument_list|)
expr_stmt|;
comment|//start tracking
name|entitySearcher
operator|.
name|open
argument_list|()
expr_stmt|;
block|}
comment|/**      * Deactivates this components.       */
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
comment|//TODO:
comment|//* unregister service
name|ServiceRegistration
name|reg
init|=
name|engineRegistration
decl_stmt|;
if|if
condition|(
name|reg
operator|!=
literal|null
condition|)
block|{
name|reg
operator|.
name|unregister
argument_list|()
expr_stmt|;
name|engineRegistration
operator|=
literal|null
expr_stmt|;
block|}
comment|//* reset engine
name|entityLinkingEngine
operator|=
literal|null
expr_stmt|;
name|engineMetadata
operator|=
literal|null
expr_stmt|;
comment|//close the tracking EntitySearcher
name|entitySearcher
operator|.
name|close
argument_list|()
expr_stmt|;
name|entitySearcher
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|addingService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|)
block|{
name|BundleContext
name|bc
init|=
name|this
operator|.
name|bundleContext
decl_stmt|;
if|if
condition|(
name|bc
operator|!=
literal|null
condition|)
block|{
name|Object
name|service
init|=
name|bc
operator|.
name|getService
argument_list|(
name|reference
argument_list|)
decl_stmt|;
if|if
condition|(
name|service
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|trackedServiceCount
operator|==
literal|0
condition|)
block|{
comment|//register the service
name|engineRegistration
operator|=
name|bc
operator|.
name|registerService
argument_list|(
operator|new
name|String
index|[]
block|{
name|EnhancementEngine
operator|.
name|class
operator|.
name|getName
argument_list|()
block|,
name|ServiceProperties
operator|.
name|class
operator|.
name|getName
argument_list|()
block|}
argument_list|,
name|entityLinkingEngine
argument_list|,
name|engineMetadata
argument_list|)
expr_stmt|;
block|}
name|trackedServiceCount
operator|++
expr_stmt|;
block|}
return|return
name|service
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|modifiedService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|,
name|Object
name|service
parameter_list|)
block|{     }
annotation|@
name|Override
specifier|public
name|void
name|removedService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|,
name|Object
name|service
parameter_list|)
block|{
name|BundleContext
name|bc
init|=
name|this
operator|.
name|bundleContext
decl_stmt|;
if|if
condition|(
name|bc
operator|!=
literal|null
condition|)
block|{
name|trackedServiceCount
operator|--
expr_stmt|;
if|if
condition|(
name|trackedServiceCount
operator|==
literal|0
operator|&&
name|engineRegistration
operator|!=
literal|null
condition|)
block|{
name|engineRegistration
operator|.
name|unregister
argument_list|()
expr_stmt|;
block|}
name|bc
operator|.
name|ungetService
argument_list|(
name|reference
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

