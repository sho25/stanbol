begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
package|;
end_package

begin_class
specifier|public
class|class
name|RepositoryAccessException
extends|extends
name|Exception
block|{
comment|/**      *       */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|3142627349833142549L
decl_stmt|;
specifier|public
name|RepositoryAccessException
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
name|RepositoryAccessException
parameter_list|(
name|String
name|message
parameter_list|,
name|Throwable
name|throwable
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|,
name|throwable
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

