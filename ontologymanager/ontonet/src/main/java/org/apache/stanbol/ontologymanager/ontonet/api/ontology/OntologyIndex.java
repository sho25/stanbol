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

begin_comment
comment|/**  * Provides an interface to the ontologies provided by registered scopes in the  * ontology manager.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|OntologyIndex
extends|extends
name|ScopeOntologyListener
extends|,
name|ScopeEventListener
block|{
name|Set
argument_list|<
name|IRI
argument_list|>
name|getIndexedOntologyIRIs
parameter_list|()
function_decl|;
comment|/** 	 * Returns an ontology having the specified IRI as its identifier, or null 	 * if no such ontology is indexed.<br> 	 *<br> 	 * Which ontology is returned in case more ontologies with this IRI are 	 * registered in different scopes is at the discretion of implementors. 	 *  	 * @param ontologyIri 	 * @return 	 */
name|OWLOntology
name|getOntology
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|)
function_decl|;
comment|/** 	 * Returns the ontology loaded within an ontology scope having the specified 	 * IRI as its identifier, or null if no such ontology is loaded in that 	 * scope. 	 *  	 * @param ontologyIri 	 * @return 	 */
name|OWLOntology
name|getOntology
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|,
name|IRI
name|scopeId
parameter_list|)
function_decl|;
comment|/** 	 * Returns the set of ontology scopes where an ontology with the specified 	 * IRI is registered in either their core spaces or their custom spaces. 	 * Optionally, session spaces can be queried as well. 	 *  	 * @param ontologyIri 	 * @param includingSessionSpaces 	 * @return 	 */
name|Set
argument_list|<
name|IRI
argument_list|>
name|getReferencingScopes
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|,
name|boolean
name|includingSessionSpaces
parameter_list|)
function_decl|;
comment|/** 	 * Determines if an ontology with the specified identifier is loaded within 	 * some registered ontology scope. 	 *  	 * @param ontologyIri 	 * @return 	 */
name|boolean
name|isOntologyLoaded
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

