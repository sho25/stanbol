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
name|demos
operator|.
name|crawler
operator|.
name|web
operator|.
name|resources
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
name|TEXT_HTML
import|;
end_import

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
name|ArrayList
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
name|Response
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
name|demos
operator|.
name|crawler
operator|.
name|cnn
operator|.
name|CNNCrawler
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
name|demos
operator|.
name|crawler
operator|.
name|web
operator|.
name|model
operator|.
name|TopicNews
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|view
operator|.
name|Viewable
import|;
end_import

begin_comment
comment|/**  * This is the web resource for CNN Crawler.  *   * @author cihan  *   */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/crawler/cnn/{index}"
argument_list|)
specifier|public
class|class
name|CNNCrawlerResource
extends|extends
name|BaseStanbolResource
block|{
specifier|private
name|CNNCrawler
name|cnnCrawler
decl_stmt|;
specifier|private
name|Object
name|templateData
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|indexName
decl_stmt|;
specifier|public
name|CNNCrawlerResource
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
block|{
name|this
operator|.
name|indexName
operator|=
name|indexName
expr_stmt|;
name|this
operator|.
name|cnnCrawler
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|CNNCrawler
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
block|}
specifier|private
name|TopicNews
name|importCNNNews
parameter_list|(
name|String
name|topic
parameter_list|,
name|Integer
name|max
parameter_list|,
name|Boolean
name|full
parameter_list|)
block|{
if|if
condition|(
name|topic
operator|==
literal|null
operator|||
name|topic
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|max
operator|==
literal|null
condition|)
block|{
name|max
operator|=
literal|10
expr_stmt|;
block|}
if|if
condition|(
name|full
operator|==
literal|null
condition|)
block|{
name|full
operator|=
literal|false
expr_stmt|;
block|}
name|Map
argument_list|<
name|URI
argument_list|,
name|String
argument_list|>
name|newsInfo
init|=
name|cnnCrawler
operator|.
name|importCNNNews
argument_list|(
name|topic
argument_list|,
name|max
argument_list|,
name|full
argument_list|,
name|indexName
argument_list|)
decl_stmt|;
name|TopicNews
name|tn
init|=
operator|new
name|TopicNews
argument_list|()
decl_stmt|;
name|tn
operator|.
name|setTopic
argument_list|(
name|topic
argument_list|)
expr_stmt|;
name|tn
operator|.
name|setUris
argument_list|(
operator|new
name|ArrayList
argument_list|<
name|URI
argument_list|>
argument_list|(
name|newsInfo
operator|.
name|keySet
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|tn
operator|.
name|setTitles
argument_list|(
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
name|newsInfo
operator|.
name|values
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|tn
return|;
block|}
comment|/** 	 * For HTML view only. 	 *  	 * @return Returns the HTML view for CNN News Crawler. 	 */
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|TEXT_HTML
argument_list|)
specifier|public
name|Response
name|importCNNNewsHTML
parameter_list|()
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
name|TEXT_HTML
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|/** 	 *  	 * @param topic 	 *            The topic which will be crawled. 	 * @param max 	 *            Maximum number of news to be retrieved from CNN about the 	 *            {@link topic} 	 * @param full 	 *            If {@code yes}, the topic will be crawled in detail to 	 *            retrieve all information from CNN about the {@link topic}. If 	 *            {@code no}, only summary of the news will be crawled and 	 *            imported. 	 * @return Returns the HTML view as the result of importing news from CNN. 	 */
annotation|@
name|POST
annotation|@
name|Produces
argument_list|(
name|TEXT_HTML
argument_list|)
specifier|public
name|Response
name|importCNNNewsHTMLPOST
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"topic"
argument_list|)
name|String
name|topic
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"max"
argument_list|)
name|Integer
name|max
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"full"
argument_list|)
name|Boolean
name|full
parameter_list|)
block|{
name|this
operator|.
name|templateData
operator|=
name|importCNNNews
argument_list|(
name|topic
argument_list|,
name|max
argument_list|,
name|full
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
specifier|public
name|Object
name|getTemplateData
parameter_list|()
block|{
return|return
name|templateData
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

