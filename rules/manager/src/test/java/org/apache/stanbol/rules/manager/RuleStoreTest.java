begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
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
name|assertNotNull
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
name|assertTrue
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
name|io
operator|.
name|File
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
name|util
operator|.
name|AutoIRIMapper
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

begin_comment
comment|/**  *   * @author elvio  */
end_comment

begin_class
specifier|public
class|class
name|RuleStoreTest
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
specifier|public
name|RuleStoreTest
parameter_list|()
block|{}
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setUpClass
parameter_list|()
throws|throws
name|Exception
block|{}
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|tearDownClass
parameter_list|()
throws|throws
name|Exception
block|{}
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
name|store
operator|=
operator|new
name|RuleStoreImpl
argument_list|(
name|configuration
argument_list|,
literal|"./src/main/resources/RuleOntology/TestKReSOntologyRules.owl"
argument_list|)
expr_stmt|;
name|blankStore
operator|=
operator|new
name|RuleStoreImpl
argument_list|(
name|configuration
argument_list|,
literal|""
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
name|blankStore
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|RuleStore
name|store
init|=
literal|null
decl_stmt|,
name|blankStore
init|=
literal|null
decl_stmt|;
annotation|@
name|Test
specifier|public
name|void
name|testKReSRuleStore
parameter_list|()
throws|throws
name|Exception
block|{
name|OWLOntology
name|owlmodel
init|=
name|store
operator|.
name|getOntology
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Path for default store config is "
operator|+
name|blankStore
operator|.
name|getFilePath
argument_list|()
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|owlmodel
argument_list|)
expr_stmt|;
name|OWLOntologyManager
name|owlmanager
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|owlmanager
operator|.
name|addIRIMapper
argument_list|(
operator|new
name|AutoIRIMapper
argument_list|(
operator|new
name|File
argument_list|(
literal|"./src/main/resources/RuleOntology/"
argument_list|)
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|src
init|=
literal|""
decl_stmt|;
try|try
block|{
name|src
operator|=
literal|"./src/main/resources/RuleOntology/TestKReSOntologyRules.owl"
expr_stmt|;
name|assertEquals
argument_list|(
name|owlmodel
argument_list|,
name|owlmanager
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
operator|new
name|File
argument_list|(
name|src
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
try|try
block|{
name|src
operator|=
literal|"./src/main/resources/RuleOntology/OffLineKReSOntologyRules.owl"
expr_stmt|;
name|assertEquals
argument_list|(
name|owlmodel
argument_list|,
name|owlmanager
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
operator|new
name|File
argument_list|(
name|src
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|ex
parameter_list|)
block|{
name|fail
argument_list|(
literal|"OWLOntologyCreationException caught when loading from "
operator|+
name|src
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testKReSRuleStore_2
parameter_list|()
throws|throws
name|Exception
block|{
name|OWLOntology
name|owlmodel
init|=
name|blankStore
operator|.
name|getOntology
argument_list|()
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Path for default store config is "
operator|+
name|blankStore
operator|.
name|getFilePath
argument_list|()
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|owlmodel
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|!
name|owlmodel
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

