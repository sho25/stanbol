begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
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
name|Status
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
name|KReSSessionManager
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
name|ONManagerImpl
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

begin_class
annotation|@
name|Path
argument_list|(
literal|"/session/{id:.+}"
argument_list|)
specifier|public
class|class
name|KReSSessionIDResource
extends|extends
name|NavigationMixin
block|{
comment|/* 	 * Placeholder for the ONManager to be fetched from the servlet context. 	 */
specifier|protected
name|ONManager
name|onm
decl_stmt|;
specifier|protected
name|ServletContext
name|servletContext
decl_stmt|;
specifier|public
name|KReSSessionIDResource
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
name|ONManager
operator|)
name|this
operator|.
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|ONManager
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
name|ONManagerImpl
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
literal|"application/rdf+xml"
argument_list|)
specifier|public
name|Response
name|getSessionMetadataOntology
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"id"
argument_list|)
name|String
name|sessionId
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
name|Session
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
name|ses
operator|=
name|mgr
operator|.
name|getSession
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|sessionId
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|ses
operator|==
literal|null
condition|)
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NO_CONTENT
argument_list|)
operator|.
name|build
argument_list|()
return|;
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
block|}
end_class

end_unit

