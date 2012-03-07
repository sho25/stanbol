begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements. See the NOTICE file distributed with this  * work for additional information regarding copyright ownership. The ASF  * licenses this file to You under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the  * License for the specific language governing permissions and limitations under  * the License.  */
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
name|APPLICATION_FORM_URLENCODED
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
name|TEXT_PLAIN
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
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
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
name|Consumes
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
name|FormParam
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
name|POST
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
name|Graph
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
name|TripleCollection
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
name|EnhancementException
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
name|ExecutionPlanHelper
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
name|InMemoryContentItem
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

begin_comment
comment|/**  * Abstract super class for all Enhancer endpoint that do also provide the  * Stanbol Enhancer Web UI. This includes "/enhancer", /enhancer/chain/{name}  * and "/engines".  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|AbstractEnhancerUiResource
extends|extends
name|AbstractEnhancerResource
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
specifier|protected
specifier|final
name|TcManager
name|tcManager
decl_stmt|;
specifier|protected
specifier|final
name|Serializer
name|serializer
decl_stmt|;
specifier|private
name|LinkedHashSet
argument_list|<
name|ExecutionNode
argument_list|>
name|_executionNodes
decl_stmt|;
specifier|private
name|LinkedHashSet
argument_list|<
name|ExecutionNode
argument_list|>
name|_activeNodes
decl_stmt|;
specifier|protected
specifier|final
name|Chain
name|chain
decl_stmt|;
specifier|public
name|AbstractEnhancerUiResource
parameter_list|(
name|String
name|chainName
parameter_list|,
name|ServletContext
name|context
parameter_list|)
block|{
name|super
argument_list|(
name|context
argument_list|)
expr_stmt|;
name|serializer
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|Serializer
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|tcManager
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|TcManager
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
if|if
condition|(
name|chainName
operator|==
literal|null
condition|)
block|{
name|chain
operator|=
name|chainManager
operator|.
name|getDefault
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|chain
operator|=
name|chainManager
operator|.
name|getChain
argument_list|(
name|chainName
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|this
operator|.
name|chain
operator|==
literal|null
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
comment|/**      * Form-based OpenCalais-compatible interface      *       * TODO: should we parse the OpenCalais paramsXML and find the closest Stanbol Enhancer semantics too?      *       * Note: the format parameter is not part of the official API      *       * @throws EngineException      *             if the content is somehow corrupted      * @throws IOException      */
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|APPLICATION_FORM_URLENCODED
argument_list|)
specifier|public
name|Response
name|enhanceFromForm
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"content"
argument_list|)
name|String
name|content
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"format"
argument_list|)
name|String
name|format
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"ajax"
argument_list|)
name|boolean
name|buildAjaxview
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
throws|throws
name|EnhancementException
throws|,
name|IOException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"enhance from From: "
operator|+
name|content
argument_list|)
expr_stmt|;
name|ContentItem
name|ci
init|=
operator|new
name|InMemoryContentItem
argument_list|(
name|content
operator|.
name|getBytes
argument_list|(
literal|"UTF-8"
argument_list|)
argument_list|,
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|buildAjaxview
condition|)
block|{
comment|//rewrite to a normal EnhancementRequest
return|return
name|enhanceFromData
argument_list|(
name|ci
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|,
name|headers
argument_list|)
return|;
block|}
else|else
block|{
comment|//enhance and build the AJAX response
name|enhance
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|ContentItemResource
name|contentItemResource
init|=
operator|new
name|ContentItemResource
argument_list|(
literal|null
argument_list|,
name|ci
argument_list|,
name|uriInfo
argument_list|,
literal|""
argument_list|,
name|tcManager
argument_list|,
name|serializer
argument_list|,
name|servletContext
argument_list|)
decl_stmt|;
name|contentItemResource
operator|.
name|setRdfSerializationFormat
argument_list|(
name|format
argument_list|)
expr_stmt|;
name|Viewable
name|ajaxView
init|=
operator|new
name|Viewable
argument_list|(
literal|"/ajax/contentitem"
argument_list|,
name|contentItemResource
argument_list|)
decl_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|ok
argument_list|(
name|ajaxView
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
literal|"; charset=UTF-8"
argument_list|)
expr_stmt|;
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|headers
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
specifier|public
name|boolean
name|isEngineActive
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|engineManager
operator|.
name|isEngine
argument_list|(
name|name
argument_list|)
return|;
block|}
specifier|public
name|URI
name|getServiceUrl
parameter_list|()
block|{
return|return
name|uriInfo
operator|.
name|getAbsolutePath
argument_list|()
return|;
block|}
comment|/**      * Getter for the executionNodes      *       * @return      */
specifier|public
name|Set
argument_list|<
name|ExecutionNode
argument_list|>
name|getExecutionNodes
parameter_list|()
block|{
if|if
condition|(
name|_executionNodes
operator|==
literal|null
condition|)
block|{
name|Graph
name|ep
decl_stmt|;
try|try
block|{
name|ep
operator|=
name|chain
operator|.
name|getExecutionPlan
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ChainException
name|e
parameter_list|)
block|{
name|ep
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|ep
operator|!=
literal|null
condition|)
block|{
name|_executionNodes
operator|=
operator|new
name|LinkedHashSet
argument_list|<
name|ExecutionNode
argument_list|>
argument_list|()
expr_stmt|;
name|Set
argument_list|<
name|NonLiteral
argument_list|>
name|processed
init|=
operator|new
name|HashSet
argument_list|<
name|NonLiteral
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|NonLiteral
argument_list|>
name|next
decl_stmt|;
do|do
block|{
name|next
operator|=
name|ExecutionPlanHelper
operator|.
name|getExecutable
argument_list|(
name|ep
argument_list|,
name|processed
argument_list|)
expr_stmt|;
for|for
control|(
name|NonLiteral
name|node
range|:
name|next
control|)
block|{
name|_executionNodes
operator|.
name|add
argument_list|(
operator|new
name|ExecutionNode
argument_list|(
name|ep
argument_list|,
name|node
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|processed
operator|.
name|addAll
argument_list|(
name|next
argument_list|)
expr_stmt|;
block|}
do|while
condition|(
operator|!
name|next
operator|.
name|isEmpty
argument_list|()
condition|)
do|;
block|}
block|}
return|return
name|_executionNodes
return|;
block|}
specifier|public
name|Set
argument_list|<
name|ExecutionNode
argument_list|>
name|getActiveNodes
parameter_list|()
block|{
if|if
condition|(
name|_activeNodes
operator|==
literal|null
condition|)
block|{
name|Set
argument_list|<
name|ExecutionNode
argument_list|>
name|ens
init|=
name|getExecutionNodes
argument_list|()
decl_stmt|;
if|if
condition|(
name|ens
operator|!=
literal|null
condition|)
block|{
name|_activeNodes
operator|=
operator|new
name|LinkedHashSet
argument_list|<
name|ExecutionNode
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|ExecutionNode
name|en
range|:
name|ens
control|)
block|{
if|if
condition|(
name|en
operator|.
name|isEngineActive
argument_list|()
condition|)
block|{
name|_activeNodes
operator|.
name|add
argument_list|(
name|en
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|_activeNodes
return|;
block|}
specifier|public
name|Chain
name|getChain
parameter_list|()
block|{
return|return
name|chain
return|;
block|}
specifier|public
name|boolean
name|isChainAvailable
parameter_list|()
block|{
name|Set
argument_list|<
name|ExecutionNode
argument_list|>
name|nodes
init|=
name|getExecutionNodes
argument_list|()
decl_stmt|;
if|if
condition|(
name|nodes
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
for|for
control|(
name|ExecutionNode
name|node
range|:
name|getExecutionNodes
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|node
operator|.
name|isOptional
argument_list|()
operator|&&
operator|!
name|node
operator|.
name|isEngineActive
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
return|return
literal|true
return|;
block|}
specifier|public
class|class
name|ExecutionNode
block|{
specifier|private
specifier|final
name|NonLiteral
name|node
decl_stmt|;
specifier|private
specifier|final
name|TripleCollection
name|ep
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|optional
decl_stmt|;
specifier|private
specifier|final
name|String
name|engineName
decl_stmt|;
specifier|public
name|ExecutionNode
parameter_list|(
name|TripleCollection
name|executionPlan
parameter_list|,
name|NonLiteral
name|node
parameter_list|)
block|{
name|this
operator|.
name|node
operator|=
name|node
expr_stmt|;
name|this
operator|.
name|ep
operator|=
name|executionPlan
expr_stmt|;
name|this
operator|.
name|optional
operator|=
name|ExecutionPlanHelper
operator|.
name|isOptional
argument_list|(
name|ep
argument_list|,
name|node
argument_list|)
expr_stmt|;
name|this
operator|.
name|engineName
operator|=
name|ExecutionPlanHelper
operator|.
name|getEngine
argument_list|(
name|ep
argument_list|,
name|node
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isOptional
parameter_list|()
block|{
return|return
name|optional
return|;
block|}
specifier|public
name|String
name|getEngineName
parameter_list|()
block|{
return|return
name|engineName
return|;
block|}
specifier|public
name|EnhancementEngine
name|getEngine
parameter_list|()
block|{
return|return
name|engineManager
operator|.
name|getEngine
argument_list|(
name|engineName
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isEngineActive
parameter_list|()
block|{
return|return
name|engineManager
operator|.
name|isEngine
argument_list|(
name|engineName
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|node
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|o
operator|instanceof
name|ExecutionNode
operator|&&
operator|(
operator|(
name|ExecutionNode
operator|)
name|o
operator|)
operator|.
name|node
operator|.
name|equals
argument_list|(
name|node
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit
