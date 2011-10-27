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
name|cmsadapter
operator|.
name|web
operator|.
name|utils
package|;
end_package

begin_comment
comment|/**  * Utility class for REST services  */
end_comment

begin_class
specifier|public
class|class
name|RestUtil
block|{
comment|/**      * @param parameter      *            parameter to be checked      * @return<code>null</code> if parameter has an empty content, otherwise trimmed<code>parameter</code>      */
specifier|public
specifier|static
name|String
name|nullify
parameter_list|(
name|String
name|parameter
parameter_list|)
block|{
if|if
condition|(
name|parameter
operator|!=
literal|null
condition|)
block|{
name|parameter
operator|=
name|parameter
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|parameter
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|parameter
operator|=
literal|null
expr_stmt|;
block|}
block|}
return|return
name|parameter
return|;
block|}
block|}
end_class

end_unit

