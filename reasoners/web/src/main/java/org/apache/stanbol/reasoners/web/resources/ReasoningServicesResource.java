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
name|reasoners
operator|.
name|web
operator|.
name|resources
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
name|Set
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
name|Status
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
name|reasoners
operator|.
name|servicesapi
operator|.
name|ReasoningService
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
name|reasoners
operator|.
name|servicesapi
operator|.
name|ReasoningServicesManager
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
name|reasoners
operator|.
name|servicesapi
operator|.
name|UnboundReasoningServiceException
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
name|reasoners
operator|.
name|servicesapi
operator|.
name|annotations
operator|.
name|Documentation
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
comment|/**  * Home page of the /reasoners module  *   * @author enridaga  *  */
end_comment

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
literal|"/reasoners"
argument_list|)
specifier|public
class|class
name|ReasoningServicesResource
extends|extends
name|BaseStanbolResource
block|{
specifier|private
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
annotation|@
name|Reference
specifier|private
name|ReasoningServicesManager
name|reasoningServicesManager
decl_stmt|;
specifier|public
name|ReasoningServicesResource
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|getCurrentPath
parameter_list|()
block|{
return|return
name|uriInfo
operator|.
name|getPath
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"[\\/]*$"
argument_list|,
literal|""
argument_list|)
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
name|getDocumentation
parameter_list|(
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|ResponseBuilder
name|rb
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
argument_list|)
decl_stmt|;
name|rb
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|TEXT_HTML
operator|+
literal|"; charset=utf-8"
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
specifier|private
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
name|service
init|=
literal|null
decl_stmt|;
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|TEXT_HTML
argument_list|)
annotation|@
name|Path
argument_list|(
literal|"{service}"
argument_list|)
specifier|public
name|Response
name|getServiceDocumentation
parameter_list|(
annotation|@
name|PathParam
argument_list|(
name|value
operator|=
literal|"service"
argument_list|)
name|String
name|serviceID
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
try|try
block|{
name|this
operator|.
name|service
operator|=
name|this
operator|.
name|getServicesManager
argument_list|()
operator|.
name|get
argument_list|(
name|serviceID
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnboundReasoningServiceException
name|e
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Service {} is not bound"
argument_list|,
name|serviceID
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
decl_stmt|;
name|rb
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|TEXT_HTML
operator|+
literal|"; charset=utf-8"
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"service"
argument_list|,
name|this
argument_list|)
argument_list|)
decl_stmt|;
name|rb
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|TEXT_HTML
operator|+
literal|"; charset=utf-8"
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
specifier|private
name|ReasoningServicesManager
name|getServicesManager
parameter_list|()
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"(getServicesManager()) "
argument_list|)
expr_stmt|;
return|return
name|reasoningServicesManager
return|;
block|}
specifier|public
name|Set
argument_list|<
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
argument_list|>
name|getActiveServices
parameter_list|()
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"(getActiveServices()) There are {} reasoning services"
argument_list|,
name|getServicesManager
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|getServicesManager
argument_list|()
operator|.
name|asUnmodifiableSet
argument_list|()
return|;
block|}
specifier|public
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
name|getService
parameter_list|()
block|{
return|return
name|this
operator|.
name|service
return|;
block|}
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getServiceDescription
parameter_list|()
block|{
return|return
name|getServiceDescription
argument_list|(
name|service
argument_list|)
return|;
block|}
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getServiceDescription
parameter_list|(
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
name|service
parameter_list|)
block|{
name|Class
argument_list|<
name|?
argument_list|>
name|serviceC
init|=
name|service
operator|.
name|getClass
argument_list|()
decl_stmt|;
name|String
name|name
decl_stmt|;
try|try
block|{
name|name
operator|=
name|serviceC
operator|.
name|getAnnotation
argument_list|(
name|Documentation
operator|.
name|class
argument_list|)
operator|.
name|name
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"The service {} is not documented: missing name"
argument_list|,
name|serviceC
argument_list|)
expr_stmt|;
name|name
operator|=
literal|""
expr_stmt|;
block|}
name|String
name|description
decl_stmt|;
try|try
block|{
name|description
operator|=
name|serviceC
operator|.
name|getAnnotation
argument_list|(
name|Documentation
operator|.
name|class
argument_list|)
operator|.
name|description
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"The service {} is not documented: missing description"
argument_list|,
name|serviceC
argument_list|)
expr_stmt|;
name|description
operator|=
literal|""
expr_stmt|;
block|}
comment|// String file = serviceC.getAnnotation(Documentation.class).file();
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|serviceProperties
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|serviceProperties
operator|.
name|put
argument_list|(
literal|"name"
argument_list|,
name|name
argument_list|)
expr_stmt|;
name|serviceProperties
operator|.
name|put
argument_list|(
literal|"description"
argument_list|,
name|description
argument_list|)
expr_stmt|;
comment|// serviceProperties.put("file", file);
name|serviceProperties
operator|.
name|put
argument_list|(
literal|"path"
argument_list|,
name|service
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|serviceProperties
return|;
block|}
specifier|public
name|List
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
name|getServicesDescription
parameter_list|()
block|{
name|List
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
name|descriptions
init|=
operator|new
name|ArrayList
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
name|service
range|:
name|getActiveServices
argument_list|()
control|)
block|{
name|descriptions
operator|.
name|add
argument_list|(
name|getServiceDescription
argument_list|(
name|service
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|descriptions
return|;
block|}
block|}
end_class

end_unit

