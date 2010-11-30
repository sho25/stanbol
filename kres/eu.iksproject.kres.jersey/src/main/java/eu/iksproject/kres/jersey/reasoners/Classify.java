begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * To change this template, choose Tools | Templates  * and open the template in the editor.  */
end_comment

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
name|reasoners
package|;
end_package

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
name|Resource
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
name|rules
operator|.
name|KReSRule
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
name|rules
operator|.
name|NoSuchRecipeException
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
name|rules
operator|.
name|RuleStore
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
name|rules
operator|.
name|util
operator|.
name|KReSRuleList
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
name|reasoners
operator|.
name|KReSCreateReasoner
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
name|reasoners
operator|.
name|KReSRunReasoner
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
name|reasoners
operator|.
name|KReSRunRules
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
name|rules
operator|.
name|KReSKB
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
name|rules
operator|.
name|manager
operator|.
name|KReSRuleStore
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
name|rules
operator|.
name|parser
operator|.
name|KReSRuleParser
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|AddImport
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
name|OWLClassAssertionAxiom
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
name|OWLDataProperty
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
name|OWLIndividual
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
name|OWLLiteral
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
name|OWLObjectProperty
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
name|reasoner
operator|.
name|InconsistentOntologyException
import|;
end_import

begin_comment
comment|/**  *  * @author elvio  */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/classify"
argument_list|)
specifier|public
class|class
name|Classify
block|{
specifier|private
name|RuleStore
name|kresRuleStore
decl_stmt|;
specifier|private
name|OWLOntology
name|inputowl
decl_stmt|;
specifier|private
name|OWLOntology
name|scopeowl
decl_stmt|;
comment|/**      * To get the KReSRuleStore where are stored the rules and the recipes      *      * @param servletContext {To get the context where the REST service is running.}      */
specifier|public
name|Classify
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|this
operator|.
name|kresRuleStore
operator|=
operator|(
name|RuleStore
operator|)
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|RuleStore
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|kresRuleStore
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
literal|"WARNING: KReSRuleStore with stored rules and recipes is missing in ServletContext. A new instance has been created."
argument_list|)
expr_stmt|;
name|this
operator|.
name|kresRuleStore
operator|=
operator|new
name|KReSRuleStore
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"PATH TO OWL FILE LOADED: "
operator|+
name|kresRuleStore
operator|.
name|getFilePath
argument_list|()
argument_list|)
expr_stmt|;
comment|//            throw new IllegalStateException(
comment|//                    "KReSRuleStore with stored rules and recipes is missing in ServletContext");
block|}
block|}
comment|/**      * To trasform a sequence of rules to a Jena Model      * @param owl {OWLOntology object contains a single recipe}      * @return {A jena rdf model contains the SWRL rule.}      */
specifier|private
name|Model
name|fromRecipeToModel
parameter_list|(
name|OWLOntology
name|owl
parameter_list|)
throws|throws
name|NoSuchRecipeException
block|{
name|RuleStore
name|store
init|=
operator|new
name|KReSRuleStore
argument_list|(
name|owl
argument_list|)
decl_stmt|;
name|Model
name|jenamodel
init|=
name|ModelFactory
operator|.
name|createDefaultModel
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|factory
init|=
name|owl
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|OWLClass
name|ontocls
init|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/rmi.owl#Recipe"
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLClassAssertionAxiom
argument_list|>
name|cls
init|=
name|owl
operator|.
name|getClassAssertionAxioms
argument_list|(
name|ontocls
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|OWLClassAssertionAxiom
argument_list|>
name|iter
init|=
name|cls
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|IRI
name|recipeiri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|iter
operator|.
name|next
argument_list|()
operator|.
name|getIndividual
argument_list|()
operator|.
name|toStringID
argument_list|()
argument_list|)
decl_stmt|;
name|OWLIndividual
name|recipeIndividual
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|recipeiri
argument_list|)
decl_stmt|;
name|OWLObjectProperty
name|objectProperty
init|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/rmi.owl#hasRule"
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLIndividual
argument_list|>
name|rules
init|=
name|recipeIndividual
operator|.
name|getObjectPropertyValues
argument_list|(
name|objectProperty
argument_list|,
name|store
operator|.
name|getOntology
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|kReSRules
init|=
literal|""
decl_stmt|;
for|for
control|(
name|OWLIndividual
name|rule
range|:
name|rules
control|)
block|{
name|OWLDataProperty
name|hasBodyAndHead
init|=
name|factory
operator|.
name|getOWLDataProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/rmi.owl#hasBodyAndHead"
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLLiteral
argument_list|>
name|kReSRuleLiterals
init|=
name|rule
operator|.
name|getDataPropertyValues
argument_list|(
name|hasBodyAndHead
argument_list|,
name|store
operator|.
name|getOntology
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|OWLLiteral
name|kReSRuleLiteral
range|:
name|kReSRuleLiterals
control|)
block|{
name|kReSRules
operator|+=
name|kReSRuleLiteral
operator|.
name|getLiteral
argument_list|()
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
expr_stmt|;
block|}
block|}
comment|//"ProvaParent =<http://www.semanticweb.org/ontologies/2010/6/ProvaParent.owl#> . rule1[ has(ProvaParent:hasParent, ?x, ?y) . has(ProvaParent:hasBrother, ?y, ?z) -> has(ProvaParent:hasUncle, ?x, ?z) ]");
name|KReSKB
name|kReSKB
init|=
name|KReSRuleParser
operator|.
name|parse
argument_list|(
name|kReSRules
argument_list|)
decl_stmt|;
name|KReSRuleList
name|listrules
init|=
name|kReSKB
operator|.
name|getkReSRuleList
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|KReSRule
argument_list|>
name|iterule
init|=
name|listrules
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|iterule
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|KReSRule
name|singlerule
init|=
name|iterule
operator|.
name|next
argument_list|()
decl_stmt|;
name|Resource
name|resource
init|=
name|singlerule
operator|.
name|toSWRL
argument_list|(
name|jenamodel
argument_list|)
decl_stmt|;
block|}
return|return
name|jenamodel
return|;
block|}
comment|/**      * To run a classifying reasoner on a RDF input File or IRI on the base of a Scope (or an ontology) and a recipe. Can be used either HermiT or an owl-link server reasoner end-point      * @param session {A string contains the session IRI used to classify the input.}      * @param scope {A string contains either a specific scope's ontology or the scope IRI used to classify the input.}      * @param recipe {A string contains the recipe IRI from the service http://localhost:port/kres/recipe/recipeName.}      * @Param file {A file in a RDF (eihter RDF/XML or owl) to be classified.}      * @Param input_graph {A string contains the IRI of RDF (either RDF/XML or OWL) to be classified.}      * @Param owllink_endpoint {A string contains the ressoner server end-point URL.}      * @return Return:<br/>      *          200 The ontology is retrieved, containing only class axioms<br/>      *          400 To run the session is needed the scope<br/>      *          404 No data is retrieved<br/>      *          409 Too much RDF inputs<br/>      *          500 Some error occurred      */
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
name|OWL_XML
block|}
argument_list|)
specifier|public
name|Response
name|ontologyClassify
parameter_list|(
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"session"
argument_list|)
name|String
name|session
parameter_list|,
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"scope"
argument_list|)
name|String
name|scope
parameter_list|,
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"recipe"
argument_list|)
name|String
name|recipe
parameter_list|,
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"input-graph"
argument_list|)
name|String
name|input_graph
parameter_list|,
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"file"
argument_list|)
name|File
name|file
parameter_list|,
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"owllink-endpoint"
argument_list|)
name|String
name|owllink_endpoint
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
operator|(
name|session
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|scope
operator|==
literal|null
operator|)
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"ERROR: Cannot load session without scope."
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|//Check for input conflict. Only one input at once is allowed
if|if
condition|(
operator|(
name|file
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|input_graph
operator|!=
literal|null
operator|)
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"ERROR: To much RDF input"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|CONFLICT
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|//Load input file or graph
if|if
condition|(
name|file
operator|!=
literal|null
condition|)
name|this
operator|.
name|inputowl
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|file
argument_list|)
expr_stmt|;
if|if
condition|(
name|input_graph
operator|!=
literal|null
condition|)
name|this
operator|.
name|inputowl
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|input_graph
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|inputowl
operator|==
literal|null
operator|&&
operator|(
name|session
operator|==
literal|null
operator|||
name|scope
operator|==
literal|null
operator|)
condition|)
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|build
argument_list|()
return|;
if|if
condition|(
name|inputowl
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|scope
operator|!=
literal|null
condition|)
name|this
operator|.
name|inputowl
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|createOntology
argument_list|()
expr_stmt|;
else|else
block|{
name|this
operator|.
name|inputowl
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|createOntology
argument_list|()
expr_stmt|;
block|}
block|}
comment|//Create list to add ontologies as imported
name|OWLOntologyManager
name|mgr
init|=
name|inputowl
operator|.
name|getOWLOntologyManager
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|factory
init|=
name|inputowl
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|additions
init|=
operator|new
name|LinkedList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
decl_stmt|;
name|boolean
name|ok
init|=
literal|false
decl_stmt|;
comment|//Load ontologies from scope, RDF input and recipe
comment|//Try to resolve scope IRI
if|if
condition|(
operator|(
name|scope
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|session
operator|==
literal|null
operator|)
condition|)
try|try
block|{
name|IRI
name|iri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|scope
argument_list|)
decl_stmt|;
name|ScopeRegistry
name|reg
init|=
name|ONManager
operator|.
name|get
argument_list|()
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|OntologyScope
name|ontoscope
init|=
name|reg
operator|.
name|getScope
argument_list|(
name|iri
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|OWLOntology
argument_list|>
name|importscope
init|=
name|ontoscope
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|getOntologies
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|OntologySpace
argument_list|>
name|importsession
init|=
name|ontoscope
operator|.
name|getSessionSpaces
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
comment|//Add ontology as import form scope, if it is anonymus we try to add single axioms.
while|while
condition|(
name|importscope
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OWLOntology
name|auxonto
init|=
name|importscope
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|auxonto
operator|.
name|getOntologyID
argument_list|()
operator|.
name|isAnonymous
argument_list|()
condition|)
block|{
name|additions
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|inputowl
argument_list|,
name|factory
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|auxonto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOntologyDocumentIRI
argument_list|(
name|auxonto
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|mgr
operator|.
name|addAxioms
argument_list|(
name|inputowl
argument_list|,
name|auxonto
operator|.
name|getAxioms
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|//Add ontology form sessions
while|while
condition|(
name|importsession
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Iterator
argument_list|<
name|OWLOntology
argument_list|>
name|sessionontos
init|=
name|importsession
operator|.
name|next
argument_list|()
operator|.
name|getOntologies
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|sessionontos
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OWLOntology
name|auxonto
init|=
name|sessionontos
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|auxonto
operator|.
name|getOntologyID
argument_list|()
operator|.
name|isAnonymous
argument_list|()
condition|)
block|{
name|additions
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|inputowl
argument_list|,
name|factory
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|auxonto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOntologyDocumentIRI
argument_list|(
name|auxonto
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|mgr
operator|.
name|addAxioms
argument_list|(
name|inputowl
argument_list|,
name|auxonto
operator|.
name|getAxioms
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"ERROR: Problem with scope: "
operator|+
name|scope
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
comment|//Get Ontologies from session
if|if
condition|(
operator|(
name|session
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|scope
operator|!=
literal|null
operator|)
condition|)
try|try
block|{
name|IRI
name|iri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|scope
argument_list|)
decl_stmt|;
name|ScopeRegistry
name|reg
init|=
name|ONManager
operator|.
name|get
argument_list|()
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|OntologyScope
name|ontoscope
init|=
name|reg
operator|.
name|getScope
argument_list|(
name|iri
argument_list|)
decl_stmt|;
name|SessionOntologySpace
name|sos
init|=
name|ontoscope
operator|.
name|getSessionSpace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|session
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontos
init|=
name|sos
operator|.
name|getOntologyManager
argument_list|()
operator|.
name|getOntologies
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|OWLOntology
argument_list|>
name|iteronto
init|=
name|ontos
operator|.
name|iterator
argument_list|()
decl_stmt|;
comment|//Add session ontologies as import, if it is anonymus we try to add single axioms.
while|while
condition|(
name|iteronto
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OWLOntology
name|auxonto
init|=
name|iteronto
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|auxonto
operator|.
name|getOntologyID
argument_list|()
operator|.
name|isAnonymous
argument_list|()
condition|)
block|{
name|additions
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|inputowl
argument_list|,
name|factory
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|auxonto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOntologyDocumentIRI
argument_list|(
name|auxonto
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|mgr
operator|.
name|addAxioms
argument_list|(
name|inputowl
argument_list|,
name|auxonto
operator|.
name|getAxioms
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"ERROR: Problem with session: "
operator|+
name|session
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
comment|//After gathered the all ontology as imported now we apply the changes
if|if
condition|(
name|additions
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
name|mgr
operator|.
name|applyChanges
argument_list|(
name|additions
argument_list|)
expr_stmt|;
comment|//Run HermiT if the reasonerURL is null;
if|if
condition|(
name|owllink_endpoint
operator|==
literal|null
condition|)
block|{
try|try
block|{
if|if
condition|(
name|recipe
operator|!=
literal|null
condition|)
block|{
name|OWLOntology
name|recipeowl
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|recipe
argument_list|)
argument_list|)
decl_stmt|;
comment|//Get Jea RDF model of SWRL rule contained in the recipe
name|Model
name|swrlmodel
init|=
name|fromRecipeToModel
argument_list|(
name|recipeowl
argument_list|)
decl_stmt|;
comment|//Create a reasoner to run rules contained in the recipe
name|KReSRunRules
name|rulereasoner
init|=
operator|new
name|KReSRunRules
argument_list|(
name|swrlmodel
argument_list|,
name|inputowl
argument_list|)
decl_stmt|;
comment|//Run the rule reasoner to the input RDF with the added top-ontology
name|inputowl
operator|=
name|rulereasoner
operator|.
name|runRulesReasoner
argument_list|()
expr_stmt|;
block|}
comment|//Create the reasoner for the classification
name|KReSCreateReasoner
name|newreasoner
init|=
operator|new
name|KReSCreateReasoner
argument_list|(
name|inputowl
argument_list|)
decl_stmt|;
comment|//Prepare and start the reasoner to classify ontology's resources
name|KReSRunReasoner
name|reasoner
init|=
operator|new
name|KReSRunReasoner
argument_list|(
name|newreasoner
operator|.
name|getReasoner
argument_list|()
argument_list|)
decl_stmt|;
comment|//Create a new OWLOntology model where to put the inferred axioms
name|OWLOntology
name|output
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|createOntology
argument_list|(
name|inputowl
operator|.
name|getOntologyID
argument_list|()
argument_list|)
decl_stmt|;
comment|//Initial input axioms count
name|int
name|startax
init|=
name|output
operator|.
name|getAxiomCount
argument_list|()
decl_stmt|;
comment|//Run the classification
name|output
operator|=
name|reasoner
operator|.
name|runClassifyInference
argument_list|(
name|output
argument_list|)
expr_stmt|;
comment|//End output axioms count
name|int
name|endax
init|=
name|output
operator|.
name|getAxiomCount
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|endax
operator|-
name|startax
operator|)
operator|>
literal|0
condition|)
block|{
comment|//Some inference is retrieved
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
else|else
block|{
comment|//No data is retrieved
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
catch|catch
parameter_list|(
name|InconsistentOntologyException
name|exc
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"CHECK ONTOLOGY CONSISTENCE"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|//If there is an owl-link server end-point specified in the form
block|}
else|else
block|{
try|try
block|{
if|if
condition|(
name|recipe
operator|!=
literal|null
condition|)
block|{
name|OWLOntology
name|recipeowl
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|recipe
argument_list|)
argument_list|)
decl_stmt|;
comment|//Get Jea RDF model of SWRL rule contained in the recipe
name|Model
name|swrlmodel
init|=
name|fromRecipeToModel
argument_list|(
name|recipeowl
argument_list|)
decl_stmt|;
comment|//Create a reasoner to run rules contained in the recipe by using the server and-point
name|KReSRunRules
name|rulereasoner
init|=
operator|new
name|KReSRunRules
argument_list|(
name|swrlmodel
argument_list|,
name|inputowl
argument_list|,
operator|new
name|URL
argument_list|(
name|owllink_endpoint
argument_list|)
argument_list|)
decl_stmt|;
comment|//Run the rule reasoner to the input RDF with the added top-ontology
name|inputowl
operator|=
name|rulereasoner
operator|.
name|runRulesReasoner
argument_list|()
expr_stmt|;
block|}
comment|//Create the reasoner for the consistency check by using the server and-point
name|KReSCreateReasoner
name|newreasoner
init|=
operator|new
name|KReSCreateReasoner
argument_list|(
name|inputowl
argument_list|,
operator|new
name|URL
argument_list|(
name|owllink_endpoint
argument_list|)
argument_list|)
decl_stmt|;
comment|//Prepare and start the reasoner to classify ontology's resources
name|KReSRunReasoner
name|reasoner
init|=
operator|new
name|KReSRunReasoner
argument_list|(
name|newreasoner
operator|.
name|getReasoner
argument_list|()
argument_list|)
decl_stmt|;
comment|//Create a new OWLOntology model where to put the inferred axioms
name|OWLOntology
name|output
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|createOntology
argument_list|(
name|inputowl
operator|.
name|getOntologyID
argument_list|()
argument_list|)
decl_stmt|;
comment|//Initial input axioms count
name|int
name|startax
init|=
name|output
operator|.
name|getAxiomCount
argument_list|()
decl_stmt|;
comment|//Run the classification
name|output
operator|=
name|reasoner
operator|.
name|runClassifyInference
argument_list|(
name|output
argument_list|)
expr_stmt|;
comment|//End output axioms count
name|int
name|endax
init|=
name|output
operator|.
name|getAxiomCount
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|endax
operator|-
name|startax
operator|)
operator|>
literal|0
condition|)
block|{
comment|//Some inference is retrieved
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
else|else
block|{
comment|//No data is retrieved
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
catch|catch
parameter_list|(
name|InconsistentOntologyException
name|exc
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"CHECK ONTOLOGY CONSISTENCE"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
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
block|}
block|}
end_class

end_unit

