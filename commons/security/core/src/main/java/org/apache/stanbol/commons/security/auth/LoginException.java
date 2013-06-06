begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one  * or more contributor license agreements.  See the NOTICE file  * distributed with this work for additional information  * regarding copyright ownership.  The ASF licenses this file  * to you under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance  * with the License.  You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing,  * software distributed under the License is distributed on an  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY  * KIND, either express or implied.  See the License for the  * specific language governing permissions and limitations  * under the License.  */
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
name|security
operator|.
name|auth
package|;
end_package

begin_comment
comment|/**  * This exception is thrown when information needed for authentication is  * missing or invalid.  *  * @author mir  */
end_comment

begin_class
specifier|public
class|class
name|LoginException
extends|extends
name|Exception
block|{
specifier|public
specifier|static
specifier|final
name|String
name|USER_NOT_EXISTING
init|=
literal|"user not existing"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PASSWORD_NOT_MATCHING
init|=
literal|"password did not match"
decl_stmt|;
specifier|private
name|String
name|type
decl_stmt|;
specifier|public
name|LoginException
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
block|}
end_class

end_unit

