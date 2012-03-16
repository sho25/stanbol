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

begin_class
specifier|public
class|class
name|UObjectAtom
extends|extends
name|StringFunctionAtom
block|{
specifier|public
specifier|static
specifier|final
name|int
name|STRING_TYPE
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|INTEGER_TYPE
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|VARIABLE_TYPE
init|=
literal|2
decl_stmt|;
specifier|private
name|Object
name|argument
decl_stmt|;
specifier|private
name|int
name|actualType
decl_stmt|;
specifier|public
name|UObjectAtom
parameter_list|(
name|Object
name|argument
parameter_list|)
block|{
name|this
operator|.
name|argument
operator|=
name|argument
expr_stmt|;
if|if
condition|(
name|argument
operator|instanceof
name|VariableAtom
condition|)
block|{
name|actualType
operator|=
literal|2
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument
operator|instanceof
name|String
condition|)
block|{
name|actualType
operator|=
literal|0
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument
operator|instanceof
name|Integer
condition|)
block|{
name|actualType
operator|=
literal|1
expr_stmt|;
block|}
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
name|argumentSPARQL
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|actualType
condition|)
block|{
case|case
literal|0
case|:
name|argumentSPARQL
operator|=
literal|"\""
operator|+
operator|(
name|String
operator|)
name|argument
operator|+
literal|"\"^^<http://www.w3.org/2001/XMLSchema#string>"
expr_stmt|;
break|break;
case|case
literal|1
case|:
name|argumentSPARQL
operator|=
operator|(
operator|(
name|Integer
operator|)
name|argument
operator|)
operator|.
name|toString
argument_list|()
operator|+
literal|"^^<http://www.w3.org/2001/XMLSchema#int>"
expr_stmt|;
break|break;
case|case
literal|2
case|:
name|argumentSPARQL
operator|=
literal|"?"
operator|+
name|argument
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
if|if
condition|(
name|argumentSPARQL
operator|!=
literal|null
condition|)
block|{
return|return
operator|new
name|SPARQLFunction
argument_list|(
name|argumentSPARQL
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
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
name|argumentString
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|actualType
condition|)
block|{
case|case
literal|0
case|:
name|argumentString
operator|=
operator|(
name|String
operator|)
name|argument
expr_stmt|;
break|break;
case|case
literal|1
case|:
name|argumentString
operator|=
operator|(
operator|(
name|Integer
operator|)
name|argument
operator|)
operator|.
name|toString
argument_list|()
expr_stmt|;
break|break;
case|case
literal|2
case|:
name|argumentString
operator|=
literal|"?"
operator|+
name|argument
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
return|return
name|argumentString
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
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

