begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|jobmanager
operator|.
name|event
operator|.
name|impl
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|jobmanager
operator|.
name|event
operator|.
name|Constants
operator|.
name|PROPERTY_JOB_MANAGER
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|jobmanager
operator|.
name|event
operator|.
name|Constants
operator|.
name|PROPERTY_NODE
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|jobmanager
operator|.
name|event
operator|.
name|Constants
operator|.
name|TOPIC_JOB_MANAGER
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|ExecutionPlanHelper
operator|.
name|getEngine
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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Dictionary
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|locks
operator|.
name|Lock
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
name|locks
operator|.
name|ReadWriteLock
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
name|locks
operator|.
name|ReentrantReadWriteLock
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|NonLiteral
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|EngineException
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|EnhancementEngine
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|EnhancementEngineManager
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|ExecutionPlanHelper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|event
operator|.
name|Event
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|event
operator|.
name|EventAdmin
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|event
operator|.
name|EventHandler
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
name|EnhancementJobHandler
implements|implements
name|EventHandler
block|{
specifier|private
name|EnhancementEngineManager
name|engineManager
decl_stmt|;
specifier|private
name|EventAdmin
name|eventAdmin
decl_stmt|;
comment|/*      * NOTE on debug level Loggings      *       *  ++ ... init some activity      *>> ... started some activity (thread has the requested lock)      *<< ... completed some activity (thread has released the lock)      *        *  n: ... no lock      *  r: ... read lock      *  w: ... write lock      */
specifier|private
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|EnhancementJobHandler
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Keys are {@link EnhancementJob}s currently asynchronously enhancing       * contentItems and the values are the objects used to interrupt the       * requesting thread as soon as the enhancement process has finished.       */
specifier|private
name|Map
argument_list|<
name|EnhancementJob
argument_list|,
name|Object
argument_list|>
name|processingJobs
decl_stmt|;
specifier|private
specifier|final
name|ReadWriteLock
name|processingLock
init|=
operator|new
name|ReentrantReadWriteLock
argument_list|()
decl_stmt|;
specifier|public
name|EnhancementJobHandler
parameter_list|(
name|EventAdmin
name|eventAdmin
parameter_list|,
name|EnhancementEngineManager
name|engineManager
parameter_list|)
block|{
if|if
condition|(
name|eventAdmin
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed EventAdmin service MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|engineManager
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed EnhancementEngineManager MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|eventAdmin
operator|=
name|eventAdmin
expr_stmt|;
name|this
operator|.
name|engineManager
operator|=
name|engineManager
expr_stmt|;
name|processingLock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|processingJobs
operator|=
operator|new
name|HashMap
argument_list|<
name|EnhancementJob
argument_list|,
name|Object
argument_list|>
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|processingLock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Closes this Handler and notifies all components that wait for still      * running jobs      */
specifier|public
name|void
name|close
parameter_list|()
block|{
name|log
operator|.
name|info
argument_list|(
literal|"deactivate {}"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|processingLock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
for|for
control|(
name|Object
name|o
range|:
name|processingJobs
operator|.
name|values
argument_list|()
control|)
block|{
synchronized|synchronized
init|(
name|o
init|)
block|{
name|o
operator|.
name|notifyAll
argument_list|()
expr_stmt|;
block|}
block|}
name|processingJobs
operator|=
literal|null
expr_stmt|;
block|}
finally|finally
block|{
name|processingLock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Registers an EnhancementJob and will start the enhancement process.      * When the process is finished or this service is deactivated the      * returned oject will be notified. Therefore callers that need to       * wait for the completion of the parsed job will want to      *<code><pre>      *   Object object = enhancementJobHandler.register();      *   while(!job.isFinished()& enhancementJobHandler != null){      *       synchronized (object) {      *           try {      *               object.wait();      *           } catch (InterruptedException e) {}      *       }      *   }      *</pre></code>      * @param enhancementJob the enhancement job to register      * @return An object that will get {@link Object#notifyAll()} as soon as      * {@link EnhancementJob#isFinished()} or this instance is deactivated      */
specifier|public
name|Object
name|register
parameter_list|(
name|EnhancementJob
name|enhancementJob
parameter_list|)
block|{
specifier|final
name|boolean
name|init
decl_stmt|;
name|Object
name|o
decl_stmt|;
name|processingLock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
if|if
condition|(
name|enhancementJob
operator|==
literal|null
operator|||
name|processingJobs
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|o
operator|=
name|processingJobs
operator|.
name|get
argument_list|(
name|enhancementJob
argument_list|)
expr_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
block|{
name|o
operator|=
operator|new
name|Object
argument_list|()
expr_stmt|;
name|processingJobs
operator|.
name|put
argument_list|(
name|enhancementJob
argument_list|,
name|o
argument_list|)
expr_stmt|;
name|init
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|init
operator|=
literal|false
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|processingLock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|init
condition|)
block|{
name|executeNextNodes
argument_list|(
name|enhancementJob
argument_list|)
expr_stmt|;
block|}
return|return
name|o
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|handleEvent
parameter_list|(
name|Event
name|event
parameter_list|)
block|{
name|EnhancementJob
name|job
init|=
operator|(
name|EnhancementJob
operator|)
name|event
operator|.
name|getProperty
argument_list|(
name|PROPERTY_JOB_MANAGER
argument_list|)
decl_stmt|;
name|NonLiteral
name|node
init|=
operator|(
name|NonLiteral
operator|)
name|event
operator|.
name|getProperty
argument_list|(
name|PROPERTY_NODE
argument_list|)
decl_stmt|;
name|String
name|engineName
init|=
name|getEngine
argument_list|(
name|job
operator|.
name|getExecutionPlan
argument_list|()
argument_list|,
name|node
argument_list|)
decl_stmt|;
comment|//(1) execute the parsed ExecutionNode
name|EnhancementEngine
name|engine
init|=
name|engineManager
operator|.
name|getEngine
argument_list|(
name|engineName
argument_list|)
decl_stmt|;
if|if
condition|(
name|engine
operator|!=
literal|null
condition|)
block|{
comment|//execute the engine
name|Exception
name|exception
init|=
literal|null
decl_stmt|;
name|int
name|engineState
decl_stmt|;
try|try
block|{
name|engineState
operator|=
name|engine
operator|.
name|canEnhance
argument_list|(
name|job
operator|.
name|getContentItem
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|EngineException
name|e
parameter_list|)
block|{
name|exception
operator|=
name|e
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to check if engine '"
operator|+
name|engineName
operator|+
literal|"'(type: "
operator|+
name|engine
operator|.
name|getClass
argument_list|()
operator|+
literal|") can enhance ContentItem '"
operator|+
name|job
operator|.
name|getContentItem
argument_list|()
operator|.
name|getUri
argument_list|()
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|engineState
operator|=
name|EnhancementEngine
operator|.
name|CANNOT_ENHANCE
expr_stmt|;
block|}
if|if
condition|(
name|engineState
operator|==
name|EnhancementEngine
operator|.
name|ENHANCE_SYNCHRONOUS
condition|)
block|{
comment|//ensure that this engine exclusively access the content item
name|log
operator|.
name|debug
argument_list|(
literal|"++ w: {}: {}"
argument_list|,
literal|"start sync execution"
argument_list|,
name|engine
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|job
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|">> w: {}: {}"
argument_list|,
literal|"start sync execution"
argument_list|,
name|engine
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|engine
operator|.
name|computeEnhancements
argument_list|(
name|job
operator|.
name|getContentItem
argument_list|()
argument_list|)
expr_stmt|;
name|job
operator|.
name|setCompleted
argument_list|(
name|node
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|EngineException
name|e
parameter_list|)
block|{
name|job
operator|.
name|setFailed
argument_list|(
name|node
argument_list|,
name|engine
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"<< w: {}: {}"
argument_list|,
literal|"finished sync execution"
argument_list|,
name|engine
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|job
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|engineState
operator|==
name|EnhancementEngine
operator|.
name|ENHANCE_ASYNC
condition|)
block|{
try|try
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"++ n: start async execution of Engine {}"
argument_list|,
name|engine
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|engine
operator|.
name|computeEnhancements
argument_list|(
name|job
operator|.
name|getContentItem
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"++ n: finished async execution of Engine {}"
argument_list|,
name|engine
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|job
operator|.
name|setCompleted
argument_list|(
name|node
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|EngineException
name|e
parameter_list|)
block|{
name|job
operator|.
name|setFailed
argument_list|(
name|node
argument_list|,
name|engine
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|//required engine is unable to enhance the content
name|job
operator|.
name|setFailed
argument_list|(
name|node
argument_list|,
name|engine
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|//engine with that name is not available
name|job
operator|.
name|setFailed
argument_list|(
name|node
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|//(2) trigger the next actions
name|log
operator|.
name|debug
argument_list|(
literal|"++ w: {}: {}"
argument_list|,
literal|"check next after"
argument_list|,
name|engineName
argument_list|)
expr_stmt|;
name|job
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|">> w: {}: {}"
argument_list|,
literal|"check next after"
argument_list|,
name|engineName
argument_list|)
expr_stmt|;
try|try
block|{
if|if
condition|(
name|job
operator|.
name|isFinished
argument_list|()
condition|)
block|{
name|finish
argument_list|(
name|job
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|job
operator|.
name|isFailed
argument_list|()
condition|)
block|{
name|executeNextNodes
argument_list|(
name|job
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|log
operator|.
name|isInfoEnabled
argument_list|()
condition|)
block|{
name|Collection
argument_list|<
name|String
argument_list|>
name|running
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
literal|3
argument_list|)
decl_stmt|;
for|for
control|(
name|NonLiteral
name|runningNode
range|:
name|job
operator|.
name|getRunning
argument_list|()
control|)
block|{
name|running
operator|.
name|add
argument_list|(
name|getEngine
argument_list|(
name|job
operator|.
name|getExecutionPlan
argument_list|()
argument_list|,
name|runningNode
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Job {} failed, but {} still running!"
argument_list|,
name|job
operator|.
name|getContentItem
argument_list|()
operator|.
name|getUri
argument_list|()
argument_list|,
name|running
argument_list|)
expr_stmt|;
block|}
block|}
block|}
finally|finally
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"<< w: {}: {}"
argument_list|,
literal|"check next after"
argument_list|,
name|engineName
argument_list|)
expr_stmt|;
name|job
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Removes a finished job from {@link #processingJobs} and notifies      * all waiting components      * @param job the finished job      */
specifier|private
name|void
name|finish
parameter_list|(
name|EnhancementJob
name|job
parameter_list|)
block|{
name|processingLock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
name|Object
name|o
decl_stmt|;
try|try
block|{
name|o
operator|=
name|processingJobs
operator|.
name|remove
argument_list|(
name|job
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|processingLock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
synchronized|synchronized
init|(
name|o
init|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"++ n: finished processing ContentItem {} with Chain {}"
argument_list|,
name|job
operator|.
name|getContentItem
argument_list|()
operator|.
name|getUri
argument_list|()
argument_list|,
name|job
operator|.
name|getChainName
argument_list|()
argument_list|)
expr_stmt|;
name|o
operator|.
name|notifyAll
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"EnhancementJob for ContentItem {} is not "
operator|+
literal|"registered with {}. Will not send notification!"
argument_list|,
name|job
operator|.
name|getContentItem
argument_list|()
operator|.
name|getUri
argument_list|()
argument_list|,
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * triggers the execution of the next nodes or if       * {@link EnhancementJob#isFinished()} notifies the one who registered       * the {@link EnhancementJob} with this component.      * @param job the enhancement job to process      */
specifier|protected
name|void
name|executeNextNodes
parameter_list|(
name|EnhancementJob
name|job
parameter_list|)
block|{
comment|//getExecutable returns an snapshot so we do not need to lock
for|for
control|(
name|NonLiteral
name|executable
range|:
name|job
operator|.
name|getExecutable
argument_list|()
control|)
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|PROPERTY_JOB_MANAGER
argument_list|,
name|job
argument_list|)
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|PROPERTY_NODE
argument_list|,
name|executable
argument_list|)
expr_stmt|;
name|job
operator|.
name|setRunning
argument_list|(
name|executable
argument_list|)
expr_stmt|;
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"SHEDULE execution of Engine {}"
argument_list|,
name|ExecutionPlanHelper
operator|.
name|getEngine
argument_list|(
name|job
operator|.
name|getExecutionPlan
argument_list|()
argument_list|,
name|executable
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|eventAdmin
operator|.
name|postEvent
argument_list|(
operator|new
name|Event
argument_list|(
name|TOPIC_JOB_MANAGER
argument_list|,
name|properties
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

