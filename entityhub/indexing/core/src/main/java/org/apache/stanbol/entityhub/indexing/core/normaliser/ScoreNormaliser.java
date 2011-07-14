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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|normaliser
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  * This Interface provides the possibility to process score values provided for  * Entities (e.g. to calculate the pageRank based on the number of incomming links  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|ScoreNormaliser
block|{
comment|/**      * Key used to configure an other {@link ScoreNormaliser} instance that      * should be called before this instance processes the score. Values      * MUST BE of type {@link ScoreNormaliser}.      */
name|String
name|CHAINED_SCORE_NORMALISER
init|=
literal|"chained"
decl_stmt|;
comment|/**      * -1 will be used a lot with implementations of this interface      */
specifier|public
specifier|static
specifier|final
name|Float
name|MINUS_ONE
init|=
name|Float
operator|.
name|valueOf
argument_list|(
operator|-
literal|1f
argument_list|)
decl_stmt|;
comment|/**      * 0 will be used a lot with implementations of this interface      */
specifier|public
specifier|static
specifier|final
name|Float
name|ZERO
init|=
name|Float
operator|.
name|valueOf
argument_list|(
literal|0f
argument_list|)
decl_stmt|;
name|void
name|setConfiguration
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
function_decl|;
comment|/**      * Normalises the parsed score value based on some algorithm.      * @param score The score to normalise.<code>null</code> and values&lt; 0      * MUST be ignored and returned as parsed.      * @return<code>null</code> if no score can be calculated based on the      * parsed value (especially if<code>null</code> is parsed as score).      * Otherwise the normalized score where values&lt;0 indicate that the      * entity should not be indexed.      */
name|Float
name|normalise
parameter_list|(
name|Float
name|score
parameter_list|)
function_decl|;
comment|/**      * Returns the {@link ScoreNormaliser} instance that is chained to this one.      * Parsed scores are first parsed to chained instances before they are      * processed by current one.      * @return the chained instance or<code>null</code> if none      */
name|ScoreNormaliser
name|getChained
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

