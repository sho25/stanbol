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
name|rules
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
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|util
operator|.
name|AtomList
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
name|model
operator|.
name|OWLDataFactory
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
name|model
operator|.
name|SWRLRule
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Model
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Resource
import|;
end_import

begin_comment
comment|/**  * A Rule is a Java object that represent a rule in KReS. It contains methods to transform a rule both in SWRL and in Rule  * syntax.   *   * @author andrea.nuzzolese  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Rule
block|{
comment|/** 	 * Gets the name of the rule. 	 *  	 * @return the {@link String} representing the name of the rule. 	 */
name|String
name|getRuleName
parameter_list|()
function_decl|;
comment|/** 	 * Sets the rule's name 	 *  	 * @param ruleName {@link String} 	 */
name|void
name|setRuleName
parameter_list|(
name|String
name|ruleName
parameter_list|)
function_decl|;
comment|/** 	 * Returns the representation of the rule in Rule syntax. 	 *  	 * @return the {@link String} of the rule in Rule syntax. 	 */
name|String
name|getRule
parameter_list|()
function_decl|;
comment|/** 	 * Sets the rule expressed in Rule syntax 	 *  	 * @param rule {@link String} 	 */
name|void
name|setRule
parameter_list|(
name|String
name|rule
parameter_list|)
function_decl|;
comment|/** 	 * Maps a {@code Rule} to a Jena {@link Resource} object in a given Jena {@link Model}. 	 * @param model {@link Model} 	 * @return the {@link Resource} containing the rule. 	 */
name|Resource
name|toSWRL
parameter_list|(
name|Model
name|model
parameter_list|)
function_decl|;
comment|/** 	 * Maps a {@code Rule} to an OWL-API {@link SWRLRule}. 	 * @param factory {@link OWLDataFactory} 	 * @return the {@link SWRLRule} containing the rule. 	 */
name|SWRLRule
name|toSWRL
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|)
function_decl|;
comment|/** 	 * Transforms the rule to a SPARQL CONSTRUCT. 	 *  	 * @return the string containing the SPARQL CONSTRUCT. 	 */
name|String
name|toSPARQL
parameter_list|()
function_decl|;
comment|/** 	 * Rules are composed by an antecedent (body) and a consequent (head). This method returnn the consequent 	 * expressed as a list of its atoms ({@link AtomList}). 	 * @return the {@link AtomList} of the consequent's atoms.  	 */
name|AtomList
name|getHead
parameter_list|()
function_decl|;
comment|/** 	 * Rules are composed by an antecedent (body) and a consequent (head). This method returnn the antecedent 	 * expressed as a list of its atoms ({@link AtomList}). 	 * @return the {@link AtomList} of the antecedent's atoms.  	 */
name|AtomList
name|getBody
parameter_list|()
function_decl|;
comment|/** 	 * Retunr the KReS syntax representation of the rule. 	 * @return the string of the rule in Rule syntax. 	 */
name|String
name|toKReSSyntax
parameter_list|()
function_decl|;
comment|/** 	 * If the variable forwardChain is set true than the forward chain mechanism is ebled for that rule. 	 * @return {@link boolean}. 	 */
name|boolean
name|isForwardChain
parameter_list|()
function_decl|;
name|boolean
name|isSPARQLConstruct
parameter_list|()
function_decl|;
name|boolean
name|isSPARQLDelete
parameter_list|()
function_decl|;
name|boolean
name|isSPARQLDeleteData
parameter_list|()
function_decl|;
name|boolean
name|isReflexive
parameter_list|()
function_decl|;
name|RuleExpressiveness
name|getExpressiveness
parameter_list|()
function_decl|;
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|reasoner
operator|.
name|rulesys
operator|.
name|Rule
name|toJenaRule
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

