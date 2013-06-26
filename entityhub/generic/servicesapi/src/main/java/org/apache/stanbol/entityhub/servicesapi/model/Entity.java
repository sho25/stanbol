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
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
comment|/**  * A Sign links three things together  *<ol>  *<li>the<b>signifier</b> (ID) used to identify the sign  *<li>the<b>description</b> (Representation) for the signified entity  *<li>the<b>organisation</b> (Site) that provides this description  *</ol>  *   * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Entity
block|{
comment|/**      * The id (signifier) of this Entity.      * @return the id      */
name|String
name|getId
parameter_list|()
function_decl|;
comment|/**      * Property used to link the site managing this entity      */
name|String
name|ENTITY_SITE
init|=
name|RdfResourceEnum
operator|.
name|site
operator|.
name|getUri
argument_list|()
decl_stmt|;
comment|/**      * Getter for the id of the referenced Site that defines/manages this sign.<br>      * Note that the Entityhub allows that different referenced Sites      * provide representations for the same id ({@link Entity#getId()}).      * Therefore there may be different entity instances of {@link Entity} with      * the same id but different representations.<br>      * In other word different referenced Sites may manage representations by      * using the same id.<br>      * Note also, that the Entityhub assumes that all such representations      * are equivalent and interchangeable. Therefore Methods that searches for      * Entities on different Sites will return the first hit without searching      * for any others.      * @return the site of this Sign      */
name|String
name|getSite
parameter_list|()
function_decl|;
comment|/**      * Getter for the {@link Representation} of that sign as defined/managed by the site      * The {@link Representation#getId() id} of this {@link Representation} MUST BE the      * same as the {@link #getId() id} if the Entity.      * @return the representation      */
name|Representation
name|getRepresentation
parameter_list|()
function_decl|;
comment|/**      * Getter for the meta data about the representation of this Entity.      * The {@link Representation#getId() id} of this {@link Representation} MUST NOT BE       * the same as the {@link #getId() id} if the Entity.      * @return the meta data      */
name|Representation
name|getMetadata
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

