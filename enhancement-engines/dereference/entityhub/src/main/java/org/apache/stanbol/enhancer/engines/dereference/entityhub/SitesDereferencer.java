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
name|enhancer
operator|.
name|engines
operator|.
name|dereference
operator|.
name|entityhub
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ExecutorService
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|marmotta
operator|.
name|ldpath
operator|.
name|api
operator|.
name|backend
operator|.
name|RDFBackend
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
name|ldpath
operator|.
name|backend
operator|.
name|SiteManagerBackend
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
name|servicesapi
operator|.
name|EntityhubException
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
name|servicesapi
operator|.
name|model
operator|.
name|Entity
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
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|servicesapi
operator|.
name|site
operator|.
name|SiteManager
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
name|util
operator|.
name|tracker
operator|.
name|ServiceTrackerCustomizer
import|;
end_import

begin_class
specifier|public
specifier|final
class|class
name|SitesDereferencer
extends|extends
name|TrackingDereferencerBase
argument_list|<
name|SiteManager
argument_list|>
block|{
comment|//    private final Logger log = LoggerFactory.getLogger(SiteDereferencer.class);
specifier|public
name|SitesDereferencer
parameter_list|(
name|BundleContext
name|context
parameter_list|)
block|{
name|this
argument_list|(
name|context
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SitesDereferencer
parameter_list|(
name|BundleContext
name|context
parameter_list|,
name|ExecutorServiceProvider
name|executorServiceProvider
parameter_list|)
block|{
name|this
argument_list|(
name|context
argument_list|,
literal|null
argument_list|,
name|executorServiceProvider
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SitesDereferencer
parameter_list|(
name|BundleContext
name|context
parameter_list|,
name|ServiceTrackerCustomizer
name|customizer
parameter_list|,
name|ExecutorServiceProvider
name|executorServiceprovider
parameter_list|)
block|{
name|super
argument_list|(
name|context
argument_list|,
name|SiteManager
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|customizer
argument_list|,
name|executorServiceprovider
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|supportsOfflineMode
parameter_list|()
block|{
return|return
literal|true
return|;
comment|//can not be determined here .. return true
block|}
annotation|@
name|Override
specifier|protected
name|Representation
name|getRepresentation
parameter_list|(
name|SiteManager
name|sm
parameter_list|,
name|String
name|id
parameter_list|,
name|boolean
name|offlineMode
parameter_list|)
throws|throws
name|EntityhubException
block|{
name|Entity
name|entity
init|=
name|sm
operator|.
name|getEntity
argument_list|(
name|id
argument_list|)
decl_stmt|;
return|return
name|entity
operator|==
literal|null
condition|?
literal|null
else|:
name|entity
operator|.
name|getRepresentation
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|protected
name|RDFBackend
argument_list|<
name|Object
argument_list|>
name|createRdfBackend
parameter_list|(
name|SiteManager
name|service
parameter_list|)
block|{
return|return
operator|new
name|SiteManagerBackend
argument_list|(
name|service
argument_list|)
return|;
block|}
block|}
end_class

end_unit

