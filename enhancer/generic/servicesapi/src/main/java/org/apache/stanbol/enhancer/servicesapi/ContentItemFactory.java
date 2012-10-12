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
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Triple
import|;
end_import

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
name|UriRef
import|;
end_import

begin_comment
comment|/**  * OSGI service to be used to create {@link ContentItem}s and Blobs.  *   * @since 0.9.1-incubating  */
end_comment

begin_interface
specifier|public
interface|interface
name|ContentItemFactory
block|{
comment|/**      * Creates a new ContentItem for the passed {@link ContentSource} and      * generates as unique ID from the passed content.      * Implementors might want to use      * {@link ContentItemHelper#streamDigest(InputStream, java.io.OutputStream, String)      * for generating an ID while reading the data from the ContentSource.      * @param source The content source      * @return the {@link ContentItem} with a generated id and the passed      * content as content-part of type {@link Blob} at index<code>0</code>      * @throws IllegalArgumentException if<code>null</code> is passed as content      * source or the passed content source is already consumed.      * @throws IOException on any error while reading the content from the       * content source.      */
name|ContentItem
name|createContentItem
parameter_list|(
name|ContentSource
name|source
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Creates a new ContentItem for the passed content source and an      * ID relative to the passed prefix.      * @param prefix the URI prefix used generate the URI of the content item.      * Note the only a generated ID will be added to the passed prefix. So passed      * values should typically end with an separator char (e.g. '/', '#', ':').      * Implementors might want to use      * {@link org.apache.stanbol.enhancer.servicesapi.helper.ContentItemHelper#streamDigest(InputStream, java.io.OutputStream, String)      * for generating an ID while reading the data from the ContentSource.      * @param source The content source      * @return the {@link ContentItem} with a generated id and the passed      * content as content-part of type {@link Blob} at index<code>0</code>      * @throws IllegalArgumentException if<code>null</code> is passed as content      * source, the content source is already consumed or the passed prefix is      *<code>null</code>      * @throws IOException on any error while reading the content from the       * content source.      */
name|ContentItem
name|createContentItem
parameter_list|(
name|String
name|prefix
parameter_list|,
name|ContentSource
name|source
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Creates a new ContentItem for the passed id and content source.      * @param id the id for the ContentItem or<code>null</code> to generate an id.      * If<code>null</code> is passed as ID, implementors might want to use      * {@link ContentItemHelper#streamDigest(InputStream, java.io.OutputStream, String)      * for generating an ID while reading the data from the ContentSource.      * @param source The content source      * @return the {@link ContentItem} with a passed/generated id and the passed      * content as content-part of type {@link Blob} at index<code>0</code>      * @throws IllegalArgumentException if<code>null</code> is passed as content      * source, the content source is already consumed or the      * passed id is not<code>null</code> but empty.      * @throws IOException on any error while reading the content from the       * content source.      */
name|ContentItem
name|createContentItem
parameter_list|(
name|UriRef
name|id
parameter_list|,
name|ContentSource
name|source
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Creates a new ContentItem for the passed id and content source.      * @param prefix the URI prefix used generate the URI of the content item.      * Note the only a generated ID will be added to the passed prefix. So passed      * values should typically end with an separator char (e.g. '/', '#', ':').      * Implementors might want to use      * {@link ContentItemHelper#streamDigest(InputStream, java.io.OutputStream, String)      * for generating an ID while reading the data from the ContentSource      * @param source The content source      * @param metadata an {@link MGraph} with the metadata or<code>null</code>      * if none. Implementation are free to use the passed instance or to generate       * a new one. However they MUST ensure that all {@link Triple}s contained by       * the passed graph are also added to the {@link ContentItem#getMetadata()       * metadata} of the returned ContentItem.      * @return the {@link ContentItem} with a passed/generated id and the passed      * content as content-part of type {@link Blob} at index<code>0</code>      * @throws IllegalArgumentException if<code>null</code> is passed as content      * source, the content source is already consumed or the      * passed prefix is<code>null</code>.      * @throws IOException on any error while reading the content from the       * content source.      */
name|ContentItem
name|createContentItem
parameter_list|(
name|String
name|prefix
parameter_list|,
name|ContentSource
name|source
parameter_list|,
name|MGraph
name|metadata
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Creates a new ContentItem for the passed id and content source.      * @param id the id for the ContentItem or<code>null</code> to generate an id.      * If<code>null</code> is passed as ID, implementors might want to use      * {@link ContentItemHelper#streamDigest(InputStream, java.io.OutputStream, String)      * for generating an ID while reading the data from the ContentSource.      * @param source The content source      * @param metadata an {@link MGraph} with the metadata or<code>null</code>      * if none. Implementation are free to use the passed instance or to generate       * a new one. However they MUST ensure that all {@link Triple}s contained by       * the passed graph are also added to the {@link ContentItem#getMetadata()       * metadata} of the returned ContentItem.      * @return the {@link ContentItem} with a passed/generated id and the passed      * content as content-part of type {@link Blob} at index<code>0</code>      * @throws IllegalArgumentException if<code>null</code> is passed as content      * source, the content source is already consumed or the      * passed id is not<code>null</code> but empty.      * @throws IOException on any error while reading the content from the       * content source.      */
name|ContentItem
name|createContentItem
parameter_list|(
name|UriRef
name|id
parameter_list|,
name|ContentSource
name|source
parameter_list|,
name|MGraph
name|metadata
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Creates a new ContentItem for the passed {@link ContentReference}. The      * {@link ContentReference#getReference()} is used as ID for the content      * item. Implementations might choose to {@link ContentReference#dereference()      * dereference}      * the reference at creation if needed.      * @param reference the reference to the content      * @return the {@link ContentItem} with the {@link ContentReference#getReference()}      * as ID.      * @throws IOException if the implementation {@link ContentReference#dereference()      * dereferences} the {@link ContentReference} during creation and this action      * fails.      * @throws IllegalArgumentException if the passed {@link ContentReference}      * is<code>null</code>.      */
name|ContentItem
name|createContentItem
parameter_list|(
name|ContentReference
name|reference
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Creates a new ContentItem for the passed {@link ContentReference}. The      * {@link ContentReference#getReference()} is used as ID for the content      * item. Implementations might choose to {@link ContentReference#dereference()      * dereference}      * the reference at creation if needed.      * @param reference the reference to the content      * @param metadata an {@link MGraph} with the metadata or<code>null</code>      * if none. Implementation are free to use the passed instance or to generate       * a new one. However they MUST ensure that all {@link Triple}s contained by       * the passed graph are also added to the {@link ContentItem#getMetadata()       * metadata} of the returned ContentItem.      * @return the {@link ContentItem} with the {@link ContentReference#getReference()}      * as ID.      * @throws IOException if the implementation {@link ContentReference#dereference()      * dereferences} the {@link ContentReference} during creation and this action      * fails.      * @throws IllegalArgumentException if the passed {@link ContentReference}      * is<code>null</code>.      */
name|ContentItem
name|createContentItem
parameter_list|(
name|ContentReference
name|reference
parameter_list|,
name|MGraph
name|metadata
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Creates a new Blob based on the passed {@link ContentSource}      * @param source The source       * @return the Blob      * @throws IllegalArgumentException of the passed source is<code>null</code>      * @throws IllegalStateException if the passed source is already consumed      * @throws IOException on any error while reading the content from the source.      */
name|Blob
name|createBlob
parameter_list|(
name|ContentSource
name|source
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Creates a new Blob based on the passed {@link ContentReference}. If the      * content reference is {@link ContentReference#dereference() dereferenced}      * during the construction or at an later point in time is implementation      * dependent.      * @param reference The reference to the content       * @return the Blob      * @throws IllegalArgumentException of the passed reference is<code>null</code>      * @throws IOException on any error while dereferencing the passed reference.      */
name|Blob
name|createBlob
parameter_list|(
name|ContentReference
name|reference
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Creates a {@link ContentSink} that allows to "stream" content to an      * newly created {@link Blob}. This is intended to be used by      * {@link EnhancementEngine}s that transform the parsed content and want      * to store "stream" the transformation result to a new {@link Blob} that      * can be later added to the {@link ContentItem}.<p>      *<b>IMPORTANT NOTE:</b> Do not parse the {@link Blob} of a {@link ContentSink}      * to any other components until all the data are written to the       * {@link OutputStream} (see {@link ContentSink} for details).      * @param mediaType the mediaType for the created blob. "application/octet-stream" is      * used as default if<code>null</code>. For textual content the charset should be      * added as parameter (e.g. "text/plain; charset=UTF-8"). If no charset is      * present Stanbol will assume that the text is encoded using<code>UTF-8</code>.      * @return The {@link ContentSink} providing both the {@link Blob} and an      * {@link OutputStream} allowing to write the data for this {@link Blob}.      * @throws IOException On any error while creating the Blob.      */
name|ContentSink
name|createContentSink
parameter_list|(
name|String
name|mediaType
parameter_list|)
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

