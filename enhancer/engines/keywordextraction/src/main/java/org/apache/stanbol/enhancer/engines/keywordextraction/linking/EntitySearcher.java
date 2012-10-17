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
name|enhancer
operator|.
name|engines
operator|.
name|keywordextraction
operator|.
name|linking
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
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|Entityhub
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
name|query
operator|.
name|TextConstraint
import|;
end_import

begin_comment
comment|/**  * Interface used to search for Entities (e.g. as defined by a Controlled  * Vocabulary) Different implementations of this interface allow to use   * different sources. Typically the {@link Entityhub} or a {@link ReferencedSite}  * will be used as source, but in some cases one might also use in-memory  * implementation.  * @author Rupert Westenthaler  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntitySearcher
block|{
comment|/**      * Lookup Concepts for the parsed strings. Parameters follow the same      * rules as  {@link TextConstraint#TextConstraint(List, String...)}      * @param field the field used to search for values in the parsed languages      * @param includeFields A set of fields that need to be included within the       * returned {@link Representation}. The parsed field needs also to be included      * even if missing in this set. If<code>null</code> only the field needs      * to be included. Other fields MAY also be included.      * @param search the tokens to search for. MUST NOT be<code>null</code>      * @param languages the languages to include in the search       * @return the Representations found for the specified query      * @throws T An exception while searching for concepts      */
name|Collection
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|lookup
parameter_list|(
name|String
name|field
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|includeFields
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|search
parameter_list|,
name|String
modifier|...
name|languages
parameter_list|)
throws|throws
name|IllegalStateException
function_decl|;
comment|/**      * Lookup a concept of the taxonomy by the id.      * @param id the id      * @param includeFields A set of fields that need to be included within the       * returned {@link Representation}. Other fields MAY be also included.      * @return the concept or<code>null</code> if not found      */
name|Representation
name|get
parameter_list|(
name|String
name|id
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|includeFields
parameter_list|)
throws|throws
name|IllegalStateException
function_decl|;
comment|/**      * Returns<code>true</code> if this EntitySearcher can operate without      * dependencies to remote services. This is important because Stanbol can      * be forced to run in offline-mode.      * @return the state      */
name|boolean
name|supportsOfflineMode
parameter_list|()
function_decl|;
comment|/**      * The maximum number of {@link Representation}s returned for {@link #lookup(String, Set, List, String...)}      * queries      * @return the Number or<code>null</code> if not known      */
name|Integer
name|getLimit
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

