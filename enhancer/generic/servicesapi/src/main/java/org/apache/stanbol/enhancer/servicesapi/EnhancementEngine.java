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
package|;
end_package

begin_comment
comment|/**  * Interface to internal or external semantic enhancement engines. There will  * usually be several of those, that the EnhancementJobManager uses to enhance  * content items.  */
end_comment

begin_interface
specifier|public
interface|interface
name|EnhancementEngine
block|{
comment|/**      * Return value for {@link #canEnhance}, meaning this engine cannot enhance      * supplied {@link ContentItem}      */
name|int
name|CANNOT_ENHANCE
init|=
literal|0
decl_stmt|;
comment|/**      * Return value for {@link #canEnhance}, meaning this engine can enhance      * supplied {@link ContentItem}, and suggests enhancing it synchronously      * instead of queuing a request for enhancement.      */
name|int
name|ENHANCE_SYNCHRONOUS
init|=
literal|1
decl_stmt|;
comment|/**      * Return value for {@link #canEnhance}, meaning this engine can enhance      * supplied {@link ContentItem}, and suggests queuing a request for      * enhancement instead of enhancing it synchronously.      */
name|int
name|ENHANCE_ASYNC
init|=
literal|1
decl_stmt|;
comment|/**      * Indicate if this engine can enhance supplied ContentItem, and if it      * suggests enhancing it synchronously or asynchronously. The      * {@link EnhancementJobManager} can force sync/async mode if desired, it is      * just a suggestion from the engine.      *      * @throws EngineException if the introspecting process of the content item      *             fails      */
name|int
name|canEnhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
function_decl|;
comment|/**      * Compute enhancements for supplied ContentItem. The results of the process      * are expected to be stored in the metadata of the content item.      *      * The client (usually an {@link EnhancementJobManager}) should take care of      * persistent storage of the enhanced {@link ContentItem}.      *      * @throws EngineException if the underlying process failed to work as      *             expected      */
name|void
name|computeEnhancements
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
function_decl|;
block|}
end_interface

end_unit

