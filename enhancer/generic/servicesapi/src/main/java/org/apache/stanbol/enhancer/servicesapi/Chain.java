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
name|enhancer
operator|.
name|servicesapi
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|Graph
import|;
end_import

begin_comment
comment|/**  * An Enhancement Chain represents a configuration that defines what engines   * and in what order are used to process ContentItems. The Chain is not  * responsible for the execution, but only provides the configuration - the  * execution plan - to the {@link EnhancementJobManager}.<p>  * Typically Chains are registered as OSGI services. Such Chains are  * accessible via the {@link ChainManager} service by name - the value of   * {@link #PROPERTY_NAME} in the service registration.   * Chains that are registered like that can be directly addressed by users by  * parsing content to<code>/engines/chain/{name}</code>.<p>  * To use a {@link Chain} for the execution of a {@link ContentItem} with the  * {@link EnhancementJobManager} it is not required that it is registered as  * OSGI service. {@link EnhancementJobManager} MUST also accept Chains that are  * not registered as OSGI service.  * Chains are registered as   * OSGI services and identified by the "stanbol.enhancer.chain.name" property.  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Chain
block|{
comment|/**      * The property to be used for providing the name of a chain.      */
name|String
name|PROPERTY_NAME
init|=
literal|"stanbol.enhancer.chain.name"
decl_stmt|;
comment|/**      * Getter for the execution plan reflecting the current configuration of this      * Chain. The returned {@link Graph} is read only and MUST NOT be changed if       * the configuration of this Chain changes. This means that the Chain MUST       * create a new Graph instance if the execution plan changes as a result of       * a change in the configuration. It MUST NOT change any execution plan       * parsed to other components by the getExecutionPlan() method.      * @return the execution plan as defined by the       *<code>http://stanbol.apache.org/ontology/enhancer/executionplan#</code>      * schema.      * @throws ChainException If the execution plan can not be created. Typically      * the case of the Chain requires runtime information to determine the      * execution plan.      */
name|Graph
name|getExecutionPlan
parameter_list|()
throws|throws
name|ChainException
function_decl|;
comment|/**      * Getter for the set of {@link EnhancementEngine}s referenced by there      * name within the execution plan. This method is intended to be used       * in situations where only the list of engines need to be known (e.g. when      * checking that all referenced engines are available).      * @return the set of engine names referenced by this chain      * @throws ChainException If the engines referenced by this chain can not be      * determined. This may happen if a chain required runtime information to      * determine the list of engines.      */
name|Set
argument_list|<
name|String
argument_list|>
name|getEngines
parameter_list|()
throws|throws
name|ChainException
function_decl|;
comment|/**      * The name of this chain as configured by {@link #PROPERTY_NAME}      * @return the name      */
name|String
name|getName
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

