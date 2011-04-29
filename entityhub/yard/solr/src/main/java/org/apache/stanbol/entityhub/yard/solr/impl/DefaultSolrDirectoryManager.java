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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|ConfigUtils
operator|.
name|*
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
name|HashMap
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
name|Set
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
name|ArchiveInputStream
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
name|filefilter
operator|.
name|DirectoryFileFilter
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
name|Reference
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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|SolrDirectoryManager
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
name|BundleContext
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
comment|/**  * Implementation of the {@link SolrDirectoryManager} interface that supports  * the dynamic initialisation of new cores based on the default core configuration  * contained within the SolrYard bundle.  *   * @author Rupert Westenthaler  *  */
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
name|SolrDirectoryManager
operator|.
name|MANAGED_SOLR_DIR_PROPERTY
argument_list|,
name|value
operator|=
name|SolrDirectoryManager
operator|.
name|DEFAULT_SOLR_DATA_DIR
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|DefaultSolrDirectoryManager
implements|implements
name|SolrDirectoryManager
block|{
comment|/**      * The logger      */
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|DefaultSolrDirectoryManager
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * This key is used to store the file name of the archive supposed to provide      * the data for the uninitialised index within the configuration the configuration      */
specifier|private
specifier|static
specifier|final
name|String
name|UNINITIALISED_INDEX_ARCHIVE_NAME_KEY
init|=
literal|"Uninitialised-Index-Archive-Name"
decl_stmt|;
comment|/**      * The dataFileProvider used to lookup index data      */
annotation|@
name|Reference
specifier|private
name|DataFileProvider
name|dataFileProvider
decl_stmt|;
comment|/**      * The directory used by the internally managed embedded solr server.       * Use {@link #lookupManagedSolrDir()} instead of using this member, because      * this member is not initialised within the constructor or the       * {@link #activate(ComponentContext)} method.      */
specifier|private
name|File
name|solrDataDir
decl_stmt|;
comment|/**      * The component context. Only available when running within an OSGI       * Environment and the component is active.      */
specifier|private
name|ComponentContext
name|componentContext
decl_stmt|;
comment|/**      * For some functionality within this component it is important to track      * if this instance operates within or outside of an OSGI environment.      * because of this this boolean is set to true as soon as the first time      * {@link #activate(ComponentContext)} or {@link #deactivate(ComponentContext)}      * is called. If someone knows a better method to check that feel free to      * change!      */
specifier|private
name|boolean
name|withinOSGI
init|=
literal|false
decl_stmt|;
comment|/**      * Initialising Solr Indexes with a lot of data may take some time. Especially      * if the data need to be copied to the managed directory. Therefore it is      * important to wait for the initialisation to be complete before opening      * an Solr Index on it.<p>      * To this set all cores that are currently initialised are added. As soon      * as an initialisation completed this set is notified.      */
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|initCores
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|/**      * Holds the list of cores that where installed by using       * {@link #createSolrDirectory(String, String, java.util.Properties)} but the      * {@link DataFileProvider} could not yet provide the necessary data for the      * initialisation.<p>      * The list of uninitialised cores is stored within the data folder of the      * bundle under {@link #UNINITIALISED_SITE_DIRECTORY_NAME} and loaded at      * activation.      *       */
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|java
operator|.
name|util
operator|.
name|Properties
argument_list|>
name|uninitialisedCores
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|java
operator|.
name|util
operator|.
name|Properties
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|DefaultSolrDirectoryManager
parameter_list|()
block|{     }
comment|/* (non-Javadoc)      * @see org.apache.stanbol.entityhub.yard.solr.impl.ManagedSolrDirectory#isSolrDir(java.lang.String)      */
specifier|public
specifier|final
name|boolean
name|isManagedIndex
parameter_list|(
name|String
name|solrIndexName
parameter_list|)
throws|throws
name|IllegalStateException
block|{
if|if
condition|(
name|solrIndexName
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed name of the Solr index MUST NOT be NULL"
argument_list|)
throw|;
block|}
if|if
condition|(
name|solrIndexName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed name of the Solr index MUST NOT be empty"
argument_list|)
throw|;
block|}
comment|//first check if the directory for the parsed index exists
name|boolean
name|exists
init|=
operator|new
name|File
argument_list|(
name|lookupManagedSolrDir
argument_list|(
name|componentContext
argument_list|)
argument_list|,
name|solrIndexName
argument_list|)
operator|.
name|exists
argument_list|()
decl_stmt|;
return|return
operator|!
name|exists
condition|?
comment|//if no directory exists
comment|//check also if an uninitialised index was requested
name|uninitialisedCores
operator|.
name|containsKey
argument_list|(
name|solrIndexName
argument_list|)
else|:
literal|true
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.entityhub.yard.solr.impl.ManagedSolrDirectory#getManagedIndices()      */
specifier|public
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|File
argument_list|>
name|getManagedIndices
parameter_list|()
throws|throws
name|IllegalStateException
block|{
name|File
name|solrDir
init|=
name|lookupManagedSolrDir
argument_list|(
name|componentContext
argument_list|)
decl_stmt|;
name|String
index|[]
name|indexNames
init|=
name|solrDir
operator|.
name|list
argument_list|(
name|DirectoryFileFilter
operator|.
name|INSTANCE
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|File
argument_list|>
name|indexes
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|File
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|indexName
range|:
name|indexNames
control|)
block|{
comment|//TODO: validate that this is actually a SolrCore!
name|indexes
operator|.
name|put
argument_list|(
name|indexName
argument_list|,
operator|new
name|File
argument_list|(
name|solrDir
argument_list|,
name|indexName
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//we need also add the uninitialised indexes (with a null as value)
for|for
control|(
name|String
name|indexName
range|:
name|uninitialisedCores
operator|.
name|keySet
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|indexes
operator|.
name|containsKey
argument_list|(
name|indexName
argument_list|)
condition|)
block|{
name|indexes
operator|.
name|put
argument_list|(
name|indexName
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|indexes
return|;
block|}
specifier|public
name|boolean
name|isInitialisedIndex
parameter_list|(
name|String
name|solrIndexName
parameter_list|)
block|{
if|if
condition|(
name|solrIndexName
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed name of the Solr index MUST NOT be NULL"
argument_list|)
throw|;
block|}
if|if
condition|(
name|solrIndexName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed name of the Solr index MUST NOT be empty"
argument_list|)
throw|;
block|}
if|if
condition|(
name|initCores
operator|.
name|contains
argument_list|(
name|solrIndexName
argument_list|)
condition|)
block|{
comment|//if it is currently initialised
return|return
literal|false
return|;
comment|//return false
block|}
else|else
block|{
comment|//check if the dir is there
return|return
operator|new
name|File
argument_list|(
name|lookupManagedSolrDir
argument_list|(
name|componentContext
argument_list|)
argument_list|,
name|solrIndexName
argument_list|)
operator|.
name|exists
argument_list|()
return|;
block|}
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.entityhub.yard.solr.impl.ManagedSolrDirectory#getSolrDirectory(java.lang.String)      */
specifier|public
specifier|final
name|File
name|getSolrIndexDirectory
parameter_list|(
specifier|final
name|String
name|solrIndexName
parameter_list|,
name|boolean
name|allowDefaultInit
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
return|return
name|initSolrDirectory
argument_list|(
name|solrIndexName
argument_list|,
literal|null
argument_list|,
name|allowDefaultInit
argument_list|,
name|componentContext
argument_list|)
return|;
block|}
specifier|public
specifier|final
name|File
name|createSolrIndex
parameter_list|(
specifier|final
name|String
name|solrIndexName
parameter_list|,
name|ArchiveInputStream
name|ais
parameter_list|)
block|{
return|return
name|initSolrDirectory
argument_list|(
name|solrIndexName
argument_list|,
name|ais
argument_list|,
literal|false
argument_list|,
name|componentContext
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|File
name|createSolrDirectory
parameter_list|(
name|String
name|solrIndexName
parameter_list|,
name|String
name|indexArchiveRef
parameter_list|,
name|java
operator|.
name|util
operator|.
name|Properties
name|properties
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|IOException
block|{
if|if
condition|(
name|componentContext
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Creating an Index by using the DataFileProvider does only work when running within an OSGI"
argument_list|)
throw|;
block|}
comment|//now add the index to the list of uninitialised
name|addUninitialisedIndex
argument_list|(
name|solrIndexName
argument_list|,
name|indexArchiveRef
argument_list|,
name|properties
argument_list|)
expr_stmt|;
return|return
name|initSolrDirectory
argument_list|(
name|solrIndexName
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|,
name|componentContext
argument_list|)
return|;
block|}
comment|/**      * Tries to get the {@link ArchiveInputStream} for the index from the      * {@link DataFileProvider}.      * @param context the context used to perform the operations      * @param solrIndexName the name of the index to initialise      * @param properties the properties for this index. Must contain the       * {@link #UNINITIALISED_INDEX_ARCHIVE_NAME_KEY}.      * @return The {@link ArchiveInputStream} or<code>null</code> if the      * data are still not available      * @throws IOException on any IO related error while initialising the index      * @throws IllegalStateException if the parsed configuration does not provide      * a value for {@link #UNINITIALISED_INDEX_ARCHIVE_NAME_KEY}.      */
specifier|private
name|ArchiveInputStream
name|lookupIndexArchive
parameter_list|(
name|ComponentContext
name|context
parameter_list|,
name|String
name|solrIndexName
parameter_list|,
name|java
operator|.
name|util
operator|.
name|Properties
name|properties
parameter_list|)
throws|throws
name|IOException
throws|,
name|IllegalStateException
block|{
name|String
name|archiveName
init|=
name|properties
operator|.
name|getProperty
argument_list|(
name|UNINITIALISED_INDEX_ARCHIVE_NAME_KEY
argument_list|)
decl_stmt|;
if|if
condition|(
name|archiveName
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Found uninitialised index config that does not contain the required "
operator|+
name|UNINITIALISED_INDEX_ARCHIVE_NAME_KEY
operator|+
literal|" property!"
argument_list|)
throw|;
block|}
comment|//we need to copy the properties to a map
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|propMap
decl_stmt|;
if|if
condition|(
name|properties
operator|==
literal|null
condition|)
block|{
name|properties
operator|=
operator|new
name|java
operator|.
name|util
operator|.
name|Properties
argument_list|()
expr_stmt|;
comment|//create an empty properties file
name|propMap
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|propMap
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
for|for
control|(
name|Entry
argument_list|<
name|Object
argument_list|,
name|Object
argument_list|>
name|entry
range|:
name|properties
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|propMap
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|!=
literal|null
condition|?
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
name|propMap
operator|.
name|remove
argument_list|(
name|UNINITIALISED_INDEX_ARCHIVE_NAME_KEY
argument_list|)
expr_stmt|;
comment|//do not parse this internal property
name|InputStream
name|is
init|=
name|dataFileProvider
operator|.
name|getInputStream
argument_list|(
name|context
operator|.
name|getBundleContext
argument_list|()
operator|.
name|getBundle
argument_list|()
operator|.
name|getSymbolicName
argument_list|()
argument_list|,
name|archiveName
argument_list|,
name|propMap
argument_list|)
decl_stmt|;
return|return
name|is
operator|==
literal|null
condition|?
literal|null
else|:
name|ConfigUtils
operator|.
name|getArchiveInputStream
argument_list|(
name|archiveName
argument_list|,
name|is
argument_list|)
return|;
block|}
specifier|private
name|void
name|addUninitialisedIndex
parameter_list|(
name|String
name|indexName
parameter_list|,
name|String
name|sourceFileName
parameter_list|,
name|java
operator|.
name|util
operator|.
name|Properties
name|config
parameter_list|)
throws|throws
name|IOException
block|{
name|ComponentContext
name|context
init|=
name|componentContext
decl_stmt|;
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"This feature is only available when running within an OSGI environment"
argument_list|)
throw|;
block|}
if|if
condition|(
name|config
operator|==
literal|null
condition|)
block|{
name|config
operator|=
operator|new
name|java
operator|.
name|util
operator|.
name|Properties
argument_list|()
expr_stmt|;
block|}
name|config
operator|.
name|setProperty
argument_list|(
name|UNINITIALISED_INDEX_ARCHIVE_NAME_KEY
argument_list|,
name|sourceFileName
argument_list|)
expr_stmt|;
synchronized|synchronized
init|(
name|uninitialisedCores
init|)
block|{
if|if
condition|(
name|uninitialisedCores
operator|.
name|put
argument_list|(
name|indexName
argument_list|,
name|config
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|removeUninitialisedIndexConfig
argument_list|(
name|context
argument_list|,
name|indexName
argument_list|)
expr_stmt|;
comment|//remove the old version
block|}
name|saveUninitialisedIndexConfig
argument_list|(
name|context
argument_list|,
name|indexName
argument_list|,
name|config
argument_list|)
expr_stmt|;
comment|//save the new version
block|}
block|}
specifier|private
name|void
name|removeUninitialisedIndex
parameter_list|(
name|String
name|indexName
parameter_list|)
block|{
name|ComponentContext
name|context
init|=
name|componentContext
decl_stmt|;
synchronized|synchronized
init|(
name|uninitialisedCores
init|)
block|{
if|if
condition|(
name|uninitialisedCores
operator|.
name|remove
argument_list|(
name|indexName
argument_list|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
comment|//check only for the context if we need actually to remove
comment|//an entry, because this method is also called outside an
comment|//OSGI environment (but will never remove something from
comment|//uninitialisedCores)
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"This feature is only available when running within an OSGI environment"
argument_list|)
throw|;
block|}
name|removeUninitialisedIndexConfig
argument_list|(
name|context
argument_list|,
name|indexName
argument_list|)
expr_stmt|;
comment|//remove the old version
block|}
block|}
block|}
comment|/**      * Internally used to get/init the Solr directory of a SolrCore or the root      * Solr directory (if<code>null</code> is parsed)      * @param solrIndexName the name of the Core or<code>null</code> to get/init      * the root solr directory      * @param ais The Input stream of the Archive to load the index from or      *<code>null</code> to load the default core configuration.      * @param allowDefaultInitialisation If<code>true</code> a new core is       * initialised with the default configuration (empty index with the default      * Solr schema and configuration). If<code>false</code> the core is only      * created if a valid configuration is parsed.      * @param context A reference to the component context or<code>null</code> if      * running outside an OSGI container. This is needed to avoid that       * {@link #deactivate(ComponentContext)} sets the context to<code>null</code>       * during this method does its initialisation work.      * @return the Solr directory or<code>null</code> if the requested index      * could not be created (e.g. because of<code>false</code> was parsed as       * create) or in case this component is deactivated      * @throws IllegalStateException in case this method is called when this      * component is running within an OSGI environment and it is deactivated or      * the initialisation for the parsed index failed.      * @throws IllegalArgumentException if the parsed solrIndexName is<code>null</code> or      * empty.      */
specifier|private
specifier|final
name|File
name|initSolrDirectory
parameter_list|(
specifier|final
name|String
name|solrIndexName
parameter_list|,
name|ArchiveInputStream
name|ais
parameter_list|,
name|boolean
name|allowDefaultInitialisation
parameter_list|,
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|IllegalStateException
block|{
if|if
condition|(
name|solrIndexName
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed name of the Solr index MUST NOT be NULL"
argument_list|)
throw|;
block|}
if|if
condition|(
name|solrIndexName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed name of the Solr index MUST NOT be empty"
argument_list|)
throw|;
block|}
name|File
name|managedCoreContainerDirectory
init|=
name|lookupManagedSolrDir
argument_list|(
name|context
argument_list|)
decl_stmt|;
if|if
condition|(
name|solrIndexName
operator|==
literal|null
condition|)
block|{
comment|//if the indexName is null
return|return
name|managedCoreContainerDirectory
return|;
comment|//return the root directory
block|}
name|File
name|coreDir
init|=
operator|new
name|File
argument_list|(
name|managedCoreContainerDirectory
argument_list|,
name|solrIndexName
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|coreDir
operator|.
name|exists
argument_list|()
condition|)
block|{
comment|//first add the index to the list of currently init cores
synchronized|synchronized
init|(
name|initCores
init|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"> start initializing SolrIndex {}"
operator|+
name|solrIndexName
argument_list|)
expr_stmt|;
name|initCores
operator|.
name|add
argument_list|(
name|solrIndexName
argument_list|)
expr_stmt|;
block|}
comment|//second check if the Index is an uninitialised one and if this is the case
comment|//try to get the ArchiveInputStream form the DataFileProvider
name|java
operator|.
name|util
operator|.
name|Properties
name|uninitialisedProperties
decl_stmt|;
synchronized|synchronized
init|(
name|uninitialisedCores
init|)
block|{
name|uninitialisedProperties
operator|=
name|uninitialisedCores
operator|.
name|get
argument_list|(
name|solrIndexName
argument_list|)
expr_stmt|;
if|if
condition|(
name|uninitialisedProperties
operator|!=
literal|null
condition|)
block|{
comment|//NOTE: this may override an parsed ArchiveInputStream
comment|// -> this is an error by the implementation of this class
comment|//    so throw an Exception to detect such errors early!
if|if
condition|(
name|ais
operator|!=
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"The parsed ArchiveInputStream is not null for an uninitialised Index. "
operator|+
literal|"Please report this error the the stanbol-def mailing list!"
argument_list|)
throw|;
block|}
try|try
block|{
name|ais
operator|=
name|lookupIndexArchive
argument_list|(
name|context
argument_list|,
name|solrIndexName
argument_list|,
name|uninitialisedProperties
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
name|warn
argument_list|(
literal|"The Index Archive for index {} not available (see \"Stanbol Data File Provider\" Tab of the Apache Webconsole for details)."
argument_list|,
name|solrIndexName
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|//third do the actual initialisation work
try|try
block|{
if|if
condition|(
name|ais
operator|!=
literal|null
condition|)
block|{
name|ConfigUtils
operator|.
name|copyCore
argument_list|(
name|ais
argument_list|,
name|coreDir
argument_list|,
name|solrIndexName
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|//try to remove from uninitialised
name|removeUninitialisedIndex
argument_list|(
name|solrIndexName
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|allowDefaultInitialisation
condition|)
block|{
comment|//TODO: Refactor so that the lookup via Bundle and/or jar
comment|//      file works via an internal implementation of an
comment|//      FileDataProvider
if|if
condition|(
name|context
operator|!=
literal|null
condition|)
block|{
comment|//load via bundle
name|ConfigUtils
operator|.
name|copyCore
argument_list|(
name|context
operator|.
name|getBundleContext
argument_list|()
operator|.
name|getBundle
argument_list|()
argument_list|,
name|coreDir
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//load from jar
name|ConfigUtils
operator|.
name|copyCore
argument_list|(
operator|(
name|Class
argument_list|<
name|?
argument_list|>
operator|)
literal|null
argument_list|,
name|coreDir
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to copy default configuration for Solr Index %s to the configured path %s"
argument_list|,
name|solrIndexName
operator|==
literal|null
condition|?
literal|""
else|:
name|solrIndexName
argument_list|,
name|managedCoreContainerDirectory
operator|.
name|getAbsoluteFile
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
comment|//regardless what happened remove the index from the currently init
comment|//indexes and notify all other waiting for the initialisation
synchronized|synchronized
init|(
name|initCores
init|)
block|{
comment|//initialisation done
name|initCores
operator|.
name|remove
argument_list|(
name|solrIndexName
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"   ... notify after trying to init SolrIndex {}"
operator|+
name|solrIndexName
argument_list|)
expr_stmt|;
comment|//notify that the initialisation completed or failed
name|initCores
operator|.
name|notifyAll
argument_list|()
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|//the dir exists
comment|//check if still initialising ... and wait until the initialisation
comment|//is complete
synchronized|synchronized
init|(
name|initCores
init|)
block|{
while|while
condition|(
name|initCores
operator|.
name|contains
argument_list|(
name|solrIndexName
argument_list|)
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"> wait for initialisation of SolrIndex "
operator|+
name|solrIndexName
argument_list|)
expr_stmt|;
try|try
block|{
name|initCores
operator|.
name|wait
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
comment|// a core is initialised ... back to work
block|}
block|}
block|}
block|}
return|return
name|coreDir
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.entityhub.yard.solr.impl.ManagedSolrDirectory#getManagedSolrDir()      */
specifier|public
name|File
name|getManagedDirectory
parameter_list|()
block|{
return|return
name|lookupManagedSolrDir
argument_list|(
name|componentContext
argument_list|)
return|;
block|}
comment|/**      * Lookup the location of the managed Solr directory. Also initialised the      * default configuration if the directory does not yet exist.      * @param context A reference to the component context or<code>null</code> if      * running outside an OSGI container. This is needed to avoid that       * {@link #deactivate(ComponentContext)} sets the context to<code>null</code>       * during this method does its initialisation work.      * @return the directory based on the current configuration      * @throws IllegalStateException in case this method is called when this      * component is running within an OSGI environment and it is deactivated.      */
specifier|private
name|File
name|lookupManagedSolrDir
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|IllegalStateException
block|{
if|if
condition|(
name|solrDataDir
operator|==
literal|null
condition|)
block|{
name|String
name|configuredDataDir
decl_stmt|;
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
comment|//load via system properties
name|configuredDataDir
operator|=
name|System
operator|.
name|getProperty
argument_list|(
name|MANAGED_SOLR_DIR_PROPERTY
argument_list|,
name|DEFAULT_SOLR_DATA_DIR
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//load via OSGI config
name|Object
name|value
init|=
name|context
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|MANAGED_SOLR_DIR_PROPERTY
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|configuredDataDir
operator|=
name|value
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|configuredDataDir
operator|=
name|DEFAULT_SOLR_DATA_DIR
expr_stmt|;
block|}
block|}
comment|//property substitution
name|configuredDataDir
operator|=
name|substituteProperty
argument_list|(
name|configuredDataDir
argument_list|,
name|context
operator|!=
literal|null
condition|?
name|context
operator|.
name|getBundleContext
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
comment|//determine the directory holding the SolrIndex
comment|/*              * NOTE:              * In case the configuredDataDir.isAbsolute()==false this code will              * initialise the index relative to the "user.dir" of the application.              */
if|if
condition|(
name|withinOSGI
operator|&&
name|context
operator|==
literal|null
condition|)
block|{
comment|//ensure to do not set an solrDataDir if this component is
comment|//running within an  OSGI environment and is deactivated
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to lookup managed Solr directories when component is deactivated!"
argument_list|)
throw|;
block|}
else|else
block|{
comment|//set the the absolute path
name|solrDataDir
operator|=
operator|new
name|File
argument_list|(
name|configuredDataDir
argument_list|)
expr_stmt|;
block|}
comment|//check if the "solr.xml" file exists in the directory
name|File
name|solrConf
init|=
operator|new
name|File
argument_list|(
name|solrDataDir
argument_list|,
literal|"solr.xml"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|solrConf
operator|.
name|exists
argument_list|()
condition|)
block|{
try|try
block|{
if|if
condition|(
name|context
operator|!=
literal|null
condition|)
block|{
comment|//load via bundle
name|solrDataDir
operator|=
name|ConfigUtils
operator|.
name|copyDefaultConfig
argument_list|(
name|context
operator|.
name|getBundleContext
argument_list|()
operator|.
name|getBundle
argument_list|()
argument_list|,
name|solrDataDir
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//load from jar
name|solrDataDir
operator|=
name|ConfigUtils
operator|.
name|copyDefaultConfig
argument_list|(
operator|(
name|Class
argument_list|<
name|?
argument_list|>
operator|)
literal|null
argument_list|,
name|solrDataDir
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
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
name|String
operator|.
name|format
argument_list|(
literal|"Unable to copy default configuration for the manages Solr Directory to the configured path %s!"
argument_list|,
name|solrDataDir
operator|.
name|getAbsoluteFile
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
return|return
name|solrDataDir
return|;
block|}
comment|/**      * Substitutes ${property.name} with the values retrieved via      * {@link System#getProperty(String, String)}. An empty string is used as      * default<p>      * Nested substitutions are NOTE supported. However multiple substitutions      * are supported.<p>      * If someone knows a default implementation feel free to replace!      * @param value the value to substitute      * @param bundleContext If not<code>null</code> the       * {@link BundleContext#getProperty(String)} is used instead of the       * {@link System#getProperty(String)}. By that it is possible to use      * OSGI only properties for substitution.      * @return the substituted value      */
specifier|private
specifier|static
name|String
name|substituteProperty
parameter_list|(
name|String
name|value
parameter_list|,
name|BundleContext
name|bundleContext
parameter_list|)
block|{
name|int
name|prevAt
init|=
literal|0
decl_stmt|;
name|int
name|foundAt
init|=
literal|0
decl_stmt|;
name|StringBuilder
name|substitution
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
while|while
condition|(
operator|(
name|foundAt
operator|=
name|value
operator|.
name|indexOf
argument_list|(
literal|"${"
argument_list|,
name|prevAt
argument_list|)
operator|)
operator|>=
name|prevAt
condition|)
block|{
name|substitution
operator|.
name|append
argument_list|(
name|value
operator|.
name|substring
argument_list|(
name|prevAt
argument_list|,
name|foundAt
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|propertyName
init|=
name|value
operator|.
name|substring
argument_list|(
name|foundAt
operator|+
literal|2
argument_list|,
name|value
operator|.
name|indexOf
argument_list|(
literal|'}'
argument_list|,
name|foundAt
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|propertyValue
init|=
name|bundleContext
operator|==
literal|null
condition|?
comment|//if no bundleContext is available
name|System
operator|.
name|getProperty
argument_list|(
name|propertyName
argument_list|)
else|:
comment|//use the System properties
name|bundleContext
operator|.
name|getProperty
argument_list|(
name|propertyName
argument_list|)
decl_stmt|;
name|substitution
operator|.
name|append
argument_list|(
name|propertyValue
operator|==
literal|null
condition|?
literal|""
else|:
name|propertyValue
argument_list|)
expr_stmt|;
name|prevAt
operator|=
name|foundAt
operator|+
name|propertyName
operator|.
name|length
argument_list|()
operator|+
literal|3
expr_stmt|;
comment|//+3 -> "${}".length
block|}
name|substitution
operator|.
name|append
argument_list|(
name|value
operator|.
name|substring
argument_list|(
name|prevAt
argument_list|,
name|value
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|substitution
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|IOException
block|{
name|componentContext
operator|=
name|context
expr_stmt|;
name|withinOSGI
operator|=
literal|true
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|java
operator|.
name|util
operator|.
name|Properties
argument_list|>
name|uninitIndexes
decl_stmt|;
synchronized|synchronized
init|(
name|uninitialisedCores
init|)
block|{
name|uninitialisedCores
operator|.
name|putAll
argument_list|(
name|loadUninitialisedIndexConfigs
argument_list|(
name|componentContext
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
synchronized|synchronized
init|(
name|uninitialisedCores
init|)
block|{
name|uninitialisedCores
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
name|componentContext
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_class

end_unit

