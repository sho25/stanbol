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
name|store
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentItem
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|EnhancementJobManager
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|Store
import|;
end_import

begin_comment
comment|/**  * {@link Store} interface for Solr.  *   * @author anil.sinaci  * @author meric.taze  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SolrStore
extends|extends
name|Store
block|{
comment|/**      * Creates a {@link SolrContentItem} with the given parameters. Created {@link SolrContentItem} is not      * persisted, this function just creates the object.      *       * @param id      *            The unique ID for the item. If it is null, {@link SolrStore} should assign a unique ID for      *            this item.      * @param content      *            The content itself.      * @param contentType      *            The mimeType of the content.      * @param constraints      *            The facets in<code>key:[value1,value2]</code> pairs.      * @return Created {@link SolrContentItem}.      */
name|SolrContentItem
name|create
parameter_list|(
name|String
name|id
parameter_list|,
name|byte
index|[]
name|content
parameter_list|,
name|String
name|contentType
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
name|constraints
parameter_list|)
function_decl|;
comment|/**      * Sends the {@link SolrContentItem} to the {@link EnhancementJobManager} to enhance the content.      * Afterwards saves the item to Solr.      *       * @param sci      *            The {@link SolrContentItem} to be enhanced and saved.      * @return The unique ID of the {@link SolrContentItem}.      */
name|String
name|enhanceAndPut
parameter_list|(
name|SolrContentItem
name|sci
parameter_list|)
function_decl|;
comment|/**      * Deletes the {@link ContentItem} from the {@link SolrStore}.      *       * @param id      *            The ID of the item to be deleted.      */
name|void
name|deleteById
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/**      * Deletes the {@link ContentItem}s from the {@link SolrStore}.      *       * @param id      *            The list of IDs of the items to be deleted.      */
name|void
name|deleteById
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|id
parameter_list|)
function_decl|;
block|}
end_interface

end_unit
