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
name|it
package|;
end_package

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_class
specifier|public
class|class
name|ProperNounLinkingTest
extends|extends
name|EnhancerTestBase
block|{
specifier|public
specifier|static
specifier|final
name|String
name|TEST_TEXT
init|=
literal|"The ProperNoun linking Chain can not "
operator|+
literal|"only detect famous cities such as London and people such as Bob "
operator|+
literal|"Marley but also books like the Theory of Relativity, events like "
operator|+
literal|"the French Revolution or the Paris Peace Conference and even "
operator|+
literal|"prices such as the Nobel Prize in Literature."
decl_stmt|;
comment|/**      *       */
specifier|public
name|ProperNounLinkingTest
parameter_list|()
block|{
name|super
argument_list|(
name|getChainEndpoint
argument_list|(
literal|"dbpedia-proper-noun"
argument_list|)
argument_list|,
literal|"langdetect"
argument_list|,
literal|" LanguageDetectionEnhancementEngine"
argument_list|,
literal|"opennlp-sentence"
argument_list|,
literal|" OpenNlpSentenceDetectionEngine"
argument_list|,
literal|"opennlp-token"
argument_list|,
literal|" OpenNlpTokenizerEngine"
argument_list|,
literal|"opennlp-pos"
argument_list|,
literal|"OpenNlpPosTaggingEngine"
argument_list|,
literal|"opennlp-chunker"
argument_list|,
literal|"OpenNlpChunkingEngine"
argument_list|,
literal|"dbpedia-proper-noun-extraction"
argument_list|,
literal|"EntityLinkingEngine"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSimpleEnhancement
parameter_list|()
throws|throws
name|Exception
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
name|getEndpoint
argument_list|()
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/rdf+nt"
argument_list|)
operator|.
name|withContent
argument_list|(
name|TEST_TEXT
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentRegexp
argument_list|(
comment|// it MUST detect the language
literal|"http://purl.org/dc/terms/creator.*LanguageDetectionEnhancementEngine"
argument_list|,
literal|"http://purl.org/dc/terms/language.*en"
argument_list|,
comment|//and the entityLinkingEngine
literal|"http://purl.org/dc/terms/creator.*EntityLinkingEngine"
argument_list|,
comment|//needs to suggest the following Entities
literal|"http://fise.iks-project.eu/ontology/entity-reference.*http://dbpedia.org/resource/London"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-reference.*http://dbpedia.org/resource/Bob_Marley"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-reference.*http://dbpedia.org/resource/French_Revolution"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-reference.*http://dbpedia.org/resource/Nobel_Prize_in_Literature"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-reference.*http://dbpedia.org/resource/Nobel_Prize"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-reference.*http://dbpedia.org/resource/Paris_Peace_Conference,_1919"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-reference.*http://dbpedia.org/resource/Theory_of_relativity"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/entity-reference.*http://dbpedia.org/resource/Theory"
argument_list|,
comment|//for the following sections within the text
literal|"http://fise.iks-project.eu/ontology/selected-text.*Theory of Relativity"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/selected-text.*Nobel Prize in Literature"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/selected-text.*Paris Peace Conference"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/selected-text.*French Revolution"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

