begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_comment
comment|/**  *   */
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
name|engines
operator|.
name|entitylinking
operator|.
name|impl
operator|.
name|LabelMatch
operator|.
name|DEFAULT_LABEL_TOKEN_COMPARATOR
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
name|Comparator
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
name|Literal
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
name|Entity
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

begin_comment
comment|/**  * A suggestion of an entity in the {@link EntitySearcher} for a part of the  * text. This class does not include the actual position within the Text,   * because it is intended to be used in combination with {@link LinkedEntity}.<p>  * This class also manages redirected entities and a state if redirects where  * already processed for this suggestion.<p>  * In addition this class also defines a set of {@link Comparator}s that are   * used to sort suggestions base on how well the fit the text.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|Suggestion
block|{
specifier|private
name|List
argument_list|<
name|LabelMatch
argument_list|>
name|labelMatches
init|=
operator|new
name|ArrayList
argument_list|<
name|LabelMatch
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|labelMatchesSorted
init|=
literal|true
decl_stmt|;
specifier|private
specifier|final
name|Entity
name|entity
decl_stmt|;
specifier|private
name|Entity
name|redirectsTo
decl_stmt|;
specifier|private
name|boolean
name|redirectProcessed
decl_stmt|;
specifier|private
name|double
name|score
decl_stmt|;
comment|/**      * used to allow overriding the MATCH of this suggestion      */
specifier|private
name|MATCH
name|match
decl_stmt|;
specifier|public
specifier|static
enum|enum
name|MATCH
block|{
comment|/**          * No match (to less tokens, wrong oder ...)          */
name|NONE
block|,
comment|/**          * Not all tokens but sufficient to suggest (with lower score)          */
name|PARTIAL
block|,
comment|/**          * All requested Tokens match, but it is no exact match e.g. because          * the label defines some additional tokens          */
name|FULL
block|,
comment|/**          * The label of the suggested Entity is exactly the requested string          */
name|EXACT
block|,     }
specifier|protected
name|Suggestion
parameter_list|(
name|Entity
name|entity
parameter_list|)
block|{
if|if
condition|(
name|entity
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Result MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|entity
operator|=
name|entity
expr_stmt|;
comment|//TODO Do no longer use the resultScore as the score. We need to provide an
comment|//own algorithm to calculate scores!
comment|//        this.resultScore = result.getFirst(RdfResourceEnum.resultScore.getUri(), Float.class);
block|}
comment|/**      * Adds an new LabelMatch to this suggestion      * @param labelMatch the labelMatch      */
specifier|public
name|void
name|addLabelMatch
parameter_list|(
name|LabelMatch
name|labelMatch
parameter_list|)
block|{
if|if
condition|(
name|labelMatch
operator|==
literal|null
operator|||
name|labelMatch
operator|.
name|getMatch
argument_list|()
operator|==
name|MATCH
operator|.
name|NONE
condition|)
block|{
return|return;
comment|//ignore null an MATCH.NONE entries
block|}
name|labelMatches
operator|.
name|add
argument_list|(
name|labelMatch
argument_list|)
expr_stmt|;
if|if
condition|(
name|labelMatches
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|labelMatchesSorted
operator|=
literal|false
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the best label in the given language      * @param suggestion the suggestion      * @param nameField the field used to search for labels      * @param language the language      * @return the best match or {@link Suggestion#getMatchedLabel()} if non is found      */
specifier|public
name|Literal
name|getBestLabel
parameter_list|(
name|IRI
name|nameField
parameter_list|,
name|String
name|language
parameter_list|)
block|{
name|Entity
name|rep
init|=
name|getEntity
argument_list|()
decl_stmt|;
comment|//start with the matched label -> so if we do not find a better one
comment|//we will use the matched!
name|Literal
name|matchedLabel
init|=
name|getMatchedLabel
argument_list|()
decl_stmt|;
name|Literal
name|label
init|=
name|matchedLabel
decl_stmt|;
comment|// 1. check if the returned Entity does has a label -> if not return null
comment|// add labels (set only a single label. Use "en" if available!
name|Iterator
argument_list|<
name|Literal
argument_list|>
name|labels
init|=
name|rep
operator|.
name|getText
argument_list|(
name|nameField
argument_list|)
decl_stmt|;
name|boolean
name|matchFound
init|=
literal|false
decl_stmt|;
while|while
condition|(
name|labels
operator|.
name|hasNext
argument_list|()
operator|&&
operator|!
name|matchFound
condition|)
block|{
name|Literal
name|actLabel
init|=
name|labels
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|label
operator|==
literal|null
condition|)
block|{
name|label
operator|=
name|actLabel
expr_stmt|;
block|}
comment|//now we have already a label check the language
name|Language
name|actLang
init|=
name|actLabel
operator|.
name|getLanguage
argument_list|()
decl_stmt|;
comment|//use startWith to match also en-GB and en-US ...
if|if
condition|(
name|actLang
operator|!=
literal|null
operator|&&
name|actLang
operator|.
name|toString
argument_list|()
operator|.
name|startsWith
argument_list|(
name|language
argument_list|)
condition|)
block|{
comment|//prefer labels with the correct language
name|label
operator|=
name|actLabel
expr_stmt|;
if|if
condition|(
name|matchedLabel
operator|!=
literal|null
operator|&&
name|matchedLabel
operator|.
name|getLexicalForm
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|label
operator|.
name|getLexicalForm
argument_list|()
argument_list|)
condition|)
block|{
comment|//found label in that language that exactly matches the
comment|//label used to match the text
name|matchFound
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
return|return
name|label
return|;
block|}
comment|/**      * Shorthand for {@link #getLabelMatch()}.getMatchedLabel()      * @return the label or<code>null</code> if {@link MATCH#NONE}      */
specifier|public
name|Literal
name|getMatchedLabel
parameter_list|()
block|{
return|return
name|getLabelMatch
argument_list|()
operator|.
name|getMatchedLabel
argument_list|()
return|;
block|}
specifier|protected
name|void
name|setMatch
parameter_list|(
name|MATCH
name|matchType
parameter_list|)
block|{
name|this
operator|.
name|match
operator|=
name|matchType
expr_stmt|;
block|}
comment|/**      * Getter for the {@link MATCH}. If not manually set      * this forwards to {@link #getLabelMatch()}.getMatch()      * @return the {@link MATCH} of this suggestion      */
specifier|public
name|MATCH
name|getMatch
parameter_list|()
block|{
return|return
name|match
operator|!=
literal|null
condition|?
name|match
else|:
name|getLabelMatch
argument_list|()
operator|.
name|getMatch
argument_list|()
return|;
block|}
specifier|public
specifier|final
name|Entity
name|getResult
parameter_list|()
block|{
return|return
name|entity
return|;
block|}
comment|/**      * Getter for the EntityRank of the suggested Entity. In case of a       * redirected Entity it will return the maximum value      */
specifier|public
name|Float
name|getEntityRank
parameter_list|()
block|{
specifier|final
name|Float
name|ranking
init|=
name|entity
operator|.
name|getEntityRanking
argument_list|()
decl_stmt|;
specifier|final
name|Float
name|rdRanking
init|=
name|redirectsTo
operator|==
literal|null
condition|?
literal|null
else|:
name|redirectsTo
operator|.
name|getEntityRanking
argument_list|()
decl_stmt|;
return|return
name|rdRanking
operator|!=
literal|null
condition|?
name|ranking
operator|==
literal|null
operator|||
name|rdRanking
operator|.
name|compareTo
argument_list|(
name|ranking
argument_list|)
operator|>
literal|0
condition|?
name|rdRanking
else|:
name|ranking
else|:
name|ranking
return|;
block|}
comment|/**      * @param score the score to set      */
specifier|public
name|void
name|setScore
parameter_list|(
name|double
name|score
parameter_list|)
block|{
name|this
operator|.
name|score
operator|=
name|score
expr_stmt|;
block|}
comment|/**      * @return the score      */
specifier|public
name|double
name|getScore
parameter_list|()
block|{
return|return
name|score
return|;
block|}
comment|/**      * Returns<code>true</code> if the result has a registered redirect      * @return<code>true</code> if a redirect is present. Otherwise<code>false</code>      */
specifier|public
name|boolean
name|isRedirect
parameter_list|()
block|{
return|return
name|redirectsTo
operator|!=
literal|null
return|;
block|}
comment|/**      * Setter for Entity the {@link #getResult() result} of this match redirects      * to. Also sets {@link #setRedirectProcessed(boolean)} to<code>true</code>      * @param redirect the redirected entity or<code>null</code> if no redirect      * is present      */
specifier|protected
name|void
name|setRedirect
parameter_list|(
name|Entity
name|redirect
parameter_list|)
block|{
name|this
operator|.
name|redirectsTo
operator|=
name|redirect
expr_stmt|;
name|setRedirectProcessed
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|/**      * Setter for the state if the redirects for this resultMatch where already      * processed. Calling {@link #setRedirect(Representation)} will set this      * automatically to<code>true</code>      * @param state the state.      */
specifier|protected
name|void
name|setRedirectProcessed
parameter_list|(
name|boolean
name|state
parameter_list|)
block|{
name|this
operator|.
name|redirectProcessed
operator|=
name|state
expr_stmt|;
block|}
comment|/**      * Getter for the state if the redirect was processed for this ResultMatch      * @return the state      */
specifier|protected
name|boolean
name|isRedirectedProcessed
parameter_list|()
block|{
return|return
name|redirectProcessed
return|;
block|}
comment|/**      * Getter for the Entity the {@link #getResult()} of this Entity redirects      * to. Returns<code>null</code> if there is no redirect.       * @return the entity the {@link #getResult()} redirects to or<code>null</code>      * if there is no redirect      */
specifier|public
name|Entity
name|getRedirect
parameter_list|()
block|{
return|return
name|redirectsTo
return|;
block|}
comment|/**      * getter for the Representation of this result. In case of       *<code>{@link #isRedirect()} == true</code> it returns the the       * {@link #getRedirect()} otherwise it returns the {@link #getResult()}.<p>      * To check explicitly for the result of the redirect one needs to use      * {@link #getRedirect()} and {@link #getRedirect()} instead.      * @return The representation for this match. might be directly the       * {@link #getResult() result} or if present the       * {@link #getRedirect() redirected} resource.       */
specifier|public
specifier|final
name|Entity
name|getEntity
parameter_list|()
block|{
return|return
name|redirectsTo
operator|==
literal|null
condition|?
name|entity
else|:
name|redirectsTo
return|;
block|}
comment|/**      * Getter for the top ranked LabelMatch.      * @return the top ranked {@link LabelMatch} or {@link LabelMatch#NONE}      * if no match is present.      */
specifier|public
specifier|final
name|LabelMatch
name|getLabelMatch
parameter_list|()
block|{
if|if
condition|(
operator|!
name|labelMatchesSorted
condition|)
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|labelMatches
argument_list|,
name|LabelMatch
operator|.
name|DEFAULT_LABEL_TOKEN_COMPARATOR
argument_list|)
expr_stmt|;
block|}
return|return
name|labelMatches
operator|.
name|isEmpty
argument_list|()
condition|?
name|LabelMatch
operator|.
name|NONE
else|:
name|labelMatches
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
comment|/**      * Getter for the sorted list with all {@link LabelMatch}s of this Suggestion      * @return the sorted LabelMatches. Guaranteed NOT<code>null</code> and      * NOT empty. In case no match is present a singleton list containing      * {@link LabelMatch#NONE} is returned.      */
specifier|public
specifier|final
name|List
argument_list|<
name|LabelMatch
argument_list|>
name|getLabelMatches
parameter_list|()
block|{
if|if
condition|(
operator|!
name|labelMatchesSorted
condition|)
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|labelMatches
argument_list|,
name|LabelMatch
operator|.
name|DEFAULT_LABEL_TOKEN_COMPARATOR
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|labelMatches
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|singletonList
argument_list|(
name|LabelMatch
operator|.
name|NONE
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|labelMatches
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|labelMatches
operator|.
name|isEmpty
argument_list|()
condition|?
literal|"no match"
else|:
name|labelMatches
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|+
literal|" for "
operator|+
name|entity
operator|.
name|getId
argument_list|()
operator|+
operator|(
name|redirectsTo
operator|!=
literal|null
condition|?
literal|" (redirects: "
operator|+
name|redirectsTo
operator|.
name|getId
argument_list|()
operator|+
literal|") "
else|:
literal|""
operator|)
operator|+
literal|" ranking: "
operator|+
name|getEntityRank
argument_list|()
return|;
block|}
comment|/**      * Compares {@link Suggestion}s based on the {@link Suggestion#getScore()}.      * In case the scores are equals the call is forwarded to the      * {@link Suggestion#DEFAULT_LABEL_TOKEN_COMPARATOR}.<p>      * This is NOT the default {@link Comparator} because score values are      * usually only calculated relative to the best matching suggestions and      * therefore only available later.      */
specifier|public
specifier|static
specifier|final
name|Comparator
argument_list|<
name|Suggestion
argument_list|>
name|SCORE_COMPARATOR
init|=
operator|new
name|Comparator
argument_list|<
name|Suggestion
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|Suggestion
name|arg0
parameter_list|,
name|Suggestion
name|arg1
parameter_list|)
block|{
return|return
name|arg0
operator|.
name|getScore
argument_list|()
operator|>
name|arg1
operator|.
name|getScore
argument_list|()
condition|?
operator|-
literal|1
else|:
comment|//bigger score first
name|arg0
operator|.
name|getScore
argument_list|()
operator|<
name|arg1
operator|.
name|getScore
argument_list|()
condition|?
literal|1
else|:
name|DEFAULT_LABEL_TOKEN_COMPARATOR
operator|.
name|compare
argument_list|(
name|arg0
operator|.
name|getLabelMatch
argument_list|()
argument_list|,
name|arg1
operator|.
name|getLabelMatch
argument_list|()
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|/**      * Compares {@link Suggestion}s based on the {@link Suggestion#getEntityRank()}.      *<code>null</code> values are assumed to be the smallest.      */
specifier|public
specifier|static
specifier|final
name|Comparator
argument_list|<
name|Suggestion
argument_list|>
name|ENTITY_RANK_COMPARATOR
init|=
operator|new
name|Comparator
argument_list|<
name|Suggestion
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|Suggestion
name|arg0
parameter_list|,
name|Suggestion
name|arg1
parameter_list|)
block|{
name|Float
name|r1
init|=
name|arg0
operator|.
name|getEntityRank
argument_list|()
decl_stmt|;
name|Float
name|r2
init|=
name|arg1
operator|.
name|getEntityRank
argument_list|()
decl_stmt|;
return|return
name|r2
operator|==
literal|null
condition|?
name|r1
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
else|:
name|r1
operator|==
literal|null
condition|?
literal|1
else|:
name|r2
operator|.
name|compareTo
argument_list|(
name|r1
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|/**      * Compares {@link Suggestion} first based on the {@link Suggestion#getMatch()} value      * and secondly based on the {@link RdfResourceEnum#entityRank}.      */
specifier|public
specifier|static
specifier|final
name|Comparator
argument_list|<
name|Suggestion
argument_list|>
name|MATCH_TYPE_SUGGESTION_COMPARATOR
init|=
operator|new
name|Comparator
argument_list|<
name|Suggestion
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|Suggestion
name|arg0
parameter_list|,
name|Suggestion
name|arg1
parameter_list|)
block|{
name|int
name|labelMatch
init|=
name|DEFAULT_LABEL_TOKEN_COMPARATOR
operator|.
name|compare
argument_list|(
name|arg0
operator|.
name|getLabelMatch
argument_list|()
argument_list|,
name|arg1
operator|.
name|getLabelMatch
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|labelMatch
operator|==
literal|0
condition|)
block|{
name|Float
name|arg0Rank
init|=
name|arg0
operator|.
name|getEntityRank
argument_list|()
decl_stmt|;
if|if
condition|(
name|arg0Rank
operator|==
literal|null
condition|)
block|{
name|arg0Rank
operator|=
name|Float
operator|.
name|valueOf
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
name|Float
name|arg1Rank
init|=
name|arg1
operator|.
name|getEntityRank
argument_list|()
decl_stmt|;
if|if
condition|(
name|arg1Rank
operator|==
literal|null
condition|)
block|{
name|arg1Rank
operator|=
name|Float
operator|.
name|valueOf
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
return|return
name|arg1Rank
operator|.
name|compareTo
argument_list|(
name|arg0Rank
argument_list|)
return|;
comment|//higher ranks first
block|}
else|else
block|{
return|return
name|labelMatch
return|;
block|}
block|}
block|}
decl_stmt|;
block|}
end_class

end_unit

