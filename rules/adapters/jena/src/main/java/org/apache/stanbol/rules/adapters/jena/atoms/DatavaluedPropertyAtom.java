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
name|IObjectAtom
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
name|TriplePattern
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
name|Rule
import|;
end_import

begin_comment
comment|/**  *   * It adapts a DatavaluedPropertyAtom to triple pattern of Jena.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|DatavaluedPropertyAtom
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
name|DatavaluedPropertyAtom
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
name|DatavaluedPropertyAtom
operator|)
name|ruleAtom
decl_stmt|;
name|IObjectAtom
name|argument1
init|=
name|tmp
operator|.
name|getArgument1
argument_list|()
decl_stmt|;
name|IObjectAtom
name|datatypeProperty
init|=
name|tmp
operator|.
name|getDatatypeProperty
argument_list|()
decl_stmt|;
name|RuleAtom
name|argument2
init|=
name|tmp
operator|.
name|getArgument2
argument_list|()
decl_stmt|;
name|ClauseEntry
name|argument2ClauseEntry
init|=
operator|(
name|ClauseEntry
operator|)
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
name|ClauseEntry
name|argument1ClauseEntry
init|=
operator|(
name|ClauseEntry
operator|)
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
name|datatypePropertyClauseEntry
init|=
operator|(
name|ClauseEntry
operator|)
name|adapter
operator|.
name|adaptTo
argument_list|(
name|datatypeProperty
argument_list|,
name|Rule
operator|.
name|class
argument_list|)
decl_stmt|;
name|Node
name|subjectNode
init|=
literal|null
decl_stmt|;
name|Node
name|predicateNode
init|=
literal|null
decl_stmt|;
name|Node
name|objectNode
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|argument1ClauseEntry
operator|instanceof
name|NodeClauseEntry
condition|)
block|{
name|subjectNode
operator|=
operator|(
operator|(
name|NodeClauseEntry
operator|)
name|argument1ClauseEntry
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
name|RuleAtomCallExeption
argument_list|(
name|getClass
argument_list|()
argument_list|)
throw|;
block|}
if|if
condition|(
name|datatypePropertyClauseEntry
operator|instanceof
name|NodeClauseEntry
condition|)
block|{
name|predicateNode
operator|=
operator|(
operator|(
name|NodeClauseEntry
operator|)
name|datatypePropertyClauseEntry
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
name|RuleAtomCallExeption
argument_list|(
name|getClass
argument_list|()
argument_list|)
throw|;
block|}
if|if
condition|(
name|argument2ClauseEntry
operator|instanceof
name|NodeClauseEntry
condition|)
block|{
name|objectNode
operator|=
operator|(
operator|(
name|NodeClauseEntry
operator|)
name|argument2ClauseEntry
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
name|RuleAtomCallExeption
argument_list|(
name|getClass
argument_list|()
argument_list|)
throw|;
block|}
return|return
operator|(
name|T
operator|)
operator|new
name|TriplePattern
argument_list|(
name|subjectNode
argument_list|,
name|predicateNode
argument_list|,
name|objectNode
argument_list|)
return|;
block|}
block|}
end_class

end_unit

