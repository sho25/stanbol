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
name|servicesapi
package|;
end_package

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
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|ContentItemHelper
import|;
end_import

begin_comment
comment|/**  * Store and retrieve ContentItem instances.  *  * Incomplete CRUD for now, we don't need it for our  * initial use cases.  */
end_comment

begin_interface
specifier|public
interface|interface
name|Store
block|{
comment|/**      * Creates a {@link ContentItem} item based on supplied data,      * using an implementation that suits this store.      *<p>      * Calling this method creates an empty data transfer object in      * memory suitable for later saving using the {@link Store#put(ContentItem)} method.      * The Store state is unchanged by the call to the      * {@link #create(String, byte[], String)} method.      *      * @param id The value to use {@link ContentItem#getId}. If<code>null</code>      * is parsed as id, an id need to be computed based on the parsed content (      * e.g. calculating the stream digest (see also      * {@link ContentItemHelper#streamDigest(java.io.InputStream, java.io.OutputStream, String)})      * @param content the binary content      * @param contentType The Mime-Type of the binary data      * @return the {@link ContentItem} that was created      */
name|ContentItem
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
parameter_list|)
function_decl|;
comment|/** Store supplied {@link ContentItem} and return its id, which      *  is assigned by the store if not defined yet.      *      *  If the {@link ContentItem} already exists, it is overwritten.      */
name|String
name|put
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
function_decl|;
comment|/** Get a {@link ContentItem} by id, null if non-existing */
name|ContentItem
name|get
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/**      * Get the graph of triples of enhancements related to the content item from      * this store      */
name|MGraph
name|getEnhancementGraph
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

