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
name|commons
operator|.
name|web
operator|.
name|base
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|JerseyEndpoint
operator|.
name|CORS_ORIGIN
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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|WebApplicationException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|HttpHeaders
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Response
operator|.
name|ResponseBuilder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Response
operator|.
name|Status
import|;
end_import

begin_comment
comment|/**  * Utilities for adding<a href="http://dev.w3.org/2006/waf/access-control/">  * CORS</a> support to the Stanbol RESTful API<p>  *<p> Note that this utility depends on the   * {@link JerseyEndpoint#CORS_ORIGIN} property read from the Servlet Context.<p>  * Currently this support for<ul>  *<li> Origin header  *<li> Preflight Requests.  *</ul>  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|CorsHelper
block|{
comment|/**      * The "Origin" header as used in requests      */
specifier|public
specifier|static
specifier|final
name|String
name|ORIGIN
init|=
literal|"Origin"
decl_stmt|;
comment|/**      * The ALLOW_ORIGIN header as added to responses      */
specifier|public
specifier|static
specifier|final
name|String
name|ALLOW_ORIGIN
init|=
literal|"Access-Control-Allow-Origin"
decl_stmt|;
comment|/**      * The "Access-Control-Request-Method" header      */
specifier|public
specifier|static
specifier|final
name|String
name|REQUEST_METHOD
init|=
literal|"Access-Control-Request-Method"
decl_stmt|;
comment|/**      * The "Access-Control-Request-Headers" header      */
specifier|public
specifier|static
specifier|final
name|String
name|REQUEST_HEADERS
init|=
literal|"Access-Control-Request-Headers"
decl_stmt|;
comment|/**      * The "Access-Control-Request-Headers" header      */
specifier|public
specifier|static
specifier|final
name|String
name|ALLOW_HEADERS
init|=
literal|"Access-Control-Allow-Headers"
decl_stmt|;
comment|/**      * The "Access-Control-Allow-Methods" header      */
specifier|public
specifier|static
specifier|final
name|String
name|ALLOW_METHODS
init|=
literal|"Access-Control-Allow-Methods"
decl_stmt|;
comment|/**      * The default methods for the Access-Control-Request-Method header field.      * Set to "GET, POST, OPTIONS"      */
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_REQUEST_METHODS
init|=
literal|"GET, POST, OPTIONS"
decl_stmt|;
comment|/**      * This methods checks the parsed origin against the present configuration      * and returns if the data returned by this Stanbol instance can be shared      * with the parsed origin.<p>      * The allowed<a href="http://enable-cors.org/">CORS</a> origins for this      * Stanbol instance are configured for the {@link JerseyEndpoint} component      * and added to the {@link ServletContext} under the       * {@link JerseyEndpoint#CORS_ORIGIN} key.      * @param origin the origin host      * @param context the servlet context      * @return<code>true</code> if the configuration includes the pased origin      * and the data can be shared with this host. Otherwise<code>false</code>.      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
specifier|static
name|boolean
name|checkCorsOrigin
parameter_list|(
name|String
name|origin
parameter_list|,
name|ServletContext
name|context
parameter_list|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|originsConfig
init|=
operator|(
name|Set
argument_list|<
name|String
argument_list|>
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|CORS_ORIGIN
argument_list|)
decl_stmt|;
return|return
name|originsConfig
operator|.
name|contains
argument_list|(
literal|"*"
argument_list|)
operator|||
name|originsConfig
operator|.
name|contains
argument_list|(
name|origin
argument_list|)
return|;
block|}
comment|/**      * Adds the Origin response header to the parsed response builder       * based on the headers of an request.      * @param context the ServletContext holding the      * {@link JerseyEndpoint#CORS_ORIGIN} configuration.      * @param responseBuilder The {@link ResponseBuilder} the origin header is added to      * if (1) a origin header is present in the request headers and (1) the parsed      * origin is compatible with the configuration set for the {@link JerseyEndpoint}.      * @param requestHeaders the request headers      * @return<code>true</code> if the origin header was added. Otherwise      *<code>false</code>      * @throws WebApplicationException it the request headers define multiple      * values for the "Origin" header an WebApplicationException with the Status      * "BAD_REQUEST" is thrown.      */
specifier|public
specifier|static
name|boolean
name|addCORSOrigin
parameter_list|(
name|ServletContext
name|servletContext
parameter_list|,
name|ResponseBuilder
name|responseBuilder
parameter_list|,
name|HttpHeaders
name|requestHeaders
parameter_list|)
throws|throws
name|WebApplicationException
block|{
name|List
argument_list|<
name|String
argument_list|>
name|originHeaders
init|=
name|requestHeaders
operator|.
name|getRequestHeader
argument_list|(
name|CorsHelper
operator|.
name|ORIGIN
argument_list|)
decl_stmt|;
if|if
condition|(
name|originHeaders
operator|!=
literal|null
operator|&&
operator|!
name|originHeaders
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|originHeaders
operator|.
name|size
argument_list|()
operator|!=
literal|1
condition|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
operator|new
name|IllegalStateException
argument_list|(
literal|"Multiple 'Origin' header values '"
operator|+
name|originHeaders
operator|+
literal|"' found in the request headers"
argument_list|)
argument_list|,
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
throw|;
block|}
else|else
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|originsConfig
init|=
operator|(
name|Set
argument_list|<
name|String
argument_list|>
operator|)
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|CORS_ORIGIN
argument_list|)
decl_stmt|;
if|if
condition|(
name|originsConfig
operator|.
name|contains
argument_list|(
literal|"*"
argument_list|)
condition|)
block|{
comment|//if config includes *
name|responseBuilder
operator|.
name|header
argument_list|(
name|CorsHelper
operator|.
name|ALLOW_ORIGIN
argument_list|,
literal|"*"
argument_list|)
expr_stmt|;
comment|//add also * to the header
return|return
literal|true
return|;
block|}
elseif|else
if|if
condition|(
name|originsConfig
operator|.
name|contains
argument_list|(
name|originHeaders
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
condition|)
block|{
comment|//otherwise add the specific Origin host
name|responseBuilder
operator|.
name|header
argument_list|(
name|CorsHelper
operator|.
name|ALLOW_ORIGIN
argument_list|,
name|originHeaders
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Enable CORS for OPTION requests based on the provided request headers and      * the allowMethods.<p>      * The {@link #addCORSOrigin(ResponseBuilder, HttpHeaders)} method is used       * deal with the origin. The allowMethods are set to the parsed values or to      * {@link CorsHelper#DEFAULT_REQUEST_METHODS} if non is parsed of the      * parsed values do not contain a single value that is not<code>null</code>      * nor empty.      * @param context the ServletContext holding the      * {@link JerseyEndpoint#CORS_ORIGIN} configuration.      * @param responseBuilder The {@link ResponseBuilder} to add the CORS headers      * @param requestHeaders the headers of the request      * @param allowMethods the allowMethods to if<code>null</code> or empty, the      * {@link CorsHelper#DEFAULT_REQUEST_METHODS} are added      * @return<code>true</code> if the CORS header where added or       * @throws WebApplicationException it the request headers define multiple      * values for the "Origin" header an WebApplicationException with the Status      * "BAD_REQUEST" is thrown.      * @throws IllegalArgumentException if a parsed allowMethods is<code>null</code>      * or empty. NOT if the String array is<code>null</code> or empty, but if      * any of the items within the array is<code>null</code> or empty!      */
specifier|public
specifier|static
name|boolean
name|enableCORS
parameter_list|(
name|ServletContext
name|context
parameter_list|,
name|ResponseBuilder
name|responseBuilder
parameter_list|,
name|HttpHeaders
name|requestHeaders
parameter_list|,
name|String
modifier|...
name|allowMethods
parameter_list|)
throws|throws
name|WebApplicationException
block|{
comment|//first check if the Origin is present
if|if
condition|(
name|addCORSOrigin
argument_list|(
name|context
argument_list|,
name|responseBuilder
argument_list|,
name|requestHeaders
argument_list|)
condition|)
block|{
comment|//now add the allowedMethods
name|boolean
name|added
init|=
literal|false
decl_stmt|;
name|StringBuilder
name|methods
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
name|allowMethods
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|method
range|:
name|allowMethods
control|)
block|{
if|if
condition|(
name|method
operator|!=
literal|null
operator|&&
operator|!
name|method
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|added
condition|)
block|{
name|methods
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
name|methods
operator|.
name|append
argument_list|(
name|method
argument_list|)
expr_stmt|;
name|added
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
comment|//throw an exception to make it easier to debug errors
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parsed allow methods MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|added
condition|)
block|{
name|methods
operator|.
name|append
argument_list|(
name|CorsHelper
operator|.
name|DEFAULT_REQUEST_METHODS
argument_list|)
expr_stmt|;
block|}
name|responseBuilder
operator|.
name|header
argument_list|(
name|CorsHelper
operator|.
name|ALLOW_METHODS
argument_list|,
name|methods
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|//third replay parsed "Access-Control-Request-Headers" values
comment|//currently there is no need to restrict such headers so the simplest
comment|//way is to return them as they are parsed
name|List
argument_list|<
name|String
argument_list|>
name|requestHeaderValues
init|=
name|requestHeaders
operator|.
name|getRequestHeader
argument_list|(
name|REQUEST_HEADERS
argument_list|)
decl_stmt|;
name|added
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|requestHeaderValues
operator|!=
literal|null
operator|&&
operator|!
name|requestHeaderValues
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|StringBuilder
name|requestHeader
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|header
range|:
name|requestHeaderValues
control|)
block|{
if|if
condition|(
name|header
operator|!=
literal|null
operator|&&
operator|!
name|header
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|added
condition|)
block|{
name|requestHeader
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
name|requestHeader
operator|.
name|append
argument_list|(
name|header
argument_list|)
expr_stmt|;
name|added
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|added
condition|)
block|{
name|responseBuilder
operator|.
name|header
argument_list|(
name|ALLOW_HEADERS
argument_list|,
name|requestHeader
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

