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

begin_comment
comment|/**  * The interface to represent a keyword and its related resources. Related resources are attached to the  * {@link Keyword} as the {@link SearchEngine}s execute.  *   * @author cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|Keyword
extends|extends
name|Scored
block|{
comment|/**      * Getter function to retrieve the keyword string.      *       * @return The keyword {@link String}      */
name|String
name|getKeyword
parameter_list|()
function_decl|;
comment|/**      * If this keyword is added to the context by means of a {@link QueryKeyword}, this function return that      * {@link QueryKeyword}.      *       * @return The {@link QueryKeyword} which has caused this {@link Keyword} to exist.      */
name|QueryKeyword
name|getRelatedQueryKeyword
parameter_list|()
function_decl|;
comment|/**      * If this {@link Keyword} causes any {@link ClassResource} to be added to the {@link SearchContext}, then      * they are related. This function returns the {@link ClassResource}s, which are added to the      * {@link SearchContext} by means of this {@link Keyword}.      *       * @return A list of {@link ClassResource}s.      */
name|List
argument_list|<
name|ClassResource
argument_list|>
name|getRelatedClassResources
parameter_list|()
function_decl|;
comment|/**      * If this {@link Keyword} causes any {@link IndividualResource} to be added to the {@link SearchContext},      * then they are related. This function returns the {@link IndividualResource}s, which are added to the      * {@link SearchContext} by means of this {@link Keyword}.      *       * @return A list of {@link IndividualResource}s.      */
name|List
argument_list|<
name|IndividualResource
argument_list|>
name|getRelatedIndividualResources
parameter_list|()
function_decl|;
comment|/**      * If this {@link Keyword} causes any {@link DocumentResource} to be added to the {@link SearchContext},      * then they are related. This function returns the {@link DocumentResource}s, which are added to the      * {@link SearchContext} by means of this {@link Keyword}.      *       * @return A list of {@link DocumentResource}s.      */
name|List
argument_list|<
name|DocumentResource
argument_list|>
name|getRelatedDocumentResources
parameter_list|()
function_decl|;
comment|/**      * If this {@link Keyword} causes any {@link ExternalResource} to be added to the {@link SearchContext},      * then they are related. This function returns the {@link ExternalResource}s, which are added to the      * {@link SearchContext} by means of this {@link Keyword}.      *       * @return A list of {@link ExternalResource}s.      */
name|List
argument_list|<
name|ExternalResource
argument_list|>
name|getRelatedExternalResources
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

