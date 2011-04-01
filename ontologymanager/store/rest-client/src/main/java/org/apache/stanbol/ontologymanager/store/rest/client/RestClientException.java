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
name|store
operator|.
name|rest
operator|.
name|client
package|;
end_package

begin_comment
comment|/**  * Class to represent any exception in execution of methods of RestClient  *   * @author Cihan  */
end_comment

begin_class
specifier|public
class|class
name|RestClientException
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
literal|5713995314670538506L
decl_stmt|;
specifier|public
name|RestClientException
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

