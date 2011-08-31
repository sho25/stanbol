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
name|utils
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
name|io
operator|.
name|ByteArrayOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|Map
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
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|locks
operator|.
name|Lock
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
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
name|UriRef
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
name|LockableMGraph
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
name|NoSuchEntityException
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
name|owl
operator|.
name|transformation
operator|.
name|JenaToClerezzaConverter
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
name|owl
operator|.
name|transformation
operator|.
name|OWLAPIToClerezzaConverter
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
name|jena
operator|.
name|JenaReasoningService
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
name|owlapi
operator|.
name|OWLApiReasoningService
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
name|servicesapi
operator|.
name|InconsistentInputException
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
name|servicesapi
operator|.
name|ReasoningServiceException
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
name|servicesapi
operator|.
name|UnsupportedTaskException
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
name|manchesterowlsyntax
operator|.
name|ManchesterOWLSyntaxOntologyFormat
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
name|OWLAxiom
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
name|model
operator|.
name|SWRLRule
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
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Model
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|ModelFactory
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Statement
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|reasoner
operator|.
name|rulesys
operator|.
name|Rule
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

begin_comment
comment|/**  * TODO Add comment  */
end_comment

begin_class
specifier|public
class|class
name|ReasoningServiceExecutor
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
specifier|private
name|HttpHeaders
name|headers
decl_stmt|;
specifier|private
name|ServletContext
name|servletContext
decl_stmt|;
specifier|private
name|UriInfo
name|uriInfo
decl_stmt|;
specifier|private
name|TcManager
name|tcManager
decl_stmt|;
comment|// This task is not dinamically provided by the service, since it work on a
comment|// specific method
comment|// (isConsistent())
specifier|public
specifier|static
name|String
name|TASK_CHECK
init|=
literal|"check"
decl_stmt|;
specifier|public
name|ReasoningServiceExecutor
parameter_list|(
name|TcManager
name|tcManager
parameter_list|,
name|HttpHeaders
name|headers
parameter_list|,
name|ServletContext
name|context
parameter_list|,
name|UriInfo
name|uriInfo
parameter_list|)
block|{
name|this
operator|.
name|headers
operator|=
name|headers
expr_stmt|;
name|this
operator|.
name|servletContext
operator|=
name|context
expr_stmt|;
name|this
operator|.
name|uriInfo
operator|=
name|uriInfo
expr_stmt|;
name|this
operator|.
name|tcManager
operator|=
name|tcManager
expr_stmt|;
block|}
comment|/** 	 * Execute a JenaReasoningService 	 *  	 * TODO: Add parameter to decide if the output graph must be deleted if 	 * exists 	 *  	 * @param s 	 * @param input 	 * @param rules 	 * @return 	 */
specifier|public
name|Response
name|executeJenaReasoningService
parameter_list|(
name|String
name|task
parameter_list|,
name|JenaReasoningService
name|s
parameter_list|,
name|Model
name|input
parameter_list|,
name|List
argument_list|<
name|Rule
argument_list|>
name|rules
parameter_list|,
name|String
name|targetGraphID
parameter_list|,
name|boolean
name|filtered
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|parameters
parameter_list|)
block|{
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"[start] Execution: {}"
argument_list|,
name|s
argument_list|)
expr_stmt|;
comment|// Check task: this is managed directly by the endpoint
if|if
condition|(
name|task
operator|.
name|equals
argument_list|(
name|ReasoningServiceExecutor
operator|.
name|TASK_CHECK
argument_list|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Task is '{}'"
argument_list|,
name|ReasoningServiceExecutor
operator|.
name|TASK_CHECK
argument_list|)
expr_stmt|;
try|try
block|{
name|boolean
name|is
init|=
name|s
operator|.
name|isConsistent
argument_list|(
name|input
argument_list|)
decl_stmt|;
name|long
name|end
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"[end] In time: {}"
argument_list|,
operator|(
name|end
operator|-
name|start
operator|)
argument_list|)
expr_stmt|;
return|return
name|buildCheckResponse
argument_list|(
name|is
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|ReasoningServiceException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Error thrown: {}"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
try|try
block|{
name|Set
argument_list|<
name|Statement
argument_list|>
name|result
init|=
name|s
operator|.
name|runTask
argument_list|(
name|task
argument_list|,
name|input
argument_list|,
name|rules
argument_list|,
name|filtered
argument_list|,
name|parameters
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Result is null"
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|()
throw|;
block|}
name|Model
name|outputModel
init|=
name|ModelFactory
operator|.
name|createDefaultModel
argument_list|()
decl_stmt|;
name|outputModel
operator|.
name|add
argument_list|(
name|result
operator|.
name|toArray
argument_list|(
operator|new
name|Statement
index|[
name|result
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
comment|// If target is null, then get back results, elsewhere put it in
comment|// target graph
name|long
name|end
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"[end] In time: {}"
argument_list|,
operator|(
name|end
operator|-
name|start
operator|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Prepare output"
argument_list|)
expr_stmt|;
if|if
condition|(
name|targetGraphID
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Returning {} statements"
argument_list|,
name|result
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|isHTML
argument_list|()
condition|)
block|{
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|outputModel
operator|.
name|write
argument_list|(
name|out
argument_list|,
literal|"TURTLE"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"result"
argument_list|,
operator|new
name|ReasoningPrettyResultResource
argument_list|(
name|servletContext
argument_list|,
name|uriInfo
argument_list|,
name|out
argument_list|)
argument_list|)
argument_list|,
name|TEXT_HTML
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
name|outputModel
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
name|save
argument_list|(
name|outputModel
argument_list|,
name|targetGraphID
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
catch|catch
parameter_list|(
name|ReasoningServiceException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Error thrown: {}"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|InconsistentInputException
name|e
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"The input is not consistent"
argument_list|)
expr_stmt|;
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
block|}
catch|catch
parameter_list|(
name|UnsupportedTaskException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Error thrown: {}"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
comment|/** 	 * Executes the OWLApiReasoingService 	 *  	 * @param task 	 * @param s 	 * @param input 	 * @param rules 	 * @param targetGraphID 	 * @param parameters 	 * @return 	 */
specifier|public
name|Response
name|executeOWLApiReasoningService
parameter_list|(
name|String
name|task
parameter_list|,
name|OWLApiReasoningService
name|s
parameter_list|,
name|OWLOntology
name|input
parameter_list|,
name|List
argument_list|<
name|SWRLRule
argument_list|>
name|rules
parameter_list|,
name|String
name|targetGraphID
parameter_list|,
name|boolean
name|filtered
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|parameters
parameter_list|)
block|{
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"[start] Execution: {}"
argument_list|,
name|s
argument_list|)
expr_stmt|;
comment|// Check task: this is managed directly by the endpoint
if|if
condition|(
name|task
operator|.
name|equals
argument_list|(
name|ReasoningServiceExecutor
operator|.
name|TASK_CHECK
argument_list|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Task is '{}'"
argument_list|,
name|ReasoningServiceExecutor
operator|.
name|TASK_CHECK
argument_list|)
expr_stmt|;
try|try
block|{
name|boolean
name|is
init|=
name|s
operator|.
name|isConsistent
argument_list|(
name|input
argument_list|)
decl_stmt|;
name|long
name|end
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"[end] In time: {}"
argument_list|,
operator|(
name|end
operator|-
name|start
operator|)
argument_list|)
expr_stmt|;
return|return
name|buildCheckResponse
argument_list|(
name|is
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|ReasoningServiceException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
comment|// We get the manager from the input ontology
comment|// XXX We must be aware of this.
name|OWLOntologyManager
name|manager
init|=
name|input
operator|.
name|getOWLOntologyManager
argument_list|()
decl_stmt|;
try|try
block|{
name|OWLOntology
name|output
init|=
name|manager
operator|.
name|createOntology
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|OWLAxiom
argument_list|>
name|axioms
init|=
name|s
operator|.
name|runTask
argument_list|(
name|task
argument_list|,
name|input
argument_list|,
name|rules
argument_list|,
name|filtered
argument_list|,
name|parameters
argument_list|)
decl_stmt|;
name|long
name|end
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"[end] In time: {} ms"
argument_list|,
operator|(
name|end
operator|-
name|start
operator|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Prepare output: {} axioms"
argument_list|,
name|axioms
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|manager
operator|.
name|addAxioms
argument_list|(
name|output
argument_list|,
name|axioms
argument_list|)
expr_stmt|;
if|if
condition|(
name|targetGraphID
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|isHTML
argument_list|()
condition|)
block|{
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|manager
operator|.
name|saveOntology
argument_list|(
name|output
argument_list|,
operator|new
name|ManchesterOWLSyntaxOntologyFormat
argument_list|()
argument_list|,
name|out
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"result"
argument_list|,
operator|new
name|ReasoningPrettyResultResource
argument_list|(
name|servletContext
argument_list|,
name|uriInfo
argument_list|,
name|out
argument_list|)
argument_list|)
argument_list|,
name|TEXT_HTML
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
name|output
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
name|save
argument_list|(
name|output
argument_list|,
name|targetGraphID
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
catch|catch
parameter_list|(
name|InconsistentInputException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"The input is not consistent"
argument_list|)
expr_stmt|;
return|return
name|buildCheckResponse
argument_list|(
literal|false
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|ReasoningServiceException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Error! \n"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Error! \n"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Error! \n"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|UnsupportedTaskException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Error! \n"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Error! \n"
argument_list|,
name|t
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|t
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
comment|/** 	 * To build the Response for any CHECK task execution 	 *  	 * @param isConsistent 	 * @return 	 */
specifier|public
name|Response
name|buildCheckResponse
parameter_list|(
name|boolean
name|isConsistent
parameter_list|)
block|{
if|if
condition|(
name|isHTML
argument_list|()
condition|)
block|{
if|if
condition|(
name|isConsistent
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"The input is consistent"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"result"
argument_list|,
operator|new
name|ReasoningPrettyResultResource
argument_list|(
name|servletContext
argument_list|,
name|uriInfo
argument_list|,
literal|"The input is consistent :)"
argument_list|)
argument_list|)
argument_list|,
name|TEXT_HTML
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"The input is not consistent"
argument_list|)
expr_stmt|;
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
name|entity
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"result"
argument_list|,
operator|new
name|ReasoningPrettyResultResource
argument_list|(
name|servletContext
argument_list|,
name|uriInfo
argument_list|,
literal|"The input is NOT consistent :("
argument_list|)
argument_list|)
argument_list|)
operator|.
name|type
argument_list|(
name|TEXT_HTML
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|isConsistent
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"The input is consistent"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|(
literal|"The input is consistent :)"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"The input is not consistent"
argument_list|)
expr_stmt|;
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
block|}
block|}
block|}
comment|/** 	 * Check if the client needs a serialization of the output or a human 	 * readable form (HTML) 	 *  	 * @param headers 	 * @return 	 */
specifier|public
name|boolean
name|isHTML
parameter_list|()
block|{
comment|// We only want to state if HTML format is the preferred format
comment|// requested
name|Set
argument_list|<
name|String
argument_list|>
name|htmlformats
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|htmlformats
operator|.
name|add
argument_list|(
name|TEXT_HTML
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|rdfformats
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|String
index|[]
name|formats
init|=
block|{
name|TEXT_HTML
block|,
literal|"text/plain"
block|,
name|KRFormat
operator|.
name|RDF_XML
block|,
name|KRFormat
operator|.
name|TURTLE
block|,
literal|"text/turtle"
block|,
literal|"text/n3"
block|}
decl_stmt|;
name|rdfformats
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|formats
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|MediaType
argument_list|>
name|mediaTypes
init|=
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
decl_stmt|;
for|for
control|(
name|MediaType
name|t
range|:
name|mediaTypes
control|)
block|{
name|String
name|strty
init|=
name|t
operator|.
name|toString
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Acceptable is {}"
argument_list|,
name|t
argument_list|)
expr_stmt|;
if|if
condition|(
name|htmlformats
operator|.
name|contains
argument_list|(
name|strty
argument_list|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Requested format is HTML {}"
argument_list|,
name|t
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
elseif|else
if|if
condition|(
name|rdfformats
operator|.
name|contains
argument_list|(
name|strty
argument_list|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Requested format is RDF {}"
argument_list|,
name|t
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
comment|// Default behavior? Should never happen!
return|return
literal|true
return|;
block|}
comment|/** 	 * To save data in the triple store. 	 *  	 * @param data 	 * @param targetGraphID 	 */
specifier|protected
name|void
name|save
parameter_list|(
name|Object
name|data
parameter_list|,
name|String
name|targetGraphID
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Attempt saving in target graph {}"
argument_list|,
name|targetGraphID
argument_list|)
expr_stmt|;
specifier|final
name|long
name|startSave
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|LockableMGraph
name|mGraph
decl_stmt|;
name|UriRef
name|graphUriRef
init|=
operator|new
name|UriRef
argument_list|(
name|targetGraphID
argument_list|)
decl_stmt|;
try|try
block|{
comment|// Check whether the graph already exists
name|mGraph
operator|=
name|tcManager
operator|.
name|getMGraph
argument_list|(
name|graphUriRef
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchEntityException
name|e
parameter_list|)
block|{
name|mGraph
operator|=
name|tcManager
operator|.
name|createMGraph
argument_list|(
name|graphUriRef
argument_list|)
expr_stmt|;
block|}
comment|// We lock the graph before proceed
name|Lock
name|writeLock
init|=
name|mGraph
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
decl_stmt|;
name|boolean
name|saved
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|data
operator|instanceof
name|Model
condition|)
block|{
name|MGraph
name|m
init|=
name|JenaToClerezzaConverter
operator|.
name|jenaModelToClerezzaMGraph
argument_list|(
operator|(
name|Model
operator|)
name|data
argument_list|)
decl_stmt|;
name|writeLock
operator|.
name|lock
argument_list|()
expr_stmt|;
name|saved
operator|=
name|mGraph
operator|.
name|addAll
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|writeLock
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|data
operator|instanceof
name|OWLOntology
condition|)
block|{
name|MGraph
name|m
init|=
name|OWLAPIToClerezzaConverter
operator|.
name|owlOntologyToClerezzaMGraph
argument_list|(
operator|(
name|OWLOntology
operator|)
name|data
argument_list|)
decl_stmt|;
name|writeLock
operator|.
name|lock
argument_list|()
expr_stmt|;
name|saved
operator|=
name|mGraph
operator|.
name|addAll
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|writeLock
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|saved
condition|)
throw|throw
operator|new
name|WebApplicationException
argument_list|(
operator|new
name|IOException
argument_list|(
literal|"Cannot save model!"
argument_list|)
argument_list|,
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
specifier|final
name|long
name|endSave
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Save time: {}"
argument_list|,
operator|(
name|endSave
operator|-
name|startSave
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

