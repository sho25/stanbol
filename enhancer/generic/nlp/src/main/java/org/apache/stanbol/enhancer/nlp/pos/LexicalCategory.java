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
name|nlp
operator|.
name|pos
package|;
end_package

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
name|UriRef
import|;
end_import

begin_comment
comment|/**  * Lexical categories used by the Stanbol Enhancer NLP module. Defined based on the top level  * MorphosyntacticCategory as defined by the<a heref="http://olia.nlp2rdf.org/">Olia</a> Ontology.  */
end_comment

begin_enum
specifier|public
enum|enum
name|LexicalCategory
block|{
comment|/**      * A noun, or noun substantive, is a part of speech (a word or phrase) which can co-occur with      * (in)definite articles and attributive adjectives, and function as the head of a noun phrase.      *       * The word "noun" derives from the Latin 'nomen' meaning "name", and a traditional definition of nouns is      * that they are all and only those expressions that refer to a person, place, thing, event, substance,      * quality, idea or an appointment. They serve as the subject or object of a verb, and the object of a      * preposition. (http://en.wikipedia.org/wiki/Noun 19.09.06)      */
name|Noun
block|,
comment|/**      * A verb is a part of speech that usually denotes action ("bring", "read"), occurrence ("decompose",      * "glitter"), or a state of being ("exist", "stand"). Depending on the language, a verb may vary in form      * according to many factors, possibly including its tense, aspect, mood and voice. It may also agree with      * the person, gender, and/or number of some of its arguments (subject, object, etc.).      * (http://en.wikipedia.org/wiki/Verb 19.09.06)      */
name|Verb
block|,
comment|/**      * An Adjective is a noun-modifying expression that specifies the properties or attributes of the nominal      * referent. (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAnAdjective.htm 18.9.06)      */
name|Adjective
block|,
comment|/**      * An adposition is a cover term for prepositions, postpositions and circumpositions. It expresses a      * grammatical and semantic relation to another unit within a clause.      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAnAdposition.htm,      * http://en.wikipedia.org/wiki/Adposition 19.09.06)      *       * The majority of cases of adpositions we have to consider in European languages are prepositions.      * (http://www.ilc.cnr.it/EAGLES96/annotate/node17.html#SECTION00062200000000000000 19.09.06)      */
name|Adposition
block|,
comment|/**      * An adverb is a part of speech that serves to modify non-nominal parts of speech, i.e., verbs,      * adjectives (including numbers), clauses, sentences and other adverbs. Modifiers of nouns are primarily      * determiners and adjectives. (http://en.wikipedia.org/wiki/Adverbs 18.09.06)      */
name|Adverb
block|,
comment|/**      * A conjunction is a word that syntactically links words or larger constituents, and expresses a semantic      * relationship between them.      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAConjunction.htm 19.09.06)      */
name|Conjuction
block|,
comment|/**      * An interjection is a form, typically brief, such as one syllable or word, which is used most often as      * an exclamation or part of an exclamation. It typically expresses an emotional reaction, often with      * respect to an accompanying sentence and may include a combination of sounds not otherwise found in the      * language, e.g. in English: psst; ugh; well, well      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAnInterjection.htm 19.09.06)      */
name|Interjection
block|,
comment|/**      * The parts of speech Pronoun, Determiner and Article heavily overlap in their formal and functional      * characteristics, and different analyses for different languages entail separating them out in different      * ways. In Eagles, Pronouns and Determiners are placed in one `super-category'. For some descriptions it      * may be thought best to treat them as totally different parts of speech.      * (http://www.ilc.cnr.it/EAGLES96/annotate/node17.html#recp 19.09.06)      */
name|PronounOrDeterminer
block|,
comment|/**      * Punctuation marks (PU) are treated here as a part of morphosyntactic annotation, as it is very common      * for punctuation marks to be tagged and to be treated as equivalent to words for the purposes of      * automatic tag assignment. (http://www.ilc.cnr.it/EAGLES96/annotate/node16.html#mp 19.09.06)      */
name|Punctuation
block|,
comment|/**      * A quantifier is a determiner that expresses a referent's definite or indefinite number or amount. A      * quantifier functions as a modifier of a noun, or pronoun.      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAQuantifier.htm 19.09.06)      */
name|Quantifier
block|,
comment|/**      * From a linguistic point of view, Residuals are a heterogeneous class and so, Residual may overlap with      * every linguistically motivate annotation concept. Also between subconcepts, overlap may occur (e.g.      * \LaTeX which is a symbol which can be read as an Acronym or acronyms which are related to      * Abbreviations, e.g. GNU "Gnu is not Unix")      */
name|Residual
block|,
comment|/**      * Unique approximates the linguistic concept "Particle". It covers categories with unique or very small      * membership, such as negative particle, which are `unassigned' to any of the standard part-of-speech      * categories. (http://www.ilc.cnr.it/EAGLES96/annotate/node16.html#mp 19.09.06)      */
name|Unique
block|,     ;
specifier|static
specifier|final
name|String
name|OLIA_NAMESPACE
init|=
literal|"http://purl.org/olia/olia.owl#"
decl_stmt|;
name|UriRef
name|uri
decl_stmt|;
name|LexicalCategory
parameter_list|()
block|{
name|this
operator|.
name|uri
operator|=
operator|new
name|UriRef
argument_list|(
name|OLIA_NAMESPACE
operator|+
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|UriRef
name|getUri
parameter_list|()
block|{
return|return
name|uri
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
literal|"olia:"
operator|+
name|name
argument_list|()
return|;
block|}
block|}
end_enum

end_unit

