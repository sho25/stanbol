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
name|json
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|OntologicalClasses
operator|.
name|DBPEDIA_ORGANISATION
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|EnumSet
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
name|LinkedHashMap
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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
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
name|contentitem
operator|.
name|inmemory
operator|.
name|InMemoryContentItemFactory
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
name|NlpAnnotations
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
name|AnalysedTextFactory
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
name|Sentence
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
name|Span
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
name|Span
operator|.
name|SpanTypeEnum
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
name|model
operator|.
name|annotation
operator|.
name|Value
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
name|Case
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
name|CaseTag
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
name|Definitness
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
name|Gender
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
name|GenderTag
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
name|morpho
operator|.
name|NumberFeature
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
name|NumberTag
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
name|Person
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
name|TenseTag
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
name|VerbMood
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
name|VerbMoodTag
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
name|servicesapi
operator|.
name|Blob
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
name|servicesapi
operator|.
name|ContentItem
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
name|servicesapi
operator|.
name|ContentItemFactory
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
name|servicesapi
operator|.
name|helper
operator|.
name|ContentItemHelper
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
name|servicesapi
operator|.
name|impl
operator|.
name|StringSource
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|BeforeClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
specifier|public
class|class
name|AnalyzedTextSerializerAndParserTest
block|{
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|AnalyzedTextSerializerAndParserTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|text
init|=
literal|"The Stanbol enhancer can detect famous "
operator|+
literal|"cities such as Paris and people such as Bob Marley."
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Annotation
argument_list|<
name|Number
argument_list|>
name|testAnnotation
init|=
operator|new
name|Annotation
argument_list|<
name|Number
argument_list|>
argument_list|(
literal|"test"
argument_list|,
name|Number
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/* -----      * Test data creates within the BeforeClass      * -----      */
comment|/**      * AnalysedText instance filled in {@link #setup()} with test dats      */
specifier|private
specifier|static
name|AnalysedText
name|analysedTextWithData
decl_stmt|;
specifier|private
specifier|static
name|LinkedHashMap
argument_list|<
name|Sentence
argument_list|,
name|String
argument_list|>
name|expectedSentences
init|=
operator|new
name|LinkedHashMap
argument_list|<
name|Sentence
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|LinkedHashMap
argument_list|<
name|Chunk
argument_list|,
name|String
argument_list|>
name|expectedChunks
init|=
operator|new
name|LinkedHashMap
argument_list|<
name|Chunk
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|LinkedHashMap
argument_list|<
name|Token
argument_list|,
name|String
argument_list|>
name|expectedTokens
init|=
operator|new
name|LinkedHashMap
argument_list|<
name|Token
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|/* -----      * Test data creates before every single test      * -----      */
comment|/**      * Empty AnalysedText instance created before each test      */
specifier|private
specifier|static
name|AnalysedText
name|at
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|ContentItemFactory
name|ciFactory
init|=
name|InMemoryContentItemFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|AnalysedTextFactory
name|atFactory
init|=
name|AnalysedTextFactory
operator|.
name|getDefaultInstance
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|ContentItem
name|ci
decl_stmt|;
specifier|private
specifier|static
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|Blob
argument_list|>
name|textBlob
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
specifier|final
name|void
name|setup
parameter_list|()
throws|throws
name|IOException
block|{
name|ci
operator|=
name|ciFactory
operator|.
name|createContentItem
argument_list|(
operator|new
name|StringSource
argument_list|(
name|text
argument_list|)
argument_list|)
expr_stmt|;
name|textBlob
operator|=
name|ContentItemHelper
operator|.
name|getBlob
argument_list|(
name|ci
argument_list|,
name|Collections
operator|.
name|singleton
argument_list|(
literal|"text/plain"
argument_list|)
argument_list|)
expr_stmt|;
name|analysedTextWithData
operator|=
name|createAnalysedText
argument_list|()
expr_stmt|;
name|int
name|sentence
init|=
name|text
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
operator|+
literal|1
decl_stmt|;
name|Sentence
name|sent1
init|=
name|analysedTextWithData
operator|.
name|addSentence
argument_list|(
literal|0
argument_list|,
name|sentence
argument_list|)
decl_stmt|;
name|expectedSentences
operator|.
name|put
argument_list|(
name|sent1
argument_list|,
literal|"The Stanbol enhancer can detect famous "
operator|+
literal|"cities such as Paris and people such as Bob Marley."
argument_list|)
expr_stmt|;
name|Token
name|the
init|=
name|sent1
operator|.
name|addToken
argument_list|(
literal|0
argument_list|,
literal|3
argument_list|)
decl_stmt|;
name|expectedTokens
operator|.
name|put
argument_list|(
name|the
argument_list|,
literal|"The"
argument_list|)
expr_stmt|;
name|the
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|POS_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PREP"
argument_list|,
name|Pos
operator|.
name|Preposition
argument_list|)
argument_list|,
literal|0.85
argument_list|)
argument_list|)
expr_stmt|;
name|Token
name|stanbol
init|=
name|sent1
operator|.
name|addToken
argument_list|(
literal|4
argument_list|,
literal|11
argument_list|)
decl_stmt|;
name|expectedTokens
operator|.
name|put
argument_list|(
name|stanbol
argument_list|,
literal|"Stanbol"
argument_list|)
expr_stmt|;
name|stanbol
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|POS_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PN"
argument_list|,
name|Pos
operator|.
name|ProperNoun
argument_list|)
argument_list|,
literal|0.95
argument_list|)
argument_list|)
expr_stmt|;
name|stanbol
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|SENTIMENT_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
literal|0.5
argument_list|)
argument_list|)
expr_stmt|;
comment|//use index to create Tokens
name|int
name|enhancerStart
init|=
name|sent1
operator|.
name|getSpan
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|"enhancer"
argument_list|)
decl_stmt|;
name|Token
name|enhancer
init|=
name|sent1
operator|.
name|addToken
argument_list|(
name|enhancerStart
argument_list|,
name|enhancerStart
operator|+
literal|"enhancer"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|expectedTokens
operator|.
name|put
argument_list|(
name|enhancer
argument_list|,
literal|"enhancer"
argument_list|)
expr_stmt|;
name|enhancer
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|POS_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PN"
argument_list|,
name|Pos
operator|.
name|ProperNoun
argument_list|)
argument_list|,
literal|0.95
argument_list|)
argument_list|)
expr_stmt|;
name|enhancer
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|POS_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"N"
argument_list|,
name|LexicalCategory
operator|.
name|Noun
argument_list|)
argument_list|,
literal|0.87
argument_list|)
argument_list|)
expr_stmt|;
name|MorphoFeatures
name|morpho
init|=
operator|new
name|MorphoFeatures
argument_list|(
literal|"enhance"
argument_list|)
decl_stmt|;
name|morpho
operator|.
name|addCase
argument_list|(
operator|new
name|CaseTag
argument_list|(
literal|"test-case-1"
argument_list|,
name|Case
operator|.
name|Comitative
argument_list|)
argument_list|)
expr_stmt|;
name|morpho
operator|.
name|addCase
argument_list|(
operator|new
name|CaseTag
argument_list|(
literal|"test-case-2"
argument_list|,
name|Case
operator|.
name|Abessive
argument_list|)
argument_list|)
expr_stmt|;
name|morpho
operator|.
name|addDefinitness
argument_list|(
name|Definitness
operator|.
name|Definite
argument_list|)
expr_stmt|;
name|morpho
operator|.
name|addPerson
argument_list|(
name|Person
operator|.
name|First
argument_list|)
expr_stmt|;
name|morpho
operator|.
name|addPos
argument_list|(
operator|new
name|PosTag
argument_list|(
literal|"PN"
argument_list|,
name|Pos
operator|.
name|ProperNoun
argument_list|)
argument_list|)
expr_stmt|;
name|morpho
operator|.
name|addGender
argument_list|(
operator|new
name|GenderTag
argument_list|(
literal|"test-gender"
argument_list|,
name|Gender
operator|.
name|Masculine
argument_list|)
argument_list|)
expr_stmt|;
name|morpho
operator|.
name|addNumber
argument_list|(
operator|new
name|NumberTag
argument_list|(
literal|"test-number"
argument_list|,
name|NumberFeature
operator|.
name|Plural
argument_list|)
argument_list|)
expr_stmt|;
name|morpho
operator|.
name|addTense
argument_list|(
operator|new
name|TenseTag
argument_list|(
literal|"test-tense"
argument_list|,
name|Tense
operator|.
name|Present
argument_list|)
argument_list|)
expr_stmt|;
name|morpho
operator|.
name|addVerbForm
argument_list|(
operator|new
name|VerbMoodTag
argument_list|(
literal|"test-verb-mood"
argument_list|,
name|VerbMood
operator|.
name|ConditionalVerb
argument_list|)
argument_list|)
expr_stmt|;
name|enhancer
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|MORPHO_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
name|morpho
argument_list|)
argument_list|)
expr_stmt|;
comment|//create a chunk
name|Chunk
name|stanbolEnhancer
init|=
name|analysedTextWithData
operator|.
name|addChunk
argument_list|(
name|stanbol
operator|.
name|getStart
argument_list|()
argument_list|,
name|enhancer
operator|.
name|getEnd
argument_list|()
argument_list|)
decl_stmt|;
name|expectedChunks
operator|.
name|put
argument_list|(
name|stanbolEnhancer
argument_list|,
literal|"Stanbol enhancer"
argument_list|)
expr_stmt|;
name|stanbolEnhancer
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|NER_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
operator|new
name|NerTag
argument_list|(
literal|"organization"
argument_list|,
name|DBPEDIA_ORGANISATION
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|stanbolEnhancer
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|PHRASE_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
operator|new
name|PhraseTag
argument_list|(
literal|"NP"
argument_list|,
name|LexicalCategory
operator|.
name|Noun
argument_list|)
argument_list|,
literal|0.98
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|initAnalysedText
parameter_list|()
throws|throws
name|Exception
block|{
name|at
operator|=
name|createAnalysedText
argument_list|()
expr_stmt|;
block|}
comment|/**      * @throws IOException      */
specifier|private
specifier|static
name|AnalysedText
name|createAnalysedText
parameter_list|()
throws|throws
name|IOException
block|{
return|return
name|atFactory
operator|.
name|createAnalysedText
argument_list|(
name|textBlob
operator|.
name|getValue
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSerialization
parameter_list|()
throws|throws
name|IOException
block|{
name|ByteArrayOutputStream
name|bout
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|AnalyzedTextSerializer
name|serializer
init|=
name|AnalyzedTextSerializer
operator|.
name|getDefaultInstance
argument_list|()
decl_stmt|;
name|serializer
operator|.
name|serialize
argument_list|(
name|analysedTextWithData
argument_list|,
name|bout
argument_list|,
literal|null
argument_list|)
expr_stmt|;
comment|//get the serialized String and check for some expected elements
name|byte
index|[]
name|data
init|=
name|bout
operator|.
name|toByteArray
argument_list|()
decl_stmt|;
name|String
name|serialized
init|=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
literal|"\"spans\" : [ {"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
literal|"\"type\" : \"Text\""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
literal|"\"type\" : \"Sentence\""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
literal|"\"type\" : \"Token\""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
literal|"\"stanbol.enhancer.nlp.pos\" : {"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
literal|"\"class\" : \"org.apache.stanbol.enhancer.nlp.pos.PosTag\""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
literal|"\"stanbol.enhancer.nlp.ner\" : {"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
literal|"\"class\" : \"org.apache.stanbol.enhancer.nlp.ner.NerTag\""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
literal|"\"stanbol.enhancer.nlp.morpho\" : {"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
literal|"\"class\" : \"org.apache.stanbol.enhancer.nlp.morpho.MorphoFeatures\""
argument_list|)
argument_list|)
expr_stmt|;
comment|//deserialize
name|AnalyzedTextParser
name|parser
init|=
name|AnalyzedTextParser
operator|.
name|getDefaultInstance
argument_list|()
decl_stmt|;
name|AnalysedText
name|parsedAt
init|=
name|parser
operator|.
name|parse
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|data
argument_list|)
argument_list|,
literal|null
argument_list|,
name|textBlob
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|analysedTextWithData
argument_list|,
name|parsedAt
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|Span
argument_list|>
name|origSpanIt
init|=
name|analysedTextWithData
operator|.
name|getEnclosed
argument_list|(
name|EnumSet
operator|.
name|allOf
argument_list|(
name|SpanTypeEnum
operator|.
name|class
argument_list|)
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Span
argument_list|>
name|parsedSpanIt
init|=
name|parsedAt
operator|.
name|getEnclosed
argument_list|(
name|EnumSet
operator|.
name|allOf
argument_list|(
name|SpanTypeEnum
operator|.
name|class
argument_list|)
argument_list|)
decl_stmt|;
while|while
condition|(
name|origSpanIt
operator|.
name|hasNext
argument_list|()
operator|&&
name|parsedSpanIt
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Span
name|orig
init|=
name|origSpanIt
operator|.
name|next
argument_list|()
decl_stmt|;
name|Span
name|parsed
init|=
name|parsedSpanIt
operator|.
name|next
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|orig
argument_list|,
name|parsed
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|origKeys
init|=
name|orig
operator|.
name|getKeys
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|parsedKeys
init|=
name|parsed
operator|.
name|getKeys
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|origKeys
argument_list|,
name|parsedKeys
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|key
range|:
name|origKeys
control|)
block|{
name|List
argument_list|<
name|Value
argument_list|<
name|?
argument_list|>
argument_list|>
name|origValues
init|=
name|orig
operator|.
name|getValues
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Value
argument_list|<
name|?
argument_list|>
argument_list|>
name|parsedValues
init|=
name|parsed
operator|.
name|getValues
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|origValues
argument_list|,
name|parsedValues
argument_list|)
expr_stmt|;
block|}
block|}
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"Original AnalyzedText MUST NOT have additional Spans"
argument_list|,
name|origSpanIt
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"Parsed AnalyzedText MUST NOT have additional Spans"
argument_list|,
name|parsedSpanIt
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

