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
name|commons
operator|.
name|jobs
operator|.
name|api
package|;
end_package

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

begin_comment
comment|/**  * This interface defines the executor of asynch processes.  *   * @author enridaga  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|JobManager
block|{
comment|/**      * Adds and runs an asynch process. Returns the String id to use for pinging its execution.      *       * @param task      * @return      */
specifier|public
name|String
name|execute
parameter_list|(
name|Job
name|job
parameter_list|)
function_decl|;
comment|/**      * Get the Future object to monitor the state of a job      */
specifier|public
name|Future
argument_list|<
name|?
argument_list|>
name|ping
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/**      * Get the location url of the result      *       * @param id      * @return      */
specifier|public
name|String
name|getResultLocation
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/**      * If the executor is managing the given job      *       * @param id      * @return      */
specifier|public
name|boolean
name|hasJob
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/**      * The currently managed jobs, in any state (running, complete, interrupted)      *       * @return      */
specifier|public
name|int
name|size
parameter_list|()
function_decl|;
comment|/**      * Interrupt the asynch process and remove it from the job list.      * To interrupt the process and keeping it, use the Future object from the ping() method.      *       * @param id      */
specifier|public
name|void
name|remove
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/**      * Interrupt all asynch processes and remove them form the job list.      */
specifier|public
name|void
name|removeAll
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

