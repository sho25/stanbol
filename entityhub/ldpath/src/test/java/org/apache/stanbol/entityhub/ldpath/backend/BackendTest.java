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
name|ldpath
operator|.
name|backend
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
name|entityhub
operator|.
name|ldpath
operator|.
name|LDPathUtils
operator|.
name|getReader
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertNotNull
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
name|Collection
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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|stanbol
operator|.
name|entityhub
operator|.
name|ldpath
operator|.
name|impl
operator|.
name|LDPathTestBase
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

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|LDPath
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|backend
operator|.
name|RDFBackend
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|model
operator|.
name|programs
operator|.
name|Program
import|;
end_import

begin_class
specifier|public
class|class
name|BackendTest
extends|extends
name|LDPathTestBase
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|BackendTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|CONTEXT_PARIS
init|=
name|DBPEDIA
operator|+
literal|"Paris"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|DBPEDIA_TEST_PROGRAM
decl_stmt|;
static|static
block|{
name|StringBuilder
name|builder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
comment|//TODO:write LDPath test statement (or load it from test resources
name|builder
operator|.
name|append
argument_list|(
literal|"@prefix geo :<http://www.w3.org/2003/01/geo/wgs84_pos#> ;"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
literal|"title = rdfs:label :: xsd:string;"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
literal|"title_en = rdfs:label[@en] :: xsd:string;"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
literal|"type = rdf:type :: xsd:anyURI;"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
literal|"all = * :: xsd:string;"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
literal|"lat = geo:lat :: xsd:double;"
argument_list|)
expr_stmt|;
name|DBPEDIA_TEST_PROGRAM
operator|=
name|builder
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|EXPECTED_RESULTS_PARIS
decl_stmt|;
static|static
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|expected
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|expected
operator|.
name|put
argument_list|(
literal|"title_en"
argument_list|,
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Paris"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|expected
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Paris"
argument_list|,
literal|"Parijs"
argument_list|,
literal|"Parigi"
argument_list|,
literal|"Pariisi"
argument_list|,
literal|"巴黎"
argument_list|,
literal|"Париж"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// NOTE: LDPath uses String to represent anyUri
name|expected
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://www.w3.org/2002/07/owl#Thing"
argument_list|,
literal|"http://dbpedia.org/ontology/Place"
argument_list|,
literal|"http://dbpedia.org/ontology/PopulatedPlace"
argument_list|,
literal|"http://dbpedia.org/ontology/Settlement"
argument_list|,
literal|"http://www.opengis.net/gml/_Feature"
argument_list|,
literal|"http://dbpedia.org/ontology/Settlement"
argument_list|,
literal|"http://www.opengis.net/gml/_Feature"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//Add all previous and some additional to test the WIldcard implementation
name|Collection
argument_list|<
name|Object
argument_list|>
name|allValues
init|=
operator|new
name|HashSet
argument_list|<
name|Object
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Collection
argument_list|<
name|?
argument_list|>
name|values
range|:
name|expected
operator|.
name|values
argument_list|()
control|)
block|{
name|allValues
operator|.
name|addAll
argument_list|(
name|values
argument_list|)
expr_stmt|;
block|}
name|allValues
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Category:Capitals_in_Europe"
argument_list|,
literal|"http://dbpedia.org/resource/Category:Host_cities_of_the_Summer_Olympic_Games"
argument_list|,
literal|"2.350833"
argument_list|,
literal|"0.81884754"
argument_list|,
literal|"2193031"
argument_list|)
argument_list|)
expr_stmt|;
name|expected
operator|.
name|put
argument_list|(
literal|"all"
argument_list|,
name|allValues
argument_list|)
expr_stmt|;
name|expected
operator|.
name|put
argument_list|(
literal|"lat"
argument_list|,
name|Collections
operator|.
name|emptySet
argument_list|()
argument_list|)
expr_stmt|;
name|EXPECTED_RESULTS_PARIS
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|expected
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|CONTEXT_HARVARD_ALUMNI
init|=
name|DBPEDIA
operator|+
literal|"Category:Harvard_University_alumni"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|CATEGORIES_TEST_PROGRAM
decl_stmt|;
static|static
block|{
name|StringBuilder
name|builder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
comment|//TODO:write LDPath test statement (or load it from test resources
name|builder
operator|.
name|append
argument_list|(
literal|"name = rdfs:label :: xsd:string;"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
literal|"parent = skos:broader :: xsd:anyURI;"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
literal|"childs = ^skos:broader :: xsd:anyURI;"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
literal|"members = ^<http://purl.org/dc/terms/subject> :: xsd:anyURI;"
argument_list|)
expr_stmt|;
name|CATEGORIES_TEST_PROGRAM
operator|=
name|builder
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|EXPECTED_HARVARD_ALUMNI
decl_stmt|;
static|static
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|expected
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|expected
operator|.
name|put
argument_list|(
literal|"name"
argument_list|,
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Harvard University alumni"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|expected
operator|.
name|put
argument_list|(
literal|"parent"
argument_list|,
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Category:Harvard_University_people"
argument_list|,
literal|"http://dbpedia.org/resource/Category:Alumni_by_university_or_college_in_Massachusetts"
argument_list|,
literal|"http://dbpedia.org/resource/Category:Ivy_League_alumni"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|expected
operator|.
name|put
argument_list|(
literal|"childs"
argument_list|,
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Category:John_F._Kennedy_School_of_Government_alumni"
argument_list|,
literal|"http://dbpedia.org/resource/Category:Harvard_Law_School_alumni"
argument_list|,
literal|"http://dbpedia.org/resource/Category:Harvard_Medical_School_alumni"
argument_list|,
literal|"http://dbpedia.org/resource/Category:Harvard_Business_School_alumni"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|expected
operator|.
name|put
argument_list|(
literal|"members"
argument_list|,
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"http://dbpedia.org/resource/Edward_Said"
argument_list|,
literal|"http://dbpedia.org/resource/Cole_Porter"
argument_list|,
literal|"http://dbpedia.org/resource/Theodore_Roosevelt"
argument_list|,
literal|"http://dbpedia.org/resource/Al_Gore"
argument_list|,
literal|"http://dbpedia.org/resource/T._S._Eliot"
argument_list|,
literal|"http://dbpedia.org/resource/Henry_Kissinger"
argument_list|,
literal|"http://dbpedia.org/resource/Robert_F._Kennedy"
argument_list|,
literal|"http://dbpedia.org/resource/Benjamin_Netanyahu"
argument_list|,
literal|"http://dbpedia.org/resource/Natalie_Portman"
argument_list|,
literal|"http://dbpedia.org/resource/John_F._Kennedy"
argument_list|,
literal|"http://dbpedia.org/resource/Michelle_Obama"
argument_list|,
literal|"http://dbpedia.org/resource/Jacques_Chirac"
argument_list|,
literal|"http://dbpedia.org/resource/Pierre_Trudeau"
argument_list|,
literal|"http://dbpedia.org/resource/Jack_Lemmon"
argument_list|,
literal|"http://dbpedia.org/resource/Franklin_D._Roosevelt"
argument_list|,
literal|"http://dbpedia.org/resource/John_Adams"
argument_list|)
comment|// and manny more
argument_list|)
argument_list|)
expr_stmt|;
name|EXPECTED_HARVARD_ALUMNI
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|expected
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|Collection
argument_list|<
name|String
argument_list|>
name|checkContexts
parameter_list|()
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
name|CONTEXT_PARIS
argument_list|,
name|CONTEXT_HARVARD_ALUMNI
argument_list|)
return|;
block|}
comment|/**      * Test {@link RDFBackend} implementation including WildCard      * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testLDPath
parameter_list|()
throws|throws
name|Exception
block|{
name|LDPath
argument_list|<
name|Object
argument_list|>
name|ldPath
init|=
operator|new
name|LDPath
argument_list|<
name|Object
argument_list|>
argument_list|(
name|backend
argument_list|)
decl_stmt|;
name|Program
argument_list|<
name|Object
argument_list|>
name|program
init|=
name|ldPath
operator|.
name|parseProgram
argument_list|(
name|getReader
argument_list|(
name|DBPEDIA_TEST_PROGRAM
argument_list|)
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"parsed Programm is null (Input: "
operator|+
name|DBPEDIA_TEST_PROGRAM
operator|+
literal|")"
argument_list|,
name|program
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"LDPath Programm:\n{}"
argument_list|,
name|program
operator|.
name|getPathExpression
argument_list|(
name|backend
argument_list|)
argument_list|)
expr_stmt|;
name|Object
name|context
init|=
name|backend
operator|.
name|createURI
argument_list|(
name|CONTEXT_PARIS
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|result
init|=
name|program
operator|.
name|execute
argument_list|(
name|backend
argument_list|,
name|context
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Results for {}:\n{}"
argument_list|,
name|CONTEXT_PARIS
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Assert LDPath Result for {}:"
argument_list|,
name|CONTEXT_PARIS
argument_list|)
expr_stmt|;
name|assertLDPathResult
argument_list|(
name|result
argument_list|,
name|EXPECTED_RESULTS_PARIS
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testInversePath
parameter_list|()
throws|throws
name|Exception
block|{
name|LDPath
argument_list|<
name|Object
argument_list|>
name|ldPath
init|=
operator|new
name|LDPath
argument_list|<
name|Object
argument_list|>
argument_list|(
name|backend
argument_list|)
decl_stmt|;
name|Program
argument_list|<
name|Object
argument_list|>
name|program
init|=
name|ldPath
operator|.
name|parseProgram
argument_list|(
name|getReader
argument_list|(
name|CATEGORIES_TEST_PROGRAM
argument_list|)
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"parsed Programm is null (Input: "
operator|+
name|CATEGORIES_TEST_PROGRAM
operator|+
literal|")"
argument_list|,
name|program
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"LDPath Programm:\n{}"
argument_list|,
name|program
operator|.
name|getPathExpression
argument_list|(
name|backend
argument_list|)
argument_list|)
expr_stmt|;
name|Object
name|context
init|=
name|backend
operator|.
name|createURI
argument_list|(
name|CONTEXT_HARVARD_ALUMNI
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|result
init|=
name|program
operator|.
name|execute
argument_list|(
name|backend
argument_list|,
name|context
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Results for {}:\n{}"
argument_list|,
name|CONTEXT_HARVARD_ALUMNI
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
literal|"The result of the LDPath execution MUST NOT be NULL "
operator|+
literal|"(entity: %s)"
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Assert LDPath Result for {}:"
argument_list|,
name|EXPECTED_HARVARD_ALUMNI
argument_list|)
expr_stmt|;
name|assertLDPathResult
argument_list|(
name|result
argument_list|,
name|EXPECTED_HARVARD_ALUMNI
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

