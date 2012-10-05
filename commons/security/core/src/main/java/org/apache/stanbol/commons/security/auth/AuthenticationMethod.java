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

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|security
operator|.
name|auth
operator|.
name|Subject
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
import|;
end_import

begin_comment
comment|/**  * Classes implementing this interface provide a method to authenticate a  * a user with the information provided in a http request.  */
end_comment

begin_interface
specifier|public
interface|interface
name|AuthenticationMethod
block|{
comment|/** 	 * Returns the Subject of the authenticate user containing the principal 	 * of the authentication and possibly some credentials.  If the authentication failed, an 	 *<code>LoginException</code> will be thrown. If no authentication 	 * information are available null is returned. 	 * @param request containing the information to authenticate a subject 	 * @param subject to add authentication information to 	 * @return true if this method did authenticate, false otherwise 	 * @throws LoginException This exception is thrown in case 	 * the login procedure failed. 	 * @throws HandlerException 	 */
specifier|public
name|boolean
name|authenticate
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Subject
name|subject
parameter_list|)
throws|throws
name|LoginException
throws|,
name|ServletException
function_decl|;
comment|/** 	 * Modifies the specified<code>Response</code> according the specified 	 *<code>Request</code> and<code>Throwable</code> 	 * (e.g.<code>LoginException</code> or<code>AccessControllException</code>. 	 * The response leads to or provides further instructions for a client to 	 * log in. 	 * @return true, iff the response was modified 	 */
specifier|public
name|boolean
name|writeLoginResponse
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|,
name|Throwable
name|cause
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
function_decl|;
block|}
end_interface

end_unit

