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
name|InvalidLiteralTypeException
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
name|NoConvertorException
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
name|impl
operator|.
name|SimpleLiteralFactory
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
name|servicesapi
operator|.
name|model
operator|.
name|Text
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
comment|/**  * This Adapter supports:  *<ul>  *<li> String: Converts all Literal to there lexical form  *<li> Text: Converts {@link PlainLiteral}s and {@link TypedLiteral}s with a  * data type constrained in {@link RdfResourceUtils#STRING_DATATYPES} to Text instances  *<li> Int, Long, UriRef ... : Converts {@link TypedLiteral}s to the according  * Java Object by using the Clerezza {@link LiteralFactory} (see {@link SimpleLiteralFactory})  *</ul>  *  * @author Rupert Westenthaler  *  * @param<T> All types of Literals  * @param<A> See above documentation  */
end_comment

begin_class
specifier|public
class|class
name|LiteralAdapter
parameter_list|<
name|T
extends|extends
name|Literal
parameter_list|,
name|A
parameter_list|>
implements|implements
name|Adapter
argument_list|<
name|T
argument_list|,
name|A
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
name|LiteralAdapter
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|LiteralFactory
name|lf
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
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|public
specifier|final
name|A
name|adapt
parameter_list|(
name|T
name|value
parameter_list|,
name|Class
argument_list|<
name|A
argument_list|>
name|type
parameter_list|)
block|{
comment|// NOTE: (Rupert Westenthaler 12.01.2011)
comment|//      Converting everything to String is not an intended functionality. When
comment|//      someone parsed String.class he rather assumes that he gets only string
comment|//      values and not also string representations for Dates, Integer ...
comment|//      If someone needs this kind of functionality he can anyway use the
comment|//      the Resource2StringAdapter.
comment|//        if(type.equals(String.class)){
comment|//            return (A) value.getLexicalForm();
comment|//        } else
if|if
condition|(
name|Text
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
condition|)
block|{
if|if
condition|(
name|value
operator|instanceof
name|PlainLiteral
operator|||
operator|(
name|value
operator|instanceof
name|TypedLiteral
operator|&&
name|RdfResourceUtils
operator|.
name|STRING_DATATYPES
operator|.
name|contains
argument_list|(
operator|(
operator|(
name|TypedLiteral
operator|)
name|value
operator|)
operator|.
name|getDataType
argument_list|()
argument_list|)
operator|)
condition|)
block|{
return|return
operator|(
name|A
operator|)
name|valueFactory
operator|.
name|createText
argument_list|(
name|value
argument_list|)
return|;
block|}
else|else
block|{
comment|//this Literal can not be converted to Text!
if|if
condition|(
name|value
operator|instanceof
name|TypedLiteral
condition|)
block|{
comment|//TODO: maybe remove this debugging for performance reasons
name|log
operator|.
name|debug
argument_list|(
literal|"TypedLiterals of type "
operator|+
operator|(
operator|(
name|TypedLiteral
operator|)
name|value
operator|)
operator|.
name|getDataType
argument_list|()
operator|+
literal|" can not be converted to Text"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Literal of type"
operator|+
name|value
operator|.
name|getClass
argument_list|()
operator|+
literal|" are not supported by this Adapter"
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|TypedLiteral
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|value
operator|.
name|getClass
argument_list|()
argument_list|)
condition|)
block|{
try|try
block|{
return|return
name|lf
operator|.
name|createObject
argument_list|(
name|type
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|value
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NoConvertorException
name|e
parameter_list|)
block|{
comment|//This usually indicates a missing converter ... so log in warning
name|log
operator|.
name|warn
argument_list|(
literal|"unable to convert "
operator|+
name|value
operator|+
literal|" to "
operator|+
name|type
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|InvalidLiteralTypeException
name|e
parameter_list|)
block|{
comment|//This usually indicated a wrong type of the literal so log in debug
name|log
operator|.
name|debug
argument_list|(
literal|"unable to Literal "
operator|+
name|value
operator|+
literal|" to the type"
operator|+
name|type
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
else|else
block|{
comment|//indicates, that someone wants to convert non TypedLiterals to an
comment|//specific data type
name|log
operator|.
name|warn
argument_list|(
literal|"Converting Literals without type information to types other "
operator|+
literal|"String is not supported (requested type: "
operator|+
name|type
operator|+
literal|")! -> return null"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

