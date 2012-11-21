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
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|ldviewable
operator|.
name|Viewable
import|;
end_import

begin_class
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
specifier|private
specifier|final
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
decl_stmt|;
specifier|public
name|EnhancementEnginesRootResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
comment|// bind the job manager by looking it up from the servlet request context
name|EnhancementEngineManager
name|engineManager
init|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|EnhancementEngineManager
operator|.
name|class
argument_list|,
name|context
argument_list|)
decl_stmt|;
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
literal|"The required EnhancementEngineManager Service is not available!"
argument_list|)
argument_list|)
throw|;
block|}
name|engines
operator|=
name|buildEnginesMap
argument_list|(
name|engineManager
argument_list|)
expr_stmt|;
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
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|value
operator|=
block|{
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
name|engines
operator|.
name|values
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
name|Collection
argument_list|<
name|EnhancementEngine
argument_list|>
name|getEngines
parameter_list|()
block|{
name|Set
argument_list|<
name|EnhancementEngine
argument_list|>
name|engines
init|=
operator|new
name|HashSet
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
name|this
operator|.
name|engines
operator|.
name|values
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
name|engines
operator|.
name|get
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
name|engines
operator|.
name|get
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
name|engines
operator|.
name|get
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
block|}
end_class

end_unit

