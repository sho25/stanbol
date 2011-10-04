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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|execution
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|Search
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|SearchResult
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|engine
operator|.
name|SearchEngine
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|OntModel
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|query
operator|.
name|larq
operator|.
name|IndexLARQ
import|;
end_import

begin_comment
comment|/**  * SearchContext provides any information needed by any {@link SearchEngine}. This is the context of a search  * operation. User ontology (if supplied), search results, relations among the keywords can be accessed within  * {@link SearchContext}. As the search engines operates, the results are stored inside the same context. That  * is, {@link SearchContext} is created at the beginning of a search operation, filled by each  * {@link SearchEngine}. In the end, the results of a search operation can be found in the  * {@link SearchContext}.  *   * @author anil.sinaci  * @author cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SearchContext
extends|extends
name|SearchResult
block|{
comment|/**      * Returns a list of {@link QueryKeyword}s. Each {@link QueryKeyword} is the {@link String} supplied      * through the {@link Search#search(String[])} method.      *       * @return A list of {@link QueryKeyword}.      */
name|List
argument_list|<
name|QueryKeyword
argument_list|>
name|getQueryKeyWords
parameter_list|()
function_decl|;
comment|/**      * Returns the {@link SearchContextFactory} associated with this {@link SearchContextFactory}. The factory      * is used to manipulate the resources in the {@link SearchContext}, i.e. adding new resources.      *       * @return The {@link SearchContextFactory} associated with this {@link SearchContextFactory}.      */
name|SearchContextFactory
name|getFactory
parameter_list|()
function_decl|;
comment|/**      * If the search operation uses an ontologu (if an ontology is provided to the search operation), this      * function returns it.      *       * @return The {@link OntModel} representing the ontology used in the semantic search.      */
name|OntModel
name|getSearchModel
parameter_list|()
function_decl|;
comment|/**      * Returns the list of {@link SearchEngine}s executed within this search operation. The list contains the      * String serializations of the {@link SearchEngine} objects. Default toString() methods are used to get      * the String serializations.      *       * @return A list of {@link SearchEngine}s.      */
name|List
argument_list|<
name|String
argument_list|>
name|getAllowedEngines
parameter_list|()
function_decl|;
comment|/**      * Retrieves the map of constraints (facets) to be applied during the semantic search operation. Each      * constraint limits the search results according to the given list of values for that constraint. For      * example, if the constraints map includes a mapping like "lang":"tr","en", search results will only      * contain documents which include "lang" fields having "tr" or "en" values.      *       * @return A map holding the constraints (facets).      */
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|getConstraints
parameter_list|()
function_decl|;
comment|/**      * If there is an ontology used in the search operation, a Lucene index is built on top of it for keyword      * based searchs on the ontology.      *       * @return The Lucene index built on top of the ontology.      */
name|IndexLARQ
name|getIndex
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

