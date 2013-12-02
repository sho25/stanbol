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
name|enhancer
operator|.
name|engines
operator|.
name|dereference
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ConcurrentModificationException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ExecutorService
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|locks
operator|.
name|Lock
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
name|MGraph
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
name|commons
operator|.
name|stanboltools
operator|.
name|offline
operator|.
name|OfflineMode
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
comment|/**  * Interface used by the {@link EntityDereferenceEngine} to dereference  * Entities  *   * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntityDereferencer
block|{
comment|/**      * If this EntityDereferences can dereference Entities when in       * {@link OfflineMode}. This method is expected to only return<code>false</code>      * when an implementation can not dereference any Entities when in offline      * mode. If some (e.g. locally cached) Entities can be dereferenced      * the dereferences should return<code>true<code> and just ignore calles      * for Entities that are not locally available.      * @return the {@link OfflineMode} status      */
name|boolean
name|supportsOfflineMode
parameter_list|()
function_decl|;
comment|/**      * EntityDereferencer can optionally provide an ExecutorService used to      * dereference Entities.      * @return the {@link ExecutorService} or<code>null</code> if not used      * by this implementation      */
name|ExecutorService
name|getExecutor
parameter_list|()
function_decl|;
comment|/**      * Dereferences the Entity with the parsed {@link UriRef} by copying the      * data to the parsed graph      * @param graph the graph to add the dereferenced entity       * @param entity the uri of the Entity to dereference      * @param writeLock The writeLock for the graph. Dereferences MUST require      * a<code>{@link Lock#lock() writeLock#lock()}</code>  before adding       * dereferenced data to the parsed graph. This is essential for using multiple       * threads  to dereference Entities. Failing to do so will cause      * {@link ConcurrentModificationException}s in this implementations or      * other components (typically other {@link EnhancementEngine}s) accessing the      * same graph.      * @param dereferenceContext Context information for the {@link EntityDereferencer}      * such as the {@link OfflineMode} state, possible languages of the content and      * requested languages in the Enhancement request.      * @return if the entity was dereferenced      * @throws DereferenceException on any error while dereferencing the      * requested Entity      */
name|boolean
name|dereference
parameter_list|(
name|UriRef
name|entity
parameter_list|,
name|MGraph
name|graph
parameter_list|,
name|Lock
name|writeLock
parameter_list|,
name|DereferenceContext
name|dereferenceContext
parameter_list|)
throws|throws
name|DereferenceException
function_decl|;
block|}
end_interface

end_unit

