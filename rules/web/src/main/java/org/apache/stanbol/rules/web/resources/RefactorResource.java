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
name|base
operator|.
name|api
operator|.
name|Recipe
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
name|util
operator|.
name|RuleList
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
name|manager
operator|.
name|KB
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
name|manager
operator|.
name|changes
operator|.
name|RecipeImpl
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
name|manager
operator|.
name|parse
operator|.
name|RuleParserImpl
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
name|Refactorer
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
name|ImplicitProduces
import|;
end_import

begin_comment
comment|/**  *   * @author andrea.nuzzolese  *   */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/refactor"
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
name|RefactorResource
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
name|ONManager
name|onManager
decl_stmt|;
specifier|protected
name|Refactorer
name|semionRefactorer
decl_stmt|;
comment|// protected SemionManager semionManager;
specifier|protected
name|TcManager
name|tcManager
decl_stmt|;
specifier|public
name|RefactorResource
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
name|Refactorer
call|)
argument_list|(
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|Refactorer
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
name|ONManager
call|)
argument_list|(
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
comment|/**      * The apply mode allows the client to compose a recipe, by mean of string containg the rules, and apply      * it "on the fly" to the graph in input.      *       * @param recipe      *            String      * @param input      *            InputStream      * @return a Response containing the transformed graph      */
annotation|@
name|POST
annotation|@
name|Path
argument_list|(
literal|"/apply"
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
name|RDF_XML
block|,
name|KRFormat
operator|.
name|OWL_XML
block|,
name|KRFormat
operator|.
name|RDF_JSON
block|}
argument_list|)
specifier|public
name|Response
name|applyRefactoring
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
comment|// Refactorer semionRefactorer = semionManager.getRegisteredRefactorer();
name|KB
name|kb
init|=
name|RuleParserImpl
operator|.
name|parse
argument_list|(
name|recipe
argument_list|)
decl_stmt|;
if|if
condition|(
name|kb
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
name|RuleList
name|ruleList
init|=
name|kb
operator|.
name|getkReSRuleList
argument_list|()
decl_stmt|;
if|if
condition|(
name|ruleList
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
name|Recipe
name|actualRecipe
init|=
operator|new
name|RecipeImpl
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
name|ruleList
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
name|actualRecipe
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RefactoringException
name|e
parameter_list|)
block|{
comment|// refactoring exceptions are re-thrown
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
catch|catch
parameter_list|(
name|NoSuchRecipeException
name|e
parameter_list|)
block|{
comment|// missing recipes result in a status 404
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
annotation|@
name|POST
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
name|RDF_XML
block|,
name|KRFormat
operator|.
name|OWL_XML
block|,
name|KRFormat
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
comment|// Refactorer semionRefactorer = semionManager.getRegisteredRefactorer();
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
name|RefactoringException
name|e
parameter_list|)
block|{
comment|// refactoring exceptions are re-thrown
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
catch|catch
parameter_list|(
name|NoSuchRecipeException
name|e
parameter_list|)
block|{
comment|// missing recipes result in a status 404
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
annotation|@
name|GET
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
name|log
operator|.
name|info
argument_list|(
literal|"recipe: {}"
argument_list|,
name|recipe
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"input-graph: {}"
argument_list|,
name|inputGraph
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"output-graph: {}"
argument_list|,
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
comment|// Refactorer semionRefactorer = semionManager.getRegisteredRefactorer();
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
name|RefactoringException
name|e
parameter_list|)
block|{
comment|// refactoring exceptions are re-thrown
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
catch|catch
parameter_list|(
name|NoSuchRecipeException
name|e
parameter_list|)
block|{
comment|// missing recipes result in a status 404
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
block|}
end_class

end_unit

