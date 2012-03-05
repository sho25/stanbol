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
name|TEXT_HTML
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
name|CorsHelper
operator|.
name|addCORSOrigin
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
name|CorsHelper
operator|.
name|enableCORS
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
name|javax
operator|.
name|servlet
operator|.
name|ServletContext
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
name|OPTIONS
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
name|access
operator|.
name|TcManager
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
name|serializedform
operator|.
name|Serializer
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
name|ContextHelper
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
name|helper
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
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|view
operator|.
name|Viewable
import|;
end_import

begin_class
annotation|@
name|Path
argument_list|(
literal|"/enhancer/engine/{engineName}"
argument_list|)
specifier|public
class|class
name|EnhancementEngineResource
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
name|EnhancementEngineResource
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
parameter_list|,
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
name|super
argument_list|(
name|context
argument_list|)
expr_stmt|;
comment|// bind the job manager by looking it up from the servlet request context
comment|//        EnhancementEngineManager engineManager =
comment|//                ContextHelper.getServiceFromContext(EnhancementEngineManager.class, context);
if|if
condition|(
name|engineManager
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
operator|new
name|IllegalStateException
argument_list|(
literal|"EnhancementEngineManager service not available"
argument_list|)
argument_list|)
throw|;
block|}
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
comment|/**      * Wraps the engine with the highest service ranking with a      * {@link SingleEngineChain}.      * @see org.apache.stanbol.enhancer.jersey.resource.AbstractEnhancerResource#getChain()      */
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
annotation|@
name|OPTIONS
specifier|public
name|Response
name|handleCorsPreflight
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
argument_list|()
decl_stmt|;
name|enableCORS
argument_list|(
name|servletContext
argument_list|,
name|res
argument_list|,
name|headers
argument_list|)
expr_stmt|;
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
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|res
argument_list|,
name|headers
argument_list|)
expr_stmt|;
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
end_class

end_unit

