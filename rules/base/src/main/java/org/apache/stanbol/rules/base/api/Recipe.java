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
name|RuleList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
import|;
end_import

begin_comment
comment|/**  * It represents a Recipe object.<br/>  * A recipe is a set of rules that share for some reason a same functionality or need.  *   * @author anuzzolese  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|Recipe
extends|extends
name|Adaptable
block|{
comment|/**      * Get the rule of the recipe identified by the rule name. The rule is returned as a {@link Rule} object.      *       * @param ruleName      *            {@link String}      * @return the object that represents a {@link Rule}      */
name|Rule
name|getRule
parameter_list|(
name|String
name|ruleName
parameter_list|)
throws|throws
name|NoSuchRuleInRecipeException
function_decl|;
comment|/**      * Get the rule of the recipe identified by the rule ID. The rule is returned as a {@link Rule} object.      *       * @param ruleID      *            {@link UriRef}      * @return the object that represents a {@link Rule}      */
name|Rule
name|getRule
parameter_list|(
name|UriRef
name|ruleID
parameter_list|)
throws|throws
name|NoSuchRuleInRecipeException
function_decl|;
comment|/**      * Get the list of the {@link Rule} contained in the recipe.      *       * @return the {@link RuleList}.      */
name|RuleList
name|getRuleList
parameter_list|()
function_decl|;
comment|/**      * Get the list of rule IDs contained in the recipe.      *       * @return the List of {@link UriRef}.      */
name|List
argument_list|<
name|UriRef
argument_list|>
name|listRuleIDs
parameter_list|()
function_decl|;
comment|/**      * Get the list of rule names contained in the recipe.      *       * @return the List of {@link UriRef}.      */
name|List
argument_list|<
name|String
argument_list|>
name|listRuleNames
parameter_list|()
function_decl|;
comment|/**      * Get the ID of the recipe in the {@link RuleStore}.      *       * @return the {@link UriRef} expressing the recipe's ID.      */
name|UriRef
name|getRecipeID
parameter_list|()
function_decl|;
comment|/**      * Get the description about the recipe.      *       * @return the {@link String} about the recipe's description.      */
name|String
name|getRecipeDescription
parameter_list|()
function_decl|;
comment|/**      * Add a Rule to the recipe. This operation does not effect a change on recipe in the rule store, but only      * in the in-memory representation of a specific recipe. To permanently change the recipe use      * {@link RuleStore#addRuleToRecipe(IRI, String)}.      *       * @param rRule      *            the {@link Rule}.      */
name|void
name|addRule
parameter_list|(
name|Rule
name|rule
parameter_list|)
function_decl|;
comment|/**      * Remove a Rule from the recipe. This operation does not effect a change on recipe in the rule store, but      * only in the in-memory representation of a specific recipe. To permanently change the recipe use      * {@link RuleStore#addRuleToRecipe(IRI, String)}.      *       * @param rRule      *            the {@link Rule}.      */
name|void
name|removeRule
parameter_list|(
name|Rule
name|rule
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

