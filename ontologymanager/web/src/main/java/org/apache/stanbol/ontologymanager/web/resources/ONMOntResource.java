begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|Response
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
name|ontology
operator|.
name|OntologyIndex
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
name|io
operator|.
name|ClerezzaOntologyStorage
import|;
end_import

begin_import
import|import
name|org
operator|.
name|coode
operator|.
name|owlapi
operator|.
name|turtle
operator|.
name|TurtleOntologyFormat
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
name|io
operator|.
name|StringDocumentTarget
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
name|OWLOntologyStorageException
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

begin_class
annotation|@
name|Path
argument_list|(
literal|"/ontonet/ontology/get"
argument_list|)
specifier|public
class|class
name|ONMOntResource
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
comment|/*      * Placeholder for the ONManager to be fetched from the servlet context.      */
specifier|protected
name|ONManager
name|onm
decl_stmt|;
specifier|protected
name|ClerezzaOntologyStorage
name|storage
decl_stmt|;
specifier|protected
name|ServletContext
name|servletContext
decl_stmt|;
specifier|protected
name|Serializer
name|serializer
decl_stmt|;
specifier|public
name|ONMOntResource
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
name|this
operator|.
name|storage
operator|=
operator|(
name|ClerezzaOntologyStorage
operator|)
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|ClerezzaOntologyStorage
operator|.
name|class
argument_list|,
name|servletContext
argument_list|)
expr_stmt|;
name|this
operator|.
name|serializer
operator|=
operator|(
name|Serializer
operator|)
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|Serializer
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
literal|"application/rdf+xml"
argument_list|)
specifier|public
name|Response
name|getOntology
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
literal|"iri"
argument_list|)
name|String
name|ontologyIri
parameter_list|)
block|{
name|IRI
name|iri
init|=
literal|null
decl_stmt|;
try|try
block|{
name|iri
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|ontologyIri
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
literal|404
argument_list|)
throw|;
block|}
name|OntologyIndex
name|index
init|=
name|onm
operator|.
name|getOntologyIndex
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|index
operator|.
name|isOntologyLoaded
argument_list|(
name|iri
argument_list|)
condition|)
comment|// No such ontology registered, so return 404.
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
name|OWLOntology
name|ont
init|=
name|index
operator|.
name|getOntology
argument_list|(
name|iri
argument_list|)
decl_stmt|;
name|OWLOntologyManager
name|tmpmgr
init|=
name|onm
operator|.
name|getOntologyManagerFactory
argument_list|()
operator|.
name|createOntologyManager
argument_list|(
literal|true
argument_list|)
decl_stmt|;
name|StringDocumentTarget
name|tgt
init|=
operator|new
name|StringDocumentTarget
argument_list|()
decl_stmt|;
try|try
block|{
name|tmpmgr
operator|.
name|saveOntology
argument_list|(
name|ont
argument_list|,
operator|new
name|RDFXMLOntologyFormat
argument_list|()
argument_list|,
name|tgt
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
literal|500
argument_list|)
throw|;
block|}
return|return
name|Response
operator|.
name|ok
argument_list|(
name|tgt
operator|.
name|toString
argument_list|()
argument_list|)
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
literal|"text/turtle"
argument_list|)
specifier|public
name|Response
name|getOntologyT
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
literal|"iri"
argument_list|)
name|String
name|ontologyIri
parameter_list|)
block|{
name|IRI
name|iri
init|=
literal|null
decl_stmt|;
try|try
block|{
name|iri
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|ontologyIri
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
literal|404
argument_list|)
throw|;
block|}
name|OntologyIndex
name|index
init|=
name|onm
operator|.
name|getOntologyIndex
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|index
operator|.
name|isOntologyLoaded
argument_list|(
name|iri
argument_list|)
condition|)
comment|// No such ontology registered, so return 404.
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
name|OWLOntology
name|ont
init|=
name|index
operator|.
name|getOntology
argument_list|(
name|iri
argument_list|)
decl_stmt|;
name|OWLOntologyManager
name|tmpmgr
init|=
name|onm
operator|.
name|getOntologyManagerFactory
argument_list|()
operator|.
name|createOntologyManager
argument_list|(
literal|true
argument_list|)
decl_stmt|;
name|StringDocumentTarget
name|tgt
init|=
operator|new
name|StringDocumentTarget
argument_list|()
decl_stmt|;
try|try
block|{
name|tmpmgr
operator|.
name|saveOntology
argument_list|(
name|ont
argument_list|,
operator|new
name|TurtleOntologyFormat
argument_list|()
argument_list|,
name|tgt
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
literal|500
argument_list|)
throw|;
block|}
return|return
name|Response
operator|.
name|ok
argument_list|(
name|tgt
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
end_class

end_unit

