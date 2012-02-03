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
name|contenthub
operator|.
name|search
operator|.
name|related
operator|.
name|wordnet
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
name|contenthub
operator|.
name|search
operator|.
name|related
operator|.
name|RelatedKeywordImpl
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|SearchException
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|related
operator|.
name|RelatedKeyword
import|;
end_import

begin_import
import|import
name|edu
operator|.
name|smu
operator|.
name|tspell
operator|.
name|wordnet
operator|.
name|NounSynset
import|;
end_import

begin_import
import|import
name|edu
operator|.
name|smu
operator|.
name|tspell
operator|.
name|wordnet
operator|.
name|Synset
import|;
end_import

begin_import
import|import
name|edu
operator|.
name|smu
operator|.
name|tspell
operator|.
name|wordnet
operator|.
name|SynsetType
import|;
end_import

begin_import
import|import
name|edu
operator|.
name|smu
operator|.
name|tspell
operator|.
name|wordnet
operator|.
name|VerbSynset
import|;
end_import

begin_import
import|import
name|edu
operator|.
name|smu
operator|.
name|tspell
operator|.
name|wordnet
operator|.
name|WordNetDatabase
import|;
end_import

begin_import
import|import
name|edu
operator|.
name|smu
operator|.
name|tspell
operator|.
name|wordnet
operator|.
name|WordNetException
import|;
end_import

begin_comment
comment|/**  * @author anil.sinaci  * @author cihan  *   */
end_comment

begin_class
specifier|public
class|class
name|WordnetClient
block|{
specifier|public
specifier|static
specifier|final
name|int
name|EXPANSION_0
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|EXPANSION_1
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|EXPANSION_2
init|=
literal|3
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|EXPANSION_3
init|=
literal|4
decl_stmt|;
specifier|private
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
name|relatedKeywordList
decl_stmt|;
specifier|private
name|Double
name|maxScore
decl_stmt|;
specifier|private
name|WordNetDatabase
name|wordnetDatabase
decl_stmt|;
specifier|private
name|double
name|degradingFactor
decl_stmt|;
specifier|private
name|int
name|expansionLevel
decl_stmt|;
specifier|public
name|WordnetClient
parameter_list|(
name|String
name|wordnetDatabase
parameter_list|,
name|Integer
name|expansionLevel
parameter_list|,
name|Double
name|degradingFactor
parameter_list|)
block|{
name|System
operator|.
name|setProperty
argument_list|(
literal|"wordnet.database.dir"
argument_list|,
name|wordnetDatabase
argument_list|)
expr_stmt|;
name|this
operator|.
name|wordnetDatabase
operator|=
name|WordNetDatabase
operator|.
name|getFileInstance
argument_list|()
expr_stmt|;
name|this
operator|.
name|degradingFactor
operator|=
name|degradingFactor
expr_stmt|;
name|this
operator|.
name|expansionLevel
operator|=
name|expansionLevel
expr_stmt|;
block|}
specifier|private
name|void
name|removeDuplicates
parameter_list|()
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|relatedKeywordList
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
for|for
control|(
name|int
name|j
init|=
name|i
operator|+
literal|1
init|;
name|j
operator|<
name|relatedKeywordList
operator|.
name|size
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|relatedKeywordList
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getKeyword
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|relatedKeywordList
operator|.
name|get
argument_list|(
name|j
argument_list|)
operator|.
name|getKeyword
argument_list|()
argument_list|)
condition|)
block|{
name|relatedKeywordList
operator|.
name|remove
argument_list|(
name|j
argument_list|)
expr_stmt|;
name|j
operator|--
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
specifier|final
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
name|getScoredWordnetResources
parameter_list|(
name|String
name|keyword
parameter_list|,
name|double
name|maxScore
parameter_list|)
throws|throws
name|SearchException
block|{
name|relatedKeywordList
operator|=
operator|new
name|ArrayList
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|()
expr_stmt|;
name|this
operator|.
name|maxScore
operator|=
name|maxScore
expr_stmt|;
name|Synset
index|[]
name|synsets0
decl_stmt|;
try|try
block|{
name|synsets0
operator|=
name|wordnetDatabase
operator|.
name|getSynsets
argument_list|(
name|keyword
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|WordNetException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SearchException
argument_list|(
literal|"Error accessing wordnet database"
argument_list|,
name|e
argument_list|)
throw|;
block|}
for|for
control|(
name|Synset
name|synset
range|:
name|synsets0
control|)
block|{
name|String
index|[]
name|wordForms
init|=
name|synset
operator|.
name|getWordForms
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|wordForm
range|:
name|wordForms
control|)
block|{
name|relatedKeywordList
operator|.
name|add
argument_list|(
operator|new
name|RelatedKeywordImpl
argument_list|(
name|wordForm
argument_list|,
name|maxScore
operator|/
name|degradingFactor
argument_list|,
name|RelatedKeyword
operator|.
name|Source
operator|.
name|WORDNET
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|expansionLevel
operator|==
name|WordnetClient
operator|.
name|EXPANSION_0
condition|)
block|{
return|return
name|relatedKeywordList
return|;
block|}
comment|/*          * Synset[] adjectiveSynsets = wordnetDatabase.getSynsets(keyword, SynsetType.ADJECTIVE); Synset[]          * adverbSynsets = wordnetDatabase.getSynsets(keyword, SynsetType.ADVERB);          */
name|Synset
index|[]
name|nounSynsets
init|=
name|wordnetDatabase
operator|.
name|getSynsets
argument_list|(
name|keyword
argument_list|,
name|SynsetType
operator|.
name|NOUN
argument_list|)
decl_stmt|;
name|Synset
index|[]
name|verbSynsets
init|=
name|wordnetDatabase
operator|.
name|getSynsets
argument_list|(
name|keyword
argument_list|,
name|SynsetType
operator|.
name|VERB
argument_list|)
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
name|expansionLevel
operator|-
literal|1
condition|;
name|i
operator|++
control|)
block|{
comment|// TODO adjectives and adverbs not implemented yet
comment|/*              * adjectiveSynsets = handleAdjectives(adjectiveSynsets, i + 1); adverbSynsets =              * handleAdverbs(adverbSynsets, i + 1);              */
name|nounSynsets
operator|=
name|handleNouns
argument_list|(
name|nounSynsets
argument_list|,
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
name|verbSynsets
operator|=
name|handleVerbs
argument_list|(
name|verbSynsets
argument_list|,
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
name|removeDuplicates
argument_list|()
expr_stmt|;
return|return
name|relatedKeywordList
return|;
block|}
comment|// TODO: Adjectives and Adverbs are not included yet
comment|/*      * private AdjectiveSynset[] handleAdjectives(Synset[] adjectiveSynsets, int currentExpansionLevel) {      *       * if (adjectiveSynsets == null) { return null; }      *       * return null; }      *       * private AdverbSynset[] handleAdverbs(Synset[] adverbSynsets, int currentExpansionLevel) {      *       * if (adverbSynsets == null) { return null; }      *       * return null; }      */
specifier|private
name|NounSynset
index|[]
name|handleNouns
parameter_list|(
name|Synset
index|[]
name|nounSynsets
parameter_list|,
name|int
name|currentExpansionLevel
parameter_list|)
block|{
if|if
condition|(
name|nounSynsets
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|List
argument_list|<
name|NounSynset
argument_list|>
name|newNounSynset
init|=
operator|new
name|ArrayList
argument_list|<
name|NounSynset
argument_list|>
argument_list|()
decl_stmt|;
comment|// TODO: Not all methods of a NounSynset is called.
for|for
control|(
name|Synset
name|synset
range|:
name|nounSynsets
control|)
block|{
name|NounSynset
name|nounSynset
init|=
operator|(
name|NounSynset
operator|)
name|synset
decl_stmt|;
comment|// Hypernyms
comment|// NounSynset[] hypernyms = nounSynset.getHypernyms();
comment|// for (NounSynset hypernym : hypernyms) {
comment|// addWordForms(hypernym.getWordForms(), currentExpansionLevel);
comment|// newNounSynset.add(hypernym);
comment|// }
comment|// NounSynset[] instanceHypernyms = nounSynset.getInstanceHypernyms();
comment|// for (NounSynset instanceHypernym : instanceHypernyms) {
comment|// addWordForms(instanceHypernym.getWordForms(), currentExpansionLevel);
comment|// newNounSynset.add(instanceHypernym);
comment|// }
comment|//
comment|// // Hyponyms
comment|// NounSynset[] directHyponyms = nounSynset.getHyponyms();
comment|// for (NounSynset directHyponym : directHyponyms) {
comment|// addWordForms(directHyponym.getWordForms(), currentExpansionLevel);
comment|// newNounSynset.add(directHyponym);
comment|// }
comment|// NounSynset[] instanceHyponyms = nounSynset.getInstanceHyponyms();
comment|// for (NounSynset instanceHyponym : instanceHyponyms) {
comment|// addWordForms(instanceHyponym.getWordForms(), currentExpansionLevel);
comment|// newNounSynset.add(instanceHyponym);
comment|// }
comment|//
comment|// // Holonyms
comment|// NounSynset[] memberHolonyms = nounSynset.getMemberHolonyms();
comment|// for (NounSynset memberHolonym : memberHolonyms) {
comment|// addWordForms(memberHolonym.getWordForms(), currentExpansionLevel);
comment|// newNounSynset.add(memberHolonym);
comment|// }
comment|// NounSynset[] substanceHolonyms = nounSynset.getSubstanceHolonyms();
comment|// for (NounSynset substanceHolonym : substanceHolonyms) {
comment|// addWordForms(substanceHolonym.getWordForms(), currentExpansionLevel);
comment|// newNounSynset.add(substanceHolonym);
comment|// }
comment|// NounSynset[] partHolonyms = nounSynset.getPartHolonyms();
comment|// for (NounSynset partHolonym : partHolonyms) {
comment|// addWordForms(partHolonym.getWordForms(), currentExpansionLevel);
comment|// newNounSynset.add(partHolonym);
comment|// }
comment|//
comment|// // Meronyms
comment|// NounSynset[] memberMeronyms = nounSynset.getMemberMeronyms();
comment|// for (NounSynset memberMeronym : memberMeronyms) {
comment|// addWordForms(memberMeronym.getWordForms(), currentExpansionLevel);
comment|// newNounSynset.add(memberMeronym);
comment|// }
comment|// NounSynset[] partMeronyms = nounSynset.getPartMeronyms();
comment|// for (NounSynset partMeronym : partMeronyms) {
comment|// addWordForms(partMeronym.getWordForms(), currentExpansionLevel);
comment|// newNounSynset.add(partMeronym);
comment|// }
comment|//
comment|//
comment|// NounSynset[] substanceMeronyms = nounSynset.getSubstanceMeronyms();
comment|// for (NounSynset substanceMeronym : substanceMeronyms) {
comment|// addWordForms(substanceMeronym.getWordForms(), currentExpansionLevel);
comment|// newNounSynset.add(substanceMeronym);
comment|// }
name|handleSynset
argument_list|(
name|nounSynset
operator|.
name|getHypernyms
argument_list|()
argument_list|,
name|newNounSynset
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|handleSynset
argument_list|(
name|nounSynset
operator|.
name|getInstanceHypernyms
argument_list|()
argument_list|,
name|newNounSynset
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|handleSynset
argument_list|(
name|nounSynset
operator|.
name|getHyponyms
argument_list|()
argument_list|,
name|newNounSynset
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|handleSynset
argument_list|(
name|nounSynset
operator|.
name|getInstanceHyponyms
argument_list|()
argument_list|,
name|newNounSynset
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|handleSynset
argument_list|(
name|nounSynset
operator|.
name|getMemberHolonyms
argument_list|()
argument_list|,
name|newNounSynset
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|handleSynset
argument_list|(
name|nounSynset
operator|.
name|getSubstanceHolonyms
argument_list|()
argument_list|,
name|newNounSynset
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|handleSynset
argument_list|(
name|nounSynset
operator|.
name|getPartHolonyms
argument_list|()
argument_list|,
name|newNounSynset
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|handleSynset
argument_list|(
name|nounSynset
operator|.
name|getMemberMeronyms
argument_list|()
argument_list|,
name|newNounSynset
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|handleSynset
argument_list|(
name|nounSynset
operator|.
name|getSubstanceMeronyms
argument_list|()
argument_list|,
name|newNounSynset
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|handleSynset
argument_list|(
name|nounSynset
operator|.
name|getPartMeronyms
argument_list|()
argument_list|,
name|newNounSynset
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
block|}
return|return
name|newNounSynset
operator|.
name|toArray
argument_list|(
operator|new
name|NounSynset
index|[
name|newNounSynset
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
specifier|private
name|void
name|handleSynset
parameter_list|(
name|NounSynset
index|[]
name|parts
parameter_list|,
name|List
argument_list|<
name|NounSynset
argument_list|>
name|accumulator
parameter_list|,
name|int
name|currentExpansionLevel
parameter_list|)
block|{
for|for
control|(
name|NounSynset
name|part
range|:
name|parts
control|)
block|{
name|addWordForms
argument_list|(
name|part
operator|.
name|getWordForms
argument_list|()
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|accumulator
operator|.
name|add
argument_list|(
name|part
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|VerbSynset
index|[]
name|handleVerbs
parameter_list|(
name|Synset
index|[]
name|verbSynsets
parameter_list|,
name|int
name|currentExpansionLevel
parameter_list|)
block|{
if|if
condition|(
name|verbSynsets
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|List
argument_list|<
name|VerbSynset
argument_list|>
name|newVerbSynset
init|=
operator|new
name|ArrayList
argument_list|<
name|VerbSynset
argument_list|>
argument_list|()
decl_stmt|;
comment|// TODO: Not all methods of a VerbSynset is called.
for|for
control|(
name|Synset
name|synset
range|:
name|verbSynsets
control|)
block|{
name|VerbSynset
name|verbSynset
init|=
operator|(
name|VerbSynset
operator|)
name|synset
decl_stmt|;
name|VerbSynset
index|[]
name|hypernyms
init|=
name|verbSynset
operator|.
name|getHypernyms
argument_list|()
decl_stmt|;
for|for
control|(
name|VerbSynset
name|hypernym
range|:
name|hypernyms
control|)
block|{
name|addWordForms
argument_list|(
name|hypernym
operator|.
name|getWordForms
argument_list|()
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|newVerbSynset
operator|.
name|add
argument_list|(
name|hypernym
argument_list|)
expr_stmt|;
block|}
name|VerbSynset
index|[]
name|troponyms
init|=
name|verbSynset
operator|.
name|getTroponyms
argument_list|()
decl_stmt|;
for|for
control|(
name|VerbSynset
name|troponym
range|:
name|troponyms
control|)
block|{
name|addWordForms
argument_list|(
name|troponym
operator|.
name|getWordForms
argument_list|()
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|newVerbSynset
operator|.
name|add
argument_list|(
name|troponym
argument_list|)
expr_stmt|;
block|}
name|VerbSynset
index|[]
name|entailments
init|=
name|verbSynset
operator|.
name|getEntailments
argument_list|()
decl_stmt|;
for|for
control|(
name|VerbSynset
name|entailment
range|:
name|entailments
control|)
block|{
name|addWordForms
argument_list|(
name|entailment
operator|.
name|getWordForms
argument_list|()
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|newVerbSynset
operator|.
name|add
argument_list|(
name|entailment
argument_list|)
expr_stmt|;
block|}
name|VerbSynset
index|[]
name|outcomes
init|=
name|verbSynset
operator|.
name|getOutcomes
argument_list|()
decl_stmt|;
for|for
control|(
name|VerbSynset
name|outcome
range|:
name|outcomes
control|)
block|{
name|addWordForms
argument_list|(
name|outcome
operator|.
name|getWordForms
argument_list|()
argument_list|,
name|currentExpansionLevel
argument_list|)
expr_stmt|;
name|newVerbSynset
operator|.
name|add
argument_list|(
name|outcome
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|newVerbSynset
operator|.
name|toArray
argument_list|(
operator|new
name|VerbSynset
index|[
name|newVerbSynset
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
specifier|private
name|void
name|addWordForms
parameter_list|(
name|String
index|[]
name|wordForms
parameter_list|,
name|int
name|currentExpansionLevel
parameter_list|)
block|{
for|for
control|(
name|String
name|wordForm
range|:
name|wordForms
control|)
block|{
name|relatedKeywordList
operator|.
name|add
argument_list|(
operator|new
name|RelatedKeywordImpl
argument_list|(
name|wordForm
argument_list|,
name|maxScore
operator|/
operator|(
name|degradingFactor
operator|*
name|currentExpansionLevel
operator|)
argument_list|,
name|RelatedKeyword
operator|.
name|Source
operator|.
name|WORDNET
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
