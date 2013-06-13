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
name|entityhub
operator|.
name|core
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
name|ArrayList
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
name|Dictionary
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
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
name|site
operator|.
name|License
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
name|ManagedSiteConfiguration
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
name|ReferencedSiteConfiguration
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
name|Site
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
name|SiteConfiguration
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
comment|/**  * Implementation of the {@link SiteConfiguration} interface.<p>  * While this implementation also provides setter methods when used within an  * OSGI environment configurations are typically read only as the configuration  * is provided as parameter to the component activation method.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|SiteConfigurationImpl
implements|implements
name|SiteConfiguration
block|{
comment|/**      * The logger      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|private
specifier|final
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SiteConfigurationImpl
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Internally used to store the configuration.      */
specifier|protected
specifier|final
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
decl_stmt|;
comment|/**      * Creates a configuration based on the parsed parameter. The parsed       * configuration is validated.<p>      * Changes to the parsed configuration do have no affect on the state of       * the created instance.<p>      * OSGI specific metadata are removed from the parsed configuration.      * @param parsed the configuration used for the initialisation.      * @throws ConfigurationException if the parsed properties are not valid      */
specifier|protected
name|SiteConfigurationImpl
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|parsed
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|this
argument_list|()
expr_stmt|;
comment|//now add the parsed configuration
if|if
condition|(
name|parsed
operator|!=
literal|null
condition|)
block|{
comment|//copy over the elements
for|for
control|(
name|Enumeration
argument_list|<
name|String
argument_list|>
name|it
init|=
name|parsed
operator|.
name|keys
argument_list|()
init|;
name|it
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|String
name|key
init|=
name|it
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|config
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|parsed
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//Remove OSGI specific metadata
name|config
operator|.
name|remove
argument_list|(
name|Constants
operator|.
name|SERVICE_ID
argument_list|)
expr_stmt|;
name|config
operator|.
name|remove
argument_list|(
name|Constants
operator|.
name|SERVICE_PID
argument_list|)
expr_stmt|;
name|config
operator|.
name|remove
argument_list|(
name|Constants
operator|.
name|OBJECTCLASS
argument_list|)
expr_stmt|;
name|config
operator|.
name|remove
argument_list|(
name|Constants
operator|.
name|SYSTEM_BUNDLE_LOCATION
argument_list|)
expr_stmt|;
name|config
operator|.
name|remove
argument_list|(
name|Constants
operator|.
name|SYSTEM_BUNDLE_SYMBOLICNAME
argument_list|)
expr_stmt|;
block|}
name|validateConfiguration
argument_list|()
expr_stmt|;
block|}
comment|/**      * Constructs an empty configuration      */
specifier|protected
name|SiteConfigurationImpl
parameter_list|()
block|{
name|this
operator|.
name|config
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
block|}
comment|/**      * Validates if the current configuration is valid and also perform type      * transformations on values (e.g. converting string values to the enumerated       * types). if the validation fails an Exception MUST BE thrown.<p>      * @throws ConfigurationException if the validation fails       */
specifier|protected
name|void
name|validateConfiguration
parameter_list|()
throws|throws
name|ConfigurationException
block|{
if|if
condition|(
name|getId
argument_list|()
operator|==
literal|null
operator|||
name|getId
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
name|ID
argument_list|,
literal|"The id of a ReferencedSite configuration MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
comment|//check if the prefixes can be converted to an String[]
try|try
block|{
name|setEntityPrefixes
argument_list|(
name|getEntityPrefixes
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|ENTITY_PREFIX
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
comment|//check the configured licenses and create the License array
name|setLicenses
argument_list|(
name|getLicenses
argument_list|()
argument_list|)
expr_stmt|;
comment|//check if the fieldMappings can be converted to an String[]
try|try
block|{
name|setFieldMappings
argument_list|(
name|getFieldMappings
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|SITE_FIELD_MAPPINGS
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
try|try
block|{
name|setDefaultMappedEntityState
argument_list|(
name|getDefaultMappedEntityState
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|DEFAULT_MAPPING_STATE
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"Unknown default MappingState (%s=%s) for Site %s! Valid values are %s "
argument_list|,
name|DEFAULT_MAPPING_STATE
argument_list|,
name|config
operator|.
name|get
argument_list|(
name|DEFAULT_MAPPING_STATE
argument_list|)
argument_list|,
name|getId
argument_list|()
argument_list|,
name|Arrays
operator|.
name|toString
argument_list|(
name|MappingState
operator|.
name|values
argument_list|()
argument_list|)
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
try|try
block|{
name|setDefaultManagedEntityState
argument_list|(
name|getDefaultManagedEntityState
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|DEFAULT_SYMBOL_STATE
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"Unknown default SymbolState (%s=%s) for Site %s! Valid values are %s "
argument_list|,
name|DEFAULT_SYMBOL_STATE
argument_list|,
name|config
operator|.
name|get
argument_list|(
name|DEFAULT_SYMBOL_STATE
argument_list|)
argument_list|,
name|getId
argument_list|()
argument_list|,
name|Arrays
operator|.
name|toString
argument_list|(
name|ManagedEntityState
operator|.
name|values
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
argument_list|)
throw|;
block|}
comment|//check if the default expire duration is a number
try|try
block|{
name|setDefaultExpireDuration
argument_list|(
name|getDefaultExpireDuration
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|DEFAULT_EXPIRE_DURATION
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse Number for %s=%s. Will return -1 (no expire duration)"
argument_list|,
name|DEFAULT_EXPIRE_DURATION
argument_list|,
name|config
operator|.
name|get
argument_list|(
name|DEFAULT_EXPIRE_DURATION
argument_list|)
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|long
name|getDefaultExpireDuration
parameter_list|()
block|{
name|Object
name|duration
init|=
name|config
operator|.
name|get
argument_list|(
name|DEFAULT_EXPIRE_DURATION
argument_list|)
decl_stmt|;
if|if
condition|(
name|duration
operator|==
literal|null
condition|)
block|{
return|return
literal|0
return|;
block|}
elseif|else
if|if
condition|(
name|duration
operator|instanceof
name|Long
condition|)
block|{
return|return
operator|(
name|Long
operator|)
name|duration
return|;
block|}
else|else
block|{
return|return
name|Long
operator|.
name|valueOf
argument_list|(
name|duration
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
block|}
comment|/**      *       * @param duration      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getDefaultExpireDuration()      */
specifier|public
specifier|final
name|void
name|setDefaultExpireDuration
parameter_list|(
name|long
name|duration
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|duration
operator|<=
literal|0
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|DEFAULT_EXPIRE_DURATION
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|DEFAULT_EXPIRE_DURATION
argument_list|,
name|duration
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|MappingState
name|getDefaultMappedEntityState
parameter_list|()
block|{
name|Object
name|defaultMappingState
init|=
name|config
operator|.
name|get
argument_list|(
name|DEFAULT_MAPPING_STATE
argument_list|)
decl_stmt|;
if|if
condition|(
name|defaultMappingState
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
elseif|else
if|if
condition|(
name|defaultMappingState
operator|instanceof
name|MappingState
condition|)
block|{
return|return
operator|(
name|MappingState
operator|)
name|defaultMappingState
return|;
block|}
else|else
block|{
return|return
name|MappingState
operator|.
name|valueOf
argument_list|(
name|defaultMappingState
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
block|}
comment|/**      * Setter for the default state of Mappings created between Entities of this      * Site and Entities managed by the Entityhub. If this configuration is not      * present created mappings will have the default state as configured by the      * Entityhub.      * @param state the default state for new Entity mappings.      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getDefaultMappedEntityState()      */
specifier|public
specifier|final
name|void
name|setDefaultMappedEntityState
parameter_list|(
name|MappingState
name|state
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|state
operator|==
literal|null
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|DEFAULT_MAPPING_STATE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|DEFAULT_MAPPING_STATE
argument_list|,
name|state
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|ManagedEntityState
name|getDefaultManagedEntityState
parameter_list|()
block|{
name|Object
name|defaultSymbolState
init|=
name|config
operator|.
name|get
argument_list|(
name|DEFAULT_SYMBOL_STATE
argument_list|)
decl_stmt|;
if|if
condition|(
name|defaultSymbolState
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
elseif|else
if|if
condition|(
name|defaultSymbolState
operator|instanceof
name|ManagedEntityState
condition|)
block|{
return|return
operator|(
name|ManagedEntityState
operator|)
name|defaultSymbolState
return|;
block|}
else|else
block|{
return|return
name|ManagedEntityState
operator|.
name|valueOf
argument_list|(
name|defaultSymbolState
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
block|}
comment|/**      * Setter for the default state of Entities after importing them into the      * Entityhub. If this configuration is not present Entities will have the      * default state set for the Entityhub.      * @param state the state or<code>null</code> to remove this configuration      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getDefaultManagedEntityState()      */
specifier|public
specifier|final
name|void
name|setDefaultManagedEntityState
parameter_list|(
name|ManagedEntityState
name|state
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|state
operator|==
literal|null
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|DEFAULT_SYMBOL_STATE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|DEFAULT_SYMBOL_STATE
argument_list|,
name|state
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getAttribution
parameter_list|()
block|{
name|Object
name|attribution
init|=
name|config
operator|.
name|get
argument_list|(
name|SITE_ATTRIBUTION
argument_list|)
decl_stmt|;
return|return
name|attribution
operator|==
literal|null
operator|||
name|attribution
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|attribution
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      *       * @param attribution      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getAttribution()      */
specifier|public
specifier|final
name|void
name|setAttribution
parameter_list|(
name|String
name|attribution
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|attribution
operator|==
literal|null
operator|||
name|attribution
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|SITE_ATTRIBUTION
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|SITE_ATTRIBUTION
argument_list|,
name|attribution
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getAttributionUrl
parameter_list|()
block|{
name|Object
name|attribution
init|=
name|config
operator|.
name|get
argument_list|(
name|SITE_ATTRIBUTION_URL
argument_list|)
decl_stmt|;
return|return
name|attribution
operator|==
literal|null
operator|||
name|attribution
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|attribution
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      *       * @param attribution      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getAttribution()      */
specifier|public
specifier|final
name|void
name|setAttributionUrl
parameter_list|(
name|String
name|attributionUrl
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|attributionUrl
operator|==
literal|null
operator|||
name|attributionUrl
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|SITE_ATTRIBUTION_URL
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|SITE_ATTRIBUTION_URL
argument_list|,
name|attributionUrl
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getDescription
parameter_list|()
block|{
name|Object
name|description
init|=
name|config
operator|.
name|get
argument_list|(
name|DESCRIPTION
argument_list|)
decl_stmt|;
return|return
name|description
operator|==
literal|null
operator|||
name|description
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|description
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Setter for the description of the {@link Site}. If set to      *<code>null</code> or an empty string this configuration will be removed.      * @param description the description      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getDescription()      */
specifier|public
specifier|final
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|description
operator|==
literal|null
operator|||
name|description
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|DESCRIPTION
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|DESCRIPTION
argument_list|,
name|description
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
index|[]
name|getEntityPrefixes
parameter_list|()
block|{
name|String
index|[]
name|prefixes
init|=
name|getStringValues
argument_list|(
name|ENTITY_PREFIX
argument_list|)
decl_stmt|;
return|return
name|prefixes
operator|==
literal|null
condition|?
operator|new
name|String
index|[]
block|{}
else|:
name|prefixes
return|;
block|}
comment|/**      * Setter for the Entity prefixes (typically the namespace or the host name)      * of the entities provided by this site. Because Sites might provide Entities      * with different namespaces this site allows to parse an array. Setting the      * prefixes to<code>null</code> or an empty array will cause that this site      * is ask for all requested entities.      * @param prefixes The array with the prefixes or<code>null</code> to       * remove any configured prefixes      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getEntityPrefixes()      */
specifier|public
specifier|final
name|void
name|setEntityPrefixes
parameter_list|(
name|String
index|[]
name|prefixes
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|prefixes
operator|==
literal|null
operator|||
name|prefixes
operator|.
name|length
operator|<
literal|1
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|ENTITY_PREFIX
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|ENTITY_PREFIX
argument_list|,
name|prefixes
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getId
parameter_list|()
block|{
name|Object
name|id
init|=
name|config
operator|.
name|get
argument_list|(
name|ID
argument_list|)
decl_stmt|;
return|return
name|id
operator|==
literal|null
condition|?
literal|null
else|:
name|id
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Setter for the id of the referenced site      * @param id the id      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @throws IllegalArgumentException in case the parsed ID is<code>null</code> or an empty String      * @see #getId()      */
specifier|public
specifier|final
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|UnsupportedOperationException
throws|,
name|IllegalArgumentException
block|{
if|if
condition|(
name|id
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The ID of the Site MUST NOT be set to NULL!"
argument_list|)
throw|;
block|}
elseif|else
if|if
condition|(
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The ID of the Site MIST NOT be set to an empty String!"
argument_list|)
throw|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|ID
argument_list|,
name|id
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|License
index|[]
name|getLicenses
parameter_list|()
block|{
comment|//get Licenses based on related keys
name|int
name|elements
init|=
literal|0
decl_stmt|;
name|String
index|[]
name|names
init|=
name|getLicenseName
argument_list|()
decl_stmt|;
if|if
condition|(
name|names
operator|==
literal|null
condition|)
block|{
name|names
operator|=
operator|new
name|String
index|[]
block|{}
expr_stmt|;
block|}
else|else
block|{
name|elements
operator|=
name|Math
operator|.
name|max
argument_list|(
name|elements
argument_list|,
name|names
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|texts
init|=
name|getLicenseText
argument_list|()
decl_stmt|;
if|if
condition|(
name|texts
operator|==
literal|null
condition|)
block|{
name|texts
operator|=
operator|new
name|String
index|[]
block|{}
expr_stmt|;
block|}
else|else
block|{
name|elements
operator|=
name|Math
operator|.
name|max
argument_list|(
name|elements
argument_list|,
name|texts
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|urls
init|=
name|getLicenseUrl
argument_list|()
decl_stmt|;
if|if
condition|(
name|urls
operator|==
literal|null
condition|)
block|{
name|urls
operator|=
operator|new
name|String
index|[]
block|{}
expr_stmt|;
block|}
else|else
block|{
name|elements
operator|=
name|Math
operator|.
name|max
argument_list|(
name|elements
argument_list|,
name|urls
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
name|Collection
argument_list|<
name|License
argument_list|>
name|licenseList
init|=
operator|new
name|ArrayList
argument_list|<
name|License
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|elements
condition|;
name|i
operator|++
control|)
block|{
try|try
block|{
name|licenseList
operator|.
name|add
argument_list|(
operator|new
name|License
argument_list|(
name|names
operator|.
name|length
operator|>
name|i
condition|?
name|names
index|[
name|i
index|]
else|:
literal|null
argument_list|,
name|urls
operator|.
name|length
operator|>
name|i
condition|?
name|urls
index|[
name|i
index|]
else|:
literal|null
argument_list|,
name|texts
operator|.
name|length
operator|>
name|i
condition|?
name|texts
index|[
name|i
index|]
else|:
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
comment|//ignore if name, text and url == null and/or empty
block|}
block|}
return|return
name|licenseList
operator|.
name|isEmpty
argument_list|()
condition|?
operator|new
name|License
index|[]
block|{}
else|:
name|licenseList
operator|.
name|toArray
argument_list|(
operator|new
name|License
index|[
name|licenseList
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
comment|/**      * Setter for the {@link License} information. This method stores the name,      * text and url of the license as strings in the according fields of the      * configuration.      * @param licenses the licenses to store.<code>null</code> or an empty      * array to remove existing values      * @throws IllegalArgumentException if the parsed array contains a<code>null</code>      * element      * @throws UnsupportedOperationException if the configuration is read-only      */
specifier|public
specifier|final
name|void
name|setLicenses
parameter_list|(
name|License
index|[]
name|licenses
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|UnsupportedOperationException
block|{
if|if
condition|(
name|licenses
operator|==
literal|null
operator|||
name|licenses
operator|.
name|length
operator|<
literal|1
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|SITE_LICENCE_NAME
argument_list|)
expr_stmt|;
name|config
operator|.
name|remove
argument_list|(
name|SITE_LICENCE_TEXT
argument_list|)
expr_stmt|;
name|config
operator|.
name|remove
argument_list|(
name|SITE_LICENCE_URL
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
index|[]
name|names
init|=
operator|new
name|String
index|[
name|licenses
operator|.
name|length
index|]
decl_stmt|;
name|String
index|[]
name|texts
init|=
operator|new
name|String
index|[
name|licenses
operator|.
name|length
index|]
decl_stmt|;
name|String
index|[]
name|urls
init|=
operator|new
name|String
index|[
name|licenses
operator|.
name|length
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|licenses
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|licenses
index|[
name|i
index|]
operator|!=
literal|null
condition|)
block|{
name|names
index|[
name|i
index|]
operator|=
name|licenses
index|[
name|i
index|]
operator|.
name|getName
argument_list|()
expr_stmt|;
name|texts
index|[
name|i
index|]
operator|=
name|licenses
index|[
name|i
index|]
operator|.
name|getText
argument_list|()
expr_stmt|;
name|urls
index|[
name|i
index|]
operator|=
name|licenses
index|[
name|i
index|]
operator|.
name|getUrl
argument_list|()
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed License array"
operator|+
literal|"MUST NOT contain a NULL element! (parsed: "
operator|+
name|Arrays
operator|.
name|toString
argument_list|(
name|licenses
argument_list|)
operator|+
literal|")"
argument_list|)
throw|;
block|}
block|}
name|config
operator|.
name|put
argument_list|(
name|SITE_LICENCE_NAME
argument_list|,
name|names
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|SITE_LICENCE_TEXT
argument_list|,
name|texts
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|SITE_LICENCE_URL
argument_list|,
name|urls
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Internally used to get the names of the licenses      * @return      */
specifier|private
name|String
index|[]
name|getLicenseName
parameter_list|()
block|{
return|return
name|getStringValues
argument_list|(
name|SITE_LICENCE_NAME
argument_list|)
return|;
block|}
comment|/**      * Internally used to get the texts of the licenes      * @return      */
specifier|private
name|String
index|[]
name|getLicenseText
parameter_list|()
block|{
return|return
name|getStringValues
argument_list|(
name|SITE_LICENCE_TEXT
argument_list|)
return|;
block|}
comment|/**      * Internally used to get the urls of the page describing the license      * @return      */
specifier|private
name|String
index|[]
name|getLicenseUrl
parameter_list|()
block|{
return|return
name|getStringValues
argument_list|(
name|SITE_LICENCE_URL
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
name|Object
name|name
init|=
name|config
operator|.
name|get
argument_list|(
name|NAME
argument_list|)
decl_stmt|;
comment|//use ID as fallback!
return|return
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
name|getId
argument_list|()
else|:
name|name
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Setter for the name of the Referenced Site. Note that if the name is not      * present the {@link #getId() id} will be used as name.      * @param name the name of the site or<code>null</code> to remove it (and      * use the {@link #getId() id} also as name      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getName()      */
specifier|public
specifier|final
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|NAME
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|NAME
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Provides direct access to the internal configuration      * @return the configuration wrapped by this class      */
specifier|public
specifier|final
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getConfiguration
parameter_list|()
block|{
return|return
name|config
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
index|[]
name|getFieldMappings
parameter_list|()
block|{
return|return
name|getStringValues
argument_list|(
name|SITE_FIELD_MAPPINGS
argument_list|)
return|;
block|}
comment|/**      * Setter for the mappings of a site. This mappings are used in case an       * Entity of this site is imported to the Entityhub. Parsing<code>null</code>      * or an empty array will cause all existing mappings to be removed.      * @param mappings the mappings      * @throws UnsupportedOperationException      */
specifier|public
specifier|final
name|void
name|setFieldMappings
parameter_list|(
name|String
index|[]
name|mappings
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|mappings
operator|==
literal|null
operator|||
name|mappings
operator|.
name|length
operator|<
literal|1
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|SITE_FIELD_MAPPINGS
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|SITE_FIELD_MAPPINGS
argument_list|,
name|mappings
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Internally used to parse String[] based on key values. This method      * supports Stirng, Stirng[] and Iterables&lt;?&gt;. For Iterables&lt;?&gt;      * the {@link Object#toString()} is used and<code>null</code> elementes are      * kept.      * @return      */
specifier|protected
specifier|final
name|String
index|[]
name|getStringValues
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|Object
name|values
init|=
name|config
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|values
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
elseif|else
if|if
condition|(
name|values
operator|instanceof
name|String
index|[]
condition|)
block|{
if|if
condition|(
operator|(
operator|(
name|String
index|[]
operator|)
name|values
operator|)
operator|.
name|length
operator|<
literal|1
condition|)
block|{
comment|//return null if empty
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
operator|(
name|String
index|[]
operator|)
name|values
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|values
operator|instanceof
name|Iterable
argument_list|<
name|?
argument_list|>
condition|)
block|{
name|Collection
argument_list|<
name|String
argument_list|>
name|prefixes
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
name|value
operator|:
operator|(
name|Iterable
argument_list|<
name|?
argument_list|>
operator|)
name|values
control|)
block|{
name|prefixes
operator|.
name|add
argument_list|(
name|value
operator|==
literal|null
condition|?
literal|null
else|:
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|prefixes
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|prefixes
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|values
operator|instanceof
name|String
condition|)
block|{
return|return
operator|new
name|String
index|[]
block|{
name|values
operator|.
name|toString
argument_list|()
block|}
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse Sting[] for field %s form type %s (supported are String, String[] and Iterables)"
argument_list|,
name|key
argument_list|,
name|values
operator|.
name|getClass
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
block|}
end_class

unit|}
end_unit

