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
name|ontologymanager
operator|.
name|servicesapi
operator|.
name|collector
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

begin_comment
comment|/**  * Informs listeners about changes in an ontology collector. Implementations of this interface should be able  * to fire events related to the modification of ontologies within an ontology collector.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|OntologyCollectorListenable
block|{
comment|/**      * Registers a new {@link OntologyCollectorListener} with this object.      *       * @param listener      *            the listener to be registered.      */
name|void
name|addOntologyCollectorListener
parameter_list|(
name|OntologyCollectorListener
name|listener
parameter_list|)
function_decl|;
comment|/**      * Unregisters every {@link OntologyCollectorListener} from this object.      */
name|void
name|clearOntologyCollectorListeners
parameter_list|()
function_decl|;
comment|/**      * Returns the list of {@link OntologyCollectorListener}s registered with this object.      *       * @return the registered listeners.      */
name|Collection
argument_list|<
name|OntologyCollectorListener
argument_list|>
name|getOntologyCollectorListeners
parameter_list|()
function_decl|;
comment|/**      * Unregisters {@link OntologyCollectorListener} from this object.      *       * @param listener      *            the listener to be unregistered.      */
name|void
name|removeOntologyCollectorListener
parameter_list|(
name|OntologyCollectorListener
name|listener
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

