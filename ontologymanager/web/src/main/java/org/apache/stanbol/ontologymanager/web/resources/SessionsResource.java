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
name|List
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
name|Status
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
name|format
operator|.
name|KRFormat
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
name|SessionsResource
extends|extends
name|BaseStanbolResource
block|{
comment|/*      * Placeholder for the ONManager to be fetched from the servlet context.      */
specifier|protected
name|SessionManager
name|sessionManager
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
comment|// @POST
comment|// @Consumes(MULTIPART_FORM_DATA)
comment|// public Response addOntology(@FormDataParam("scope") String scope,
comment|// @FormDataParam("import") InputStream importOntology,
comment|// @FormDataParam("session") String session,
comment|// @Context UriInfo uriInfo,
comment|// @Context HttpHeaders headers,
comment|// @Context ServletContext servletContext) {
comment|//
comment|// IRI scopeIRI = IRI.create(scope);
comment|// IRI sessionIRI = IRI.create(session);
comment|//
comment|// OWLOntology ontology;
comment|// try {
comment|// ontology = onm.getOwlCacheManager().loadOntologyFromOntologyDocument(importOntology);
comment|//
comment|// ScopeRegistry scopeRegistry = onm.getScopeRegistry();
comment|//
comment|// // OntologyScope ontologyScope = scopeRegistry.getScope(scope);
comment|// // SessionOntologySpace sos = ontologyScope.getSessionSpace(sessionIRI);
comment|// // try {
comment|// // sos.addOntology(new RootOntologySource(ontology));
comment|// return Response.ok().build();
comment|// // } catch (UnmodifiableOntologySpaceException e) {
comment|// // return Response.status(INTERNAL_SERVER_ERROR).build();
comment|// // }
comment|// } catch (OWLOntologyCreationException e1) {
comment|// return Response.status(NOT_FOUND).build();
comment|// }
comment|//
comment|// }
comment|// /**
comment|// *
comment|// * If the session param is missing, this method creates a new session, ignoring other params
comment|// *
comment|// * @param scope
comment|// * @param session
comment|// * @param location
comment|// * @param uriInfo
comment|// * @param headers
comment|// * @param servletContext
comment|// * @return
comment|// */
comment|// @POST
comment|// @Consumes(APPLICATION_FORM_URLENCODED)
comment|// @Produces(value = {RDF_XML, OWL_XML, TURTLE, FUNCTIONAL_OWL, MANCHESTER_OWL, RDF_JSON})
comment|// public Response addOntology(@FormParam("scope") String scope,
comment|// @FormParam("session") String session,
comment|// @FormParam("location") String location,
comment|// @Context UriInfo uriInfo,
comment|// @Context HttpHeaders headers,
comment|// @Context ServletContext servletContext) {
comment|// if (session == null || session.equals("")) {
comment|// return createSession(scope, uriInfo, headers);
comment|// } else {
comment|// IRI scopeIRI = IRI.create(scope);
comment|// IRI ontologyIRI = IRI.create(location);
comment|// ScopeRegistry scopeRegistry = onm.getScopeRegistry();
comment|//
comment|// OntologyScope ontologyScope = scopeRegistry.getScope(scope);
comment|// SessionOntologySpace sos = ontologyScope.getSessionSpace(session);
comment|// try {
comment|// sos.addOntology(new RootOntologyIRISource(ontologyIRI));
comment|// return Response.ok().build();
comment|// } catch (UnmodifiableOntologyCollectorException e) {
comment|// return Response.status(INTERNAL_SERVER_ERROR).build();
comment|// } catch (OWLOntologyCreationException e) {
comment|// return Response.status(INTERNAL_SERVER_ERROR).build();
comment|// }
comment|// }
comment|// }
comment|// /**
comment|// * This method creates a session.
comment|// *
comment|// * @param scope
comment|// * @param uriInfo
comment|// * @param headers
comment|// * @return
comment|// */
comment|// private Response createSession(String scope, UriInfo uriInfo, HttpHeaders headers) {
comment|// if (scope == null || scope.equals("")) {
comment|// return Response.status(INTERNAL_SERVER_ERROR).build();
comment|// }
comment|// Session ses = null;
comment|// SessionManager mgr = onm.getSessionManager();
comment|//
comment|// /*
comment|// * Create the KReS session to associate to the scope.
comment|// */
comment|// ses = mgr.createSession();
comment|//
comment|// /*
comment|// * First get the scope registry.
comment|// */
comment|// ScopeRegistry scopeRegistry = onm.getScopeRegistry();
comment|//
comment|// /*
comment|// * Then retrieve the ontology scope.
comment|// */
comment|// IRI scopeIRI = IRI.create(scope);
comment|// OntologyScope ontologyScope = scopeRegistry.getScope(scope);
comment|//
comment|// /*
comment|// * Finally associate the KReS session to the scope.
comment|// */
comment|// OntologySpaceFactory ontologySpaceFactory = onm.getOntologySpaceFactory();
comment|// SessionOntologySpace sessionOntologySpace = ontologySpaceFactory.createSessionOntologySpace(scope);
comment|// try {
comment|// ontologyScope.addSessionSpace(sessionOntologySpace, ses.getID());
comment|// } catch (UnmodifiableOntologyCollectorException e) {
comment|// throw new WebApplicationException(e);
comment|// }
comment|//
comment|// return Response.ok(SessionRenderer.getSessionMetadataRDFasOntology(ses)).build();
comment|//
comment|// }
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|value
operator|=
block|{
name|KRFormat
operator|.
name|RDF_XML
block|,
name|KRFormat
operator|.
name|OWL_XML
block|,
name|KRFormat
operator|.
name|TURTLE
block|,
name|KRFormat
operator|.
name|FUNCTIONAL_OWL
block|,
name|KRFormat
operator|.
name|MANCHESTER_OWL
block|,
name|KRFormat
operator|.
name|RDF_JSON
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
comment|// SessionManager sesMgr = onm.getSessionManager();
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
name|getNamespace
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
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
return|return
name|Response
operator|.
name|ok
argument_list|(
name|o
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|// /**
comment|// * FIXME what are these path params anyway?
comment|// *
comment|// * @param scope
comment|// * @param session
comment|// * @param deleteOntology
comment|// * @param uriInfo
comment|// * @param headers
comment|// * @return
comment|// */
comment|// @DELETE
comment|// public Response deleteSession(@QueryParam("scope") String scope,
comment|// @QueryParam("session") String session,
comment|// @QueryParam("delete") String deleteOntology,
comment|// @Context UriInfo uriInfo,
comment|// @Context HttpHeaders headers) {
comment|//
comment|// IRI scopeID = IRI.create(scope);
comment|//
comment|// if (deleteOntology != null) {
comment|// IRI ontologyIRI = IRI.create(deleteOntology);
comment|//
comment|// ScopeRegistry scopeRegistry = onm.getScopeRegistry();
comment|//
comment|// OntologyScope ontologyScope = scopeRegistry.getScope(scope);
comment|// SessionOntologySpace sos = ontologyScope.getSessionSpace(session);
comment|//
comment|// try {
comment|// /*
comment|// * TODO : previous implementation reloaded the whole ontology before deleting it, thus
comment|// * treating this as a physical IRI. See if it still works this way
comment|// */
comment|// OWLOntology o = sos.getOntology(ontologyIRI);
comment|// if (o != null) sos.removeOntology(ontologyIRI);
comment|// return Response.ok().build();
comment|// } catch (OntologyCollectorModificationException e) {
comment|// return Response.status(INTERNAL_SERVER_ERROR).build();
comment|// }
comment|// } else {
comment|// onm.getSessionManager().destroySession(session);
comment|// return Response.ok().build();
comment|// }
comment|//
comment|// }
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

