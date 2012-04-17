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
name|related
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
name|SearchResult
import|;
end_import

begin_comment
comment|/**  * This manager interface provides methods for searching related keywords for a query term. These methods  * returns the related keyword results encapsulated in {@link SearchResult} objects.<br>  *<br>  * Three types of sources are offered for related keywords which are<i>Wordnet</i>,<i>a specified  * ontology</i> and<i>referenced sites</i> managed by Stanbol Entityhub. Currently, this interface provides  * separate services for each type of source, but please note that in the future a more elegant approach may  * replace this one.  *   * @author anil.sinaci  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RelatedKeywordSearchManager
block|{
comment|/**      * Queries all the {@link RelatedKeywordSearch} instances with the given<code>keyword</code> and      * aggregates the results.      *       * @param keyword      *            Keyword for which related keywords will be obtained      * @return a {@link SearchResult} instance which encapsulates the related keyword {@link Map}. This map      *         would have a single key which is the given<code>keyword</code>. The value corresponding to the      *         key is another map. Its keys represent the different related keyword sources e.g Wordnet,      *         dbpedia, etc. Values of inner map contain {@link List} of {@link RelatedKeyword}s obtained from      *         that certain source.      * @throws SearchException      */
name|SearchResult
name|getRelatedKeywordsFromAllSources
parameter_list|(
name|String
name|keyword
parameter_list|)
throws|throws
name|SearchException
function_decl|;
comment|/**      * Queries all the {@link RelatedKeywordSearch} instances with the given<code>keyword</code> and      * aggregates the results. It takes URI of an ontology which is passed to related keyword searchers which      * process the ontology.      *       * @param keyword      *            Keyword for which related keywords will be obtained      * @param ontologyURI      *            URI of an ontology to be searched for related keywords      * @return a {@link SearchResult} instance which encapsulates the related keyword {@link Map}. This map      *         would have a single key which is the given<code>keyword</code>. The value corresponding to the      *         key is another map. Its keys represent the different related keyword sources e.g Wordnet,      *         dbpedia, etc. Values of inner map contain {@link List} of {@link RelatedKeyword}s obtained from      *         a certain source.      * @throws SearchException      */
name|SearchResult
name|getRelatedKeywordsFromAllSources
parameter_list|(
name|String
name|keyword
parameter_list|,
name|String
name|ontologyURI
parameter_list|)
throws|throws
name|SearchException
function_decl|;
comment|/**      * Searches related keywords from the ontology specified by<code>ontologyURI</code> for the given      *<code>keyword</code>.      *       * @param keyword      *            Keyword for which related keywords will be obtained      * @param ontologyURI      *            URI of an ontology to be searched for related keywords      * @return a {@link SearchResult} instance which encapsulates the related keyword {@link Map}. This map      *         would have a single key which is the given<code>keyword</code>. The value corresponding to the      *         key is another map. It also has a single key which indicates the "Ontology" source. Value      *         corresponding to this key contains {@link List} of {@link RelatedKeyword}s obtained from the      *         ontology.      * @throws SearchException      */
name|SearchResult
name|getRelatedKeywordsFromOntology
parameter_list|(
name|String
name|keyword
parameter_list|,
name|String
name|ontologyURI
parameter_list|)
throws|throws
name|SearchException
function_decl|;
comment|/**      * Searches related keywords in the the referenced sites managed by Stanbol Entityhub for the given      *<code>keyword</code>.      *       * @param keyword      *            Keyword for which related keywords will be obtained      * @return a {@link SearchResult} instance which encapsulates the related keyword {@link Map}. This map      *         would have a single key which is the given<code>keyword</code>. The value corresponding to the      *         key is another map. Its keys represent the different related keyword sources i.e referenced      *         sites. Values of inner map contain {@link List} of {@link RelatedKeyword}s obtained from a      *         certain source.      * @throws SearchException      */
name|SearchResult
name|getRelatedKeywordsFromReferencedSites
parameter_list|(
name|String
name|keyword
parameter_list|)
throws|throws
name|SearchException
function_decl|;
block|}
end_interface

end_unit

