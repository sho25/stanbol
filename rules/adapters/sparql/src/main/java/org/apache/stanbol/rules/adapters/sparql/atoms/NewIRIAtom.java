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
comment|/**  * It adapts any NewIRIAtom to a BIND operator in SPARQL, which binds to a variable an object value.  *   * @author anuzzolese  *   */
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
name|SPARQLObject
name|binding
init|=
name|adapter
operator|.
name|adaptTo
argument_list|(
name|tmp
operator|.
name|getBinding
argument_list|()
argument_list|,
name|SPARQLObject
operator|.
name|class
argument_list|)
decl_stmt|;
name|SPARQLObject
name|variable
init|=
name|adapter
operator|.
name|adaptTo
argument_list|(
name|tmp
operator|.
name|getNewNodeVariable
argument_list|()
argument_list|,
name|SPARQLObject
operator|.
name|class
argument_list|)
decl_stmt|;
name|SPARQLObject
name|sparqlObject
init|=
operator|new
name|SPARQLFunction
argument_list|(
literal|"BIND("
operator|+
literal|"IRI("
operator|+
name|binding
operator|.
name|getObject
argument_list|()
operator|+
literal|") AS "
operator|+
name|variable
operator|.
name|getObject
argument_list|()
operator|+
literal|")"
argument_list|)
decl_stmt|;
return|return
operator|(
name|T
operator|)
name|sparqlObject
return|;
block|}
block|}
end_class

end_unit

