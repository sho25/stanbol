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
comment|/**  * Thrown whenever an attempt to modify the ontology network within a read-only  * ontology space (e.g. a core or custom space in a bootstrapped system) is  * detected and denied.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|UnmodifiableOntologySpaceException
extends|extends
name|OntologySpaceModificationException
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|6747720213098173405L
decl_stmt|;
comment|/** 	 * Creates a new instance of UnmodifiableOntologySpaceException. 	 *  	 * @param space 	 *            the ontology space whose modification was attempted. 	 */
specifier|public
name|UnmodifiableOntologySpaceException
parameter_list|(
name|OntologySpace
name|space
parameter_list|)
block|{
name|super
argument_list|(
name|space
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

