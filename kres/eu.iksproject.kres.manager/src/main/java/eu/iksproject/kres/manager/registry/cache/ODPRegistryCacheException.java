begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  *   */
end_comment

begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|manager
operator|.
name|registry
operator|.
name|cache
package|;
end_package

begin_comment
comment|/**  * @author Enrico Daga  *   */
end_comment

begin_class
specifier|public
class|class
name|ODPRegistryCacheException
extends|extends
name|Exception
block|{
specifier|public
name|ODPRegistryCacheException
parameter_list|(
name|Throwable
name|e
parameter_list|)
block|{
name|super
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ODPRegistryCacheException
parameter_list|()
block|{ 	}
comment|/**      *       */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|9018256167443781857L
decl_stmt|;
block|}
end_class

end_unit

