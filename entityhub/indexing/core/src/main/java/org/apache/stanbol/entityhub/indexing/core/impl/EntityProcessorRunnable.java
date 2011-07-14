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
name|entityhub
operator|.
name|indexing
operator|.
name|core
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|impl
operator|.
name|IndexerConstants
operator|.
name|PROCESS_COMPLETE
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|impl
operator|.
name|IndexerConstants
operator|.
name|PROCESS_DURATION
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|impl
operator|.
name|IndexerConstants
operator|.
name|PROCESS_STARTED
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|BlockingQueue
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|EntityProcessor
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
import|;
end_import

begin_comment
comment|/**  * Consumes Representations as created by the IndexingSource and processes  * it by using the configured {@link EntityProcessor}. In addition this  * components adds configured keys to the Representation.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|EntityProcessorRunnable
extends|extends
name|IndexingDaemon
argument_list|<
name|Representation
argument_list|,
name|Representation
argument_list|>
block|{
specifier|private
specifier|final
name|EntityProcessor
name|processor
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|keys
decl_stmt|;
specifier|public
name|EntityProcessorRunnable
parameter_list|(
name|String
name|name
parameter_list|,
name|BlockingQueue
argument_list|<
name|QueueItem
argument_list|<
name|Representation
argument_list|>
argument_list|>
name|consume
parameter_list|,
name|BlockingQueue
argument_list|<
name|QueueItem
argument_list|<
name|Representation
argument_list|>
argument_list|>
name|produce
parameter_list|,
name|BlockingQueue
argument_list|<
name|QueueItem
argument_list|<
name|IndexingError
argument_list|>
argument_list|>
name|error
parameter_list|,
name|EntityProcessor
name|processor
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|keys
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|IndexerConstants
operator|.
name|SEQUENCE_NUMBER_PROCESSOR_DAEMON
argument_list|,
name|consume
argument_list|,
name|produce
argument_list|,
name|error
argument_list|)
expr_stmt|;
name|this
operator|.
name|processor
operator|=
name|processor
expr_stmt|;
if|if
condition|(
name|keys
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|keys
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|keys
operator|=
name|keys
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
while|while
condition|(
operator|!
name|isQueueFinished
argument_list|()
condition|)
block|{
name|QueueItem
argument_list|<
name|Representation
argument_list|>
name|item
init|=
name|consume
argument_list|()
decl_stmt|;
if|if
condition|(
name|item
operator|!=
literal|null
condition|)
block|{
name|Long
name|start
init|=
name|Long
operator|.
name|valueOf
argument_list|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
argument_list|)
decl_stmt|;
name|item
operator|.
name|setProperty
argument_list|(
name|PROCESS_STARTED
argument_list|,
name|start
argument_list|)
expr_stmt|;
name|Representation
name|processed
init|=
name|processor
operator|.
name|process
argument_list|(
name|item
operator|.
name|getItem
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|processed
operator|==
literal|null
condition|)
block|{
name|sendError
argument_list|(
name|item
operator|.
name|getItem
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|item
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"Processor %s returned NULL for Entity %s"
argument_list|,
name|processor
argument_list|,
name|item
operator|.
name|getItem
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|String
name|key
range|:
name|keys
control|)
block|{
comment|//consume the property and add it to the
comment|//transformed representation
name|Object
name|value
init|=
name|item
operator|.
name|removeProperty
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|processed
operator|.
name|add
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
name|QueueItem
argument_list|<
name|Representation
argument_list|>
name|produced
init|=
operator|new
name|QueueItem
argument_list|<
name|Representation
argument_list|>
argument_list|(
name|processed
argument_list|,
name|item
argument_list|)
decl_stmt|;
name|Long
name|completed
init|=
name|Long
operator|.
name|valueOf
argument_list|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
argument_list|)
decl_stmt|;
name|produced
operator|.
name|setProperty
argument_list|(
name|PROCESS_COMPLETE
argument_list|,
name|completed
argument_list|)
expr_stmt|;
name|produced
operator|.
name|setProperty
argument_list|(
name|PROCESS_DURATION
argument_list|,
name|Float
operator|.
name|valueOf
argument_list|(
call|(
name|float
call|)
argument_list|(
name|completed
operator|.
name|longValue
argument_list|()
operator|-
name|start
operator|.
name|longValue
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|produce
argument_list|(
name|produced
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|setFinished
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

