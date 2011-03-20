begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|web
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
name|stanbol
operator|.
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|KReSONManager
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
name|reasoners
operator|.
name|base
operator|.
name|api
operator|.
name|InconcistencyException
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|NoSuchRecipeException
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
name|rules
operator|.
name|refactor
operator|.
name|api
operator|.
name|SemionRefactorer
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
name|rules
operator|.
name|refactor
operator|.
name|api
operator|.
name|SemionRefactoringException
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
name|format
operator|.
name|KReSFormat
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
name|kres
operator|.
name|jersey
operator|.
name|resource
operator|.
name|NavigationMixin
import|;
end_import

begin_comment
comment|/**  *   * @author andrea.nuzzolese  *   */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/refactorer"
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
name|SemionRefactorerResource
extends|extends
name|NavigationMixin
block|{
specifier|protected
name|KReSONManager
name|onManager
decl_stmt|;
comment|// protected SemionManager semionManager;
specifier|protected
name|TcManager
name|tcManager
decl_stmt|;
specifier|protected
name|SemionRefactorer
name|semionRefactorer
decl_stmt|;
specifier|public
name|SemionRefactorerResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|semionRefactorer
operator|=
call|(
name|SemionRefactorer
call|)
argument_list|(
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|SemionRefactorer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|onManager
operator|=
call|(
name|KReSONManager
call|)
argument_list|(
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
argument_list|)
expr_stmt|;
name|tcManager
operator|=
call|(
name|TcManager
call|)
argument_list|(
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|TcManager
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|semionRefactorer
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"SemionRefactorer missing in ServletContext"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
literal|"/lazy"
argument_list|)
specifier|public
name|Response
name|performRefactoringLazyCreateGraph
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
literal|"recipe"
argument_list|)
name|String
name|recipe
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"input-graph"
argument_list|)
name|String
name|inputGraph
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"output-graph"
argument_list|)
name|String
name|outputGraph
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"recipe: "
operator|+
name|recipe
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"input-graph: "
operator|+
name|inputGraph
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"output-graph: "
operator|+
name|outputGraph
argument_list|)
expr_stmt|;
name|IRI
name|recipeIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|recipe
argument_list|)
decl_stmt|;
name|IRI
name|inputGraphIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|inputGraph
argument_list|)
decl_stmt|;
name|IRI
name|outputGraphIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|outputGraph
argument_list|)
decl_stmt|;
comment|// SemionRefactorer semionRefactorer = semionManager.getRegisteredRefactorer();
try|try
block|{
name|semionRefactorer
operator|.
name|ontologyRefactoring
argument_list|(
name|outputGraphIRI
argument_list|,
name|inputGraphIRI
argument_list|,
name|recipeIRI
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
name|SemionRefactoringException
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
name|NoSuchRecipeException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|204
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
annotation|@
name|POST
annotation|@
name|Path
argument_list|(
literal|"/lazy"
argument_list|)
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|MULTIPART_FORM_DATA
argument_list|)
annotation|@
name|Produces
argument_list|(
name|value
operator|=
block|{
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
name|RDF_XML
block|,
name|KReSFormat
operator|.
name|OWL_XML
block|,
name|KReSFormat
operator|.
name|RDF_JSON
block|}
argument_list|)
specifier|public
name|Response
name|performRefactoring
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"recipe"
argument_list|)
name|String
name|recipe
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"input"
argument_list|)
name|InputStream
name|input
parameter_list|)
block|{
comment|// SemionRefactorer semionRefactorer = semionManager.getRegisteredRefactorer();
name|IRI
name|recipeIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|recipe
argument_list|)
decl_stmt|;
name|OWLOntologyManager
name|manager
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|OWLOntology
name|inputOntology
decl_stmt|;
try|try
block|{
name|inputOntology
operator|=
name|manager
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|input
argument_list|)
expr_stmt|;
name|OWLOntology
name|outputOntology
decl_stmt|;
try|try
block|{
name|outputOntology
operator|=
name|semionRefactorer
operator|.
name|ontologyRefactoring
argument_list|(
name|inputOntology
argument_list|,
name|recipeIRI
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SemionRefactoringException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
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
name|NoSuchRecipeException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|204
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
return|return
name|Response
operator|.
name|ok
argument_list|(
name|outputOntology
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
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
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
name|GET
annotation|@
name|Path
argument_list|(
literal|"/consistent"
argument_list|)
specifier|public
name|Response
name|performConsistentRefactoringCreateGraph
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
literal|"recipe"
argument_list|)
name|String
name|recipe
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"input-graph"
argument_list|)
name|String
name|inputGraph
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"output-graph"
argument_list|)
name|String
name|outputGraph
parameter_list|)
block|{
name|IRI
name|recipeIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|recipe
argument_list|)
decl_stmt|;
name|IRI
name|inputGraphIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|inputGraph
argument_list|)
decl_stmt|;
name|IRI
name|outputGraphIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|outputGraph
argument_list|)
decl_stmt|;
comment|// SemionRefactorer semionRefactorer = semionManager.getRegisteredRefactorer();
try|try
block|{
name|semionRefactorer
operator|.
name|consistentOntologyRefactoring
argument_list|(
name|outputGraphIRI
argument_list|,
name|inputGraphIRI
argument_list|,
name|recipeIRI
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
name|SemionRefactoringException
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
name|NoSuchRecipeException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|204
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|InconcistencyException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|415
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
annotation|@
name|POST
annotation|@
name|Path
argument_list|(
literal|"/consistent"
argument_list|)
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|MULTIPART_FORM_DATA
argument_list|)
annotation|@
name|Produces
argument_list|(
block|{
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
name|RDF_XML
block|,
name|KReSFormat
operator|.
name|OWL_XML
block|,
name|KReSFormat
operator|.
name|RDF_JSON
block|}
argument_list|)
specifier|public
name|Response
name|consistentRefactoringOfNewGraph
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"recipe"
argument_list|)
name|String
name|recipe
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"input"
argument_list|)
name|InputStream
name|input
parameter_list|)
block|{
name|IRI
name|recipeIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|recipe
argument_list|)
decl_stmt|;
comment|// SemionRefactorer semionRefactorer = semionManager.getRegisteredRefactorer();
name|OWLOntologyManager
name|manager
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|OWLOntology
name|inputOntology
decl_stmt|;
try|try
block|{
name|inputOntology
operator|=
name|manager
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|input
argument_list|)
expr_stmt|;
name|OWLOntology
name|outputOntology
decl_stmt|;
try|try
block|{
name|outputOntology
operator|=
name|semionRefactorer
operator|.
name|consistentOntologyRefactoring
argument_list|(
name|inputOntology
argument_list|,
name|recipeIRI
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SemionRefactoringException
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
name|NoSuchRecipeException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|204
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|InconcistencyException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|415
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
return|return
name|Response
operator|.
name|ok
argument_list|(
name|outputOntology
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
literal|404
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
specifier|public
name|String
name|getNamespace
parameter_list|()
block|{
return|return
name|onManager
operator|.
name|getKReSNamespace
argument_list|()
return|;
block|}
block|}
end_class

end_unit

