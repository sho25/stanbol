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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|Span
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
name|SpanTypeEnum
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

begin_class
specifier|public
class|class
name|SectionData
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SectionData
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * The section      */
specifier|public
specifier|final
name|Section
name|section
decl_stmt|;
comment|/**      * Holds the {@link Token}s of the current {@link #sentence}       * to allow fast index based access.      */
specifier|private
name|List
argument_list|<
name|TokenData
argument_list|>
name|tokens
init|=
operator|new
name|ArrayList
argument_list|<
name|TokenData
argument_list|>
argument_list|(
literal|64
argument_list|)
decl_stmt|;
comment|/**      * If a linkable token is present in this section      */
specifier|private
name|boolean
name|hasLinkableToken
init|=
literal|false
decl_stmt|;
specifier|public
name|SectionData
parameter_list|(
name|LanguageProcessingConfig
name|tpc
parameter_list|,
name|Section
name|section
parameter_list|,
name|Set
argument_list|<
name|SpanTypeEnum
argument_list|>
name|enclosedSpanTypes
parameter_list|,
name|boolean
name|isUnicaseLanguage
parameter_list|)
block|{
name|this
operator|.
name|section
operator|=
name|section
expr_stmt|;
name|Iterator
argument_list|<
name|Span
argument_list|>
name|enclosed
init|=
name|section
operator|.
name|getEnclosed
argument_list|(
name|enclosedSpanTypes
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|ChunkData
argument_list|>
name|activeChunks
init|=
operator|new
name|ArrayList
argument_list|<
name|ChunkData
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|enclosed
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Span
name|span
init|=
name|enclosed
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|span
operator|.
name|getStart
argument_list|()
operator|>=
name|span
operator|.
name|getEnd
argument_list|()
condition|)
block|{
comment|//save guard against empty spans
name|log
operator|.
name|warn
argument_list|(
literal|"Detected Empty Span {} in section {}: '{}'"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|span
block|,
name|section
block|,
name|section
operator|.
name|getSpan
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|span
operator|.
name|getType
argument_list|()
operator|==
name|SpanTypeEnum
operator|.
name|Chunk
condition|)
block|{
name|ChunkData
name|chunkData
init|=
operator|new
name|ChunkData
argument_list|(
name|tpc
argument_list|,
operator|(
name|Chunk
operator|)
name|span
argument_list|)
decl_stmt|;
if|if
condition|(
name|chunkData
operator|.
name|isProcessable
argument_list|()
condition|)
block|{
name|activeChunks
operator|.
name|add
argument_list|(
literal|0
argument_list|,
name|chunkData
argument_list|)
expr_stmt|;
name|chunkData
operator|.
name|startToken
operator|=
name|tokens
operator|.
name|size
argument_list|()
expr_stmt|;
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|">> Chunk: (type:{}, startPos: {}) text: '{}'"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|chunkData
operator|.
name|chunk
operator|.
name|getType
argument_list|()
block|,
name|chunkData
operator|.
name|startToken
block|,
name|chunkData
operator|.
name|chunk
operator|.
name|getSpan
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
block|}
comment|//else ignore chunks that are not processable
block|}
elseif|else
if|if
condition|(
name|span
operator|.
name|getType
argument_list|()
operator|==
name|SpanTypeEnum
operator|.
name|Token
condition|)
block|{
name|TokenData
name|tokenData
init|=
operator|new
name|TokenData
argument_list|(
name|tpc
argument_list|,
name|tokens
operator|.
name|size
argument_list|()
argument_list|,
operator|(
name|Token
operator|)
name|span
argument_list|,
name|activeChunks
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|activeChunks
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"> {}: {} {}(pos:{}) chunk: '{}'"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|tokenData
operator|.
name|index
block|,
name|tokenData
operator|.
name|token
block|,
name|tokenData
operator|.
name|morpho
operator|!=
literal|null
condition|?
operator|(
literal|"(lemma: "
operator|+
name|tokenData
operator|.
name|morpho
operator|.
name|getLemma
argument_list|()
operator|+
literal|") "
operator|)
else|:
literal|""
block|,
name|tokenData
operator|.
name|token
operator|.
name|getAnnotations
argument_list|(
name|POS_ANNOTATION
argument_list|)
block|,
name|tokenData
operator|.
name|inChunk
operator|!=
literal|null
condition|?
name|tokenData
operator|.
name|inChunk
operator|.
name|chunk
operator|.
name|getSpan
argument_list|()
else|:
literal|"none"
block|}
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|tokenData
operator|.
name|hasAlphaNumeric
condition|)
block|{
name|tokenData
operator|.
name|isLinkable
operator|=
literal|false
expr_stmt|;
name|tokenData
operator|.
name|isMatchable
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
comment|// (1) apply basic rules for linkable/processable tokens
comment|//determine if the token should be linked/matched
name|tokenData
operator|.
name|isLinkable
operator|=
name|tokenData
operator|.
name|isLinkablePos
operator|!=
literal|null
condition|?
name|tokenData
operator|.
name|isLinkablePos
else|:
literal|false
expr_stmt|;
comment|//matchabel := linkable OR has matchablePos
name|tokenData
operator|.
name|isMatchable
operator|=
name|tokenData
operator|.
name|isLinkable
operator|||
operator|(
name|tokenData
operator|.
name|isMatchablePos
operator|!=
literal|null
operator|&&
name|tokenData
operator|.
name|isMatchablePos
operator|)
expr_stmt|;
comment|//(2) for non linkable tokens check for upper case rules
if|if
condition|(
operator|!
name|tokenData
operator|.
name|isLinkable
operator|&&
name|tokenData
operator|.
name|upperCase
operator|&&
name|tokenData
operator|.
name|index
operator|>
literal|0
operator|&&
comment|//not a sentence or sub-sentence start
operator|!
name|tokens
operator|.
name|get
argument_list|(
name|tokenData
operator|.
name|index
operator|-
literal|1
argument_list|)
operator|.
name|isSubSentenceStart
condition|)
block|{
comment|//We have an upper case token!
if|if
condition|(
name|tpc
operator|.
name|isLinkUpperCaseTokens
argument_list|()
condition|)
block|{
if|if
condition|(
name|tokenData
operator|.
name|isMatchable
condition|)
block|{
comment|//convert matchable to
name|tokenData
operator|.
name|isLinkable
operator|=
literal|true
expr_stmt|;
comment|//linkable
name|tokenData
operator|.
name|isMatchable
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
comment|// and other tokens to
name|tokenData
operator|.
name|isMatchable
operator|=
literal|true
expr_stmt|;
comment|//matchable
block|}
block|}
else|else
block|{
comment|//finally we need to convert other Tokens to matchable
comment|//if MatchUpperCaseTokens is active
if|if
condition|(
operator|!
name|tokenData
operator|.
name|isMatchable
operator|&&
name|tpc
operator|.
name|isMatchUpperCaseTokens
argument_list|()
condition|)
block|{
name|tokenData
operator|.
name|isMatchable
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
comment|//else not an upper case token
comment|//(3) Unknown POS tag Rules (see STANBOL-1049)
if|if
condition|(
operator|!
name|tokenData
operator|.
name|isLinkable
operator|&&
operator|(
name|tokenData
operator|.
name|isLinkablePos
operator|==
literal|null
operator|||
name|tokenData
operator|.
name|isMatchablePos
operator|==
literal|null
operator|)
condition|)
block|{
if|if
condition|(
name|isUnicaseLanguage
operator|||
operator|!
name|tpc
operator|.
name|isLinkOnlyUpperCaseTokensWithUnknownPos
argument_list|()
condition|)
block|{
if|if
condition|(
name|tokenData
operator|.
name|isLinkablePos
operator|==
literal|null
operator|&&
name|tokenData
operator|.
name|hasSearchableLength
condition|)
block|{
name|tokenData
operator|.
name|isLinkable
operator|=
literal|true
expr_stmt|;
name|tokenData
operator|.
name|isMatchable
operator|=
literal|true
expr_stmt|;
block|}
comment|//else no need to change the state
block|}
else|else
block|{
comment|//non unicase language and link only upper case tokens enabled
if|if
condition|(
name|tokenData
operator|.
name|upperCase
operator|&&
comment|// upper case token
name|tokenData
operator|.
name|index
operator|>
literal|0
operator|&&
comment|//not a sentence or sub-sentence start
operator|!
name|tokens
operator|.
name|get
argument_list|(
name|tokenData
operator|.
name|index
operator|-
literal|1
argument_list|)
operator|.
name|isSubSentenceStart
condition|)
block|{
if|if
condition|(
name|tokenData
operator|.
name|hasSearchableLength
operator|&&
name|tokenData
operator|.
name|isLinkablePos
operator|==
literal|null
condition|)
block|{
name|tokenData
operator|.
name|isLinkable
operator|=
literal|true
expr_stmt|;
name|tokenData
operator|.
name|isMatchable
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tokenData
operator|.
name|isMatchablePos
operator|==
literal|null
condition|)
block|{
name|tokenData
operator|.
name|isMatchable
operator|=
literal|true
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|tokenData
operator|.
name|hasSearchableLength
operator|&&
comment|//lower case and long token
name|tokenData
operator|.
name|isMatchablePos
operator|==
literal|null
condition|)
block|{
name|tokenData
operator|.
name|isMatchable
operator|=
literal|true
expr_stmt|;
block|}
comment|//else lower case and short word
block|}
block|}
comment|//else already linkable or POS tag present
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"    - {}"
argument_list|,
name|tokenData
argument_list|)
expr_stmt|;
comment|//add the token to the list
name|tokens
operator|.
name|add
argument_list|(
name|tokenData
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|hasLinkableToken
condition|)
block|{
name|hasLinkableToken
operator|=
name|tokenData
operator|.
name|isLinkable
expr_stmt|;
block|}
name|Iterator
argument_list|<
name|ChunkData
argument_list|>
name|activeChunkIt
init|=
name|activeChunks
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|activeChunkIt
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|ChunkData
name|activeChunk
init|=
name|activeChunkIt
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|tokenData
operator|.
name|isLinkable
condition|)
block|{
comment|//ignore matchableCount in Chunks with linkable Tokens
name|activeChunk
operator|.
name|matchableCount
operator|=
operator|-
literal|10
expr_stmt|;
comment|//by setting the count to -10
block|}
elseif|else
if|if
condition|(
name|tokenData
operator|.
name|isMatchable
condition|)
block|{
name|activeChunk
operator|.
name|matchableCount
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|tokenData
operator|.
name|isMatchable
condition|)
block|{
comment|//for matchable tokens
comment|//update the matchable span within the active chunk
if|if
condition|(
name|activeChunk
operator|.
name|matchableStart
operator|<
literal|0
condition|)
block|{
name|activeChunk
operator|.
name|matchableStart
operator|=
name|tokenData
operator|.
name|index
expr_stmt|;
name|activeChunk
operator|.
name|matchableStartCharIndex
operator|=
name|tokenData
operator|.
name|token
operator|.
name|getStart
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|activeChunk
operator|.
name|matchableStart
operator|>=
literal|0
condition|)
block|{
comment|//if start is set also set end
name|activeChunk
operator|.
name|matchableEnd
operator|=
name|tokenData
operator|.
name|index
expr_stmt|;
name|activeChunk
operator|.
name|matchableEndCharIndex
operator|=
name|tokenData
operator|.
name|token
operator|.
name|getEnd
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|span
operator|.
name|getEnd
argument_list|()
operator|>=
name|activeChunk
operator|.
name|getEndChar
argument_list|()
condition|)
block|{
comment|//this is the last token in the current chunk
name|activeChunk
operator|.
name|endToken
operator|=
name|tokens
operator|.
name|size
argument_list|()
operator|-
literal|1
expr_stmt|;
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"<< end Chunk {} '{}' @pos: {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|activeChunk
operator|.
name|chunk
block|,
name|activeChunk
operator|.
name|chunk
operator|.
name|getSpan
argument_list|()
block|,
name|activeChunk
operator|.
name|endToken
block|}
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|tpc
operator|.
name|isLinkMultiMatchableTokensInChunk
argument_list|()
operator|&&
name|activeChunk
operator|.
name|getMatchableCount
argument_list|()
operator|>
literal|1
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"   - multi-matchable Chunk:"
argument_list|)
expr_stmt|;
comment|//mark the last of two immediate following matchable
comment|//tokens as processable
for|for
control|(
name|int
name|i
init|=
name|activeChunk
operator|.
name|endToken
operator|-
literal|1
init|;
name|i
operator|>=
name|activeChunk
operator|.
name|startToken
operator|+
literal|1
condition|;
name|i
operator|--
control|)
block|{
name|TokenData
name|ct
init|=
name|tokens
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|TokenData
name|pt
init|=
name|tokens
operator|.
name|get
argument_list|(
name|i
operator|-
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|ct
operator|.
name|isMatchable
operator|&&
name|pt
operator|.
name|isMatchable
condition|)
block|{
if|if
condition|(
operator|!
name|ct
operator|.
name|isLinkable
condition|)
block|{
comment|//if not already processable
name|log
operator|.
name|debug
argument_list|(
literal|"> convert Token {}: {} (pos:{}) from matchable to processable"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|i
block|,
name|ct
operator|.
name|token
operator|.
name|getSpan
argument_list|()
block|,
name|ct
operator|.
name|token
operator|.
name|getAnnotations
argument_list|(
name|POS_ANNOTATION
argument_list|)
block|}
argument_list|)
expr_stmt|;
name|ct
operator|.
name|isLinkable
operator|=
literal|true
expr_stmt|;
if|if
condition|(
operator|!
name|hasLinkableToken
condition|)
block|{
name|hasLinkableToken
operator|=
literal|true
expr_stmt|;
block|}
block|}
name|i
operator|--
expr_stmt|;
comment|//mark both (ct& pt) as processed
block|}
block|}
block|}
comment|//remove the closed chunk from the list with active
name|activeChunkIt
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
specifier|public
name|List
argument_list|<
name|TokenData
argument_list|>
name|getTokens
parameter_list|()
block|{
return|return
name|tokens
return|;
block|}
specifier|public
name|boolean
name|hasLinkableToken
parameter_list|()
block|{
return|return
name|hasLinkableToken
return|;
block|}
block|}
end_class

end_unit

