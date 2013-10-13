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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_comment
comment|/**  * The {@link RuleAdapterManager} allows to manage rule adapters.<br/>  * A rule adapter is able to adapt a {@link Recipe} to an external representation, e.g., Jena rules, SPARQL,  * Clerezza, etc...  *   * @author anuzzolese  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RuleAdapterManager
block|{
comment|/**      * It adapts the {@link Adaptable} object to the class provided as second parameter.      *       * @param<AdaptedTo>      * @param adaptable      *            {The object that we want to adapt, e.g., a Recipe}      * @param adaptedToType      *            {The object that we want in output}      * @return      * @throws UnavailableRuleObjectException      */
parameter_list|<
name|AdaptedTo
parameter_list|>
name|RuleAdapter
name|getAdapter
parameter_list|(
name|Adaptable
name|adaptable
parameter_list|,
name|Class
argument_list|<
name|AdaptedTo
argument_list|>
name|adaptedToType
parameter_list|)
throws|throws
name|UnavailableRuleObjectException
function_decl|;
comment|/**      * It returns the list of available rule adapters.      *       * @return the list of available adapters      */
name|List
argument_list|<
name|RuleAdapter
argument_list|>
name|listRuleAdapters
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

