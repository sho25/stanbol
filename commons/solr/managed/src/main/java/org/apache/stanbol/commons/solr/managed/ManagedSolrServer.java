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
name|solr
operator|.
name|managed
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
name|IOException
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
name|Properties
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
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrServer
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
name|core
operator|.
name|CoreContainer
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
name|core
operator|.
name|SolrCore
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
name|solr
operator|.
name|utils
operator|.
name|ConfigUtils
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
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_comment
comment|/**  * Service that provides an managed Solr server ({@link CoreContainer}).   * This interface allows to register activate, deactivate and remove indexes   * ({@link SolrCore}s)  *<p> TODO: Update Javadoc to new Interface!  * Note that is only refers to the Files and not the Solr server (or EmbeddedSolrServer). Users need to use  * the {@link SolrServerProvider#getSolrServer(SolrServerTypeEnum, String, String...)} to get an {@link SolrServer} instance  * based on the directory provided by this Interface.  *<p>  * The {@link #MANAGED_SOLR_DIR_PROPERTY} property can be used to define the location of the internally  * managed index. Implementations need to load this property by the {@link ComponentContext} if running within  * an OSGI container or otherwise use the system properties. In cases a relative path is configured the  * "user.dir" need to be used as base. Implementations need also to provide an default value in case no  * configuration is present.<br>  * Implementations need also support property substitution based on the system properties for the  * {@link #MANAGED_SOLR_DIR_PROPERTY}. E.g. parsing the value "${user.home}/.stanbol/indexes" will create the  * managed solr indexes within the home directory of the user.  *<p>  * This Service is also useful if one needs to initialise an own Solr Core for the manage Solr Server. In this  * case the {@link #getManagedDirectory()} method can be used to get the managed Solr directory and new  * indices can be added as sub directories. Utility methods for initialising cores are available as part of  * the {@link ConfigUtils}.  *   * @author Rupert Westenthaler  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ManagedSolrServer
block|{
comment|/**      * The name of this server      * @return      */
specifier|public
name|String
name|getServerName
parameter_list|()
function_decl|;
comment|/**      * This property can be used to configure the location of the internally managed EmbeddedSolrServer.      *<p>      * Configuring an absolute path (starting with {@link File#separatorChar}) will cause the index to be      * initialised in this directory.      *<p>      * Configuring an relative value will use      *<ul>      *<li>the working directory (<code>Systen.getProperty("user.dir")</code>) outside of an OSGI environment      *<li>the data directory provided by the SolrYard bundle (by calling      * {@link BundleContext#getDataFile(String)} with the relative path.      *</ul>      * In case this property is not present the {@link #DEFAULT_SOLR_DATA_DIR} (an relative path) is used.      */
name|String
name|MANAGED_SOLR_DIR_PROPERTY
init|=
literal|"org.apache.stanbol.commons.solr.managed.managedSolrDir"
decl_stmt|;
comment|/**      * Default value for the relative path used if the {@link #MANAGED_SOLR_DIR_PROPERTY} is not present. It      * is not required that implementations use this as default.      */
name|String
name|DEFAULT_SOLR_DATA_DIR
init|=
literal|"indexes"
decl_stmt|;
comment|/**      * Checks if a solrIndex with the parsed name is managed or not. Note that      * an Index might be managed, but not yet be initialised. To check if an      * index is managed and can be used use {@link #getIndexState(String)      * @param index the name of the Solr index to check      * @return<code>true</code> only if a Solr index with the parsed name is      * already present within the manages Solr directory.      * @throws IllegalStateException In case the managed Solr directory can not      * be obtained (usually indicates that this component is currently       * deactivated)      * @throws IllegalArgumentException In case<code>null</code> or an empty       * string is parsed as solrIndexName      */
name|boolean
name|isManagedIndex
parameter_list|(
name|String
name|indexName
parameter_list|)
function_decl|;
comment|/**      * Getter for the meta data for the index with the parsed name.      * @param indexName the name of the index      * @return the meta data or<code>null</code> if no index with the parsed name      * is managed by this server      * @throws IllegalArgumentException if<code>null</code> or an empty string      * is parsed as indexName.      */
name|IndexMetadata
name|getIndexMetadata
parameter_list|(
name|String
name|indexName
parameter_list|)
function_decl|;
comment|/**      * Checks if the managed index is also initialised and ready to be used.      *<p>      * Indexes are managed as soon as they are announced to the SolrDirectoryManager. However when using the      * {@link #createSolrIndex(String, String, Properties)} it can not be assured that the archive with      * the actual data is already available.      *<p>      *       * @param index      *            the name of the index      * @return the state of the index or<code>null</code> if not {@link #isManagedIndex(String)}      * @throws IllegalArgumentException if the parsed name is<code>null</code> or empty      */
name|ManagedIndexState
name|getIndexState
parameter_list|(
name|String
name|indexName
parameter_list|)
function_decl|;
comment|/**      * Getter for all indexes in a specific state      *       * @return A collection with the {@link IndexMetadata} of all managed      * indexes in that state. An empty collection in case no index is in the      * parsed state      * @throws IllegalArgumentException if<code>null</code> is parsed as state      */
name|Collection
argument_list|<
name|IndexMetadata
argument_list|>
name|getIndexes
parameter_list|(
name|ManagedIndexState
name|state
parameter_list|)
function_decl|;
comment|/**      * Getter for the directory of the parsed index. Implementations need to ensure that returned directories      * are valid Solr indices (or Solr Cores)      *<p>      * Directories returned by this method are typically used as second parameter of      * {@link SolrServerProvider#getSolrServer(SolrServerTypeEnum, String, String...)} to create an {@link SolrServer}      * instance.      *<p>      * If the requested Index is currently initialising, than this method MUST      * wait until the initialisation is finished before returning.       *       * @param name      *            the name of the requested solr index.       * @return the directory (instanceDir) of the index or<code>null</code> a      *         SolrIndex with that name is not managed.      * @throws IllegalArgumentException      *             if the parsed solrIndexName is<code>null</code> or empty      */
name|File
name|getSolrIndexDirectory
parameter_list|(
name|String
name|indexName
parameter_list|)
function_decl|;
comment|/**      * Creates a new Solr Index based on the data in the provided {@link ArchiveInputStream}      *       * @param name      *            the name of the index to create      * @param ais      *            the stream providing the data for the new index      * @return the directory (instanceDir) of the index.      * @throws IOException      *             On any error while reading from the parsed input stream      * @throws SAXException      *             On any Error while parsing the {@link SolrCore} configuration      *             files when registering the core for the parsed data.      * @throws IllegalArgumentException      *             if the parsed solrIndexName is<code>null</code> or empty      */
name|IndexMetadata
name|createSolrIndex
parameter_list|(
name|String
name|indexName
parameter_list|,
name|ArchiveInputStream
name|ais
parameter_list|)
throws|throws
name|IOException
throws|,
name|SAXException
function_decl|;
comment|/**      * Creates a new {@link SolrCore} based on looking up the Index data via the {@link DataFileProvider} service      *       * @param coreName      *            The name of the solrIndex to create      * @param indexPath      *            the name of the dataFile looked up via the {@link DataFileProvider}      * @param properties      *            Additional properties describing the index      * @return the directory (instanceDir) of the index or null if the index data could not be found      * @throws IllegalArgumentException      * @throws IOException      */
name|IndexMetadata
name|createSolrIndex
parameter_list|(
name|String
name|indexName
parameter_list|,
name|String
name|indexPath
parameter_list|,
name|Properties
name|properties
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Creates/Updates the core with the parsed name to the data parsed within the      * {@link ArchiveInputStream}.      * @param name The name for the Index to create/update      * @param ais The {@link ArchiveInputStream} used to read the data for the       *             Index to create/update      * @return The metadata for the created index      * @throws IOException      *             On ansy Error while copying the data provided by the parsed      *             {@link ArchiveInputStream}      * @throws SAXException      *             On any Error while parsing the {@link SolrCore} configuration      *             files when registering the core for the parsed data.      */
name|IndexMetadata
name|updateIndex
parameter_list|(
name|String
name|indexName
parameter_list|,
name|ArchiveInputStream
name|ais
parameter_list|)
throws|throws
name|IOException
throws|,
name|SAXException
function_decl|;
comment|/**      * Updates the data of the core with the parsed name with the data provided      * by the resource with the parsed name. The resource is loaded by using the      * {@link DataFileProvider} infrastructure      * @param name      * @param resourceName      * @param properties      * @throws IOException      */
name|IndexMetadata
name|updateIndex
parameter_list|(
name|String
name|indexName
parameter_list|,
name|String
name|resourceName
parameter_list|,
name|Properties
name|properties
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Removes the index with the parsed name and optionally also deletes the      * data on the file system.      * @param name      */
name|void
name|removeIndex
parameter_list|(
name|String
name|indexName
parameter_list|,
name|boolean
name|deleteFiles
parameter_list|)
function_decl|;
comment|/**      * Sets the index with the parsed name to the {@link ManagedIndexState#INACTIVE}      * state. If the referenced index in not {@link ManagedIndexState#ACTIVE} this      * method has no effect.      * @param indexName the name of the index      * @return The current meta data for that index or<code>null</code> if no      * index with the parsed name is managed.      * @throws IllegalArgumentException if the parsed indexName is<code>null</code>      * or an empty string      */
name|IndexMetadata
name|deactivateIndex
parameter_list|(
name|String
name|indexName
parameter_list|)
function_decl|;
comment|/**      * Can be used to activate an {@link ManagedIndexState#INACTIVE} index.<p>      * This does not try to update the index data or to create an index with the      * parsed name.<p>      * If the referenced index is not within the INACTIVE state this call might      * be ignored. The resulting state of the index can be retrieved from the      * returned {@link IndexMetadata} instance.       * @param indexName the name of the index      * @return the {@link IndexMetadata} or<code>null</code> if no index with      * the parsed name is managed by this server.      * @throws IOException      *             On ansy Error while copying the data provided by the parsed      *             {@link ArchiveInputStream}      * @throws SAXException      *             On any Error while parsing the {@link SolrCore} configuration      *             files when registering the core for the parsed data.      * @throws IllegalArgumentException if<code>null</code> or an empty string      * is parsed as indexName      */
name|IndexMetadata
name|activateIndex
parameter_list|(
name|String
name|indexName
parameter_list|)
throws|throws
name|IOException
throws|,
name|SAXException
function_decl|;
comment|/**      * Getter for the directory on the local file system used as working directory      * for the {@link CoreContainer} managed by this component.      *       * @return the directory of the Solr Home used for the internally managed {@link CoreContainer} or      *<code>null</code> if running within an OSGI Environment and this component is deactivated.      * @throws IllegalStateException      *             In case the managed Solr directory can not be obtained (usually indicates that this      *             component is currently deactivated) or initialised.      */
name|File
name|getManagedDirectory
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

