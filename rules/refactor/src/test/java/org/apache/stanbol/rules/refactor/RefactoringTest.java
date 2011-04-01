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
name|refactor
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|fail
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
name|java
operator|.
name|util
operator|.
name|Dictionary
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|Set
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
name|access
operator|.
name|WeightedTcProvider
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|sparql
operator|.
name|QueryEngine
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
name|jena
operator|.
name|sparql
operator|.
name|JenaSparqlEngine
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
name|simple
operator|.
name|storage
operator|.
name|SimpleTcProvider
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
name|Rule
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
name|RuleStore
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
name|RecipeList
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
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|refactor
operator|.
name|impl
operator|.
name|RefactorerImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|BeforeClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
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
name|OWLOntologyStorageException
import|;
end_import

begin_class
specifier|public
class|class
name|RefactoringTest
block|{
specifier|static
name|RuleStore
name|ruleStore
decl_stmt|;
specifier|static
name|OWLOntology
name|ontology
decl_stmt|;
specifier|static
name|IRI
name|recipeIRI
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setup
parameter_list|()
block|{
name|recipeIRI
operator|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/rmi_config.owl#MyTestRecipe"
argument_list|)
expr_stmt|;
name|InputStream
name|ontologyStream
init|=
name|RefactoringTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"/META-INF/test/testKReSOnt.owl"
argument_list|)
decl_stmt|;
name|InputStream
name|recipeStream
init|=
name|RefactoringTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"/META-INF/test/rmi.owl"
argument_list|)
decl_stmt|;
try|try
block|{
specifier|final
name|OWLOntology
name|recipeModel
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|recipeStream
argument_list|)
decl_stmt|;
name|ontology
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|ontologyStream
argument_list|)
expr_stmt|;
name|ruleStore
operator|=
operator|new
name|RuleStore
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|setStore
parameter_list|(
name|OWLOntology
name|owl
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|void
name|saveOntology
parameter_list|()
throws|throws
name|OWLOntologyStorageException
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|RecipeList
name|listRecipes
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|IRI
argument_list|>
name|listIRIRecipes
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getRuleStoreNamespace
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Recipe
name|getRecipe
parameter_list|(
name|IRI
name|recipeIRI
parameter_list|)
throws|throws
name|NoSuchRecipeException
block|{
name|Recipe
name|recipe
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|recipeIRI
operator|!=
literal|null
condition|)
block|{
name|OWLDataFactory
name|factory
init|=
name|OWLManager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|OWLIndividual
name|recipeIndividual
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|recipeIRI
argument_list|)
decl_stmt|;
if|if
condition|(
name|recipeIndividual
operator|!=
literal|null
condition|)
block|{
name|String
name|ruleNS
init|=
literal|"http://kres.iks-project.eu/ontology/meta/rmi.owl#"
decl_stmt|;
comment|/**                              * First get the recipe description in the rule/recipe ontology.                              */
name|OWLDataProperty
name|hasDescription
init|=
name|factory
operator|.
name|getOWLDataProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ruleNS
operator|+
literal|"hasDescription"
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|recipeDescription
init|=
literal|null
decl_stmt|;
name|Set
argument_list|<
name|OWLLiteral
argument_list|>
name|descriptions
init|=
name|recipeIndividual
operator|.
name|getDataPropertyValues
argument_list|(
name|hasDescription
argument_list|,
name|recipeModel
argument_list|)
decl_stmt|;
for|for
control|(
name|OWLLiteral
name|description
range|:
name|descriptions
control|)
block|{
name|recipeDescription
operator|=
name|description
operator|.
name|getLiteral
argument_list|()
expr_stmt|;
block|}
comment|/**                              * Then retrieve the rules associated to the recipe in the rule store.                              */
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
name|ruleNS
operator|+
literal|"hasRule"
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
name|recipeModel
argument_list|)
decl_stmt|;
name|String
name|kReSRulesInKReSSyntax
init|=
literal|""
decl_stmt|;
comment|/**                              * Fetch the rule content expressed as a literal in Rule Syntax.                              */
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
name|ruleNS
operator|+
literal|"hasBodyAndHead"
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|OWLIndividual
name|rule
range|:
name|rules
control|)
block|{
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
name|recipeModel
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
name|String
name|ruleTmp
init|=
name|kReSRuleLiteral
operator|.
name|getLiteral
argument_list|()
operator|.
name|replace
argument_list|(
literal|"&lt;"
argument_list|,
literal|"<"
argument_list|)
decl_stmt|;
name|ruleTmp
operator|=
name|ruleTmp
operator|.
name|replace
argument_list|(
literal|"&gt;"
argument_list|,
literal|">"
argument_list|)
expr_stmt|;
name|kReSRulesInKReSSyntax
operator|+=
name|ruleTmp
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
comment|/**                              * Create the Recipe object.                              */
name|RuleList
name|ruleList
init|=
name|RuleParserImpl
operator|.
name|parse
argument_list|(
name|kReSRulesInKReSSyntax
argument_list|)
operator|.
name|getkReSRuleList
argument_list|()
decl_stmt|;
name|recipe
operator|=
operator|new
name|RecipeImpl
argument_list|(
name|recipeIRI
argument_list|,
name|recipeDescription
argument_list|,
name|ruleList
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|NoSuchRecipeException
argument_list|(
name|recipeIRI
argument_list|)
throw|;
block|}
block|}
return|return
name|recipe
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntology
name|getOntology
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getFilePath
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|addRecipe
parameter_list|(
name|IRI
name|recipeIRI
parameter_list|,
name|String
name|recipeDescription
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|Recipe
name|addRuleToRecipe
parameter_list|(
name|String
name|recipeID
parameter_list|,
name|String
name|kReSRuleInKReSSyntax
parameter_list|)
throws|throws
name|NoSuchRecipeException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Recipe
name|addRuleToRecipe
parameter_list|(
name|Recipe
name|recipe
parameter_list|,
name|String
name|kReSRuleInKReSSyntax
parameter_list|)
block|{
return|return
literal|null
return|;
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|void
name|createRecipe
parameter_list|(
name|String
name|recipeID
parameter_list|,
name|String
name|rulesInKReSSyntax
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|removeRecipe
parameter_list|(
name|Recipe
name|recipe
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Not supported yet."
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|removeRecipe
parameter_list|(
name|IRI
name|recipeIRI
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Not supported yet."
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|removeRule
parameter_list|(
name|Rule
name|rule
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Not supported yet."
argument_list|)
throw|;
block|}
block|}
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|refactoringTest
parameter_list|()
throws|throws
name|Exception
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|emptyConfig
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
class|class
name|SpecialTcManager
extends|extends
name|TcManager
block|{
specifier|public
name|SpecialTcManager
parameter_list|(
name|QueryEngine
name|qe
parameter_list|,
name|WeightedTcProvider
name|wtcp
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|bindQueryEngine
argument_list|(
name|qe
argument_list|)
expr_stmt|;
name|bindWeightedTcProvider
argument_list|(
name|wtcp
argument_list|)
expr_stmt|;
block|}
block|}
name|QueryEngine
name|qe
init|=
operator|new
name|JenaSparqlEngine
argument_list|()
decl_stmt|;
name|WeightedTcProvider
name|wtcp
init|=
operator|new
name|SimpleTcProvider
argument_list|()
decl_stmt|;
name|TcManager
name|tcm
init|=
operator|new
name|SpecialTcManager
argument_list|(
name|qe
argument_list|,
name|wtcp
argument_list|)
decl_stmt|;
name|ONManager
name|onm
init|=
operator|new
name|ONManagerImpl
argument_list|(
name|tcm
argument_list|,
name|wtcp
argument_list|,
name|emptyConfig
argument_list|)
decl_stmt|;
name|Refactorer
name|refactorer
init|=
operator|new
name|RefactorerImpl
argument_list|(
literal|null
argument_list|,
operator|new
name|Serializer
argument_list|()
argument_list|,
name|tcm
argument_list|,
name|onm
argument_list|,
name|ruleStore
argument_list|,
name|emptyConfig
argument_list|)
decl_stmt|;
try|try
block|{
name|refactorer
operator|.
name|ontologyRefactoring
argument_list|(
name|ontology
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
name|fail
argument_list|(
literal|"Error while refactoring."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchRecipeException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Error while refactoring: no such recipe"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

