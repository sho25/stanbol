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
name|config
package|;
end_package

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
name|stanbol
operator|.
name|enhancer
operator|.
name|engines
operator|.
name|entitylinking
operator|.
name|EntitySearcher
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
name|Chunk
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
name|phrase
operator|.
name|PhraseTag
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
name|Pos
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

begin_class
specifier|public
class|class
name|LanguageProcessingConfig
implements|implements
name|Cloneable
block|{
comment|/**      * The linked Phrase types. Includes {@link LexicalCategory#Noun} phrases      */
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|DEFAULT_PROCESSED_PHRASE_CATEGORIES
init|=
name|EnumSet
operator|.
name|of
argument_list|(
name|LexicalCategory
operator|.
name|Noun
argument_list|)
decl_stmt|;
comment|/**      * The default set of {@link LexicalCategory LexicalCategories} used to      * lookup (link) Entities within the {@link EntitySearcher}      */
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|DEFAULT_LINKED_LEXICAL_CATEGORIES
init|=
name|EnumSet
operator|.
name|of
argument_list|(
name|LexicalCategory
operator|.
name|Noun
argument_list|,
name|LexicalCategory
operator|.
name|Residual
argument_list|)
decl_stmt|;
comment|/**      * The default set of {@link LexicalCategory LexicalCategories} used to      * match (and search) for Entities.<p>      * Matched Tokens are not used for linking, but are considered when matching      * label tokens of Entities with the Text.      */
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|DEFAULT_MATCHED_LEXICAL_CATEGORIES
init|=
name|EnumSet
operator|.
name|of
argument_list|(
name|LexicalCategory
operator|.
name|Noun
argument_list|,
name|LexicalCategory
operator|.
name|Quantifier
argument_list|,
name|LexicalCategory
operator|.
name|Residual
argument_list|)
decl_stmt|;
comment|/**      * The default set of {@link Pos} types that are used to lookup (link) Entities.      * By defualt only {@link Pos#ProperNoun}s and two       * {@link LexicalCategory#Residual} acronyms and      * words marked as foreign material.      */
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|Pos
argument_list|>
name|DEFAULT_LINKED_POS
init|=
name|EnumSet
operator|.
name|of
argument_list|(
name|Pos
operator|.
name|ProperNoun
argument_list|,
name|Pos
operator|.
name|Foreign
argument_list|,
name|Pos
operator|.
name|Acronym
argument_list|)
decl_stmt|;
comment|/**      * Default value for POS annotation confidence required for processed POS tags.      * Used for<ul>      *<li> {@link #getLinkedLexicalCategories()}      *<li> {@link #getLinkedPosTags()} and      *<li> {@link #getMatchedLexicalCategories()}      *<ul>      */
specifier|public
specifier|static
specifier|final
name|double
name|DEFAULT_MIN_POS_ANNOTATION_PROBABILITY
init|=
literal|0.75
decl_stmt|;
comment|/**      * Default value for POS annotation confidence required for not-processed POS tags      * (not contained in both {@link #getLinkedLexicalCategories()} and       * {@link #getLinkedPosTags()}).<br> The default is       *<code>{@link #DEFAULT_MIN_POS_ANNOTATION_PROBABILITY}/2</code>      */
specifier|public
specifier|static
specifier|final
name|double
name|DEFAULT_MIN_EXCLUDE_POS_ANNOTATION_PROBABILITY
init|=
name|DEFAULT_MIN_POS_ANNOTATION_PROBABILITY
operator|/
literal|2
decl_stmt|;
comment|/**      * By default {@link Chunk}s are considered      */
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_IGNORE_CHUNK_STATE
init|=
literal|false
decl_stmt|;
comment|/**      * the minimum probability so that a phrase in processed based on the Phrase Annotation      */
specifier|public
specifier|static
specifier|final
name|double
name|DEFAULT_MIN_PHRASE_ANNOTATION_PROBABILITY
init|=
literal|0.75
decl_stmt|;
comment|/**      * the minimum probability so that a phrase is rejected based on the Phrase Annotation      */
specifier|public
specifier|static
specifier|final
name|double
name|DEFAULT_MIN_EXCLUDE_PHRASE_ANNOTATION_PROBABILITY
init|=
name|DEFAULT_MIN_PHRASE_ANNOTATION_PROBABILITY
operator|/
literal|2
decl_stmt|;
comment|/**      * The default for linking upper case tokens (regardless of length and POS)      * The default is<code>false</code> as some languages (like German) use upper      * case for Nouns and so this would also affect configurations that only      * link {@link Pos#ProperNoun}s      */
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_LINK_UPPER_CASE_TOKEN_STATE
init|=
literal|false
decl_stmt|;
comment|/**      * The default for matching upper case tokens (regardless of length and POS)      * is<code>true</code>      */
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_MATCH_UPPER_CASE_TOKEN_STATE
init|=
literal|true
decl_stmt|;
comment|/**      * By default linking of chunks with multiple matchable tokens is enabled.      * This is useful to link Entities represented by two common nouns.        */
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_LINK_MULTIPLE_MATCHABLE_TOKENS_IN_CHUNKS_STATE
init|=
literal|true
decl_stmt|;
comment|/**      * The set of {@link PosTag#getCategory()} considered for EntityLinking      * @see #DEFAULT_LINKED_LEXICAL_CATEGORIES      */
specifier|private
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|linkedLexicalCategories
init|=
name|DEFAULT_LINKED_LEXICAL_CATEGORIES
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|matchedLexicalCategories
init|=
name|DEFAULT_MATCHED_LEXICAL_CATEGORIES
decl_stmt|;
comment|/**      * The linked {@link Pos} categories      */
specifier|private
name|Set
argument_list|<
name|Pos
argument_list|>
name|linkedPos
init|=
name|DEFAULT_LINKED_POS
decl_stmt|;
comment|/**      * The set of {@link PosTag#getTag()} values that are processed      */
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|linkedPosTags
init|=
name|Collections
operator|.
name|emptySet
argument_list|()
decl_stmt|;
comment|/**      * The minimum confidence of POS annotations for {@link #getLinkedLexicalCategories()}      * and {@link #getLinkedPosTags()}      */
specifier|private
name|double
name|minPosAnnotationProbability
init|=
name|DEFAULT_MIN_POS_ANNOTATION_PROBABILITY
decl_stmt|;
comment|/**      * The minimum confidence that a POS annotation       */
specifier|private
name|double
name|minExcludePosAnnotationProbability
init|=
name|DEFAULT_MIN_EXCLUDE_POS_ANNOTATION_PROBABILITY
decl_stmt|;
specifier|private
name|boolean
name|ignoreChunksState
init|=
name|DEFAULT_IGNORE_CHUNK_STATE
decl_stmt|;
specifier|private
name|double
name|minPhraseAnnotationProbability
init|=
name|DEFAULT_MIN_PHRASE_ANNOTATION_PROBABILITY
decl_stmt|;
specifier|private
name|double
name|minExcludePhraseAnnotationProbability
init|=
name|DEFAULT_MIN_EXCLUDE_PHRASE_ANNOTATION_PROBABILITY
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|processedPhraseCategories
init|=
name|DEFAULT_PROCESSED_PHRASE_CATEGORIES
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|processedPhraseTags
init|=
name|Collections
operator|.
name|emptySet
argument_list|()
decl_stmt|;
comment|/**      * If upper case tokens are linked (and matched)      */
specifier|private
name|boolean
name|linkUpperCaseTokensState
init|=
name|DEFAULT_LINK_UPPER_CASE_TOKEN_STATE
decl_stmt|;
comment|/**      * If upper case tokens are matched      */
specifier|private
name|boolean
name|matchUpperCaseTokensState
init|=
name|DEFAULT_MATCH_UPPER_CASE_TOKEN_STATE
decl_stmt|;
comment|/**      * If for {@link Chunk}s with multiple matchable Tokens those should be      * linked.      */
specifier|private
name|boolean
name|linkMultiMatchableTokensInChunkState
init|=
name|DEFAULT_LINK_MULTIPLE_MATCHABLE_TOKENS_IN_CHUNKS_STATE
decl_stmt|;
specifier|private
name|int
name|minSearchTokenLength
decl_stmt|;
specifier|private
name|boolean
name|linkOnlyUpperCaseTokenWithUnknownPos
decl_stmt|;
comment|/**      * The language or<code>null</code> for the default configuration      * @param language      */
specifier|public
name|LanguageProcessingConfig
parameter_list|()
block|{     }
specifier|public
specifier|final
name|boolean
name|isIgnoreChunks
parameter_list|()
block|{
return|return
name|ignoreChunksState
return|;
block|}
comment|/**      * Setter for the ignore {@link Chunk} state.      * @param state the state or<code>null</code> to set the       * {@link #DEFAULT_IGNORE_CHUNK_STATE}      */
specifier|public
specifier|final
name|void
name|setIgnoreChunksState
parameter_list|(
name|Boolean
name|state
parameter_list|)
block|{
if|if
condition|(
name|state
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|ignoreChunksState
operator|=
name|DEFAULT_IGNORE_CHUNK_STATE
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|ignoreChunksState
operator|=
name|state
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the set of {@link LexicalCategory LexicalCategories} used       * to link Entities in the configured Vocabulary.      * @return the set of {@link LexicalCategory LexicalCategories} used       * for linking.      * @see #DEFAULT_LINKED_LEXICAL_CATEGORIES      */
specifier|public
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|getLinkedLexicalCategories
parameter_list|()
block|{
return|return
name|linkedLexicalCategories
return|;
block|}
comment|/**      * Getter for the set of {@link LexicalCategory LexicalCategories} used      * to match label tokens of suggested Entities.      * @return the set of {@link LexicalCategory LexicalCategories} used for      * matching      */
specifier|public
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|getMatchedLexicalCategories
parameter_list|()
block|{
return|return
name|matchedLexicalCategories
return|;
block|}
comment|/**      * Setter for the matched lexical categories      * @param matchedLexicalCategories the set or<code>null</code>      * to set the {@link #DEFAULT_MATCHED_LEXICAL_CATEGORIES}      */
specifier|public
name|void
name|setMatchedLexicalCategories
parameter_list|(
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|matchedLexicalCategories
parameter_list|)
block|{
if|if
condition|(
name|matchedLexicalCategories
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|matchedLexicalCategories
operator|=
name|DEFAULT_MATCHED_LEXICAL_CATEGORIES
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|matchedLexicalCategories
operator|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|LexicalCategory
operator|.
name|class
argument_list|)
expr_stmt|;
name|this
operator|.
name|matchedLexicalCategories
operator|.
name|addAll
argument_list|(
name|matchedLexicalCategories
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * The set of tags used for linking. This is useful if the string tags      * used by the POS tagger are not mapped to {@link LexicalCategory} nor      * {@link Pos} enum members.       * @return the set of pos tags used for linking entities      */
specifier|public
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|getLinkedPosTags
parameter_list|()
block|{
return|return
name|linkedPosTags
return|;
block|}
comment|/**      * Getter for the minimum probability of POS annotations for       * {@link #getLinkedLexicalCategories()} or {@link #getLinkedPosTags()}      * @return the probability      */
specifier|public
specifier|final
name|double
name|getMinPosAnnotationProbability
parameter_list|()
block|{
return|return
name|minPosAnnotationProbability
return|;
block|}
comment|/**      * Getter for the minimum probability of POS annotations not included in       * {@link #getLinkedLexicalCategories()} or {@link #getLinkedPosTags()}      * @return the probability      */
specifier|public
specifier|final
name|double
name|getMinExcludePosAnnotationProbability
parameter_list|()
block|{
return|return
name|minExcludePosAnnotationProbability
return|;
block|}
comment|/**      * Setter for the minimum probability of POS annotations for       * {@link #getLinkedLexicalCategories()} or {@link #getLinkedPosTags()}      * @param minPosAnnotationProbability the probability or<code>null</code> to set      * {@value #DEFAULT_MIN_POS_ANNOTATION_PROBABILITY}      */
specifier|public
specifier|final
name|void
name|setMinPosAnnotationProbability
parameter_list|(
name|Double
name|minPosAnnotationProbability
parameter_list|)
block|{
if|if
condition|(
name|minPosAnnotationProbability
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|minPosAnnotationProbability
operator|=
name|DEFAULT_MIN_POS_ANNOTATION_PROBABILITY
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|minPosAnnotationProbability
operator|>=
literal|0
operator|&&
name|minPosAnnotationProbability
operator|<=
literal|1
condition|)
block|{
name|this
operator|.
name|minPosAnnotationProbability
operator|=
name|minPosAnnotationProbability
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"parsed value MUST BE in the range 0..1 or NULL to set the default"
argument_list|)
throw|;
block|}
block|}
comment|/**      * Setter for the minimum probability of POS annotations not included in       * {@link #getLinkedLexicalCategories()} or {@link #getLinkedPosTags()}      * @param minExcludePosAnnotationProbability the probability or<code>null</code> to set      * {@value #DEFAULT_MIN_EXCLUDE_POS_ANNOTATION_PROBABILITY}      */
specifier|public
specifier|final
name|void
name|setMinExcludePosAnnotationProbability
parameter_list|(
name|Double
name|minExcludePosAnnotationProbability
parameter_list|)
block|{
if|if
condition|(
name|minExcludePosAnnotationProbability
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|minExcludePosAnnotationProbability
operator|=
name|DEFAULT_MIN_EXCLUDE_POS_ANNOTATION_PROBABILITY
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|minExcludePosAnnotationProbability
operator|>=
literal|0
operator|&&
name|minExcludePosAnnotationProbability
operator|<=
literal|1
condition|)
block|{
name|this
operator|.
name|minExcludePosAnnotationProbability
operator|=
name|minExcludePosAnnotationProbability
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"parsed value MUST BE in the range 0..1 or NULL to set the default"
argument_list|)
throw|;
block|}
block|}
comment|/**      * Setter for the linked {@link LexicalCategory LexicalCategories}      * @param linkedLexicalCategories the set or<code>null</code> to set      * the {@link #DEFAULT_LINKED_LEXICAL_CATEGORIES}.      */
specifier|public
specifier|final
name|void
name|setLinkedLexicalCategories
parameter_list|(
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|linkedLexicalCategories
parameter_list|)
block|{
if|if
condition|(
name|linkedLexicalCategories
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|linkedLexicalCategories
operator|=
name|DEFAULT_LINKED_LEXICAL_CATEGORIES
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|linkedLexicalCategories
operator|.
name|contains
argument_list|(
literal|null
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed set with linked LexicalCategories MUST NOT contain the NULL element!"
argument_list|)
throw|;
block|}
else|else
block|{
name|this
operator|.
name|linkedLexicalCategories
operator|=
name|linkedLexicalCategories
expr_stmt|;
block|}
block|}
comment|/**      * Setter for the linked {@link Pos} types.      * @param linkedLexicalCategories the set of linked {@link Pos} types or<code>null</code>      * to set the {@link #DEFAULT_LINKED_POS} types      */
specifier|public
specifier|final
name|void
name|setLinkedPos
parameter_list|(
name|Set
argument_list|<
name|Pos
argument_list|>
name|linkedPos
parameter_list|)
block|{
if|if
condition|(
name|linkedPos
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|linkedPos
operator|=
name|DEFAULT_LINKED_POS
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|linkedPos
operator|.
name|contains
argument_list|(
literal|null
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed set with linked LexicalCategories MUST NOT contain the NULL element!"
argument_list|)
throw|;
block|}
else|else
block|{
name|this
operator|.
name|linkedPos
operator|=
name|linkedPos
expr_stmt|;
block|}
block|}
comment|/**      * Setter for the linked Pos Tags. This should only be used of the       * used POS tagger uses {@link PosTag}s that are not mapped to      * {@link LexicalCategory LexicalCategories} nor {@link Pos} types.      * @param processedPosTags the linked Pos tags. if<code>null</code>      * the value is set to an empty set.      */
specifier|public
specifier|final
name|void
name|setLinkedPosTags
parameter_list|(
name|Set
argument_list|<
name|String
argument_list|>
name|processedPosTags
parameter_list|)
block|{
if|if
condition|(
name|processedPosTags
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|linkedPosTags
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|processedPosTags
operator|.
name|contains
argument_list|(
literal|null
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed set with processed POS tags MUST NOT contain the NULL element!"
argument_list|)
throw|;
block|}
else|else
block|{
name|this
operator|.
name|linkedPosTags
operator|=
name|processedPosTags
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the processed phrase categories.      * {@link Chunk}s of other types will be ignored.      * @return      */
specifier|public
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|getProcessedPhraseCategories
parameter_list|()
block|{
return|return
name|processedPhraseCategories
return|;
block|}
comment|/**      * Setter for the processable phrase categories.       * @param processablePhraseCategories the processable categories or      *<code>null</code> to set the {@link #DEFAULT_PROCESSED_PHRASE_CATEGORIES}.      */
specifier|public
name|void
name|setProcessedPhraseCategories
parameter_list|(
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|processablePhraseCategories
parameter_list|)
block|{
if|if
condition|(
name|processablePhraseCategories
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|processedPhraseCategories
operator|=
name|DEFAULT_PROCESSED_PHRASE_CATEGORIES
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|processedPhraseCategories
operator|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|LexicalCategory
operator|.
name|class
argument_list|)
expr_stmt|;
name|this
operator|.
name|processedPhraseCategories
operator|.
name|addAll
argument_list|(
name|processablePhraseCategories
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the prococessed phrase Tags. This should be only      * used if the {@link PhraseTag}s used by the Chunker are not      * mapped to {@link LexicalCategory LexicalCategories}.      * @return the processed phrase tags      */
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getProcessedPhraseTags
parameter_list|()
block|{
return|return
name|processedPhraseTags
return|;
block|}
comment|/**      * Setter for the Processed Phrase Tags      * @param processedPhraseTags the set with the tags. If<code>null</code>      * the value is set to an empty set.      */
specifier|public
name|void
name|setProcessedPhraseTags
parameter_list|(
name|Set
argument_list|<
name|String
argument_list|>
name|processedPhraseTags
parameter_list|)
block|{
if|if
condition|(
name|processedPhraseTags
operator|==
literal|null
operator|||
name|processedPhraseTags
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|this
operator|.
name|processedPhraseTags
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|processedPhraseTags
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|processedPhraseTags
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the minimum required probability so that {@link PhraseTag}s      * are accepted.      * @return the probability [0..1)      */
specifier|public
name|double
name|getMinPhraseAnnotationProbability
parameter_list|()
block|{
return|return
name|minPhraseAnnotationProbability
return|;
block|}
comment|/**      * Getter for the minimum required probability so that {@link PhraseTag}s      * are considered for rejecting (e.g. to skip a VerbPhrase if       * {@link LexicalCategory#Verb} is not present in       * {@link #getProcessedPhraseCategories()}). Typically this value is      * lower as {@link #getMinPhraseAnnotationProbability()}      * @return the probability [0..1)      */
specifier|public
name|double
name|getMinExcludePhraseAnnotationProbability
parameter_list|()
block|{
return|return
name|minExcludePhraseAnnotationProbability
return|;
block|}
comment|/**      * Setter for the minimum phrase annotation probability [0..1)      * @param prob the probability [0..1) or<code>null</code> to set      * the {@value #DEFAULT_MIN_PHRASE_ANNOTATION_PROBABILITY}      * @throws IllegalArgumentException if the parsed value is not      * in the range [0..1).      */
specifier|public
name|void
name|setMinPhraseAnnotationProbability
parameter_list|(
name|Double
name|prob
parameter_list|)
block|{
if|if
condition|(
name|prob
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|minPhraseAnnotationProbability
operator|=
name|DEFAULT_MIN_PHRASE_ANNOTATION_PROBABILITY
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prob
operator|>=
literal|1
operator|||
name|prob
operator|<
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed minimum phrase annotation probability '"
operator|+
name|prob
operator|+
literal|" MUST be in the range [0..1)!"
argument_list|)
throw|;
block|}
else|else
block|{
name|this
operator|.
name|minPhraseAnnotationProbability
operator|=
name|prob
expr_stmt|;
block|}
block|}
comment|/**      * Setter for the minimum excluded phrase annotation probability [0..1)      * @param prob the probability [0..1) or<code>null</code> to set      * the {@value #DEFAULT_MIN_EXCLUDE_PHRASE_ANNOTATION_PROBABILITY}      * @throws IllegalArgumentException if the parsed value is not      * in the range [0..1).      */
specifier|public
name|void
name|setMinExcludePhraseAnnotationProbability
parameter_list|(
name|Double
name|prob
parameter_list|)
block|{
if|if
condition|(
name|prob
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|minExcludePhraseAnnotationProbability
operator|=
name|DEFAULT_MIN_EXCLUDE_PHRASE_ANNOTATION_PROBABILITY
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prob
operator|>=
literal|1
operator|||
name|prob
operator|<
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed minimum exclude phrase annotation probability '"
operator|+
name|prob
operator|+
literal|" MUST be in the range [0..1)!"
argument_list|)
throw|;
block|}
else|else
block|{
name|this
operator|.
name|minExcludePhraseAnnotationProbability
operator|=
name|prob
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the set of {@link Pos} types used for linking Entities      * @return the linked {@link Pos} types      */
specifier|public
name|Set
argument_list|<
name|Pos
argument_list|>
name|getLinkedPos
parameter_list|()
block|{
return|return
name|linkedPos
return|;
block|}
comment|/**      * If upper case Tokens should be linked regardless      * of the POS type and length      * @return      */
specifier|public
name|boolean
name|isLinkUpperCaseTokens
parameter_list|()
block|{
return|return
name|linkUpperCaseTokensState
return|;
block|}
comment|/**      * Setter for the state if upper case token should be      * linked regardless of the POS type and length      * @param linkUpperCaseTokensState the state or<code>null</code>      * to set the {@link #DEFAULT_LINK_UPPER_CASE_TOKEN_STATE}      */
specifier|public
name|void
name|setLinkUpperCaseTokensState
parameter_list|(
name|Boolean
name|linkUpperCaseTokensState
parameter_list|)
block|{
if|if
condition|(
name|linkUpperCaseTokensState
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|linkUpperCaseTokensState
operator|=
name|DEFAULT_LINK_UPPER_CASE_TOKEN_STATE
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|linkUpperCaseTokensState
operator|=
name|linkUpperCaseTokensState
expr_stmt|;
block|}
block|}
comment|/**      * If upper case Tokens should be matched regardless      * of the POS type and length      * @return      */
specifier|public
name|boolean
name|isMatchUpperCaseTokens
parameter_list|()
block|{
return|return
name|matchUpperCaseTokensState
return|;
block|}
comment|/**      * Setter for the state if upper case token should be      * matched regardless of the POS type and length      * @param matchUpperCaseTokensState the state or<code>null</code>      * to set the {@link #DEFAULT_MATCH_UPPER_CASE_TOKEN_STATE}      */
specifier|public
name|void
name|setMatchUpperCaseTokensState
parameter_list|(
name|Boolean
name|matchUpperCaseTokensState
parameter_list|)
block|{
if|if
condition|(
name|matchUpperCaseTokensState
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|matchUpperCaseTokensState
operator|=
name|DEFAULT_MATCH_UPPER_CASE_TOKEN_STATE
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|matchUpperCaseTokensState
operator|=
name|matchUpperCaseTokensState
expr_stmt|;
block|}
block|}
comment|/**      * If {@link #isIgnoreChunks()} is disabled than this allows      * to convert matchable {@link Token}s to linked one in       * case a {@link Chunk} contains more than one matchable      * Token.<p>      * This is especially useful in cases where only      * {@link Pos#ProperNoun}s are processed to also detect      * Entities that are named by using multiple Common Nouns.      * In cases where all {@link LexicalCategory#Noun}s are      * processed this option has usually no influence on the      * results.      * @return the state      */
specifier|public
name|boolean
name|isLinkMultiMatchableTokensInChunk
parameter_list|()
block|{
return|return
name|linkMultiMatchableTokensInChunkState
return|;
block|}
comment|/**      * Setter for state if for {@link Chunk}s with multiple       * matchable {@link Token}s those Tokens should be treated      * as linkable.<p>      * This is especially useful in cases where only      * {@link Pos#ProperNoun}s are linked to also detect      * Entities that are named by using multiple Common Nouns.      * In cases where all {@link LexicalCategory#Noun}s are      * processed this option has usually no influence on the      * results.      * @param state the state or<code>null</code> to reset to the      * the {@link #DEFAULT_LINK_MULTIPLE_MATCHABLE_TOKENS_IN_CHUNKS_STATE default}      */
specifier|public
name|void
name|setLinkMultiMatchableTokensInChunkState
parameter_list|(
name|Boolean
name|state
parameter_list|)
block|{
if|if
condition|(
name|state
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|linkMultiMatchableTokensInChunkState
operator|=
name|DEFAULT_LINK_MULTIPLE_MATCHABLE_TOKENS_IN_CHUNKS_STATE
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|linkMultiMatchableTokensInChunkState
operator|=
name|state
expr_stmt|;
block|}
block|}
comment|/**      * The minimum number of character a {@link Token} (word) must have to be      * used {@link EntitySearcher#lookup(java.util.List, String...) lookup} concepts      * in the taxonomy. Note that this parameter is only used of no POS (Part-      * of-speech) tags are available in the {@link AnalysedText}.      * @param minSearchTokenLength the minSearchTokenLength to set      */
specifier|public
name|void
name|setMinSearchTokenLength
parameter_list|(
name|int
name|minSearchTokenLength
parameter_list|)
block|{
name|this
operator|.
name|minSearchTokenLength
operator|=
name|minSearchTokenLength
expr_stmt|;
block|}
comment|/**      * The minimum number of character a {@link Token} (word) must have to be      * used {@link EntitySearcher#lookup(java.util.List, String...) lookup} concepts      * in the taxonomy. Note that this parameter is only used of no POS (Part-      * of-speech) tags are available in the {@link AnalysedText}.      * @return the minSearchTokenLength      */
specifier|public
name|int
name|getMinSearchTokenLength
parameter_list|()
block|{
return|return
name|minSearchTokenLength
return|;
block|}
comment|/**      * This returns the state if only upper case tokens should be marked as       * 'linkable' if they do not have a POS tag      * @return the state      */
specifier|public
name|boolean
name|isLinkOnlyUpperCaseTokensWithUnknownPos
parameter_list|()
block|{
return|return
name|linkOnlyUpperCaseTokenWithUnknownPos
return|;
block|}
comment|/**      * This returns the state if only upper case tokens should be marked as       * 'linkable' if they do not have a POS tag      * @param linkOnlyUpperCaseTokenWithUnknownPos the state      */
specifier|public
name|void
name|setLinkOnlyUpperCaseTokenWithUnknownPos
parameter_list|(
name|boolean
name|linkOnlyUpperCaseTokenWithUnknownPos
parameter_list|)
block|{
name|this
operator|.
name|linkOnlyUpperCaseTokenWithUnknownPos
operator|=
name|linkOnlyUpperCaseTokenWithUnknownPos
expr_stmt|;
block|}
comment|/**      * Clones the {@link LanguageProcessingConfig}. Intended to be used      * to create language specific configs based on the default one.      */
annotation|@
name|Override
specifier|public
name|LanguageProcessingConfig
name|clone
parameter_list|()
block|{
name|LanguageProcessingConfig
name|c
init|=
operator|new
name|LanguageProcessingConfig
argument_list|()
decl_stmt|;
name|c
operator|.
name|ignoreChunksState
operator|=
name|ignoreChunksState
expr_stmt|;
name|c
operator|.
name|minExcludePhraseAnnotationProbability
operator|=
name|minExcludePhraseAnnotationProbability
expr_stmt|;
name|c
operator|.
name|minExcludePosAnnotationProbability
operator|=
name|minExcludePosAnnotationProbability
expr_stmt|;
name|c
operator|.
name|minPhraseAnnotationProbability
operator|=
name|minPhraseAnnotationProbability
expr_stmt|;
name|c
operator|.
name|minPosAnnotationProbability
operator|=
name|minPosAnnotationProbability
expr_stmt|;
name|c
operator|.
name|linkedLexicalCategories
operator|=
name|linkedLexicalCategories
expr_stmt|;
name|c
operator|.
name|processedPhraseCategories
operator|=
name|processedPhraseCategories
expr_stmt|;
name|c
operator|.
name|processedPhraseTags
operator|=
name|processedPhraseTags
expr_stmt|;
name|c
operator|.
name|linkedPos
operator|=
name|linkedPos
expr_stmt|;
name|c
operator|.
name|linkedPosTags
operator|=
name|linkedPosTags
expr_stmt|;
name|c
operator|.
name|linkUpperCaseTokensState
operator|=
name|linkUpperCaseTokensState
expr_stmt|;
name|c
operator|.
name|matchUpperCaseTokensState
operator|=
name|matchUpperCaseTokensState
expr_stmt|;
name|c
operator|.
name|linkMultiMatchableTokensInChunkState
operator|=
name|linkMultiMatchableTokensInChunkState
expr_stmt|;
name|c
operator|.
name|matchedLexicalCategories
operator|=
name|matchedLexicalCategories
expr_stmt|;
name|c
operator|.
name|minSearchTokenLength
operator|=
name|minSearchTokenLength
expr_stmt|;
name|c
operator|.
name|linkOnlyUpperCaseTokenWithUnknownPos
operator|=
name|linkOnlyUpperCaseTokenWithUnknownPos
expr_stmt|;
return|return
name|c
return|;
block|}
block|}
end_class

end_unit

