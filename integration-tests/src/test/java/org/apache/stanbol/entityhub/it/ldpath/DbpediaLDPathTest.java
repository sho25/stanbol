begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|it
operator|.
name|ldpath
package|;
end_package

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
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLEncoder
import|;
end_import

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
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Response
operator|.
name|Status
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|entity
operator|.
name|UrlEncodedFormEntity
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
name|entityhub
operator|.
name|it
operator|.
name|ReferencedSiteTest
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
name|entityhub
operator|.
name|it
operator|.
name|SitesManagerTest
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
name|entityhub
operator|.
name|test
operator|.
name|it
operator|.
name|EntityhubTestBase
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
name|entityhub
operator|.
name|test
operator|.
name|query
operator|.
name|FieldQueryTestCase
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
name|entityhub
operator|.
name|test
operator|.
name|query
operator|.
name|FindQueryTestCase
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
name|entityhub
operator|.
name|test
operator|.
name|query
operator|.
name|QueryTestBase
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONException
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
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Tests for the "/query" (Field Query) and "/find" (label search)   * implementation of the Entityhub.<p>  * All the tests defined by this class assume the default data set for   * dbpedia as provided by the   *<code>org.apache.stanbol.data.sites.dbpedia.default</code> bundle.<p>  * This test cases are used to test both the ReferencedSiteManager and   * the ReferencedSite. This is also the reason why having this abstract super  * class defining the tests.  * @see ReferencedSiteTest  * @see SitesManagerTest  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|DbpediaLDPathTest
extends|extends
name|EntityhubTestBase
block|{
specifier|public
name|DbpediaLDPathTest
parameter_list|()
block|{
name|super
argument_list|(
name|Collections
operator|.
name|singleton
argument_list|(
literal|"dbpedia"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testNoContext
parameter_list|()
throws|throws
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/ldpath"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"ldpath"
argument_list|,
literal|"name = rdfs:label[@en] :: xsd:string;"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
operator|.
name|getStatusCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEmptyContext
parameter_list|()
throws|throws
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/ldpath"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"ldpath"
argument_list|,
literal|"name = rdfs:label[@en] :: xsd:string;"
argument_list|,
literal|"context"
argument_list|,
literal|""
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
operator|.
name|getStatusCode
argument_list|()
argument_list|)
expr_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/ldpath"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"ldpath"
argument_list|,
literal|"name = rdfs:label[@en] :: xsd:string;"
argument_list|,
literal|"context"
argument_list|,
literal|null
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
operator|.
name|getStatusCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testNoLDPath
parameter_list|()
throws|throws
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/ldpath"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"context"
argument_list|,
literal|"http://dbpedia.org/resource/Paris"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
operator|.
name|getStatusCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEmptyLDPath
parameter_list|()
throws|throws
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/ldpath"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"context"
argument_list|,
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
literal|"ldpath"
argument_list|,
literal|null
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
operator|.
name|getStatusCode
argument_list|()
argument_list|)
expr_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/ldpath"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"context"
argument_list|,
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
literal|"ldpath"
argument_list|,
literal|""
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
operator|.
name|getStatusCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testIllegalLDPath
parameter_list|()
throws|throws
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/ldpath"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"context"
argument_list|,
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
comment|//missing semicolon
literal|"ldpath"
argument_list|,
literal|"name = rdfs:label[@en] :: xsd:string"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
operator|.
name|getStatusCode
argument_list|()
argument_list|)
expr_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/ldpath"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"context"
argument_list|,
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
comment|//unknown namespace prefix
literal|"ldpath"
argument_list|,
literal|"name = dct:subject :: xsd:anyURI;"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
operator|.
name|getStatusCode
argument_list|()
argument_list|)
expr_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/ldpath"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"context"
argument_list|,
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
comment|//unknown dataType prefix
literal|"ldpath"
argument_list|,
literal|"name = rdfs:label[@en] :: xsd:String;"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
operator|.
name|getStatusCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMultipleContext
parameter_list|()
throws|throws
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/ldpath"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"context"
argument_list|,
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
literal|"context"
argument_list|,
literal|"http://dbpedia.org/resource/London"
argument_list|,
literal|"ldpath"
argument_list|,
literal|"name = rdfs:label[@en] :: xsd:string;"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentContains
argument_list|(
literal|"\"@literal\": \"Paris\""
argument_list|,
literal|"\"@literal\": \"London\""
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testUnknownContext
parameter_list|()
throws|throws
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/ldpath"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"context"
argument_list|,
literal|"http://dbpedia.org/resource/ThisEntityDoesNotExist_ForSure_49283"
argument_list|,
literal|"ldpath"
argument_list|,
literal|"name = rdfs:label[@en] :: xsd:string;"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentContains
argument_list|(
literal|"{"
argument_list|,
literal|"}"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testLDPath
parameter_list|()
throws|throws
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/ldpath"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"context"
argument_list|,
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
literal|"ldpath"
argument_list|,
literal|"@prefix dct :<http://purl.org/dc/terms/subject/> ;"
operator|+
literal|"@prefix geo :<http://www.w3.org/2003/01/geo/wgs84_pos#> ;"
operator|+
literal|"name = rdfs:label[@en] :: xsd:string;"
operator|+
literal|"labels = rdfs:label :: xsd:string;"
operator|+
literal|"comment = rdfs:comment[@en] :: xsd:string;"
operator|+
literal|"categories = dct:subject :: xsd:anyURI;"
operator|+
literal|"homepage = foaf:homepage :: xsd:anyURI;"
operator|+
literal|"location = fn:concat(\"[\",geo:lat,\",\",geo:long,\"]\") :: xsd:string;"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentType
argument_list|(
literal|"application/json"
argument_list|)
operator|.
name|assertContentContains
argument_list|(
literal|"\"@subject\": \"http://dbpedia.org/resource/Paris\""
argument_list|,
literal|"\"comment\": {"
argument_list|,
literal|"Paris is the capital and largest city in France"
argument_list|,
literal|"\"homepage\": ["
argument_list|,
literal|"http://www.paris.fr"
argument_list|,
literal|"\"labels\": ["
argument_list|,
literal|"\"@literal\": \"Pariisi\","
argument_list|,
literal|"\"@literal\": \"巴黎\","
argument_list|,
literal|"\"location\": \"[48.856667,2.350833]\","
argument_list|,
literal|"\"name\": {"
argument_list|,
literal|"\"@literal\": \"Paris\","
argument_list|)
expr_stmt|;
block|}
comment|/*      * "/find" tests      */
annotation|@
name|Test
specifier|public
name|void
name|testFindInvalidLDPath
parameter_list|()
throws|throws
name|IOException
block|{
comment|//parse some illegal LDPath
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/ldpath"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/turtle"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"name"
argument_list|,
literal|"Vienna"
argument_list|,
literal|"lang"
argument_list|,
literal|"en"
argument_list|,
comment|//NOTE the missing semicolon
literal|"ldpath"
argument_list|,
literal|"label_de = rdfs:label[@de] :: xsd:string"
argument_list|,
literal|"limit"
argument_list|,
literal|"1"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
operator|.
name|getStatusCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFindLDPathSelectLabel
parameter_list|()
throws|throws
name|IOException
block|{
comment|//select the German label on a query for the english one
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/find"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/turtle"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"name"
argument_list|,
literal|"Vienna"
argument_list|,
literal|"lang"
argument_list|,
literal|"en"
argument_list|,
literal|"ldpath"
argument_list|,
literal|"name_de = rdfs:label[@de] :: xsd:string;"
argument_list|,
literal|"limit"
argument_list|,
literal|"1"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentType
argument_list|(
literal|"text/turtle"
argument_list|)
operator|.
name|assertContentContains
argument_list|(
literal|"<http://www.iks-project.eu/ontology/rick/query/score>"
argument_list|,
literal|"<http://dbpedia.org/resource/Vienna>"
argument_list|,
literal|"<name_de> \"Wien\"@de ."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFindLDPathOnMultipleResults
parameter_list|()
throws|throws
name|IOException
block|{
comment|//select multiple end check that LD-Path is executed on all results
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/find"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/turtle"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"name"
argument_list|,
literal|"York"
argument_list|,
literal|"lang"
argument_list|,
literal|"en"
argument_list|,
literal|"ldpath"
argument_list|,
literal|"@prefix geo :<http://www.w3.org/2003/01/geo/wgs84_pos#> ;"
operator|+
literal|"lat = geo:lat :: xsd:double;"
argument_list|,
literal|"limit"
argument_list|,
literal|"3"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentType
argument_list|(
literal|"text/turtle"
argument_list|)
operator|.
name|assertContentContains
argument_list|(
literal|"<http://www.iks-project.eu/ontology/rick/query/score>"
argument_list|,
literal|"<http://dbpedia.org/resource/York>"
argument_list|,
literal|"<lat>   \"53.958332\"^^<http://www.w3.org/2001/XMLSchema#double> ."
argument_list|,
literal|"<http://dbpedia.org/resource/New_York_City>"
argument_list|,
literal|"<lat>   \"40.716667\"^^<http://www.w3.org/2001/XMLSchema#double> ."
argument_list|,
literal|"<http://dbpedia.org/resource/New_York>"
argument_list|,
literal|"<lat>   \"43.0\"^^<http://www.w3.org/2001/XMLSchema#double> ."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFindLDPathSelectPaths
parameter_list|()
throws|throws
name|IOException
block|{
comment|//select the German name and the categories ond other members of the
comment|//same category
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/find"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/turtle"
argument_list|)
operator|.
name|withFormContent
argument_list|(
literal|"name"
argument_list|,
literal|"Spinne"
argument_list|,
literal|"lang"
argument_list|,
literal|"de"
argument_list|,
literal|"ldpath"
argument_list|,
literal|"@prefix dct :<http://purl.org/dc/terms/> ;"
operator|+
literal|"name = rdfs:label[@en] :: xsd:string;"
operator|+
literal|"category = dct:subject :: xsd:anyURI;"
operator|+
literal|"others = dct:subject/^dct:subject :: xsd:anyURI;"
argument_list|,
literal|"limit"
argument_list|,
literal|"1"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentType
argument_list|(
literal|"text/turtle"
argument_list|)
operator|.
name|assertContentContains
argument_list|(
literal|"<http://www.iks-project.eu/ontology/rick/query/score>"
argument_list|,
literal|"<name>  \"Spider\"@en ;"
argument_list|,
literal|"<category><http://dbpedia.org/resource/Category:Arachnids> , "
operator|+
literal|"<http://dbpedia.org/resource/Category:Spiders> ;"
argument_list|,
literal|"<others><http://dbpedia.org/resource/Acari> , "
operator|+
literal|"<http://dbpedia.org/resource/Spider> , "
operator|+
literal|"<http://dbpedia.org/resource/Scorpion> , "
operator|+
literal|"<http://dbpedia.org/resource/Arachnid> ."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testQueryIllegalLDPath
parameter_list|()
throws|throws
name|IOException
block|{
comment|//The field query as java string
name|String
name|query
init|=
literal|"{"
operator|+
literal|"\"ldpath\": \"@prefix dct :<http:\\/\\/purl.org\\/dc\\/terms\\/subject\\/> ; "
operator|+
literal|"@prefix geo :<http:\\/\\/www.w3.org\\/2003\\/01\\/geo\\/wgs84_pos#> ; "
operator|+
literal|"@prefix dbp-ont :<http:\\/\\/dbpedia.org\\/ontology\\/> ; "
operator|+
comment|//note the missing semicolon
literal|"lat = geo:lat :: xsd:decimal ; long = geo:long :: xsd:decimal "
operator|+
literal|"type = rdf:type :: xsd:anyURI;\","
operator|+
literal|"\"constraints\": [{ "
operator|+
literal|"\"type\": \"reference\","
operator|+
literal|"\"field\": \"http:\\/\\/www.w3.org\\/1999\\/02\\/22-rdf-syntax-ns#type\","
operator|+
literal|"\"value\": \"http:\\/\\/dbpedia.org\\/ontology\\/Place\","
operator|+
literal|"},"
operator|+
literal|"{"
operator|+
literal|"\"type\": \"range\","
operator|+
literal|"\"field\": \"http:\\/\\/www.w3.org\\/2003\\/01\\/geo\\/wgs84_pos#lat\","
operator|+
literal|"\"lowerBound\": 50,"
operator|+
literal|"\"upperBound\": 51,"
operator|+
literal|"\"inclusive\": true,"
operator|+
literal|"\"datatype\": \"xsd:double\""
operator|+
literal|"},"
operator|+
literal|"{"
operator|+
literal|"\"type\": \"range\","
operator|+
literal|"\"field\": \"http:\\/\\/www.w3.org\\/2003\\/01\\/geo\\/wgs84_pos#long\","
operator|+
literal|"\"lowerBound\": 6,"
operator|+
literal|"\"upperBound\": 8,"
operator|+
literal|"\"inclusive\": true,"
operator|+
literal|"\"datatype\": \"xsd:double\""
operator|+
literal|"}"
operator|+
literal|"],"
operator|+
literal|"\"offset\": 0,"
operator|+
literal|"\"limit\": 10,"
operator|+
literal|"}"
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/query"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/turtle"
argument_list|)
operator|.
name|withContent
argument_list|(
name|query
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
operator|.
name|getStatusCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testQueryLDPathSelection
parameter_list|()
throws|throws
name|IOException
block|{
comment|//The field query as java string
name|String
name|query
init|=
literal|"{"
operator|+
literal|"\"ldpath\": \"@prefix dct :<http:\\/\\/purl.org\\/dc\\/terms\\/subject\\/> ; "
operator|+
literal|"@prefix geo :<http:\\/\\/www.w3.org\\/2003\\/01\\/geo\\/wgs84_pos#> ; "
operator|+
literal|"@prefix dbp-ont :<http:\\/\\/dbpedia.org\\/ontology\\/> ; "
operator|+
literal|"lat = geo:lat :: xsd:decimal ; long = geo:long :: xsd:decimal ; "
operator|+
literal|"population = dbp-ont:populationTotal :: xsd:integer ; "
operator|+
literal|"elevation = dbp-ont:elevation :: xsd:integer ; "
operator|+
literal|"name = rdfs:label[@en] :: xsd:string; "
operator|+
literal|"categories = dct:subject :: xsd:anyURI; "
operator|+
literal|"type = rdf:type :: xsd:anyURI;\","
operator|+
literal|"\"constraints\": [{ "
operator|+
literal|"\"type\": \"reference\","
operator|+
literal|"\"field\": \"http:\\/\\/www.w3.org\\/1999\\/02\\/22-rdf-syntax-ns#type\","
operator|+
literal|"\"value\": \"http:\\/\\/dbpedia.org\\/ontology\\/Place\","
operator|+
literal|"},"
operator|+
literal|"{"
operator|+
literal|"\"type\": \"range\","
operator|+
literal|"\"field\": \"http:\\/\\/www.w3.org\\/2003\\/01\\/geo\\/wgs84_pos#lat\","
operator|+
literal|"\"lowerBound\": 50,"
operator|+
literal|"\"upperBound\": 51,"
operator|+
literal|"\"inclusive\": true,"
operator|+
literal|"\"datatype\": \"xsd:double\""
operator|+
literal|"},"
operator|+
literal|"{"
operator|+
literal|"\"type\": \"range\","
operator|+
literal|"\"field\": \"http:\\/\\/www.w3.org\\/2003\\/01\\/geo\\/wgs84_pos#long\","
operator|+
literal|"\"lowerBound\": 6,"
operator|+
literal|"\"upperBound\": 8,"
operator|+
literal|"\"inclusive\": true,"
operator|+
literal|"\"datatype\": \"xsd:double\""
operator|+
literal|"}"
operator|+
literal|"],"
operator|+
literal|"\"offset\": 0,"
operator|+
literal|"\"limit\": 10,"
operator|+
literal|"}"
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
literal|"/entityhub/site/dbpedia/query"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/turtle"
argument_list|)
operator|.
name|withContent
argument_list|(
name|query
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentType
argument_list|(
literal|"text/turtle"
argument_list|)
operator|.
name|assertContentContains
argument_list|(
comment|//first expected entities
literal|"<http://dbpedia.org/resource/Bonn>"
argument_list|,
literal|"<http://dbpedia.org/resource/Aachen>"
argument_list|,
literal|"<http://dbpedia.org/resource/Koblenz>"
argument_list|,
literal|"<http://dbpedia.org/resource/Cologne>"
argument_list|,
comment|//now some values based on the LDPath
literal|"<name>  \"Koblenz\"@en"
argument_list|,
literal|"<lat>   \"50.359722\""
argument_list|,
literal|"<long>  \"7.597778\""
argument_list|,
literal|"<type><http://www.w3.org/2002/07/owl#Thing> , "
operator|+
literal|"<http://www.opengis.net/gml/_Feature> , "
operator|+
literal|"<http://dbpedia.org/ontology/Town>"
argument_list|,
literal|"<population> 314926"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

