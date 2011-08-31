begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reasoners
operator|.
name|servicesapi
package|;
end_package

begin_comment
comment|/**  * The process cannot be completed because the input is inconsistent. This Exception must be used by reasoning  * services which must stop the inference process if any inconsistency is found.  */
end_comment

begin_class
specifier|public
class|class
name|InconsistentInputException
extends|extends
name|Exception
block|{
specifier|public
name|InconsistentInputException
parameter_list|()
block|{ }
specifier|public
name|InconsistentInputException
parameter_list|(
name|Exception
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|cause
argument_list|)
expr_stmt|;
block|}
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|117198026192803326L
decl_stmt|;
block|}
end_class

end_unit

