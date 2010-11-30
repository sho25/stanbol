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
name|manager
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
name|IRI
import|;
end_import

begin_comment
comment|/**  * An ontology space factory is responsible for the creation of new, readily  * specialized ontology spaces from supplied ontology input sources.  *   * Implementations should not call the setup method of the ontology space once  * it is created, so that it is not locked from editing since creation time.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|OntologySpaceFactory
block|{
comment|/** 	 * Creates and sets up a default core ontology space. 	 *  	 * @param scopeID 	 *            the unique identifier of the ontology scope that will 	 *            reference this space. It can be used for generating the 	 *            identifier for this ontology space. 	 * @param coreSource 	 *            the input source for the ontologies in this space. 	 * @return the generated ontology space. 	 */
specifier|public
name|CoreOntologySpace
name|createCoreOntologySpace
parameter_list|(
name|IRI
name|scopeID
parameter_list|,
name|OntologyInputSource
name|coreSource
parameter_list|)
function_decl|;
comment|/** 	 * Creates and sets up a default custom ontology space. 	 *  	 * @param scopeID 	 *            the unique identifier of the ontology scope that will 	 *            reference this space. It can be used for generating the 	 *            identifier for this ontology space. 	 * @param customSource 	 *            the input source for the ontologies in this space. 	 * @return the generated ontology space. 	 */
specifier|public
name|CustomOntologySpace
name|createCustomOntologySpace
parameter_list|(
name|IRI
name|scopeID
parameter_list|,
name|OntologyInputSource
name|customSource
parameter_list|)
function_decl|;
comment|/** 	 * Creates and sets up a default session ontology space. 	 *  	 * @param scopeID 	 *            the unique identifier of the ontology scope that will 	 *            reference this space. It can be used for generating the 	 *            identifier for this ontology space. 	 * @return the generated ontology space. 	 */
specifier|public
name|SessionOntologySpace
name|createSessionOntologySpace
parameter_list|(
name|IRI
name|scopeID
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

