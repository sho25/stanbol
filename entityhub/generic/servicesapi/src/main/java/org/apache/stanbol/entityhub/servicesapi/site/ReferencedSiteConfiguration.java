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
name|EntityhubConfiguration
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
name|ManagedEntityState
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
name|MappingState
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
comment|/**  * Configuration for a {@link Site} that uses external services to access  * and query for Entities. ReferencedSites may cache remote entities locally.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|ReferencedSiteConfiguration
extends|extends
name|SiteConfiguration
block|{
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
name|ENTITY_DEREFERENCER_TYPE
init|=
literal|"org.apache.stanbol.entityhub.site.dereferencerType"
decl_stmt|;
comment|/**      * The name of the {@link EntityDereferencer} to be used for accessing      * representations of entities managed by this Site      * @return the id of the entity dereferencer implementation      */
name|String
name|getEntityDereferencerType
parameter_list|()
function_decl|;
comment|/**      * Key used for the configuration of the uri to access the query service of      * the site      */
name|String
name|QUERY_URI
init|=
literal|"org.apache.stanbol.entityhub.site.queryUri"
decl_stmt|;
comment|/**      * Getter for the queryUri of the site.      * @return the uri to access the query service of this site      */
name|String
name|getQueryUri
parameter_list|()
function_decl|;
comment|/**      * Key used for the configuration of the type of the query      */
name|String
name|ENTITY_SEARCHER_TYPE
init|=
literal|"org.apache.stanbol.entityhub.site.searcherType"
decl_stmt|;
comment|/**      * The type of the {@link EntitySearcher} to be used to query for      * representations of entities managed by this Site.      * @return the id of the entity searcher implementation.      */
name|String
name|getEntitySearcherType
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
block|}
end_interface

end_unit

