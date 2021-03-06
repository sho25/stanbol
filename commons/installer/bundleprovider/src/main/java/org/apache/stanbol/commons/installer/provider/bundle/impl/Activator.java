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
name|installer
operator|.
name|provider
operator|.
name|bundle
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|sling
operator|.
name|installer
operator|.
name|api
operator|.
name|OsgiInstaller
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
name|BundleActivator
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
name|osgi
operator|.
name|util
operator|.
name|tracker
operator|.
name|ServiceTrackerCustomizer
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
comment|/**  * Simple {@link BundleActivator} that also listens to the {@link OsgiInstaller}  * service.  *<p>  * If the Bundle is active and the {@link OsgiInstaller} is available the  * {@link BundleInstaller} is created. If the bundle is stopped or the  * {@link OsgiInstaller} goes away the {@link BundleInstaller} is closed.   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|Activator
implements|implements
name|BundleActivator
implements|,
name|ServiceTrackerCustomizer
block|{
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
name|Activator
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|ServiceTracker
name|installerTracker
decl_stmt|;
specifier|private
name|BundleContext
name|bundleContext
decl_stmt|;
specifier|private
name|BundleInstaller
name|bundleInstaller
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|start
parameter_list|(
name|BundleContext
name|context
parameter_list|)
throws|throws
name|Exception
block|{
name|bundleContext
operator|=
name|context
expr_stmt|;
comment|//Note that this class implements ServiceTrackerCustomizer to init/stop
comment|// the BundleInstaller
name|installerTracker
operator|=
operator|new
name|ServiceTracker
argument_list|(
name|context
argument_list|,
name|OsgiInstaller
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|installerTracker
operator|.
name|open
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|stop
parameter_list|(
name|BundleContext
name|context
parameter_list|)
throws|throws
name|Exception
block|{
name|closeBundleInstaller
argument_list|()
expr_stmt|;
name|installerTracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|bundleContext
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|addingService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|)
block|{
name|Object
name|service
init|=
name|bundleContext
operator|.
name|getService
argument_list|(
name|reference
argument_list|)
decl_stmt|;
if|if
condition|(
name|service
operator|instanceof
name|OsgiInstaller
condition|)
block|{
name|initBundleInstaller
argument_list|(
operator|(
name|OsgiInstaller
operator|)
name|service
argument_list|)
expr_stmt|;
return|return
name|service
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
comment|/* not needed for the OsgiInstaller */
annotation|@
name|Override
specifier|public
name|void
name|modifiedService
parameter_list|(
name|ServiceReference
name|arg0
parameter_list|,
name|Object
name|arg1
parameter_list|)
block|{
comment|/* unused */
block|}
annotation|@
name|Override
specifier|public
name|void
name|removedService
parameter_list|(
name|ServiceReference
name|sr
parameter_list|,
name|Object
name|s
parameter_list|)
block|{
comment|//stop the BundleInstaller
name|closeBundleInstaller
argument_list|()
expr_stmt|;
comment|//unget the service
name|bundleContext
operator|.
name|ungetService
argument_list|(
name|sr
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|synchronized
name|void
name|initBundleInstaller
parameter_list|(
name|OsgiInstaller
name|installer
parameter_list|)
block|{
if|if
condition|(
name|bundleInstaller
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"start BundleInstaller"
argument_list|)
expr_stmt|;
name|bundleInstaller
operator|=
operator|new
name|BundleInstaller
argument_list|(
name|installer
argument_list|,
name|bundleContext
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|synchronized
name|void
name|closeBundleInstaller
parameter_list|()
block|{
if|if
condition|(
name|bundleInstaller
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"close BundleInstaller"
argument_list|)
expr_stmt|;
name|bundleInstaller
operator|.
name|close
argument_list|()
expr_stmt|;
name|bundleInstaller
operator|=
literal|null
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

