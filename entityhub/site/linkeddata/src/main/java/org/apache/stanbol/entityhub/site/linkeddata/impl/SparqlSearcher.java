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
name|site
operator|.
name|linkeddata
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
name|io
operator|.
name|InputStream
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
name|Collections
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
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|Graph
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
name|commons
operator|.
name|rdf
operator|.
name|Graph
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
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
name|serializedform
operator|.
name|Parser
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
name|serializedform
operator|.
name|SupportedFormat
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
name|Reference
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
name|indexedgraph
operator|.
name|IndexedGraph
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
name|QueryResultListImpl
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
name|site
operator|.
name|AbstractEntitySearcher
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
name|query
operator|.
name|clerezza
operator|.
name|RdfQueryResultList
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
name|query
operator|.
name|sparql
operator|.
name|SparqlFieldQuery
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
name|query
operator|.
name|sparql
operator|.
name|SparqlFieldQueryFactory
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
name|site
operator|.
name|EntitySearcher
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
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONObject
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

begin_class
annotation|@
name|Component
argument_list|(
name|name
operator|=
literal|"org.apache.stanbol.entityhub.searcher.SparqlSearcher"
argument_list|,
name|factory
operator|=
literal|"org.apache.stanbol.entityhub.searcher.SparqlSearcherFactory"
argument_list|,
name|specVersion
operator|=
literal|"1.1"
argument_list|)
specifier|public
class|class
name|SparqlSearcher
extends|extends
name|AbstractEntitySearcher
implements|implements
name|EntitySearcher
block|{
specifier|public
name|SparqlSearcher
parameter_list|()
block|{
name|super
argument_list|(
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SparqlSearcher
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Reference
specifier|private
name|Parser
name|parser
decl_stmt|;
specifier|protected
specifier|static
specifier|final
name|String
name|DEFAULT_RDF_CONTENT_TYPE
init|=
name|SupportedFormat
operator|.
name|RDF_XML
decl_stmt|;
specifier|protected
specifier|static
specifier|final
name|String
name|DEFAULT_SPARQL_RESULT_CONTENT_TYPE
init|=
name|SparqlEndpointUtils
operator|.
name|SPARQL_RESULT_JSON
decl_stmt|;
annotation|@
name|Override
specifier|public
specifier|final
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|findEntities
parameter_list|(
name|FieldQuery
name|parsedQuery
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|SparqlFieldQuery
name|query
init|=
name|SparqlFieldQueryFactory
operator|.
name|getSparqlFieldQuery
argument_list|(
name|parsedQuery
argument_list|)
decl_stmt|;
name|String
name|sparqlQuery
init|=
name|query
operator|.
name|toSparqlSelect
argument_list|(
literal|false
argument_list|)
decl_stmt|;
name|InputStream
name|in
init|=
name|SparqlEndpointUtils
operator|.
name|sendSparqlRequest
argument_list|(
name|getQueryUri
argument_list|()
argument_list|,
name|sparqlQuery
argument_list|,
name|DEFAULT_SPARQL_RESULT_CONTENT_TYPE
argument_list|)
decl_stmt|;
comment|//Move to util class!
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|entities
init|=
name|extractEntitiesFromJsonResult
argument_list|(
name|in
argument_list|,
name|query
operator|.
name|getRootVariableName
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|QueryResultListImpl
argument_list|<
name|String
argument_list|>
argument_list|(
name|query
argument_list|,
name|entities
operator|.
name|iterator
argument_list|()
argument_list|,
name|String
operator|.
name|class
argument_list|)
return|;
block|}
comment|/**      * Extracts the values of the Query. Also used by {@link VirtuosoSearcher}      * and {@link LarqSearcher}      * @param rootVariable the name of the variable to extract      * @param in the input stream with the data      * @return the extracted results      * @throws IOException if the input streams decides to explode      */
specifier|protected
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|extractEntitiesFromJsonResult
parameter_list|(
name|InputStream
name|in
parameter_list|,
specifier|final
name|String
name|rootVariable
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|entities
decl_stmt|;
try|try
block|{
name|JSONObject
name|result
init|=
operator|new
name|JSONObject
argument_list|(
name|IOUtils
operator|.
name|toString
argument_list|(
name|in
argument_list|)
argument_list|)
decl_stmt|;
name|JSONObject
name|results
init|=
name|result
operator|.
name|getJSONObject
argument_list|(
literal|"results"
argument_list|)
decl_stmt|;
if|if
condition|(
name|results
operator|!=
literal|null
condition|)
block|{
name|JSONArray
name|bindings
init|=
name|results
operator|.
name|getJSONArray
argument_list|(
literal|"bindings"
argument_list|)
decl_stmt|;
if|if
condition|(
name|bindings
operator|!=
literal|null
operator|&&
name|bindings
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|entities
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
name|bindings
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bindings
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|JSONObject
name|solution
init|=
name|bindings
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|solution
operator|!=
literal|null
condition|)
block|{
name|JSONObject
name|rootVar
init|=
name|solution
operator|.
name|getJSONObject
argument_list|(
name|rootVariable
argument_list|)
decl_stmt|;
if|if
condition|(
name|rootVar
operator|!=
literal|null
condition|)
block|{
name|String
name|entityId
init|=
name|rootVar
operator|.
name|getString
argument_list|(
literal|"value"
argument_list|)
decl_stmt|;
if|if
condition|(
name|entityId
operator|!=
literal|null
condition|)
block|{
name|entities
operator|.
name|add
argument_list|(
name|entityId
argument_list|)
expr_stmt|;
block|}
comment|//else missing value (very unlikely)
block|}
comment|//else missing binding for rootVar (very unlikely)
block|}
comment|//else solution in array is null (very unlikely)
block|}
comment|//end for all solutions
block|}
else|else
block|{
name|entities
operator|=
name|Collections
operator|.
name|emptyList
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
name|entities
operator|=
name|Collections
operator|.
name|emptyList
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
comment|//TODO: convert in better exception
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Unable to parse JSON Result Set for parsed query"
argument_list|,
name|e
argument_list|)
throw|;
block|}
return|return
name|entities
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|find
parameter_list|(
name|FieldQuery
name|parsedQuery
parameter_list|)
throws|throws
name|IOException
block|{
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
specifier|final
name|SparqlFieldQuery
name|query
init|=
name|SparqlFieldQueryFactory
operator|.
name|getSparqlFieldQuery
argument_list|(
name|parsedQuery
argument_list|)
decl_stmt|;
name|String
name|sparqlQuery
init|=
name|query
operator|.
name|toSparqlConstruct
argument_list|()
decl_stmt|;
name|long
name|initEnd
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> InitTime: "
operator|+
operator|(
name|initEnd
operator|-
name|start
operator|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> SPARQL query:\n"
operator|+
name|sparqlQuery
argument_list|)
expr_stmt|;
name|InputStream
name|in
init|=
name|SparqlEndpointUtils
operator|.
name|sendSparqlRequest
argument_list|(
name|getQueryUri
argument_list|()
argument_list|,
name|sparqlQuery
argument_list|,
name|DEFAULT_RDF_CONTENT_TYPE
argument_list|)
decl_stmt|;
name|long
name|queryEnd
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> QueryTime: "
operator|+
operator|(
name|queryEnd
operator|-
name|initEnd
operator|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|in
operator|!=
literal|null
condition|)
block|{
name|Graph
name|graph
decl_stmt|;
name|Graph
name|rdfData
init|=
name|parser
operator|.
name|parse
argument_list|(
name|in
argument_list|,
name|DEFAULT_RDF_CONTENT_TYPE
argument_list|,
operator|new
name|IRI
argument_list|(
name|getBaseUri
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|rdfData
operator|instanceof
name|Graph
condition|)
block|{
name|graph
operator|=
operator|(
name|Graph
operator|)
name|rdfData
expr_stmt|;
block|}
else|else
block|{
name|graph
operator|=
operator|new
name|IndexedGraph
argument_list|(
name|rdfData
argument_list|)
expr_stmt|;
block|}
name|long
name|parseEnd
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> ParseTime: "
operator|+
operator|(
name|parseEnd
operator|-
name|queryEnd
operator|)
argument_list|)
expr_stmt|;
return|return
operator|new
name|RdfQueryResultList
argument_list|(
name|query
argument_list|,
name|graph
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

