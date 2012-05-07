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
operator|.
name|impl
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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ConfigurationPolicy
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|ContentReference
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
name|ContentSink
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

begin_comment
comment|/**  * Abstract implementation of the {@link ContentItemFactory} that requires only  * the three abstract methods<ul>  *<li> {@link #createBlob(ContentSource)}  *<li> {@link #createContentItem(String, Blob, MGraph)}  *<li> {@link #createContentItem(UriRef, Blob, MGraph)}  *</ul> to be overridden.<p>  * Implementers should NOTE that {@link #createBlob(ContentSource)} will be  * called to create the main {@link Blob} instance for a contentItem before  * the {@link ContentItem} itself is instantiated. If this is a problem, than  * this abstract super class can not be used.  *   * @author Rupert Westenthaler  * @since 0.9.1-incubating  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|componentAbstract
operator|=
literal|true
argument_list|,
name|immediate
operator|=
literal|true
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|OPTIONAL
argument_list|)
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|ContentItemFactory
operator|.
name|class
argument_list|)
specifier|public
specifier|abstract
class|class
name|AbstractContentItemFactory
implements|implements
name|ContentItemFactory
block|{
comment|/**      * State if {@link LazyDereferencingBlob}s are used for {@link Blob}s      * created for {@link ContentReference}s      */
specifier|private
specifier|final
name|boolean
name|lazyLoadingBlobsEnabled
decl_stmt|;
comment|/**      * Default constructor setting {@link #isLazyDereferenceing()} to<code>false</code>      */
specifier|protected
name|AbstractContentItemFactory
parameter_list|()
block|{
name|this
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a AbstractContentItemFactory and sets the state for the usage      * of {@link LazyDereferencingBlob}s.      * @param enableLazyDereferencingBlobs if {@link Blob}s generated for      * {@link ContentReference}s should dereference the content during the      * construction or lazily on the first access to the content.      */
specifier|protected
name|AbstractContentItemFactory
parameter_list|(
name|boolean
name|enableLazyDereferencingBlobs
parameter_list|)
block|{
name|this
operator|.
name|lazyLoadingBlobsEnabled
operator|=
name|enableLazyDereferencingBlobs
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|ContentItem
name|createContentItem
parameter_list|(
name|ContentSource
name|source
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|createContentItem
argument_list|(
operator|(
name|UriRef
operator|)
literal|null
argument_list|,
name|source
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
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
block|{
return|return
name|createContentItem
argument_list|(
name|prefix
argument_list|,
name|source
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
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
block|{
return|return
name|createContentItem
argument_list|(
name|id
argument_list|,
name|source
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|ContentItem
name|createContentItem
parameter_list|(
name|ContentReference
name|reference
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|createContentItem
argument_list|(
name|reference
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
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
block|{
if|if
condition|(
name|reference
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ContentReference MUST NOT be NULL!"
argument_list|)
throw|;
block|}
return|return
name|createContentItem
argument_list|(
operator|new
name|UriRef
argument_list|(
name|reference
operator|.
name|getReference
argument_list|()
argument_list|)
argument_list|,
name|createBlob
argument_list|(
name|reference
argument_list|)
argument_list|,
name|metadata
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
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
block|{
if|if
condition|(
name|prefix
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed prefix MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|source
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ContentSource MUST NOT be NULL!"
argument_list|)
throw|;
block|}
return|return
name|createContentItem
argument_list|(
name|prefix
argument_list|,
name|createBlob
argument_list|(
name|source
argument_list|)
argument_list|,
name|metadata
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
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
block|{
if|if
condition|(
name|source
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ContentSource MUST NOT be NULL!"
argument_list|)
throw|;
block|}
return|return
name|createContentItem
argument_list|(
name|id
argument_list|,
name|createBlob
argument_list|(
name|source
argument_list|)
argument_list|,
name|metadata
argument_list|)
return|;
block|}
comment|/**      * Creates a ContentItem for the parsed parameters      * @param id the ID or<code>null</code>. Implementors might want to use      * {@link ContentItemHelper#streamDigest(InputStream, java.io.OutputStream, String)      * for generating an ID while reading the data from the ContentSource      * @param blob the Blob      * @param metadata the metadata or<code>null</code> if none. Implementation       * are free to use the passed instance or to generate a new one. However       * they MUST ensure that all {@link Triple}s contained by the passed graph       * are also added to the {@link ContentItem#getMetadata() metadata} of the       * returned ContentItem.      * @return the created content item      */
specifier|protected
specifier|abstract
name|ContentItem
name|createContentItem
parameter_list|(
name|UriRef
name|id
parameter_list|,
name|Blob
name|blob
parameter_list|,
name|MGraph
name|metadata
parameter_list|)
function_decl|;
comment|/**      * Creates a ContentItem for the parsed parameters      * @param prefix the prefix for the ID of the contentItem. Guaranteed to be      * NOT<code>null</code>. Implementors might want to use      * {@link ContentItemHelper#streamDigest(InputStream, java.io.OutputStream, String)      * for generating an ID while reading the data from the ContentSource      * @param blob the Blob      * @param metadata the metadata or<code>null</code> if none. Implementation       * are free to use the passed instance or to generate a new one. However       * they MUST ensure that all {@link Triple}s contained by the passed graph       * are also added to the {@link ContentItem#getMetadata() metadata} of the       * returned ContentItem.      * @return the created content item      */
specifier|protected
specifier|abstract
name|ContentItem
name|createContentItem
parameter_list|(
name|String
name|prefix
parameter_list|,
name|Blob
name|blob
parameter_list|,
name|MGraph
name|metadata
parameter_list|)
function_decl|;
annotation|@
name|Override
specifier|public
specifier|abstract
name|Blob
name|createBlob
parameter_list|(
name|ContentSource
name|source
parameter_list|)
throws|throws
name|IOException
function_decl|;
annotation|@
name|Override
specifier|public
specifier|abstract
name|ContentSink
name|createContentSink
parameter_list|(
name|String
name|mediaType
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Getter for the state if the content from {@link ContentReference} used      * to create {@link ContentItem}s and Blobs is dereferenced immediately or      * - lazily - on the first usage.      * @return the lazy dereferencing state.      */
specifier|public
name|boolean
name|isLazyDereferenceing
parameter_list|()
block|{
return|return
name|lazyLoadingBlobsEnabled
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|Blob
name|createBlob
parameter_list|(
name|ContentReference
name|reference
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|reference
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The passed ContentReference MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|lazyLoadingBlobsEnabled
condition|)
block|{
return|return
operator|new
name|LazyDereferencingBlob
argument_list|(
name|reference
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|createBlob
argument_list|(
name|reference
operator|.
name|dereference
argument_list|()
argument_list|)
return|;
block|}
block|}
comment|/**      * If {@link AbstractContentItemFactory#isLazyDereferenceing}      * @author westei      *      */
specifier|protected
class|class
name|LazyDereferencingBlob
implements|implements
name|Blob
block|{
specifier|private
specifier|final
name|ContentReference
name|contentReference
decl_stmt|;
specifier|private
name|Blob
name|_blob
decl_stmt|;
specifier|protected
name|LazyDereferencingBlob
parameter_list|(
name|ContentReference
name|contentReference
parameter_list|)
block|{
name|this
operator|.
name|contentReference
operator|=
name|contentReference
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getMimeType
parameter_list|()
block|{
return|return
name|getLazy
argument_list|()
operator|.
name|getMimeType
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|InputStream
name|getStream
parameter_list|()
block|{
return|return
name|getLazy
argument_list|()
operator|.
name|getStream
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getParameter
parameter_list|()
block|{
return|return
name|getLazy
argument_list|()
operator|.
name|getParameter
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getContentLength
parameter_list|()
block|{
if|if
condition|(
name|_blob
operator|==
literal|null
condition|)
block|{
comment|//do not dereference for calls on getContentLength
return|return
operator|-
literal|1
return|;
block|}
else|else
block|{
return|return
name|_blob
operator|.
name|getContentLength
argument_list|()
return|;
block|}
block|}
specifier|public
name|Blob
name|getLazy
parameter_list|()
block|{
if|if
condition|(
name|_blob
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|_blob
operator|=
name|createBlob
argument_list|(
name|contentReference
operator|.
name|dereference
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to derefernece content reference '"
operator|+
name|contentReference
operator|.
name|getReference
argument_list|()
operator|+
literal|" (Message: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|")!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
return|return
name|_blob
return|;
block|}
block|}
block|}
end_class

end_unit
