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
comment|/**  * An ontology input source provides a point for loading an ontology. Currently  * it provides two ways of obtaining an ontology document:  *   *<ol>  *<li>From an OWLOntology.  *<li>By dereferencing an physical IRI.  *</ol>  *   * Consumers that use an ontology input source will attempt to obtain a concrete  * representation of an ontology in the above order. Implementations of this  * interface may try to dereference the IRI internally and just provide the  * OWLOntology, or directly provide the physical IRI for other classes to  * dereference. Implementations should allow multiple attempts at loading an  * ontology.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|OntologyInputSource
block|{
comment|/** 	 * Returns the IRI by dereferencing which it should be possible to obtain 	 * the ontology. This method is supposed to return null if the ontology 	 * lives in-memory and was not or is not going to be stored publicly. 	 *  	 * @return the physical location for this ontology source, or null if 	 *         unknown. 	 */
specifier|public
name|IRI
name|getPhysicalIRI
parameter_list|()
function_decl|;
comment|/** 	 * Returns the OWL Ontology that imports the whole ontology network 	 * addressed by this input source. 	 *  	 * @return the ontology network root. 	 */
specifier|public
name|OWLOntology
name|getRootOntology
parameter_list|()
function_decl|;
comment|/** 	 * Determines if a physical IRI is known for this ontology source. Note that 	 * an anonymous ontology may have been fetched from a physical location, 	 * just as a named ontology may have been stored in memory and have no 	 * physical location. 	 *  	 * @return true if a physical location is known for this ontology source. 	 */
specifier|public
name|boolean
name|hasPhysicalIRI
parameter_list|()
function_decl|;
comment|/** 	 * Determines if a root ontology that imports the entire network is 	 * available. 	 *  	 * @return true if a root ontology is available, false otherwise. 	 */
specifier|public
name|boolean
name|hasRootOntology
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

