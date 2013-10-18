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
name|entitylinking
operator|.
name|impl
package|;
end_package

begin_import
import|import static
name|java
operator|.
name|util
operator|.
name|Collections
operator|.
name|disjoint
import|;
end_import

begin_import
import|import static
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
operator|.
name|POS_ANNOTATION
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
name|List
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|LanguageProcessingConfig
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
name|morpho
operator|.
name|MorphoFeatures
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
comment|/**  * Internally used to store additional Metadata for Tokens of the current Sentence  *<p>  * Checks if the parsed {@link Token} is processable. This decision is taken first based on the POS  * annotation ( Lexical Category, POS tag) and second on the  * {@link EntityLinkerConfig#getMinSearchTokenLength()} if no POS annotations are available or the  * probability of the POS annotations is to low.  *<p>  * Since STANBOL-685two POS Probabilities are used<ul>  *<li> {@link LanguageProcessingConfig#getMinPosAnnotationProbability()} for accepting POS tags that are  * processed - included in {@link LanguageProcessingConfig#getLinkedLexicalCategories()} or  * {@link LanguageProcessingConfig#getLinkedPosTags()}.  *<li> {@link LanguageProcessingConfig#getMinExcludePosAnnotationProbability()} for those that are not  * processed. By default the exclusion probability is set to half of the inclusion one.  *</ul>  * Assuming that the<code>minPosTypePropb=0.667</code> a  *<ul>  *<li>noun with the prop 0.8 would result in returning<code>true</code>  *<li>noun with prop 0.5 would return<code>null</code>  *<li>verb with prop 0.4 would return<code>false</code>  *<li>verb with prop 0.3 would return<code>null</code>  *</ul>  * This algorithm makes it less likely that the {@link EntityLinkerConfig#getMinSearchTokenLength()} needs  * to be used as fallback for Tokens (what typically still provides better estimations as the token  * length).  *<p>  * (see also STANBOL-685 even that this Issue refers a version of this Engine that has not yet used the  * Stanbol NLP processing chain)  *   * @param token  *            the {@link Token} to check.  * @return<code>true</code> if the parsed token needs to be processed. Otherwise<code>false</code>  */
end_comment

begin_class
specifier|public
class|class
name|TokenData
block|{
comment|/** The Token */
specifier|public
specifier|final
name|Token
name|token
decl_stmt|;
comment|/** The index of the Token within the current Section (Sentence) */
specifier|public
specifier|final
name|int
name|index
decl_stmt|;
comment|/** If this Token should be linked with the Vocabulary */
specifier|public
name|boolean
name|isLinkable
decl_stmt|;
comment|/** If this Token should be used for multi word searches in the Vocabulary */
specifier|public
name|boolean
name|isMatchable
decl_stmt|;
comment|/** if this Token has an alpha or numeric char */
specifier|public
specifier|final
name|boolean
name|hasAlphaNumeric
decl_stmt|;
comment|/** the chunk of this Token */
specifier|public
specifier|final
name|ChunkData
name|inChunk
decl_stmt|;
comment|/** the morphological features of the Token (selected based on the POS Tag) */
specifier|public
specifier|final
name|MorphoFeatures
name|morpho
decl_stmt|;
comment|/**      * if this token starts with an upperCase letter      */
specifier|public
specifier|final
name|boolean
name|upperCase
decl_stmt|;
comment|/**      * if the length of the token is&gt;= {@link LanguageProcessingConfig#getMinSearchTokenLength()}      */
specifier|public
name|boolean
name|hasSearchableLength
decl_stmt|;
comment|/**      * If the POS type of this word matches a linkable category      */
specifier|public
specifier|final
name|Boolean
name|isLinkablePos
decl_stmt|;
comment|/**      * if the POS type of this word matches a matchable category      */
specifier|public
specifier|final
name|Boolean
name|isMatchablePos
decl_stmt|;
comment|/**      * if this Token represents the start of an sub-sentence such as an       * starting ending quote       * @see ProcessingState#SUB_SENTENCE_START_POS      */
specifier|public
specifier|final
name|boolean
name|isSubSentenceStart
decl_stmt|;
comment|/**      * Constructs and initializes meta data needed for linking based       * on the current tokens (and its NLP annotation)      * @param index the index of the Token within the current section      * @param token the token      * @param chunk the current chunk or<code>null</code> if none      */
specifier|public
name|TokenData
parameter_list|(
name|LanguageProcessingConfig
name|tpc
parameter_list|,
name|int
name|index
parameter_list|,
name|Token
name|token
parameter_list|,
name|ChunkData
name|chunk
parameter_list|)
block|{
comment|//(0) init fields
name|this
operator|.
name|token
operator|=
name|token
expr_stmt|;
name|this
operator|.
name|index
operator|=
name|index
expr_stmt|;
name|this
operator|.
name|inChunk
operator|=
name|chunk
expr_stmt|;
name|this
operator|.
name|hasAlphaNumeric
operator|=
name|Utils
operator|.
name|hasAlphaNumericChar
argument_list|(
name|token
operator|.
name|getSpan
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|hasSearchableLength
operator|=
name|token
operator|.
name|getSpan
argument_list|()
operator|.
name|length
argument_list|()
operator|>=
name|tpc
operator|.
name|getMinSearchTokenLength
argument_list|()
expr_stmt|;
name|PosTag
name|selectedPosTag
init|=
literal|null
decl_stmt|;
name|boolean
name|matchedPosTag
init|=
literal|false
decl_stmt|;
comment|//matched any of the POS annotations
comment|//(1) check if this Token should be linked against the Vocabulary (isProcessable)
name|upperCase
operator|=
name|token
operator|.
name|getEnd
argument_list|()
operator|>
name|token
operator|.
name|getStart
argument_list|()
operator|&&
comment|//not an empty token
name|Character
operator|.
name|isUpperCase
argument_list|(
name|token
operator|.
name|getSpan
argument_list|()
operator|.
name|codePointAt
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
comment|//and upper case
name|boolean
name|isLinkablePos
init|=
literal|false
decl_stmt|;
name|boolean
name|isMatchablePos
init|=
literal|false
decl_stmt|;
name|boolean
name|isSubSentenceStart
init|=
literal|false
decl_stmt|;
name|List
argument_list|<
name|Value
argument_list|<
name|PosTag
argument_list|>
argument_list|>
name|posAnnotations
init|=
name|token
operator|.
name|getAnnotations
argument_list|(
name|POS_ANNOTATION
argument_list|)
decl_stmt|;
for|for
control|(
name|Value
argument_list|<
name|PosTag
argument_list|>
name|posAnnotation
range|:
name|posAnnotations
control|)
block|{
comment|// check three possible match
comment|//  1. the LexicalCategory matches
comment|//  2. the Pos matches
comment|//  3. the String tag matches
name|PosTag
name|posTag
init|=
name|posAnnotation
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
operator|!
name|disjoint
argument_list|(
name|tpc
operator|.
name|getLinkedLexicalCategories
argument_list|()
argument_list|,
name|posTag
operator|.
name|getCategories
argument_list|()
argument_list|)
operator|)
operator|||
operator|(
operator|!
name|disjoint
argument_list|(
name|tpc
operator|.
name|getLinkedPos
argument_list|()
argument_list|,
name|posTag
operator|.
name|getPosHierarchy
argument_list|()
argument_list|)
operator|)
operator|||
name|tpc
operator|.
name|getLinkedPosTags
argument_list|()
operator|.
name|contains
argument_list|(
name|posTag
operator|.
name|getTag
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|posAnnotation
operator|.
name|probability
argument_list|()
operator|==
name|Value
operator|.
name|UNKNOWN_PROBABILITY
operator|||
name|posAnnotation
operator|.
name|probability
argument_list|()
operator|>=
name|tpc
operator|.
name|getMinPosAnnotationProbability
argument_list|()
condition|)
block|{
name|selectedPosTag
operator|=
name|posTag
expr_stmt|;
name|isLinkablePos
operator|=
literal|true
expr_stmt|;
name|isMatchablePos
operator|=
literal|true
expr_stmt|;
name|matchedPosTag
operator|=
literal|true
expr_stmt|;
break|break;
block|}
comment|// else probability to low for inclusion
block|}
elseif|else
if|if
condition|(
name|posAnnotation
operator|.
name|probability
argument_list|()
operator|==
name|Value
operator|.
name|UNKNOWN_PROBABILITY
operator|||
name|posAnnotation
operator|.
name|probability
argument_list|()
operator|>=
name|tpc
operator|.
name|getMinExcludePosAnnotationProbability
argument_list|()
condition|)
block|{
name|selectedPosTag
operator|=
name|posTag
expr_stmt|;
comment|//also rejected PosTags are selected
name|matchedPosTag
operator|=
literal|true
expr_stmt|;
name|isLinkablePos
operator|=
literal|false
expr_stmt|;
break|break;
block|}
comment|// else probability to low for exclusion
block|}
if|if
condition|(
operator|!
name|matchedPosTag
condition|)
block|{
comment|//not matched against a POS Tag ...
name|this
operator|.
name|isLinkablePos
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|isLinkablePos
operator|=
name|isLinkablePos
expr_stmt|;
block|}
comment|//(2) check if this token should be considered to match labels of suggestions
if|if
condition|(
name|this
operator|.
name|isLinkablePos
operator|!=
literal|null
operator|&&
name|this
operator|.
name|isLinkablePos
condition|)
block|{
comment|//processable tokens are also matchable
name|this
operator|.
name|isMatchablePos
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
comment|//check POS and length to see if token is matchable
name|matchedPosTag
operator|=
literal|false
expr_stmt|;
comment|//reset to false!
for|for
control|(
name|Value
argument_list|<
name|PosTag
argument_list|>
name|posAnnotation
range|:
name|posAnnotations
control|)
block|{
name|PosTag
name|posTag
init|=
name|posAnnotation
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
name|posTag
operator|.
name|isMapped
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|Collections
operator|.
name|disjoint
argument_list|(
name|tpc
operator|.
name|getMatchedLexicalCategories
argument_list|()
argument_list|,
name|posTag
operator|.
name|getCategories
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|posAnnotation
operator|.
name|probability
argument_list|()
operator|==
name|Value
operator|.
name|UNKNOWN_PROBABILITY
operator|||
name|posAnnotation
operator|.
name|probability
argument_list|()
operator|>=
name|tpc
operator|.
name|getMinPosAnnotationProbability
argument_list|()
condition|)
block|{
comment|//override selectedPosTag if present
name|selectedPosTag
operator|=
name|posTag
expr_stmt|;
comment|//mark the matchable as selected PosTag
name|isMatchablePos
operator|=
literal|true
expr_stmt|;
name|matchedPosTag
operator|=
literal|true
expr_stmt|;
break|break;
block|}
comment|// else probability to low for inclusion
block|}
elseif|else
if|if
condition|(
name|posAnnotation
operator|.
name|probability
argument_list|()
operator|==
name|Value
operator|.
name|UNKNOWN_PROBABILITY
operator|||
name|posAnnotation
operator|.
name|probability
argument_list|()
operator|>=
name|tpc
operator|.
name|getMinExcludePosAnnotationProbability
argument_list|()
condition|)
block|{
if|if
condition|(
name|selectedPosTag
operator|==
literal|null
condition|)
block|{
comment|//do not override existing values
name|selectedPosTag
operator|=
name|posTag
expr_stmt|;
comment|//also rejected PosTags are selected
block|}
name|isMatchablePos
operator|=
literal|false
expr_stmt|;
name|matchedPosTag
operator|=
literal|true
expr_stmt|;
break|break;
block|}
comment|// else probability to low for exclusion
block|}
comment|//else not matched ... search next one
block|}
if|if
condition|(
operator|!
name|matchedPosTag
condition|)
block|{
comment|//not matched against POS tag ...
comment|//fall back to the token length
name|this
operator|.
name|isMatchablePos
operator|=
literal|null
expr_stmt|;
comment|//this.isMatchablePos = token.getSpan().length()>= tpc.getMinSearchTokenLength();
block|}
else|else
block|{
name|this
operator|.
name|isMatchablePos
operator|=
name|isMatchablePos
expr_stmt|;
block|}
block|}
comment|//(3) check if the POS tag indicates the start/end of an sub-sentence
for|for
control|(
name|Value
argument_list|<
name|PosTag
argument_list|>
name|posAnnotation
range|:
name|posAnnotations
control|)
block|{
name|PosTag
name|posTag
init|=
name|posAnnotation
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
operator|!
name|disjoint
argument_list|(
name|ProcessingState
operator|.
name|SUB_SENTENCE_START_POS
argument_list|,
name|posTag
operator|.
name|getPosHierarchy
argument_list|()
argument_list|)
operator|)
condition|)
block|{
if|if
condition|(
name|posAnnotation
operator|.
name|probability
argument_list|()
operator|==
name|Value
operator|.
name|UNKNOWN_PROBABILITY
operator|||
name|posAnnotation
operator|.
name|probability
argument_list|()
operator|>=
name|tpc
operator|.
name|getMinPosAnnotationProbability
argument_list|()
condition|)
block|{
name|isSubSentenceStart
operator|=
literal|true
expr_stmt|;
block|}
comment|// else probability to low for inclusion
block|}
elseif|else
if|if
condition|(
name|posAnnotation
operator|.
name|probability
argument_list|()
operator|==
name|Value
operator|.
name|UNKNOWN_PROBABILITY
operator|||
name|posAnnotation
operator|.
name|probability
argument_list|()
operator|>=
name|tpc
operator|.
name|getMinExcludePosAnnotationProbability
argument_list|()
condition|)
block|{
name|isSubSentenceStart
operator|=
literal|false
expr_stmt|;
block|}
block|}
name|this
operator|.
name|isSubSentenceStart
operator|=
name|isSubSentenceStart
expr_stmt|;
comment|//(4) check for morpho analyses
if|if
condition|(
name|selectedPosTag
operator|==
literal|null
condition|)
block|{
comment|//token is not processable or matchable
comment|//we need to set the selectedPoas tag to the first POS annotation
name|Value
argument_list|<
name|PosTag
argument_list|>
name|posAnnotation
init|=
name|token
operator|.
name|getAnnotation
argument_list|(
name|POS_ANNOTATION
argument_list|)
decl_stmt|;
if|if
condition|(
name|posAnnotation
operator|!=
literal|null
condition|)
block|{
name|selectedPosTag
operator|=
name|posAnnotation
operator|.
name|value
argument_list|()
expr_stmt|;
block|}
block|}
name|List
argument_list|<
name|Value
argument_list|<
name|MorphoFeatures
argument_list|>
argument_list|>
name|morphoAnnotations
init|=
name|token
operator|.
name|getAnnotations
argument_list|(
name|NlpAnnotations
operator|.
name|MORPHO_ANNOTATION
argument_list|)
decl_stmt|;
if|if
condition|(
name|selectedPosTag
operator|==
literal|null
condition|)
block|{
comment|//no POS information ... use the first morpho annotation
name|morpho
operator|=
name|morphoAnnotations
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|morphoAnnotations
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
else|else
block|{
comment|//select the correct morpho annotation based on the POS tag
name|MorphoFeatures
name|mf
init|=
literal|null
decl_stmt|;
name|selectMorphoFeature
label|:
for|for
control|(
name|Value
argument_list|<
name|MorphoFeatures
argument_list|>
name|morphoAnnotation
range|:
name|morphoAnnotations
control|)
block|{
for|for
control|(
name|PosTag
name|posTag
range|:
name|morphoAnnotation
operator|.
name|value
argument_list|()
operator|.
name|getPosList
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|disjoint
argument_list|(
name|selectedPosTag
operator|.
name|getCategories
argument_list|()
argument_list|,
name|posTag
operator|.
name|getCategories
argument_list|()
argument_list|)
condition|)
block|{
name|mf
operator|=
name|morphoAnnotation
operator|.
name|value
argument_list|()
expr_stmt|;
break|break
name|selectMorphoFeature
break|;
comment|//stop after finding the first one
block|}
block|}
block|}
name|morpho
operator|=
name|mf
expr_stmt|;
block|}
block|}
comment|/**      * Getter for token text      * @return the text of the token      */
specifier|public
name|String
name|getTokenText
parameter_list|()
block|{
return|return
name|token
operator|.
name|getSpan
argument_list|()
return|;
block|}
comment|/**      * Getter for the Lemma of the token.       * @return the Lemma of the Token or<code>null</code> if not available      */
specifier|public
name|String
name|getTokenLemma
parameter_list|()
block|{
return|return
name|morpho
operator|!=
literal|null
condition|?
name|morpho
operator|.
name|getLemma
argument_list|()
else|:
literal|null
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
literal|"TokenData: '"
argument_list|)
operator|.
name|append
argument_list|(
name|getTokenText
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"'[linkable="
argument_list|)
operator|.
name|append
argument_list|(
name|isLinkable
argument_list|)
operator|.
name|append
argument_list|(
literal|"(linkabkePos="
argument_list|)
operator|.
name|append
argument_list|(
name|isLinkablePos
argument_list|)
operator|.
name|append
argument_list|(
literal|")| matchable="
argument_list|)
operator|.
name|append
argument_list|(
name|isMatchable
argument_list|)
operator|.
name|append
argument_list|(
literal|"(matchablePos="
argument_list|)
operator|.
name|append
argument_list|(
name|isMatchablePos
argument_list|)
operator|.
name|append
argument_list|(
literal|")| alpha="
argument_list|)
operator|.
name|append
argument_list|(
name|hasAlphaNumeric
argument_list|)
operator|.
name|append
argument_list|(
literal|"| seachLength="
argument_list|)
operator|.
name|append
argument_list|(
name|hasSearchableLength
argument_list|)
operator|.
name|append
argument_list|(
literal|"| upperCase="
argument_list|)
operator|.
name|append
argument_list|(
name|upperCase
argument_list|)
operator|.
name|append
argument_list|(
literal|"]"
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit
