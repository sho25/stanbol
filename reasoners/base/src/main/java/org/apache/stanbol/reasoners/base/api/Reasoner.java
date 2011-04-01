begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reasoners
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
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntology
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
name|reasoner
operator|.
name|OWLReasoner
import|;
end_import

begin_comment
comment|/**  * The KReS Reasoner provides all the reasoning services to the KReS.  *   *   * @author andrea.nuzzolese  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Reasoner
block|{
comment|/** 	 * Gets the reasoner. 	 *  	 * @param ontology {@link OWLOntology} 	 * @return the reasoner {@link OWLReasoner}. 	 */
specifier|public
name|OWLReasoner
name|getReasoner
parameter_list|(
name|OWLOntology
name|ontology
parameter_list|)
function_decl|;
comment|/** 	 * Runs a consistency check on the ontology. 	 *  	 * @param owlReasoner {@link OWLReasoner} 	 * @return true if the ontology is consistent, false otherwise. 	 */
specifier|public
name|boolean
name|consistencyCheck
parameter_list|(
name|OWLReasoner
name|owlReasoner
parameter_list|)
function_decl|;
comment|/** 	 * Launch the reasoning on a set of rules applied to a gien ontology. 	 * @param ontology 	 * @param ruleOntology 	 * @return the inferred ontology 	 */
specifier|public
name|OWLOntology
name|runRules
parameter_list|(
name|OWLOntology
name|ontology
parameter_list|,
name|OWLOntology
name|ruleOntology
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

