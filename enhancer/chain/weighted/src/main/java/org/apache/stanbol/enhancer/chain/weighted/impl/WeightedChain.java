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
name|weighted
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
name|ConfigUtils
operator|.
name|getState
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
name|helper
operator|.
name|ConfigUtils
operator|.
name|parseConfig
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
name|Arrays
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
name|helper
operator|.
name|ConfigUtils
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
name|impl
operator|.
name|AbstractChain
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
name|impl
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
comment|/**  * This Chain implementation takes a list of engines names as input   * and uses the "org.apache.stanbol.enhancer.engine.order " metadata provided by   * such engines to calculate the ExecutionGraph.<p>  *   * Similar the current WeightedJobManager implementation Engines would be  * dependent to each other based on decreasing order values. Engines with the   * same order value would could be executed in parallel.<p>  *   * This implementation is targeted for easy configuration - just a list of the   * engine names contained within a chain - but has limited possibilities to   * control the execution order within an chain. However it is expected   * that it provides enough flexibility for most of the usage scenarios.<p>  *   * This engine also supports the definition of additional parameters for  * Enhancement Engines. The syntax is the same as used by BND tools:   *<pre><code>  *&lt;engineName&gt;;&ltparam-name&gt=&ltparam-value&gt  *&lt;engineName&gt;;&ltparam-name&gt  *</code></pre>  * Parameter without value are interpreted as enabled boolean switch<p>  *   * Currently this Chain implementation supports the following Parameter:<ul>  *<li> optional: Boolean switch that allows to define that the execution of this  * engine is not required.  *</ul>  *   *<i>NOTE:</i> Since<code>0.12.1</code> this supports EnhancementProperties  * as described by<a href="https://issues.apache.org/jira/browse/STANBOL-488"></a>  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|configurationFactory
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|REQUIRE
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
name|Chain
operator|.
name|PROPERTY_NAME
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|WeightedChain
operator|.
name|PROPERTY_CHAIN
argument_list|,
name|cardinality
operator|=
literal|1000
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|AbstractChain
operator|.
name|PROPERTY_CHAIN_PROPERTIES
argument_list|,
name|cardinality
operator|=
literal|1000
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|,
name|intValue
operator|=
literal|0
argument_list|)
block|}
argument_list|)
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|Chain
operator|.
name|class
argument_list|)
specifier|public
class|class
name|WeightedChain
extends|extends
name|AbstractChain
implements|implements
name|Chain
implements|,
name|ServiceTrackerCustomizer
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
name|WeightedChain
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * The list of Enhancement Engine names used to build the Execution Plan       * based on there weights.       */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_CHAIN
init|=
literal|"stanbol.enhancer.chain.weighted.chain"
decl_stmt|;
comment|/**      * the Chain configuration as parsed in the {@link #activate(ComponentContext)} method      */
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|>
name|chain
decl_stmt|;
comment|/**      * Do hold chain scope EnhancementProperties of the configured chain.      */
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|>
name|chainScopedEnhProps
decl_stmt|;
comment|/**      * Tracks the engines defined in the {@link #chain}      */
specifier|private
name|EnginesTracker
name|tracker
decl_stmt|;
comment|/**      * Used to sync access to the {@link #executionPlan}      */
specifier|private
name|Object
name|epLock
init|=
operator|new
name|Object
argument_list|()
decl_stmt|;
specifier|private
name|Graph
name|executionPlan
init|=
literal|null
decl_stmt|;
annotation|@
name|Override
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
name|super
operator|.
name|activate
argument_list|(
name|ctx
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
name|PROPERTY_CHAIN
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|configuredChain
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|String
index|[]
condition|)
block|{
name|configuredChain
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|(
name|String
index|[]
operator|)
name|value
argument_list|)
argument_list|)
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
for|for
control|(
name|Object
name|o
operator|:
operator|(
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
name|value
control|)
block|{
if|if
condition|(
name|o
operator|instanceof
name|String
condition|)
block|{
name|configuredChain
operator|.
name|add
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_CHAIN
argument_list|,
literal|"The engines of a Weigted Chain MUST BE configured as a Array or "
operator|+
literal|"Collection of Strings (parsed: "
operator|+
operator|(
name|value
operator|!=
literal|null
condition|?
name|value
operator|.
name|getClass
argument_list|()
else|:
literal|"null"
operator|)
operator|+
literal|")"
argument_list|)
throw|;
block|}
comment|//now parse the configured chain
try|try
block|{
name|chain
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|parseConfig
argument_list|(
name|configuredChain
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_CHAIN
argument_list|,
literal|"Unable to parse Chain Configuraiton (message: '"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|"')!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
if|if
condition|(
name|chain
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_CHAIN
argument_list|,
literal|"The configured chain MUST at least contain a single valid entry!"
argument_list|)
throw|;
block|}
comment|//init the chain scoped enhancement properties
name|chainScopedEnhProps
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
if|if
condition|(
name|getChainProperties
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|chainScopedEnhProps
operator|.
name|put
argument_list|(
literal|null
argument_list|,
name|getChainProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|>
name|entry
range|:
name|chain
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|enhProp
init|=
name|ConfigUtils
operator|.
name|getEnhancementProperties
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|enhProp
operator|!=
literal|null
condition|)
block|{
name|chainScopedEnhProps
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|enhProp
argument_list|)
expr_stmt|;
block|}
block|}
comment|//start tracking the engines of the configured chain
name|tracker
operator|=
operator|new
name|EnginesTracker
argument_list|(
name|ctx
operator|.
name|getBundleContext
argument_list|()
argument_list|,
name|chain
operator|.
name|keySet
argument_list|()
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|tracker
operator|.
name|open
parameter_list|()
constructor_decl|;
block|}
end_class

begin_function
annotation|@
name|Override
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|tracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|tracker
operator|=
literal|null
expr_stmt|;
name|chainScopedEnhProps
operator|=
literal|null
expr_stmt|;
name|chain
operator|=
literal|null
expr_stmt|;
name|super
operator|.
name|deactivate
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
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
name|epLock
init|)
block|{
if|if
condition|(
name|executionPlan
operator|==
literal|null
condition|)
block|{
name|executionPlan
operator|=
name|createExecutionPlan
argument_list|()
expr_stmt|;
block|}
return|return
name|executionPlan
return|;
block|}
block|}
end_function

begin_function
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getEngines
parameter_list|()
block|{
return|return
name|chain
operator|.
name|keySet
argument_list|()
return|;
block|}
end_function

begin_comment
comment|/**      * Creates a new execution plan based on the configured {@link #chain} and      * the currently available {@link EnhancementEngine}s. If required      * {@link EnhancementEngine}s are missing a {@link ChainException} will be      * thrown.      * @return the execution plan      * @throws ChainException if a required {@link EnhancementEngine} of the      * configured {@link #chain} is not active.      */
end_comment

begin_function
specifier|private
name|Graph
name|createExecutionPlan
parameter_list|()
throws|throws
name|ChainException
block|{
name|List
argument_list|<
name|EnhancementEngine
argument_list|>
name|availableEngines
init|=
operator|new
name|ArrayList
argument_list|<
name|EnhancementEngine
argument_list|>
argument_list|(
name|chain
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|optionalEngines
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|missingEngines
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|>
name|entry
range|:
name|chain
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|boolean
name|optional
init|=
name|getState
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|,
literal|"optional"
argument_list|)
decl_stmt|;
name|EnhancementEngine
name|engine
init|=
name|tracker
operator|.
name|getEngine
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|engine
operator|!=
literal|null
condition|)
block|{
name|availableEngines
operator|.
name|add
argument_list|(
name|engine
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|missingEngines
operator|.
name|add
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|optional
condition|)
block|{
name|optionalEngines
operator|.
name|add
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|calculateExecutionPlan
argument_list|(
name|getName
argument_list|()
argument_list|,
name|availableEngines
argument_list|,
name|optionalEngines
argument_list|,
name|missingEngines
argument_list|,
name|chainScopedEnhProps
argument_list|)
return|;
block|}
end_function

begin_function
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
name|ComponentContext
name|context
init|=
name|this
operator|.
name|context
decl_stmt|;
if|if
condition|(
name|context
operator|!=
literal|null
condition|)
block|{
return|return
name|context
operator|.
name|getBundleContext
argument_list|()
operator|.
name|getService
argument_list|(
name|reference
argument_list|)
return|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to get EnhancementEngine for Reference {} because"
operator|+
literal|"this {} seams already be deactivated -> return null"
argument_list|,
name|reference
operator|.
name|getProperty
argument_list|(
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|)
argument_list|,
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
end_function

begin_function
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
end_function

begin_function
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
name|ComponentContext
name|context
init|=
name|this
operator|.
name|context
decl_stmt|;
if|if
condition|(
name|context
operator|!=
literal|null
condition|)
block|{
name|context
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
block|}
end_function

begin_function
specifier|private
name|void
name|invalidateExecutionPlan
parameter_list|()
block|{
synchronized|synchronized
init|(
name|epLock
init|)
block|{
name|this
operator|.
name|executionPlan
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_function

unit|}
end_unit

