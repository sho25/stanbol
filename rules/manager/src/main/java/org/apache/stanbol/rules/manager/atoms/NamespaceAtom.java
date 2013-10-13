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
name|NamespaceAtom
extends|extends
name|StringFunctionAtom
block|{
specifier|private
name|IObjectAtom
name|uriResource
decl_stmt|;
specifier|public
name|NamespaceAtom
parameter_list|(
name|IObjectAtom
name|uriResource
parameter_list|)
block|{
name|this
operator|.
name|uriResource
operator|=
name|uriResource
expr_stmt|;
block|}
specifier|public
name|IObjectAtom
name|getUriResource
parameter_list|()
block|{
return|return
name|uriResource
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|String
name|argument
init|=
name|uriResource
operator|.
name|toString
argument_list|()
decl_stmt|;
return|return
literal|"namespace("
operator|+
name|argument
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
literal|"the namespace of the URI<"
operator|+
name|uriResource
operator|.
name|toString
argument_list|()
operator|+
literal|">"
return|;
block|}
block|}
end_class

end_unit

