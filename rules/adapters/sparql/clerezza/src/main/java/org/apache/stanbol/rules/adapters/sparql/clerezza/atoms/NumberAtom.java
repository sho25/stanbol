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
name|sparql
operator|.
name|clerezza
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|LiteralFactory
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
name|LiteralExpression
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
name|Variable
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
name|sparql
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
name|Symbols
import|;
end_import

begin_comment
comment|/**  * It adapts any NumberAtom to a number literal in Clerezza.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|NumberAtom
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
name|NumberAtom
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
name|NumberAtom
operator|)
name|ruleAtom
decl_stmt|;
name|String
name|number
init|=
name|tmp
operator|.
name|getNumber
argument_list|()
decl_stmt|;
name|Expression
name|exp
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|number
operator|.
name|startsWith
argument_list|(
literal|"\""
argument_list|)
operator|&&
name|number
operator|.
name|endsWith
argument_list|(
literal|"\""
argument_list|)
condition|)
block|{
name|number
operator|=
name|number
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|number
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|number
operator|.
name|startsWith
argument_list|(
name|Symbols
operator|.
name|variablesPrefix
argument_list|)
condition|)
block|{
name|exp
operator|=
operator|new
name|Variable
argument_list|(
name|number
operator|.
name|replace
argument_list|(
name|Symbols
operator|.
name|variablesPrefix
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|exp
operator|=
operator|new
name|LiteralExpression
argument_list|(
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createTypedLiteral
argument_list|(
name|number
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|T
operator|)
operator|new
name|ClerezzaSparqlObject
argument_list|(
name|exp
argument_list|)
return|;
block|}
block|}
end_class

end_unit

