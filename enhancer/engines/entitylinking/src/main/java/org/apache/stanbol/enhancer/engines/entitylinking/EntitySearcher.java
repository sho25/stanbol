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
name|entitylinking
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
name|Map
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Resource
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
import|;
end_import

begin_comment
comment|/**  * Interface used to search for Entities (e.g. as defined by a Controlled  * Vocabulary) Different implementations of this interface allow to use   * different sources.<p>  *<b>NOTE:</b> Implementations that support entity rankings SHOULD provide an  * own {@link Entity} implementation that overrides the default   * {@link Entity#getEntityRanking()} implementation.  * @author Rupert Westenthaler  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntitySearcher
block|{
comment|/**      * Lookup Entities for the parsed parameters.      * @param field the field used to search for values in the parsed languages      * @param selectedFields A set of fields that need to be included within the       * returned {@link Representation}. The parsed field needs also to be included      * even if missing in this set. If<code>null</code> only the field needs      * to be included. Other fields MAY also be included.      * @param search the tokens to search for. MUST NOT be<code>null</code>      * @param languages the languages to include in the search       * @param limit The maximum number of resutls of<code>null</code> to use the default      * @return the Entities found for the specified query containing information for      * all selected fields      * @throws EntitySearcherException An exception while searching for concepts      * @throws IllegalArgumentException if the parsed field is<code>null</code>;      * the list with the search terms is<code>null</code> or empty;      */
name|Collection
argument_list|<
name|?
extends|extends
name|Entity
argument_list|>
name|lookup
parameter_list|(
name|UriRef
name|field
parameter_list|,
name|Set
argument_list|<
name|UriRef
argument_list|>
name|selectedFields
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|search
parameter_list|,
name|String
index|[]
name|languages
parameter_list|,
name|Integer
name|limit
parameter_list|)
throws|throws
name|EntitySearcherException
function_decl|;
comment|/**      * Lookup an Entity of the linked vocabulary by the id.      * @param id the id      * @param selectedFields A set of fields that need to be included within the       * returned {@link Representation}. Other fields MAY be also included.      * @return the concept or<code>null</code> if not found      * @throws EntitySearcherException on any error while dereferencing the      * Entity with the parsed Id      * @throws IllegalArgumentException if the parsed id is<code>null</code>      */
name|Entity
name|get
parameter_list|(
name|UriRef
name|id
parameter_list|,
name|Set
argument_list|<
name|UriRef
argument_list|>
name|selectedFields
parameter_list|)
throws|throws
name|EntitySearcherException
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
comment|/**      * Information in this map are added to each      *<code>fise:EntityAnnotation</code> linking to      * an entity returned by this EntitySearcher.         * @return the predicate[1..1] -> predicate[1..*] tuples added to any       * 'fise:EntityAnnotation'.      */
name|Map
argument_list|<
name|UriRef
argument_list|,
name|Collection
argument_list|<
name|Resource
argument_list|>
argument_list|>
name|getOriginInformation
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

