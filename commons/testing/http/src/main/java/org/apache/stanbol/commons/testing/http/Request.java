begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements. See the NOTICE file distributed with this  * work for additional information regarding copyright ownership. The ASF  * licenses this file to You under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the  * License for the specific language governing permissions and limitations under  * the License.  */
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
name|testing
operator|.
name|http
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|HttpEntity
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|methods
operator|.
name|HttpEntityEnclosingRequestBase
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|methods
operator|.
name|HttpUriRequest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|entity
operator|.
name|StringEntity
import|;
end_import

begin_comment
comment|/** Request class with convenience with... methods to   *  add headers, parameters etc.  */
end_comment

begin_class
specifier|public
class|class
name|Request
block|{
specifier|private
specifier|final
name|HttpUriRequest
name|request
decl_stmt|;
specifier|private
name|String
name|username
decl_stmt|;
specifier|private
name|String
name|password
decl_stmt|;
specifier|private
name|boolean
name|redirects
init|=
literal|true
decl_stmt|;
name|Request
parameter_list|(
name|HttpUriRequest
name|r
parameter_list|)
block|{
name|request
operator|=
name|r
expr_stmt|;
block|}
specifier|public
name|HttpUriRequest
name|getRequest
parameter_list|()
block|{
return|return
name|request
return|;
block|}
specifier|public
name|Request
name|withHeader
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|request
operator|.
name|addHeader
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|Request
name|withCredentials
parameter_list|(
name|String
name|username
parameter_list|,
name|String
name|password
parameter_list|)
block|{
name|this
operator|.
name|username
operator|=
name|username
expr_stmt|;
name|this
operator|.
name|password
operator|=
name|password
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|Request
name|withRedirects
parameter_list|(
name|boolean
name|followRedirectsAutomatically
parameter_list|)
block|{
name|redirects
operator|=
name|followRedirectsAutomatically
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|private
name|HttpEntityEnclosingRequestBase
name|getHttpEntityEnclosingRequestBase
parameter_list|()
block|{
if|if
condition|(
name|request
operator|instanceof
name|HttpEntityEnclosingRequestBase
condition|)
block|{
return|return
operator|(
name|HttpEntityEnclosingRequestBase
operator|)
name|request
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Request is not an HttpEntityEnclosingRequestBase: "
operator|+
name|request
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
throw|;
block|}
block|}
specifier|public
name|Request
name|withContent
parameter_list|(
name|String
name|content
parameter_list|)
throws|throws
name|UnsupportedEncodingException
block|{
return|return
name|withEntity
argument_list|(
operator|new
name|StringEntity
argument_list|(
name|content
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|Request
name|withEntity
parameter_list|(
name|HttpEntity
name|e
parameter_list|)
throws|throws
name|UnsupportedEncodingException
block|{
name|getHttpEntityEnclosingRequestBase
argument_list|()
operator|.
name|setEntity
argument_list|(
name|e
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|String
name|getUsername
parameter_list|()
block|{
return|return
name|username
return|;
block|}
specifier|public
name|String
name|getPassword
parameter_list|()
block|{
return|return
name|password
return|;
block|}
specifier|public
name|boolean
name|getRedirects
parameter_list|()
block|{
return|return
name|redirects
return|;
block|}
block|}
end_class

end_unit

