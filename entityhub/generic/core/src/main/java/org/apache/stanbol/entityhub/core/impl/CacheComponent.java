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
name|entityhub
operator|.
name|core
operator|.
name|impl
package|;
end_package

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
name|Properties
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
name|stanbol
operator|.
name|commons
operator|.
name|namespaceprefix
operator|.
name|NamespacePrefixService
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
name|core
operator|.
name|site
operator|.
name|CacheImpl
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
name|core
operator|.
name|utils
operator|.
name|OsgiUtils
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
name|yard
operator|.
name|Cache
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
name|yard
operator|.
name|Yard
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
name|yard
operator|.
name|YardException
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
comment|/**  * OSGI component for {@link CacheImpl}  * @since 0.12.0  * @author Rupert Westenthaler  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
comment|//set the Class name of CacheImpl for backward compatibility
name|name
operator|=
literal|"org.apache.stanbol.entityhub.core.site.CacheImpl"
argument_list|,
name|configurationFactory
operator|=
literal|true
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|REQUIRE
argument_list|,
comment|//the baseUri is required!
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
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Cache
operator|.
name|CACHE_YARD
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Cache
operator|.
name|ADDITIONAL_MAPPINGS
argument_list|,
name|cardinality
operator|=
name|Integer
operator|.
name|MAX_VALUE
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|CacheComponent
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
name|CacheComponent
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|ServiceTracker
name|yardTracker
decl_stmt|;
specifier|private
name|Yard
name|yard
decl_stmt|;
specifier|private
name|ComponentContext
name|cc
decl_stmt|;
specifier|private
name|ServiceRegistration
name|cacheRegistration
decl_stmt|;
specifier|private
name|Cache
name|cache
decl_stmt|;
specifier|private
name|String
index|[]
name|additionalMappings
decl_stmt|;
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_UNARY
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|,
name|bind
operator|=
literal|"bindNamespacePrefixService"
argument_list|,
name|unbind
operator|=
literal|"unbindNamespacePrefixService"
argument_list|,
name|strategy
operator|=
name|ReferenceStrategy
operator|.
name|EVENT
argument_list|)
specifier|private
name|NamespacePrefixService
name|nsPrefixService
decl_stmt|;
specifier|protected
name|void
name|bindNamespacePrefixService
parameter_list|(
name|NamespacePrefixService
name|ps
parameter_list|)
block|{
name|this
operator|.
name|nsPrefixService
operator|=
name|ps
expr_stmt|;
name|updateServiceRegistration
argument_list|(
name|cc
argument_list|,
name|yard
argument_list|,
name|additionalMappings
argument_list|,
name|nsPrefixService
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|unbindNamespacePrefixService
parameter_list|(
name|NamespacePrefixService
name|ps
parameter_list|)
block|{
if|if
condition|(
name|ps
operator|.
name|equals
argument_list|(
name|this
operator|.
name|nsPrefixService
argument_list|)
condition|)
block|{
name|this
operator|.
name|nsPrefixService
operator|=
literal|null
expr_stmt|;
name|updateServiceRegistration
argument_list|(
name|cc
argument_list|,
name|yard
argument_list|,
name|additionalMappings
argument_list|,
name|nsPrefixService
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
specifier|final
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|ConfigurationException
throws|,
name|YardException
throws|,
name|IllegalStateException
throws|,
name|InvalidSyntaxException
block|{
if|if
condition|(
name|context
operator|==
literal|null
operator|||
name|context
operator|.
name|getProperties
argument_list|()
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Invalid ComponentContext parsed in activate (context=%s)"
argument_list|,
name|context
argument_list|)
argument_list|)
throw|;
block|}
name|this
operator|.
name|cc
operator|=
name|context
expr_stmt|;
name|Object
name|value
init|=
name|context
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|Cache
operator|.
name|ADDITIONAL_MAPPINGS
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|String
index|[]
condition|)
block|{
name|this
operator|.
name|additionalMappings
operator|=
operator|(
name|String
index|[]
operator|)
name|value
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|String
condition|)
block|{
name|this
operator|.
name|additionalMappings
operator|=
operator|new
name|String
index|[]
block|{
operator|(
name|String
operator|)
name|value
block|}
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|Collection
argument_list|<
name|?
argument_list|>
condition|)
block|{
try|try
block|{
name|additionalMappings
operator|=
operator|(
operator|(
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
name|value
operator|)
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
operator|(
operator|(
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
name|value
operator|)
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ArrayStoreException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|Cache
operator|.
name|ADDITIONAL_MAPPINGS
argument_list|,
literal|"Additional Mappings MUST BE a String, String[] or Collection<String>!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
else|else
block|{
name|additionalMappings
operator|=
literal|null
expr_stmt|;
block|}
name|String
name|yardId
init|=
name|OsgiUtils
operator|.
name|checkProperty
argument_list|(
name|context
operator|.
name|getProperties
argument_list|()
argument_list|,
name|Cache
operator|.
name|CACHE_YARD
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|cacheFilter
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
name|Yard
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|Yard
operator|.
name|ID
argument_list|,
name|yardId
argument_list|)
decl_stmt|;
name|yardTracker
operator|=
operator|new
name|ServiceTracker
argument_list|(
name|context
operator|.
name|getBundleContext
argument_list|()
argument_list|,
name|context
operator|.
name|getBundleContext
argument_list|()
operator|.
name|createFilter
argument_list|(
name|cacheFilter
argument_list|)
argument_list|,
operator|new
name|ServiceTrackerCustomizer
argument_list|()
block|{
comment|//store the reference to the ComponentContext to avoid NPE if deactivate
comment|//is called for the CacheComponent
specifier|final
name|ComponentContext
name|cc
init|=
name|context
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|removedService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|,
name|Object
name|service
parameter_list|)
block|{
if|if
condition|(
name|service
operator|.
name|equals
argument_list|(
name|yard
argument_list|)
condition|)
block|{
name|yard
operator|=
operator|(
name|Yard
operator|)
name|yardTracker
operator|.
name|getService
argument_list|()
expr_stmt|;
name|updateServiceRegistration
argument_list|(
name|cc
argument_list|,
name|yard
argument_list|,
name|additionalMappings
argument_list|,
name|nsPrefixService
argument_list|)
expr_stmt|;
block|}
name|cc
operator|.
name|getBundleContext
argument_list|()
operator|.
name|ungetService
argument_list|(
name|reference
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|modifiedService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|,
name|Object
name|service
parameter_list|)
block|{
comment|//the service.ranking might have changed ... so check if the
comment|//top ranked Cache is a different one
name|Yard
name|newYard
init|=
operator|(
name|Yard
operator|)
name|yardTracker
operator|.
name|getService
argument_list|()
decl_stmt|;
if|if
condition|(
name|newYard
operator|==
literal|null
operator|||
operator|!
name|newYard
operator|.
name|equals
argument_list|(
name|cache
argument_list|)
condition|)
block|{
name|yard
operator|=
name|newYard
expr_stmt|;
comment|//set the new cahce
comment|//and update the service registration
name|updateServiceRegistration
argument_list|(
name|cc
argument_list|,
name|yard
argument_list|,
name|additionalMappings
argument_list|,
name|nsPrefixService
argument_list|)
expr_stmt|;
block|}
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
name|cc
operator|.
name|getBundleContext
argument_list|()
operator|.
name|getService
argument_list|(
name|reference
argument_list|)
decl_stmt|;
if|if
condition|(
name|service
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|yardTracker
operator|.
name|getServiceReference
argument_list|()
operator|==
literal|null
operator|||
comment|//the first added Service or
comment|//the new service as higher ranking as the current
operator|(
name|reference
operator|.
name|compareTo
argument_list|(
name|yardTracker
operator|.
name|getServiceReference
argument_list|()
argument_list|)
operator|>
literal|0
operator|)
condition|)
block|{
name|yard
operator|=
operator|(
name|Yard
operator|)
name|service
expr_stmt|;
name|updateServiceRegistration
argument_list|(
name|cc
argument_list|,
name|yard
argument_list|,
name|additionalMappings
argument_list|,
name|nsPrefixService
argument_list|)
expr_stmt|;
block|}
comment|// else the new service has lower ranking as the currently use one
block|}
comment|//else service == null -> ignore
return|return
name|service
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|yardTracker
operator|.
name|open
parameter_list|()
constructor_decl|;
block|}
end_class

begin_function
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
name|this
operator|.
name|yardTracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|this
operator|.
name|yardTracker
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|cc
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|yard
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|cache
operator|=
literal|null
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|cacheRegistration
operator|!=
literal|null
condition|)
block|{
name|cacheRegistration
operator|.
name|unregister
argument_list|()
expr_stmt|;
name|cacheRegistration
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_function

begin_function
specifier|private
specifier|synchronized
name|void
name|updateServiceRegistration
parameter_list|(
name|ComponentContext
name|cc
parameter_list|,
name|Yard
name|yard
parameter_list|,
name|String
index|[]
name|additionalMappings
parameter_list|,
name|NamespacePrefixService
name|nsPrefixService
parameter_list|)
block|{
if|if
condition|(
name|cacheRegistration
operator|!=
literal|null
condition|)
block|{
name|cacheRegistration
operator|.
name|unregister
argument_list|()
expr_stmt|;
name|cacheRegistration
operator|=
literal|null
expr_stmt|;
name|cache
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|cc
operator|!=
literal|null
operator|&&
name|yard
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|cache
operator|=
operator|new
name|CacheImpl
argument_list|(
name|yard
argument_list|,
name|additionalMappings
argument_list|,
name|nsPrefixService
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to init Cache for Yard '"
operator|+
name|yard
operator|.
name|getId
argument_list|()
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|cacheRegistration
operator|=
name|cc
operator|.
name|getBundleContext
argument_list|()
operator|.
name|registerService
argument_list|(
name|Cache
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|cache
argument_list|,
name|OsgiUtils
operator|.
name|copyConfig
argument_list|(
name|cc
operator|.
name|getProperties
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_function

unit|}
end_unit

