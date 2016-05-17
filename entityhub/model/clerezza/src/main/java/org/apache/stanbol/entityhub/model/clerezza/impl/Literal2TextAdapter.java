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
name|defaults
operator|.
name|DataTypeEnum
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
comment|/**  * This Adapter does two things:  *<ol>  *<li> It filters {@link Literal}s based on the languages parsed in the  *      constructor. If no languages are parsed, than all languages are accepted  *<li> It converts {@link Literal}s to {@link Text}. Only {@link PlainLiteral}  *      and {@link TypedLiteral} with an xsd data type present in the  *      {@link RdfResourceUtils#STRING_DATATYPES} are converted. All other literals are  *      filtered (meaning that<code>null</code> is returned)  *</ol>  * The difference of this Adapter to the {@link LiteralAdapter} with the generic  * type {@link Text} is that the LiteralAdapter can not be used to filter  * literals based on there language.  *  * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|Literal2TextAdapter
parameter_list|<
name|T
extends|extends
name|Literal
parameter_list|>
implements|implements
name|Adapter
argument_list|<
name|T
argument_list|,
name|Text
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
name|Literal2TextAdapter
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
comment|/**      * Unmodifiable set of the active languages      */
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
specifier|private
specifier|final
name|RdfValueFactory
name|valueFactory
init|=
name|RdfValueFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
comment|/**      * Filters Literals in the parsed Iterator based on the parsed languages and      * convert matching Literals to Text      * @param it the iterator      * @param lang the active languages. If<code>null</code> or empty, all      * languages are active. If<code>null</code> is parsed as an element, that      * also Literals without a language are returned      */
specifier|public
name|Literal2TextAdapter
parameter_list|(
name|String
modifier|...
name|lang
parameter_list|)
block|{
if|if
condition|(
name|lang
operator|!=
literal|null
operator|&&
name|lang
operator|.
name|length
operator|>
literal|0
condition|)
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
name|lang
control|)
block|{
if|if
condition|(
name|lang1
operator|==
literal|null
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
name|languages
operator|.
name|contains
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
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
block|}
comment|//init the first element
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|Text
name|adapt
parameter_list|(
name|T
name|value
parameter_list|,
name|Class
argument_list|<
name|Text
argument_list|>
name|type
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
name|Language
name|literalLang
init|=
name|value
operator|.
name|getLanguage
argument_list|()
decl_stmt|;
if|if
condition|(
name|languages
operator|==
literal|null
operator|||
name|languages
operator|.
name|contains
argument_list|(
name|literalLang
argument_list|)
condition|)
block|{
return|return
name|valueFactory
operator|.
name|createText
argument_list|(
name|value
argument_list|)
return|;
block|}
comment|//else wrong language -> filter
block|}
else|else
block|{
if|if
condition|(
name|containsNull
operator|&&
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
comment|/*                  * if the null language is active, than we can also return                  * "normal" literals (with no known language).                  * But first we need to check the Datatype!                  */
return|return
name|valueFactory
operator|.
name|createText
argument_list|(
name|value
argument_list|)
return|;
block|}
comment|// else no xsd:string dataType and therefore not a text with default lang!
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

