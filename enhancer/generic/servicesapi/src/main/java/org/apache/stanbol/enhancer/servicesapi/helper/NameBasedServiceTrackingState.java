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
name|servicesapi
operator|.
name|helper
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
name|HashMap
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
name|Map
operator|.
name|Entry
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
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|Chain
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|EnhancementEngine
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

begin_comment
comment|/**  * Used to manage the state of ServiceReferences for services that are   * accessed based on the value of a specific property.<p>  * This can be used to track both {@link EnhancementEngine}s as well as  * {@link Chain}s.<p>  * This implementation supports the use of {@link #readLock()} on returned  * values. Also the<code>null</code> as value for the parsed name property.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|NameBasedServiceTrackingState
extends|extends
name|ServiceTracker
implements|implements
name|ServiceTrackerCustomizer
block|{
comment|//    /**
comment|//     * Allows to forward to an other customiser after this class has finished
comment|//     * his work
comment|//     */
specifier|private
specifier|final
name|ServiceTrackerCustomizer
name|customizer
decl_stmt|;
comment|/**      * Lock used to protect acces to {@link #state} and {@link #tracked}      */
name|ReadWriteLock
name|lock
init|=
operator|new
name|ReentrantReadWriteLock
argument_list|()
decl_stmt|;
comment|/**      * This member uses lazzy initialisation and is set back to<code>null</code>      * after every change of a tracked service. Use {@link #getState()}      * to access this the internal state.<p>      * Mapping from the names to the {@link ServiceReference}s of the      * Engines. ServiveReferences are sorted by {@link Constants#SERVICE_RANKING}.<p>      * Note that values of Entries are not modified on changes but replaced      * by new instances. Because of this one needs only sync the retrieval      * but not further accesses to the list.      */
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|state_
decl_stmt|;
comment|/**      * Property used to retrieve the name of the component via the properties      * provided by the {@link ServiceReference}      */
specifier|private
specifier|final
name|String
name|property
decl_stmt|;
comment|/**      * Creates a trackingState with an optional customiser      * @param context the {@link BundleContext} used for tracking. MUST NOT      * be<code>null</code>.      * @param filter The filter used for the ServiceTracker      * @param nameProperty the property used to lookup the name of the tracked      * services. This MUST NOT be<code>null</code> nor empty.      * @param customizer optionally a customiser used with this tracker      * @throws IllegalArgumentException it the parsed nameProperty is       *<code>null</code> or an empty string.      */
specifier|public
name|NameBasedServiceTrackingState
parameter_list|(
name|BundleContext
name|context
parameter_list|,
name|Filter
name|filter
parameter_list|,
name|String
name|nameProperty
parameter_list|,
name|ServiceTrackerCustomizer
name|customizer
parameter_list|)
block|{
name|super
argument_list|(
name|context
argument_list|,
name|filter
argument_list|,
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|nameProperty
operator|==
literal|null
operator|||
name|nameProperty
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"the property to lookup the "
operator|+
literal|"name of tracked services MUST NOT be NULL nor emtoy!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|property
operator|=
name|nameProperty
expr_stmt|;
name|this
operator|.
name|customizer
operator|=
name|customizer
expr_stmt|;
block|}
comment|/**      * Creates a trackingState with an optional customiser      * @param context the {@link BundleContext} used for tracking. MUST NOT      * be<code>null</code>.      * @param clazz The type of the tracked services      * @param nameProperty the property used to lookup the name of the tracked      * services. This MUST NOT be<code>null</code> nor empty.      * @param customizer optionally a customiser used with this tracker      * @throws IllegalArgumentException it the parsed nameProperty is       *<code>null</code> or an empty string.      */
specifier|public
name|NameBasedServiceTrackingState
parameter_list|(
name|BundleContext
name|context
parameter_list|,
name|String
name|clazz
parameter_list|,
name|String
name|nameProperty
parameter_list|,
name|ServiceTrackerCustomizer
name|customizer
parameter_list|)
block|{
name|super
argument_list|(
name|context
argument_list|,
name|clazz
argument_list|,
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|nameProperty
operator|==
literal|null
operator|||
name|nameProperty
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"the property to lookup the "
operator|+
literal|"name of tracked services MUST NOT be NULL nor emtoy!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|property
operator|=
name|nameProperty
expr_stmt|;
name|this
operator|.
name|customizer
operator|=
name|customizer
expr_stmt|;
block|}
comment|/**      * Getter for the read only set of names of all currently active and       * tracked engines      * @return the names of all currently active and tracked engines      */
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getNames
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|state
init|=
name|getState
argument_list|()
decl_stmt|;
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|state
operator|.
name|keySet
argument_list|()
argument_list|)
return|;
block|}
finally|finally
block|{
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the read only names -&gt; {@link ServiceReference} of the       * currently active and tracked engines.      * @return the name -&gt; {@link ServiceReference} mapping of all active      * engines.      */
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|ServiceReference
argument_list|>
name|getActive
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|state
init|=
name|getState
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|ServiceReference
argument_list|>
name|active
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|ServiceReference
argument_list|>
argument_list|(
name|state
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|entry
range|:
name|state
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|active
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|active
argument_list|)
return|;
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
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|this
operator|.
name|state_
operator|=
literal|null
expr_stmt|;
block|}
finally|finally
block|{
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
specifier|final
name|Object
name|service
decl_stmt|;
if|if
condition|(
name|customizer
operator|!=
literal|null
condition|)
block|{
name|service
operator|=
name|customizer
operator|.
name|addingService
argument_list|(
name|reference
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|service
operator|=
name|context
operator|.
name|getService
argument_list|(
name|reference
argument_list|)
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
name|modifiedService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|,
name|Object
name|service
parameter_list|)
block|{
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|this
operator|.
name|state_
operator|=
literal|null
expr_stmt|;
block|}
finally|finally
block|{
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|customizer
operator|!=
literal|null
condition|)
block|{
name|customizer
operator|.
name|modifiedService
argument_list|(
name|reference
argument_list|,
name|service
argument_list|)
expr_stmt|;
block|}
comment|//else nothing to do
block|}
comment|/**      * Looks like {@link #modifiedService(ServiceReference, Object)} is not       * called on property changes (add is used instead). Parsed      * {@link ServiceReference} objects are not equals even if they are for the      * same {@link ServiceRegistration}. So the only way to keep the state in      * sync is to rebuild it after every change to a tracked service.      */
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|getState
parameter_list|()
block|{
name|lock
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
name|this
operator|.
name|state_
operator|==
literal|null
condition|)
block|{
comment|//Temporary map to collect the values
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|tmp
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|ServiceReference
index|[]
name|serviceRefs
init|=
name|getServiceReferences
argument_list|()
decl_stmt|;
if|if
condition|(
name|serviceRefs
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|ServiceReference
name|ref
range|:
name|serviceRefs
control|)
block|{
name|String
name|name
init|=
operator|(
name|String
operator|)
name|ref
operator|.
name|getProperty
argument_list|(
name|property
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|refs
init|=
name|tmp
operator|.
name|get
argument_list|(
name|name
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
literal|3
argument_list|)
expr_stmt|;
name|tmp
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|refs
argument_list|)
expr_stmt|;
block|}
name|refs
operator|.
name|add
argument_list|(
name|ref
argument_list|)
expr_stmt|;
block|}
block|}
comment|//now iterate a second time to sort and make values immutable an
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|state
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
argument_list|(
name|tmp
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|entry
range|:
name|tmp
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|getValue
argument_list|()
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
name|entry
operator|.
name|getValue
argument_list|()
argument_list|,
name|ServiceReferenceRankingComparator
operator|.
name|INSTANCE
argument_list|)
expr_stmt|;
block|}
name|state
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|state_
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|state
argument_list|)
expr_stmt|;
block|}
return|return
name|state_
return|;
block|}
finally|finally
block|{
name|lock
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
name|removedService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|,
name|Object
name|service
parameter_list|)
block|{
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|this
operator|.
name|state_
operator|=
literal|null
expr_stmt|;
block|}
finally|finally
block|{
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|customizer
operator|!=
literal|null
condition|)
block|{
name|customizer
operator|.
name|removedService
argument_list|(
name|reference
argument_list|,
name|service
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|context
operator|.
name|ungetService
argument_list|(
name|reference
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|getReferences
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|state
init|=
name|getState
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|refs
init|=
name|state
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
return|return
name|refs
operator|==
literal|null
condition|?
literal|null
else|:
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|refs
argument_list|)
return|;
block|}
specifier|public
name|ServiceReference
name|getReference
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|state
init|=
name|getState
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|refs
init|=
name|state
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
return|return
name|refs
operator|==
literal|null
condition|?
literal|null
else|:
name|refs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
block|}
end_class

end_unit

