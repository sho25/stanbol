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
name|commons
operator|.
name|jsonld
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_comment
comment|/**  * A comparator for JSON-LD maps to ensure the order of certain key elements  * like '#', '@', 'a' in JSON-LD output.  *  * @author Fabian Christ  */
end_comment

begin_class
specifier|public
class|class
name|JsonComparator
implements|implements
name|Comparator
argument_list|<
name|Object
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|arg0
parameter_list|,
name|Object
name|arg1
parameter_list|)
block|{
name|int
name|value
decl_stmt|;
if|if
condition|(
name|arg0
operator|.
name|equals
argument_list|(
name|arg1
argument_list|)
condition|)
block|{
name|value
operator|=
literal|0
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg0
operator|.
name|equals
argument_list|(
literal|"#"
argument_list|)
condition|)
block|{
name|value
operator|=
operator|-
literal|1
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg1
operator|.
name|equals
argument_list|(
literal|"#"
argument_list|)
condition|)
block|{
name|value
operator|=
literal|1
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg0
operator|.
name|equals
argument_list|(
literal|"@"
argument_list|)
condition|)
block|{
name|value
operator|=
operator|-
literal|1
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg1
operator|.
name|equals
argument_list|(
literal|"@"
argument_list|)
condition|)
block|{
name|value
operator|=
literal|1
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg0
operator|.
name|equals
argument_list|(
literal|"a"
argument_list|)
condition|)
block|{
name|value
operator|=
operator|-
literal|1
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg1
operator|.
name|equals
argument_list|(
literal|"a"
argument_list|)
condition|)
block|{
name|value
operator|=
literal|1
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg0
operator|.
name|equals
argument_list|(
literal|"#base"
argument_list|)
condition|)
block|{
name|value
operator|=
operator|-
literal|1
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg1
operator|.
name|equals
argument_list|(
literal|"#base"
argument_list|)
condition|)
block|{
name|value
operator|=
literal|1
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg0
operator|.
name|equals
argument_list|(
literal|"#vocab"
argument_list|)
condition|)
block|{
name|value
operator|=
operator|-
literal|1
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg1
operator|.
name|equals
argument_list|(
literal|"#vocab"
argument_list|)
condition|)
block|{
name|value
operator|=
literal|1
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg0
operator|.
name|equals
argument_list|(
literal|"#types"
argument_list|)
condition|)
block|{
name|value
operator|=
literal|1
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg1
operator|.
name|equals
argument_list|(
literal|"#types"
argument_list|)
condition|)
block|{
name|value
operator|=
operator|-
literal|1
expr_stmt|;
block|}
else|else
block|{
name|value
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|arg0
argument_list|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|compareTo
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|arg1
argument_list|)
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|value
return|;
block|}
block|}
end_class

end_unit

