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
name|WILDCARD
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
name|EnhancementPropertiesHelper
operator|.
name|INCLUDE_EXECUTION_METADATA
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
name|EnhancementPropertiesHelper
operator|.
name|OMIT_METADATA
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
name|EnhancementPropertiesHelper
operator|.
name|OMIT_PARSED_CONTENT
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
name|EnhancementPropertiesHelper
operator|.
name|OUTPUT_CONTENT
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
name|EnhancementPropertiesHelper
operator|.
name|OUTPUT_CONTENT_PART
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
name|EnhancementPropertiesHelper
operator|.
name|RDF_FORMAT
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
name|EnhancementPropertiesHelper
operator|.
name|getEnhancementProperties
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
name|HttpMethod
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
name|QueryParam
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
name|UriRef
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|utils
operator|.
name|MediaTypeUtil
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
name|jersey
operator|.
name|utils
operator|.
name|EnhancementPropertiesHelper
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
name|NoSuchPartException
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
name|ExecutionMetadata
import|;
end_import

begin_comment
comment|/**  * Abstract super class for all enhancement endpoints that do not use/support  * the default Enhancer Web UI.<p>  * This is mainly used for supporting enhancement requests to single  * enhancement engines.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|AbstractEnhancerResource
extends|extends
name|BaseStanbolResource
block|{
specifier|protected
specifier|final
name|EnhancementJobManager
name|jobManager
decl_stmt|;
specifier|protected
specifier|final
name|EnhancementEngineManager
name|engineManager
decl_stmt|;
specifier|protected
specifier|final
name|ChainManager
name|chainManager
decl_stmt|;
specifier|public
name|AbstractEnhancerResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
comment|// bind the job manager by looking it up from the servlet request context
name|jobManager
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|EnhancementJobManager
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|chainManager
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|ChainManager
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|engineManager
operator|=
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
expr_stmt|;
block|}
comment|/**      * Getter for the Enhancement {@link Chain}      * @return the enhancement chain. MUST NOT return<code>null</code>      * @throws ChainException if the Chain is currently not available      */
specifier|protected
specifier|abstract
name|Chain
name|getChain
parameter_list|()
throws|throws
name|ChainException
function_decl|;
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
name|OPTIONS
annotation|@
name|Path
argument_list|(
literal|"/ep"
argument_list|)
specifier|public
name|Response
name|handleEpCorsPreflight
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
argument_list|,
name|HttpMethod
operator|.
name|OPTIONS
argument_list|,
name|HttpMethod
operator|.
name|GET
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
name|Path
argument_list|(
literal|"/ep"
argument_list|)
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
name|getExecutionPlan
parameter_list|(
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|ResponseBuilder
name|res
decl_stmt|;
name|Chain
name|chain
init|=
literal|null
decl_stmt|;
try|try
block|{
name|chain
operator|=
name|getChain
argument_list|()
expr_stmt|;
name|res
operator|=
name|Response
operator|.
name|ok
argument_list|(
name|chain
operator|.
name|getExecutionPlan
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ChainException
name|e
parameter_list|)
block|{
name|String
name|chainName
init|=
name|chain
operator|==
literal|null
condition|?
literal|""
else|:
operator|(
literal|"'"
operator|+
name|chain
operator|.
name|getName
argument_list|()
operator|+
literal|"' "
operator|)
decl_stmt|;
name|res
operator|=
name|Response
operator|.
name|status
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
literal|"The Enhancement Chain "
operator|+
name|chainName
operator|+
literal|"is currently"
operator|+
literal|"not executeable (message: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|")!"
argument_list|)
expr_stmt|;
block|}
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
comment|/**      * Media-Type based handling of the raw POST data.      *       * @param data      *            binary payload to analyze      * @param uri      *            optional URI for the content items (to be used as an identifier in the enhancement graph)      * @throws EngineException      *             if the content is somehow corrupted      * @throws IOException      */
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|WILDCARD
argument_list|)
specifier|public
name|Response
name|enhanceFromData
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"uri"
argument_list|)
name|String
name|uri
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"executionmetadata"
argument_list|)
name|boolean
name|inclExecMetadata
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"outputContent"
argument_list|)
name|Set
argument_list|<
name|String
argument_list|>
name|mediaTypes
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"omitParsed"
argument_list|)
name|boolean
name|omitParsed
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"outputContentPart"
argument_list|)
name|Set
argument_list|<
name|String
argument_list|>
name|contentParts
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"omitMetadata"
argument_list|)
name|boolean
name|omitMetadata
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"rdfFormat"
argument_list|)
name|String
name|rdfFormat
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
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|enhancementProperties
init|=
name|getEnhancementProperties
argument_list|(
name|ci
argument_list|)
decl_stmt|;
name|enhancementProperties
operator|.
name|put
argument_list|(
name|INCLUDE_EXECUTION_METADATA
argument_list|,
name|inclExecMetadata
argument_list|)
expr_stmt|;
if|if
condition|(
name|mediaTypes
operator|!=
literal|null
operator|&&
operator|!
name|mediaTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|enhancementProperties
operator|.
name|put
argument_list|(
name|OUTPUT_CONTENT
argument_list|,
name|mediaTypes
argument_list|)
expr_stmt|;
block|}
name|enhancementProperties
operator|.
name|put
argument_list|(
name|OMIT_PARSED_CONTENT
argument_list|,
name|omitParsed
argument_list|)
expr_stmt|;
if|if
condition|(
name|contentParts
operator|!=
literal|null
operator|&&
operator|!
name|contentParts
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Set
argument_list|<
name|UriRef
argument_list|>
name|outputContentParts
init|=
operator|new
name|HashSet
argument_list|<
name|UriRef
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|contentPartUri
range|:
name|contentParts
control|)
block|{
if|if
condition|(
name|contentPartUri
operator|!=
literal|null
operator|&&
operator|!
name|contentPartUri
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
literal|"*"
operator|.
name|equals
argument_list|(
name|contentPartUri
argument_list|)
condition|)
block|{
name|outputContentParts
operator|.
name|add
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|//indicated wildcard
block|}
else|else
block|{
name|outputContentParts
operator|.
name|add
argument_list|(
operator|new
name|UriRef
argument_list|(
name|contentPartUri
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|enhancementProperties
operator|.
name|put
argument_list|(
name|OUTPUT_CONTENT_PART
argument_list|,
name|outputContentParts
argument_list|)
expr_stmt|;
block|}
name|enhancementProperties
operator|.
name|put
argument_list|(
name|OMIT_METADATA
argument_list|,
name|omitMetadata
argument_list|)
expr_stmt|;
if|if
condition|(
name|rdfFormat
operator|!=
literal|null
operator|&&
operator|!
name|rdfFormat
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|enhancementProperties
operator|.
name|put
argument_list|(
name|RDF_FORMAT
argument_list|,
name|MediaType
operator|.
name|valueOf
argument_list|(
name|rdfFormat
argument_list|)
operator|.
name|toString
argument_list|()
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
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Response
operator|.
name|status
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse MediaType form parameter"
operator|+
literal|"rdfFormat=%s"
argument_list|,
name|rdfFormat
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
throw|;
block|}
block|}
name|enhance
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|ok
argument_list|(
name|ci
argument_list|)
decl_stmt|;
name|MediaType
name|mediaType
init|=
name|MediaTypeUtil
operator|.
name|getAcceptableMediaType
argument_list|(
name|headers
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|mediaType
operator|!=
literal|null
condition|)
block|{
name|rb
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|mediaType
argument_list|)
expr_stmt|;
block|}
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
comment|/**      * Enhances the parsed ContentItem      * @param ci the content item to enhance      * @throws EnhancementException      */
specifier|protected
name|void
name|enhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EnhancementException
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|enhancementPropertis
init|=
name|EnhancementPropertiesHelper
operator|.
name|getEnhancementProperties
argument_list|(
name|ci
argument_list|)
decl_stmt|;
if|if
condition|(
name|jobManager
operator|!=
literal|null
condition|)
block|{
name|jobManager
operator|.
name|enhanceContent
argument_list|(
name|ci
argument_list|,
name|getChain
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|MGraph
name|graph
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|Boolean
name|includeExecutionMetadata
init|=
operator|(
name|Boolean
operator|)
name|enhancementPropertis
operator|.
name|get
argument_list|(
name|INCLUDE_EXECUTION_METADATA
argument_list|)
decl_stmt|;
if|if
condition|(
name|includeExecutionMetadata
operator|!=
literal|null
operator|&&
name|includeExecutionMetadata
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
try|try
block|{
name|graph
operator|.
name|addAll
argument_list|(
name|ci
operator|.
name|getPart
argument_list|(
name|ExecutionMetadata
operator|.
name|CHAIN_EXECUTION
argument_list|,
name|TripleCollection
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchPartException
name|e
parameter_list|)
block|{
comment|// no executionMetadata available
block|}
block|}
block|}
block|}
end_class

end_unit

