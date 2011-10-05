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
name|java
operator|.
name|util
operator|.
name|Map
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
name|engine
operator|.
name|SearchEngine
import|;
end_import

begin_comment
comment|/**  * This is the interface providing the search functionality in the Stanbol Contenthub system. Semantic search  * is performed on the documents residing in the Stanbol Contenthub.  *   * @author anil.sinaci  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|Search
block|{
comment|/**      * Performs the semantic search operation by taking a single keyword {@link String}. This function      * actually invokes {@link #search(keyword, null, null, null)}.      *       * @param keyword      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
name|keyword
parameter_list|)
function_decl|;
comment|/**      * Performs the semantic search operation by taking a keyword {@link String} and the URI of the ontology      * to be processed. This function actually invokes {@link #search(String keyword, String ontologyURI,      * null, null)}.      *       * @param keyword      *            Keyword string.      * @param ontologyURI      *            URI of the ontology.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
name|keyword
parameter_list|,
name|String
name|ontologyURI
parameter_list|)
function_decl|;
comment|/**      * Performs the semantic search operation by taking a single keyword {@link String} and a list of      * {@link SearchEngine}s to be executed in this search operation. This function actually invokes {@link      * #search(String keyword, null, List<String> allowedEngines, null)}.      *       * @param keyword      *            Keyword string.      * @param allowedEngines      *            Semantic search is performed by execution of a number of search engines. Each search engine      *            is identified by its full name, default {@link #toString()} method of each search engine      *            implementing {@link SearchEngine} interface provides the full name.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
name|keyword
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|allowedEngines
parameter_list|)
function_decl|;
comment|/**      *       * Performs the semantic search operation by taking a single keyword {@link String} and a {@link Map} of      * facets. This function actually invokes {@link #search(String keyword, null, null,      * Map<String,List<Object>> facets)}.      *       * @param keyword      *            Keyword string.      * @param facets      *            A map of facets (constraints) to be applied during the semantic search operation. Each facet      *            limits the search results according to the given list of values for that facet. For example,      *            if the facets map includes a mapping like "lang":"tr","en", search results will only contain      *            documents which include "lang" fields having "tr" or "en" values.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
name|keyword
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|facets
parameter_list|)
function_decl|;
comment|/**      * Performs the semantic search operation by taking a single keyword, the URI of the ontology to be      * processed and a list of {@link SearchEngine}s to be executed in this search operation. This function      * actually invokes {@link #search(String keyword, String ontologyURI, List<String> allowedEngines, null)}      * .      *       * @param keyword      *            Keyword string.      * @param ontologyURI      *            URI of the ontology.      * @param allowedEngines      *            Semantic search is performed by execution of a number of search engines. Each search engine      *            is identified by its full name, default {@link #toString()} method of each search engine      *            implementing {@link SearchEngine} interface provides the full name.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
name|keyword
parameter_list|,
name|String
name|ontologyURI
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|allowedEngines
parameter_list|)
function_decl|;
comment|/**      * Performs the semantic search operation by taking a single keyword, the URI of the ontology to be      * processed and a {@link Map} of facets. This function actually invokes {@link #search(String keyword,      * String ontologyURI, null, Map<String,List<Object>> facets)}.      *       * @param keyword      *            Keyword string.      * @param ontologyURI      *            URI of the ontology.      * @param facets      *            A map of facets (constraints) to be applied during the semantic search operation. Each facet      *            limits the search results according to the given list of values for that facet. For example,      *            if the facets map includes a mapping like "lang":"tr","en", search results will only contain      *            documents which include "lang" fields having "tr" or "en" values.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
name|keyword
parameter_list|,
name|String
name|ontologyURI
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|facets
parameter_list|)
function_decl|;
comment|/**      * Performs the semantic search operation by taking a single keyword, a list of {@link SearchEngine}s to      * be executed in this search operation and a {@link Map} of facets. This function actually invokes      * {@link #search(String keyword, null, List<String> allowedEngines, Map<String,List<Object>> facets)}.      *       * @param keyword      *            Keyword string.      * @param allowedEngines      *            Semantic search is performed by execution of a number of search engines. Each search engine      *            is identified by its full name, default {@link #toString()} method of each search engine      *            implementing {@link SearchEngine} interface provides the full name.      * @param facets      *            A map of facets (constraints) to be applied during the semantic search operation. Each facet      *            limits the search results according to the given list of values for that facet. For example,      *            if the facets map includes a mapping like "lang":"tr","en", search results will only contain      *            documents which include "lang" fields having "tr" or "en" values.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
name|keyword
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|allowedEngines
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|facets
parameter_list|)
function_decl|;
comment|/**      * Performs the semantic search operation by taking a single keyword, the URI of the ontology to be      * processed, a list of {@link SearchEngine}s to be executed in this search operation and a {@link Map} of      * facets. This function actually invokes {@link #search(String[] keywords, String ontologyURI,      * List<String> allowedEngines allowedEngines, Map<String,List<Object>> facets)} after inserting the      * single keyword into a String array whose size is 1.      *       * @param keyword      *            Keyword string.      * @param ontologyURI      *            URI of the ontology.      * @param allowedEngines      *            Semantic search is performed by execution of a number of search engines. Each search engine      *            is identified by its full name, default {@link #toString()} method of each search engine      *            implementing {@link SearchEngine} interface provides the full name.      * @param facets      *            A map of facets (constraints) to be applied during the semantic search operation. Each facet      *            limits the search results according to the given list of values for that facet. For example,      *            if the facets map includes a mapping like "lang":"tr","en", search results will only contain      *            documents which include "lang" fields having "tr" or "en" values.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
name|keyword
parameter_list|,
name|String
name|ontologyURI
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|allowedEngines
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|facets
parameter_list|)
function_decl|;
comment|/**      * Performs the semantic search operation by taking a {@link String[]} array of keywords. This function      * actually invokes {@link #search(String[] keywords, null, null, null)}.      *       * @param keywords      *            Array of keywords.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
index|[]
name|keywords
parameter_list|)
function_decl|;
comment|/**      * Performs the semantic search operation by taking a {@link String[]} array of keywords and the URI of      * the ontology to be processed. This function actually invokes {@link #search(String[] keywords, String      * ontologyURI, null, null)}.      *       * @param keywords      *            Array of keywords.      * @param ontologyURI      *            URI of the ontology.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
index|[]
name|keywords
parameter_list|,
name|String
name|ontologyURI
parameter_list|)
function_decl|;
comment|/**      * Performs the semantic search operation by taking a {@link String[]} array of keywords and a list of      * {@link SearchEngine}s to be executed in this search operation. This function actually invokes {@link      * #search(String[] keywords, null, List<String> allowedEngines, null)}.      *       * @param keywords      *            Array of keywords.      * @param allowedEngines      *            Semantic search is performed by execution of a number of search engines. Each search engine      *            is identified by its full name, default {@link #toString()} method of each search engine      *            implementing {@link SearchEngine} interface provides the full name.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
index|[]
name|keywords
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|allowedEngines
parameter_list|)
function_decl|;
comment|/**      *       * Performs the semantic search operation by taking a {@link String[]} array of keywords and a {@link Map}      * of facets. This function actually invokes {@link #search(String[] keywords, null, null,      * Map<String,List<Object>> facets)}.      *       * @param keywords      *            Array of keywords.      * @param facets      *            A map of facets (constraints) to be applied during the semantic search operation. Each facet      *            limits the search results according to the given list of values for that facet. For example,      *            if the facets map includes a mapping like "lang":"tr","en", search results will only contain      *            documents which include "lang" fields having "tr" or "en" values.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
index|[]
name|keywords
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|facets
parameter_list|)
function_decl|;
comment|/**      * Performs the semantic search operation by taking a {@link String[]} array of keywords, the URI of the      * ontology to be processed and a list of {@link SearchEngine}s to be executed in this search operation.      * This function actually invokes {@link #search(String[] keywords, String ontologyURI, List<String>      * allowedEngines, null)}.      *       * @param keywords      *            Array of keywords.      * @param ontologyURI      *            URI of the ontology.      * @param allowedEngines      *            Semantic search is performed by execution of a number of search engines. Each search engine      *            is identified by its full name, default {@link #toString()} method of each search engine      *            implementing {@link SearchEngine} interface provides the full name.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
index|[]
name|keywords
parameter_list|,
name|String
name|ontologyURI
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|allowedEngines
parameter_list|)
function_decl|;
comment|/**      * Performs the semantic search operation by taking a {@link String[]} array of keywords, the URI of the      * ontology to be processed and a {@link Map} of facets. This function actually invokes {@link      * #search(String[] keywords, String ontologyURI, null, Map<String,List<Object>> facets)}.      *       * @param keywords      *            Array of keywords.      * @param ontologyURI      *            URI of the ontology.      * @param facets      *            A map of facets (constraints) to be applied during the semantic search operation. Each facet      *            limits the search results according to the given list of values for that facet. For example,      *            if the facets map includes a mapping like "lang":"tr","en", search results will only contain      *            documents which include "lang" fields having "tr" or "en" values.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
index|[]
name|keywords
parameter_list|,
name|String
name|ontologyURI
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|facets
parameter_list|)
function_decl|;
comment|/**      * Performs the semantic search operation by taking a {@link String[]} array of keywords, a list of      * {@link SearchEngine}s to be executed in this search operation and a {@link Map} of facets. This      * function actually invokes {@link #search(String[] keywords, null, List<String> allowedEngines,      * Map<String,List<Object>> facets)}.      *       * @param keywords      *            Array of keywords.      * @param allowedEngines      *            Semantic search is performed by execution of a number of search engines. Each search engine      *            is identified by its full name, default {@link #toString()} method of each search engine      *            implementing {@link SearchEngine} interface provides the full name.      * @param facets      *            A map of facets (constraints) to be applied during the semantic search operation. Each facet      *            limits the search results according to the given list of values for that facet. For example,      *            if the facets map includes a mapping like "lang":"tr","en", search results will only contain      *            documents which include "lang" fields having "tr" or "en" values.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
index|[]
name|keywords
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|allowedEngines
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|facets
parameter_list|)
function_decl|;
comment|/**      * Performs the semantic search operation by taking a {@link String[]} array of keywords, the URI of the      * ontology to be processed, a list of {@link SearchEngine}s to be executed in this search operation and a      * {@link Map} of facets.      *       * @param keywords      *            Array of keywords.      * @param ontologyURI      *            URI of the ontology.      * @param allowedEngines      *            Semantic search is performed by execution of a number of search engines. Each search engine      *            is identified by its full name, default {@link #toString()} method of each search engine      *            implementing {@link SearchEngine} interface provides the full name.      * @param facets      *            A map of facets (constraints) to be applied during the semantic search operation. Each facet      *            limits the search results according to the given list of values for that facet. For example,      *            if the facets map includes a mapping like "lang":"tr","en", search results will only contain      *            documents which include "lang" fields having "tr" or "en" values.      * @return {@link SearchResult}      */
name|SearchResult
name|search
parameter_list|(
name|String
index|[]
name|keywords
parameter_list|,
name|String
name|ontologyURI
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|allowedEngines
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|facets
parameter_list|)
function_decl|;
block|}
end_interface

end_unit
