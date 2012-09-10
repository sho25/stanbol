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
import|import static
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
operator|.
name|APPLICATION_JSON
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|N3
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|N_TRIPLE
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|RDF_JSON
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|RDF_XML
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|TURTLE
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|X_TURTLE
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
name|lang
operator|.
name|reflect
operator|.
name|GenericArrayType
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|ParameterizedType
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Type
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|WildcardType
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLDecoder
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
name|UnsupportedCharsetException
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
name|Arrays
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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
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
name|StringTokenizer
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
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|ext
operator|.
name|MessageBodyReader
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
name|ldpath
operator|.
name|query
operator|.
name|LDPathFieldQueryImpl
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
name|model
operator|.
name|Entity
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
name|model
operator|.
name|Representation
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
name|QueryResultList
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

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|representation
operator|.
name|Form
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
comment|/**      * Unmodifiable Set with the Media Types supported for {@link Representation}      */
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|REPRESENTATION_SUPPORTED_MEDIA_TYPES
init|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
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
name|APPLICATION_JSON
argument_list|,
name|RDF_XML
argument_list|,
name|N3
argument_list|,
name|TURTLE
argument_list|,
name|X_TURTLE
argument_list|,
name|RDF_JSON
argument_list|,
name|N_TRIPLE
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * Unmodifiable Set with the Media Types supported for {@link Entity}      */
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|ENTITY_SUPPORTED_MEDIA_TYPES
init|=
name|REPRESENTATION_SUPPORTED_MEDIA_TYPES
decl_stmt|;
comment|/**      * Unmodifiable Set with the Media Types supported for {@link QueryResultList}      */
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|QUERY_RESULT_SUPPORTED_MEDIA_TYPES
init|=
name|REPRESENTATION_SUPPORTED_MEDIA_TYPES
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
comment|//    /**
comment|//     * Returns the {@link FieldQuery} based on the JSON formatted String (in case
comment|//     * of "application/x-www-form-urlencoded" requests) or file (in case of
comment|//     * "multipart/form-data" requests).<p>
comment|//     * @param query the string containing the JSON serialised FieldQuery or
comment|//     *<code>null</code> in case of a "multipart/form-data" request
comment|//     * @param file the temporary file holding the data parsed by the request to
comment|//     * the web server in case of a "multipart/form-data" request or<code>null</code>
comment|//     * in case of the "application/x-www-form-urlencoded" request.
comment|//     * @return the FieldQuery parsed from the string provided by one of the two
comment|//     * parameters
comment|//     * @throws WebApplicationException if both parameter are<code>null</code> or
comment|//     * if the string provided by both parameters could not be used to parse a
comment|//     * {@link FieldQuery} instance.
comment|//     */
comment|//    public static FieldQuery parseFieldQuery(String query, File file) throws WebApplicationException {
comment|//        if(query == null&& file == null) {
comment|//            throw new WebApplicationException(new IllegalArgumentException("Query Requests MUST define the \"query\" parameter"), Response.Status.BAD_REQUEST);
comment|//        }
comment|//        FieldQuery fieldQuery = null;
comment|//        JSONException exception = null;
comment|//        if(query != null){
comment|//            try {
comment|//                fieldQuery = JSONToFieldQuery.fromJSON(queryFactory,query);
comment|//            } catch (JSONException e) {
comment|//                log.warn("unable to parse FieldQuery from \"application/x-www-form-urlencoded\" encoded query string "+query,e);
comment|//                fieldQuery = null;
comment|//                exception = e;
comment|//            }
comment|//        } //else no query via application/x-www-form-urlencoded parsed
comment|//        if(fieldQuery == null&& file != null){
comment|//            try {
comment|//                query = FileUtils.readFileToString(file);
comment|//                fieldQuery = JSONToFieldQuery.fromJSON(queryFactory,query);
comment|//            } catch (IOException e) {
comment|//                throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
comment|//            } catch (JSONException e) {
comment|//                log.warn("unable to parse FieldQuery from \"multipart/form-data\" encoded query string "+query,e);
comment|//                exception = e;
comment|//            }
comment|//        }//fieldquery already initialised or no query via multipart/form-data parsed
comment|//        if(fieldQuery == null){
comment|//            throw new WebApplicationException(new IllegalArgumentException("Unable to parse FieldQuery form the parsed query String:"+query, exception),Response.Status.BAD_REQUEST);
comment|//        }
comment|//        return fieldQuery;
comment|//    }
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
parameter_list|,
name|String
name|ldpath
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
name|log
operator|.
name|debug
argument_list|(
literal|"> ldpath: "
operator|+
name|ldpath
argument_list|)
expr_stmt|;
name|FieldQuery
name|query
decl_stmt|;
if|if
condition|(
name|ldpath
operator|!=
literal|null
operator|&&
operator|!
name|ldpath
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//STANBOL-417
name|query
operator|=
operator|new
name|LDPathFieldQueryImpl
argument_list|()
expr_stmt|;
operator|(
operator|(
name|LDPathFieldQueryImpl
operator|)
name|query
operator|)
operator|.
name|setLDPathSelect
argument_list|(
name|ldpath
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//if no LDPath is parsed select the default field
name|query
operator|=
name|queryFactory
operator|.
name|createFieldQuery
argument_list|()
expr_stmt|;
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
block|}
if|if
condition|(
name|language
operator|==
literal|null
operator|||
name|language
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
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
comment|//    /**
comment|//     * Getter for a Service from the {@link ServletContext} by using the
comment|//     * {@link Class#getName()} as key for {@link ServletContext#getAttribute(String)}.
comment|//     * In case the Service can not be found a {@link WebApplicationException} is
comment|//     * thrown with the message that the Service is currently not available.
comment|//     * @param<T> The type of the Service
comment|//     * @param service the Service interface
comment|//     * @param context the context used to search the service
comment|//     * @return the Service instance
comment|//     * @throws WebApplicationException in case the service instance was not found
comment|//     * in the parsed servlet context
comment|//     * @throws IllegalArgumentException if<code>null</code> is parsed as
comment|//     * service or context
comment|//     */
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
comment|/**      * Tests if a generic type (may be&lt;?&gt;,&lt;? extends {required}&gt;       * or&lt;? super {required}&gt;) is compatible with the required one.      * TODO: Should be moved to an utility class      * @param required the required class the generic type MUST BE compatible with      * @param genericType the required class      * @return if the generic type is compatible with the required class      */
specifier|public
specifier|static
name|boolean
name|testType
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|required
parameter_list|,
name|Type
name|type
parameter_list|)
block|{
comment|//for the examples let assume that a Set is the raw type and the
comment|//requested generic type is a Representation with the following class
comment|//hierarchy:
comment|// Object
comment|//     -> Representation
comment|//         -> RdfRepresentation
comment|//         -> InMemoryRepresentation
comment|//     -> InputStream
comment|//     -> Collection<T>
name|boolean
name|typeOK
init|=
literal|false
decl_stmt|;
comment|//        while(type != null&& !typeOK){
comment|//            types.add(type);
if|if
condition|(
name|type
operator|instanceof
name|Class
argument_list|<
name|?
argument_list|>
condition|)
block|{
name|typeOK
operator|=
name|required
operator|.
name|isAssignableFrom
argument_list|(
operator|(
name|Class
argument_list|<
name|?
argument_list|>
operator|)
name|type
argument_list|)
expr_stmt|;
name|type
operator|=
operator|(
operator|(
name|Class
argument_list|<
name|?
argument_list|>
operator|)
name|type
operator|)
operator|.
name|getGenericSuperclass
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|type
operator|instanceof
name|WildcardType
condition|)
block|{
comment|//In cases<? super {class}>,<? extends {class},<?>
name|WildcardType
name|wildcardSetType
init|=
operator|(
name|WildcardType
operator|)
name|type
decl_stmt|;
if|if
condition|(
name|wildcardSetType
operator|.
name|getLowerBounds
argument_list|()
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|Type
name|lowerBound
init|=
name|wildcardSetType
operator|.
name|getLowerBounds
argument_list|()
index|[
literal|0
index|]
decl_stmt|;
comment|//OK
comment|//  Set<? super RdfRepresentation>
comment|//  Set<? super Representation>
comment|//NOT OK
comment|//  Set<? super InputStream>
comment|//  Set<? super Collection<Representation>>
name|typeOK
operator|=
name|lowerBound
operator|instanceof
name|Class
argument_list|<
name|?
argument_list|>
operator|&&
name|required
operator|.
name|isAssignableFrom
argument_list|(
operator|(
name|Class
argument_list|<
name|?
argument_list|>
operator|)
name|lowerBound
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|wildcardSetType
operator|.
name|getUpperBounds
argument_list|()
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|Type
name|upperBound
init|=
name|wildcardSetType
operator|.
name|getUpperBounds
argument_list|()
index|[
literal|0
index|]
decl_stmt|;
comment|//OK
comment|//  Set<? extends Representation>
comment|//  Set<? extends Object>
comment|//NOT OK
comment|//  Set<? extends RdfRepresentation>
comment|//  Set<? extends InputStream>
comment|//  Set<? extends Collection<Representation>
name|typeOK
operator|=
name|upperBound
operator|instanceof
name|Class
argument_list|<
name|?
argument_list|>
operator|&&
operator|(
operator|(
name|Class
argument_list|<
name|?
argument_list|>
operator|)
name|upperBound
operator|)
operator|.
name|isAssignableFrom
argument_list|(
name|required
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//no upper nor lower bound
comment|// Set<?>
name|typeOK
operator|=
literal|true
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|required
operator|.
name|isArray
argument_list|()
operator|&&
name|type
operator|instanceof
name|GenericArrayType
condition|)
block|{
comment|//In case the required type is an array we need also to support
comment|//possible generic Array specifications
name|GenericArrayType
name|arrayType
init|=
operator|(
name|GenericArrayType
operator|)
name|type
decl_stmt|;
name|typeOK
operator|=
name|testType
argument_list|(
name|required
operator|.
name|getComponentType
argument_list|()
argument_list|,
name|arrayType
operator|.
name|getGenericComponentType
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|type
operator|instanceof
name|ParameterizedType
condition|)
block|{
name|ParameterizedType
name|pType
init|=
operator|(
operator|(
name|ParameterizedType
operator|)
name|type
operator|)
decl_stmt|;
name|typeOK
operator|=
name|pType
operator|.
name|getRawType
argument_list|()
operator|instanceof
name|Class
argument_list|<
name|?
argument_list|>
operator|&&
name|required
operator|.
name|isAssignableFrom
argument_list|(
operator|(
name|Class
argument_list|<
name|?
argument_list|>
operator|)
name|pType
operator|.
name|getRawType
argument_list|()
argument_list|)
expr_stmt|;
name|type
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
comment|//GenericArrayType but !required.isArray() -> incompatible
comment|//TypeVariable -> no variables define -> incompatible
name|typeOK
operator|=
literal|false
expr_stmt|;
comment|//                type = null; //end
block|}
comment|//        }
return|return
name|typeOK
return|;
block|}
comment|/**      * Tests the parsed type against the raw type and parsed Type parameters.      * This allows e.g. to check for<code>Map&lt;String,Number&gt</code> but      * also works with classes that extend generic types such as      *<code>Dummy extends {@link HashMap}&lt;String,String&gt</code>.      * @param rawType the raw type to test against      * @param parameterTypes the types of the parameters      * @param type the type to test      * @return if the type is compatible or not      */
specifier|public
specifier|static
name|boolean
name|testParameterizedType
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|rawType
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|parameterTypes
parameter_list|,
name|Type
name|type
parameter_list|)
block|{
comment|// first check the raw type
if|if
condition|(
operator|!
name|testType
argument_list|(
name|rawType
argument_list|,
name|type
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
while|while
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
comment|// types.add(type);
name|Type
index|[]
name|parameters
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|type
operator|instanceof
name|ParameterizedType
condition|)
block|{
name|parameters
operator|=
operator|(
operator|(
name|ParameterizedType
operator|)
name|type
operator|)
operator|.
name|getActualTypeArguments
argument_list|()
expr_stmt|;
comment|// the number of type arguments MUST BE the same as parameter types
if|if
condition|(
name|parameters
operator|.
name|length
operator|==
name|parameterTypes
operator|.
name|length
condition|)
block|{
name|boolean
name|compatible
init|=
literal|true
decl_stmt|;
comment|// All parameters MUST BE compatible!
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|compatible
operator|&&
name|i
operator|<
name|parameters
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|compatible
operator|=
name|testType
argument_list|(
name|parameterTypes
index|[
name|i
index|]
argument_list|,
name|parameters
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|compatible
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
comment|// else check parent types
block|}
comment|// else not parameterised
if|if
condition|(
name|type
operator|instanceof
name|Class
argument_list|<
name|?
argument_list|>
condition|)
block|{
name|type
operator|=
operator|(
operator|(
name|Class
argument_list|<
name|?
argument_list|>
operator|)
name|type
operator|)
operator|.
name|getGenericSuperclass
argument_list|()
expr_stmt|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * This Method is intended to parse form data from       * {@link MediaType#APPLICATION_FORM_URLENCODED} requests. This functionality      * us usually needed when writing a {@link MessageBodyReader} to get the      * data from the "{@link InputStream} entityStream" parameter of the       * {@link MessageBodyReader#readFrom(Class, Type, java.lang.annotation.Annotation[], MediaType, javax.ws.rs.core.MultivaluedMap, InputStream)}      * method.      * @param entityStream the stream with the form data      * @param charset The charset used for the request (if<code>null</code> or      * empty UTF-8 is used as default.      * @return the parsed form data as key value map      * @throws IOException on any exception while reading the data form the stream      */
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parseForm
parameter_list|(
name|InputStream
name|entityStream
parameter_list|,
name|String
name|charset
parameter_list|)
throws|throws
name|IOException
block|{
comment|/* TODO: Question:           * If I get an Post Request with "application/x-www-form-urlencoded"           * and a charset (lets assume "iso-2022-kr") do I need to use the           * charset to read the String from the Stream, or to URL decode the           * String or both?          *           * This code assumes that it needs to be used for both, but this needs          * validation!          */
if|if
condition|(
name|charset
operator|==
literal|null
operator|||
name|charset
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|charset
operator|=
literal|"UTF-8"
expr_stmt|;
block|}
name|String
name|data
decl_stmt|;
try|try
block|{
name|data
operator|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|entityStream
argument_list|,
name|charset
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedCharsetException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|form
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|StringTokenizer
name|tokenizer
init|=
operator|new
name|StringTokenizer
argument_list|(
name|data
argument_list|,
literal|"&"
argument_list|)
decl_stmt|;
name|String
name|token
decl_stmt|;
try|try
block|{
while|while
condition|(
name|tokenizer
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|token
operator|=
name|tokenizer
operator|.
name|nextToken
argument_list|()
expr_stmt|;
name|int
name|index
init|=
name|token
operator|.
name|indexOf
argument_list|(
literal|'='
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|<
literal|0
condition|)
block|{
name|form
operator|.
name|put
argument_list|(
name|URLDecoder
operator|.
name|decode
argument_list|(
name|token
argument_list|,
name|charset
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|index
operator|>
literal|0
condition|)
block|{
name|form
operator|.
name|put
argument_list|(
name|URLDecoder
operator|.
name|decode
argument_list|(
name|token
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
argument_list|,
name|charset
argument_list|)
argument_list|,
name|URLDecoder
operator|.
name|decode
argument_list|(
name|token
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
argument_list|,
name|charset
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|UnsupportedCharsetException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
return|return
name|form
return|;
block|}
block|}
end_class

end_unit

