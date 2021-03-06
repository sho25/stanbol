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
comment|/**  * This default implementation returns the parsed value. Intended to be used  * in cases where parsing<code>null</code> as {@link ScoreNormaliser} is not  * supported for some reason.  * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|DefaultNormaliser
implements|implements
name|ScoreNormaliser
block|{
specifier|private
name|ScoreNormaliser
name|normaliser
decl_stmt|;
annotation|@
name|Override
specifier|public
name|Float
name|normalise
parameter_list|(
name|Float
name|score
parameter_list|)
block|{
if|if
condition|(
name|normaliser
operator|!=
literal|null
condition|)
block|{
name|score
operator|=
name|normaliser
operator|.
name|normalise
argument_list|(
name|score
argument_list|)
expr_stmt|;
block|}
return|return
name|score
return|;
block|}
annotation|@
name|Override
specifier|public
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
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|CHAINED_SCORE_NORMALISER
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|normaliser
operator|=
operator|(
name|ScoreNormaliser
operator|)
name|value
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|ScoreNormaliser
name|getChained
parameter_list|()
block|{
return|return
name|normaliser
return|;
block|}
block|}
end_class

end_unit

