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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|source
package|;
end_package

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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|EnumSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeMap
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
name|compress
operator|.
name|archivers
operator|.
name|zip
operator|.
name|ZipArchiveEntry
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
name|compress
operator|.
name|archivers
operator|.
name|zip
operator|.
name|ZipFile
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
name|FileUtils
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
name|FilenameUtils
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

begin_class
specifier|public
class|class
name|ResourceLoader
block|{
specifier|private
specifier|final
name|boolean
name|failOnError
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ResourceLoader
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|ResourceImporter
name|resourceImporter
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|ResourceState
argument_list|>
name|files
decl_stmt|;
specifier|private
name|File
name|importedDir
decl_stmt|;
comment|/**      * for future uses to activate/deactivate parsing of entries within ZIP      * archives. If<code>false</code> the ZIP archive will be parsed as a       * whole. If<code>true</code> the Entries of the ZIP archive will be      * parsed to the resource handler.      */
specifier|private
name|boolean
name|loadEntriesWithinZipArchives
init|=
literal|true
decl_stmt|;
specifier|public
name|ResourceLoader
parameter_list|(
name|ResourceImporter
name|resourceImporter
parameter_list|,
name|boolean
name|failOnError
parameter_list|)
block|{
name|this
argument_list|(
name|resourceImporter
argument_list|,
literal|true
argument_list|,
name|failOnError
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ResourceLoader
parameter_list|(
name|ResourceImporter
name|resourceImporter
parameter_list|,
name|boolean
name|processEntriesWithinArchives
parameter_list|,
name|boolean
name|failOnError
parameter_list|)
block|{
if|if
condition|(
name|resourceImporter
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"The parsed ResourceProcessor instance MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|resourceImporter
operator|=
name|resourceImporter
expr_stmt|;
name|this
operator|.
name|loadEntriesWithinZipArchives
operator|=
name|processEntriesWithinArchives
expr_stmt|;
comment|//use a tree map to have the files sorted
name|this
operator|.
name|files
operator|=
operator|new
name|TreeMap
argument_list|<
name|String
argument_list|,
name|ResourceState
argument_list|>
argument_list|()
expr_stmt|;
name|this
operator|.
name|failOnError
operator|=
name|failOnError
expr_stmt|;
block|}
specifier|public
name|void
name|setImportedDir
parameter_list|(
name|File
name|importedDir
parameter_list|)
block|{
name|this
operator|.
name|importedDir
operator|=
name|importedDir
expr_stmt|;
if|if
condition|(
name|importedDir
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|importedDir
operator|.
name|isFile
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed imported directory MUST NOT be a File"
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|importedDir
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|importedDir
operator|.
name|mkdirs
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to create imported directory "
operator|+
name|importedDir
argument_list|)
throw|;
block|}
block|}
block|}
block|}
comment|/**      * Adds a new {@link File} resource to this resource loader. In case a      * directory is parsed, all files directly within this directory will be       * also added. Note that hidden Files are ignored.      * @param fileOrDirectory the file/directory to add.      */
specifier|public
name|void
name|addResource
parameter_list|(
name|File
name|fileOrDirectory
parameter_list|)
block|{
if|if
condition|(
name|fileOrDirectory
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|file
range|:
name|getFiles
argument_list|(
name|fileOrDirectory
argument_list|)
control|)
block|{
name|ResourceState
name|state
init|=
name|files
operator|.
name|get
argument_list|(
name|file
argument_list|)
decl_stmt|;
if|if
condition|(
name|state
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"File {} registered to this RdfLoader"
argument_list|,
name|file
argument_list|)
expr_stmt|;
name|files
operator|.
name|put
argument_list|(
name|file
argument_list|,
name|ResourceState
operator|.
name|REGISTERED
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|state
operator|==
name|ResourceState
operator|.
name|ERROR
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Readding file {} after previous error while loading"
argument_list|,
name|file
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Ignore file {} because it already present with state {}"
argument_list|,
name|file
argument_list|,
name|state
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|/**      * Getter for the read only status of the resource loader.      * @return the read only view of the status      */
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|ResourceState
argument_list|>
name|getResourceStates
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|files
argument_list|)
return|;
block|}
comment|/**      * Getter for all resources that are currently in the parsed state.      * This Method returns a copy of all resources in the parsed state.      * @param state the processing state      * @return A copy of all resources in the parsed state      */
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|getResources
parameter_list|(
name|ResourceState
name|state
parameter_list|)
block|{
if|if
condition|(
name|state
operator|==
literal|null
condition|)
block|{
return|return
name|Collections
operator|.
name|emptySet
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|getResources
argument_list|(
name|EnumSet
operator|.
name|of
argument_list|(
name|state
argument_list|)
argument_list|)
return|;
block|}
block|}
comment|/**      * Getter for all resources that are currently in on of the parsed states.      * This Method returns a copy of all resources in such states.      * @param states the processing states      * @return A copy of all resources in one of the parsed states      */
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|getResources
parameter_list|(
name|Set
argument_list|<
name|ResourceState
argument_list|>
name|states
parameter_list|)
block|{
if|if
condition|(
name|states
operator|==
literal|null
condition|)
block|{
return|return
name|Collections
operator|.
name|emptySet
argument_list|()
return|;
block|}
else|else
block|{
name|Collection
argument_list|<
name|String
argument_list|>
name|files
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
synchronized|synchronized
init|(
name|this
operator|.
name|files
init|)
block|{
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|ResourceState
argument_list|>
name|entry
range|:
name|this
operator|.
name|files
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|states
operator|.
name|contains
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
block|{
name|files
operator|.
name|add
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|files
return|;
block|}
block|}
specifier|public
name|void
name|loadResources
parameter_list|()
block|{
name|Collection
argument_list|<
name|String
argument_list|>
name|fileToLoad
decl_stmt|;
do|do
block|{
comment|//to support adding of new files while loading
name|fileToLoad
operator|=
name|getResources
argument_list|(
name|ResourceState
operator|.
name|REGISTERED
argument_list|)
expr_stmt|;
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Loding {} File{} ..."
argument_list|,
name|fileToLoad
operator|.
name|size
argument_list|()
argument_list|,
name|fileToLoad
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|?
literal|"s"
else|:
literal|""
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|file
range|:
name|fileToLoad
control|)
block|{
name|loadResource
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|" ... {} files imported in {} seconds"
argument_list|,
name|fileToLoad
operator|.
name|size
argument_list|()
argument_list|,
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
operator|)
operator|/
literal|1000
argument_list|)
expr_stmt|;
block|}
do|while
condition|(
operator|!
name|fileToLoad
operator|.
name|isEmpty
argument_list|()
condition|)
do|;
block|}
comment|/**      * Loads a resource from a file      * @param file the file resource      */
specifier|private
name|void
name|loadResource
parameter_list|(
name|String
name|file
parameter_list|)
block|{
synchronized|synchronized
init|(
name|files
init|)
block|{
comment|//sync to files to avoid two threads loading the same file
name|ResourceState
name|state
init|=
name|files
operator|.
name|get
argument_list|(
name|file
argument_list|)
decl_stmt|;
if|if
condition|(
name|state
operator|==
literal|null
operator|||
name|state
operator|!=
name|ResourceState
operator|.
name|REGISTERED
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Do not load File {} because of its state {} (null means removed from list)"
argument_list|,
name|file
argument_list|,
name|state
argument_list|)
expr_stmt|;
return|return;
comment|//someone removed it in between
block|}
else|else
block|{
comment|//set to loading
name|setResourceState
argument_list|(
name|file
argument_list|,
name|ResourceState
operator|.
name|LOADING
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
name|long
name|startFile
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"> loading '{}' ..."
argument_list|,
name|file
argument_list|)
expr_stmt|;
name|String
name|extension
init|=
name|FilenameUtils
operator|.
name|getExtension
argument_list|(
name|file
argument_list|)
decl_stmt|;
if|if
condition|(
name|loadEntriesWithinZipArchives
operator|&&
operator|(
literal|"zip"
operator|.
name|equalsIgnoreCase
argument_list|(
name|extension
argument_list|)
operator|||
literal|"jar"
operator|.
name|equalsIgnoreCase
argument_list|(
name|extension
argument_list|)
operator|)
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"  - processing {}-archive entries:"
argument_list|,
name|extension
argument_list|)
expr_stmt|;
name|ZipFile
name|zipArchive
decl_stmt|;
try|try
block|{
name|zipArchive
operator|=
operator|new
name|ZipFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|zipArchive
operator|=
literal|null
expr_stmt|;
name|setResourceState
argument_list|(
name|file
argument_list|,
name|ResourceState
operator|.
name|ERROR
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|zipArchive
operator|!=
literal|null
condition|)
block|{
name|boolean
name|isError
init|=
literal|false
decl_stmt|;
name|Enumeration
argument_list|<
name|ZipArchiveEntry
argument_list|>
name|entries
init|=
name|zipArchive
operator|.
name|getEntries
argument_list|()
decl_stmt|;
while|while
condition|(
name|entries
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|ZipArchiveEntry
name|entry
init|=
name|entries
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|entry
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|String
name|entryName
init|=
name|entry
operator|.
name|getName
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"     o loading entry '{}'"
argument_list|,
name|entryName
argument_list|)
expr_stmt|;
try|try
block|{
name|ResourceState
name|state
init|=
name|resourceImporter
operator|.
name|importResource
argument_list|(
name|zipArchive
operator|.
name|getInputStream
argument_list|(
name|entry
argument_list|)
argument_list|,
name|FilenameUtils
operator|.
name|getName
argument_list|(
name|entryName
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|state
operator|==
name|ResourceState
operator|.
name|ERROR
condition|)
block|{
name|isError
operator|=
literal|true
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|isError
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
comment|//set the state for the Archive as a whole
name|setResourceState
argument_list|(
name|file
argument_list|,
name|isError
condition|?
name|ResourceState
operator|.
name|ERROR
else|:
name|ResourceState
operator|.
name|LOADED
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|InputStream
name|is
decl_stmt|;
try|try
block|{
name|is
operator|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|ResourceState
name|state
init|=
name|resourceImporter
operator|.
name|importResource
argument_list|(
name|is
argument_list|,
name|FilenameUtils
operator|.
name|getName
argument_list|(
name|file
argument_list|)
argument_list|)
decl_stmt|;
name|setResourceState
argument_list|(
name|file
argument_list|,
name|state
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
comment|//during init it is checked that files exists and are files
comment|//and there is read access so this can only happen if
comment|//someone deletes the file in between
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to load resource "
operator|+
name|file
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|setResourceState
argument_list|(
name|file
argument_list|,
name|ResourceState
operator|.
name|ERROR
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to load resource "
operator|+
name|file
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|setResourceState
argument_list|(
name|file
argument_list|,
name|ResourceState
operator|.
name|ERROR
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to load resource "
operator|+
name|file
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|setResourceState
argument_list|(
name|file
argument_list|,
name|ResourceState
operator|.
name|ERROR
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
name|log
operator|.
name|info
argument_list|(
literal|"   - completed in {} seconds"
argument_list|,
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|startFile
operator|)
operator|/
literal|1000
argument_list|)
expr_stmt|;
block|}
comment|/**      * Getter for the files based on a parsed File or Directory. Hidden Files      * are ignored. Doese not search recursively to the directory structure!      * @param fileOrDir The file or directory      * @return the Collection of files found based on the parameter      */
specifier|private
specifier|static
name|Collection
argument_list|<
name|String
argument_list|>
name|getFiles
parameter_list|(
name|File
name|fileOrDir
parameter_list|)
block|{
if|if
condition|(
name|fileOrDir
operator|==
literal|null
condition|)
block|{
return|return
name|Collections
operator|.
name|emptySet
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|fileOrDir
operator|.
name|isHidden
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|emptySet
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|fileOrDir
operator|.
name|isFile
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|singleton
argument_list|(
name|fileOrDir
operator|.
name|getPath
argument_list|()
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|fileOrDir
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|Collection
argument_list|<
name|String
argument_list|>
name|files
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|File
name|file
range|:
name|fileOrDir
operator|.
name|listFiles
argument_list|()
control|)
block|{
if|if
condition|(
name|file
operator|.
name|isFile
argument_list|()
operator|&&
operator|!
name|file
operator|.
name|isHidden
argument_list|()
condition|)
block|{
name|files
operator|.
name|add
argument_list|(
name|FilenameUtils
operator|.
name|concat
argument_list|(
name|fileOrDir
operator|.
name|getPath
argument_list|()
argument_list|,
name|file
operator|.
name|getPath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|files
return|;
block|}
else|else
block|{
comment|//file does not exist
return|return
name|Collections
operator|.
name|emptySet
argument_list|()
return|;
block|}
block|}
comment|/**      * Logs the Exception and sets the file to the {@link ResourceState#ERROR}      * state      * @param file the affected file      * @param e the Exception      */
specifier|private
name|void
name|setResourceState
parameter_list|(
name|String
name|file
parameter_list|,
name|ResourceState
name|state
parameter_list|,
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Exception while loading file "
operator|+
name|file
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|state
operator|==
literal|null
condition|)
block|{
comment|//ensure that there are no null values in the map
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ProcessingState MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|file
operator|==
literal|null
condition|)
block|{
comment|//ignore calls if file is null
return|return;
block|}
synchronized|synchronized
init|(
name|files
init|)
block|{
if|if
condition|(
name|files
operator|.
name|containsKey
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"File {} now in state {}"
argument_list|,
name|file
argument_list|,
name|state
argument_list|)
expr_stmt|;
if|if
condition|(
name|importedDir
operator|!=
literal|null
operator|&&
name|ResourceState
operator|.
name|LOADED
operator|==
name|state
condition|)
block|{
name|files
operator|.
name|remove
argument_list|(
name|file
argument_list|)
expr_stmt|;
try|try
block|{
name|files
operator|.
name|put
argument_list|(
name|moveToImportedFolder
argument_list|(
operator|new
name|File
argument_list|(
name|file
argument_list|)
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|,
name|state
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ioe
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to move loaded RDFTerm {} to imported Directory! "
operator|+
literal|"Please move the file manually to {}!"
argument_list|,
name|file
argument_list|,
name|importedDir
argument_list|)
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"Reason: "
operator|+
name|ioe
operator|.
name|getMessage
argument_list|()
argument_list|,
name|ioe
argument_list|)
expr_stmt|;
name|files
operator|.
name|put
argument_list|(
name|file
argument_list|,
name|state
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|//this does not use an imported folder or the state is not LOADED
name|files
operator|.
name|put
argument_list|(
name|file
argument_list|,
name|state
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ResourceState
operator|.
name|ERROR
operator|==
name|state
condition|)
block|{
comment|//if failOnError is activated we stop the loading on the first error!
if|if
condition|(
name|failOnError
condition|)
block|{
name|String
name|msg
init|=
literal|"Error while loading RDFTerm "
operator|+
name|file
decl_stmt|;
if|if
condition|(
name|e
operator|!=
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|msg
argument_list|,
name|e
argument_list|)
throw|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|msg
argument_list|)
throw|;
block|}
block|}
else|else
block|{
comment|//if failOnError is de-activated ignore the resource loading error and continue..
name|log
operator|.
name|info
argument_list|(
literal|"Ignore Error for File {} and continue"
argument_list|,
name|file
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Ignore Error for File {} because it is no longer registered with this RdfLoader"
argument_list|,
name|file
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|File
name|moveToImportedFolder
parameter_list|(
name|File
name|file
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|importedDir
operator|==
literal|null
condition|)
block|{
return|return
name|file
return|;
block|}
name|File
name|moved
init|=
operator|new
name|File
argument_list|(
name|importedDir
argument_list|,
name|file
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|moved
operator|.
name|isFile
argument_list|()
condition|)
block|{
name|i
operator|++
expr_stmt|;
name|moved
operator|=
operator|new
name|File
argument_list|(
name|importedDir
argument_list|,
name|i
operator|+
literal|"_"
operator|+
name|file
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"   ... moving imported file {} to {}/{}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|file
operator|.
name|getName
argument_list|()
block|,
name|importedDir
operator|.
name|getName
argument_list|()
block|,
name|moved
operator|.
name|getName
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|FileUtils
operator|.
name|moveFile
argument_list|(
name|file
argument_list|,
name|moved
argument_list|)
expr_stmt|;
return|return
name|moved
return|;
block|}
block|}
end_class

end_unit

