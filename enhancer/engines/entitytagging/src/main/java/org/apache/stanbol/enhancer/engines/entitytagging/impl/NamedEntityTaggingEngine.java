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
name|entitytagging
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
name|rdf
operator|.
name|OntologicalClasses
operator|.
name|DBPEDIA_ORGANISATION
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
name|RDF_TYPE
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
name|NonLiteral
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
name|Triple
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
name|ReferenceCardinality
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
name|ReferencePolicy
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
name|ReferenceStrategy
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
name|OfflineMode
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
name|EnhancementJobManager
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
name|rdf
operator|.
name|OntologicalClasses
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
name|Properties
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
name|TechnicalClasses
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
name|EntityhubException
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
name|defaults
operator|.
name|NamespaceEnum
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
name|model
operator|.
name|Entity
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
name|query
operator|.
name|FieldQuery
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
name|query
operator|.
name|QueryResultList
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
name|query
operator|.
name|ReferenceConstraint
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
name|query
operator|.
name|TextConstraint
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
name|ReferencedSite
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
name|ReferencedSiteException
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
name|ReferencedSiteManager
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
comment|/**  * Engine that uses a {@link ReferencedSite} to search for entities for existing TextAnnotations of an Content  * Item.  *   * @author ogrisel, rwesten  */
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
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|NamedEntityTaggingEngine
implements|implements
name|EnhancementEngine
implements|,
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
annotation|@
name|Property
comment|//(value = "dbpedia")
specifier|public
specifier|static
specifier|final
name|String
name|REFERENCED_SITE_ID
init|=
literal|"org.apache.stanbol.enhancer.engines.entitytagging.referencedSiteId"
decl_stmt|;
annotation|@
name|Property
comment|//(boolValue = true)
specifier|public
specifier|static
specifier|final
name|String
name|PERSON_STATE
init|=
literal|"org.apache.stanbol.enhancer.engines.entitytagging.personState"
decl_stmt|;
annotation|@
name|Property
comment|//(value = "dbp-ont:Person")
specifier|public
specifier|static
specifier|final
name|String
name|PERSON_TYPE
init|=
literal|"org.apache.stanbol.enhancer.engines.entitytagging.personType"
decl_stmt|;
annotation|@
name|Property
comment|//(boolValue = true)
specifier|public
specifier|static
specifier|final
name|String
name|ORG_STATE
init|=
literal|"org.apache.stanbol.enhancer.engines.entitytagging.organisationState"
decl_stmt|;
annotation|@
name|Property
comment|//(value = "dbp-ont:Organisation")
specifier|public
specifier|static
specifier|final
name|String
name|ORG_TYPE
init|=
literal|"org.apache.stanbol.enhancer.engines.entitytagging.organisationType"
decl_stmt|;
annotation|@
name|Property
comment|//(boolValue = true)
specifier|public
specifier|static
specifier|final
name|String
name|PLACE_STATE
init|=
literal|"org.apache.stanbol.enhancer.engines.entitytagging.placeState"
decl_stmt|;
annotation|@
name|Property
comment|//(value = "dbp-ont:Place")
specifier|public
specifier|static
specifier|final
name|String
name|PLACE_TYPE
init|=
literal|"org.apache.stanbol.enhancer.engines.entitytagging.placeType"
decl_stmt|;
comment|/**      * Use the RDFS label as default      */
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"rdfs:label"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|NAME_FIELD
init|=
literal|"org.apache.stanbol.enhancer.engines.entitytagging.nameField"
decl_stmt|;
comment|/**      * Service of the Entityhub that manages all the active referenced Site. This Service is used to lookup the      * configured Referenced Site when we need to enhance a content item.      */
annotation|@
name|Reference
specifier|protected
name|ReferencedSiteManager
name|siteManager
decl_stmt|;
comment|/**      * Used to lookup Entities if the {@link #REFERENCED_SITE_ID} property is      * set to "entityhub" or "local"      */
annotation|@
name|Reference
specifier|protected
name|Entityhub
name|entityhub
decl_stmt|;
comment|/**      * This holds the id of the {@link ReferencedSite} used to lookup Entities      * or<code>null</code> if the {@link Entityhub} is used.       */
specifier|protected
name|String
name|referencedSiteID
decl_stmt|;
comment|/**      * The default value for the Execution of this Engine. Currently set to      * {@link EnhancementJobManager#DEFAULT_ORDER}      */
specifier|public
specifier|static
specifier|final
name|Integer
name|defaultOrder
init|=
name|ORDERING_EXTRACTION_ENHANCEMENT
decl_stmt|;
comment|/**      * State if text annotations of type {@link OntologicalClasses#DBPEDIA_PERSON} are enhanced by this engine      */
specifier|protected
name|boolean
name|personState
decl_stmt|;
comment|/**      * State if text annotations of type {@link OntologicalClasses#DBPEDIA_ORGANISATION} are enhanced by this      * engine      */
specifier|protected
name|boolean
name|orgState
decl_stmt|;
comment|/**      * State if text annotations of type {@link OntologicalClasses#DBPEDIA_PLACE} are enhanced by this engine      */
specifier|protected
name|boolean
name|placeState
decl_stmt|;
comment|/**      * The rdf:type constraint used to search for persons or<code>null</code> if no type constraint should be      * used      */
specifier|protected
name|String
name|personType
decl_stmt|;
comment|/**      * The rdf:type constraint used to search for organisations or<code>null</code> if no type constraint      * should be used      */
specifier|protected
name|String
name|orgType
decl_stmt|;
comment|/**      * The rdf:type constraint used to search for places or<code>null</code> if no type constraint should be      * used      */
specifier|protected
name|String
name|placeType
decl_stmt|;
comment|/**      * The field used to search for the selected text of the TextAnnotation.      */
specifier|private
name|String
name|nameField
decl_stmt|;
comment|/**      * The number of Suggestions to be added      */
specifier|public
name|Integer
name|numSuggestions
init|=
literal|3
decl_stmt|;
comment|/**      * The {@link OfflineMode} is used by Stanbol to indicate that no external service should be referenced.      * For this engine that means it is necessary to check if the used {@link ReferencedSite} can operate      * offline or not.      *       * @see #enableOfflineMode(OfflineMode)      * @see #disableOfflineMode(OfflineMode)      */
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_UNARY
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|,
name|bind
operator|=
literal|"enableOfflineMode"
argument_list|,
name|unbind
operator|=
literal|"disableOfflineMode"
argument_list|,
name|strategy
operator|=
name|ReferenceStrategy
operator|.
name|EVENT
argument_list|)
specifier|private
name|OfflineMode
name|offlineMode
decl_stmt|;
comment|/**      * Called by the ConfigurationAdmin to bind the {@link #offlineMode} if the service becomes available      *       * @param mode      */
specifier|protected
specifier|final
name|void
name|enableOfflineMode
parameter_list|(
name|OfflineMode
name|mode
parameter_list|)
block|{
name|this
operator|.
name|offlineMode
operator|=
name|mode
expr_stmt|;
block|}
comment|/**      * Called by the ConfigurationAdmin to unbind the {@link #offlineMode} if the service becomes unavailable      *       * @param mode      */
specifier|protected
specifier|final
name|void
name|disableOfflineMode
parameter_list|(
name|OfflineMode
name|mode
parameter_list|)
block|{
name|this
operator|.
name|offlineMode
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * Returns<code>true</code> only if Stanbol operates in {@link OfflineMode}.      *       * @return the offline state      */
specifier|protected
specifier|final
name|boolean
name|isOfflineMode
parameter_list|()
block|{
return|return
name|offlineMode
operator|!=
literal|null
return|;
block|}
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
name|context
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
name|config
init|=
name|context
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|Object
name|referencedSiteID
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
name|referencedSiteID
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
name|this
operator|.
name|referencedSiteID
operator|=
name|referencedSiteID
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|this
operator|.
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
name|this
operator|.
name|referencedSiteID
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Init NamedEntityTaggingEngine instance for the Entityhub"
argument_list|)
expr_stmt|;
name|this
operator|.
name|referencedSiteID
operator|=
literal|null
expr_stmt|;
block|}
name|Object
name|state
init|=
name|config
operator|.
name|get
argument_list|(
name|PERSON_STATE
argument_list|)
decl_stmt|;
name|personState
operator|=
name|state
operator|==
literal|null
condition|?
literal|true
else|:
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|state
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|state
operator|=
name|config
operator|.
name|get
argument_list|(
name|ORG_STATE
argument_list|)
expr_stmt|;
name|orgState
operator|=
name|state
operator|==
literal|null
condition|?
literal|true
else|:
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|state
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|state
operator|=
name|config
operator|.
name|get
argument_list|(
name|PLACE_STATE
argument_list|)
expr_stmt|;
name|placeState
operator|=
name|state
operator|==
literal|null
condition|?
literal|true
else|:
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|state
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|Object
name|type
init|=
name|config
operator|.
name|get
argument_list|(
name|PERSON_TYPE
argument_list|)
decl_stmt|;
name|personType
operator|=
name|type
operator|==
literal|null
operator|||
name|type
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|type
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|type
operator|=
name|config
operator|.
name|get
argument_list|(
name|ORG_TYPE
argument_list|)
expr_stmt|;
name|orgType
operator|=
name|type
operator|==
literal|null
operator|||
name|type
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|type
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|type
operator|=
name|config
operator|.
name|get
argument_list|(
name|PLACE_TYPE
argument_list|)
expr_stmt|;
name|placeType
operator|=
name|type
operator|==
literal|null
operator|||
name|type
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|type
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|Object
name|nameField
init|=
name|config
operator|.
name|get
argument_list|(
name|NAME_FIELD
argument_list|)
decl_stmt|;
name|this
operator|.
name|nameField
operator|=
name|nameField
operator|==
literal|null
operator|||
name|nameField
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
name|NamespaceEnum
operator|.
name|rdfs
operator|+
literal|"label"
else|:
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|nameField
operator|.
name|toString
argument_list|()
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
name|referencedSiteID
operator|=
literal|null
expr_stmt|;
name|personType
operator|=
literal|null
expr_stmt|;
name|orgType
operator|=
literal|null
expr_stmt|;
name|placeType
operator|=
literal|null
expr_stmt|;
name|nameField
operator|=
literal|null
expr_stmt|;
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
specifier|final
name|ReferencedSite
name|site
decl_stmt|;
if|if
condition|(
name|referencedSiteID
operator|!=
literal|null
condition|)
block|{
comment|//lookup the referenced site
name|site
operator|=
name|siteManager
operator|.
name|getReferencedSite
argument_list|(
name|referencedSiteID
argument_list|)
expr_stmt|;
comment|//ensure that it is present
if|if
condition|(
name|site
operator|==
literal|null
condition|)
block|{
name|String
name|msg
init|=
name|String
operator|.
name|format
argument_list|(
literal|"Unable to enhance %s because Referenced Site %s is currently not active!"
argument_list|,
name|ci
operator|.
name|getId
argument_list|()
argument_list|,
name|referencedSiteID
argument_list|)
decl_stmt|;
name|log
operator|.
name|warn
argument_list|(
name|msg
argument_list|)
expr_stmt|;
comment|// TODO: throwing Exceptions is currently deactivated. We need a more clear
comment|// policy what do to in such situations
comment|// throw new EngineException(msg);
return|return;
block|}
comment|//and that it supports offline mode if required
if|if
condition|(
name|isOfflineMode
argument_list|()
operator|&&
operator|!
name|site
operator|.
name|supportsLocalMode
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to enhance ci {} because OfflineMode is not supported by ReferencedSite {}."
argument_list|,
name|ci
operator|.
name|getId
argument_list|()
argument_list|,
name|site
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
else|else
block|{
comment|// null indicates to use the Entityhub to lookup Entities
name|site
operator|=
literal|null
expr_stmt|;
block|}
name|UriRef
name|contentItemId
init|=
operator|new
name|UriRef
argument_list|(
name|ci
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
name|MGraph
name|graph
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
comment|// Retrieve the existing text annotations
name|Map
argument_list|<
name|UriRef
argument_list|,
name|List
argument_list|<
name|UriRef
argument_list|>
argument_list|>
name|textAnnotations
init|=
operator|new
name|HashMap
argument_list|<
name|UriRef
argument_list|,
name|List
argument_list|<
name|UriRef
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|graph
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|RDF_TYPE
argument_list|,
name|TechnicalClasses
operator|.
name|ENHANCER_TEXTANNOTATION
argument_list|)
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|UriRef
name|uri
init|=
operator|(
name|UriRef
operator|)
name|it
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
decl_stmt|;
if|if
condition|(
name|graph
operator|.
name|filter
argument_list|(
name|uri
argument_list|,
name|Properties
operator|.
name|DC_RELATION
argument_list|,
literal|null
argument_list|)
operator|.
name|hasNext
argument_list|()
condition|)
block|{
comment|// this is not the most specific occurrence of this name: skip
continue|continue;
block|}
comment|// This is a first occurrence, collect any subsumed annotations
name|List
argument_list|<
name|UriRef
argument_list|>
name|subsumed
init|=
operator|new
name|ArrayList
argument_list|<
name|UriRef
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it2
init|=
name|graph
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|Properties
operator|.
name|DC_RELATION
argument_list|,
name|uri
argument_list|)
init|;
name|it2
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|subsumed
operator|.
name|add
argument_list|(
operator|(
name|UriRef
operator|)
name|it2
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|textAnnotations
operator|.
name|put
argument_list|(
name|uri
argument_list|,
name|subsumed
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|List
argument_list|<
name|UriRef
argument_list|>
argument_list|>
name|entry
range|:
name|textAnnotations
operator|.
name|entrySet
argument_list|()
control|)
block|{
try|try
block|{
name|computeEntityRecommentations
argument_list|(
name|site
argument_list|,
name|literalFactory
argument_list|,
name|graph
argument_list|,
name|contentItemId
argument_list|,
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|EntityhubException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|EngineException
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
comment|/**      * Computes the Enhancements      * @param site The {@link ReferencedSiteException} id or<code>null</code> to      * use the {@link Entityhub}      * @param literalFactory the {@link LiteralFactory} used to create RDF Literals      * @param graph the graph to write the lined entities      * @param contentItemId the id of the contentItem      * @param textAnnotation the text annotation to enhance      * @param subsumedAnnotations other text annotations for the same entity       * @return the suggested {@link Entity entities}      * @throws EntityhubException On any Error while looking up Entities via      * the Entityhub      */
specifier|protected
specifier|final
name|Iterable
argument_list|<
name|Entity
argument_list|>
name|computeEntityRecommentations
parameter_list|(
name|ReferencedSite
name|site
parameter_list|,
name|LiteralFactory
name|literalFactory
parameter_list|,
name|MGraph
name|graph
parameter_list|,
name|UriRef
name|contentItemId
parameter_list|,
name|UriRef
name|textAnnotation
parameter_list|,
name|List
argument_list|<
name|UriRef
argument_list|>
name|subsumedAnnotations
parameter_list|)
throws|throws
name|EntityhubException
block|{
comment|// First get the required properties for the parsed textAnnotation
comment|// ... and check the values
name|String
name|name
init|=
name|EnhancementEngineHelper
operator|.
name|getString
argument_list|(
name|graph
argument_list|,
name|textAnnotation
argument_list|,
name|ENHANCER_SELECTED_TEXT
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to process TextAnnotation "
operator|+
name|textAnnotation
operator|+
literal|" because property"
operator|+
name|ENHANCER_SELECTED_TEXT
operator|+
literal|" is not present"
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
name|UriRef
name|type
init|=
name|EnhancementEngineHelper
operator|.
name|getReference
argument_list|(
name|graph
argument_list|,
name|textAnnotation
argument_list|,
name|DC_TYPE
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to process TextAnnotation "
operator|+
name|textAnnotation
operator|+
literal|" because property"
operator|+
name|DC_TYPE
operator|+
literal|" is not present"
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
comment|// remove punctations form the search string
name|name
operator|=
name|cleanupKeywords
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Process TextAnnotation "
operator|+
name|name
operator|+
literal|" type="
operator|+
name|type
argument_list|)
expr_stmt|;
name|FieldQuery
name|query
init|=
name|site
operator|==
literal|null
condition|?
comment|//if site is NULL use the Entityhub
name|entityhub
operator|.
name|getQueryFactory
argument_list|()
operator|.
name|createFieldQuery
argument_list|()
else|:
name|site
operator|.
name|getQueryFactory
argument_list|()
operator|.
name|createFieldQuery
argument_list|()
decl_stmt|;
comment|// replace spaces with plus to create an AND search for all words in the name!
name|query
operator|.
name|setConstraint
argument_list|(
name|nameField
argument_list|,
operator|new
name|TextConstraint
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
comment|// name.replace(' ', '+')));
if|if
condition|(
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
if|if
condition|(
name|personState
condition|)
block|{
if|if
condition|(
name|personType
operator|!=
literal|null
condition|)
block|{
name|query
operator|.
name|setConstraint
argument_list|(
name|RDF_TYPE
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
operator|new
name|ReferenceConstraint
argument_list|(
name|personType
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// else no type constraint
block|}
else|else
block|{
comment|// ignore people
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|DBPEDIA_ORGANISATION
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
if|if
condition|(
name|orgState
condition|)
block|{
if|if
condition|(
name|orgType
operator|!=
literal|null
condition|)
block|{
name|query
operator|.
name|setConstraint
argument_list|(
name|RDF_TYPE
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
operator|new
name|ReferenceConstraint
argument_list|(
name|orgType
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// else no type constraint
block|}
else|else
block|{
comment|// ignore people
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|OntologicalClasses
operator|.
name|DBPEDIA_PLACE
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
if|if
condition|(
name|this
operator|.
name|placeState
condition|)
block|{
if|if
condition|(
name|this
operator|.
name|placeType
operator|!=
literal|null
condition|)
block|{
name|query
operator|.
name|setConstraint
argument_list|(
name|RDF_TYPE
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
operator|new
name|ReferenceConstraint
argument_list|(
name|placeType
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// else no type constraint
block|}
else|else
block|{
comment|// ignore people
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
name|query
operator|.
name|setLimit
argument_list|(
name|this
operator|.
name|numSuggestions
argument_list|)
expr_stmt|;
name|QueryResultList
argument_list|<
name|Entity
argument_list|>
name|results
init|=
name|site
operator|==
literal|null
condition|?
comment|//if site is NULL
name|entityhub
operator|.
name|findEntities
argument_list|(
name|query
argument_list|)
else|:
comment|//use the Entityhub
name|site
operator|.
name|findEntities
argument_list|(
name|query
argument_list|)
decl_stmt|;
comment|//else the referenced site
name|log
operator|.
name|debug
argument_list|(
literal|"{} results returned by query {}"
argument_list|,
name|results
operator|.
name|size
argument_list|()
argument_list|,
name|query
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|annotationsToRelate
init|=
operator|new
name|ArrayList
argument_list|<
name|NonLiteral
argument_list|>
argument_list|()
decl_stmt|;
name|annotationsToRelate
operator|.
name|add
argument_list|(
name|textAnnotation
argument_list|)
expr_stmt|;
name|annotationsToRelate
operator|.
name|addAll
argument_list|(
name|subsumedAnnotations
argument_list|)
expr_stmt|;
for|for
control|(
name|Entity
name|guess
range|:
name|results
control|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Adding {} to ContentItem {}"
argument_list|,
name|guess
argument_list|,
name|contentItemId
argument_list|)
expr_stmt|;
name|EnhancementRDFUtils
operator|.
name|writeEntityAnnotation
argument_list|(
name|this
argument_list|,
name|literalFactory
argument_list|,
name|graph
argument_list|,
name|contentItemId
argument_list|,
name|annotationsToRelate
argument_list|,
name|guess
argument_list|,
name|nameField
argument_list|)
expr_stmt|;
block|}
return|return
name|results
return|;
block|}
specifier|public
name|int
name|canEnhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
block|{
comment|/*          * This engine consumes existing enhancements because of that it can enhance any type of ci! TODO: It          * would also be possible to check here if there is an TextAnnotation and use that as result!          */
return|return
name|ENHANCE_SYNCHRONOUS
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
comment|/**      * Removes punctuations form a parsed string      */
specifier|private
specifier|static
name|String
name|cleanupKeywords
parameter_list|(
name|String
name|keywords
parameter_list|)
block|{
return|return
name|keywords
operator|.
name|replaceAll
argument_list|(
literal|"\\p{P}"
argument_list|,
literal|" "
argument_list|)
operator|.
name|trim
argument_list|()
return|;
block|}
block|}
end_class

end_unit

