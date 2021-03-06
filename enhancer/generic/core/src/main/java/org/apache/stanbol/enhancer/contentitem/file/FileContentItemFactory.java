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
name|file
package|;
end_package

begin_import
import|import static
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
operator|.
name|DEFAULT_CONTENT_ITEM_PREFIX
import|;
end_import

begin_import
import|import static
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
operator|.
name|SHA1
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileOutputStream
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|commons
operator|.
name|rdf
operator|.
name|Graph
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|IOUtils
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
name|Activate
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
name|Deactivate
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
name|Properties
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
name|Property
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
name|commons
operator|.
name|indexedgraph
operator|.
name|IndexedGraph
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
name|AbstractContentItemFactory
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
name|osgi
operator|.
name|framework
operator|.
name|Constants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|cm
operator|.
name|ConfigurationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * ContentItemFactory that stores the parsed content in Files. This Factory  * should be preferred to the InMemoryContentItemFactory in cases where content  * is parsed to the Enhancer that can not be kept in Memory.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|inherit
operator|=
literal|true
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
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|FileContentItemFactory
operator|.
name|PARAM_BASE_DIR
argument_list|,
name|value
operator|=
literal|""
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|,
name|intValue
operator|=
literal|50
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|FileContentItemFactory
extends|extends
name|AbstractContentItemFactory
implements|implements
name|ContentItemFactory
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|FileContentItemFactory
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_BINARY_MIMETYPE
init|=
literal|"application/octet-stream"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_BASE_DIR
init|=
literal|"stanbol.enhancer.contentitem.file.baseDir"
decl_stmt|;
specifier|private
specifier|static
name|FileContentItemFactory
name|instance
decl_stmt|;
comment|/**      * Base directory used to create temp files      */
specifier|private
name|File
name|baseDir
decl_stmt|;
comment|/**      * Getter for the singleton instance of this factory. Within an OSGI       * environment this should not be used as this Factory is also registered      * as OSGI service.      * @return the singleton instance using the system default temporary file      * directory.      */
specifier|public
specifier|static
name|FileContentItemFactory
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|instance
operator|==
literal|null
condition|)
block|{
name|instance
operator|=
operator|new
name|FileContentItemFactory
argument_list|()
expr_stmt|;
block|}
return|return
name|instance
return|;
block|}
specifier|public
name|FileContentItemFactory
parameter_list|()
block|{
name|super
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|//dereference all data on construction
block|}
specifier|public
name|FileContentItemFactory
parameter_list|(
name|File
name|baseDir
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|()
expr_stmt|;
if|if
condition|(
name|baseDir
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|baseDir
operator|=
name|baseDir
expr_stmt|;
name|initBaseDir
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Object
name|value
init|=
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|PARAM_BASE_DIR
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|home
init|=
name|ctx
operator|.
name|getBundleContext
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"sling.home"
argument_list|)
decl_stmt|;
if|if
condition|(
name|home
operator|!=
literal|null
condition|)
block|{
name|baseDir
operator|=
operator|new
name|File
argument_list|(
name|home
argument_list|,
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|baseDir
operator|=
operator|new
name|File
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|initBaseDir
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
operator|new
name|ConfigurationException
argument_list|(
name|PARAM_BASE_DIR
argument_list|,
literal|"Unable to initialise"
operator|+
literal|"configured base Directory '"
operator|+
name|value
operator|+
literal|"' (absolute path: '"
operator|+
name|baseDir
operator|.
name|getAbsolutePath
argument_list|()
operator|+
literal|"')!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Internally used to initialise the {@link #baseDir}      * @throws IllegalStateException if the parsed Directory already exists      * but is not an directory.      * @throws IOException if the configured directory does not exists but      * could not be created      */
specifier|private
name|void
name|initBaseDir
parameter_list|()
throws|throws
name|IOException
block|{
if|if
condition|(
name|baseDir
operator|.
name|exists
argument_list|()
operator|&&
operator|!
name|baseDir
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|baseDir
operator|=
literal|null
expr_stmt|;
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"A File with the configured Directory '"
operator|+
name|baseDir
operator|.
name|getAbsolutePath
argument_list|()
operator|+
literal|"' already exists, but is not a Directory!"
argument_list|)
throw|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"activate {} with temp directory {}"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|baseDir
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|baseDir
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|baseDir
operator|.
name|mkdirs
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Unable to create"
operator|+
literal|"temp-directory '"
operator|+
name|baseDir
operator|.
name|getAbsolutePath
argument_list|()
operator|+
literal|")!"
argument_list|)
throw|;
block|}
block|}
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|baseDir
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|ContentItem
name|createContentItem
parameter_list|(
name|IRI
name|id
parameter_list|,
name|Blob
name|blob
parameter_list|,
name|Graph
name|metadata
parameter_list|)
block|{
return|return
operator|new
name|FileContentItem
argument_list|(
name|id
argument_list|,
name|blob
argument_list|,
name|metadata
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|ContentItem
name|createContentItem
parameter_list|(
name|String
name|prefix
parameter_list|,
name|Blob
name|blob
parameter_list|,
name|Graph
name|metadata
parameter_list|)
block|{
return|return
operator|new
name|FileContentItem
argument_list|(
name|prefix
argument_list|,
name|blob
argument_list|,
name|metadata
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Blob
name|createBlob
parameter_list|(
name|ContentSource
name|source
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|FileBlob
argument_list|(
name|source
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|ContentSink
name|createContentSink
parameter_list|(
name|String
name|mediaType
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|FileContentSink
argument_list|(
name|mediaType
argument_list|)
return|;
block|}
specifier|protected
name|File
name|createTempFile
parameter_list|(
name|String
name|prefix
parameter_list|)
block|{
name|File
name|tmpFile
decl_stmt|;
try|try
block|{
name|tmpFile
operator|=
name|File
operator|.
name|createTempFile
argument_list|(
name|prefix
argument_list|,
literal|null
argument_list|,
name|baseDir
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
if|if
condition|(
name|baseDir
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to create temp-file in directory "
operator|+
name|baseDir
operator|+
literal|" (try to create in system temp"
argument_list|)
expr_stmt|;
try|try
block|{
name|tmpFile
operator|=
name|File
operator|.
name|createTempFile
argument_list|(
name|prefix
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e1
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to create temp-file"
operator|+
literal|"in '"
operator|+
name|baseDir
operator|+
literal|"' and system temp directory"
argument_list|,
name|e1
argument_list|)
throw|;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to create temp-file"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
name|tmpFile
operator|.
name|deleteOnExit
argument_list|()
expr_stmt|;
return|return
name|tmpFile
return|;
block|}
specifier|public
class|class
name|FileContentSink
implements|implements
name|ContentSink
block|{
specifier|private
specifier|final
name|WriteableFileBlob
name|blob
decl_stmt|;
specifier|protected
name|FileContentSink
parameter_list|(
name|String
name|mediaType
parameter_list|)
block|{
name|blob
operator|=
operator|new
name|WriteableFileBlob
argument_list|(
name|mediaType
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|OutputStream
name|getOutputStream
parameter_list|()
block|{
return|return
name|blob
operator|.
name|getOutputStream
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Blob
name|getBlob
parameter_list|()
block|{
return|return
name|blob
return|;
block|}
block|}
specifier|public
class|class
name|WriteableFileBlob
implements|implements
name|Blob
block|{
specifier|private
specifier|final
name|File
name|file
decl_stmt|;
specifier|private
specifier|final
name|OutputStream
name|out
decl_stmt|;
specifier|private
name|String
name|mimeType
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parameters
decl_stmt|;
specifier|protected
name|WriteableFileBlob
parameter_list|(
name|String
name|mediaType
parameter_list|)
block|{
name|this
operator|.
name|file
operator|=
name|createTempFile
argument_list|(
literal|"blob"
argument_list|)
expr_stmt|;
try|try
block|{
name|this
operator|.
name|out
operator|=
operator|new
name|FileOutputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"temporary file '"
operator|+
name|file
operator|.
name|getAbsolutePath
argument_list|()
operator|+
literal|"' was not created as expected!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parameters
decl_stmt|;
if|if
condition|(
name|mediaType
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|mimeType
operator|=
name|DEFAULT_BINARY_MIMETYPE
expr_stmt|;
name|parameters
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|parameters
operator|=
name|ContentItemHelper
operator|.
name|parseMimeType
argument_list|(
name|mediaType
argument_list|)
expr_stmt|;
name|this
operator|.
name|mimeType
operator|=
name|parameters
operator|.
name|remove
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|parameters
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|parameters
argument_list|)
expr_stmt|;
block|}
comment|/**          * Used by the {@link FileContentSink} implementation          * @return          */
specifier|protected
specifier|final
name|OutputStream
name|getOutputStream
parameter_list|()
block|{
return|return
name|out
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getMimeType
parameter_list|()
block|{
return|return
name|mimeType
return|;
block|}
annotation|@
name|Override
specifier|public
name|InputStream
name|getStream
parameter_list|()
block|{
try|try
block|{
return|return
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"temporary file '"
operator|+
name|file
operator|.
name|getAbsolutePath
argument_list|()
operator|+
literal|"' no longer present!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
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
name|parameters
return|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getContentLength
parameter_list|()
block|{
return|return
name|file
operator|.
name|length
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|finalize
parameter_list|()
throws|throws
name|Throwable
block|{
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|file
operator|.
name|delete
argument_list|()
expr_stmt|;
name|super
operator|.
name|finalize
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Blob implementation that store the data in a temp file. NOTE that      * all the other information such as {@link #getMimeType()},      * {@link #getParameter()} are kept in memory. So this can NOT be used      * to persist a ContentItem!      * @author Rupert Westenthaler      *      */
specifier|public
class|class
name|FileBlob
implements|implements
name|Blob
block|{
specifier|private
specifier|final
name|File
name|file
decl_stmt|;
comment|/**          * This implementation generates the sha1 while copying the data          * in the constructor to the file to avoid reading the data twice if a          * {@link ContentItem} is created based on a Blob.          */
specifier|private
specifier|final
name|String
name|sha1
decl_stmt|;
specifier|private
specifier|final
name|String
name|mimeType
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parameters
decl_stmt|;
specifier|protected
name|FileBlob
parameter_list|(
name|ContentSource
name|source
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
literal|"The parsed ConentSource MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|file
operator|=
name|createTempFile
argument_list|(
literal|"blob"
argument_list|)
expr_stmt|;
name|OutputStream
name|out
init|=
literal|null
decl_stmt|;
name|InputStream
name|in
init|=
literal|null
decl_stmt|;
try|try
block|{
name|out
operator|=
operator|new
name|FileOutputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|in
operator|=
name|source
operator|.
name|getStream
argument_list|()
expr_stmt|;
name|sha1
operator|=
name|ContentItemHelper
operator|.
name|streamDigest
argument_list|(
name|in
argument_list|,
name|out
argument_list|,
name|SHA1
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parameters
decl_stmt|;
if|if
condition|(
name|source
operator|.
name|getMediaType
argument_list|()
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|mimeType
operator|=
name|DEFAULT_BINARY_MIMETYPE
expr_stmt|;
name|parameters
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|parameters
operator|=
name|ContentItemHelper
operator|.
name|parseMimeType
argument_list|(
name|source
operator|.
name|getMediaType
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|mimeType
operator|=
name|parameters
operator|.
name|remove
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|parameters
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|parameters
argument_list|)
expr_stmt|;
block|}
comment|/**          * The tmp file representing this Blob          * @return the file          */
specifier|protected
specifier|final
name|File
name|getFile
parameter_list|()
block|{
return|return
name|file
return|;
block|}
comment|/**          * The sha1 of this Blob - typically used to generate the default IDs          * of a ContentItem          * @return the sha1          */
specifier|protected
specifier|final
name|String
name|getSha1
parameter_list|()
block|{
return|return
name|sha1
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getMimeType
parameter_list|()
block|{
return|return
name|mimeType
return|;
block|}
annotation|@
name|Override
specifier|public
name|InputStream
name|getStream
parameter_list|()
block|{
try|try
block|{
return|return
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"temporary file '"
operator|+
name|file
operator|.
name|getAbsolutePath
argument_list|()
operator|+
literal|"' no longer present!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
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
name|parameters
return|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getContentLength
parameter_list|()
block|{
return|return
name|file
operator|.
name|length
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|finalize
parameter_list|()
throws|throws
name|Throwable
block|{
comment|//delete the file
name|file
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Utility that returns the ID for a FileContentItem based on      * {@link FileBlob#getSha1()}.<p>      * This method is part of the {@link FileContentItemFactory} because it      * is used in the super(..) call of the {@link FileContentItem}. Normally      * it would be a static method of the inner class (what is a similar scope      * as a non static method in the outer class).      * @param blob the blob      * @return the id      * @throws IllegalArgumentException if the parsed {@link Blob} or the      * prefix is<code>null</code>      * @throws IllegalStateException if the parsed blob is not an {@link FileBlob}      */
specifier|protected
name|IRI
name|getDefaultUri
parameter_list|(
name|Blob
name|blob
parameter_list|,
name|String
name|prefix
parameter_list|)
block|{
if|if
condition|(
name|blob
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Blob MUST NOT be NULL!"
argument_list|)
throw|;
block|}
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
name|blob
operator|instanceof
name|FileBlob
condition|)
block|{
return|return
operator|new
name|IRI
argument_list|(
name|prefix
operator|+
name|SHA1
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|'-'
operator|+
operator|(
operator|(
name|FileBlob
operator|)
name|blob
operator|)
operator|.
name|getSha1
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"FileContentItem expects FileBlobs to be used"
operator|+
literal|"as Blob implementation (found: "
operator|+
name|blob
operator|.
name|getClass
argument_list|()
operator|+
literal|")!"
argument_list|)
throw|;
block|}
block|}
specifier|protected
class|class
name|FileContentItem
extends|extends
name|ContentItemImpl
implements|implements
name|ContentItem
block|{
specifier|public
name|FileContentItem
parameter_list|(
name|IRI
name|id
parameter_list|,
name|Blob
name|blob
parameter_list|,
name|Graph
name|metadata
parameter_list|)
block|{
name|super
argument_list|(
name|id
operator|==
literal|null
condition|?
name|getDefaultUri
argument_list|(
name|blob
argument_list|,
name|DEFAULT_CONTENT_ITEM_PREFIX
argument_list|)
else|:
name|id
argument_list|,
name|blob
argument_list|,
name|metadata
operator|==
literal|null
condition|?
operator|new
name|IndexedGraph
argument_list|()
else|:
name|metadata
argument_list|)
expr_stmt|;
block|}
specifier|public
name|FileContentItem
parameter_list|(
name|String
name|prefix
parameter_list|,
name|Blob
name|blob
parameter_list|,
name|Graph
name|metadata
parameter_list|)
block|{
name|super
argument_list|(
name|getDefaultUri
argument_list|(
name|blob
argument_list|,
name|prefix
argument_list|)
argument_list|,
name|blob
argument_list|,
name|metadata
operator|==
literal|null
condition|?
operator|new
name|IndexedGraph
argument_list|()
else|:
name|metadata
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

