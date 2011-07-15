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
comment|/**  * Single writer, multiple readers lock implementation for persistence store  *   * @author Cihan  */
end_comment

begin_interface
specifier|public
interface|interface
name|LockManager
block|{
name|String
name|GLOBAL_SPACE
init|=
literal|"GLOBAL_SPACE"
decl_stmt|;
comment|/**      * Obtain a read lock for specified ontology      *       * @param ontologyURI      *            URI of the ontology      */
name|void
name|obtainReadLockFor
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
comment|/**      * Release read lock for specified ontology      *       * @param ontologyURI      *            URI of the ontology      */
name|void
name|releaseReadLockFor
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
comment|/**      * Obtain a write lock for specified ontology      *       * @param ontologyURI      *            URI of the ontology      */
name|void
name|obtainWriteLockFor
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
comment|/**      * Release write lock for specified ontology      *       * @param ontologyURI      */
name|void
name|releaseWriteLockFor
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

