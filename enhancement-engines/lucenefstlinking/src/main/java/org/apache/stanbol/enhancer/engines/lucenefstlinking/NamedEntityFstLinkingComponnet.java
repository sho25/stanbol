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
name|lucenefstlinking
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
name|DEFAULT_INCLUDE_SIMILAR_SCORE
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
name|INCLUDE_SIMILAR_SCORE
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
name|ner
operator|.
name|NerTag
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

begin_comment
comment|/**  * OSGI component used to configure a {@link FstLinkingEngine} with  * {@link LinkingModeEnum#NER}.<p>  *<b>NOTE:</b> Using this Engine requires {@link NerTag}s to be present in the  * {@link AnalysedText} content part. In addition {@link NerTag#getTag()} and  * {@link NerTag#getType()} values need to be mapped to expected Entity types  * in the linked vocabulary. This is configured by using the   * {@link FstLinkingEngineComponent#NAMED_ENTITY_TYPE_MAPPINGS} property.   *   * @author Rupert Westenthaler  *  */
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
literal|false
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
name|PROPERTY_NAME
argument_list|)
block|,
comment|//the name of the engine
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|FstLinkingEngineComponent
operator|.
name|SOLR_CORE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|IndexConfiguration
operator|.
name|FIELD_ENCODING
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
name|IndexConfiguration
operator|.
name|FIELD_ENCODING
operator|+
literal|".option.none"
argument_list|,
name|name
operator|=
literal|"None"
argument_list|)
block|,
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|'%'
operator|+
name|IndexConfiguration
operator|.
name|FIELD_ENCODING
operator|+
literal|".option.solrYard"
argument_list|,
name|name
operator|=
literal|"SolrYard"
argument_list|)
block|,
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|'%'
operator|+
name|IndexConfiguration
operator|.
name|FIELD_ENCODING
operator|+
literal|".option.minusPrefix"
argument_list|,
name|name
operator|=
literal|"MinusPrefix"
argument_list|)
block|,
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|'%'
operator|+
name|IndexConfiguration
operator|.
name|FIELD_ENCODING
operator|+
literal|".option.underscorePrefix"
argument_list|,
name|name
operator|=
literal|"UnderscorePrefix"
argument_list|)
block|,
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|'%'
operator|+
name|IndexConfiguration
operator|.
name|FIELD_ENCODING
operator|+
literal|".option.minusSuffix"
argument_list|,
name|name
operator|=
literal|"MinusSuffix"
argument_list|)
block|,
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|'%'
operator|+
name|IndexConfiguration
operator|.
name|FIELD_ENCODING
operator|+
literal|".option.underscoreSuffix"
argument_list|,
name|name
operator|=
literal|"UnderscoreSuffix"
argument_list|)
block|,
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|'%'
operator|+
name|IndexConfiguration
operator|.
name|FIELD_ENCODING
operator|+
literal|".option.atPrefix"
argument_list|,
name|name
operator|=
literal|"AtPrefix"
argument_list|)
block|,
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|'%'
operator|+
name|IndexConfiguration
operator|.
name|FIELD_ENCODING
operator|+
literal|".option.atSuffix"
argument_list|,
name|name
operator|=
literal|"AtSuffix"
argument_list|)
block|}
argument_list|,
name|value
operator|=
literal|"SolrYard"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|IndexConfiguration
operator|.
name|FST_CONFIG
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
name|IndexConfiguration
operator|.
name|FST_FOLDER
argument_list|,
name|value
operator|=
name|IndexConfiguration
operator|.
name|DEFAULT_FST_FOLDER
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|IndexConfiguration
operator|.
name|SOLR_TYPE_FIELD
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
name|IndexConfiguration
operator|.
name|SOLR_RANKING_FIELD
argument_list|,
name|value
operator|=
literal|"entityhub:entityRank"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|FstLinkingEngineComponent
operator|.
name|FST_THREAD_POOL_SIZE
argument_list|,
name|intValue
operator|=
name|FstLinkingEngineComponent
operator|.
name|DEFAULT_FST_THREAD_POOL_SIZE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|FstLinkingEngineComponent
operator|.
name|ENTITY_CACHE_SIZE
argument_list|,
name|intValue
operator|=
name|FstLinkingEngineComponent
operator|.
name|DEFAULT_ENTITY_CACHE_SIZE
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
name|INCLUDE_SIMILAR_SCORE
argument_list|,
name|boolValue
operator|=
name|DEFAULT_INCLUDE_SIMILAR_SCORE
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
name|FstLinkingEngineComponent
operator|.
name|NAMED_ENTITY_TYPE_MAPPINGS
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
literal|"dbp-ont:Person> dbp-ont:Person; schema:Person; foaf:Person"
block|,
literal|"dbp-ont:Organisation> dbp-ont:Organisation; dbp-ont:Newspaper; schema:Organization"
block|,
literal|"dbp-ont:Place> dbp-ont:Place; schema:Place; geonames:Feature"
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
name|NamedEntityFstLinkingComponnet
extends|extends
name|FstLinkingEngineComponent
block|{
comment|/**      * used to resolve '{prefix}:{local-name}' used within the engines configuration      */
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_UNARY
argument_list|)
specifier|private
name|NamespacePrefixService
name|prefixService
decl_stmt|;
annotation|@
name|Activate
annotation|@
name|Override
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
literal|"activate {}"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|bundleContext
operator|=
name|ctx
operator|.
name|getBundleContext
argument_list|()
expr_stmt|;
name|super
operator|.
name|applyConfig
argument_list|(
name|LinkingModeEnum
operator|.
name|NER
argument_list|,
name|ctx
operator|.
name|getProperties
argument_list|()
argument_list|,
name|prefixService
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Deactivate
annotation|@
name|Override
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
block|}
end_class

end_unit

