begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reasoners
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
name|*
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
name|*
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
name|*
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
name|Response
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
name|reasoners
operator|.
name|base
operator|.
name|api
operator|.
name|ConsistentRefactorer
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
name|RefactoringException
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

begin_comment
comment|/**  * Special refactoring services that employ a DL reasoner for ensuring/checking consistency.  *   * @author alessandro  *   */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/reasoners/refactor"
argument_list|)
specifier|public
class|class
name|ConsistentRefactoring
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
specifier|protected
name|ConsistentRefactorer
name|refactorer
decl_stmt|;
specifier|public
name|ConsistentRefactoring
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|refactorer
operator|=
call|(
name|ConsistentRefactorer
call|)
argument_list|(
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|ConsistentRefactorer
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
name|refactorer
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
comment|// Refactorer semionRefactorer = semionManager.getRegisteredRefactorer();
try|try
block|{
name|refactorer
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
name|RefactoringException
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
name|NoSuchRecipeException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|NO_CONTENT
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
name|log
operator|.
name|error
argument_list|(
literal|"Cannot classify ionconsistent graph "
operator|+
name|inputGraph
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|PRECONDITION_FAILED
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
name|MULTIPART_FORM_DATA
argument_list|)
annotation|@
name|Produces
argument_list|(
block|{
name|TURTLE
block|,
name|FUNCTIONAL_OWL
block|,
name|MANCHESTER_OWL
block|,
name|RDF_XML
block|,
name|OWL_XML
block|,
name|RDF_JSON
block|}
argument_list|)
specifier|public
name|Response
name|consistentRefactoringOfNewGraph
parameter_list|(
annotation|@
name|FormDataParam
argument_list|(
literal|"recipe"
argument_list|)
name|String
name|recipe
parameter_list|,
annotation|@
name|FormDataParam
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
comment|// Refactorer semionRefactorer = semionManager.getRegisteredRefactorer();
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
name|refactorer
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
name|RefactoringException
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
name|NoSuchRecipeException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|NO_CONTENT
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
name|log
operator|.
name|error
argument_list|(
literal|"Cannot classify ionconsistent graph "
operator|+
name|inputOntology
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|PRECONDITION_FAILED
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
name|NOT_FOUND
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

