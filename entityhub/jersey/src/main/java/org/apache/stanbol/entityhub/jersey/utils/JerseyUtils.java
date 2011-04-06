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
name|entityhub
operator|.
name|jersey
operator|.
name|utils
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|Collection
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
name|MediaType
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
name|FileUtils
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
name|entityhub
operator|.
name|core
operator|.
name|query
operator|.
name|DefaultQueryFactory
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
name|entityhub
operator|.
name|jersey
operator|.
name|parsers
operator|.
name|JSONToFieldQuery
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|FieldQuery
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|FieldQueryFactory
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|TextConstraint
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|TextConstraint
operator|.
name|PatternType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONException
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
comment|/**  * Utility methods used by several of the RESTful service endpoints of the  * Entityhub.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|JerseyUtils
block|{
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|JerseyUtils
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * This utility class used the {@link DefaultQueryFactory} as      * {@link FieldQueryFactory} instance.       */
specifier|private
specifier|static
name|FieldQueryFactory
name|queryFactory
init|=
name|DefaultQueryFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|private
name|JerseyUtils
parameter_list|()
block|{
comment|/* do not create instance of Util Classes */
block|}
comment|/**      * Searches the Header for acceptable media types and returns the first found      * that is not the wildcard type. If no one is found the parsed default type      * is returned.      *      * @param headers the request headers      * @param defaultType the default type if no or only the wildcard type was found in      * the header      * @return the acceptable media type      */
specifier|public
specifier|static
name|MediaType
name|getAcceptableMediaType
parameter_list|(
name|HttpHeaders
name|headers
parameter_list|,
name|MediaType
name|defaultType
parameter_list|)
block|{
name|MediaType
name|acceptedMediaType
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|!
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|MediaType
name|accepted
range|:
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|accepted
operator|.
name|isWildcardType
argument_list|()
condition|)
block|{
name|acceptedMediaType
operator|=
name|accepted
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|acceptedMediaType
operator|==
literal|null
condition|)
block|{
name|acceptedMediaType
operator|=
name|defaultType
expr_stmt|;
block|}
return|return
name|acceptedMediaType
return|;
block|}
comment|/**      * Returns the {@link FieldQuery} based on the JSON formatted String (in case      * of "application/x-www-form-urlencoded" requests) or file (in case of      * "multipart/form-data" requests).<p>      * @param query the string containing the JSON serialised FieldQuery or      *<code>null</code> in case of a "multipart/form-data" request      * @param file the temporary file holding the data parsed by the request to      * the web server in case of a "multipart/form-data" request or<code>null</code>      * in case of the "application/x-www-form-urlencoded" request.      * @return the FieldQuery parsed from the string provided by one of the two      * parameters      * @throws WebApplicationException if both parameter are<code>null</code> or      * if the string provided by both parameters could not be used to parse a      * {@link FieldQuery} instance.      */
specifier|public
specifier|static
name|FieldQuery
name|parseFieldQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|File
name|file
parameter_list|)
throws|throws
name|WebApplicationException
block|{
if|if
condition|(
name|query
operator|==
literal|null
operator|&&
name|file
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Query Requests MUST define the \"query\" parameter"
argument_list|)
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
throw|;
block|}
name|FieldQuery
name|fieldQuery
init|=
literal|null
decl_stmt|;
name|JSONException
name|exception
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|query
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|fieldQuery
operator|=
name|JSONToFieldQuery
operator|.
name|fromJSON
argument_list|(
name|queryFactory
argument_list|,
name|query
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"unable to parse FieldQuery from \"application/x-www-form-urlencoded\" encoded query string "
operator|+
name|query
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|fieldQuery
operator|=
literal|null
expr_stmt|;
name|exception
operator|=
name|e
expr_stmt|;
block|}
block|}
comment|//else no query via application/x-www-form-urlencoded parsed
if|if
condition|(
name|fieldQuery
operator|==
literal|null
operator|&&
name|file
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|query
operator|=
name|FileUtils
operator|.
name|readFileToString
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|fieldQuery
operator|=
name|JSONToFieldQuery
operator|.
name|fromJSON
argument_list|(
name|queryFactory
argument_list|,
name|query
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"unable to parse FieldQuery from \"multipart/form-data\" encoded query string "
operator|+
name|query
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|exception
operator|=
name|e
expr_stmt|;
block|}
block|}
comment|//fieldquery already initialised or no query via multipart/form-data parsed
if|if
condition|(
name|fieldQuery
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unable to parse FieldQuery form the parsed query String:"
operator|+
name|query
argument_list|,
name|exception
argument_list|)
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
throw|;
block|}
return|return
name|fieldQuery
return|;
block|}
comment|/**      * Creates an {@link FieldQuery} for parameters parsed by the /find requests      * supported by the /symbol, /sites and {siteId} RESTful endpoints.      * TODO: This has to be refactored to use "EntityQuery" as soon as Multiple      *       query types are implemented for the Entityhub.      * @param name the name pattern to search entities for (required)      * @param field the field used to search for entities (required)      * @param language the language of the parsed name pattern (optional)      * @param limit the maximum number of result (optional)      * @param offset the offset of the first result (optional)      * @return the {@link FieldQuery} representing the parsed parameter      * @throws WebApplicationException in case the parsed name pattern is invalid.      * The validation of this required parameter provided by the Request is done      * by this method.      * @throws IllegalArgumentException in case the parsed field is invalid. Callers      * of this method need to ensure that this parameter is set to an valid value.      */
specifier|public
specifier|static
name|FieldQuery
name|createFieldQueryForFindRequest
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|language
parameter_list|,
name|Integer
name|limit
parameter_list|,
name|Integer
name|offset
parameter_list|)
throws|throws
name|WebApplicationException
throws|,
name|IllegalArgumentException
block|{
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// This throws an WebApplicationException, because the search name is
comment|// provided by the caller. So an empty or missing name is interpreted
comment|// as an bad Requested sent by the user
throw|throw
operator|new
name|WebApplicationException
argument_list|(
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed \"name\" pattern to search entities for MUST NOT be NULL nor EMPTY"
argument_list|)
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
throw|;
block|}
else|else
block|{
name|name
operator|=
name|name
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|field
operator|==
literal|null
operator|||
name|field
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// This throws no WebApplicationException, because "field" is an
comment|// optional parameter and callers of this method MUST provide an
comment|// valid default value in case the request does not provide any or
comment|// valid data.
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed search \"field\" MUST NOT be NULL nor EMPTY"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|field
operator|=
name|field
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Process Find Request:"
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> name  : "
operator|+
name|name
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> field  : "
operator|+
name|field
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> lang  : "
operator|+
name|language
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> limit : "
operator|+
name|limit
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> offset: "
operator|+
name|offset
argument_list|)
expr_stmt|;
name|FieldQuery
name|query
init|=
name|queryFactory
operator|.
name|createFieldQuery
argument_list|()
decl_stmt|;
if|if
condition|(
name|language
operator|==
literal|null
condition|)
block|{
name|query
operator|.
name|setConstraint
argument_list|(
name|field
argument_list|,
operator|new
name|TextConstraint
argument_list|(
name|name
argument_list|,
name|PatternType
operator|.
name|wildcard
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|query
operator|.
name|setConstraint
argument_list|(
name|field
argument_list|,
operator|new
name|TextConstraint
argument_list|(
name|name
argument_list|,
name|PatternType
operator|.
name|wildcard
argument_list|,
literal|false
argument_list|,
name|language
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Collection
argument_list|<
name|String
argument_list|>
name|selectedFields
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|selectedFields
operator|.
name|add
argument_list|(
name|field
argument_list|)
expr_stmt|;
comment|//select also the field used to find entities
name|query
operator|.
name|addSelectedFields
argument_list|(
name|selectedFields
argument_list|)
expr_stmt|;
if|if
condition|(
name|limit
operator|!=
literal|null
operator|&&
name|limit
operator|>
literal|0
condition|)
block|{
name|query
operator|.
name|setLimit
argument_list|(
name|limit
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|offset
operator|!=
literal|null
operator|&&
name|offset
operator|>
literal|0
condition|)
block|{
name|query
operator|.
name|setOffset
argument_list|(
name|offset
argument_list|)
expr_stmt|;
block|}
return|return
name|query
return|;
block|}
comment|/**      * Getter for a Service from the {@link ServletContext} by using the      * {@link Class#getName()} as key for {@link ServletContext#getAttribute(String)}.      * In case the Service can not be found a {@link WebApplicationException} is      * thrown with the message that the Service is currently not available.      * @param<T> The type of the Service      * @param service the Service interface      * @param context the context used to search the service      * @return the Service instance      * @throws WebApplicationException in case the service instance was not found       * in the parsed servlet context      * @throws IllegalArgumentException if<code>null</code> is parsed as      * service or context      */
comment|//    @SuppressWarnings("unchecked")
comment|//    public static<T> T getService(Class<T> service, ServletContext context) throws WebApplicationException, IllegalArgumentException {
comment|//        if(service == null){
comment|//            throw new IllegalArgumentException("The parsed ServiceInterface MUST NOT be NULL!");
comment|//        }
comment|//        if(context == null){
comment|//            throw new IllegalArgumentException("The parsed ServletContext MUST NOT be NULL");
comment|//        }
comment|//        T serviceInstance = (T) context.getAttribute(service.getName());
comment|//        if(serviceInstance == null){
comment|//            throw new WebApplicationException(new IllegalStateException(
comment|//                "The "+service.getSimpleName()+" Service is currently not available " +
comment|//                		"(full name= "+service+"| " +
comment|//                				"servlet context name = "+context.getServletContextName()+")"),
comment|//                Response.Status.INTERNAL_SERVER_ERROR);
comment|//        }
comment|//        return serviceInstance;
comment|//    }
block|}
end_class

end_unit

