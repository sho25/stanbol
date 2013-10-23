begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
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
name|CorsConstants
operator|.
name|CORS_ORIGIN
import|;
end_import

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
name|CorsConstants
operator|.
name|CORS_ACCESS_CONTROL_EXPOSE_HEADERS
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
comment|/**  * Utilities for adding<a href="http://dev.w3.org/2006/waf/access-control/"> CORS</a> support to the Stanbol  * RESTful API  *<p>  *<p>  * Note that this utility depends on the {@link JerseyEndpoint#CORS_ORIGIN} property read from the Servlet  * Context.  *<p>  * Currently this support for  *<ul>  *<li>Origin header  *<li>Preflight Requests.  *</ul>  *   * @author Rupert Westenthaler  *   */
end_comment

begin_comment
comment|//TODO recreate (or provide functionality by other means
end_comment

begin_class
specifier|public
specifier|final
class|class
name|CorsHelper
block|{}
end_class

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * The "Origin" header as used in requests
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public static final String ORIGIN = "Origin";
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * The ALLOW_ORIGIN header as added to responses
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public static final String ALLOW_ORIGIN = "Access-Control-Allow-Origin";
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * The "Access-Control-Request-Method" header
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public static final String REQUEST_METHOD = "Access-Control-Request-Method";
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * The "Access-Control-Request-Headers" header
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public static final String REQUEST_HEADERS = "Access-Control-Request-Headers";
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * The "Access-Control-Request-Headers" header
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public static final String ALLOW_HEADERS = "Access-Control-Allow-Headers";
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * The "Access-Control-Allow-Methods" header
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public static final String ALLOW_METHODS = "Access-Control-Allow-Methods";
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * The "Access-Control-Expose-Headers" header
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public static final String EXPOSE_HEADERS = "Access-Control-Expose-Headers";
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * The default methods for the Access-Control-Request-Method header field. Set to "GET, POST, OPTIONS"
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public static final String DEFAULT_REQUEST_METHODS = "GET, POST, OPTIONS";
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * This methods checks the parsed origin against the present configuration and returns if the data
end_comment

begin_comment
comment|//     * returned by this Stanbol instance can be shared with the parsed origin.
end_comment

begin_comment
comment|//     *<p>
end_comment

begin_comment
comment|//     * The allowed<a href="http://enable-cors.org/">CORS</a> origins for this Stanbol instance are configured
end_comment

begin_comment
comment|//     * for the {@link JerseyEndpoint} component and added to the {@link ServletContext} under the
end_comment

begin_comment
comment|//     * {@link JerseyEndpoint#CORS_ORIGIN} key.
end_comment

begin_comment
comment|//     *
end_comment

begin_comment
comment|//     * @param origin
end_comment

begin_comment
comment|//     *            the origin host
end_comment

begin_comment
comment|//     * @param context
end_comment

begin_comment
comment|//     *            the servlet context
end_comment

begin_comment
comment|//     * @return<code>true</code> if the configuration includes the pased origin and the data can be shared
end_comment

begin_comment
comment|//     *         with this host. Otherwise<code>false</code>.
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    @SuppressWarnings("unchecked")
end_comment

begin_comment
comment|//    public static boolean checkCorsOrigin(String origin, ServletContext context) {
end_comment

begin_comment
comment|//        Set<String> originsConfig = (Set<String>) context.getAttribute(CORS_ORIGIN);
end_comment

begin_comment
comment|//        return originsConfig.contains("*") || originsConfig.contains(origin);
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * Adds the Origin response header to the parsed response builder based on the headers of an request.
end_comment

begin_comment
comment|//     *
end_comment

begin_comment
comment|//     * @param context
end_comment

begin_comment
comment|//     *            the ServletContext holding the {@link JerseyEndpoint#CORS_ORIGIN} configuration.
end_comment

begin_comment
comment|//     * @param responseBuilder
end_comment

begin_comment
comment|//     *            The {@link ResponseBuilder} the origin header is added to if (1) a origin header is present
end_comment

begin_comment
comment|//     *            in the request headers and (1) the parsed origin is compatible with the configuration set
end_comment

begin_comment
comment|//     *            for the {@link JerseyEndpoint}.
end_comment

begin_comment
comment|//     * @param requestHeaders
end_comment

begin_comment
comment|//     *            the request headers
end_comment

begin_comment
comment|//     * @return<code>true</code> if the origin header was added. Otherwise<code>false</code>
end_comment

begin_comment
comment|//     * @throws WebApplicationException
end_comment

begin_comment
comment|//     *             it the request headers define multiple values for the "Origin" header an
end_comment

begin_comment
comment|//     *             WebApplicationException with the Status "BAD_REQUEST" is thrown.
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public static boolean addCORSOrigin(ServletContext servletContext,
end_comment

begin_comment
comment|//                                        ResponseBuilder responseBuilder,
end_comment

begin_comment
comment|//                                        HttpHeaders requestHeaders) throws WebApplicationException {
end_comment

begin_comment
comment|//        List<String> originHeaders = requestHeaders.getRequestHeader(CorsHelper.ORIGIN);
end_comment

begin_comment
comment|//        if (originHeaders != null&& !originHeaders.isEmpty()) {
end_comment

begin_comment
comment|//            if (originHeaders.size() != 1) {
end_comment

begin_comment
comment|//                throw new WebApplicationException(new IllegalStateException(
end_comment

begin_comment
comment|//                        "Multiple 'Origin' header values '" + originHeaders
end_comment

begin_comment
comment|//                                + "' found in the request headers"), Status.BAD_REQUEST);
end_comment

begin_comment
comment|//            } else {
end_comment

begin_comment
comment|//                Set<String> originsConfig = (Set<String>) servletContext.getAttribute(CORS_ORIGIN);
end_comment

begin_comment
comment|//                if (originsConfig.contains("*")) { // if config includes *
end_comment

begin_comment
comment|//                    responseBuilder.header(CorsHelper.ALLOW_ORIGIN, "*"); // add also * to the header
end_comment

begin_comment
comment|//                    addExposedHeaders(servletContext, responseBuilder, requestHeaders);
end_comment

begin_comment
comment|//                    return true;
end_comment

begin_comment
comment|//                } else if (originsConfig.contains(originHeaders.get(0))) {
end_comment

begin_comment
comment|//                    // otherwise add the specific Origin host
end_comment

begin_comment
comment|//                    addExposedHeaders(servletContext, responseBuilder, requestHeaders);
end_comment

begin_comment
comment|//                    responseBuilder.header(CorsHelper.ALLOW_ORIGIN, originHeaders.get(0));
end_comment

begin_comment
comment|//                    return true;
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        return false;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * Adds the
end_comment

begin_comment
comment|//     *
end_comment

begin_comment
comment|//     * @param servletContext
end_comment

begin_comment
comment|//     * @param responseBuilder
end_comment

begin_comment
comment|//     * @param httpHeaders
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    private static void addExposedHeaders(ServletContext servletContext,
end_comment

begin_comment
comment|//                                          ResponseBuilder responseBuilder,
end_comment

begin_comment
comment|//                                          HttpHeaders httpHeaders) {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        Set<String> exposedHeadersConfig = (Set<String>) servletContext
end_comment

begin_comment
comment|//                .getAttribute(CORS_ACCESS_CONTROL_EXPOSE_HEADERS);
end_comment

begin_comment
comment|//        if (exposedHeadersConfig != null&& !exposedHeadersConfig.isEmpty()) {
end_comment

begin_comment
comment|//            StringBuilder requestHeader = new StringBuilder();
end_comment

begin_comment
comment|//            boolean added = false;
end_comment

begin_comment
comment|//            for (String header : exposedHeadersConfig) {
end_comment

begin_comment
comment|//                if (header != null&& !header.isEmpty()) {
end_comment

begin_comment
comment|//                    if (added) {
end_comment

begin_comment
comment|//                        requestHeader.append(", ");
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                    requestHeader.append(header);
end_comment

begin_comment
comment|//                    added = true;
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            if (added) {
end_comment

begin_comment
comment|//                responseBuilder.header(EXPOSE_HEADERS, requestHeader.toString());
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * Enable CORS for OPTION requests based on the provided request headers and the allowMethods.
end_comment

begin_comment
comment|//     *<p>
end_comment

begin_comment
comment|//     * The {@link #addCORSOrigin(ResponseBuilder, HttpHeaders)} method is used deal with the origin. The
end_comment

begin_comment
comment|//     * allowMethods are set to the parsed values or to {@link CorsHelper#DEFAULT_REQUEST_METHODS} if non is
end_comment

begin_comment
comment|//     * parsed of the parsed values do not contain a single value that is not<code>null</code> nor empty.
end_comment

begin_comment
comment|//     *
end_comment

begin_comment
comment|//     * @param context
end_comment

begin_comment
comment|//     *            the ServletContext holding the {@link JerseyEndpoint#CORS_ORIGIN} configuration.
end_comment

begin_comment
comment|//     * @param responseBuilder
end_comment

begin_comment
comment|//     *            The {@link ResponseBuilder} to add the CORS headers
end_comment

begin_comment
comment|//     * @param requestHeaders
end_comment

begin_comment
comment|//     *            the headers of the request
end_comment

begin_comment
comment|//     * @param allowMethods
end_comment

begin_comment
comment|//     *            the allowMethods to if<code>null</code> or empty, the
end_comment

begin_comment
comment|//     *            {@link CorsHelper#DEFAULT_REQUEST_METHODS} are added
end_comment

begin_comment
comment|//     * @return<code>true</code> if the CORS header where added or
end_comment

begin_comment
comment|//     * @throws WebApplicationException
end_comment

begin_comment
comment|//     *             it the request headers define multiple values for the "Origin" header an
end_comment

begin_comment
comment|//     *             WebApplicationException with the Status "BAD_REQUEST" is thrown.
end_comment

begin_comment
comment|//     * @throws IllegalArgumentException
end_comment

begin_comment
comment|//     *             if a parsed allowMethods is<code>null</code> or empty. NOT if the String array is
end_comment

begin_comment
comment|//     *<code>null</code> or empty, but if any of the items within the array is<code>null</code>
end_comment

begin_comment
comment|//     *             or empty!
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public static boolean enableCORS(ServletContext context,
end_comment

begin_comment
comment|//                                     ResponseBuilder responseBuilder,
end_comment

begin_comment
comment|//                                     HttpHeaders requestHeaders,
end_comment

begin_comment
comment|//                                     String... allowMethods) throws WebApplicationException {
end_comment

begin_comment
comment|//        // first check if the Origin is present
end_comment

begin_comment
comment|//        if (addCORSOrigin(context, responseBuilder, requestHeaders)) {
end_comment

begin_comment
comment|//            // now add the allowedMethods
end_comment

begin_comment
comment|//            boolean added = false;
end_comment

begin_comment
comment|//            StringBuilder methods = new StringBuilder();
end_comment

begin_comment
comment|//            if (allowMethods != null) {
end_comment

begin_comment
comment|//                for (String method : allowMethods) {
end_comment

begin_comment
comment|//                    if (method != null&& !method.isEmpty()) {
end_comment

begin_comment
comment|//                        if (added) {
end_comment

begin_comment
comment|//                            methods.append(", ");
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                        methods.append(method);
end_comment

begin_comment
comment|//                        added = true;
end_comment

begin_comment
comment|//                    } else {
end_comment

begin_comment
comment|//                        // throw an exception to make it easier to debug errors
end_comment

begin_comment
comment|//                        throw new IllegalArgumentException("Parsed allow methods MUST NOT be NULL nor empty!");
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            if (!added) {
end_comment

begin_comment
comment|//                methods.append(CorsHelper.DEFAULT_REQUEST_METHODS);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            responseBuilder.header(CorsHelper.ALLOW_METHODS, methods.toString());
end_comment

begin_comment
comment|//            // third replay parsed "Access-Control-Request-Headers" values
end_comment

begin_comment
comment|//            // currently there is no need to restrict such headers so the simplest
end_comment

begin_comment
comment|//            // way is to return them as they are parsed
end_comment

begin_comment
comment|//            List<String> requestHeaderValues = requestHeaders.getRequestHeader(REQUEST_HEADERS);
end_comment

begin_comment
comment|//            added = false;
end_comment

begin_comment
comment|//            if (requestHeaderValues != null&& !requestHeaderValues.isEmpty()) {
end_comment

begin_comment
comment|//                StringBuilder requestHeader = new StringBuilder();
end_comment

begin_comment
comment|//                for (String header : requestHeaderValues) {
end_comment

begin_comment
comment|//                    if (header != null&& !header.isEmpty()) {
end_comment

begin_comment
comment|//                        if (added) {
end_comment

begin_comment
comment|//                            requestHeader.append(", ");
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                        requestHeader.append(header);
end_comment

begin_comment
comment|//                        added = true;
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//                if (added) {
end_comment

begin_comment
comment|//                    responseBuilder.header(ALLOW_HEADERS, requestHeader.toString());
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            return true;
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            return false;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//}
end_comment

begin_comment
comment|//
end_comment

end_unit

