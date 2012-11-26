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
name|nlp
operator|.
name|pos
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|EnumMap
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
name|Map
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
name|rdf
operator|.
name|core
operator|.
name|UriRef
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
name|Tense
import|;
end_import

begin_import
import|import
name|com
operator|.
name|ibm
operator|.
name|icu
operator|.
name|impl
operator|.
name|Punycode
import|;
end_import

begin_comment
comment|/**  * Enumeration over all POS (Part of Speach) categories as defined by the   * MorphosyntacticCategory class hierarchy og the   *<a heref="http://olia.nlp2rdf.org/">Olia</a> Ontology.<p>  * The top-level morphosyntactic categories are not defined by this enum, but link  * to the {@link LexicalCategory} enumeration. The multi-sub-class hierarchy of the  * morphosyntactic categories is also reflected by this enumeration and accessible  * via the<ul>  *<li> {@link #parents()}: the direct parent categories  *<li> {@link #hierarchy()}: the transitive closure  *<li> {@link #categories()}: the {@link LexicalCategory LexicalCategories}  *</ul>  * Enumeration elements that represent classes that are deprecated within the Olia  * Ontology are also deprecated within this Enumeration.  */
end_comment

begin_enum
specifier|public
enum|enum
name|Pos
block|{
comment|/**      *       An attributive adjective is an adjective that qualifies or modifies a noun and that precedes the noun,      * e.g."a delicious apple", "a short letter".<br>      * (http://en.wikipedia.org/wiki/Adjective 18.09.06)      */
name|AttributiveAdjective
parameter_list|(
name|LexicalCategory
operator|.
name|Adjective
parameter_list|)
operator|,
comment|/**      * It is an adjective, which expresses the character and feature of subject or an object, while modifying      * a noun.      *       *<pre>      *     ང་འ ་ ང ་ འ ག།      *     Shing-di rim du      *     'The tree is tall'      *</pre>      *       *<br>      * (http://panl10n.net/english/Outputs%20Phase%202/CCs/Bhutan/Papers/2007/0701/PartOfSpeech.pdf)      */
constructor|CharacteristicAdjective(LexicalCategory.Adjective
block|)
enum|,
comment|/**      * It is an adjective, which expresses the time or period of the circumstances, while modifying a noun.      *       *<pre>      *     ན་ ང་ ང་ ་ ལ་ ་ འ ་ །      *     Nahing Nga chigyel-lu joyi      *     'I went abroad last year'      *</pre>      *       *<br>      * (http://panl10n.net/english/Outputs%20Phase%202/CCs/Bhutan/Papers/2007/0701/PartOfSpeech.pdf)      */
name|PeriodicAdjective
argument_list|(
name|LexicalCategory
operator|.
name|Adjective
argument_list|)
operator|,
comment|/**      * A PossessiveAdjective is an denominal adjective, often derived from a ProperNoun, that serves to      * indicate possession in most Slavic languages. Unlike a genitival construction, a possessive adjective      * shows agreement with its head noun. (Chiarcos)      *<p>      * Adjective/Type="possessive" are denominal, not pronominal expressions of possession (Ivan A Derzhanski,      * email 2010/06/09). Therefore not to be confused with Pronoun/Type=adjectival(a) (Bulgarian only), for      * words like умно /cleverly, wisely, sensibly/, which are derived from adjectives. (Dimitrova et al.      * 2009)      *<p>      * e.g., Slovene dušikovima/dušikov, Marsovi/Marsov,      *<p>      * Slovak vojvodova/vojvodov, vojvodove/vojvodov, vojvodovej/vojvodov, vojvodovho/vojvodov,      * vojvodovi/vojvodov, vojvodovmu/vojvodov, vojvodovo/vojvodov, vojvodovom/vojvodov, vojvodovou/vojvodov,      *<p>      * Serbian evroazijske/evroazijska, evroazijskih/evroazijski, Goldštajnov, govornikov, Jehovine/Jehovin,      * malabarskom/malabarski, O'Brajenov, O'Brajenovog/O'Brajenov, oficirov,      *<p>      * Czech Riegrovými/Riegrův, Stradellovými/Stradellův, Tristanovou/Tristanův, Wagnerových/Wagnerův,      * Wagnerovým/Wagnerův, Weberovi/Weberův, Weberových/Weberův, Wertherovi/Wertherův, Winstonovi/Winstonův<br>      * (http://purl.org/olia/mte/multext-east.owl#PossessiveAdjective)      */
name|PossessiveAdjective
argument_list|(
name|LexicalCategory
operator|.
name|Adjective
argument_list|)
operator|,
comment|/**      *       A predicative adjective is one which functions as part of the predicate of a sentence. This means that      * it is linked to the noun by a verb, often a copula (such as to be).<br>      * (http://en.wikipedia.org/wiki/Adjective 18.09.06)      */
name|PredicativeAdjective
argument_list|(
name|LexicalCategory
operator|.
name|Adjective
argument_list|)
operator|,
comment|/**      * Relative adjectives express similarity or a comparison. (Schmidt 1999, p.218,      * http://purl.org/olia/emille.owl#RelativeAdjective)      */
name|RelativeAdjective
argument_list|(
name|LexicalCategory
operator|.
name|Adjective
argument_list|)
operator|,
comment|/**      *       An adjective that modifies an implied, but not expressed, noun. When translating such an adjective into      * English, you must supply the missing noun. (www.southwestern.edu/~carlg/Latin_Web/glossary.html;      * http://www.isocat.org/datcat/DC-1394)      *<p>      * (Chiarcos: this seems to pertain to nominalization)      */
name|SubstantiveAdjective
argument_list|(
name|LexicalCategory
operator|.
name|Adjective
argument_list|)
operator|,
comment|/**      *       Adjective expressing a numeric ranking.<br>      * (http://www.isocat.org/datcat/DC-1338)      *<p>      * Cf. "second", "next", "last"      *       * @deprecated The DCR definition and this term are incorrect. "Ordinal adjective" entered ISOcat from      *             MULTEXT-East, where it was originally applied to relational adjectives (Slovene, Resian,      *             Ukrainian, Czech). "Ordinal adjective" is a mistranslation from Slovene _vrstni pridevniki_      *             that should be properly rendered in English as "relational adjective" (Derzhanski and      *             Kotsyba 2009). However, the Macedonian MULTEXT v.4 guidelines use this category for ordinal      *             numerals. Due to its inherent ambiguity, this category is to be avoided.      */
name|OrdinalAdjective
argument_list|(
name|LexicalCategory
operator|.
name|Adjective
argument_list|)
operator|,
comment|/**      * Adjective used to qualify.<br>      * (http://www.isocat.org/datcat/DC-1477)      */
name|QualifierAdjective
argument_list|(
name|LexicalCategory
operator|.
name|Adjective
argument_list|,
name|LexicalCategory
operator|.
name|Quantifier
argument_list|)
operator|,
comment|/**      * The Slovene adjective expresses three main ideas: quality (qualitative adjectives, kakovostni      * pridevniki), relation (relational adjectives, vrstni pridevniki) and possession (possessive adjectives,      * svojilni pridevniki). Relational adjectives express type, class or numerical sequence of a noun. For      * instance: kemijska in fizikalna sprememba (chemical and physical change), fotografski aparat      * (photographic device (=camera)).<br>      * (http://en.wikipedia.org/wiki/Slovene_grammar)      */
name|RelationalAdjective
argument_list|(
name|LexicalCategory
operator|.
name|Adjective
argument_list|)
operator|,
comment|/**      * A circumposition is an adposition with a part before the noun phrase and a part after. It is much less      * common than prepositions or postpositions.<br>      * (http://en.wikipedia.org/wiki/Circumposition 19.09.06)      */
name|Circumposition
argument_list|(
name|LexicalCategory
operator|.
name|Adposition
argument_list|)
operator|,
comment|/**      * A postposition is an adposition that occurs after its complement.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAPostposition.htm 19.09.06)      */
name|Postposition
argument_list|(
name|LexicalCategory
operator|.
name|Adposition
argument_list|)
operator|,
comment|/**      * A preposition is an adposition that occurs before its complement.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAPreposition.htm 19.09.06)      */
name|Preposition
argument_list|(
name|LexicalCategory
operator|.
name|Adposition
argument_list|)
operator|,
comment|/**      * Preposition that is a aggregation of words<br>      * (http://www.isocat.org/datcat/DC-1934)      */
name|CompoundPreposition
argument_list|(
name|Preposition
argument_list|)
operator|,
comment|/**      * Preposition that is the result of a morphological merge from at least two words.<br>      * (http://www.isocat.org/datcat/DC-1901)      */
name|FusedPreposition
argument_list|(
name|Preposition
argument_list|)
operator|,
comment|/**      * Preposition that is a pure simple word in contrast with the notion of fused preposition.<br>      * (http://www.isocat.org/datcat/DC-1900)      */
name|SimplePreposition
argument_list|(
name|Preposition
argument_list|)
operator|,
comment|/**      * An adjectival adverb is an adverb that is formally identical to an adjective.<br/>      * MULTEXT-East Adverb/Type="adjectival" (Serbian, Macedonian, Bulgarian)<br/>      * Bulgarian AdjectivalAdverbs have the same form as adjectives in Gender = neuter, Person = 3, Number =      * singular. (MTE v4, http://purl.org/olia/mte/multext-east.owl#AdjectivalAdverb)      */
name|AdjectivalAdverb
argument_list|(
name|LexicalCategory
operator|.
name|Adverb
argument_list|)
operator|,
comment|/**      * Adverb/Type="causal" is used in the Hungarian MTE v4, but no examples are provided.<br>      * (http://purl.org/olia/mte/multext-east.owl#CausalAdverb)      *       * @deprecated equivalent to Adverb and hasSemanticRole some CauseRole      */
name|CausalAdverb
argument_list|(
name|LexicalCategory
operator|.
name|Adverb
argument_list|)
operator|,
comment|/**      * Any adverb which modifies an adjective, an adverb, a verbal particle, a preposition, a conjunction or a      * determiner is a degree adverb.<br>      * (http://xlex.uni-muenster.de/Portal/MTPE/tagsetDescriptionEN.doc, p. 113, 8.1 Degree Adverbs 23.09.06)      *<p>      * Also known as specifier adverb<br>      * (http://www.unlweb.net/unlarium/dictionary/export_tagset.php)      */
name|DegreeAdverb
argument_list|(
name|LexicalCategory
operator|.
name|Adverb
argument_list|)
operator|,
comment|/**      * equivalent to Adverb and hasSemanticRole some LocationRole      */
name|LocationAdverb
argument_list|(
name|LexicalCategory
operator|.
name|Adverb
argument_list|)
operator|,
comment|/**      * equivalent to Adverb and hasSemanticRole some MannerRole      */
name|MannerAdverb
argument_list|(
name|LexicalCategory
operator|.
name|Adverb
argument_list|)
operator|,
comment|/**      * Adverb/Type="modifier" is used in the English, Romanian and Hungarian MTE v4 specs. For Romanian,      * Adverb/Type="modifier" applies to adverbs which can have predicative role, that is they can govern a      * subordinate sentence (ex. Fireşte că o ştiu -- Certainly I know it). Here (for uniformity within a      * multilingual environment), they are squeezed into the modifier class. (MTE v4) e.g., better (en)<br>      * (http://purl.org/olia/mte/multext-east.owl#ModifierAdverb)      */
name|ModifierAdverb
argument_list|(
name|LexicalCategory
operator|.
name|Adverb
argument_list|)
operator|,
comment|/**      * Adverb/Type="negative" are used in the Serbian and Romanian MTE v4 specs, e.g., for Romanian nicăieri -      * nowhere, niciodată - never. (MTE v4)<br>      * (http://purl.org/olia/mte/multext-east.owl#NegativeAdverb)      */
name|NegativeAdverb
argument_list|(
name|LexicalCategory
operator|.
name|Adverb
argument_list|)
operator|,
comment|/**      * Pronominal adverbs substitute for a preposition (which is incorporated into them) and an NP, cf.      * English therefore lit. "for this (reason, ...)", German deswegen lit. "because of this (reason, ...)".<br>      * (http://www.ilc.cnr.it/EAGLES96/elm_de/node235.html 21.09.06, examples Ch. Chiarcos)      */
name|PronominalAdverb
argument_list|(
name|LexicalCategory
operator|.
name|Adverb
argument_list|)
operator|,
comment|/**      * Pronominal adverb derived from a demonstrative stem (Ch. Chiarcos)      */
name|DemonstrativeAdverb
argument_list|(
name|PronominalAdverb
argument_list|)
operator|,
comment|/**      * Adverb/Type="verbal" applies to adverbs derived from from verbs (verbal adverbs) in the Serbian,      * Macedonian and Hungarian MTE v4 specs. Macedonian verbal adverbs (gerunds) like odejkji are thus not      * considered as verbal forms, but as Adverb/Type="verbal". (MTE v4)<br>      * (http://purl.org/olia/mte/multext-east.owl#VerbalAdverb)      */
name|VerbalAdverb
argument_list|(
name|LexicalCategory
operator|.
name|Adverb
argument_list|)
operator|,
comment|/**      * Adverb that serves to express interrogativity, exclamation or that serves to link a subordinate clause      * to the matrix clause. (Ch. Chiarcos)      */
name|WHTypeAdverbs
argument_list|(
name|LexicalCategory
operator|.
name|Adverb
argument_list|)
operator|,
comment|/**      * An ExclamatoryAdverb seves to express exclamation, cf. how in "How well everyone played!"      *<p>      * Exclamative sentences or exclamatives An exclamatory sentence or exclamation is generally a more      * emphatic form of statement, in particular, they are used are used to express strong feelings (Latin      * exclamare : "to call out, to cry out").<br>      * (http://english.unitecnology.ac.nz/resources/resources/exp_lang/sentence.html 07.05.07,      * http://en.wikipedia.org/wiki/Sentence_(linguistics) 07.05.07)      */
name|ExclamatoryAdverb
argument_list|(
name|WHTypeAdverbs
argument_list|)
operator|,
comment|/**      * Interrogative adverbs are used to introduce questions, e.g. "When are you coming?" (Angelika Adam)      */
name|InterrogativeAdverb
argument_list|(
name|WHTypeAdverbs
argument_list|)
operator|,
comment|/**      * The value relative is used for adverbs in clear relative cases as in: "The place 'where' I met you.",      * "The reason 'why' I did it."<br>      * (http://www.ilc.cnr.it/EAGLES96/pub/eagles/lexicons/elm_en.ps.gz, p.33, 07.05.07)      */
name|RelativeAdverb
argument_list|(
name|WHTypeAdverbs
argument_list|)
operator|,
comment|/**      * Multi-word conjunction      *<p>      * Besides the usual and, or, but, etc., certain prepositions and subordinating conjunctions can be used      * as coordinating conjunctions. Multi-word coordinating conjunctions are labeled CONJP (see section 7      * [Coordination]). ... CONJP — Conjunction Phrase. Used to mark certain “multi-word” conjunctions, such      * as as well as, instead of. (Bies et al. 1995)      */
name|ConjunctionPhrase
argument_list|(
name|LexicalCategory
operator|.
name|Conjuction
argument_list|)
operator|,
comment|/**      * Coordinating conjunctions, also called coordinators, are conjunctions that join two items of equal      * syntactic importance.<br>      * (http://en.wikipedia.org/wiki/Grammatical_conjunction 19.09.06)      */
name|CoordinatingConjunction
argument_list|(
name|LexicalCategory
operator|.
name|Conjuction
argument_list|)
operator|,
comment|/**      * When the same word is also placed before the first conjunct, as in French "ou...ou...", the former      * occurrence is given the Correlative value and the latter the Simple value.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node18.html#oav1av 17.11.06)      *<p>      * Conjunction/Coord_Type="correlat" (Romanian). In Romanian, there are three kinds of conjunctions      * depending on their usage: as such or together with other conjunctions or adverbs: (1) simple, between      * conjuncts: Ion ori Maria (John or Mary); (2) repetitive, before each conjunct: fie Ion fie Maria fie...      * (either John or Mary or...) (3) correlative, before a conjoined phrase, it requires specific      * coordinators between conjuncts: atât mama cât şi tata (both mother and father). (MTE v4,      * http://purl.org/olia/mte/multext-east.owl#CorrelativeCoordinatingConjunction)      */
name|CorrelativeCoordinatingConjunction
argument_list|(
name|CoordinatingConjunction
argument_list|)
operator|,
comment|/**      * When two distinct words occur, as in German "weder...noch...", then the first is given the Initial      * value.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node18.html#oav1av 17.11.06)      */
name|InitialCoordinatingConjunction
argument_list|(
name|CoordinatingConjunction
argument_list|)
operator|,
comment|/**      * When two distinct words occur, as in German weder...noch..., then the second is given the Non-initial      * value.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node18.html#oav1av 17.11.06)      */
name|NonInitialCoordinatingConjunction
argument_list|(
name|CoordinatingConjunction
argument_list|)
operator|,
comment|/**      * Conjunction/Coord_Type="repetit" (Romanian). In Romanian, there are three kinds of conjunctions      * depending on their usage: as such or together with other conjunctions or adverbs: (1) simple, between      * conjuncts: Ion ori Maria (John or Mary); (2) repetitive, before each conjunct: fie Ion fie Maria fie...      * (either John or Mary or...) (3) correlative, before a conjoined phrase, it requires specific      * coordinators between conjuncts: atât mama cât şi tata (both mother and father). (MTE v4,      * http://purl.org/olia/mte/multext-east.owl#RepetitiveCoordinatingConjunction)      */
name|RepetitiveCoordinatingConjunction
argument_list|(
name|CoordinatingConjunction
argument_list|)
operator|,
comment|/**      * Simple applies to the regular type of coordinator occurring between conjuncts: German und, for example.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node18.html#oav1av 17.11.06)      *<p>      * In the Romanian MTE v4 specs, Conjunction/Coord_Type="simple" is defined in contrast to repetitive and      * correlative coordinating conjunctions. In Romanian, there are three kinds of conjunctions depending on      * their usage: as such or together with other conjunctions or adverbs: (1) simple, between conjuncts: Ion      * ori Maria (John or Mary); (2) repetitive, before each conjunct: fie Ion fie Maria fie... (either John      * or Mary or...) (3) correlative, before a conjoined phrase, it requires specific coordinators between      * conjuncts: atât mama cât şi tata (both mother and father). (MTE v4), e.g., aşa_că, va_să_zică (ro)<br>      * (http://purl.org/olia/mte/multext-east.owl#SimpleCoordinatingConjunction)      */
name|SimpleCoordinatingConjunction
argument_list|(
name|CoordinatingConjunction
argument_list|)
operator|,
comment|/**      * Subordinating conjunctions, also called subordinators, are conjunctions that introduce a dependent      * clause.<br>      * (http://en.wikipedia.org/wiki/Grammatical_conjunction 19.09.06)      */
name|SubordinatingConjunction
argument_list|(
name|LexicalCategory
operator|.
name|Conjuction
argument_list|)
operator|,
comment|/**      *       For example, in German the subordinating conjunction "als" is followed by various kinds of comparative      * clause (including clauses without finite verbs).<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node19.html#oav2u 17.11.06)      */
name|SubordinatingConjunctionWithComparative
argument_list|(
name|SubordinatingConjunction
argument_list|)
operator|,
comment|/**      * For example, in German the subordinating conjunction "weil" introduces a clause with a finite verb.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node19.html#oav2u 17.11.06)      */
name|SubordinatingConjunctionWithFiniteClause
argument_list|(
name|SubordinatingConjunction
argument_list|)
operator|,
comment|/**      * For example, in German the subordinating conjunction "ohne" ("zu"...) is followed by an infinitive.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node19.html#oav2u 17.11.06)      */
name|SubordinatingConjunctionWithInfinite
argument_list|(
name|SubordinatingConjunction
argument_list|)
operator|,
comment|/**      * Conjunction/Sub_Type="negative" (Romanian, Serbian, Russian) In Romanian, each conjunction requires      * another mood, so that the diversity may be controlled by subcategorisation rules. The attribute      * Sub_Type distinguishes among the positive and negative conjunctions, providing means to control verbal      * double negation, (as in case of the negative pronouns, determiners and adverbs): nici NU am venit,      * nimeni NU vorbeşte, nici_un tren N-a trecut, nicăieri N-am văzut (MTE v4,      * http://purl.org/olia/mte/multext-east.owl#NegativeSubordinatingConjunction)      */
name|SubordinatingConjunctionWithNegation
argument_list|(
name|SubordinatingConjunction
argument_list|)
operator|,
comment|/**      * Conjunction/Sub_Type="negative" (Romanian, Serbian, Russian) In Romanian, each conjunction requires      * another mood, so that the diversity may be controlled by subcategorisation rules. The attribute      * Sub_Type distinguishes among the positive and negative conjunctions, providing means to control verbal      * double negation, (as in case of the negative pronouns, determiners and adverbs): nici NU am venit,      * nimeni NU vorbeşte, nici_un tren N-a trecut, nicăieri N-am văzut (MTE v4,      * http://purl.org/olia/mte/multext-east.owl#PositiveSubordinatingConjunction)      */
name|SubordinatingConjunctionWithoutNegation
argument_list|(
name|SubordinatingConjunction
argument_list|)
operator|,
comment|/**      * 0|Zero represents a zero complementizer (= subordinating conjunction); it may need to be deleted. The      * zero complementizer is generally the counterpart of the overt complementizer that. Example: Iâ ¹m      * sure 0 heâ ¹ll be here any minute. ...      *<p>      * 0 stands in for overt subordinating conjunctions like that in tensed subordinate clauses, including      * relative clauses. So the relative clause the man I saw should be bracketed as follows: (NP (NP the man)      * (SBAR 0 (S (NP I) (VP saw) (NP T)))))      *       * (Santorini 1991)      */
name|ZeroComplementizer
argument_list|(
name|SubordinatingConjunction
argument_list|)
operator|,
comment|/**      *       An interjection is a form, typically brief, such as one syllable or word, which is used most often as      * an exclamation or part of an exclamation. It typically expresses an emotional reaction, often with      * respect to an accompanying sentence and may include a combination of sounds not otherwise found in the      * language, e.g. in English: psst; ugh; well, well<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAnInterjection.htm 19.09.06)      */
name|Interjection
argument_list|(
name|LexicalCategory
operator|.
name|Conjuction
argument_list|)
operator|,
comment|/**      * A common noun is a noun that signifies a non-specific member of a group.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsACommonNoun.htm 19.09.06)      */
name|CommonNoun
argument_list|(
name|LexicalCategory
operator|.
name|Noun
argument_list|)
operator|,
comment|/**      * Dzongkha uses honorific forms: ན་བཟའ་/nam za/ (cloths) is the honorific form of the noun གོ་      * ལ་/gola/(cloths), གསངས་/sung/(tell) the honorific form of the verb སབ་/lab/(tell). We opted to mark      * them by adding the tag NNH (honorific common noun) and VBH (honorific verb) to enable future research      * on this specific usage of Dzongkha language. A number of tags were added to the set, of which we      * describe four in more detail: two of the additional tags are subclasses of verbs: VBH (honorific verb      * form), and VBN which describes past participle forms, like, e.g. བངམ་/jun/(created), the past particle      * form of བང་/jung/(create). (Chungku et al. 2010)      *<p>      * A noun, which indicates respect for the person being addressed, e.g., Miwang Gel-poi Yab “A king's      * father” [Though father=Apa, but colloquially we say YAB in Dzongkha]<br>      * (http://panl10n.net/english/Outputs%20Phase%202/CCs/Bhutan/Papers/2007/0701/PartOfSpeech.pdf)      */
name|HonorificCommonNoun
argument_list|(
name|CommonNoun
argument_list|)
operator|,
comment|/**      * A title designates the function or the social status of an individual. Often, it accompanies a proper      * noun, but it can also be used in place of a proper noun (if the bearer of the title is contextually      * unambiguous). E.g. "The/Det German/Adj Chancellor/Title Angela/Name Merkel/Name said ..." can be used      * besides "the German Chancellor said ...". Accordingly, some schemes (e.g., Chungku et al. 2010, for      * Dzongkha) group titles together with proper names<br>      * (http://purl.org/olia/dzongkha.owl#ParticularPersonNoun).      *<p>      * However, if multiple people hold the same title, they can be referred to as a group, e.g.,      * "Since WWII, the politics of the German chancellors always followed ...", and in this usage, titles are      * more comparable to common nouns. Functionally, titles are thus an intermediate category between      * CommonNoun and ProperNoun (cf. also Mulkern 1996).      *<p>      * Titles do, however, share important characteristics with common nouns. In English, for example, titles      * generally require a definite determiner (unlike proper nouns), even if unambiguous ("the pope"). They      * are thus classified here as a subtype of CommonNoun.      *<p>      * (Ann E. Mulkern. The name of the game. In Jeanette Gundel and Thorstein Fretheim, editors. Reference      * and Referent Accessibility: Pragmatics and Beyond. John Benjamins, Amsterdam and Philadelphia, 1996,      * pages 235–250.)      */
name|TitleNoun
argument_list|(
name|CommonNoun
argument_list|)
operator|,
comment|/**      * Measuring units are frequently used with numerals. However, they have a different syntactic structure      * than numerals (Sajjad 2007).      *<p>      * In European languages, Units are generally expressed as nouns, e.g., English      * "ten/Numeral kilogram/Unit". "Kilogram" can also be used as a common noun:      * "The kilogram is losing weight"<br>      * (http://www.bbc.co.uk/news/science-environment-12276822)      *<p>      * Hassan Sajjad (2007), Urdu Part of Speech Tagset, version 1.0.0.0, 07-12-2007, Center for research in      * Urdu Language Processing. National University of Computer and Emerging Sciences, Lahore, Pakistan,      * http://www.crulp.org/Downloads/langproc/UrduPOStagger/UrduPOStagset.pdf      */
name|UnitNoun
argument_list|(
name|CommonNoun
argument_list|)
operator|,
comment|/**      * NLOC Noun Location This is an entirely new tag introduced to cover an important phenomenon of Indian      * Languages. Words like 'Age', 'upara', 'pahele', 'bAda', etc. are used in various ways in Hindi.      *<ol>      *<li>They act as a postposition along with 'ke' e.g. ghade ke upara thAlI rakhI HE. ("pot" "on" "plate"      * "kept" "is") Here 'ke upara' is a post position which is the direct equivalent of the English      * preposition 'on'.      *<li>They also act as adverbs. e.g. tuma upara jAo. ("You" "up" "go") Here 'upara' is an adverbial of      * place.      *<li>These words also take post positions themselves and so in some sense behave like nouns. e.g. vaHa      * upara se AyA. ("He" "above" "from" "came")      *<li>As pointed out in 3. above, these words take postpositions and act as arguments of the verb in the      * sentence. And they also take a post position to join with a another noun. So in that sense also they      * behave like nouns. e.g. upara kA HissA ("above" "of" "portion")      *</ol>      * To tag such words one option is to tag them according to the category to which they belong in the given      * sentence. For example in 1. above, the word is occurring as a postposition so can be marked as a      * postposition. In example 2. above, it is an adverb so can be marked as an adverb and so on.      *<p>      * But we feel that these words are more like nouns as is evident from 3. and 4. above, and also if we      * consider for examples, 'aage', 'upara', etc. as places which are in front, up, etc then we can tag them      * as nouns.      *<p>      * But these are not pure nouns. They are nouns which indicate a location or time. These also function as      * adverbs or prepositions in a context. So a new tag NLOC is introduced for such words. This tag will      * cater to a finite set of such words. set: (Age, piche, upara, nIce, bAda, pahele) ("front", "behind",      * "above", "below", "before") Such words if tagged according to their syntactic function, it will hamper      * machine learning. So a single tag, NLOC has been devised for such words which indicate location and      * time.      *<p>      * e.g., (upara, Age, pahele, bAda) (IIIT (2007), A Part of Speech Tagger for Indian Languages (POS      * tagger), Tagset developed at IIIT - Hyderabad after consultations with several institutions through two      * workshops. available under http://shiva.iiit.ac.in/SPSAL2007/iiit_tagset_guidelines.pdf)      */
name|SpatiotemporalNoun
argument_list|(
name|CommonNoun
argument_list|)
operator|,
comment|/**      * relation noun (MIRACL& LSCA; http://www.isocat.org/datcat/DC-2226)      */
name|RelationNoun
argument_list|(
name|CommonNoun
argument_list|)
operator|,
comment|/**      *       A countable noun (also count noun) is a noun which can be modified by a numeral and occur in both      * singular and plural form, as well as co-occurring with quantificational determiners like every, each,      * several, most, etc..<br>      * (http://en.wikipedia.org/wiki/Countable_noun 19.09.06)      */
name|CountableNoun
argument_list|(
name|LexicalCategory
operator|.
name|Noun
argument_list|)
operator|,
comment|/**      * A mass noun (also uncountable noun or non-count noun) can't be modified by a numeral, occur in      * singular/plural or co-occur with the relevant kind of determiner.<br>      * (http://en.wikipedia.org/wiki/Mass_noun 19.09.06)      */
name|MassNoun
argument_list|(
name|LexicalCategory
operator|.
name|Noun
argument_list|)
operator|,
comment|/**      * Proper nouns (also called proper names) are the names of unique entities.<br>      * (http://en.wikipedia.org/wiki/Noun 19.09.06)      */
name|ProperNoun
argument_list|(
name|LexicalCategory
operator|.
name|Noun
argument_list|)
operator|,
comment|/**      * diminutive noun (MIRACL LSCA; http://www.isocat.org/datcat/DC-2225)      */
name|DiminutiveNoun
argument_list|(
name|LexicalCategory
operator|.
name|Noun
argument_list|)
operator|,
comment|/**      * A noun which quantifies one or more things, regardless of subject and an object.      *       *<pre>      *             ང་གིས་ བམོ་ ལ་ དང་ ཕད་ཅི།      *             NGAGI BUM 'NGA 'DA CHECI      *             I girl five with met      *             “I met with five girls.”      *</pre>      *       * (Jurmey Rabgay, email Sep 20, 2010)      */
name|NominalQuantifier
argument_list|(
name|LexicalCategory
operator|.
name|Noun
argument_list|,
name|LexicalCategory
operator|.
name|Quantifier
argument_list|)
operator|,
comment|/**      * noun of a voice<br>      * (http://www.isocat.org/datcat/DC-2253)      */
name|VoiceNoun
argument_list|(
name|LexicalCategory
operator|.
name|Noun
argument_list|)
operator|,
comment|/**      * A determiner is a noun modifier that expresses the reference of a noun or noun phrase in the context,      * including quantity, rather than attributes expressed by adjectives. This part of speech is defined in      * some languages, such as in English, as it is distinct from adjectives grammatically, though most      * English dictionaries still identify the determiners as adjectives.<br>      * (http://en.wikipedia.org/wiki/Determiner 19.09.06)      */
name|Determiner
argument_list|(
name|LexicalCategory
operator|.
name|PronounOrDeterminer
argument_list|)
operator|,
comment|/**      * An article is a member of a small class of determiners that identify a noun's definite or indefinite      * reference, and the new or given status.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAnArticle.htm 02.05.07)      */
name|Article
argument_list|(
name|Determiner
argument_list|)
operator|,
comment|/**      * A definite article is used before singular and plural nouns that refer to a particular member of a      * group.<br>      * (http://en.wikipedia.org/wiki/Article_%28grammar%29 18.09.06)      */
name|DefiniteArticle
argument_list|(
name|Article
argument_list|)
operator|,
comment|/**      * clitic definite determiner, e.g., in Macedonian, Bulgarian, and Romanian<br>      * (http://purl.org/olia/mte/multext-east.owl#CliticDeterminerType)      */
name|CliticDefiniteArticle
argument_list|(
name|DefiniteArticle
argument_list|)
operator|,
comment|/**      * For definiteness, when a specific form is the syntactic subject of the clause. (DFKI;      * http://www.isocat.org/datcat/DC-1928)      */
name|FullDefiniteArticle
argument_list|(
name|DefiniteArticle
argument_list|)
operator|,
comment|/**      * For definiteness, when a specific form is not the syntactic subject of the clause.<br>      * (http://www.isocat.org/datcat/DC-1927)      */
name|ShortDefiniteArticle
argument_list|(
name|DefiniteArticle
argument_list|)
operator|,
comment|/**      * The additional value Fused prep-art is for the benefit of those who do not find it practical to split      * fused words such as French au (= à + le) into two text words. This very common phenomenon of a fused      * preposition + article in West European languages should preferably, however, be handled by assigning      * two tags to the same orthographic word (one for the preposition and one for the article).<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node18.html#oav1ap 19.09.06)      */
name|FusedPrepArt
argument_list|(
name|Preposition
argument_list|,
name|Article
argument_list|)
operator|,
comment|/**      * An indefinite article is used before singular nouns that refer to any member of a group.<br>      * (http://en.wikipedia.org/wiki/Article_%28grammar%29 18.09.06)      */
name|IndefiniteArticle
argument_list|(
name|Article
argument_list|)
operator|,
comment|/**      * A partitive article indicates an indefinite quantity of a mass noun; there is no partitive article in      * English, though the words some or any often have that function. An example is French du / de la / des,      * as in Voulez-vous du café? ("Do you want some coffee?" or "Do you want coffee").<br>      * (http://en.wikipedia.org/wiki/Article_(grammar) 19.09.06)      */
name|PartitiveArticle
argument_list|(
name|Article
argument_list|)
operator|,
comment|/**      * In Romanian, the possessive article (also called genitival article) is an element in the structure of      * the possessive pronoun, of the ordinal numeral (e.g. al meu (mine) and al treilea (the third)), and of      * the indefinite genitive forms of the nouns (e.g. capitol al cărţii (chapter of the book)), e.g.,      * -al/al, a/al, ai/al, al, ale/al, alor/al<br>      * (http://purl.org/olia/mte/multext-east.owl#PossessiveArticle)      */
name|PossessiveArticle
argument_list|(
name|Article
argument_list|)
operator|,
comment|/**      * "By ʻspecificʼ and ʻnon-specificʼ I intend the difference between the two readings of English      * indefinites like (3):      *<p>      * (3) Iʼm looking for a deer.      *<p>      * In the specific reading there is a particular deer, say Bambi, that I am looking for. In the      * non-specific reading I will be happy to find any deer. Von Heusinger (2002) likes the test in English      * of inserting ʻcertainʼ after the ʻaʼ to fix the specific reading. In either reading of (3) a deer is      * being introduced as a new discourse referent. This is opposed to ʻdefiniteʼ which requires a previous      * pragmatic instantiation as in ʻIʼm looking for the deer.ʼ In English both the readings of (3) are      * indefinite. In Klallam, the specific demonstratives are neither definite nor indefinite."      *<p>      * (Montler, Timothy. 2007. Klallam demonstratives. Papers ICSNL XLVII. The 42nd International Conference      * on Salish and Neighbouring Language, pp. 409-425. University of British Columbia Working Papers in      * Linguistics, Volume 20; on specific vs. nonspecific determiners in Klallam, a Salish language,      * http://montler.net/papers/KlallamDemons.pdf)      */
name|NonspecificDeterminer
argument_list|(
name|Article
argument_list|)
operator|,
comment|/**      * "By ʻspecificʼ and ʻnon-specificʼ I intend the difference between the two readings of English      * indefinites like (3):      *<p>      * (3) Iʼm looking for a deer.      *<p>      * In the specific reading there is a particular deer, say Bambi, that I am looking for. In the      * non-specific reading I will be happy to find any deer. Von Heusinger (2002) likes the test in English      * of inserting ʻcertainʼ after the ʻaʼ to fix the specific reading. In either reading of (3) a deer is      * being introduced as a new discourse referent. This is opposed to ʻdefiniteʼ which requires a previous      * pragmatic instantiation as in ʻIʼm looking for the deer.ʼ In English both the readings of (3) are      * indefinite. In Klallam, the specific demonstratives are neither definite nor indefinite."      *<p>      * (Montler, Timothy. 2007. Klallam demonstratives. Papers ICSNL XLVII. The 42nd International Conference      * on Salish and Neighbouring Language, pp. 409-425. University of British Columbia Working Papers in      * Linguistics, Volume 20; on specific vs. nonspecific determiners in Klallam, a Salish language,      * http://montler.net/papers/KlallamDemons.pdf)      */
name|SpecificArticle
argument_list|(
name|Article
argument_list|)
operator|,
comment|/**      * Persian does have an article, but it marks specificity rather than definiteness. The Persian article is      * similar to the Balkan one (a clitic of pronominal origin that's written together with the word), except      * that it isn't exactly definite (you can even see it described as an indefinite article). (Ivan A.      * Derzhanski, p.c. 2010/06/18)      */
name|CliticSpecificArticle
argument_list|(
name|SpecificArticle
argument_list|)
operator|,
comment|/**      * Determiner/Type="emphatic" (Romanian)<br/>      *<p>      * In Romanian, there are specific forms for the so-called emphatic determiner, which may accompany both a      * noun and a personal pronoun: fata însăşi (the girl herself), also ea însăşi (she herself). e.g.,      * însele/însumi, însemi/însumi, însene/însumi, însevă/însumi, înseşi/însumi, înseţi/însumi, însumi,      * însuşi/însumi, însuţi/însumi<br>      * (http://purl.org/olia/mte/multext-east.owl#EmphaticDeterminer)      */
name|EmphaticDeterminer
argument_list|(
name|Determiner
argument_list|)
operator|,
comment|/**      * An indefinite determiner is a determiner that expresses a referent's indefinite number or amount, i.e.      * "some", "any", "many".<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAQuantifier.htm 22.09.06)      *<p>      * Note that here, a separate top-level class Quantifier has been introduced that covers expressions of      * number and amount as *semantic* concepts. Plural indefinite determiners are thus to be modeled as      * IndefiniteDeteriner and Quantifier.      */
name|IndefiniteDeterminer
argument_list|(
name|Determiner
argument_list|)
operator|,
comment|/**      * Determiner/Type="negative" (Romanian)<br/>      * In Romanian the negative determiner is expressed by the unit nici + indefinite article (e.g. nici un,      * nici o). (MTE v4)      *<p>      * e.g., nici-o/nici_un, nici_o/nici_un, nici_un, nici_unei/nici_un, nici_unii/nici_un, nici_unor/nici_un,      * nici_unui/nici_un<br>      * (http://purl.org/olia/mte/multext-east.owl#NegativeDeterminer)      */
name|NegativeDeterminer
argument_list|(
name|IndefiniteDeterminer
argument_list|)
operator|,
comment|/**      * A partitive determiner indicates an indefinite quantity of a mass noun; there is no partitive article      * in English, though the words some or any often have that function. (Wilson and Leech 1996)      */
name|PartitiveDeterminer
argument_list|(
name|Determiner
argument_list|)
operator|,
comment|/**      * Determiner/Type="exceptional" is applied to the Persian uniquitive determiner تنها i.e., "the only"      * (MTE v4; Hamidreza Kobdani, email 2010/06/15,      * http://purl.org/olia/mte/multext-east.owl#UniquitiveDeterminer)      */
name|UniquitiveDeterminer
argument_list|(
name|Determiner
argument_list|)
operator|,
comment|/**      * @deprecated to be replaced by InterrogativeDeterminer or RelativeDeterminer      */
name|WHDeterminer
argument_list|(
name|Determiner
argument_list|)
operator|,
comment|/**      * A exclamatory determiner is used in combination with a Nominal Phrase in order to create an exclamation      * (a more emphatic form of statement), e.g. "What a lovely colour!", "What a wonderful day this is!"<br>      * (http://www.ilc.cnr.it/EAGLES96/pub/eagles/lexicons/elm_en.ps.gz, p.27, 07.05.07;      * http://en.wikipedia.org/wiki/Sentence_(linguistics), 07.05.07)      */
name|ExclamatoryDeterminer
argument_list|(
name|WHDeterminer
argument_list|)
operator|,
comment|/**      * A interrogative is a function word used to introduce an interrogative clause. E.g. "which", "what",      * "whose" (interrogative possessive determiner) are interrogative determiner in English.<br>      * (http://en.wikipedia.org/wiki/Interrogative_word 02.05.07)      */
name|InterrogativeDeterminer
argument_list|(
name|WHDeterminer
argument_list|)
operator|,
comment|/**      * The relative determiner describes a attributive relative pronoun. In German "wessen" in      * "Ich weiss nicht, wessen Auto das ist." or the English "whose" in "The man whose daughter became ill.".      *<p>      * The relative determiner needs a noun to complete a NP (Nominal Phrase).<br>      * (http://www.ilc.cnr.it/EAGLES96/pub/eagles/lexicons/elm_en.ps.gz, p.28, 07.05.07)      */
name|RelativeDeterminer
argument_list|(
name|WHDeterminer
argument_list|)
operator|,
comment|/**      * Determiner that refers to the same entity.<br>      * (http://www.isocat.org/datcat/DC-1377)      */
name|ReflexiveDeterminer
argument_list|(
name|Determiner
argument_list|)
operator|,
comment|/**      * A pronoun is a pro-form which functions like a noun and substitutes for a noun or a noun-phrase. A      * language may have several classes of pronouns.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAPronoun.htm 19.09.06)      *<p>      * A pronominal is a phrase that functions as a pronoun      * (www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAPronominal.htm;      * http://www.isocat.org/datcat/DC-1369)      */
name|Pronoun
argument_list|(
name|LexicalCategory
operator|.
name|PronounOrDeterminer
argument_list|)
operator|,
comment|/**      * An attributive pronoun is a pronoun that modifies an NP.      */
name|AttributivePronoun
argument_list|(
name|Determiner
argument_list|,
name|Pronoun
argument_list|)
operator|,
comment|/**      * Demonstratives are deictic expressions (they depend on an external frame of reference) which indicate      * entities a speaker refers to, and distinguishes those entities from others. Demonstratives are usually      * employed for spatial deixis (using the context of the physical surroundings), but in many languages      * they double as discourse deictics, referring not to concrete objects but to words, phrases and      * propositions mentioned in speech.<br>      * (http://en.wikipedia.org/wiki/Demonstrative 19.09.06)      */
name|DemonstrativeDeterminer
argument_list|(
name|AttributivePronoun
argument_list|)
operator|,
comment|/**      * Demonstrative pronouns are deictic words (they depend on an external frame of reference). They indicate      * which entities a speaker refers to, and distinguishes those entities from others.<br>      * (http://en.wikipedia.org/wiki/Demonstrative_pronoun 19.09.06)      */
name|DemonstrativePronoun
argument_list|(
name|Pronoun
argument_list|)
operator|,
comment|/**      * A pronoun, which classifies or differentiates(pronoun) by a single basis, like everybody; each;      * individual etc.      *       *<pre>      *             འ ག་པ ་ ་ ར་ ག་ར་ ན་ ང་ ང་ཁ་ ས་ད །      *             Drupai Miser Gara Enrung Dzongkha ShegÔ      *             'Every Bhutanese must know Dzongkha'      *</pre>      *       *<br>      * (http://panl10n.net/english/Outputs%20Phase%202/CCs/Bhutan/Papers/2007/0701/PartOfSpeech.pdf)      */
name|DifferentialPronoun
argument_list|(
name|Pronoun
argument_list|)
operator|,
comment|/**      * When the subject is conjoined, the reflexive cannot refer to only one of them. The proform has to be a      * distributive pronoun, i.e., the reduplicated form, when it has coreference to respective subjects,      * e.g., kumaarum_i/Kumar.and umaavum_j/Uma.and tan_i+j/self-poss puunekki/cat.to paalu/milk      * kuDuttaanaanga/give-pst-aggr. "*Kumar_i and Uma gave milk to his_i/her_j cat." (Annamalai 2000, p. 189,      * on Tamil)      *<p>      * Unlike reciprocals, the two parts of a distributive pronoun cannot be considered as two full,      * independent NPs. In "awar/1 awar/2", only "awar/2" is case marked; "awar/1" is its citation form. Also,      * the two parts cannot be separated by intervening material (cf. English "one another"). (Jayaseelan      * 2000, p. 149, on Malayalam)      *<p>      * (K.A. Jayaseelan, 2000, Lexical anaphors and pronouns in Malayalam, In: Barbara C. Lust, Kashi Wali,      * James W. Gair, K.V.Subharao (eds.), Lexical Anaphors and Pronouns in Selected South Asian Languages. A      * Principled Typology, Mouton de Gruyter, Berlin, p. 113-168) (E. Annamalai, 2000, Lexical anaphors and      * pronouns in Tamil, , In: Barbara C. Lust, Kashi Wali, James W. Gair, K.V.Subharao (eds.), Lexical      * Anaphors and Pronouns in Selected South Asian Languages. A Principled Typology, Mouton de Gruyter,      * Berlin, p. 169-216)      */
name|DistributivePronoun
argument_list|(
name|Pronoun
argument_list|)
operator|,
comment|/**      * An indefinite pronoun is a pronoun that belongs to a class whose members indicate indefinite reference.      * Examples in English are "anybody", "one", "somebody".<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAnIndefinitePronoun.htm 19.09.06)      */
name|IndefinitePronoun
argument_list|(
name|Pronoun
argument_list|)
operator|,
comment|/**      * In the Russian MTE v4 specs, Pronoun/Type="nonspecific" marks the following Russian words: весь 'all',      * всякий 'any, every', сам 'oneself', самый 'the very', каждый 'every, each', иной 'other', любой 'any',      * другой 'other'. The name "nonspecific" follows Halliday (1985, Section 6.2.1.1). (MTE v4)      *<p>      * A nonspecific pronoun refers to an unidentified or general entity (e.g., "I saw *someone*",      * "I saw *everyone*"). A nonspecific pronoun is not, therefore, a personal pronoun, but an indefinite      * one. (Andrews 2003).      *<p>      * Andrews, Richard J. (2003), Introduction to Classical Nahuatl. University of Oklahoma Press. Halliday,      * M.A.K. (1985), An introduction to Functional Grammar, London: Edward Arnold<br>      * (http://purl.org/olia/mte/multext-east.owl#NonspecificPronoun)      */
name|NonspecificPronoun
argument_list|(
name|IndefinitePronoun
argument_list|)
operator|,
comment|/**      * Pronoun lacking person referent. (Gil Francopoulo; http://www.isocat.org/datcat/DC-1426)      *<p>      * More precisely, a form of pronoun that denotes the absence of a concrete or specific referent, e.g.,      * German "man".      *<p>      * As opposed to IndefinitePronoun, this referent is not just discourse-new, but generic or hypothetical.      */
name|ImpersonalPronoun
argument_list|(
name|IndefinitePronoun
argument_list|)
operator|,
comment|/**      * Pronoun used in a context of a negation or for expressing a negation.<br>      * (http://www.isocat.org/datcat/DC-1925)      */
name|NegativePronoun
argument_list|(
name|IndefinitePronoun
argument_list|)
operator|,
comment|/**      * A Locative pronoun is a pronoun, which locates the object of a noun or place of anything.      *       *<pre>      *             ་ ན་ གས་ ང་ ་ ག།      *             Nâ[LP] PhÜntsho'ling-lu ShÔ      *             'Come here at Phuntsholing'      *</pre>      *       *<br>      * (http://panl10n.net/english/Outputs%20Phase%202/CCs/Bhutan/Papers/2007/0701/PartOfSpeech.pdf)      */
name|LocativePronoun
argument_list|(
name|Pronoun
argument_list|)
operator|,
comment|/**      * In Eagles personal and reflexive pronouns are brought together as a single value Pers./Refl.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node17.html#recp 19.09.06)      */
name|PersReflPronoun
argument_list|(
name|Pronoun
argument_list|)
operator|,
comment|/**      * The Estonian determinal pronouns _ise_, _end(a)_ `(one)self'." combine aspects of emphatic pronouns and      * reflexive pronouns. It could also be described as an intensifier that is formally identical with the      * reflexive pronoun or as an emphatic reflexive pronoun. (Ivan A. Derzhanski, Heiki-Jaan Kaalep,      * http://purl.org/olia/mte/multext-east.owl#DeterminalPronoun;      *<p>      * Insa Gülzow (2006), The acquisition of intensifiers: Emphatic reflexives in English and German child      * language, Mouton de Gruyter, Berlin, p. 258)      */
name|DeterminalPronoun
argument_list|(
name|PersReflPronoun
argument_list|)
operator|,
comment|/**      * A FirstPersonPronoun refers to the speaker, or to both the speaker and referents grouped with the      * speaker.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsFirstPersonDeixis.htm 19.09.06)      */
name|FirstPersonPronoun
argument_list|(
name|PersReflPronoun
argument_list|)
operator|,
comment|/**      * A personal pronoun is a pronoun that expresses a distinction of person deixis.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAPersonalPronoun.htm 19.09.06)      */
name|PersonalPronoun
argument_list|(
name|PersReflPronoun
argument_list|)
operator|,
comment|/**      * *|An asterisk represents a zero pronoun; it may need to be deleted. ... is used to represent the empty      * subject of gerunds, imperatives and to-infinitive clauses. (Santorini 1991)      *<p>      * (NP *) â ´ arbitrary PRO, controlled PRO, and trace of A-movement (Bies et al. 1995)      */
name|ZeroPronoun
argument_list|(
name|PersonalPronoun
argument_list|)
operator|,
comment|/**      * Personnal pronoun that is affixed. (MIRACL& LSCA; http://www.isocat.org/datcat/DC-2221)      */
name|AffixedPersonalPronoun
argument_list|(
name|PersonalPronoun
argument_list|)
operator|,
comment|/**      * Personal pronoun that can occupy the position after a preposition and/or reinforce a weak personal      * pronoun. (Eagles; http://www.isocat.org/datcat/DC-1390)      */
name|StrongPersonalPronoun
argument_list|(
name|PersonalPronoun
argument_list|)
operator|,
comment|/**      * Personal pronoun that cannot occupy the position after a preposition and/or reinforce a strong personal      * pronoun.<br>      * (http://www.isocat.org/datcat/DC-1414)      */
name|WeakPersonalPronoun
argument_list|(
name|PersonalPronoun
argument_list|)
operator|,
comment|/**      * A reciprocal pronoun is a pronoun that expresses a mutual feeling or action among the referents of a      * plural subject.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAReciprocalPronoun.htm 19.09.06)      */
name|ReciprocalPronoun
argument_list|(
name|PersReflPronoun
argument_list|)
operator|,
comment|/**      * A reflexive pronoun is a pronoun that has coreference with the subject.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAReflexivePronoun.htm 19.09.06)      */
name|ReflexivePronoun
argument_list|(
name|PersReflPronoun
argument_list|)
operator|,
comment|/**      * Second person deixis means deictic reference to a person or persons identified as addressee.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsSecondPersonDeixis.htm 19.09.06)      */
name|SecondPersonPronoun
argument_list|(
name|PersReflPronoun
argument_list|)
operator|,
comment|/**      * In several European languages exist special forms of pronouns for polite or respectful reference, e.g.      * Dutch u and Spanish usted. The concept FamiliarSecondPersonPronoun applies to the corresponding      * unmarked forms for informal conversiation in such languages.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node18.html#oav1p 19.09.06)      */
name|FamiliarSecondPersonPronoun
argument_list|(
name|SecondPersonPronoun
argument_list|)
operator|,
comment|/**      * In several European languages exist special forms of pronouns for polite or respectful reference, e.g.      * Dutch u and Spanish usted.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node18.html#oav1p 19.09.06)      */
name|PoliteSecondPersonPronoun
argument_list|(
name|SecondPersonPronoun
argument_list|)
operator|,
comment|/**      * Third person reference is a deictic reference to a referent(s) not identified as the speaker or      * addressee.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsThirdPersonDeixis.htm 19.09.06)      */
name|ThirdPersonPronoun
argument_list|(
name|PersReflPronoun
argument_list|)
operator|,
comment|/**      * A possessive pronoun is a pronoun that expresses relationships like ownership, such as kinship, and      * other forms of association.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAPossessivePronoun.htm 19.09.06)      */
name|PossessivePronoun
argument_list|(
name|Pronoun
argument_list|)
operator|,
comment|/**      * A possessive determiner is a part of speech that modifies a noun by attributing ownership to someone or      * something.<br>      * (http://en.wikipedia.org/wiki/Possessive_adjective 19.09.06)      */
name|PossessiveDeterminer
argument_list|(
name|AttributivePronoun
argument_list|,
name|PossessivePronoun
argument_list|)
operator|,
comment|/**      * Attributive possessive pronoun form of the reflexive pronoun, e.g., Russian свой:      *<p>      * Обама на свой день рождения угощал гостей стейками и хот-догами. Obama on his day of.birth entertained      * guests with.steaks and hot.dogs      * "On his birthday, Obama entertained his guests with steaks and hot dogs."<br>      * (http://ua.rian.ru/world_news/20110805/78815136.html)      *<p>      * The antecedent of a possessive reflexive is not determined by its gender, but by its syntactic      * prominence.      */
name|ReflexivePossessiveDeterminer
argument_list|(
name|ReflexiveDeterminer
argument_list|,
name|PossessiveDeterminer
argument_list|)
operator|,
comment|/**      * non-attributive pronoun      */
name|SubstitutivePronoun
argument_list|(
name|Pronoun
argument_list|)
operator|,
comment|/**      * @deprecated to be replaced with InterrogativePronoun or #RelativePronoun      */
enum|@
name|Deprecated
name|WHPronoun
argument_list|(
name|Pronoun
argument_list|)
operator|,
comment|/**      * An exclamative pronoun is a word which marks an exclamation.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAnExclamative.htm 19.09.06)      */
name|ExclamatoryPronoun
argument_list|(
name|WHPronoun
argument_list|)
operator|,
comment|/**      * A interrogative pronoun is a pro-form that is used in questions in place of the item questioned for.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAnInterrogativeProForm.htm 19.09.06)      */
name|InterrogativePronoun
argument_list|(
name|WHPronoun
argument_list|)
operator|,
comment|/**      * A relative pronoun is a pronoun that marks a relative clause, functions grammatically within the      * relative clause, and is coreferential to the word modified by the relative clause.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsARelativePronoun.htm 19.09.06)      */
name|RelativePronoun
argument_list|(
name|WHPronoun
argument_list|)
operator|,
comment|/**      * pronoun that have reference to something characterized by allusions. (MIRACL& LSCA;      * http://www.isocat.org/datcat/DC-2223)      *<p>      * an invariable pronoun expressing a specific intention by means of unclear term (Khemakhem Aida,      * 2010-05-10 via isocat-morpho@loria.fr)      *<p>      * examples from Arabic (Monica Monachini 2010-05-06 via isocat-morpho@loria.fr): "kam nahaituhu" (how      * often I forbade him, Hans Wehr), "baas Saar `amra `ashr isniin, gam (= kam) yriid paysikil" (He just      * turned ten, and here [how] he wants a bicycle, Georgetown University Iraqi Arabic-English Dictionary),      * "gam (= kam) yurguS imnil-faraH" ([how] he jumped for joy, Georgetown University Iraqi Arabic-English      * Dictionary)      */
name|AllusivePronoun
argument_list|(
name|Pronoun
argument_list|)
operator|,
comment|/**      * conditional pronoun (MIRACL& LSCA; http://www.isocat.org/datcat/DC-2222)      */
name|ConditionalPronoun
argument_list|(
name|Pronoun
argument_list|)
operator|,
comment|/**      * Pronoun marked to show its importance.<br>      * (http://www.isocat.org/datcat/DC-1941)      */
name|EmphaticPronoun
argument_list|(
name|Pronoun
argument_list|)
operator|,
comment|/**      * Punctuation that is more important than a secondary punctuation with regards to sentence splitting in a      * text.<br>      * (http://www.isocat.org/datcat/DC-2075)      */
name|MainPunctuation
argument_list|(
name|LexicalCategory
operator|.
name|Punctuation
argument_list|)
operator|,
comment|/**      * SentenceFinalPunctuation are . ? !.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node17.html#recv 19.09.06)      */
name|SentenceFinalPunctuation
argument_list|(
name|MainPunctuation
argument_list|)
operator|,
comment|/**      * Sign used to express a question.<br>      * (http://www.isocat.org/datcat/DC-1444)      */
name|QuestionMark
argument_list|(
name|SentenceFinalPunctuation
argument_list|)
operator|,
comment|/**      * Sign (.) used to expresses the end of a sentence or an abbreviation.<br>      * (http://www.isocat.org/datcat/DC-1445)      */
name|Point
argument_list|(
name|SentenceFinalPunctuation
argument_list|)
operator|,
comment|/**      * Special sign (!) usually used in writing to mark exclamation.<br>      * (http://www.isocat.org/datcat/DC-1441)      */
name|ExclamativePoint
argument_list|(
name|MainPunctuation
argument_list|)
operator|,
comment|/**      * Punctuation used when the sentence is interrogative.<br>      * (http://www.isocat.org/datcat/DC-2087)      */
name|InterrogativePunctuation
argument_list|(
name|MainPunctuation
argument_list|)
operator|,
comment|/**      * Punctuation that is not very important with regards to sentence splitting in a text.<br>      * (http://www.isocat.org/datcat/DC-2076)      */
name|SecondaryPunctuation
argument_list|(
name|LexicalCategory
operator|.
name|Punctuation
argument_list|)
operator|,
comment|/**      * Parenthetical elements are dominated by a node labeled PRN. Punctuation marks that set off a      * parenthetical (i.e., commas, dashes, parentheses (-LRB- and -RRB-)) are contained within the PRN node.      * Use of PRN is determined ultimately by individual annotator intuition, though the presence of dashes or      * parentheses strongly suggests a parenthetical. (Bies et al. 1995)      */
name|ParentheticalPunctuation
argument_list|(
name|SecondaryPunctuation
argument_list|)
operator|,
comment|/**      * Beginning of a paired punctuation.<br>      * (http://www.isocat.org/datcat/DC-2078)      */
name|LeftParentheticalPunctuation
argument_list|(
name|ParentheticalPunctuation
argument_list|)
operator|,
comment|/**      *&lt; LAB* Left angle bracket (Santorini 1991)      */
name|OpenAngleBracket
argument_list|(
name|LeftParentheticalPunctuation
argument_list|)
operator|,
comment|/**      * [ LSB* Left square bracket (Santorini 1991)      */
name|OpenSquareBracket
argument_list|(
name|LeftParentheticalPunctuation
argument_list|)
operator|,
comment|/**      * Inverted comma.<br>      * (http://www.isocat.org/datcat/DC-1443)      */
name|InvertedComma
argument_list|(
name|LeftParentheticalPunctuation
argument_list|)
operator|,
comment|/**      * Punctuation used in certain languages at the beginning of an interrogative sentence.<br>      * (http://www.isocat.org/datcat/DC-2088)      */
name|InvertedQuestionMark
argument_list|(
name|LeftParentheticalPunctuation
argument_list|)
operator|,
comment|/**      * Punctuation that is represented graphically as [<br>      * (http://www.isocat.org/datcat/DC-2082)      */
name|OpenBracket
argument_list|(
name|LeftParentheticalPunctuation
argument_list|)
operator|,
comment|/**      * Punctuation that is graphically represented as {<br>      * (http://www.isocat.org/datcat/DC-2084)      */
name|OpenCurlyBracket
argument_list|(
name|LeftParentheticalPunctuation
argument_list|)
operator|,
comment|/**      * Beginning of a pair of parenthesis.<br>      * (http://www.isocat.org/datcat/DC-1442)      */
name|OpenParenthesis
argument_list|(
name|LeftParentheticalPunctuation
argument_list|)
operator|,
comment|/**      * End of a paired punctuation.<br>      * (http://www.isocat.org/datcat/DC-2079)      *       * RightParentheticalPunctuation is a punctuation mark which concludes a constituent whose the opening is      * marked by a LeftParentheticalPunctuation, e.g. ), ] and Spanish ?.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node17.html#recv 19.09.06)      */
name|RightParentheticalPunctuation
argument_list|(
name|ParentheticalPunctuation
argument_list|)
operator|,
comment|/**      *&gt; RAB* Right angle bracket (Santorini 1991)      */
name|CloseAngleBracket
argument_list|(
name|RightParentheticalPunctuation
argument_list|)
operator|,
comment|/**      * ] RSB* Right square bracket (Santorini 1991)      */
name|CloseSquareBracket
argument_list|(
name|RightParentheticalPunctuation
argument_list|)
operator|,
comment|/**      * Punctuation that is graphically represented by ]<br>      * (http://www.isocat.org/datcat/DC-2083)      */
name|CloseBracket
argument_list|(
name|RightParentheticalPunctuation
argument_list|)
operator|,
comment|/**      * Punctuation that is graphically represented by }<br>      * (http://www.isocat.org/datcat/DC-2085)      */
name|CloseCurlyBracket
argument_list|(
name|RightParentheticalPunctuation
argument_list|)
operator|,
comment|/**      * End of a parenthesis pair.<br>      * (http://www.isocat.org/datcat/DC-1440)      */
name|CloseParenthesis
argument_list|(
name|RightParentheticalPunctuation
argument_list|)
operator|,
comment|/**      * SentenceMedialPunctuation are , ; : - .<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node17.html#recv 19.09.06)      */
name|SentenceMedialPunctuation
argument_list|(
name|SecondaryPunctuation
argument_list|)
operator|,
comment|/**      * Sign with two vertical points that is used in writing and printing to introduce an explanation, example      * or quotation. (Gil Francopoulo; http://www.isocat.org/datcat/DC-1439)      */
name|Colon
argument_list|(
name|SentenceMedialPunctuation
argument_list|)
operator|,
comment|/**      * Mark (,) used in writing to show a short pause or to separate items in a list. (Longman DCE 2005;      * http://www.isocat.org/datcat/DC-1448)      */
name|Comma
argument_list|(
name|SentenceMedialPunctuation
argument_list|)
operator|,
comment|/**      * Punctuation that is graphically presented as "-".<br>      * (http://www.isocat.org/datcat/DC-2077)      */
name|Hyphen
argument_list|(
name|SentenceMedialPunctuation
argument_list|)
operator|,
comment|/**      * Sign (;) usually used to separate phrases.<br>      * (http://www.isocat.org/datcat/DC-1446)      */
name|SemiColon
argument_list|(
name|SentenceMedialPunctuation
argument_list|)
operator|,
comment|/**      * Sequence of three dots having the same meaning as "et cetera" (full form) or "etc" (abbreviated form).<br>      * (http://www.isocat.org/datcat/DC-1447)      */
name|SuspensionPoints
argument_list|(
name|SentenceMedialPunctuation
argument_list|)
operator|,
comment|/**      * Punctuation usually used to surround a quotation.<br>      * (http://www.isocat.org/datcat/DC-2081)      */
name|Quote
argument_list|(
name|SecondaryPunctuation
argument_list|)
operator|,
comment|/**      * quotation mark, closing      */
name|CloseQuote
argument_list|(
name|Quote
argument_list|)
operator|,
comment|/**      * quotation mark, opening      */
name|OpenQuote
argument_list|(
name|Quote
argument_list|)
operator|,
comment|/**      * The punctuation sign /<br>      * (http://www.isocat.org/datcat/DC-1437)      */
name|Slash
argument_list|(
name|SecondaryPunctuation
argument_list|)
operator|,
comment|/**      * Quantifiers that enforce dual agreement (i.e., as with the numeral "2").      *<p>      * Some feminine and neuter body parts in Czech have preserved dual forms, and if the noun is dual, so are      * its attributes (adjectives, pronouns). So the agreement of the numeral 2 differs formally from 3-4      * (Ivan A. Derzhanski, email 2010/06/16, http://purl.org/olia/mte/multext-east.owl#DualQuantifier)      *<p>      * Numeral/Class="definite", Numeral/Class="definite1", Numeral/Class="definite234" etc. refer to specific      * patterns of congruency with Slavic numerals that originate from the difference between Old Slavic      * singular (definite1), dual (definite2, definite234) and plural (definite).<br>      * (http://purl.org/olia/mte/multext-east.owl#DualQuantifier)      */
name|DualQuantifier
argument_list|(
name|LexicalCategory
operator|.
name|Quantifier
argument_list|)
operator|,
comment|/**      * A numeral is a word, functioning most typically as an adjective or pronoun, that expresses a number,      * and relation to the number, such as one of the following: Quantity, Sequence, Frequency, Fraction.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsANumeral.htm 19.09.06)      */
name|Numeral
argument_list|(
name|LexicalCategory
operator|.
name|Quantifier
argument_list|)
operator|,
comment|/**      * Bulgarian has Numeral/Form=approx(a), used for approximate numerals (десетина /about a ten/, стотина      * /about a hundred/) (Dimitrova et al. 2009,      * http://purl.org/olia/mte/multext-east.owl#ApproximateNumeral)      */
name|ApproximateNumeral
argument_list|(
name|Numeral
argument_list|)
operator|,
comment|/**      * A cardinal numeral is a numeral of the class whose members are considered basic in form, used in      * counting, and used in expressing how many objects are referred to.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsACardinalNumeral.htm 19.09.06)      */
name|CardinalNumber
argument_list|(
name|Numeral
argument_list|)
operator|,
comment|/**      * Numeral/Type="collect" (Romanian)<br/>      * In traditional Romanian grammars, expressions like amândoi "both", toţi trei "all three" are referred      * to as collective numerals. (MTE v4, http://purl.org/olia/mte/multext-east.owl#CollectiveNumeral)      *<p>      * e.g., ambelor/ambii, ambilor/ambii, amânduror/amândoi, amândurora/amândoi, câteşipatru, tuspatru (ro,      * http://purl.org/olia/mte/multext-east.owl#CollectiveNumeral)      *<p>      * e.g., czworga/czworo, czworgiem/czworo, czworgu/czworo, czworo/czworo, dwoje/dwoje, dwojga/dwoje,      * dwojgiem/dwoje, dwojgu/dwoje, jedenaścioro/jedenaścioro (pl,      * http://purl.org/olia/mte/multext-east.owl#CollectiveNumeral)      *<p>      * e.g., dvadesetora/dvadesetoro, dvoja/dvoje, dvoje, dvoji/dvoje, dvojih/dvoje, dvojim/dvoje, oboje,      * tridesetora/tridesetoro, troja/troje (sr, http://purl.org/olia/mte/multext-east.owl#CollectiveNumeral)      *<p>      * e.g., обата, обајцата, обете, шеесетминава/шеесетмина, шеесетминана/шеесетмина,      * шеесетмината/шеесетмина, шеснаесетминава/шеснаесетмина, шеснаесетминана/шеснаесетмина,      * шеснаесетмината/шеснаесетмина (mk, http://purl.org/olia/mte/multext-east.owl#CollectiveNumeral)      */
name|CollectiveNumeral
argument_list|(
name|Numeral
argument_list|)
operator|,
comment|/**      * Nominal numbers are used to identify or refer the things. It does not show the quantity or rank.      *<p>      * Example:      *       *<pre>      *             ངེ་གི་ འགལ་འཕིན་ ཨང་གངས་ འདི་ ༡༧༦༤༩༠༣༧ ཨིན།      *             NGIGI DRUELTHRIN ANGDRANG 'DI 17649037 INN      *             my mobile number is 17649037 be      *             “ My mobile number is 17649037.”      *</pre>      *       * (Jurmey Rabgay, email Sep 20, 2010, http://purl.org/olia/dzongkha.owl#NominalNumber)      */
name|NominalNumber
argument_list|(
name|Numeral
argument_list|)
operator|,
comment|/**      * An ordinal number is a number belonging to a class whose members designate positions in a sequence,      * e.g. in English "First", "Second", "Third".<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAOrdinalNumeral.htm 19.09.06)      */
name|OrdinalNumber
argument_list|(
name|Numeral
argument_list|)
operator|,
comment|/**      * Numeral/Form="fractional" (Romanian)<br/>      * In traditional Romanian grammars, FractionalNumeral refers to expressions like treime-one third. (MTE      * v4, http://purl.org/olia/mte/multext-east.owl#FractalNumeral)      */
name|Fraction
argument_list|(
name|Numeral
argument_list|)
operator|,
comment|/**      * Quantifiers that enforce paucal agreement. In many Slavic languages, numerals between 2 and 4 (and some      * quantifiers) involve a specific agreement patterns that is different from that of smaller and greater      * numbers. In Russian, for example, genitive singular is requires. These numerals and quantifiers with      * the same characteristics are referred to here as "paucal quantifiers". (cf. David Pesetsky,      * http://www.uni-leipzig.de/~jtrommer/Harvard/pesetsky.pdf)      */
name|PaucalQuantifier
argument_list|(
name|LexicalCategory
operator|.
name|Quantifier
argument_list|)
operator|,
comment|/**      * A PluralQuantifier is a Quantifier (or Numeral) that specifies a large multitude of entities. The      * agreement pattern of a plural quantifier is different from that or an singular quantifier, but as      * opposed to DualQuantifier and PaucalQuantifier, PluralQuantifier includes quantifiers that denote      * arbitrarily large sets of entities. (Chiarcos) The corresponding category in Czech, Polish and Slovak      * MTE v4 specs is Numeral/Class="definite", that refers to numerals larger than four. (MTE v4)      */
name|PluralQuantifier
argument_list|(
name|LexicalCategory
operator|.
name|Quantifier
argument_list|)
operator|,
comment|/**      * A ProQuantifier is a quantifier derived from a pronominal element. ProQuantifiers thus partly      * characterized as pronouns (e.g., as pronominal adverbs) or quantifiers (e.g., "indefinite numeral" as      * in MTE v.4).<br>      * (http://purl.org/olia/mte/multext-east.owl#ProQuantifier)      */
name|ProQuantifier
argument_list|(
name|LexicalCategory
operator|.
name|Quantifier
argument_list|,
name|Pronoun
argument_list|)
operator|,
comment|/**      * In the Czech and Slovak MTE v4 specs, Numeral/Class="demonstrative" are items meaning `this many/much',      * etc. Strictly speaking, they are pronumerals (pro-quantifiers), but traditional descriptions don't      * recognise such a category, so they are described variously as pronouns (because they contain a      * demonstrative element) or as numerals (because their syntactic distribution is that of numerals, or      * very close)." (Ivan A Derzhanski, email 2010/06/11,      * http://purl.org/olia/mte/multext-east.owl#DemonstrativeQuantifier)      */
name|DemonstrativeQuantifier
argument_list|(
name|ProQuantifier
argument_list|)
operator|,
comment|/**      * In the Czech and Slovak MTE v4 specs, Numeral/Class="indefinite" are items meaning `several/some', etc.      * Strictly speaking, they are pronumerals (pro-quantifiers), but traditional descriptions don't recognise      * such a category, so they are described variously as pronouns or as numerals (because their syntactic      * distribution is that of numerals, or very close)." (Ivan A Derzhanski, email 2010/06/11,      * http://purl.org/olia/mte/multext-east.owl#IndefiniteQuantifier)      */
name|IndefiniteQuantifier
argument_list|(
name|ProQuantifier
argument_list|)
operator|,
comment|/**      * In the Czech and Slovak MTE v4 pecs, Numeral/Class="interrogative" are items meaning `how many/much',      * etc. Strictly speaking, they are pronumerals (pro-quantifiers), but traditional descriptions don't      * recognise such a category, so they are described variously as pronouns or as numerals (because their      * syntactic distribution is that of numerals, or very close)." (Ivan A Derzhanski, email 2010/06/11,      * http://purl.org/olia/mte/multext-east.owl#InterrogativeQuantifier)      */
name|InterrogativeQuantifier
argument_list|(
name|ProQuantifier
argument_list|)
operator|,
comment|/**      * In the Czech MTE v4 specs, Numeral/Class="relative" are items meaning `how many/much', `as many/much'      * etc. Strictly speaking, they are pronumerals (pro-quantifiers), but traditional descriptions don't      * recognise such a category, so they are described variously as pronouns or as numerals (because their      * syntactic distribution is that of numerals, or very close)." (Ivan A Derzhanski, email 2010/06/11,      * http://purl.org/olia/mte/multext-east.owl#RelativeQuantifier)      */
name|RelativeQuantifier
argument_list|(
name|ProQuantifier
argument_list|)
operator|,
comment|/**      * A singular quantifier is a quantifier or a numeral that specifies a single referent from a set.      * (Chiarcos) In Czech and Slovak MTE v4 specs, the corresponding category Numeral/Class="definite1" is      * applied to the numeral "one". (MTE v4)      */
name|SingularQuantifier
argument_list|(
name|LexicalCategory
operator|.
name|Quantifier
argument_list|)
operator|,
comment|/**      * A Multiple Numeral serves to define a complex whole, with respect to the number of its parts, e.g.,      * English "twofold", "twice" or "manyfold". Used in morphosyntactic descriptions of, e.g., Romanian,      * Slovak and Czech. (Joseph Ghostwick [1878], English language -- Grammar, Historical, London, Longmans,      * Green, and Co.; http://purl.org/olia/mte/multext-east.owl#MultipleNumeral)      */
name|MultiplicativeNumeral
argument_list|(
name|LexicalCategory
operator|.
name|Quantifier
argument_list|)
operator|,
comment|/**      * Abbreviation (from Latin brevis "short") is strictly speaking a shorter form of a word, but more      * particularly, an abbreviation is a letter or group of letters, taken from a word or words, and employed      * to represent them for the sake of brevity. For example, the word "abbreviation" can be abbreviated as      * "abbr." or "abbrev."<br>      * (http://en.wikipedia.org/wiki/Abbreviation 19.09.06)      */
name|Abbreviation
argument_list|(
name|LexicalCategory
operator|.
name|Residual
argument_list|)
operator|,
comment|/**      * Abbreviation/Syntactic_Type="pronominal" (Romanian), e.g., d-ta/dumneata, d-tale/dumitale,      * d-voastră/dumneavoastră, dv./dumneavoastră, dvs./dumneavoastră<br>      * (http://purl.org/olia/mte/multext-east.owl#Pronominal)      */
name|AbbreviatedPronoun
argument_list|(
name|Pronoun
argument_list|,
name|Abbreviation
argument_list|)
operator|,
comment|/**      * An acronym is an abbreviation, such as NATO, laser, and ABC, written as the initial letter or letters      * of words, and pronounced on the basis of this abbreviated written form. Acronyms are used most often to      * abbreviate names of organizations and long or frequently referenced terms.<br>      * (http://en.wikipedia.org/wiki/Acronym 19.09.06)      */
name|Acronym
argument_list|(
name|LexicalCategory
operator|.
name|Residual
argument_list|)
operator|,
comment|/**      * Date is a stretch of text that specifies a specific point in time and that is not further      * linguistically analysed. (Chiarcos)      */
name|Date
argument_list|(
name|LexicalCategory
operator|.
name|Residual
argument_list|)
operator|,
comment|/**      * A foreign word is a text word which lies outside the traditionally accepted range of grammatical      * classes, it occurs quite commonly in many texts and very commonly in some.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node16.html#mr 19.09.06)      */
name|Foreign
argument_list|(
name|LexicalCategory
operator|.
name|Residual
argument_list|)
operator|,
comment|/**      * A formula (mathematical formulae) is a text word which lies outside the traditionally accepted range of      * grammatical classes, it occurs quite commonly in many texts and very commonly in some.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node16.html#mr 19.09.06)      */
name|Formula
argument_list|(
name|LexicalCategory
operator|.
name|Residual
argument_list|)
operator|,
comment|/**      * Introduced to account for Bullet http://www.isocat.org/datcat/DC-1438      */
name|LayoutElement
argument_list|(
name|LexicalCategory
operator|.
name|Residual
argument_list|)
operator|,
comment|/**      * LST — List marker. (Bies et al. 1995)      */
name|ListMarker
argument_list|(
name|LayoutElement
argument_list|)
operator|,
comment|/**      *       Sign used to mark an item in a list.<br>      * (http://www.isocat.org/datcat/DC-1438)      */
name|Bullet
argument_list|(
name|LayoutElement
argument_list|)
operator|,
comment|/**      *       graphical representation<br>      * (http://www.isocat.org/datcat/DC-2249)      */
name|Image
argument_list|(
name|LayoutElement
argument_list|)
operator|,
comment|/**      * In morphosyntactic annotation schemes, a symbol is a single graphical sign that occurs in a written      * text with a conventionalized meaning but that does not represent a phoneme (like ordinary characters),      * an orthogaphic sign (punctuation), or a number. (Christian Chiarcos)      *<p>      * Symbols such as alphabetic characters can vary for singular and plural (e.g. How many Ps are there in      * `psychopath'?), and are in this respect like common nouns. In some languages (e.g. Portuguese) such      * symbols also have gender.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node17.html#recr)      */
name|Symbol
argument_list|(
name|LexicalCategory
operator|.
name|Residual
argument_list|)
operator|,
comment|/**      * a mis-typed word      */
name|Typo
argument_list|(
name|LexicalCategory
operator|.
name|Residual
argument_list|)
operator|,
comment|/**      * adopted from Dzongkha tagset (Chungku et al. 2010). If its tradition of grammar description is      * influenced by the Indian, these case markers are variously described as case morphemes or as      * postpositions. Therefore introduced as a shorthand for Adposition or MorphologicalParticle      */
name|CaseMarker
argument_list|(
name|LexicalCategory
operator|.
name|Unique
argument_list|)
operator|,
comment|/**      * A classifier is a word or affix that expresses the classification of a noun.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAClassifier.htm 19.09.06)      *<p>      * Classifiers are a very typical feature of sign languages. In some Asian languages, classifiers are used      * as particles to combine a noun with a numeral, e.g. chin. _san ge ren_ 'three pieces of people', 'three      * people' (Bußmann 2002, under Klassifikator)      *<p>      * Bharati et al. (2006, for Indian languages) group Classifiers together with Quantifiers and Numerals,      * but they do not provide a detailed characterization of this class.      *<p>      * Akshar Bharati, Dipti Misra Sharma, Lakshmi Bai, Rajeev Sangal (2006), AnnCorra : Annotating Corpora.      * Guidelines For POS And Chunk Annotation For Indian Languages, Tech. Rep., L anguage Technologies      * Research Centre IIIT, Hyderabad, version of 15-12-2006, http://ltrc.iiit.ac.in/tr031/posguidelines.pdf      */
name|Classifier
argument_list|(
name|LexicalCategory
operator|.
name|Unique
argument_list|)
operator|,
comment|/**      * Generally, discourse markers are expressions or phrases of greeting, apologizing, thanking, short      * emotional utterances, and interjections. Their node label is DM. ... Typical discourse markers are: ja,      * nein, hallo, oh, aha, pst, nunja, gewiß, toll, nun ja, etc. (Telljohann et al. 2009, p. 136)      */
name|DiscourseMarker
argument_list|(
name|LexicalCategory
operator|.
name|Unique
argument_list|)
operator|,
comment|/**      * For Hindi, words like 'bahuta', 'kama', etc. when intensifying adjectives or adverbs will be annotated      * as INTF. Example, h37. hEdarAbAda meM aMgUra bahuta_INTF acche milate hEM 'HyderabAd' 'in' 'grapes'      * 'very' 'good' 'available' 'are' “Very good grapes are available in Hyderabad” (Bharati et al. 2006)<br>      * Akshar Bharati, Dipti Misra Sharma, Lakshmi Bai, Rajeev Sangal (2006), AnnCorra : Annotating Corpora.      * Guidelines For POS And Chunk Annotation For Indian Languages, Tech. Rep., L anguage Technologies      * Research Centre IIIT, Hyderabad, version of 15-12-2006, http://ltrc.iiit.ac.in/tr031/posguidelines.pdf      */
name|Intensifier
argument_list|(
name|LexicalCategory
operator|.
name|Unique
argument_list|)
operator|,
comment|/**      * The izāfat (pronounced as a shorter form of –ē–) is an enclitic of Persian origin which is used in      * Farsi and neighboring languages. In Urdu, it can be considered a preposition under certain      * circumstances: it links two nouns in a possessive relationship, although the phrase thus produced may      * often have a different meaning to a phrase produced with the native Urdu postposition kā. However, the      * izāfat may also join a noun to an adjective, in which case it is not so clearly accurate to describe it      * as a preposition parallel to the prepositions in European languages for which the EAGLES guidelines      * were compiled. A better way to treat izāfat is in the context of the Unique category of miscellaneous      * one-member wordclasses, discussed below. (Hardie 2003, http://purl.org/olia/emille.owl#Izafat)      */
name|Izafat
argument_list|(
name|LexicalCategory
operator|.
name|Unique
argument_list|)
operator|,
comment|/**      * In Urdu, multiplicative numerals are formed by adding the suffix gunâ (Schmidt 1999, p.      * 260,http://purl.org/olia/emille.owl#MultiplicativeMarker)      */
name|MultiplicativeMarker
argument_list|(
name|LexicalCategory
operator|.
name|Unique
argument_list|)
operator|,
comment|/**      * synonym of Unique, to be avoided because of its divergent definitions (Chiarcos)      */
name|Particle
argument_list|(
name|LexicalCategory
operator|.
name|Unique
argument_list|)
operator|,
comment|/**      * Particle that serves to form adjective phrases, e.g., Urdu sā<br>      * (http://purl.org/olia/emille.owl#AdjectivalParticle)      */
name|AdjectivalParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      * Contrastive particle, e.g., (one of the uses of) Urdu tô:      *       *<pre>      *     vo urdû parhê gâ      *     "He will study Urdu." (simple statement)      *       *     vo tô urdû parhê gâ      *     "HE will study Urdu." (Contrast: the other students may not.)      *</pre>      *       * (Schmidt 1999, p. 232, see http://purl.org/olia/emille.owl#ContrastiveEmphaticParticle)      */
name|ContrastiveParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      * Emphatic particle, e.g., (one of the uses of) Urdu tô:      *       *<pre>      *     vo urdû parhê gâ      *     "He will study Urdu." (simple statement)      *               *     vo urdû parhê gâ tô lêkin imtihân nahîm dê gâ      * "He will STUDY Urdu, OF COURSE, but he won't take the examination."      *       *<pre>      (Schmidt 1999, p. 232, see http://purl.org/olia/emille.owl#ContrastiveEmphaticParticle)      */
name|EmphaticParticle
argument_list|(
name|Particle
argument_list|,
name|Intensifier
argument_list|)
operator|,
comment|/**      * adopted from EMILLE, http://purl.org/olia/emille.owl#ContrastiveEmphaticParticle, shorthand for      * ContrastiveParticle and EmphaticParticle      */
name|ContrastiveEmphaticParticle
argument_list|(
name|EmphaticParticle
argument_list|,
name|ContrastiveParticle
argument_list|)
operator|,
comment|/**      * In Urdu, the exclusive emphatic particle hî emphasizes the preceding word and excludes something else      * (which may not be expressed). (Schmidt 1999, p.233,      * http://purl.org/olia/emille.owl#ExclusiveEmphaticParticle)<br>      * Compare with the inclusive emphatic particle bhî:      *       *<pre>      *     maim *bhî* faisalâ karûm gâ      *     "I'll *also* make a decision"      *            *     maim *hî* faisalâ karûm gâ      *     "*I'm the one who* will make the decision."      *</pre>      *       * (Schmidt 1999, p.237, http://purl.org/olia/emille.owl#InclusiveEmphaticParticle)      */
name|ExclusiveEmphaticParticle
argument_list|(
name|EmphaticParticle
argument_list|)
operator|,
comment|/**      * In Urdu, bhî is an emphatic particle meaning 'even'. In opposition to contrastive tô and exclusive hî,      * bhî is inclusive:      *       *<pre>      *     maim *bhî* faisalâ karûm gâ      *     "I'll *also* make a decision"      *                   *      maim *hî* faisalâ karûm gâ      *      "*I'm the one who* will make the decision."      *</pre>      *       * (Schmidt 1999, p.237, http://purl.org/olia/emille.owl#InclusiveEmphaticParticle)      */
name|InclusiveEmphaticParticle
argument_list|(
name|EmphaticParticle
argument_list|)
operator|,
comment|/**      * English existential there is specified as a subtype of pronoun in MTE v4, i.e., Pronoun/Type="ex-there"<br>      * (http://purl.org/olia/mte/multext-east.owl#ExistentialThere)      */
name|ExistentialParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      * http://www.isocat.org/datcat/DC-1455 (preverbalParticleLmf)      */
name|PreverbalParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      * A verbal particle modifies the verb and carries information on the verb form (e.g., finiteness, tense      * and aspect). (Dimitrova et al. 2009, Dan Tufis, email 2010/06/09).      *       * In the Bulgarian MTE specs, Particle/Type=verbal(v) is used to form different type of verbal      * syntactical relationships, e.g. to create future tense (ще говориш), or particles like се, да.      * (Dimitrova et al. 2009) The Romanian MTE v4 specs provide a more fine-grained subclassification of      * (verbal) particles (MTE v4, http://purl.org/olia/mte/multext-east.owl#VerbalParticle)      */
name|VerbalParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      * In the Romanian MULTEXT-East scheme, a verbal particle with Particle/Type="aspect" modifies the verbs      * and carries information on the verb form, i.e., on its aspect (Dan Tufis, email 2010/06/09,      * http://purl.org/olia/mte/multext-east.owl#AspectParticle)      */
name|AspectParticle
argument_list|(
name|VerbalParticle
argument_list|)
operator|,
comment|/**      * A modality-marking adverb is a verbal particle that serves to indicate mood, aspect and/or tense (cf.      * Schmidt 1999). Note that this is not to be confused with the conventional meaning of "modal adverb" in      * the sense of "manner adverb" (cf. http://en.wiktionary.org/wiki/Category:English_modal_adverbs), hence      * the uncommon name.      *       * Ruth Laila Schmidt (1999) Urdu, an essential grammar, Routledge, London.      */
name|ModalityMarkingAdverb
argument_list|(
name|LexicalCategory
operator|.
name|Adverb
argument_list|,
name|VerbalParticle
argument_list|)
operator|,
comment|/**      * In the Romanian MULTEXT-East scheme, a verbal particle with Particle/Type="future" modifies the verbs      * and marks the verb as being subjunctive, e.g., s-/să, să (Dan Tufis, email 2010/06/09,      * http://purl.org/olia/mte/multext-east.owl#SubjunctiveParticle)      */
name|SubjunctiveParticle
argument_list|(
name|VerbalParticle
argument_list|)
operator|,
comment|/**      * Dzongkha has also a tense marker, which is not complicated like in other languages. It has got only six      * tense markers and can be used in a very simple and effective way. They are: ('Ni'+'Wong') for future,      * ('D'o'+'D'ä') for present and ('Ci'+'Yi') for past tense.      *       *<pre>      *     ང་ ནངས་པ་ འ ་ །      *     Nga naba jo-ni[past tense]      *     I tomorrow go-will-[past]      *     'I am going tomorrow'      *</pre>      *       *<br>      * (http://panl10n.net/english/Outputs%20Phase%202/CCs/Bhutan/Papers/2007/0701/PartOfSpeech.pdf)      */
name|TenseMarkingParticle
argument_list|(
name|VerbalParticle
argument_list|)
operator|,
comment|/**      * Particle used in order to express future.<br>      * (http://www.isocat.org/datcat/DC-1919)      */
name|FutureParticle
argument_list|(
name|TenseMarkingParticle
argument_list|)
operator|,
comment|/**      * E.g., the mediopassive (middle) voice marker se in the Portuguese EAGLES scheme. (Leech and Wilson      * 1996)      */
name|VoiceParticle
argument_list|(
name|VerbalParticle
argument_list|)
operator|,
comment|/**      *       Particle used to express infinitive.<br>      * (http://www.isocat.org/datcat/DC-1896)      */
name|InfinitiveParticle
argument_list|(
name|VerbalParticle
argument_list|)
operator|,
comment|/**      *       Particle used to compare.<br>      * (http://www.isocat.org/datcat/DC-1922)      */
name|ComparativeParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      *       conditional particule (MIRACL& LSCA; http://www.isocat.org/datcat/DC-2230)      */
name|ConditionalParticule
argument_list|(
name|CoordinatingConjunction
argument_list|,
name|Particle
argument_list|)
operator|,
comment|/**      *       particle for coordination (MIRACL& LSCA; http://www.isocat.org/datcat/DC-2227)      */
name|CoordinationParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      *       distinctive particle (MIRACL& LSCA; http://www.isocat.org/datcat/DC-2228)      */
name|DistinctiveParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      *       Particle used to express a question.<br>      * (http://www.isocat.org/datcat/DC-1921)      */
name|InterrogativeParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      *       Particle which functions as a modal.<br>      * (http://www.isocat.org/datcat/DC-1920)      */
name|ModalParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      *       Particle used to express negation. (Gil Francopoulo; http://www.isocat.org/datcat/DC-1894)      */
name|NegativeParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      * Particle used to express affirmation.<br>      * (http://www.isocat.org/datcat/DC-1918)      */
name|AffirmativeParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      *       Particle expressing ownship.<br>      * (http://www.isocat.org/datcat/DC-1895)      */
name|PossessiveParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      * relative particle (MIRACL& LSCA; http://www.isocat.org/datcat/DC-2229)      */
name|RelativeParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      *       Particle expressing superlative degree. Superlative is the comparison between more than two entities      * and contrasts with comparative where only two entities are involved and positive where no comparison is      * implied. (Crystal 2003; http://www.isocat.org/datcat/DC-1923)      */
name|SuperlativeParticle
argument_list|(
name|Particle
argument_list|)
operator|,
comment|/**      * In Urdu, wālā can be added to substantives to derive nouns implying possession or general      * relationships, e.g., go-wāl, or go-wālā, s.m. cow-keeper, cow-herd (from go, 'cow'), or ghar-wālā, s.m.      * master or owner of the house (from ghar, 'house') (Plats 1884, cf. http://purl.org/olia/urdu.owl#Wala)      */
name|PossessionMarker
argument_list|(
name|LexicalCategory
operator|.
name|Unique
argument_list|)
operator|,
comment|/**      *       Word which serves no grammatical function, but which fills up a sentence or gives emphasis.      * (www.southwestern.edu/~carlg/Latin_Web/glossary.html; http://www.isocat.org/datcat/DC-1283)      */
name|Expletive
argument_list|(
name|LexicalCategory
operator|.
name|Unique
argument_list|)
operator|,
comment|/**      *       An expletive (also known as a dummy word) is a part of speech whose members have no meaning, but      * complete a sentence to make it grammatical [Crystal 1997, 127]<br>      * (http://purl.org/linguistics/gold/Expletive)      *<p>      * In European languages, expletives are pronouns. A verbal part of speech that      * "has no meaning, but complete a sentence to make it grammatical" is a copula (see AuxiliaryVerb).      */
name|ExpletivePronoun
argument_list|(
name|ThirdPersonPronoun
argument_list|,
name|Expletive
argument_list|)
operator|,
comment|/**      *       Three different expletive usages [of the German expletive pronoun es] are traditionally distinguished:      * formal subject or object (expletive argument), correlate of an extraposed clausal argument (expletive      * correlate), and Vorfeld-es (structural expletive) (cf. (Eisenberg 1999 2001), (Pütz 1986)). ...      *<p>      * The formal subject obligatorily occurs with weather verbs, e.g. "Es regnet" and unpersonal or agentless      * constructions such as "Es gibt so eine Buchung" or "Es geht um populäre Unterhaltung." Some verbs      * optionally permit an expletive subject but also occur with referential subjects such as      * "Max/Es kopft an der Tür." A formal object is found in constructions like "jmd. legt es an auf etw." or      * "jmd. verdirbt es mit jmdm." In all examples mentioned, es functions as a grammatical argument without      * semantic contribution, i.e. it does not refer to a person, object, or event. (Telljohann et al. 2009,      * p.60f)      */
name|ExpletiveArgument
argument_list|(
name|ExpletivePronoun
argument_list|)
operator|,
comment|/**      *       Three different expletive usages [of the German expletive pronoun es] are traditionally distinguished:      * formal subject or object (expletive argument), correlate of an extraposed clausal argument (expletive      * correlate), and Vorfeld-es (structural expletive) (cf. (Eisenberg 1999 2001), (Pütz 1986)). (Telljohann      * et al. 2009, p.60)      *<p>      * Extraposed clausal arguments:      * "Aber [es] ist übertrieben zu sagen, damit bekäme die FU erst eine Identität." (Telljohann et al. 2009,      * p.62)      */
name|ExpletiveCorrelate
argument_list|(
name|ExpletivePronoun
argument_list|)
operator|,
comment|/**      *       Three different expletive usages [of the German expletive pronoun es] are traditionally distinguished:      * formal subject or object (expletive argument), correlate of an extraposed clausal argument (expletive      * correlate), and Vorfeld-es (structural expletive) (cf. (Eisenberg 1999 2001), (Pütz 1986)). (Telljohann      * et al. 2009, p.60)      *<p>      * In German, a purely structural dummy element ... occurs in Vorfeld position only and is not correlated      * with any argument of the clause. It does not agree with the verb which becomes evident if there is a      * plural subject in the Mittelfeld:      *<p>      * "es zahlen ihn die Völker, deren Menschenrechte angeblich verteidigt werden."      *<p>      * It is ungrammatical in the Mittelfeld, e.g. *". . . dass es ihn die Völker zahlen".      */
name|StructuralExpletive
argument_list|(
name|ExpletivePronoun
argument_list|)
operator|,
comment|/**      *       An auxiliary verb is a verb which accompanies the lexical verb of a verb phrase, and expresses      * grammatical distinctions not carried by the lexical verb, such as person, number, tense aspect, and      * voice.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAnAuxiliaryVerb.htm 19.09.06)      *<p>      * Besides modal verbs ("semiauxiliary") and "strict" auxiliary verbs, also copulas are classified under      * auxiliary verbs here, as this is a praxis applied in practically every EAGLES-conformant      * morphosyntactic annotation scheme.      *<p>      * Part of speech referring to the set of verbs, subordinate to the main lexical verb which help to make      * distinction in mood, aspect, voice etc. (Crystal 2003; http://www.isocat.org/datcat/DC-1244)      */
name|AuxiliaryVerb
argument_list|(
name|LexicalCategory
operator|.
name|Verb
argument_list|)
operator|,
comment|/**      *       A copula is an intransitivity verb which links a subject to a noun phrase, an adjective or an other      * constituent which expresses the predicate.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsACopula.htm 19.09.06)      */
name|Copula
argument_list|(
name|AuxiliaryVerb
argument_list|)
operator|,
comment|/**      *       Verb form that is usually used with another verb to express ideas such as possibilities, permission, or      * intention. (Gil Francopoulo; http://www.isocat.org/datcat/DC-1329)      *<p>      * A modal verb (also modal, modal auxiliary verb, modal auxiliary) is a type of auxiliary verb that is      * used to indicate modality. The use of auxiliary verbs to express modality is characteristic of Germanic      * languages.<br>      * (http://en.wikipedia.org/wiki/Modal_verb 19.09.06)      *<p>      * In addition to main and auxiliary verbs, it may be useful (e.g. in English) to recognise an      * intermediate category of semi-auxiliary for such verbs as be going to, have got to, ought to.<br>      * (http://www.ilc.cnr.it/EAGLES96/annotate/node18.html#oav1v 20.09.06)      *<p>      * The auxiliaries in English subdivide into the primary verbs `be', `have', and `do', which can also      * function as main verbs, and the modal auxiliaries such as `can', `will', and `would', which are      * uninflected, and always function as auxiliaries.<br>      * (http://www.ilc.cnr.it/EAGLES96/morphsyn/node158.html#SECTION00054800000000000000)      */
name|ModalVerb
argument_list|(
name|AuxiliaryVerb
argument_list|)
operator|,
comment|/**      *       Non-modal, non-copular auxiliary verb.      */
name|StrictAuxiliaryVerb
argument_list|(
name|AuxiliaryVerb
argument_list|)
operator|,
comment|/**      * An auxiliary that marks exclusively aspect, e.g., in Urdu:      *<p>      * Auxiliaries: Based on the syntactic nature of Urdu, auxiliaries are divided into two categories.      * Aspectual auxiliaries always occur after main verb of the sentence. Tense auxiliaries are used to show      * the time of the action. They occurred at the end of the verb phrase (Sajjad 2007).      *<p>      * E.g., Urdu rahā, an auxiliary element is used to mark the durative aspect. (Hardie 2004,      * http://purl.org/olia/emille.owl#RahaAuxiliary)      */
name|AspectMarkingAuxiliary
argument_list|(
name|StrictAuxiliaryVerb
argument_list|)
operator|,
comment|/**      * An auxiliary that marks exclusively tense, e.g., in Urdu:      *<p>      * Auxiliaries: Based on the syntactic nature of Urdu, auxiliaries are divided into two categories.      * Aspectual auxiliaries always occur after main verb of the sentence. Tense auxiliaries are used to show      * the time of the action. They occurred at the end of the verb phrase. (Sajjad 2007).      *<p>      * In Urdu, the auxiliary gā indicates future tense when it follows a verb in the subjunctive form.<br>      * (http://purl.org/olia/emille.owl#GaAuxiliary)      */
name|TenseMarkingAuxiliary
argument_list|(
name|StrictAuxiliaryVerb
argument_list|)
operator|,
comment|/**      *       Verb used to link the subject of a sentence and its noun or adjective complement or complementing      * phrase in certain languages. This verb could be used also to form the passive voice.      * (www.wordreference.com/English/definition.asp?en=be -> 4); http://www.isocat.org/datcat/DC-1246)      */
name|BeAuxiliary
argument_list|(
name|StrictAuxiliaryVerb
argument_list|)
operator|,
comment|/**      *       The verb have as an auxiliary.      * (www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAnAuxiliaryVerb.htm;      * http://www.isocat.org/datcat/DC-1299)      */
name|HaveAuxiliary
argument_list|(
name|StrictAuxiliaryVerb
argument_list|)
operator|,
comment|/**      *       A finite verb is a verb form that occurs in an independent clause, and is fully inflected according to      * the inflectional categories marked on verbs in the language.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAFiniteVerb.htm 19.09.06)      *<p>      * Property applied to a verb form that can occur on its own in an independent sentence. (Crystal 2003;      * http://www.isocat.org/datcat/DC-1287)      */
name|FiniteVerb
argument_list|(
name|LexicalCategory
operator|.
name|Verb
argument_list|)
operator|,
comment|/**      *       A conditional verb is a verb form in many languages. It is used to express degrees of certainty or      * uncertainty and hypothesis about past, present, or future. Such forms often occur in conditional      * sentences.<br>      * (http://en.wikipedia.org/wiki/Conditional_mood 19.09.06)      */
name|ConditionalVerb
argument_list|(
name|FiniteVerb
argument_list|)
operator|,
comment|/**      *       An imperative verb is used to express commands, direct requests, and prohibitions. Often, direct use of      * the imperative mood may appear blunt or even rude, so it is often used with care. Example: "Paul, read      * that book".<br>      * (http://en.wikipedia.org/wiki/Grammatical_mood#Imperative_mood 19.09.06)      */
name|ImperativeVerb
argument_list|(
name|FiniteVerb
argument_list|)
operator|,
comment|/**      *       Indicative mood is used in factual statements. All intentions in speaking that a particular language      * does not put into another mood use the indicative. It is the most commonly used mood and is found in      * all languages.<br>      * (http://en.wikipedia.org/wiki/Grammatical_mood#Indicative_mood 19.09.06)      */
name|IndicativeVerb
argument_list|(
name|FiniteVerb
argument_list|)
operator|,
comment|/**      *       A subjunctive verb is typically used to expresses wishes, commands (in subordinate clauses), emotion,      * possibility, judgment, necessity, and statements that are contrary to fact at present.<br>      * (http://en.wikipedia.org/wiki/Subjunctive_mood 19.09.06)      */
name|SubjunctiveVerb
argument_list|(
name|FiniteVerb
argument_list|)
operator|,
comment|/**      * In linguistics, a light verb is a verb participating in complex predication that has little semantic      * content of its own, but provides through inflection some details on the event semantics, such as      * aspect, mood, or tense. The semantics of the compound, as well as its argument structure, are      * determined by the head or primary component of the compound, which may be a verb or noun (V+V or V+N      * compounds). Other names for "light verb" include: vector verb or explicator verb, emphasising its role      * within the compound; or thin verb or semantically weak verb, emphasising (as with "light") its lack of      * semantics. A "semantically weak" verb is not to be confused with a "weak verb" as in the Germanic weak      * inflection. Light verbs are similar to auxiliary verbs in some ways.      *<p>      * Most English light verbs occur in V+N forms sometimes called "stretched verbs": for example, take in      * take a nap, where the primary sense is provided by "nap", and "take" is the light verb. The light verbs      * most common in these constructions are also common in phrasal verbs. A verb which is "light" in one      * context may be "heavy" in another: as with "take" in I will take a book to read.      *<p>      * Examples in other languages include the Yiddish geb in geb a helf (literally give a help, "help"); the      * French faire in faire semblant (lit. make seeming, "pretend"); the Hindi nikal paRA (lit. leave fall,      * "start to leave"); and the bǎ construction in Chinese.[1] Some verbs are found in many such      * expressions; to reuse an earlier example, take is found in take a nap, take a shower, take a sip, take      * a bow, take turns, and so on. Light verbs are extremely common in Indo-Iranian languages, Japanese, and      * other languages in which verb compounding is a primary mechanism for marking aspectual distinctions.<br>      * (http://en.wikipedia.org/wiki/Light_verb)      */
name|LightVerb
argument_list|(
name|LexicalCategory
operator|.
name|Verb
argument_list|)
operator|,
comment|/**      * Verb forms occurring on their own only in dependent clauses and lacking tense and mood contrasts.      * (adapted from Crystal 2003; http://www.isocat.org/datcat/DC-1332)      *<p>      * A non-finite verb is a verb that is not fully inflected for categories that are marked inflectionally      * in a language, such as the following: Tense, Aspect, Modality, Number, Person.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsANonfiniteVerb.htm 19.09.06)      */
name|NonFiniteVerb
argument_list|(
name|LexicalCategory
operator|.
name|Verb
argument_list|)
operator|,
comment|/**      * property for a non-finite form of a verb other than the infinitive.<br>      * (http://www.isocat.org/datcat/DC-2243)      *<p>      * A gerund is a kind of verbal noun that exists in some languages. In today's English, gerunds are nouns      * built from a verb with an '-ing' suffix. They can be used as the subject of a sentence, an object, or      * an object of preposition. They can also be used to complement a subject. Often, gerunds exist      * side-by-side with nouns that come from the same root but the gerund and the common noun have different      * shades of meaning.<br>      * (http://en.wikipedia.org/wiki/Gerund, http://en.wikibooks.org/wiki/English:Gerund 19.09.06)      *<p>      * The term _gerund_ is ambiguous: with respect to Latin, in whose grammatical tradition it originates, it      * refers to a deverbal noun, and is needed in this function for Polish as well; in descriptions of some      * other languages, however, it has been used for an adverbial participle. The two meanings have nothing      * in common, except that the English _ing_-form can translate both. (Ivan A Derzhanski, email 2010/06/09)      * Here, it is assumed that Gerund refers only to deverbal nouns, cf. NominalNonfiniteVerb in the IIIT      * tagset<br>      * (http://purl.org/olia/iiit.owl#NominalNonFiniteVerb)      */
name|Gerund
argument_list|(
name|NonFiniteVerb
argument_list|)
operator|,
comment|/**      * An infinitive is the base form of a verb. It is unmarked for inflectional categories such as the      * following: Aspect, Modality, Number, Person and Tense.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAnInfinitive.htm 19.09.06)      */
name|Infinitive
argument_list|(
name|NonFiniteVerb
argument_list|)
operator|,
comment|/**      *       A participle is a lexical item, derived from a verb that has some of the characteristics and functions      * of both verbs and adjectives. In English, participles may be used as adjectives, and in non-finite      * forms of verbs.<br>      * (http://www.sil.org/linguistics/GlossaryOfLinguisticTerms/WhatIsAParticiple.htm 19.09.06)      */
name|Participle
argument_list|(
name|NonFiniteVerb
argument_list|)
operator|,
comment|/**      * Adverb/Type="participle" is used in the Slovene MTE v4 specs, e.g., 'leže' / lying. Slovenian adverbial      * participles are, however, not attested for Resian. (MTE      * v4)(http://purl.org/olia/mte/multext-east.owl#AdverbialParticiple)      */
name|AdverbialParticiple
argument_list|(
name|LexicalCategory
operator|.
name|Adverb
argument_list|,
name|Participle
argument_list|)
operator|,
comment|/**      * [In Bengali, t]he Conditional Participle is widely used to convey      * "if a certain action [pertaining to the parent verb] is done,...". The logic is: "in the case or      * condition of a certain action being done". Being impersonal, without regard for the doer of the action      * that caused the condition, it is not declined to suit number or gender. If this doer is not defined in      * the Bengali condition clause but needs to be stated in a natural-sounding English translation, this is      * identified and drawn from the second clause. For example:- Student: Teaching Truth in Bengali      *<p>      * If you pay attention,* you will learn. manoyog kar-*le* tumi shikh-be. [or, If attention is paid]      *<p>      *<br>      * (http://www.jaspell.co.uk/bengalicourse2007/wb149study49.pdf)      */
name|ConditionalParticiple
argument_list|(
name|Participle
argument_list|)
operator|,
comment|/**      * Participle and hasTense some Past      */
name|PastParticiple
argument_list|(
name|Participle
argument_list|)
operator|,
comment|/**      * Participle and hasTense some Present      */
name|PresentParticiple
argument_list|(
name|Participle
argument_list|)
operator|,
comment|/**      * English verb forms ending in '-ing' that represent either Gerunds or Participles.      */
name|Ing
argument_list|(
literal|"ing"
argument_list|,
name|Gerund
argument_list|,
name|Participle
argument_list|)
operator|,
comment|/**      * Adjective based on a verb.<br>      * (http://www.isocat.org/datcat/DC-1598)      */
name|ParticipleAdjective
argument_list|(
name|LexicalCategory
operator|.
name|Adjective
argument_list|,
name|Participle
argument_list|)
operator|,
comment|/**      *       Adjective based on a past participle.<br>      * (http://www.isocat.org/datcat/DC-1596)      */
name|PastParticipleAdjective
argument_list|(
name|ParticipleAdjective
argument_list|,
name|PastParticiple
argument_list|)
operator|,
comment|/**      *       Adjective based on a present participle.<br>      * (http://www.isocat.org/datcat/DC-1597)      */
name|PresentParticipleAdjective
argument_list|(
name|ParticipleAdjective
argument_list|,
name|PresentParticiple
argument_list|)
operator|,
comment|/**      *       Supine is a nonfinite form of motion verbs with functions similar to that of an infinitive (Angelika      * Adams)      */
name|Supine
argument_list|(
name|NonFiniteVerb
argument_list|)
operator|,
comment|/**      * A verbal noun is a noun formed directly as an inflexion of a verb or a verb stem, sharing at least in      * part its constructions. This term is applied especially to gerunds, and sometimes also to infinitives      * and supines.<br>      * (http://en.wikipedia.org/wiki/Verbal_noun 19.09.06)      */
name|VerbalNoun
argument_list|(
name|CommonNoun
argument_list|,
name|NonFiniteVerb
argument_list|)
operator|,
comment|/**      * Main verb in contrast to a modal or an auxiliary.<br>      * (http://www.isocat.org/datcat/DC-1400)      */
name|MainVerb
argument_list|(
name|LexicalCategory
operator|.
name|Verb
argument_list|)
operator|,
comment|/**      * An agentive verb marks the semantic role of agent or the doer of an action.      *<p>      * Example:      *       *<pre>      *     ་ ་ ས་ ་ ་ བསད་ ག།       *     Dorji-gi jele sänu       *     'Dorji killed the cat'      *</pre>      *       *<br>      * (http://panl10n.net/english/Outputs%20Phase%202/CCs/Bhutan/Papers/2007/0701/PartOfSpeech.pdf)      */
name|AgentiveVerb
argument_list|(
name|MainVerb
argument_list|)
operator|,
comment|/**      * It is a verb, which indicates a strong desire to achieve something, without the doer.      *       *<pre>      *     དག་པ ་ ང་ ་ ་བར་ ག།       *     dag-pai zhing-lu kewa shÔ       *     'May i be born in pure land'      *</pre>      *       *<br>      * (http://panl10n.net/english/Outputs%20Phase%202/CCs/Bhutan/Papers/2007/0701/PartOfSpeech.pdf)      */
name|AspirationalVerb
argument_list|(
name|MainVerb
argument_list|)
operator|,
comment|/**      * Dzongkha uses honorific forms: ན་བཟའ་/nam za/ (cloths) is the honorific form of the noun གོ་      * ལ་/gola/(cloths), གསངས་/sung/(tell) the honorific form of the verb སབ་/lab/(tell). We opted to mark      * them by adding the tag NNH (honorific common noun) and VBH (honorific verb) to enable future research      * on this specific usage of Dzongkha language. A number of tags were added to the set, of which we      * describe four in more detail: two of the additional tags are subclasses of verbs: VBH (honorific verb      * form), and VBN which describes past participle forms, like, e.g. བངམ་/jun/(created), the past particle      * form of བང་/jung/(create).      */
name|HonorificVerb
argument_list|(
name|MainVerb
argument_list|)
operator|,
comment|/**      * (of a verb) having no logical subject. Usually in English the pronoun it is used in such cases as a      * grammatical subject, as for example in It is raining. (of a pronoun) not denoting a person      * (www.wordreference.com/English/definition.asp?en=impersonal; http://www.isocat.org/datcat/DC-1306)      */
name|Impersonal
argument_list|(
name|MainVerb
argument_list|)
operator|,
comment|/**      * A non-agentive verb is a type of verb, which indicates an action without the doer.      *<p>      * Example: ང་མ་ འ ར་ ས། 'lungma phur-dä 'A wind is blowing'<br>      * (http://panl10n.net/english/Outputs%20Phase%202/CCs/Bhutan/Papers/2007/0701/PartOfSpeech.pdf)      */
name|NonAgentiveVerb
argument_list|(
name|MainVerb
argument_list|)
operator|,
enum|;
end_enum

begin_decl_stmt
specifier|static
specifier|final
name|String
name|OLIA_NAMESPACE
init|=
literal|"http://purl.org/olia/olia.owl#"
decl_stmt|;
end_decl_stmt

begin_decl_stmt
specifier|private
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|categories
decl_stmt|;
end_decl_stmt

begin_decl_stmt
specifier|private
specifier|final
name|Collection
argument_list|<
name|Pos
argument_list|>
name|parents
decl_stmt|;
end_decl_stmt

begin_decl_stmt
specifier|private
specifier|final
name|UriRef
name|uri
decl_stmt|;
end_decl_stmt

begin_expr_stmt
name|Pos
argument_list|(
name|LexicalCategory
name|category
argument_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
name|category
argument_list|,
operator|(
name|LexicalCategory
operator|)
literal|null
argument_list|)
block|;     }
name|Pos
argument_list|(
name|LexicalCategory
name|category
argument_list|,
name|LexicalCategory
name|additional
argument_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
name|category
argument_list|,
name|additional
argument_list|)
block|;     }
name|Pos
argument_list|(
name|String
name|name
argument_list|,
name|LexicalCategory
name|category
argument_list|,
name|LexicalCategory
name|additional
argument_list|)
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
operator|(
name|name
operator|==
literal|null
operator|?
name|name
argument_list|()
operator|:
name|name
operator|)
argument_list|)
block|;
name|categories
operator|=
name|EnumSet
operator|.
name|of
argument_list|(
name|category
argument_list|)
block|;
if|if
condition|(
name|additional
operator|!=
literal|null
condition|)
block|{
name|categories
operator|.
name|add
argument_list|(
name|additional
argument_list|)
expr_stmt|;
block|}
name|parents
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
end_expr_stmt

begin_expr_stmt
unit|}      Pos
operator|(
name|Pos
operator|...
name|parent
operator|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
name|parent
argument_list|)
block|;     }
name|Pos
argument_list|(
name|String
name|name
argument_list|,
name|Pos
operator|...
name|parent
argument_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
literal|null
argument_list|,
name|parent
argument_list|)
block|;     }
name|Pos
argument_list|(
name|LexicalCategory
name|category
argument_list|,
name|Pos
operator|...
name|parent
argument_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
name|category
argument_list|,
name|parent
argument_list|)
block|;     }
name|Pos
argument_list|(
name|String
name|name
argument_list|,
name|LexicalCategory
name|category
argument_list|,
name|Pos
operator|...
name|parent
argument_list|)
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
operator|(
name|name
operator|==
literal|null
operator|?
name|name
argument_list|()
operator|:
name|name
operator|)
argument_list|)
block|;
name|this
operator|.
name|parents
operator|=
name|parent
operator|==
literal|null
operator|||
name|parent
operator|.
name|length
operator|<
literal|1
condition|?
name|Collections
operator|.
name|EMPTY_SET
else|:
name|Arrays
operator|.
name|asList
argument_list|(
name|parent
argument_list|)
block|;
name|categories
operator|=
name|category
operator|==
literal|null
condition|?
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|LexicalCategory
operator|.
name|class
argument_list|)
else|:
name|EnumSet
operator|.
name|of
argument_list|(
name|category
argument_list|)
block|;
name|Set
argument_list|<
name|Pos
argument_list|>
name|toProcess
operator|=
operator|new
name|HashSet
argument_list|<
name|Pos
argument_list|>
argument_list|(
name|parents
argument_list|)
block|;
while|while
condition|(
operator|!
name|toProcess
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Iterator
argument_list|<
name|Pos
argument_list|>
name|it
init|=
name|toProcess
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Pos
name|p
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|it
operator|.
name|remove
argument_list|()
expr_stmt|;
name|categories
operator|.
name|addAll
argument_list|(
name|p
operator|.
name|categories
argument_list|)
expr_stmt|;
name|toProcess
operator|.
name|addAll
argument_list|(
name|p
operator|.
name|parents
argument_list|)
expr_stmt|;
block|}
end_expr_stmt

begin_function
unit|}      public
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|categories
parameter_list|()
block|{
return|return
name|categories
return|;
block|}
end_function

begin_function
specifier|public
name|boolean
name|isParent
parameter_list|()
block|{
return|return
name|parents
operator|.
name|isEmpty
argument_list|()
return|;
block|}
end_function

begin_function
specifier|public
name|Collection
argument_list|<
name|Pos
argument_list|>
name|parents
parameter_list|()
block|{
return|return
name|parents
return|;
block|}
end_function

begin_function
specifier|public
name|UriRef
name|getUri
parameter_list|()
block|{
return|return
name|uri
return|;
block|}
end_function

begin_function
specifier|public
name|Set
argument_list|<
name|Pos
argument_list|>
name|hierarchy
parameter_list|()
block|{
return|return
name|transitiveClosureMap
operator|.
name|get
argument_list|(
name|this
argument_list|)
return|;
block|}
end_function

begin_function
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"olia:%s"
argument_list|,
name|uri
operator|.
name|getUnicodeString
argument_list|()
operator|.
name|substring
argument_list|(
name|OLIA_NAMESPACE
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
end_function

begin_comment
comment|/**      * This is needed because one can not create EnumSet instances before the      * initialization of an Enum has finished.<p>      * To keep using the much faster {@link EnumSet} a static member initialised      * in an static {} block is used as a workaround. The {@link Tense#getTenses()}      * method does use this static member instead of a member variable      */
end_comment

begin_decl_stmt
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|Pos
argument_list|,
name|Set
argument_list|<
name|Pos
argument_list|>
argument_list|>
name|transitiveClosureMap
decl_stmt|;
end_decl_stmt

begin_static
static|static
block|{
name|transitiveClosureMap
operator|=
operator|new
name|EnumMap
argument_list|<
name|Pos
argument_list|,
name|Set
argument_list|<
name|Pos
argument_list|>
argument_list|>
argument_list|(
name|Pos
operator|.
name|class
argument_list|)
expr_stmt|;
for|for
control|(
name|Pos
name|pos
range|:
name|Pos
operator|.
name|values
argument_list|()
control|)
block|{
name|Set
argument_list|<
name|Pos
argument_list|>
name|parents
init|=
name|EnumSet
operator|.
name|of
argument_list|(
name|pos
argument_list|)
decl_stmt|;
for|for
control|(
name|Pos
name|posParent
range|:
name|pos
operator|.
name|parents
argument_list|()
control|)
block|{
name|Set
argument_list|<
name|Pos
argument_list|>
name|transParents
init|=
name|transitiveClosureMap
operator|.
name|get
argument_list|(
name|posParent
argument_list|)
decl_stmt|;
if|if
condition|(
name|transParents
operator|!=
literal|null
condition|)
block|{
name|parents
operator|.
name|addAll
argument_list|(
name|transParents
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|posParent
operator|!=
literal|null
condition|)
block|{
name|parents
operator|.
name|add
argument_list|(
name|posParent
argument_list|)
expr_stmt|;
block|}
comment|// else no parent
block|}
name|transitiveClosureMap
operator|.
name|put
argument_list|(
name|pos
argument_list|,
name|parents
argument_list|)
expr_stmt|;
block|}
block|}
end_static

unit|}
end_unit

