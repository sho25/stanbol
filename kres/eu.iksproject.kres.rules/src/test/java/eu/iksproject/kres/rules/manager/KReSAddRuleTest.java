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
name|KReSAddRuleTest
block|{
specifier|public
name|KReSAddRuleTest
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
comment|/**      * Test of addRule method, of class KReSAddRule.      */
annotation|@
name|Test
specifier|public
name|void
name|testAddRule_3args_1
parameter_list|()
block|{
comment|//        RuleStore store  = new KReSRuleStore("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
name|String
name|ruleName
init|=
literal|"MyRuleA"
decl_stmt|;
name|String
name|ruleBodyHead
init|=
literal|"MyRuleABody -> MyRuleAHead"
decl_stmt|;
name|String
name|ruleDescription
init|=
literal|"My comment to the rule A"
decl_stmt|;
name|KReSAddRule
name|instance
init|=
operator|new
name|KReSAddRule
argument_list|(
name|store
argument_list|)
decl_stmt|;
name|boolean
name|result
init|=
name|instance
operator|.
name|addRule
argument_list|(
name|ruleName
argument_list|,
name|ruleBodyHead
argument_list|,
name|ruleDescription
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
name|String
name|ID
init|=
name|newonto
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
literal|"MyRuleA"
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
literal|3
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
literal|"Some problem occurs with addRule of KReSAddRule"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Test of addRule method, of class KReSAddRule.      */
annotation|@
name|Test
specifier|public
name|void
name|testAddRule_3args_2
parameter_list|()
block|{
comment|//        RuleStore store  = new KReSRuleStore("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
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
name|IRI
name|ruleName
init|=
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleA"
argument_list|)
decl_stmt|;
name|String
name|ruleBodyHead
init|=
literal|"MyRuleABody -> MyRuleAHead"
decl_stmt|;
name|String
name|ruleDescription
init|=
literal|"My comment to the rule A"
decl_stmt|;
name|KReSAddRule
name|instance
init|=
operator|new
name|KReSAddRule
argument_list|(
name|store
argument_list|)
decl_stmt|;
name|boolean
name|result
init|=
name|instance
operator|.
name|addRule
argument_list|(
name|ruleName
argument_list|,
name|ruleBodyHead
argument_list|,
name|ruleDescription
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
name|ruleName
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
literal|3
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
literal|"Some problem occurs with addRule of KReSAddRule"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Test of addRuleMap method, of class KReSAddRule.      */
annotation|@
name|Test
specifier|public
name|void
name|testAddRuleMap
parameter_list|()
block|{
comment|//        RuleStore store  = new KReSRuleStore("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
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
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|ruleBodyHeadMap
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
name|ruleDescriptionMap
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|ruleBodyHeadMap
operator|.
name|put
argument_list|(
literal|"MyRuleA"
argument_list|,
literal|"MyRuleABody -> MyRuleAHead"
argument_list|)
expr_stmt|;
name|ruleBodyHeadMap
operator|.
name|put
argument_list|(
literal|"MyRuleB"
argument_list|,
literal|"MyRuleBBody -> MyRuleBHead"
argument_list|)
expr_stmt|;
name|ruleDescriptionMap
operator|.
name|put
argument_list|(
literal|"MyRuleA"
argument_list|,
literal|"My comment to the rule A"
argument_list|)
expr_stmt|;
name|ruleDescriptionMap
operator|.
name|put
argument_list|(
literal|"MyRuleB"
argument_list|,
literal|"My comment to the rule B"
argument_list|)
expr_stmt|;
name|KReSAddRule
name|instance
init|=
operator|new
name|KReSAddRule
argument_list|(
name|store
argument_list|)
decl_stmt|;
name|boolean
name|result
init|=
name|instance
operator|.
name|addRuleMap
argument_list|(
name|ruleBodyHeadMap
argument_list|,
name|ruleDescriptionMap
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
name|ruleBodyHeadMap
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
literal|6
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
literal|"Some problem occurs with addRuleMap of KReSAddRule"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Test of addRuleMapIRI method, of class KReSAddRule.      */
annotation|@
name|Test
specifier|public
name|void
name|testAddRuleMapIRI
parameter_list|()
block|{
comment|//        RuleStore store  = new KReSRuleStore("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
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
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|String
argument_list|>
name|ruleBodyHeadMap
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
name|ruleDescriptionMap
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|ruleBodyHeadMap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleA"
argument_list|)
argument_list|,
literal|"MyRuleABody -> MyRuleAHead"
argument_list|)
expr_stmt|;
name|ruleBodyHeadMap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleB"
argument_list|)
argument_list|,
literal|"MyRuleBBody -> MyRuleBHead"
argument_list|)
expr_stmt|;
name|ruleDescriptionMap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleA"
argument_list|)
argument_list|,
literal|"My comment to the rule A"
argument_list|)
expr_stmt|;
name|ruleDescriptionMap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleB"
argument_list|)
argument_list|,
literal|"My comment to the rule B"
argument_list|)
expr_stmt|;
name|KReSAddRule
name|instance
init|=
operator|new
name|KReSAddRule
argument_list|(
name|store
argument_list|)
decl_stmt|;
name|boolean
name|result
init|=
name|instance
operator|.
name|addRuleMapIRI
argument_list|(
name|ruleBodyHeadMap
argument_list|,
name|ruleDescriptionMap
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
name|ruleBodyHeadMap
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
literal|6
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
literal|"Some problem occurs with addRuleMap of KReSAddRule"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

