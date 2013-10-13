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

begin_class
specifier|public
class|class
name|StartsWithAtom
extends|extends
name|ComparisonAtom
block|{
specifier|private
name|StringFunctionAtom
name|argument
decl_stmt|;
specifier|private
name|StringFunctionAtom
name|term
decl_stmt|;
specifier|public
name|StartsWithAtom
parameter_list|(
name|StringFunctionAtom
name|argument
parameter_list|,
name|StringFunctionAtom
name|term
parameter_list|)
block|{
name|this
operator|.
name|argument
operator|=
name|argument
expr_stmt|;
name|this
operator|.
name|term
operator|=
name|term
expr_stmt|;
block|}
specifier|public
name|StringFunctionAtom
name|getArgument
parameter_list|()
block|{
return|return
name|argument
return|;
block|}
specifier|public
name|StringFunctionAtom
name|getTerm
parameter_list|()
block|{
return|return
name|term
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"startsWith("
operator|+
name|argument
operator|.
name|toString
argument_list|()
operator|+
literal|", "
operator|+
name|term
operator|.
name|toString
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
name|argument
operator|.
name|prettyPrint
argument_list|()
operator|+
literal|" starts with "
operator|+
name|term
operator|.
name|prettyPrint
argument_list|()
return|;
block|}
block|}
end_class

end_unit

