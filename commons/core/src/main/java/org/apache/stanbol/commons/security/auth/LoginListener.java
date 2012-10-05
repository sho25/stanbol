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
comment|/**  * A Service interface whose implementations will be notified when a user has  * successfully logged in.  *  * @author tio  */
end_comment

begin_interface
specifier|public
interface|interface
name|LoginListener
block|{
comment|/** 	 * Notifies when a user is logged in successfully. 	 * 	 * @param userName of the user who has successfully logged in 	 */
specifier|public
name|void
name|userLoggedIn
parameter_list|(
name|String
name|userName
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

