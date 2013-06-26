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
name|Representation
import|;
end_import

begin_comment
comment|/**  * Extends ReferencedSite by create/update/delete functionalities  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|ManagedSite
extends|extends
name|Site
block|{
comment|/**      * Stores (create or updates) the parsed representation.       * @param representation the representation to be stored/updated      * @throws ManagedSiteException On any error while performing the operation      * @throws IllegalArgumentException if<code>null</code> is parsed as      * Representation.      */
name|void
name|store
parameter_list|(
name|Representation
name|representation
parameter_list|)
throws|throws
name|ManagedSiteException
function_decl|;
comment|/**      * Stores (create or updates) the parsed representations.       * @param representation the representation to be stored/updated      * @return The updated entity.      * @throws ManagedSiteException On any error while performing the operation      * @throws IllegalArgumentException if<code>null</code> is parsed      */
name|void
name|store
parameter_list|(
name|Iterable
argument_list|<
name|Representation
argument_list|>
name|representation
parameter_list|)
throws|throws
name|ManagedSiteException
function_decl|;
comment|/**      * Deletes the Entity with the parsed id. This will delete the entity      * and all its information including metadata and mappings to other entities      * form the Entityhub. To mark the Entity as removed use       * {@link #setState(String, ManagedEntityState)} with       * {@link ManagedEntityState#removed} as second parameter.      * @param id The id of the Entity to delete      * @throws EntityhubException On any error while performing the operation      * @throws IllegalArgumentException if<code>null</code> or an empty String      * is parsed as id      */
name|void
name|delete
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|ManagedSiteException
function_decl|;
comment|/**      * Deletes all Entities and their Mappings from the Entityhub.      * @throws EntityhubException On any error while performing the operation      */
name|void
name|deleteAll
parameter_list|()
throws|throws
name|ManagedSiteException
function_decl|;
block|}
end_interface

end_unit

