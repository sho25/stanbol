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
name|query
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
name|util
operator|.
name|Arrays
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
specifier|abstract
class|class
name|DbpediaQueryTest
extends|extends
name|QueryTestBase
block|{
specifier|public
name|DbpediaQueryTest
parameter_list|(
name|String
name|path
parameter_list|,
name|String
name|referencedSite
parameter_list|)
block|{
name|super
argument_list|(
name|path
argument_list|,
name|referencedSite
argument_list|,
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|DbpediaQueryTest
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFindNameQuery
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FindQueryTestCase
name|test
init|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"Paris"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
literal|"http://dbpedia.org/resource/University_of_Paris"
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Hilton"
argument_list|)
argument_list|)
decl_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFindLimitAndOffsetQuery
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//only the first result
name|FindQueryTestCase
name|test
init|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"Paris"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Paris"
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/University_of_Paris"
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Hilton"
argument_list|)
argument_list|)
decl_stmt|;
name|test
operator|.
name|setLimit
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
comment|//the second result
name|test
operator|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"Paris"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/University_of_Paris"
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Hilton"
argument_list|)
argument_list|)
expr_stmt|;
name|test
operator|.
name|setLimit
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|test
operator|.
name|setOffset
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
comment|//the second and third
name|test
operator|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"Paris"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/University_of_Paris"
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Hilton"
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Paris"
argument_list|)
argument_list|)
expr_stmt|;
name|test
operator|.
name|setLimit
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|test
operator|.
name|setOffset
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFindLanguageQuery
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FindQueryTestCase
name|test
init|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"Parigi"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
literal|"http://dbpedia.org/resource/University_of_Paris"
argument_list|,
literal|"http://dbpedia.org/resource/Paris_M%C3%A9tro"
argument_list|)
argument_list|)
decl_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
comment|//now the same test but only in English labels
name|test
operator|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"Parigi"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|//no results
name|test
operator|.
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
comment|//now in Italian
name|test
operator|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"Parigi"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
literal|"http://dbpedia.org/resource/University_of_Paris"
argument_list|,
literal|"http://dbpedia.org/resource/Paris%E2%80%93Roubaix"
argument_list|,
literal|"http://dbpedia.org/resource/Dakar_Rally"
argument_list|)
argument_list|)
expr_stmt|;
name|test
operator|.
name|setLanguage
argument_list|(
literal|"it"
argument_list|)
expr_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
comment|//now search for Paris in Italian labels
name|test
operator|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"Paris"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Paris_Hilton"
argument_list|,
literal|"http://dbpedia.org/resource/Paris%E2%80%93Nice"
argument_list|,
literal|"http://dbpedia.org/resource/Paris,_Texas"
argument_list|)
argument_list|)
expr_stmt|;
name|test
operator|.
name|setLanguage
argument_list|(
literal|"it"
argument_list|)
expr_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFindWildcards
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//first a search without wildcards
name|FindQueryTestCase
name|test
init|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"cia"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/CIA"
argument_list|,
literal|"http://dbpedia.org/resource/CIA_World_Factbook"
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Ciara"
argument_list|)
argument_list|)
decl_stmt|;
name|test
operator|.
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
comment|//now the same search with wildcards
name|test
operator|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"cia*"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/CIA"
argument_list|,
literal|"http://dbpedia.org/resource/Ciara"
argument_list|,
literal|"http://dbpedia.org/resource/CIA_World_Factbook"
argument_list|)
argument_list|)
expr_stmt|;
name|test
operator|.
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
name|test
operator|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"proto*"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Prototype"
argument_list|,
literal|"http://dbpedia.org/resource/Proton"
argument_list|,
literal|"http://dbpedia.org/resource/Internet_Protocol"
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Pretoria"
argument_list|)
argument_list|)
expr_stmt|;
name|test
operator|.
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
comment|//now the same search with wildcards
name|test
operator|=
operator|new
name|FindQueryTestCase
argument_list|(
literal|"pr?to*"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Pretoria"
argument_list|,
literal|"http://dbpedia.org/resource/Prototype"
argument_list|,
literal|"http://dbpedia.org/resource/Proton"
argument_list|,
literal|"http://dbpedia.org/resource/Internet_Protocol"
argument_list|)
argument_list|)
expr_stmt|;
name|test
operator|.
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testFindSpecificFieldQuery
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{              }
annotation|@
name|Test
specifier|public
name|void
name|testFieldQueryRangeConstraints
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{ "
operator|+
literal|"'selected': ["
operator|+
literal|"'http:\\/\\/www.w3.org\\/2000\\/01\\/rdf-schema#label',"
operator|+
literal|"'http:\\/\\/www.w3.org\\/1999\\/02\\/22-rdf-syntax-ns#type',"
operator|+
literal|"'http:\\/\\/dbpedia.org\\/ontology\\/birthDate',"
operator|+
literal|"'http:\\/\\/dbpedia.org\\/ontology\\/deathDate'],"
operator|+
literal|"'offset': '0',"
operator|+
literal|"'limit': '3',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'range', "
operator|+
literal|"'field': 'http:\\/\\/dbpedia.org\\/ontology\\/birthDate', "
operator|+
literal|"'lowerBound': '1946-01-01T00:00:00.000Z',"
operator|+
literal|"'upperBound': '1946-12-31T23:59:59.999Z',"
operator|+
literal|"'inclusive': true,"
operator|+
literal|"'datatype': 'xsd:dateTime'"
operator|+
literal|"},{ "
operator|+
literal|"'type': 'reference', "
operator|+
literal|"'field': 'http:\\/\\/www.w3.org\\/1999\\/02\\/22-rdf-syntax-ns#type', "
operator|+
literal|"'value': 'http:\\/\\/dbpedia.org\\/ontology\\/Person', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
comment|//list of expected results
literal|"http://dbpedia.org/resource/Bill_Clinton"
argument_list|,
literal|"http://dbpedia.org/resource/George_W._Bush"
argument_list|,
literal|"http://dbpedia.org/resource/Donald_Trump"
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
comment|//list of required fields for results
literal|"http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
argument_list|,
literal|"http://www.w3.org/2000/01/rdf-schema#label"
argument_list|,
literal|"http://dbpedia.org/ontology/birthDate"
argument_list|)
argument_list|)
decl_stmt|;
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
comment|//cities with more than 1 million inhabitants and an altitude over
comment|//1000 meter
name|test
operator|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{"
operator|+
literal|"'selected': ["
operator|+
literal|"'http:\\/\\/www.w3.org\\/2000\\/01\\/rdf-schema#label',"
operator|+
literal|"'http:\\/\\/dbpedia.org\\/ontology\\/populationTotal',"
operator|+
literal|"'http:\\/\\/www.w3.org\\/2003\\/01\\/geo\\/wgs84_pos#alt'],"
operator|+
literal|"'offset': '0',"
operator|+
literal|"'limit': '3',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'range', "
operator|+
literal|"'field': 'http:\\/\\/dbpedia.org\\/ontology\\/populationTotal', "
operator|+
literal|"'lowerBound': 1000000,"
operator|+
literal|"'inclusive': true,"
operator|+
literal|"'datatype': 'xsd:long'"
operator|+
literal|"},{ "
operator|+
literal|"'type': 'range', "
operator|+
literal|"'field': 'http:\\/\\/www.w3.org\\/2003\\/01\\/geo\\/wgs84_pos#alt', "
operator|+
literal|"'lowerBound': 1000,"
operator|+
literal|"'inclusive': false,"
operator|+
literal|"},{ "
operator|+
literal|"'type': 'reference', "
operator|+
literal|"'field': 'http:\\/\\/www.w3.org\\/1999\\/02\\/22-rdf-syntax-ns#type', "
operator|+
literal|"'value': 'http:\\/\\/dbpedia.org\\/ontology\\/City', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Mexico_City"
argument_list|,
literal|"http://dbpedia.org/resource/Bogot%C3%A1"
argument_list|,
literal|"http://dbpedia.org/resource/Quito"
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://www.w3.org/2000/01/rdf-schema#label"
argument_list|,
literal|"http://dbpedia.org/ontology/populationTotal"
argument_list|,
literal|"http://www.w3.org/2003/01/geo/wgs84_pos#alt"
argument_list|)
argument_list|)
expr_stmt|;
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFieldQueryTextConstraints
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{ "
operator|+
literal|"'selected': ["
operator|+
literal|"'http:\\/\\/www.w3.org\\/2000\\/01\\/rdf-schema#label'],"
operator|+
literal|"'offset': '0',"
operator|+
literal|"'limit': '3',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
literal|"'language': 'de', "
operator|+
literal|"'patternType': 'wildcard', "
operator|+
literal|"'text': 'Frankf*', "
operator|+
literal|"'field': 'http:\\/\\/www.w3.org\\/2000\\/01\\/rdf-schema#label' "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
comment|//list of expected results
literal|"http://dbpedia.org/resource/Frankfurt"
argument_list|,
literal|"http://dbpedia.org/resource/Eintracht_Frankfurt"
argument_list|,
literal|"http://dbpedia.org/resource/Frankfort,_Kentucky"
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
comment|//list of required fields for results
literal|"http://www.w3.org/2000/01/rdf-schema#label"
argument_list|)
argument_list|)
decl_stmt|;
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
name|test
operator|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{ "
operator|+
literal|"'selected': ["
operator|+
literal|"'http:\\/\\/www.w3.org\\/2000\\/01\\/rdf-schema#label'],"
operator|+
literal|"'offset': '0',"
operator|+
literal|"'limit': '3',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
literal|"'text': ['Frankfurt','Main','Flughafen'], "
operator|+
literal|"'field': 'http:\\/\\/www.w3.org\\/2000\\/01\\/rdf-schema#label' "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
comment|//list of expected results
literal|"http://dbpedia.org/resource/Frankfurt_Airport"
argument_list|,
literal|"http://dbpedia.org/resource/Frankfurt"
argument_list|,
literal|"http://dbpedia.org/resource/Airport"
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
comment|//list of required fields for results
literal|"http://www.w3.org/2000/01/rdf-schema#label"
argument_list|)
argument_list|)
expr_stmt|;
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFieldQueryValueConstraints
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|FieldQueryTestCase
name|test
init|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{ "
operator|+
literal|"'selected': ["
operator|+
literal|"'http:\\/\\/www.w3.org\\/2000\\/01\\/rdf-schema#label'],"
operator|+
literal|"'offset': '0',"
operator|+
literal|"'limit': '3',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'value',"
operator|+
literal|"'value': '34',"
operator|+
literal|"'field': 'http:\\/\\/www.w3.org\\/2003\\/01\\/geo\\/wgs84_pos#alt',"
operator|+
literal|"'datatype': 'xsd:int'"
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
comment|//list of expected results
literal|"http://dbpedia.org/resource/Berlin"
argument_list|,
literal|"http://dbpedia.org/resource/Baghdad"
argument_list|,
literal|"http://dbpedia.org/resource/Orlando,_Florida"
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
comment|//list of required fields for results
literal|"http://www.w3.org/2000/01/rdf-schema#label"
argument_list|)
argument_list|)
decl_stmt|;
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
comment|// now the same query but with no datatype
name|test
operator|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{ "
operator|+
literal|"'selected': ["
operator|+
literal|"'http:\\/\\/www.w3.org\\/2000\\/01\\/rdf-schema#label'],"
operator|+
literal|"'offset': '0',"
operator|+
literal|"'limit': '3',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'value',"
operator|+
literal|"'value': '34',"
operator|+
comment|//NOTE this is a JSON String!
literal|"'field': 'http:\\/\\/www.w3.org\\/2003\\/01\\/geo\\/wgs84_pos#alt',"
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|//we expect no results, because the datatype should be xsd:string
comment|//a third time the same query (without a datatype), but now we parse a
comment|//JSON number as value
name|test
operator|=
operator|new
name|FieldQueryTestCase
argument_list|(
literal|"{ "
operator|+
literal|"'selected': ["
operator|+
literal|"'http:\\/\\/www.w3.org\\/2000\\/01\\/rdf-schema#label'],"
operator|+
literal|"'offset': '0',"
operator|+
literal|"'limit': '3',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'value',"
operator|+
literal|"'value': 34,"
operator|+
literal|"'field': 'http:\\/\\/www.w3.org\\/2003\\/01\\/geo\\/wgs84_pos#alt',"
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
comment|//list of expected results
literal|"http://dbpedia.org/resource/Berlin"
argument_list|,
literal|"http://dbpedia.org/resource/Baghdad"
argument_list|,
literal|"http://dbpedia.org/resource/Orlando,_Florida"
argument_list|)
argument_list|)
expr_stmt|;
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

