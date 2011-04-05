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

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Model
import|;
end_import

begin_interface
specifier|public
interface|interface
name|Recipe
block|{
comment|/** 	 * Get the rule of the recipe identified by the ruleURI. The rule is returned as 	 * a {@link Rule} object. 	 *  	 * @param ruleURI 	 * @return the object that represents a {@link Rule} 	 */
name|Rule
name|getRule
parameter_list|(
name|String
name|ruleURI
parameter_list|)
function_decl|;
comment|/** 	 * Trasnform the rules contained in the recipe in a set of SPARQL CONSTRUCT queries. 	 *  	 * @return the {@link String} array that contains the SPARQL CONSTRUCT queries. 	 */
name|String
index|[]
name|toSPARQL
parameter_list|()
function_decl|;
comment|/** 	 * Serialize the {@link Recipe} into a Jena {@link Model}. 	 *  	 * @return the {@link Model} of the Recipe. 	 */
name|Model
name|getRecipeAsRDFModel
parameter_list|()
function_decl|;
comment|/** 	 * Serialize the rules contained in the recipe to Rule Syntax. 	 * @return the {@link String} containing the serialization of the recipe's rules 	 * in Rule Syntax. 	 */
name|String
name|getRulesInKReSSyntax
parameter_list|()
function_decl|;
comment|/** 	 * Get the list of the {@link Rule} contained in the recipe. 	 * @return the {@link RuleList}. 	 */
name|RuleList
name|getkReSRuleList
parameter_list|()
function_decl|;
comment|/** 	 * Get the ID of the recipe in the {@link RuleStore}. 	 * @return the {@link IRI} expressing the recipe's ID. 	 */
name|IRI
name|getRecipeID
parameter_list|()
function_decl|;
comment|/** 	 * Get the description about the recipe. 	 * @return the {@link String} about the recipe's description. 	 */
name|String
name|getRecipeDescription
parameter_list|()
function_decl|;
comment|/** 	 * Add a Rule to the recipe. 	 * This operation does not effect a change on recipe in the rule store, but only in the in-memory 	 * representation of a specific recipe. To permanently change the recipe use {@link RuleStore#addRuleToRecipe(IRI, String)}. 	 * @param kReSRule the {@link Rule}. 	 */
name|void
name|addKReSRule
parameter_list|(
name|Rule
name|kReSRule
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

