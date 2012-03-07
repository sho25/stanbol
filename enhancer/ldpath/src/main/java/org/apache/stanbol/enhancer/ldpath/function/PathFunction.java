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
name|ldpath
operator|.
name|function
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

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
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|backend
operator|.
name|RDFBackend
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|functions
operator|.
name|SelectorFunction
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|selectors
operator|.
name|NodeSelector
import|;
end_import

begin_comment
comment|/**  * Maps a {@link NodeSelector} to a function name. This is useful to provide  * function shortcuts for longer ld-path statements.  * @author Rupert Westenthaler  *  * @param<Node>  */
end_comment

begin_class
specifier|public
class|class
name|PathFunction
parameter_list|<
name|Node
parameter_list|>
implements|implements
name|SelectorFunction
argument_list|<
name|Node
argument_list|>
block|{
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
specifier|private
specifier|final
name|NodeSelector
argument_list|<
name|Node
argument_list|>
name|selector
decl_stmt|;
comment|/**      * create a function available under fn:{name} for the parsed selector      * @param name the name of the function MUST NOT be<code>null</code> nor      * empty      * @param selector the selector MUST NOT be<code>null</code>      * @throws IllegalArgumentException if the parsed name is<code>null</code>      * or empty; if the parsed {@link NodeSelector} is<code>null</code>.      */
specifier|public
name|PathFunction
parameter_list|(
name|String
name|name
parameter_list|,
name|NodeSelector
argument_list|<
name|Node
argument_list|>
name|selector
parameter_list|)
block|{
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed function name MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
if|if
condition|(
name|selector
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed NodeSelector MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|selector
operator|=
name|selector
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|Node
argument_list|>
name|apply
parameter_list|(
name|RDFBackend
argument_list|<
name|Node
argument_list|>
name|backend
parameter_list|,
name|Collection
argument_list|<
name|Node
argument_list|>
modifier|...
name|args
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
if|if
condition|(
name|args
operator|==
literal|null
operator|||
name|args
operator|.
name|length
operator|<
literal|1
operator|||
name|args
index|[
literal|0
index|]
operator|==
literal|null
operator|||
name|args
index|[
literal|0
index|]
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The 'fn:"
operator|+
name|name
operator|+
literal|"' function "
operator|+
literal|"requires at least a single none empty parameter (the context). Use 'fn:"
operator|+
name|name
operator|+
literal|"(.)' to execute it on the path context!"
argument_list|)
throw|;
block|}
name|Set
argument_list|<
name|Node
argument_list|>
name|selected
init|=
operator|new
name|HashSet
argument_list|<
name|Node
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Node
name|context
range|:
name|args
index|[
literal|0
index|]
control|)
block|{
name|selected
operator|.
name|addAll
argument_list|(
name|selector
operator|.
name|select
argument_list|(
name|backend
argument_list|,
name|context
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|selected
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getPathExpression
parameter_list|(
name|RDFBackend
argument_list|<
name|Node
argument_list|>
name|backend
parameter_list|)
block|{
return|return
name|name
return|;
block|}
block|}
end_class

end_unit
