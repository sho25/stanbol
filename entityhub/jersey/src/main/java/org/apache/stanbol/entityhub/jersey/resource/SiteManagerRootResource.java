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
name|resource
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
name|TEXT_HTML
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
name|TreeSet
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
name|Consumes
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
name|DefaultValue
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
name|FormParam
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
name|GET
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
name|POST
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
name|Path
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
name|Produces
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
name|QueryParam
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
name|Context
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
name|core
operator|.
name|UriInfo
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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|ontologies
operator|.
name|RDFS
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|ContextHelper
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|resource
operator|.
name|BaseStanbolResource
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
name|utils
operator|.
name|JerseyUtils
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
name|site
operator|.
name|ReferencedSiteManager
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
name|JSONArray
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
name|view
operator|.
name|Viewable
import|;
end_import

begin_comment
comment|/**  * Resource to provide a REST API for the {@link ReferencedSiteManager}.  *   * TODO: add description  */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/entityhub/sites"
argument_list|)
specifier|public
class|class
name|SiteManagerRootResource
extends|extends
name|BaseStanbolResource
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|RDF_MEDIA_TYPES
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|N3
argument_list|,
name|N_TRIPLE
argument_list|,
name|RDF_XML
argument_list|,
name|TURTLE
argument_list|,
name|X_TURTLE
argument_list|,
name|RDF_JSON
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The Field used for find requests if not specified TODO: Will be depreciated as soon as EntityQuery is      * implemented      */
specifier|private
specifier|static
specifier|final
name|String
name|DEFAULT_FIND_FIELD
init|=
name|RDFS
operator|.
name|label
operator|.
name|getUnicodeString
argument_list|()
decl_stmt|;
comment|/**      * The default number of maximal results of searched sites.      */
specifier|private
specifier|static
specifier|final
name|int
name|DEFAULT_FIND_RESULT_LIMIT
init|=
literal|5
decl_stmt|;
specifier|private
name|ServletContext
name|context
decl_stmt|;
specifier|public
name|SiteManagerRootResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
block|}
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
specifier|public
name|Response
name|getSitesPage
parameter_list|()
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"index"
argument_list|,
name|this
argument_list|)
argument_list|)
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|TEXT_HTML
operator|+
literal|"; charset=utf-8"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|// removed to allow request with Accept headers other than text/html to return
comment|// the JSON array
comment|//    @GET
comment|//    @Path("/referenced")
comment|//    @Produces(MediaType.TEXT_HTML)
comment|//    public Response getReferencedSitesPage() {
comment|//        return Response.ok(new Viewable("referenced", this))
comment|//        .header(HttpHeaders.CONTENT_TYPE, TEXT_HTML+"; charset=utf-8").build();
comment|//    }
comment|/**      * Getter for the id's of all referenced sites      *       * @return the id's of all referenced sites.      */
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
name|value
operator|=
literal|"/referenced"
argument_list|)
annotation|@
name|Produces
argument_list|(
block|{
name|MediaType
operator|.
name|APPLICATION_JSON
block|,
name|MediaType
operator|.
name|TEXT_HTML
block|}
argument_list|)
specifier|public
name|Response
name|getReferencedSites
parameter_list|(
annotation|@
name|Context
name|UriInfo
name|uriInfo
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|MediaType
name|acceptable
init|=
name|JerseyUtils
operator|.
name|getAcceptableMediaType
argument_list|(
name|headers
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|,
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
argument_list|,
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
argument_list|)
decl_stmt|;
if|if
condition|(
name|MediaType
operator|.
name|TEXT_HTML_TYPE
operator|.
name|isCompatible
argument_list|(
name|acceptable
argument_list|)
condition|)
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"referenced"
argument_list|,
name|this
argument_list|)
argument_list|)
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|TEXT_HTML
operator|+
literal|"; charset=utf-8"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
name|JSONArray
name|referencedSites
init|=
operator|new
name|JSONArray
argument_list|()
decl_stmt|;
name|ReferencedSiteManager
name|referencedSiteManager
init|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|ReferencedSiteManager
operator|.
name|class
argument_list|,
name|context
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|site
range|:
name|referencedSiteManager
operator|.
name|getReferencedSiteIds
argument_list|()
control|)
block|{
name|referencedSites
operator|.
name|put
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%sentityhub/site/%s/"
argument_list|,
name|uriInfo
operator|.
name|getBaseUri
argument_list|()
argument_list|,
name|site
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|Response
operator|.
name|ok
argument_list|(
name|referencedSites
argument_list|)
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|acceptable
operator|+
literal|"; charset=utf-8"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
comment|/**      * Cool URI handler for Signs.      *       * @param id      *            The id of the entity (required)      * @param headers      *            the request headers used to get the requested {@link MediaType}      * @return a redirection to either a browser view, the RDF meta data or the raw binary content      */
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
literal|"/entity"
argument_list|)
specifier|public
name|Response
name|getEntityById
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"id"
argument_list|)
name|String
name|id
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"getSignById() request\n\t> id       : {}\n\t> accept   : {}\n\t> mediaType: {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|id
block|,
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
block|,
name|headers
operator|.
name|getMediaType
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|supported
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|JerseyUtils
operator|.
name|ENTITY_SUPPORTED_MEDIA_TYPES
argument_list|)
decl_stmt|;
name|supported
operator|.
name|add
argument_list|(
name|TEXT_HTML
argument_list|)
expr_stmt|;
specifier|final
name|MediaType
name|acceptedMediaType
init|=
name|JerseyUtils
operator|.
name|getAcceptableMediaType
argument_list|(
name|headers
argument_list|,
name|supported
argument_list|,
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|==
literal|null
operator|||
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|MediaType
operator|.
name|TEXT_HTML_TYPE
operator|.
name|isCompatible
argument_list|(
name|acceptedMediaType
argument_list|)
condition|)
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"entity"
argument_list|,
name|this
argument_list|)
argument_list|)
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|TEXT_HTML
operator|+
literal|"; charset=utf-8"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"No or empty ID was parsed. Missing parameter id.\n"
argument_list|)
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|ACCEPT
argument_list|,
name|acceptedMediaType
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
name|ReferencedSiteManager
name|referencedSiteManager
init|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|ReferencedSiteManager
operator|.
name|class
argument_list|,
name|context
argument_list|)
decl_stmt|;
name|Entity
name|sign
decl_stmt|;
comment|// try {
name|sign
operator|=
name|referencedSiteManager
operator|.
name|getEntity
argument_list|(
name|id
argument_list|)
expr_stmt|;
comment|// } catch (IOException e) {
comment|// log.error("IOException while accessing ReferencedSiteManager",e);
comment|// throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
comment|// }
if|if
condition|(
name|sign
operator|!=
literal|null
condition|)
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
name|sign
argument_list|)
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|acceptedMediaType
operator|+
literal|"; charset=utf-8"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
comment|// TODO: How to parse an ErrorMessage?
comment|// create an Response with the the Error?
name|log
operator|.
name|info
argument_list|(
literal|"getSignById() entity {} not found on any referenced site"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Entity with ID '"
operator|+
name|id
operator|+
literal|"' not found an any referenced site\n"
argument_list|)
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|ACCEPT
argument_list|,
name|acceptedMediaType
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
comment|//    @GET
comment|//    @Path("/find")
comment|//    @Produces(MediaType.TEXT_HTML)
comment|//    public Response getFindPage() {
comment|//        return Response.ok(new Viewable("find", this))
comment|//        .header(HttpHeaders.CONTENT_TYPE, TEXT_HTML+"; charset=utf-8").build();
comment|//    }
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
literal|"/find"
argument_list|)
specifier|public
name|Response
name|findEntityfromGet
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"name"
argument_list|)
name|String
name|name
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"field"
argument_list|)
name|String
name|field
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"lang"
argument_list|)
name|String
name|language
parameter_list|,
comment|// @QueryParam(value="select") String select,
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"limit"
argument_list|)
annotation|@
name|DefaultValue
argument_list|(
name|value
operator|=
literal|"-1"
argument_list|)
name|int
name|limit
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"offset"
argument_list|)
annotation|@
name|DefaultValue
argument_list|(
name|value
operator|=
literal|"0"
argument_list|)
name|int
name|offset
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
return|return
name|findEntity
argument_list|(
name|name
argument_list|,
name|field
argument_list|,
name|language
argument_list|,
name|limit
argument_list|,
name|offset
argument_list|,
name|headers
argument_list|)
return|;
block|}
annotation|@
name|POST
annotation|@
name|Path
argument_list|(
literal|"/find"
argument_list|)
specifier|public
name|Response
name|findEntity
parameter_list|(
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"name"
argument_list|)
name|String
name|name
parameter_list|,
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"field"
argument_list|)
name|String
name|field
parameter_list|,
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"lang"
argument_list|)
name|String
name|language
parameter_list|,
comment|// @FormParam(value="select") String select,
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"limit"
argument_list|)
name|Integer
name|limit
parameter_list|,
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"offset"
argument_list|)
name|Integer
name|offset
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"findEntity() Request"
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|supported
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|JerseyUtils
operator|.
name|QUERY_RESULT_SUPPORTED_MEDIA_TYPES
argument_list|)
decl_stmt|;
name|supported
operator|.
name|add
argument_list|(
name|TEXT_HTML
argument_list|)
expr_stmt|;
specifier|final
name|MediaType
name|acceptedMediaType
init|=
name|JerseyUtils
operator|.
name|getAcceptableMediaType
argument_list|(
name|headers
argument_list|,
name|supported
argument_list|,
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|MediaType
operator|.
name|TEXT_HTML_TYPE
operator|.
name|isCompatible
argument_list|(
name|acceptedMediaType
argument_list|)
condition|)
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"find"
argument_list|,
name|this
argument_list|)
argument_list|)
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|TEXT_HTML
operator|+
literal|"; charset=utf-8"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"The name must not be null nor empty for find requests. Missing parameter name.\n"
argument_list|)
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|ACCEPT
argument_list|,
name|acceptedMediaType
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
if|if
condition|(
name|field
operator|==
literal|null
condition|)
block|{
name|field
operator|=
name|DEFAULT_FIND_FIELD
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
if|if
condition|(
name|field
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|field
operator|=
name|DEFAULT_FIND_FIELD
expr_stmt|;
block|}
block|}
name|ReferencedSiteManager
name|referencedSiteManager
init|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|ReferencedSiteManager
operator|.
name|class
argument_list|,
name|context
argument_list|)
decl_stmt|;
name|FieldQuery
name|query
init|=
name|JerseyUtils
operator|.
name|createFieldQueryForFindRequest
argument_list|(
name|name
argument_list|,
name|field
argument_list|,
name|language
argument_list|,
name|limit
operator|==
literal|null
operator|||
name|limit
operator|<
literal|1
condition|?
name|DEFAULT_FIND_RESULT_LIMIT
else|:
name|limit
argument_list|,
name|offset
argument_list|)
decl_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|(
name|referencedSiteManager
operator|.
name|find
argument_list|(
name|query
argument_list|)
argument_list|)
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|acceptedMediaType
operator|+
literal|"; charset=utf-8"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
literal|"/query"
argument_list|)
specifier|public
name|Response
name|getQueryDocumentation
parameter_list|()
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"query"
argument_list|,
name|this
argument_list|)
argument_list|)
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|TEXT_HTML
operator|+
literal|"; charset=utf-8"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|/**      * Allows to parse any kind of {@link FieldQuery} in its JSON Representation.      *<p>      * TODO: as soon as the entityhub supports multiple query types this need to be refactored. The idea is      * that this dynamically detects query types and than redirects them to the referenced site      * implementation.      *       * @param query      *            The field query in JSON format      * @param headers      *            the header information of the request      * @return the results of the query      */
annotation|@
name|POST
annotation|@
name|Path
argument_list|(
literal|"/query"
argument_list|)
annotation|@
name|Consumes
argument_list|(
block|{
name|MediaType
operator|.
name|APPLICATION_JSON
block|}
argument_list|)
specifier|public
name|Response
name|queryEntities
parameter_list|(
name|FieldQuery
name|query
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|ReferencedSiteManager
name|referencedSiteManager
init|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|ReferencedSiteManager
operator|.
name|class
argument_list|,
name|context
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|supported
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|JerseyUtils
operator|.
name|QUERY_RESULT_SUPPORTED_MEDIA_TYPES
argument_list|)
decl_stmt|;
name|supported
operator|.
name|add
argument_list|(
name|TEXT_HTML
argument_list|)
expr_stmt|;
specifier|final
name|MediaType
name|acceptedMediaType
init|=
name|JerseyUtils
operator|.
name|getAcceptableMediaType
argument_list|(
name|headers
argument_list|,
name|supported
argument_list|,
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
argument_list|)
decl_stmt|;
if|if
condition|(
name|query
operator|==
literal|null
condition|)
block|{
comment|//if query is null nd the mediaType is HTML we need to print the
comment|//Documentation of the RESTful API
if|if
condition|(
name|MediaType
operator|.
name|TEXT_HTML_TYPE
operator|.
name|isCompatible
argument_list|(
name|acceptedMediaType
argument_list|)
condition|)
block|{
return|return
name|getQueryDocumentation
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"The query must not be null nor empty for query requests. Missing parameter query.\n"
argument_list|)
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|ACCEPT
argument_list|,
name|acceptedMediaType
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
name|referencedSiteManager
operator|.
name|find
argument_list|(
name|query
argument_list|)
argument_list|)
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|acceptedMediaType
operator|+
literal|"; charset=utf-8"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

