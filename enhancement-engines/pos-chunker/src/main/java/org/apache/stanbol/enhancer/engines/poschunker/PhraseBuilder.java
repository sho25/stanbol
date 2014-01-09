begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|poschunker
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
name|PHRASE_ANNOTATION
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
name|PosTag
import|;
end_import

begin_class
specifier|public
class|class
name|PhraseBuilder
block|{
comment|/**      * Just a fallback in case Pos annotations do not provide probabilities.       * In most cases the value of this will not have any effect as typically       * Pos Taggers that do not provide probabilities only emit a      * single POS tag per Token. In such cases this tag will be always accepted       * regardless of the configured value.<p>      * The value is only important if some Pos annotation for a Token do have       * probabilities while others have not. In such cases those without are rated       * against other that have by using this value. Such Situations should only      * occur if a chain uses several POS taggers - a setting that should be      * avoided<p>      */
specifier|private
specifier|static
specifier|final
name|double
name|DEFAULT_SCORE
init|=
literal|0.1
decl_stmt|;
specifier|private
specifier|final
name|PhraseTypeDefinition
name|phraseType
decl_stmt|;
specifier|private
specifier|final
name|ChunkFactory
name|chunkFactory
decl_stmt|;
specifier|private
specifier|final
name|double
name|minPosSocre
decl_stmt|;
comment|/**      * The {@link PhraseTag} added to all {@link Chunk}s created by this      * {@link PhraseBuilder}      */
specifier|private
specifier|final
name|PhraseTag
name|phraseTag
decl_stmt|;
comment|/**      * Holds Tokens of a current phrase. Empty if no phrase is building.      */
specifier|private
name|List
argument_list|<
name|Token
argument_list|>
name|current
init|=
operator|new
name|ArrayList
argument_list|<
name|Token
argument_list|>
argument_list|()
decl_stmt|;
comment|/**      * If {@link #current} contains a Tokens matching       * {@link PhraseTypeDefinition#getRequiredType()}      */
name|boolean
name|valid
decl_stmt|;
specifier|public
name|PhraseBuilder
parameter_list|(
name|PhraseTypeDefinition
name|phraseType
parameter_list|,
name|ChunkFactory
name|chunkFactory
parameter_list|,
name|double
name|minPosSocre
parameter_list|)
block|{
if|if
condition|(
name|phraseType
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed PhraseTypeDefinition MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|phraseType
operator|=
name|phraseType
expr_stmt|;
name|this
operator|.
name|phraseTag
operator|=
operator|new
name|PhraseTag
argument_list|(
name|phraseType
operator|.
name|getPhraseType
argument_list|()
operator|.
name|name
argument_list|()
argument_list|,
name|phraseType
operator|.
name|getPhraseType
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|chunkFactory
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ChunkFactory MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|chunkFactory
operator|=
name|chunkFactory
expr_stmt|;
if|if
condition|(
name|minPosSocre
argument_list|<
literal|0
operator|||
name|minPosSocre
argument_list|>
literal|1
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed minPosScore '"
operator|+
name|minPosSocre
operator|+
literal|"' MUST BE within the ranve [0..1]!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|minPosSocre
operator|=
name|minPosSocre
expr_stmt|;
block|}
specifier|public
name|void
name|nextToken
parameter_list|(
name|Token
name|token
parameter_list|)
block|{
if|if
condition|(
name|current
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//check for start
name|checkStart
argument_list|(
name|token
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|checkContinuation
argument_list|(
name|token
argument_list|)
condition|)
block|{
comment|//check for continuation
name|buildPhrase
argument_list|(
name|token
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|nextSection
parameter_list|(
name|Section
name|section
parameter_list|)
block|{
name|buildPhrase
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
comment|//varargs with generic types
specifier|private
name|void
name|checkStart
parameter_list|(
name|Token
name|token
parameter_list|)
block|{
name|boolean
index|[]
name|states
init|=
name|checkCategories
argument_list|(
name|token
argument_list|,
name|phraseType
operator|.
name|getStartType
argument_list|()
argument_list|,
name|phraseType
operator|.
name|getRequiredType
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|states
index|[
literal|0
index|]
condition|)
block|{
name|current
operator|.
name|add
argument_list|(
name|token
argument_list|)
expr_stmt|;
name|valid
operator|=
name|states
index|[
literal|1
index|]
expr_stmt|;
block|}
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
comment|//varargs with generic types
specifier|private
name|boolean
name|checkContinuation
parameter_list|(
name|Token
name|token
parameter_list|)
block|{
specifier|final
name|boolean
index|[]
name|states
decl_stmt|;
if|if
condition|(
operator|!
name|valid
condition|)
block|{
name|states
operator|=
name|checkCategories
argument_list|(
name|token
argument_list|,
name|phraseType
operator|.
name|getContinuationType
argument_list|()
argument_list|,
name|phraseType
operator|.
name|getRequiredType
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|states
operator|=
name|checkCategories
argument_list|(
name|token
argument_list|,
name|phraseType
operator|.
name|getContinuationType
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|states
index|[
literal|0
index|]
condition|)
block|{
name|current
operator|.
name|add
argument_list|(
name|token
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|states
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|valid
operator|=
name|states
index|[
literal|1
index|]
expr_stmt|;
block|}
return|return
name|states
index|[
literal|0
index|]
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
comment|//varargs with generic types
specifier|private
name|void
name|buildPhrase
parameter_list|(
name|Token
name|token
parameter_list|)
block|{
name|Token
name|lastConsumedToken
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|valid
condition|)
block|{
comment|//search backwards for the first token matching an allowed end
comment|//category
name|int
name|endIndex
init|=
name|current
operator|.
name|size
argument_list|()
operator|-
literal|1
decl_stmt|;
while|while
condition|(
name|endIndex
operator|>
literal|0
operator|&&
operator|!
name|checkCategories
argument_list|(
name|current
operator|.
name|get
argument_list|(
name|endIndex
argument_list|)
argument_list|,
name|phraseType
operator|.
name|getEndType
argument_list|()
argument_list|)
index|[
literal|0
index|]
condition|)
block|{
name|endIndex
operator|--
expr_stmt|;
block|}
name|lastConsumedToken
operator|=
name|current
operator|.
name|get
argument_list|(
name|endIndex
argument_list|)
expr_stmt|;
comment|//NOTE: ignore phrases with a single token
if|if
condition|(
name|endIndex
operator|>
literal|0
condition|)
block|{
name|Chunk
name|chunk
init|=
name|chunkFactory
operator|.
name|createChunk
argument_list|(
name|current
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|,
name|lastConsumedToken
argument_list|)
decl_stmt|;
comment|//TODO: add support for confidence
name|chunk
operator|.
name|addAnnotation
argument_list|(
name|PHRASE_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
name|phraseTag
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|//cleanup
name|current
operator|.
name|clear
argument_list|()
expr_stmt|;
name|valid
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|token
operator|!=
literal|null
operator|&&
operator|!
name|token
operator|.
name|equals
argument_list|(
name|lastConsumedToken
argument_list|)
condition|)
block|{
comment|//the current token might be the start of a new phrase
name|checkStart
argument_list|(
name|token
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Checks if the a the {@link NlpAnnotations#POS_ANNOTATION POS Annotations}      * of a {@link Token} matches the parsed categories. This method supports      * to check against multiple sets of categories to allow checking e.g. if a token      * is suitable for {@link PhraseTypeDefinition#getStartType()} and      * {@link PhraseTypeDefinition#getRequiredType()}.      * @param token the Token      * @param categories the list of categories to check      * @return if the sum of matching annotations compared to the score of all      * POS annotations is higher or equals the configured {@link #minPosSocre}.      * For each parsed categories set a boolean state is returned.      */
specifier|private
name|boolean
index|[]
name|checkCategories
parameter_list|(
name|Token
name|token
parameter_list|,
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
modifier|...
name|categories
parameter_list|)
block|{
comment|//there are different ways NLP frameworks do assign scores. For some the
comment|//sum of all categories would sum up to 1.0, but as only the top three
comment|//categories are included the sum would be< 1
comment|//Others assign scores so that each score is< 1, but the sum of all
comment|//is higher as 1.0.
comment|//There is also the possibility that no scores are present.
comment|//Because of that this sums up all scores and normalizes with the
comment|//Match.max(1.0,sumScore).
comment|//POS tags without score are assigned a #DEFAULT_SCORE. If not a single
comment|//POS tag with a score is present the sumScore is NOT normalized to 1.0
name|boolean
name|scorePresent
init|=
literal|false
decl_stmt|;
name|double
name|sumScore
init|=
literal|0
decl_stmt|;
name|double
index|[]
name|matchScores
init|=
operator|new
name|double
index|[
name|categories
operator|.
name|length
index|]
decl_stmt|;
for|for
control|(
name|Value
argument_list|<
name|PosTag
argument_list|>
name|pos
range|:
name|token
operator|.
name|getAnnotations
argument_list|(
name|POS_ANNOTATION
argument_list|)
control|)
block|{
name|double
name|score
init|=
name|pos
operator|.
name|probability
argument_list|()
decl_stmt|;
if|if
condition|(
name|score
operator|==
name|Value
operator|.
name|UNKNOWN_PROBABILITY
condition|)
block|{
name|score
operator|=
name|DEFAULT_SCORE
expr_stmt|;
block|}
else|else
block|{
name|scorePresent
operator|=
literal|true
expr_stmt|;
block|}
name|sumScore
operator|=
name|sumScore
operator|+
name|pos
operator|.
name|probability
argument_list|()
expr_stmt|;
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|tokenCategories
init|=
name|pos
operator|.
name|value
argument_list|()
operator|.
name|getCategories
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|categories
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|category
init|=
name|categories
index|[
name|i
index|]
decl_stmt|;
if|if
condition|(
operator|!
name|Collections
operator|.
name|disjoint
argument_list|(
name|tokenCategories
argument_list|,
name|category
argument_list|)
condition|)
block|{
name|matchScores
index|[
name|i
index|]
operator|=
name|matchScores
index|[
name|i
index|]
operator|+
name|pos
operator|.
name|probability
argument_list|()
expr_stmt|;
block|}
block|}
block|}
name|boolean
index|[]
name|matches
init|=
operator|new
name|boolean
index|[
name|matchScores
operator|.
name|length
index|]
decl_stmt|;
comment|//the score used to normalize annotations. See comments at method start
name|double
name|normScore
init|=
name|scorePresent
condition|?
name|Math
operator|.
name|max
argument_list|(
literal|1.0
argument_list|,
name|sumScore
argument_list|)
else|:
name|sumScore
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|matchScores
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|matches
index|[
name|i
index|]
operator|=
name|matchScores
index|[
name|i
index|]
operator|/
name|normScore
operator|>=
name|minPosSocre
expr_stmt|;
block|}
return|return
name|matches
return|;
block|}
specifier|public
specifier|static
interface|interface
name|ChunkFactory
block|{
name|Chunk
name|createChunk
parameter_list|(
name|Token
name|start
parameter_list|,
name|Token
name|end
parameter_list|)
function_decl|;
block|}
block|}
end_class

end_unit

