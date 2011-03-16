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
name|provider
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
name|EnumMap
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
name|ServiceLoader
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
name|stanbol
operator|.
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|provider
operator|.
name|SolrServerProvider
operator|.
name|Type
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
comment|/**  * Manager for different {@link SolrServerProvider} present in the current  * environment.  * This manager works both within an OSGI Environment by defining an Reference  * and outside by using {@link #getInstance()}.  *   * @author Rupert Westenthaler  *  */
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
name|SolrServerProviderManager
operator|.
name|class
argument_list|)
specifier|public
specifier|final
class|class
name|SolrServerProviderManager
block|{
comment|/**      * Used for the singleton pattern, but also init within the OSGI environment      * when {@link #activate(ComponentContext)} is called.      */
specifier|private
specifier|static
name|SolrServerProviderManager
name|solrServerProviderManager
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SolrServerProviderManager
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Reference
argument_list|(
name|referenceInterface
operator|=
name|SolrServerProvider
operator|.
name|class
argument_list|,
name|strategy
operator|=
name|ReferenceStrategy
operator|.
name|EVENT
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|,
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|MANDATORY_MULTIPLE
argument_list|,
name|bind
operator|=
literal|"addSolrProvider"
argument_list|,
name|unbind
operator|=
literal|"removeSolrProvider"
argument_list|)
specifier|private
name|Map
argument_list|<
name|Type
argument_list|,
name|List
argument_list|<
name|SolrServerProvider
argument_list|>
argument_list|>
name|solrServerProviders
init|=
name|Collections
operator|.
name|synchronizedMap
argument_list|(
operator|new
name|EnumMap
argument_list|<
name|Type
argument_list|,
name|List
argument_list|<
name|SolrServerProvider
argument_list|>
argument_list|>
argument_list|(
name|Type
operator|.
name|class
argument_list|)
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|SolrServerProviderManager
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|solrServerProviderManager
operator|==
literal|null
condition|)
block|{
name|SolrServerProviderManager
name|manager
init|=
operator|new
name|SolrServerProviderManager
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|SolrServerProvider
argument_list|>
name|providerIt
init|=
name|ServiceLoader
operator|.
name|load
argument_list|(
name|SolrServerProvider
operator|.
name|class
argument_list|,
name|SolrServerProviderManager
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|)
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|providerIt
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|SolrServerProvider
name|provider
init|=
name|providerIt
operator|.
name|next
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"load provider "
operator|+
name|provider
operator|.
name|getClass
argument_list|()
operator|+
literal|" supporting "
operator|+
name|provider
operator|.
name|supportedTypes
argument_list|()
argument_list|)
expr_stmt|;
name|manager
operator|.
name|addSolrProvider
argument_list|(
name|provider
argument_list|)
expr_stmt|;
block|}
name|solrServerProviderManager
operator|=
name|manager
expr_stmt|;
block|}
return|return
name|solrServerProviderManager
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
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Activate SolrServerProviderManager"
argument_list|)
expr_stmt|;
if|if
condition|(
name|solrServerProviderManager
operator|==
literal|null
condition|)
block|{
name|solrServerProviderManager
operator|=
name|this
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
name|log
operator|.
name|debug
argument_list|(
literal|"Activate SolrServerProviderManager"
argument_list|)
expr_stmt|;
name|solrServerProviderManager
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|SolrServer
name|getSolrServer
parameter_list|(
name|Type
name|type
parameter_list|,
name|String
name|uriOrPath
parameter_list|,
name|String
modifier|...
name|additionalServerLocations
parameter_list|)
block|{
name|List
argument_list|<
name|SolrServerProvider
argument_list|>
name|providers
init|=
name|solrServerProviders
operator|.
name|get
argument_list|(
name|type
argument_list|)
decl_stmt|;
if|if
condition|(
name|providers
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"No Provider for type "
operator|+
name|type
operator|+
literal|" available"
argument_list|)
throw|;
block|}
for|for
control|(
name|SolrServerProvider
name|provider
range|:
name|providers
control|)
block|{
try|try
block|{
return|return
name|provider
operator|.
name|getSolrServer
argument_list|(
name|type
argument_list|,
name|uriOrPath
argument_list|,
name|additionalServerLocations
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to create SolrServer by using Provider "
operator|+
name|provider
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to create SolrServer for type %s and service location %s"
argument_list|,
name|type
argument_list|,
name|uriOrPath
argument_list|)
argument_list|)
throw|;
block|}
specifier|protected
name|void
name|addSolrProvider
parameter_list|(
name|SolrServerProvider
name|provider
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"add SolrProvider "
operator|+
name|provider
operator|+
literal|" types "
operator|+
name|provider
operator|.
name|supportedTypes
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Type
name|type
range|:
name|provider
operator|.
name|supportedTypes
argument_list|()
control|)
block|{
name|List
argument_list|<
name|SolrServerProvider
argument_list|>
name|providers
init|=
name|solrServerProviders
operator|.
name|get
argument_list|(
name|type
argument_list|)
decl_stmt|;
if|if
condition|(
name|providers
operator|==
literal|null
condition|)
block|{
name|providers
operator|=
operator|new
name|CopyOnWriteArrayList
argument_list|<
name|SolrServerProvider
argument_list|>
argument_list|()
expr_stmt|;
name|solrServerProviders
operator|.
name|put
argument_list|(
name|type
argument_list|,
name|providers
argument_list|)
expr_stmt|;
block|}
name|providers
operator|.
name|add
argument_list|(
name|provider
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|removeSolrProvider
parameter_list|(
name|SolrServerProvider
name|provider
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"remove SolrProvider "
operator|+
name|provider
operator|+
literal|" types "
operator|+
name|provider
operator|.
name|supportedTypes
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Type
name|type
range|:
name|provider
operator|.
name|supportedTypes
argument_list|()
control|)
block|{
name|List
argument_list|<
name|SolrServerProvider
argument_list|>
name|providers
init|=
name|solrServerProviders
operator|.
name|get
argument_list|(
name|type
argument_list|)
decl_stmt|;
if|if
condition|(
name|providers
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|providers
operator|.
name|remove
argument_list|(
name|provider
argument_list|)
operator|&&
name|providers
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//last element removed -> remove the mapping
name|solrServerProviders
operator|.
name|remove
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

