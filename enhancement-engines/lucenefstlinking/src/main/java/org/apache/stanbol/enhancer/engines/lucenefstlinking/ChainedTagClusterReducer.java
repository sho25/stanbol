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
name|engines
operator|.
name|lucenefstlinking
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang
operator|.
name|ArrayUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|opensextant
operator|.
name|solrtexttagger
operator|.
name|TagClusterReducer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|opensextant
operator|.
name|solrtexttagger
operator|.
name|TagLL
import|;
end_import

begin_import
import|import
name|org
operator|.
name|opensextant
operator|.
name|solrtexttagger
operator|.
name|Tagger
import|;
end_import

begin_comment
comment|/**  * Allow to use multiple {@link TagClusterReducer} with a {@link Tagger}  * instance.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|ChainedTagClusterReducer
implements|implements
name|TagClusterReducer
block|{
specifier|private
specifier|final
name|TagClusterReducer
index|[]
name|reducers
decl_stmt|;
specifier|public
name|ChainedTagClusterReducer
parameter_list|(
name|TagClusterReducer
modifier|...
name|reducers
parameter_list|)
block|{
if|if
condition|(
name|reducers
operator|==
literal|null
operator|||
name|reducers
operator|==
literal|null
operator|||
name|ArrayUtils
operator|.
name|contains
argument_list|(
name|reducers
argument_list|,
literal|null
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed TagClusterReducers MUST NOT"
operator|+
literal|"be NULL an emoty array or contain any NULL element!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|reducers
operator|=
name|reducers
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|reduce
parameter_list|(
name|TagLL
index|[]
name|head
parameter_list|)
block|{
for|for
control|(
name|TagClusterReducer
name|reducer
range|:
name|reducers
control|)
block|{
if|if
condition|(
name|head
index|[
literal|0
index|]
operator|==
literal|null
condition|)
block|{
return|return;
comment|//no more tags left
block|}
name|reducer
operator|.
name|reduce
argument_list|(
name|head
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|new
name|StringBuilder
argument_list|(
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
name|Arrays
operator|.
name|toString
argument_list|(
name|reducers
argument_list|)
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

