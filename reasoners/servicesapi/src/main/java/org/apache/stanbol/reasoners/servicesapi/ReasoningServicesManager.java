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
name|Set
import|;
end_import

begin_comment
comment|/**  * Classes which implements this interface provide the {@see ReasoningService} mapped   * to a given key string (generally used as path for a REST endpoint)  *   * @author enridaga  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|ReasoningServicesManager
block|{
comment|/**      * The number of available {@see ReasoningService}s      * @return      */
specifier|public
specifier|abstract
name|int
name|size
parameter_list|()
function_decl|;
comment|/**      * The {@see ReasoningService} mapped       * to the given key string      *       * @param path      * @return      * @throws UnboundReasoningServiceException      */
specifier|public
specifier|abstract
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
name|get
parameter_list|(
name|String
name|path
parameter_list|)
throws|throws
name|UnboundReasoningServiceException
function_decl|;
comment|/**      * The unmodifiable set of available {@see ReasoningService}s      */
specifier|public
specifier|abstract
name|Set
argument_list|<
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
argument_list|>
name|asUnmodifiableSet
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

