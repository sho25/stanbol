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
name|rules
operator|.
name|base
operator|.
name|api
package|;
end_package

begin_comment
comment|/**  * A {@link NoSuchRecipeException} is thrown when the recipe requested does not exist in the store.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|NoSuchRecipeException
extends|extends
name|Exception
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|protected
name|String
name|recipeID
decl_stmt|;
comment|/**      * Creates a new instance of OntologySpaceModificationException.      *       * @param space      *            the ontology space whose modification was attempted.      */
specifier|public
name|NoSuchRecipeException
parameter_list|(
name|String
name|recipeID
parameter_list|)
block|{
name|this
operator|.
name|recipeID
operator|=
name|recipeID
expr_stmt|;
block|}
comment|/**      * Returns the {@link String} of the recipe that threw the exception.      *       * @return the recipe {@link String} on which the exception was thrown.      */
specifier|public
name|String
name|getRecipeID
parameter_list|()
block|{
return|return
name|recipeID
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
literal|"The recipe "
operator|+
name|recipeID
operator|+
literal|" does not exist."
return|;
block|}
block|}
end_class

end_unit

