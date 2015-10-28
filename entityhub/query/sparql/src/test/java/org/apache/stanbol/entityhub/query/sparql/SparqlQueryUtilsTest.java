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
name|query
operator|.
name|sparql
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
name|assertEquals
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
name|assertTrue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|List
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
name|core
operator|.
name|query
operator|.
name|DefaultQueryFactory
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
name|query
operator|.
name|sparql
operator|.
name|SparqlFieldQuery
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
name|query
operator|.
name|sparql
operator|.
name|SparqlFieldQueryFactory
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
name|query
operator|.
name|sparql
operator|.
name|SparqlQueryUtils
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
name|query
operator|.
name|FieldQuery
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
name|query
operator|.
name|FieldQueryFactory
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
name|query
operator|.
name|TextConstraint
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
name|query
operator|.
name|TextConstraint
operator|.
name|PatternType
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
name|Test
import|;
end_import

begin_class
specifier|public
class|class
name|SparqlQueryUtilsTest
block|{
annotation|@
name|Test
specifier|public
name|void
name|testCreateFullTextQueryString
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|keywords
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"test"
argument_list|,
literal|"keyword"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"\"test\" OR \"keyword\""
argument_list|,
name|SparqlQueryUtils
operator|.
name|createFullTextQueryString
argument_list|(
name|keywords
argument_list|)
argument_list|)
expr_stmt|;
name|keywords
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"test keyword"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"(\"test\" AND \"keyword\")"
argument_list|,
name|SparqlQueryUtils
operator|.
name|createFullTextQueryString
argument_list|(
name|keywords
argument_list|)
argument_list|)
expr_stmt|;
name|keywords
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"'test' \"keyword\""
argument_list|)
expr_stmt|;
comment|//NOTE: changed implementation to remove none word chars
name|assertEquals
argument_list|(
literal|"(\"test\" AND \"keyword\")"
argument_list|,
name|SparqlQueryUtils
operator|.
name|createFullTextQueryString
argument_list|(
name|keywords
argument_list|)
argument_list|)
expr_stmt|;
name|keywords
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"1 Alpha ? Numeric Test ."
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"(\"1\" AND \"Alpha\" AND \"Numeric\" AND \"Test\")"
argument_list|,
name|SparqlQueryUtils
operator|.
name|createFullTextQueryString
argument_list|(
name|keywords
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests for grammar encoding (STANBOL-877)      */
annotation|@
name|Test
specifier|public
name|void
name|testGrammarEncodning
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"test\\'s"
argument_list|,
name|SparqlQueryUtils
operator|.
name|getGrammarEscapedValue
argument_list|(
literal|"test's"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"test\\\"s"
argument_list|,
name|SparqlQueryUtils
operator|.
name|getGrammarEscapedValue
argument_list|(
literal|"test\"s"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"new\\\nline"
argument_list|,
name|SparqlQueryUtils
operator|.
name|getGrammarEscapedValue
argument_list|(
literal|"new\nline"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Utility function for generating SparqlQuery      * @param textWithDoubleQuote      * @param patternType      */
specifier|private
name|String
name|generateSparqlQueryString
parameter_list|(
name|String
name|textWithDoubleQuote
parameter_list|,
name|PatternType
name|patternType
parameter_list|)
block|{
name|int
name|limit
init|=
literal|10
decl_stmt|;
name|FieldQueryFactory
name|qf
init|=
name|DefaultQueryFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|FieldQuery
name|query
init|=
name|qf
operator|.
name|createFieldQuery
argument_list|()
decl_stmt|;
name|String
name|DEFAULT_AUTOCOMPLETE_SEARCH_FIELD
init|=
name|NamespaceEnum
operator|.
name|rdfs
operator|+
literal|"label"
decl_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|selectedFields
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|selectedFields
operator|.
name|add
argument_list|(
name|DEFAULT_AUTOCOMPLETE_SEARCH_FIELD
argument_list|)
expr_stmt|;
name|query
operator|.
name|addSelectedFields
argument_list|(
name|selectedFields
argument_list|)
expr_stmt|;
name|query
operator|.
name|setConstraint
argument_list|(
name|DEFAULT_AUTOCOMPLETE_SEARCH_FIELD
argument_list|,
operator|new
name|TextConstraint
argument_list|(
name|textWithDoubleQuote
argument_list|,
name|patternType
argument_list|,
literal|true
argument_list|,
literal|"en"
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|query
operator|.
name|setLimit
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|query
operator|.
name|setOffset
argument_list|(
literal|0
argument_list|)
expr_stmt|;
specifier|final
name|SparqlFieldQuery
name|sparqlQuery
init|=
name|SparqlFieldQueryFactory
operator|.
name|getSparqlFieldQuery
argument_list|(
name|query
argument_list|)
decl_stmt|;
return|return
name|SparqlQueryUtils
operator|.
name|createSparqlSelectQuery
argument_list|(
name|sparqlQuery
argument_list|,
literal|false
argument_list|,
name|limit
argument_list|,
name|SparqlEndpointTypeEnum
operator|.
name|Standard
argument_list|)
return|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testDoubleQuotes
parameter_list|()
block|{
name|String
name|testString
init|=
literal|"double\"quote"
decl_stmt|;
name|String
name|queryNone
init|=
name|generateSparqlQueryString
argument_list|(
name|testString
argument_list|,
name|PatternType
operator|.
name|none
argument_list|)
decl_stmt|;
comment|//the quote have to be double escaped before checked with .contains
name|assertTrue
argument_list|(
name|queryNone
operator|.
name|contains
argument_list|(
name|testString
operator|.
name|replaceAll
argument_list|(
literal|"\\\""
argument_list|,
literal|"\\\\\""
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|queryRegex
init|=
name|generateSparqlQueryString
argument_list|(
name|testString
argument_list|,
name|PatternType
operator|.
name|regex
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|queryRegex
operator|.
name|contains
argument_list|(
name|testString
operator|.
name|replaceAll
argument_list|(
literal|"\\\""
argument_list|,
literal|"\\\\\""
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests word level matching for {@link TextConstraint}s (STANBOL-1277)      */
annotation|@
name|Test
specifier|public
name|void
name|testMultiWordTextConstraints
parameter_list|()
block|{
comment|//queries for a TextConstraint with {text1} or {text2} in the languages
comment|// {lang1} or {lang2} are expected to look like:
comment|//
comment|//    select ?entity, ?label where {
comment|//        ?entity rdfs:label ?label
comment|//        FILTER((regex(str(?label),"\\b{text1}\\b","i") || regex(str(?label),"\\b{text2}\\b","i"))
comment|//&& ((lang(?label) = "{lang1}") || (lang(?label) = "{lang2}"))) .
comment|//    }
comment|//first test a pattern type NONE
name|SparqlFieldQuery
name|query
init|=
name|SparqlFieldQueryFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createFieldQuery
argument_list|()
decl_stmt|;
name|query
operator|.
name|setConstraint
argument_list|(
literal|"urn:field4"
argument_list|,
operator|new
name|TextConstraint
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Global"
argument_list|,
literal|"Toy"
argument_list|)
argument_list|,
name|PatternType
operator|.
name|none
argument_list|,
literal|false
argument_list|,
literal|"en"
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|queryString
init|=
name|SparqlQueryUtils
operator|.
name|createSparqlSelectQuery
argument_list|(
name|query
argument_list|,
literal|true
argument_list|,
literal|0
argument_list|,
name|SparqlEndpointTypeEnum
operator|.
name|Standard
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|queryString
operator|.
name|contains
argument_list|(
literal|"regex(str(?tmp1),\"\\\\bGlobal\\\\b\",\"i\") "
operator|+
literal|"|| regex(str(?tmp1),\"\\\\bToy\\\\b\",\"i\")"
argument_list|)
argument_list|)
expr_stmt|;
comment|//also test for pattern type WILDCARD
name|query
operator|=
name|SparqlFieldQueryFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createFieldQuery
argument_list|()
expr_stmt|;
name|query
operator|.
name|setConstraint
argument_list|(
literal|"urn:field4"
argument_list|,
operator|new
name|TextConstraint
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Glo?al"
argument_list|,
literal|"Toy"
argument_list|)
argument_list|,
name|PatternType
operator|.
name|wildcard
argument_list|,
literal|false
argument_list|,
literal|"en"
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|queryString
operator|=
name|SparqlQueryUtils
operator|.
name|createSparqlSelectQuery
argument_list|(
name|query
argument_list|,
literal|true
argument_list|,
literal|0
argument_list|,
name|SparqlEndpointTypeEnum
operator|.
name|Standard
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|queryString
operator|.
name|contains
argument_list|(
literal|"regex(str(?tmp1),\"\\\\bGlo.al\\\\b\",\"i\") "
operator|+
literal|"|| regex(str(?tmp1),\"\\\\bToy\\\\b\",\"i\")"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

