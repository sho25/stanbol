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
name|web
operator|.
name|resources
package|;
end_package

begin_import
import|import static
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
operator|.
name|TEXT_HTML
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
name|List
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Consumes
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|DELETE
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|DefaultValue
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|FormParam
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|GET
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|POST
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Produces
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Context
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Response
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Response
operator|.
name|Status
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
name|cmsadapter
operator|.
name|core
operator|.
name|mapping
operator|.
name|ContenthubFeederManager
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|ContenthubFeeder
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|ContenthubFeederException
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|ConnectionInfo
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccessException
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
name|cmsadapter
operator|.
name|web
operator|.
name|utils
operator|.
name|RestUtil
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
name|web
operator|.
name|base
operator|.
name|ContextHelper
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
name|web
operator|.
name|base
operator|.
name|resource
operator|.
name|BaseStanbolResource
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

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|view
operator|.
name|Viewable
import|;
end_import

begin_comment
comment|/**  * This resource provides services to submit content repository objects into Contenthub component. Submitted  * content items are also enhanced by<b>Stanbol Enhancer</b>. This service also enables deletion of content  * items from Contenthub.  *   * It basically delegates the request suitable {@link ContenthubFeeder} instance.  *   * @author suat  *   */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/cmsadapter/contenthubfeed"
argument_list|)
specifier|public
class|class
name|ContenthubFeedResource
extends|extends
name|BaseStanbolResource
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ContenthubFeedResource
operator|.
name|class
argument_list|)
decl_stmt|;
name|ContenthubFeederManager
name|feederManager
decl_stmt|;
specifier|public
name|ContenthubFeedResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
name|feederManager
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|ContenthubFeederManager
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|TEXT_HTML
argument_list|)
specifier|public
name|Response
name|get
parameter_list|()
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"index"
argument_list|,
name|this
argument_list|)
argument_list|,
name|TEXT_HTML
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|/**      * This service enables submission of content repository objects to Contenthub. Connection to the content      * repository is established by the provided connection information. This service makes possible to submit      * content items through either their IDs or paths in the content repository. Enhancements of content      * items are obtained through<b>Stanbol Enhancer</b> before submitting them to Contenthub.      *       *<p>      * If<code>id</code> parameter is set, the target object is obtained from the content repository      * according to its ID. If<code>path</code> parameter is set, first the ID of target object is obtained      * from the content repository and then the retrieved ID is used in submission of content item. When      *<code>path</code> parameter is set, it is also possible to process all content repository objects under      * the specified path by setting<code>recursive</code> parameter as<code>true</code>.      *       *<p>      * For some cases, it is necessary to know the property of the content repository object that keeps the      * actual content e.g while processing a nt:unstructured typed JCR content repository object. Such custom      * properties are specified within the<code>contentProperties</code> parameter.      *       *       * @param repositoryURL      * @param workspaceName      * @param username      * @param password      * @param connectionType      * @param id      *            content repository ID of the content item to be submitted      * @param path      *            content repository path of the content item to be submitted      * @param recursive      *            this parameter is used together with<code>path</code> parameter. Its default value is      *<code>false</code>. If it is set as<code>true</code>. All content repository objects under      *            the specified path are processed.      * @param contentProperties      *            this parameter indicates the list of properties that are possibly holding the actual      *            content. Possible values are passed as comma separated. Its default value is<b>content,      *            skos:definition</b>.      *       * @return      * @throws RepositoryAccessException      * @throws ContenthubFeederException      */
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|APPLICATION_FORM_URLENCODED
argument_list|)
specifier|public
name|Response
name|submitObjectsToContenthub
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"repositoryURL"
argument_list|)
name|String
name|repositoryURL
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"workspaceName"
argument_list|)
name|String
name|workspaceName
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"username"
argument_list|)
name|String
name|username
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"password"
argument_list|)
name|String
name|password
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"connectionType"
argument_list|)
name|String
name|connectionType
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"id"
argument_list|)
name|String
name|id
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"path"
argument_list|)
name|String
name|path
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"recursive"
argument_list|)
annotation|@
name|DefaultValue
argument_list|(
literal|"false"
argument_list|)
name|boolean
name|recursive
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"contentProperties"
argument_list|)
annotation|@
name|DefaultValue
argument_list|(
literal|"skos:definition,content"
argument_list|)
name|String
name|contentProperties
parameter_list|)
throws|throws
name|RepositoryAccessException
throws|,
name|ContenthubFeederException
block|{
name|repositoryURL
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|repositoryURL
argument_list|)
expr_stmt|;
name|workspaceName
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|workspaceName
argument_list|)
expr_stmt|;
name|username
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|username
argument_list|)
expr_stmt|;
name|password
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|password
argument_list|)
expr_stmt|;
name|connectionType
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|connectionType
argument_list|)
expr_stmt|;
name|id
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|path
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|path
argument_list|)
expr_stmt|;
name|contentProperties
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|contentProperties
argument_list|)
expr_stmt|;
if|if
condition|(
name|repositoryURL
operator|==
literal|null
operator|||
name|username
operator|==
literal|null
operator|||
name|password
operator|==
literal|null
operator|||
name|connectionType
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Repository URL, username, password and connection type parameters should not be null"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Repository URL, username, password and connection type parameters should not be null"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|contentFieldList
init|=
name|parseContentProperties
argument_list|(
name|contentProperties
argument_list|)
decl_stmt|;
name|ContenthubFeeder
name|feeder
init|=
name|feederManager
operator|.
name|getContenthubFeeder
argument_list|(
name|formConnectionInfo
argument_list|(
name|repositoryURL
argument_list|,
name|workspaceName
argument_list|,
name|username
argument_list|,
name|password
argument_list|,
name|connectionType
argument_list|)
argument_list|,
name|contentFieldList
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|!=
literal|null
condition|)
block|{
name|feeder
operator|.
name|submitContentItemByID
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|path
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|recursive
condition|)
block|{
name|feeder
operator|.
name|submitContentItemByPath
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|feeder
operator|.
name|submitContentItemsUnderPath
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"There is no parameter specified to select content repository objects\n"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
return|return
name|Response
operator|.
name|ok
argument_list|()
operator|.
name|build
argument_list|()
return|;
block|}
comment|/**      * This service enables deletion of content items from Contenthub. Connection to the content repository is      * established by the provided connection information. This service makes possible to delete content items      * through either their IDs or paths in the content repository.      *       *<p>      * If<code>id</code> parameter is set, the content item is directly tried to be deleted from Contenthub.      * If<code>path</code> parameter is set, the ID of the target object is first obtained from the content      * repository according to its path. Then retrieved ID is used to delete related content item from      * Contenthub.      *       * @param repositoryURL      * @param workspaceName      * @param username      * @param password      * @param connectionType      * @param id      *            content repository ID of the content item to be submitted      * @param path      *            content repository path of the content item to be submitted      * @param recursive      *            this parameter is used together with<code>path</code> parameter. Its default value is      *<code>false</code>. If it is set as<code>true</code>. All content repository objects under      *            the specified path are processed.      * @return      * @throws RepositoryAccessException      * @throws ContenthubFeederException      */
annotation|@
name|DELETE
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|APPLICATION_FORM_URLENCODED
argument_list|)
specifier|public
name|Response
name|deleteObjectsFromContenthub
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"repositoryURL"
argument_list|)
name|String
name|repositoryURL
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"workspaceName"
argument_list|)
name|String
name|workspaceName
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"username"
argument_list|)
name|String
name|username
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"password"
argument_list|)
name|String
name|password
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"connectionType"
argument_list|)
name|String
name|connectionType
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"id"
argument_list|)
name|String
name|id
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"path"
argument_list|)
name|String
name|path
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"recursive"
argument_list|)
annotation|@
name|DefaultValue
argument_list|(
literal|"false"
argument_list|)
name|boolean
name|recursive
parameter_list|)
throws|throws
name|RepositoryAccessException
throws|,
name|ContenthubFeederException
block|{
name|repositoryURL
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|repositoryURL
argument_list|)
expr_stmt|;
name|workspaceName
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|workspaceName
argument_list|)
expr_stmt|;
name|username
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|username
argument_list|)
expr_stmt|;
name|password
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|password
argument_list|)
expr_stmt|;
name|connectionType
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|connectionType
argument_list|)
expr_stmt|;
name|id
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|path
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|path
argument_list|)
expr_stmt|;
if|if
condition|(
name|repositoryURL
operator|==
literal|null
operator|||
name|username
operator|==
literal|null
operator|||
name|password
operator|==
literal|null
operator|||
name|connectionType
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Repository URL, username, password and connection type parameters should not be null"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Repository URL, username, password and connection type parameters should not be null"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|ContenthubFeeder
name|feeder
init|=
name|feederManager
operator|.
name|getContenthubFeeder
argument_list|(
name|formConnectionInfo
argument_list|(
name|repositoryURL
argument_list|,
name|workspaceName
argument_list|,
name|username
argument_list|,
name|password
argument_list|,
name|connectionType
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|!=
literal|null
condition|)
block|{
name|feeder
operator|.
name|deleteContentItemByID
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|path
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|recursive
condition|)
block|{
name|feeder
operator|.
name|deleteContentItemByPath
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|feeder
operator|.
name|deleteContentItemsUnderPath
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"There is no parameter specified to select content repository objects\n"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
return|return
name|Response
operator|.
name|ok
argument_list|()
operator|.
name|build
argument_list|()
return|;
block|}
specifier|private
name|ConnectionInfo
name|formConnectionInfo
parameter_list|(
name|String
name|repositoryURL
parameter_list|,
name|String
name|workspaceName
parameter_list|,
name|String
name|username
parameter_list|,
name|String
name|password
parameter_list|,
name|String
name|connectionType
parameter_list|)
block|{
name|ConnectionInfo
name|cInfo
init|=
operator|new
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
name|repository
operator|.
name|ConnectionInfo
argument_list|(
name|repositoryURL
argument_list|,
name|workspaceName
argument_list|,
name|username
argument_list|,
name|password
argument_list|,
name|connectionType
argument_list|)
decl_stmt|;
return|return
name|cInfo
return|;
block|}
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|parseContentProperties
parameter_list|(
name|String
name|contentProperties
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|fieldsList
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|contentProperties
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|fields
init|=
name|contentProperties
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
name|String
name|f
init|=
name|field
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|f
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|fieldsList
operator|.
name|add
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|fieldsList
return|;
block|}
block|}
end_class

end_unit
