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
name|morpho
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
import|;
end_import

begin_comment
comment|/**  * Enumeration representing the different verbal moods based on the<a* href="http://purl.org/olia/olia.owl">OLIA</a> Ontology  *   */
end_comment

begin_enum
specifier|public
enum|enum
name|VerbMood
block|{
comment|/**      * An infinitive is the base form of a verb. It is unmarked for inflectional categories such as the following: Aspect, Modality, Number, Person and Tense. 	 * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAnInfinitive.htm 19.09.06)      */
name|Infinitive
block|,
comment|/** 	 * A verbal noun is a noun formed directly as an inflexion of a verb or a verb stem, sharing at least in part its constructions. 	 * This term is applied especially to gerunds, and sometimes also to infinitives and supines. (http://en.wikipedia.org/wiki/Verbal_noun 19.09.06) 	 */
name|VerbalNoun
block|,
comment|/** 	 * A participle is a lexical item, derived from a verb that has some of the characteristics and functions of both verbs and adjectives. 	 * In English, participles may be used as adjectives, and in non-finite forms of verbs. (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAParticiple.htm 19.09.06) 	 */
comment|/** 	 * Supine is a nonfinite form of motion verbs with functions similar to that of an infinitive (Angelika Adams) 	 */
name|Supine
block|,
name|Participle
block|,
comment|/** 	 *  A gerund is a kind of verbal noun that exists in some languages. In today's English, gerunds are nouns built from a verb with an '-ing' suffix.  	 *  They can be used as the subject of a sentence, an object, or an object of preposition. They can also be used to complement a subject.  	 *  Often, gerunds exist side-by-side with nouns that come from the same root but the gerund and the common noun have different shades of meaning.  	 *  (http://en.wikipedia.org/wiki/Gerund, http://en.wikibooks.org/wiki/English:Gerund 19.09.06) property for a non-finite form of a verb other than the infinitive. (http://www.isocat.org/datcat/DC-2243) 	 */
name|Gerund
block|,
comment|/** 	 * A subjunctive verb is typically used to expresses wishes,commands (in subordinate clauses), emotion, possibility, 	 * judgment, necessity, and statements that are contrary to fact at present. (http://en.wikipedia.org/wiki/Subjunctive_mood 19.09.06) 	 */
name|SubjunctiveVerb
block|,
comment|/** 	 * A conditional verb is a verb form in many languages. It is used to express degrees of certainty or uncertainty and hypothesis 	 * about past, present, or future. Such forms often occur in conditional sentences. (http://en.wikipedia.org/wiki/Conditional_mood 19.09.06) 	 */
name|ConditionalVerb
block|,
comment|/** 	 * An imperative verb is used to express commands, direct requests, and prohibitions. Often, direct use of the imperative mood may appear  	 * blunt or even rude, so it is often used with care. Example: "Paul,read that book".(http://en.wikipedia.org/wiki/Grammatical_mood#Imperative_mood 19.09.06) 	 */
name|ImperativeVerb
block|,
comment|/** 	 * Indicative mood is used in factual statements. All intentions in speaking that a particular language does not put into another mood 	 * use the indicative. It is the most commonly used mood and is found in all languages.(http://en.wikipedia.org/wiki/Grammatical_mood#Indicative_mood 19.09.06) 	 */
name|IndicativeVerb
block|,     ;
specifier|static
specifier|final
name|String
name|OLIA_NAMESPACE
init|=
literal|"http://purl.org/olia/olia.owl#"
decl_stmt|;
name|IRI
name|uri
decl_stmt|;
name|VerbMood
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|VerbMood
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|uri
operator|=
operator|new
name|IRI
argument_list|(
name|OLIA_NAMESPACE
operator|+
operator|(
name|name
operator|==
literal|null
condition|?
name|name
argument_list|()
else|:
operator|(
name|name
operator|+
literal|"Verb Form"
operator|)
operator|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|IRI
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
name|uri
operator|.
name|getUnicodeString
argument_list|()
return|;
block|}
block|}
end_enum

end_unit

