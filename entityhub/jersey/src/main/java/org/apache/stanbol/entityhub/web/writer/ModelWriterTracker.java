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
name|web
operator|.
name|writer
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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedHashSet
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
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
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
name|web
operator|.
name|ModelWriter
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

begin_class
specifier|public
class|class
name|ModelWriterTracker
extends|extends
name|ServiceTracker
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
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
comment|/**      * Holds the config      */
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|MediaType
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
argument_list|>
name|writers
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|MediaType
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
comment|/**      * Caches requests for MediaTypes and types      */
specifier|private
specifier|final
name|Map
argument_list|<
name|CacheKey
argument_list|,
name|Collection
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|cache
init|=
operator|new
name|HashMap
argument_list|<
name|CacheKey
argument_list|,
name|Collection
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
comment|/**      * lock for {@link #writers} and {@link #cache}      */
specifier|private
specifier|final
name|ReadWriteLock
name|lock
init|=
operator|new
name|ReentrantReadWriteLock
argument_list|()
decl_stmt|;
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
name|super
operator|.
name|addingService
argument_list|(
name|reference
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|MediaType
argument_list|>
name|mediaTypes
init|=
name|parseMediaTypes
argument_list|(
operator|(
operator|(
name|ModelWriter
operator|)
name|service
operator|)
operator|.
name|supportedMediaTypes
argument_list|()
argument_list|)
decl_stmt|;
name|Class
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|nativeType
init|=
operator|(
operator|(
name|ModelWriter
operator|)
name|service
operator|)
operator|.
name|getNativeType
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|mediaTypes
operator|.
name|isEmpty
argument_list|()
condition|)
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
for|for
control|(
name|MediaType
name|mediaType
range|:
name|mediaTypes
control|)
block|{
name|addModelWriter
argument_list|(
name|nativeType
argument_list|,
name|mediaType
argument_list|,
name|reference
argument_list|)
expr_stmt|;
block|}
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
return|return
name|service
return|;
block|}
else|else
block|{
comment|//else no MediaTypes registered
return|return
literal|null
return|;
comment|//ignore this service
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
if|if
condition|(
name|service
operator|!=
literal|null
condition|)
block|{
name|Set
argument_list|<
name|MediaType
argument_list|>
name|mediaTypes
init|=
name|parseMediaTypes
argument_list|(
operator|(
operator|(
name|ModelWriter
operator|)
name|service
operator|)
operator|.
name|supportedMediaTypes
argument_list|()
argument_list|)
decl_stmt|;
name|Class
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|nativeType
init|=
operator|(
operator|(
name|ModelWriter
operator|)
name|service
operator|)
operator|.
name|getNativeType
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|mediaTypes
operator|.
name|isEmpty
argument_list|()
condition|)
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
for|for
control|(
name|MediaType
name|mediaType
range|:
name|mediaTypes
control|)
block|{
name|removeModelWriter
argument_list|(
name|nativeType
argument_list|,
name|mediaType
argument_list|,
name|reference
argument_list|)
expr_stmt|;
block|}
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
block|}
name|super
operator|.
name|removedService
argument_list|(
name|reference
argument_list|,
name|service
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
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
name|super
operator|.
name|modifiedService
argument_list|(
name|reference
argument_list|,
name|service
argument_list|)
expr_stmt|;
if|if
condition|(
name|service
operator|!=
literal|null
condition|)
block|{
name|Set
argument_list|<
name|MediaType
argument_list|>
name|mediaTypes
init|=
name|parseMediaTypes
argument_list|(
operator|(
operator|(
name|ModelWriter
operator|)
name|service
operator|)
operator|.
name|supportedMediaTypes
argument_list|()
argument_list|)
decl_stmt|;
name|Class
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|nativeType
init|=
operator|(
operator|(
name|ModelWriter
operator|)
name|service
operator|)
operator|.
name|getNativeType
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|mediaTypes
operator|.
name|isEmpty
argument_list|()
condition|)
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
for|for
control|(
name|MediaType
name|mediaType
range|:
name|mediaTypes
control|)
block|{
name|updateModelWriter
argument_list|(
name|nativeType
argument_list|,
name|mediaType
argument_list|,
name|reference
argument_list|)
expr_stmt|;
block|}
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
block|}
block|}
comment|/**      * @param reference      * @param key      */
specifier|private
name|void
name|addModelWriter
parameter_list|(
name|Class
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|nativeType
parameter_list|,
name|MediaType
name|mediaType
parameter_list|,
name|ServiceReference
name|reference
parameter_list|)
block|{
comment|//we want to have all ModelWriters under the null key
name|log
operator|.
name|debug
argument_list|(
literal|"> add ModelWriter format: {}, bundle: {}, nativeType: {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|mediaType
block|,
name|reference
operator|.
name|getBundle
argument_list|()
block|,
name|nativeType
operator|!=
literal|null
condition|?
name|nativeType
operator|.
name|getName
argument_list|()
else|:
literal|"none"
block|}
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|MediaType
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|typeWriters
init|=
name|writers
operator|.
name|get
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|addTypeWriter
argument_list|(
name|typeWriters
argument_list|,
name|mediaType
argument_list|,
name|reference
argument_list|)
expr_stmt|;
if|if
condition|(
name|nativeType
operator|!=
literal|null
condition|)
block|{
comment|//register also as native type writers
name|typeWriters
operator|=
name|writers
operator|.
name|get
argument_list|(
name|nativeType
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|typeWriters
operator|==
literal|null
condition|)
block|{
name|typeWriters
operator|=
operator|new
name|HashMap
argument_list|<
name|MediaType
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|writers
operator|.
name|put
argument_list|(
name|nativeType
operator|.
name|getName
argument_list|()
argument_list|,
name|typeWriters
argument_list|)
expr_stmt|;
block|}
name|addTypeWriter
argument_list|(
name|typeWriters
argument_list|,
name|mediaType
argument_list|,
name|reference
argument_list|)
expr_stmt|;
block|}
name|cache
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|//clear the cache after a change
block|}
comment|/**      * @param typeWriters      * @param mediaType      * @param reference      */
specifier|private
name|void
name|addTypeWriter
parameter_list|(
name|Map
argument_list|<
name|MediaType
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|typeWriters
parameter_list|,
name|MediaType
name|mediaType
parameter_list|,
name|ServiceReference
name|reference
parameter_list|)
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|l
decl_stmt|;
name|l
operator|=
name|typeWriters
operator|.
name|get
argument_list|(
name|mediaType
argument_list|)
expr_stmt|;
if|if
condition|(
name|l
operator|==
literal|null
condition|)
block|{
name|l
operator|=
operator|new
name|ArrayList
argument_list|<
name|ServiceReference
argument_list|>
argument_list|()
expr_stmt|;
name|typeWriters
operator|.
name|put
argument_list|(
name|mediaType
argument_list|,
name|l
argument_list|)
expr_stmt|;
block|}
name|l
operator|.
name|add
argument_list|(
name|reference
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|l
argument_list|)
expr_stmt|;
comment|//service ranking based sorting
block|}
comment|/**      * @param key      * @param reference      */
specifier|private
name|void
name|removeModelWriter
parameter_list|(
name|Class
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|nativeType
parameter_list|,
name|MediaType
name|mediaType
parameter_list|,
name|ServiceReference
name|reference
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"> remove ModelWriter format: {}, service: {}, nativeType: {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|mediaType
block|,
name|reference
block|,
name|nativeType
operator|!=
literal|null
condition|?
name|nativeType
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|"none"
block|}
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|MediaType
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|typeWriters
init|=
name|writers
operator|.
name|get
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|removeTypeWriter
argument_list|(
name|typeWriters
argument_list|,
name|mediaType
argument_list|,
name|reference
argument_list|)
expr_stmt|;
if|if
condition|(
name|nativeType
operator|!=
literal|null
condition|)
block|{
name|typeWriters
operator|=
name|writers
operator|.
name|get
argument_list|(
name|nativeType
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|typeWriters
operator|!=
literal|null
condition|)
block|{
name|removeTypeWriter
argument_list|(
name|typeWriters
argument_list|,
name|mediaType
argument_list|,
name|reference
argument_list|)
expr_stmt|;
if|if
condition|(
name|typeWriters
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|writers
operator|.
name|remove
argument_list|(
name|nativeType
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|cache
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|//clear the cache after a change
block|}
comment|/**      * @param typeWriters      * @param mediaType      * @param reference      */
specifier|private
name|void
name|removeTypeWriter
parameter_list|(
name|Map
argument_list|<
name|MediaType
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|typeWriters
parameter_list|,
name|MediaType
name|mediaType
parameter_list|,
name|ServiceReference
name|reference
parameter_list|)
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|l
init|=
name|typeWriters
operator|.
name|get
argument_list|(
name|mediaType
argument_list|)
decl_stmt|;
if|if
condition|(
name|l
operator|!=
literal|null
operator|&&
name|l
operator|.
name|remove
argument_list|(
name|reference
argument_list|)
operator|&&
name|l
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|writers
operator|.
name|remove
argument_list|(
name|mediaType
argument_list|)
expr_stmt|;
comment|//remove empty mediaTypes
block|}
block|}
comment|/**      * @param key      * @param reference      */
specifier|private
name|void
name|updateModelWriter
parameter_list|(
name|Class
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|nativeType
parameter_list|,
name|MediaType
name|mediaType
parameter_list|,
name|ServiceReference
name|reference
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"> update ModelWriter format: {}, service: {}, nativeType: {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|mediaType
block|,
name|reference
block|,
name|nativeType
operator|!=
literal|null
condition|?
name|nativeType
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|"none"
block|}
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|MediaType
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|typeWriters
init|=
name|writers
operator|.
name|get
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|updateTypeWriter
argument_list|(
name|typeWriters
argument_list|,
name|mediaType
argument_list|,
name|reference
argument_list|)
expr_stmt|;
if|if
condition|(
name|nativeType
operator|!=
literal|null
condition|)
block|{
name|typeWriters
operator|=
name|writers
operator|.
name|get
argument_list|(
name|nativeType
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|typeWriters
operator|!=
literal|null
condition|)
block|{
name|updateTypeWriter
argument_list|(
name|typeWriters
argument_list|,
name|mediaType
argument_list|,
name|reference
argument_list|)
expr_stmt|;
block|}
block|}
name|cache
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|//clear the cache after a change
block|}
comment|/**      * @param typeWriters      * @param mediaType      * @param reference      */
specifier|private
name|void
name|updateTypeWriter
parameter_list|(
name|Map
argument_list|<
name|MediaType
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|typeWriters
parameter_list|,
name|MediaType
name|mediaType
parameter_list|,
name|ServiceReference
name|reference
parameter_list|)
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|l
init|=
name|typeWriters
operator|.
name|get
argument_list|(
name|mediaType
argument_list|)
decl_stmt|;
if|if
condition|(
name|l
operator|!=
literal|null
operator|&&
name|l
operator|.
name|contains
argument_list|(
name|reference
argument_list|)
condition|)
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|l
argument_list|)
expr_stmt|;
comment|//maybe service.ranking has changed
block|}
block|}
specifier|public
name|ModelWriterTracker
parameter_list|(
name|BundleContext
name|context
parameter_list|)
block|{
name|super
argument_list|(
name|context
argument_list|,
name|ModelWriter
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
comment|//add the union key value mapping
name|writers
operator|.
name|put
argument_list|(
literal|null
argument_list|,
operator|new
name|HashMap
argument_list|<
name|MediaType
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param mts      * @return      */
specifier|private
name|Set
argument_list|<
name|MediaType
argument_list|>
name|parseMediaTypes
parameter_list|(
name|Collection
argument_list|<
name|MediaType
argument_list|>
name|mts
parameter_list|)
block|{
if|if
condition|(
name|mts
operator|==
literal|null
operator|||
name|mts
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|emptySet
argument_list|()
return|;
block|}
name|Set
argument_list|<
name|MediaType
argument_list|>
name|mediaTypes
init|=
operator|new
name|HashSet
argument_list|<
name|MediaType
argument_list|>
argument_list|(
name|mts
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|MediaType
name|mt
range|:
name|mts
control|)
block|{
if|if
condition|(
name|mt
operator|!=
literal|null
condition|)
block|{
comment|//strip all parameters
name|MediaType
name|mediaType
init|=
name|mt
operator|.
name|getParameters
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
name|mt
else|:
operator|new
name|MediaType
argument_list|(
name|mt
operator|.
name|getType
argument_list|()
argument_list|,
name|mt
operator|.
name|getSubtype
argument_list|()
argument_list|)
decl_stmt|;
name|mediaTypes
operator|.
name|add
argument_list|(
name|mediaType
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|mediaTypes
return|;
block|}
comment|/**      * Getter for a sorted list of References to {@link ModelWriter} that can      * serialise Representations to the parsed {@link MediaType}. If a      * nativeType of the Representation is given {@link ModelWriter} for that      * specific type will be preferred.      * @param mediaType The {@link MediaType}. Wildcards are supported      * @param nativeType optionally the native type of the {@link Representation}      * @return A sorted collection of references to compatible {@link ModelWriter}.      * Use {@link #getService()} to receive the actual service. However note that      * such calls may return<code>null</code> if the service was deactivated in      * the meantime.      */
specifier|public
name|Collection
argument_list|<
name|ServiceReference
argument_list|>
name|getModelWriters
parameter_list|(
name|MediaType
name|mediaType
parameter_list|,
name|Class
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|nativeType
parameter_list|)
block|{
name|Collection
argument_list|<
name|ServiceReference
argument_list|>
name|refs
decl_stmt|;
name|String
name|nativeTypeName
init|=
name|nativeType
operator|==
literal|null
condition|?
literal|null
else|:
name|nativeType
operator|.
name|getName
argument_list|()
decl_stmt|;
name|CacheKey
name|key
init|=
operator|new
name|CacheKey
argument_list|(
name|mediaType
argument_list|,
name|nativeTypeName
argument_list|)
decl_stmt|;
name|lock
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|refs
operator|=
name|cache
operator|.
name|get
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|lock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|refs
operator|==
literal|null
condition|)
block|{
comment|//not found in cache
name|refs
operator|=
operator|new
name|ArrayList
argument_list|<
name|ServiceReference
argument_list|>
argument_list|()
expr_stmt|;
name|Map
argument_list|<
name|MediaType
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|typeWriters
init|=
name|writers
operator|.
name|get
argument_list|(
name|nativeTypeName
argument_list|)
decl_stmt|;
if|if
condition|(
name|typeWriters
operator|!=
literal|null
condition|)
block|{
comment|//there are some native writers for this type
name|refs
operator|.
name|addAll
argument_list|(
name|getTypeWriters
argument_list|(
name|typeWriters
argument_list|,
name|mediaType
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|nativeType
operator|!=
literal|null
condition|)
block|{
comment|//if we have a native type
comment|//also add writers for the generic type to the end
name|refs
operator|.
name|addAll
argument_list|(
name|getTypeWriters
argument_list|(
name|writers
operator|.
name|get
argument_list|(
literal|null
argument_list|)
argument_list|,
name|mediaType
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|refs
operator|=
name|Collections
operator|.
name|unmodifiableCollection
argument_list|(
name|refs
argument_list|)
expr_stmt|;
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
name|cache
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|refs
argument_list|)
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
block|}
return|return
name|refs
return|;
block|}
specifier|private
name|Collection
argument_list|<
name|ServiceReference
argument_list|>
name|getTypeWriters
parameter_list|(
name|Map
argument_list|<
name|MediaType
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|typeWriters
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
block|{
comment|//use a linked has set to keep order but filter duplicates
name|Collection
argument_list|<
name|ServiceReference
argument_list|>
name|refs
init|=
operator|new
name|LinkedHashSet
argument_list|<
name|ServiceReference
argument_list|>
argument_list|()
decl_stmt|;
name|boolean
name|wildcard
init|=
name|mediaType
operator|.
name|isWildcardSubtype
argument_list|()
operator|||
name|mediaType
operator|.
name|isWildcardType
argument_list|()
decl_stmt|;
name|lock
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
name|wildcard
condition|)
block|{
comment|//add writer that explicitly mention this type first
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|l
init|=
name|typeWriters
operator|.
name|get
argument_list|(
name|mediaType
argument_list|)
decl_stmt|;
if|if
condition|(
name|l
operator|!=
literal|null
condition|)
block|{
name|refs
operator|.
name|addAll
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
block|}
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|wildcardMatches
init|=
literal|null
decl_stmt|;
name|int
name|count
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|MediaType
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|entry
range|:
name|typeWriters
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|MediaType
name|mt
init|=
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
if|if
condition|(
name|mt
operator|.
name|isCompatible
argument_list|(
name|mediaType
argument_list|)
operator|&&
comment|//ignore exact matches already treated above
operator|(
name|wildcard
operator|||
operator|!
name|mt
operator|.
name|equals
argument_list|(
name|mediaType
argument_list|)
operator|)
condition|)
block|{
if|if
condition|(
name|count
operator|==
literal|0
condition|)
block|{
name|wildcardMatches
operator|=
name|entry
operator|.
name|getValue
argument_list|()
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|count
operator|==
literal|1
condition|)
block|{
name|wildcardMatches
operator|=
operator|new
name|ArrayList
argument_list|<
name|ServiceReference
argument_list|>
argument_list|(
name|wildcardMatches
argument_list|)
expr_stmt|;
block|}
name|wildcardMatches
operator|.
name|addAll
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|count
operator|>
literal|1
condition|)
block|{
comment|//sort matches for different media types
name|Collections
operator|.
name|sort
argument_list|(
name|wildcardMatches
argument_list|)
expr_stmt|;
block|}
comment|//add wildcard matches to the linked has set
if|if
condition|(
name|count
operator|>
literal|0
condition|)
block|{
name|refs
operator|.
name|addAll
argument_list|(
name|wildcardMatches
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|lock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
return|return
name|refs
return|;
block|}
annotation|@
name|Override
specifier|public
name|ModelWriter
name|getService
parameter_list|()
block|{
return|return
operator|(
name|ModelWriter
operator|)
name|super
operator|.
name|getService
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|ModelWriter
name|getService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|)
block|{
return|return
operator|(
name|ModelWriter
operator|)
name|super
operator|.
name|getService
argument_list|(
name|reference
argument_list|)
return|;
block|}
comment|/**      * Used as key for {@link ModelWriterTracker#cache}      */
specifier|private
specifier|static
class|class
name|CacheKey
block|{
specifier|final
name|String
name|nativeType
decl_stmt|;
specifier|final
name|MediaType
name|mediaType
decl_stmt|;
name|CacheKey
parameter_list|(
name|MediaType
name|mediaType
parameter_list|,
name|String
name|nativeType
parameter_list|)
block|{
name|this
operator|.
name|nativeType
operator|=
name|nativeType
expr_stmt|;
name|this
operator|.
name|mediaType
operator|=
name|mediaType
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
specifier|final
name|int
name|prime
init|=
literal|31
decl_stmt|;
name|int
name|result
init|=
literal|1
decl_stmt|;
name|result
operator|=
name|prime
operator|*
name|result
operator|+
name|mediaType
operator|.
name|hashCode
argument_list|()
expr_stmt|;
name|result
operator|=
name|prime
operator|*
name|result
operator|+
operator|(
operator|(
name|nativeType
operator|==
literal|null
operator|)
condition|?
literal|0
else|:
name|nativeType
operator|.
name|hashCode
argument_list|()
operator|)
expr_stmt|;
return|return
name|result
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|obj
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|obj
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getClass
argument_list|()
operator|!=
name|obj
operator|.
name|getClass
argument_list|()
condition|)
return|return
literal|false
return|;
name|CacheKey
name|other
init|=
operator|(
name|CacheKey
operator|)
name|obj
decl_stmt|;
if|if
condition|(
operator|!
name|mediaType
operator|.
name|equals
argument_list|(
name|other
operator|.
name|mediaType
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|nativeType
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|nativeType
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
block|}
elseif|else
if|if
condition|(
operator|!
name|nativeType
operator|.
name|equals
argument_list|(
name|other
operator|.
name|nativeType
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
block|}
block|}
end_class

end_unit

