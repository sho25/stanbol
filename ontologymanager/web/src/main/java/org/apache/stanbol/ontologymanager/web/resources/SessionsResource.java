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
name|MULTIPART_FORM_DATA
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
name|NOT_FOUND
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
name|DELETE
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
name|ONManager
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
name|RootOntologyIRISource
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
name|OntologyCollectorModificationException
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
name|OntologyScope
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
name|OntologySpaceFactory
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
name|ScopeRegistry
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
name|SessionOntologySpace
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
name|UnmodifiableOntologyCollectorException
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
name|SessionManager
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
name|impl
operator|.
name|renderers
operator|.
name|SessionRenderer
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

begin_class
annotation|@
name|Path
argument_list|(
literal|"/ontonet/session"
argument_list|)
specifier|public
class|class
name|SessionsResource
extends|extends
name|BaseStanbolResource
block|{
comment|/*      * Placeholder for the ONManager to be fetched from the servlet context.      */
specifier|protected
name|ONManager
name|onm
decl_stmt|;
specifier|protected
name|ServletContext
name|servletContext
decl_stmt|;
specifier|public
name|SessionsResource
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
name|onm
operator|=
operator|(
name|ONManager
operator|)
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|ONManager
operator|.
name|class
argument_list|,
name|servletContext
argument_list|)
expr_stmt|;
block|}
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|MULTIPART_FORM_DATA
argument_list|)
specifier|public
name|Response
name|addOntology
parameter_list|(
annotation|@
name|FormDataParam
argument_list|(
literal|"scope"
argument_list|)
name|String
name|scope
parameter_list|,
annotation|@
name|FormDataParam
argument_list|(
literal|"import"
argument_list|)
name|InputStream
name|importOntology
parameter_list|,
annotation|@
name|FormDataParam
argument_list|(
literal|"session"
argument_list|)
name|String
name|session
parameter_list|,
annotation|@
name|Context
name|UriInfo
name|uriInfo
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|,
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|IRI
name|scopeIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|scope
argument_list|)
decl_stmt|;
name|IRI
name|sessionIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|session
argument_list|)
decl_stmt|;
name|OWLOntology
name|ontology
decl_stmt|;
try|try
block|{
name|ontology
operator|=
name|onm
operator|.
name|getOwlCacheManager
argument_list|()
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|importOntology
argument_list|)
expr_stmt|;
name|ScopeRegistry
name|scopeRegistry
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
comment|// OntologyScope ontologyScope = scopeRegistry.getScope(scope);
comment|// SessionOntologySpace sos = ontologyScope.getSessionSpace(sessionIRI);
comment|// try {
comment|// sos.addOntology(new RootOntologySource(ontology));
return|return
name|Response
operator|.
name|ok
argument_list|()
operator|.
name|build
argument_list|()
return|;
comment|// } catch (UnmodifiableOntologySpaceException e) {
comment|// return Response.status(INTERNAL_SERVER_ERROR).build();
comment|// }
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e1
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|NOT_FOUND
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
comment|/**      *       * If the session param is missing, this method creates a new session, ignoring other params      *       * @param scope      * @param session      * @param location      * @param uriInfo      * @param headers      * @param servletContext      * @return      */
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|APPLICATION_FORM_URLENCODED
argument_list|)
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
name|FUNCTIONAL_OWL
block|,
name|MANCHESTER_OWL
block|,
name|RDF_JSON
block|}
argument_list|)
specifier|public
name|Response
name|addOntology
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"scope"
argument_list|)
name|String
name|scope
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"session"
argument_list|)
name|String
name|session
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"location"
argument_list|)
name|String
name|location
parameter_list|,
annotation|@
name|Context
name|UriInfo
name|uriInfo
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|,
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
if|if
condition|(
name|session
operator|==
literal|null
operator|||
name|session
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
return|return
name|createSession
argument_list|(
name|scope
argument_list|,
name|uriInfo
argument_list|,
name|headers
argument_list|)
return|;
block|}
else|else
block|{
name|IRI
name|scopeIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|scope
argument_list|)
decl_stmt|;
name|IRI
name|ontologyIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|location
argument_list|)
decl_stmt|;
name|ScopeRegistry
name|scopeRegistry
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|OntologyScope
name|ontologyScope
init|=
name|scopeRegistry
operator|.
name|getScope
argument_list|(
name|scope
argument_list|)
decl_stmt|;
name|SessionOntologySpace
name|sos
init|=
name|ontologyScope
operator|.
name|getSessionSpace
argument_list|(
name|session
argument_list|)
decl_stmt|;
try|try
block|{
name|sos
operator|.
name|addOntology
argument_list|(
operator|new
name|RootOntologyIRISource
argument_list|(
name|ontologyIRI
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|()
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologyCollectorException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
block|}
comment|/**      * This method creates a session.      *       * @param scope      * @param uriInfo      * @param headers      * @return      */
specifier|private
name|Response
name|createSession
parameter_list|(
name|String
name|scope
parameter_list|,
name|UriInfo
name|uriInfo
parameter_list|,
name|HttpHeaders
name|headers
parameter_list|)
block|{
if|if
condition|(
name|scope
operator|==
literal|null
operator|||
name|scope
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|Session
name|ses
init|=
literal|null
decl_stmt|;
name|SessionManager
name|mgr
init|=
name|onm
operator|.
name|getSessionManager
argument_list|()
decl_stmt|;
comment|/*          * Create the KReS session to associate to the scope.          */
name|ses
operator|=
name|mgr
operator|.
name|createSession
argument_list|()
expr_stmt|;
comment|/*          * First get the scope registry.          */
name|ScopeRegistry
name|scopeRegistry
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
comment|/*          * Then retrieve the ontology scope.          */
name|IRI
name|scopeIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|scope
argument_list|)
decl_stmt|;
name|OntologyScope
name|ontologyScope
init|=
name|scopeRegistry
operator|.
name|getScope
argument_list|(
name|scope
argument_list|)
decl_stmt|;
comment|/*          * Finally associate the KReS session to the scope.          */
name|OntologySpaceFactory
name|ontologySpaceFactory
init|=
name|onm
operator|.
name|getOntologySpaceFactory
argument_list|()
decl_stmt|;
name|SessionOntologySpace
name|sessionOntologySpace
init|=
name|ontologySpaceFactory
operator|.
name|createSessionOntologySpace
argument_list|(
name|scope
argument_list|)
decl_stmt|;
try|try
block|{
name|ontologyScope
operator|.
name|addSessionSpace
argument_list|(
name|sessionOntologySpace
argument_list|,
name|ses
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologyCollectorException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|)
throw|;
block|}
return|return
name|Response
operator|.
name|ok
argument_list|(
name|SessionRenderer
operator|.
name|getSessionMetadataRDFasOntology
argument_list|(
name|ses
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|/**      * FIXME what are these path params anyway?      *       * @param scope      * @param session      * @param deleteOntology      * @param uriInfo      * @param headers      * @return      */
annotation|@
name|DELETE
specifier|public
name|Response
name|deleteSession
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
literal|"scope"
argument_list|)
name|String
name|scope
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"session"
argument_list|)
name|String
name|session
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"delete"
argument_list|)
name|String
name|deleteOntology
parameter_list|,
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
name|IRI
name|scopeID
init|=
name|IRI
operator|.
name|create
argument_list|(
name|scope
argument_list|)
decl_stmt|;
if|if
condition|(
name|deleteOntology
operator|!=
literal|null
condition|)
block|{
name|IRI
name|ontologyIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|deleteOntology
argument_list|)
decl_stmt|;
name|ScopeRegistry
name|scopeRegistry
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|OntologyScope
name|ontologyScope
init|=
name|scopeRegistry
operator|.
name|getScope
argument_list|(
name|scope
argument_list|)
decl_stmt|;
name|SessionOntologySpace
name|sos
init|=
name|ontologyScope
operator|.
name|getSessionSpace
argument_list|(
name|session
argument_list|)
decl_stmt|;
try|try
block|{
comment|/*                  * TODO : previous implementation reloaded the whole ontology before deleting it, thus                  * treating this as a physical IRI. See if it still works this way                  */
name|OWLOntology
name|o
init|=
name|sos
operator|.
name|getOntology
argument_list|(
name|ontologyIRI
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
name|sos
operator|.
name|removeOntology
argument_list|(
name|ontologyIRI
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|()
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|OntologyCollectorModificationException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
name|onm
operator|.
name|getSessionManager
argument_list|()
operator|.
name|destroySession
argument_list|(
name|session
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|()
operator|.
name|build
argument_list|()
return|;
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
name|getView
parameter_list|()
block|{
return|return
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
operator|.
name|build
argument_list|()
return|;
block|}
block|}
end_class

end_unit
