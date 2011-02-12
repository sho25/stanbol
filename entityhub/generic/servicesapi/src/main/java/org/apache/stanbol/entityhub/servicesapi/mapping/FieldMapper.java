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
name|mapping
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
name|Representation
import|;
end_import

begin_interface
specifier|public
interface|interface
name|FieldMapper
extends|extends
name|Cloneable
block|{
comment|/**      * Adds a FieldMapping.      */
name|void
name|addMapping
parameter_list|(
name|FieldMapping
name|mapping
parameter_list|)
function_decl|;
comment|//    private static String getPrefix(String fieldPattern){
comment|//        return fieldPattern.split("[\\?\\*]")[0];
comment|//    }
comment|/**      * Removes a FieldMapping.      * @param mapping the Mapping to remove      */
name|void
name|removeFieldMapping
parameter_list|(
name|FieldMapping
name|mapping
parameter_list|)
function_decl|;
comment|/**      * Uses the state of the source representation and the configured mappings      * to update the state of the target {@link Representation}. The state of the      * source {@link Representation} is not modified. Existing values in the      * target are not removed nor modified. If the same instance is parsed as      * source and target representation, fields created by the mapping process      * are NOT used as source fields for further mappings.      * @param source the source for the mapping process      * @param target the target for the processed mappings (can be the same as source)      * @return the {@link Representation} parsed as target.      * TODO: This Method should return a MappingReport, that can be stored with      * the {@link EntityMapping}. However the MappingActivity functionality is      * not yet designed/implemented!      */
name|Representation
name|applyMappings
parameter_list|(
name|Representation
name|source
parameter_list|,
name|Representation
name|target
parameter_list|)
function_decl|;
comment|/**      * Getter for the unmodifiable collection of all mappings      * @return the configured mappings      */
name|Collection
argument_list|<
name|FieldMapping
argument_list|>
name|getMappings
parameter_list|()
function_decl|;
comment|/**      * Creates a clone of this FieldMapper instance with shallow copies of the      * {@link FieldMapping} instances      * @return the clone      */
name|FieldMapper
name|clone
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

