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
name|commons
operator|.
name|solr
operator|.
name|SolrConstants
operator|.
name|PROPERTY_CORE_DIR
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
name|commons
operator|.
name|solr
operator|.
name|SolrConstants
operator|.
name|PROPERTY_CORE_NAME
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
name|commons
operator|.
name|solr
operator|.
name|SolrConstants
operator|.
name|PROPERTY_CORE_SERVER_ID
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
name|commons
operator|.
name|solr
operator|.
name|SolrConstants
operator|.
name|PROPERTY_SERVER_DIR
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
name|commons
operator|.
name|solr
operator|.
name|SolrConstants
operator|.
name|PROPERTY_SERVER_NAME
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Constants
operator|.
name|SERVICE_PID
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
name|Arrays
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
name|List
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
name|NameFileFilter
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
name|Service
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
name|client
operator|.
name|solrj
operator|.
name|embedded
operator|.
name|EmbeddedSolrServer
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
name|IndexReference
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
name|SolrConstants
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
name|SolrServerAdapter
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
name|SolrServerProvider
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
name|SolrServerTypeEnum
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
name|solr
operator|.
name|utils
operator|.
name|ServiceReferenceRankingComparator
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
name|InvalidSyntaxException
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
comment|/**  * {@link SolrServerProvider} that supports all {@link CoreContainer}s and  * {@link SolrCore}s registered as OSGI services.<p>  * This implementation is intended to be used with {@link CoreContainer}s and  * {@link SolrCore}s as registered by the {@link SolrServerAdapter} however it  * can work with all {@link CoreContainer}s and {@link SolrCore}s if they provide  * the required meta data.  * @author Rupert Westenthaler  *   */
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
specifier|public
class|class
name|RegisteredSolrServerProvider
implements|implements
name|SolrServerProvider
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
name|RegisteredSolrServerProvider
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// define the default values here because they are not accessible via the Solr API
specifier|private
name|ServiceTracker
name|coreTracker
decl_stmt|;
specifier|private
name|ServiceTracker
name|serverTracker
decl_stmt|;
specifier|public
name|RegisteredSolrServerProvider
parameter_list|()
block|{}
comment|/**      * This supports the following uriOrPath values to lookup internally      * managed {@link SolrCore}s and to return an {@link EmbeddedSolrServer}      * instance:<ol>      *<li> file URLs to the Core directory      *<li> file paths to the Core directory      *<li> [{server-name}:]{core-name} where both the server-name and the      *      core-name MUST NOT contain '/' and '\' (on windows) chars. If the       *      server-name is not specified the server with the highest       *      {@link Constants#SERVICE_RANKING} is assumed.      *</ol><p>      * If the server-name is not known (the case for the first two options),      * than<code>null</code> is returned if the referenced Core is not present.      * In case of the third option an {@link EmbeddedSolrServer} instance is      * also returned if the referenced SolrServer does currently not define      * a core with the specified name. Therefore users are encouraged to test      * by calling {@link SolrServer#ping()} whether the core is ready to be used      * or not. This behaviour was chosen to avoid the need to wait for the       * completion of the initialisation of a Core. In addition typically users      * might not know if a used SolrCore is local (embedded) or accessed via      * the RESTful interface. Therefore code for dealing with temporarily      * unavailable services is typically needed anyway.      */
annotation|@
name|Override
specifier|public
name|SolrServer
name|getSolrServer
parameter_list|(
name|SolrServerTypeEnum
name|type
parameter_list|,
name|String
name|uriOrPath
parameter_list|,
name|String
modifier|...
name|additional
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"getSolrServer Request for {} and path {}"
argument_list|,
name|type
argument_list|,
name|uriOrPath
argument_list|)
expr_stmt|;
if|if
condition|(
name|uriOrPath
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The name of requested SolrCore MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|type
operator|!=
name|SolrServerTypeEnum
operator|.
name|EMBEDDED
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Parsed SolrServer type '"
operator|+
name|type
operator|+
literal|"' is not supported by this Implementation (suppoted: '"
operator|+
name|SolrServerTypeEnum
operator|.
name|EMBEDDED
operator|+
literal|"')!"
argument_list|)
throw|;
block|}
comment|//(1) parse the pathOrUir -> serverName& coreName
comment|//        String serverName;
name|String
name|coreName
decl_stmt|;
name|IndexReference
name|indexReference
init|=
name|IndexReference
operator|.
name|parse
argument_list|(
name|uriOrPath
argument_list|)
decl_stmt|;
name|ServiceReference
name|serverRef
decl_stmt|;
if|if
condition|(
name|indexReference
operator|.
name|getServer
argument_list|()
operator|!=
literal|null
condition|)
block|{
comment|//            if(indexReference.getServer().isEmpty()){
comment|//                serverRef = getDefaultServerReference();
comment|//            } else {
name|serverRef
operator|=
name|getServerReference
argument_list|(
name|indexReference
operator|.
name|getServer
argument_list|()
argument_list|)
expr_stmt|;
comment|//            }
if|if
condition|(
name|serverRef
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"> {} SolrServer {} is currently not present"
argument_list|,
name|indexReference
operator|.
name|getServer
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|"Default"
else|:
literal|"Parsed"
argument_list|,
name|indexReference
operator|.
name|getServer
argument_list|()
argument_list|)
expr_stmt|;
name|coreName
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|coreName
operator|=
name|indexReference
operator|.
name|getIndex
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
name|ServiceReference
name|coreRef
init|=
name|getCoreReference
argument_list|(
name|indexReference
operator|.
name|getIndex
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|coreRef
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"> Unable to locate Core '{}' on any active Solr Server"
argument_list|,
name|indexReference
operator|.
name|getIndex
argument_list|()
argument_list|)
expr_stmt|;
name|serverRef
operator|=
literal|null
expr_stmt|;
name|coreName
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|serverRef
operator|=
name|getServerReference
argument_list|(
name|coreRef
argument_list|)
expr_stmt|;
if|if
condition|(
name|serverRef
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"> SolrServer for Core {} no longer available "
argument_list|,
name|indexReference
operator|.
name|getIndex
argument_list|()
argument_list|)
expr_stmt|;
name|coreName
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|coreName
operator|=
operator|(
name|String
operator|)
name|coreRef
operator|.
name|getProperty
argument_list|(
name|PROPERTY_CORE_NAME
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|serverRef
operator|!=
literal|null
condition|)
block|{
comment|//we need not to check for the core -> create anyway!
return|return
operator|new
name|EmbeddedSolrServer
argument_list|(
operator|(
name|CoreContainer
operator|)
name|serverTracker
operator|.
name|getService
argument_list|(
name|serverRef
argument_list|)
argument_list|,
name|coreName
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Getter for the reference to the tracked {@link CoreContainer} instance       * with the highest {@link Constants#SERVICE_RANKING} - the default       * Server      */
specifier|private
name|ServiceReference
name|getDefaultServerReference
parameter_list|()
block|{
name|ServiceReference
index|[]
name|refs
init|=
name|serverTracker
operator|.
name|getServiceReferences
argument_list|()
decl_stmt|;
if|if
condition|(
name|refs
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
if|if
condition|(
name|refs
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|Arrays
operator|.
name|sort
argument_list|(
name|refs
argument_list|,
name|ServiceReferenceRankingComparator
operator|.
name|INSTANCE
argument_list|)
expr_stmt|;
block|}
return|return
name|refs
index|[
literal|0
index|]
return|;
block|}
block|}
comment|/**      * Getter for the Reference to the {@link CoreContainer} for the {@link SolrCore}      * referenced bythe parsed {@link ServiceReference}.<p>      *<b>IMPLEMENTATION NOTE:</b><br>      * This method Iterates over all tracked {@link ServiceReference} and      * can therefore be assumed as low-performance. However the assumptions are that      * first there are only a few tracked services and this method is not called      * frequently. If one of this assumptions is not true one should consider to      * re-implement this by building an in-memory model of tracked       * {@link CoreContainer}s and {@link SolrCore}s with the required indexes      * required to implement lookups like that.      * @param name the name (or path) of the {@link SolrCore}      * @return the reference to the {@link CoreContainer} or<code>null</code>      * if not found      */
specifier|private
name|ServiceReference
name|getServerReference
parameter_list|(
name|ServiceReference
name|coreRef
parameter_list|)
block|{
if|if
condition|(
name|coreRef
operator|!=
literal|null
condition|)
block|{
name|Long
name|serverId
init|=
operator|(
name|Long
operator|)
name|coreRef
operator|.
name|getProperty
argument_list|(
name|PROPERTY_CORE_SERVER_ID
argument_list|)
decl_stmt|;
if|if
condition|(
name|serverId
operator|!=
literal|null
condition|)
block|{
name|ServiceReference
index|[]
name|serverRefs
init|=
name|serverTracker
operator|.
name|getServiceReferences
argument_list|()
decl_stmt|;
if|if
condition|(
name|serverRefs
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|ServiceReference
name|serverRef
range|:
name|serverRefs
control|)
block|{
if|if
condition|(
name|serverId
operator|.
name|equals
argument_list|(
name|serverRef
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_ID
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|serverRef
return|;
block|}
block|}
block|}
return|return
literal|null
return|;
block|}
else|else
block|{
comment|//search based on name
name|String
name|serverName
init|=
operator|(
name|String
operator|)
name|coreRef
operator|.
name|getProperty
argument_list|(
name|PROPERTY_SERVER_NAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|serverName
operator|!=
literal|null
condition|)
block|{
name|ServiceReference
index|[]
name|serverRefs
init|=
name|serverTracker
operator|.
name|getServiceReferences
argument_list|()
decl_stmt|;
if|if
condition|(
name|serverRefs
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|ServiceReference
name|serverRef
range|:
name|serverRefs
control|)
block|{
if|if
condition|(
name|serverName
operator|.
name|equals
argument_list|(
name|serverRef
operator|.
name|getProperty
argument_list|(
name|PROPERTY_SERVER_NAME
argument_list|)
argument_list|)
condition|)
block|{
comment|//we need to get the name, because maybe the path was parsed
name|String
name|coreName
init|=
operator|(
name|String
operator|)
name|coreRef
operator|.
name|getProperty
argument_list|(
name|PROPERTY_CORE_NAME
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|?
argument_list|>
name|cores
init|=
operator|(
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
name|serverRef
operator|.
name|getProperty
argument_list|(
name|SolrConstants
operator|.
name|PROPERTY_SERVER_CORES
argument_list|)
decl_stmt|;
if|if
condition|(
name|cores
operator|.
name|contains
argument_list|(
name|coreName
argument_list|)
condition|)
block|{
return|return
name|serverRef
return|;
block|}
comment|//else the wrong server
block|}
comment|//else  server with wrong server name
block|}
block|}
return|return
literal|null
return|;
comment|//not found
block|}
else|else
block|{
comment|//no PROPERTY_CORE_SERVER_PID and PROPERTY_SERVER_NAME
comment|//property for this core
return|return
literal|null
return|;
block|}
block|}
block|}
else|else
block|{
comment|//the requested core was not found
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Getter for the {@link ServiceReference} with the highest Ranking for      * the requested Core name<p>      *<b>IMPLEMENTATION NOTE:</b><br>      * This method Iterates over all tracked {@link ServiceReference} and      * can therefore be assumed as low-performance. However the assumptions are that      * first there are only a few tracked services and this method is not called      * frequently. If one of this assumptions is not true one should consider to      * re-implement this by building an in-memory model of tracked       * {@link CoreContainer}s and {@link SolrCore}s with the required indexes      * required to implement lookups like that.           * @param name the name (or path) of the core      * @return the reference or<code>null</code> if not found      */
specifier|private
name|ServiceReference
name|getCoreReference
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|matches
decl_stmt|;
name|ServiceReference
index|[]
name|refs
init|=
name|coreTracker
operator|.
name|getServiceReferences
argument_list|()
decl_stmt|;
if|if
condition|(
name|refs
operator|!=
literal|null
condition|)
block|{
name|matches
operator|=
operator|new
name|ArrayList
argument_list|<
name|ServiceReference
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|ServiceReference
name|ref
range|:
name|refs
control|)
block|{
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
name|ref
operator|.
name|getProperty
argument_list|(
name|PROPERTY_CORE_NAME
argument_list|)
argument_list|)
operator|||
name|name
operator|.
name|equals
argument_list|(
name|ref
operator|.
name|getProperty
argument_list|(
name|PROPERTY_CORE_DIR
argument_list|)
argument_list|)
condition|)
block|{
name|matches
operator|.
name|add
argument_list|(
name|ref
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|matches
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|matches
argument_list|,
name|ServiceReferenceRankingComparator
operator|.
name|INSTANCE
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|matches
operator|=
literal|null
expr_stmt|;
block|}
return|return
name|matches
operator|==
literal|null
operator|||
name|matches
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|matches
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
comment|/**      * Getter for the {@link ServiceReference} to the {@link CoreContainer} for      * the parsed name (or path) with the highest service rank<p>      *<b>IMPLEMENTATION NOTE:</b><br>      * This method Iterates over all tracked {@link ServiceReference} and      * can therefore be assumed as low-performance. However the assumptions are that      * first there are only a few tracked services and this method is not called      * frequently. If one of this assumptions is not true one should consider to      * re-implement this by building an in-memory model of tracked       * {@link CoreContainer}s and {@link SolrCore}s with the required indexes      * required to implement lookups like that.      * @param name The name (or path) of the server      * @return The reference or<code>null</code> if not found      */
specifier|private
name|ServiceReference
name|getServerReference
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|matches
decl_stmt|;
name|ServiceReference
index|[]
name|refs
init|=
name|serverTracker
operator|.
name|getServiceReferences
argument_list|()
decl_stmt|;
if|if
condition|(
name|refs
operator|!=
literal|null
condition|)
block|{
name|matches
operator|=
operator|new
name|ArrayList
argument_list|<
name|ServiceReference
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|ServiceReference
name|ref
range|:
name|refs
control|)
block|{
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
name|ref
operator|.
name|getProperty
argument_list|(
name|PROPERTY_SERVER_NAME
argument_list|)
argument_list|)
operator|||
name|name
operator|.
name|equals
argument_list|(
name|ref
operator|.
name|getProperty
argument_list|(
name|PROPERTY_SERVER_DIR
argument_list|)
argument_list|)
condition|)
block|{
name|matches
operator|.
name|add
argument_list|(
name|ref
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|matches
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|matches
argument_list|,
name|ServiceReferenceRankingComparator
operator|.
name|INSTANCE
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|matches
operator|=
literal|null
expr_stmt|;
block|}
return|return
name|matches
operator|==
literal|null
operator|||
name|matches
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|matches
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|SolrServerTypeEnum
argument_list|>
name|supportedTypes
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|singleton
argument_list|(
name|SolrServerTypeEnum
operator|.
name|EMBEDDED
argument_list|)
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
name|InvalidSyntaxException
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"activating"
operator|+
name|RegisteredSolrServerProvider
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
argument_list|)
expr_stmt|;
name|coreTracker
operator|=
operator|new
name|ServiceTracker
argument_list|(
name|context
operator|.
name|getBundleContext
argument_list|()
argument_list|,
name|SolrCore
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|serverTracker
operator|=
operator|new
name|ServiceTracker
argument_list|(
name|context
operator|.
name|getBundleContext
argument_list|()
argument_list|,
name|CoreContainer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|coreTracker
operator|.
name|open
argument_list|()
expr_stmt|;
name|serverTracker
operator|.
name|open
argument_list|()
expr_stmt|;
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
name|log
operator|.
name|debug
argument_list|(
literal|"deactivating"
operator|+
name|RegisteredSolrServerProvider
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|coreTracker
operator|!=
literal|null
condition|)
block|{
name|coreTracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|coreTracker
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|serverTracker
operator|!=
literal|null
condition|)
block|{
name|serverTracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|serverTracker
operator|=
literal|null
expr_stmt|;
block|}
block|}
comment|// Keeping for now because this might be useful when checking for required files
comment|// /**
comment|// * Checks if the parsed directory contains a file that starts with the parsed
comment|// * name. Parsing "hallo" will find "hallo.all", "hallo.ween" as well as "hallo".
comment|// * @param dir the Directory. This assumes that the parsed File is not
comment|// *<code>null</code>, exists and is an directory
comment|// * @param name the name. If<code>null</code> any file is accepted, meaning
comment|// * that this will return true if the directory contains any file
comment|// * @return the state
comment|// */
comment|// private boolean hasFile(File dir, String name){
comment|// return dir.list(new NameFileFilter(name)).length>0;
comment|// }
comment|// /**
comment|// * Returns the first file that matches the parsed name.
comment|// * Parsing "hallo" will find "hallo.all", "hallo.ween" as well as "hallo".
comment|// * @param dir the Directory. This assumes that the parsed File is not
comment|// *<code>null</code>, exists and is an directory.
comment|// * @param name the name. If<code>null</code> any file is accepted, meaning
comment|// * that this will return true if the directory contains any file
comment|// * @return the first file matching the parsed prefix.
comment|// */
comment|// private File getFileByPrefix(File dir, String prefix){
comment|// String[] files = dir.list(new PrefixFileFilter(prefix));
comment|// return files.length>0?new File(dir,files[0]):null;
comment|// }
comment|/**      * Returns the first file that matches the parsed name (case sensitive)      *       * @param dir      *            the Directory. This assumes that the parsed File is not<code>null</code>, exists and is an      *            directory.      * @param name      *            the name. If<code>null</code> any file is accepted, meaning that this will return true if      *            the directory contains any file      * @return the first file matching the parsed name.      */
specifier|private
name|File
name|getFile
parameter_list|(
name|File
name|dir
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|String
index|[]
name|files
init|=
name|dir
operator|.
name|list
argument_list|(
operator|new
name|NameFileFilter
argument_list|(
name|name
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|files
operator|.
name|length
operator|>
literal|0
condition|?
operator|new
name|File
argument_list|(
name|dir
argument_list|,
name|files
index|[
literal|0
index|]
argument_list|)
else|:
literal|null
return|;
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Integer
operator|.
name|MAX_VALUE
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
