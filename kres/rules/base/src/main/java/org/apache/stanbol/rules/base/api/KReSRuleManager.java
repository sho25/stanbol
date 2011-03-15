begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|Set
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
comment|/**  *   * @author andrea.nuzzolese  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|KReSRuleManager
block|{
comment|/** 	 * Adds one or more rules to the rule ontology of KReS. Rules are passed as {@link String} contantining rules in KReSRule format 	 * @param kReSRuleString {@link String} 	 * @return true if the rule is added, false otherwise. 	 */
specifier|public
name|boolean
name|addRules
parameter_list|(
name|String
name|kReSRuleString
parameter_list|)
function_decl|;
comment|/** 	 * Creates a recipe with the specified ID. 	 *  	 * @param recipeID {@link String} 	 * @return true if the recipe is created, false otherwise. 	 */
specifier|public
name|boolean
name|createRecipe
parameter_list|(
name|String
name|recipeID
parameter_list|)
function_decl|;
comment|/** 	 * Creates a recipe with the specified ID and adds the rules identified by the IRIs in the set to the recipe. 	 *  	 * @param recipeID {@link String} 	 * @param ruleIRIs {@link Set< IRI>} 	 * @return true if the recipe is created, false otherwise. 	 */
specifier|public
name|boolean
name|createRecipe
parameter_list|(
name|String
name|recipeID
parameter_list|,
name|Set
argument_list|<
name|IRI
argument_list|>
name|ruleIRIs
parameter_list|)
function_decl|;
comment|/** 	 * Removes the recipe identified by the {@code recipeID}. 	 *  	 * @param recipeID {@link String} 	 * @return true if the recipe is removed, false otherwise. 	 */
specifier|public
name|boolean
name|removeRecipe
parameter_list|(
name|String
name|recipeID
parameter_list|)
function_decl|;
comment|/** 	 * Adds the rule identified by its IRI to a scpecified recipe. 	 * @param recipeIRI {@link IRI} 	 * @param swrlRuleIri {@link IRI} 	 * @return true if the rule is added to the recipe, false otherwise. 	 */
specifier|public
name|boolean
name|addRuleToRecipe
parameter_list|(
name|IRI
name|recipeIRI
parameter_list|,
name|IRI
name|swrlRuleIri
parameter_list|)
function_decl|;
comment|/** 	 * Gets the recipe specified by the IRI. 	 *  	 * @param recipeIRI {@link IRI} 	 * @return the set ot the rules' IRIs. 	 */
specifier|public
name|Set
argument_list|<
name|IRI
argument_list|>
name|getRecipe
parameter_list|(
name|IRI
name|recipeIRI
parameter_list|)
function_decl|;
comment|/** 	 * Gets the selected rule from the rule base. 	 *  	 * @param ruleIRI {@link IRI} 	 * @return the {@link KReSRule}. 	 */
specifier|public
name|KReSRule
name|getRule
parameter_list|(
name|IRI
name|ruleIRI
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

