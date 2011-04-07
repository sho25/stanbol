begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements. See the NOTICE file distributed with this  * work for additional information regarding copyright ownership. The ASF  * licenses this file to You under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the  * License for the specific language governing permissions and limitations under  * the License.  */
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
name|stanboltools
operator|.
name|datafileprovider
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
name|Arrays
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
name|Dictionary
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
import|;
end_import

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

begin_comment
comment|// DO NOT REMOVE - workaround for FELIX-2906
end_comment

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|Integer
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
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|DataFileProvider
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
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|DataFileProviderEvent
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
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|DataFileProviderLog
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
name|framework
operator|.
name|ServiceReference
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
name|osgi
operator|.
name|util
operator|.
name|tracker
operator|.
name|ServiceTracker
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
comment|/** The main DatafileProvider, delegates to other DataFileProvider if   *  the requested file is not found in its datafiles folder.  *    *  Must have the highest service ranking of all DatafileProvider, so  *  that this is the default one which delegates to others.  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Service
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
name|Integer
operator|.
name|MAX_VALUE
argument_list|)
specifier|public
class|class
name|MainDataFileProvider
implements|implements
name|DataFileProvider
implements|,
name|DataFileProviderLog
block|{
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"sling/datafiles"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|DATA_FILES_FOLDER_PROP
init|=
literal|"data.files.folder"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|intValue
operator|=
literal|100
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|MAX_EVENTS_PROP
init|=
literal|"max.events"
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
name|MainDataFileProvider
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|File
name|dataFilesFolder
decl_stmt|;
specifier|private
name|int
name|maxEvents
decl_stmt|;
comment|/** List of past events, up to maxEvents in size */
specifier|private
specifier|final
name|List
argument_list|<
name|DataFileProviderEvent
argument_list|>
name|events
init|=
operator|new
name|LinkedList
argument_list|<
name|DataFileProviderEvent
argument_list|>
argument_list|()
decl_stmt|;
comment|/** Tracks providers to which we can delegate */
specifier|private
name|ServiceTracker
name|providersTracker
decl_stmt|;
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
name|dataFilesFolder
operator|=
operator|new
name|File
argument_list|(
name|requireProperty
argument_list|(
name|ctx
operator|.
name|getProperties
argument_list|()
argument_list|,
name|DATA_FILES_FOLDER_PROP
argument_list|,
name|String
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|dataFilesFolder
operator|.
name|exists
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|dataFilesFolder
operator|.
name|mkdirs
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|DATA_FILES_FOLDER_PROP
argument_list|,
literal|"Unable to create the configured Directory "
operator|+
name|dataFilesFolder
argument_list|)
throw|;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|dataFilesFolder
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|DATA_FILES_FOLDER_PROP
argument_list|,
literal|"The configured DataFile directory "
operator|+
name|dataFilesFolder
operator|+
literal|" does already exists but is not a directory!"
argument_list|)
throw|;
block|}
comment|//else exists and is a directory!
name|maxEvents
operator|=
name|requireProperty
argument_list|(
name|ctx
operator|.
name|getProperties
argument_list|()
argument_list|,
name|MAX_EVENTS_PROP
argument_list|,
name|Integer
operator|.
name|class
argument_list|)
operator|.
name|intValue
argument_list|()
expr_stmt|;
name|providersTracker
operator|=
operator|new
name|ServiceTracker
argument_list|(
name|ctx
operator|.
name|getBundleContext
argument_list|()
argument_list|,
name|DataFileProvider
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|providersTracker
operator|.
name|open
argument_list|()
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Activated, max.events {}, data files folder {}"
argument_list|,
name|maxEvents
argument_list|,
name|dataFilesFolder
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
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
if|if
condition|(
name|providersTracker
operator|!=
literal|null
condition|)
block|{
name|providersTracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|providersTracker
operator|=
literal|null
expr_stmt|;
block|}
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|private
parameter_list|<
name|ResultType
parameter_list|>
name|ResultType
name|requireProperty
parameter_list|(
name|Dictionary
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|props
parameter_list|,
name|String
name|name
parameter_list|,
name|Class
argument_list|<
name|ResultType
argument_list|>
name|clazz
parameter_list|)
throws|throws
name|ConfigurationException
block|{
specifier|final
name|Object
name|o
init|=
name|props
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|name
argument_list|,
literal|"Missing required configuration property: "
operator|+
name|name
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
operator|(
name|clazz
operator|.
name|isAssignableFrom
argument_list|(
name|o
operator|.
name|getClass
argument_list|()
argument_list|)
operator|)
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|name
argument_list|,
literal|"Property is not a "
operator|+
name|clazz
operator|.
name|getName
argument_list|()
argument_list|)
throw|;
block|}
return|return
operator|(
name|ResultType
operator|)
name|o
return|;
block|}
comment|/** @inheritDoc */
annotation|@
name|Override
specifier|public
name|Iterator
argument_list|<
name|DataFileProviderEvent
argument_list|>
name|iterator
parameter_list|()
block|{
comment|// Iterate on a copy of our list to avoid concurrency issues
specifier|final
name|List
argument_list|<
name|DataFileProviderEvent
argument_list|>
name|copy
init|=
operator|new
name|LinkedList
argument_list|<
name|DataFileProviderEvent
argument_list|>
argument_list|()
decl_stmt|;
synchronized|synchronized
init|(
name|events
init|)
block|{
name|copy
operator|.
name|addAll
argument_list|(
name|events
argument_list|)
expr_stmt|;
block|}
return|return
name|copy
operator|.
name|iterator
argument_list|()
return|;
block|}
comment|/** @inheritDoc */
annotation|@
name|Override
specifier|public
name|int
name|maxEventsCount
parameter_list|()
block|{
return|return
name|maxEvents
return|;
block|}
comment|/** @inheritDoc */
annotation|@
name|Override
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|events
operator|.
name|size
argument_list|()
return|;
block|}
comment|/** @inheritDoc */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|public
name|InputStream
name|getInputStream
parameter_list|(
name|String
name|bundleSymbolicName
parameter_list|,
name|String
name|filename
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|comments
parameter_list|)
throws|throws
name|IOException
block|{
name|InputStream
name|result
init|=
literal|null
decl_stmt|;
name|String
name|fileUrl
init|=
literal|null
decl_stmt|;
comment|// First look for the file in our data folder,
comment|// with and without bundle symbolic name prefix
specifier|final
name|String
index|[]
name|candidateNames
init|=
block|{
name|bundleSymbolicName
operator|+
literal|"-"
operator|+
name|filename
block|,
name|filename
block|}
decl_stmt|;
for|for
control|(
name|String
name|name
range|:
name|candidateNames
control|)
block|{
specifier|final
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|dataFilesFolder
argument_list|,
name|name
argument_list|)
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Looking for file {}"
argument_list|,
name|f
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|f
operator|.
name|exists
argument_list|()
operator|&&
name|f
operator|.
name|canRead
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"File found in data files folder: {}"
argument_list|,
name|filename
argument_list|)
expr_stmt|;
name|result
operator|=
operator|new
name|FileInputStream
argument_list|(
name|f
argument_list|)
expr_stmt|;
name|fileUrl
operator|=
literal|"file://"
operator|+
name|f
operator|.
name|getAbsolutePath
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
comment|// Then, if not found, query other DataFileProviders,
comment|// ordered by service ranking
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
comment|// Sort providers by service ranking
specifier|final
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|refs
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|providersTracker
operator|.
name|getServiceReferences
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|refs
argument_list|)
expr_stmt|;
for|for
control|(
name|ServiceReference
name|ref
range|:
name|refs
control|)
block|{
specifier|final
name|Object
name|o
init|=
name|providersTracker
operator|.
name|getService
argument_list|(
name|ref
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
name|this
condition|)
block|{
continue|continue;
block|}
specifier|final
name|DataFileProvider
name|dfp
init|=
operator|(
name|DataFileProvider
operator|)
name|o
decl_stmt|;
try|try
block|{
name|result
operator|=
name|dfp
operator|.
name|getInputStream
argument_list|(
name|bundleSymbolicName
argument_list|,
name|filename
argument_list|,
name|comments
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|//Exceptions thrown by an implementation should never
comment|//affect the MainDataFileProvider
name|log
operator|.
name|debug
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Eception while searching DataFile %s by using provider %s (ignore)"
argument_list|,
name|filename
argument_list|,
name|dfp
argument_list|)
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"{} does not provide file {}"
argument_list|,
name|dfp
argument_list|,
name|filename
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fileUrl
operator|=
name|dfp
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"://"
operator|+
name|filename
expr_stmt|;
break|break;
comment|//break as soon as a resource was found
block|}
block|}
block|}
comment|// Add event
specifier|final
name|DataFileProviderEvent
name|event
init|=
operator|new
name|DataFileProviderEvent
argument_list|(
name|bundleSymbolicName
argument_list|,
name|filename
argument_list|,
name|comments
argument_list|,
name|fileUrl
argument_list|)
decl_stmt|;
synchronized|synchronized
init|(
name|events
init|)
block|{
if|if
condition|(
name|events
operator|.
name|size
argument_list|()
operator|>=
name|maxEvents
condition|)
block|{
name|events
operator|.
name|remove
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
name|events
operator|.
name|add
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"File not found: "
operator|+
name|filename
argument_list|)
throw|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"Successfully loaded file {}"
argument_list|,
name|event
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
name|File
name|getDataFilesFolder
parameter_list|()
block|{
return|return
name|dataFilesFolder
return|;
block|}
block|}
end_class

end_unit

