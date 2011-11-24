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
name|reasoners
operator|.
name|servicesapi
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reasoners
operator|.
name|servicesapi
operator|.
name|ReasoningServiceInputProvider
import|;
end_import

begin_comment
comment|/**  * A {@see ReasoningServiceInputManager} must be able to collect {@see ReasoningServiceInputProvider}s and  * traverse the whole data on {@see #getInputData(Class<T> type)}.   * Only input providers which support Type must be activated.  *   * @author enridaga  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|ReasoningServiceInputManager
block|{
comment|/**      * Add an input provider      *       * @param provider      */
specifier|public
name|void
name|addInputProvider
parameter_list|(
name|ReasoningServiceInputProvider
name|provider
parameter_list|)
function_decl|;
comment|/**      * Remove the input provider      *       * @param provider      */
specifier|public
name|void
name|removeInputProvider
parameter_list|(
name|ReasoningServiceInputProvider
name|provider
parameter_list|)
function_decl|;
comment|/**      * Get the input data. This should iterate over the collection from all the registered input providers.      *       * Consider that this can be called more then once, to obtain more then one input depending on the type.      *       * It is the Type of the object to instruct about its usage.      *       * @param type      * @return      */
specifier|public
parameter_list|<
name|T
parameter_list|>
name|Iterator
argument_list|<
name|T
argument_list|>
name|getInputData
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
function_decl|;
comment|/**      * Returns the immutable list of registered providers.      *       * @return      */
specifier|public
name|List
argument_list|<
name|ReasoningServiceInputProvider
argument_list|>
name|getProviders
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

