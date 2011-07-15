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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|session
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_interface
specifier|public
interface|interface
name|SessionListenable
block|{
comment|/** 	 * Adds the given SessionListener to the pool of registered listeners. 	 *  	 * @param listener 	 *            the session listener to be added 	 */
name|void
name|addSessionListener
parameter_list|(
name|SessionListener
name|listener
parameter_list|)
function_decl|;
comment|/** 	 * Clears the pool of registered session listeners. 	 */
name|void
name|clearSessionListeners
parameter_list|()
function_decl|;
comment|/** 	 * Returns all the registered session listeners. It is up to developers to 	 * decide whether implementations should return sets (unordered but without 	 * redundancy), lists (e.g. in the order they wer registered but potentially 	 * redundant) or other data structures that implement {@link Collection}. 	 *  	 * @return a collection of registered session listeners. 	 */
name|Collection
argument_list|<
name|SessionListener
argument_list|>
name|getSessionListeners
parameter_list|()
function_decl|;
comment|/** 	 * Removes the given SessionListener from the pool of active listeners. 	 *  	 * @param listener 	 *            the session listener to be removed 	 */
name|void
name|removeSessionListener
parameter_list|(
name|SessionListener
name|listener
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

