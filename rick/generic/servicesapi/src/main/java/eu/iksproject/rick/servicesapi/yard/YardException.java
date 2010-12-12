begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|yard
package|;
end_package

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|RickException
import|;
end_import

begin_comment
comment|/**  * Used to indicate an error while performing an operation on a yard  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|YardException
extends|extends
name|RickException
block|{
comment|/**      * The default serial version UIR      */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/**      * Creates an exception with a message and a cause      * @param reason the message describing the reason for the error      * @param cause the parent      */
specifier|public
name|YardException
parameter_list|(
name|String
name|reason
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|reason
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates an exception with a message and a cause      * @param reason the message describing the reason for the error      */
specifier|public
name|YardException
parameter_list|(
name|String
name|reason
parameter_list|)
block|{
name|super
argument_list|(
name|reason
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

