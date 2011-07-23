begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

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
name|GetRule
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
name|OWLOntology
import|;
end_import

begin_comment
comment|/**  *  * @author elvio  */
end_comment

begin_class
specifier|public
class|class
name|GetRuleTest
block|{
specifier|public
name|GetRuleTest
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
name|configuration
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
comment|/**      * Test of getRule method, of class GetRule.      */
annotation|@
name|Test
specifier|public
name|void
name|testGetRule
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
name|owl
operator|=
name|load
operator|.
name|getStore
argument_list|()
operator|.
name|getOntology
argument_list|()
expr_stmt|;
name|GetRule
name|rule
init|=
operator|new
name|GetRule
argument_list|(
name|store
argument_list|)
decl_stmt|;
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|String
argument_list|>
name|map
init|=
name|rule
operator|.
name|getRule
argument_list|(
literal|"MyRuleC"
argument_list|)
decl_stmt|;
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|String
argument_list|>
name|expmap
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|expmap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleC"
argument_list|)
argument_list|,
literal|"MyRuleCBody -> MyRuleCHead"
argument_list|)
expr_stmt|;
if|if
condition|(
name|map
operator|!=
literal|null
condition|)
block|{
name|assertEquals
argument_list|(
name|expmap
argument_list|,
name|map
argument_list|)
expr_stmt|;
comment|// TODO review the generated test code and remove the default call to fail.
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Some error occurs for method GetRule of KReSGetRule"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Test of getAllRule method, of class GetRule.      */
annotation|@
name|Test
specifier|public
name|void
name|testGetAllRule
parameter_list|()
block|{
comment|//        RuleStoreImpl store  = new RuleStoreImpl("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
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
name|owl
operator|=
name|load
operator|.
name|getStore
argument_list|()
operator|.
name|getOntology
argument_list|()
expr_stmt|;
name|GetRule
name|rule
init|=
operator|new
name|GetRule
argument_list|(
name|store
argument_list|)
decl_stmt|;
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|String
argument_list|>
name|map
init|=
name|rule
operator|.
name|getAllRules
argument_list|()
decl_stmt|;
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|String
argument_list|>
name|expmap
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
comment|//MyRuleX
name|String
name|rulex
init|=
literal|"PREFIX var http://kres.iksproject.eu/rules# ."
operator|+
literal|"PREFIX dbs http://andriry.altervista.org/tesiSpecialistica/dbs_l1.owl# ."
operator|+
literal|"PREFIX lmm http://www.ontologydesignpatterns.org/ont/lmm/LMM_L1.owl# ."
operator|+
literal|"rule1[dbs:Table(?x) -> lmm:Meaning(?x)]"
decl_stmt|;
name|expmap
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
name|expmap
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
name|expmap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleC"
argument_list|)
argument_list|,
literal|"MyRuleCBody -> MyRuleCHead"
argument_list|)
expr_stmt|;
name|expmap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleD"
argument_list|)
argument_list|,
literal|"MyRuleDBody -> MyRuleDHead"
argument_list|)
expr_stmt|;
name|expmap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleE"
argument_list|)
argument_list|,
literal|"MyRuleEBody -> MyRuleEHead"
argument_list|)
expr_stmt|;
name|expmap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleF"
argument_list|)
argument_list|,
literal|"MyRuleFBody -> MyRuleFHead"
argument_list|)
expr_stmt|;
name|expmap
operator|.
name|put
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ID
operator|+
literal|"MyRuleX"
argument_list|)
argument_list|,
name|rulex
argument_list|)
expr_stmt|;
if|if
condition|(
name|map
operator|!=
literal|null
condition|)
block|{
name|Iterator
argument_list|<
name|IRI
argument_list|>
name|key
init|=
name|map
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|int
name|m
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|key
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|IRI
name|k
init|=
name|key
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|expmap
operator|.
name|keySet
argument_list|()
operator|.
name|contains
argument_list|(
name|k
argument_list|)
condition|)
if|if
condition|(
name|expmap
operator|.
name|get
argument_list|(
name|k
argument_list|)
operator|.
name|equals
argument_list|(
name|map
operator|.
name|get
argument_list|(
name|k
argument_list|)
argument_list|)
condition|)
name|m
operator|++
expr_stmt|;
block|}
name|assertEquals
argument_list|(
name|expmap
operator|.
name|size
argument_list|()
argument_list|,
name|m
argument_list|)
expr_stmt|;
comment|// TODO review the generated test code and remove the default call to fail.
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Some error occurs for method GetAllRule of KReSGetRule"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Test of getRule method, of class GetRule.      */
annotation|@
name|Test
specifier|public
name|void
name|testGetRuleUsage
parameter_list|()
block|{
comment|//        RuleStoreImpl store  = new RuleStoreImpl("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
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
name|owl
operator|=
name|load
operator|.
name|getStore
argument_list|()
operator|.
name|getOntology
argument_list|()
expr_stmt|;
name|GetRule
name|rule
init|=
operator|new
name|GetRule
argument_list|(
name|store
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|IRI
argument_list|>
name|vector
init|=
name|rule
operator|.
name|getRuleUsage
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
decl_stmt|;
if|if
condition|(
name|vector
operator|!=
literal|null
condition|)
block|{
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|vector
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
comment|// TODO review the generated test code and remove the default call to fail.
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Some error occurs for method getRuleUsage of KReSGetRule"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetRulesOfRecipe
parameter_list|()
block|{
comment|//        RuleStoreImpl store  = new RuleStoreImpl("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
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
name|owl
operator|=
name|load
operator|.
name|getStore
argument_list|()
operator|.
name|getOntology
argument_list|()
expr_stmt|;
name|GetRule
name|rule
init|=
operator|new
name|GetRule
argument_list|(
name|store
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|IRI
argument_list|>
name|vector
init|=
name|rule
operator|.
name|getRuleUsage
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
decl_stmt|;
if|if
condition|(
name|vector
operator|!=
literal|null
condition|)
block|{
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|vector
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
comment|// TODO review the generated test code and remove the default call to fail.
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Some error occurs for method getRuleUsage of KReSGetRule"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

