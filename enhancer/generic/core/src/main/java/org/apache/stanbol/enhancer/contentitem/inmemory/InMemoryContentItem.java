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
name|contentitem
operator|.
name|inmemory
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
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
name|impl
operator|.
name|SimpleMGraph
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
name|indexedgraph
operator|.
name|IndexedMGraph
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
name|Blob
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
name|ContentItemFactory
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
name|ContentSource
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
name|impl
operator|.
name|ByteArraySource
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
name|impl
operator|.
name|ContentItemImpl
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
name|impl
operator|.
name|StringSource
import|;
end_import

begin_comment
comment|/**  * ContentItem implementation that holds a complete copy of the data in  * memory. Internally it uses {@link InMemoryBlob} to store the content and  * an {@link SimpleMGraph} for the metadata.  *<p>  * This implementation can be used independently of any store implementation and  * is suitable for stateless processing.  */
end_comment

begin_class
specifier|public
class|class
name|InMemoryContentItem
extends|extends
name|ContentItemImpl
block|{
comment|//Do not allow to create a ContentItem without a content
comment|//    public InMemoryContentItem(String id) {
comment|//        this(id, null, null, null);
comment|//    }
comment|/**      *       * @param content      * @param mimeType      * @deprecated use {@link InMemoryContentItemFactory#createContentItem(ContentItemFactory.ContentSource)}       * with a {@link ByteArraySource}      */
specifier|public
name|InMemoryContentItem
parameter_list|(
name|byte
index|[]
name|content
parameter_list|,
name|String
name|mimeType
parameter_list|)
block|{
name|this
argument_list|(
operator|(
name|UriRef
operator|)
literal|null
argument_list|,
operator|new
name|InMemoryBlob
argument_list|(
name|content
argument_list|,
name|mimeType
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      *       * @param id      * @param content      * @param mimeType      * @deprecated use {@link InMemoryContentItemFactory#createContentItem(UriRef, ContentSource)}      * with a {@link StringSource} instead.      */
specifier|public
name|InMemoryContentItem
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|content
parameter_list|,
name|String
name|mimeType
parameter_list|)
block|{
name|this
argument_list|(
name|id
argument_list|,
operator|new
name|InMemoryBlob
argument_list|(
name|content
argument_list|,
name|mimeType
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      *       * @param id      * @param content      * @param mimetype      * @deprecated use {@link InMemoryContentItemFactory#createContentItem(UriRef, ContentSource)}      * with a {@link ByteArraySource} instead.      */
specifier|public
name|InMemoryContentItem
parameter_list|(
name|String
name|id
parameter_list|,
name|byte
index|[]
name|content
parameter_list|,
name|String
name|mimetype
parameter_list|)
block|{
name|this
argument_list|(
name|id
argument_list|,
operator|new
name|InMemoryBlob
argument_list|(
name|content
argument_list|,
name|mimetype
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      *       * @param id      * @param content      * @param mimetype      * @deprecated use {@link InMemoryContentItemFactory#createContentItem(UriRef, ContentSource,MGraph)}      * with a {@link ByteArraySource} instead.      */
specifier|public
name|InMemoryContentItem
parameter_list|(
name|String
name|uriString
parameter_list|,
name|byte
index|[]
name|content
parameter_list|,
name|String
name|mimeType
parameter_list|,
name|MGraph
name|metadata
parameter_list|)
block|{
name|this
argument_list|(
name|uriString
operator|!=
literal|null
condition|?
operator|new
name|UriRef
argument_list|(
name|uriString
argument_list|)
else|:
literal|null
argument_list|,
operator|new
name|InMemoryBlob
argument_list|(
name|content
argument_list|,
name|mimeType
argument_list|)
argument_list|,
name|metadata
argument_list|)
expr_stmt|;
block|}
comment|/**      *       * @param id      * @param content      * @param mimetype      * @deprecated use {@link InMemoryContentItemFactory#createContentItem(UriRef, ContentSource,MGraph)}      * with a {@link StringSource} instead.      */
specifier|public
name|InMemoryContentItem
parameter_list|(
name|UriRef
name|uriRef
parameter_list|,
name|String
name|content
parameter_list|,
name|String
name|mimeType
parameter_list|)
block|{
name|this
argument_list|(
name|uriRef
argument_list|,
operator|new
name|InMemoryBlob
argument_list|(
name|content
argument_list|,
name|mimeType
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      *       * @param id      * @param content      * @param mimetype      * @deprecated use {@link InMemoryContentItemFactory#createContentItem(UriRef, ContentSource,MGraph)}      * with a {@link ByteArraySource} instead.      */
specifier|public
name|InMemoryContentItem
parameter_list|(
name|UriRef
name|uri
parameter_list|,
name|byte
index|[]
name|content
parameter_list|,
name|String
name|mimeType
parameter_list|,
name|MGraph
name|metadata
parameter_list|)
block|{
name|this
argument_list|(
name|uri
argument_list|,
operator|new
name|InMemoryBlob
argument_list|(
name|content
argument_list|,
name|mimeType
argument_list|)
argument_list|,
name|metadata
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|InMemoryContentItem
parameter_list|(
name|String
name|uriString
parameter_list|,
name|Blob
name|blob
parameter_list|,
name|MGraph
name|metadata
parameter_list|)
block|{
name|this
argument_list|(
name|uriString
operator|!=
literal|null
condition|?
operator|new
name|UriRef
argument_list|(
name|uriString
argument_list|)
else|:
literal|null
argument_list|,
name|blob
argument_list|,
name|metadata
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|InMemoryContentItem
parameter_list|(
name|UriRef
name|uri
parameter_list|,
name|Blob
name|blob
parameter_list|,
name|MGraph
name|metadata
parameter_list|)
block|{
name|super
argument_list|(
name|uri
operator|==
literal|null
condition|?
name|ContentItemHelper
operator|.
name|makeDefaultUrn
argument_list|(
name|blob
argument_list|)
else|:
name|uri
argument_list|,
name|blob
argument_list|,
name|metadata
operator|==
literal|null
condition|?
operator|new
name|IndexedMGraph
argument_list|()
else|:
name|metadata
argument_list|)
expr_stmt|;
block|}
comment|/**      *       * @param id      * @param content      * @param mimetype      * @deprecated use {@link InMemoryContentItemFactory#createContentItem(ContentSource)}      * with a {@link StringSource} instead.      */
specifier|protected
specifier|static
specifier|final
name|InMemoryContentItem
name|fromString
parameter_list|(
name|String
name|content
parameter_list|)
block|{
return|return
operator|new
name|InMemoryContentItem
argument_list|(
name|content
operator|.
name|getBytes
argument_list|()
argument_list|,
literal|"text/plain"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

