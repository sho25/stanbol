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
name|SOURCE_DURATION
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
name|SOURCE_STARTED
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
name|STORE_DURATION
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStreamWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|commons
operator|.
name|io
operator|.
name|IOUtils
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

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_class
specifier|public
class|class
name|FinishedEntityDaemon
extends|extends
name|IndexingDaemon
argument_list|<
name|Representation
argument_list|,
name|Object
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|int
name|DEFAULT_MAJOR_INTERVAL
init|=
literal|100000
decl_stmt|;
comment|/**      * For now use an logger as output!      */
specifier|private
name|Logger
name|out
decl_stmt|;
specifier|private
name|int
name|major
decl_stmt|;
specifier|private
name|int
name|minor
decl_stmt|;
specifier|private
name|double
name|sourceDurationAll
decl_stmt|;
specifier|private
name|double
name|processDurationAll
decl_stmt|;
specifier|private
name|double
name|storeDurationAll
decl_stmt|;
specifier|private
name|double
name|durationAll
decl_stmt|;
specifier|private
name|double
name|sourceDurationMajor
decl_stmt|;
specifier|private
name|double
name|processDurationMajor
decl_stmt|;
specifier|private
name|double
name|storeDurationMajor
decl_stmt|;
specifier|private
name|double
name|durationMajor
decl_stmt|;
specifier|private
name|double
name|durationMinor
decl_stmt|;
specifier|private
name|long
name|start
decl_stmt|;
specifier|private
name|long
name|startMajor
decl_stmt|;
specifier|private
name|long
name|startMinor
decl_stmt|;
specifier|private
name|double
name|timeAll
decl_stmt|;
specifier|private
name|double
name|timeMajor
decl_stmt|;
specifier|private
name|double
name|timeMinor
decl_stmt|;
specifier|private
name|long
name|count
decl_stmt|;
specifier|private
name|long
name|countedAll
decl_stmt|;
specifier|private
name|long
name|countedMajor
decl_stmt|;
specifier|private
name|long
name|countedMinor
decl_stmt|;
comment|/**      * Allows to write finished ids to a file. one ID per line      */
specifier|private
specifier|final
name|BufferedWriter
name|idWriter
decl_stmt|;
comment|/**      * The charset used for the {@link #idWriter}      */
specifier|private
specifier|static
specifier|final
name|Charset
name|UTF8
init|=
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
decl_stmt|;
specifier|public
name|FinishedEntityDaemon
parameter_list|(
name|BlockingQueue
argument_list|<
name|QueueItem
argument_list|<
name|Representation
argument_list|>
argument_list|>
name|consume
parameter_list|,
name|int
name|majorInterval
parameter_list|,
name|Logger
name|out
parameter_list|,
name|OutputStream
name|idOut
parameter_list|)
block|{
name|super
argument_list|(
literal|"Indexing: Finished Entity Logger Deamon"
argument_list|,
name|IndexerConstants
operator|.
name|SEQUENCE_NUMBER_FINISHED_DAEMON
argument_list|,
name|consume
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|this
operator|.
name|out
operator|=
name|out
expr_stmt|;
if|if
condition|(
name|majorInterval
operator|>
literal|0
condition|)
block|{
name|this
operator|.
name|major
operator|=
name|majorInterval
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|major
operator|=
name|DEFAULT_MAJOR_INTERVAL
expr_stmt|;
block|}
name|this
operator|.
name|minor
operator|=
name|major
operator|/
literal|10
expr_stmt|;
if|if
condition|(
name|idOut
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|idWriter
operator|=
operator|new
name|BufferedWriter
argument_list|(
operator|new
name|OutputStreamWriter
argument_list|(
name|idOut
argument_list|,
name|UTF8
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|idWriter
operator|=
literal|null
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
name|count
operator|=
literal|0
expr_stmt|;
comment|//Elements indexed
comment|//Elements with valid statistics
name|countedAll
operator|=
literal|0
expr_stmt|;
name|countedMajor
operator|=
literal|0
expr_stmt|;
name|countedMinor
operator|=
literal|0
expr_stmt|;
name|long
name|current
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
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
if|if
condition|(
name|idWriter
operator|!=
literal|null
operator|&&
name|item
operator|.
name|getItem
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|String
name|id
init|=
name|item
operator|.
name|getItem
argument_list|()
operator|.
name|getId
argument_list|()
decl_stmt|;
try|try
block|{
if|if
condition|(
name|count
operator|!=
literal|0
condition|)
block|{
name|idWriter
operator|.
name|newLine
argument_list|()
expr_stmt|;
block|}
name|idWriter
operator|.
name|write
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Exception while logging ID of indexed Entity '"
operator|+
name|id
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
name|current
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
if|if
condition|(
name|count
operator|==
literal|0
condition|)
block|{
name|start
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
comment|//default for the start!
block|}
if|if
condition|(
name|startMajor
operator|==
literal|0
condition|)
block|{
name|startMajor
operator|=
name|current
expr_stmt|;
block|}
if|if
condition|(
name|startMinor
operator|==
literal|0
condition|)
block|{
name|startMinor
operator|=
name|current
expr_stmt|;
block|}
name|count
operator|++
expr_stmt|;
try|try
block|{
name|long
name|startSource
init|=
operator|(
operator|(
name|Long
operator|)
name|item
operator|.
name|getProperty
argument_list|(
name|SOURCE_STARTED
argument_list|)
operator|)
operator|.
name|longValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|count
operator|<
name|minor
condition|)
block|{
comment|//for the first few item
comment|//try to get the correct start time for the indexing!
if|if
condition|(
name|startSource
operator|<
name|start
condition|)
block|{
name|start
operator|=
name|startSource
expr_stmt|;
block|}
block|}
name|float
name|sourceDuration
init|=
operator|(
operator|(
name|Float
operator|)
name|item
operator|.
name|getProperty
argument_list|(
name|SOURCE_DURATION
argument_list|)
operator|)
operator|.
name|floatValue
argument_list|()
decl_stmt|;
name|float
name|processDuration
init|=
operator|(
operator|(
name|Float
operator|)
name|item
operator|.
name|getProperty
argument_list|(
name|PROCESS_DURATION
argument_list|)
operator|)
operator|.
name|floatValue
argument_list|()
decl_stmt|;
name|float
name|storeDuration
init|=
operator|(
operator|(
name|Float
operator|)
name|item
operator|.
name|getProperty
argument_list|(
name|STORE_DURATION
argument_list|)
operator|)
operator|.
name|floatValue
argument_list|()
decl_stmt|;
name|sourceDurationAll
operator|+=
name|sourceDuration
expr_stmt|;
name|sourceDurationMajor
operator|+=
name|sourceDuration
expr_stmt|;
name|processDurationAll
operator|+=
name|processDuration
expr_stmt|;
name|processDurationMajor
operator|+=
name|processDuration
expr_stmt|;
name|storeDurationAll
operator|+=
name|storeDuration
expr_stmt|;
name|storeDurationMajor
operator|+=
name|storeDuration
expr_stmt|;
name|double
name|duration
init|=
name|sourceDuration
operator|+
name|processDuration
operator|+
name|storeDuration
decl_stmt|;
name|durationAll
operator|+=
name|duration
expr_stmt|;
name|durationMajor
operator|+=
name|duration
expr_stmt|;
name|durationMinor
operator|+=
name|duration
expr_stmt|;
name|long
name|time
init|=
name|current
operator|-
name|startSource
decl_stmt|;
name|timeAll
operator|+=
name|time
expr_stmt|;
name|timeMajor
operator|+=
name|time
expr_stmt|;
name|timeMinor
operator|+=
name|time
expr_stmt|;
name|countedAll
operator|++
expr_stmt|;
name|countedMajor
operator|++
expr_stmt|;
name|countedMinor
operator|++
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|//ignore NullpointerExceptions that will be thrown on missing
comment|//metadata!
block|}
if|if
condition|(
name|count
operator|%
name|major
operator|==
literal|0
condition|)
block|{
name|printMajor
argument_list|(
name|current
argument_list|)
expr_stmt|;
name|sourceDurationMajor
operator|=
literal|0
expr_stmt|;
name|processDurationMajor
operator|=
literal|0
expr_stmt|;
name|storeDurationMajor
operator|=
literal|0
expr_stmt|;
name|durationMajor
operator|=
literal|0
expr_stmt|;
name|timeMajor
operator|=
literal|0
expr_stmt|;
name|countedMajor
operator|=
literal|0
expr_stmt|;
name|startMajor
operator|=
literal|0
expr_stmt|;
comment|//reset also minor
name|durationMinor
operator|=
literal|0
expr_stmt|;
name|timeMinor
operator|=
literal|0
expr_stmt|;
name|countedMinor
operator|=
literal|0
expr_stmt|;
name|startMinor
operator|=
literal|0
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|count
operator|%
name|minor
operator|==
literal|0
condition|)
block|{
name|printMinor
argument_list|(
name|current
argument_list|)
expr_stmt|;
name|durationMinor
operator|=
literal|0
expr_stmt|;
name|timeMinor
operator|=
literal|0
expr_stmt|;
name|countedMinor
operator|=
literal|0
expr_stmt|;
name|startMinor
operator|=
literal|0
expr_stmt|;
block|}
block|}
block|}
name|printSummary
argument_list|(
name|current
argument_list|)
expr_stmt|;
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|idWriter
argument_list|)
expr_stmt|;
name|setFinished
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|printMinor
parameter_list|(
name|long
name|current
parameter_list|)
block|{
name|long
name|interval
init|=
name|current
operator|-
name|start
decl_stmt|;
name|long
name|intervalMinor
init|=
name|current
operator|-
name|startMinor
decl_stmt|;
comment|//        double itemDurationAll = countedAll>0?durationAll/countedAll:-1;
name|double
name|itemDurationMinor
init|=
name|countedMinor
operator|>
literal|0
condition|?
name|durationMinor
operator|/
name|countedMinor
else|:
operator|-
literal|1
decl_stmt|;
comment|//        double itemTimeAll = countedAll>0?timeAll/countedAll:-1;
name|double
name|itemTimeMinor
init|=
name|countedMinor
operator|>
literal|0
condition|?
name|timeMinor
operator|/
name|countedMinor
else|:
operator|-
literal|1
decl_stmt|;
name|out
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"    - %d items in %dsec (last %d in %dsec | %7.3fms/item | %7.3fms in queue)"
argument_list|,
name|count
argument_list|,
operator|(
name|int
operator|)
name|interval
operator|/
literal|1000
argument_list|,
name|minor
argument_list|,
operator|(
name|int
operator|)
name|intervalMinor
operator|/
literal|1000
argument_list|,
name|itemDurationMinor
argument_list|,
name|itemTimeMinor
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|printMajor
parameter_list|(
name|long
name|current
parameter_list|)
block|{
name|long
name|interval
init|=
name|current
operator|-
name|start
decl_stmt|;
name|long
name|intervalMajor
init|=
name|current
operator|-
name|startMajor
decl_stmt|;
name|double
name|itemDurationAll
init|=
name|countedAll
operator|>
literal|0
condition|?
name|durationAll
operator|/
name|countedAll
else|:
operator|-
literal|1
decl_stmt|;
name|double
name|itemDurationMajor
init|=
name|countedMajor
operator|>
literal|0
condition|?
name|durationMinor
operator|/
name|countedMajor
else|:
operator|-
literal|1
decl_stmt|;
name|double
name|itemTimeAll
init|=
name|countedAll
operator|>
literal|0
condition|?
name|timeAll
operator|/
name|countedAll
else|:
operator|-
literal|1
decl_stmt|;
name|double
name|itemTimeMajor
init|=
name|countedMajor
operator|>
literal|0
condition|?
name|timeMajor
operator|/
name|countedMajor
else|:
operator|-
literal|1
decl_stmt|;
name|double
name|itemSourceDurationAll
init|=
name|countedAll
operator|>
literal|0
condition|?
name|sourceDurationAll
operator|/
name|countedAll
else|:
operator|-
literal|1
decl_stmt|;
name|double
name|itemProcessingDurationAll
init|=
name|countedAll
operator|>
literal|0
condition|?
name|processDurationAll
operator|/
name|countedAll
else|:
operator|-
literal|1
decl_stmt|;
name|double
name|itemStoreDurationAll
init|=
name|countedAll
operator|>
literal|0
condition|?
name|storeDurationAll
operator|/
name|countedAll
else|:
operator|-
literal|1
decl_stmt|;
name|double
name|itemSourceDurationMajor
init|=
name|countedMajor
operator|>
literal|0
condition|?
name|sourceDurationMajor
operator|/
name|countedMajor
else|:
operator|-
literal|1
decl_stmt|;
name|double
name|itemProcessingDurationMajor
init|=
name|countedMajor
operator|>
literal|0
condition|?
name|processDurationMajor
operator|/
name|countedMajor
else|:
operator|-
literal|1
decl_stmt|;
name|double
name|itemStoreDurationMajor
init|=
name|countedMajor
operator|>
literal|0
condition|?
name|storeDurationMajor
operator|/
name|countedMajor
else|:
operator|-
literal|1
decl_stmt|;
name|out
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"+ %d items in %dsec (%7.3fms/item): processing: %7.3fms/item | queue: %7.3fms"
argument_list|,
name|count
argument_list|,
operator|(
name|int
operator|)
name|interval
operator|/
literal|1000
argument_list|,
operator|(
name|float
operator|)
name|interval
operator|/
name|count
argument_list|,
name|itemDurationAll
argument_list|,
name|itemTimeAll
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"  last %d items in %dsec (%7.3fms/item): processing %7.3fms/item | queue: %7.3fms"
argument_list|,
name|major
argument_list|,
operator|(
name|int
operator|)
name|intervalMajor
operator|/
literal|1000
argument_list|,
operator|(
name|float
operator|)
name|intervalMajor
operator|/
name|major
argument_list|,
name|itemDurationMajor
argument_list|,
name|itemTimeMajor
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"  - source   : all: %7.3fms/item | current: %7.3fms/item"
argument_list|,
name|itemSourceDurationAll
argument_list|,
name|itemSourceDurationMajor
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"  - processing: all: %7.3fms | current: %7.3fms/item"
argument_list|,
name|itemProcessingDurationAll
argument_list|,
name|itemProcessingDurationMajor
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"  - store     : all: %7.3fms | current: %7.3fms/item"
argument_list|,
name|itemStoreDurationAll
argument_list|,
name|itemStoreDurationMajor
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|printSummary
parameter_list|(
name|long
name|current
parameter_list|)
block|{
name|long
name|interval
init|=
name|current
operator|-
name|start
decl_stmt|;
name|double
name|itemDurationAll
init|=
name|countedAll
operator|>
literal|0
condition|?
name|durationAll
operator|/
name|countedAll
else|:
operator|-
literal|1
decl_stmt|;
name|double
name|itemTimeAll
init|=
name|countedAll
operator|>
literal|0
condition|?
name|timeAll
operator|/
name|countedAll
else|:
operator|-
literal|1
decl_stmt|;
name|double
name|itemSourceDurationAll
init|=
name|countedAll
operator|>
literal|0
condition|?
name|sourceDurationAll
operator|/
name|countedAll
else|:
operator|-
literal|1
decl_stmt|;
name|double
name|itemProcessingDurationAll
init|=
name|countedAll
operator|>
literal|0
condition|?
name|processDurationAll
operator|/
name|countedAll
else|:
operator|-
literal|1
decl_stmt|;
name|double
name|itemStoreDurationAll
init|=
name|countedAll
operator|>
literal|0
condition|?
name|sourceDurationAll
operator|/
name|countedAll
else|:
operator|-
literal|1
decl_stmt|;
name|out
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Indexed %d items in %dsec (%7.3fms/item): processing: %7.3fms/item | queue: %7.3fms"
argument_list|,
name|count
argument_list|,
operator|(
name|int
operator|)
name|interval
operator|/
literal|1000
argument_list|,
operator|(
name|float
operator|)
name|interval
operator|/
name|count
argument_list|,
name|itemDurationAll
argument_list|,
name|itemTimeAll
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"  - source   : %7.3fms/item"
argument_list|,
name|itemSourceDurationAll
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"  - processing: %7.3fms/item"
argument_list|,
name|itemProcessingDurationAll
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"  - store     : %7.3fms/item"
argument_list|,
name|itemStoreDurationAll
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

