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
name|commons
operator|.
name|semanticindex
operator|.
name|index
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
name|osgi
operator|.
name|framework
operator|.
name|Constants
import|;
end_import

begin_comment
comment|/**  * Provides methods to access to managed {@link SemanticIndex}es by different means such name,  * {@link EndpointTypeEnum} or both.  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SemanticIndexManager
block|{
comment|/**      * Retrieves the {@link SemanticIndex} instance with the given name and having highest      * {@link Constants#SERVICE_RANKING} value.      *       * @param name      *            Name of the {@link SemanticIndex} to be retrieved      * @return the {@link SemanticIndex} instance with the given name if there is any,       *         otherwise {@code null}. In case several {@link SemanticIndex}s would confirm      *         to the parsed requirements the one with the higest {@link Constants#SERVICE_RANKING ranking}      *         is returned.      * @throws IndexManagementException      */
name|SemanticIndex
argument_list|<
name|?
argument_list|>
name|getIndex
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|IndexManagementException
function_decl|;
comment|/**      * Retrieves the {@link SemanticIndex} instances with the given name.      *       * @param name      *            Name of the {@link SemanticIndex}es to be retrieved      * @return the {@link SemanticIndex}es with the given name if there is any,       *         otherwise an empty list.Returned SemanticIndexes are sorted by      *         their {@link Constants#SERVICE_RANKING rankings}.      * @throws IndexManagementException      */
name|List
argument_list|<
name|SemanticIndex
argument_list|<
name|?
argument_list|>
argument_list|>
name|getIndexes
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|IndexManagementException
function_decl|;
comment|/**      * Retrieves the {@link SemanticIndex} instance with the given {@link EndpointTypeEnum} and having highest      * {@link Constants#SERVICE_RANKING} value.      *       * @param endpointType      *            The name of the endpoint type that need to be supported by the      *            returned SemanticIndexes. See the {@link EndpointTypeEnum} for      *            well known RESTfull endpoint types. to search for Java endpoints      *            use {@link Class#getName()}.      * @return the {@link SemanticIndex} instance with the given {@link EndpointTypeEnum} if there is any,      *         otherwise {@code null}. In case several {@link SemanticIndex}s would confirm      *         to the parsed requirements the one with the higest {@link Constants#SERVICE_RANKING ranking}      *         is returned.      * @throws IndexManagementException      */
name|SemanticIndex
argument_list|<
name|?
argument_list|>
name|getIndexByEndpointType
parameter_list|(
name|String
name|endpointType
parameter_list|)
throws|throws
name|IndexManagementException
function_decl|;
comment|/**      * Retrieves the {@link SemanticIndex}es with the given {@link EndpointTypeEnum}.      *       * @param endpointType      *            The name of the endpoint type that need to be supported by the      *            returned SemanticIndexes. See the {@link EndpointTypeEnum} for      *            well known RESTfull endpoint types. to search for Java endpoints      *            use {@link Class#getName()}.      * @return the {@link SemanticIndex}es instances with the given {@link EndpointTypeEnum} if there is any,      *         otherwise an empty list. Returned SemanticIndexes are sorted by      *         their {@link Constants#SERVICE_RANKING rankings}.      * @throws IndexManagementException      */
name|List
argument_list|<
name|SemanticIndex
argument_list|<
name|?
argument_list|>
argument_list|>
name|getIndexesByEndpointType
parameter_list|(
name|String
name|endpointType
parameter_list|)
throws|throws
name|IndexManagementException
function_decl|;
comment|/**      * Retrieves the {@link SemanticIndex} instance with the given name, {@link EndpointTypeEnum} and highest      * {@link Constants#SERVICE_RANKING} value.      *       * @param name      *            Name of the {@link SemanticIndex}es to be retrieved.      *<code>null</code> is used as wildcard      * @param endpointType      *            The name of the endpoint type that need to be supported by the      *            returned SemanticIndexes. See the {@link EndpointTypeEnum} for      *            well known RESTfull endpoint types. to search for Java endpoints      *            use {@link Class#getName()}.<code>null</code> is used as wildcard      * @return the {@link SemanticIndex} instance with the given name, {@link EndpointTypeEnum} if there is any,      *         otherwise {@code null}. In case several {@link SemanticIndex}s would confirm      *         to the parsed requirements the one with the higest {@link Constants#SERVICE_RANKING ranking}      *         is returned.      * @throws IndexManagementException      */
name|SemanticIndex
argument_list|<
name|?
argument_list|>
name|getIndex
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|endpointType
parameter_list|)
throws|throws
name|IndexManagementException
function_decl|;
comment|/**      * Retrieves the {@link SemanticIndex}es instance with the given name and {@link EndpointTypeEnum}.      *       * @param name      *            Name of the {@link SemanticIndex}es to be retrieved.      *<code>null</code> is used as wildcard      * @param endpointType      *            The name of the endpoint type that need to be supported by the      *            returned SemanticIndexes. See the {@link EndpointTypeEnum} for      *            well known endpoint types.<code>null</code> is used as      *            wildcard      * @return the {@link SemanticIndex} instance with the given name and {@link EndpointTypeEnum} if there is      *         any, otherwise an empty list. Returned SemanticIndexes are sorted by      *         their {@link Constants#SERVICE_RANKING rankings}.      * @throws IndexManagementException      */
name|List
argument_list|<
name|SemanticIndex
argument_list|<
name|?
argument_list|>
argument_list|>
name|getIndexes
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|endpointType
parameter_list|)
throws|throws
name|IndexManagementException
function_decl|;
block|}
end_interface

end_unit

