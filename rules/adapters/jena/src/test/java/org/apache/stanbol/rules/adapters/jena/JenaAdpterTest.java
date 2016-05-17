begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
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
name|adapters
operator|.
name|jena
package|;
end_package

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
name|sparql
operator|.
name|query
operator|.
name|ConstructQuery
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
name|RuleAdapter
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
name|RuleAtomCallExeption
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
name|UnavailableRuleObjectException
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
name|UnsupportedTypeForExportException
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
name|KB
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

begin_comment
comment|/**  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|JenaAdpterTest
block|{
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|JenaAdpterTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Recipe
name|recipeGood
decl_stmt|;
specifier|private
name|Recipe
name|recipeWrong
decl_stmt|;
specifier|private
specifier|static
name|RuleAdapter
name|ruleAdapter
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setUpClass
parameter_list|()
block|{
name|ruleAdapter
operator|=
operator|new
name|JenaAdapter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|tearDownClass
parameter_list|()
block|{
name|ruleAdapter
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
block|{
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
name|recipeString
init|=
literal|"kres =<http://kres.iks-project.eu/ontology.owl#> . "
operator|+
name|separator
operator|+
literal|"foaf =<http://xmlns.com/foaf/0.1/> . "
operator|+
name|separator
operator|+
literal|"rule1[ is(kres:Person, ?x) . has(kres:friend, ?x, ?y) -> is(foaf:Person, ?x) . has(foaf:knows, ?x, ?y) . is(foaf:Person, ?y)] . "
operator|+
literal|"rule2[ is(kres:Person, ?x) . values(kres:age, ?x, ?age) . endsWith(?t, \"string\") . gt(?age, sum(sub(70, ?k), ?z)) -> is(kres:OldPerson, ?x)]"
decl_stmt|;
name|KB
name|kb
init|=
name|RuleParserImpl
operator|.
name|parse
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/adapters/jena/test/"
argument_list|,
name|recipeString
argument_list|)
decl_stmt|;
name|recipeGood
operator|=
operator|new
name|RecipeImpl
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/adapters/jena/test"
argument_list|)
argument_list|,
literal|"A recipe."
argument_list|,
name|kb
operator|.
name|getRuleList
argument_list|()
argument_list|)
expr_stmt|;
name|recipeString
operator|=
literal|"kres =<http://kres.iks-project.eu/ontology.owl#> . "
operator|+
name|separator
operator|+
literal|"foaf =<http://xmlns.com/foaf/0.1/> . "
operator|+
name|separator
operator|+
literal|"rule1[ is(kres:Person, ?x) . has(kres:friend, ?x, ?y) -> is(foaf:Person, ?x) . has(foaf:knows, ?x, ?y) . is(foaf:Person, ?y)] . "
operator|+
literal|"rule2[ is(kres:Person, ?x) . same(\"Andrea\", localname(?x)) -> is(kres:OldPerson, ?x)]"
expr_stmt|;
name|kb
operator|=
name|RuleParserImpl
operator|.
name|parse
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/adapters/jena/test/"
argument_list|,
name|recipeString
argument_list|)
expr_stmt|;
name|recipeWrong
operator|=
operator|new
name|RecipeImpl
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.com/stanbol/rules/adapters/jena/test"
argument_list|)
argument_list|,
literal|"A recipe."
argument_list|,
name|kb
operator|.
name|getRuleList
argument_list|()
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
name|recipeGood
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Test
specifier|public
name|void
name|test
parameter_list|()
block|{
try|try
block|{
name|List
argument_list|<
name|Rule
argument_list|>
name|rules
init|=
operator|(
name|List
argument_list|<
name|Rule
argument_list|>
operator|)
name|ruleAdapter
operator|.
name|adaptTo
argument_list|(
name|recipeGood
argument_list|,
name|Rule
operator|.
name|class
argument_list|)
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|Rule
name|rule
range|:
name|rules
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|rule
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Assert
operator|.
name|assertNotSame
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnavailableRuleObjectException
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|fail
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedTypeForExportException
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|fail
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuleAtomCallExeption
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|fail
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Test
specifier|public
name|void
name|wrongAdaptabeClassTest
parameter_list|()
block|{
try|try
block|{
name|List
argument_list|<
name|ConstructQuery
argument_list|>
name|constructQueries
init|=
operator|(
name|List
argument_list|<
name|ConstructQuery
argument_list|>
operator|)
name|ruleAdapter
operator|.
name|adaptTo
argument_list|(
name|recipeGood
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
decl_stmt|;
for|for
control|(
name|ConstructQuery
name|constructQuery
range|:
name|constructQueries
control|)
block|{
name|log
operator|.
name|debug
argument_list|(
name|constructQuery
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|(
literal|"The adapter for Jena should not accept "
operator|+
name|ConstructQuery
operator|.
name|class
operator|.
name|getCanonicalName
argument_list|()
operator|+
literal|" objects."
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|UnavailableRuleObjectException
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|fail
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedTypeForExportException
name|e
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuleAtomCallExeption
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|fail
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Test
specifier|public
name|void
name|unavailableRuleObjectTest
parameter_list|()
block|{
try|try
block|{
name|List
argument_list|<
name|Rule
argument_list|>
name|rules
init|=
operator|(
name|List
argument_list|<
name|Rule
argument_list|>
operator|)
name|ruleAdapter
operator|.
name|adaptTo
argument_list|(
name|recipeWrong
argument_list|,
name|Rule
operator|.
name|class
argument_list|)
decl_stmt|;
for|for
control|(
name|Rule
name|rule
range|:
name|rules
control|)
block|{
name|log
operator|.
name|debug
argument_list|(
name|rule
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|UnavailableRuleObjectException
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|fail
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedTypeForExportException
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|fail
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuleAtomCallExeption
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

