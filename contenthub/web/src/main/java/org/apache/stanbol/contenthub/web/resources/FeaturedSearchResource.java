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
name|contenthub
operator|.
name|web
operator|.
name|resources
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
name|CorsHelper
operator|.
name|addCORSOrigin
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
name|CorsHelper
operator|.
name|enableCORS
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
name|Iterator
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
name|OPTIONS
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
name|PathParam
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
name|core
operator|.
name|UriRef
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
name|core
operator|.
name|access
operator|.
name|TcManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrQuery
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrServerException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|common
operator|.
name|params
operator|.
name|SolrParams
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|servlet
operator|.
name|SolrRequestParsers
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
name|viewable
operator|.
name|Viewable
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|Constants
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|ldpath
operator|.
name|SemanticIndexManager
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|SearchException
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|featured
operator|.
name|FacetResult
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|featured
operator|.
name|FeaturedSearch
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|featured
operator|.
name|SearchResult
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|related
operator|.
name|RelatedKeywordSearchManager
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|solr
operator|.
name|SolrQueryUtil
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|store
operator|.
name|vocabulary
operator|.
name|SolrVocabulary
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
name|contenthub
operator|.
name|web
operator|.
name|util
operator|.
name|JSONUtils
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
name|contenthub
operator|.
name|web
operator|.
name|util
operator|.
name|RestUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|InvalidSyntaxException
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
comment|/**  * This class is the web resource which provides RESTful and HTTP interfaces for {@link FeaturedSearch}  * services.  *   * @author anil.sinaci  * @author suat  *   */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/contenthub/{index}/search/featured"
argument_list|)
specifier|public
class|class
name|FeaturedSearchResource
extends|extends
name|BaseStanbolResource
block|{
specifier|private
specifier|final
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|FeaturedSearchResource
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|TcManager
name|tcManager
decl_stmt|;
specifier|private
name|FeaturedSearch
name|featuredSearch
decl_stmt|;
specifier|private
name|String
name|indexName
decl_stmt|;
comment|/**      *       * @param context      * @param indexName      *            Name of the LDPath program (name of the Solr core/index) to be used while storing this      *            content item. LDPath programs can be managed through {@link SemanticIndexManagerResource} or      *            {@link SemanticIndexManager}      * @throws IOException      * @throws InvalidSyntaxException      */
specifier|public
name|FeaturedSearchResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|,
annotation|@
name|PathParam
argument_list|(
name|value
operator|=
literal|"index"
argument_list|)
name|String
name|indexName
parameter_list|)
throws|throws
name|IOException
throws|,
name|InvalidSyntaxException
block|{
name|this
operator|.
name|indexName
operator|=
name|indexName
expr_stmt|;
name|this
operator|.
name|featuredSearch
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|FeaturedSearch
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|this
operator|.
name|tcManager
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|TcManager
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|OPTIONS
specifier|public
name|Response
name|handleCorsPreflight
parameter_list|(
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|ResponseBuilder
name|res
init|=
name|Response
operator|.
name|ok
argument_list|()
decl_stmt|;
name|enableCORS
argument_list|(
name|servletContext
argument_list|,
name|res
argument_list|,
name|headers
argument_list|)
expr_stmt|;
return|return
name|res
operator|.
name|build
argument_list|()
return|;
block|}
comment|/**      * HTTP GET method to make a featured search over Contenthub.      *       * @param queryTerm      *            A keyword a statement or a set of keywords which can be regarded as the query term.      * @param solrQuery      *            Solr query string. This is the string format which is accepted by a Solr server. For      *            example, {@code q="john doe"&fl=score} is a valid value for this parameter. If this      *            parameter exists, search is performed based on this solrQuery and any queryTerms are      *            neglected.      * @param jsonCons      *            Constrainst in JSON format. These constraints are tranformed to corresponding Solr queries      *            to enable faceted search. Each constraint is a facet field and values of the constraints      *            maps to the values of the facet fields in Solr queries.      * @param ontologyURI      *            URI of the ontology in which related keywords will be searched by      *            {@link RelatedKeywordSearchManager#getRelatedKeywordsFromOntology(String, String)}      * @param offset      *            The offset of the document from which the resultant documents will start as the search      *            result. {@link offset} and {@link limit} parameters can be used to make a pagination      *            mechanism for search results.      * @param limit      *            Maximum number of resultant documents to be returned as the search result. {@link offset}      *            and {@link limit} parameters can be used to make a pagination mechanism for search results.      * @param fromStore      *            Special parameter for HTML view only.      * @param headers      *            HTTP headers      * @return HTML view or JSON representation of the search results or HTTP BAD REQUEST(400)      * @throws IllegalArgumentException      * @throws SearchException      * @throws InstantiationException      * @throws IllegalAccessException      * @throws SolrServerException      * @throws IOException      */
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
block|{
name|MediaType
operator|.
name|TEXT_HTML
block|,
name|MediaType
operator|.
name|APPLICATION_JSON
block|}
argument_list|)
specifier|public
specifier|final
name|Response
name|get
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
literal|"queryTerm"
argument_list|)
name|String
name|queryTerm
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"solrQuery"
argument_list|)
name|String
name|solrQuery
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"constraints"
argument_list|)
name|String
name|jsonCons
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"ontologyURI"
argument_list|)
name|String
name|ontologyURI
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"offset"
argument_list|)
annotation|@
name|DefaultValue
argument_list|(
literal|"0"
argument_list|)
name|int
name|offset
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"limit"
argument_list|)
annotation|@
name|DefaultValue
argument_list|(
literal|"10"
argument_list|)
name|int
name|limit
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"fromStore"
argument_list|)
name|String
name|fromStore
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|SearchException
throws|,
name|InstantiationException
throws|,
name|IllegalAccessException
throws|,
name|SolrServerException
throws|,
name|IOException
block|{
name|MediaType
name|acceptedHeader
init|=
name|RestUtil
operator|.
name|getAcceptedMediaType
argument_list|(
name|headers
argument_list|,
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
argument_list|)
decl_stmt|;
name|this
operator|.
name|queryTerm
operator|=
name|queryTerm
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|queryTerm
argument_list|)
expr_stmt|;
name|solrQuery
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|solrQuery
argument_list|)
expr_stmt|;
name|ontologyURI
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|ontologyURI
argument_list|)
expr_stmt|;
name|jsonCons
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|jsonCons
argument_list|)
expr_stmt|;
name|this
operator|.
name|offset
operator|=
name|offset
expr_stmt|;
name|this
operator|.
name|pageSize
operator|=
name|limit
expr_stmt|;
if|if
condition|(
name|acceptedHeader
operator|.
name|isCompatible
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML_TYPE
argument_list|)
condition|)
block|{
if|if
condition|(
name|fromStore
operator|!=
literal|null
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
literal|"index"
argument_list|,
name|this
argument_list|)
argument_list|,
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
if|if
condition|(
name|queryTerm
operator|==
literal|null
operator|&&
name|solrQuery
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|ontologies
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|Set
argument_list|<
name|UriRef
argument_list|>
name|mGraphs
init|=
name|tcManager
operator|.
name|listMGraphs
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|UriRef
argument_list|>
name|it
init|=
name|mGraphs
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|ontologyURI
operator|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
expr_stmt|;
if|if
condition|(
name|Constants
operator|.
name|isGraphReserved
argument_list|(
name|ontologyURI
argument_list|)
condition|)
block|{
continue|continue;
block|}
name|this
operator|.
name|ontologies
operator|.
name|add
argument_list|(
name|ontologyURI
argument_list|)
expr_stmt|;
block|}
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
argument_list|,
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
name|ResponseBuilder
name|rb
init|=
name|performSearch
argument_list|(
name|queryTerm
argument_list|,
name|solrQuery
argument_list|,
name|jsonCons
argument_list|,
name|ontologyURI
argument_list|,
name|offset
argument_list|,
name|limit
argument_list|,
name|MediaType
operator|.
name|TEXT_HTML_TYPE
argument_list|)
decl_stmt|;
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|headers
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|queryTerm
operator|==
literal|null
operator|&&
name|solrQuery
operator|==
literal|null
condition|)
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
literal|"Either 'queryTerm' or 'solrQuery' should be specified"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
name|ResponseBuilder
name|rb
init|=
name|performSearch
argument_list|(
name|queryTerm
argument_list|,
name|solrQuery
argument_list|,
name|jsonCons
argument_list|,
name|ontologyURI
argument_list|,
name|offset
argument_list|,
name|limit
argument_list|,
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
argument_list|)
decl_stmt|;
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|headers
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
block|}
block|}
specifier|private
name|ResponseBuilder
name|performSearch
parameter_list|(
name|String
name|queryTerm
parameter_list|,
name|String
name|solrQuery
parameter_list|,
name|String
name|jsonCons
parameter_list|,
name|String
name|ontologyURI
parameter_list|,
name|int
name|offset
parameter_list|,
name|int
name|limit
parameter_list|,
name|MediaType
name|acceptedMediaType
parameter_list|)
throws|throws
name|SearchException
block|{
if|if
condition|(
name|solrQuery
operator|!=
literal|null
condition|)
block|{
name|SolrParams
name|solrParams
init|=
name|SolrRequestParsers
operator|.
name|parseQueryString
argument_list|(
name|solrQuery
argument_list|)
decl_stmt|;
name|this
operator|.
name|searchResults
operator|=
name|featuredSearch
operator|.
name|search
argument_list|(
name|solrParams
argument_list|,
name|ontologyURI
argument_list|,
name|indexName
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|queryTerm
operator|!=
literal|null
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|constraintsMap
init|=
name|JSONUtils
operator|.
name|convertToMap
argument_list|(
name|jsonCons
argument_list|)
decl_stmt|;
name|this
operator|.
name|chosenFacets
operator|=
name|JSONUtils
operator|.
name|convertToString
argument_list|(
name|constraintsMap
argument_list|)
expr_stmt|;
name|SolrQuery
name|sq
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|chosenFacets
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|FacetResult
argument_list|>
name|allAvailableFacets
init|=
name|featuredSearch
operator|.
name|getAllFacetResults
argument_list|(
name|indexName
argument_list|)
decl_stmt|;
name|sq
operator|=
name|SolrQueryUtil
operator|.
name|prepareSolrQuery
argument_list|(
name|queryTerm
argument_list|,
name|allAvailableFacets
argument_list|,
name|constraintsMap
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sq
operator|=
name|SolrQueryUtil
operator|.
name|prepareSolrQuery
argument_list|(
name|queryTerm
argument_list|)
expr_stmt|;
block|}
name|sq
operator|.
name|setStart
argument_list|(
name|offset
argument_list|)
expr_stmt|;
name|sq
operator|.
name|setRows
argument_list|(
name|limit
operator|+
literal|1
argument_list|)
expr_stmt|;
name|this
operator|.
name|searchResults
operator|=
name|featuredSearch
operator|.
name|search
argument_list|(
name|sq
argument_list|,
name|ontologyURI
argument_list|,
name|indexName
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Should never reach here!!!!"
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|SearchException
argument_list|(
literal|"Either 'queryTerm' or 'solrQuery' paramater should be set"
argument_list|)
throw|;
block|}
name|ResponseBuilder
name|rb
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|acceptedMediaType
operator|.
name|isCompatible
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML_TYPE
argument_list|)
condition|)
block|{
comment|// return HTML document
comment|/*              * For HTML view, sort facets according to their names              */
name|this
operator|.
name|searchResults
operator|.
name|setFacets
argument_list|(
name|sortFacetResults
argument_list|(
name|this
operator|.
name|searchResults
operator|.
name|getFacets
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|rb
operator|=
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"result.ftl"
argument_list|,
name|this
argument_list|)
argument_list|)
expr_stmt|;
name|rb
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|MediaType
operator|.
name|TEXT_HTML
operator|+
literal|"; charset=utf-8"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// it is compatible with JSON (default) - return JSON
name|rb
operator|=
name|Response
operator|.
name|ok
argument_list|(
name|this
operator|.
name|searchResults
argument_list|)
expr_stmt|;
name|rb
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|MediaType
operator|.
name|APPLICATION_JSON
operator|+
literal|"; charset=utf-8"
argument_list|)
expr_stmt|;
block|}
return|return
name|rb
return|;
block|}
specifier|private
name|List
argument_list|<
name|FacetResult
argument_list|>
name|sortFacetResults
parameter_list|(
name|List
argument_list|<
name|FacetResult
argument_list|>
name|facetResults
parameter_list|)
block|{
name|List
argument_list|<
name|FacetResult
argument_list|>
name|orderedFacets
init|=
operator|new
name|ArrayList
argument_list|<
name|FacetResult
argument_list|>
argument_list|()
decl_stmt|;
name|int
name|annotatedFacetNum
init|=
literal|0
decl_stmt|;
for|for
control|(
name|FacetResult
name|fr
range|:
name|facetResults
control|)
block|{
name|String
name|facetName
init|=
name|fr
operator|.
name|getFacetField
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|fr
operator|.
name|getFacetField
argument_list|()
operator|.
name|getValues
argument_list|()
operator|==
literal|null
condition|)
block|{
continue|continue;
block|}
elseif|else
if|if
condition|(
name|SolrVocabulary
operator|.
name|SolrFieldName
operator|.
name|isAnnotatedEntityFacet
argument_list|(
name|facetName
argument_list|)
condition|)
block|{
name|orderedFacets
operator|.
name|add
argument_list|(
name|annotatedFacetNum
argument_list|,
name|fr
argument_list|)
expr_stmt|;
name|annotatedFacetNum
operator|++
expr_stmt|;
block|}
else|else
block|{
name|boolean
name|inserted
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
name|annotatedFacetNum
init|;
name|j
operator|<
name|orderedFacets
operator|.
name|size
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|facetName
operator|.
name|compareTo
argument_list|(
name|orderedFacets
operator|.
name|get
argument_list|(
name|j
argument_list|)
operator|.
name|getFacetField
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
operator|<
literal|0
condition|)
block|{
name|orderedFacets
operator|.
name|add
argument_list|(
name|j
argument_list|,
name|fr
argument_list|)
expr_stmt|;
name|inserted
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|inserted
operator|==
literal|false
condition|)
block|{
name|orderedFacets
operator|.
name|add
argument_list|(
name|fr
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|orderedFacets
return|;
block|}
comment|/*      * Services to draw HTML view      */
comment|// Data holders for HTML view
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|ontologies
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|queryTerm
init|=
literal|null
decl_stmt|;
comment|// private String solrQuery = null;
comment|// private String ldProgram = null;
comment|// private String graphURI = null;
specifier|private
name|SearchResult
name|searchResults
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|chosenFacets
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|offset
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|pageSize
init|=
literal|10
decl_stmt|;
comment|// ///////////////////////////
comment|/*      * Helper methods for HTML view      */
specifier|public
name|Object
name|getMoreRecentItems
parameter_list|()
block|{
if|if
condition|(
name|offset
operator|>=
name|pageSize
condition|)
block|{
return|return
operator|new
name|Object
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
name|Object
name|getOlderItems
parameter_list|()
block|{
if|if
condition|(
name|searchResults
operator|.
name|getDocuments
argument_list|()
operator|.
name|size
argument_list|()
operator|<=
name|pageSize
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
operator|new
name|Object
argument_list|()
return|;
block|}
block|}
specifier|public
name|int
name|getOffset
parameter_list|()
block|{
return|return
name|this
operator|.
name|offset
return|;
block|}
specifier|public
name|int
name|getPageSize
parameter_list|()
block|{
return|return
name|this
operator|.
name|pageSize
return|;
block|}
specifier|public
name|Object
name|getSearchResults
parameter_list|()
block|{
return|return
name|this
operator|.
name|searchResults
return|;
block|}
specifier|public
name|Object
name|getDocuments
parameter_list|()
block|{
if|if
condition|(
name|searchResults
operator|.
name|getDocuments
argument_list|()
operator|.
name|size
argument_list|()
operator|>
name|pageSize
condition|)
block|{
return|return
name|searchResults
operator|.
name|getDocuments
argument_list|()
operator|.
name|subList
argument_list|(
literal|0
argument_list|,
name|pageSize
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|searchResults
operator|.
name|getDocuments
argument_list|()
return|;
block|}
block|}
specifier|public
name|Object
name|getOntologies
parameter_list|()
block|{
return|return
name|this
operator|.
name|ontologies
return|;
block|}
specifier|public
name|Object
name|getQueryTerm
parameter_list|()
block|{
if|if
condition|(
name|queryTerm
operator|!=
literal|null
condition|)
block|{
return|return
name|queryTerm
return|;
block|}
return|return
literal|""
return|;
block|}
specifier|public
name|String
name|getChosenFacets
parameter_list|()
block|{
return|return
name|this
operator|.
name|chosenFacets
return|;
block|}
specifier|public
name|String
name|getIndexName
parameter_list|()
block|{
return|return
name|this
operator|.
name|indexName
return|;
block|}
block|}
end_class

end_unit

