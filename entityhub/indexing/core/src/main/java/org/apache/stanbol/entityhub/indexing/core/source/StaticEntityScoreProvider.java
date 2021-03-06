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
name|source
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

begin_import
import|import
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
name|EntityScoreProvider
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
import|;
end_import

begin_comment
comment|/**  * Implementation of an {@link EntityScoreProvider} that parsed the same value  * for all entities. The default value is '1' but it can be configured by using  * {@link #PARAM_SCORE}("{@value #PARAM_SCORE}").  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|StaticEntityScoreProvider
implements|implements
name|EntityScoreProvider
block|{
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_SCORE
init|=
literal|"score"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Float
name|DEFAULT_SCORE
init|=
name|Float
operator|.
name|valueOf
argument_list|(
literal|1f
argument_list|)
decl_stmt|;
specifier|private
name|Float
name|score
init|=
name|DEFAULT_SCORE
decl_stmt|;
specifier|public
name|StaticEntityScoreProvider
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|StaticEntityScoreProvider
parameter_list|(
name|Float
name|score
parameter_list|)
block|{
name|setScore
argument_list|(
name|score
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param score      */
specifier|private
name|void
name|setScore
parameter_list|(
name|Float
name|score
parameter_list|)
block|{
if|if
condition|(
name|score
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|score
operator|=
name|DEFAULT_SCORE
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|score
operator|<=
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Score MUST NOT be<= 0!"
argument_list|)
throw|;
block|}
else|else
block|{
name|this
operator|.
name|score
operator|=
name|score
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|needsData
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|Float
name|process
parameter_list|(
name|String
name|id
parameter_list|)
block|{
return|return
name|score
return|;
block|}
annotation|@
name|Override
specifier|public
name|Float
name|process
parameter_list|(
name|Representation
name|entity
parameter_list|)
block|{
return|return
name|score
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
block|{     }
annotation|@
name|Override
specifier|public
name|void
name|initialise
parameter_list|()
block|{     }
annotation|@
name|Override
specifier|public
name|boolean
name|needsInitialisation
parameter_list|()
block|{
return|return
literal|false
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
name|PARAM_SCORE
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|setScore
argument_list|(
name|Float
operator|.
name|valueOf
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse score from value {}"
argument_list|,
name|value
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
block|}
end_class

end_unit

