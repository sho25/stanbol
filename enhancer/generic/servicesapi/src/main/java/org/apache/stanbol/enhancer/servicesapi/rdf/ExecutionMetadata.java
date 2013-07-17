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
name|servicesapi
operator|.
name|rdf
package|;
end_package

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
name|UriRef
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

begin_comment
comment|/**  * Defined constants for Classes and Properties defined by the  * {@link NamespaceEnum#em Execution Metadata ontology} used by the Stanbol  * Enhancer to describe metadata about the enhancement process.  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ExecutionMetadata
block|{
specifier|private
name|ExecutionMetadata
parameter_list|()
block|{}
empty_stmt|;
comment|/**      * Class representing an execution of a {@link Chain} or an {@link EnhancementEngine}.      * This is considered an abstract concept. Use {@link #CHAIN_EXECUTION} or      * {@link #ENGINE_EXECUTION} depending on the type of the executed component.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|EXECUTION
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"Execution"
argument_list|)
decl_stmt|;
comment|/**      * Property that links {@link #EXECUTION} to its parent       * {@link #CHAIN_EXECUTION}.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|EXECUTION_PART
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"executionPart"
argument_list|)
decl_stmt|;
comment|/**      * The current status of an {@link #EXECUTION}. Values are expected to be      * one of {@link #EXECUTION_STATUS}.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|STATUS
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"status"
argument_list|)
decl_stmt|;
comment|/**      * The 'xsd:startTime' when an {@link #EXECUTION} started      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|STARTED
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"started"
argument_list|)
decl_stmt|;
comment|/**      * The 'xsd:dateTime' when an {@link #EXECUTION} execution completed or      * failed.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|COMPLETED
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"completed"
argument_list|)
decl_stmt|;
comment|/**      * Allows to add a status message to a {@link #EXECUTION} node.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|STATUS_MESSAGE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"statusMessage"
argument_list|)
decl_stmt|;
comment|/**      * Class representing the execution of a {@link Chain}. This class is a       * sub-class of {@link #EXECUTION}      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|CHAIN_EXECUTION
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"ChainExecution"
argument_list|)
decl_stmt|;
comment|/**      * Property indicating if the {@link ExecutionPlan#EXECUTION_PLAN} executed      * by a {@link #CHAIN_EXECUTION} was the {@link ChainManager#getDefault()}      * {@link Chain} at that time. Values are expected to be of data type      * 'xsd:boolean'.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|IS_DEFAULT_CHAIN
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"defualtChain"
argument_list|)
decl_stmt|;
comment|/**      * Property that links from the {@link #CHAIN_EXECUTION} to the      * {@link ExecutionPlan#EXECUTION_PLAN}      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|EXECUTION_PLAN
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"executionPlan"
argument_list|)
decl_stmt|;
comment|/**      * Property that links from the {@link #CHAIN_EXECUTION} node to the      * enhanced {@link ContentItem#getUri()}      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|ENHANCES
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"enhances"
argument_list|)
decl_stmt|;
comment|/**      * Property that links from {@link ContentItem#getUri()} to the       * {@link #CHAIN_EXECUTION} defining the root node of the execution metadata      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|ENHANCED_BY
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"enhancedBy"
argument_list|)
decl_stmt|;
comment|/**      * Class that represents the execution of an {@link EnhancementEngine}.      *  This is a sub-class of {@link #EXECUTION}.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|ENGINE_EXECUTION
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"EngineExecution"
argument_list|)
decl_stmt|;
comment|/**      * Property that links from the {@link #ENGINE_EXECUTION} to the      * {@link ExecutionPlan#EXECUTION_NODE}      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|EXECUTION_NODE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"executionNode"
argument_list|)
decl_stmt|;
comment|/**      * Type for all ExecutionStatus values: {@link #STATUS_SCHEDULED},      * {@link #STATUS_IN_PROGRESS}, {@link #STATUS_COMPLETED}, {@link #STATUS_SKIPPED},      * {@link #STATUS_FAILED}.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|EXECUTION_STATUS
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"ExecutionStatus"
argument_list|)
decl_stmt|;
comment|/**      * em:ExecutionStatus indicating that the execution is scheduled, but has not yet started      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|STATUS_SCHEDULED
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"StatusSheduled"
argument_list|)
decl_stmt|;
comment|/**      * em:ExecutionStatus indicating that the execution was skipped       */
specifier|public
specifier|static
specifier|final
name|UriRef
name|STATUS_SKIPPED
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"StatusSkipped"
argument_list|)
decl_stmt|;
comment|/**      * em:ExecutionStatus indicating that the execution is in progress      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|STATUS_IN_PROGRESS
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"StatusInProgress"
argument_list|)
decl_stmt|;
comment|/**      * em:ExecutionStatus indicating that the execution has completed successfully      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|STATUS_COMPLETED
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"StatusCompleted"
argument_list|)
decl_stmt|;
comment|/**      * em:ExecutionStatus indicating that the execution has failed      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|STATUS_FAILED
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|em
operator|+
literal|"StatusFailed"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

