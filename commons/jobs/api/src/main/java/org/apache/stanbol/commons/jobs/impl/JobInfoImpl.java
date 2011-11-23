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
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
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
operator|.
name|JobInfo
import|;
end_import

begin_class
specifier|public
class|class
name|JobInfoImpl
implements|implements
name|JobInfo
block|{
specifier|private
name|String
name|status
init|=
literal|"undefined"
decl_stmt|;
specifier|private
name|String
name|outputLocation
init|=
literal|""
decl_stmt|;
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|messages
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|/* (non-Javadoc)      * @see org.apache.stanbol.commons.jobs.web.utils.JobInfo#setOutputLocation(java.lang.String)      */
annotation|@
name|Override
specifier|public
name|void
name|setOutputLocation
parameter_list|(
name|String
name|outputLocation
parameter_list|)
block|{
name|this
operator|.
name|outputLocation
operator|=
name|outputLocation
expr_stmt|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.commons.jobs.web.utils.JobInfo#getOutputLocation()      */
annotation|@
name|Override
specifier|public
name|String
name|getOutputLocation
parameter_list|()
block|{
return|return
name|this
operator|.
name|outputLocation
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.commons.jobs.web.utils.JobInfo#addMessage(java.lang.String)      */
annotation|@
name|Override
specifier|public
name|void
name|addMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|this
operator|.
name|messages
operator|.
name|add
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.commons.jobs.web.utils.JobInfo#getMessages()      */
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getMessages
parameter_list|()
block|{
return|return
name|messages
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.commons.jobs.web.utils.JobInfo#setMessages(java.util.List)      */
annotation|@
name|Override
specifier|public
name|void
name|setMessages
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|messages
parameter_list|)
block|{
name|this
operator|.
name|messages
operator|=
name|messages
expr_stmt|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.commons.jobs.web.utils.JobInfo#getStatus()      */
annotation|@
name|Override
specifier|public
name|String
name|getStatus
parameter_list|()
block|{
return|return
name|this
operator|.
name|status
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.commons.jobs.web.utils.JobInfo#setFinished()      */
annotation|@
name|Override
specifier|public
name|void
name|setFinished
parameter_list|()
block|{
name|this
operator|.
name|status
operator|=
name|FINISHED
expr_stmt|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.commons.jobs.web.utils.JobInfo#setRunning()      */
annotation|@
name|Override
specifier|public
name|void
name|setRunning
parameter_list|()
block|{
name|this
operator|.
name|status
operator|=
name|RUNNING
expr_stmt|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.commons.jobs.web.utils.JobInfo#isRunning()      */
annotation|@
name|Override
specifier|public
name|boolean
name|isRunning
parameter_list|()
block|{
return|return
name|this
operator|.
name|status
operator|.
name|equals
argument_list|(
name|RUNNING
argument_list|)
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.commons.jobs.web.utils.JobInfo#isFinished()      */
annotation|@
name|Override
specifier|public
name|boolean
name|isFinished
parameter_list|()
block|{
return|return
name|this
operator|.
name|status
operator|.
name|equals
argument_list|(
name|FINISHED
argument_list|)
return|;
block|}
block|}
end_class

end_unit
