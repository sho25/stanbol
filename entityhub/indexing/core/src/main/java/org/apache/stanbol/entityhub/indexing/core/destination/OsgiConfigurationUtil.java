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
name|indexing
operator|.
name|core
operator|.
name|destination
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|commons
operator|.
name|io
operator|.
name|FileUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|FilenameUtils
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
name|cm
operator|.
name|file
operator|.
name|ConfigurationHandler
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
name|indexing
operator|.
name|core
operator|.
name|config
operator|.
name|IndexingConfig
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
name|SiteConfiguration
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

begin_import
import|import
name|aQute
operator|.
name|lib
operator|.
name|osgi
operator|.
name|Builder
import|;
end_import

begin_import
import|import
name|aQute
operator|.
name|lib
operator|.
name|osgi
operator|.
name|Jar
import|;
end_import

begin_class
specifier|public
specifier|final
class|class
name|OsgiConfigurationUtil
block|{
specifier|private
name|OsgiConfigurationUtil
parameter_list|()
block|{
comment|/* Do not create instances of Util classes*/
block|}
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
name|OsgiConfigurationUtil
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_MAPPING_STATE
init|=
literal|"proposed"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_IMPORTED_ENTTIY_STATE
init|=
literal|"proposed"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Object
name|DEFAULT_EXPIRE_DURATION
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
literal|0
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|REFERENCED_SITE_COMPONENT_ID
init|=
literal|"org.apache.stanbol.entityhub.site.referencedSite"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CACHE_COMPONENT_ID
init|=
literal|"org.apache.stanbol.entityhub.core.site.CacheImpl"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|CONFIG_ROOT
init|=
literal|"config"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|CONFIG_PATH_ELEMENTS
init|=
operator|new
name|String
index|[]
block|{
literal|"org"
block|,
literal|"apache"
block|,
literal|"stanbol"
block|,
literal|"data"
block|,
literal|"site"
block|}
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|CONFIG_PATH
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|CONFIG_PACKAGE
decl_stmt|;
static|static
block|{
name|StringBuilder
name|path
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|StringBuilder
name|java
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|part
range|:
name|CONFIG_PATH_ELEMENTS
control|)
block|{
name|path
operator|.
name|append
argument_list|(
name|part
argument_list|)
expr_stmt|;
name|java
operator|.
name|append
argument_list|(
name|part
argument_list|)
expr_stmt|;
name|path
operator|.
name|append
argument_list|(
name|File
operator|.
name|separatorChar
argument_list|)
expr_stmt|;
name|java
operator|.
name|append
argument_list|(
literal|'.'
argument_list|)
expr_stmt|;
block|}
name|CONFIG_PATH
operator|=
name|path
operator|.
name|toString
argument_list|()
expr_stmt|;
name|CONFIG_PACKAGE
operator|=
name|java
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|writeOsgiConfig
parameter_list|(
name|IndexingConfig
name|indexingConfig
parameter_list|,
name|String
name|name
parameter_list|,
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|indexingConfig
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed IndexingConfiguration MUST NOT be NULL"
argument_list|)
throw|;
block|}
if|if
condition|(
name|name
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed file name MUST NOT be NULL"
argument_list|)
throw|;
block|}
if|if
condition|(
name|config
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed configuration MUST NOT be NULL"
argument_list|)
throw|;
block|}
if|if
condition|(
name|config
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed configuration MUST NOT be empty"
argument_list|)
throw|;
block|}
name|File
name|configFile
init|=
operator|new
name|File
argument_list|(
name|getConfigDirectory
argument_list|(
name|indexingConfig
argument_list|)
argument_list|,
name|name
argument_list|)
decl_stmt|;
name|ConfigurationHandler
operator|.
name|write
argument_list|(
name|FileUtils
operator|.
name|openOutputStream
argument_list|(
name|configFile
argument_list|)
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|createSiteConfig
parameter_list|(
name|IndexingConfig
name|indexingConfig
parameter_list|)
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
comment|//basic properties
comment|//we use the name as ID
name|config
operator|.
name|put
argument_list|(
name|SiteConfiguration
operator|.
name|ID
argument_list|,
name|indexingConfig
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
comment|//also set the id as name
name|config
operator|.
name|put
argument_list|(
name|SiteConfiguration
operator|.
name|NAME
argument_list|,
name|indexingConfig
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
comment|//config.put(SiteConfiguration.NAME, indexingConfig.getName());
if|if
condition|(
name|indexingConfig
operator|.
name|getDescription
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|indexingConfig
operator|.
name|getDescription
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|SiteConfiguration
operator|.
name|DESCRIPTION
argument_list|,
name|indexingConfig
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//the cache
comment|//name the Cache is the same as for the Yard.
name|config
operator|.
name|put
argument_list|(
name|ReferencedSiteConfiguration
operator|.
name|CACHE_ID
argument_list|,
name|getYardID
argument_list|(
name|indexingConfig
argument_list|)
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|ReferencedSiteConfiguration
operator|.
name|CACHE_STRATEGY
argument_list|,
name|CacheStrategy
operator|.
name|all
argument_list|)
expr_stmt|;
comment|//Entity Dereferencer (optional)
if|if
condition|(
name|addProperty
argument_list|(
name|ReferencedSiteConfiguration
operator|.
name|ACCESS_URI
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|)
condition|)
block|{
name|addProperty
argument_list|(
name|ReferencedSiteConfiguration
operator|.
name|ENTITY_DEREFERENCER_TYPE
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|,
literal|"Referenced Site for "
operator|+
name|indexingConfig
operator|.
name|getName
argument_list|()
operator|+
literal|" (including a full local Cache)"
argument_list|)
expr_stmt|;
block|}
comment|//Entity Searcher (optional)
if|if
condition|(
name|addProperty
argument_list|(
name|ReferencedSiteConfiguration
operator|.
name|QUERY_URI
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|)
condition|)
block|{
name|addProperty
argument_list|(
name|ReferencedSiteConfiguration
operator|.
name|ENTITY_SEARCHER_TYPE
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|)
expr_stmt|;
block|}
comment|//General Properties
name|addProperty
argument_list|(
name|SiteConfiguration
operator|.
name|DEFAULT_EXPIRE_DURATION
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|,
name|DEFAULT_EXPIRE_DURATION
argument_list|)
expr_stmt|;
name|addProperty
argument_list|(
name|SiteConfiguration
operator|.
name|DEFAULT_MAPPING_STATE
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|,
name|DEFAULT_MAPPING_STATE
argument_list|)
expr_stmt|;
name|addProperty
argument_list|(
name|SiteConfiguration
operator|.
name|DEFAULT_SYMBOL_STATE
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|,
name|DEFAULT_IMPORTED_ENTTIY_STATE
argument_list|)
expr_stmt|;
comment|//the entity prefix is optional and may be an array
name|addPropertyValues
argument_list|(
name|SiteConfiguration
operator|.
name|ENTITY_PREFIX
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|)
expr_stmt|;
comment|//add the Field Mappings when entities of this Site are imported to the
comment|//entityhub. This may be the same mappings as used for the Cache however
comment|//they may be also different.
name|Object
name|value
init|=
name|indexingConfig
operator|.
name|getProperty
argument_list|(
name|SiteConfiguration
operator|.
name|SITE_FIELD_MAPPINGS
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|File
name|fieldMappingConfig
init|=
name|indexingConfig
operator|.
name|getConfigFile
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldMappingConfig
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|config
operator|.
name|put
argument_list|(
name|SiteConfiguration
operator|.
name|SITE_FIELD_MAPPINGS
argument_list|,
name|FileUtils
operator|.
name|readLines
argument_list|(
name|fieldMappingConfig
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to read Field Mappings for Referenced Site "
operator|+
literal|"configuration"
argument_list|)
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to load configured Field Mappings for Reference Site "
operator|+
literal|"{}={}"
argument_list|,
name|SiteConfiguration
operator|.
name|SITE_FIELD_MAPPINGS
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
comment|//set other optional properties
name|addProperty
argument_list|(
name|SiteConfiguration
operator|.
name|SITE_ATTRIBUTION
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|)
expr_stmt|;
name|addProperty
argument_list|(
name|SiteConfiguration
operator|.
name|SITE_ATTRIBUTION_URL
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|)
expr_stmt|;
name|addPropertyValues
argument_list|(
name|SiteConfiguration
operator|.
name|SITE_LICENCE_NAME
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|)
expr_stmt|;
name|addPropertyValues
argument_list|(
name|SiteConfiguration
operator|.
name|SITE_LICENCE_TEXT
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|)
expr_stmt|;
name|addPropertyValues
argument_list|(
name|SiteConfiguration
operator|.
name|SITE_LICENCE_URL
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|)
expr_stmt|;
return|return
name|config
return|;
block|}
specifier|private
specifier|static
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|createCacheConfig
parameter_list|(
name|IndexingConfig
name|indexingConfig
parameter_list|)
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
comment|//a cache needs to provide the ID of the Yard
name|String
name|yardId
init|=
name|getYardID
argument_list|(
name|indexingConfig
argument_list|)
decl_stmt|;
name|config
operator|.
name|put
argument_list|(
name|Cache
operator|.
name|ID
argument_list|,
name|yardId
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|Cache
operator|.
name|NAME
argument_list|,
name|indexingConfig
operator|.
name|getName
argument_list|()
operator|+
literal|" Cache"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|Cache
operator|.
name|DESCRIPTION
argument_list|,
literal|"Cache for the "
operator|+
name|indexingConfig
operator|.
name|getName
argument_list|()
operator|+
literal|" Referenced Site using the "
operator|+
name|yardId
operator|+
literal|"."
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|Cache
operator|.
name|CACHE_YARD
argument_list|,
name|getYardID
argument_list|(
name|indexingConfig
argument_list|)
argument_list|)
expr_stmt|;
comment|//additinal Mappings:
comment|// This can be used to define what information are store to the cache
comment|// if an Entity is updated from a remote site.
comment|// If not present the mappings used by the Yard are used. This default
comment|// is sufficient for full indexes as created by the indexing utils
comment|// therefore we need not to deal with additional mappings here
return|return
name|config
return|;
block|}
comment|/**      * Adds the configurations as defined by the Yard Interface. Configurations      * of specific Yard implementations might need to add additional       * parameters.<p>      * This also ensures that the ID of the Yard is the same as referenced by the      * configurations for the referenced site and the cache.      * @param indexingConfig      * @return      */
specifier|public
specifier|static
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|createYardConfig
parameter_list|(
name|IndexingConfig
name|indexingConfig
parameter_list|)
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|config
operator|.
name|put
argument_list|(
name|Yard
operator|.
name|ID
argument_list|,
name|getYardID
argument_list|(
name|indexingConfig
argument_list|)
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|Yard
operator|.
name|NAME
argument_list|,
name|indexingConfig
operator|.
name|getName
argument_list|()
operator|+
literal|" Index"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|Yard
operator|.
name|DESCRIPTION
argument_list|,
literal|"Full local index for the Referenced Site \""
operator|+
name|indexingConfig
operator|.
name|getName
argument_list|()
operator|+
literal|"\"."
argument_list|)
expr_stmt|;
return|return
name|config
return|;
block|}
specifier|public
specifier|static
name|void
name|writeSiteConfiguration
parameter_list|(
name|IndexingConfig
name|indexingConfig
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|siteConfigFileName
init|=
name|REFERENCED_SITE_COMPONENT_ID
operator|+
literal|"-"
operator|+
name|indexingConfig
operator|.
name|getName
argument_list|()
operator|+
literal|".config"
decl_stmt|;
name|writeOsgiConfig
argument_list|(
name|indexingConfig
argument_list|,
name|siteConfigFileName
argument_list|,
name|createSiteConfig
argument_list|(
name|indexingConfig
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|writeCacheConfiguration
parameter_list|(
name|IndexingConfig
name|indexingConfig
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|cacheFileName
init|=
name|CACHE_COMPONENT_ID
operator|+
literal|"-"
operator|+
name|indexingConfig
operator|.
name|getName
argument_list|()
operator|+
literal|".config"
decl_stmt|;
name|writeOsgiConfig
argument_list|(
name|indexingConfig
argument_list|,
name|cacheFileName
argument_list|,
name|createCacheConfig
argument_list|(
name|indexingConfig
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Getter for default ID of the yard based on the value of       * {@link IndexingConfig#getName()}      * @param config the IndexingConfig      * @return the default ID of the yard based on the value of       * {@link IndexingConfig#getName()}      */
specifier|public
specifier|static
name|String
name|getYardID
parameter_list|(
name|IndexingConfig
name|config
parameter_list|)
block|{
return|return
name|config
operator|.
name|getName
argument_list|()
operator|+
literal|"Index"
return|;
block|}
specifier|private
specifier|static
name|boolean
name|addPropertyValues
parameter_list|(
name|String
name|key
parameter_list|,
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|,
name|IndexingConfig
name|indexingConfig
parameter_list|)
block|{
name|Object
name|value
init|=
name|indexingConfig
operator|.
name|getProperty
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
specifier|private
specifier|static
name|boolean
name|addProperty
parameter_list|(
name|String
name|key
parameter_list|,
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|,
name|IndexingConfig
name|indexingConfig
parameter_list|)
block|{
return|return
name|addProperty
argument_list|(
name|key
argument_list|,
name|config
argument_list|,
name|indexingConfig
argument_list|,
literal|null
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|boolean
name|addProperty
parameter_list|(
name|String
name|key
parameter_list|,
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|,
name|IndexingConfig
name|indexingConfig
parameter_list|,
name|Object
name|defaultValue
parameter_list|)
block|{
name|Object
name|value
init|=
name|indexingConfig
operator|.
name|getProperty
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
operator|||
name|defaultValue
operator|!=
literal|null
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
operator|!=
literal|null
condition|?
name|value
else|:
name|defaultValue
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
comment|/**      * Getter for the Directory that need to contain all Files to be included      * in the OSGI Bundle.      * @param config the indexing configuration      * @return the directory (created if not already existing)      * @throws IOException If the directory could not be created      */
specifier|public
specifier|static
name|File
name|getConfigDirectory
parameter_list|(
name|IndexingConfig
name|config
parameter_list|)
throws|throws
name|IOException
block|{
name|File
name|configRoot
init|=
operator|new
name|File
argument_list|(
name|config
operator|.
name|getDestinationFolder
argument_list|()
argument_list|,
name|CONFIG_ROOT
argument_list|)
decl_stmt|;
name|File
name|siteConfigDir
init|=
operator|new
name|File
argument_list|(
name|configRoot
argument_list|,
name|CONFIG_PATH
operator|+
name|config
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|siteConfigDir
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|siteConfigDir
operator|.
name|mkdirs
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Unable to create config Directory "
operator|+
name|siteConfigDir
argument_list|)
throw|;
block|}
block|}
return|return
name|siteConfigDir
return|;
block|}
specifier|public
specifier|static
name|void
name|createBundle
parameter_list|(
name|IndexingConfig
name|config
parameter_list|)
block|{
name|Builder
name|builder
init|=
operator|new
name|Builder
argument_list|()
decl_stmt|;
name|builder
operator|.
name|setProperty
argument_list|(
literal|"Install-Path"
argument_list|,
name|FilenameUtils
operator|.
name|separatorsToUnix
argument_list|(
name|CONFIG_PATH
argument_list|)
comment|//see STANBOL-768
operator|+
name|config
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|setProperty
argument_list|(
name|Builder
operator|.
name|EXPORT_PACKAGE
argument_list|,
name|CONFIG_PACKAGE
operator|+
name|config
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|setProperty
argument_list|(
name|Builder
operator|.
name|BUNDLE_CATEGORY
argument_list|,
literal|"Stanbol Data"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|setProperty
argument_list|(
name|Builder
operator|.
name|BUNDLE_NAME
argument_list|,
literal|"Apache Stanbol Data: "
operator|+
name|config
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|setProperty
argument_list|(
name|Builder
operator|.
name|CREATED_BY
argument_list|,
literal|"Apache Stanbol Entityhub Indexing Utils"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|setProperty
argument_list|(
name|Builder
operator|.
name|BUNDLE_VENDOR
argument_list|,
literal|"Apache Stanbol (Incubating)"
argument_list|)
expr_stmt|;
comment|//TODO make configureable
name|builder
operator|.
name|setProperty
argument_list|(
name|Builder
operator|.
name|BUNDLE_VERSION
argument_list|,
literal|"1.0.0"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|setProperty
argument_list|(
name|Builder
operator|.
name|BUNDLE_DESCRIPTION
argument_list|,
literal|"Bundle created for import of the referenced site "
operator|+
name|config
operator|.
name|getName
argument_list|()
operator|+
literal|" into the Apache Stanbol Entityhub"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|setProperty
argument_list|(
name|Builder
operator|.
name|BUNDLE_SYMBOLICNAME
argument_list|,
name|CONFIG_PACKAGE
operator|+
name|config
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|builder
operator|.
name|addClasspath
argument_list|(
operator|new
name|File
argument_list|(
name|config
operator|.
name|getDestinationFolder
argument_list|()
argument_list|,
name|CONFIG_ROOT
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"(builder.addClasspath) Unable to build OSGI Bundle for Indexed Referenced Site "
operator|+
name|config
operator|.
name|getName
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|builder
operator|.
name|close
argument_list|()
expr_stmt|;
comment|//?? why not throwing an exception here ??
return|return;
block|}
try|try
block|{
name|Jar
name|jar
init|=
name|builder
operator|.
name|build
argument_list|()
decl_stmt|;
name|jar
operator|.
name|write
argument_list|(
operator|new
name|File
argument_list|(
name|config
operator|.
name|getDistributionFolder
argument_list|()
argument_list|,
name|CONFIG_PACKAGE
operator|+
name|config
operator|.
name|getName
argument_list|()
operator|+
literal|"-1.0.0.jar"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"(builder.build) Unable to build OSGI Bundle for Indexed Referenced Site "
operator|+
name|config
operator|.
name|getName
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
comment|//?? why not throwing an exception here ??
return|return;
block|}
finally|finally
block|{
name|builder
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

