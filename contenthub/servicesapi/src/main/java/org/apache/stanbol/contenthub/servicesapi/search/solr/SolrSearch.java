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
name|solr
package|;
end_package

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
name|response
operator|.
name|QueryResponse
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

begin_comment
comment|/**  * Apache Solr based search interface of Stanbol Contenthub. It makes use of SolrJ API in the provided  * services such that it is possible to provide queries passed in {@link SolrParams} and response are returned  * in the form of {@link QueryResponse}s. This interface also allows querying different Solr cores which are  * created based on the LDPath programs submitted through the {@link SemanticIndexManager}.  *   * @author anil.sinaci  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SolrSearch
block|{
comment|/**      * Queries the default Solr core of Contenthub with the given<code>queryTerm</code>.      *       * @param queryTerm      *            Query term to be searched      * @return the {@link QueryResponse} as is obtained from Solr.      * @throws SearchException      */
name|QueryResponse
name|search
parameter_list|(
name|String
name|queryTerm
parameter_list|)
throws|throws
name|SearchException
function_decl|;
comment|/**      * Queries the Solr core corresponding to the given<code>ldProgramName</code> of Contenthub with the      * given<code>queryTerm</code>.      *       * @param queryTerm      *            Query term to be searched      * @param indexName      *            LDPath program name (Solr core/index name) to obtain the corresponding Solr core to be      *            searched      * @return the {@link QueryResponse} as is obtained from Solr.      * @throws SearchException      */
name|QueryResponse
name|search
parameter_list|(
name|String
name|queryTerm
parameter_list|,
name|String
name|indexName
parameter_list|)
throws|throws
name|SearchException
function_decl|;
comment|/**      * Executes the given<code>solrQuery</code> on the default Solr core of Contenthub.      *       * @param solrQuery      *            {@link SolrParams} to be executed      * @return the {@link QueryResponse} as is obtained from Solr.      * @throws SearchException      */
name|QueryResponse
name|search
parameter_list|(
name|SolrParams
name|solrQuery
parameter_list|)
throws|throws
name|SearchException
function_decl|;
comment|/**      * Executes the given<code>solrQuery</code> on the Solr core corresponding to the given      *<code>ldProgramName</code> of Contenthub.      *       * @param solrQuery      *            {@link SolrParams} to be executed      * @param indexName      *            LDPath program name (Solr core/index name) to obtain the corresponding Solr core to be      *            searched      * @return the {@link QueryResponse} as is obtained from Solr.      * @throws SearchException      */
name|QueryResponse
name|search
parameter_list|(
name|SolrParams
name|solrQuery
parameter_list|,
name|String
name|indexName
parameter_list|)
throws|throws
name|SearchException
function_decl|;
block|}
end_interface

end_unit

