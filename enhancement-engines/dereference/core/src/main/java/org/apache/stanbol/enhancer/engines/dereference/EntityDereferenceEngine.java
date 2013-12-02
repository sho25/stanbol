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
name|enhancer
operator|.
name|engines
operator|.
name|dereference
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|DC_LANGUAGE
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|ENHANCER_ENTITY_REFERENCE
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
name|Callable
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
name|ExecutionException
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
name|Future
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
name|Lock
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Language
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|NonLiteral
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Resource
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Triple
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
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
name|lang
operator|.
name|StringUtils
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
name|stanboltools
operator|.
name|offline
operator|.
name|OfflineMode
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
name|ContentItem
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
name|EngineException
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
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|ServiceProperties
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
name|helper
operator|.
name|EnhancementEngineHelper
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
name|rdf
operator|.
name|Properties
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
name|EntityDereferenceEngine
implements|implements
name|EnhancementEngine
implements|,
name|ServiceProperties
implements|,
name|DereferenceConstants
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
name|EntityDereferenceEngine
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * By default the EntityDereferenceEngine does use {@link ServiceProperties#ORDERING_POST_PROCESSING}      */
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_ENGINE_ORDERING
init|=
name|ServiceProperties
operator|.
name|ORDERING_POST_PROCESSING
decl_stmt|;
comment|/**      * If the offline mode is enabled enforced for dereferencing Entities      */
specifier|private
name|boolean
name|offline
decl_stmt|;
specifier|protected
specifier|final
name|EntityDereferencer
name|dereferencer
decl_stmt|;
specifier|protected
specifier|final
name|DereferenceEngineConfig
name|config
decl_stmt|;
specifier|protected
specifier|final
name|String
name|name
decl_stmt|;
specifier|protected
specifier|final
name|boolean
name|filterContentLanguages
decl_stmt|;
specifier|protected
specifier|final
name|boolean
name|filterAcceptLanguages
decl_stmt|;
comment|/**      * The Map holding the {@link #serviceProperties} for this engine.      */
specifier|protected
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|serviceProperties
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
comment|/**      * Unmodifiable view over {@link #serviceProperties} returned by      * {@link #getServiceProperties()}      */
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|unmodServiceProperties
init|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|serviceProperties
argument_list|)
decl_stmt|;
specifier|public
name|EntityDereferenceEngine
parameter_list|(
name|EntityDereferencer
name|dereferencer
parameter_list|,
name|DereferenceEngineConfig
name|config
parameter_list|)
block|{
if|if
condition|(
name|config
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed DereferenceEngineConfig MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|config
operator|=
name|config
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|config
operator|.
name|getEngineName
argument_list|()
expr_stmt|;
name|this
operator|.
name|filterContentLanguages
operator|=
name|config
operator|.
name|isFilterContentLanguages
argument_list|()
expr_stmt|;
name|this
operator|.
name|filterAcceptLanguages
operator|=
name|config
operator|.
name|isFilterAcceptLanguages
argument_list|()
expr_stmt|;
if|if
condition|(
name|dereferencer
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed EntityDereferencer MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|dereferencer
operator|=
name|dereferencer
expr_stmt|;
comment|//init the defautl ordering
name|setEngineOrdering
argument_list|(
name|DEFAULT_ENGINE_ORDERING
argument_list|)
expr_stmt|;
block|}
comment|/**      * Setter for the offline mode. This method is typically called of      * {@link OfflineMode} is injected to the component registering an instance      * of this Engine implementation      * @param mode the offline mode      */
specifier|public
name|void
name|setOfflineMode
parameter_list|(
name|boolean
name|mode
parameter_list|)
block|{
name|this
operator|.
name|offline
operator|=
name|mode
expr_stmt|;
block|}
specifier|public
name|boolean
name|isOfflineMode
parameter_list|()
block|{
return|return
name|offline
return|;
block|}
comment|/**      * Setter for the {@link ServiceProperties#ENHANCEMENT_ENGINE_ORDERING      * engine ordering}.      * @param ordering The ordering or<code>null</code> to set the       * {@value #DEFAULT_ENGINE_ORDERING default} for this engine.      */
specifier|public
name|void
name|setEngineOrdering
parameter_list|(
name|Integer
name|ordering
parameter_list|)
block|{
name|serviceProperties
operator|.
name|put
argument_list|(
name|ServiceProperties
operator|.
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|,
name|ordering
operator|==
literal|null
condition|?
name|DEFAULT_ENGINE_ORDERING
else|:
name|ordering
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Integer
name|getEngineOrdering
parameter_list|()
block|{
return|return
operator|(
name|Integer
operator|)
name|serviceProperties
operator|.
name|get
argument_list|(
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|)
return|;
block|}
comment|/**      * Getter for the config of this engine      * @return the Dereference Engine Configuration      */
specifier|public
name|DereferenceEngineConfig
name|getConfig
parameter_list|()
block|{
return|return
name|config
return|;
block|}
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getServiceProperties
parameter_list|()
block|{
return|return
name|unmodServiceProperties
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|canEnhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
if|if
condition|(
name|offline
operator|&&
operator|!
name|dereferencer
operator|.
name|supportsOfflineMode
argument_list|()
condition|)
block|{
return|return
name|CANNOT_ENHANCE
return|;
block|}
else|else
block|{
return|return
name|ENHANCE_ASYNC
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|computeEnhancements
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
if|if
condition|(
name|offline
operator|&&
operator|!
name|dereferencer
operator|.
name|supportsOfflineMode
argument_list|()
condition|)
block|{
comment|//entity dereferencer does no longer support offline mode
return|return;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"> dereference Entities for ContentItem {}"
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|DereferenceContext
name|derefContext
init|=
operator|new
name|DereferenceContext
argument_list|(
name|offline
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|includedLangs
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|//TODO: parse accept languages as soon as Enhancement properties are implemented
specifier|final
name|MGraph
name|metadata
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|UriRef
argument_list|>
name|referencedEntities
init|=
operator|new
name|HashSet
argument_list|<
name|UriRef
argument_list|>
argument_list|()
decl_stmt|;
comment|//(1) read all Entities we need to dereference from the parsed contentItem
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
comment|//parse the languages detected for the content
if|if
condition|(
name|filterContentLanguages
condition|)
block|{
for|for
control|(
name|NonLiteral
name|langAnno
range|:
name|EnhancementEngineHelper
operator|.
name|getLanguageAnnotations
argument_list|(
name|metadata
argument_list|)
control|)
block|{
name|includedLangs
operator|.
name|add
argument_list|(
name|EnhancementEngineHelper
operator|.
name|getString
argument_list|(
name|metadata
argument_list|,
name|langAnno
argument_list|,
name|DC_LANGUAGE
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|//no content language filtering - leave contentLanguages empty
comment|//parse the referenced entities from the graph
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|entityReferences
init|=
name|metadata
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|ENHANCER_ENTITY_REFERENCE
argument_list|,
literal|null
argument_list|)
decl_stmt|;
while|while
condition|(
name|entityReferences
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Triple
name|triple
init|=
name|entityReferences
operator|.
name|next
argument_list|()
decl_stmt|;
name|Resource
name|entityReference
init|=
name|triple
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|entityReference
operator|instanceof
name|UriRef
condition|)
block|{
name|boolean
name|added
init|=
name|referencedEntities
operator|.
name|add
argument_list|(
operator|(
name|UriRef
operator|)
name|entityReference
argument_list|)
decl_stmt|;
if|if
condition|(
name|added
operator|&&
name|log
operator|.
name|isTraceEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|trace
argument_list|(
literal|"  ... schedule Entity {}"
argument_list|,
name|entityReference
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|log
operator|.
name|isWarnEnabled
argument_list|()
condition|)
block|{
comment|//log enhancement that use a fise:entiy-reference with a non UriRef value!
name|NonLiteral
name|enhancement
init|=
name|triple
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"Can not dereference invalid Enhancement {}"
argument_list|,
name|enhancement
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|metadata
operator|.
name|filter
argument_list|(
name|enhancement
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"   {}"
argument_list|,
name|it
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
finally|finally
block|{
name|ci
operator|.
name|getLock
argument_list|()
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
operator|!
name|includedLangs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|includedLangs
operator|.
name|add
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|//also include literals without language
comment|//and set the list to the dereference context
name|derefContext
operator|.
name|setLanguages
argument_list|(
name|includedLangs
argument_list|)
expr_stmt|;
block|}
comment|//else no filterLanguages set ... nothing to do
specifier|final
name|Lock
name|writeLock
init|=
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
decl_stmt|;
name|log
operator|.
name|trace
argument_list|(
literal|" - scheduled {} Entities for dereferencing"
argument_list|,
name|referencedEntities
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
comment|//(2) dereference the Entities
name|ExecutorService
name|executor
init|=
name|dereferencer
operator|.
name|getExecutor
argument_list|()
decl_stmt|;
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|UriRef
argument_list|>
name|failedEntities
init|=
operator|new
name|HashSet
argument_list|<
name|UriRef
argument_list|>
argument_list|()
decl_stmt|;
name|int
name|dereferencedCount
init|=
literal|0
decl_stmt|;
name|List
argument_list|<
name|DereferenceJob
argument_list|>
name|dereferenceJobs
init|=
operator|new
name|ArrayList
argument_list|<
name|DereferenceJob
argument_list|>
argument_list|(
name|referencedEntities
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|executor
operator|!=
literal|null
operator|&&
operator|!
name|executor
operator|.
name|isShutdown
argument_list|()
condition|)
block|{
comment|//dereference using executor
comment|//schedule all entities to dereference
for|for
control|(
specifier|final
name|UriRef
name|entity
range|:
name|referencedEntities
control|)
block|{
name|DereferenceJob
name|dereferenceJob
init|=
operator|new
name|DereferenceJob
argument_list|(
name|entity
argument_list|,
name|metadata
argument_list|,
name|writeLock
argument_list|,
name|derefContext
argument_list|)
decl_stmt|;
name|dereferenceJob
operator|.
name|setFuture
argument_list|(
name|executor
operator|.
name|submit
argument_list|(
name|dereferenceJob
argument_list|)
argument_list|)
expr_stmt|;
name|dereferenceJobs
operator|.
name|add
argument_list|(
name|dereferenceJob
argument_list|)
expr_stmt|;
block|}
comment|//wait for all entities to be dereferenced
for|for
control|(
name|DereferenceJob
name|dereferenceJob
range|:
name|dereferenceJobs
control|)
block|{
try|try
block|{
if|if
condition|(
name|dereferenceJob
operator|.
name|await
argument_list|()
condition|)
block|{
name|dereferencedCount
operator|++
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
comment|// Restore the interrupted status
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|interrupt
argument_list|()
expr_stmt|;
throw|throw
operator|new
name|EngineException
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
literal|"Interupted while waiting for dereferencing Entities"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|ExecutionException
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getCause
argument_list|()
operator|instanceof
name|DereferenceException
condition|)
block|{
name|failedEntities
operator|.
name|add
argument_list|(
name|dereferenceJob
operator|.
name|entity
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|" ... error while dereferencing "
operator|+
name|dereferenceJob
operator|.
name|entity
operator|+
literal|"!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//unknown error
throw|throw
operator|new
name|EngineException
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
literal|"Unchecked Error while "
operator|+
literal|"dereferencing Entity "
operator|+
name|dereferenceJob
operator|.
name|entity
operator|+
literal|"!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
block|}
else|else
block|{
comment|//dereference using the current thread
for|for
control|(
name|UriRef
name|entity
range|:
name|referencedEntities
control|)
block|{
try|try
block|{
name|log
operator|.
name|trace
argument_list|(
literal|"  ... dereference {}"
argument_list|,
name|entity
argument_list|)
expr_stmt|;
if|if
condition|(
name|dereferencer
operator|.
name|dereference
argument_list|(
name|entity
argument_list|,
name|metadata
argument_list|,
name|writeLock
argument_list|,
name|derefContext
argument_list|)
condition|)
block|{
name|dereferencedCount
operator|++
expr_stmt|;
name|log
operator|.
name|trace
argument_list|(
literal|"    + success"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|trace
argument_list|(
literal|"    - not found"
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|DereferenceException
name|e
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" ... error while dereferencing "
operator|+
name|entity
operator|+
literal|"!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|failedEntities
operator|.
name|add
argument_list|(
name|entity
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|long
name|duration
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
decl_stmt|;
if|if
condition|(
operator|!
name|failedEntities
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|" - unable to dereference {} of {} for ContentItem {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|failedEntities
operator|.
name|size
argument_list|()
block|,
name|referencedEntities
operator|.
name|size
argument_list|()
block|,
name|ci
operator|.
name|getUri
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" - dereferenced {} of {} Entities in {}ms ({}ms/dereferenced)"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|dereferencedCount
block|,
name|referencedEntities
operator|.
name|size
argument_list|()
block|,
name|duration
block|,
operator|(
name|duration
operator|*
literal|100
operator|/
name|dereferencedCount
operator|)
operator|/
literal|100.0f
block|}
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/**      * Used both as {@link Callable} submitted to the {@link ExecutorService}      * and as object to {@link #await()} the completion of the task.      * @author Rupert Westenthaler      *      */
class|class
name|DereferenceJob
implements|implements
name|Callable
argument_list|<
name|Boolean
argument_list|>
block|{
specifier|final
name|UriRef
name|entity
decl_stmt|;
specifier|final
name|MGraph
name|metadata
decl_stmt|;
specifier|final
name|Lock
name|writeLock
decl_stmt|;
specifier|final
name|DereferenceContext
name|derefContext
decl_stmt|;
specifier|private
name|Future
argument_list|<
name|Boolean
argument_list|>
name|future
decl_stmt|;
name|DereferenceJob
parameter_list|(
name|UriRef
name|entity
parameter_list|,
name|MGraph
name|metadata
parameter_list|,
name|Lock
name|writeLock
parameter_list|,
name|DereferenceContext
name|derefContext
parameter_list|)
block|{
name|this
operator|.
name|entity
operator|=
name|entity
expr_stmt|;
name|this
operator|.
name|metadata
operator|=
name|metadata
expr_stmt|;
name|this
operator|.
name|writeLock
operator|=
name|writeLock
expr_stmt|;
name|this
operator|.
name|derefContext
operator|=
name|derefContext
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Boolean
name|call
parameter_list|()
throws|throws
name|DereferenceException
block|{
name|log
operator|.
name|trace
argument_list|(
literal|"  ... dereference {}"
argument_list|,
name|entity
argument_list|)
expr_stmt|;
name|boolean
name|state
init|=
name|dereferencer
operator|.
name|dereference
argument_list|(
name|entity
argument_list|,
name|metadata
argument_list|,
name|writeLock
argument_list|,
name|derefContext
argument_list|)
decl_stmt|;
if|if
condition|(
name|state
condition|)
block|{
name|log
operator|.
name|trace
argument_list|(
literal|"    + success"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|trace
argument_list|(
literal|"    - not found"
argument_list|)
expr_stmt|;
block|}
return|return
name|state
return|;
block|}
name|void
name|setFuture
parameter_list|(
name|Future
argument_list|<
name|Boolean
argument_list|>
name|future
parameter_list|)
block|{
name|this
operator|.
name|future
operator|=
name|future
expr_stmt|;
block|}
specifier|public
name|boolean
name|await
parameter_list|()
throws|throws
name|InterruptedException
throws|,
name|ExecutionException
block|{
return|return
name|future
operator|.
name|get
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

