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
name|enhancer
operator|.
name|engines
operator|.
name|sentiment
operator|.
name|summarize
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
name|EnumSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|stanbol
operator|.
name|enhancer
operator|.
name|nlp
operator|.
name|NlpAnnotations
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|AnalysedText
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Section
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Sentence
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Token
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|annotation
operator|.
name|Value
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
name|enhancer
operator|.
name|nlp
operator|.
name|pos
operator|.
name|LexicalCategory
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
name|enhancer
operator|.
name|nlp
operator|.
name|pos
operator|.
name|PosTag
import|;
end_import

begin_comment
comment|/**  * This class is used to allow adding negations to sentiments even if the  * sentiment was already assigned to an SentimentInfo. In addition this class  * stores the token for the sentiment AND the tokens causing the negations. No  * support for multiple negations - meaning that the sentiment value is inverted  * if 1..* negations are present.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|Sentiment
block|{
comment|/**      * Minimum POS tag confidence so that the annotated POS is used without      * considering the {@link #PREF_LEX_CAT}      */
specifier|private
specifier|static
specifier|final
name|double
name|MIN_POS_CONF
init|=
literal|0.85
decl_stmt|;
comment|/**      * if the confidence of the main POS tag is lower then {@link #MIN_POS_CONF},      * than all POS tags are searched for the an POS annotation compatible with      * {@link #PREF_LEX_CAT}.      */
specifier|private
specifier|static
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|PREF_LEX_CAT
init|=
name|EnumSet
operator|.
name|of
argument_list|(
name|LexicalCategory
operator|.
name|Adjective
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|Token
name|token
decl_stmt|;
specifier|private
specifier|final
name|double
name|value
decl_stmt|;
specifier|private
specifier|final
name|Sentence
name|sentence
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Token
argument_list|>
name|negated
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Token
argument_list|>
name|aboutness
decl_stmt|;
specifier|private
name|PosTag
name|posTag
decl_stmt|;
specifier|private
name|int
name|start
decl_stmt|;
specifier|private
name|int
name|end
decl_stmt|;
specifier|private
name|Token
name|verb
decl_stmt|;
comment|/**      * The Token with the sentiment, the value of the sentiment and optionally      * the Sentence for the token      * @param token      * @param value      * @param sentence      */
specifier|public
name|Sentiment
parameter_list|(
name|Token
name|token
parameter_list|,
name|double
name|value
parameter_list|,
name|Sentence
name|sentence
parameter_list|)
block|{
name|this
operator|.
name|token
operator|=
name|token
expr_stmt|;
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
name|this
operator|.
name|sentence
operator|=
name|sentence
expr_stmt|;
name|this
operator|.
name|start
operator|=
name|token
operator|.
name|getStart
argument_list|()
expr_stmt|;
name|this
operator|.
name|end
operator|=
name|token
operator|.
name|getEnd
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|Value
argument_list|<
name|PosTag
argument_list|>
argument_list|>
name|tags
init|=
name|token
operator|.
name|getAnnotations
argument_list|(
name|NlpAnnotations
operator|.
name|POS_ANNOTATION
argument_list|)
decl_stmt|;
for|for
control|(
name|Value
argument_list|<
name|PosTag
argument_list|>
name|tag
range|:
name|tags
control|)
block|{
if|if
condition|(
name|tag
operator|.
name|probability
argument_list|()
operator|==
name|Value
operator|.
name|UNKNOWN_PROBABILITY
operator|||
name|tag
operator|.
name|probability
argument_list|()
operator|>=
name|MIN_POS_CONF
operator|||
operator|!
name|Collections
operator|.
name|disjoint
argument_list|(
name|tag
operator|.
name|value
argument_list|()
operator|.
name|getCategories
argument_list|()
argument_list|,
name|PREF_LEX_CAT
argument_list|)
condition|)
block|{
name|posTag
operator|=
name|tag
operator|.
name|value
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|posTag
operator|==
literal|null
condition|)
block|{
name|posTag
operator|=
name|tags
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|value
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|posTag
operator|.
name|hasCategory
argument_list|(
name|LexicalCategory
operator|.
name|Noun
argument_list|)
condition|)
block|{
name|addAbout
argument_list|(
name|token
argument_list|)
expr_stmt|;
comment|//add the token also as noun
block|}
if|if
condition|(
name|posTag
operator|.
name|hasCategory
argument_list|(
name|LexicalCategory
operator|.
name|Verb
argument_list|)
condition|)
block|{
name|setVerb
argument_list|(
name|token
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|negate
parameter_list|(
name|Token
name|token
parameter_list|)
block|{
if|if
condition|(
name|negated
operator|==
literal|null
condition|)
block|{
comment|//most of the time a singeltonList will do
name|negated
operator|=
name|Collections
operator|.
name|singletonList
argument_list|(
name|token
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|negated
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|List
argument_list|<
name|Token
argument_list|>
name|l
init|=
operator|new
name|ArrayList
argument_list|<
name|Token
argument_list|>
argument_list|(
literal|4
argument_list|)
decl_stmt|;
name|l
operator|.
name|add
argument_list|(
name|negated
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|l
operator|.
name|add
argument_list|(
name|token
argument_list|)
expr_stmt|;
name|negated
operator|=
name|l
expr_stmt|;
block|}
name|checkSpan
argument_list|(
name|token
argument_list|)
expr_stmt|;
block|}
specifier|protected
specifier|final
name|void
name|setVerb
parameter_list|(
name|Token
name|verb
parameter_list|)
block|{
name|this
operator|.
name|verb
operator|=
name|verb
expr_stmt|;
name|checkSpan
argument_list|(
name|verb
argument_list|)
expr_stmt|;
block|}
specifier|protected
specifier|final
name|void
name|addAbout
parameter_list|(
name|Token
name|noun
parameter_list|)
block|{
if|if
condition|(
name|aboutness
operator|==
literal|null
condition|)
block|{
name|aboutness
operator|=
operator|new
name|ArrayList
argument_list|<
name|Token
argument_list|>
argument_list|(
literal|4
argument_list|)
expr_stmt|;
block|}
name|aboutness
operator|.
name|add
argument_list|(
name|noun
argument_list|)
expr_stmt|;
name|checkSpan
argument_list|(
name|noun
argument_list|)
expr_stmt|;
block|}
comment|/**      * Checks the {@link #start} {@link #end} values against the span selected      * by the parsed token      * @param token      */
specifier|private
name|void
name|checkSpan
parameter_list|(
name|Token
name|token
parameter_list|)
block|{
if|if
condition|(
name|start
operator|>
name|token
operator|.
name|getStart
argument_list|()
condition|)
block|{
name|start
operator|=
name|token
operator|.
name|getStart
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|end
operator|<
name|token
operator|.
name|getEnd
argument_list|()
condition|)
block|{
name|end
operator|=
name|token
operator|.
name|getEnd
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * The POS tag of the Token with a sentiment.      * @return      */
specifier|public
name|PosTag
name|getPosTag
parameter_list|()
block|{
return|return
name|posTag
return|;
block|}
specifier|public
name|double
name|getValue
parameter_list|()
block|{
return|return
name|negated
operator|==
literal|null
condition|?
name|value
else|:
name|value
operator|*
operator|-
literal|1
return|;
block|}
specifier|public
name|Token
name|getToken
parameter_list|()
block|{
return|return
name|token
return|;
block|}
specifier|public
name|Sentence
name|getSentence
parameter_list|()
block|{
return|return
name|sentence
return|;
block|}
specifier|public
name|AnalysedText
name|getAnalysedText
parameter_list|()
block|{
return|return
name|token
operator|.
name|getContext
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|Token
argument_list|>
name|getNegates
parameter_list|()
block|{
return|return
name|negated
operator|==
literal|null
condition|?
name|Collections
operator|.
name|EMPTY_LIST
else|:
name|negated
return|;
block|}
comment|/**      * The Nouns or Pronoun(s) the Adjectives are about      * @return      */
specifier|public
name|List
argument_list|<
name|Token
argument_list|>
name|getAboutness
parameter_list|()
block|{
return|return
name|aboutness
operator|==
literal|null
condition|?
name|Collections
operator|.
name|EMPTY_LIST
else|:
name|aboutness
return|;
block|}
comment|/**      * The verb used to assign Adjectives to the Nouns (or Pronouns)      * @return      */
specifier|public
name|Token
name|getVerb
parameter_list|()
block|{
return|return
name|verb
return|;
block|}
specifier|public
name|int
name|getStart
parameter_list|()
block|{
return|return
name|start
return|;
block|}
specifier|public
name|int
name|getEnd
parameter_list|()
block|{
return|return
name|end
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
operator|new
name|StringBuilder
argument_list|(
literal|"Sentiment ["
argument_list|)
operator|.
name|append
argument_list|(
name|start
argument_list|)
operator|.
name|append
argument_list|(
literal|','
argument_list|)
operator|.
name|append
argument_list|(
name|end
argument_list|)
operator|.
name|append
argument_list|(
literal|"]:"
argument_list|)
operator|.
name|append
argument_list|(
name|token
argument_list|)
operator|.
name|append
argument_list|(
literal|'@'
argument_list|)
operator|.
name|append
argument_list|(
name|getValue
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|" | negations: "
argument_list|)
operator|.
name|append
argument_list|(
name|getNegates
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|" | about: "
argument_list|)
operator|.
name|append
argument_list|(
name|getAboutness
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|" | verb: "
argument_list|)
operator|.
name|append
argument_list|(
name|verb
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|token
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|obj
operator|instanceof
name|Sentiment
operator|&&
name|token
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Sentiment
operator|)
name|obj
operator|)
operator|.
name|token
argument_list|)
operator|&&
name|value
operator|==
operator|(
operator|(
name|Sentiment
operator|)
name|obj
operator|)
operator|.
name|value
operator|&&
operator|(
operator|(
name|negated
operator|==
literal|null
operator|&&
operator|(
operator|(
name|Sentiment
operator|)
name|obj
operator|)
operator|.
name|negated
operator|==
literal|null
operator|)
operator|||
operator|(
name|negated
operator|!=
literal|null
operator|&&
operator|!
name|negated
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|(
operator|(
name|Sentiment
operator|)
name|obj
operator|)
operator|.
name|negated
operator|!=
literal|null
operator|&&
operator|!
operator|(
operator|(
name|Sentiment
operator|)
name|obj
operator|)
operator|.
name|negated
operator|.
name|isEmpty
argument_list|()
operator|)
operator|)
return|;
block|}
block|}
end_class

end_unit

