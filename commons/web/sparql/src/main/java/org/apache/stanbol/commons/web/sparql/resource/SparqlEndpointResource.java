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
name|sparql
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
name|TEXT_HTML
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
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedHashMap
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
name|TripleCollection
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|sparql
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|sparql
operator|.
name|QueryParser
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
name|sparql
operator|.
name|query
operator|.
name|ConstructQuery
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
name|sparql
operator|.
name|query
operator|.
name|DescribeQuery
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
name|sparql
operator|.
name|query
operator|.
name|Query
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Reference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|resource
operator|.
name|BaseStanbolResource
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
name|BundleContext
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
name|Constants
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
name|osgi
operator|.
name|framework
operator|.
name|ServiceReference
import|;
end_import

begin_comment
comment|/**  * This is the SPARQL endpoint which is used throughout the Stanbol. It uses {@link BundleContext} to retrive  * {@link TripleCollection} s registered to OSGi environment. To be able to execute SPARQL queries on triple  * collections, they should be registered to the OSGi environment with the following parameters:  *   *<p>  *<ul>  *<li>graph.uri<b>(required)</b> : The URI of the graph. This is the same as used with the TcManager</li>  *<li>service.ranking: If this parameter is not specified, "0" will be used as default value.</li>  *<li>graph.name: The name of the graph. Human readable name intended to be used in the UI</li>  *<li>graph.description: human readable description providing additional information about the RDF graph</li>  *</ul>  *</p>  *   *<p>  * If a uri is not specified, the graph having highest service.ranking value will be chosen.  *</p>  *   */
end_comment

begin_class
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|Object
operator|.
name|class
argument_list|)
annotation|@
name|Property
argument_list|(
name|name
operator|=
literal|"javax.ws.rs"
argument_list|,
name|boolValue
operator|=
literal|true
argument_list|)
annotation|@
name|Path
argument_list|(
literal|"/sparql"
argument_list|)
specifier|public
class|class
name|SparqlEndpointResource
extends|extends
name|BaseStanbolResource
block|{
specifier|private
specifier|static
specifier|final
name|Comparator
argument_list|<
name|ServiceReference
argument_list|>
name|SERVICE_RANKING_COMPARATOR
init|=
operator|new
name|Comparator
argument_list|<
name|ServiceReference
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|ServiceReference
name|ref1
parameter_list|,
name|ServiceReference
name|ref2
parameter_list|)
block|{
name|int
name|r1
decl_stmt|,
name|r2
decl_stmt|;
name|Object
name|tmp
init|=
name|ref1
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|)
decl_stmt|;
name|r1
operator|=
name|tmp
operator|!=
literal|null
condition|?
operator|(
operator|(
name|Integer
operator|)
name|tmp
operator|)
operator|.
name|intValue
argument_list|()
else|:
literal|0
expr_stmt|;
name|tmp
operator|=
name|ref2
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|)
expr_stmt|;
name|r2
operator|=
name|tmp
operator|!=
literal|null
condition|?
operator|(
operator|(
name|Integer
operator|)
name|tmp
operator|)
operator|.
name|intValue
argument_list|()
else|:
literal|0
expr_stmt|;
if|if
condition|(
name|r1
operator|==
name|r2
condition|)
block|{
name|tmp
operator|=
name|ref1
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_ID
argument_list|)
expr_stmt|;
name|long
name|id1
init|=
name|tmp
operator|!=
literal|null
condition|?
operator|(
operator|(
name|Long
operator|)
name|tmp
operator|)
operator|.
name|longValue
argument_list|()
else|:
name|Long
operator|.
name|MAX_VALUE
decl_stmt|;
name|tmp
operator|=
name|ref2
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_ID
argument_list|)
expr_stmt|;
name|long
name|id2
init|=
name|tmp
operator|!=
literal|null
condition|?
operator|(
operator|(
name|Long
operator|)
name|tmp
operator|)
operator|.
name|longValue
argument_list|()
else|:
name|Long
operator|.
name|MAX_VALUE
decl_stmt|;
comment|//the lowest id must be first -> id1< id2 -> [id1,id2] -> return -1
return|return
name|id1
operator|<
name|id2
condition|?
operator|-
literal|1
else|:
name|id2
operator|==
name|id1
condition|?
literal|0
else|:
literal|1
return|;
block|}
else|else
block|{
comment|//the highest ranking MUST BE first -> r1< r2 -> [r2,r1] -> return 1
return|return
name|r1
operator|<
name|r2
condition|?
literal|1
else|:
operator|-
literal|1
return|;
block|}
block|}
block|}
decl_stmt|;
annotation|@
name|Reference
specifier|protected
name|TcManager
name|tcManager
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|GRAPH_URI
init|=
literal|"graph.uri"
decl_stmt|;
specifier|private
name|BundleContext
name|bundleContext
decl_stmt|;
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|BundleContext
name|bundleContext
parameter_list|)
block|{
name|this
operator|.
name|bundleContext
operator|=
name|bundleContext
expr_stmt|;
block|}
comment|//TODO re-enable
comment|/*@OPTIONS     public Response handleCorsPreflight(@Context HttpHeaders headers) {         ResponseBuilder res = Response.ok();         enableCORS(servletContext, res, headers);         return res.build();     }*/
comment|/**      * HTTP GET service to execute SPARQL queries on {@link TripleCollection}s registered to OSGi environment.      * If a<code>null</code>, it is assumed that the request is coming from the HTML interface of SPARQL      * endpoint. Otherwise the query is executed on the triple collection specified by<code>graphUri</code>.      * But, if no graph uri is passed, then the triple collection having highest service.ranking value is      * chosen.      *       * Type of the result is determined according to type of the query such that if the specified query is      * either a<b>describe query</b> or<b>construct query</b>, results are returned in      *<b>application/rdf+xml</b> format, otherwise in<b>pplication/sparql-results+xml</b> format.      *       * @param graphUri      *            the URI of the graph on which the SPARQL query will be executed.      * @param sparqlQuery      *            SPARQL query to be executed      * @return      */
annotation|@
name|GET
annotation|@
name|Consumes
argument_list|(
name|APPLICATION_FORM_URLENCODED
argument_list|)
annotation|@
name|Produces
argument_list|(
block|{
name|TEXT_HTML
operator|+
literal|";qs=2"
block|,
literal|"application/sparql-results+xml"
block|,
literal|"application/rdf+xml"
block|}
argument_list|)
specifier|public
name|Response
name|sparql
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"graphuri"
argument_list|)
name|String
name|graphUri
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"query"
argument_list|)
name|String
name|sparqlQuery
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
throws|throws
name|ParseException
throws|,
name|InvalidSyntaxException
block|{
if|if
condition|(
name|sparqlQuery
operator|==
literal|null
condition|)
block|{
name|populateTripleCollectionList
argument_list|(
name|getServices
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
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
name|TEXT_HTML
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|Query
name|query
init|=
name|QueryParser
operator|.
name|getInstance
argument_list|()
operator|.
name|parse
argument_list|(
name|sparqlQuery
argument_list|)
decl_stmt|;
name|String
name|mediaType
init|=
literal|"application/sparql-results+xml"
decl_stmt|;
if|if
condition|(
name|query
operator|instanceof
name|DescribeQuery
operator|||
name|query
operator|instanceof
name|ConstructQuery
condition|)
block|{
name|mediaType
operator|=
literal|"application/rdf+xml"
expr_stmt|;
block|}
name|TripleCollection
name|tripleCollection
init|=
name|getTripleCollection
argument_list|(
name|graphUri
argument_list|)
decl_stmt|;
name|ResponseBuilder
name|rb
decl_stmt|;
if|if
condition|(
name|tripleCollection
operator|!=
literal|null
condition|)
block|{
name|Object
name|result
init|=
name|tcManager
operator|.
name|executeSparqlQuery
argument_list|(
name|query
argument_list|,
name|tripleCollection
argument_list|)
decl_stmt|;
name|rb
operator|=
name|Response
operator|.
name|ok
argument_list|(
name|result
argument_list|,
name|mediaType
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|rb
operator|=
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
name|String
operator|.
name|format
argument_list|(
literal|"There is no registered graph with given uri: %s"
argument_list|,
name|graphUri
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//addCORSOrigin(servletContext, rb, headers);
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
comment|/**      * HTTP GET service to execute SPARQL queries on {@link TripleCollection}s registered to OSGi environment.      * For details, see {@link #sparql(String, String, HttpHeaders)}      */
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|APPLICATION_FORM_URLENCODED
argument_list|)
annotation|@
name|Produces
argument_list|(
block|{
literal|"application/sparql-results+xml"
block|,
literal|"application/rdf+xml"
block|}
argument_list|)
specifier|public
name|Response
name|postSparql
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"graphuri"
argument_list|)
name|String
name|graphUri
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"query"
argument_list|)
name|String
name|sparqlQuery
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
throws|throws
name|ParseException
throws|,
name|InvalidSyntaxException
block|{
return|return
name|sparql
argument_list|(
name|graphUri
argument_list|,
name|sparqlQuery
argument_list|,
name|headers
argument_list|)
return|;
block|}
specifier|private
name|TripleCollection
name|getTripleCollection
parameter_list|(
name|String
name|graphUri
parameter_list|)
throws|throws
name|InvalidSyntaxException
block|{
name|LinkedHashMap
argument_list|<
name|ServiceReference
argument_list|,
name|TripleCollection
argument_list|>
name|services
init|=
name|getServices
argument_list|(
name|graphUri
argument_list|)
decl_stmt|;
if|if
condition|(
name|services
operator|!=
literal|null
operator|&&
name|services
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
return|return
name|services
operator|.
name|get
argument_list|(
name|services
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|private
name|void
name|populateTripleCollectionList
parameter_list|(
name|LinkedHashMap
argument_list|<
name|ServiceReference
argument_list|,
name|TripleCollection
argument_list|>
name|services
parameter_list|)
block|{
if|if
condition|(
name|services
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|ServiceReference
name|service
range|:
name|services
operator|.
name|keySet
argument_list|()
control|)
block|{
name|Object
name|graphUri
init|=
name|service
operator|.
name|getProperty
argument_list|(
name|GRAPH_URI
argument_list|)
decl_stmt|;
if|if
condition|(
name|service
operator|.
name|getProperty
argument_list|(
name|GRAPH_URI
argument_list|)
operator|instanceof
name|UriRef
condition|)
block|{
name|graphUri
operator|=
operator|(
operator|(
name|UriRef
operator|)
name|graphUri
operator|)
operator|.
name|getUnicodeString
argument_list|()
expr_stmt|;
block|}
name|Object
name|graphName
init|=
name|service
operator|.
name|getProperty
argument_list|(
literal|"graph.name"
argument_list|)
decl_stmt|;
name|Object
name|graphDescription
init|=
name|service
operator|.
name|getProperty
argument_list|(
literal|"graph.description"
argument_list|)
decl_stmt|;
if|if
condition|(
name|graphUri
operator|instanceof
name|String
operator|&&
name|graphName
operator|instanceof
name|String
operator|&&
name|graphDescription
operator|instanceof
name|String
condition|)
block|{
name|tripleCollections
operator|.
name|add
argument_list|(
operator|new
name|TripleCollectionInfo
argument_list|(
operator|(
name|String
operator|)
name|graphUri
argument_list|,
operator|(
name|String
operator|)
name|graphName
argument_list|,
operator|(
name|String
operator|)
name|graphDescription
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|private
name|LinkedHashMap
argument_list|<
name|ServiceReference
argument_list|,
name|TripleCollection
argument_list|>
name|getServices
parameter_list|(
name|String
name|graphUri
parameter_list|)
throws|throws
name|InvalidSyntaxException
block|{
name|LinkedHashMap
argument_list|<
name|ServiceReference
argument_list|,
name|TripleCollection
argument_list|>
name|registeredGraphs
init|=
operator|new
name|LinkedHashMap
argument_list|<
name|ServiceReference
argument_list|,
name|TripleCollection
argument_list|>
argument_list|()
decl_stmt|;
name|ServiceReference
index|[]
name|refs
init|=
name|bundleContext
operator|.
name|getServiceReferences
argument_list|(
name|TripleCollection
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|getFilter
argument_list|(
name|graphUri
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|refs
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|refs
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|Arrays
operator|.
name|sort
argument_list|(
name|refs
argument_list|,
name|SERVICE_RANKING_COMPARATOR
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|ServiceReference
name|ref
range|:
name|refs
control|)
block|{
name|registeredGraphs
operator|.
name|put
argument_list|(
name|ref
argument_list|,
operator|(
name|TripleCollection
operator|)
name|bundleContext
operator|.
name|getService
argument_list|(
name|ref
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|registeredGraphs
return|;
block|}
specifier|private
name|String
name|getFilter
parameter_list|(
name|String
name|graphUri
parameter_list|)
block|{
name|String
name|constraint
init|=
literal|"(%s=%s)"
decl_stmt|;
name|StringBuilder
name|filterString
decl_stmt|;
if|if
condition|(
name|graphUri
operator|!=
literal|null
condition|)
block|{
name|filterString
operator|=
operator|new
name|StringBuilder
argument_list|(
literal|"(&"
argument_list|)
expr_stmt|;
name|filterString
operator|.
name|append
argument_list|(
name|String
operator|.
name|format
argument_list|(
name|constraint
argument_list|,
name|GRAPH_URI
argument_list|,
name|graphUri
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|filterString
operator|=
operator|new
name|StringBuilder
argument_list|()
expr_stmt|;
block|}
name|filterString
operator|.
name|append
argument_list|(
name|String
operator|.
name|format
argument_list|(
name|constraint
argument_list|,
name|Constants
operator|.
name|OBJECTCLASS
argument_list|,
name|TripleCollection
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|graphUri
operator|!=
literal|null
condition|)
block|{
name|filterString
operator|.
name|append
argument_list|(
literal|')'
argument_list|)
expr_stmt|;
block|}
return|return
name|filterString
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/*      * HTML View      */
specifier|private
name|List
argument_list|<
name|TripleCollectionInfo
argument_list|>
name|tripleCollections
init|=
operator|new
name|ArrayList
argument_list|<
name|SparqlEndpointResource
operator|.
name|TripleCollectionInfo
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|List
argument_list|<
name|TripleCollectionInfo
argument_list|>
name|getTripleCollectionList
parameter_list|()
block|{
return|return
name|this
operator|.
name|tripleCollections
return|;
block|}
specifier|public
class|class
name|TripleCollectionInfo
block|{
specifier|private
name|String
name|graphUri
decl_stmt|;
specifier|private
name|String
name|graphName
decl_stmt|;
specifier|private
name|String
name|graphDescription
decl_stmt|;
specifier|public
name|TripleCollectionInfo
parameter_list|(
name|String
name|graphUri
parameter_list|,
name|String
name|graphName
parameter_list|,
name|String
name|graphDescription
parameter_list|)
block|{
name|this
operator|.
name|graphUri
operator|=
name|graphUri
expr_stmt|;
name|this
operator|.
name|graphName
operator|=
name|graphName
operator|!=
literal|null
condition|?
name|graphName
else|:
literal|""
expr_stmt|;
name|this
operator|.
name|graphDescription
operator|=
name|graphDescription
operator|!=
literal|null
condition|?
name|graphDescription
else|:
literal|""
expr_stmt|;
block|}
specifier|public
name|String
name|getGraphUri
parameter_list|()
block|{
return|return
name|graphUri
return|;
block|}
specifier|public
name|String
name|getGraphName
parameter_list|()
block|{
return|return
name|graphName
return|;
block|}
specifier|public
name|String
name|getGraphDescription
parameter_list|()
block|{
return|return
name|graphDescription
return|;
block|}
block|}
block|}
end_class

end_unit

