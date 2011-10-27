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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
package|;
end_package

begin_comment
comment|/**  * This interface provides methods to submit and delete content items to/from Contenthub.  *   * Stanbol provides default implementations of this interface for JCR and CMIS content repositories. However,  * it is also possible to provide custom implementations based on the needs of content repository. It is still  * possible to provide new implementations for JCR or CMIS repositories.<code>ContenthubFeederManager</code>  * gives higher priority to custom implementations when selecting the appropriate {@link ContenthubFeeder}  * instance.  *   * While submitting content items to Contenthub properties of content repository objects are provided as  * metadata of the content items. Supplied metadata is used to provide faceted search feature in the  * Contenthub.  *   * @author suat  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ContenthubFeeder
block|{
comment|/**      * Name of the factory for JCR Contenthub Feeder      */
specifier|public
specifier|static
specifier|final
name|String
name|JCR_CONTENTHUB_FEEDER_FACTORY
init|=
literal|"org.apache.stanbol.cmsadapter.jcr.mapping.JCRContenthubFeederFactory"
decl_stmt|;
comment|/**      * Name of the factory for CMIS Contenthub Feeder      */
specifier|public
specifier|static
specifier|final
name|String
name|CMIS_CONTENTUB_FEEDER_FACTORY
init|=
literal|"org.apache.stanbol.cmsadapter.cmis.mapping.CMISContenthubFeederFactory"
decl_stmt|;
comment|/**      * Session property for default JCR and CMIS Contenthub Feeder implementations      */
specifier|public
specifier|static
specifier|final
name|String
name|PROP_SESSION
init|=
literal|"org.apache.stanbol.cmsadapter.servicesapi.mapping.ContenthubFeeder.session"
decl_stmt|;
comment|/**      * Content properties property. It indicates the fields that holds the actual content in the content      * repository item.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROP_CONTENT_PROPERTIES
init|=
literal|"org.apache.stanbol.cmsadapter.servicesapi.mappingçContenthubFeeder.contentFields"
decl_stmt|;
comment|/**      * Submits content item by its ID to the Contenthub. If there is an already existing content item in the      * Contenthub with the same id, the existing content item should be deleted first.      *       * @param contentItemID      *            ID of the content item in the repository      */
name|void
name|submitContentItemByID
parameter_list|(
name|String
name|contentItemID
parameter_list|)
function_decl|;
comment|/**      * Submits content item by its path to the Contenthub. If there is an already existing content item in the      * Contenthub with the same id, the existing content item should be deleted first.      *       * @param contentItemPath      *            path of the content item in the repository      */
name|void
name|submitContentItemByPath
parameter_list|(
name|String
name|contentItemPath
parameter_list|)
function_decl|;
comment|/**      * Submits all of the content items under the specified path to the Contenthub. If there are already      * existing content items in the Contenthub with same ids of submitted content items, the existing content      * items should be deleted first.      *       * @param rootPath      *            root path in the content repository      */
name|void
name|submitContentItemsUnderPath
parameter_list|(
name|String
name|rootPath
parameter_list|)
function_decl|;
comment|/**      * Filters content items from content repository via the specific {@link ContentItemFilter} implementation      * passed as a parameter and submits the filtered content items to the Contenthub. If there are already      * existing content items in the Contenthub with same ids of submitted content items, the existing content      * items should be deleted first.      *       * @param customContentItemFilter      *            custom {@link ContentItemFilter} implementation      */
name|void
name|submitContentItemsByCustomFilter
parameter_list|(
name|ContentItemFilter
name|customContentItemFilter
parameter_list|)
function_decl|;
comment|/**      * Deletes content item by its ID from the Contenthub      *       * @param contentItemID      *            ID of the content item in the repository      */
name|void
name|deleteContentItemByID
parameter_list|(
name|String
name|contentItemID
parameter_list|)
function_decl|;
comment|/**      * Deletes content item by its path from the Contenthub      *       * @param contentItemPath      *            path of the content item in the repository      */
name|void
name|deleteContentItemByPath
parameter_list|(
name|String
name|contentItemPath
parameter_list|)
function_decl|;
comment|/**      * Deletes all of the content items under the specified path to Contenthub      *       * @param rootPath      *            root path in the content repository      */
name|void
name|deleteContentItemsUnderPath
parameter_list|(
name|String
name|rootPath
parameter_list|)
function_decl|;
comment|/**      * Filters content items from content repository via the specific {@link ContentItemFilter} implementation      * passed as a parameter and deletes the filtered content items from the Contenthub      *       * @param customContentItemFilter      *            custom {@link ContentItemFilter} implementation      */
name|void
name|deleteContentItemsByCustomFilter
parameter_list|(
name|ContentItemFilter
name|customContentItemFilter
parameter_list|)
function_decl|;
comment|/**      * Determines the specific implementation of {@link ContenthubFeeder} supports for the specified      *<connectionType>.      *       * @param connectionType      *            connection type for which an {@link RDFMapper} is requested      * @return whether certain implementation can handle specified connection type      */
name|boolean
name|canFeed
parameter_list|(
name|String
name|connectionType
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

