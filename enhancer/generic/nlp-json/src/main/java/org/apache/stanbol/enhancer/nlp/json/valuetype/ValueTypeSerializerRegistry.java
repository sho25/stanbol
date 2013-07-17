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
name|nlp
operator|.
name|json
operator|.
name|valuetype
package|;
end_package

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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|locks
operator|.
name|ReadWriteLock
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
name|locks
operator|.
name|ReentrantReadWriteLock
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
name|Service
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
comment|/**  * Tracks {@link ValueTypeSerializer} implementations by using a   * {@link ServiceTracker} when running within OSIG and the {@link ServiceLoader}  * utility when used outside of OSGI.   * @author Rupert Westenthaler  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|IGNORE
argument_list|)
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|ValueTypeSerializerRegistry
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ValueTypeSerializerRegistry
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
name|ValueTypeSerializerRegistry
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|ValueTypeSerializerRegistry
name|instance
decl_stmt|;
comment|/**      * Should be used when running outside of OSGI to create the singleton      * instance of this factory.      * @return the singleton instance      */
specifier|public
specifier|static
specifier|final
name|ValueTypeSerializerRegistry
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|instance
operator|==
literal|null
condition|)
block|{
name|instance
operator|=
operator|new
name|ValueTypeSerializerRegistry
argument_list|()
expr_stmt|;
block|}
return|return
name|instance
return|;
block|}
name|ReadWriteLock
name|serializerLock
init|=
operator|new
name|ReentrantReadWriteLock
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|inOsgi
decl_stmt|;
comment|/**      * Used outside OSGI      */
name|Map
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|ValueTypeSerializer
argument_list|<
name|?
argument_list|>
argument_list|>
name|valueTypeSerializers
decl_stmt|;
comment|/**      * Used if running within OSGI in combination with the {@link #serializerTracker}      */
name|Map
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|valueTypeSerializerRefs
decl_stmt|;
name|ServiceTracker
name|serializerTracker
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
parameter_list|<
name|T
parameter_list|>
name|ValueTypeSerializer
argument_list|<
name|T
argument_list|>
name|getSerializer
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
block|{
if|if
condition|(
operator|!
name|inOsgi
operator|&&
name|valueTypeSerializers
operator|==
literal|null
condition|)
block|{
name|initValueTypeSerializer
argument_list|()
expr_stmt|;
comment|//running outside OSGI
block|}
name|serializerLock
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
if|if
condition|(
operator|!
name|inOsgi
condition|)
block|{
return|return
operator|(
name|ValueTypeSerializer
argument_list|<
name|T
argument_list|>
operator|)
name|valueTypeSerializers
operator|.
name|get
argument_list|(
name|type
argument_list|)
return|;
block|}
else|else
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|refs
init|=
name|valueTypeSerializerRefs
operator|.
name|get
argument_list|(
name|type
argument_list|)
decl_stmt|;
return|return
name|refs
operator|==
literal|null
operator|||
name|refs
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
operator|(
name|ValueTypeSerializer
argument_list|<
name|T
argument_list|>
operator|)
name|serializerTracker
operator|.
name|getService
argument_list|(
name|refs
operator|.
name|get
argument_list|(
name|refs
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
return|;
block|}
block|}
finally|finally
block|{
name|serializerLock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|inOsgi
operator|=
literal|true
expr_stmt|;
specifier|final
name|BundleContext
name|bc
init|=
name|ctx
operator|.
name|getBundleContext
argument_list|()
decl_stmt|;
name|valueTypeSerializerRefs
operator|=
operator|new
name|HashMap
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|serializerTracker
operator|=
operator|new
name|ServiceTracker
argument_list|(
name|bc
argument_list|,
name|ValueTypeSerializer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
operator|new
name|SerializerTracker
argument_list|(
name|bc
argument_list|)
argument_list|)
expr_stmt|;
name|serializerTracker
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
name|ctx
parameter_list|)
block|{
name|inOsgi
operator|=
literal|false
expr_stmt|;
name|serializerTracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|serializerTracker
operator|=
literal|null
expr_stmt|;
name|serializerLock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
if|if
condition|(
name|valueTypeSerializers
operator|!=
literal|null
condition|)
block|{
name|valueTypeSerializers
operator|.
name|clear
argument_list|()
expr_stmt|;
name|valueTypeSerializers
operator|=
literal|null
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|serializerLock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Only used when running outside an OSGI environment      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"rawtypes"
argument_list|)
specifier|private
name|void
name|initValueTypeSerializer
parameter_list|()
block|{
name|serializerLock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
if|if
condition|(
name|valueTypeSerializers
operator|==
literal|null
condition|)
block|{
name|valueTypeSerializers
operator|=
operator|new
name|HashMap
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|ValueTypeSerializer
argument_list|<
name|?
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|ServiceLoader
argument_list|<
name|ValueTypeSerializer
argument_list|>
name|loader
init|=
name|ServiceLoader
operator|.
name|load
argument_list|(
name|ValueTypeSerializer
operator|.
name|class
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|ValueTypeSerializer
argument_list|>
name|it
init|=
name|loader
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ValueTypeSerializer
name|vts
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|ValueTypeSerializer
argument_list|<
name|?
argument_list|>
name|serializer
init|=
name|valueTypeSerializers
operator|.
name|get
argument_list|(
name|vts
operator|.
name|getType
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|serializer
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Multiple Serializers for type {} (keep: {}, ignoreing: {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|vts
operator|.
name|getType
argument_list|()
block|,
name|serializer
block|,
name|vts
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|valueTypeSerializers
operator|.
name|put
argument_list|(
name|vts
operator|.
name|getType
argument_list|()
argument_list|,
name|vts
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
finally|finally
block|{
name|serializerLock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * {@link ServiceTrackerCustomizer} writing services to the      * {@link ValueTypeSerializerRegistry#valueTypeSerializerRefs} map. It also      * sorts multiple Serializer for a single type based on sorting      * {@link ServiceReference}s. The reference with the highest ranking will be      * last in the list.      *      */
specifier|private
specifier|final
class|class
name|SerializerTracker
implements|implements
name|ServiceTrackerCustomizer
block|{
specifier|private
specifier|final
name|BundleContext
name|bc
decl_stmt|;
specifier|private
name|SerializerTracker
parameter_list|(
name|BundleContext
name|bc
parameter_list|)
block|{
name|this
operator|.
name|bc
operator|=
name|bc
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
name|ValueTypeSerializer
argument_list|<
name|?
argument_list|>
name|service
init|=
operator|(
name|ValueTypeSerializer
argument_list|<
name|?
argument_list|>
operator|)
name|bc
operator|.
name|getService
argument_list|(
name|reference
argument_list|)
decl_stmt|;
name|serializerLock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|refs
init|=
name|valueTypeSerializerRefs
operator|.
name|get
argument_list|(
name|service
operator|.
name|getType
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|refs
operator|==
literal|null
condition|)
block|{
name|refs
operator|=
operator|new
name|ArrayList
argument_list|<
name|ServiceReference
argument_list|>
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|valueTypeSerializerRefs
operator|.
name|put
argument_list|(
name|service
operator|.
name|getType
argument_list|()
argument_list|,
name|refs
argument_list|)
expr_stmt|;
block|}
name|refs
operator|.
name|add
argument_list|(
name|reference
argument_list|)
expr_stmt|;
if|if
condition|(
name|refs
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
name|refs
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|serializerLock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
return|return
name|service
return|;
block|}
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
name|ValueTypeSerializer
argument_list|<
name|?
argument_list|>
name|vts
init|=
operator|(
name|ValueTypeSerializer
argument_list|<
name|?
argument_list|>
operator|)
name|service
decl_stmt|;
name|serializerLock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|refs
init|=
name|valueTypeSerializerRefs
operator|.
name|get
argument_list|(
name|vts
operator|.
name|getType
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|refs
operator|!=
literal|null
operator|&&
name|refs
operator|.
name|remove
argument_list|(
name|reference
argument_list|)
operator|&&
name|refs
operator|.
name|isEmpty
argument_list|()
operator|&&
name|valueTypeSerializers
operator|!=
literal|null
condition|)
block|{
comment|//not yet deactivated
name|valueTypeSerializers
operator|.
name|remove
argument_list|(
name|vts
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|serializerLock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
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
name|ValueTypeSerializer
argument_list|<
name|?
argument_list|>
name|vts
init|=
operator|(
name|ValueTypeSerializer
argument_list|<
name|?
argument_list|>
operator|)
name|service
decl_stmt|;
try|try
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|refs
init|=
name|valueTypeSerializerRefs
operator|.
name|get
argument_list|(
name|vts
operator|.
name|getType
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|refs
operator|!=
literal|null
condition|)
block|{
name|refs
operator|.
name|remove
argument_list|(
name|reference
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|refs
operator|=
operator|new
name|ArrayList
argument_list|<
name|ServiceReference
argument_list|>
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|valueTypeSerializerRefs
operator|.
name|put
argument_list|(
name|vts
operator|.
name|getType
argument_list|()
argument_list|,
name|refs
argument_list|)
expr_stmt|;
block|}
name|refs
operator|.
name|add
argument_list|(
name|reference
argument_list|)
expr_stmt|;
if|if
condition|(
name|refs
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
name|refs
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|serializerLock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

