begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
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
name|yard
operator|.
name|solr
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
name|assertNotNull
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
name|assertNull
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOError
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
name|Arrays
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
name|ServiceLoader
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrServer
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
name|commons
operator|.
name|namespaceprefix
operator|.
name|NamespacePrefixService
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
name|commons
operator|.
name|solr
operator|.
name|IndexReference
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
name|commons
operator|.
name|solr
operator|.
name|SolrConstants
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
name|commons
operator|.
name|solr
operator|.
name|SolrServerAdapter
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
name|commons
operator|.
name|solr
operator|.
name|managed
operator|.
name|IndexMetadata
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
name|commons
operator|.
name|solr
operator|.
name|managed
operator|.
name|ManagedSolrServer
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
name|commons
operator|.
name|solr
operator|.
name|managed
operator|.
name|standalone
operator|.
name|StandaloneEmbeddedSolrServerProvider
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
name|commons
operator|.
name|solr
operator|.
name|managed
operator|.
name|standalone
operator|.
name|StandaloneManagedSolrServer
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
name|model
operator|.
name|Representation
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
name|model
operator|.
name|Text
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
name|QueryResultList
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
name|SimilarityConstraint
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
name|yard
operator|.
name|Yard
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
name|yard
operator|.
name|YardException
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
name|yard
operator|.
name|YardTest
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
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYard
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
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYardConfig
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
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

begin_comment
comment|/**  * This test uses the system property "basedir" to configure an embedded Solr Server. This property is set by  * the mvn surefire plugin. When using this Unit Test within a build environment that does not set this  * property one need to set it manually to the base directory of this module.  *<p>  *   * @author Rupert Westenthaler  *   */
end_comment

begin_class
specifier|public
class|class
name|SolrYardTest
extends|extends
name|YardTest
block|{
comment|/**      * The SolrYard used for the tests      */
specifier|private
specifier|static
name|Yard
name|yard
decl_stmt|;
comment|/**      * The SolrDirectoryManager also tested within this unit test      */
specifier|public
specifier|static
specifier|final
name|String
name|TEST_YARD_ID
init|=
literal|"testYard"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TEST_SOLR_CORE_NAME
init|=
literal|"test"
decl_stmt|;
specifier|protected
specifier|static
specifier|final
name|String
name|TEST_INDEX_REL_PATH
init|=
name|File
operator|.
name|separatorChar
operator|+
literal|"target"
operator|+
name|File
operator|.
name|separatorChar
operator|+
name|ManagedSolrServer
operator|.
name|DEFAULT_SOLR_DATA_DIR
decl_stmt|;
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
name|SolrYardTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|StandaloneEmbeddedSolrServerProvider
name|solrServerProvider
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
specifier|final
name|void
name|initYard
parameter_list|()
throws|throws
name|YardException
throws|,
name|IOException
block|{
comment|// get the working directory
comment|// use property substitution to test this feature!
name|String
name|prefix
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"basedir"
argument_list|)
operator|==
literal|null
condition|?
literal|"."
else|:
literal|"${basedir}"
decl_stmt|;
name|String
name|solrServerDir
init|=
name|prefix
operator|+
name|TEST_INDEX_REL_PATH
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Test Solr Server Directory: "
operator|+
name|solrServerDir
argument_list|)
expr_stmt|;
name|SolrYardConfig
name|config
init|=
operator|new
name|SolrYardConfig
argument_list|(
name|TEST_YARD_ID
argument_list|,
name|TEST_SOLR_CORE_NAME
argument_list|)
decl_stmt|;
name|config
operator|.
name|setName
argument_list|(
literal|"Solr Yard Test"
argument_list|)
expr_stmt|;
name|config
operator|.
name|setDescription
argument_list|(
literal|"The Solr Yard instance used to execute the Unit Tests defined for the Yard Interface"
argument_list|)
expr_stmt|;
name|config
operator|.
name|setAllowInitialisation
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|//for unit testing we want immidiate commits (required after STANBOL-1092
comment|// as the default changed to false)
name|config
operator|.
name|setImmediateCommit
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|//init the ManagedSolrServer used for the UnitTest
name|System
operator|.
name|setProperty
argument_list|(
name|ManagedSolrServer
operator|.
name|MANAGED_SOLR_DIR_PROPERTY
argument_list|,
name|solrServerDir
argument_list|)
expr_stmt|;
name|IndexReference
name|solrServerRef
init|=
name|IndexReference
operator|.
name|parse
argument_list|(
name|config
operator|.
name|getSolrServerLocation
argument_list|()
argument_list|)
decl_stmt|;
name|solrServerProvider
operator|=
name|StandaloneEmbeddedSolrServerProvider
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|SolrServer
name|server
init|=
name|solrServerProvider
operator|.
name|getSolrServer
argument_list|(
name|solrServerRef
argument_list|,
name|config
operator|.
name|isAllowInitialisation
argument_list|()
condition|?
name|config
operator|.
name|getIndexConfigurationName
argument_list|()
else|:
literal|null
argument_list|)
decl_stmt|;
comment|//Optional support for the nsPrefix service
specifier|final
name|NamespacePrefixService
name|nsPrefixService
decl_stmt|;
name|ServiceLoader
argument_list|<
name|NamespacePrefixService
argument_list|>
name|spsl
init|=
name|ServiceLoader
operator|.
name|load
argument_list|(
name|NamespacePrefixService
operator|.
name|class
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|NamespacePrefixService
argument_list|>
name|it
init|=
name|spsl
operator|.
name|iterator
argument_list|()
decl_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|nsPrefixService
operator|=
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|nsPrefixService
operator|=
literal|null
expr_stmt|;
block|}
name|yard
operator|=
operator|new
name|SolrYard
argument_list|(
name|server
argument_list|,
name|config
argument_list|,
name|nsPrefixService
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|Yard
name|getYard
parameter_list|()
block|{
return|return
name|yard
return|;
block|}
comment|/*      * Three unit tests that check that SolrYardConfig does throw IllegalArgumentExceptions when parsing an      * illegal parameters.      */
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testSolrYardConfigInitWithNullParams
parameter_list|()
block|{
operator|new
name|SolrYardConfig
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testSolrYardConfigInitWithNullUrl
parameter_list|()
block|{
operator|new
name|SolrYardConfig
argument_list|(
name|TEST_YARD_ID
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testSolrYardConfigInitWithNullID
parameter_list|()
block|{
operator|new
name|SolrYardConfig
argument_list|(
literal|null
argument_list|,
name|TEST_SOLR_CORE_NAME
argument_list|)
expr_stmt|;
block|}
comment|/**      * Additional test for<a href="https://issues.apache.org/jira/browse/STANBOL-1065">      * STANBOL-1065</a>      * @throws YardException      */
annotation|@
name|Test
specifier|public
name|void
name|testUriWithSpaces
parameter_list|()
throws|throws
name|YardException
block|{
name|Yard
name|yard
init|=
name|getYard
argument_list|()
decl_stmt|;
name|String
name|id1
init|=
literal|"http://www.example.com/with space"
decl_stmt|;
name|String
name|id2
init|=
literal|"http://www.example.com/other"
decl_stmt|;
name|Representation
name|test1
init|=
name|create
argument_list|(
name|id1
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|Representation
name|test2
init|=
name|create
argument_list|(
name|id2
argument_list|,
literal|true
argument_list|)
decl_stmt|;
comment|//now add a label containing space to id2
name|test1
operator|.
name|addNaturalText
argument_list|(
name|NamespaceEnum
operator|.
name|rdfs
operator|+
literal|"label"
argument_list|,
literal|"expected result"
argument_list|,
literal|"en"
argument_list|)
expr_stmt|;
name|test2
operator|.
name|addNaturalText
argument_list|(
name|NamespaceEnum
operator|.
name|rdfs
operator|+
literal|"label"
argument_list|,
literal|"space"
argument_list|,
literal|"en"
argument_list|)
expr_stmt|;
name|test2
operator|.
name|addNaturalText
argument_list|(
name|NamespaceEnum
operator|.
name|rdfs
operator|+
literal|"comment"
argument_list|,
literal|"URIs with space got separated "
operator|+
literal|"in queries causing parts in URIs after spaces to form full text "
operator|+
literal|"queries instead!"
argument_list|,
literal|"en"
argument_list|)
expr_stmt|;
name|yard
operator|.
name|update
argument_list|(
name|test1
argument_list|)
expr_stmt|;
name|yard
operator|.
name|update
argument_list|(
name|test2
argument_list|)
expr_stmt|;
comment|//now try to query for some combination
name|assertNull
argument_list|(
literal|"No Entity with ID 'http://www.example.com/with URIs' expected"
argument_list|,
name|yard
operator|.
name|getRepresentation
argument_list|(
literal|"http://www.example.com/with URIs"
argument_list|)
argument_list|)
expr_stmt|;
name|assertNull
argument_list|(
literal|"No Entity with ID 'http://www.example.com/with' expected"
argument_list|,
name|yard
operator|.
name|getRepresentation
argument_list|(
literal|"http://www.example.com/with"
argument_list|)
argument_list|)
expr_stmt|;
comment|//no check that lookups do work withspace uris
name|Representation
name|result
init|=
name|yard
operator|.
name|getRepresentation
argument_list|(
name|id1
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"Entity with ID 'http://www.example.com/with space' expected"
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Entity with id '"
operator|+
name|id1
operator|+
literal|"' expected, but got '"
operator|+
name|result
operator|.
name|getId
argument_list|()
operator|+
literal|"' instead"
argument_list|,
name|id1
argument_list|,
name|result
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
comment|//finally test removal of Entities with space
name|yard
operator|.
name|remove
argument_list|(
name|id1
argument_list|)
expr_stmt|;
name|assertNull
argument_list|(
literal|"Entity with ID 'http://www.example.com/with space' got not deleted"
argument_list|,
name|yard
operator|.
name|getRepresentation
argument_list|(
name|id1
argument_list|)
argument_list|)
expr_stmt|;
comment|//and also clean up the 2nd entity used for the test
name|yard
operator|.
name|remove
argument_list|(
name|id2
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFieldQuery
parameter_list|()
throws|throws
name|YardException
block|{
comment|// NOTE: this does not test if the updated view of the representation is
comment|// stored, but only that the update method works correctly
name|Yard
name|yard
init|=
name|getYard
argument_list|()
decl_stmt|;
name|String
name|id1
init|=
literal|"urn:yard.test.testFieldQuery:representation.id1"
decl_stmt|;
name|String
name|id2
init|=
literal|"urn:yard.test.testFieldQuery:representation.id2"
decl_stmt|;
name|String
name|field
init|=
literal|"urn:the.field:used.for.testFieldQuery"
decl_stmt|;
name|Representation
name|test1
init|=
name|create
argument_list|(
name|id1
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|Representation
name|test2
init|=
name|create
argument_list|(
name|id2
argument_list|,
literal|true
argument_list|)
decl_stmt|;
comment|// change the representations to be sure to force an update even if the
comment|// implementation checks for changes before updating a representation
name|test1
operator|.
name|add
argument_list|(
name|field
argument_list|,
literal|"This is the text content of a field with value1."
argument_list|)
expr_stmt|;
name|test2
operator|.
name|add
argument_list|(
name|field
argument_list|,
literal|"This is the text content of a field with value2."
argument_list|)
expr_stmt|;
name|Iterable
argument_list|<
name|Representation
argument_list|>
name|updatedIterable
init|=
name|yard
operator|.
name|update
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|test1
argument_list|,
name|test2
argument_list|)
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|updatedIterable
argument_list|)
expr_stmt|;
name|FieldQuery
name|query
init|=
name|yard
operator|.
name|getQueryFactory
argument_list|()
operator|.
name|createFieldQuery
argument_list|()
decl_stmt|;
name|query
operator|.
name|setConstraint
argument_list|(
name|field
argument_list|,
operator|new
name|TextConstraint
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"text content"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|results
init|=
name|yard
operator|.
name|find
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|results
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
comment|// fetch the light / minimal representation
name|query
operator|=
name|yard
operator|.
name|getQueryFactory
argument_list|()
operator|.
name|createFieldQuery
argument_list|()
expr_stmt|;
name|query
operator|.
name|setConstraint
argument_list|(
name|field
argument_list|,
operator|new
name|TextConstraint
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"value2"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|results
operator|=
name|yard
operator|.
name|find
argument_list|(
name|query
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|results
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Representation
name|result
init|=
name|results
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"urn:yard.test.testFieldQuery:representation.id2"
argument_list|,
name|result
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|result
operator|.
name|getFirst
argument_list|(
name|field
argument_list|)
argument_list|)
expr_stmt|;
comment|// fetch the full representation
name|results
operator|=
name|yard
operator|.
name|findRepresentation
argument_list|(
name|query
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|results
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|result
operator|=
name|results
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
expr_stmt|;
name|assertEquals
argument_list|(
literal|"urn:yard.test.testFieldQuery:representation.id2"
argument_list|,
name|result
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"This is the text content of a field with value2."
argument_list|,
name|result
operator|.
name|getFirst
argument_list|(
name|field
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFieldQueryWithSimilarityConstraint
parameter_list|()
throws|throws
name|YardException
block|{
comment|// NOTE: this does not test if the updated view of the representation is
comment|// stored, but only that the update method works correctly
name|Yard
name|yard
init|=
name|getYard
argument_list|()
decl_stmt|;
name|String
name|id1
init|=
literal|"urn:yard.test.testFieldQueryWithSimilarityConstraint:representation.id1"
decl_stmt|;
name|String
name|id2
init|=
literal|"urn:yard.test.testFieldQueryWithSimilarityConstraint:representation.id2"
decl_stmt|;
name|String
name|id3
init|=
literal|"urn:yard.test.testFieldQueryWithSimilarityConstraint:representation.id3"
decl_stmt|;
name|String
name|similarityfield
init|=
name|NamespaceEnum
operator|.
name|rdfs
operator|+
literal|"comment"
decl_stmt|;
name|String
name|filterfield
init|=
literal|"urn:the.field:used.for.testFieldQueryWithSimilarityConstraint.filter"
decl_stmt|;
name|Representation
name|test1
init|=
name|create
argument_list|(
name|id1
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|Representation
name|test2
init|=
name|create
argument_list|(
name|id2
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|Representation
name|test3
init|=
name|create
argument_list|(
name|id3
argument_list|,
literal|true
argument_list|)
decl_stmt|;
comment|// change the representations to be sure to force an update even if the
comment|// implementation checks for changes before updating a representation
name|test1
operator|.
name|add
argument_list|(
name|similarityfield
argument_list|,
literal|"aaaa aaaa aaaa bbbb bbbb cccc cccc dddd dddd"
argument_list|)
expr_stmt|;
name|test1
operator|.
name|add
argument_list|(
name|filterfield
argument_list|,
literal|"Some text content"
argument_list|)
expr_stmt|;
name|test2
operator|.
name|add
argument_list|(
name|similarityfield
argument_list|,
literal|"aaaa bbbb bbbb bbbb bbbb eeee"
argument_list|)
expr_stmt|;
name|test2
operator|.
name|add
argument_list|(
name|filterfield
argument_list|,
literal|"Some other content"
argument_list|)
expr_stmt|;
name|test3
operator|.
name|add
argument_list|(
name|similarityfield
argument_list|,
literal|"eeee eeee ffff gggg"
argument_list|)
expr_stmt|;
name|test3
operator|.
name|add
argument_list|(
name|filterfield
argument_list|,
literal|"Different content"
argument_list|)
expr_stmt|;
name|Iterable
argument_list|<
name|Representation
argument_list|>
name|updatedIterable
init|=
name|yard
operator|.
name|update
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|test1
argument_list|,
name|test2
argument_list|,
name|test3
argument_list|)
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|updatedIterable
argument_list|)
expr_stmt|;
comment|// Perform a first similarity query that looks a lot like the first document
name|FieldQuery
name|query
init|=
name|yard
operator|.
name|getQueryFactory
argument_list|()
operator|.
name|createFieldQuery
argument_list|()
decl_stmt|;
name|query
operator|.
name|setConstraint
argument_list|(
name|similarityfield
argument_list|,
operator|new
name|SimilarityConstraint
argument_list|(
literal|"aaaa aaaa aaaa aaaa zzzz yyyy"
argument_list|)
argument_list|)
expr_stmt|;
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|results
init|=
name|yard
operator|.
name|find
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|results
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|Representation
argument_list|>
name|it
init|=
name|results
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Representation
name|first
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"urn:yard.test.testFieldQueryWithSimilarityConstraint:representation.id1"
argument_list|,
name|first
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
comment|// assertEquals(0.99, first.getFirst("http://www.iks-project.eu/ontology/rick/query/score"));
name|Representation
name|second
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"urn:yard.test.testFieldQueryWithSimilarityConstraint:representation.id2"
argument_list|,
name|second
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
comment|// assertEquals(0.80, first.getFirst("http://www.iks-project.eu/ontology/rick/query/score"));
comment|// combine similarity with traditional filtering
name|query
operator|=
name|yard
operator|.
name|getQueryFactory
argument_list|()
operator|.
name|createFieldQuery
argument_list|()
expr_stmt|;
name|query
operator|.
name|setConstraint
argument_list|(
name|similarityfield
argument_list|,
operator|new
name|SimilarityConstraint
argument_list|(
literal|"aaaa aaaa aaaa aaaa zzzz yyyy"
argument_list|)
argument_list|)
expr_stmt|;
name|query
operator|.
name|setConstraint
argument_list|(
name|filterfield
argument_list|,
operator|new
name|TextConstraint
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"other"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|results
operator|=
name|yard
operator|.
name|find
argument_list|(
name|query
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|results
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|it
operator|=
name|results
operator|.
name|iterator
argument_list|()
expr_stmt|;
name|first
operator|=
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
name|assertEquals
argument_list|(
literal|"urn:yard.test.testFieldQueryWithSimilarityConstraint:representation.id2"
argument_list|,
name|first
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * This Method removes all Representations create via {@link #create()} or      * {@link #create(String, boolean)} from the tested {@link Yard}. It also removes all Representations      * there ID was manually added to the {@link #representationIds} list.      */
annotation|@
name|After
specifier|public
specifier|final
name|void
name|clearUpRepresentations
parameter_list|()
throws|throws
name|YardException
block|{
if|if
condition|(
operator|!
name|representationIds
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|yard
operator|.
name|remove
argument_list|(
name|representationIds
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

