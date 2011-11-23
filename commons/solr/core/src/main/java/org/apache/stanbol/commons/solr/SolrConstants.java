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
name|commons
operator|.
name|solr
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
name|osgi
operator|.
name|framework
operator|.
name|Constants
import|;
end_import

begin_comment
comment|/**  * Defines the keys used to register {@link SolrCore}s as OSGI services  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|SolrConstants
block|{
specifier|private
name|SolrConstants
parameter_list|()
block|{
comment|/*do not create instances*/
block|}
comment|/**      * Used as prefix for all {@link CoreContainer} related properties      */
specifier|private
specifier|static
specifier|final
name|String
name|PROPERTY_SOLR_SERVER
init|=
literal|"org.apache.solr.core.CoreContainer"
decl_stmt|;
comment|/**      * Used as prefix for all {@link SolrCore} related properties      */
specifier|private
specifier|static
specifier|final
name|String
name|PROPERTY_SOLR_CORE
init|=
literal|"org.apache.solr.core.SolrCore"
decl_stmt|;
comment|/**      * Property used for the human readable name of a SolrServer. This will be used      * as alternative to the absolute file path of the solr.xml file used for the      * initialisation of the solr server      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_SERVER_NAME
init|=
name|PROPERTY_SOLR_SERVER
operator|+
literal|".name"
decl_stmt|;
comment|/**      * The directory of the SolrServer. Values are expected to be {@link File}       * objects with<code>{@link File#isDirectory()}==true</code> or {@link String}      * values containing a file path. The {@link File#getAbsolutePath()} will be      * used to initialise the SolrServer.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_SERVER_DIR
init|=
name|PROPERTY_SOLR_SERVER
operator|+
literal|".dir"
decl_stmt|;
comment|/**      * the name of the solr.xml file defining the configuration for the Solr      * Server. If not defined {@link #SOLR_XML_NAME} is used as default<p>      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_SOLR_XML_NAME
init|=
name|PROPERTY_SOLR_SERVER
operator|+
literal|".solrXml"
decl_stmt|;
comment|/**      * The registered {@link SolrCore} names for this server. Values are expected      * to be a read only collection of names.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_SERVER_CORES
init|=
name|PROPERTY_SOLR_SERVER
operator|+
literal|".cores"
decl_stmt|;
comment|/**      * The {@link Constants#SERVICE_RANKING service ranking} of the Solr server.      * If not defined that '0' is used as default.<p>      * Values are expected to be Integers. This Property uses       * {@link Constants#SERVICE_RANKING} as key.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_SERVER_RANKING
init|=
name|Constants
operator|.
name|SERVICE_RANKING
decl_stmt|;
comment|/**      * Allows to enable/disable the publishing of the RESTful interface of Solr      * on the OSGI HttpService by using the value of the {@link #PROPERTY_SERVER_NAME}      * as path.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_SERVER_PUBLISH_REST
init|=
name|PROPERTY_SOLR_SERVER
operator|+
literal|".publishREST"
decl_stmt|;
comment|/**      * By default the RESTful API of a SolrServer is published      */
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_PUBLISH_REST
init|=
literal|true
decl_stmt|;
comment|/**      * Property used for the name of a solr core. This is typically set by the      * {@link SolrServerAdapter} implementation based on the name of the       * cores registered with a SolrServer.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_CORE_NAME
init|=
name|PROPERTY_SOLR_CORE
operator|+
literal|".name"
decl_stmt|;
comment|/**      * The directory of this core. This needs to be set if the      * core is not located within a sub-directory within the      * {@link #PROPERTY_SERVER_DIR} with the name {@link #PROPERTY_CORE_NAME}.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_CORE_DIR
init|=
name|PROPERTY_SOLR_CORE
operator|+
literal|".dir"
decl_stmt|;
comment|/**      * The data directory of a core. Set by the {@link SolrServerAdapter} when      * registering a SolrCore based on {@link SolrCore#getDataDir()}      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_CORE_DATA_DIR
init|=
name|PROPERTY_SOLR_CORE
operator|+
literal|".dadadir"
decl_stmt|;
comment|/**      * The index directory of a core. Set by the {@link SolrServerAdapter} when      * registering a SolrCore based on {@link SolrCore#getIndexDir()}      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_CORE_INDEX_DIR
init|=
name|PROPERTY_SOLR_CORE
operator|+
literal|".indexdir"
decl_stmt|;
comment|/**      * The name of the "schema.xml" file defining the solr schema for this core.      * If not defined {@link #SOLR_SCHEMA_NAME} is used as default.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_CORE_SCHEMA
init|=
name|PROPERTY_SOLR_CORE
operator|+
literal|".schema"
decl_stmt|;
comment|/**      * The name of the "solrconf.xml" file defining the configuration for this      * core. If not defined {@link #SOLR_SCHEMA_NAME} is used as default.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_CORE_SOLR_CONF
init|=
name|PROPERTY_SOLR_CORE
operator|+
literal|".solrconf"
decl_stmt|;
comment|/**      * The {@link Constants#SERVICE_ID} of the {@link CoreContainer} this core      * is registered with      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_CORE_SERVER_ID
init|=
name|PROPERTY_SOLR_CORE
operator|+
literal|".service.id"
decl_stmt|;
comment|/**      * The {@link Constants#SERVICE_RANKING service ranking} of the SolrCore.       * The ranking of the SolrServer is used as default if not defined. If also no       * ServiceRanking is defined for the server than '0' is used.<p>      * Values are expected to be Integers. This Property uses       * {@link Constants#SERVICE_RANKING} as key.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_CORE_RANKING
init|=
name|Constants
operator|.
name|SERVICE_RANKING
decl_stmt|;
comment|/**      * Default name of the solr.xml file needed for the initialisation of a      * {@link CoreContainer Solr server}       */
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_XML_NAME
init|=
literal|"solr.xml"
decl_stmt|;
comment|/**      * default name of the Solrconfig.xml file needed for the initialisation of       * a {@link SolrCore}      */
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_CONFIG_NAME
init|=
literal|"solrconfig.xml"
decl_stmt|;
comment|/**      * Defualt name of the schema.xml file needed for the initialisation of       * a {@link SolrCore}      */
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_SCHEMA_NAME
init|=
literal|"schema.xml"
decl_stmt|;
block|}
end_class

end_unit

