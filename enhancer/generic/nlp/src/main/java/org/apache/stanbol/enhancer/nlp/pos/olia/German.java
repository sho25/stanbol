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

begin_comment
comment|/**  * Defines {@link TagSet}s for the German language.<p>  * TODO: this is currently done manually but it should be able to generate this  * based on the<a herf="http://nlp2rdf.lod2.eu/olia/">OLIA</a> Ontologies  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|German
block|{
specifier|private
name|German
parameter_list|()
block|{}
specifier|public
specifier|static
specifier|final
name|TagSet
argument_list|<
name|PosTag
argument_list|>
name|STTS
init|=
operator|new
name|TagSet
argument_list|<
name|PosTag
argument_list|>
argument_list|(
literal|"STTS"
argument_list|,
literal|"de"
argument_list|)
decl_stmt|;
static|static
block|{
comment|//TODO: define constants for annotation model and linking model
name|STTS
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
literal|"http://purl.org/olia/stts.owl"
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"olia.linkingModel"
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://purl.org/olia/stts-link.rdf"
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"ADJA"
argument_list|,
name|Pos
operator|.
name|AttributiveAdjective
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"ADJD"
argument_list|,
name|Pos
operator|.
name|PredicativeAdjective
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"ADV"
argument_list|,
name|LexicalCategory
operator|.
name|Adverb
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"APPR"
argument_list|,
name|Pos
operator|.
name|Preposition
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"APPRART"
argument_list|,
name|Pos
operator|.
name|FusedPrepArt
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"APPO"
argument_list|,
name|Pos
operator|.
name|Postposition
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"APZR"
argument_list|,
name|Pos
operator|.
name|Circumposition
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"ART"
argument_list|,
name|Pos
operator|.
name|Article
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"CARD"
argument_list|,
name|Pos
operator|.
name|CardinalNumber
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"FM"
argument_list|,
name|Pos
operator|.
name|Foreign
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"ITJ"
argument_list|,
name|LexicalCategory
operator|.
name|Interjection
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"KOUI"
argument_list|,
name|Pos
operator|.
name|SubordinatingConjunction
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"KOUS"
argument_list|,
name|Pos
operator|.
name|SubordinatingConjunctionWithFiniteClause
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"KON"
argument_list|,
name|Pos
operator|.
name|CoordinatingConjunction
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"KOKOM"
argument_list|,
name|Pos
operator|.
name|ComparativeParticle
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"NN"
argument_list|,
name|Pos
operator|.
name|CommonNoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"NE"
argument_list|,
name|Pos
operator|.
name|ProperNoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PDS"
argument_list|,
name|Pos
operator|.
name|DemonstrativePronoun
argument_list|,
name|Pos
operator|.
name|SubstitutivePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PDAT"
argument_list|,
name|Pos
operator|.
name|DemonstrativePronoun
argument_list|,
name|Pos
operator|.
name|AttributivePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PIS"
argument_list|,
name|Pos
operator|.
name|SubstitutivePronoun
argument_list|,
name|Pos
operator|.
name|IndefinitePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PIAT"
argument_list|,
name|Pos
operator|.
name|AttributivePronoun
argument_list|,
name|Pos
operator|.
name|IndefinitePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PIDAT"
argument_list|,
name|Pos
operator|.
name|AttributivePronoun
argument_list|,
name|Pos
operator|.
name|IndefinitePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PPER"
argument_list|,
name|Pos
operator|.
name|PersonalPronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PPOSS"
argument_list|,
name|Pos
operator|.
name|SubstitutivePronoun
argument_list|,
name|Pos
operator|.
name|PossessivePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PPOSAT"
argument_list|,
name|Pos
operator|.
name|AttributivePronoun
argument_list|,
name|Pos
operator|.
name|PossessivePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PRELS"
argument_list|,
name|Pos
operator|.
name|SubstitutivePronoun
argument_list|,
name|Pos
operator|.
name|RelativePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PRELAT"
argument_list|,
name|Pos
operator|.
name|AttributivePronoun
argument_list|,
name|Pos
operator|.
name|RelativePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PRF"
argument_list|,
name|Pos
operator|.
name|ReflexivePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PWS"
argument_list|,
name|Pos
operator|.
name|SubstitutivePronoun
argument_list|,
name|Pos
operator|.
name|InterrogativePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PWAT"
argument_list|,
name|Pos
operator|.
name|AttributivePronoun
argument_list|,
name|Pos
operator|.
name|InterrogativePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PWAV"
argument_list|,
name|LexicalCategory
operator|.
name|Adverb
argument_list|,
name|Pos
operator|.
name|RelativePronoun
argument_list|,
name|Pos
operator|.
name|InterrogativePronoun
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PAV"
argument_list|,
name|Pos
operator|.
name|PronominalAdverb
argument_list|)
argument_list|)
expr_stmt|;
comment|//Tiger-STTS for PAV
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PROAV"
argument_list|,
name|Pos
operator|.
name|PronominalAdverb
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PTKA"
argument_list|,
name|Pos
operator|.
name|AdjectivalParticle
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PTKANT"
argument_list|,
name|Pos
operator|.
name|Particle
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PTKNEG"
argument_list|,
name|Pos
operator|.
name|NegativeParticle
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PTKVZ"
argument_list|,
name|Pos
operator|.
name|VerbalParticle
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PTKZU"
argument_list|,
name|Pos
operator|.
name|Particle
argument_list|)
argument_list|)
expr_stmt|;
comment|//particle "zu"  e.g. "zu [gehen]".
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"TRUNC"
argument_list|,
name|Pos
operator|.
name|Abbreviation
argument_list|)
argument_list|)
expr_stmt|;
comment|//e.g. An- [und Abreise]
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VVIMP"
argument_list|,
name|Pos
operator|.
name|ImperativeVerb
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VVINF"
argument_list|,
name|Pos
operator|.
name|Infinitive
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VVFIN"
argument_list|,
name|Pos
operator|.
name|FiniteVerb
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VVIZU"
argument_list|,
name|Pos
operator|.
name|Infinitive
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VVPP"
argument_list|,
name|Pos
operator|.
name|PastParticiple
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VAFIN"
argument_list|,
name|Pos
operator|.
name|FiniteVerb
argument_list|,
name|Pos
operator|.
name|AuxiliaryVerb
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VAIMP"
argument_list|,
name|Pos
operator|.
name|AuxiliaryVerb
argument_list|,
name|Pos
operator|.
name|ImperativeVerb
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VAINF"
argument_list|,
name|Pos
operator|.
name|AuxiliaryVerb
argument_list|,
name|Pos
operator|.
name|Infinitive
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VAPP"
argument_list|,
name|Pos
operator|.
name|PastParticiple
argument_list|,
name|Pos
operator|.
name|AuxiliaryVerb
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VMFIN"
argument_list|,
name|Pos
operator|.
name|FiniteVerb
argument_list|,
name|Pos
operator|.
name|ModalVerb
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VMINF"
argument_list|,
name|Pos
operator|.
name|Infinitive
argument_list|,
name|Pos
operator|.
name|ModalVerb
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"VMPP"
argument_list|,
name|Pos
operator|.
name|PastParticiple
argument_list|,
name|Pos
operator|.
name|ModalVerb
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"XY"
argument_list|)
argument_list|)
expr_stmt|;
comment|//non words (e.g. H20, 3:7 ...)
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"$."
argument_list|,
name|Pos
operator|.
name|Point
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"$,"
argument_list|,
name|Pos
operator|.
name|Comma
argument_list|)
argument_list|)
expr_stmt|;
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"$("
argument_list|,
name|Pos
operator|.
name|ParentheticalPunctuation
argument_list|)
argument_list|)
expr_stmt|;
comment|//Normal nouns in named entities (not in stts 1999)
name|STTS
operator|.
name|addTag
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"NNE"
argument_list|,
name|Pos
operator|.
name|ProperNoun
argument_list|)
argument_list|)
expr_stmt|;
comment|//TODO maybe map to common non
block|}
block|}
end_class

end_unit

