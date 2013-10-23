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
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertNotNull
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|fail
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|CharsetDecoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|IOUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|LineIterator
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
name|Header
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
name|HttpException
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
name|HttpHost
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
name|HttpRequest
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
name|HttpRequestInterceptor
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
name|HttpResponse
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
name|ParseException
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
name|auth
operator|.
name|AuthScope
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
name|auth
operator|.
name|AuthState
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
name|auth
operator|.
name|Credentials
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
name|auth
operator|.
name|UsernamePasswordCredentials
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
name|ClientProtocolException
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
name|CredentialsProvider
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
name|client
operator|.
name|params
operator|.
name|ClientPNames
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
name|protocol
operator|.
name|ClientContext
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
name|ContentType
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
name|impl
operator|.
name|auth
operator|.
name|BasicScheme
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
name|impl
operator|.
name|client
operator|.
name|DefaultHttpClient
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
name|protocol
operator|.
name|ExecutionContext
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
name|protocol
operator|.
name|HTTP
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
name|protocol
operator|.
name|HttpContext
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
name|util
operator|.
name|EntityUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_comment
comment|/**  * Executes a Request and provides convenience methods  * to validate the results.  */
end_comment

begin_class
specifier|public
class|class
name|RequestExecutor
block|{
specifier|private
specifier|final
name|DefaultHttpClient
name|httpClient
decl_stmt|;
specifier|private
name|HttpUriRequest
name|request
decl_stmt|;
specifier|private
name|HttpResponse
name|response
decl_stmt|;
specifier|private
name|HttpEntity
name|entity
decl_stmt|;
specifier|private
name|String
name|contentString
decl_stmt|;
specifier|private
name|byte
index|[]
name|content
decl_stmt|;
specifier|private
name|ContentType
name|contentType
decl_stmt|;
specifier|private
name|Charset
name|charset
decl_stmt|;
comment|/**      * HttpRequestInterceptor for preemptive authentication, based on httpclient      * 4.0 example      */
specifier|private
specifier|static
class|class
name|PreemptiveAuthInterceptor
implements|implements
name|HttpRequestInterceptor
block|{
specifier|public
name|void
name|process
parameter_list|(
name|HttpRequest
name|request
parameter_list|,
name|HttpContext
name|context
parameter_list|)
throws|throws
name|HttpException
throws|,
name|IOException
block|{
name|AuthState
name|authState
init|=
operator|(
name|AuthState
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|ClientContext
operator|.
name|TARGET_AUTH_STATE
argument_list|)
decl_stmt|;
name|CredentialsProvider
name|credsProvider
init|=
operator|(
name|CredentialsProvider
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|ClientContext
operator|.
name|CREDS_PROVIDER
argument_list|)
decl_stmt|;
name|HttpHost
name|targetHost
init|=
operator|(
name|HttpHost
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|ExecutionContext
operator|.
name|HTTP_TARGET_HOST
argument_list|)
decl_stmt|;
comment|// If not auth scheme has been initialized yet
if|if
condition|(
name|authState
operator|.
name|getAuthScheme
argument_list|()
operator|==
literal|null
condition|)
block|{
name|AuthScope
name|authScope
init|=
operator|new
name|AuthScope
argument_list|(
name|targetHost
operator|.
name|getHostName
argument_list|()
argument_list|,
name|targetHost
operator|.
name|getPort
argument_list|()
argument_list|)
decl_stmt|;
comment|// Obtain credentials matching the target host
name|Credentials
name|creds
init|=
name|credsProvider
operator|.
name|getCredentials
argument_list|(
name|authScope
argument_list|)
decl_stmt|;
comment|// If found, generate BasicScheme preemptively
if|if
condition|(
name|creds
operator|!=
literal|null
condition|)
block|{
name|authState
operator|.
name|update
argument_list|(
operator|new
name|BasicScheme
argument_list|()
argument_list|,
name|creds
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
name|RequestExecutor
parameter_list|(
name|DefaultHttpClient
name|client
parameter_list|)
block|{
name|httpClient
operator|=
name|client
expr_stmt|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
if|if
condition|(
name|request
operator|==
literal|null
condition|)
block|{
return|return
literal|"Request"
return|;
block|}
return|return
name|request
operator|.
name|getMethod
argument_list|()
operator|+
literal|" request to "
operator|+
name|request
operator|.
name|getURI
argument_list|()
return|;
block|}
comment|/**      * Executes a {@link Request} using this executor.<p>      * Note that this cleans up all data of the previous executed request.      * @param r the request to execute      * @return this      * @throws ClientProtocolException      * @throws IOException      */
specifier|public
name|RequestExecutor
name|execute
parameter_list|(
name|Request
name|r
parameter_list|)
throws|throws
name|ClientProtocolException
throws|,
name|IOException
block|{
name|clear
argument_list|()
expr_stmt|;
name|request
operator|=
name|r
operator|.
name|getRequest
argument_list|()
expr_stmt|;
comment|// Optionally setup for basic authentication
if|if
condition|(
name|r
operator|.
name|getUsername
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|httpClient
operator|.
name|getCredentialsProvider
argument_list|()
operator|.
name|setCredentials
argument_list|(
name|AuthScope
operator|.
name|ANY
argument_list|,
operator|new
name|UsernamePasswordCredentials
argument_list|(
name|r
operator|.
name|getUsername
argument_list|()
argument_list|,
name|r
operator|.
name|getPassword
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// And add request interceptor to have preemptive authentication
name|httpClient
operator|.
name|addRequestInterceptor
argument_list|(
operator|new
name|PreemptiveAuthInterceptor
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, null);
name|httpClient
operator|.
name|removeRequestInterceptorByClass
argument_list|(
name|PreemptiveAuthInterceptor
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
comment|// Setup redirects
name|httpClient
operator|.
name|getParams
argument_list|()
operator|.
name|setBooleanParameter
argument_list|(
name|ClientPNames
operator|.
name|HANDLE_REDIRECTS
argument_list|,
name|r
operator|.
name|getRedirects
argument_list|()
argument_list|)
expr_stmt|;
comment|// Execute request
name|response
operator|=
name|httpClient
operator|.
name|execute
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|entity
operator|=
name|response
operator|.
name|getEntity
argument_list|()
expr_stmt|;
if|if
condition|(
name|entity
operator|!=
literal|null
condition|)
block|{
comment|// We fully read the content every time, not super efficient but
comment|// how can we read it on demand while avoiding a (boring) cleanup()
comment|// method on this class?
name|content
operator|=
name|EntityUtils
operator|.
name|toByteArray
argument_list|(
name|entity
argument_list|)
expr_stmt|;
name|contentType
operator|=
name|ContentType
operator|.
name|getOrDefault
argument_list|(
name|entity
argument_list|)
expr_stmt|;
name|charset
operator|=
name|contentType
operator|.
name|getCharset
argument_list|()
expr_stmt|;
name|contentString
operator|=
operator|new
name|String
argument_list|(
name|content
argument_list|,
name|charset
operator|!=
literal|null
condition|?
name|charset
else|:
name|HTTP
operator|.
name|DEF_CONTENT_CHARSET
argument_list|)
expr_stmt|;
comment|//and close the stream
name|entity
operator|.
name|getContent
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
return|return
name|this
return|;
block|}
specifier|protected
name|void
name|clear
parameter_list|()
block|{
name|request
operator|=
literal|null
expr_stmt|;
name|entity
operator|=
literal|null
expr_stmt|;
name|contentType
operator|=
literal|null
expr_stmt|;
name|charset
operator|=
literal|null
expr_stmt|;
name|content
operator|=
literal|null
expr_stmt|;
name|contentString
operator|=
literal|null
expr_stmt|;
name|response
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * Verify that response matches supplied status      */
specifier|public
name|RequestExecutor
name|assertStatus
parameter_list|(
name|int
name|expected
parameter_list|)
block|{
name|assertNotNull
argument_list|(
name|this
operator|.
name|toString
argument_list|()
argument_list|,
name|response
argument_list|)
expr_stmt|;
name|int
name|status
init|=
name|response
operator|.
name|getStatusLine
argument_list|()
operator|.
name|getStatusCode
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|this
operator|+
literal|": expecting status "
operator|+
name|expected
operator|+
literal|" (content: "
operator|+
name|contentString
operator|+
literal|")"
argument_list|,
name|expected
argument_list|,
name|status
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
comment|/**      * Verify that response matches supplied content type      */
specifier|public
name|RequestExecutor
name|assertContentType
parameter_list|(
name|String
name|expected
parameter_list|)
block|{
name|assertNotNull
argument_list|(
name|this
operator|.
name|toString
argument_list|()
argument_list|,
name|response
argument_list|)
expr_stmt|;
if|if
condition|(
name|entity
operator|==
literal|null
condition|)
block|{
name|fail
argument_list|(
name|this
operator|+
literal|": no entity in response, cannot check content type"
argument_list|)
expr_stmt|;
block|}
comment|// And check for match
name|assertEquals
argument_list|(
name|this
operator|+
literal|": expecting content type "
operator|+
name|expected
argument_list|,
name|expected
argument_list|,
name|contentType
operator|.
name|getMimeType
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
comment|/**      * Verify that response matches supplied charset      */
specifier|public
name|RequestExecutor
name|assertCharset
parameter_list|(
name|String
name|expected
parameter_list|)
block|{
name|assertNotNull
argument_list|(
name|this
operator|.
name|toString
argument_list|()
argument_list|,
name|response
argument_list|)
expr_stmt|;
if|if
condition|(
name|entity
operator|==
literal|null
condition|)
block|{
name|fail
argument_list|(
name|this
operator|+
literal|": no entity in response, cannot check content type"
argument_list|)
expr_stmt|;
block|}
name|assertEquals
argument_list|(
name|this
operator|+
literal|": expecting charset "
operator|+
name|expected
argument_list|,
name|expected
argument_list|,
name|charset
operator|==
literal|null
condition|?
literal|null
else|:
name|charset
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|RequestExecutor
name|assertHeader
parameter_list|(
name|String
name|key
parameter_list|,
name|String
modifier|...
name|values
parameter_list|)
block|{
name|assertNotNull
argument_list|(
name|this
operator|.
name|toString
argument_list|()
argument_list|,
name|response
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|expectedValues
decl_stmt|;
if|if
condition|(
name|values
operator|==
literal|null
operator|||
name|values
operator|.
name|length
operator|<
literal|1
condition|)
block|{
name|expectedValues
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|expectedValues
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|values
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Header
index|[]
name|headers
init|=
name|response
operator|.
name|getHeaders
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|headers
operator|.
name|length
operator|<
literal|1
condition|)
block|{
name|headers
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|expectedValues
operator|==
literal|null
condition|)
block|{
name|assertTrue
argument_list|(
literal|"The header "
operator|+
name|key
operator|+
literal|" MUST NOT have any values (values: "
operator|+
name|headers
operator|+
literal|")"
argument_list|,
name|headers
operator|==
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|assertNotNull
argument_list|(
literal|"There are no values for header "
operator|+
name|key
operator|+
literal|"!"
argument_list|,
name|headers
argument_list|)
expr_stmt|;
for|for
control|(
name|Header
name|header
range|:
name|headers
control|)
block|{
name|assertTrue
argument_list|(
literal|"Unexpected header value "
operator|+
name|header
operator|.
name|getValue
argument_list|()
argument_list|,
name|expectedValues
operator|.
name|remove
argument_list|(
name|header
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
literal|"Missing header values "
operator|+
name|expectedValues
operator|+
literal|"!"
argument_list|,
name|expectedValues
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|this
return|;
block|}
comment|/**      * For each supplied regexp, fail unless content contains at      * least one line that matches.      * Regexps are automatically prefixed/suffixed with .* so as      * to have match partial lines.      */
specifier|public
name|RequestExecutor
name|assertContentRegexp
parameter_list|(
name|String
modifier|...
name|regexp
parameter_list|)
block|{
return|return
name|assertContentRegexp
argument_list|(
literal|true
argument_list|,
name|regexp
argument_list|)
return|;
block|}
comment|/**      * For each supplied regexp, fail unless<ul>      *<li><code>expected</code>: content contains at least one line that matches.      *<li><code>!expected</code>: content contains any line that mathces.      *<ul>      * Regexps are automatically prefixed/suffixed with .* so as      * to have match partial lines.      */
specifier|public
name|RequestExecutor
name|assertContentRegexp
parameter_list|(
name|boolean
name|expected
parameter_list|,
name|String
modifier|...
name|regexp
parameter_list|)
block|{
name|assertNotNull
argument_list|(
name|this
operator|.
name|toString
argument_list|()
argument_list|,
name|response
argument_list|)
expr_stmt|;
name|nextPattern
label|:
for|for
control|(
name|String
name|expr
range|:
name|regexp
control|)
block|{
specifier|final
name|Pattern
name|p
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".*"
operator|+
name|expr
operator|+
literal|".*"
argument_list|)
decl_stmt|;
specifier|final
name|LineIterator
name|it
init|=
operator|new
name|LineIterator
argument_list|(
operator|new
name|StringReader
argument_list|(
name|contentString
argument_list|)
argument_list|)
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
specifier|final
name|String
name|line
init|=
name|it
operator|.
name|nextLine
argument_list|()
decl_stmt|;
if|if
condition|(
name|expected
operator|&
name|p
operator|.
name|matcher
argument_list|(
name|line
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
block|{
continue|continue
name|nextPattern
continue|;
block|}
if|if
condition|(
operator|!
name|expected
operator|&
name|p
operator|.
name|matcher
argument_list|(
name|line
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
block|{
name|fail
argument_list|(
name|this
operator|+
literal|": match found for regexp '"
operator|+
name|expr
operator|+
literal|"', content=\n"
operator|+
name|contentString
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|expected
condition|)
block|{
name|fail
argument_list|(
name|this
operator|+
literal|": no match for regexp '"
operator|+
name|expr
operator|+
literal|"', content=\n"
operator|+
name|contentString
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|this
return|;
block|}
comment|/**      * For each supplied string, fail unless content contains it      */
specifier|public
name|RequestExecutor
name|assertContentContains
parameter_list|(
name|String
modifier|...
name|expected
parameter_list|)
throws|throws
name|ParseException
block|{
name|assertNotNull
argument_list|(
name|this
operator|.
name|toString
argument_list|()
argument_list|,
name|response
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|exp
range|:
name|expected
control|)
block|{
if|if
condition|(
operator|!
name|contentString
operator|.
name|contains
argument_list|(
name|exp
argument_list|)
condition|)
block|{
name|fail
argument_list|(
name|this
operator|+
literal|": content does not contain '"
operator|+
name|exp
operator|+
literal|"', content=\n"
operator|+
name|contentString
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|this
return|;
block|}
specifier|public
name|void
name|generateDocumentation
parameter_list|(
name|RequestDocumentor
name|documentor
parameter_list|,
name|String
modifier|...
name|metadata
parameter_list|)
throws|throws
name|IOException
block|{
name|documentor
operator|.
name|generateDocumentation
argument_list|(
name|this
argument_list|,
name|metadata
argument_list|)
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
name|HttpResponse
name|getResponse
parameter_list|()
block|{
return|return
name|response
return|;
block|}
specifier|public
name|HttpEntity
name|getEntity
parameter_list|()
block|{
return|return
name|entity
return|;
block|}
comment|/**      * Getter for an {@link InputStream} over the byte array containing the      * data of the {@link HttpEntity}. This means that this method can be      * called multiple times and the streams need not to be closed as      * {@link ByteArrayInputStream} does not consume any System resources.      * @return      */
specifier|public
name|InputStream
name|getStream
parameter_list|()
block|{
return|return
operator|new
name|ByteArrayInputStream
argument_list|(
name|content
argument_list|)
return|;
block|}
comment|/**      * Getter for the String content of the HttpEntity.      * @return      */
specifier|public
name|String
name|getContent
parameter_list|()
block|{
if|if
condition|(
name|contentString
operator|==
literal|null
condition|)
block|{
name|contentString
operator|=
operator|new
name|String
argument_list|(
name|content
argument_list|,
name|charset
argument_list|)
expr_stmt|;
block|}
return|return
name|contentString
return|;
block|}
comment|/**      * @return the contentType      */
specifier|public
specifier|final
name|ContentType
name|getContentType
parameter_list|()
block|{
return|return
name|contentType
return|;
block|}
comment|/**      * @return the charset      */
specifier|public
specifier|final
name|Charset
name|getCharset
parameter_list|()
block|{
return|return
name|charset
return|;
block|}
block|}
end_class

end_unit

