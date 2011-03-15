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
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|MGraph
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
name|sparql
operator|.
name|ResultSet
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
name|KReSRuleList
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
comment|/** 	 * Get the rule of the recipe identified by the ruleURI. The rule is returned as 	 * a {@link KReSRule} object.  	 *  	 * @param ruleURI 	 * @return the object that represents a {@link KReSRule} 	 */
specifier|public
name|KReSRule
name|getRule
parameter_list|(
name|String
name|ruleURI
parameter_list|)
function_decl|;
comment|/** 	 * Trasnform the rules contained in the recipe in a set of SPARQL CONSTRUCT queries. 	 *  	 * @return the {@link String} array that contains the SPARQL CONSTRUCT queries. 	 */
specifier|public
name|String
index|[]
name|toSPARQL
parameter_list|()
function_decl|;
comment|/** 	 * Serialize the {@link Recipe} into a Jena {@link Model}. 	 *  	 * @return the {@link Model} of the Recipe. 	 */
specifier|public
name|Model
name|getRecipeAsRDFModel
parameter_list|()
function_decl|;
comment|/** 	 * Serialize the rules contained in the recipe to KReSRule Syntax. 	 * @return the {@link String} containing the serialization of the recipe's rules 	 * in KReSRule Syntax. 	 */
specifier|public
name|String
name|getRulesInKReSSyntax
parameter_list|()
function_decl|;
comment|/** 	 * Get the list of the {@link KReSRule} contained in the recipe. 	 * @return the {@link KReSRuleList}. 	 */
specifier|public
name|KReSRuleList
name|getkReSRuleList
parameter_list|()
function_decl|;
comment|/** 	 * Get the ID of the recipe in the {@link RuleStore}. 	 * @return the {@link IRI} expressing the recipe's ID. 	 */
specifier|public
name|IRI
name|getRecipeID
parameter_list|()
function_decl|;
comment|/** 	 * Get the description about the recipe. 	 * @return the {@link String} about the recipe's description. 	 */
specifier|public
name|String
name|getRecipeDescription
parameter_list|()
function_decl|;
comment|/** 	 * Add a KReSRule to the recipe. 	 * This operation does not effect a change on recipe in the rule store, but only in the in-memory 	 * representation of a specific recipe. To permanently change the recipe use {@link RuleStore#addRuleToRecipe(IRI, String)}. 	 * @param kReSRule the {@link KReSRule}. 	 */
specifier|public
name|void
name|addKReSRule
parameter_list|(
name|KReSRule
name|kReSRule
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

