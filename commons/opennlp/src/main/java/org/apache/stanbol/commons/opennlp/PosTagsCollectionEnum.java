begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|opennlp
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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|stanbol
operator|.
name|commons
operator|.
name|opennlp
operator|.
name|TextAnalyzer
operator|.
name|AnalysedText
operator|.
name|Chunk
import|;
end_import

begin_comment
comment|/**  * Enumeration with pre-configured sets of POS tags for finding nouns, verbs ...  * in different languages  * @author Rupert Westenthaler  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|PosTagsCollectionEnum
block|{
comment|/**      * Nouns related POS types for English based on the       *<a href="http://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html">      * Penn Treebank</a> tag set      */
name|EN_NOUN
argument_list|(
literal|"en"
argument_list|,
name|PosTypeCollectionType
operator|.
name|NOUN
argument_list|,
literal|"NN"
argument_list|,
literal|"NNP"
argument_list|,
literal|"NNPS"
argument_list|,
literal|"NNS"
argument_list|,
literal|"FW"
argument_list|,
literal|"CD"
argument_list|)
block|,
comment|/**      * Verb related POS types for English based on the       *<a href="http://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html">      * Penn Treebank</a> tag set      */
name|EN_VERB
argument_list|(
literal|"en"
argument_list|,
name|PosTypeCollectionType
operator|.
name|VERB
argument_list|,
literal|"VB"
argument_list|,
literal|"VBD"
argument_list|,
literal|"VBG"
argument_list|,
literal|"VBN"
argument_list|,
literal|"VBP"
argument_list|,
literal|"VBZ"
argument_list|)
block|,
comment|/**      * POS types one needs typically to follow to build {@link Chunk}s over       * Nouns (e.g. "University_NN of_IN Otago_NNP" or "Geneva_NNP ,_, Ohio_NNP").      * For English and based on the       *<a href="http://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html">      * Penn Treebank</a> tag set      */
name|EN_FOLLOW
argument_list|(
literal|"en"
argument_list|,
name|PosTypeCollectionType
operator|.
name|FOLLOW
argument_list|,
literal|"#"
argument_list|,
literal|"$"
argument_list|,
literal|" "
argument_list|,
literal|"("
argument_list|,
literal|")"
argument_list|,
literal|","
argument_list|,
literal|"."
argument_list|,
literal|":"
argument_list|,
literal|"``"
argument_list|,
literal|"POS"
argument_list|,
literal|"IN"
argument_list|)
block|,
comment|/**      * Noun related POS types for German based on the       *<a href="http://www.ims.uni-stuttgart.de/projekte/corplex/TagSets/stts-table.html">      * STTS Tag Set</a>       */
name|DE_NOUN
argument_list|(
literal|"de"
argument_list|,
name|PosTypeCollectionType
operator|.
name|NOUN
argument_list|,
literal|"NN"
argument_list|,
literal|"NE"
argument_list|,
literal|"FM"
argument_list|,
literal|"XY"
argument_list|)
block|,
comment|/**      * Verb related POS types for German based on the       *<a href="http://www.ims.uni-stuttgart.de/projekte/corplex/TagSets/stts-table.html">      * STTS Tag Set</a>       */
name|DE_VERB
argument_list|(
literal|"de"
argument_list|,
name|PosTypeCollectionType
operator|.
name|VERB
argument_list|,
literal|"VVFIN"
argument_list|,
literal|"VVIMP"
argument_list|,
literal|"VVINF"
argument_list|,
literal|"VVIZU"
argument_list|,
literal|"VVPP"
argument_list|,
literal|"VAFIN"
argument_list|,
literal|"VAIMP"
argument_list|,
literal|"VAINF"
argument_list|,
literal|"VAPP"
argument_list|,
literal|"VMFIN"
argument_list|,
literal|"VMINF"
argument_list|,
literal|"VMPP"
argument_list|)
block|,
comment|/**      * POS types one needs typically to follow to build {@link Chunk}s over       * Nouns (e.g. "University_NN of_IN Otago_NNP" or "Geneva_NNP ,_, Ohio_NNP").      * For German based on the       *<a href="http://www.ims.uni-stuttgart.de/projekte/corplex/TagSets/stts-table.html">      * STTS Tag Set</a>       */
name|DE_FOLLOW
argument_list|(
literal|"de"
argument_list|,
name|PosTypeCollectionType
operator|.
name|FOLLOW
argument_list|,
literal|"$"
argument_list|,
literal|"$."
argument_list|,
literal|"$("
argument_list|)
block|,
comment|/**      * POS types representing Nouns for Danish based on the PAROLE Tagset as      * described by<a href="http://korpus.dsl.dk/paroledoc_en.pdf">this paper</a>      *<p>      * TODO: Someone who speaks Danish should check this List      * NOTES:<ul>      *<li> included also "XX" and "XR" because the examples in the      * training data for OpenNLP seam to be good candidates for processing      *<li> "AC" is included because it refers to numbers      *</ul>      */
name|DA_NOUN
argument_list|(
literal|"da"
argument_list|,
name|PosTypeCollectionType
operator|.
name|NOUN
argument_list|,
literal|"N"
argument_list|,
literal|"NP"
argument_list|,
literal|"NC"
argument_list|,
literal|"AC"
argument_list|,
literal|"XX"
argument_list|,
literal|"XR"
argument_list|)
block|,
comment|/**      * POS types representing Verbs for Danish based on the PAROLE Tagset as      * described by<a href="http://korpus.dsl.dk/paroledoc_en.pdf">this paper</a>      *<p>      * TODO: Someone who speaks Danish should check this List      */
name|DA_VERB
argument_list|(
literal|"da"
argument_list|,
name|PosTypeCollectionType
operator|.
name|VERB
argument_list|,
literal|"V"
argument_list|,
literal|"VA"
argument_list|,
literal|"VE"
argument_list|)
block|,
comment|/**      * POS types that are followd to extend chunks for Danish based on the PAROLE Tagset as      * described by<a href="http://korpus.dsl.dk/paroledoc_en.pdf">this paper</a>      *<p>      * TODO: Someone who speaks Danish should check this List<p>      * NOTES:<ul>      *<li> included also "U" for unknown, because most of the examples in the      * training data for OpenNLP seam to be good candidates for following      *<li> "XA" is included because the examples include units of       *<li> "XP" stands for punctuation and such      *</ul>      */
name|DA_FOLLOW
argument_list|(
literal|"da"
argument_list|,
name|PosTypeCollectionType
operator|.
name|FOLLOW
argument_list|,
literal|"XP"
argument_list|,
literal|"XA"
argument_list|,
literal|"SP"
argument_list|,
literal|"CS"
argument_list|,
literal|"CC"
argument_list|,
literal|"U"
argument_list|)
block|,
comment|/**      * POS types for Nouns based on the      *<a href="http://beta.visl.sdu.dk/visl/pt/symbolset-floresta.html">PALAVRAS tag set</a>      * for Portuguese.<p>      * TODO: Someone who speaks this language should check this List<p>      * NOTES: Currently this includes nouns, proper nouns and numbers.      * In addition I added "vp". "vp" is not part of the POS tag set       * documentation but in the training set there is a single occurrence       * therefore the POS tagger sometimes do tag words with this tag.      */
name|PT_NOUN
argument_list|(
literal|"pt"
argument_list|,
name|PosTypeCollectionType
operator|.
name|NOUN
argument_list|,
literal|"n"
argument_list|,
literal|"num"
argument_list|,
literal|"prop"
argument_list|,
literal|"vp"
argument_list|)
block|,
comment|/**      * POS types for Verbs based on the      *<a href="http://beta.visl.sdu.dk/visl/pt/symbolset-floresta.html">PALAVRAS tag set</a>      * for Portuguese.<p>      * TODO: Someone who speaks this language should check this List<p>      */
name|PT_VERB
argument_list|(
literal|"pt"
argument_list|,
name|PosTypeCollectionType
operator|.
name|VERB
argument_list|,
literal|"v-pcp"
argument_list|,
literal|"v-fin"
argument_list|,
literal|"v-inf"
argument_list|,
literal|"v-ger"
argument_list|)
block|,
comment|/**      * POS types followed to build Chunks based on the      *<a href="http://beta.visl.sdu.dk/visl/pt/symbolset-floresta.html">PALAVRAS tag set</a>      * for Portuguese.<p>      * TODO: Someone who speaks this language should check this List<p>      * NOTES: Currently this pubctations and prepositions.       */
name|PT_FOLLOW
argument_list|(
literal|"pt"
argument_list|,
name|PosTypeCollectionType
operator|.
name|FOLLOW
argument_list|,
literal|"punc"
argument_list|,
literal|"prp"
argument_list|)
block|,
comment|/**      * POS types for Nouns based on the WOTAN tagset for Dutch (as used with       * Mbt).<p>      * TODOO: Someone who speaks this language should checkthis List<p>      * NOTES: This includes now Nouns, Numbers and "others".      */
name|NL_NOUN
argument_list|(
literal|"nl"
argument_list|,
name|PosTypeCollectionType
operator|.
name|NOUN
argument_list|,
literal|"N"
argument_list|,
literal|"Num"
argument_list|,
literal|"Misc"
argument_list|)
block|,
comment|/**      * POS types for Verbs based on the WOTAN tagset for Dutch (as used with       * Mbt).<p>      * The tagger does not distinguish the different forms fo verbs. Therefore      * it is enough so include "V"      */
name|NL_VERB
argument_list|(
literal|"nl"
argument_list|,
name|PosTypeCollectionType
operator|.
name|VERB
argument_list|,
literal|"V"
argument_list|)
block|,
comment|/**      * POS types followed to build Chunks based on the WOTAN tagset for Dutch       * (as used with Mbt).<p>      * NOTES: THis includes only prepositions and punctuations      *       */
name|NL_FOLLOW
argument_list|(
literal|"nl"
argument_list|,
name|PosTypeCollectionType
operator|.
name|FOLLOW
argument_list|,
literal|"Punc"
argument_list|,
literal|"Prep"
argument_list|)
block|,
comment|/**      * POS types for Nouns for Swedish language based on       *<a href="http://w3.msi.vxu.se/users/nivre/research/MAMBAlex.html">      * Lexical categories in MAMBA</a>      * NOTE:<ul>      *<li> This includes all typical noun categories as defined by MAMBA      *<li> Unclassifiable part-of-speech and      *<li> Numerical "RO"      *<li> EN is excluded       *</ul>      */
name|SV_NOUN
argument_list|(
literal|"sv"
argument_list|,
name|PosTypeCollectionType
operator|.
name|NOUN
argument_list|,
literal|"NN"
argument_list|,
literal|"PN"
argument_list|,
literal|"AN"
argument_list|,
literal|"MN"
argument_list|,
literal|"VN"
argument_list|,
literal|"XX"
argument_list|,
literal|"RO"
argument_list|)
block|,
comment|/**      * POS types for Verbs of the Swedish language based on the      *<a href="http://w3.msi.vxu.se/users/nivre/research/MAMBAlex.html">      * Lexical categories in MAMBA</a>      */
name|SV_VERB
argument_list|(
literal|"sv"
argument_list|,
name|PosTypeCollectionType
operator|.
name|VERB
argument_list|,
literal|"MV"
argument_list|,
literal|"AV"
argument_list|,
literal|"BV"
argument_list|,
literal|"FV"
argument_list|,
literal|"GV"
argument_list|,
literal|"HV"
argument_list|,
literal|"KV"
argument_list|,
literal|"QV"
argument_list|,
literal|"SV"
argument_list|,
literal|"VV"
argument_list|,
literal|"WV"
argument_list|)
block|,
comment|/**      * POS types followed to build Chunks based on the TODO      *<p>      * NOTES: this includes  prepositions, Part of idiom, Infinitive marker      *  as well as all kinds of punctuations      */
name|SV_FOLLOW
argument_list|(
literal|"sv"
argument_list|,
name|PosTypeCollectionType
operator|.
name|FOLLOW
argument_list|,
literal|"PR"
argument_list|,
literal|"ID"
argument_list|,
literal|"IM"
argument_list|,
literal|"I?"
argument_list|,
literal|"IC"
argument_list|,
literal|"IG"
argument_list|,
literal|"IK"
argument_list|,
literal|"IP"
argument_list|,
literal|"IQ"
argument_list|,
literal|"IR"
argument_list|,
literal|"IS"
argument_list|,
literal|"IT"
argument_list|,
literal|"IU"
argument_list|)
block|;
name|Set
argument_list|<
name|String
argument_list|>
name|tags
decl_stmt|;
specifier|private
name|String
name|language
decl_stmt|;
specifier|private
name|PosTypeCollectionType
name|type
decl_stmt|;
specifier|private
name|PosTagsCollectionEnum
parameter_list|(
name|String
name|lang
parameter_list|,
name|PosTypeCollectionType
name|type
parameter_list|,
name|String
modifier|...
name|tags
parameter_list|)
block|{
name|this
operator|.
name|tags
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|tags
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|language
operator|=
name|lang
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
comment|/**      * Getter for the set of POS tags      * @return the tags      */
specifier|public
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|getTags
parameter_list|()
block|{
return|return
name|tags
return|;
block|}
comment|/**      * @return the language      */
specifier|public
specifier|final
name|String
name|getLanguage
parameter_list|()
block|{
return|return
name|language
return|;
block|}
comment|/**      * @return the type      */
specifier|public
specifier|final
name|PosTypeCollectionType
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|CollectionType
argument_list|,
name|PosTagsCollectionEnum
argument_list|>
name|tagCollections
decl_stmt|;
static|static
block|{
name|Map
argument_list|<
name|CollectionType
argument_list|,
name|PosTagsCollectionEnum
argument_list|>
name|tcm
init|=
operator|new
name|HashMap
argument_list|<
name|CollectionType
argument_list|,
name|PosTagsCollectionEnum
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|PosTagsCollectionEnum
name|collection
range|:
name|PosTagsCollectionEnum
operator|.
name|values
argument_list|()
control|)
block|{
name|CollectionType
name|type
init|=
operator|new
name|CollectionType
argument_list|(
name|collection
operator|.
name|getLanguage
argument_list|()
argument_list|,
name|collection
operator|.
name|getType
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|tcm
operator|.
name|put
argument_list|(
name|type
argument_list|,
name|collection
argument_list|)
operator|!=
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"The PosTagsCollectionEnum contains"
operator|+
literal|"Multiple POS tags collections for the Language '"
operator|+
name|collection
operator|.
name|getLanguage
argument_list|()
operator|+
literal|"' and POS tag type '"
operator|+
name|collection
operator|.
name|getType
argument_list|()
operator|+
literal|"'!"
argument_list|)
throw|;
block|}
block|}
name|tagCollections
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|tcm
argument_list|)
expr_stmt|;
block|}
comment|/**      * Getter for the POS (Part-of-Speech) tag collection for the given language      * and type      * @param lang the language      * @param type the type      * @return the collection or<code>null</code> if no configuration for the      * parsed parameters is available.      */
specifier|public
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|getPosTagCollection
parameter_list|(
name|String
name|lang
parameter_list|,
name|PosTypeCollectionType
name|type
parameter_list|)
block|{
name|PosTagsCollectionEnum
name|collection
init|=
name|tagCollections
operator|.
name|get
argument_list|(
operator|new
name|CollectionType
argument_list|(
name|lang
argument_list|,
name|type
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|collection
operator|==
literal|null
condition|?
literal|null
else|:
name|collection
operator|.
name|getTags
argument_list|()
return|;
block|}
comment|/**      * Internally used as key for a Map that mapps POS tag collection sets       * based on Language and {@link PosTypeCollectionType}      * @author Rupert Westenthaler      *      */
specifier|private
specifier|static
class|class
name|CollectionType
block|{
specifier|protected
name|String
name|lang
decl_stmt|;
specifier|protected
name|PosTypeCollectionType
name|type
decl_stmt|;
specifier|private
name|CollectionType
parameter_list|(
name|String
name|lang
parameter_list|,
name|PosTypeCollectionType
name|type
parameter_list|)
block|{
name|this
operator|.
name|lang
operator|=
name|lang
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|lang
operator|.
name|hashCode
argument_list|()
operator|+
name|type
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|obj
operator|instanceof
name|CollectionType
operator|&&
operator|(
operator|(
name|CollectionType
operator|)
name|obj
operator|)
operator|.
name|lang
operator|.
name|equals
argument_list|(
name|lang
argument_list|)
operator|&&
operator|(
operator|(
name|CollectionType
operator|)
name|obj
operator|)
operator|.
name|type
operator|==
name|type
return|;
block|}
block|}
block|}
end_enum

end_unit
