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
name|reasoners
package|;
end_package

begin_comment
comment|/**  *   * @author andrea.nuzzolese  *  */
end_comment

begin_class
specifier|public
class|class
name|InconcistencyException
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
literal|1L
decl_stmt|;
specifier|public
name|InconcistencyException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|InconcistencyException
parameter_list|(
name|Throwable
name|cause
parameter_list|)
block|{
name|initCause
argument_list|(
name|cause
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

