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
name|util
operator|.
name|AtomList
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
name|SPARQLFunction
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
name|SWRLAtom
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
name|rdf
operator|.
name|model
operator|.
name|Model
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
name|rdf
operator|.
name|model
operator|.
name|Resource
import|;
end_import

begin_class
specifier|public
class|class
name|UnionAtom
implements|implements
name|RuleAtom
block|{
specifier|private
name|AtomList
name|atomList1
decl_stmt|;
specifier|private
name|AtomList
name|atomList2
decl_stmt|;
specifier|public
name|UnionAtom
parameter_list|(
name|AtomList
name|atomList1
parameter_list|,
name|AtomList
name|atomList2
parameter_list|)
block|{
name|this
operator|.
name|atomList1
operator|=
name|atomList1
expr_stmt|;
name|this
operator|.
name|atomList2
operator|=
name|atomList2
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Resource
name|toSWRL
parameter_list|(
name|Model
name|model
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|SPARQLObject
name|toSPARQL
parameter_list|()
block|{
name|String
name|scope1
init|=
literal|""
decl_stmt|;
for|for
control|(
name|RuleAtom
name|kReSRuleAtom
range|:
name|atomList1
control|)
block|{
if|if
condition|(
operator|!
name|scope1
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|scope1
operator|+=
literal|" . "
expr_stmt|;
block|}
name|scope1
operator|+=
name|kReSRuleAtom
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
expr_stmt|;
block|}
name|String
name|scope2
init|=
literal|""
decl_stmt|;
for|for
control|(
name|RuleAtom
name|kReSRuleAtom
range|:
name|atomList2
control|)
block|{
if|if
condition|(
operator|!
name|scope2
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|scope2
operator|+=
literal|" . "
expr_stmt|;
block|}
name|scope2
operator|+=
name|kReSRuleAtom
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
expr_stmt|;
block|}
name|String
name|sparqlUnion
init|=
literal|" { "
operator|+
name|scope1
operator|+
literal|" } UNION { "
operator|+
name|scope2
operator|+
literal|" } "
decl_stmt|;
return|return
operator|new
name|SPARQLFunction
argument_list|(
name|sparqlUnion
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|SWRLAtom
name|toSWRL
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toKReSSyntax
parameter_list|()
block|{
name|String
name|scope1
init|=
literal|""
decl_stmt|;
for|for
control|(
name|RuleAtom
name|kReSRuleAtom
range|:
name|atomList1
control|)
block|{
if|if
condition|(
operator|!
name|scope1
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|scope1
operator|+=
literal|" . "
expr_stmt|;
block|}
name|scope1
operator|+=
name|kReSRuleAtom
operator|.
name|toKReSSyntax
argument_list|()
expr_stmt|;
block|}
name|String
name|scope2
init|=
literal|""
decl_stmt|;
for|for
control|(
name|RuleAtom
name|kReSRuleAtom
range|:
name|atomList2
control|)
block|{
if|if
condition|(
operator|!
name|scope2
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|scope2
operator|+=
literal|" . "
expr_stmt|;
block|}
name|scope2
operator|+=
name|kReSRuleAtom
operator|.
name|toKReSSyntax
argument_list|()
expr_stmt|;
block|}
return|return
literal|"union("
operator|+
name|scope1
operator|+
literal|", "
operator|+
name|scope2
operator|+
literal|")"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLConstruct
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLDelete
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLDeleteData
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

