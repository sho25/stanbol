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
name|reasoners
operator|.
name|base
operator|.
name|api
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
name|OWLOntology
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|reasoner
operator|.
name|OWLReasoner
import|;
end_import

begin_comment
comment|/**  * The KReS Reasoner provides all the reasoning services to the KReS.  *   *   * @author andrea.nuzzolese  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Reasoner
block|{
comment|/** 	 * Gets the reasoner. 	 *  	 * @param ontology {@link OWLOntology} 	 * @return the reasoner {@link OWLReasoner}. 	 */
name|OWLReasoner
name|getReasoner
parameter_list|(
name|OWLOntology
name|ontology
parameter_list|)
function_decl|;
comment|/** 	 * Runs a consistency check on the ontology. 	 *  	 * @param owlReasoner {@link OWLReasoner} 	 * @return true if the ontology is consistent, false otherwise. 	 */
name|boolean
name|consistencyCheck
parameter_list|(
name|OWLReasoner
name|owlReasoner
parameter_list|)
function_decl|;
comment|/** 	 * Launch the reasoning on a set of rules applied to a gien ontology. 	 * @param ontology 	 * @param ruleOntology 	 * @return the inferred ontology 	 */
name|OWLOntology
name|runRules
parameter_list|(
name|OWLOntology
name|ontology
parameter_list|,
name|OWLOntology
name|ruleOntology
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

