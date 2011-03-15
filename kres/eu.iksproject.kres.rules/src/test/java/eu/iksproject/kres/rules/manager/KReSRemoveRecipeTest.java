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
name|Hashtable
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

begin_comment
comment|/**  *  * @author elvio  */
end_comment

begin_class
specifier|public
class|class
name|KReSRemoveRecipeTest
block|{
specifier|public
name|KReSRemoveRecipeTest
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
name|ONManager
argument_list|(
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
name|KReSRuleStore
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
name|KReSONManager
name|onm
init|=
literal|null
decl_stmt|;
comment|/**      * Test of removeRule method, of class KReSRemoveRecipe.      */
annotation|@
name|Test
specifier|public
name|void
name|testRemoveRule_String
parameter_list|()
throws|throws
name|OWLOntologyCreationException
block|{
comment|//        RuleStore store  = new KReSRuleStore("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
name|String
name|owlID
init|=
name|store
operator|.
name|getOntology
argument_list|()
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
comment|//Load the example file
name|KReSLoadRuleFile
name|load
init|=
operator|new
name|KReSLoadRuleFile
argument_list|(
literal|"./src/main/resources/RuleOntology/TestRuleFileExample.txt"
argument_list|,
name|store
argument_list|)
decl_stmt|;
name|OWLOntology
name|owlstart
init|=
name|load
operator|.
name|getStore
argument_list|()
operator|.
name|getOntology
argument_list|()
decl_stmt|;
name|String
name|recipeName
init|=
name|owlID
operator|+
literal|"MyRecipe"
decl_stmt|;
name|KReSRemoveRecipe
name|instance
init|=
operator|new
name|KReSRemoveRecipe
argument_list|(
name|load
operator|.
name|getStore
argument_list|()
argument_list|)
decl_stmt|;
name|boolean
name|expResult
init|=
literal|true
decl_stmt|;
name|boolean
name|result
init|=
name|instance
operator|.
name|removeRecipe
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|recipeName
argument_list|)
argument_list|)
decl_stmt|;
name|OWLOntology
name|owlend
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
name|assertEquals
argument_list|(
name|expResult
argument_list|,
operator|(
name|owlstart
operator|.
name|getAxiomCount
argument_list|()
operator|>
name|owlend
operator|.
name|getAxiomCount
argument_list|()
operator|)
argument_list|)
expr_stmt|;
comment|// TODO review the generated test code and remove the default call to fail.
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Some errors occur with removeRule of KReSRemoveRule."
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Test of removeRule method, of class KReSRemoveRecipe.      */
annotation|@
name|Test
specifier|public
name|void
name|testRemoveRuleName_String
parameter_list|()
throws|throws
name|OWLOntologyCreationException
block|{
comment|//        RuleStore store  = new KReSRuleStore("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
comment|//Load the example file
name|KReSLoadRuleFile
name|load
init|=
operator|new
name|KReSLoadRuleFile
argument_list|(
literal|"./src/main/resources/RuleOntology/TestRuleFileExample.txt"
argument_list|,
name|store
argument_list|)
decl_stmt|;
name|OWLOntology
name|owlstart
init|=
name|load
operator|.
name|getStore
argument_list|()
operator|.
name|getOntology
argument_list|()
decl_stmt|;
name|String
name|recipeName
init|=
literal|"MyRecipe"
decl_stmt|;
name|KReSRemoveRecipe
name|instance
init|=
operator|new
name|KReSRemoveRecipe
argument_list|(
name|load
operator|.
name|getStore
argument_list|()
argument_list|)
decl_stmt|;
name|boolean
name|expResult
init|=
literal|true
decl_stmt|;
name|boolean
name|result
init|=
name|instance
operator|.
name|removeRecipe
argument_list|(
name|recipeName
argument_list|)
decl_stmt|;
name|OWLOntology
name|owlend
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
name|assertEquals
argument_list|(
name|expResult
argument_list|,
operator|(
name|owlstart
operator|.
name|getAxiomCount
argument_list|()
operator|>
name|owlend
operator|.
name|getAxiomCount
argument_list|()
operator|)
argument_list|)
expr_stmt|;
comment|// TODO review the generated test code and remove the default call to fail.
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Some errors occur with removeRule of KReSRemoveRule."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

