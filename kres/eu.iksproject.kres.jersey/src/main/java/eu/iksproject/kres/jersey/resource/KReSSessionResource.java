begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|jersey
operator|.
name|resource
package|;
end_package

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
name|PUT
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
name|UriInfo
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
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|format
operator|.
name|KReSFormat
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|KReSONManager
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|ontology
operator|.
name|OntologyInputSource
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|ontology
operator|.
name|OntologyScope
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|ontology
operator|.
name|OntologySpaceFactory
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|ontology
operator|.
name|OntologySpaceModificationException
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|ontology
operator|.
name|ScopeRegistry
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|ontology
operator|.
name|SessionOntologySpace
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|ontology
operator|.
name|UnmodifiableOntologySpaceException
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|session
operator|.
name|KReSSession
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|session
operator|.
name|KReSSessionManager
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|manager
operator|.
name|ONManager
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|manager
operator|.
name|renderers
operator|.
name|SessionRenderer
import|;
end_import

begin_class
annotation|@
name|Path
argument_list|(
literal|"/session"
argument_list|)
specifier|public
class|class
name|KReSSessionResource
extends|extends
name|NavigationMixin
block|{
comment|/* 	 * Placeholder for the KReSONManager to be fetched from the servlet context. 	 */
specifier|protected
name|KReSONManager
name|onm
decl_stmt|;
specifier|protected
name|ServletContext
name|servletContext
decl_stmt|;
specifier|public
name|KReSSessionResource
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
name|onm
operator|=
operator|(
name|KReSONManager
operator|)
name|this
operator|.
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|KReSONManager
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|onm
operator|==
literal|null
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"[KReS] :: No KReS Ontology Network Manager provided by Servlet Context. Instantiating now..."
argument_list|)
expr_stmt|;
name|onm
operator|=
operator|new
name|ONManager
argument_list|()
expr_stmt|;
block|}
block|}
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
block|{
name|KReSFormat
operator|.
name|FUNCTIONAL_OWL
block|,
name|KReSFormat
operator|.
name|MANCHERSTER_OWL
block|,
name|KReSFormat
operator|.
name|OWL_XML
block|,
name|KReSFormat
operator|.
name|RDF_XML
block|,
name|KReSFormat
operator|.
name|TURTLE
block|,
name|KReSFormat
operator|.
name|RDF_JSON
block|}
argument_list|)
specifier|public
name|Response
name|createSession
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
name|KReSSession
name|ses
init|=
literal|null
decl_stmt|;
name|KReSSessionManager
name|mgr
init|=
name|onm
operator|.
name|getSessionManager
argument_list|()
decl_stmt|;
comment|/* 		 * Create the KReS session to associate to the scope. 		 */
name|ses
operator|=
name|mgr
operator|.
name|createSession
argument_list|()
expr_stmt|;
comment|/* 		 * First get the scope registry. 		 */
name|ScopeRegistry
name|scopeRegistry
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
comment|/* 		 * Then retrieve the ontology scope. 		 */
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
name|scopeIRI
argument_list|)
decl_stmt|;
comment|/* 		 * Finally associate the KReS session to the scope. 		 */
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
name|scopeIRI
argument_list|)
decl_stmt|;
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
return|return
name|Response
operator|.
name|ok
argument_list|(
name|SessionRenderer
operator|.
name|getSessionMetadataRDF
argument_list|(
name|ses
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
annotation|@
name|PUT
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|APPLICATION_FORM_URLENCODED
argument_list|)
specifier|public
name|Response
name|addOntology
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
name|scopeIRI
argument_list|)
decl_stmt|;
name|SessionOntologySpace
name|sos
init|=
name|ontologyScope
operator|.
name|getSessionSpace
argument_list|(
name|sessionIRI
argument_list|)
decl_stmt|;
try|try
block|{
name|sos
operator|.
name|addOntology
argument_list|(
name|createOntologyInputSource
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
name|UnmodifiableOntologySpaceException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
literal|500
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
name|ok
argument_list|(
literal|500
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
annotation|@
name|PUT
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|MULTIPART_FORM_DATA
argument_list|)
specifier|public
name|Response
name|addOntology
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
literal|"import"
argument_list|)
name|InputStream
name|importOntology
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
name|OntologyScope
name|ontologyScope
init|=
name|scopeRegistry
operator|.
name|getScope
argument_list|(
name|scopeIRI
argument_list|)
decl_stmt|;
name|SessionOntologySpace
name|sos
init|=
name|ontologyScope
operator|.
name|getSessionSpace
argument_list|(
name|sessionIRI
argument_list|)
decl_stmt|;
try|try
block|{
name|sos
operator|.
name|addOntology
argument_list|(
name|createOntologyInputSource
argument_list|(
name|ontology
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
name|UnmodifiableOntologySpaceException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|500
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
literal|500
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
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
literal|404
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
annotation|@
name|DELETE
specifier|public
name|Response
name|deleteSession
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"scope"
argument_list|)
name|String
name|scope
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"session"
argument_list|)
name|String
name|session
parameter_list|,
annotation|@
name|PathParam
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
name|IRI
name|sessionID
init|=
name|IRI
operator|.
name|create
argument_list|(
name|session
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
name|scopeID
argument_list|)
decl_stmt|;
name|SessionOntologySpace
name|sos
init|=
name|ontologyScope
operator|.
name|getSessionSpace
argument_list|(
name|sessionID
argument_list|)
decl_stmt|;
try|try
block|{
name|sos
operator|.
name|removeOntology
argument_list|(
name|createOntologyInputSource
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
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|500
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|OntologySpaceModificationException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|500
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
name|sessionID
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
specifier|private
name|OntologyInputSource
name|createOntologyInputSource
parameter_list|(
specifier|final
name|IRI
name|iri
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
specifier|final
name|OWLOntology
name|ontology
init|=
name|onm
operator|.
name|getOwlCacheManager
argument_list|()
operator|.
name|loadOntology
argument_list|(
name|iri
argument_list|)
decl_stmt|;
name|OntologyInputSource
name|ontologyInputSource
init|=
operator|new
name|OntologyInputSource
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|hasRootOntology
parameter_list|()
block|{
return|return
name|ontology
operator|==
literal|null
condition|?
literal|false
else|:
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasPhysicalIRI
parameter_list|()
block|{
return|return
name|iri
operator|==
literal|null
condition|?
literal|false
else|:
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntology
name|getRootOntology
parameter_list|()
block|{
return|return
name|ontology
return|;
block|}
annotation|@
name|Override
specifier|public
name|IRI
name|getPhysicalIRI
parameter_list|()
block|{
return|return
name|iri
return|;
block|}
block|}
decl_stmt|;
return|return
name|ontologyInputSource
return|;
block|}
specifier|private
name|OntologyInputSource
name|createOntologyInputSource
parameter_list|(
specifier|final
name|OWLOntology
name|ontology
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
specifier|final
name|IRI
name|iri
init|=
name|ontology
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
decl_stmt|;
name|OntologyInputSource
name|ontologyInputSource
init|=
operator|new
name|OntologyInputSource
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|hasRootOntology
parameter_list|()
block|{
return|return
name|ontology
operator|==
literal|null
condition|?
literal|false
else|:
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasPhysicalIRI
parameter_list|()
block|{
return|return
name|iri
operator|==
literal|null
condition|?
literal|false
else|:
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntology
name|getRootOntology
parameter_list|()
block|{
return|return
name|ontology
return|;
block|}
annotation|@
name|Override
specifier|public
name|IRI
name|getPhysicalIRI
parameter_list|()
block|{
return|return
name|iri
return|;
block|}
block|}
decl_stmt|;
return|return
name|ontologyInputSource
return|;
block|}
block|}
end_class

end_unit

