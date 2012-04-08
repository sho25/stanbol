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
name|Resource
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
name|enhancer
operator|.
name|ldpath
operator|.
name|backend
operator|.
name|ContentItemBackend
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

begin_comment
comment|/**  * This class checks if the {@link RDFBackend} parsed to   * {@link #apply(ContentItemBackend, Collection...) apply} is an instance of  * {@link ContentItemBackend}. It also implements the   * {@link #getPathExpression(RDFBackend)} method by returning the name parsed  * in the constructor.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|ContentItemFunction
implements|implements
name|SelectorFunction
argument_list|<
name|Resource
argument_list|>
block|{
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
specifier|protected
name|ContentItemFunction
parameter_list|(
name|String
name|name
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
literal|"The parsed name MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|Collection
argument_list|<
name|Resource
argument_list|>
name|apply
parameter_list|(
name|RDFBackend
argument_list|<
name|Resource
argument_list|>
name|backend
parameter_list|,
name|Resource
name|context
parameter_list|,
name|Collection
argument_list|<
name|Resource
argument_list|>
modifier|...
name|args
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
if|if
condition|(
name|backend
operator|instanceof
name|ContentItemBackend
condition|)
block|{
return|return
name|apply
argument_list|(
operator|(
name|ContentItemBackend
operator|)
name|backend
argument_list|,
name|context
argument_list|,
name|args
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"This ContentFunction can only be "
operator|+
literal|"used in combination with an RDFBackend of type '"
operator|+
name|ContentItemBackend
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"' (parsed Backend: "
operator|+
name|backend
operator|.
name|getClass
argument_list|()
operator|+
literal|")!"
argument_list|)
throw|;
block|}
block|}
empty_stmt|;
specifier|public
specifier|abstract
name|Collection
argument_list|<
name|Resource
argument_list|>
name|apply
parameter_list|(
name|ContentItemBackend
name|backend
parameter_list|,
name|Resource
name|context
parameter_list|,
name|Collection
argument_list|<
name|Resource
argument_list|>
modifier|...
name|args
parameter_list|)
function_decl|;
annotation|@
name|Override
specifier|public
name|String
name|getPathExpression
parameter_list|(
name|RDFBackend
argument_list|<
name|Resource
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

