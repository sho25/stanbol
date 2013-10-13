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
name|jena
operator|.
name|HigherOrderClauseEntry
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
name|jena
operator|.
name|NodeClauseEntry
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
name|ExpressionAtom
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
name|graph
operator|.
name|Node
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
name|BuiltinRegistry
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
name|ClauseEntry
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
name|Functor
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
comment|/**  *   * It adapts any DifferentAtom to a notEqual functor in Jena.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|DifferentAtom
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
name|Node
name|arg1Node
init|=
literal|null
decl_stmt|;
name|Node
name|arg2Node
init|=
literal|null
decl_stmt|;
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
name|DifferentAtom
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
name|DifferentAtom
operator|)
name|ruleAtom
decl_stmt|;
name|ExpressionAtom
name|argument1
init|=
name|tmp
operator|.
name|getStringFunctionAtom1
argument_list|()
decl_stmt|;
name|ExpressionAtom
name|argument2
init|=
name|tmp
operator|.
name|getStringFunctionAtom2
argument_list|()
decl_stmt|;
name|ClauseEntry
name|clauseEntry1
init|=
name|adapter
operator|.
name|adaptTo
argument_list|(
name|argument1
argument_list|,
name|Rule
operator|.
name|class
argument_list|)
decl_stmt|;
name|ClauseEntry
name|clauseEntry2
init|=
name|adapter
operator|.
name|adaptTo
argument_list|(
name|argument2
argument_list|,
name|Rule
operator|.
name|class
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|ClauseEntry
argument_list|>
name|clauseEntries
init|=
operator|new
name|ArrayList
argument_list|<
name|ClauseEntry
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|clauseEntry1
operator|instanceof
name|HigherOrderClauseEntry
condition|)
block|{
name|arg1Node
operator|=
operator|(
operator|(
name|HigherOrderClauseEntry
operator|)
name|clauseEntry1
operator|)
operator|.
name|getBindableNode
argument_list|()
expr_stmt|;
name|clauseEntries
operator|.
name|addAll
argument_list|(
operator|(
operator|(
name|HigherOrderClauseEntry
operator|)
name|clauseEntry1
operator|)
operator|.
name|getClauseEntries
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|clauseEntry1
operator|instanceof
name|NodeClauseEntry
condition|)
block|{
name|arg1Node
operator|=
operator|(
operator|(
name|NodeClauseEntry
operator|)
name|clauseEntry1
operator|)
operator|.
name|getNode
argument_list|()
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
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
argument_list|(
name|getClass
argument_list|()
argument_list|)
throw|;
block|}
if|if
condition|(
name|clauseEntry2
operator|instanceof
name|HigherOrderClauseEntry
condition|)
block|{
name|arg2Node
operator|=
operator|(
operator|(
name|HigherOrderClauseEntry
operator|)
name|clauseEntry2
operator|)
operator|.
name|getBindableNode
argument_list|()
expr_stmt|;
name|clauseEntries
operator|.
name|addAll
argument_list|(
operator|(
operator|(
name|HigherOrderClauseEntry
operator|)
name|clauseEntry2
operator|)
operator|.
name|getClauseEntries
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|clauseEntry2
operator|instanceof
name|NodeClauseEntry
condition|)
block|{
name|arg2Node
operator|=
operator|(
operator|(
name|NodeClauseEntry
operator|)
name|clauseEntry2
operator|)
operator|.
name|getNode
argument_list|()
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
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
argument_list|(
name|getClass
argument_list|()
argument_list|)
throw|;
block|}
name|java
operator|.
name|util
operator|.
name|List
argument_list|<
name|Node
argument_list|>
name|nodes
init|=
operator|new
name|ArrayList
argument_list|<
name|Node
argument_list|>
argument_list|()
decl_stmt|;
name|nodes
operator|.
name|add
argument_list|(
name|arg1Node
argument_list|)
expr_stmt|;
name|nodes
operator|.
name|add
argument_list|(
name|arg2Node
argument_list|)
expr_stmt|;
return|return
operator|(
name|T
operator|)
operator|new
name|Functor
argument_list|(
literal|"notEqual"
argument_list|,
name|nodes
argument_list|,
name|BuiltinRegistry
operator|.
name|theRegistry
argument_list|)
return|;
block|}
block|}
end_class

end_unit

