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
name|manager
operator|.
name|atoms
operator|.
name|TypedLiteralAtom
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
name|datatypes
operator|.
name|xsd
operator|.
name|XSDDatatype
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
name|Node_RuleVariable
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
name|vocabulary
operator|.
name|XSD
import|;
end_import

begin_comment
comment|/**  * It provides a static method (<code>getTypedLiteral</code>) that allows to convert an object to {@link Node}  * that implements a typed literal in Jena.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|NodeFactory
block|{
comment|/**      * Restrict instantiation      */
specifier|private
name|NodeFactory
parameter_list|()
block|{}
comment|/**      * The argument is converted to a Jena {@link Node}      *       * @param argument      *            any Object      * @return the {@link Node}      */
specifier|public
specifier|static
name|Node
name|getTypedLiteral
parameter_list|(
name|Object
name|argument
parameter_list|)
block|{
name|Node
name|literal
decl_stmt|;
if|if
condition|(
name|argument
operator|instanceof
name|TypedLiteralAtom
condition|)
block|{
name|TypedLiteralAtom
name|typedLiteralAtom
init|=
operator|(
name|TypedLiteralAtom
operator|)
name|argument
decl_stmt|;
name|URIResource
name|xsdType
init|=
name|typedLiteralAtom
operator|.
name|getXsdType
argument_list|()
decl_stmt|;
if|if
condition|(
name|xsdType
operator|.
name|getURI
argument_list|()
operator|.
name|equals
argument_list|(
name|XSD
operator|.
name|xboolean
argument_list|)
condition|)
block|{
name|literal
operator|=
name|Node_RuleVariable
operator|.
name|createLiteral
argument_list|(
name|argument
operator|.
name|toString
argument_list|()
argument_list|,
literal|null
argument_list|,
name|XSDDatatype
operator|.
name|XSDboolean
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|xsdType
operator|.
name|getURI
argument_list|()
operator|.
name|equals
argument_list|(
name|XSD
operator|.
name|xdouble
argument_list|)
condition|)
block|{
name|literal
operator|=
name|Node_RuleVariable
operator|.
name|createLiteral
argument_list|(
name|argument
operator|.
name|toString
argument_list|()
argument_list|,
literal|null
argument_list|,
name|XSDDatatype
operator|.
name|XSDdouble
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|xsdType
operator|.
name|getURI
argument_list|()
operator|.
name|equals
argument_list|(
name|XSD
operator|.
name|xfloat
argument_list|)
condition|)
block|{
name|literal
operator|=
name|Node_RuleVariable
operator|.
name|createLiteral
argument_list|(
name|argument
operator|.
name|toString
argument_list|()
argument_list|,
literal|null
argument_list|,
name|XSDDatatype
operator|.
name|XSDfloat
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|xsdType
operator|.
name|getURI
argument_list|()
operator|.
name|equals
argument_list|(
name|XSD
operator|.
name|xint
argument_list|)
condition|)
block|{
name|literal
operator|=
name|Node_RuleVariable
operator|.
name|createLiteral
argument_list|(
name|argument
operator|.
name|toString
argument_list|()
argument_list|,
literal|null
argument_list|,
name|XSDDatatype
operator|.
name|XSDint
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|literal
operator|=
name|Node_RuleVariable
operator|.
name|createLiteral
argument_list|(
name|argument
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|argument
operator|instanceof
name|String
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|argument
argument_list|)
expr_stmt|;
name|String
name|argString
init|=
operator|(
name|String
operator|)
name|argument
decl_stmt|;
if|if
condition|(
name|argString
operator|.
name|startsWith
argument_list|(
literal|"\""
argument_list|)
operator|&&
name|argString
operator|.
name|endsWith
argument_list|(
literal|"\""
argument_list|)
condition|)
block|{
name|argString
operator|=
name|argString
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|argString
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|literal
operator|=
name|Node_RuleVariable
operator|.
name|createLiteral
argument_list|(
name|argString
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
name|literal
operator|=
name|Node_RuleVariable
operator|.
name|createLiteral
argument_list|(
name|argument
operator|.
name|toString
argument_list|()
argument_list|,
literal|null
argument_list|,
name|XSDDatatype
operator|.
name|XSDint
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|literal
operator|=
name|Node_RuleVariable
operator|.
name|createLiteral
argument_list|(
name|argument
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|literal
return|;
block|}
block|}
end_class

end_unit

