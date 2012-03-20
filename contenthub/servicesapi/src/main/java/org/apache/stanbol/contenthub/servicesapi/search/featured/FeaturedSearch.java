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
name|servicesapi
operator|.
name|search
operator|.
name|featured
package|;
end_package

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
name|related
operator|.
name|RelatedKeywordSearch
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
name|SolrSearch
import|;
end_import

begin_comment
comment|/**  * This interface provides search functionalities over the submitted content items to the Contenthub. It  * mainly uses the two main search components of Contenthub which are {@link SolrSearch} and  * {@link RelatedKeywordSearch}. The main aim of the interface to provide user with a unified search  * functionality which returns results containing fetched documents from underlying Solr cores together with  * the related keywords that are obtained from various types of sources.  *   * @author anil.sinaci  * @see SolrSearch  * @see RelatedKeywordSearch  */
end_comment

begin_interface
specifier|public
interface|interface
name|FeaturedSearch
block|{
comment|/**      * This method returns a {@link SearchResult} as a unified search response. The response contains content      * items retrieved from the default index of Contenthub for the given<code>queryTerm</code>. It also      * consists of related keywords which are obtained from the available {@link RelatedKeywordSearch}      * instances. To obtain related keywords, the given query term is tokenized with      * {@link #tokenizeEntities(String)}. And then, related keyword searchers are queried for all the query      * tokens. Furthermore, the {@link SearchResult} includes Solr facets that are obtained for the obtained      * content items.      *       * @param queryTerm      *            Query term for which the unified response will be obtained      * @return {@link SearchResult} for the given query term. For details of the response see      *         {@link SearchResult}.      * @throws SearchException      */
name|SearchResult
name|search
parameter_list|(
name|String
name|queryTerm
parameter_list|)
throws|throws
name|SearchException
function_decl|;
comment|/**      * This method returns a {@link SearchResult} as a unified search response. The response contains content      * items retrieved from the index, which is accessed using the given<code>indexName</code>, of Contenthub      * for the given<code>queryTerm</code>. This name corresponds to a Solr Core name within Contenthub. It      * also consists of related keywords that are obtained from the available {@link RelatedKeywordSearch}      * instances. This method also takes an ontology URI. Using the URI, actual ontology is retrieved and it      * is used as related keyword source. To obtain related keywords, the given query term is tokenized with      * {@link #tokenizeEntities(String)}. And then, related keyword searchers are queried for all the query      * tokens. Furthermore, the {@link SearchResult} includes Solr facets that are obtained for the obtained      * content items.      *       * @param queryTerm      *            Query term for which the unified response will be obtained      * @param ontologyURI      *            URI of an ontology in which related keywords will be searched      * @param indexName      *            LDPath program name (name of the Solr core/index) which is used to obtained the      *            corresponding Solr core which will be searched for the given query term      * @return {@link SearchResult} for the given query term. For details of the response see      *         {@link SearchResult}.      * @throws SearchException      */
name|SearchResult
name|search
parameter_list|(
name|String
name|queryTerm
parameter_list|,
name|String
name|ontologyURI
parameter_list|,
name|String
name|indexName
parameter_list|)
throws|throws
name|SearchException
function_decl|;
comment|/**      * This methods returns a {@link SearchResult} as a unified search response. The response contains content      * items retrieved from the default index of Contenthub after executing the given<code>solrQuery</code>.      * It also consists of related keywords that are obtained from the available {@link RelatedKeywordSearch}      * instances. To obtain related keywords, first the meaningful query terms are extracted from the Solr      * query and then they are tokenized with {@link #tokenizeEntities(String)}. And then, related keyword      * searchers are queried for all the query tokens. Furthermore, the {@link SearchResult} includes Solr      * facets that are obtained for the obtained content items.      *       * @param solrQuery      *            for which the search results will be obtained      * @return a unified response in a {@link SearchResult} containing actual content items, related keywords      *         and facets for the obtained content items.      * @throws SearchException      */
name|SearchResult
name|search
parameter_list|(
name|SolrParams
name|solrQuery
parameter_list|)
throws|throws
name|SearchException
function_decl|;
comment|/**      * This methods returns a {@link SearchResult} as a unified search response. The response contains content      * items retrieved from the index, which is accessed using the given<code>indexName</code>, of      * Contenthub for the given<code>queryTerm</code>. This name corresponds to a Solr Core name within      * Contenthub. It also consists of related keywords that are obtained from the available      * {@link RelatedKeywordSearch} instances. This method also takes an ontology URI. Using the URI, actual      * ontology is obtained and it is used as related keyword source. To obtain related keywords, first the      * meaningful query terms are extracted from the Solr query and then they are tokenized with      * {@link #tokenizeEntities(String)}. And then, related keyword searchers are queried for all the query      * tokens. Furthermore, the {@link SearchResult} includes Solr facets that are obtained for the obtained      * content items.      *       * @param solrQuery      *            for which the search results will be obtained      * @return a unified response in a {@link SearchResult} containing actual content items, related keywords      *         and facets for the obtained content items.      * @throws SearchException      */
name|SearchResult
name|search
parameter_list|(
name|SolrParams
name|solrQuery
parameter_list|,
name|String
name|ontologyURI
parameter_list|,
name|String
name|indexName
parameter_list|)
throws|throws
name|SearchException
function_decl|;
comment|/**      * This method obtains the available field names of the default index of Contenthub.      *       * @return {@link List} of field names related index      * @throws SearchException      */
name|List
argument_list|<
name|FacetResult
argument_list|>
name|getAllFacets
parameter_list|()
throws|throws
name|SearchException
function_decl|;
comment|/**      * This method obtains the available field names of the index, corresponding to the given      *<code>indexName</code> of Contenthub. This name corresponds to a Solr Core name within Contenthub.      *       * @param indexName      *            Name of the index for which the field names will be obtained.      * @return {@link List} of field names related index      * @throws SearchException      */
name|List
argument_list|<
name|FacetResult
argument_list|>
name|getAllFacets
parameter_list|(
name|String
name|indexName
parameter_list|)
throws|throws
name|SearchException
function_decl|;
comment|/**      * This method tokenizes the given query term with the help of Stanbol Enhancer. The query term is fed to      * Enhancer and labels of obtained named entities are search in the original query term and if any found      * they are treated as a single query term.      *       * @param queryTerm      *            To be tokenized      * @return {@link List} of query tokens of the given<code>queryTerm</code>.      */
name|List
argument_list|<
name|String
argument_list|>
name|tokenizeEntities
parameter_list|(
name|String
name|queryTerm
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

