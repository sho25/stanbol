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
name|jersey
operator|.
name|resource
package|;
end_package

begin_import
import|import static
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
operator|.
name|APPLICATION_JSON
import|;
end_import

begin_import
import|import static
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
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|N3
import|;
end_import

begin_import
import|import static
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
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|N_TRIPLE
import|;
end_import

begin_import
import|import static
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
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|RDF_JSON
import|;
end_import

begin_import
import|import static
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
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|RDF_XML
import|;
end_import

begin_import
import|import static
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
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|TURTLE
import|;
end_import

begin_import
import|import static
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
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|X_TURTLE
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|utils
operator|.
name|MediaTypeUtil
operator|.
name|JSON_LD
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
name|jersey
operator|.
name|utils
operator|.
name|EnhancerUtils
operator|.
name|addActiveEngines
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
name|jersey
operator|.
name|utils
operator|.
name|EnhancerUtils
operator|.
name|buildEnginesMap
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
name|Comparator
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
name|Map
operator|.
name|Entry
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
name|GET
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
name|Path
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
name|PathParam
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
name|Produces
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
name|WebApplicationException
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
name|Context
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
name|HttpHeaders
import|;
end_import

begin_import
import|import static
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
operator|.
name|TEXT_HTML
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
name|Response
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
name|Response
operator|.
name|ResponseBuilder
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
name|UriInfo
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
name|impl
operator|.
name|SimpleMGraph
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
name|commons
operator|.
name|web
operator|.
name|viewable
operator|.
name|Viewable
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
name|web
operator|.
name|base
operator|.
name|resource
operator|.
name|BaseStanbolResource
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
name|web
operator|.
name|base
operator|.
name|resource
operator|.
name|LayoutConfiguration
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
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentItemFactory
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
name|EnhancementEngineManager
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
name|EnhancementJobManager
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
name|impl
operator|.
name|EnginesTracker
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
name|SingleEngineChain
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

begin_class
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|Object
operator|.
name|class
argument_list|)
annotation|@
name|Property
argument_list|(
name|name
operator|=
literal|"javax.ws.rs"
argument_list|,
name|boolValue
operator|=
literal|true
argument_list|)
annotation|@
name|Path
argument_list|(
literal|"/enhancer/engine"
argument_list|)
specifier|public
class|class
name|EnhancementEnginesRootResource
extends|extends
name|BaseStanbolResource
block|{
annotation|@
name|Reference
specifier|private
name|EnhancementJobManager
name|jobManager
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|ChainManager
name|chainManager
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|ContentItemFactory
name|ciFactory
decl_stmt|;
comment|/**      * Read-only list of active engines. Do not access directly as this is      * lazy initialised after any change in the list of active {@link EnhancementEngine}.       * Use {@link #getActiveEngine(String)} and {@link #getActiveEngines()} instead.       */
specifier|protected
name|Map
argument_list|<
name|String
argument_list|,
name|Entry
argument_list|<
name|ServiceReference
argument_list|,
name|EnhancementEngine
argument_list|>
argument_list|>
name|_enginesCache
decl_stmt|;
comment|/**      * Tracks available EnhancementEngines      */
specifier|private
name|EnginesTracker
name|engineTracker
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
block|{
specifier|final
name|BundleContext
name|bc
init|=
name|ctx
operator|.
name|getBundleContext
argument_list|()
decl_stmt|;
name|engineTracker
operator|=
operator|new
name|EnginesTracker
argument_list|(
name|bc
argument_list|,
name|Collections
operator|.
expr|<
name|String
operator|>
name|emptySet
argument_list|()
argument_list|,
operator|new
name|ServiceTrackerCustomizer
argument_list|()
block|{
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
name|bc
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
name|_enginesCache
operator|=
literal|null
expr_stmt|;
comment|//rebuild the cache on the next call
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
name|_enginesCache
operator|=
literal|null
expr_stmt|;
comment|//rebuild the cache on the next call
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
name|reference
operator|!=
literal|null
condition|)
block|{
name|bc
operator|.
name|ungetService
argument_list|(
name|reference
argument_list|)
expr_stmt|;
name|_enginesCache
operator|=
literal|null
expr_stmt|;
comment|//rebuild the cache on the next call
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|engineTracker
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
if|if
condition|(
name|engineTracker
operator|!=
literal|null
condition|)
block|{
name|engineTracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|engineTracker
operator|=
literal|null
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the list of active EnhancementEngines      * @return the list of active EnhancementEngines (both {@link ServiceReference}      * and service).      */
specifier|protected
name|Collection
argument_list|<
name|Entry
argument_list|<
name|ServiceReference
argument_list|,
name|EnhancementEngine
argument_list|>
argument_list|>
name|getActiveEngines
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Entry
argument_list|<
name|ServiceReference
argument_list|,
name|EnhancementEngine
argument_list|>
argument_list|>
name|engines
init|=
name|getEnginesMap
argument_list|()
decl_stmt|;
return|return
name|engines
operator|.
name|values
argument_list|()
return|;
block|}
comment|/**      * Getter for an active engine by name      * @param name the name of the engine      * @return the active EnhancementEngine (both {@link ServiceReference}      * and service) or<code>null<code> if none is active with this name      */
specifier|protected
name|Entry
argument_list|<
name|ServiceReference
argument_list|,
name|EnhancementEngine
argument_list|>
name|getActiveEngine
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|getEnginesMap
argument_list|()
operator|.
name|get
argument_list|(
name|name
argument_list|)
return|;
block|}
comment|/**      * @return      */
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Entry
argument_list|<
name|ServiceReference
argument_list|,
name|EnhancementEngine
argument_list|>
argument_list|>
name|getEnginesMap
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Entry
argument_list|<
name|ServiceReference
argument_list|,
name|EnhancementEngine
argument_list|>
argument_list|>
name|engines
init|=
name|_enginesCache
decl_stmt|;
if|if
condition|(
name|engines
operator|==
literal|null
condition|)
block|{
name|engines
operator|=
name|buildEnginesMap
argument_list|(
name|engineTracker
argument_list|)
expr_stmt|;
name|this
operator|.
name|_enginesCache
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|engines
argument_list|)
expr_stmt|;
block|}
return|return
name|engines
return|;
block|}
comment|/* @OPTIONS     public Response handleCorsPreflight(@Context HttpHeaders headers){         ResponseBuilder res = Response.ok();        // enableCORS(servletContext,res, headers);         return res.build();     }*/
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|TEXT_HTML
argument_list|)
specifier|public
name|Response
name|get
parameter_list|(
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|ResponseBuilder
name|res
init|=
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"index"
argument_list|,
name|this
argument_list|)
argument_list|,
name|TEXT_HTML
argument_list|)
decl_stmt|;
comment|//addCORSOrigin(servletContext,res, headers);
return|return
name|res
operator|.
name|build
argument_list|()
return|;
block|}
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|value
operator|=
block|{
name|JSON_LD
block|,
name|APPLICATION_JSON
block|,
name|N3
block|,
name|N_TRIPLE
block|,
name|RDF_JSON
block|,
name|RDF_XML
block|,
name|TURTLE
block|,
name|X_TURTLE
block|}
argument_list|)
specifier|public
name|Response
name|getEngines
parameter_list|(
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|String
name|rootUrl
init|=
name|uriInfo
operator|.
name|getBaseUriBuilder
argument_list|()
operator|.
name|path
argument_list|(
name|getRootUrl
argument_list|()
argument_list|)
operator|.
name|build
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|MGraph
name|graph
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|addActiveEngines
argument_list|(
name|getActiveEngines
argument_list|()
argument_list|,
name|graph
argument_list|,
name|rootUrl
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|res
init|=
name|Response
operator|.
name|ok
argument_list|(
name|graph
argument_list|)
decl_stmt|;
comment|//     addCORSOrigin(servletContext,res, headers);
return|return
name|res
operator|.
name|build
argument_list|()
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|EnhancementEngine
argument_list|>
name|getEngines
parameter_list|()
block|{
name|List
argument_list|<
name|EnhancementEngine
argument_list|>
name|engines
init|=
operator|new
name|ArrayList
argument_list|<
name|EnhancementEngine
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|ServiceReference
argument_list|,
name|EnhancementEngine
argument_list|>
name|entry
range|:
name|getActiveEngines
argument_list|()
control|)
block|{
name|engines
operator|.
name|add
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|engines
argument_list|,
operator|new
name|Comparator
argument_list|<
name|EnhancementEngine
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|EnhancementEngine
name|o1
parameter_list|,
name|EnhancementEngine
name|o2
parameter_list|)
block|{
return|return
name|o1
operator|.
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o2
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
return|return
name|engines
return|;
block|}
specifier|public
name|String
name|getServicePid
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|Entry
argument_list|<
name|ServiceReference
argument_list|,
name|EnhancementEngine
argument_list|>
name|entry
init|=
name|getActiveEngine
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|String
operator|)
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_PID
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
name|Integer
name|getServiceRanking
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|Entry
argument_list|<
name|ServiceReference
argument_list|,
name|EnhancementEngine
argument_list|>
name|entry
init|=
name|getActiveEngine
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|Integer
name|ranking
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
name|ranking
operator|=
operator|(
name|Integer
operator|)
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ranking
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|ranking
return|;
block|}
block|}
specifier|public
name|Long
name|getServiceId
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|Entry
argument_list|<
name|ServiceReference
argument_list|,
name|EnhancementEngine
argument_list|>
name|entry
init|=
name|getActiveEngine
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|Long
operator|)
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_ID
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
annotation|@
name|Path
argument_list|(
literal|"{engineName}"
argument_list|)
specifier|public
name|EngineResource
name|getEngine
parameter_list|(
annotation|@
name|PathParam
argument_list|(
name|value
operator|=
literal|"engineName"
argument_list|)
name|String
name|name
parameter_list|)
block|{
return|return
operator|new
name|EngineResource
argument_list|(
name|name
argument_list|,
name|jobManager
argument_list|,
name|engineTracker
argument_list|,
name|chainManager
argument_list|,
name|ciFactory
argument_list|,
name|getLayoutConfiguration
argument_list|()
argument_list|,
name|getUriInfo
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
class|class
name|EngineResource
extends|extends
name|AbstractEnhancerResource
block|{
specifier|private
specifier|final
name|List
argument_list|<
name|EnhancementEngine
argument_list|>
name|engines
decl_stmt|;
specifier|private
specifier|final
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|engineRefs
decl_stmt|;
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
specifier|public
name|EngineResource
parameter_list|(
name|String
name|name
parameter_list|,
name|EnhancementJobManager
name|jobManager
parameter_list|,
name|EnhancementEngineManager
name|engineManager
parameter_list|,
name|ChainManager
name|chainManager
parameter_list|,
name|ContentItemFactory
name|ciFactory
parameter_list|,
name|LayoutConfiguration
name|layoutConfiguration
parameter_list|,
name|UriInfo
name|uriInfo
parameter_list|)
block|{
name|super
argument_list|(
name|jobManager
argument_list|,
name|engineManager
argument_list|,
name|chainManager
argument_list|,
name|ciFactory
argument_list|,
name|layoutConfiguration
argument_list|,
name|uriInfo
argument_list|)
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|engineRefs
operator|=
name|engineManager
operator|.
name|getReferences
argument_list|(
name|name
argument_list|)
expr_stmt|;
if|if
condition|(
name|engineRefs
operator|==
literal|null
operator|||
name|engineRefs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|NOT_FOUND
argument_list|)
throw|;
block|}
name|engines
operator|=
operator|new
name|ArrayList
argument_list|<
name|EnhancementEngine
argument_list|>
argument_list|(
name|engineRefs
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|ServiceReference
argument_list|>
name|it
init|=
name|engineRefs
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
name|EnhancementEngine
name|engine
init|=
name|engineManager
operator|.
name|getEngine
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|engine
operator|==
literal|null
condition|)
block|{
comment|//removed in the meantime
name|it
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|engines
operator|.
name|add
argument_list|(
name|engine
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|engines
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//in the meantime deactivated ...
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|NOT_FOUND
argument_list|)
throw|;
block|}
block|}
comment|/**          * Wraps the engine with the highest service ranking with a          * {@link SingleEngineChain}.          *          * @see          * org.apache.stanbol.enhancer.jersey.resource.AbstractEnhancerResource#getChain()          */
annotation|@
name|Override
specifier|protected
name|Chain
name|getChain
parameter_list|()
block|{
return|return
operator|new
name|SingleEngineChain
argument_list|(
name|engines
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
block|}
comment|/*   @OPTIONS          public Response handleCorsPreflight(@Context HttpHeaders headers){          ResponseBuilder res = Response.ok();          enableCORS(servletContext,res, headers);          return res.build();          }*/
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|TEXT_HTML
argument_list|)
specifier|public
name|Response
name|get
parameter_list|(
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|ResponseBuilder
name|res
init|=
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"index"
argument_list|,
name|this
argument_list|)
argument_list|,
name|TEXT_HTML
argument_list|)
decl_stmt|;
comment|// addCORSOrigin(servletContext,res, headers);
return|return
name|res
operator|.
name|build
argument_list|()
return|;
block|}
specifier|public
name|EnhancementEngine
name|getEngine
parameter_list|()
block|{
return|return
name|engines
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
specifier|public
name|List
argument_list|<
name|EnhancementEngine
argument_list|>
name|getEngines
parameter_list|()
block|{
return|return
name|engines
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
specifier|public
name|Integer
name|getRanking
parameter_list|()
block|{
name|Integer
name|ranking
init|=
operator|(
name|Integer
operator|)
name|engineRefs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|)
decl_stmt|;
if|if
condition|(
name|ranking
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|ranking
return|;
block|}
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
operator|(
name|Long
operator|)
name|engineRefs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_ID
argument_list|)
return|;
block|}
specifier|public
name|String
name|getPid
parameter_list|()
block|{
return|return
operator|(
name|String
operator|)
name|engineRefs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_PID
argument_list|)
return|;
block|}
specifier|public
name|Integer
name|getOrdering
parameter_list|()
block|{
name|Integer
name|ordering
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|engines
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|instanceof
name|ServiceProperties
condition|)
block|{
name|ordering
operator|=
call|(
name|Integer
call|)
argument_list|(
operator|(
name|ServiceProperties
operator|)
name|engines
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|.
name|getServiceProperties
argument_list|()
operator|.
name|get
argument_list|(
name|ServiceProperties
operator|.
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ordering
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|ordering
return|;
block|}
block|}
specifier|public
name|boolean
name|isMultipleEngines
parameter_list|()
block|{
return|return
name|engines
operator|.
name|size
argument_list|()
operator|>
literal|1
return|;
block|}
specifier|public
name|List
argument_list|<
name|EnhancementEngine
argument_list|>
name|getAdditionalEngines
parameter_list|()
block|{
return|return
name|engines
operator|.
name|subList
argument_list|(
literal|1
argument_list|,
name|engines
operator|.
name|size
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

