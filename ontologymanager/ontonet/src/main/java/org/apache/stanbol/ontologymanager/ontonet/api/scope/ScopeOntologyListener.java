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
name|ontonet
operator|.
name|api
operator|.
name|scope
package|;
end_package

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
import|;
end_import

begin_comment
comment|/**  * Implementations of this interface are able to react to modifications on the ontology network  * infrastructure.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ScopeOntologyListener
block|{
comment|/**      * Called whenever an ontology is set to be managed by a scope, space or session.      *       * @param scopeId      * @param addedOntology      */
name|void
name|onOntologyAdded
parameter_list|(
name|String
name|scopeId
parameter_list|,
name|IRI
name|addedOntology
parameter_list|)
function_decl|;
comment|/**      * Called whenever an ontology is set to no longer be managed by a scope, space or session. This method is      * not called if that ontology was not being managed earlier.      *       * @param scopeId      * @param addedOntology      */
name|void
name|onOntologyRemoved
parameter_list|(
name|String
name|scopeId
parameter_list|,
name|IRI
name|removedOntology
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

