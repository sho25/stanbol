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

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_comment
comment|/**  * A {@link RecipeConstructionException} is thrown when an error occurs during the generation of a recipe  * object after that a recipe is fetched by the store. This does not mean that the recipe does not exist, but  * only that some error prevents to adapt the recipe in the store to a {@link Recipe} object.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|RecipeConstructionException
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
specifier|private
name|Throwable
name|e
decl_stmt|;
specifier|public
name|RecipeConstructionException
parameter_list|(
name|Throwable
name|e
parameter_list|)
block|{
name|this
operator|.
name|e
operator|=
name|e
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
literal|"An exception occurred while generating the Recipe."
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|printStackTrace
parameter_list|()
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|printStackTrace
parameter_list|(
name|PrintStream
name|s
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|printStackTrace
parameter_list|(
name|PrintWriter
name|s
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Throwable
name|getCause
parameter_list|()
block|{
return|return
name|e
return|;
block|}
annotation|@
name|Override
specifier|public
name|StackTraceElement
index|[]
name|getStackTrace
parameter_list|()
block|{
return|return
name|e
operator|.
name|getStackTrace
argument_list|()
return|;
block|}
block|}
end_class

end_unit

