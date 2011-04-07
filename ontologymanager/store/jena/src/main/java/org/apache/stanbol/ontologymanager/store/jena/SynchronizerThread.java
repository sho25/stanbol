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
name|jena
package|;
end_package

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentInstance
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
name|ontologymanager
operator|.
name|store
operator|.
name|api
operator|.
name|StoreSynchronizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
specifier|public
class|class
name|SynchronizerThread
extends|extends
name|Thread
block|{
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SynchronizerThread
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|StoreSynchronizer
name|synchronizer
decl_stmt|;
specifier|private
name|ComponentInstance
name|instance
decl_stmt|;
specifier|private
name|Boolean
name|done
init|=
literal|false
decl_stmt|;
specifier|public
name|SynchronizerThread
parameter_list|(
name|StoreSynchronizer
name|synchronizer
parameter_list|,
name|ComponentInstance
name|instance
parameter_list|)
block|{
name|this
operator|.
name|synchronizer
operator|=
name|synchronizer
expr_stmt|;
name|this
operator|.
name|instance
operator|=
name|instance
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|synchronizer
operator|.
name|synchronizeAll
argument_list|(
literal|true
argument_list|)
expr_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
synchronized|synchronized
init|(
name|done
init|)
block|{
if|if
condition|(
name|done
condition|)
block|{
return|return;
block|}
name|long
name|t1
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Started Synchronizing"
argument_list|)
expr_stmt|;
name|synchronizer
operator|.
name|synchronizeAll
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|long
name|t2
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Completed Synchronizing in "
operator|+
operator|(
name|t2
operator|-
name|t1
operator|)
operator|+
literal|" miliseconds"
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|Thread
operator|.
name|sleep
argument_list|(
literal|10000
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Thread Interrupted"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|void
name|done
parameter_list|()
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"About to stop synchronizer"
argument_list|)
expr_stmt|;
synchronized|synchronized
init|(
name|done
init|)
block|{
name|this
operator|.
name|done
operator|=
literal|true
expr_stmt|;
name|this
operator|.
name|synchronizer
operator|.
name|clear
argument_list|()
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Stopped synchronizer"
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|instance
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

