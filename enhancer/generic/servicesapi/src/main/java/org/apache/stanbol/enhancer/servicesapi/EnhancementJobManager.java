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
name|List
import|;
end_import

begin_comment
comment|/**  * Accept requests for enhancing ContentItems, and processes them either  * synchronously or asynchronously (as decided by the enhancement engines or by  * configuration).  *<p>  * The progress of the enhancement process should be made accessible in the  * ContentItem's metadata.  */
end_comment

begin_interface
specifier|public
interface|interface
name|EnhancementJobManager
block|{
comment|/**      * Create relevant asynchronous requests or enhance content immediately. The      * result is not persisted right now. The caller is responsible for calling the      * {@link Store#put(ContentItem)} afterwards in case persistence is      * required.      *<p>      * TODO: define the expected semantics if asynchronous enhancements were to      * get implemented.      *      * @throws EngineException if the enhancement process failed      */
name|void
name|enhanceContent
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
function_decl|;
comment|/**      *       * @param ci : ContentItem to be enhanced      * @param chain : enhancement chain Name      * @throws EngineException : if the enhancement process failed      */
name|void
name|enhanceContent
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|String
name|chain
parameter_list|)
throws|throws
name|EngineException
function_decl|;
comment|/**      * Return the unmodifiable list of active registered engine instance that      * can be used by the manager.      */
name|List
argument_list|<
name|EnhancementEngine
argument_list|>
name|getActiveEngines
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

