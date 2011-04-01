begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * To change this template, choose Tools | Templates  * and open the template in the editor.  */
end_comment

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
name|manager
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
name|assertEquals
import|;
end_import

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
name|HashMap
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
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|manager
operator|.
name|changes
operator|.
name|AddRecipe
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
name|LoadRuleFile
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
name|RuleStoreImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|AfterClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
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
name|OWLNamedIndividual
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

begin_comment
comment|/**  *  * @author elvio  */
end_comment

begin_class
specifier|public
class|class
name|AddRecipeTest
block|{
specifier|public
name|AddRecipeTest
parameter_list|()
block|{     }
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setUpClass
parameter_list|()
throws|throws
name|Exception
block|{     }
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|tearDownClass
parameter_list|()
throws|throws
name|Exception
block|{     }
annotation|@
name|Before
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
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
name|onm
operator|=
operator|new
name|ONManagerImpl
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|store
operator|=
operator|new
name|RuleStoreImpl
argument_list|(
name|onm
argument_list|,
name|configuration
argument_list|,
literal|"./src/main/resources/RuleOntology/TestKReSOntologyRules.owl"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|After
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|store
operator|=
literal|null
expr_stmt|;
name|onm
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|RuleStore
name|store
init|=
literal|null
decl_stmt|;
specifier|public
name|ONManager
name|onm
init|=
literal|null
decl_stmt|;
comment|/**      * Test of addRecipe method, of class AddRecipe.      */
annotation|@
name|Test
specifier|public
name|void
name|testAddRecipe_3args_1
parameter_list|()
block|{
comment|//        RuleStore store  = new RuleStoreImpl("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
name|OWLOntology
name|owl
init|=
name|store
operator|.
name|getOntology
argument_list|()
decl_stmt|;
comment|//Load the example file
name|LoadRuleFile
name|load
init|=
operator|new
name|LoadRuleFile
argument_list|(
literal|"./src/main/resources/RuleOntology/TestRuleFileExample.txt"
argument_list|,
name|store
argument_list|)
decl_stmt|;
name|String
name|ID
init|=
name|owl
operator|.
name|getOntologyID
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<"
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|">"
argument_list|,
literal|""
argument_list|)
operator|+
literal|"#"
decl_stmt|;
name|String
name|recipeName
init|=
literal|"MyRecipeNew"
decl_stmt|;
name|Vector
argument_list|<
name|IRI
argument_list|>
name|rules
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|rules
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleC"
argument_list|)
argument_list|)
expr_stmt|;
name|rules
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleB"
argument_list|)
argument_list|)
expr_stmt|;
name|rules
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleA"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|recipeDescription
init|=
literal|"My comment to the recipe"
decl_stmt|;
name|AddRecipe
name|instance
init|=
operator|new
name|AddRecipe
argument_list|(
name|load
operator|.
name|getStore
argument_list|()
argument_list|)
decl_stmt|;
name|boolean
name|result
init|=
name|instance
operator|.
name|addRecipe
argument_list|(
name|recipeName
argument_list|,
name|rules
argument_list|,
name|recipeDescription
argument_list|)
decl_stmt|;
name|OWLOntology
name|newonto
init|=
name|instance
operator|.
name|getStore
argument_list|()
operator|.
name|getOntology
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
condition|)
block|{
name|OWLNamedIndividual
name|ruleind
init|=
name|newonto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
operator|.
name|getOWLNamedIndividual
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRecipeNew"
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|axiom
init|=
name|newonto
operator|.
name|getAxioms
argument_list|(
name|ruleind
argument_list|)
operator|.
name|size
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|8
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
comment|// TODO review the generated test code and remove the default call to fail.
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Some problem occurs with addRecipe of KReSAddRule"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Test of addRecipe method, of class AddRecipe.      */
annotation|@
name|Test
specifier|public
name|void
name|testAddRecipe_4args_1
parameter_list|()
block|{
comment|//        RuleStore store  = new RuleStoreImpl("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
name|OWLOntology
name|owl
init|=
name|store
operator|.
name|getOntology
argument_list|()
decl_stmt|;
name|String
name|ID
init|=
name|owl
operator|.
name|getOntologyID
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<"
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|">"
argument_list|,
literal|""
argument_list|)
operator|+
literal|"#"
decl_stmt|;
name|String
name|recipeName
init|=
literal|"MyRecipeNew"
decl_stmt|;
name|String
name|recipeDescription
init|=
literal|"My comment to the recipe"
decl_stmt|;
name|AddRecipe
name|instance
init|=
operator|new
name|AddRecipe
argument_list|(
name|store
argument_list|)
decl_stmt|;
name|boolean
name|result
init|=
name|instance
operator|.
name|addSimpleRecipe
argument_list|(
name|recipeName
argument_list|,
name|recipeDescription
argument_list|)
decl_stmt|;
name|OWLOntology
name|newonto
init|=
name|instance
operator|.
name|getStore
argument_list|()
operator|.
name|getOntology
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
condition|)
block|{
name|OWLNamedIndividual
name|ruleind
init|=
name|newonto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
operator|.
name|getOWLNamedIndividual
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRecipeNew"
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|axiom
init|=
name|newonto
operator|.
name|getAxioms
argument_list|(
name|ruleind
argument_list|)
operator|.
name|size
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
comment|// TODO review the generated test code and remove the default call to fail.
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Some problem occurs with addRecipe of KReSAddRule"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Test of addRecipe method, of class AddRecipe.      */
annotation|@
name|Test
specifier|public
name|void
name|testAddRecipe_3args_2
parameter_list|()
block|{
comment|//     RuleStore store  = new RuleStoreImpl("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
name|OWLOntology
name|owl
init|=
name|store
operator|.
name|getOntology
argument_list|()
decl_stmt|;
comment|//Load the example file
name|LoadRuleFile
name|load
init|=
operator|new
name|LoadRuleFile
argument_list|(
literal|"./src/main/resources/RuleOntology/TestRuleFileExample.txt"
argument_list|,
name|store
argument_list|)
decl_stmt|;
name|String
name|ID
init|=
name|owl
operator|.
name|getOntologyID
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<"
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|">"
argument_list|,
literal|""
argument_list|)
operator|+
literal|"#"
decl_stmt|;
name|IRI
name|recipeName
init|=
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRecipeNew"
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|IRI
argument_list|>
name|rules
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|rules
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleC"
argument_list|)
argument_list|)
expr_stmt|;
name|rules
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleB"
argument_list|)
argument_list|)
expr_stmt|;
name|rules
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleA"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|recipeDescription
init|=
literal|"My comment to the recipe"
decl_stmt|;
name|AddRecipe
name|instance
init|=
operator|new
name|AddRecipe
argument_list|(
name|load
operator|.
name|getStore
argument_list|()
argument_list|)
decl_stmt|;
name|boolean
name|result
init|=
name|instance
operator|.
name|addRecipe
argument_list|(
name|recipeName
argument_list|,
name|rules
argument_list|,
name|recipeDescription
argument_list|)
decl_stmt|;
name|OWLOntology
name|newonto
init|=
name|instance
operator|.
name|getStore
argument_list|()
operator|.
name|getOntology
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
condition|)
block|{
name|OWLNamedIndividual
name|ruleind
init|=
name|newonto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
operator|.
name|getOWLNamedIndividual
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRecipeNew"
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|axiom
init|=
name|newonto
operator|.
name|getAxioms
argument_list|(
name|ruleind
argument_list|)
operator|.
name|size
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|8
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
comment|// TODO review the generated test code and remove the default call to fail.
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Some problem occurs with addRecipe of KReSAddRule"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Test of addRecipeMap method, of class AddRecipe.      */
annotation|@
name|Test
specifier|public
name|void
name|testAddRecipeMap
parameter_list|()
block|{
comment|//        RuleStore store  = new RuleStoreImpl("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
name|OWLOntology
name|owl
init|=
name|store
operator|.
name|getOntology
argument_list|()
decl_stmt|;
comment|//Load the example file
name|LoadRuleFile
name|load
init|=
operator|new
name|LoadRuleFile
argument_list|(
literal|"./src/main/resources/RuleOntology/TestRuleFileExample.txt"
argument_list|,
name|store
argument_list|)
decl_stmt|;
name|String
name|ID
init|=
name|owl
operator|.
name|getOntologyID
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<"
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|">"
argument_list|,
literal|""
argument_list|)
operator|+
literal|"#"
decl_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|Vector
argument_list|<
name|IRI
argument_list|>
argument_list|>
name|recipeMap
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|recipeDescriptionMap
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|AddRecipe
name|instance
init|=
operator|new
name|AddRecipe
argument_list|(
name|load
operator|.
name|getStore
argument_list|()
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|IRI
argument_list|>
name|rules
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|rules
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleC"
argument_list|)
argument_list|)
expr_stmt|;
name|rules
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleB"
argument_list|)
argument_list|)
expr_stmt|;
name|rules
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleA"
argument_list|)
argument_list|)
expr_stmt|;
name|Vector
argument_list|<
name|IRI
argument_list|>
name|rules2
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|rules2
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleE"
argument_list|)
argument_list|)
expr_stmt|;
name|rules2
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleF"
argument_list|)
argument_list|)
expr_stmt|;
name|rules2
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleD"
argument_list|)
argument_list|)
expr_stmt|;
name|recipeMap
operator|.
name|put
argument_list|(
literal|"MyRecipeNEW1"
argument_list|,
name|rules
argument_list|)
expr_stmt|;
name|recipeMap
operator|.
name|put
argument_list|(
literal|"MyRecipeNEW2"
argument_list|,
name|rules2
argument_list|)
expr_stmt|;
name|recipeDescriptionMap
operator|.
name|put
argument_list|(
literal|"MyRecipeNEW1"
argument_list|,
literal|"My comment to the recipe new 1"
argument_list|)
expr_stmt|;
name|recipeDescriptionMap
operator|.
name|put
argument_list|(
literal|"MyRecipeNEW2"
argument_list|,
literal|"My comment to the recipe new 2"
argument_list|)
expr_stmt|;
name|boolean
name|result
init|=
name|instance
operator|.
name|addRecipeMap
argument_list|(
name|recipeMap
argument_list|,
name|recipeDescriptionMap
argument_list|)
decl_stmt|;
name|OWLOntology
name|newonto
init|=
name|instance
operator|.
name|getStore
argument_list|()
operator|.
name|getOntology
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
condition|)
block|{
name|Iterator
argument_list|<
name|String
argument_list|>
name|keys
init|=
name|recipeMap
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|int
name|axiom
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|keys
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OWLNamedIndividual
name|ruleind
init|=
name|newonto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
operator|.
name|getOWLNamedIndividual
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
name|keys
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|axiom
operator|=
name|axiom
operator|+
name|newonto
operator|.
name|getAxioms
argument_list|(
name|ruleind
argument_list|)
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
name|assertEquals
argument_list|(
literal|16
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
comment|// TODO review the generated test code and remove the default call to fail.
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Some problem occurs with addRecipeMap of KReSAddRule"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Test of addRecipeMapIRI method, of class AddRecipe.      */
annotation|@
name|Test
specifier|public
name|void
name|testAddRecipeMapIRI
parameter_list|()
block|{
comment|//        RuleStore store  = new RuleStoreImpl("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
name|OWLOntology
name|owl
init|=
name|store
operator|.
name|getOntology
argument_list|()
decl_stmt|;
comment|//Load the example file
name|LoadRuleFile
name|load
init|=
operator|new
name|LoadRuleFile
argument_list|(
literal|"./src/main/resources/RuleOntology/TestRuleFileExample.txt"
argument_list|,
name|store
argument_list|)
decl_stmt|;
name|String
name|ID
init|=
name|owl
operator|.
name|getOntologyID
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<"
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|">"
argument_list|,
literal|""
argument_list|)
operator|+
literal|"#"
decl_stmt|;
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|Vector
argument_list|<
name|IRI
argument_list|>
argument_list|>
name|recipeMap
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|String
argument_list|>
name|recipeDescriptionMap
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|AddRecipe
name|instance
init|=
operator|new
name|AddRecipe
argument_list|(
name|load
operator|.
name|getStore
argument_list|()
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|IRI
argument_list|>
name|rules
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|rules
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleC"
argument_list|)
argument_list|)
expr_stmt|;
name|rules
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleB"
argument_list|)
argument_list|)
expr_stmt|;
name|rules
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleA"
argument_list|)
argument_list|)
expr_stmt|;
name|Vector
argument_list|<
name|IRI
argument_list|>
name|rules2
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|rules2
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleE"
argument_list|)
argument_list|)
expr_stmt|;
name|rules2
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleF"
argument_list|)
argument_list|)
expr_stmt|;
name|rules2
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleD"
argument_list|)
argument_list|)
expr_stmt|;
name|recipeMap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRecipeNEW1"
argument_list|)
argument_list|,
name|rules
argument_list|)
expr_stmt|;
name|recipeMap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRecipeNEW2"
argument_list|)
argument_list|,
name|rules2
argument_list|)
expr_stmt|;
name|recipeDescriptionMap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRecipeNEW1"
argument_list|)
argument_list|,
literal|"My comment to the recipe new 1"
argument_list|)
expr_stmt|;
name|recipeDescriptionMap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRecipeNEW2"
argument_list|)
argument_list|,
literal|"My comment to the recipe new 2"
argument_list|)
expr_stmt|;
name|boolean
name|result
init|=
name|instance
operator|.
name|addRecipeMapIRI
argument_list|(
name|recipeMap
argument_list|,
name|recipeDescriptionMap
argument_list|)
decl_stmt|;
name|OWLOntology
name|newonto
init|=
name|instance
operator|.
name|getStore
argument_list|()
operator|.
name|getOntology
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
condition|)
block|{
name|Iterator
argument_list|<
name|IRI
argument_list|>
name|keys
init|=
name|recipeMap
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|int
name|axiom
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|keys
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OWLNamedIndividual
name|ruleind
init|=
name|newonto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
operator|.
name|getOWLNamedIndividual
argument_list|(
name|keys
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
name|axiom
operator|=
name|axiom
operator|+
name|newonto
operator|.
name|getAxioms
argument_list|(
name|ruleind
argument_list|)
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
name|assertEquals
argument_list|(
literal|16
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
comment|// TODO review the generated test code and remove the default call to fail.
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Some problem occurs with addRecipeMapIRI of KReSAddRule"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

