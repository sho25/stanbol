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
name|core
operator|.
name|site
package|;
end_package

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
name|entityhub
operator|.
name|core
operator|.
name|mapping
operator|.
name|DefaultFieldMapperImpl
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
name|mapping
operator|.
name|FieldMappingUtils
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
name|mapping
operator|.
name|ValueConverterFactory
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
name|model
operator|.
name|InMemoryValueFactory
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
name|query
operator|.
name|DefaultQueryFactory
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
name|mapping
operator|.
name|FieldMapper
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
name|mapping
operator|.
name|FieldMapping
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
name|model
operator|.
name|ValueFactory
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
name|query
operator|.
name|FieldQuery
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
name|query
operator|.
name|FieldQueryFactory
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
name|query
operator|.
name|QueryResultList
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
name|util
operator|.
name|ModelUtils
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
name|CacheStrategy
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
comment|/**  * This is the Implementation of the {@link Cache} Interface as defined by the  * entityhub services API.<p>  * Currently the dependency to the Cache is managed vial a {@link ServiceTracker}.  * This means that the Cache is activated/keeps active even if the Yard is not  * available or is disabled.  * If the Yard is not available all Yard related methods like get/store/remove/  * find(..) throw {@link YardException}s.<p>  * TODO This is not the intended way to do it, but because I have no Idea how  * to start/stop a OSGI Component from within a class :(  *  * @author Rupert Westenthaler  * @see Cache  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
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
name|Service
argument_list|(
name|value
operator|=
name|Cache
operator|.
name|class
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
literal|1000
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|CacheImpl
implements|implements
name|Cache
block|{
specifier|private
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|CacheImpl
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CACHE_FACTORY_NAME
init|=
literal|"org.apache.stanbol.entityhub.yard.CacheFactory"
decl_stmt|;
specifier|private
name|ServiceTracker
name|yardTracker
decl_stmt|;
comment|//private Yard yard;
specifier|private
name|String
name|yardId
decl_stmt|;
specifier|private
name|boolean
name|initWithYard
init|=
literal|false
decl_stmt|;
specifier|private
name|FieldMapper
name|baseMapper
decl_stmt|;
specifier|private
name|FieldMapper
name|additionalMapper
decl_stmt|;
specifier|private
name|ComponentContext
name|context
decl_stmt|;
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
name|context
operator|=
name|context
expr_stmt|;
name|yardId
operator|=
name|OsgiUtils
operator|.
name|checkProperty
argument_list|(
name|context
operator|.
name|getProperties
argument_list|()
argument_list|,
name|CACHE_YARD
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
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
literal|null
argument_list|)
expr_stmt|;
name|yardTracker
operator|.
name|open
argument_list|()
expr_stmt|;
block|}
comment|/**      * Lazy initialisation of the yard the first time the yard is ued by this      * cache      *      * @param yard the yard instance. MUST NOT be NULL!      * @throws YardException      */
specifier|protected
name|void
name|initWithCacheYard
parameter_list|(
name|Yard
name|yard
parameter_list|)
throws|throws
name|YardException
block|{
comment|//(1) Read the base mappings from the Yard
name|this
operator|.
name|baseMapper
operator|=
name|CacheUtils
operator|.
name|loadBaseMappings
argument_list|(
name|yard
argument_list|)
expr_stmt|;
comment|//(2) Init the additional mappings based on the configuration
name|Object
name|mappings
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
name|FieldMapper
name|configuredMappings
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|mappings
operator|instanceof
name|String
index|[]
operator|&&
operator|(
operator|(
name|String
index|[]
operator|)
name|mappings
operator|)
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|configuredMappings
operator|=
operator|new
name|DefaultFieldMapperImpl
argument_list|(
name|ValueConverterFactory
operator|.
name|getDefaultInstance
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|mappingString
range|:
operator|(
name|String
index|[]
operator|)
name|mappings
control|)
block|{
name|FieldMapping
name|fieldMapping
init|=
name|FieldMappingUtils
operator|.
name|parseFieldMapping
argument_list|(
name|mappingString
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldMapping
operator|!=
literal|null
condition|)
block|{
name|configuredMappings
operator|.
name|addMapping
argument_list|(
name|fieldMapping
argument_list|)
expr_stmt|;
block|}
block|}
comment|//check if there are valid mappings
if|if
condition|(
name|configuredMappings
operator|.
name|getMappings
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|configuredMappings
operator|=
literal|null
expr_stmt|;
comment|//if no mappings where found set to null
block|}
block|}
name|FieldMapper
name|yardAdditionalMappings
init|=
name|CacheUtils
operator|.
name|loadAdditionalMappings
argument_list|(
name|yard
argument_list|)
decl_stmt|;
if|if
condition|(
name|yardAdditionalMappings
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|configuredMappings
operator|!=
literal|null
condition|)
block|{
name|setAdditionalMappings
argument_list|(
name|yard
argument_list|,
name|configuredMappings
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|yardAdditionalMappings
operator|.
name|equals
argument_list|(
name|configuredMappings
argument_list|)
condition|)
block|{
comment|//this may also set the additional mappings to null!
name|log
operator|.
name|info
argument_list|(
literal|"Replace Additional Mappings for Cache "
operator|+
name|yardId
operator|+
literal|"with Mappings configured by OSGI"
argument_list|)
expr_stmt|;
name|setAdditionalMappings
argument_list|(
name|yard
argument_list|,
name|configuredMappings
argument_list|)
expr_stmt|;
block|}
comment|//else current config equals configured one -> nothing to do!
name|initWithYard
operator|=
literal|true
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
comment|//        context.getBundleContext().removeServiceListener(this);
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
name|initWithYard
operator|=
literal|false
expr_stmt|;
name|this
operator|.
name|yardId
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|baseMapper
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|additionalMapper
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|context
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isAvailable
parameter_list|()
block|{
return|return
name|yardTracker
operator|.
name|getService
argument_list|()
operator|!=
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|CacheStrategy
name|isField
parameter_list|(
name|String
name|field
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|CacheStrategy
name|isLanguage
parameter_list|(
name|String
name|lang
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|CacheStrategy
name|strategy
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
comment|// Currently not needed, because instances are created and disposed by the YardManagerImpl!
comment|//    @Override
comment|//    public void serviceChanged(ServiceEvent event) {
comment|//        log.info("Print Service Event for "+event.getSource());
comment|//        for(String key : event.getServiceReference().getPropertyKeys()){
comment|//            log.info("> "+key+"="+event.getServiceReference().getProperty(key));
comment|//        }
comment|//        Object cacheYardPropertyValue = event.getServiceReference().getProperty(CACHE_YARD);
comment|//        //TODO: check the type of the Service provided by the Reference!
comment|//        if(cacheYardPropertyValue != null&& yardId.equals(cacheYardPropertyValue.toString())){
comment|//            //process the Event
comment|//            if(event.getType() == ServiceEvent.REGISTERED){
comment|//
comment|//            } else if(event.getType() == ServiceEvent.UNREGISTERING){
comment|//
comment|//            }
comment|//
comment|//        }
comment|//    }
comment|/**      * Getter for the Yard used by this Cache.      *      * @return the Yard used by this Cache or<code>null</code> if currently not      *         available.      */
specifier|public
name|Yard
name|getCacheYard
parameter_list|()
block|{
name|Yard
name|yard
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
name|yard
operator|!=
literal|null
operator|&&
operator|!
name|initWithYard
condition|)
block|{
try|try
block|{
name|initWithCacheYard
argument_list|(
name|yard
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
comment|//this case can be recovered because initWithYard will not be
comment|//set to true.
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to initialize the Cache with Yard "
operator|+
name|yardId
operator|+
literal|"! This is usually caused by Errors while reading the Cache Configuration from the Yard."
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
return|return
name|yard
return|;
block|}
comment|/*--------------------------------------------------------------------------      * Store and Update calls MUST respect the mappings configured for the      * Cache!      * --------------------------------------------------------------------------      */
annotation|@
name|Override
specifier|public
name|Representation
name|store
parameter_list|(
name|Representation
name|representation
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|YardException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|yard
operator|.
name|store
argument_list|(
name|applyCacheMappings
argument_list|(
name|yard
argument_list|,
name|representation
argument_list|)
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Representation
name|update
parameter_list|(
name|Representation
name|representation
parameter_list|)
throws|throws
name|YardException
throws|,
name|IllegalArgumentException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|yard
operator|.
name|update
argument_list|(
name|applyCacheMappings
argument_list|(
name|yard
argument_list|,
name|representation
argument_list|)
argument_list|)
return|;
block|}
block|}
comment|/**      * Applies the mappings defined by the {@link #baseMapper} and the {@link #additionalMapper}      * to the parsed Representation.      *      * @param yard The yard (local reference to avoid syncronization)      * @param representation The representation to map      * @return the mapped representation      */
specifier|private
name|Representation
name|applyCacheMappings
parameter_list|(
name|Yard
name|yard
parameter_list|,
name|Representation
name|representation
parameter_list|)
block|{
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|Representation
name|mapped
init|=
literal|null
decl_stmt|;
name|ValueFactory
name|valueFactory
init|=
name|getValueFactory
argument_list|()
decl_stmt|;
if|if
condition|(
name|baseMapper
operator|!=
literal|null
condition|)
block|{
name|mapped
operator|=
name|yard
operator|.
name|getValueFactory
argument_list|()
operator|.
name|createRepresentation
argument_list|(
name|representation
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|baseMapper
operator|.
name|applyMappings
argument_list|(
name|representation
argument_list|,
name|mapped
argument_list|,
name|valueFactory
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|additionalMapper
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|mapped
operator|==
literal|null
condition|)
block|{
name|mapped
operator|=
name|yard
operator|.
name|getValueFactory
argument_list|()
operator|.
name|createRepresentation
argument_list|(
name|representation
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|additionalMapper
operator|.
name|applyMappings
argument_list|(
name|representation
argument_list|,
name|mapped
argument_list|,
name|valueFactory
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"  -- applied mappings in "
operator|+
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
operator|)
operator|+
literal|"ms"
argument_list|)
expr_stmt|;
return|return
name|mapped
operator|!=
literal|null
condition|?
name|mapped
else|:
name|representation
return|;
block|}
comment|/*--------------------------------------------------------------------------      * Methods that forward calls to the Yard configured for this Cache      * --------------------------------------------------------------------------      */
annotation|@
name|Override
specifier|public
name|Representation
name|create
parameter_list|()
throws|throws
name|YardException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
return|return
name|createRepresentation
argument_list|(
literal|null
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|yard
operator|.
name|create
argument_list|()
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Representation
name|create
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|YardException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
return|return
name|createRepresentation
argument_list|(
name|id
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|yard
operator|.
name|create
argument_list|(
name|id
argument_list|)
return|;
block|}
block|}
comment|/**      * Only used to create a representation if the Yard is currently not available      *      * @param id the id or<code>null</code> if a random one should be generated      * @return the created Representation      */
specifier|private
name|Representation
name|createRepresentation
parameter_list|(
name|String
name|id
parameter_list|)
block|{
if|if
condition|(
name|id
operator|==
literal|null
condition|)
block|{
name|id
operator|=
name|String
operator|.
name|format
argument_list|(
literal|"urn:org.apache.stanbol:entityhub.yard.%s:%s.%s"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|yardId
argument_list|,
name|ModelUtils
operator|.
name|randomUUID
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createRepresentation
argument_list|(
name|id
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|find
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|YardException
throws|,
name|IllegalArgumentException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|yard
operator|.
name|find
argument_list|(
name|query
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|findReferences
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|YardException
throws|,
name|IllegalArgumentException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|yard
operator|.
name|findReferences
argument_list|(
name|query
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|findRepresentation
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|YardException
throws|,
name|IllegalArgumentException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|yard
operator|.
name|findRepresentation
argument_list|(
name|query
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"Cache Wrapper for Yard %s "
argument_list|,
name|yardId
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|yardId
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|yardId
operator|+
literal|" Cache"
return|;
block|}
annotation|@
name|Override
specifier|public
name|FieldQueryFactory
name|getQueryFactory
parameter_list|()
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
return|return
name|DefaultQueryFactory
operator|.
name|getInstance
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|yard
operator|.
name|getQueryFactory
argument_list|()
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Representation
name|getRepresentation
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|YardException
throws|,
name|IllegalArgumentException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|yard
operator|.
name|getRepresentation
argument_list|(
name|id
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|ValueFactory
name|getValueFactory
parameter_list|()
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
return|return
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|yard
operator|.
name|getValueFactory
argument_list|()
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isRepresentation
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|YardException
throws|,
name|IllegalArgumentException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|yard
operator|.
name|isRepresentation
argument_list|(
name|id
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|YardException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
name|yard
operator|.
name|remove
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
block|}
comment|/*--------------------------------------------------------------------------      * Methods for reading and storing and changing the cache configuration      * --------------------------------------------------------------------------      */
comment|/**      * Getter for the base mappings used by this Cache. Modifications on the      * returned object do not have any influence on the mappings, because this      * method returns a clone. Use {@link #setBaseMappings(FieldMapper)} to      * change the used base mappings. However make sure you understand the      * implications of changing the base mappings as described in the      * documentation of the setter method      *      * @return A clone of the base mappings or<code>null</code> if no base      *         mappings are defined      */
annotation|@
name|Override
specifier|public
specifier|final
name|FieldMapper
name|getBaseMappings
parameter_list|()
block|{
return|return
name|baseMapper
operator|==
literal|null
condition|?
literal|null
else|:
name|baseMapper
operator|.
name|clone
argument_list|()
return|;
block|}
comment|/**      * Getter for the additional mappings used by this Cache. Modifications on the      * returned object do not have any influence on the mappings, because this      * method returns a clone. Use {@link #setAdditionalMappings(FieldMapper)} to      * change the used additional mappings. However make sure you understand the      * implications of changing the base mappings as described in the      * documentation of the setter method      *      * @return A clone of the additional mappings or<code>null</code> if no      *         additional mappings are defined      */
annotation|@
name|Override
specifier|public
specifier|final
name|FieldMapper
name|getAdditionalMappings
parameter_list|()
block|{
return|return
name|additionalMapper
operator|==
literal|null
condition|?
literal|null
else|:
name|additionalMapper
operator|.
name|clone
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setAdditionalMappings
parameter_list|(
name|FieldMapper
name|fieldMapper
parameter_list|)
throws|throws
name|YardException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
name|setAdditionalMappings
argument_list|(
name|yard
argument_list|,
name|fieldMapper
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Internally used in the initialisation to be able to parse the Yard instance      *      * @param yard the yard used to set the configured additional mappings      * @param fieldMapper the configuration      * @throws YardException on any error while accessing the yard      */
specifier|protected
name|void
name|setAdditionalMappings
parameter_list|(
name|Yard
name|yard
parameter_list|,
name|FieldMapper
name|fieldMapper
parameter_list|)
throws|throws
name|YardException
block|{
name|FieldMapper
name|old
init|=
name|this
operator|.
name|additionalMapper
decl_stmt|;
name|this
operator|.
name|additionalMapper
operator|=
name|fieldMapper
expr_stmt|;
try|try
block|{
name|CacheUtils
operator|.
name|storeAdditionalMappingsConfiguration
argument_list|(
name|yard
argument_list|,
name|additionalMapper
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
name|this
operator|.
name|additionalMapper
operator|=
name|old
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|setBaseMappings
parameter_list|(
name|FieldMapper
name|fieldMapper
parameter_list|)
throws|throws
name|YardException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
if|if
condition|(
name|isAvailable
argument_list|()
condition|)
block|{
name|FieldMapper
name|old
init|=
name|this
operator|.
name|baseMapper
decl_stmt|;
name|this
operator|.
name|baseMapper
operator|=
name|fieldMapper
expr_stmt|;
try|try
block|{
name|CacheUtils
operator|.
name|storeBaseMappingsConfiguration
argument_list|(
name|yard
argument_list|,
name|baseMapper
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
name|this
operator|.
name|baseMapper
operator|=
name|old
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|(
name|Iterable
argument_list|<
name|String
argument_list|>
name|ids
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|YardException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
name|yard
operator|.
name|remove
argument_list|(
name|ids
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeAll
parameter_list|()
throws|throws
name|YardException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
comment|//ensure that the baseConfig (if present) is not deleted by this
comment|//operation
name|Representation
name|baseConfig
init|=
name|yard
operator|.
name|getRepresentation
argument_list|(
name|Cache
operator|.
name|BASE_CONFIGURATION_URI
argument_list|)
decl_stmt|;
name|yard
operator|.
name|removeAll
argument_list|()
expr_stmt|;
if|if
condition|(
name|baseConfig
operator|!=
literal|null
condition|)
block|{
name|yard
operator|.
name|store
argument_list|(
name|baseConfig
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|Iterable
argument_list|<
name|Representation
argument_list|>
name|store
parameter_list|(
name|Iterable
argument_list|<
name|Representation
argument_list|>
name|representations
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|YardException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|yard
operator|.
name|store
argument_list|(
name|representations
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Iterable
argument_list|<
name|Representation
argument_list|>
name|update
parameter_list|(
name|Iterable
argument_list|<
name|Representation
argument_list|>
name|representations
parameter_list|)
throws|throws
name|YardException
throws|,
name|IllegalArgumentException
block|{
name|Yard
name|yard
init|=
name|getCacheYard
argument_list|()
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|YardException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The Yard %s for this cache is currently not available"
argument_list|,
name|yardId
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|yard
operator|.
name|update
argument_list|(
name|representations
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

