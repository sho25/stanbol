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
name|servicesapi
operator|.
name|defaults
operator|.
name|NamespaceEnum
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
name|servicesapi
operator|.
name|defaults
operator|.
name|SpecialFieldEnum
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
literal|"http://dbpedia.org/resource/Paris_Hilton"
argument_list|,
literal|"http://dbpedia.org/resource/University_of_Paris"
argument_list|)
argument_list|)
decl_stmt|;
comment|//and more
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
comment|//expected:
comment|// Paris: 16.905037
comment|// University_of_Paris: 10.565648
comment|// Paris_Hilton, Salon_(Paris), Paris_M%C3%A9tro, Paris_Opera,
comment|//    Paris_Saint-Germain_F.C., Paris_Commune, Paris_Masters: 8.452518
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
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Saint-Germain_F.C."
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
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Saint-Germain_F.C."
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
literal|"http://dbpedia.org/resource/Paris_Saint-Germain_F.C."
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Hilton"
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/University_of_Paris"
argument_list|,
literal|"http://dbpedia.org/resource/Paris"
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
name|test
operator|.
name|setLimit
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|test
operator|.
name|setOffset
argument_list|(
literal|2
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
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Commune"
argument_list|,
literal|"http://dbpedia.org/resource/Paris-Charles_de_Gaulle_Airport"
argument_list|)
argument_list|)
decl_stmt|;
name|test
operator|.
name|setLimit
argument_list|(
literal|10
argument_list|)
expr_stmt|;
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
name|test
operator|.
name|setLimit
argument_list|(
literal|3
argument_list|)
expr_stmt|;
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
comment|//now in Italian (expects the same as the query with no language constriants
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
literal|"http://dbpedia.org/resource/Paris_M%C3%A9tro"
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Peace_Conference,_1919"
argument_list|,
literal|"http://dbpedia.org/resource/Paris-Charles_de_Gaulle_Airport"
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
name|test
operator|.
name|setLimit
argument_list|(
literal|10
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
literal|"http://dbpedia.org/resource/Paris_Saint-Germain_F.C."
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Opera"
argument_list|,
literal|"http://dbpedia.org/resource/Stade_Fran%C3%A7ais"
argument_list|,
literal|"http://dbpedia.org/resource/Institut_d'%C3%89tudes_Politiques_de_Paris"
argument_list|)
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
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Peace_Conference,_1919"
argument_list|,
literal|"http://dbpedia.org/resource/Paris-Charles_de_Gaulle_Airport"
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
name|test
operator|.
name|setLimit
argument_list|(
literal|10
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
literal|"http://dbpedia.org/resource/Central_Intelligence_Agency"
argument_list|,
literal|"http://dbpedia.org/resource/The_World_Factbook"
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
name|setField
argument_list|(
literal|"http://dbpedia.org/ontology/surfaceForm"
argument_list|)
expr_stmt|;
name|test
operator|.
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
name|test
operator|.
name|setLimit
argument_list|(
literal|5
argument_list|)
expr_stmt|;
comment|//there are a lot of those
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
literal|"http://dbpedia.org/resource/Central_Intelligence_Agency"
argument_list|,
literal|"http://dbpedia.org/resource/County_Kerry"
argument_list|,
comment|//Ciarraí (county)
literal|"http://dbpedia.org/resource/Vitamin_C"
argument_list|,
comment|//Ciamin
literal|"http://dbpedia.org/resource/Ciara"
argument_list|,
literal|"http://dbpedia.org/resource/The_World_Factbook"
argument_list|)
argument_list|)
expr_stmt|;
comment|//CIA World Factbook
name|test
operator|.
name|setField
argument_list|(
literal|"http://dbpedia.org/ontology/surfaceForm"
argument_list|)
expr_stmt|;
name|test
operator|.
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
name|test
operator|.
name|setLimit
argument_list|(
literal|10
argument_list|)
expr_stmt|;
comment|//there are a lot of those
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
literal|"http://dbpedia.org/resource/Hypertext_Transfer_Protocol"
argument_list|,
literal|"http://dbpedia.org/resource/File_Transfer_Protocol"
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
name|test
operator|.
name|setLimit
argument_list|(
literal|100
argument_list|)
expr_stmt|;
comment|//there a a lot of those
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
literal|"http://dbpedia.org/resource/Program_and_System_Information_Protocol"
argument_list|,
literal|"http://dbpedia.org/resource/Hypertext_Transfer_Protocol"
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
name|test
operator|.
name|setLimit
argument_list|(
literal|100
argument_list|)
expr_stmt|;
comment|//there a a lot of those
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
name|testFindSpecificFieldQuery
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//TODO: there is no other text field as rdfs:label in the dbpedia
comment|//default dataset :(
block|}
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
literal|"'limit': '5',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'range', "
operator|+
literal|"'field': 'http:\\/\\/dbpedia.org\\/ontology\\/birthDate', "
operator|+
literal|"'lowerBound': '1946-07-01T00:00:00.000Z',"
operator|+
literal|"'upperBound': '1946-08-31T23:59:59.999Z',"
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
literal|"'value': 'http:\\/\\/dbpedia.org\\/ontology\\/MusicalArtist', "
operator|+
literal|"}]"
operator|+
literal|"}"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
comment|//list of expected results (3/5 found)
literal|"http://dbpedia.org/resource/Linda_Ronstadt"
argument_list|,
literal|"http://dbpedia.org/resource/Barry_Gibb"
argument_list|,
literal|"http://dbpedia.org/resource/Jimmy_Webb"
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
comment|//cities with more than 3 million inhabitants and an altitude over
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
literal|"'limit': '5',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'range', "
operator|+
literal|"'field': 'http:\\/\\/dbpedia.org\\/ontology\\/populationTotal', "
operator|+
literal|"'lowerBound': 3000000,"
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
literal|"http://dbpedia.org/resource/Nairobi"
argument_list|,
literal|"http://dbpedia.org/resource/Kunming"
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
literal|"'limit': '10',"
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
literal|"http://dbpedia.org/resource/Frankfurt_Airport"
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
literal|"'limit': '10',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
literal|"'text': ['Frankfurt','Main','Flughafen'], "
operator|+
literal|"'language': 'de', "
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
literal|"http://dbpedia.org/resource/Maine"
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
name|testMultiWordWildcardTextConstraints
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//this is specially for issue described in the first comment of
comment|//STANBOL-607
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
literal|"'text': 'Frankf* am Main', "
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
literal|"'limit': '5',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'value',"
operator|+
literal|"'value': '64',"
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
literal|"http://dbpedia.org/resource/Manchester,_New_Hampshire"
argument_list|,
literal|"http://dbpedia.org/resource/Cornwall,_Ontario"
argument_list|,
literal|"http://dbpedia.org/resource/Lexington,_Massachusetts"
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
literal|"'value': '64',"
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
literal|"'value': 64,"
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
literal|"http://dbpedia.org/resource/Manchester,_New_Hampshire"
argument_list|,
literal|"http://dbpedia.org/resource/Cornwall,_Ontario"
argument_list|,
literal|"http://dbpedia.org/resource/Lexington,_Massachusetts"
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
name|testFieldQueryMultiReferenceConstraints
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
literal|"'rdfs:label',"
operator|+
literal|"'rdf:type'],"
operator|+
literal|"'offset': '0',"
operator|+
literal|"'limit': '5',"
operator|+
literal|"'constraints': ["
operator|+
literal|"{"
operator|+
literal|"'type': 'text',"
operator|+
literal|"'patternType': 'wildcard',"
operator|+
literal|"'text': ['ford'],"
operator|+
literal|"'field': 'rdfs:label',"
operator|+
literal|"},{"
operator|+
literal|"'type': 'reference',"
operator|+
literal|"'value': ['dbp-ont:Organisation','dbp-ont:OfficeHolder'],"
operator|+
literal|"'field': 'rdf:type',"
operator|+
literal|"}"
operator|+
literal|"]"
operator|+
literal|"}"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
comment|//list of expected results
literal|"http://dbpedia.org/resource/Ford_Motor_Company"
argument_list|,
literal|"http://dbpedia.org/resource/Gerald_Ford"
argument_list|,
comment|//this third result is important, as we would get different
comment|//without the reference constraint
literal|"http://dbpedia.org/resource/Ford_Foundation"
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
comment|//list of required fields for results
literal|"http://www.w3.org/2000/01/rdf-schema#label"
argument_list|,
literal|"http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
argument_list|)
argument_list|)
decl_stmt|;
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
name|testFieldQueryMultipleValueConstraints
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//munich is on geo:alt 519 (will change to 518 on dbpedia 3.7)
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
literal|"'value': ['1288','519'],"
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
literal|"http://dbpedia.org/resource/Munich"
argument_list|,
comment|//519
literal|"http://dbpedia.org/resource/Salt_Lake_City"
argument_list|)
argument_list|,
comment|//1288
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
comment|//a 2nd time the same query (without a datatype), but now we parse a
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
literal|"'value': [1288,519],"
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
literal|"http://dbpedia.org/resource/Munich"
argument_list|,
comment|//519
literal|"http://dbpedia.org/resource/Salt_Lake_City"
argument_list|)
argument_list|)
expr_stmt|;
comment|//1288
comment|//now execute the test
name|executeQuery
argument_list|(
name|test
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests that full text queries are possible by using the       * {@link SpecialFieldEnum#fullText} field (STANBOL-596)       */
annotation|@
name|Test
specifier|public
name|void
name|testFullTextQuery
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
literal|"'limit': '5',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text',"
operator|+
literal|"'patternType': 'wildcard',"
operator|+
literal|"'text': ['physicist'],"
operator|+
literal|"'field': 'entityhub-query:fullText'"
operator|+
literal|"},{"
operator|+
literal|"'type': 'reference',"
operator|+
literal|"'value': ['dbp-ont:Scientist'],"
operator|+
literal|"'field': 'rdf:type',"
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
literal|"http://dbpedia.org/resource/Albert_Einstein"
argument_list|,
literal|"http://dbpedia.org/resource/Isaac_Newton"
argument_list|,
literal|"http://dbpedia.org/resource/Galileo_Galilei"
argument_list|,
literal|"http://dbpedia.org/resource/Nikola_Tesla"
argument_list|,
literal|"http://dbpedia.org/resource/Stephen_Hawking"
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
block|}
comment|/**      * Tests searches for references in the semantic context field (the      * field containing all references to other entities (STANBOL-597)       * @throws IOException      * @throws JSONException      */
annotation|@
name|Test
specifier|public
name|void
name|testSemanticContextQuery
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
literal|"'limit': '5',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'reference',"
operator|+
literal|"'value': ["
operator|+
literal|"'http://dbpedia.org/resource/Category:Capitals_in_Europe',"
operator|+
literal|"'http://dbpedia.org/resource/Category:Host_cities_of_the_Summer_Olympic_Games'"
operator|+
literal|"],"
operator|+
literal|"'field': 'entityhub-query:references',"
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
literal|"http://dbpedia.org/resource/London"
argument_list|,
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
literal|"http://dbpedia.org/resource/Moscow"
argument_list|,
literal|"http://dbpedia.org/resource/Rome"
argument_list|,
literal|"http://dbpedia.org/resource/Helsinki"
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
block|}
comment|/**      * Tests ValueConstraint MODE "any" and "all" queries (STANBOL-595)       * @throws IOException      * @throws JSONException      */
annotation|@
name|Test
specifier|public
name|void
name|testConstraintValueModeQuery
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//First with mode = 'any' -> combine Entity Ranking with types
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
literal|"'limit': '20',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'reference',"
operator|+
literal|"'value': ["
operator|+
literal|"'http:\\/\\/dbpedia.org\\/resource\\/Category:Capitals_in_Europe',"
operator|+
literal|"'http:\\/\\/dbpedia.org\\/resource\\/Category:Host_cities_of_the_Summer_Olympic_Games',"
operator|+
literal|"'http:\\/\\/dbpedia.org\\/ontology\\/City'"
operator|+
literal|"],"
operator|+
literal|"'field': 'entityhub-query:references',"
operator|+
literal|"'mode': 'any'"
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
literal|"http://dbpedia.org/resource/Amsterdam"
argument_list|,
literal|"http://dbpedia.org/resource/London"
argument_list|,
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
literal|"http://dbpedia.org/resource/Rome"
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
comment|//Second query for Entities that do have relations to all three
comment|//Entities (NOTE: the dbp-ont:City type is missing for most of the
comment|//members of the two categories used in this example!)
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
literal|"'limit': '5',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'reference',"
operator|+
literal|"'value': ["
operator|+
literal|"'http:\\/\\/dbpedia.org\\/resource\\/Category:Capitals_in_Europe',"
operator|+
literal|"'http:\\/\\/dbpedia.org\\/resource\\/Category:Host_cities_of_the_Summer_Olympic_Games',"
operator|+
literal|"'http:\\/\\/dbpedia.org\\/ontology\\/City'"
operator|+
literal|"],"
operator|+
literal|"'field': 'entityhub-query:references',"
operator|+
literal|"'mode': 'all'"
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
literal|"http://dbpedia.org/resource/Amsterdam"
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
comment|/**      * Tests (1) similarity searches and (2) that the full text field is supported      * for those (STANBOL-589 and STANBOL-596)      * @throws IOException      * @throws JSONException      */
annotation|@
name|Test
specifier|public
name|void
name|testSimilaritySearch
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//searches Places with "Wolfgang Amadeus Mozart" as context
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
literal|"'limit': '5',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'reference',"
operator|+
literal|"'value': 'http:\\/\\/dbpedia.org\\/ontology\\/Place',"
operator|+
literal|"'field': 'http:\\/\\/www.w3.org\\/1999\\/02\\/22-rdf-syntax-ns#type',"
operator|+
literal|"},{"
operator|+
literal|"'type': 'similarity',"
operator|+
literal|"'context': 'Wolfgang Amadeus Mozart',"
operator|+
literal|"'field': 'http:\\/\\/stanbol.apache.org\\/ontology\\/entityhub\\/query#fullText',"
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
literal|"http://dbpedia.org/resource/Salzburg"
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
block|}
annotation|@
name|Test
specifier|public
name|void
name|testBoostAndProximityRanking
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
comment|//test features added with STANBOL-1105, STANBOL-1106
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
literal|"'limit': '10',"
operator|+
literal|"'constraints': [{ "
operator|+
literal|"'type': 'text', "
operator|+
literal|"'text': ['Frankfurt','Main','Flughafen'], "
operator|+
literal|"'language': ['de', 'en'], "
operator|+
literal|"'field': 'http:\\/\\/www.w3.org\\/2000\\/01\\/rdf-schema#label', "
operator|+
literal|"'boost': 12.34,"
operator|+
literal|"'proximityRanking': true"
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
comment|//NOTE: Main is no longer part of the new default data index
comment|//      with only 26k (instead of 43k) entities.
literal|"http://dbpedia.org/resource/Goethe_University_Frankfurt"
argument_list|,
literal|"http://dbpedia.org/resource/Frankfurt_(Oder)"
argument_list|,
literal|"http://dbpedia.org/resource/FSV_Frankfurt"
argument_list|,
literal|"http://dbpedia.org/resource/Eintracht_Frankfurt"
argument_list|,
literal|"http://dbpedia.org/resource/1._FFC_Frankfurt"
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
block|}
block|}
end_class

end_unit

