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
name|crawler
operator|.
name|cnn
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
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
name|ldpath
operator|.
name|SemanticIndexManager
import|;
end_import

begin_comment
comment|/**  * This is the interface to crawl CNN.  *   * @see<a href=" http://topics.cnn.com/topics/">CNN News Topics</a>  *   *   * @author cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|CNNImporter
block|{
comment|/**      *       * @param topic      *            The topic which will be crawled.      * @param maxNumber      *            Max number of news to be retrieved from CNN about the {@link topic}      * @param fullNews      *            If {@code true}, the topic will be crawled in detail to retrieve all information from CNN      *            about the {@link topic}. If {@code false}, only summary of the news will be crawled and      *            imported.      * @return A map which includes the URI of the related topic and the news content. If {@link fullNews} is      *         {@code true}, the news content is the full news; if not, it is the summary of the news.      */
name|Map
argument_list|<
name|URI
argument_list|,
name|String
argument_list|>
name|importCNNNews
parameter_list|(
name|String
name|topic
parameter_list|,
name|int
name|maxNumber
parameter_list|,
name|boolean
name|fullNews
parameter_list|)
function_decl|;
comment|/**      *       * @param topic      *            The topic which will be crawled.      * @param maxNumber      *            Max number of news to be retrieved from CNN about the {@link topic}      * @param fullNews      *            If {@code true}, the topic will be crawled in detail to retrieve all information from CNN      *            about the {@link topic}. If {@code false}, only summary of the news will be crawled and      *            imported.      *               * @param indexName      *            Name of the LDPath program (name of the Solr core/index) to be used while storing this      *            content item. LDPath programs can be managed through {@link SemanticIndexManagerResource} or      *            {@link SemanticIndexManager}      *                  * @return A map which includes the URI of the related topic and the news content. If {@link fullNews} is      *         {@code true}, the news content is the full news; if not, it is the summary of the news.      */
name|Map
argument_list|<
name|URI
argument_list|,
name|String
argument_list|>
name|importCNNNews
parameter_list|(
name|String
name|topic
parameter_list|,
name|int
name|maxNumber
parameter_list|,
name|boolean
name|fullNews
parameter_list|,
name|String
name|indexName
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

