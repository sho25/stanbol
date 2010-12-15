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
name|*
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
name|io
operator|.
name|RDFXMLOntologyFormat
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
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntologySetProvider
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
name|OWLOntologyStorageException
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
name|util
operator|.
name|OWLOntologyMerger
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
name|OntologySpace
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
name|jersey
operator|.
name|util
operator|.
name|OntologyRenderUtils
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
name|io
operator|.
name|RootOntologySource
import|;
end_import

begin_comment
comment|/**  * This resource represents ontologies loaded within a scope.  *   * @author alessandro  *   */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/ontology/{scopeid}/{uri:.+}"
argument_list|)
specifier|public
class|class
name|ONMScopeOntologyResource
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
name|ONMScopeOntologyResource
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
block|}
comment|/** 	 * Returns an RDF/XML representation of the ontology identified by logical 	 * IRI<code>ontologyid</code>, if it is loaded within the scope 	 *<code>[baseUri]/scopeid</code>. 	 *  	 * @param scopeid 	 * @param ontologyid 	 * @param uriInfo 	 * @return, or a status 404 if either the scope is not registered or the 	 *          ontology is not loaded within that scope. 	 */
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|value
operator|=
block|{
name|KReSFormat
operator|.
name|RDF_XML
block|,
name|KReSFormat
operator|.
name|OWL_XML
block|,
name|KReSFormat
operator|.
name|TURTLE
block|,
name|KReSFormat
operator|.
name|FUNCTIONAL_OWL
block|,
name|KReSFormat
operator|.
name|MANCHESTER_OWL
block|,
name|KReSFormat
operator|.
name|RDF_JSON
block|}
argument_list|)
specifier|public
name|Response
name|getScopeOntology
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"scopeid"
argument_list|)
name|String
name|scopeid
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"uri"
argument_list|)
name|String
name|ontologyid
parameter_list|,
annotation|@
name|Context
name|UriInfo
name|uriInfo
parameter_list|)
block|{
name|String
name|absur
init|=
name|uriInfo
operator|.
name|getAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|URI
name|uri
init|=
name|URI
operator|.
name|create
argument_list|(
name|absur
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|absur
operator|.
name|lastIndexOf
argument_list|(
name|ontologyid
argument_list|)
operator|-
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|IRI
name|sciri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|uri
argument_list|)
decl_stmt|;
name|IRI
name|ontiri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|ontologyid
argument_list|)
decl_stmt|;
comment|// TODO: hack (ma anche no)
if|if
condition|(
operator|!
name|ontiri
operator|.
name|isAbsolute
argument_list|()
condition|)
name|ontiri
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|absur
argument_list|)
expr_stmt|;
name|ScopeRegistry
name|reg
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|OntologyScope
name|scope
init|=
name|reg
operator|.
name|getScope
argument_list|(
name|sciri
argument_list|)
decl_stmt|;
if|if
condition|(
name|scope
operator|==
literal|null
condition|)
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
comment|/* BEGIN debug code, uncomment only for local testing */
comment|// OWLOntology test = null, top = null;
comment|// test = scope.getCustomSpace().getOntology(ontiri);
comment|// System.out.println("Ontology " + ontiri);
comment|// for (OWLImportsDeclaration imp : test.getImportsDeclarations())
comment|// System.out.println("\timports " + imp.getIRI());
comment|// top = scope.getCoreSpace().getTopOntology();
comment|// System.out.println("Core root for scope " + scopeid);
comment|// for (OWLImportsDeclaration imp : top.getImportsDeclarations())
comment|// System.out.println("\timports " + imp.getIRI());
comment|/* END debug code */
name|OWLOntology
name|ont
init|=
literal|null
decl_stmt|;
comment|// By default, always try retrieving the ontology from the custom space
comment|// first.
name|OntologySpace
name|space
init|=
name|scope
operator|.
name|getCustomSpace
argument_list|()
decl_stmt|;
if|if
condition|(
name|space
operator|==
literal|null
condition|)
name|space
operator|=
name|scope
operator|.
name|getCoreSpace
argument_list|()
expr_stmt|;
if|if
condition|(
name|space
operator|!=
literal|null
condition|)
name|ont
operator|=
name|space
operator|.
name|getOntology
argument_list|(
name|ontiri
argument_list|)
expr_stmt|;
if|if
condition|(
name|ont
operator|==
literal|null
condition|)
block|{
name|OWLOntologyManager
name|man
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
specifier|final
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
init|=
name|scope
operator|.
name|getSessionSpace
argument_list|(
name|ontiri
argument_list|)
operator|.
name|getOntologies
argument_list|()
decl_stmt|;
name|OWLOntologySetProvider
name|provider
init|=
operator|new
name|OWLOntologySetProvider
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|()
block|{
comment|// System.out.println("ID SPACE : " + ontologies);
return|return
name|ontologies
return|;
block|}
block|}
decl_stmt|;
name|OWLOntologyMerger
name|merger
init|=
operator|new
name|OWLOntologyMerger
argument_list|(
name|provider
argument_list|)
decl_stmt|;
comment|/* 			 * Set<OntologySpace> spaces = scope.getSessionSpaces(); 			 * for(OntologySpace space : spaces){ 			 * System.out.println("ID SPACE : "+space.getID()); } 			 */
try|try
block|{
name|ont
operator|=
name|merger
operator|.
name|createMergedOntology
argument_list|(
name|man
argument_list|,
name|ontiri
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
block|}
if|if
condition|(
name|ont
operator|==
literal|null
condition|)
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
name|String
name|res
init|=
literal|null
decl_stmt|;
try|try
block|{
name|res
operator|=
name|OntologyRenderUtils
operator|.
name|renderOntology
argument_list|(
name|ont
argument_list|,
operator|new
name|RDFXMLOntologyFormat
argument_list|()
argument_list|,
name|sciri
operator|.
name|toString
argument_list|()
argument_list|,
name|onm
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
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
return|return
name|Response
operator|.
name|ok
argument_list|(
name|res
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|/** 	 * Unloads an ontology from an ontology scope. 	 *  	 * @param scopeId 	 * @param ontologyid 	 * @param uriInfo 	 * @param headers 	 */
annotation|@
name|DELETE
specifier|public
name|void
name|unloadOntology
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"scopeid"
argument_list|)
name|String
name|scopeId
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"uri"
argument_list|)
name|String
name|ontologyid
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
if|if
condition|(
name|ontologyid
operator|!=
literal|null
operator|&&
operator|!
name|ontologyid
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|String
name|scopeURI
init|=
name|uriInfo
operator|.
name|getAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
name|ontologyid
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Received DELETE request for ontology "
operator|+
name|ontologyid
operator|+
literal|" in scope "
operator|+
name|scopeURI
argument_list|)
expr_stmt|;
name|IRI
name|scopeIri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|uriInfo
operator|.
name|getBaseUri
argument_list|()
operator|+
literal|"ontology/"
operator|+
name|scopeId
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"SCOPE IRI : "
operator|+
name|scopeIri
argument_list|)
expr_stmt|;
name|IRI
name|ontIri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|ontologyid
argument_list|)
decl_stmt|;
name|ScopeRegistry
name|reg
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|OntologyScope
name|scope
init|=
name|reg
operator|.
name|getScope
argument_list|(
name|scopeIri
argument_list|)
decl_stmt|;
name|OntologySpace
name|cs
init|=
name|scope
operator|.
name|getCustomSpace
argument_list|()
decl_stmt|;
if|if
condition|(
name|cs
operator|.
name|hasOntology
argument_list|(
name|ontIri
argument_list|)
condition|)
block|{
try|try
block|{
name|reg
operator|.
name|setScopeActive
argument_list|(
name|scopeIri
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|cs
operator|.
name|removeOntology
argument_list|(
operator|new
name|RootOntologySource
argument_list|(
name|cs
operator|.
name|getOntology
argument_list|(
name|ontIri
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|reg
operator|.
name|setScopeActive
argument_list|(
name|scopeIri
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OntologySpaceModificationException
name|e
parameter_list|)
block|{
name|reg
operator|.
name|setScopeActive
argument_list|(
name|scopeIri
argument_list|,
literal|true
argument_list|)
expr_stmt|;
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
block|}
block|}
block|}
block|}
end_class

end_unit

