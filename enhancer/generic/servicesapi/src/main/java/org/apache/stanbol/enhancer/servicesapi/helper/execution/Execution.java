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
name|helper
operator|.
name|execution
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
name|servicesapi
operator|.
name|helper
operator|.
name|EnhancementEngineHelper
operator|.
name|getReference
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|TripleCollection
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
name|helper
operator|.
name|ExecutionMetadataHelper
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
name|rdf
operator|.
name|ExecutionMetadata
import|;
end_import

begin_comment
comment|/**  * The Execution of an EnhancementEngine as defined by the {@link #getExecutionNode()}  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|Execution
implements|implements
name|Comparable
argument_list|<
name|Execution
argument_list|>
block|{
specifier|protected
specifier|final
name|NonLiteral
name|node
decl_stmt|;
specifier|private
specifier|final
name|ExecutionNode
name|executionNode
decl_stmt|;
specifier|private
specifier|final
name|UriRef
name|status
decl_stmt|;
specifier|protected
specifier|final
name|TripleCollection
name|graph
decl_stmt|;
specifier|private
specifier|final
name|Date
name|started
decl_stmt|;
specifier|private
specifier|final
name|Date
name|completed
decl_stmt|;
specifier|private
specifier|final
name|Long
name|duration
decl_stmt|;
specifier|private
specifier|final
name|ChainExecution
name|chain
decl_stmt|;
specifier|public
name|Execution
parameter_list|(
name|ChainExecution
name|parent
parameter_list|,
name|TripleCollection
name|graph
parameter_list|,
name|NonLiteral
name|node
parameter_list|)
block|{
name|this
operator|.
name|chain
operator|=
name|parent
expr_stmt|;
name|this
operator|.
name|graph
operator|=
name|graph
expr_stmt|;
name|this
operator|.
name|node
operator|=
name|node
expr_stmt|;
name|NonLiteral
name|executionNode
init|=
name|ExecutionMetadataHelper
operator|.
name|getExecutionNode
argument_list|(
name|graph
argument_list|,
name|node
argument_list|)
decl_stmt|;
if|if
condition|(
name|executionNode
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|executionNode
operator|=
operator|new
name|ExecutionNode
argument_list|(
name|graph
argument_list|,
name|executionNode
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|executionNode
operator|=
literal|null
expr_stmt|;
block|}
name|this
operator|.
name|status
operator|=
name|getReference
argument_list|(
name|graph
argument_list|,
name|node
argument_list|,
name|ExecutionMetadata
operator|.
name|STATUS
argument_list|)
expr_stmt|;
name|this
operator|.
name|started
operator|=
name|ExecutionMetadataHelper
operator|.
name|getStarted
argument_list|(
name|graph
argument_list|,
name|node
argument_list|)
expr_stmt|;
name|this
operator|.
name|completed
operator|=
name|ExecutionMetadataHelper
operator|.
name|getCompleted
argument_list|(
name|graph
argument_list|,
name|node
argument_list|)
expr_stmt|;
if|if
condition|(
name|started
operator|!=
literal|null
operator|&&
name|completed
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|duration
operator|=
name|completed
operator|.
name|getTime
argument_list|()
operator|-
name|started
operator|.
name|getTime
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|duration
operator|=
literal|null
expr_stmt|;
block|}
block|}
comment|/**      * The Status of the execution      * @return the status      */
specifier|public
specifier|final
name|UriRef
name|getStatus
parameter_list|()
block|{
return|return
name|status
return|;
block|}
comment|/**      * The start date of the Execution      * @return the started      */
specifier|public
specifier|final
name|Date
name|getStarted
parameter_list|()
block|{
return|return
name|started
return|;
block|}
comment|/**      * The duration of the Execution in milliseconds.      * @return the duration      */
specifier|public
specifier|final
name|Long
name|getDuration
parameter_list|()
block|{
return|return
name|duration
return|;
block|}
comment|/**      * @return the executionNode      */
specifier|public
name|ExecutionNode
name|getExecutionNode
parameter_list|()
block|{
return|return
name|executionNode
return|;
block|}
specifier|public
name|Date
name|getCompleted
parameter_list|()
block|{
return|return
name|completed
return|;
block|}
specifier|public
name|boolean
name|isFailed
parameter_list|()
block|{
return|return
name|ExecutionMetadata
operator|.
name|STATUS_FAILED
operator|.
name|equals
argument_list|(
name|status
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isCompleted
parameter_list|()
block|{
return|return
name|ExecutionMetadata
operator|.
name|STATUS_COMPLETED
operator|.
name|equals
argument_list|(
name|status
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|node
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|o
operator|instanceof
name|ExecutionNode
operator|&&
operator|(
operator|(
name|ExecutionNode
operator|)
name|o
operator|)
operator|.
name|node
operator|.
name|equals
argument_list|(
name|node
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|Execution
name|e2
parameter_list|)
block|{
if|if
condition|(
name|started
operator|!=
literal|null
operator|&&
name|e2
operator|.
name|started
operator|!=
literal|null
condition|)
block|{
name|int
name|result
init|=
name|started
operator|.
name|compareTo
argument_list|(
name|e2
operator|.
name|started
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|==
literal|0
condition|)
block|{
if|if
condition|(
name|completed
operator|!=
literal|null
operator|&&
name|e2
operator|.
name|completed
operator|!=
literal|null
condition|)
block|{
name|result
operator|=
name|started
operator|.
name|compareTo
argument_list|(
name|e2
operator|.
name|completed
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
operator|==
literal|0
condition|)
block|{
return|return
name|node
operator|.
name|toString
argument_list|()
operator|.
name|compareTo
argument_list|(
name|e2
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|result
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|completed
operator|==
literal|null
operator|&&
name|e2
operator|.
name|completed
operator|==
literal|null
condition|)
block|{
return|return
name|node
operator|.
name|toString
argument_list|()
operator|.
name|compareTo
argument_list|(
name|e2
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|completed
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
literal|1
return|;
block|}
block|}
else|else
block|{
return|return
name|result
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|started
operator|==
literal|null
operator|&&
name|e2
operator|.
name|started
operator|==
literal|null
condition|)
block|{
return|return
name|node
operator|.
name|toString
argument_list|()
operator|.
name|compareTo
argument_list|(
name|e2
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|started
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
literal|1
return|;
block|}
block|}
comment|/**      * @return the chain      */
specifier|public
name|ChainExecution
name|getChain
parameter_list|()
block|{
return|return
name|chain
return|;
block|}
block|}
end_class

end_unit

