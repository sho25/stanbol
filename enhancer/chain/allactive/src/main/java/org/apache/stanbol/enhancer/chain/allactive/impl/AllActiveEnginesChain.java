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
name|chain
operator|.
name|allactive
operator|.
name|impl
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
name|helper
operator|.
name|ExecutionPlanHelper
operator|.
name|calculateExecutionPlan
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
name|Set
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
name|Graph
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
name|Deactivate
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
name|ChainException
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
name|EnginesTracker
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
name|ServiceTrackerCustomizer
import|;
end_import

begin_comment
comment|/**  * Chain implementation that uses all currently active {@link EnhancementEngine}s  * to build the execution plan based on the   * {@link ServiceProperties#ENHANCEMENT_ENGINE_ORDERING ordering} values of such  * engines.<p>  * The execution plan produced by this Chain is the same as used by the  * Stanbol Enhancer before the introduction of enhancement {@link Chain}s.<p>  * An instance of this chain is registerd under:<ul>  *<li> {@link EnhancementEngine#PROPERTY_NAME} = "default"  *<li> {@link Constants#SERVICE_RANKING} = {@link Integer#MIN_VALUE}  *</ul>  * by the {@link DefaultChain} component. This ensures that the behaviour of  * the Stanbol Enhancer - to enhance parsed {@link ContentItem}s by using all  * currently active {@link EnhancementEngine}s - is still the default after the  * introduction of {@link Chain}s. See the documentation of the Stanbol Enhancer  * for details.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|AllActiveEnginesChain
implements|implements
name|ServiceTrackerCustomizer
implements|,
name|Chain
block|{
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
specifier|final
name|Object
name|lock
init|=
operator|new
name|Object
argument_list|()
decl_stmt|;
specifier|private
name|BundleContext
name|context
decl_stmt|;
specifier|private
name|Graph
name|executionPlan
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|engineNames
decl_stmt|;
specifier|private
name|EnginesTracker
name|tracker
decl_stmt|;
specifier|public
name|AllActiveEnginesChain
parameter_list|(
name|BundleContext
name|context
parameter_list|,
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed BundleContext MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Chain name MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|trackAll
init|=
name|Collections
operator|.
name|emptySet
argument_list|()
decl_stmt|;
name|this
operator|.
name|tracker
operator|=
operator|new
name|EnginesTracker
argument_list|(
name|context
argument_list|,
name|trackAll
argument_list|,
comment|//empty set to track all engines
name|this
argument_list|)
expr_stmt|;
name|this
operator|.
name|tracker
operator|.
name|open
argument_list|()
expr_stmt|;
block|}
comment|/**      * This internally used an {@link EnginesTracker} to track currently active      * {@link EnhancementEngine}. This will {@link EnginesTracker#close() close}      * this tracker and also clear other member variables      */
specifier|public
name|void
name|close
parameter_list|()
block|{
synchronized|synchronized
init|(
name|lock
init|)
block|{
name|this
operator|.
name|executionPlan
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|engineNames
operator|=
literal|null
expr_stmt|;
block|}
name|this
operator|.
name|tracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|this
operator|.
name|tracker
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|name
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|finalize
parameter_list|()
throws|throws
name|Throwable
block|{
name|close
argument_list|()
expr_stmt|;
name|super
operator|.
name|finalize
argument_list|()
expr_stmt|;
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
annotation|@
name|Override
specifier|public
name|Graph
name|getExecutionPlan
parameter_list|()
throws|throws
name|ChainException
block|{
synchronized|synchronized
init|(
name|lock
init|)
block|{
if|if
condition|(
name|executionPlan
operator|==
literal|null
condition|)
block|{
name|update
argument_list|()
expr_stmt|;
block|}
return|return
name|executionPlan
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getEngines
parameter_list|()
throws|throws
name|ChainException
block|{
synchronized|synchronized
init|(
name|lock
init|)
block|{
if|if
condition|(
name|engineNames
operator|==
literal|null
condition|)
block|{
name|update
argument_list|()
expr_stmt|;
block|}
return|return
name|engineNames
return|;
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
name|invalidateExecutionPlan
argument_list|()
expr_stmt|;
return|return
name|context
operator|.
name|getService
argument_list|(
name|reference
argument_list|)
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
name|invalidateExecutionPlan
argument_list|()
expr_stmt|;
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
name|invalidateExecutionPlan
argument_list|()
expr_stmt|;
name|context
operator|.
name|ungetService
argument_list|(
name|reference
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|invalidateExecutionPlan
parameter_list|()
block|{
synchronized|synchronized
init|(
name|lock
init|)
block|{
name|this
operator|.
name|executionPlan
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|engineNames
operator|=
literal|null
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|update
parameter_list|()
throws|throws
name|ChainException
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|activeEngineNames
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|tracker
operator|.
name|getActiveEngineNames
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|activeEngineNames
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ChainException
argument_list|(
literal|"Currently there are no active EnhancementEngines available"
argument_list|)
throw|;
block|}
name|List
argument_list|<
name|EnhancementEngine
argument_list|>
name|activeEngines
init|=
operator|new
name|ArrayList
argument_list|<
name|EnhancementEngine
argument_list|>
argument_list|(
name|activeEngineNames
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|String
argument_list|>
name|names
init|=
name|activeEngineNames
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|names
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|name
init|=
name|names
operator|.
name|next
argument_list|()
decl_stmt|;
name|EnhancementEngine
name|engine
init|=
name|tracker
operator|.
name|getEngine
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|engine
operator|!=
literal|null
condition|)
block|{
name|activeEngines
operator|.
name|add
argument_list|(
name|engine
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//looks like the config has changed in the meantime
name|names
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
name|Set
argument_list|<
name|String
argument_list|>
name|emptySet
init|=
name|Collections
operator|.
name|emptySet
argument_list|()
decl_stmt|;
name|executionPlan
operator|=
name|calculateExecutionPlan
argument_list|(
name|activeEngines
argument_list|,
name|emptySet
argument_list|,
comment|//this Chain does not support optional engines
name|emptySet
argument_list|)
expr_stmt|;
comment|//only active meaning that no engines are missing
name|engineNames
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|activeEngineNames
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

