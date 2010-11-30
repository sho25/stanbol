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
package|;
end_package

begin_comment
comment|/**  * Indicates an error while performing an operation within the RICK.<p>  * This class is abstract use one of the more specific subclasses  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|RickException
extends|extends
name|Exception
block|{
comment|/** 	 * default serial version uid. 	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/** 	 * Creates an exception with a message and a cause 	 * @param reason the message describing the reason for the error 	 * @param cause the parent 	 */
specifier|protected
name|RickException
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
comment|/** 	 * Creates an exception with a message and a cause 	 * @param reason the message describing the reason for the error 	 */
specifier|protected
name|RickException
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

