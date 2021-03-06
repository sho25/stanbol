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
name|Chain
operator|.
name|PROPERTY_NAME
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Constants
operator|.
name|OBJECTCLASS
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
name|ChainManager
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
comment|/**  * Utility similar to {@link ServiceTracker} that allows to track one/some/all  * {@link Chain}s. As convenience this also implements the  * {@link ChainManager} interface however the intended usage scenarios   * for this utility are considerable different to the using the   * ChainManager interface as a service.<p>  * This utility especially allows to<ul>  *<li> track only {@link Chains} with names as parsed in the  * constructor.  *<li> A {@link ServiceTrackerCustomizer} can be parsed to this utility. The  * methods of this interface will be called on changes to any service tracked  * by this instance. This allows users of this utility to update there internal  * state on any change of the state of tracked chains.  *</ul>  * Please not that calls to {@link #open()} and {@link #close()} are required  * to start and stop the tracking of this class. In general the same rules  * as for the {@link ServiceTracker} apply also to this utility.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|ChainsTracker
implements|implements
name|ChainManager
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
name|ChainsTracker
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|trackedChains
decl_stmt|;
specifier|private
name|NameBasedServiceTrackingState
name|nameTracker
decl_stmt|;
comment|/**      * Protected constructor intended to be used by subclasses that do not want      * to compete the initialisation as part of construction(e.g.      * implementations of the {@link ChainManager} interface the follow the      * OSGI component model).<p>      * Users that use this constructor MUST make sure to call      * {@link #initChainTracker(BundleContext, Set, ServiceTrackerCustomizer)}.       * Note that {@link #initChainTracker()} does NOT call {@link #open()}.<p>      * Access to the internal state is provided by the protected getters for the      * {@link ServiceTracker} and the {@link NameBasedServiceTrackingState} and      * the public {@link #getTrackedChains()} method.      */
specifier|protected
name|ChainsTracker
parameter_list|()
block|{
comment|/* nothing to do here */
block|}
comment|/**      * Creates a new {@link ChainsTracker} for the parsed {@link BundleContext}      * and chain names.      * Examples:      *<code><pre>      *     //Track all active chains      *     new ChainsTracker(context);      *           *     //Track only the chain with the name "dbpediaLinking"      *     new ChainsTracker(context,"dbpediaLinking");      *</pre></code>      * @param context The bundle context used to track chains      * @param chainNames the name of the chains to track. If empty       * all chains are tracked.      */
specifier|public
name|ChainsTracker
parameter_list|(
name|BundleContext
name|context
parameter_list|,
name|String
modifier|...
name|chainNames
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
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|names
decl_stmt|;
if|if
condition|(
name|chainNames
operator|==
literal|null
condition|)
block|{
name|names
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|names
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|chainNames
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|initChainTracker
argument_list|(
name|context
argument_list|,
name|names
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new {@link ChainsTracker} for the parsed {@link BundleContext}      * and chain names.      * Examples:      *<code><pre>      *     //Track all active chains with a customiser      *     new ChainsTracker(context,null,customiser);      *           *     //Track all chains with the names and use the customiser      *     //to react on changes      *     new ChainsTracker(context,chainNames,customiser);      *</pre></code>      * @param context the bundle context used to track chains      * @param chainNames the names of the chains to track. Parse<code>null</code>      * or an {@link Collections#emptySet()} to track all chains      * @param customizer the {@link ServiceTrackerCustomizer} used with this tracker.      */
specifier|public
name|ChainsTracker
parameter_list|(
name|BundleContext
name|context
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|chainNames
parameter_list|,
name|ServiceTrackerCustomizer
name|customizer
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
name|initChainTracker
argument_list|(
name|context
argument_list|,
name|chainNames
argument_list|,
name|customizer
argument_list|)
expr_stmt|;
block|}
comment|/**      * Initialises the {@link ChainsTracker} by using the parsed parameter.<p>      * This will create a copy of the parsed chainNames to avoid changes to the      * internal state due to external changes.      * @param context the {@link BundleContext}. MUST NOT be<code>null</code>.      * @param chainNames the chains to track.<code>null</code> or an empty Set      * to track all chains      * This Method can also be used to re-initialise an existing instance. Any      * existing {@link ServiceTracker} will be closed and the current state      * will be lost.      * @param customiser an optional service tracker customiser.      * @throws IllegalStateException it the parsed {@link BundleContext} is<code>null</code>      * @throws IllegalArgumentException if the parsed chainNames do only contain      * invalid Chain names. Even through null values and empty values are removed      * without failing it is assumed as error if the parsed set only contains      * such values.      */
specifier|protected
name|void
name|initChainTracker
parameter_list|(
name|BundleContext
name|context
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|chainNames
parameter_list|,
name|ServiceTrackerCustomizer
name|customiser
parameter_list|)
block|{
if|if
condition|(
name|nameTracker
operator|!=
literal|null
condition|)
block|{
comment|//if we re-initialise
name|nameTracker
operator|.
name|close
argument_list|()
expr_stmt|;
comment|//try to close the current ServiceTracker
block|}
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to initialise tracking if NULL is parsed as Bundle Context!"
argument_list|)
throw|;
block|}
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|trackedChains
decl_stmt|;
if|if
condition|(
name|chainNames
operator|==
literal|null
operator|||
name|chainNames
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|trackedChains
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
name|this
operator|.
name|trackedChains
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|//copy to not modify the parsed set and to avoid internal state
comment|//to be modified by changes of the parsed set.
name|trackedChains
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|chainNames
argument_list|)
expr_stmt|;
if|if
condition|(
name|trackedChains
operator|.
name|remove
argument_list|(
literal|null
argument_list|)
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"NULL element was removed from the parsed chain names"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|trackedChains
operator|.
name|remove
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"empty String element was removed from the parsed chain names"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|trackedChains
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed set with the chainNames "
operator|+
literal|"contained only invalid chain names. Parse NULL or an empty set"
operator|+
literal|"if you want to track all chains (parsed: '"
operator|+
name|chainNames
operator|+
literal|"')!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|trackedChains
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|chainNames
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"configured tracking for {}"
argument_list|,
name|trackedChains
operator|.
name|isEmpty
argument_list|()
condition|?
literal|"all Chains"
else|:
operator|(
literal|"the Chains "
operator|+
name|trackedChains
operator|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|trackedChains
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|this
operator|.
name|nameTracker
operator|=
operator|new
name|NameBasedServiceTrackingState
argument_list|(
name|context
argument_list|,
name|Chain
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|PROPERTY_NAME
argument_list|,
name|customiser
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|StringBuilder
name|filterString
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|filterString
operator|.
name|append
argument_list|(
literal|"(&"
argument_list|)
expr_stmt|;
name|filterString
operator|.
name|append
argument_list|(
literal|'('
argument_list|)
operator|.
name|append
argument_list|(
name|OBJECTCLASS
argument_list|)
operator|.
name|append
argument_list|(
literal|'='
argument_list|)
expr_stmt|;
name|filterString
operator|.
name|append
argument_list|(
name|Chain
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|')'
argument_list|)
expr_stmt|;
name|filterString
operator|.
name|append
argument_list|(
literal|"(|"
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|name
range|:
name|trackedChains
control|)
block|{
name|filterString
operator|.
name|append
argument_list|(
literal|'('
argument_list|)
operator|.
name|append
argument_list|(
name|PROPERTY_NAME
argument_list|)
expr_stmt|;
name|filterString
operator|.
name|append
argument_list|(
literal|'='
argument_list|)
operator|.
name|append
argument_list|(
name|name
argument_list|)
operator|.
name|append
argument_list|(
literal|')'
argument_list|)
expr_stmt|;
block|}
name|filterString
operator|.
name|append
argument_list|(
literal|"))"
argument_list|)
expr_stmt|;
try|try
block|{
name|this
operator|.
name|nameTracker
operator|=
operator|new
name|NameBasedServiceTrackingState
argument_list|(
name|context
argument_list|,
name|context
operator|.
name|createFilter
argument_list|(
name|filterString
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
name|PROPERTY_NAME
argument_list|,
name|customiser
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InvalidSyntaxException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unable to build Filter for the"
operator|+
literal|"parsed chain names "
operator|+
name|trackedChains
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
comment|/**      * Starts tracking based on the configuration parsed in the constructor      */
specifier|public
name|void
name|open
parameter_list|()
block|{
name|nameTracker
operator|.
name|open
argument_list|()
expr_stmt|;
block|}
comment|/**      * Closes this tracker      */
specifier|public
name|void
name|close
parameter_list|()
block|{
name|nameTracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|nameTracker
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * Getter for the list of tracked chain names. This set represents the      * names of chains tracked by this instance. It does not provide any      * indication if an {@link Chain} with that name is available      * or not.<p>      * If all chains are tracked by this ChainTracker instance this is      * Indicated by returning an empty Set.      * @return the tracked chains or an empty set if all chains are tracked.      */
specifier|public
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|getTrackedChains
parameter_list|()
block|{
return|return
name|trackedChains
return|;
block|}
comment|/*      * (non-Javadoc)      * @see org.apache.stanbol.enhancer.servicesapi.ChainManager#getReference(java.lang.String)      */
annotation|@
name|Override
specifier|public
name|ServiceReference
name|getReference
parameter_list|(
name|String
name|name
parameter_list|)
block|{
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
literal|"The parsed name MUST NOT be NULL or empty"
argument_list|)
throw|;
block|}
if|if
condition|(
name|trackedChains
operator|.
name|isEmpty
argument_list|()
operator|||
name|trackedChains
operator|.
name|contains
argument_list|(
name|name
argument_list|)
condition|)
block|{
return|return
name|nameTracker
operator|.
name|getReference
argument_list|(
name|name
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The Chain with the parsed name '"
operator|+
name|name
operator|+
literal|"' is not tracked (tracked: "
operator|+
name|trackedChains
operator|+
literal|")!"
argument_list|)
throw|;
block|}
block|}
comment|/*      * (non-Javadoc)      * @see org.apache.stanbol.enhancer.servicesapi.ChainManager#isChain(java.lang.String)      */
annotation|@
name|Override
specifier|public
name|boolean
name|isChain
parameter_list|(
name|String
name|name
parameter_list|)
block|{
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
literal|"The parsed name MUST NOT be NULL or empty"
argument_list|)
throw|;
block|}
return|return
name|nameTracker
operator|.
name|getReference
argument_list|(
name|name
argument_list|)
operator|!=
literal|null
return|;
block|}
comment|/*      * (non-Javadoc)      * @see org.apache.stanbol.enhancer.servicesapi.ChainManager#getReferences(java.lang.String)      */
annotation|@
name|Override
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
throws|throws
name|IllegalArgumentException
block|{
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
literal|"The parsed name MUST NOT be NULL or empty"
argument_list|)
throw|;
block|}
if|if
condition|(
name|trackedChains
operator|.
name|isEmpty
argument_list|()
operator|||
name|trackedChains
operator|.
name|contains
argument_list|(
name|name
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|refs
init|=
name|nameTracker
operator|.
name|getReferences
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
name|Collections
operator|.
name|emptyList
argument_list|()
expr_stmt|;
block|}
return|return
name|refs
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The chain with the parsed name '"
operator|+
name|name
operator|+
literal|"' is not tracked (tracked: "
operator|+
name|trackedChains
operator|+
literal|")!"
argument_list|)
throw|;
block|}
block|}
comment|/*      * (non-Javadoc)      * @see org.apache.stanbol.enhancer.servicesapi.ChainManager#getActiveChainNames()      */
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getActiveChainNames
parameter_list|()
block|{
return|return
name|nameTracker
operator|.
name|getNames
argument_list|()
return|;
block|}
comment|/**      * Getter for the map with the names and the {@link ServiceReference} of the       * chain with the highest priority for that name.      * @return the map with the names and {@link ServiceReference}s of all      * currently active and tracked chains      */
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|ServiceReference
argument_list|>
name|getActiveChainReferences
parameter_list|()
block|{
return|return
name|nameTracker
operator|.
name|getActive
argument_list|()
return|;
block|}
comment|/*      * (non-Javadoc)      * @see org.apache.stanbol.enhancer.servicesapi.ChainManager#getChain(java.lang.String)      */
annotation|@
name|Override
specifier|public
name|Chain
name|getChain
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|ServiceReference
name|ref
init|=
name|getReference
argument_list|(
name|name
argument_list|)
decl_stmt|;
return|return
name|ref
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|Chain
operator|)
name|nameTracker
operator|.
name|getService
argument_list|(
name|ref
argument_list|)
return|;
block|}
comment|/*      * (non-Javadoc)      * @see org.apache.stanbol.enhancer.servicesapi.ChainManager#getChain(org.osgi.framework.ServiceReference)      */
specifier|public
name|Chain
name|getChain
parameter_list|(
name|ServiceReference
name|chainReference
parameter_list|)
block|{
return|return
operator|(
name|Chain
operator|)
name|nameTracker
operator|.
name|getService
argument_list|(
name|chainReference
argument_list|)
return|;
block|}
comment|/*      * (non-Javadoc)      * @see org.apache.stanbol.enhancer.servicesapi.ChainManager#getDefault()      */
annotation|@
name|Override
specifier|public
name|Chain
name|getDefault
parameter_list|()
block|{
name|Chain
name|chain
init|=
name|getChain
argument_list|(
name|DEFAULT_CHAIN_NAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|chain
operator|==
literal|null
condition|)
block|{
name|chain
operator|=
operator|(
name|Chain
operator|)
name|nameTracker
operator|.
name|getService
argument_list|()
expr_stmt|;
block|}
return|return
name|chain
return|;
block|}
comment|/**      * Getter for the name based service tracker. {@link ServiceReference}s      * returned by this instance are guaranteed to refer to {@link Chain}      * services with names that are {@link #getTrackedChains() tracked} by this      * instance      * @return the chain tracking state      */
specifier|protected
specifier|final
name|NameBasedServiceTrackingState
name|getChainTrackingState
parameter_list|()
block|{
return|return
name|nameTracker
return|;
block|}
block|}
end_class

end_unit

