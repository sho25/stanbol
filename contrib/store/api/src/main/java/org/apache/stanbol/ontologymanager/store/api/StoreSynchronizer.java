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
name|store
operator|.
name|api
package|;
end_package

begin_comment
comment|/**  * Interface for synchronizing the resources managed by {@link ResourceManager} with underlying  * {@link JenaPersistenceProvider} implementation  *   * @author Cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|StoreSynchronizer
block|{
comment|/**      * Synchronizes all graphs that is stored in a {@link ResourceManager}      *       * @param force      *<p>      *            If set {@link ResourceManager} will be cleared and all resource-graph mappings will be      *            rebuilt.      *<p>      *            If not set the synchronizer should consider only deletion/addition of graphs.      */
name|void
name|synchronizeAll
parameter_list|(
name|boolean
name|force
parameter_list|)
function_decl|;
comment|/**      * Synchronizes only specified graph. After synchronization the resource-graph mappings of the      * {@link ResourceManager} for the particular graph is synchronized      *       * @param graphURI      *            URI of the graph of which resources will be synchronized      */
name|void
name|synchronizeGraph
parameter_list|(
name|String
name|graphURI
parameter_list|)
function_decl|;
comment|/**      * StoreSynchronizer is obtained through a factory and when its job is finished this method should be      * invoked to remove graph listeners.      */
name|void
name|clear
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

