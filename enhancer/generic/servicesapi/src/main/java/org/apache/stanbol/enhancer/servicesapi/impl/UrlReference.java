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
name|enhancer
operator|.
name|servicesapi
operator|.
name|impl
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
name|java
operator|.
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLConnection
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentReference
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentSource
import|;
end_import

begin_comment
comment|/**  * Allows to use a URL for referencing a content.  */
end_comment

begin_class
specifier|public
class|class
name|UrlReference
implements|implements
name|ContentReference
block|{
specifier|final
name|URL
name|url
decl_stmt|;
comment|/**      * Uses the passed URI string to parse the URL.      * @param uri an absolute URI that can be converted to an URL      * @throws IllegalArgumentException if the passed URI string is<code>null</code>      * or can not be converted to an {@link URL}      */
specifier|public
name|UrlReference
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
if|if
condition|(
name|uri
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed URI reference MUST NOT be NULL!"
argument_list|)
throw|;
block|}
try|try
block|{
name|this
operator|.
name|url
operator|=
name|URI
operator|.
name|create
argument_list|(
name|uri
argument_list|)
operator|.
name|toURL
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"the passed URI can not be converted to an URL"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|public
name|UrlReference
parameter_list|(
name|URL
name|url
parameter_list|)
block|{
if|if
condition|(
name|url
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed URL MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|url
operator|=
name|url
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getReference
parameter_list|()
block|{
return|return
name|url
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|ContentSource
name|dereference
parameter_list|()
throws|throws
name|IOException
block|{
name|URLConnection
name|uc
init|=
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
return|return
operator|new
name|StreamSource
argument_list|(
name|uc
operator|.
name|getInputStream
argument_list|()
argument_list|,
name|uc
operator|.
name|getContentType
argument_list|()
argument_list|,
name|uc
operator|.
name|getHeaderFields
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

