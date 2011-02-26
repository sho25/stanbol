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
name|linkedData
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
name|rdf
operator|.
name|core
operator|.
name|MGraph
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
name|impl
operator|.
name|SimpleMGraph
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
name|ConfigurationPolicy
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
name|clerezza
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
name|clerezza
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
name|query
operator|.
name|clerezza
operator|.
name|SparqlQueryUtils
operator|.
name|EndpointTypeEnum
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
literal|"org.apache.stanbol.entityhub.searcher.VirtuosoSearcher"
argument_list|,
name|factory
operator|=
literal|"org.apache.stanbol.entityhub.searcher.VirtuosoSearcherFactory"
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|REQUIRE
argument_list|,
comment|//the queryUri and the SPARQL Endpoint are required
name|specVersion
operator|=
literal|"1.1"
argument_list|)
specifier|public
class|class
name|VirtuosoSearcher
extends|extends
name|AbstractEntitySearcher
implements|implements
name|EntitySearcher
block|{
annotation|@
name|Reference
specifier|protected
name|Parser
name|parser
decl_stmt|;
specifier|public
name|VirtuosoSearcher
parameter_list|()
block|{
name|super
argument_list|(
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|VirtuosoSearcher
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
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
name|query
operator|.
name|setEndpointType
argument_list|(
name|EndpointTypeEnum
operator|.
name|Virtuoso
argument_list|)
expr_stmt|;
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
name|info
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
name|info
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
name|SparqlSearcher
operator|.
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
name|info
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
name|MGraph
name|graph
decl_stmt|;
name|TripleCollection
name|rdfData
init|=
name|parser
operator|.
name|parse
argument_list|(
name|in
argument_list|,
name|SparqlSearcher
operator|.
name|DEFAULT_RDF_CONTENT_TYPE
argument_list|)
decl_stmt|;
if|if
condition|(
name|rdfData
operator|instanceof
name|MGraph
condition|)
block|{
name|graph
operator|=
operator|(
name|MGraph
operator|)
name|rdfData
expr_stmt|;
block|}
else|else
block|{
name|graph
operator|=
operator|new
name|SimpleMGraph
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
name|info
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
annotation|@
name|Override
specifier|public
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
name|query
operator|.
name|setEndpointType
argument_list|(
name|EndpointTypeEnum
operator|.
name|Virtuoso
argument_list|)
expr_stmt|;
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
name|SparqlSearcher
operator|.
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
name|SparqlSearcher
operator|.
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
block|}
end_class

end_unit

