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
operator|.
name|shared
package|;
end_package

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
name|Hashtable
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
name|ExecutorService
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
name|Executors
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
name|ThreadFactory
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
name|ConfigurationPolicy
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
name|Property
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
name|Filter
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
name|ServiceRegistration
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
name|cm
operator|.
name|ConfigurationException
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

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ThreadFactoryBuilder
import|;
end_import

begin_comment
comment|/**  * Component that registers a Thread  * @author westei  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|configurationFactory
operator|=
literal|false
argument_list|,
comment|//only a single instance
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|OPTIONAL
argument_list|,
comment|// the baseUri is required!
name|specVersion
operator|=
literal|"1.1"
argument_list|,
name|metatype
operator|=
literal|true
argument_list|,
name|immediate
operator|=
literal|true
argument_list|)
specifier|public
class|class
name|SharedDereferenceThreadPool
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
name|SharedDereferenceThreadPool
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|intValue
operator|=
name|SharedDereferenceThreadPool
operator|.
name|DEFAULT_SHARED_THREAD_POOL_SIZE
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|SHARED_THREAD_POOL_SIZE
init|=
literal|"enhancer.engines.dereference.entityhub.sharedthreadpool.size"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_SHARED_THREAD_POOL_SIZE
init|=
literal|8
decl_stmt|;
comment|/** 	 * used as key for {@link Filter}s to track the shared thread pool 	 */
specifier|static
specifier|final
name|String
name|SHARED_THREAD_POOL_NAME
init|=
literal|"enhancer.engines.dereference.entityhub.sharedthreadpool.name"
decl_stmt|;
comment|/** 	 * used as value for {@link Filter}s to track the shared thread pool 	 */
specifier|static
specifier|final
name|String
name|DEFAULT_SHARED_THREAD_POOL_NAME
init|=
literal|"shared"
decl_stmt|;
comment|/** 	 * {@link Filter} string for tracking the {@link ExecutorService} registered 	 * by this component as OSGI service 	 */
specifier|public
specifier|static
name|String
name|SHARED_THREAD_POOL_FILTER
init|=
name|String
operator|.
name|format
argument_list|(
literal|"(&(%s=%s)(%s=%s))"
argument_list|,
name|Constants
operator|.
name|OBJECTCLASS
argument_list|,
name|ExecutorService
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|SHARED_THREAD_POOL_NAME
argument_list|,
name|DEFAULT_SHARED_THREAD_POOL_NAME
argument_list|)
decl_stmt|;
specifier|private
name|ServiceRegistration
name|serviceRegistration
decl_stmt|;
specifier|private
name|ExecutorService
name|executorService
decl_stmt|;
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"activate {}"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|)
expr_stmt|;
name|Object
name|value
init|=
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|SHARED_THREAD_POOL_SIZE
argument_list|)
decl_stmt|;
name|int
name|poolSize
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|poolSize
operator|=
name|DEFAULT_SHARED_THREAD_POOL_SIZE
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|Number
condition|)
block|{
name|poolSize
operator|=
operator|(
operator|(
name|Number
operator|)
name|value
operator|)
operator|.
name|intValue
argument_list|()
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|poolSize
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|SHARED_THREAD_POOL_SIZE
argument_list|,
literal|"Value '"
operator|+
name|value
operator|+
literal|"'(type: "
operator|+
name|value
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|") can not be parsed "
operator|+
literal|"as Integer"
argument_list|)
throw|;
block|}
block|}
if|if
condition|(
name|poolSize
operator|==
literal|0
condition|)
block|{
name|poolSize
operator|=
name|DEFAULT_SHARED_THREAD_POOL_SIZE
expr_stmt|;
block|}
if|if
condition|(
name|poolSize
operator|<
literal|0
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"{} is deactivated because configured thread pool size< 0"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|namePattern
init|=
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"-thread-%s"
decl_stmt|;
name|ThreadFactory
name|threadFactory
init|=
operator|new
name|ThreadFactoryBuilder
argument_list|()
operator|.
name|setNameFormat
argument_list|(
name|namePattern
argument_list|)
operator|.
name|setDaemon
argument_list|(
literal|true
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|" - create Threadpool(namePattern='{}' | size='{}')"
argument_list|,
name|namePattern
argument_list|,
name|poolSize
argument_list|)
expr_stmt|;
name|executorService
operator|=
name|Executors
operator|.
name|newFixedThreadPool
argument_list|(
name|poolSize
argument_list|,
name|threadFactory
argument_list|)
expr_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|dict
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|dict
operator|.
name|put
argument_list|(
name|SHARED_THREAD_POOL_SIZE
argument_list|,
name|poolSize
argument_list|)
expr_stmt|;
name|dict
operator|.
name|put
argument_list|(
name|SHARED_THREAD_POOL_NAME
argument_list|,
name|DEFAULT_SHARED_THREAD_POOL_NAME
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|" - register ExecutorService"
argument_list|)
expr_stmt|;
name|serviceRegistration
operator|=
name|ctx
operator|.
name|getBundleContext
argument_list|()
operator|.
name|registerService
argument_list|(
name|ExecutorService
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|executorService
argument_list|,
name|dict
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
name|ctx
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"deactivate {}"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|serviceRegistration
operator|!=
literal|null
condition|)
block|{
name|serviceRegistration
operator|.
name|unregister
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|executorService
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|" ... shutdown ExecutorService"
argument_list|)
expr_stmt|;
name|executorService
operator|.
name|shutdown
argument_list|()
expr_stmt|;
name|executorService
operator|=
literal|null
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
