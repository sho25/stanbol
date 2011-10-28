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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
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
name|execution
operator|.
name|DocumentResource
import|;
end_import

begin_comment
comment|/**  * This interface defines the structure of a search result returned by  * {@link Search#search(String[], String, List, Map)}. All results of a search operation are encapsulated.  *   * @author anil.sinaci  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SearchResult
block|{
comment|/**      * Returns a list of IDs of the documents found by the search operation in sorted order. The results are      * sorted according to their scores assigned within the search operation.      *       * @return A list of IDs      */
name|List
argument_list|<
name|String
argument_list|>
name|getDocumentIDs
parameter_list|()
function_decl|;
comment|/**      * Returns a list of {@link DocumentResource}s found by the search operation in sorted order. The results      * are sorted according to their scores assigned within the search operation.      *       * @return A list of {@link DocumentResource}      */
name|List
argument_list|<
name|DocumentResource
argument_list|>
name|getDocuments
parameter_list|()
function_decl|;
comment|/**      * Returns the facets generated as a result of the search operations. Each search result has its own      * facets.      *       * @return A map of<code>property:[value1,value2]</code> pairs.      */
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|getFacets
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

