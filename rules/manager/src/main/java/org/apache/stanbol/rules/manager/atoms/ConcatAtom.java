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
name|manager
operator|.
name|atoms
package|;
end_package

begin_comment
comment|/**  *   * Atom for concatenation. Returns the concatenation of the first argument with the secondo argument.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|ConcatAtom
extends|extends
name|StringFunctionAtom
block|{
specifier|private
name|StringFunctionAtom
name|argument1
decl_stmt|;
specifier|private
name|StringFunctionAtom
name|argument2
decl_stmt|;
specifier|public
name|ConcatAtom
parameter_list|(
name|StringFunctionAtom
name|argument1
parameter_list|,
name|StringFunctionAtom
name|argument2
parameter_list|)
block|{
name|this
operator|.
name|argument1
operator|=
name|argument1
expr_stmt|;
name|this
operator|.
name|argument2
operator|=
name|argument2
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"concat("
operator|+
name|argument1
operator|.
name|prettyPrint
argument_list|()
operator|+
literal|", "
operator|+
name|argument2
operator|.
name|prettyPrint
argument_list|()
operator|+
literal|")"
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|prettyPrint
parameter_list|()
block|{
return|return
literal|"Concatenation of "
operator|+
name|argument1
operator|.
name|toString
argument_list|()
operator|+
literal|" with "
operator|+
name|argument2
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|StringFunctionAtom
name|getArgument1
parameter_list|()
block|{
return|return
name|argument1
return|;
block|}
specifier|public
name|StringFunctionAtom
name|getArgument2
parameter_list|()
block|{
return|return
name|argument2
return|;
block|}
block|}
end_class

end_unit

