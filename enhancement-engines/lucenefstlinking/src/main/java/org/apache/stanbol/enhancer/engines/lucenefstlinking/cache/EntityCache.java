begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|engines
operator|.
name|lucenefstlinking
operator|.
name|cache
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|document
operator|.
name|Document
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
name|SolrDocument
import|;
end_import

begin_comment
comment|/**  * A Cache for {@link SolrDocument}s holding Entity information required for  * entity linking. This cache is intended to avoid disc access for loading  * entity data of entities detected by the FST tagging in the parsed document.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntityCache
block|{
comment|/**      * if the current version of the index does not equals this version      * the Cache need to be renewed.      * @return the version this cache is build upon      */
name|Object
name|getVersion
parameter_list|()
function_decl|;
comment|/**      * Getter for the Document based on the Lucene Document ID      * @param docId the Lucene document ID (the unique key)      * @return the Document or<code>null</code> if not in the cache      */
name|Document
name|get
parameter_list|(
name|Integer
name|docId
parameter_list|)
function_decl|;
comment|/**      * Caches the document for the parsed Lucene document id      * @param docId the Lucene document id      * @param doc the Document      */
name|void
name|cache
parameter_list|(
name|Integer
name|docId
parameter_list|,
name|Document
name|doc
parameter_list|)
function_decl|;
comment|/**      * The size of the cache of<code>-1</code> if not available      * @return the size or<code>-1</code> if not known      */
name|int
name|size
parameter_list|()
function_decl|;
comment|/**      * The statistics for this Cache      * @return       */
name|String
name|printStatistics
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

