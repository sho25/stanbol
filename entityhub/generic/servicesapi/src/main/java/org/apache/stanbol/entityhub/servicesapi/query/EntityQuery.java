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
name|query
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
name|rdf
operator|.
name|RdfResourceEnum
import|;
end_import

begin_comment
comment|/**  * A simple Search service that is intended for Entity lookup based on  *<ul>  *<li> Tags/Keywords by parsing the exact name  *<li> Prefix to provide suggestions for users that type some characters into  * a search field.  *</ul><p>  * Results of such queries will be {@link Representation} with the  * following fields  *<ul>  *<li> id (required)  *<li> name (required) by using field {@link RdfResourceEnum#name()}  *<li> rank (required) by using field  *<li> description (optional)  *<li> image (optional)  *<li> type (optional)  *</ul><p>  * Specific implementations may add additional fields. All fields MUST BE part  * of the list returned by {@link QueryResultList#getSelectedFields()}.  * TODO: define the URIs of the results  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntityQuery
extends|extends
name|Query
block|{
comment|/**      * Getter for the name/name fragment of the entity to search      * @return the name      */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * If Prefix is enabled, the parsed name is interpreted as prefix.      * The regex representation would be<code>"^"+getName()+".*$"</code>      * @return      */
name|boolean
name|isPrefix
parameter_list|()
function_decl|;
comment|/**      * The type of the entity      * TODO: currently symbols define only a required name and description, but      * often it is also the type that is used to filter individuals. Therefore      * it could make sense to also add the type to the properties of symbols      * @return the type of the entities (full name, no wildcard support)      */
name|String
name|getEntityType
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

