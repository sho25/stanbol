begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ontology
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
name|OWLOntologyManager
import|;
end_import

begin_comment
comment|/**  * An ontology scope for application use. There exists exactly one scope for  * each live (active or halted) KReS session.<br>  *<br>  * This is the only type of ontology scope that allows public access to its OWL  * ontology manager.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SessionOntologySpace
extends|extends
name|OntologySpace
block|{
comment|/** 	 * Returns the OWL ontology manager associated to this scope. 	 *  	 * @return the associated ontology manager 	 */
specifier|public
name|OWLOntologyManager
name|getOntologyManager
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

