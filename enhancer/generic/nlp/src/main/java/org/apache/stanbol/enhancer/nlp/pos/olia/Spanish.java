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
operator|.
name|olia
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
name|tag
operator|.
name|TagSet
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
name|Pos
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
name|Spanish
block|{
specifier|private
name|Spanish
parameter_list|()
block|{}
comment|/**      * The PAROLE TagSet for Spanish. This is mainly defined based on this      *<a herf="http://www.lsi.upc.edu/~nlp/SVMTool/parole.html">description</a>      * as the Ontology mainly defines REGEX tag matchings that are not very      * helpful for fast tag lookup needed for processing POS tag results.       */
specifier|public
specifier|static
specifier|final
name|TagSet
argument_list|<
name|PosTag
argument_list|>
name|PAROLE
init|=
operator|new
name|TagSet
argument_list|<
name|PosTag
argument_list|>
argument_list|(
literal|"PAROLE Spanish"
argument_list|,
literal|"es"
argument_list|)
decl_stmt|;
static|static
block|{
comment|//TODO: define constants for annotation model and linking model
name|PAROLE
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"olia.annotationModel"
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://purl.org/olia/parole_es_cat.owl"
argument_list|)
argument_list|)
expr_stmt|;
comment|// NO linking model
comment|//        PAROLE.getProperties().put("olia.linkingModel",
comment|//            new UriRef("http://purl.org/olia/???"));
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"AO"
argument_list|,
name|LexicalCategory
operator|.
name|Adjective
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"AQ"
argument_list|,
name|Pos
operator|.
name|QualifierAdjective
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"CC"
argument_list|,
name|Pos
operator|.
name|CoordinatingConjunction
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"CS"
argument_list|,
name|Pos
operator|.
name|SubordinatingConjunction
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"DA"
argument_list|,
name|Pos
operator|.
name|Article
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"DD"
argument_list|,
name|Pos
operator|.
name|DemonstrativeDeterminer
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"DE"
argument_list|,
name|Pos
operator|.
name|ExclamatoryDeterminer
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"DI"
argument_list|,
name|Pos
operator|.
name|IndefiniteDeterminer
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"DN"
argument_list|,
name|Pos
operator|.
name|Numeral
argument_list|,
name|Pos
operator|.
name|Determiner
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"DP"
argument_list|,
name|Pos
operator|.
name|PossessiveDeterminer
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"DT"
argument_list|,
name|Pos
operator|.
name|InterrogativeDeterminer
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Faa"
argument_list|,
name|LexicalCategory
operator|.
name|Punctuation
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fat"
argument_list|,
name|Pos
operator|.
name|ExclamativePoint
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fc"
argument_list|,
name|Pos
operator|.
name|Comma
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fd"
argument_list|,
name|Pos
operator|.
name|Colon
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fe"
argument_list|,
name|Pos
operator|.
name|Quote
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fg"
argument_list|,
name|Pos
operator|.
name|Hyphen
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fh"
argument_list|,
name|Pos
operator|.
name|Slash
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fia"
argument_list|,
name|Pos
operator|.
name|InvertedQuestionMark
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fit"
argument_list|,
name|Pos
operator|.
name|QuestionMark
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fp"
argument_list|,
name|Pos
operator|.
name|Point
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fpa"
argument_list|,
name|Pos
operator|.
name|OpenParenthesis
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fpt"
argument_list|,
name|Pos
operator|.
name|CloseParenthesis
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fs"
argument_list|,
name|Pos
operator|.
name|SuspensionPoints
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fx"
argument_list|,
name|Pos
operator|.
name|SemiColon
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Fz"
argument_list|,
name|LexicalCategory
operator|.
name|Punctuation
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"I"
argument_list|,
name|LexicalCategory
operator|.
name|Interjection
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"NC"
argument_list|,
name|Pos
operator|.
name|CommonNoun
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"NP"
argument_list|,
name|Pos
operator|.
name|ProperNoun
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"P0"
argument_list|,
name|Pos
operator|.
name|Pronoun
argument_list|)
argument_list|)
expr_stmt|;
comment|//TODO: CliticPronoun is missing
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PD"
argument_list|,
name|Pos
operator|.
name|DemonstrativePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PE"
argument_list|,
name|Pos
operator|.
name|ExclamatoryPronoun
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PI"
argument_list|,
name|Pos
operator|.
name|IndefinitePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PN"
argument_list|,
name|Pos
operator|.
name|Pronoun
argument_list|)
argument_list|)
expr_stmt|;
comment|//TODO: NumeralPronoun is missing
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PP"
argument_list|,
name|Pos
operator|.
name|PersonalPronoun
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PR"
argument_list|,
name|Pos
operator|.
name|RelativePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PT"
argument_list|,
name|Pos
operator|.
name|InterrogativePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PX"
argument_list|,
name|Pos
operator|.
name|PossessivePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"RG"
argument_list|,
name|LexicalCategory
operator|.
name|Adverb
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"RN"
argument_list|,
name|Pos
operator|.
name|NegativeAdverb
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"SP"
argument_list|,
name|Pos
operator|.
name|Preposition
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VAG"
argument_list|,
name|Pos
operator|.
name|StrictAuxiliaryVerb
argument_list|,
name|Pos
operator|.
name|Gerund
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VAI"
argument_list|,
name|Pos
operator|.
name|StrictAuxiliaryVerb
argument_list|,
name|Pos
operator|.
name|IndicativeVerb
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VAM"
argument_list|,
name|Pos
operator|.
name|StrictAuxiliaryVerb
argument_list|,
name|Pos
operator|.
name|ImperativeVerb
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VAN"
argument_list|,
name|Pos
operator|.
name|StrictAuxiliaryVerb
argument_list|,
name|Pos
operator|.
name|Infinitive
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VAP"
argument_list|,
name|Pos
operator|.
name|StrictAuxiliaryVerb
argument_list|,
name|Pos
operator|.
name|Participle
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VAS"
argument_list|,
name|Pos
operator|.
name|StrictAuxiliaryVerb
argument_list|,
name|Pos
operator|.
name|SubjunctiveVerb
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VMG"
argument_list|,
name|Pos
operator|.
name|MainVerb
argument_list|,
name|Pos
operator|.
name|Gerund
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VMI"
argument_list|,
name|Pos
operator|.
name|MainVerb
argument_list|,
name|Pos
operator|.
name|IndicativeVerb
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VMM"
argument_list|,
name|Pos
operator|.
name|MainVerb
argument_list|,
name|Pos
operator|.
name|ImperativeVerb
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VMN"
argument_list|,
name|Pos
operator|.
name|MainVerb
argument_list|,
name|Pos
operator|.
name|Infinitive
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VMP"
argument_list|,
name|Pos
operator|.
name|MainVerb
argument_list|,
name|Pos
operator|.
name|Participle
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VMS"
argument_list|,
name|Pos
operator|.
name|MainVerb
argument_list|,
name|Pos
operator|.
name|SubjunctiveVerb
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VSG"
argument_list|,
name|Pos
operator|.
name|ModalVerb
argument_list|,
name|Pos
operator|.
name|Gerund
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VSI"
argument_list|,
name|Pos
operator|.
name|ModalVerb
argument_list|,
name|Pos
operator|.
name|IndicativeVerb
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VSM"
argument_list|,
name|Pos
operator|.
name|ModalVerb
argument_list|,
name|Pos
operator|.
name|ImperativeVerb
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VSN"
argument_list|,
name|Pos
operator|.
name|ModalVerb
argument_list|,
name|Pos
operator|.
name|Infinitive
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VSP"
argument_list|,
name|Pos
operator|.
name|ModalVerb
argument_list|,
name|Pos
operator|.
name|Participle
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VSS"
argument_list|,
name|Pos
operator|.
name|ModalVerb
argument_list|,
name|Pos
operator|.
name|SubjunctiveVerb
argument_list|)
argument_list|)
expr_stmt|;
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"W"
argument_list|,
name|Pos
operator|.
name|Date
argument_list|)
argument_list|)
expr_stmt|;
comment|//date times
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"X"
argument_list|)
argument_list|)
expr_stmt|;
comment|//unknown
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Y"
argument_list|,
name|Pos
operator|.
name|Abbreviation
argument_list|)
argument_list|)
expr_stmt|;
comment|//abbreviation
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Z"
argument_list|,
name|Pos
operator|.
name|Image
argument_list|)
argument_list|)
expr_stmt|;
comment|//Figures
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Zm"
argument_list|,
name|Pos
operator|.
name|Symbol
argument_list|)
argument_list|)
expr_stmt|;
comment|//currency
name|PAROLE
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"Zp"
argument_list|,
name|Pos
operator|.
name|Symbol
argument_list|)
argument_list|)
expr_stmt|;
comment|//percentage
block|}
block|}
end_class

end_unit
