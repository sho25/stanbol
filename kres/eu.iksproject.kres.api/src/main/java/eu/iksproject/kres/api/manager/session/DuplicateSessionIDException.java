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
name|session
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
name|DuplicateIDException
import|;
end_import

begin_comment
comment|/**  * Thrown when attempting to create a KReSSession by forcing a session ID that  * is already registered, even if it used to be associated to a session that has  * been destroyed.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|DuplicateSessionIDException
extends|extends
name|DuplicateIDException
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3548783975623103351L
decl_stmt|;
specifier|public
name|DuplicateSessionIDException
parameter_list|(
name|IRI
name|dupe
parameter_list|)
block|{
name|super
argument_list|(
name|dupe
argument_list|)
expr_stmt|;
block|}
specifier|public
name|DuplicateSessionIDException
parameter_list|(
name|IRI
name|dupe
parameter_list|,
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|dupe
argument_list|,
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|DuplicateSessionIDException
parameter_list|(
name|IRI
name|dupe
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|dupe
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

