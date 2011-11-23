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
name|jobs
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertFalse
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertNotNull
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|CancellationException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ExecutionException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|Future
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
name|Job
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
name|JobManager
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
name|JobResult
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
name|impl
operator|.
name|JobManagerImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|BeforeClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
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

begin_comment
comment|/**  * Tests the {@see JobManagerImpl}  *   * @author enridaga  *   */
end_comment

begin_class
specifier|public
class|class
name|TestJobManagerImpl
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|TestJobManagerImpl
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// Number of jobs to add
specifier|private
specifier|static
specifier|final
name|int
name|numberOfJobs
init|=
literal|20
decl_stmt|;
comment|// Each job counts until ...
specifier|private
specifier|static
specifier|final
name|int
name|countUntil
init|=
literal|100
decl_stmt|;
comment|// Each count sleep ... ms
specifier|private
specifier|static
specifier|final
name|int
name|jobsleepTime
init|=
literal|100
decl_stmt|;
specifier|private
specifier|static
name|JobManager
name|jobManager
decl_stmt|;
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|jobs
decl_stmt|;
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|terminated
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setup
parameter_list|()
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Test initialized"
argument_list|)
expr_stmt|;
name|jobs
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|terminated
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|jobManager
operator|=
operator|new
name|JobManagerImpl
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|x
init|=
literal|0
init|;
name|x
operator|<
name|numberOfJobs
condition|;
name|x
operator|++
control|)
block|{
name|addJob
argument_list|()
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"Launched {} processes"
argument_list|,
name|numberOfJobs
argument_list|)
expr_stmt|;
block|}
comment|/**      * Before each test method, we add some jobs      */
annotation|@
name|Before
specifier|public
name|void
name|init
parameter_list|()
block|{}
specifier|private
specifier|static
name|void
name|addJob
parameter_list|()
block|{
specifier|final
name|int
name|max
init|=
name|countUntil
decl_stmt|;
specifier|final
name|int
name|number
init|=
name|jobs
operator|.
name|size
argument_list|()
operator|+
literal|1
decl_stmt|;
specifier|final
name|int
name|jst
init|=
name|jobsleepTime
decl_stmt|;
name|jobs
operator|.
name|add
argument_list|(
name|jobManager
operator|.
name|execute
argument_list|(
operator|new
name|Job
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|JobResult
name|call
parameter_list|()
block|{
specifier|final
name|int
name|num
init|=
name|number
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|max
condition|;
name|i
operator|++
control|)
block|{
try|try
block|{
comment|//log.debug("Process " + Integer.toString(num) + " is working");
name|Thread
operator|.
name|sleep
argument_list|(
name|jst
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|ie
parameter_list|)
block|{}
block|}
name|JobResult
name|r
init|=
operator|new
name|JobResult
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
literal|"This is process "
operator|+
name|Integer
operator|.
name|toString
argument_list|(
name|num
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSuccess
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
block|}
decl_stmt|;
return|return
name|r
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|buildResultLocation
parameter_list|(
name|String
name|jobId
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|contains
parameter_list|()
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Testing hasJob(String id)"
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|id
range|:
name|jobs
control|)
block|{
name|assertTrue
argument_list|(
name|jobManager
operator|.
name|hasJob
argument_list|(
name|id
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|future
parameter_list|()
throws|throws
name|InterruptedException
throws|,
name|ExecutionException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Testing monitoring"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|jobs
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|assertNotNull
argument_list|(
name|jobManager
operator|.
name|ping
argument_list|(
name|jobs
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|ping
parameter_list|()
throws|throws
name|InterruptedException
throws|,
name|ExecutionException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Testing ping(String id)"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|jobs
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Waiting 0.5 sec before checking status"
argument_list|)
expr_stmt|;
name|Thread
operator|.
name|sleep
argument_list|(
literal|500
argument_list|)
expr_stmt|;
name|boolean
name|finished
init|=
name|ping
argument_list|(
name|jobs
argument_list|)
decl_stmt|;
if|if
condition|(
name|finished
condition|)
block|{
break|break;
block|}
block|}
block|}
comment|/**      * To test the interaction with the Future object, for interrupting jobs. Jobs are canceled, but they      * persist in the manager.      */
annotation|@
name|Test
specifier|public
name|void
name|interrupt
parameter_list|()
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Testing the Future object (for monitoring)"
argument_list|)
expr_stmt|;
comment|// We interrupt the first numberOfJobs/2 processes
name|int
name|numberToInterrupt
init|=
name|jobs
operator|.
name|size
argument_list|()
operator|/
literal|2
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Going to interrupt {} jobs"
argument_list|,
name|numberToInterrupt
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|id
range|:
name|jobs
control|)
block|{
if|if
condition|(
name|numberToInterrupt
operator|==
literal|0
condition|)
block|{
break|break;
block|}
else|else
block|{
name|numberToInterrupt
operator|--
expr_stmt|;
block|}
name|Future
argument_list|<
name|?
argument_list|>
name|f
init|=
name|jobManager
operator|.
name|ping
argument_list|(
name|id
argument_list|)
decl_stmt|;
comment|// We force the job to interrupt
name|boolean
name|success
init|=
name|f
operator|.
name|cancel
argument_list|(
literal|true
argument_list|)
decl_stmt|;
name|boolean
name|throwsOnget
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|success
condition|)
block|{
try|try
block|{
name|f
operator|.
name|get
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
comment|// This should NOT happen
name|assertFalse
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ExecutionException
name|e
parameter_list|)
block|{
comment|// This should NOT happen
name|assertFalse
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|CancellationException
name|e
parameter_list|)
block|{
comment|// This exception SHOULD happen
name|throwsOnget
operator|=
literal|true
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|throwsOnget
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Job {} interrupted"
argument_list|,
name|id
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|boolean
name|ping
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|processes
parameter_list|)
throws|throws
name|InterruptedException
throws|,
name|ExecutionException
block|{
name|int
name|size
init|=
name|processes
operator|.
name|size
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|id
range|:
name|processes
control|)
block|{
if|if
condition|(
name|terminated
operator|.
name|contains
argument_list|(
name|id
argument_list|)
condition|)
block|{
name|assertFalse
argument_list|(
name|jobManager
operator|.
name|hasJob
argument_list|(
name|id
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Future
argument_list|<
name|?
argument_list|>
name|f
init|=
name|jobManager
operator|.
name|ping
argument_list|(
name|id
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|f
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Pinging id {}: {}"
argument_list|,
name|id
argument_list|,
name|f
operator|.
name|isDone
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|f
operator|.
name|isCancelled
argument_list|()
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"{} - have been interrupted."
argument_list|,
name|id
argument_list|)
expr_stmt|;
name|terminated
operator|.
name|add
argument_list|(
name|id
argument_list|)
expr_stmt|;
comment|// We can remove this, since we have known it
name|jobManager
operator|.
name|remove
argument_list|(
name|id
argument_list|)
expr_stmt|;
comment|// There is no output
name|size
operator|=
name|size
operator|-
literal|1
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|f
operator|.
name|isDone
argument_list|()
condition|)
block|{
name|terminated
operator|.
name|add
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|jobManager
operator|.
name|remove
argument_list|(
name|id
argument_list|)
expr_stmt|;
comment|// The get method should return something, wince we know this is not a canceled job
name|log
operator|.
name|info
argument_list|(
literal|"{} completed, output is {}"
argument_list|,
name|id
argument_list|,
name|f
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|size
operator|=
name|size
operator|-
literal|1
expr_stmt|;
block|}
block|}
block|}
return|return
operator|(
name|terminated
operator|.
name|size
argument_list|()
operator|==
name|processes
operator|.
name|size
argument_list|()
operator|)
return|;
block|}
block|}
end_class

end_unit

