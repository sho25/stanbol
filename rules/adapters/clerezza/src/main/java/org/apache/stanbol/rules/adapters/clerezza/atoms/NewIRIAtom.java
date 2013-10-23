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
name|clerezza
operator|.
name|atoms
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|rdf
operator|.
name|core
operator|.
name|sparql
operator|.
name|query
operator|.
name|BuiltInCall
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
name|Expression
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
name|adapters
operator|.
name|AbstractAdaptableAtom
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
name|adapters
operator|.
name|clerezza
operator|.
name|ClerezzaSparqlObject
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
name|atoms
operator|.
name|IObjectAtom
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
name|atoms
operator|.
name|StringFunctionAtom
import|;
end_import

begin_comment
comment|/**  * It adapts any NewIRIAtom to the BIND built in call in Clerezza for creating new UriRef resources binding  * the value to a variable.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|NewIRIAtom
extends|extends
name|AbstractAdaptableAtom
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|public
parameter_list|<
name|T
parameter_list|>
name|T
name|adapt
parameter_list|(
name|RuleAtom
name|ruleAtom
parameter_list|)
throws|throws
name|RuleAtomCallExeption
throws|,
name|UnavailableRuleObjectException
throws|,
name|UnsupportedTypeForExportException
block|{
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
operator|.
name|NewIRIAtom
name|tmp
init|=
operator|(
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
operator|.
name|NewIRIAtom
operator|)
name|ruleAtom
decl_stmt|;
name|StringFunctionAtom
name|bindingAtom
init|=
name|tmp
operator|.
name|getBinding
argument_list|()
decl_stmt|;
name|IObjectAtom
name|variableAtom
init|=
name|tmp
operator|.
name|getNewNodeVariable
argument_list|()
decl_stmt|;
name|ClerezzaSparqlObject
name|binding
init|=
operator|(
name|ClerezzaSparqlObject
operator|)
name|adapter
operator|.
name|adaptTo
argument_list|(
name|bindingAtom
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
decl_stmt|;
name|ClerezzaSparqlObject
name|variable
init|=
operator|(
name|ClerezzaSparqlObject
operator|)
name|adapter
operator|.
name|adaptTo
argument_list|(
name|variableAtom
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Expression
argument_list|>
name|iriArgumentExpressions
init|=
operator|new
name|ArrayList
argument_list|<
name|Expression
argument_list|>
argument_list|()
decl_stmt|;
name|iriArgumentExpressions
operator|.
name|add
argument_list|(
operator|(
name|Expression
operator|)
name|binding
operator|.
name|getClerezzaObject
argument_list|()
argument_list|)
expr_stmt|;
name|BuiltInCall
name|iriBuiltInCall
init|=
operator|new
name|BuiltInCall
argument_list|(
literal|"IRI"
argument_list|,
name|iriArgumentExpressions
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Expression
argument_list|>
name|bindArgumentExpressions
init|=
operator|new
name|ArrayList
argument_list|<
name|Expression
argument_list|>
argument_list|()
decl_stmt|;
name|bindArgumentExpressions
operator|.
name|add
argument_list|(
name|iriBuiltInCall
argument_list|)
expr_stmt|;
name|bindArgumentExpressions
operator|.
name|add
argument_list|(
operator|(
name|Expression
operator|)
name|variable
operator|.
name|getClerezzaObject
argument_list|()
argument_list|)
expr_stmt|;
name|BuiltInCall
name|bindBuiltInCall
init|=
operator|new
name|BuiltInCall
argument_list|(
literal|"BIND"
argument_list|,
name|bindArgumentExpressions
argument_list|)
decl_stmt|;
return|return
operator|(
name|T
operator|)
operator|new
name|ClerezzaSparqlObject
argument_list|(
name|bindBuiltInCall
argument_list|)
return|;
block|}
block|}
end_class

end_unit

