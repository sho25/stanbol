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
name|AddRule
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
name|RemoveRule
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
name|OWLOntologyStorageException
import|;
end_import

begin_comment
comment|/**  *  * @author elvio  */
end_comment

begin_class
specifier|public
class|class
name|RemoveRuleTest
block|{
specifier|public
name|RemoveRuleTest
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
comment|/**      * Test of removeRule method, of class RemoveRule.      */
annotation|@
name|Test
specifier|public
name|void
name|testRemoveRule
parameter_list|()
block|{
comment|//        RuleStore store  = new RuleStoreImpl("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
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
name|AddRule
name|rule
init|=
operator|new
name|AddRule
argument_list|(
name|load
operator|.
name|getStore
argument_list|()
argument_list|)
decl_stmt|;
name|rule
operator|.
name|addRule
argument_list|(
literal|"MyRuleProva"
argument_list|,
literal|"Body -> Head"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|String
name|ruleName
init|=
literal|"MyRuleProva"
decl_stmt|;
name|RemoveRule
name|instance
init|=
operator|new
name|RemoveRule
argument_list|(
name|rule
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
name|removeRule
argument_list|(
name|ruleName
argument_list|)
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
name|result
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
comment|/**      * Test of removeRule method, of class RemoveRule.      */
annotation|@
name|Test
specifier|public
name|void
name|testRemoveSingleRule
parameter_list|()
throws|throws
name|OWLOntologyStorageException
block|{
comment|//        RuleStore store  = new RuleStoreImpl("./src/main/resources/RuleOntology/TestKReSOntologyRules.owl");
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
name|IRI
name|rule
init|=
name|IRI
operator|.
name|create
argument_list|(
name|owlID
operator|+
literal|"MyRuleB"
argument_list|)
decl_stmt|;
name|IRI
name|recipe
init|=
name|IRI
operator|.
name|create
argument_list|(
name|owlID
operator|+
literal|"MyRecipe"
argument_list|)
decl_stmt|;
name|RemoveRule
name|instance
init|=
operator|new
name|RemoveRule
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
name|removeRuleFromRecipe
argument_list|(
name|rule
argument_list|,
name|recipe
argument_list|)
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
name|result
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

