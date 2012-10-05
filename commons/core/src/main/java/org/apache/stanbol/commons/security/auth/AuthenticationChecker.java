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
comment|/**  * A service that checks if a provided username and password matches a  * username and password in a credentials store.  *  * @author mir  */
end_comment

begin_interface
specifier|public
interface|interface
name|AuthenticationChecker
block|{
comment|/** 	 * Checks if the provided username and password matches a username and 	 * password in a credentials store. 	 * @param userName 	 *		The name of the user to authenticate. The name uniquely identifies 	 *		the user. 	 * @param password 	 *		The password used to authenticate the user identified by the user 	 *		name. 	 * @return	true is the user has been authenticated, false if the user can 	 *			not be authenticated 	 * @throws NoSuchAgent	if no user could be found for the provided user name 	 */
name|boolean
name|authenticate
parameter_list|(
name|String
name|userName
parameter_list|,
name|String
name|password
parameter_list|)
throws|throws
name|NoSuchAgent
function_decl|;
block|}
end_interface

end_unit

