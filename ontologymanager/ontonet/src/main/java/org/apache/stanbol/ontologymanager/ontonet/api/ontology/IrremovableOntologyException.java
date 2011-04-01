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
name|IRI
import|;
end_import

begin_comment
comment|/**  * Thrown whenever an illegal attempt at removing an ontology from an ontology  * space is detected. This can happen e.g. if the ontology is the space root or  * not a direct child thereof.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|IrremovableOntologyException
extends|extends
name|OntologySpaceModificationException
block|{
specifier|protected
name|IRI
name|ontologyId
decl_stmt|;
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|3301398666369788964L
decl_stmt|;
comment|/** 	 * Constructs a new instance of<code>IrremovableOntologyException</code>. 	 *  	 * @param space 	 *            the space that holds the ontology. 	 * @param ontologyId 	 *            the logical IRI of the ontology whose removal was denied. 	 */
specifier|public
name|IrremovableOntologyException
parameter_list|(
name|OntologySpace
name|space
parameter_list|,
name|IRI
name|ontologyId
parameter_list|)
block|{
name|super
argument_list|(
name|space
argument_list|)
expr_stmt|;
name|this
operator|.
name|ontologyId
operator|=
name|ontologyId
expr_stmt|;
block|}
comment|/** 	 * Returns the unique identifier of the ontology whose removal was denied. 	 *  	 * @return the ID of the ontology that was not removed. 	 */
specifier|public
name|IRI
name|getOntologyId
parameter_list|()
block|{
return|return
name|ontologyId
return|;
block|}
block|}
end_class

end_unit

