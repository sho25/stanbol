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
name|List
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|NoSuchRuleInRecipeException
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
name|Assert
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
comment|/**  * Set of tests for the validation of the features provided by the RuleStore.  *   * @author anuzzolese  */
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
block|{
comment|/*class SpecialTcManager extends TcManager {             public SpecialTcManager(QueryEngine qe, WeightedTcProvider wtcp) {                 super();                 bindQueryEngine(qe);                 bindWeightedTcProvider(wtcp);             }         }          QueryEngine qe = new JenaSparqlEngine();         WeightedTcProvider wtcp = new SimpleTcProvider();*/
name|TcManager
name|tcm
init|=
name|TcManager
operator|.
name|getInstance
argument_list|()
decl_stmt|;
comment|//new SpecialTcManager(qe, wtcp);
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
name|ClerezzaRuleStore
argument_list|(
name|configuration
argument_list|,
name|tcm
argument_list|)
expr_stmt|;
block|}
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|tearDownClass
parameter_list|()
throws|throws
name|Exception
block|{
name|store
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|setUp
parameter_list|()
block|{      }
annotation|@
name|After
specifier|public
name|void
name|tearDown
parameter_list|()
block|{      }
specifier|public
specifier|static
name|RuleStore
name|store
init|=
literal|null
decl_stmt|;
comment|/**      * Calls all the other (now private) test methods to ensure the correct      * order of execution      * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testRuleStore
parameter_list|()
throws|throws
name|Exception
block|{
name|createRecipeTest
argument_list|()
expr_stmt|;
name|addRuleToRecipeTest
argument_list|()
expr_stmt|;
name|getRecipeTest
argument_list|()
expr_stmt|;
name|getNotExistingRuleByNameInRecipeTest
argument_list|()
expr_stmt|;
name|getNotExistingRuleByIdInRecipeTest
argument_list|()
expr_stmt|;
name|getExistingRuleByIdInRecipeTest
argument_list|()
expr_stmt|;
name|getExistingRuleByNameInRecipeTest
argument_list|()
expr_stmt|;
name|findRecipesByDescriptionTest
argument_list|()
expr_stmt|;
name|findRulesByDescriptionTest
argument_list|()
expr_stmt|;
name|findRulesByNameTest
argument_list|()
expr_stmt|;
name|removeRuleInRecipeTest
argument_list|()
expr_stmt|;
name|removeRecipeTest
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|createRecipeTest
parameter_list|()
throws|throws
name|Exception
block|{
name|Recipe
name|recipe
init|=
name|store
operator|.
name|createRecipe
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/test/recipeA"
argument_list|)
argument_list|,
literal|"The text recipe named A."
argument_list|)
decl_stmt|;
if|if
condition|(
name|recipe
operator|==
literal|null
condition|)
block|{
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Created recipe with ID "
operator|+
name|recipe
operator|.
name|getRecipeID
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|addRuleToRecipeTest
parameter_list|()
throws|throws
name|Exception
block|{
name|Recipe
name|recipe
init|=
name|store
operator|.
name|getRecipe
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/test/recipeA"
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|separator
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
decl_stmt|;
name|String
name|rule
init|=
literal|"rule1["
operator|+
name|separator
operator|+
literal|"	is(<http://dbpedia.org/ontology/Person>, ?x) . "
operator|+
name|separator
operator|+
literal|"	has(<http://dbpedia.org/ontology/playsInTeam>, ?x, ?y) . "
operator|+
name|separator
operator|+
literal|"	is (<http://dbpedia.org/ontology/FootballTeam>, ?y) "
operator|+
name|separator
operator|+
literal|"		-> "
operator|+
name|separator
operator|+
literal|"	is(<http://dbpedia.org/ontology/FootballPlayer>, ?x)"
operator|+
name|separator
operator|+
literal|"] . "
operator|+
literal|"rule2["
operator|+
name|separator
operator|+
literal|"	is(<http://dbpedia.org/ontology/Organisation>, ?x) . "
operator|+
name|separator
operator|+
literal|"	has(<http://dbpedia.org/ontology/hasProduct>, ?x, ?y)"
operator|+
name|separator
operator|+
literal|"		-> "
operator|+
name|separator
operator|+
literal|"	is(<http://dbpedia.org/ontology/Company>, ?x)"
operator|+
name|separator
operator|+
literal|"]"
decl_stmt|;
name|store
operator|.
name|addRulesToRecipe
argument_list|(
name|recipe
argument_list|,
name|rule
argument_list|,
literal|"This is a test rule."
argument_list|)
expr_stmt|;
if|if
condition|(
name|recipe
operator|==
literal|null
condition|)
block|{
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Got recipe with ID "
operator|+
name|recipe
operator|.
name|getRecipeID
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|getRecipeTest
parameter_list|()
throws|throws
name|Exception
block|{
name|Recipe
name|recipe
init|=
name|store
operator|.
name|getRecipe
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/test/recipeA"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|recipe
operator|==
literal|null
condition|)
block|{
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Recipe: "
operator|+
name|recipe
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Got recipe with ID "
operator|+
name|recipe
operator|.
name|getRecipeID
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|getNotExistingRuleByNameInRecipeTest
parameter_list|()
throws|throws
name|Exception
block|{
name|Recipe
name|recipe
init|=
name|store
operator|.
name|getRecipe
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/test/recipeA"
argument_list|)
argument_list|)
decl_stmt|;
try|try
block|{
name|recipe
operator|.
name|getRule
argument_list|(
literal|"ruleX"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchRuleInRecipeException
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|getNotExistingRuleByIdInRecipeTest
parameter_list|()
throws|throws
name|Exception
block|{
name|Recipe
name|recipe
init|=
name|store
operator|.
name|getRecipe
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/test/recipeA"
argument_list|)
argument_list|)
decl_stmt|;
try|try
block|{
name|recipe
operator|.
name|getRule
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://foo.org/ruleX"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchRuleInRecipeException
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|getExistingRuleByIdInRecipeTest
parameter_list|()
throws|throws
name|Exception
block|{
name|Recipe
name|recipe
init|=
name|store
operator|.
name|getRecipe
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/test/recipeA"
argument_list|)
argument_list|)
decl_stmt|;
try|try
block|{
name|Rule
name|rule
init|=
name|recipe
operator|.
name|getRule
argument_list|(
name|recipe
operator|.
name|listRuleIDs
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|rule
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchRuleInRecipeException
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|getExistingRuleByNameInRecipeTest
parameter_list|()
throws|throws
name|Exception
block|{
name|Recipe
name|recipe
init|=
name|store
operator|.
name|getRecipe
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/test/recipeA"
argument_list|)
argument_list|)
decl_stmt|;
try|try
block|{
name|Rule
name|rule
init|=
name|recipe
operator|.
name|getRule
argument_list|(
name|recipe
operator|.
name|listRuleNames
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|rule
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchRuleInRecipeException
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|findRecipesByDescriptionTest
parameter_list|()
throws|throws
name|Exception
block|{
name|RecipeList
name|recipes
init|=
name|store
operator|.
name|findRecipesByDescription
argument_list|(
literal|"recipe named A"
argument_list|)
decl_stmt|;
if|if
condition|(
name|recipes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|findRulesByDescriptionTest
parameter_list|()
throws|throws
name|Exception
block|{
name|RuleList
name|rules
init|=
name|store
operator|.
name|findRulesByDescription
argument_list|(
literal|"a test rule."
argument_list|)
decl_stmt|;
if|if
condition|(
name|rules
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|findRulesByNameTest
parameter_list|()
throws|throws
name|Exception
block|{
name|RuleList
name|rules
init|=
name|store
operator|.
name|findRulesByName
argument_list|(
literal|"1"
argument_list|)
decl_stmt|;
if|if
condition|(
name|rules
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|removeRuleInRecipeTest
parameter_list|()
throws|throws
name|Exception
block|{
name|Recipe
name|recipe
init|=
name|store
operator|.
name|getRecipe
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/test/recipeA"
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|tmp
init|=
name|recipe
operator|.
name|toString
argument_list|()
decl_stmt|;
name|Rule
name|rule
init|=
name|recipe
operator|.
name|getRule
argument_list|(
name|recipe
operator|.
name|listRuleNames
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
name|store
operator|.
name|removeRule
argument_list|(
name|recipe
argument_list|,
name|rule
argument_list|)
expr_stmt|;
name|Recipe
name|recipe2
init|=
name|store
operator|.
name|getRecipe
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/test/recipeA"
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|tmp2
init|=
name|recipe2
operator|.
name|toString
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertNotSame
argument_list|(
name|tmp
argument_list|,
name|tmp2
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|removeRecipeTest
parameter_list|()
throws|throws
name|Exception
block|{
name|RecipeList
name|recipeListInitial
init|=
name|store
operator|.
name|listRecipes
argument_list|()
decl_stmt|;
name|Recipe
index|[]
name|initialRecipes
init|=
operator|new
name|Recipe
index|[
name|recipeListInitial
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|initialRecipes
operator|=
name|recipeListInitial
operator|.
name|toArray
argument_list|(
name|initialRecipes
argument_list|)
expr_stmt|;
name|Recipe
name|recipe
init|=
name|store
operator|.
name|getRecipe
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/test/recipeA"
argument_list|)
argument_list|)
decl_stmt|;
name|store
operator|.
name|removeRecipe
argument_list|(
name|recipe
argument_list|)
expr_stmt|;
name|RecipeList
name|recipeListFinal
init|=
name|store
operator|.
name|listRecipes
argument_list|()
decl_stmt|;
name|Recipe
index|[]
name|finalRecipes
init|=
operator|new
name|Recipe
index|[
name|recipeListInitial
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|finalRecipes
operator|=
name|recipeListFinal
operator|.
name|toArray
argument_list|(
name|finalRecipes
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotSame
argument_list|(
name|initialRecipes
argument_list|,
name|finalRecipes
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

