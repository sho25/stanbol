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
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Reference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ReferenceCardinality
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ReferencePolicy
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Provides user authentication given the user credentials.  *  * This service considers all enabled {@link AuthenticationChecker} services to   * authenticate a user. If multiple AuthenticationCheckers are present,   * only one needs to positively authenticate the user for the authentication  * process to succeed.  *  * @author daniel  */
end_comment

begin_class
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|AuthenticationService
operator|.
name|class
argument_list|)
annotation|@
name|Reference
argument_list|(
name|name
operator|=
literal|"restrictionElement"
argument_list|,
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|MANDATORY_MULTIPLE
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|,
name|referenceInterface
operator|=
name|AuthenticationChecker
operator|.
name|class
argument_list|)
specifier|public
class|class
name|AuthenticationService
block|{
specifier|private
specifier|final
specifier|static
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|AuthenticationCheckerImpl
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|List
argument_list|<
name|AuthenticationChecker
argument_list|>
name|authenticationCheckers
init|=
operator|new
name|ArrayList
argument_list|<
name|AuthenticationChecker
argument_list|>
argument_list|()
decl_stmt|;
comment|/** 	 * Authenticates a user given its user name and password credentials. 	 * 	 * @param userName 	 *		The name of the user to authenticate. The name uniquely identifies 	 *		the user. 	 * @param password	 	 *		The password used to authenticate the user identified by the user 	 *		name. 	 * @return	true is the user has been authenticated, false if the user can 	 *			not be authenticated 	 * @throws NoSuchAgent	if no user could be found for the provided user name 	 */
specifier|public
name|boolean
name|authenticateUser
parameter_list|(
name|String
name|userName
parameter_list|,
name|String
name|password
parameter_list|)
throws|throws
name|NoSuchAgent
block|{
name|boolean
name|userNameExists
init|=
literal|false
decl_stmt|;
for|for
control|(
name|AuthenticationChecker
name|checker
range|:
name|authenticationCheckers
control|)
block|{
try|try
block|{
if|if
condition|(
name|checker
operator|.
name|authenticate
argument_list|(
name|userName
argument_list|,
name|password
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
name|userNameExists
operator|=
literal|true
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchAgent
name|ex
parameter_list|)
block|{
continue|continue;
block|}
block|}
if|if
condition|(
operator|!
name|userNameExists
condition|)
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"No service could unsuccessfully authenticate user {}. Reason: user does not exist"
argument_list|,
name|userName
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|NoSuchAgent
argument_list|()
throw|;
block|}
return|return
literal|false
return|;
block|}
comment|/** 	 * Called when new {@link AuthenticationChecker} services are registered in 	 * the OSGi environment. 	 * 	 * @param service	the AuthenticationChecker 	 */
specifier|protected
name|void
name|bindAuthenticationChecker
parameter_list|(
name|AuthenticationChecker
name|service
parameter_list|)
block|{
name|authenticationCheckers
operator|.
name|add
argument_list|(
name|service
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Called when {@link AuthenticationChecker} services are unregistered 	 * in the OSGi environment. 	 * 	 * @param service	the AuthenticationChecker 	 */
specifier|protected
name|void
name|unbindAuthenticationChecker
parameter_list|(
name|AuthenticationChecker
name|service
parameter_list|)
block|{
name|authenticationCheckers
operator|.
name|remove
argument_list|(
name|service
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

