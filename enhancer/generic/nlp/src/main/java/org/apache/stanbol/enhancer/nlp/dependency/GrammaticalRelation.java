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
name|dependency
package|;
end_package

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

begin_comment
comment|/**  * Enumeration over all grammatical relation categories as defined by the class  * hierarchy og the<a heref="http://olia.nlp2rdf.org/">Olia</a> Ontology.  *<p>  * The top-level categories are not defined by this enum, but link to the  * {@link GrammaticalRelationCategory} enumeration. The multi-sub-class  * hierarchy of the relation categories is also reflected by this enumeration  * and accessible via the  *<ul>  *<li> {@link #getParents()}: the direct parent classes  *<li> {@link #getCategory()}: the {@link GrammaticalRelationCategory  * GrammaticalRelationCategory}  *</ul>  *   * @author Cristian Petroaca  *   */
end_comment

begin_enum
specifier|public
enum|enum
name|GrammaticalRelation
block|{
name|Complement
parameter_list|(
name|GrammaticalRelationCategory
operator|.
name|Argument
parameter_list|)
operator|,
comment|/** 	 * A dependency is labeled as dep when the system is unable to determine a 	 * more precise dependency relation between two words. This may be because 	 * of a weird grammatical construction, a limitation in the Stanford 	 * Dependency conversion software, a parser error, or because of an 	 * unresolved long distance dependency. Example : Then, as if to show that 	 * he could, . . . Result : dep(show, if) 	 */
constructor|Dependent(GrammaticalRelationCategory.DependencyLabel
block|)
enum|,
name|Modifier
argument_list|(
name|Dependent
argument_list|)
operator|,
name|Object
argument_list|(
name|Complement
argument_list|)
operator|,
comment|/** 	 * An abbreviation modifier of an NP is a parenthesized NP that serves to 	 * abbreviate the NP (or to define an abbreviation). Example : The 	 * Australian Broadcasting Corporation (ABC) Result : abbrev(Corporation, 	 * ABC) 	 */
name|AbbreviationModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * An adjectival complement of a verb is an adjectival phrase which 	 * functions as the complement (like an object of the verb). Example : She 	 * looks very beautiful Result : acomp(looks, beautiful) 	 */
name|AdjectivalComplement
argument_list|(
name|Complement
argument_list|)
operator|,
comment|/** 	 * An adjectival modifier of an NP is any adjectival phrase that serves to 	 * modify the meaning of the NP. Example : Sam eats red meat. Result : 	 * amod(meat, red) 	 */
name|AdjectivalModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * An adverbial clause modifier of a VP or S is a clause modifying the verb 	 * (temporal clause, consequence, conditional clause, etc.). Example : The 	 * accident happened as the night was falling. Result : advcl(happened, 	 * falling) 	 */
name|AdverbialClauseModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * An adverbial modifier of a word is a (non-clausal) adverb or adverbial 	 * phrase (ADVP) that serves to modify the meaning of the word. Example : 	 * Genetically modified food Result : advmod(modified, genetically) 	 */
name|AdverbialModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * An agent is the complement of a passive verb which is introduced by the 	 * preposition&quot;by&quot; and does the action. Example : The man has 	 * been killed by the police. Result : agent(killed, police) 	 */
name|Agent
argument_list|(
name|GrammaticalRelationCategory
operator|.
name|Argument
argument_list|)
operator|,
comment|/** 	 * An appositional modifier of an NP is an NP immediately to the right of 	 * the first NP that serves to define or modify that NP. It includes 	 * parenthesized examples. Example : Sam, my brother Result : appos(Sam, 	 * brother) 	 */
name|AppositionalModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * An attributive is a WHNP complement of a copular verb such as&quot;to 	 * be&quot;,&quot;to seem&quot;,&quot;to appear&quot;. Example : What is 	 * that? Result : attr (is, What) 	 */
name|Attributive
argument_list|(
name|Complement
argument_list|)
operator|,
comment|/** 	 * An auxiliary of a clause is a non-main verb of the clause, e.g. modal 	 * auxiliary,&quot;be&quot; and&quot;have&quot; in a composed tense. 	 * Example : Reagan has died Result : aux(died, has) 	 */
name|Auxiliary
argument_list|(
name|Dependent
argument_list|)
operator|,
comment|/** 	 * An open clausal complement (xcomp) of a VP or an ADJP is a clausal 	 * complement without its own subject, whose reference is determined by an 	 * external subject. These complements are always non-finite. The name xcomp 	 * is borrowed from Lexical-Functional Grammar. Example : He says that you 	 * like to swim Result : xcomp(like, swim) 	 */
name|ClausalComplementWithExternalSubject
argument_list|(
name|Complement
argument_list|)
operator|,
comment|/** 	 * A clausal complement of a verb or adjective is a dependent clause with an 	 * internal subject which functions like an object of the verb, or 	 * adjective. Clausal complements for nouns are limited to complement 	 * clauses with a subset of nouns like&quot;fact&quot; or 	 *&quot;report&quot;. We analyze them the same (parallel to the analysis of 	 * this class as&quot;content clauses&quot; in Huddleston and Pullum 2002). 	 * Such clausal complements are usually finite (though there are occasional 	 * remnant English subjunctives). Example : He says that you like to swim 	 * Result :ccomp(says, like) 	 */
name|ClausalComplementWithInternalSubject
argument_list|(
name|Complement
argument_list|)
operator|,
name|Subject
argument_list|(
name|GrammaticalRelationCategory
operator|.
name|Argument
argument_list|)
operator|,
comment|/** 	 * A clausal subject is a clausal syntactic subject of a clause, i.e., the 	 * subject is itself a clause. The governor of this relation might not 	 * always be a verb: when the verb is a copular verb, the root of the clause 	 * is the complement of the copular verb. In the two following examples, 	 *&quot;what she said&quot; is the subject. Example : What she said makes 	 * sense Result : csubj (makes, said) 	 */
name|ClausalSubject
argument_list|(
name|Subject
argument_list|)
operator|,
comment|/** 	 * A complementizer of a clausal complement (ccomp) is the word introducing 	 * it. It will be the subordinating conjunction&quot;that&quot; or 	 *&quot;whether&quot;. Example : He says that you like to swim Result : 	 * complm(like, that) 	 */
name|Complementizer
argument_list|(
name|Complement
argument_list|)
operator|,
comment|/** 	 * An element of compound number is a part of a number phrase or currency 	 * amount. Example : I lost $ 3.2 billion Result : number($, billion) 	 */
name|CompountNumberElement
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * A conjunct is the relation between two elements connected by a 	 * coordinating conjunction, such as&quot;and&quot;,&quot;or&quot;, etc. 	 * We treat conjunctions asymmetrically: The head of the relation is the 	 * first conjunct and other conjunctions depend on it via the conj relation. 	 * Example : Bill is big and honest&quot; Result : conj (big, honest) 	 */
name|Conjunct
argument_list|(
name|Dependent
argument_list|)
operator|,
comment|/** 	 * A controlling subject is the relation between the head of a open clausal 	 * complement (xcomp) and the external subject of that clause. Example : Tom 	 * likes to eat fish Result : xsubj (eat, Tom) 	 */
name|ControllingSubject
argument_list|(
name|GrammaticalRelationCategory
operator|.
name|SemanticDependent
argument_list|)
operator|,
comment|/** 	 * A coordination is the relation between an element of a conjunct and the 	 * coordinating conjunction word of the conjunct. (Note: different 	 * dependency grammars have different treatments of coordination. We take 	 * one conjunct of a conjunction (normally the first) as the head of the 	 * conjunction.) Example : Bill is big and honest Result : cc(big, and) 	 */
name|Coordination
argument_list|(
name|Dependent
argument_list|)
operator|,
comment|/** 	 * A copula is the relation between the complement of a copular verb and the 	 * copular verb. (We normally take a copula as a dependent of its 	 * complement.) Example : Bill is big Result : cop(big, is) 	 */
name|Copula
argument_list|(
name|GrammaticalRelationCategory
operator|.
name|Auxiliary
argument_list|)
operator|,
comment|/** 	 * A determiner is the relation between the head of an NP and its 	 * determiner. Example : The man is here Result : det(man, the) 	 */
name|Determiner
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * The direct object of a VP is the noun phrase which is the (accusative) 	 * object of the verb. Example : She gave me a raise Result : dobj (gave, 	 * raise) 	 */
name|DirectObject
argument_list|(
name|Object
argument_list|)
operator|,
comment|/** 	 * This relation captures an existential&quot;there&quot;. The main verb of 	 * the clause is the governor. Example : There is a ghost in the room Result 	 * : expl(is, There) 	 */
name|Expletive
argument_list|(
name|Dependent
argument_list|)
operator|,
comment|/** 	 * The indirect object of a VP is the noun phrase which is the (dative) 	 * object of the verb. Examle : She gave me a raise Result : iobj (gave, me) 	 */
name|IndirectObject
argument_list|(
name|Object
argument_list|)
operator|,
comment|/** 	 * An infinitival modifier of an NP is an infinitive that serves to modify 	 * the meaning of the NP. Example : Points to establish are . . . Result ; 	 * infmod(points, establish) 	 */
name|InfinitivalModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * A marker of an adverbial clausal complement (advcl) is the word 	 * introducing it. It will be a subordinating conjunction different from 	 *&quot;that&quot; or&quot;whether&quot;: e.g.&quot;because&quot;, 	 *&quot;when&quot;,&quot;although&quot;, etc. Example : Forces engaged in 	 * fighting after insurgents attacked Result : mark(attacked, after) 	 */
name|Marker
argument_list|(
name|Complement
argument_list|)
operator|,
name|MeasurePhraseModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * The multi-word expression (modifier) relation is used for certain 	 * multi-word idioms that behave like a single function word. It is used for 	 * a closed set of dependencies between words in common multi-word 	 * expressions for which it seems difficult or unclear to assign any other 	 * relationships. At present, this relation is used inside the following 	 * expressions: rather than, as well as, instead of, such as, because of, 	 * instead of, in addition to, all but, such as, because of, instead of, due 	 * to. The boundaries of this class are unclear; it could grow or shrink a 	 * little over time. Example : I like dogs as well as cats&quot; Result : 	 * mwe(well, as) 	 */
name|MultiWordExpression
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * The negation modifier is the relation between a negation word and the 	 * word it modifies. Example : Bill is not a scientist Result : 	 * neg(scientist, not) 	 */
name|NegationModifier
argument_list|(
name|AdverbialModifier
argument_list|)
operator|,
comment|/** 	 * A nominal subject is a noun phrase which is the syntactic subject of a 	 * clause. The governor of this relation might not always be a verb: when 	 * the verb is a copular verb, the root of the clause is the complement of 	 * the copular verb, which can be an adjective or noun. Example : Clinton 	 * defeated Dole Result : nsubj (defeated, Clinton) 	 */
name|NominalSubject
argument_list|(
name|Subject
argument_list|)
operator|,
comment|/** 	 * A noun compound modifier of an NP is any noun that serves to modify the 	 * head noun. (Note that in the current system for dependency extraction, 	 * all nouns modify the rightmost noun of the NP -- there is no intelligent 	 * noun compound analysis. This is likely to be fixed once the Penn Treebank 	 * represents the branching structure of NPs.) Example : Oil price futures 	 * Result : nn(futures, oil) Result : nn(futures, price) 	 */
name|NounCompoundModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * This relation captures various places where something syntactically a 	 * noun phrase (NP) is used as an adverbial modifier in a sentence. These 	 * usages include: (i) a measure phrase, which is the relation between the 	 * head of an ADJP/ADVP/PP and the head of a measure phrase modifying the 	 * ADJP/ADVP; (ii) noun phrases giving an extent inside a VP which are not 	 * objects; (iii) financial constructions involving an adverbial or PP-like 	 * NP, notably the following construction $5 a share, where the second NP 	 * means&quot;per share&quot;; (iv) floating reflexives; and (v) certain 	 * other absolutive NP constructions. A temporal modifier (tmod) is a 	 * subclass of npadvmod which is distinguished as a separate relation. 	 * Example : The director is 65 years old Result : npadvmod(old, years) 	 */
name|NounPhraseAsAdverbialModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * A numeric modifier of a noun is any number phrase that serves to modify 	 * the meaning of the noun. Example : Sam eats 3 sheep Result : num(sheep, 	 * 3) 	 */
name|NumericModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * The object of a preposition is the head of a noun phrase following the 	 * preposition, or the adverbs&quot;here&quot; and&quot;there&quot;. (The 	 * preposition in turn may be modifying a noun, verb, etc.) Unlike the Penn 	 * Treebank, we here define cases of VBG quasi-prepositions like 	 *&quot;including&quot;,&quot;concerning&quot;, etc. as instances of pobj. 	 * (The preposition can be called a FW for&quot;pace&quot;, 	 *&quot;versus&quot;, etc. It can also be called a CC -- but we don&#39;t 	 * currently handle that and would need to distinguish from conjoined 	 * prepositions.) In the case of preposition stranding, the object can 	 * precede the preposition (e.g.,&quot;What does CPR stand for?&quot;). 	 * Example : I sat on the chair Result : pobj (on, chair) 	 */
name|ObjectOfPreposition
argument_list|(
name|Object
argument_list|)
operator|,
comment|/** 	 * The parataxis relation (from Greek for&quot;place side by side&quot;) is 	 * a relation between the main verb of a clause and other sentential 	 * elements, such as a sentential parenthetical, or a clause after a 	 *&quot;:&quot; or a&quot;;&quot;. Example : The guy, John said, left 	 * early in the morning Result : parataxis(left, said) 	 */
name|Parataxis
argument_list|(
name|Dependent
argument_list|)
operator|,
comment|/** 	 * A participial modifier of an NP or VP or sentence is a participial verb 	 * form that serves to modify the meaning of a noun phrase or sentence. 	 * Example : Truffles picked during the spring are tasty Result : 	 * partmod(truffles, picked) 	 */
name|ParticipalModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * A passive auxiliary of a clause is a non-main verb of the clause which 	 * contains the passive information. Example : Kennedy has been killed 	 * Result : auxpass(killed, been) Result : aux(killed,has) 	 */
name|PassiveAuxiliary
argument_list|(
name|GrammaticalRelationCategory
operator|.
name|Auxiliary
argument_list|)
operator|,
comment|/** 	 * A clausal passive subject is a clausal syntactic subject of a passive 	 * clause. In the example below,&quot;that she lied&quot; is the subject. 	 * Example : That she lied was suspected by everyone Result : 	 * csubjpass(suspected, lied) 	 */
name|PassiveClausalSubject
argument_list|(
name|ClausalSubject
argument_list|)
operator|,
comment|/** 	 * A passive nominal subject is a noun phrase which is the syntactic subject 	 * of a passive clause. Example : Dole was defeated by Clinton Result : 	 * nsubjpass(defeated, Dole) 	 */
name|PassiveNominalSubject
argument_list|(
name|NominalSubject
argument_list|)
operator|,
comment|/** 	 * The phrasal verb particle relation identifies a phrasal verb, and holds 	 * between the verb and its particle. Example : They shut down the station 	 * Result : prt(shut, down) 	 */
name|PhrasalVerbParticle
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * The possession modifier relation holds between the head of an NP and its 	 * possessive determiner, or a genitive&#39;s complement. Example : their 	 * offices Result : poss(offices, their) 	 */
name|PossessionModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * The possessive modifier relation appears between the head of an NP and 	 * the genitive 's. Example : Bill's clothes Result : possessive(John, 's) 	 */
name|PossessiveModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * A preconjunct is the relation between the head of an NP and a word that 	 * appears at the beginning bracketing a conjunction (and puts emphasis on 	 * it), such as&quot;either&quot;,&quot;both&quot;,&quot;neither&quot;). 	 * Example : Both the boys and the girls are here Result : preconj (boys, 	 * both) 	 */
name|Preconjunct
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * A predeterminer is the relation between the head of an NP and a word that 	 * precedes and modifies the meaning of the NP determiner. Example : All the 	 * boys are here Result : predet(boys, all) 	 */
name|Predeterminer
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * A prepositional modifier of a verb, adjective, or noun is any 	 * prepositional phrase that serves to modify the meaning of the verb, 	 * adjective, noun, or even another prepositon. In the collapsed 	 * representation, this is used only for prepositions with NP complements. 	 * Example : I saw a cat in a hat Result : prep(cat, in) 	 */
name|PrepositionalModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * In the collapsed representation, a prepositional clausal modifier of a 	 * verb, adjective, or noun is a clause introduced by a preposition which 	 * serves to modify the meaning of the verb, adjective, or noun. Example : 	 * He purchased it without paying a premium Result : 	 * prepc_without(purchased, paying) 	 */
name|PrepositionalClausalModifier
argument_list|(
name|PrepositionalModifier
argument_list|)
operator|,
comment|/** 	 * This is used when the complement of a preposition is a clause or 	 * prepositional phrase (or occasionally, an adverbial phrase). The 	 * prepositional complement of a preposition is the head of a clause 	 * following the preposition, or the preposition head of the following PP. 	 * Example : We have no information on whether users are at risk Result : 	 * pcomp(on, are) 	 */
name|PrepositionalComplement
argument_list|(
name|Complement
argument_list|)
operator|,
comment|/** 	 * This is used for any piece of punctuation in a clause, if punctuation is 	 * being retained in the typed dependencies. By default, punctuation is not 	 * retained in the output. Example : Go home! Result : punct(Go, !) 	 */
name|Punctuation
argument_list|(
name|Dependent
argument_list|)
operator|,
comment|/** 	 * A purpose clause modifier of a VP is a clause headed by&quot;(in order) 	 * to&quot; specifying a purpose. At present the system only recognizes ones 	 * that have&quot;in order to&quot; as otherwise the system is unable to 	 * distinguish from the surface representations between these and open 	 * clausal complements (xcomp). It can also recognize fronted&quot;to&quot; 	 * purpose clauses in sentences. Example : He talked to him in order to 	 * secure the account Result : purpcl(talked, secure) 	 */
name|PurposeClauseModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * A quantifier modifier is an element modifying the head of a QP 	 * constituent. (These are modifiers in complex numeric quantifiers, not 	 * other types of&quot;quantification&quot;. Quantifiers like 	 *&quot;all&quot; become det.) Example : About 200 people came to the party 	 * Result : quantmod(200, About) 	 */
name|QuantifierModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * A referent of the head of an NP is the relative word introducing the 	 * relative clause modifying the NP. Example : I saw the book which you 	 * bought Result : ref (book, which) 	 */
name|Referent
argument_list|(
name|Dependent
argument_list|)
operator|,
comment|/** 	 * A relative of a relative clause is the head word of the WH-phrase 	 * introducing it. This analysis is used only for relative words which are 	 * not the subject of the relative clause. Relative words which act as the 	 * subject of a relative clause are analyzed as a nsubj. I saw the man whose 	 * wife you love. Result : rel (love, wife) 	 */
name|Relative
argument_list|(
name|Complement
argument_list|)
operator|,
comment|/** 	 * A relative clause modifier of an NP is a relative clause modifying the 	 * NP. The relation points from the head noun of the NP to the head of the 	 * relative clause, normally a verb. Example : I saw the man you love Result 	 * : rcmod(man, love) 	 */
name|RelativeClauseModifier
argument_list|(
name|Modifier
argument_list|)
operator|,
comment|/** 	 * The root grammatical relation points to the root of the sentence. A fake 	 * node&quot;ROOT&quot; is used as the governor. The ROOT node is indexed 	 * with&quot;0&quot;, since the indexation of real words in the sentence 	 * starts at 1. Example : I love French fries. Result : root(ROOT, love) 	 */
name|Root
argument_list|(
name|GrammaticalRelationCategory
operator|.
name|DependencyLabel
argument_list|)
operator|,
comment|/** 	 * A temporal modifier (of a VP, NP, or an ADJP is a bare noun phrase 	 * constituent that serves to modify the meaning of the constituent by 	 * specifying a time. (Other temporal modifiers are prepositional phrases 	 * and are introduced as prep.) Example : Last night, I swam in the pool 	 * Result : tmod(swam, night) 	 */
name|TemporalModifier
argument_list|(
name|NounPhraseAsAdverbialModifier
argument_list|)
enum|;
end_enum

begin_comment
comment|/** 	 * The top level category which this grammatical relation belongs to. 	 */
end_comment

begin_decl_stmt
specifier|private
name|GrammaticalRelationCategory
name|category
decl_stmt|;
end_decl_stmt

begin_comment
comment|/** 	 * The parents of this grammatical relation. 	 */
end_comment

begin_decl_stmt
specifier|private
name|Set
argument_list|<
name|GrammaticalRelation
argument_list|>
name|parents
decl_stmt|;
end_decl_stmt

begin_expr_stmt
name|GrammaticalRelation
argument_list|(
name|GrammaticalRelationCategory
name|category
argument_list|)
block|{
name|this
argument_list|(
name|category
argument_list|,
literal|null
argument_list|)
block|; 	}
name|GrammaticalRelation
argument_list|(
name|GrammaticalRelation
name|parent
argument_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
name|parent
argument_list|)
block|; 	}
name|GrammaticalRelation
argument_list|(
name|GrammaticalRelationCategory
name|category
argument_list|,
name|GrammaticalRelation
name|parent
argument_list|)
block|{
if|if
condition|(
name|parent
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|parents
operator|=
operator|new
name|HashSet
argument_list|<
name|GrammaticalRelation
argument_list|>
argument_list|()
expr_stmt|;
name|addParents
argument_list|(
name|parent
argument_list|)
expr_stmt|;
block|}
end_expr_stmt

begin_if
if|if
condition|(
name|category
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|category
operator|=
name|category
expr_stmt|;
block|}
end_if

begin_function
unit|}  	public
name|GrammaticalRelationCategory
name|getCategory
parameter_list|()
block|{
return|return
name|this
operator|.
name|category
return|;
block|}
end_function

begin_function
specifier|public
name|Collection
argument_list|<
name|GrammaticalRelation
argument_list|>
name|getParents
parameter_list|()
block|{
return|return
name|this
operator|.
name|parents
return|;
block|}
end_function

begin_function
specifier|private
name|void
name|addParents
parameter_list|(
name|GrammaticalRelation
name|parent
parameter_list|)
block|{
name|this
operator|.
name|parents
operator|.
name|add
argument_list|(
name|parent
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|GrammaticalRelation
argument_list|>
name|grandParents
init|=
name|parent
operator|.
name|getParents
argument_list|()
decl_stmt|;
if|if
condition|(
name|grandParents
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|category
operator|=
name|parent
operator|.
name|getCategory
argument_list|()
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|GrammaticalRelation
name|grandParent
range|:
name|grandParents
control|)
block|{
name|addParents
argument_list|(
name|grandParent
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_function

unit|}
end_unit

