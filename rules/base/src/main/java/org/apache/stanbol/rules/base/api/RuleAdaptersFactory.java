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
comment|/**  * The rule adapter factory allows to add, get, register and remove rule adapters ({@link RuleAdapter}).  *   * @author anuzzolese  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RuleAdaptersFactory
block|{
comment|/**      * It gets the list of available registered adapters.      *       * @return the list of {@link RuleAdapter} objects available      */
name|List
argument_list|<
name|RuleAdapter
argument_list|>
name|listRuleAdapters
parameter_list|()
function_decl|;
comment|/**      * It gets the registered rule adapter able to adapt object that are instances of the class      *<code>type</code>      *       * @param type      *            the class that the adpter accepts      * @return the right rule adpater able to adapt instances of the class<code>type</code>      * @throws UnavailableRuleObjectException      */
name|RuleAdapter
name|getRuleAdapter
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|)
throws|throws
name|UnavailableRuleObjectException
function_decl|;
comment|/**      * It adds a new {@link RuleAdapter} instance.      *       * @param ruleAdapter      *            the {@link RuleAdapter} instance      * @throws UnavailableRuleObjectException      */
name|void
name|addRuleAdapter
parameter_list|(
name|RuleAdapter
name|ruleAdapter
parameter_list|)
throws|throws
name|UnavailableRuleObjectException
function_decl|;
comment|/**      * It removes a new {@link RuleAdapter} instance from the list of registered adapters.      *       * @param ruleAdapter      *            the {@link RuleAdapter} instance to be removed      * @throws UnavailableRuleObjectException      */
name|void
name|removeRuleAdapter
parameter_list|(
name|RuleAdapter
name|ruleAdapter
parameter_list|)
throws|throws
name|UnavailableRuleObjectException
function_decl|;
block|}
end_interface

end_unit

