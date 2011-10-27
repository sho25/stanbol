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
name|core
operator|.
name|mapping
package|;
end_package

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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|CopyOnWriteArrayList
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
name|ReferenceCardinality
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
name|ReferencePolicy
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
name|ReferenceStrategy
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
name|RepositoryAccess
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
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccessManager
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
name|ComponentFactory
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
name|ComponentInstance
import|;
end_import

begin_comment
comment|/**  * This class manages the {@link ContenthubFeeder} instances loaded in the OSGI environment.  *   * @author suat  *   */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|ContenthubFeederManager
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ContenthubFeederManager
block|{
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|MANDATORY_UNARY
argument_list|,
name|target
operator|=
literal|"(component.factory="
operator|+
name|ContenthubFeeder
operator|.
name|JCR_CONTENTHUB_FEEDER_FACTORY
operator|+
literal|")"
argument_list|)
specifier|private
name|ComponentFactory
name|defaultJCRContentHubFeederFactory
decl_stmt|;
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|MANDATORY_UNARY
argument_list|,
name|target
operator|=
literal|"(component.factory="
operator|+
name|ContenthubFeeder
operator|.
name|CMIS_CONTENTUB_FEEDER_FACTORY
operator|+
literal|")"
argument_list|)
specifier|private
name|ComponentFactory
name|defaultCMISContentHubFeederFactory
decl_stmt|;
comment|/*      * Holds additional ContenthubFeeder implementations if there is any.      */
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_MULTIPLE
argument_list|,
name|referenceInterface
operator|=
name|ContenthubFeeder
operator|.
name|class
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|,
name|bind
operator|=
literal|"bindContenthubFeeder"
argument_list|,
name|unbind
operator|=
literal|"unbindContenthubFeeder"
argument_list|,
name|strategy
operator|=
name|ReferenceStrategy
operator|.
name|EVENT
argument_list|)
name|List
argument_list|<
name|ContenthubFeeder
argument_list|>
name|boundedFeeders
init|=
operator|new
name|CopyOnWriteArrayList
argument_list|<
name|ContenthubFeeder
argument_list|>
argument_list|()
decl_stmt|;
comment|/*      * Holds instances of default implementations for JCR and CMIS ContenthubFeeders for different connection      * information. Destruction of invalid instances (instances having invalid sessions) may be implemented.      */
specifier|private
name|Map
argument_list|<
name|ConnectionInfo
argument_list|,
name|ComponentInstance
argument_list|>
name|defaultFeeders
init|=
name|Collections
operator|.
name|synchronizedMap
argument_list|(
operator|new
name|HashMap
argument_list|<
name|ConnectionInfo
argument_list|,
name|ComponentInstance
argument_list|>
argument_list|()
argument_list|)
decl_stmt|;
annotation|@
name|Reference
name|RepositoryAccessManager
name|accessManager
decl_stmt|;
comment|/**      * Looks for a suitable {@link ContenthubFeeder} instance based on the provided      *<code>connectionInfo</code>.      *       * @param connectionInfo      * @return {@link ContenthubFeeder} instance.      * @throws RepositoryAccessException      * @throws ContenthubFeederException      * @see #getContenthubFeeder(ConnectionInfo, List)      */
specifier|public
name|ContenthubFeeder
name|getContenthubFeeder
parameter_list|(
name|ConnectionInfo
name|connectionInfo
parameter_list|)
throws|throws
name|RepositoryAccessException
throws|,
name|ContenthubFeederException
block|{
return|return
name|getContenthubFeeder
argument_list|(
name|connectionInfo
argument_list|,
literal|null
argument_list|)
return|;
block|}
comment|/**      * Looks for a suitable {@link ContenthubFeeder} instance based on the provided      *<code>connectionInfo</code>. If there is an instance of a custom implementation provided in the      * environment suitable for the connection type, it is retrieved first. If there is no custom      * implementations in the OSGI environment, a new instance created based on the given      *<code>connectionInfo</code> or an already existing one is returned.      *       * @param connectionInfo      * @return {@link ContenthubFeeder} instance.      * @throws RepositoryAccessException      * @throws ContenthubFeederException      */
annotation|@
name|SuppressWarnings
argument_list|(
block|{
literal|"rawtypes"
block|,
literal|"unchecked"
block|}
argument_list|)
specifier|public
name|ContenthubFeeder
name|getContenthubFeeder
parameter_list|(
name|ConnectionInfo
name|connectionInfo
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|contentProperties
parameter_list|)
throws|throws
name|RepositoryAccessException
throws|,
name|ContenthubFeederException
block|{
comment|// check additional implementations according to their connection types
name|String
name|connectionType
init|=
name|connectionInfo
operator|.
name|getConnectionType
argument_list|()
decl_stmt|;
for|for
control|(
name|ContenthubFeeder
name|feeder
range|:
name|boundedFeeders
control|)
block|{
if|if
condition|(
name|feeder
operator|.
name|canFeed
argument_list|(
name|connectionType
argument_list|)
condition|)
block|{
return|return
name|feeder
return|;
block|}
block|}
comment|// check default feeder instances
comment|// TODO: check whether the session object is still valid
name|ComponentInstance
name|componentInstance
init|=
name|defaultFeeders
operator|.
name|get
argument_list|(
name|connectionInfo
argument_list|)
decl_stmt|;
if|if
condition|(
name|componentInstance
operator|!=
literal|null
condition|)
block|{
name|Object
name|value
init|=
name|componentInstance
operator|.
name|getInstance
argument_list|()
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|ContenthubFeeder
operator|)
name|value
return|;
block|}
else|else
block|{
comment|/*                  * Default feeder instance for this connection info is not available, so remove it from the                  * map                  */
name|defaultFeeders
operator|.
name|remove
argument_list|(
name|connectionInfo
argument_list|)
expr_stmt|;
block|}
block|}
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
name|model
operator|.
name|web
operator|.
name|ConnectionInfo
name|cInfo
init|=
name|mapConnectionInfo
argument_list|(
name|connectionInfo
argument_list|)
decl_stmt|;
name|RepositoryAccess
name|repositoryAccess
init|=
name|accessManager
operator|.
name|getRepositoryAccessor
argument_list|(
name|cInfo
argument_list|)
decl_stmt|;
name|Object
name|session
init|=
name|repositoryAccess
operator|.
name|getSession
argument_list|(
name|cInfo
argument_list|)
decl_stmt|;
specifier|final
name|Dictionary
name|props
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|props
operator|.
name|put
argument_list|(
name|ContenthubFeeder
operator|.
name|PROP_SESSION
argument_list|,
name|session
argument_list|)
expr_stmt|;
name|props
operator|.
name|put
argument_list|(
name|ContenthubFeeder
operator|.
name|PROP_CONTENT_PROPERTIES
argument_list|,
name|contentProperties
argument_list|)
expr_stmt|;
if|if
condition|(
name|connectionType
operator|.
name|equals
argument_list|(
name|ConnectionInfo
operator|.
name|JCR_CONNECTION_STRING
argument_list|)
condition|)
block|{
name|componentInstance
operator|=
name|defaultJCRContentHubFeederFactory
operator|.
name|newInstance
argument_list|(
name|props
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|connectionType
operator|.
name|equals
argument_list|(
name|ConnectionInfo
operator|.
name|CMIS_CONNECTION_STRING
argument_list|)
condition|)
block|{
name|componentInstance
operator|=
name|defaultCMISContentHubFeederFactory
operator|.
name|newInstance
argument_list|(
name|props
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|ContenthubFeederException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unexpected default connection type: %s"
argument_list|,
name|connectionType
argument_list|)
argument_list|)
throw|;
block|}
name|ContenthubFeeder
name|feeder
init|=
operator|(
name|ContenthubFeeder
operator|)
name|componentInstance
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|defaultFeeders
operator|.
name|put
argument_list|(
name|connectionInfo
argument_list|,
name|componentInstance
argument_list|)
expr_stmt|;
return|return
name|feeder
return|;
block|}
specifier|private
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
name|model
operator|.
name|web
operator|.
name|ConnectionInfo
name|mapConnectionInfo
parameter_list|(
name|ConnectionInfo
name|connectionInfo
parameter_list|)
block|{
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
name|model
operator|.
name|web
operator|.
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
name|model
operator|.
name|web
operator|.
name|ConnectionInfo
argument_list|()
decl_stmt|;
name|cInfo
operator|.
name|setConnectionType
argument_list|(
name|connectionInfo
operator|.
name|getConnectionType
argument_list|()
argument_list|)
expr_stmt|;
name|cInfo
operator|.
name|setPassword
argument_list|(
name|connectionInfo
operator|.
name|getPassword
argument_list|()
argument_list|)
expr_stmt|;
name|cInfo
operator|.
name|setUsername
argument_list|(
name|connectionInfo
operator|.
name|getUsername
argument_list|()
argument_list|)
expr_stmt|;
name|cInfo
operator|.
name|setRepositoryURL
argument_list|(
name|connectionInfo
operator|.
name|getRepositoryURL
argument_list|()
argument_list|)
expr_stmt|;
name|cInfo
operator|.
name|setWorkspaceName
argument_list|(
name|connectionInfo
operator|.
name|getWorkspaceIdentifier
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|cInfo
return|;
block|}
specifier|protected
name|void
name|bindContenthubFeeder
parameter_list|(
name|ContenthubFeeder
name|contenthubFeeder
parameter_list|)
block|{
name|boundedFeeders
operator|.
name|add
argument_list|(
name|contenthubFeeder
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|unbindContenthubFeeder
parameter_list|(
name|ContenthubFeeder
name|contenthubFeeder
parameter_list|)
block|{
name|boundedFeeders
operator|.
name|remove
argument_list|(
name|contenthubFeeder
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

