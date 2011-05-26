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
package|;
end_package

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
name|yard
operator|.
name|Yard
import|;
end_import

begin_comment
comment|/**  * Provides the Configuration needed by the {@link Entityhub}.<p>  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntityhubConfiguration
block|{
comment|/**      * The key used to configure the ID of the entity hub      */
name|String
name|ID
init|=
literal|"org.apache.stanbol.entityhub.id"
decl_stmt|;
comment|/**      * The ID of the Entityhub. This ID is used as origin (sign site) for all symbols      * and mapped entities created by the Entityhub      * @return the ID of the Entityhub      */
name|String
name|getID
parameter_list|()
function_decl|;
comment|/**      * The property used to configure the prefix used for {@link Symbol}s and      * {@link EntityMapping}s created by the Entityhub      */
name|String
name|PREFIX
init|=
literal|"org.apache.stanbol.entityhub.prefix"
decl_stmt|;
comment|/**      * Getter for the Prefix to be used for all {@link Symbol}s and {@link EntityMapping}s      * created by the {@link Entityhub}      * @return The prefix for {@link Symbol}s and {@link EntityMapping}s      */
name|String
name|getEntityhubPrefix
parameter_list|()
function_decl|;
comment|/**      * The key used to configure the name of the entity hub      */
name|String
name|NAME
init|=
literal|"org.apache.stanbol.entityhub.name"
decl_stmt|;
comment|/**      * The human readable name of this entity hub instance. Typically used as label      * in addition/instead of the ID.      * @return the Name (or the ID in case no name is defined)      */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * The key used to configure the description of the entity hub      */
name|String
name|DESCRIPTION
init|=
literal|"org.apache.stanbol.entityhub.description"
decl_stmt|;
comment|/**      * The human readable description to provide some background information about      * this entity hub instance.      * @return the description or<code>null</code> if none is defined/configured.      */
name|String
name|getDescription
parameter_list|()
function_decl|;
comment|/**      * The property used to configure the id of the {@link Yard} used to store      * the data of the {@link Entityhub}      */
name|String
name|ENTITYHUB_YARD_ID
init|=
literal|"org.apache.stanbol.entityhub.yard.entityhubYardId"
decl_stmt|;
comment|/**      * The default ID for the {@link Yard} used for the {@link Entityhub}      */
name|String
name|DEFAULT_ENTITYHUB_YARD_ID
init|=
literal|"entityhubYard"
decl_stmt|;
comment|/**      * This is the ID of the {@link Yard} used by the {@link Entityhub} to store       * its data      * @return the entity hub yard id      */
name|String
name|getEntityhubYardId
parameter_list|()
function_decl|;
comment|/**      * The property used to configure the field mappings for the {@link Entityhub}      */
name|String
name|FIELD_MAPPINGS
init|=
literal|"org.apache.stanbol.entityhub.mapping.entityhub"
decl_stmt|;
comment|/**      * Getter for the FieldMapping configuration of the {@link Entityhub}.       * These Mappings are used for every {@link ReferencedSite} of the       * {@link Entityhub}.<br>      * Note that {@link FieldMapping#parseFieldMapping(String)} is used to      * parsed the values returned by this Method      * @return the configured mappings for the {@link Entityhub}      */
name|Collection
argument_list|<
name|String
argument_list|>
name|getFieldMappingConfig
parameter_list|()
function_decl|;
comment|/**      * The property used to configure the initial state for new {@link EntityMapping}s      */
name|String
name|DEFAULT_MAPPING_STATE
init|=
literal|"org.apache.stanbol.entityhub.defaultMappingState"
decl_stmt|;
comment|/**      * The initial (default) state for new {@link EntityMapping}s      * @return the default state for new {@link EntityMapping}s      */
name|MappingState
name|getDefaultMappingState
parameter_list|()
function_decl|;
comment|/**      * The property used to configure the initial state for new {@link Symbol}s      */
name|String
name|DEFAULT_SYMBOL_STATE
init|=
literal|"org.apache.stanbol.entityhub.defaultSymbolState"
decl_stmt|;
comment|/**      * The initial (default) state for new {@link Symbol}s      * @return the default state for new {@link Symbol}s      */
name|ManagedEntityState
name|getDefaultManagedEntityState
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

