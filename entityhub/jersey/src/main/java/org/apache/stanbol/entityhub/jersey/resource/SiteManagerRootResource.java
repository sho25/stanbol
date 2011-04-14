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
name|APPLICATION_FORM_URLENCODED
import|;
end_import

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
name|APPLICATION_JSON_TYPE
import|;
end_import

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
name|MULTIPART_FORM_DATA
import|;
end_import

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
name|Response
operator|.
name|Status
operator|.
name|BAD_REQUEST
import|;
end_import

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
name|Response
operator|.
name|Status
operator|.
name|NOT_FOUND
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
name|File
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
name|Sign
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
name|APPLICATION_JSON
argument_list|)
specifier|public
name|JSONArray
name|getReferencedSites
parameter_list|(
annotation|@
name|Context
name|UriInfo
name|uriInfo
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"getReferencedSites() request"
argument_list|)
expr_stmt|;
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
name|log
operator|.
name|debug
argument_list|(
literal|"getReferencedSites() returns {}"
argument_list|,
name|referencedSites
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|referencedSites
return|;
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
name|getSignById
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
name|log
operator|.
name|error
argument_list|(
literal|"getSignById() No or emptpy ID was parsed as query parameter (id={})"
argument_list|,
name|id
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|BAD_REQUEST
argument_list|)
throw|;
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
name|Sign
name|sign
decl_stmt|;
comment|// try {
name|sign
operator|=
name|referencedSiteManager
operator|.
name|getSign
argument_list|(
name|id
argument_list|)
expr_stmt|;
comment|// } catch (IOException e) {
comment|// log.error("IOException while accessing ReferencedSiteManager",e);
comment|// throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
comment|// }
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
name|APPLICATION_JSON_TYPE
argument_list|)
decl_stmt|;
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
argument_list|,
name|acceptedMediaType
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
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|NOT_FOUND
argument_list|)
throw|;
block|}
block|}
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
name|QueryParam
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
name|APPLICATION_JSON_TYPE
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
argument_list|,
name|acceptedMediaType
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|/**      * Allows to parse any kind of {@link FieldQuery} in its JSON Representation. Note that the maximum number      * of results (limit) and the offset of the first result (offset) are parsed as seperate parameters and      * are not part of the field query as in the java API.      *<p>      * TODO: as soon as the entityhub supports multiple query types this need to be refactored. The idea is      * that this dynamically detects query types and than redirects them to the referenced site      * implementation.      *       * @param query      *            The field query in JSON format      * @param limit      *            the maximum number of results starting at offset      * @param offset      *            the offset of the first result      * @param headers      *            the header information of the request      * @return the results of the query      */
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
name|APPLICATION_FORM_URLENCODED
operator|+
literal|";qs=1.0"
block|,
name|MULTIPART_FORM_DATA
operator|+
literal|";qs=0.9"
block|}
argument_list|)
specifier|public
name|Response
name|queryEntities
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"query"
argument_list|)
name|String
name|queryString
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"query"
argument_list|)
name|File
name|file
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
name|FieldQuery
name|query
init|=
name|JerseyUtils
operator|.
name|parseFieldQuery
argument_list|(
name|queryString
argument_list|,
name|file
argument_list|)
decl_stmt|;
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
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
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
argument_list|,
name|acceptedMediaType
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
end_class

end_unit

