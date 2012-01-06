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
name|rules
operator|.
name|manager
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|JenaVariableMap
import|;
end_import

begin_class
specifier|public
class|class
name|JenaVariableMapImpl
implements|implements
name|JenaVariableMap
block|{
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|variableMap
decl_stmt|;
specifier|public
name|JenaVariableMapImpl
parameter_list|()
block|{
name|variableMap
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
argument_list|()
expr_stmt|;
block|}
specifier|public
name|JenaVariableMapImpl
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|variableMap
parameter_list|)
block|{
name|this
operator|.
name|variableMap
operator|=
name|variableMap
expr_stmt|;
block|}
specifier|public
name|int
name|getVariableIndex
parameter_list|(
name|String
name|ruleVariable
parameter_list|)
block|{
name|Integer
name|index
init|=
name|variableMap
operator|.
name|get
argument_list|(
name|ruleVariable
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|==
literal|null
condition|)
block|{
name|index
operator|=
name|variableMap
operator|.
name|size
argument_list|()
expr_stmt|;
name|variableMap
operator|.
name|put
argument_list|(
name|ruleVariable
argument_list|,
name|index
argument_list|)
expr_stmt|;
block|}
return|return
name|index
return|;
block|}
block|}
end_class

end_unit

