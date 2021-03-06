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
name|io
operator|.
name|InputStream
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
name|query
operator|.
name|FieldQuery
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
name|query
operator|.
name|QueryResultList
import|;
end_import

begin_interface
specifier|public
interface|interface
name|SiteManager
block|{
comment|/**      * Returns if a site with the parsed id is referenced      * @param baseUri the base URI      * @return<code>true</code> if a site with the parsed ID is present.      * Otherwise<code>false</code>.      */
name|boolean
name|isReferred
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/**      * Getter for the referenced site based on the id      * @param baseUri the base URI of the referred Site      * @return the {@link Site} or<code>null</code> if no site is      * present for the parsed base ID.      */
name|Site
name|getSite
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/**      * Getter for Sites that manages entities with the given ID. A Site can      * define a list of prefixes of Entities ID it manages. This method can      * be used to retrieve all the Site that may be able to dereference the      * parsed entity id      * @param entityUri the ID of the entity      * @return A list of referenced sites that may manage the entity in question.      */
name|Collection
argument_list|<
name|Site
argument_list|>
name|getSitesByEntityPrefix
parameter_list|(
name|String
name|entityUri
parameter_list|)
function_decl|;
comment|/**      * Getter for the Entity referenced by the parsed ID. This method will search      * all referenced sites      * @param id the id of the entity      * @return the Sign or<code>null</code> if not found      */
name|Entity
name|getEntity
parameter_list|(
name|String
name|reference
parameter_list|)
function_decl|;
comment|/**      * Returns the Entities that confirm to the parsed Query      * @param query the query      * @return the id's of Entities      */
name|QueryResultList
argument_list|<
name|Entity
argument_list|>
name|findEntities
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
function_decl|;
comment|/**      * Searches for Entities based on the parsed query and returns representations      * including the selected fields and filtered values      * @param query The query      * @return The representations including selected fields/values      */
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|find
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
function_decl|;
comment|/**      * Searches for Entities based on the parsed query and returns the ids.      * @param query The query      * @return the ids of the selected entities      */
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|findIds
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
function_decl|;
comment|/**      * Getter for the content of the entity      * @param entity the id of the entity      * @param contentType the content type      * @return the content as {@link InputStream} or<code>null</code> if no      * entity with this ID is known by the Entityhub or no representation for       * the requested entity is available for the parsed content type      */
name|InputStream
name|getContent
parameter_list|(
name|String
name|entity
parameter_list|,
name|String
name|contentType
parameter_list|)
function_decl|;
comment|/**      * Getter for the Id's of all active referenced sites      * @return Unmodifiable collections of the id#s of all referenced sites      */
name|Collection
argument_list|<
name|String
argument_list|>
name|getSiteIds
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

