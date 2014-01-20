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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  * Interface to internal or external semantic enhancement engines. There will  * usually be several of those, that the EnhancementJobManager uses to enhance  * content items.  */
end_comment

begin_interface
specifier|public
interface|interface
name|EnhancementEngine
block|{
comment|/**      * The property used to provide the name of an enhancement engine.      */
name|String
name|PROPERTY_NAME
init|=
literal|"stanbol.enhancer.engine.name"
decl_stmt|;
comment|/**      * Return value for {@link #canEnhance}, meaning this engine cannot enhance      * supplied {@link ContentItem}      */
name|int
name|CANNOT_ENHANCE
init|=
literal|0
decl_stmt|;
comment|/**      * Return value for {@link #canEnhance}, meaning this engine can enhance      * supplied {@link ContentItem}, and suggests enhancing it synchronously      * instead of queueing a request for enhancement.      */
name|int
name|ENHANCE_SYNCHRONOUS
init|=
literal|1
decl_stmt|;
comment|/**      * Return value for {@link #canEnhance}, meaning this engine can enhance      * supplied {@link ContentItem}, and suggests queueing a request for      * enhancement instead of enhancing it synchronously.      */
name|int
name|ENHANCE_ASYNC
init|=
literal|2
decl_stmt|;
comment|/**      * Indicate if this engine can enhance supplied ContentItem, and if it      * suggests enhancing it synchronously or asynchronously. The      * {@link EnhancementJobManager} can force sync/async mode if desired, it is      * just a suggestion from the engine.      *<p>      * This method is expected to execute fast and MUST NOT change the parsed      * {@link ContentItem}. It is called with a read lock on the ContentItem.      *<p>      *<b>NOTE:</b> Returning {@link #CANNOT_ENHANCE} will cause the       * {@link EnhancementJobManager} to skip the execution of this Engine. If      * an {@link EngineException} is thrown the executed {@link Chain} will      * fail (unless this engine is marked as OPTIONAL).      *      * @param ci The ContentItem to enhance      * @param context The enhancement context: Request specific parameters      *      * @throws EngineException if the introspecting process of the content item      *             fails      */
name|int
name|canEnhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
function_decl|;
comment|//int canEnhance(ContentItem ci, Map<String,Object> context) throws EngineException;
comment|/**      * Compute enhancements for supplied ContentItem. The results of the process      * are expected to be stored in the {@link ContentItem#getMetadata() metadata       * of the content item} or by adding/modifying any contentPart.<p>      * Engines that do support {@link #ENHANCE_ASYNC} are required to use the      * {@link ContentItem#getLock()} to acquire read/write locks when reading/      * modifying information of the {@link ContentItem}. For Engines that that      * do use {@link #ENHANCE_SYNCHRONOUS} the {@link EnhancementJobManager}      * is responsible to acquire a write lock before calling this method.       *<p>      *<b>NOTE</b>: If an EnhancementEngine can not extract any information it      * is expected to return. In case an error is encountered during processing      * an {@link EngineException} need to be thrown.      *      * @param ci The ContentItem to enhance      * @param context The enhancement context: Request specific parameters      *      * @throws EngineException if the underlying process failed to work as      *             expected      */
name|void
name|computeEnhancements
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
function_decl|;
comment|//void computeEnhancements(ContentItem ci, Map<String,Object> context) throws EngineException;
comment|/**      * Getter for the name of this EnhancementEngine instance as configured      * by {@link #PROPERTY_NAME}      * @return the name      */
name|String
name|getName
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

