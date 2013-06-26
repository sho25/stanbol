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
name|entityhub
operator|.
name|model
operator|.
name|clerezza
operator|.
name|impl
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
name|Literal
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
name|PlainLiteral
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
name|Resource
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
name|TypedLiteral
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
name|UriRef
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|util
operator|.
name|AdaptingIterator
operator|.
name|Adapter
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
name|entityhub
operator|.
name|model
operator|.
name|clerezza
operator|.
name|RdfResourceUtils
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
name|entityhub
operator|.
name|model
operator|.
name|clerezza
operator|.
name|RdfValueFactory
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
name|entityhub
operator|.
name|model
operator|.
name|clerezza
operator|.
name|RdfResourceUtils
operator|.
name|XsdDataTypeEnum
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Converts the Resources (used to store field values in the Clerezza triple store) back to values as defined  * by the {@link Representation} interface  *   * @author Rupert Westenthaler  *   * @param<T>  *            the type of the Resource that can be converted to values  */
end_comment

begin_class
specifier|public
class|class
name|Resource2ValueAdapter
parameter_list|<
name|T
extends|extends
name|Resource
parameter_list|>
implements|implements
name|Adapter
argument_list|<
name|T
argument_list|,
name|Object
argument_list|>
block|{
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|Resource2ValueAdapter
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|private
name|RdfValueFactory
name|valueFactory
init|=
name|RdfValueFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
annotation|@
name|Override
specifier|public
specifier|final
name|Object
name|adapt
parameter_list|(
name|T
name|value
parameter_list|,
name|Class
argument_list|<
name|Object
argument_list|>
name|type
parameter_list|)
block|{
if|if
condition|(
name|value
operator|instanceof
name|UriRef
condition|)
block|{
return|return
name|valueFactory
operator|.
name|createReference
argument_list|(
operator|(
name|UriRef
operator|)
name|value
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|PlainLiteral
condition|)
block|{
return|return
name|valueFactory
operator|.
name|createText
argument_list|(
operator|(
name|Literal
operator|)
name|value
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|TypedLiteral
condition|)
block|{
name|TypedLiteral
name|literal
init|=
operator|(
name|TypedLiteral
operator|)
name|value
decl_stmt|;
if|if
condition|(
name|literal
operator|.
name|getDataType
argument_list|()
operator|==
literal|null
condition|)
block|{
comment|// if no dataType is defined
comment|// return a Text without a language
return|return
name|valueFactory
operator|.
name|createText
argument_list|(
name|literal
argument_list|)
return|;
block|}
else|else
block|{
name|XsdDataTypeEnum
name|mapping
init|=
name|RdfResourceUtils
operator|.
name|XSD_DATATYPE_VALUE_MAPPING
operator|.
name|get
argument_list|(
name|literal
operator|.
name|getDataType
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|mapping
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|mapping
operator|.
name|getMappedClass
argument_list|()
operator|!=
literal|null
condition|)
block|{
try|try
block|{
return|return
name|literalFactory
operator|.
name|createObject
argument_list|(
name|mapping
operator|.
name|getMappedClass
argument_list|()
argument_list|,
name|literal
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Unable to convert Literal value {} to Java Class {} because of {} with message {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|literal
block|,
name|mapping
operator|.
name|getMappedClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
block|,
name|e
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
block|,
name|e
operator|.
name|getMessage
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|log
operator|.
name|trace
argument_list|(
literal|"Exception:"
argument_list|,
name|e
argument_list|)
expr_stmt|;
comment|//STANBOL-698: Decide what to do in such cases
comment|//(a) throw an exception
comment|// throw e;
comment|//(b) ignore illegal values
comment|//return null;
comment|//(c) use the lexical form
return|return
name|literal
operator|.
name|getLexicalForm
argument_list|()
return|;
block|}
block|}
else|else
block|{
comment|// if no mapped class
comment|// bypass the LiteralFactory and return the string
comment|// representation
return|return
name|literal
operator|.
name|getLexicalForm
argument_list|()
return|;
block|}
block|}
else|else
block|{
comment|// if dataType is not supported
comment|/*                      * this could indicate two things: 1) the SimpleLiteralFactory supports a new DataType and                      * because of that it creates Literals with this Type 2) Literals with this data type                      * where created by other applications. In the first case one need to update the                      * enumeration. In the second case using the LexicalForm should be OK Rupert Westenthaler                      * 2010.10.28                      */
name|log
operator|.
name|warn
argument_list|(
literal|"Missing Mapping for DataType {} -> return String representation"
argument_list|,
name|literal
operator|.
name|getDataType
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|literal
operator|.
name|getLexicalForm
argument_list|()
return|;
block|}
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unsupported Resource Type {} -> return String by using the toString method"
argument_list|,
name|value
operator|.
name|getClass
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|value
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

