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
name|ontology
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
name|OWLOntologyManager
import|;
end_import

begin_comment
comment|/**  * An ontology scope for application use. There exists exactly one scope for each live (active or halted) KReS  * session.<br>  *<br>  * This is the only type of ontology scope that allows public access to its OWL ontology manager.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SessionOntologySpace
extends|extends
name|OntologySpace
block|{
comment|/**      * Returns the OWL ontology manager associated to this scope.      *       * @return the associated ontology manager      */
name|OWLOntologyManager
name|getOntologyManager
parameter_list|()
function_decl|;
name|void
name|attachSpace
parameter_list|(
name|OntologySpace
name|space
parameter_list|,
name|boolean
name|skipRoot
parameter_list|)
throws|throws
name|UnmodifiableOntologySpaceException
function_decl|;
block|}
end_interface

end_unit

