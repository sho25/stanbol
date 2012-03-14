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
name|ontologymanager
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
operator|.
name|INTERNAL_SERVER_ERROR
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
name|Response
operator|.
name|Status
operator|.
name|UNSUPPORTED_MEDIA_TYPE
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
name|format
operator|.
name|KRFormat
operator|.
name|FUNCTIONAL_OWL
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
name|format
operator|.
name|KRFormat
operator|.
name|MANCHESTER_OWL
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
name|format
operator|.
name|KRFormat
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
name|stanbol
operator|.
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
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
name|stanbol
operator|.
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
operator|.
name|OWL_XML
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
name|format
operator|.
name|KRFormat
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
name|stanbol
operator|.
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
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
name|stanbol
operator|.
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
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
name|stanbol
operator|.
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
operator|.
name|X_TURTLE
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
name|serializedform
operator|.
name|UnsupportedFormatException
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|io
operator|.
name|OntologyContentInputSource
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|io
operator|.
name|OntologyInputSource
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|OntologyProvider
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntology
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntologyCreationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntologyManager
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
name|ImplicitProduces
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
comment|/**  *   * @author anuzzolese, alexdma  *   */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/ontonet"
argument_list|)
annotation|@
name|ImplicitProduces
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
operator|+
literal|";qs=2"
argument_list|)
specifier|public
class|class
name|OntoNetRootResource
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
comment|/*      * Placeholder for the OntologyProvider to be fetched from the servlet context.      */
specifier|protected
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|ontologyProvider
decl_stmt|;
specifier|public
name|OntoNetRootResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|ontologyProvider
operator|=
operator|(
name|OntologyProvider
argument_list|<
name|?
argument_list|>
operator|)
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|OntologyProvider
operator|.
name|class
argument_list|,
name|servletContext
argument_list|)
expr_stmt|;
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
name|getHtmlInfo
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
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|value
operator|=
block|{
name|RDF_XML
block|,
name|TURTLE
block|,
name|X_TURTLE
block|,
name|N3
block|,
name|N_TRIPLE
block|,
name|RDF_JSON
block|}
argument_list|)
specifier|public
name|Response
name|storeGraph
parameter_list|(
name|InputStream
name|content
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|long
name|before
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|ResponseBuilder
name|rb
decl_stmt|;
name|String
name|key
init|=
literal|null
decl_stmt|;
try|try
block|{
name|key
operator|=
name|ontologyProvider
operator|.
name|loadInStore
argument_list|(
name|content
argument_list|,
name|headers
operator|.
name|getMediaType
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"POST request for ontology addition completed in {} ms."
argument_list|,
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|before
operator|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedFormatException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"POST method failed for media type {}. This should not happen (should fail earlier)"
argument_list|,
name|headers
operator|.
name|getMediaType
argument_list|()
argument_list|)
expr_stmt|;
name|rb
operator|=
name|Response
operator|.
name|status
argument_list|(
name|UNSUPPORTED_MEDIA_TYPE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
comment|// An exception should have been thrown earlier, but just in case.
if|if
condition|(
name|key
operator|==
literal|null
operator|||
name|key
operator|.
name|isEmpty
argument_list|()
condition|)
name|rb
operator|=
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
expr_stmt|;
name|rb
operator|=
name|Response
operator|.
name|ok
argument_list|()
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
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|value
operator|=
block|{
name|OWL_XML
block|,
name|FUNCTIONAL_OWL
block|,
name|MANCHESTER_OWL
block|}
argument_list|)
specifier|public
name|Response
name|storeOWLOntology
parameter_list|(
name|InputStream
name|content
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|long
name|before
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
try|try
block|{
name|OntologyInputSource
argument_list|<
name|OWLOntology
argument_list|,
name|OWLOntologyManager
argument_list|>
name|src
init|=
operator|new
name|OntologyContentInputSource
argument_list|(
name|content
argument_list|)
decl_stmt|;
name|ontologyProvider
operator|.
name|loadInStore
argument_list|(
name|src
operator|.
name|getRootOntology
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"POST request for ontology addition completed in {} ms."
argument_list|,
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|before
operator|)
argument_list|)
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|ok
argument_list|()
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
comment|/*      * TODO before implementing removal, we need OWL dependency checks. Also, this is quite a strong method      * and would be best implemented with RESTful authentication.      */
comment|// @DELETE
specifier|public
name|Response
name|remove
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
argument_list|()
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
block|}
end_class

end_unit

