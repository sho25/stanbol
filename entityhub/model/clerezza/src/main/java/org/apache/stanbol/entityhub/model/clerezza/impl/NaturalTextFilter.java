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
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|commons
operator|.
name|rdf
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
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
name|commons
operator|.
name|rdf
operator|.
name|Language
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
name|FilteringIterator
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
name|FilteringIterator
operator|.
name|Filter
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
name|defaults
operator|.
name|DataTypeEnum
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
comment|/**  * Filter implementation to be used in combination with {@link FilteringIterator}  * to return only {@link Literal} values (may be {@link PlainLiteral}s and/or  * {@link TypedLiteral}s) that confirm to the parsed set of languages.<p>  * Parsing<code>null</code>, an empty array is interpreted such that any  * language is accepted. Parsing "" or<code>null</code> as one element of the  * array indicated that Literals without any language tag are included. This also  * includes {@link TypedLiteral}s with the data type<code>xsd:string</code>.<p>  * Note that parsing:<ul>  *<li> an empty array will result in all Literals (regardless of the language)  *      are returned  *<li> an array that contains only the<code>null</code> element will result in  *      only Literals without any language tag are returned.  *</ul>  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|NaturalTextFilter
implements|implements
name|Filter
argument_list|<
name|Literal
argument_list|>
block|{
specifier|private
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|NaturalTextFilter
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * The xsd:string data type constant used for TypedLiterals to check if the      * represent an string value!      */
specifier|private
specifier|static
name|IRI
name|xsdString
init|=
operator|new
name|IRI
argument_list|(
name|DataTypeEnum
operator|.
name|String
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|Language
argument_list|>
name|languages
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|containsNull
decl_stmt|;
specifier|public
name|NaturalTextFilter
parameter_list|(
name|String
modifier|...
name|languages
parameter_list|)
block|{
if|if
condition|(
name|languages
operator|==
literal|null
operator|||
name|languages
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|this
operator|.
name|languages
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|containsNull
operator|=
literal|true
expr_stmt|;
comment|// if no language is parse accept any (also the default)
block|}
else|else
block|{
name|Set
argument_list|<
name|Language
argument_list|>
name|languagesConverted
init|=
operator|new
name|HashSet
argument_list|<
name|Language
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|lang1
range|:
name|languages
control|)
block|{
if|if
condition|(
name|lang1
operator|==
literal|null
operator|||
name|lang1
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|languagesConverted
operator|.
name|add
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|languagesConverted
operator|.
name|add
argument_list|(
operator|new
name|Language
argument_list|(
name|lang1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|this
operator|.
name|languages
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|languagesConverted
argument_list|)
expr_stmt|;
name|this
operator|.
name|containsNull
operator|=
name|this
operator|.
name|languages
operator|.
name|contains
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|boolean
name|isValid
parameter_list|(
name|Literal
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|.
name|getLanguage
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|languages
operator|==
literal|null
condition|)
block|{
comment|//no language restrictions
return|return
literal|true
return|;
comment|//return any Plain Literal
block|}
else|else
block|{
name|Language
name|literalLang
init|=
name|value
operator|.
name|getLanguage
argument_list|()
decl_stmt|;
return|return
name|languages
operator|.
name|contains
argument_list|(
name|literalLang
argument_list|)
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|value
operator|.
name|getDataType
argument_list|()
operator|.
name|equals
argument_list|(
name|xsdString
argument_list|)
condition|)
block|{
comment|/*              * if the null language is active, than we can also return              * "normal" literals (with no known language). This includes              * Types literals with the data type xsd:string              */
return|return
name|containsNull
return|;
block|}
else|else
block|{
comment|// unknown Literal type -> filter + warning
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unknown LiteralType %s (lexicalForm=\"%s\") -> ignored! Pleas adapt this implementation to support this type!"
argument_list|,
name|value
operator|.
name|getClass
argument_list|()
argument_list|,
name|value
operator|.
name|getLexicalForm
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

