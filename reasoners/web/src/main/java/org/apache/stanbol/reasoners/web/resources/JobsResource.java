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
name|Future
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
name|MediaType
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
name|UriInfo
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
name|jobs
operator|.
name|api
operator|.
name|JobManager
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
name|BaseStanbolResource
operator|.
name|ResultData
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
name|web
operator|.
name|utils
operator|.
name|ReasoningServiceResult
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
name|web
operator|.
name|utils
operator|.
name|ResponseTaskBuilder
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
comment|/**  * Return the result of a reasoners background job  *   * @author enridaga  *   */
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
literal|"/reasoners/jobs"
argument_list|)
specifier|public
class|class
name|JobsResource
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
specifier|private
name|String
name|jobLocation
init|=
literal|""
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|JobManager
name|jobManager
decl_stmt|;
specifier|public
name|JobsResource
parameter_list|()
block|{     }
comment|/**      * To read a /reasoners job output.      *       * @param id      * @return      */
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
literal|"/{jid}"
argument_list|)
specifier|public
name|Response
name|get
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"jid"
argument_list|)
name|String
name|id
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Pinging job {}"
argument_list|,
name|id
argument_list|)
expr_stmt|;
comment|// No id
if|if
condition|(
name|id
operator|==
literal|null
operator|||
name|id
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
decl_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
name|JobManager
name|m
init|=
name|getJobManager
argument_list|()
decl_stmt|;
comment|// If the job exists
if|if
condition|(
name|m
operator|.
name|hasJob
argument_list|(
name|id
argument_list|)
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Found job with id {}"
argument_list|,
name|id
argument_list|)
expr_stmt|;
name|Future
argument_list|<
name|?
argument_list|>
name|f
init|=
name|m
operator|.
name|ping
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|f
operator|.
name|isDone
argument_list|()
operator|&&
operator|(
operator|!
name|f
operator|.
name|isCancelled
argument_list|()
operator|)
condition|)
block|{
comment|/**                  * We return OK with the result                  */
name|Object
name|o
decl_stmt|;
try|try
block|{
name|o
operator|=
name|f
operator|.
name|get
argument_list|()
expr_stmt|;
if|if
condition|(
name|o
operator|instanceof
name|ReasoningServiceResult
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Is a ReasoningServiceResult"
argument_list|)
expr_stmt|;
name|ReasoningServiceResult
argument_list|<
name|?
argument_list|>
name|result
init|=
operator|(
name|ReasoningServiceResult
argument_list|<
name|?
argument_list|>
operator|)
name|o
decl_stmt|;
return|return
operator|new
name|ResponseTaskBuilder
argument_list|(
operator|new
name|JobResultResource
argument_list|(
name|uriInfo
argument_list|,
name|headers
argument_list|)
argument_list|)
operator|.
name|build
argument_list|(
name|result
argument_list|)
return|;
block|}
else|else
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Job {} does not belong to reasoners"
argument_list|,
name|id
argument_list|)
expr_stmt|;
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
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Error: "
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|ExecutionException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Error: "
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
else|else
block|{
comment|/**                  * We return 404 with additional info                  */
name|String
name|jobService
init|=
operator|new
name|StringBuilder
argument_list|()
operator|.
name|append
argument_list|(
name|getPublicBaseUri
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"jobs/"
argument_list|)
operator|.
name|append
argument_list|(
name|id
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
name|this
operator|.
name|jobLocation
operator|=
name|jobService
expr_stmt|;
name|Viewable
name|viewable
init|=
operator|new
name|Viewable
argument_list|(
literal|"404.ftl"
argument_list|,
name|this
argument_list|)
decl_stmt|;
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
name|MediaType
operator|.
name|TEXT_HTML
operator|+
literal|"; charset=utf-8"
argument_list|)
expr_stmt|;
name|rb
operator|.
name|entity
argument_list|(
name|viewable
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"No job found with id {}"
argument_list|,
name|id
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
name|MediaType
operator|.
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
block|}
comment|/**      * If the output is not ready, this field contains the location of the job to be rendered in the viewable.      *       * @return      */
specifier|public
name|String
name|getJobLocation
parameter_list|()
block|{
return|return
name|this
operator|.
name|jobLocation
return|;
block|}
comment|/**      * Gets the job manager      *       * @return      */
specifier|private
name|JobManager
name|getJobManager
parameter_list|()
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"(getJobManager()) "
argument_list|)
expr_stmt|;
return|return
name|jobManager
return|;
block|}
annotation|@
name|OPTIONS
specifier|public
name|Response
name|handleCorsPreflight
parameter_list|()
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|ok
argument_list|()
decl_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
specifier|public
class|class
name|JobResultResource
extends|extends
name|ResultData
implements|implements
name|ReasoningResult
block|{
specifier|private
name|Object
name|result
decl_stmt|;
specifier|private
name|UriInfo
name|uriInfo
decl_stmt|;
specifier|private
name|HttpHeaders
name|headers
decl_stmt|;
specifier|public
name|JobResultResource
parameter_list|(
name|UriInfo
name|uriInfo
parameter_list|,
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|this
operator|.
name|headers
operator|=
name|headers
expr_stmt|;
name|this
operator|.
name|uriInfo
operator|=
name|uriInfo
expr_stmt|;
block|}
specifier|public
name|void
name|setResult
parameter_list|(
name|Object
name|result
parameter_list|)
block|{
name|this
operator|.
name|result
operator|=
name|result
expr_stmt|;
block|}
specifier|public
name|Object
name|getResult
parameter_list|()
block|{
return|return
name|this
operator|.
name|result
return|;
block|}
specifier|public
name|HttpHeaders
name|getHeaders
parameter_list|()
block|{
return|return
name|headers
return|;
block|}
specifier|public
name|UriInfo
name|getUriInfo
parameter_list|()
block|{
return|return
name|uriInfo
return|;
block|}
block|}
block|}
end_class

end_unit

