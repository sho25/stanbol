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
name|scope
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
name|ontologymanager
operator|.
name|servicesapi
operator|.
name|collector
operator|.
name|DuplicateIDException
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
name|ontologymanager
operator|.
name|servicesapi
operator|.
name|io
operator|.
name|OntologyInputSource
import|;
end_import

begin_interface
specifier|public
interface|interface
name|ScopeFactory
extends|extends
name|ScopeEventListenable
block|{
comment|/**      * Creates and returns a new ontology scope with the core space ontologies obtained from      *<code>coreSource</code> and the custom space not set.      *       * @param scopeID      *            the desired unique identifier for the ontology scope.      * @param coreSource      *            the input source that provides the top ontology for the core space.      * @return the newly created ontology scope.      * @throws DuplicateIDException      *             if an ontology scope with the given identifier is already<i>registered</i>. The exception      *             is not thrown if another scope with the same ID has been created but not registered.      */
name|Scope
name|createOntologyScope
parameter_list|(
name|String
name|scopeID
parameter_list|,
name|OntologyInputSource
argument_list|<
name|?
argument_list|>
modifier|...
name|coreOntologies
parameter_list|)
throws|throws
name|DuplicateIDException
function_decl|;
block|}
end_interface

end_unit

