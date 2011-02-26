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
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
package|;
end_package

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
name|mapping
operator|.
name|FieldMapper
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
name|mapping
operator|.
name|FieldMapping
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
name|EntityMapping
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
name|Symbol
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
name|Symbol
operator|.
name|SymbolState
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
name|yard
operator|.
name|CacheStrategy
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
name|yard
operator|.
name|Yard
import|;
end_import

begin_comment
comment|/**  * This interface defines the getter as well as the property keys for the  * configuration of a {@link ReferencedSite}.<p>  *  * TODO: No Idea how to handle that in an OSGI context.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|ConfiguredSite
block|{
comment|/**      * The key to be used for the site id      */
name|String
name|ID
init|=
literal|"org.apache.stanbol.entityhub.site.id"
decl_stmt|;
comment|/**      * Getter for the id of this site      * @return      */
name|String
name|getId
parameter_list|()
function_decl|;
comment|/**      * The key to be used for the name of the site      */
name|String
name|NAME
init|=
literal|"org.apache.stanbol.entityhub.site.name"
decl_stmt|;
comment|/**      * The preferred name of this site (if not present use the id)      * @return the name (or if not defined the id) of the site      */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * The key to be used for the site description      */
name|String
name|DESCRIPTION
init|=
literal|"org.apache.stanbol.entityhub.site.description"
decl_stmt|;
comment|/**      * Getter for the default short description of this site.      * @return The description or<code>null</code> if non is defined      */
name|String
name|getDescription
parameter_list|()
function_decl|;
comment|/**      * Key used for the configuration of the AccessURI  for a site      */
name|String
name|ACCESS_URI
init|=
literal|"org.apache.stanbol.entityhub.site.accessUri"
decl_stmt|;
comment|/**      * The URI used to access the Data of this Site. This is usually a different      * URI as the ID of the site.<p>      *      * To give some Examples:<p>      *      * symbol.label: DBPedia<br>      * symbol.id: http://dbpedia.org<br>      * site.acessUri: http://dbpedia.org/resource/<p>      *      * symbol.label: Freebase<br>      * symbol.id: http://www.freebase.com<br>      * site.acessUri: http://rdf.freebase.com/<p>      *      * @return the accessURI for the data of the referenced site      */
name|String
name|getAccessUri
parameter_list|()
function_decl|;
comment|/**      * Key used for the configuration of the name of the dereferencer type to be      * used for this site      */
name|String
name|DEREFERENCER_TYPE
init|=
literal|"org.apache.stanbol.entityhub.site.dereferencerType"
decl_stmt|;
comment|/**      * The name of the {@link EntityDereferencer} to be used for accessing      * representations of entities managed by this Site      * @return the id of the entity dereferencer implementation      */
name|String
name|getDereferencerType
parameter_list|()
function_decl|;
comment|/**      * Key used for the configuration of the uri to access the query service of      * the site      */
name|String
name|QUERY_URI
init|=
literal|"org.apache.stanbol.entityhub.site.queryUri"
decl_stmt|;
comment|/**      * Getter for the queryUri of the site. IF not defined the {@link #ACCESS_URI}      * is used.      * @return the uri to access the query service of this site      */
name|String
name|getQueryUri
parameter_list|()
function_decl|;
comment|/**      * Key used for the configuration of the type of the query      */
name|String
name|SEARCHER_TYPE
init|=
literal|"org.apache.stanbol.entityhub.site.searcherType"
decl_stmt|;
comment|/**      * The name of the {@link EntitySearcher} to be used to query for      * representations of entities managed by this Site.      * @return the id of the entity searcher implementation.      */
name|String
name|getQueryType
parameter_list|()
function_decl|;
comment|/**      * Key used for the configuration of the default {@link SymbolState} for a site      */
name|String
name|DEFAULT_SYMBOL_STATE
init|=
literal|"org.apache.stanbol.entityhub.site.defaultSymbolState"
decl_stmt|;
comment|/**      * The initial state if a {@link Symbol} is created for an entity managed      * by this site      * @return the default state for new symbols      */
name|Symbol
operator|.
name|SymbolState
name|getDefaultSymbolState
parameter_list|()
function_decl|;
comment|/**      * Key used for the configuration of the default {@link EntityMapping} state      * ({@link EntityMapping.MappingState} for a site      */
name|String
name|DEFAULT_MAPPING_STATE
init|=
literal|"org.apache.stanbol.entityhub.site.defaultMappedEntityState"
decl_stmt|;
comment|/**      * The initial state for mappings of entities managed by this site      * @return the default state for mappings to entities of this site      */
name|EntityMapping
operator|.
name|MappingState
name|getDefaultMappedEntityState
parameter_list|()
function_decl|;
comment|/**      * Key used for the configuration of the default expiration duration for entities and      * data for a site      */
name|String
name|DEFAULT_EXPIRE_DURATION
init|=
literal|"org.apache.stanbol.entityhub.site.defaultExpireDuration"
decl_stmt|;
comment|/**      * Return the duration in milliseconds or values<= 0 if mappings to entities      * of this Site do not expire.      * @return the duration in milliseconds or values<=0 if not applicable.      */
name|long
name|getDefaultExpireDuration
parameter_list|()
function_decl|;
comment|/**      * Key used for the configuration of the default expiration duration for entities and      * data for a site      */
name|String
name|CACHE_STRATEGY
init|=
literal|"org.apache.stanbol.entityhub.site.cacheStrategy"
decl_stmt|;
comment|/**      * The cache strategy used by for this site to be used.      * @return the cache strategy      */
name|CacheStrategy
name|getCacheStrategy
parameter_list|()
function_decl|;
comment|/**      * The key used for the configuration of the id for the yard used as a cache      * for the data of a referenced Site. This property is ignored if      * {@link CacheStrategy#none} is used.      */
name|String
name|CACHE_ID
init|=
literal|"org.apache.stanbol.entityhub.site.cacheId"
decl_stmt|;
comment|/**      * The id of the Yard used to cache data of this referenced site.      * @return the id of the {@link Yard} used as a cache. May be<code>null</code>      * if {@link CacheStrategy#none} is configured for this yard      */
name|String
name|getCacheId
parameter_list|()
function_decl|;
comment|/**      * Key used for the configuration of prefixes used by Entities managed by this Site      */
name|String
name|ENTITY_PREFIX
init|=
literal|"org.apache.stanbol.entityhub.site.entityPrefix"
decl_stmt|;
comment|/**      * Getter for the prefixes of entities managed by this Site      * @return the entity prefixes. In case there are non an empty array is returned.      */
name|String
index|[]
name|getEntityPrefixes
parameter_list|()
function_decl|;
comment|/**      * The key used to configure the FieldMappings for a Site. Note that values      * are parsed by using {@link FieldMapping#parseFieldMapping(String)}      */
name|String
name|SITE_FIELD_MAPPINGS
init|=
literal|"org.apache.stanbol.entityhub.site.fieldMappings"
decl_stmt|;
comment|/**      * The {@link FieldMapper} as configured for this Site.      * @return the FieldMappings      */
name|FieldMapper
name|getFieldMapper
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

