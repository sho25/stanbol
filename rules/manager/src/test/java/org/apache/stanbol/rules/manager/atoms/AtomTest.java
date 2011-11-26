begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
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
name|model
operator|.
name|SWRLAtom
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|AtomTest
block|{
specifier|protected
name|OWLDataFactory
name|factory
decl_stmt|;
annotation|@
name|Before
specifier|public
name|void
name|init
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
block|}
annotation|@
name|Test
specifier|public
specifier|abstract
name|void
name|testValidAtomWithVariableArguments
parameter_list|()
function_decl|;
annotation|@
name|Test
specifier|public
specifier|abstract
name|void
name|testValidAtomWithLiteralArguments
parameter_list|()
function_decl|;
annotation|@
name|Test
specifier|public
specifier|abstract
name|void
name|testValidAtomWithTypedLiteralArguments
parameter_list|()
function_decl|;
specifier|protected
name|void
name|execTest
parameter_list|(
name|RuleAtom
name|ruleAtom
parameter_list|)
block|{
name|String
name|stanbolSyntax
init|=
name|ruleAtom
operator|.
name|toKReSSyntax
argument_list|()
decl_stmt|;
if|if
condition|(
name|stanbolSyntax
operator|==
literal|null
condition|)
block|{
name|fail
argument_list|(
name|GreaterThanAtom
operator|.
name|class
operator|.
name|getCanonicalName
argument_list|()
operator|+
literal|" does not produce any rule in Stanbo syntax."
argument_list|)
expr_stmt|;
block|}
name|String
name|sparql
init|=
name|ruleAtom
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|sparql
operator|==
literal|null
condition|)
block|{
name|fail
argument_list|(
name|GreaterThanAtom
operator|.
name|class
operator|.
name|getCanonicalName
argument_list|()
operator|+
literal|" does not produce any rule as SPARQL CONSTRUCT."
argument_list|)
expr_stmt|;
block|}
name|SWRLAtom
name|swrlAtom
init|=
name|ruleAtom
operator|.
name|toSWRL
argument_list|(
name|factory
argument_list|)
decl_stmt|;
if|if
condition|(
name|swrlAtom
operator|==
literal|null
condition|)
block|{
name|fail
argument_list|(
name|GreaterThanAtom
operator|.
name|class
operator|.
name|getCanonicalName
argument_list|()
operator|+
literal|" does not produce any rule in SWRL."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

