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
name|entitylinking
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
name|Comparator
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
name|stanbol
operator|.
name|enhancer
operator|.
name|engines
operator|.
name|entitylinking
operator|.
name|impl
operator|.
name|Suggestion
operator|.
name|MATCH
import|;
end_import

begin_class
specifier|public
class|class
name|LabelMatch
block|{
comment|/**      * To be used in case no match is present      */
specifier|public
specifier|static
specifier|final
name|LabelMatch
name|NONE
init|=
operator|new
name|LabelMatch
argument_list|()
decl_stmt|;
specifier|private
name|MATCH
name|match
init|=
name|MATCH
operator|.
name|NONE
decl_stmt|;
specifier|private
name|int
name|start
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|span
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|processableMatchCount
init|=
literal|0
decl_stmt|;
specifier|private
name|PlainLiteral
name|label
decl_stmt|;
specifier|private
name|int
name|labelTokenCount
init|=
literal|0
decl_stmt|;
specifier|private
name|double
name|score
decl_stmt|;
comment|/**      * The score of the matches (e.g. when a match is based on stemming or some      * oder kind of fuzziness, than matchers might assign a match score than      * 1.0.      */
specifier|private
name|float
name|tokenMatchScore
decl_stmt|;
specifier|private
name|double
name|textScore
decl_stmt|;
specifier|private
name|double
name|labelScore
decl_stmt|;
specifier|private
name|LabelMatch
parameter_list|()
block|{
comment|//internally used to create the NONE instance
block|}
comment|/**      * Creates an {@link MATCH#EXACT} label match      * @param start      * @param span      */
specifier|protected
name|LabelMatch
parameter_list|(
name|int
name|start
parameter_list|,
name|int
name|span
parameter_list|,
name|PlainLiteral
name|label
parameter_list|)
block|{
name|this
argument_list|(
name|start
argument_list|,
name|span
argument_list|,
name|span
argument_list|,
name|span
argument_list|,
literal|1f
argument_list|,
name|label
argument_list|,
name|span
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|LabelMatch
parameter_list|(
name|int
name|start
parameter_list|,
name|int
name|span
parameter_list|,
name|int
name|processableMatchCount
parameter_list|,
name|int
name|matchCount
parameter_list|,
name|float
name|tokenMatchScore
parameter_list|,
name|PlainLiteral
name|label
parameter_list|,
name|int
name|labelTokenCount
parameter_list|)
block|{
if|if
condition|(
name|start
operator|<
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"parsed start position MUST BE>= 0!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|start
operator|=
name|start
expr_stmt|;
if|if
condition|(
name|span
operator|<=
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"parsed span MUST be> 0!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|span
operator|=
name|span
expr_stmt|;
if|if
condition|(
name|label
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NullPointerException
argument_list|(
literal|"parsed Label MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|label
operator|=
name|label
expr_stmt|;
if|if
condition|(
name|processableMatchCount
operator|<=
literal|0
condition|)
block|{
name|match
operator|=
name|MATCH
operator|.
name|NONE
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|processableMatchCount
operator|==
name|span
condition|)
block|{
name|match
operator|=
name|MATCH
operator|.
name|FULL
expr_stmt|;
block|}
else|else
block|{
name|match
operator|=
name|MATCH
operator|.
name|PARTIAL
expr_stmt|;
block|}
if|if
condition|(
name|tokenMatchScore
operator|>
literal|1f
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The matchScore MUST NOT be greater than one (parsed value = "
operator|+
name|tokenMatchScore
operator|+
literal|")"
argument_list|)
throw|;
block|}
name|this
operator|.
name|tokenMatchScore
operator|=
name|tokenMatchScore
expr_stmt|;
name|this
operator|.
name|processableMatchCount
operator|=
name|processableMatchCount
expr_stmt|;
name|this
operator|.
name|labelTokenCount
operator|=
name|labelTokenCount
expr_stmt|;
comment|//init scores();
name|double
name|suggestionMatchScore
init|=
name|matchCount
operator|*
name|this
operator|.
name|tokenMatchScore
decl_stmt|;
name|textScore
operator|=
name|suggestionMatchScore
operator|/
name|this
operator|.
name|span
expr_stmt|;
name|labelScore
operator|=
name|suggestionMatchScore
operator|/
name|this
operator|.
name|labelTokenCount
expr_stmt|;
name|score
operator|=
name|textScore
operator|*
name|labelScore
expr_stmt|;
if|if
condition|(
name|span
operator|<
name|processableMatchCount
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The span '"
operator|+
name|span
operator|+
literal|"' MUST BE>= the number of matched processable tokens'"
operator|+
name|processableMatchCount
operator|+
literal|"': "
operator|+
name|toString
argument_list|()
operator|+
literal|"!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|span
operator|<
name|matchCount
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The span '"
operator|+
name|span
operator|+
literal|"' MUST BE>= the number of matched tokens '"
operator|+
name|matchCount
operator|+
literal|"': "
operator|+
name|toString
argument_list|()
operator|+
literal|"!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|processableMatchCount
operator|>
name|matchCount
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The number of matched processable tokens '"
operator|+
name|processableMatchCount
operator|+
literal|"' MUST BE<= the number of matched tokens '"
operator|+
name|matchCount
operator|+
literal|"': "
operator|+
name|toString
argument_list|()
operator|+
literal|"!"
argument_list|)
throw|;
block|}
block|}
comment|/**      * How well matches the label matches the text span.      * Only considers matched tokens of the label. This      * value gets low if matches are not exact AND if      * some words are not matched at all.      * @return      */
specifier|public
name|double
name|getTextScore
parameter_list|()
block|{
return|return
name|textScore
return|;
block|}
comment|/**      * How well matches the label. Sets the tokens of the      * Label in relation to the matched tokens in the text. Also      * considers that tokens might not match perfectly.<p>      * This score get low if the labels defines a lot of additional      * tokens that are not present in the Text.      * @return      */
specifier|public
name|double
name|getLabelScore
parameter_list|()
block|{
return|return
name|labelScore
return|;
block|}
comment|/**      * The actual label of the {@link #getResult() result} that produced the      * based match for the given search tokens.      * @return the label      */
specifier|public
name|PlainLiteral
name|getMatchedLabel
parameter_list|()
block|{
return|return
name|label
return|;
block|}
comment|/**      * Getter for the number of Tokens of the label. Usually needed to calculate      * the score (how good the label matches)      * @return the labelTokenCount      */
specifier|public
name|int
name|getLabelTokenCount
parameter_list|()
block|{
return|return
name|labelTokenCount
return|;
block|}
comment|/**      * Getter for the the type of the match      * @return The type of the match      */
specifier|public
name|MATCH
name|getMatch
parameter_list|()
block|{
return|return
name|match
return|;
block|}
comment|/**      * The overall score how well the label matches the text.      * This is the product of the {@link #getLabelScore() labelScore}       * with the {@link #getTextScore()}      * @return the overall score [0..1]      */
specifier|public
name|double
name|getMatchScore
parameter_list|()
block|{
return|return
name|score
return|;
block|}
comment|/**      * Getter for the number of the token matched by this suggestion      * @return The number of the token matched by this suggestion      */
specifier|public
name|int
name|getSpan
parameter_list|()
block|{
return|return
name|span
return|;
block|}
comment|/**      * Getter for the start index of this Suggestion      * @return the start token index for this suggestion      */
specifier|public
name|int
name|getStart
parameter_list|()
block|{
return|return
name|start
return|;
block|}
comment|/**      * Getter for the he number of matching tokens.      * @return The number of matching tokens.      */
specifier|public
name|int
name|getMatchCount
parameter_list|()
block|{
return|return
name|processableMatchCount
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
if|if
condition|(
name|match
operator|==
name|MATCH
operator|.
name|NONE
condition|)
block|{
return|return
literal|"no match"
return|;
block|}
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|label
operator|.
name|getLexicalForm
argument_list|()
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"[m="
argument_list|)
operator|.
name|append
argument_list|(
name|match
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|",s="
argument_list|)
operator|.
name|append
argument_list|(
name|span
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|",c="
argument_list|)
operator|.
name|append
argument_list|(
name|processableMatchCount
argument_list|)
operator|.
name|append
argument_list|(
literal|'('
argument_list|)
operator|.
name|append
argument_list|(
name|tokenMatchScore
argument_list|)
operator|.
name|append
argument_list|(
literal|")/"
argument_list|)
operator|.
name|append
argument_list|(
name|labelTokenCount
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"] score="
argument_list|)
operator|.
name|append
argument_list|(
name|score
argument_list|)
operator|.
name|append
argument_list|(
literal|"[l="
argument_list|)
operator|.
name|append
argument_list|(
name|labelScore
argument_list|)
operator|.
name|append
argument_list|(
literal|",t="
argument_list|)
operator|.
name|append
argument_list|(
name|textScore
argument_list|)
operator|.
name|append
argument_list|(
literal|']'
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Compares {@link LabelMatch} first based on the {@link LabelMatch#getMatchCount()}       * number of matched tokens. If the number of the matched tokens is equals or      * any of the parsed {@link Suggestion} instances has {@link MATCH#NONE} it      * forwards the request to the {@link #MATCH_TYPE_SUGGESTION_COMPARATOR}.      */
specifier|public
specifier|static
specifier|final
name|Comparator
argument_list|<
name|LabelMatch
argument_list|>
name|DEFAULT_LABEL_TOKEN_COMPARATOR
init|=
operator|new
name|Comparator
argument_list|<
name|LabelMatch
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|LabelMatch
name|arg0
parameter_list|,
name|LabelMatch
name|arg1
parameter_list|)
block|{
if|if
condition|(
name|arg0
operator|.
name|match
operator|==
name|MATCH
operator|.
name|NONE
operator|||
name|arg1
operator|.
name|match
operator|==
name|MATCH
operator|.
name|NONE
operator|||
name|arg0
operator|.
name|processableMatchCount
operator|==
name|arg1
operator|.
name|processableMatchCount
condition|)
block|{
return|return
name|arg1
operator|.
name|match
operator|.
name|ordinal
argument_list|()
operator|-
name|arg0
operator|.
name|match
operator|.
name|ordinal
argument_list|()
return|;
comment|//higher ordinal first
block|}
else|else
block|{
return|return
name|arg1
operator|.
name|processableMatchCount
operator|-
name|arg0
operator|.
name|processableMatchCount
return|;
comment|//bigger should be first
block|}
block|}
block|}
decl_stmt|;
block|}
end_class

end_unit

