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
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|IRI
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|util
operator|.
name|AtomList
import|;
end_import

begin_comment
comment|/**  * A Rule is a Java object that represent a rule in Stanbol.  *   * @author anuzzolese  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|Rule
extends|extends
name|Adaptable
block|{
comment|/**      * Gets the ID of the rule.      *       * @return the {@link IRI} representing the name of the rule.      */
name|IRI
name|getRuleID
parameter_list|()
function_decl|;
comment|/**      * Gets the name of the rule.      *       * @return the {@link String} representing the name of the rule.      */
name|String
name|getRuleName
parameter_list|()
function_decl|;
comment|/**      * It allows to return the textual description of the rule.      *       * @return the {@link String} containing the description of the rule.      */
name|String
name|getDescription
parameter_list|()
function_decl|;
comment|/**      * It sets the description of the rule.      *       * @param description      *            {@link String}      */
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
function_decl|;
comment|/**      * Sets the rule's name      *       * @param ruleName      *            {@link String}      */
name|void
name|setRuleName
parameter_list|(
name|String
name|ruleName
parameter_list|)
function_decl|;
comment|/**      * Sets the rule expressed in Rule syntax      *       * @param rule      *            {@link String}      */
name|void
name|setRule
parameter_list|(
name|String
name|rule
parameter_list|)
function_decl|;
comment|/**      * Rules are composed by an antecedent (body) and a consequent (head). This method returnn the consequent      * expressed as a list of its atoms ({@link AtomList}).      *       * @return the {@link AtomList} of the consequent's atoms.      */
name|AtomList
name|getHead
parameter_list|()
function_decl|;
comment|/**      * Rules are composed by an antecedent (body) and a consequent (head). This method returnn the antecedent      * expressed as a list of its atoms ({@link AtomList}).      *       * @return the {@link AtomList} of the antecedent's atoms.      */
name|AtomList
name|getBody
parameter_list|()
function_decl|;
comment|/**      * A rule is always a member of recipe. This method returns the recipe in which the rule exists.      *       * @return the recipe {@link Recipe}      */
name|Recipe
name|getRecipe
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

