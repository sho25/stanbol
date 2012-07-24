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
name|semanticindex
operator|.
name|store
package|;
end_package

begin_comment
comment|/**  * Interface to store and retrieve Items instances persistently.  */
end_comment

begin_interface
specifier|public
interface|interface
name|Store
parameter_list|<
name|Item
parameter_list|>
block|{
comment|/** 	 * Getter for the type of Items managed by this Store 	 * @return 	 */
name|Class
argument_list|<
name|Item
argument_list|>
name|getItemType
parameter_list|()
function_decl|;
comment|/** 	 * Removes an item with the given uri from the  store 	 * @param uri 	 * @return 	 * @throws StoreException 	 */
name|Item
name|remove
parameter_list|(
name|String
name|uri
parameter_list|)
throws|throws
name|StoreException
function_decl|;
comment|/**      * Stores supplied item and return its uri, which is assigned by the store if not defined      * yet.      *       * If the {@link ContentItem} already exists, it is overwritten.      * @param item the item to store      * @param the URI of the stored item      * @throws StoreException on any error while storing the item      */
name|String
name|put
parameter_list|(
name|Item
name|item
parameter_list|)
throws|throws
name|StoreException
function_decl|;
comment|/**       * Gets a Item by uri, null if non-existing       * @param uri the uri of the item      * @return the item or<code>null</code> if not present      * @throws StoreException on any error while retrieving the item      */
name|Item
name|get
parameter_list|(
name|String
name|uri
parameter_list|)
throws|throws
name|StoreException
function_decl|;
comment|/**      * Requests the next<code>batchSize</code> changes starting from<code>revision</code>. If there are no      * more revisions that a {@link ChangeSet} with an empty {@link ChangeSet#changed()} set. There can be      * more changes in the results than the given<code>batchSize</code> not to return a subset of changes      * regarding a specific revision. For instance, if the batch size is 5, given revision is 9 and there 15      * changes regarding revision 10. As a result, there will be 10 changed items in the returned change set.      *       * @param revision      *            Starting revision number for the returned {@link ChangeSet}      * @param batchSize      *            Maximum number of changes to be returned      * @return the {@link ChangeSet} with a maximum of<code>batchSize</code> changes      * @throws StoreException      *             On any error while accessing the store.      * @see ChangeSet      */
name|ChangeSet
argument_list|<
name|Item
argument_list|>
name|changes
parameter_list|(
name|long
name|revision
parameter_list|,
name|int
name|batchSize
parameter_list|)
throws|throws
name|StoreException
function_decl|;
block|}
end_interface

end_unit

