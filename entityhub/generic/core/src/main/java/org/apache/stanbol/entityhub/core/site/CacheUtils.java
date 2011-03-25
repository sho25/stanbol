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
name|core
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
name|core
operator|.
name|mapping
operator|.
name|DefaultFieldMapperImpl
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
name|core
operator|.
name|mapping
operator|.
name|FieldMappingUtils
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
name|core
operator|.
name|mapping
operator|.
name|ValueConverterFactory
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
name|core
operator|.
name|utils
operator|.
name|ModelUtils
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
name|Representation
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
name|CacheInitialisationException
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
name|YardException
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
specifier|public
specifier|final
class|class
name|CacheUtils
block|{
specifier|private
name|CacheUtils
parameter_list|()
block|{}
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|CacheUtils
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Loads the base mappings form the parsed Yard      * @param yard The yard      * @return The baseMappings      * @throws YardException on any Error while getting the Representation holding      * the Configuration from the Yard.      * @throws CacheInitialisationException if the configuration is found but not      * valid.      * @throws IllegalArgumentException if<code>null</code> is parsed as {@link Yard}      */
specifier|public
specifier|static
name|FieldMapper
name|loadBaseMappings
parameter_list|(
name|Yard
name|yard
parameter_list|)
throws|throws
name|YardException
throws|,
name|CacheInitialisationException
block|{
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Yard MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|Representation
name|baseConfig
init|=
name|yard
operator|.
name|getRepresentation
argument_list|(
name|Cache
operator|.
name|BASE_CONFIGURATION_URI
argument_list|)
decl_stmt|;
if|if
condition|(
name|baseConfig
operator|!=
literal|null
condition|)
block|{
name|FieldMapper
name|mapper
init|=
name|readFieldConfig
argument_list|(
name|yard
argument_list|,
name|baseConfig
argument_list|)
decl_stmt|;
if|if
condition|(
name|mapper
operator|==
literal|null
condition|)
block|{
name|String
name|msg
init|=
literal|"Invalid Base Configuration: Unable to parse FieldMappings from Field "
operator|+
name|Cache
operator|.
name|FIELD_MAPPING_CONFIG_FIELD
decl_stmt|;
name|log
operator|.
name|error
argument_list|(
name|msg
argument_list|)
expr_stmt|;
if|if
condition|(
name|log
operator|.
name|isErrorEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|error
argument_list|(
name|ModelUtils
operator|.
name|getRepresentationInfo
argument_list|(
name|baseConfig
argument_list|)
argument_list|)
expr_stmt|;
block|}
throw|throw
operator|new
name|CacheInitialisationException
argument_list|(
name|msg
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|mapper
return|;
block|}
block|}
else|else
block|{
return|return
literal|null
return|;
comment|//throw new CacheInitialisationException("Base Configuration not present");
block|}
block|}
comment|/**      * Loads the additional field mappings used by this cache from the yard.      * This method sets the {@link #baseMapper} field during initialisation.      * @param yard The yard      * @return The parsed mappings or<code>null</code> if no found      * @throws YardException on any Error while reading the {@link Representation}      * holding the configuration from the {@link Yard}.      * @throws IllegalArgumentException if<code>null</code> is parsed as {@link Yard}.      */
specifier|protected
specifier|static
name|FieldMapper
name|loadAdditionalMappings
parameter_list|(
name|Yard
name|yard
parameter_list|)
throws|throws
name|YardException
block|{
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Yard MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|Representation
name|addConfig
init|=
name|yard
operator|.
name|getRepresentation
argument_list|(
name|Cache
operator|.
name|ADDITIONAL_CONFIGURATION_URI
argument_list|)
decl_stmt|;
if|if
condition|(
name|addConfig
operator|!=
literal|null
condition|)
block|{
name|FieldMapper
name|mapper
init|=
name|readFieldConfig
argument_list|(
name|yard
argument_list|,
name|addConfig
argument_list|)
decl_stmt|;
if|if
condition|(
name|mapper
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Invalid Additinal Configuration: Unable to parse FieldMappings from Field "
operator|+
name|Cache
operator|.
name|FIELD_MAPPING_CONFIG_FIELD
operator|+
literal|"-> return NULL (no additional Configuration)"
argument_list|)
expr_stmt|;
if|if
condition|(
name|log
operator|.
name|isWarnEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
name|ModelUtils
operator|.
name|getRepresentationInfo
argument_list|(
name|addConfig
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|mapper
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Reads the field mapping config from an document      * @param yard the yard of the parsed Representation      * @param config the configuration MUST NOT be<code>null</code>      * @return A field mapper configured based on the configuration in the parsed {@link Representation}      * @throws if the parsed {@link Representation} does not contain a value for {@value CacheConstants.FIELD_MAPPING_CONFIG_FIELD}.      */
specifier|private
specifier|static
name|FieldMapper
name|readFieldConfig
parameter_list|(
name|Yard
name|yard
parameter_list|,
name|Representation
name|config
parameter_list|)
block|{
name|Object
name|mappingValue
init|=
name|config
operator|.
name|getFirst
argument_list|(
name|Cache
operator|.
name|FIELD_MAPPING_CONFIG_FIELD
argument_list|)
decl_stmt|;
if|if
condition|(
name|mappingValue
operator|!=
literal|null
condition|)
block|{
name|DefaultFieldMapperImpl
name|fieldMapper
init|=
operator|new
name|DefaultFieldMapperImpl
argument_list|(
name|ValueConverterFactory
operator|.
name|getDefaultInstance
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|mappingStirng
range|:
name|mappingValue
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
control|)
block|{
name|FieldMapping
name|mapping
init|=
name|FieldMappingUtils
operator|.
name|parseFieldMapping
argument_list|(
name|mappingStirng
argument_list|)
decl_stmt|;
if|if
condition|(
name|mapping
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"> add Mapping: "
operator|+
name|mappingStirng
argument_list|)
expr_stmt|;
name|fieldMapper
operator|.
name|addMapping
argument_list|(
name|mapping
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|fieldMapper
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Stores the baseMappings to the {@link Yard}. This may cause unexpected      * behaviour for subsequest calls of the stored configuration does not      * correspond with the actual data stored within the cache.<p>      * Typically this is only used at the start or end of the creation of a      * full Cache ({@link CacheStrategy#all}) of an referenced site (entity source).<p>      * Note also that if the {@link #baseMapper} is<code>null</code> this      * method removes any existing configuration from the yard.      * @throws YardException an any error while storing the config to the yard.      * @throws IllegalArgumentException if<code>null</code> is parsed as {@link Yard}.      */
specifier|public
specifier|static
name|void
name|storeBaseMappingsConfiguration
parameter_list|(
name|Yard
name|yard
parameter_list|,
name|FieldMapper
name|baseMapper
parameter_list|)
throws|throws
name|YardException
throws|,
name|IllegalArgumentException
block|{
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Yard MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|baseMapper
operator|==
literal|null
condition|)
block|{
name|yard
operator|.
name|remove
argument_list|(
name|Cache
operator|.
name|BASE_CONFIGURATION_URI
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Representation
name|config
init|=
name|yard
operator|.
name|getValueFactory
argument_list|()
operator|.
name|createRepresentation
argument_list|(
name|Cache
operator|.
name|BASE_CONFIGURATION_URI
argument_list|)
decl_stmt|;
name|writeFieldConfig
argument_list|(
name|config
argument_list|,
name|baseMapper
argument_list|)
expr_stmt|;
name|yard
operator|.
name|store
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Stores the current configuration used for caching documents back to the      * {@link Yard}. This configuration is present in the  {@link #additionalMapper}).      * If this field is<code>null</code> than any existing configuration is      * removed form the index.      * @throws YardException on any error while changing the configuration in the      * yard.      * @throws IllegalArgumentException if<code>null</code> is parsed as {@link Yard}.      */
specifier|protected
specifier|static
name|void
name|storeAdditionalMappingsConfiguration
parameter_list|(
name|Yard
name|yard
parameter_list|,
name|FieldMapper
name|additionalMapper
parameter_list|)
throws|throws
name|YardException
throws|,
name|IllegalArgumentException
block|{
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Yard MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|additionalMapper
operator|==
literal|null
condition|)
block|{
name|yard
operator|.
name|remove
argument_list|(
name|Cache
operator|.
name|ADDITIONAL_CONFIGURATION_URI
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Representation
name|config
init|=
name|yard
operator|.
name|getValueFactory
argument_list|()
operator|.
name|createRepresentation
argument_list|(
name|Cache
operator|.
name|ADDITIONAL_CONFIGURATION_URI
argument_list|)
decl_stmt|;
name|writeFieldConfig
argument_list|(
name|config
argument_list|,
name|additionalMapper
argument_list|)
expr_stmt|;
name|yard
operator|.
name|store
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Serialises all {@link FieldMapping}s of the parsed {@link FieldMapper}      * and stores them in the {@value Cache#FIELD_MAPPING_CONFIG_FIELD} of the      * parsed {@link Representation}      * @param config the representation to store the field mapping configuration      * @param mapper the field mapper with the configuration to store      */
specifier|private
specifier|static
name|void
name|writeFieldConfig
parameter_list|(
name|Representation
name|config
parameter_list|,
name|FieldMapper
name|mapper
parameter_list|)
block|{
name|StringBuilder
name|builder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|FieldMapping
name|mapping
range|:
name|mapper
operator|.
name|getMappings
argument_list|()
control|)
block|{
name|builder
operator|.
name|append
argument_list|(
name|FieldMappingUtils
operator|.
name|serialiseFieldMapping
argument_list|(
name|mapping
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
name|config
operator|.
name|set
argument_list|(
name|Cache
operator|.
name|FIELD_MAPPING_CONFIG_FIELD
argument_list|,
name|builder
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

