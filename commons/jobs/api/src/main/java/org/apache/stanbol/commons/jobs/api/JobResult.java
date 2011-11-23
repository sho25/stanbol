begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|jobs
operator|.
name|api
package|;
end_package

begin_comment
comment|/**  * Interface for Job results.  * It only provides a report message and a boolean success/failure.  *   * @author enridaga  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|JobResult
block|{
comment|/**      * A report message.      *       * @return      */
specifier|public
name|String
name|getMessage
parameter_list|()
function_decl|;
comment|/**      * True if the job execution succeeded, false otherwise      *       * @return      */
specifier|public
name|boolean
name|isSuccess
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

