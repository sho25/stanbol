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
name|java
operator|.
name|util
operator|.
name|Dictionary
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
name|Cache
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
comment|/**  * This interface defines the getter as well as the keys used to configure  * such properties when parsing an configuration for a {@link Site}.<p>  *  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|SiteConfiguration
block|{
comment|/**      * The key to be used for the site id      */
name|String
name|ID
init|=
literal|"org.apache.stanbol.entityhub.site.id"
decl_stmt|;
comment|/**      * Getter for the id of this site.      * @return The id of the Site      */
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
comment|/**      * The key used to configure the FieldMappings for a Site. Note that values      * are parsed by using {@link FieldMapping#parseFieldMapping(String)}      */
name|String
name|SITE_FIELD_MAPPINGS
init|=
literal|"org.apache.stanbol.entityhub.site.fieldMappings"
decl_stmt|;
comment|/**      * Getter for the field mappings used for this site when importing entities      * to the Entityhub.<p>      * Note that this field mappings are used in addition to the field mappings      * defined by the {@link EntityhubConfiguration}.      * @return the FieldMappings or<code>null</code> if none.      */
name|String
index|[]
name|getFieldMappings
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
comment|/**      * Key used for the configuration of the default {@link ManagedEntityState} for a site      */
name|String
name|DEFAULT_SYMBOL_STATE
init|=
literal|"org.apache.stanbol.entityhub.site.defaultSymbolState"
decl_stmt|;
comment|/**      * The initial state if a {@link Symbol} is created for an entity managed      * by this site      * @return the default state for new symbols      */
name|ManagedEntityState
name|getDefaultManagedEntityState
parameter_list|()
function_decl|;
comment|/**      * Key used for the configuration of the default {@link EntityMapping} state      * ({@link MappingState} for a site      */
name|String
name|DEFAULT_MAPPING_STATE
init|=
literal|"org.apache.stanbol.entityhub.site.defaultMappedEntityState"
decl_stmt|;
comment|/**      * The initial state for mappings of entities managed by this site      * @return the default state for mappings to entities of this site      */
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
comment|/**      * The key used to configure the name of License used by a referenced Site      */
name|String
name|SITE_LICENCE_NAME
init|=
literal|"org.apache.stanbol.entityhub.site.licenseName"
decl_stmt|;
comment|/**      * The key used to configure the License of a referenced Site      */
name|String
name|SITE_LICENCE_TEXT
init|=
literal|"org.apache.stanbol.entityhub.site.licenseText"
decl_stmt|;
comment|/**      * The key used to configure the link to the License used by a referenced Site      */
name|String
name|SITE_LICENCE_URL
init|=
literal|"org.apache.stanbol.entityhub.site.licenseUrl"
decl_stmt|;
comment|/**      * Getter for the the License(s) used for the data provided by this       * site. If multiple Liceneses are given it is assumed that any of them can      * be used.<p>      * Licenses are defined by the name ({@link #SITE_LICENCE_NAME}),      * the text ({@link #SITE_LICENCE_TEXT}) and the url       * ({@link #SITE_LICENCE_URL}) to the page of the license. This three keys      * are combined to the {@link License} object.<p>      * It is recommended to use "cc:license" to link those licenses to the      * page.      * @return The name of the license      */
name|License
index|[]
name|getLicenses
parameter_list|()
function_decl|;
comment|/**      * The attribution for the data provided by this referenced site      */
name|String
name|SITE_ATTRIBUTION
init|=
literal|"org.apache.stanbol.entityhub.site.attribution"
decl_stmt|;
comment|/**      * The name the creator of the Work (representations in case of referenced      * sites) would like used when attributing re-use.<code>null</code> if      * none is required by the license.<p>      * It is recommended to use "cc:attributionName" to represent this in      * RDF graphs      * @return the attribution name      */
name|String
name|getAttribution
parameter_list|()
function_decl|;
comment|/**      * The URL to the attribution for the data provided by this referenced site      */
name|String
name|SITE_ATTRIBUTION_URL
init|=
literal|"org.apache.stanbol.entityhub.site.attributionUrl"
decl_stmt|;
comment|/**      * The URL the creator of a Work (representations in case of referenced      * sites) would like used when attributing re-use.<code>null</code> if      * none is required by the license.<p>      * It is recommended to use "cc:attributionURL" to represent this in      * RDF graphs      * @return the link to the URL providing the full attribution      */
name|String
name|getAttributionUrl
parameter_list|()
function_decl|;
comment|/**      * The configuration as {@link Dictionary} (as used by OSGI)      * @return the configuration as Dictionary      */
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getConfiguration
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

