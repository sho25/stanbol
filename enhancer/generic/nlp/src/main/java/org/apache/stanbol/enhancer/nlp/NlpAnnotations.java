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
package|;
end_package

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
name|AnalysedText
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
name|Chunk
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
name|Token
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
name|annotation
operator|.
name|Annotation
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
name|MorphoFeatures
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
name|ner
operator|.
name|NerTag
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
name|phrase
operator|.
name|PhraseTag
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
comment|/**  * Defines the {@link Annotation} constants typically used by NLP components  */
end_comment

begin_interface
specifier|public
interface|interface
name|NlpAnnotations
block|{
comment|/**      * The POS {@link Annotation} added by POS taggers to {@link Token}s of      * an {@link AnalysedText}.      */
name|Annotation
argument_list|<
name|String
argument_list|,
name|PosTag
argument_list|>
name|POS_ANNOTATION
init|=
operator|new
name|Annotation
argument_list|<
name|String
argument_list|,
name|PosTag
argument_list|>
argument_list|(
literal|"stanbol.enhancer.nlp.pos"
argument_list|,
name|PosTag
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      *       */
name|Annotation
argument_list|<
name|String
argument_list|,
name|NerTag
argument_list|>
name|NER_ANNOTATION
init|=
operator|new
name|Annotation
argument_list|<
name|String
argument_list|,
name|NerTag
argument_list|>
argument_list|(
literal|"stanbol.enhancer.nlp.ner"
argument_list|,
name|NerTag
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * The Phrase {@link Annotation} added by chunker to a group of      * [1..*] {@link Token}s.<p>      * This annotation is typically found on {@link Chunk}s.      */
name|Annotation
argument_list|<
name|String
argument_list|,
name|PhraseTag
argument_list|>
name|PHRASE_ANNOTATION
init|=
operator|new
name|Annotation
argument_list|<
name|String
argument_list|,
name|PhraseTag
argument_list|>
argument_list|(
literal|"stanbol.enhancer.nlp.phrase"
argument_list|,
name|PhraseTag
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * The Sentiment {@link Annotation} added by a sentiment tagger typically      * to single {@link Token}s that do carry a positive or negative sentiment.      */
name|Annotation
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|SENTIMENT_ANNOTATION
init|=
operator|new
name|Annotation
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
argument_list|(
literal|"stanbol.enhancer.nlp.sentiment"
argument_list|,
name|Double
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * {@link Annotation} representing the Morphological analysis of a word.       * Typically used on {@link Token}s.<p>      * The {@link MorphoFeatures} defines at least the Lemma and [1..*] POS tags.      * NOTE that the POS tag information does not assign a Tag to the {@link Token},      * but rather specifies that if the Token is classified by a {@link #POS_ANNOTATION}      * to be of one of the Tags the definitions of this {@link MorphoFeatures} can      * be applied.      */
name|Annotation
argument_list|<
name|String
argument_list|,
name|MorphoFeatures
argument_list|>
name|MORPHO_ANNOTATION
init|=
operator|new
name|Annotation
argument_list|<
name|String
argument_list|,
name|MorphoFeatures
argument_list|>
argument_list|(
literal|"stanbol.enhancer.nlp.morpho"
argument_list|,
name|MorphoFeatures
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/*       * Currently only used as part of MorphoFeatures      */
comment|//    Annotation<String,CaseTag> CASE_ANNOTATION = new Annotation<String,CaseTag>(
comment|//            "stanbol.enhancer.nlp.morpho.case",CaseTag.class);
comment|//
comment|//    Annotation<String,GenderTag> GENDER_ANNOTATION = new Annotation<String,GenderTag>(
comment|//            "stanbol.enhancer.nlp.morpho.gender",GenderTag.class);
comment|//
comment|//    Annotation<String,NumberTag> NUMBER_ANNOTATION = new Annotation<String,NumberTag>(
comment|//            "stanbol.enhancer.nlp.morpho.number",NumberTag.class);
comment|//
comment|//    Annotation<String,PersonTag> PERSON_ANNOTATION = new Annotation<String,PersonTag>(
comment|//            "stanbol.enhancer.nlp.morpho.person",PersonTag.class);
comment|//
comment|//    Annotation<String,TenseTag> TENSE_ANNOTATION = new Annotation<String,TenseTag>(
comment|//            "stanbol.enhancer.nlp.morpho.tense",TenseTag.class);
comment|//
comment|//    Annotation<String,VerbMoodTag> VERB_MOOD_ANNOTATION = new Annotation<String,VerbMoodTag>(
comment|//            "stanbol.enhancer.nlp.morpho.verb-mood",VerbMoodTag.class);
block|}
end_interface

end_unit

