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
name|io
operator|.
name|InputStream
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
name|TripleCollection
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
name|RecipeList
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

begin_comment
comment|/**  * The RuleStore provides features in order to manage the persistence of recipes and rules.<br/>  * Through the RuleStore recipes and rules can be:  *<ul>  *<li>stored  *<li>accessed  *<li>modified  *<li>removed  *</ul>  *   * @author anuzzolese  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RuleStore
block|{
comment|/**      * The key used to configure default location where to store the index graph      */
name|String
name|RECIPE_INDEX_LOCATION
init|=
literal|"org.apache.stanbol.rules.base.recipe_index"
decl_stmt|;
comment|/**      * Create a new recipe in the Rule Store.<br/>      * The recipe is identified and described by the first and the second parameter respectively.<br/>      * The description is optional and a null value can be in case passed.<br/>      * This method returns a {@link Recipe} object, which is the representation of a recipe of rules in      * Stanbol.<br/>      * If some error occurs during the creation of the recipe a {@link RecipeConstructionException} is thrown.      *       * @param recipeID      *            {@link UriRef}      * @param recipeDescription      *            {@link String}      * @return a {@link Recipe}      * @throws AlreadyExistingRecipeException      */
name|Recipe
name|createRecipe
parameter_list|(
name|UriRef
name|recipeID
parameter_list|,
name|String
name|recipeDescription
parameter_list|)
throws|throws
name|AlreadyExistingRecipeException
function_decl|;
comment|/**      * The method adds a new rule passed as second parameter to a recipe passed as first parameter.<br/>      * The descriptions contains some text about the role of the rule in the recipe. It can be      *<code>null</code>.<br/>      * It returns the recipe object updated with the new rule.<br/>      * The change consisting in adding the new rule is performed permanently in the store.      *       * @param recipe      *            {@link Recipe}      * @param rule      *            {@link Rule}      * @param description      *            {@link String}      * @return {@link Recipe}      */
name|Recipe
name|addRuleToRecipe
parameter_list|(
name|Recipe
name|recipe
parameter_list|,
name|Rule
name|rule
parameter_list|,
name|String
name|description
parameter_list|)
function_decl|;
comment|/**      * The method adds one or more rules passed as second parameter to a recipe passed as first parameter.<br/>      * The rule or the rules consist of a string that expresses the rules in Stanbol syntax.<br/>      * The descriptions contains some text about the role of the rules in the recipe. The description can be      * used in order to find this set of rule. It can be<code>null</code>.<br/>      * It returns the recipe object updated with the new rule(s).<br/>      * The change consisting in adding the new rule(s) is performed permanently in the store.      *       * @param recipe      *            {@link Recipe}      * @param rule      *            {@link Rule}      * @param description      *            {@link String}      * @return {@link Recipe}      */
name|Recipe
name|addRulesToRecipe
parameter_list|(
name|Recipe
name|recipe
parameter_list|,
name|String
name|rules
parameter_list|,
name|String
name|description
parameter_list|)
function_decl|;
comment|/**      * The method adds one or more rules passed as second parameter to a recipe passed as first parameter.<br/>      * The rule or the rules consist of an {@link InputStream} that provides rules expressed in Stanbol      * syntax.<br/>      * The descriptions contains some text about the role of the rules in the recipe. The description can be      * used in order to find this set of rule. It can be<code>null</code>.<br/>      * It returns the recipe object updated with the new rule(s).<br/>      * The change consisting in adding the new rule(s) is performed permanently in the store.      *       * @param recipe      *            {@link Recipe}      * @param rule      *            {@link Rule}      * @param description      *            {@link String}      * @return {@link Recipe}      */
name|Recipe
name|addRulesToRecipe
parameter_list|(
name|Recipe
name|recipe
parameter_list|,
name|InputStream
name|rules
parameter_list|,
name|String
name|description
parameter_list|)
function_decl|;
comment|/**      * It returns the rule in a recipe selected by name.<br/>      *       * @param recipe      *            {@link Recipe}      * @param ruleName      *            {@link String}      * @return {@link Rule}      * @throws NoSuchRuleInRecipeException      */
name|Rule
name|getRule
parameter_list|(
name|Recipe
name|recipe
parameter_list|,
name|String
name|ruleName
parameter_list|)
throws|throws
name|NoSuchRuleInRecipeException
function_decl|;
comment|/**      * It returns the rule in a recipe selected by ID.<br/>      *       * @param recipe      *            {@link Recipe}      * @param ruleID      *            {@link UriRef}      * @return {@link Rule}      * @throws NoSuchRuleInRecipeException      */
name|Rule
name|getRule
parameter_list|(
name|Recipe
name|recipe
parameter_list|,
name|UriRef
name|ruleID
parameter_list|)
throws|throws
name|NoSuchRuleInRecipeException
function_decl|;
comment|/**      * It returns the set of rules that realize the recipe passed as parameter.      *       * @param recipe      *            {@link Recipe}      * @return {@link RuleList}      */
name|RuleList
name|listRules
parameter_list|(
name|Recipe
name|recipe
parameter_list|)
function_decl|;
comment|/**      * It returns the {@link List} or rules' identifiers ({@link UriRef}).      *       * @param recipe      *            {@link Recipe}      * @return {@link List} of {@link UriRef}      */
name|List
argument_list|<
name|UriRef
argument_list|>
name|listRuleIDs
parameter_list|(
name|Recipe
name|recipe
parameter_list|)
function_decl|;
comment|/**      * It returns the {@link List} of rules' names.      *       * @param recipe      *            {@link Recipe}      * @return {@link List} of {@link String}      */
name|List
argument_list|<
name|String
argument_list|>
name|listRuleNames
parameter_list|(
name|Recipe
name|recipe
parameter_list|)
function_decl|;
comment|/**      * It returns a {@link Recipe} object identified in the store by the recipe's identifier provided as      * parameter.<br/>      * If the recipe's identifier does not exist in the store a {@link NoSuchRecipeException} is thrown.<br/>      * If some error occurs while generating the recipe object a {@link RecipeConstructionException} is      * thrown.      *       * @param recipeID      *            {@link UriRef}      * @return {@link Recipe}      * @throws NoSuchRecipeException      * @throws RecipeConstructionException      */
name|Recipe
name|getRecipe
parameter_list|(
name|UriRef
name|recipeID
parameter_list|)
throws|throws
name|NoSuchRecipeException
throws|,
name|RecipeConstructionException
function_decl|;
comment|/**      * It returns a list of existing recipes' IDs in the store.<br/>      *       * @return {@link List} of {@link UriRef}      */
name|List
argument_list|<
name|UriRef
argument_list|>
name|listRecipeIDs
parameter_list|()
function_decl|;
comment|/**      * It returns the list of exisitng recipes in the RuleStore.<br/>      *       * @return {@link RecipeList}      */
name|RecipeList
name|listRecipes
parameter_list|()
throws|throws
name|NoSuchRecipeException
throws|,
name|RecipeConstructionException
function_decl|;
comment|/**      * It removes the recipe identified by the ID passed as parameter.<br/>      * If any problem occurs during the elimination of the recipe from the store a      * {@link RecipeEliminationException} is thrown.      *       * @param recipeID      *            {@link UriRef}      * @return<code>true</code> if the recipe has been removed, false otherwise.      * @throws RecipeEliminationException      */
name|boolean
name|removeRecipe
parameter_list|(
name|UriRef
name|recipeID
parameter_list|)
throws|throws
name|RecipeEliminationException
function_decl|;
comment|/**      * It removes the recipe passed as parameter.<br/>      * If any problem occurs during the elimination of the recipe from the store a      * {@link RecipeEliminationException} is thrown.      *       * @param recipe      *            {@link Recipe}      * @return<code>true</code> if the recipe has been removed, false otherwise.      * @throws RecipeEliminationException      */
name|boolean
name|removeRecipe
parameter_list|(
name|Recipe
name|recipe
parameter_list|)
throws|throws
name|RecipeEliminationException
function_decl|;
comment|/**      * It removes the rule passed as second parameter from the recipe passed as first parameter.<br/>      *       * @param recipe      *            {@link Recipe}      * @param rule      *            {@link Rule}      * @return<code>true</code> if the recipe has been removed, false otherwise.      */
name|Recipe
name|removeRule
parameter_list|(
name|Recipe
name|recipe
parameter_list|,
name|Rule
name|rule
parameter_list|)
function_decl|;
comment|/**      * It allows to export recipes as Clerezza's {@link TripleCollection} objects.      *       * @param recipe      * @return      * @throws NoSuchRecipeException      */
name|TripleCollection
name|exportRecipe
parameter_list|(
name|Recipe
name|recipe
parameter_list|)
throws|throws
name|NoSuchRecipeException
function_decl|;
comment|/**      * Find the set of recipes in the rule store whose description matches the<code>term</code>      *       * @param term      * @return the list of matching recipes      */
name|RecipeList
name|findRecipesByDescription
parameter_list|(
name|String
name|term
parameter_list|)
function_decl|;
comment|/**      * Find the set of rules in the rule store whose name matches the<code>term</code>      *       * @param term      * @return the list of matching rules      */
name|RuleList
name|findRulesByName
parameter_list|(
name|String
name|term
parameter_list|)
function_decl|;
comment|/**      * Find the set of rules in the rule store whose descriptions matches the<code>term</code>      *       * @param term      * @return the list of matching rules      */
name|RuleList
name|findRulesByDescription
parameter_list|(
name|String
name|term
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

