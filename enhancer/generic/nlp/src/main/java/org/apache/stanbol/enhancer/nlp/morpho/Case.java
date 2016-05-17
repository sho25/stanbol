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
name|Set
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

begin_comment
comment|/**  * Defines verb tenses as defined by the<a href="">OLIA</a> Ontology.  *<p>  * The hierarchy is represented by this enumeration. The {@link Set} of parent concepts is accessible via the  * {@link #getParent()} and {@link #getTenses()}.  */
end_comment

begin_enum
specifier|public
enum|enum
name|Case
block|{
comment|/**      * AbessiveCase expresses the lack or absence of the referent of the noun it marks. It has the meaning of      * the English preposition 'without' (Pei and Gaynor 1954: 3,35; Gove, et al. 1966: 3).      * (http://purl.org/linguistics/gold/Abessive)      */
name|Abessive
block|,
comment|/**      * Case used to indicate locative or instrumental function. (http://www.isocat.org/datcat/DC-1224)      *       * AblativeCase expresses that the referent of the noun it marks is the location from which another      * referent is moving. It has the meaning 'from'. (http://purl.org/linguistics/gold/Ablative)      */
name|Ablative
block|,
comment|/**      * Absolutive case marks the first argument of an intransitive verb and the second argument of a      * transitive verb in ergative-absolutive languages.      * (http://languagelink.let.uu.nl/tds/onto/LinguisticOntology.owl#absolutiveCase)      */
name|Absolutive
block|,
comment|/**      * In nominative-accusative languages, accusative case marks certain syntactic functions, usually direct      * objects. (http://www.sil.org/linguistics/glossaryoflinguisticterms/WhatIsAccusativeCase.htm 17.11.06)      */
name|Accusative
argument_list|(
literal|"Accusative"
argument_list|)
block|,
comment|/**      * AdessiveCase expresses that the referent of the noun it marks is the location near/at which another      * referent exists. It has the meaning of 'at' or 'near' (Crystal 1997: 8).      * (http://purl.org/linguistics/gold/Adessive)      */
name|Adessive
block|,
comment|/**      * Case expressing "to" in Basque studies. (http://www.isocat.org/datcat/DC-1229)      */
name|Aditive
block|,
comment|/**      * AllativeCase expresses motion to or toward the referent of the noun it marks (Pei and Gaynor 1954:      * 6,9,216; Lyons 1968: 299; Crystal 1985: 1213; Gove, et al. 1966: 55,2359).      * (http://purl.org/linguistics/gold/Allative)      */
name|Allative
block|,
comment|/**      * BenefactiveCase expresses that the referent of the noun it marks receives the benefit of the situation      * expressed by the clause (Crystal 1980: 43; Gove, et al. 1966: 203).      * (http://purl.org/linguistics/gold/Benefactive)      */
name|Benefactive
block|,
comment|/**      * Case which expresses that the referent of the noun it marks is the cause of the situation expressed by      * the clause. (http://www.isocat.org/datcat/DC-1253)      */
name|Causative
block|,
comment|/**      * ComitativeCase expresses accompaniment. It carries the meaning 'with' or 'accompanied by' (Anderson,      * Stephen 1985: 186; Pei and Gaynor 1954: 42;Dixon, R. 1972: 12; Gove, et al. 1966: 455).      * (http://purl.org/linguistics/gold/Comitative)      */
name|Comitative
block|,
comment|/**      * ContablativeCase expresses that the referent of the noun it marks is the location from near which      * another referent is moving. It has the meaning 'from near'.      * (http://purl.org/linguistics/gold/Contablative)      */
name|Contablative
block|,
comment|/**      * ContallativeCase expresses that something is moving toward the vicinity of the referent of the noun it      * marks. It has the meaning 'towards the vicinity of'. (http://purl.org/linguistics/gold/Contallative)      */
name|Contallative
block|,
comment|/**      * ConterminativeCase expresses the notion of something moving into the vicinity of the referent of the      * noun it marks, but not through that region. It has the meaning 'moving into the vicinity of'.      * (http://purl.org/linguistics/gold/Conterminative)      */
name|Conterminative
block|,
comment|/**      * ContlativeCase expresses that the referent of the noun it marks is the location in the vicinity of      * which another referent is moving. It has the meaning 'in the vicinity of'.      * (http://purl.org/linguistics/gold/Contlative)      */
name|Contlative
block|,
comment|/**      * Dative case marks indirect objects (for languages in which they are held to exist), or nouns having the      * role of a recipient (as of things given), a beneficiary of an action, or a possessor of an item.      * (http://www.sil.org/linguistics/glossaryoflinguisticterms/WhatIsDativeCase.htm 17.11.06)      */
name|Dative
block|,
comment|/**      * DelativeCase expresses motion downward from the referent of the noun it marks (Pei and Gaynor 1954: 53;      * Gove, et al. 1966: 595). (http://purl.org/linguistics/gold/Delative)      */
name|Delative
block|,
comment|/**      * In the Romanian case system the value 'direct' conflates 'nominative' and 'accusative', e.g.,      * -acea/acel, -aceasta/acesta, -această/acest (http://purl.org/olia/mte/multext-east.owl#DirectCase)      */
name|Direct
block|,
comment|/**      * The distributive case is used on nouns for the meanings of per or each, e.g., Hungarian egyenként/egy,      * hetenként/hét, ilyenként/ily, kéthetenként/kéthét, rekordonként/rekord, tömbönként/tömb,      * vércsoportonként/vércsoport      *       * In Hungarian it is -nként and expresses the manner when something happens to each member of a set one      * by one (e.g., fejenként "per head", esetenként "in some case"), or the frequency in time (hetenként      * "once a week", tízpercenként "every ten minutes"). In the Finnish language, this adverb type is rare,      * even rarer in the singular. Its ending is -ttain/-ttäin. The basic meaning is "separately for each".      * For example, maa ("country") becomes maittain for an expression like Laki ratifioidaan maittain      * ("The law is ratified separately in each country"). It can be used to distribute the action to frequent      * points in time, e.g., päivä (day) has the plural distributive päivittäin (each day). It can mean also      * "in (or with) regard to the (cultural) perspective" when combined with a word referring to an      * inhabitant (-lais-). Frequently Finns (suomalaiset) say that suomalaisittain tuntuu oudolta, että, or      * "in the Finnish perspective, it feels strange that".      * (http://purl.org/olia/mte/multext-east.owl#DistributiveCase,      * http://en.wikipedia.org/wiki/Distributive_case)      */
name|Distributive
block|,
comment|/**      * ElativeCase expresses that the referent of the noun it marks is the location out of which another      * referent is moving. It has the meaning 'out of' (Lyons 1968: 299; Pei and Gaynor 1954: 64; Crystal      * 1985: 106; Gove, et al. 1966: 730). (http://purl.org/linguistics/gold/Elative)      */
name|Elative
block|,
comment|/**      * Case that expresses likeness or identity to the referent of the noun it marks. It can have meaning,      * such as: 'as', 'like', or 'in the capacity of'. (http://www.isocat.org/datcat/DC-1279)      */
name|Equative
block|,
comment|/**      * In ergative-absolutive languages, the ergative case identifies the subject of a transitive verb. In      * such languages, the ergative case is typically marked (most salient), while the absolutive case is      * unmarked. (http://languagelink.let.uu.nl/tds/onto/LinguisticOntology.owl#ergativeCase with reference to      * http://en.wikipedia.org/wiki/Ergative_case).      */
name|Ergative
block|,
comment|/**      * EssiveCase expresses that the referent of the noun it marks is the location at which another referent      * exists (Lyons 1968: 299,301; Gove, et al. 1966: 778; Crystal 1985: 112; Blake 1994: 154-5).      * (http://purl.org/linguistics/gold/Essive)      */
name|Essive
block|,
comment|/**      * The Hungarian      * "formativus, or essivus-formalis `-ként' ... usually expresses a position, task and manner of the person or the thing."      * (Nose 2003), e.g., Hungarian 'katonaként' -> [serves] as a soldier. (Csaba Oravecz, email 2010/06/15)<br/>      *<br/>      *       * "Haspelmath& Buchholz (1998:321) explained the function of the essive case as ``role phrases''. Role phrases represent the role of the function in which a participant appears. They regard the role phrases as adverbial."      * (Nose 2003, p. 117)<br/>      *       * In the Hungarian language this case combines the Essive case and the Formal case, and it can express      * the position, task, state (e.g. "as a tourist"), or the manner (e.g. "like a hunted animal"). The      * status of the suffix -ként in the declension system is disputed for several reasons. First, in general,      * Hungarian case suffixes are absolute word-final, while -ként permits further suffixation by the      * locative suffix -i. Second, most Hungarian case endings participate in vowel harmony, while -ként does      * not. For these reasons, many modern analyses of the Hungarian case system, starting with László Antal's      * "A magyar esetrendszer" (1961) do not consider the essive/formal to be a case.      * (http://en.wikipedia.org/wiki/Essive-formal_case)<br/>      *       * cf. Masahiko Nose (2003), Adverbial Usage of the Hungarian Essive Case      */
name|EssiveFormal
block|,
comment|/**      * case category of the Hungarian MULTEXT-East scheme, e.g., amilyenné/amilyen, azzá/az, erőddé/erő,      * jelmezeivé/jelmez, jelükké/jel, kevéssé/kevés, Kissé/Kiss, legjelentéktelenebbekké/jelentéktelen (hu)      * (http://purl.org/olia/mte/multext-east.owl#FactiveCase)      */
name|Factive
block|,
comment|/**      * In Hungarian, `essive-formal' is in some descriptions simply called `formal', with the affix      * _-képp(en)_ and meaning (`in the form of ...', they probably meant when they came up with the term). In      * the Hungarian MULTEXT-East scheme, essive-formal and formal are distinguished. (Ivan A. Derzhanski,      * email 2010/06/15, http://purl.org/olia/mte/multext-east.owl#FormalCase)      */
name|Formal
block|,
comment|/**      * Genitive case signals that the referent of the marked noun is the possessor of the referent of another      * noun, e.g. "the man's foot". In some languages, genitive case may express an associative relation      * between the marked noun and another noun.      * (http://www.sil.org/linguistics/glossaryoflinguisticterms/WhatIsGenitiveCase.htm 17.11.06)      */
name|Genitive
block|,
comment|/**      * IllativeCase expresses that the referent of the noun it marks is the location into which another      * referent is moving. It has the meaning 'into' (Lyons 1968: 299; Gove, et al. 1966: 1126; Crystal 1985:      * 152). (http://purl.org/linguistics/gold/Illative)      */
name|Illative
block|,
comment|/**      * InablativeCase expresses that the referent of the noun it marks is the location from within which      * another referent is moving. It has the meaning 'from within'.      * (http://purl.org/linguistics/gold/Inablative)      */
name|Inablative
block|,
comment|/**      * InallativeCase expresses that something is moving toward the region that is inside the referent of the      * noun it marks. It has the meaning 'towards in(side)'. (http://purl.org/linguistics/gold/Inallative)      */
name|Inallative
block|,
comment|/**      * InessiveCase expresses that the referent of the noun it marks is the location within which another      * referent exists. It has the meaning of 'within' or 'inside' (Lyons 1968: 299; Gove, et al. 1966: 1156;      * Crystal 1985: 156). X in Y. (http://purl.org/linguistics/gold/Inessive)      */
name|Inessive
block|,
comment|/**      * InstrumentalCase indicates that the referent of the noun it marks is the means of the accomplishment of      * the action expressed by the clause (http://purl.org/linguistics/gold/Instrumental)      */
name|Instrumental
block|,
comment|/**      * InterablativeCase expresses that the referent of the noun it marks is the location from between which      * another referent is moving. It has the meaning 'from inbetween'.      * (http://purl.org/linguistics/gold/Interablative)      */
name|Interablative
block|,
comment|/**      * InterallativeCase expresses that something is moving toward the region that is in the middle of the      * referent of the noun it marks. It has the meaning 'towards the middle of'.      * (http://purl.org/linguistics/gold/Interallative)      */
name|Interallative
block|,
comment|/**      * InteressiveCase expresses that the referent of the noun it marks is the location between which another      * referent exists. It has the meaning of 'between'. (http://purl.org/linguistics/gold/Interessive)      */
name|Interessive
block|,
comment|/**      * InterlativeCase expresses that the referent of the noun it marks is the location between which another      * referent is moving. It has the meaning 'to the middle of'.      * (http://purl.org/linguistics/gold/Interlative)      */
name|Interlative
block|,
comment|/**      * 'into in(side of)'. (http://purl.org/linguistics/gold/Interminative)      */
name|Interminative
block|,
comment|/**      * InterterminativeCase expresses the notion of something moving into the middle of the referent of the      * noun it marks, but not through it. It has the meaning 'into the middle of'.      * (http://purl.org/linguistics/gold/Interminative)      */
name|Interterminative
block|,
comment|/**      * IntertranslativeCase expresses the notion of something moving along a trajectory between the referent      * of the noun it marks. It has the meaning 'along the in between.      * (http://purl.org/linguistics/gold/Intertranslative)      */
name|Intertranslative
block|,
comment|/**      * IntranslativeCase expresses the notion of something moving through the referent of the noun it marks.      * It has the meaning 'along through'. (http://purl.org/linguistics/gold/Intranslative)      */
name|Intranslative
block|,
comment|/**      * LativeCase expresses 'motion up to the location of,' or 'as far as' the referent of the noun it marks      * (Pei and Gaynor 1954: 121; Gove, et al. 1966: 1277). (http://purl.org/linguistics/gold/Lative)      */
name|Lative
block|,
comment|/**      * Category of case that denotes that the referent of the noun it marks is a location.      * (http://purl.org/linguistics/gold/Locational)      */
name|Locational
block|,
comment|/**      * Case that indicates a final location of action or a time of the action.      * (http://www.isocat.org/datcat/DC-1326)      */
name|Locative
block|,
comment|/**      * Opposite of BenefactiveCase; used when the marked noun is negatively affected in the clause.      * (http://purl.org/linguistics/gold/Malefactive)      */
name|Malefactive
block|,
comment|/**      * The multiplicative case is a grammatical case used for marking a number of something ("three times").      * The case is found in the Hungarian language, for example nyolc (eight), nyolcszor (eight times). The      * case appears also in Finnish as an adverbial (adverb-forming) case. Used with a cardinal number it      * denotes the number of actions; for example, viisi (five) -> viidesti (five times). Used with adjectives      * it refers to the mean of the action, corresponding the English suffix -ly: kaunis (beautiful) ->      * kauniisti (beautifully). It is also used with a small number of nouns: leikki (play) -> leikisti (just      * kidding, not really). In addition, it acts as an intensifier when used with a swearword: piru ->      * pirusti. (http://en.wikipedia.org/wiki/Multiplicative_case)      */
name|Multiplicative
block|,
comment|/**      * In nominative-accusative languages, nominative case marks clausal subjects and is applies to nouns in      * isolation. (http://www.sil.org/linguistics/glossaryoflinguisticterms/WhatIsNominativeCase.htm 17.11.06)      */
name|Nominative
argument_list|(
literal|"Nominative"
argument_list|)
block|,
comment|/**      * Case that is used when a noun is the object of a verb or a proposition, except for nominative and      * vocative case. (http://www.isocat.org/datcat/DC-1336)      */
name|Oblique
block|,
comment|/**      * The partitive case is a grammatical case which denotes "partialness", "without result", or      * "without specific identity".      * (http://languagelink.let.uu.nl/tds/onto/LinguisticOntology.owl#partitiveCase with reference to      * http://en.wikipedia.org/wiki/Partitive)      *       * PartitiveCase expresses the partial nature of the referent of the noun it marks, as opposed to      * expressing the whole unit or class of which the referent is a part. This case may be found in items      * such as the following: existential clauses, nouns that are accompanied by numerals or units of measure,      * or predications of material from which something is made. It often has a meaning similar to the English      * word 'some' (Pei and Gaynor 1954: 161; Richards, Platt, and Weber 1985: 208; Quirk, et al. 1985: 249;      * Gove, et al. 1966: 1648; Sebeok 1946: 1214). (http://purl.org/linguistics/gold/Partitive)      */
name|Partitive
block|,
comment|/**      * PerlativeCase expresses that something moved 'through','across', or 'along' the referent of the noun      * that is marked (Blake 1998: 38, 203). (http://purl.org/linguistics/gold/Perlative)      */
name|Perlative
block|,
comment|/**      * PossessedCase is used to mark the noun whose referent is possessed by the referent of another noun.      * (http://purl.org/linguistics/gold/Possessed)      */
name|Possessed
block|,
comment|/**      * In many grammars, the term "prepositional case" is to refer to case marking that only occurs in      * combination with prepositions. Normally, this is an oblique case, e.g., the Russian 6th case, also      * referred to as "locative". (Ch. Chiarcos)      */
name|Prepositional
block|,
comment|/**      * Case for a noun or a pronoun that expresses motion within a place or a period of time needed for an      * event. (http://www.isocat.org/datcat/DC-1368)      */
name|Prolative
block|,
comment|/**      * Proprietive case marks a possessional relation, i.e. 'having' something.      * (http://languagelink.let.uu.nl/tds/onto/LinguisticOntology.owl#proprietiveCase-grammatical)      */
name|Proprietive
block|,
comment|/**      * Purposive marks the goal of an activity, e.g., 'going out FOR (i.e. to catch) KANGAROOS'; 'call them      * FOR (i.e. to eat) FOOD'. The common purposive suffix -gu is a recurrent suffix on verbs ... The      * purposive case suffix is often used on a nominalised clause (and this may possibly be the origin of the      * verbal purposive). (Dixon 2002, p.134, on purposive case in [several] Australian languages)      *       * R.M.W. Dixon (2002), Australian Languages. CUP, Cambridge      */
name|Purposive
block|,
comment|/**      * Case related to the person in whose company the action is carried out, or to any belongings of people      * which take part in the action. (http://www.isocat.org/datcat/DC-1388)      */
name|Sociative
block|,
comment|/**      * SubablativeCase expresses that the referent of the noun it marks is the location from under which      * another referent is moving. It has the meaning 'from under'.      * (http://purl.org/linguistics/gold/Subablative)      */
name|Subablative
block|,
comment|/**      * SuballativeCase expresses that something is moving toward the region that is under the referent of the      * noun it marks. It has the meaning 'towards the region that is under'.      * (http://purl.org/linguistics/gold/Suballative)      */
name|Suballative
block|,
comment|/**      * SubessiveCase expresses that the referent of the noun it marks is the location under which another      * referent exists. It has the meaning of 'under' or 'beneath'.      * (http://purl.org/linguistics/gold/Subessive)      */
name|Subessive
block|,
comment|/**      * SublativeCase expresses that the referent of the noun it marks is the location under which another      * referent is moving toward. It has the meaning 'towards the underneath of'.      * (http://purl.org/linguistics/gold/Sublative)      */
name|Sublative
block|,
comment|/**      * SubterminativeCase expresses the notion of something moving into the region under the referent of the      * noun it marks, but not through that region. It has the meaning 'into the region under'.      * (http://purl.org/linguistics/gold/Subterminative)      */
name|Subterminative
block|,
comment|/**      * SubtranslativeCase expresses the notion of something moving along a trajectory underneath the referent      * of the noun it marks. It has the meaning 'along the region underneath'. Unfortunate name clash with      * 'Superlative' as a feature of adjectives. (http://purl.org/linguistics/gold/Subtranslative)      */
name|Subtranslative
block|,
comment|/**      * Superablative expresses that the referent of the noun it marks is the location from over which another      * referent is moving. It has the meaning 'from over'. (http://purl.org/linguistics/gold/Superablative)      */
name|Superablative
block|,
comment|/**      * SuperallativeCase expresses that something is moving toward the region that is above the referent of      * the noun it marks. It has the meaning 'towards the region that is over'.      * (http://purl.org/linguistics/gold/Superallative)      */
name|Superallative
block|,
comment|/**      * SuperessiveCase expresses that the referent of the noun it marks is the location on which another      * referent exists. It has the meaning of 'on' or 'upon'. (Pei and Gaynor 1954: 207, Gove, et al. 1966:      * 2293). (http://purl.org/linguistics/gold/Superessive)      */
name|Superessive
block|,
comment|/**      * SuperlativeCase expresses that the referent of the noun it marks is the location onto which another      * referent is moving. It has the meaning of 'onto'. Unfortunate name clash with 'Superlative' as a      * property of adjectives. (http://purl.org/linguistics/gold/Superlative)      */
name|Superlative
block|,
comment|/**      * SuperterminativeCase expresses the notion of something moving into the region over the referent of the      * noun it marks, but not through that region. It has the meaning 'into the region over'.      * (http://purl.org/linguistics/gold/Superterminative)      */
name|Superterminative
block|,
comment|/**      * SupertranslativeCase expresses the notion of something moving along a trajectory above the referent of      * the noun it marks. It has the meaning 'along the region over'.      * (http://purl.org/linguistics/gold/Supertranslative)      */
name|Supertranslative
block|,
comment|/**      * The so-called Temporalis Case is formed in Hungarian with -kor. Expresses a point of time or a period.      * (http://member.melbpc.org.au/~tmajlath/form-suffix.html)      */
name|Temporalis
block|,
comment|/**      * Case that indicates to what or where something ends. (http://www.isocat.org/datcat/DC-1401)      *       * TerminativeCase expresses the notion of something into but not further than (ie, not through) the      * referent of the noun it marks. It has the meaning 'into but not through'.      * (http://purl.org/linguistics/gold/TerminativeCase)      */
name|Terminative
block|,
comment|/**      * TranslativeCase expresses that the referent of the noun, or the quality of the adjective, that it marks      * is the result of a process of change (Lyons 1968: 299301, Gove, et al. 1966: 813,2429, Sebeok 1946: 17,      * Hakulinen 1961: 70). X along, across Y. (http://purl.org/linguistics/gold/Translative)      */
name|Translative
block|,
comment|/**      * In many inflecting languages, there occur lexemes whose form does not change throughout the paradigm, e.g., 	 * Russian papa "dad". For such forms, the category uninflected may be assigned. However, Uninflected is not to be confused with BaseForm  	 * that applies to forms in a paradigm where overt marking exists. Uninflected is a characteristic of lexemes, not individual tokens.      */
name|Uninflected
block|,
comment|/**      * Vocative case marks a noun whose referent is being addressed.      * (http://www.sil.org/linguistics/glossaryoflinguisticterms/WhatIsVocativeCase.htm 17.11.06)      */
name|Vocative
block|,      ;
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
name|Case
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|Case
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
literal|"Case"
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

