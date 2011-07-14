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
name|entityhub
operator|.
name|indexing
operator|.
name|core
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|yard
operator|.
name|Yard
import|;
end_import

begin_comment
comment|/**  * The main indexing interface.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Indexer
block|{
comment|/**      * States of the Indexer      * @author Rupert Westenthaler      *      */
enum|enum
name|State
block|{
comment|/**          * After construction of the an instance          */
name|UNINITIALISED
block|,
comment|/**          * Indicates that the initialising of the {@link IndexingSource}s is on          * the way.          */
name|INITIALISING
block|,
comment|/**          * All {@link IndexingSource}s are initialised, but the actual indexing          * of the entities has not yet started.          * This is the last opportunity to call {@link Indexer#setChunkSize(int)}          * and {@link Indexer#setIndexAllEntitiesState(boolean)}          */
name|INITIALISED
block|,
comment|/**          * While the indexing of the entities is on the way.          */
name|INDEXING
block|,
comment|/**          * All Entities provided by the {@link IndexingSource}s are processed          * and stored to the {@link IndexingTarget}.          */
name|INDEXED
block|,
comment|/**          * While the {@link IndexingTarget} is finalising the indexing process.          */
name|FINALISING
block|,
comment|/**          * Indicates that the indexing has finished and the {@link IndexingTarget}          * has completed the finalisation.           */
name|FINISHED
block|}
empty_stmt|;
comment|/**      * The default number of documents sent in one chunk to the {@link Yard}       * provided by the configured {@link IndexingDestination}      */
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_CHUNK_SIZE
init|=
literal|10
decl_stmt|;
comment|/**      * Setter for the chunk size. parsing values&lt;= 0 results in the      * chunk size to be set to {@link #DEFAULT_CHUNK_SIZE}.       * @param chunkSize the chunkSize to set      * @throws IllegalStateException if {@link #getState()}&gt;       * {@link State#INITIALISED}      */
name|void
name|setChunkSize
parameter_list|(
name|int
name|chunkSize
parameter_list|)
throws|throws
name|IllegalStateException
function_decl|;
comment|/**      * Getter for the chunk size (the number of entities that are indexed before      * they are store to the {@link Yard}.      * @return the chunkSize      */
name|int
name|getChunkSize
parameter_list|()
function_decl|;
comment|/**      * Getter for the Yard used to store the indexed entities.      * @return the yard      */
name|Yard
name|getYard
parameter_list|()
function_decl|;
comment|/**      * Brings this indexer in the {@link State#INITIALISED} by initialising all       * {@link IndexingComponent}s.This method blocks until the  whole process is       * completed.Calls to this method are ignored if the indexer is not in the       * {@link State#UNINITIALISED} state.      *<p>      * This Method is intended to be used by caller that need more control over       * the indexing process as simple to call {@link #index()}.      */
name|void
name|initialiseIndexingSources
parameter_list|()
function_decl|;
comment|/**      * Brings this indexer in the {@link State#INDEXED} by indexing all Entities      * provided by the {@link IndexingComponent}s. This method blocks until the        * whole process is completed. Calls to this method are ignored if the       * indexer is in a state greater than {@link State#INITIALISED}.      *<p>      * This Method is intended to be used by caller that need more control over       * the indexing process as simple to call {@link #index()}.      * @throws IllegalStateException if {@link #getState()}&lt;       * {@link State#INITIALISED}      */
name|void
name|indexAllEntities
parameter_list|()
throws|throws
name|IllegalStateException
function_decl|;
comment|/**      * {@link State#FINISHED Finalises} the indexing process by calling finalise      * on the {@link IndexingDestination}. This method blocks until the        * whole process is completed. Calls to this method are ignored if the       * indexer is in a state greater than {@link State#INDEXED}.      *<p>      * This Method is intended to be used by caller that need more control over       * the indexing process as simple to call {@link #index()}.      * @throws IllegalStateException if {@link #getState()}&lt;       * {@link State#INDEXED}      */
name|void
name|finaliseIndexingTarget
parameter_list|()
throws|throws
name|IllegalStateException
function_decl|;
comment|/**      * Initialise the {@link IndexingComponent}s, indexes all entities and       * finalises the {@link IndexingDestination}.<p>      * Calls to this method do have the same result as subsequent calls to       * {@link #initialiseIndexingSources()}, {@link #indexAllEntities()},      * {@link #finaliseIndexingTarget()}. This method can also be used if any of      * the mentioned three methods was already called to this indexer instance.      *<p>      * This method blocks until the whole process is completed. Ideal if the       * called does not need/want any further control over the indexing process.      *<p>      * To perform the indexing in the background one need to execute this      * Method in an own {@link Thread}.      */
name|void
name|index
parameter_list|()
function_decl|;
comment|/**      * This allows to set the Indexer in an state that it also indexes entities      * with an negative score. Typically Entities with a negative score are      * considered to be marked as not to be indexed. However setting this state      * to true allows to index also such entities.      * @param indexAllEntitiesState the indexAllEntitiesState to set      * @throws IllegalStateException if {@link #getState()}&gt; {@link State#INITIALISED}      */
name|void
name|setIndexAllEntitiesState
parameter_list|(
name|boolean
name|indexAllEntitiesState
parameter_list|)
throws|throws
name|IllegalStateException
function_decl|;
comment|/**      * Getter  for the state if entities with a negative score are indexed or      * not. The default is to exclude such entities.       * @return the indexAllEntitiesState      */
name|boolean
name|isIndexAllEntitiesState
parameter_list|()
function_decl|;
comment|/**      * The current state of the indexing process      * @return the state      */
specifier|public
specifier|abstract
name|State
name|getState
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

