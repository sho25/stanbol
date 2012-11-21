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
name|cmsadapter
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
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLConnection
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
name|DefaultValue
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
name|serializedform
operator|.
name|Parser
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
name|SupportedFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|FileUtils
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
name|cmsadapter
operator|.
name|core
operator|.
name|mapping
operator|.
name|DefaultRDFBridgeImpl
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
name|cmsadapter
operator|.
name|core
operator|.
name|mapping
operator|.
name|RDFBridgeManager
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|RDFBridge
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|RDFBridgeException
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|RDFMapper
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccessException
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
name|cmsadapter
operator|.
name|web
operator|.
name|utils
operator|.
name|RestUtil
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

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|core
operator|.
name|header
operator|.
name|FormDataContentDisposition
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
name|multipart
operator|.
name|FormDataParam
import|;
end_import

begin_comment
comment|/**  * This resource provides functionalities for bidirectional mapping between external RDF data and JCR/CMIS  * content repositories. In other words, it is possible to populate content repository based on an external  * RDF. On the other direction, it enables generation of RDF using the structure of content repository. The  * mapping operation is done by {@link RDFBridge}s.  */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/cmsadapter/map"
argument_list|)
specifier|public
class|class
name|RDFMapperResource
extends|extends
name|BaseStanbolResource
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RDFMapperResource
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Parser
name|clerezzaParser
decl_stmt|;
specifier|private
name|RDFBridgeManager
name|bridgeManager
decl_stmt|;
specifier|public
name|RDFMapperResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
name|clerezzaParser
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|Parser
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|bridgeManager
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|RDFBridgeManager
operator|.
name|class
argument_list|,
name|context
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
name|OPTIONS
annotation|@
name|Path
argument_list|(
literal|"/rdf"
argument_list|)
specifier|public
name|Response
name|handleCorsPreflightRDF
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
literal|"/cms"
argument_list|)
specifier|public
name|Response
name|handleCorsPreflightCMS
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
argument_list|,
name|TEXT_HTML
argument_list|)
decl_stmt|;
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
comment|/**      * Allows clients to map specified RDF to the content repository. In the first step the RDF data is      * annotated according to RDF Bridges loaded in the OSGI environment. Additional annotations provide      * selection of certain resources from RDF data and creation/update of related content repository object.      * See Javadoc of {@link DefaultRDFBridgeImpl} for possible configuration options of default      * {@link RDFBridge} implementation. Either a raw RDF can be given in<code>serializedGraph</code>      * parameter or URL of an external RDF data can given in<code>url</code> parameter. However,      *<code>serializedGraph</code> has a higher priority.      *       * @param sessionKey      *            session key to obtain a previously created session to be used to connect a content      *            repository      * @param serializedGraph      *            is the serialized RDF graph in<b>application/rdf+xml" format that is desired to be      *            transformed into repository objects      * @param url      *            URL of the external RDF data.      * @return      * @throws MalformedURLException      * @throws IOException      */
annotation|@
name|Path
argument_list|(
literal|"/rdf"
argument_list|)
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|APPLICATION_FORM_URLENCODED
argument_list|)
specifier|public
name|Response
name|mapRawRDFToRepository
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"sessionKey"
argument_list|)
name|String
name|sessionKey
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"serializedGraph"
argument_list|)
name|String
name|serializedGraph
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"url"
argument_list|)
name|String
name|url
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
throws|throws
name|MalformedURLException
throws|,
name|IOException
block|{
name|sessionKey
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|sessionKey
argument_list|)
expr_stmt|;
if|if
condition|(
name|sessionKey
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Sessin key should not be null"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Session key should not be null"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|Graph
name|g
decl_stmt|;
if|if
condition|(
name|serializedGraph
operator|!=
literal|null
operator|&&
operator|!
name|serializedGraph
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|g
operator|=
name|clerezzaParser
operator|.
name|parse
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|serializedGraph
operator|.
name|getBytes
argument_list|()
argument_list|)
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|url
operator|!=
literal|null
operator|&&
operator|!
name|url
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|URLConnection
name|uc
init|=
operator|(
operator|new
name|URL
argument_list|(
name|url
argument_list|)
operator|)
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|g
operator|=
name|clerezzaParser
operator|.
name|parse
argument_list|(
name|uc
operator|.
name|getInputStream
argument_list|()
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"There is no RDF data source specified"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"There is no RDF data source specified"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|Response
name|r
init|=
name|mapRDF
argument_list|(
name|g
argument_list|,
name|sessionKey
argument_list|,
name|headers
argument_list|)
decl_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"RDF mapping finished in: {} seconds"
argument_list|,
operator|(
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
operator|)
operator|/
literal|1000
operator|)
argument_list|)
expr_stmt|;
return|return
name|r
return|;
block|}
comment|/**      * Same with {@link #mapRawRDFToRepository(String, String, String)}. But this service allows clients to      * submit external RDF data through a {@link File} specified in<code>rdfFile</code> parameter.      *       * @param sessionKey      *            session key to obtain a previously created session to be used to connect a content      *            repository      * @param rdfFile      *            {@link File} containing the RDF to be mapped to the content repository      * @param rdfFileInfo      *            Information related with RDF file      * @return      * @throws IOException      */
annotation|@
name|Path
argument_list|(
literal|"/rdf"
argument_list|)
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|MULTIPART_FORM_DATA
argument_list|)
specifier|public
name|Response
name|mapRDFToRepositoryFromFile
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
literal|"sessionKey"
argument_list|)
name|String
name|sessionKey
parameter_list|,
annotation|@
name|FormDataParam
argument_list|(
literal|"rdfFile"
argument_list|)
name|File
name|rdfFile
parameter_list|,
annotation|@
name|FormDataParam
argument_list|(
literal|"rdfFile"
argument_list|)
name|FormDataContentDisposition
name|rdfFileInfo
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
throws|throws
name|IOException
block|{
name|sessionKey
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|sessionKey
argument_list|)
expr_stmt|;
if|if
condition|(
name|sessionKey
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Sessin key should not be null"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Session key should not be null"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|Graph
name|g
decl_stmt|;
if|if
condition|(
name|rdfFile
operator|!=
literal|null
condition|)
block|{
name|InputStream
name|is
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|FileUtils
operator|.
name|readFileToByteArray
argument_list|(
name|rdfFile
argument_list|)
argument_list|)
decl_stmt|;
name|g
operator|=
name|clerezzaParser
operator|.
name|parse
argument_list|(
name|is
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"There is RDF file specified"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"There is no RDF file specified"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|Response
name|r
init|=
name|mapRDF
argument_list|(
name|g
argument_list|,
name|sessionKey
argument_list|,
name|headers
argument_list|)
decl_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"RDF mapping finished in: {} seconds"
argument_list|,
operator|(
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
operator|)
operator|/
literal|1000
operator|)
argument_list|)
expr_stmt|;
return|return
name|r
return|;
block|}
specifier|private
name|Response
name|mapRDF
parameter_list|(
name|Graph
name|g
parameter_list|,
name|String
name|sessionKey
parameter_list|,
name|HttpHeaders
name|headers
parameter_list|)
block|{
try|try
block|{
name|bridgeManager
operator|.
name|storeRDFToRepository
argument_list|(
name|sessionKey
argument_list|,
name|g
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Failed to obtain a session from repository"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Failed to obtain a session from repository"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|RDFBridgeException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
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
argument_list|()
operator|.
name|entity
argument_list|(
literal|"RDF data has been mapped to the content repository"
argument_list|)
decl_stmt|;
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
comment|/**      * This service provides obtaining an RDF from the content repository based on the {@link RDFBridge}      * instances in the environment. Target content repository objects are determined according to path      * configurations of RDF Bridges. In the first step, content repository objects are converted into an RDF.      * This process is realized by {@link RDFMapper}. For JCR and CMIS repositories there are two      * implementations of this interface namely,<code>JCRRDFMapper</code> and<code>CMISRDFMapper</code>. At      * the end of first step, generated RDF contains only<b>CMS Vocabulary</b> annotations. Afterwards,      * additional assertions are added based on RDF Bridges.      *       * @param sessionKey      *            session key to obtain a previously created session to be used to connect a content      *            repository      * @param baseURI      *            base URI for the RDF to be generated      * @param store      *            if this boolean parameter is set as<code>true</code>, generated RDF is stored persistently      *            in Stanbol environment      * @param update      *            precondition to consider this parameter is setting<code>true</code> for<code>store</code>      *            parameter. If so; if this parameter is set as<code>true</code> previously store RDF having      *            the identified by the URI passed in<code>baseURI</code> parameter is updated. However, if      *            there is no stored RDF a new one is created. If it is not set explicitly, its default value      *            is<code>true</code>      *       * @return generated {@link MGraph} wrapped in a {@link Response} in "application/rdf+xml" format      */
annotation|@
name|Path
argument_list|(
literal|"/cms"
argument_list|)
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|APPLICATION_FORM_URLENCODED
argument_list|)
annotation|@
name|Produces
argument_list|(
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|)
specifier|public
name|Response
name|mapRepositoryToRDF
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"sessionKey"
argument_list|)
name|String
name|sessionKey
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"baseURI"
argument_list|)
name|String
name|baseURI
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"store"
argument_list|)
name|boolean
name|store
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"update"
argument_list|)
annotation|@
name|DefaultValue
argument_list|(
literal|"true"
argument_list|)
name|boolean
name|update
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|sessionKey
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|sessionKey
argument_list|)
expr_stmt|;
if|if
condition|(
name|sessionKey
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Sessin key should not be null"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Session key should not be null"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
try|try
block|{
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|MGraph
name|generatedGraph
init|=
name|bridgeManager
operator|.
name|generateRDFFromRepository
argument_list|(
name|baseURI
argument_list|,
name|sessionKey
argument_list|,
name|store
argument_list|,
name|update
argument_list|)
decl_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"CMS mapping finished in: {} seconds"
argument_list|,
operator|(
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
operator|)
operator|/
literal|1000
operator|)
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|ok
argument_list|(
name|generatedGraph
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|)
decl_stmt|;
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
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|String
name|message
init|=
name|e
operator|.
name|getMessage
argument_list|()
decl_stmt|;
name|logger
operator|.
name|warn
argument_list|(
name|message
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
name|message
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|RDFBridgeException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Error while generating RDF from repository"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Error while generating RDF from repository"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

