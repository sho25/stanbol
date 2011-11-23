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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_interface
specifier|public
interface|interface
name|JobInfo
block|{
specifier|public
specifier|static
specifier|final
name|String
name|FINISHED
init|=
literal|"finished"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RUNNING
init|=
literal|"running"
decl_stmt|;
specifier|public
specifier|abstract
name|void
name|setOutputLocation
parameter_list|(
name|String
name|outputLocation
parameter_list|)
function_decl|;
specifier|public
specifier|abstract
name|String
name|getOutputLocation
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|void
name|addMessage
parameter_list|(
name|String
name|message
parameter_list|)
function_decl|;
specifier|public
specifier|abstract
name|List
argument_list|<
name|String
argument_list|>
name|getMessages
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|void
name|setMessages
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|messages
parameter_list|)
function_decl|;
specifier|public
specifier|abstract
name|String
name|getStatus
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|void
name|setFinished
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|void
name|setRunning
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|boolean
name|isRunning
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|boolean
name|isFinished
parameter_list|()
function_decl|;
block|}
end_interface

end_unit
