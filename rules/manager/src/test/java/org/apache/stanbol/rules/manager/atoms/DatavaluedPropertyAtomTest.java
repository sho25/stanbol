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
name|manager
operator|.
name|atoms
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
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
name|RuleAtom
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
name|OWLOntologyManager
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
name|vocabulary
operator|.
name|XSD
import|;
end_import

begin_class
specifier|public
class|class
name|DatavaluedPropertyAtomTest
extends|extends
name|AtomTest
block|{
specifier|private
name|IObjectAtom
name|datatypeProperty
decl_stmt|;
specifier|private
name|IObjectAtom
name|argument1
decl_stmt|;
comment|// argument2
specifier|private
name|RuleAtom
name|variable
decl_stmt|;
specifier|private
name|RuleAtom
name|literal
decl_stmt|;
specifier|private
name|RuleAtom
name|typedLiteral
decl_stmt|;
annotation|@
name|Before
specifier|public
name|void
name|setup
parameter_list|()
block|{
name|OWLOntologyManager
name|manager
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|this
operator|.
name|factory
operator|=
name|manager
operator|.
name|getOWLDataFactory
argument_list|()
expr_stmt|;
name|datatypeProperty
operator|=
operator|new
name|ResourceAtom
argument_list|(
name|URI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/resource#hasTest"
argument_list|)
argument_list|)
expr_stmt|;
name|argument1
operator|=
operator|new
name|VariableAtom
argument_list|(
name|URI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#x"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|variable
operator|=
operator|new
name|VariableAtom
argument_list|(
name|URI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#x"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|literal
operator|=
operator|new
name|StringAtom
argument_list|(
literal|"some text"
argument_list|)
expr_stmt|;
try|try
block|{
name|typedLiteral
operator|=
operator|new
name|TypedLiteralAtom
argument_list|(
operator|new
name|NumberAtom
argument_list|(
literal|"3.0"
argument_list|)
argument_list|,
operator|new
name|ResourceAtom
argument_list|(
operator|new
name|URI
argument_list|(
name|XSD
operator|.
name|xdouble
operator|.
name|getURI
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
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
name|testValidAtomWithVariableArguments
parameter_list|()
block|{
name|RuleAtom
name|ruleAtom
init|=
operator|new
name|DatavaluedPropertyAtom
argument_list|(
name|datatypeProperty
argument_list|,
name|argument1
argument_list|,
name|variable
argument_list|)
decl_stmt|;
name|execTest
argument_list|(
name|ruleAtom
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testValidAtomWithLiteralArguments
parameter_list|()
block|{
name|RuleAtom
name|ruleAtom
init|=
operator|new
name|DatavaluedPropertyAtom
argument_list|(
name|datatypeProperty
argument_list|,
name|argument1
argument_list|,
name|literal
argument_list|)
decl_stmt|;
name|execTest
argument_list|(
name|ruleAtom
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testValidAtomWithTypedLiteralArguments
parameter_list|()
block|{
name|RuleAtom
name|ruleAtom
init|=
operator|new
name|DatavaluedPropertyAtom
argument_list|(
name|datatypeProperty
argument_list|,
name|argument1
argument_list|,
name|typedLiteral
argument_list|)
decl_stmt|;
name|execTest
argument_list|(
name|ruleAtom
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

