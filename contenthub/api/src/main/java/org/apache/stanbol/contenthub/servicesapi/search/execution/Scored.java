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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|execution
package|;
end_package

begin_comment
comment|/**  * This is a base interface indicating the score of the resources. Search operation assigns a score to the  * search results regardless of its type (i.e. {@link DocumentResource}, {@link ClassResource},  * {@link IndividualResource}).  *   * @author cihan  *   */
end_comment

begin_interface
specifier|public
specifier|abstract
interface|interface
name|Scored
block|{
comment|/**      * Returns the score of the resource.      *       * @return The score of the resource.      */
name|double
name|getScore
parameter_list|()
function_decl|;
comment|// TODO Freemarker rounds doubles automatically, this function will be removed once a solution is found
name|String
name|getScoreString
parameter_list|()
function_decl|;
comment|/**      * Updates the score of the resource based on a weight. The score is updated according to a weight based      * calculation.      *       * @param score      *            New score.      * @param weight      *            The weight of the new score.      */
name|void
name|updateScore
parameter_list|(
name|Double
name|score
parameter_list|,
name|Double
name|weight
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

