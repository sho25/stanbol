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
name|SPARQLNot
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
name|SPARQLTriple
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
name|SPARQLObject
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

begin_comment
comment|/**  * It adapts any ClassAtom to a triple pattern of SPARQL.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|ClassAtom
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
name|String
name|argument1SPARQL
init|=
literal|null
decl_stmt|;
name|String
name|argument2SPARQL
init|=
literal|null
decl_stmt|;
name|boolean
name|negativeArg
init|=
literal|false
decl_stmt|;
name|boolean
name|negativeClass
init|=
literal|false
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
name|ClassAtom
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
name|ClassAtom
operator|)
name|ruleAtom
decl_stmt|;
name|SPARQLObject
name|sparqlArgument1
init|=
name|adapter
operator|.
name|adaptTo
argument_list|(
name|tmp
operator|.
name|getArgument1
argument_list|()
argument_list|,
name|SPARQLObject
operator|.
name|class
argument_list|)
decl_stmt|;
name|SPARQLObject
name|sparqlArgument2
init|=
name|adapter
operator|.
name|adaptTo
argument_list|(
name|tmp
operator|.
name|getClassResource
argument_list|()
argument_list|,
name|SPARQLObject
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|negativeArg
operator|||
name|negativeClass
condition|)
block|{
name|String
name|optional
init|=
name|sparqlArgument1
operator|.
name|getObject
argument_list|()
operator|+
literal|"<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> "
operator|+
name|sparqlArgument2
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|filters
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|negativeArg
condition|)
block|{
name|filters
operator|.
name|add
argument_list|(
literal|"!bound("
operator|+
name|argument1SPARQL
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|negativeClass
condition|)
block|{
name|filters
operator|.
name|add
argument_list|(
literal|"!bound("
operator|+
name|argument2SPARQL
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|filterArray
init|=
operator|new
name|String
index|[
name|filters
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|filterArray
operator|=
name|filters
operator|.
name|toArray
argument_list|(
name|filterArray
argument_list|)
expr_stmt|;
return|return
operator|(
name|T
operator|)
operator|new
name|SPARQLNot
argument_list|(
name|optional
argument_list|,
name|filterArray
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|(
name|T
operator|)
operator|new
name|SPARQLTriple
argument_list|(
name|sparqlArgument1
operator|.
name|getObject
argument_list|()
operator|+
literal|"<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> "
operator|+
name|sparqlArgument2
operator|.
name|getObject
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

