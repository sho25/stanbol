begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

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
name|registry
operator|.
name|api
package|;
end_package

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
name|registry
operator|.
name|api
operator|.
name|model
operator|.
name|RegistryItem
import|;
end_import

begin_comment
comment|/**  * Thrown when an attempt to create an illegal cycle in the registry item model is detected. Examples of  * illegal cycle include being both a parent and a child of the same registry item, or a parent or child of  * itself.  */
end_comment

begin_class
specifier|public
class|class
name|IllegalRegistryCycleException
extends|extends
name|RegistryContentException
block|{
comment|/**      *       */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|2929796860026423332L
decl_stmt|;
specifier|private
name|RegistryOperation
name|operationType
decl_stmt|;
specifier|private
name|RegistryItem
name|sourceNode
decl_stmt|,
name|targetNode
decl_stmt|;
comment|/**      * Creates a new instance of {@link IllegalRegistryCycleException}.      *       * @param sourceNode      *            the source node of the cycle.      * @param targetNode      *            the target node of the cycle.      * @param operationType      *            the type of operation attempted, i.e. the disallowed arc of the cycle.      */
specifier|public
name|IllegalRegistryCycleException
parameter_list|(
name|RegistryItem
name|sourceNode
parameter_list|,
name|RegistryItem
name|targetNode
parameter_list|,
name|RegistryOperation
name|operationType
parameter_list|)
block|{
name|super
argument_list|(
literal|"Cycles of type "
operator|+
name|operationType
operator|+
literal|" between registry items "
operator|+
name|sourceNode
operator|+
literal|" and "
operator|+
name|targetNode
operator|+
literal|" are not allowed."
argument_list|)
expr_stmt|;
name|this
operator|.
name|sourceNode
operator|=
name|sourceNode
expr_stmt|;
name|this
operator|.
name|targetNode
operator|=
name|targetNode
expr_stmt|;
block|}
comment|/**      * Returns the type of operation attempted, i.e. the disallowed arc of this cycle.      *       * @return the type of operation attempted.      */
specifier|public
name|RegistryOperation
name|getOperationType
parameter_list|()
block|{
return|return
name|operationType
return|;
block|}
comment|/**      * Returns the source node of this cycle.      *       * @return the source node of the cycle.      */
specifier|public
name|RegistryItem
name|getSourceNode
parameter_list|()
block|{
return|return
name|sourceNode
return|;
block|}
comment|/**      * Returns the target node of this cycle.      *       * @return the target node of the cycle.      */
specifier|public
name|RegistryItem
name|getTargetNode
parameter_list|()
block|{
return|return
name|targetNode
return|;
block|}
block|}
end_class

end_unit

