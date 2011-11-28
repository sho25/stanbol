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
name|base
operator|.
name|api
operator|.
name|JenaClauseEntry
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
name|JenaVariableMap
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

begin_class
specifier|public
class|class
name|ConcatAtom
extends|extends
name|StringFunctionAtom
block|{
specifier|private
name|StringFunctionAtom
name|argument1
decl_stmt|;
specifier|private
name|StringFunctionAtom
name|argument2
decl_stmt|;
specifier|public
name|ConcatAtom
parameter_list|(
name|StringFunctionAtom
name|argument1
parameter_list|,
name|StringFunctionAtom
name|argument2
parameter_list|)
block|{
name|this
operator|.
name|argument1
operator|=
name|argument1
expr_stmt|;
name|this
operator|.
name|argument2
operator|=
name|argument2
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
name|sparqlConcat
init|=
literal|"<http://www.w3.org/2005/xpath-functions#concat>"
decl_stmt|;
name|String
name|function
init|=
name|sparqlConcat
operator|+
literal|" ("
operator|+
name|argument1
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
operator|+
literal|", "
operator|+
name|argument2
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
operator|+
literal|")"
decl_stmt|;
return|return
operator|new
name|SPARQLFunction
argument_list|(
name|function
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
return|return
literal|"concat("
operator|+
name|argument1
operator|.
name|toKReSSyntax
argument_list|()
operator|+
literal|", "
operator|+
name|argument2
operator|.
name|toKReSSyntax
argument_list|()
operator|+
literal|")"
return|;
block|}
annotation|@
name|Override
specifier|public
name|JenaClauseEntry
name|toJenaClauseEntry
parameter_list|(
name|JenaVariableMap
name|jenaVariableMap
parameter_list|)
block|{
comment|/* 		 * TODO 		String arg1 = argument1.toString(); 		if(arg1.startsWith("http://kres.iks-project.eu/ontology/meta/variables#")){ 			arg1 = "?" + arg1.replace("http://kres.iks-project.eu/ontology/meta/variables#", ""); 		} 		 		String arg2 = argument1.toString(); 		if(arg2.startsWith("http://kres.iks-project.eu/ontology/meta/variables#")){ 			arg2 = "?" + arg2.replace("http://kres.iks-project.eu/ontology/meta/variables#", ""); 		} 		 		java.util.List<Node> nodes = new ArrayList<Node>(); 		 		nodes.add(Node.createURI(arg1)); 		nodes.add(Node.createURI(arg2)); 		nodes.add(Node.createURI("?t324")); 		 		return new Functor("strConcat", nodes); 		*/
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

