begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|rules
package|;
end_package

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
name|OWLDataFactory
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
name|SWRLRule
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
name|Resource
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|rules
operator|.
name|util
operator|.
name|AtomList
import|;
end_import

begin_comment
comment|/**  * A KReSRule is a Java object that represent a rule in KReS. It contains methods to transform a rule both in SWRL and in KReSRule  * syntax.   *   * @author andrea.nuzzolese  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|KReSRule
block|{
comment|/** 	 * Gets the name of the rule. 	 *  	 * @return the {@link String} representing the name of the rule. 	 */
specifier|public
name|String
name|getRuleName
parameter_list|()
function_decl|;
comment|/** 	 * Sets the rule's name 	 *  	 * @param ruleName {@link String} 	 */
specifier|public
name|void
name|setRuleName
parameter_list|(
name|String
name|ruleName
parameter_list|)
function_decl|;
comment|/** 	 * Returns the representation of the rule in KReSRule syntax. 	 *  	 * @return the {@link String} of the rule in KReSRule syntax. 	 */
specifier|public
name|String
name|getRule
parameter_list|()
function_decl|;
comment|/** 	 * Sets the rule expressed in KReSRule syntax 	 *  	 * @param rule {@link String} 	 */
specifier|public
name|void
name|setRule
parameter_list|(
name|String
name|rule
parameter_list|)
function_decl|;
comment|/** 	 * Maps a {@code KReSRule} to a Jena {@link Resource} object in a given Jena {@link Model}. 	 * @param model {@link Model} 	 * @return the {@link Resource} containing the rule. 	 */
specifier|public
name|Resource
name|toSWRL
parameter_list|(
name|Model
name|model
parameter_list|)
function_decl|;
comment|/** 	 * Maps a {@code KReSRule} to an OWL-API {@link SWRLRule}. 	 * @param factory {@link OWLDataFactory} 	 * @return the {@link SWRLRule} containing the rule. 	 */
specifier|public
name|SWRLRule
name|toSWRL
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|)
function_decl|;
comment|/** 	 * Transforms the rule to a SPARQL CONSTRUCT. 	 *  	 * @return the string containing the SPARQL CONSTRUCT. 	 */
specifier|public
name|String
name|toSPARQL
parameter_list|()
function_decl|;
comment|/** 	 * Rules are composed by an antecedent (body) and a consequent (head). This method returnn the consequent 	 * expressed as a list of its atoms ({@link AtomList}). 	 * @return the {@link AtomList} of the consequent's atoms.  	 */
specifier|public
name|AtomList
name|getHead
parameter_list|()
function_decl|;
comment|/** 	 * Rules are composed by an antecedent (body) and a consequent (head). This method returnn the antecedent 	 * expressed as a list of its atoms ({@link AtomList}). 	 * @return the {@link AtomList} of the antecedent's atoms.  	 */
specifier|public
name|AtomList
name|getBody
parameter_list|()
function_decl|;
comment|/** 	 * Retunr the KReS syntax representation of the rule. 	 * @return the string of the rule in KReSRule syntax. 	 */
specifier|public
name|String
name|toKReSSyntax
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

