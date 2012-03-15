begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|contenthub
operator|.
name|test
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|*
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
name|org
operator|.
name|apache
operator|.
name|sling
operator|.
name|junit
operator|.
name|annotations
operator|.
name|SlingAnnotationsTestRunner
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|sling
operator|.
name|junit
operator|.
name|annotations
operator|.
name|TestReference
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|SearchException
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|related
operator|.
name|RelatedKeyword
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|related
operator|.
name|RelatedKeywordSearchManager
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
name|junit
operator|.
name|runner
operator|.
name|RunWith
import|;
end_import

begin_class
annotation|@
name|RunWith
argument_list|(
name|SlingAnnotationsTestRunner
operator|.
name|class
argument_list|)
specifier|public
class|class
name|RelatedSearchTest
block|{
comment|/* ClosureHelper.java IndexingHelper.java relatedKeywordSearch.search() will be tested OntologyResourceSearch will be tested later ReferencedSiteSearch will not be tested */
annotation|@
name|TestReference
specifier|private
name|RelatedKeywordSearchManager
name|relatedKeywordSearchManager
decl_stmt|;
comment|//	@TestReference
comment|//	private RelatedKeywordSearch relatedKeywordSearch;
comment|//	@Test
comment|//	public void testGetRelatedKeywordsFromAllSources(){
comment|//
comment|//	}
comment|//	@Test
comment|//	public void testGetRelatedKeywordsFromOntology() throws SearchException, EnhancementException, UnsupportedEncodingException {
comment|//
comment|//		TripleCollection triples = new SimpleMGraph();
comment|//		triples.add(new TripleImpl(new UriRef("http://dbpedia.org/resource/Paris"), new UriRef(
comment|//				"http://www.apache.org/stanbol/cms#parentRef"), new UriRef(
comment|//				"http://dbpedia.org/resource/France")));
comment|//
comment|//		triples.add(new TripleImpl(new UriRef("http://dbpedia.org/resource/Paris"), new UriRef(
comment|//				"rdf:type"), new UriRef(
comment|//				"http://dbpedia.org/ontology/Place")));
comment|//
comment|//		triples.add(new TripleImpl(new UriRef("http://dbpedia.org/resource/Paris"), new UriRef(
comment|//				"rdfs:subClassOf"), new UriRef(
comment|//				"http://dbpedia.org/resource/France")));
comment|//
comment|//		tcManager.createMGraph(TestVocabulary.ontologyURI);
comment|//		tcManager.getMGraph(TestVocabulary.ontologyURI).addAll(triples);
comment|//
comment|//		//TODO: check whether res contains TestVocabulary.resultTerm
comment|//		SearchResult res = relatedKeywordSearchManager.getRelatedKeywordsFromOntology(
comment|//				TestVocabulary.queryTerm, TestVocabulary.ontologyURI.getUnicodeString());
comment|//
comment|//
comment|//		tcManager.deleteTripleCollection(TestVocabulary.ontologyURI);
comment|//	}
comment|//	@Test
comment|//	public void testGetRelatedKeywordsFromReferencedSites(){
comment|//
comment|//	}
annotation|@
name|Test
specifier|public
name|void
name|testGetRelatedKeywordsFromWordnet
parameter_list|()
throws|throws
name|SearchException
block|{
comment|//TODO: check wordnet is setted
name|boolean
name|isFinded
init|=
literal|false
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
argument_list|>
name|relatedKeywordsMap
init|=
name|relatedKeywordSearchManager
operator|.
name|getRelatedKeywordsFromWordnet
argument_list|(
name|TestVocabulary
operator|.
name|queryTerm
argument_list|)
operator|.
name|getRelatedKeywords
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
name|relatedKeyword
init|=
name|relatedKeywordsMap
operator|.
name|get
argument_list|(
name|TestVocabulary
operator|.
name|queryTerm
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
name|values
init|=
name|relatedKeyword
operator|.
name|get
argument_list|(
literal|"Wordnet"
argument_list|)
decl_stmt|;
for|for
control|(
name|RelatedKeyword
name|value
range|:
name|values
control|)
block|{
if|if
condition|(
name|value
operator|.
name|getKeyword
argument_list|()
operator|.
name|contains
argument_list|(
name|TestVocabulary
operator|.
name|resultTerm
argument_list|)
condition|)
block|{
name|isFinded
operator|=
literal|true
expr_stmt|;
block|}
block|}
name|assertTrue
argument_list|(
name|isFinded
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

