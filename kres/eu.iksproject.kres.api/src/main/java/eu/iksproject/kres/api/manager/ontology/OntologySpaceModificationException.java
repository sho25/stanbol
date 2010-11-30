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

begin_comment
comment|/**  * Thrown whenever an illegal operation that modifies an ontology space is  * detected and denied.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|OntologySpaceModificationException
extends|extends
name|Exception
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|5147080356192253724L
decl_stmt|;
specifier|protected
name|OntologySpace
name|space
decl_stmt|;
comment|/** 	 * Creates a new instance of OntologySpaceModificationException. 	 *  	 * @param space 	 *            the ontology space whose modification was attempted. 	 */
specifier|public
name|OntologySpaceModificationException
parameter_list|(
name|OntologySpace
name|space
parameter_list|)
block|{
name|this
operator|.
name|space
operator|=
name|space
expr_stmt|;
block|}
comment|/** 	 * Returns the ontology space that threw the exception (presumably after a 	 * failed modification attempt). 	 *  	 * @return the ontology space on which the exception was thrown. 	 */
specifier|public
name|OntologySpace
name|getSpace
parameter_list|()
block|{
return|return
name|space
return|;
block|}
block|}
end_class

end_unit

