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
name|HttpGet
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
name|HttpPost
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
name|HttpRequestBase
import|;
end_import

begin_comment
comment|/** Convenience builder for Request objects */
end_comment

begin_class
specifier|public
class|class
name|RequestBuilder
block|{
specifier|private
specifier|final
name|String
name|baseUrl
decl_stmt|;
specifier|public
name|RequestBuilder
parameter_list|(
name|String
name|baseUrl
parameter_list|)
block|{
name|this
operator|.
name|baseUrl
operator|=
name|baseUrl
expr_stmt|;
block|}
specifier|public
name|Request
name|buildGetRequest
parameter_list|(
name|String
name|path
parameter_list|)
block|{
return|return
operator|new
name|Request
argument_list|(
operator|new
name|HttpGet
argument_list|(
name|baseUrl
operator|+
name|path
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|Request
name|buildPostRequest
parameter_list|(
name|String
name|path
parameter_list|)
block|{
return|return
operator|new
name|Request
argument_list|(
operator|new
name|HttpPost
argument_list|(
name|baseUrl
operator|+
name|path
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|Request
name|buildOtherRequest
parameter_list|(
name|HttpRequestBase
name|r
parameter_list|)
block|{
return|return
operator|new
name|Request
argument_list|(
name|r
argument_list|)
return|;
block|}
block|}
end_class

end_unit

