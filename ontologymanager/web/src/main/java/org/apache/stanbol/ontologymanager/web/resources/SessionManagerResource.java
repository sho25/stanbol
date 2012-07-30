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
name|MediaType
operator|.
name|TEXT_PLAIN
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
name|FORBIDDEN
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
name|ArrayList
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
name|UriInfo
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|session
operator|.
name|Session
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
name|session
operator|.
name|SessionLimitException
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
name|session
operator|.
name|SessionManager
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
name|apibinding
operator|.
name|OWLManager
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
name|AddAxiom
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
name|IRI
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
name|OWLClass
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
name|OWLDataFactory
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
name|OWLNamedIndividual
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
name|OWLOntologyChange
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
literal|"/ontonet/session"
argument_list|)
specifier|public
class|class
name|SessionManagerResource
extends|extends
name|BaseStanbolResource
block|{
comment|/*      * Placeholder for the ONManager to be fetched from the servlet context.      */
specifier|protected
name|SessionManager
name|sessionManager
decl_stmt|;
specifier|public
name|SessionManagerResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|this
operator|.
name|servletContext
operator|=
name|servletContext
expr_stmt|;
name|this
operator|.
name|sessionManager
operator|=
operator|(
name|SessionManager
operator|)
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|SessionManager
operator|.
name|class
argument_list|,
name|servletContext
argument_list|)
expr_stmt|;
block|}
annotation|@
name|POST
specifier|public
name|Response
name|createSessionWithAutomaticId
parameter_list|(
annotation|@
name|Context
name|UriInfo
name|uriInfo
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|Session
name|s
decl_stmt|;
try|try
block|{
name|s
operator|=
name|sessionManager
operator|.
name|createSession
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SessionLimitException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|FORBIDDEN
argument_list|)
throw|;
block|}
name|String
name|uri
init|=
name|uriInfo
operator|.
name|getRequestUri
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
while|while
condition|(
name|uri
operator|.
name|endsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
name|uri
operator|=
name|uri
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|uri
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|uri
operator|+=
literal|"/"
operator|+
name|s
operator|.
name|getID
argument_list|()
expr_stmt|;
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|created
argument_list|(
name|URI
operator|.
name|create
argument_list|(
name|uri
argument_list|)
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
specifier|public
name|Set
argument_list|<
name|Session
argument_list|>
name|getSessions
parameter_list|()
block|{
name|Set
argument_list|<
name|Session
argument_list|>
name|result
init|=
operator|new
name|HashSet
argument_list|<
name|Session
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|id
range|:
name|sessionManager
operator|.
name|getRegisteredSessionIDs
argument_list|()
control|)
name|result
operator|.
name|add
argument_list|(
name|sessionManager
operator|.
name|getSession
argument_list|(
name|id
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|result
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
name|RDF_XML
block|,
name|OWL_XML
block|,
name|TURTLE
block|,
name|X_TURTLE
block|,
name|FUNCTIONAL_OWL
block|,
name|MANCHESTER_OWL
block|,
name|RDF_JSON
block|,
name|N3
block|,
name|N_TRIPLE
block|,
name|TEXT_PLAIN
block|}
argument_list|)
specifier|public
name|Response
name|listSessions
parameter_list|(
annotation|@
name|Context
name|UriInfo
name|uriInfo
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|OWLOntologyManager
name|ontMgr
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|df
init|=
name|ontMgr
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|OWLClass
name|cSession
init|=
name|df
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontologies/meta/Session"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLOntology
name|o
decl_stmt|;
try|try
block|{
name|o
operator|=
name|ontMgr
operator|.
name|createOntology
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|uriInfo
operator|.
name|getRequestUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|id
range|:
name|sessionManager
operator|.
name|getRegisteredSessionIDs
argument_list|()
control|)
block|{
name|IRI
name|sessionid
init|=
name|IRI
operator|.
name|create
argument_list|(
name|sessionManager
operator|.
name|getDefaultNamespace
argument_list|()
operator|+
name|sessionManager
operator|.
name|getID
argument_list|()
operator|+
literal|"/"
operator|+
name|id
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|ind
init|=
name|df
operator|.
name|getOWLNamedIndividual
argument_list|(
name|sessionid
argument_list|)
decl_stmt|;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|o
argument_list|,
name|df
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|cSession
argument_list|,
name|ind
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ontMgr
operator|.
name|applyChanges
argument_list|(
name|changes
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
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|ok
argument_list|(
name|o
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

