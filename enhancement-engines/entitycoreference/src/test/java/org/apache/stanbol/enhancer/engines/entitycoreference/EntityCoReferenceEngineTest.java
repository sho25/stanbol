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
name|engines
operator|.
name|entitycoreference
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
name|Properties
operator|.
name|DC_LANGUAGE
import|;
end_import

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
name|Properties
operator|.
name|DC_TYPE
import|;
end_import

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
name|Properties
operator|.
name|ENHANCER_CONFIDENCE
import|;
end_import

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
name|TechnicalClasses
operator|.
name|DCTERMS_LINGUISTIC_SYSTEM
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
name|Dictionary
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|commons
operator|.
name|rdf
operator|.
name|Graph
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
name|impl
operator|.
name|utils
operator|.
name|PlainLiteralImpl
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
name|impl
operator|.
name|utils
operator|.
name|TripleImpl
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
name|coref
operator|.
name|CorefFeature
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
name|EngineException
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
name|EnhancementEngine
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
name|helper
operator|.
name|EnhancementEngineHelper
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
name|Test
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|cm
operator|.
name|ConfigurationException
import|;
end_import

begin_comment
comment|/**  * Main test class  *   * @author Cristian Petroaca  *  */
end_comment

begin_class
specifier|public
class|class
name|EntityCoReferenceEngineTest
block|{
specifier|private
specifier|static
specifier|final
name|String
name|SPATIAL_SENTENCE_1
init|=
literal|"Angela Merkel visited China."
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SPATIAL_SENTENCE_2
init|=
literal|"The German politician met the Chinese prime minister."
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SPATIAL_TEXT
init|=
name|SPATIAL_SENTENCE_1
operator|+
name|SPATIAL_SENTENCE_2
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
name|EntityCoReferenceEngine
name|engine
decl_stmt|;
annotation|@
name|Before
specifier|public
name|void
name|setUpServices
parameter_list|()
throws|throws
name|IOException
throws|,
name|ConfigurationException
block|{
name|engine
operator|=
operator|new
name|EntityCoReferenceEngine
argument_list|()
expr_stmt|;
comment|// we need to set some fields that would otherwise be injected by the
comment|// container
name|engine
operator|.
name|siteManager
operator|=
operator|new
name|MockSiteManager
argument_list|()
expr_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|config
operator|.
name|put
argument_list|(
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|,
literal|"entity-coreference"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|EntityCoReferenceEngine
operator|.
name|CONFIG_LANGUAGES
argument_list|,
literal|"en"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|EntityCoReferenceEngine
operator|.
name|REFERENCED_SITE_ID
argument_list|,
name|MockEntityCorefDbpediaSite
operator|.
name|SITE_ID
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|EntityCoReferenceEngine
operator|.
name|MAX_DISTANCE
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|EntityCoReferenceEngine
operator|.
name|ENTITY_URI_BASE
argument_list|,
literal|"http://dbpedia.org/resource/"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|EntityCoReferenceEngine
operator|.
name|SPATIAL_ATTR_FOR_PERSON
argument_list|,
name|Constants
operator|.
name|DEFAULT_SPATIAL_ATTR_FOR_PERSON
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|EntityCoReferenceEngine
operator|.
name|SPATIAL_ATTR_FOR_ORGANIZATION
argument_list|,
name|Constants
operator|.
name|DEFAULT_SPATIAL_ATTR_FOR_ORGANIZATION
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|EntityCoReferenceEngine
operator|.
name|SPATIAL_ATTR_FOR_PLACE
argument_list|,
name|Constants
operator|.
name|DEFAULT_SPATIAL_ATTR_FOR_PLACE
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|EntityCoReferenceEngine
operator|.
name|ORG_ATTR_FOR_PERSON
argument_list|,
name|Constants
operator|.
name|DEFAULT_ORG_ATTR_FOR_PERSON
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|EntityCoReferenceEngine
operator|.
name|ENTITY_CLASSES_TO_EXCLUDE
argument_list|,
name|Constants
operator|.
name|DEFAULT_ENTITY_CLASSES_TO_EXCLUDE
argument_list|)
expr_stmt|;
name|engine
operator|.
name|activate
argument_list|(
operator|new
name|MockComponentContext
argument_list|(
name|config
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSpatialCoref
parameter_list|()
throws|throws
name|EngineException
throws|,
name|IOException
block|{
name|ContentItem
name|ci
init|=
name|ciFactory
operator|.
name|createContentItem
argument_list|(
operator|new
name|StringSource
argument_list|(
name|SPATIAL_TEXT
argument_list|)
argument_list|)
decl_stmt|;
name|Graph
name|graph
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|IRI
name|textEnhancement
init|=
name|EnhancementEngineHelper
operator|.
name|createTextEnhancement
argument_list|(
name|ci
argument_list|,
name|engine
argument_list|)
decl_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textEnhancement
argument_list|,
name|DC_LANGUAGE
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
literal|"en"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textEnhancement
argument_list|,
name|ENHANCER_CONFIDENCE
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
literal|"100.0"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textEnhancement
argument_list|,
name|DC_TYPE
argument_list|,
name|DCTERMS_LINGUISTIC_SYSTEM
argument_list|)
argument_list|)
expr_stmt|;
name|Entry
argument_list|<
name|IRI
argument_list|,
name|Blob
argument_list|>
name|textBlob
init|=
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
decl_stmt|;
name|AnalysedText
name|at
init|=
name|atFactory
operator|.
name|createAnalysedText
argument_list|(
name|ci
argument_list|,
name|textBlob
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
name|Sentence
name|sentence1
init|=
name|at
operator|.
name|addSentence
argument_list|(
literal|0
argument_list|,
name|SPATIAL_SENTENCE_1
operator|.
name|indexOf
argument_list|(
literal|"."
argument_list|)
operator|+
literal|1
argument_list|)
decl_stmt|;
name|Chunk
name|angelaMerkel
init|=
name|sentence1
operator|.
name|addChunk
argument_list|(
literal|0
argument_list|,
literal|"Angela Merkel"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|angelaMerkel
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
literal|"Angela Merkel"
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Sentence
name|sentence2
init|=
name|at
operator|.
name|addSentence
argument_list|(
name|SPATIAL_SENTENCE_1
operator|.
name|indexOf
argument_list|(
literal|"."
argument_list|)
operator|+
literal|1
argument_list|,
name|SPATIAL_SENTENCE_1
operator|.
name|length
argument_list|()
operator|+
name|SPATIAL_SENTENCE_2
operator|.
name|indexOf
argument_list|(
literal|"."
argument_list|)
operator|+
literal|1
argument_list|)
decl_stmt|;
name|int
name|theStartIdx
init|=
name|sentence2
operator|.
name|getSpan
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|"The"
argument_list|)
decl_stmt|;
name|int
name|germanStartIdx
init|=
name|sentence2
operator|.
name|getSpan
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|"German"
argument_list|)
decl_stmt|;
name|int
name|chancellorStartIdx
init|=
name|sentence2
operator|.
name|getSpan
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|"politician"
argument_list|)
decl_stmt|;
name|Token
name|the
init|=
name|sentence2
operator|.
name|addToken
argument_list|(
name|theStartIdx
argument_list|,
name|theStartIdx
operator|+
literal|"The"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
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
literal|"The"
argument_list|,
name|LexicalCategory
operator|.
name|PronounOrDeterminer
argument_list|,
name|Pos
operator|.
name|Determiner
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Token
name|german
init|=
name|sentence2
operator|.
name|addToken
argument_list|(
name|germanStartIdx
argument_list|,
name|germanStartIdx
operator|+
literal|"German"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|german
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
literal|"German"
argument_list|,
name|LexicalCategory
operator|.
name|Adjective
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Token
name|politician
init|=
name|sentence2
operator|.
name|addToken
argument_list|(
name|chancellorStartIdx
argument_list|,
name|chancellorStartIdx
operator|+
literal|"politician"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|politician
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
literal|"politician"
argument_list|,
name|LexicalCategory
operator|.
name|Noun
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Chunk
name|theGermanChancellor
init|=
name|sentence2
operator|.
name|addChunk
argument_list|(
name|theStartIdx
argument_list|,
name|chancellorStartIdx
operator|+
literal|"politician"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|theGermanChancellor
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
literal|"The German politician"
argument_list|,
name|LexicalCategory
operator|.
name|Noun
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|engine
operator|.
name|computeEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|Value
argument_list|<
name|CorefFeature
argument_list|>
name|representativeCorefValue
init|=
name|angelaMerkel
operator|.
name|getAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|COREF_ANNOTATION
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|representativeCorefValue
argument_list|)
expr_stmt|;
name|CorefFeature
name|representativeCoref
init|=
name|representativeCorefValue
operator|.
name|value
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|representativeCoref
operator|.
name|isRepresentative
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|representativeCoref
operator|.
name|getMentions
argument_list|()
operator|.
name|contains
argument_list|(
name|theGermanChancellor
argument_list|)
argument_list|)
expr_stmt|;
name|Value
argument_list|<
name|CorefFeature
argument_list|>
name|subordinateCorefValue
init|=
name|theGermanChancellor
operator|.
name|getAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|COREF_ANNOTATION
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|subordinateCorefValue
argument_list|)
expr_stmt|;
name|CorefFeature
name|subordinateCoref
init|=
name|subordinateCorefValue
operator|.
name|value
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
operator|!
name|subordinateCoref
operator|.
name|isRepresentative
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|subordinateCoref
operator|.
name|getMentions
argument_list|()
operator|.
name|contains
argument_list|(
name|angelaMerkel
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

