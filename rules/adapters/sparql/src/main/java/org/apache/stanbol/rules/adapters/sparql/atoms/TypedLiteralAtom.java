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
name|SPARQLFunction
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
name|URIResource
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

begin_comment
comment|/**  * It adapts any TypedLiteralAtom to a typed literal in SPARQL.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|TypedLiteralAtom
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
name|TypedLiteralAtom
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
name|TypedLiteralAtom
operator|)
name|ruleAtom
decl_stmt|;
name|ExpressionAtom
name|valueExpression
init|=
name|tmp
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|URIResource
name|xsdTypeResource
init|=
name|tmp
operator|.
name|getXsdType
argument_list|()
decl_stmt|;
name|SPARQLObject
name|sparqlObject
init|=
literal|null
decl_stmt|;
name|sparqlObject
operator|=
name|adapter
operator|.
name|adaptTo
argument_list|(
name|valueExpression
argument_list|,
name|SPARQLObject
operator|.
name|class
argument_list|)
expr_stmt|;
name|String
name|value
init|=
name|sparqlObject
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|value
operator|.
name|startsWith
argument_list|(
literal|"\""
argument_list|)
condition|)
block|{
name|value
operator|=
literal|"\""
operator|+
name|value
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|value
operator|.
name|endsWith
argument_list|(
literal|"\""
argument_list|)
condition|)
block|{
name|value
operator|+=
literal|"\""
expr_stmt|;
block|}
name|String
name|xsdType
init|=
literal|"<"
operator|+
name|xsdTypeResource
operator|.
name|getURI
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|">"
decl_stmt|;
return|return
operator|(
name|T
operator|)
operator|new
name|SPARQLFunction
argument_list|(
name|value
operator|+
literal|"^^"
operator|+
name|xsdType
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

