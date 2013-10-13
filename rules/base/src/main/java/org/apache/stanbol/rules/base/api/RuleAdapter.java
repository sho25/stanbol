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
name|rules
operator|.
name|base
operator|.
name|api
package|;
end_package

begin_comment
comment|/**  *   * This interface provides methods that allow to adapt Stanbol adaptable rule objects to specific classes  * provided as input.  *   *   * @author anuzzolese  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RuleAdapter
block|{
comment|/**      * It asks to the adapter if it is able to adapt the adaptable object passed as first argument to the type      * passed as second argument.      *       * @param<T>      * @param adaptable      *            {@link Adaptable}      * @param type      *            {@link Class<T>}      * @return<code>true</code> if the adapter can work in transforming the adaptable object to the specified      *         type, false otherwise.      */
parameter_list|<
name|T
parameter_list|>
name|boolean
name|canAdaptTo
parameter_list|(
name|Adaptable
name|adaptable
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
function_decl|;
comment|/**      * It returns the object adapted by the adapter.      *       * @param<T>      * @param adaptable      *            {@link Adaptable}      * @param type      *            {@link Class<T>}      * @return<T> the adapted object      * @throws RuleAtomCallExeption      * @throws UnavailableRuleObjectException      * @throws UnsupportedTypeForExportException      */
parameter_list|<
name|T
parameter_list|>
name|T
name|adaptTo
parameter_list|(
name|Adaptable
name|adaptable
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
throws|throws
name|RuleAtomCallExeption
throws|,
name|UnavailableRuleObjectException
throws|,
name|UnsupportedTypeForExportException
function_decl|;
comment|/**      * It return the type that the concrete adapter implementation is able to manage.      *       * @param<T>      * @return the type that the concrete adapter implementation is able to manage {@link Class<T>}      */
parameter_list|<
name|T
parameter_list|>
name|Class
argument_list|<
name|T
argument_list|>
name|getExportClass
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

