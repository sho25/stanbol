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
name|semanticweb
operator|.
name|owlapi
operator|.
name|apibinding
operator|.
name|OWLManager
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
name|OWLLiteral
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
name|SWRLLiteralArgument
import|;
end_import

begin_class
specifier|public
class|class
name|DatavaluedPropertyAtom
extends|extends
name|CoreAtom
block|{
specifier|private
name|IObjectAtom
name|datatypeProperty
decl_stmt|;
specifier|private
name|IObjectAtom
name|argument1
decl_stmt|;
specifier|private
name|RuleAtom
name|argument2
decl_stmt|;
specifier|public
name|DatavaluedPropertyAtom
parameter_list|(
name|IObjectAtom
name|datatypeProperty
parameter_list|,
name|IObjectAtom
name|argument1
parameter_list|,
name|RuleAtom
name|argument2
parameter_list|)
block|{
name|this
operator|.
name|datatypeProperty
operator|=
name|datatypeProperty
expr_stmt|;
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
name|String
name|prettyPrint
parameter_list|()
block|{
return|return
literal|"Individual "
operator|+
name|argument1
operator|.
name|toString
argument_list|()
operator|+
literal|" has datatype property "
operator|+
name|argument1
operator|.
name|toString
argument_list|()
operator|+
literal|" with value "
operator|+
name|argument2
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
name|SWRLLiteralArgument
name|getSWRLTypedLiteral
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|,
name|Object
name|argument
parameter_list|)
block|{
name|OWLLiteral
name|owlLiteral
decl_stmt|;
if|if
condition|(
name|argument
operator|instanceof
name|String
condition|)
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
name|String
operator|)
name|argument
argument_list|)
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
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
operator|(
name|Integer
operator|)
name|argument
operator|)
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument
operator|instanceof
name|Double
condition|)
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
operator|(
name|Double
operator|)
name|argument
operator|)
operator|.
name|doubleValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument
operator|instanceof
name|Float
condition|)
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
operator|(
name|Float
operator|)
name|argument
operator|)
operator|.
name|floatValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument
operator|instanceof
name|Boolean
condition|)
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
operator|(
name|Boolean
operator|)
name|argument
operator|)
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLStringLiteral
argument_list|(
name|argument
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|factory
operator|.
name|getSWRLLiteralArgument
argument_list|(
name|owlLiteral
argument_list|)
return|;
block|}
specifier|private
name|OWLLiteral
name|getOWLTypedLiteral
parameter_list|(
name|Object
name|argument
parameter_list|)
block|{
name|OWLDataFactory
name|factory
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|OWLLiteral
name|owlLiteral
decl_stmt|;
if|if
condition|(
name|argument
operator|instanceof
name|String
condition|)
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
name|String
operator|)
name|argument
argument_list|)
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
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
operator|(
name|Integer
operator|)
name|argument
operator|)
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument
operator|instanceof
name|Double
condition|)
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
operator|(
name|Double
operator|)
name|argument
operator|)
operator|.
name|doubleValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument
operator|instanceof
name|Float
condition|)
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
operator|(
name|Float
operator|)
name|argument
operator|)
operator|.
name|floatValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument
operator|instanceof
name|Boolean
condition|)
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
operator|(
name|Boolean
operator|)
name|argument
operator|)
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLStringLiteral
argument_list|(
name|argument
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|owlLiteral
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"values("
operator|+
name|datatypeProperty
operator|.
name|toString
argument_list|()
operator|+
literal|", "
operator|+
name|argument1
operator|.
name|toString
argument_list|()
operator|+
literal|", "
operator|+
name|argument2
operator|.
name|toString
argument_list|()
operator|+
literal|")"
return|;
comment|/*          * String arg1 = null; String arg2 = null; String arg3 = null;          *           * if(argument1.toString().startsWith(Symbols.variablesPrefix)){ arg1 =          * "?"+argument1.toString().replace(Symbols.variablesPrefix, ""); VariableAtom variable =          * (VariableAtom) argument1; if(variable.isNegative()){ arg1 = "notex(" + arg1 + ")"; } } else{ arg1 =          * argument1.toString(); }          *           *           * if(datatypeProperty.toString().startsWith(Symbols.variablesPrefix)){ arg3 =          * "?"+datatypeProperty.toString().replace(Symbols.variablesPrefix, ""); VariableAtom variable =          * (VariableAtom) datatypeProperty; if(variable.isNegative()){ arg3 = "notex(" + arg3 + ")"; } } else{          * arg3 = datatypeProperty.toString(); }          *           * if(argument2.toString().startsWith(Symbols.variablesPrefix)){ arg2 =          * "?"+argument2.toString().replace(Symbols.variablesPrefix, "");          *           * VariableAtom variable = (VariableAtom) argument2; if(variable.isNegative()){ arg2 = "notex(" + arg2          * + ")"; }          *           * return "values(" + arg3 + ", " + arg1 + ", " + arg2 +")"; } else{ OWLLiteral literal =          * getOWLTypedLiteral(argument2);          *           * return "values(" + arg3 + ", " + arg1 + ", " + literal.getLiteral() +")"; }          */
block|}
specifier|public
name|IObjectAtom
name|getArgument1
parameter_list|()
block|{
return|return
name|argument1
return|;
block|}
specifier|public
name|RuleAtom
name|getArgument2
parameter_list|()
block|{
return|return
name|argument2
return|;
block|}
specifier|public
name|IObjectAtom
name|getDatatypeProperty
parameter_list|()
block|{
return|return
name|datatypeProperty
return|;
block|}
block|}
end_class

end_unit

