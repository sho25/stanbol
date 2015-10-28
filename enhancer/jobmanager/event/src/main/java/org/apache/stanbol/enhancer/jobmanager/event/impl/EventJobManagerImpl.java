begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

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
name|TOPIC_JOB_MANAGER
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
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
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|Map
operator|.
name|Entry
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
name|Graph
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
name|Triple
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
name|serializedform
operator|.
name|Serializer
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
name|serializedform
operator|.
name|SupportedFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Deactivate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Reference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ReferenceCardinality
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|jobmanager
operator|.
name|event
operator|.
name|impl
operator|.
name|EnhancementJobHandler
operator|.
name|EnhancementJobObserver
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
name|Chain
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
name|ChainException
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
name|ChainManager
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
name|ContentItem
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
name|EnhancementException
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
name|EnhancementJobManager
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
name|execution
operator|.
name|Execution
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
name|execution
operator|.
name|ExecutionMetadata
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Constants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|ServiceRegistration
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
name|component
operator|.
name|ComponentContext
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
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Service
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
comment|//register with a ranking lower than 0 to allow easy overriding by specific
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|,
name|intValue
operator|=
name|EventJobManagerImpl
operator|.
name|DEFAULT_SERVICE_RANKING
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EventJobManagerImpl
operator|.
name|MAX_ENHANCEMENT_JOB_WAIT_TIME
argument_list|,
name|intValue
operator|=
name|EventJobManagerImpl
operator|.
name|DEFAULT_MAX_ENHANCEMENT_JOB_WAIT_TIME
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|EventJobManagerImpl
implements|implements
name|EnhancementJobManager
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|EventJobManagerImpl
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Logger for the {@link EnhancementJobManager} interface. This is used      * to log statistics about execution times for enhancement jobs      */
specifier|private
specifier|final
name|Logger
name|enhancementJobManagerLog
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|EnhancementJobManager
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_SERVICE_RANKING
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|MAX_ENHANCEMENT_JOB_WAIT_TIME
init|=
literal|"stanbol.maxEnhancementJobWaitTime"
decl_stmt|;
comment|/**      * default max wait time is 60sec (similar to the http timeout)      */
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_MAX_ENHANCEMENT_JOB_WAIT_TIME
init|=
literal|60
operator|*
literal|1000
decl_stmt|;
annotation|@
name|Reference
specifier|protected
name|ChainManager
name|chainManager
decl_stmt|;
annotation|@
name|Reference
specifier|protected
name|EnhancementEngineManager
name|engineManager
decl_stmt|;
annotation|@
name|Reference
specifier|protected
name|EventAdmin
name|eventAdmin
decl_stmt|;
comment|/**      * If available it is used for logging ExecutionMetadata of failed or      * timed out Enhancement Requests (OPTIONAL)      */
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_UNARY
argument_list|)
specifier|protected
name|Serializer
name|serializer
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"rawtypes"
argument_list|)
specifier|private
name|ServiceRegistration
name|jobHandlerRegistration
decl_stmt|;
specifier|private
name|EnhancementJobHandler
name|jobHandler
decl_stmt|;
specifier|private
name|int
name|maxEnhancementJobWaitTime
init|=
name|DEFAULT_MAX_ENHANCEMENT_JOB_WAIT_TIME
decl_stmt|;
comment|/**      * Instantiates and registers the {@link EnhancementJobHandler} as      * {@link EventHandler} for the topic       * {@link org.apache.stanbol.enhancer.jobmanager.event.Constants#TOPIC_JOB_MANAGER}      * @param ctx      */
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"activate {}"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|jobHandler
operator|=
operator|new
name|EnhancementJobHandler
argument_list|(
name|eventAdmin
argument_list|,
name|engineManager
argument_list|)
expr_stmt|;
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
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|event
operator|.
name|EventConstants
operator|.
name|EVENT_TOPIC
argument_list|,
name|TOPIC_JOB_MANAGER
argument_list|)
expr_stmt|;
name|jobHandlerRegistration
operator|=
name|ctx
operator|.
name|getBundleContext
argument_list|()
operator|.
name|registerService
argument_list|(
name|EventHandler
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|jobHandler
argument_list|,
name|properties
argument_list|)
expr_stmt|;
name|Object
name|maxWaitTime
init|=
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|MAX_ENHANCEMENT_JOB_WAIT_TIME
argument_list|)
decl_stmt|;
if|if
condition|(
name|maxWaitTime
operator|instanceof
name|Integer
condition|)
block|{
name|this
operator|.
name|maxEnhancementJobWaitTime
operator|=
operator|(
name|Integer
operator|)
name|maxWaitTime
expr_stmt|;
block|}
block|}
comment|/**      * Unregisters the {@link EnhancementJobHandler}      * @param ctx      */
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
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
name|EnhancementJobHandler
name|jobHandler
init|=
name|this
operator|.
name|jobHandler
decl_stmt|;
comment|//set first the field to null
name|this
operator|.
name|jobHandler
operator|=
literal|null
expr_stmt|;
comment|//and than close the instance to ensure that running jobs are shut down
comment|//correctly
name|jobHandler
operator|.
name|close
argument_list|()
expr_stmt|;
name|jobHandlerRegistration
operator|.
name|unregister
argument_list|()
expr_stmt|;
name|jobHandlerRegistration
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|enhanceContent
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EnhancementException
block|{
name|Chain
name|defaultChain
init|=
name|chainManager
operator|.
name|getDefault
argument_list|()
decl_stmt|;
if|if
condition|(
name|defaultChain
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ChainException
argument_list|(
literal|"Unable to enhance ContentItem '"
operator|+
name|ci
operator|.
name|getUri
argument_list|()
operator|+
literal|"' because currently no enhancement chain is active. Please"
operator|+
literal|"configure a Chain or enable the default chain"
argument_list|)
throw|;
block|}
name|enhanceContent
argument_list|(
name|ci
argument_list|,
name|defaultChain
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|enhanceContent
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|Chain
name|chain
parameter_list|)
throws|throws
name|EnhancementException
block|{
if|if
condition|(
name|ci
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed contentItem MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|chain
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unable to enhance ContentItem '"
operator|+
name|ci
operator|.
name|getUri
argument_list|()
operator|+
literal|"' because NULL was passed as enhancement chain"
argument_list|)
throw|;
block|}
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|enhancementJobManagerLog
operator|.
name|debug
argument_list|(
literal|">> enhance {} with chain {}"
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|chain
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|isDefaultChain
init|=
name|chain
operator|.
name|equals
argument_list|(
name|chainManager
operator|.
name|getDefault
argument_list|()
argument_list|)
decl_stmt|;
name|EnhancementJob
name|job
init|=
operator|new
name|EnhancementJob
argument_list|(
name|ci
argument_list|,
name|chain
operator|.
name|getName
argument_list|()
argument_list|,
name|chain
operator|.
name|getExecutionPlan
argument_list|()
argument_list|,
name|isDefaultChain
argument_list|)
decl_stmt|;
comment|//start the execution
comment|//wait for the results
name|EnhancementJobObserver
name|observer
init|=
name|jobHandler
operator|.
name|register
argument_list|(
name|job
argument_list|)
decl_stmt|;
comment|//now wait for the execution to finish for the configured maximum time
name|boolean
name|completed
init|=
name|observer
operator|.
name|waitForCompletion
argument_list|(
name|maxEnhancementJobWaitTime
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|completed
condition|)
block|{
comment|//throw timeout exception
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"Status:\n"
argument_list|)
decl_stmt|;
name|ExecutionMetadata
name|em
init|=
name|ExecutionMetadata
operator|.
name|parseFrom
argument_list|(
name|job
operator|.
name|getExecutionMetadata
argument_list|()
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|Execution
argument_list|>
name|ex
range|:
name|em
operator|.
name|getEngineExecutions
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"  -"
argument_list|)
operator|.
name|append
argument_list|(
name|ex
operator|.
name|getKey
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|": "
argument_list|)
operator|.
name|append
argument_list|(
name|ex
operator|.
name|getValue
argument_list|()
operator|.
name|getStatus
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
throw|throw
operator|new
name|ChainException
argument_list|(
literal|"Execution timeout after "
operator|+
operator|(
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
operator|)
operator|/
literal|1000f
operator|)
operator|+
literal|"sec (timeout:"
operator|+
operator|(
name|maxEnhancementJobWaitTime
operator|/
literal|1000
operator|)
operator|+
literal|"sec) for ContentItem "
operator|+
name|ci
operator|.
name|getUri
argument_list|()
operator|+
literal|"\n"
operator|+
name|sb
operator|.
name|toString
argument_list|()
operator|+
literal|" \n To change the timeout change value of property '"
operator|+
name|MAX_ENHANCEMENT_JOB_WAIT_TIME
operator|+
literal|"' for the service "
operator|+
name|getClass
argument_list|()
argument_list|)
throw|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"Execution of Chain {} {} after {}ms for ContentItem {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|chain
operator|.
name|getName
argument_list|()
block|,
name|job
operator|.
name|isFailed
argument_list|()
condition|?
literal|"failed"
else|:
literal|"finished"
block|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
block|,
name|job
operator|.
name|getContentItem
argument_list|()
operator|.
name|getUri
argument_list|()
block|}
argument_list|)
expr_stmt|;
comment|//NOTE: ExecutionMetadata are not added to the metadata of the ContentItem
comment|//      by the EnhancementJobManager.
comment|//      However one could add this as an optional feature to the
comment|//      RESTful interface of the Enhancer!
comment|//ci.getMetadata().addAll(job.getExecutionMetadata());
if|if
condition|(
name|job
operator|.
name|isFailed
argument_list|()
condition|)
block|{
name|Exception
name|e
init|=
name|job
operator|.
name|getError
argument_list|()
decl_stmt|;
name|EnhancementJobHandler
operator|.
name|logJobInfo
argument_list|(
name|log
argument_list|,
name|job
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|logExecutionMetadata
argument_list|(
name|enhancementJobManagerLog
argument_list|,
name|job
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"ExecutionMetadata: "
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|job
operator|.
name|getExecutionMetadata
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
name|log
operator|.
name|warn
argument_list|(
name|it
operator|.
name|next
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
control|)
empty_stmt|;
if|if
condition|(
name|e
operator|instanceof
name|SecurityException
condition|)
block|{
throw|throw
operator|(
name|SecurityException
operator|)
name|e
throw|;
block|}
elseif|else
if|if
condition|(
name|e
operator|instanceof
name|EnhancementException
condition|)
block|{
throw|throw
operator|(
name|EnhancementException
operator|)
name|e
throw|;
block|}
else|else
block|{
throw|throw
operator|new
name|ChainException
argument_list|(
name|job
operator|.
name|getErrorMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
if|if
condition|(
operator|!
name|job
operator|.
name|isFinished
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Execution finished, but Job is not finished!"
argument_list|)
expr_stmt|;
name|EnhancementJobHandler
operator|.
name|logJobInfo
argument_list|(
name|log
argument_list|,
name|job
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|logExecutionMetadata
argument_list|(
name|log
argument_list|,
name|job
argument_list|,
literal|true
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|ChainException
argument_list|(
literal|"EnhancementJobManager was deactivated while"
operator|+
literal|" enhancing the passed ContentItem "
operator|+
name|job
operator|.
name|getContentItem
argument_list|()
operator|+
literal|" (EnhancementJobManager type: "
operator|+
name|getClass
argument_list|()
operator|+
literal|")"
argument_list|)
throw|;
block|}
else|else
block|{
comment|//log infos about the execution times to the enhancementJobManager
name|EnhancementJobHandler
operator|.
name|logExecutionTimes
argument_list|(
name|enhancementJobManagerLog
argument_list|,
name|job
argument_list|)
expr_stmt|;
name|logExecutionMetadata
argument_list|(
name|enhancementJobManagerLog
argument_list|,
name|job
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Logs the ExecutionMetadata       * @param logger the logger to log the execution metadata to      * @param job the enhancement job to log the execution metadata for      * @param isWarn if<code>true</code> the data are logged with<code>WARN</code> level.      * If<code>false</code> the<code>DEBUG</code> level is used      */
specifier|protected
name|void
name|logExecutionMetadata
parameter_list|(
name|Logger
name|logger
parameter_list|,
name|EnhancementJob
name|job
parameter_list|,
name|boolean
name|isWarn
parameter_list|)
block|{
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
operator|||
operator|(
name|isWarn
operator|&&
name|log
operator|.
name|isWarnEnabled
argument_list|()
operator|)
condition|)
block|{
name|StringBuilder
name|message
init|=
operator|new
name|StringBuilder
argument_list|(
literal|1024
argument_list|)
decl_stmt|;
name|message
operator|.
name|append
argument_list|(
literal|"ExecutionMetadata for ContentItem "
argument_list|)
operator|.
name|append
argument_list|(
name|job
operator|.
name|getContentItem
argument_list|()
operator|.
name|getUri
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|" and Chain "
argument_list|)
operator|.
name|append
argument_list|(
name|job
operator|.
name|getChainName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|": \n"
argument_list|)
expr_stmt|;
name|boolean
name|serialized
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|serializer
operator|!=
literal|null
condition|)
block|{
name|ByteArrayOutputStream
name|bout
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
try|try
block|{
name|serializer
operator|.
name|serialize
argument_list|(
name|bout
argument_list|,
name|job
operator|.
name|getExecutionMetadata
argument_list|()
argument_list|,
name|SupportedFormat
operator|.
name|TURTLE
argument_list|)
expr_stmt|;
name|message
operator|.
name|append
argument_list|(
name|bout
operator|.
name|toString
argument_list|(
literal|"utf-8"
argument_list|)
argument_list|)
expr_stmt|;
name|serialized
operator|=
literal|true
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"   ... unable to serialize Execution Metadata | {}: {}"
argument_list|,
name|e
operator|.
name|getClass
argument_list|()
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"   ... unable to serialize Execution Metadata | {}: {}"
argument_list|,
name|e
operator|.
name|getClass
argument_list|()
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|serialized
condition|)
block|{
comment|//TURTLE serialization not possible ... use the toString method of triple
for|for
control|(
name|Triple
name|t
range|:
name|job
operator|.
name|getExecutionMetadata
argument_list|()
control|)
block|{
name|message
operator|.
name|append
argument_list|(
name|t
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
block|}
comment|//finally write the serialized graph to the logger
if|if
condition|(
name|isWarn
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
name|message
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|debug
argument_list|(
name|message
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|EnhancementEngine
argument_list|>
name|getActiveEngines
parameter_list|()
block|{
comment|//This implementation return the list of active engined for the default
comment|//Chain in the order they would be executed
name|Chain
name|defaultChain
init|=
name|chainManager
operator|.
name|getDefault
argument_list|()
decl_stmt|;
if|if
condition|(
name|defaultChain
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Currently no enhancement chain is "
operator|+
literal|"active. Please configure a Chain or enable the default chain"
argument_list|)
throw|;
block|}
name|Graph
name|ep
decl_stmt|;
try|try
block|{
name|ep
operator|=
name|defaultChain
operator|.
name|getExecutionPlan
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ChainException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to get Execution Plan for "
operator|+
literal|"default enhancement chain (name: '"
operator|+
name|defaultChain
operator|.
name|getName
argument_list|()
operator|+
literal|"'| class: '"
operator|+
name|defaultChain
operator|.
name|getClass
argument_list|()
operator|+
literal|"')!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
return|return
name|ExecutionPlanHelper
operator|.
name|getActiveEngines
argument_list|(
name|engineManager
argument_list|,
name|ep
argument_list|)
return|;
block|}
block|}
end_class

end_unit

