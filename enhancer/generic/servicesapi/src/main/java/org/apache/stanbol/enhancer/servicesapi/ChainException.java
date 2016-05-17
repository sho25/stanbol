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

begin_comment
comment|//import static org.apache.stanbol.enhancer.servicesapi.helper.ExecutionPlanHelper.getDependend;
end_comment

begin_comment
comment|//import static org.apache.stanbol.enhancer.servicesapi.helper.ExecutionPlanHelper.getEngine;
end_comment

begin_comment
comment|//import static org.apache.stanbol.enhancer.servicesapi.helper.ExecutionPlanHelper.isOptional;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import org.apache.clerezza.commons.rdf.ImmutableGraph;
end_comment

begin_comment
comment|//import org.apache.clerezza.commons.rdf.BlankNodeOrIRI;
end_comment

begin_comment
comment|/**  * BaseException thrown by {@link Chain} implementations or  * {@link EnhancementJobManager} implementations when encountering problems   * while executing e Chain  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|ChainException
extends|extends
name|EnhancementException
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|ChainException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ChainException
parameter_list|(
name|String
name|message
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
comment|//Removed - unused
comment|//    /**
comment|//     * Creates a chain exception for the parsed node within the parsed executionPlan
comment|//     * @param executionPlan
comment|//     * @param node
comment|//     * @param message
comment|//     * @param cause
comment|//     */
comment|//    public ChainException(ImmutableGraph executionPlan, BlankNodeOrIRI node, String message, Throwable cause){
comment|//        super(String.format("Unable to execute node {} (engine: {} | optional : {}" +
comment|//        		" | dependsOn : {}) because of: {}",
comment|//            node,getEngine(executionPlan, node),
comment|//            isOptional(executionPlan, node), getDependend(executionPlan, node),
comment|//            message == null || message.isEmpty() ? "<unknown>": message),cause);
comment|//    }
block|}
end_class

end_unit

