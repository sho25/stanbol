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
name|ArrayList
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|impl
operator|.
name|SimpleMGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|IOUtils
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
name|core
operator|.
name|store
operator|.
name|SolrContentItemImpl
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
name|core
operator|.
name|utils
operator|.
name|ContentItemIDOrganizer
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
name|store
operator|.
name|SolrContentItem
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
name|store
operator|.
name|SolrStore
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
name|junit
operator|.
name|runner
operator|.
name|RunWith
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
annotation|@
name|RunWith
argument_list|(
name|SlingAnnotationsTestRunner
operator|.
name|class
argument_list|)
specifier|public
class|class
name|CoreUnitTest
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|CoreUnitTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|MGraph
name|metadata
decl_stmt|;
specifier|private
specifier|static
name|String
name|id
decl_stmt|;
specifier|private
specifier|static
name|String
name|mimeType
decl_stmt|;
specifier|private
specifier|static
name|byte
index|[]
name|data
decl_stmt|;
specifier|private
specifier|static
name|String
name|content
decl_stmt|;
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|constraints
decl_stmt|;
specifier|private
specifier|static
name|String
name|title
decl_stmt|;
annotation|@
name|TestReference
specifier|private
name|SolrStore
name|store
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|before
parameter_list|()
block|{
name|metadata
operator|=
operator|new
name|SimpleMGraph
argument_list|()
expr_stmt|;
name|id
operator|=
literal|"a76b7e72a923"
expr_stmt|;
name|mimeType
operator|=
literal|"text/plain"
expr_stmt|;
name|data
operator|=
literal|"test content"
operator|.
name|getBytes
argument_list|()
expr_stmt|;
name|content
operator|=
literal|"test content"
expr_stmt|;
name|constraints
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|Object
argument_list|>
name|titleList
init|=
operator|new
name|ArrayList
argument_list|<
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|titleList
operator|.
name|add
argument_list|(
literal|"meric"
argument_list|)
expr_stmt|;
name|constraints
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|titleList
argument_list|)
expr_stmt|;
name|title
operator|=
literal|"Test Title"
expr_stmt|;
block|}
specifier|public
name|void
name|assertEqualItem
parameter_list|(
name|SolrContentItem
name|sci
parameter_list|)
throws|throws
name|IOException
block|{
name|assertEquals
argument_list|(
literal|"SolrContentItem's id is not matching with original id"
argument_list|,
name|id
argument_list|,
name|ContentItemIDOrganizer
operator|.
name|detachBaseURI
argument_list|(
name|sci
operator|.
name|getUri
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|//		assertEquals(
comment|//				"SolrContentItem's title is not matching with original title",
comment|//				title, sci.getTitle());
name|assertEquals
argument_list|(
literal|"SolrContentItem's content is not matching with original content"
argument_list|,
name|content
argument_list|,
name|IOUtils
operator|.
name|toString
argument_list|(
name|sci
operator|.
name|getStream
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"SolrContentItem's mimeType is not matching with original mimeType"
argument_list|,
name|mimeType
argument_list|,
name|sci
operator|.
name|getMimeType
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"SolrContentItem's metadata is not matching with original metadata"
argument_list|,
name|metadata
operator|.
name|toString
argument_list|()
argument_list|,
name|sci
operator|.
name|getMetadata
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"SolrContentItem's constraints is not matching with original constraints"
argument_list|,
name|constraints
argument_list|,
name|sci
operator|.
name|getConstraints
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|SolrContentItemImplConsTest
parameter_list|()
throws|throws
name|IOException
block|{
name|SolrContentItem
name|sci
init|=
operator|new
name|SolrContentItemImpl
argument_list|(
name|data
argument_list|,
operator|new
name|UriRef
argument_list|(
name|id
argument_list|)
argument_list|,
literal|null
argument_list|,
name|mimeType
argument_list|,
name|metadata
argument_list|,
name|constraints
argument_list|)
decl_stmt|;
name|assertEqualItem
argument_list|(
name|sci
argument_list|)
expr_stmt|;
block|}
comment|//	@Test
comment|//	public void testGetEnhancementGraph(){
comment|//
comment|//	}
comment|//
comment|//	@Test
comment|//	public void testEnhanceAndPut(){
comment|//
comment|//	}
annotation|@
name|Test
specifier|public
name|void
name|testCreate
parameter_list|()
throws|throws
name|IOException
block|{
name|SolrContentItem
name|ci
init|=
operator|(
name|SolrContentItem
operator|)
name|store
operator|.
name|create
argument_list|(
literal|""
argument_list|,
name|data
argument_list|,
name|mimeType
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
literal|"Creating item with empty id is failed"
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|SolrContentItem
name|sci
init|=
name|store
operator|.
name|create
argument_list|(
name|data
argument_list|,
name|id
argument_list|,
name|title
argument_list|,
name|mimeType
argument_list|,
name|constraints
argument_list|)
decl_stmt|;
name|assertEqualItem
argument_list|(
name|sci
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testPut
parameter_list|()
throws|throws
name|IOException
block|{
comment|//		SolrContentItem sci = store.create(data, null, title, mimeType,
comment|//				constraints);
comment|//		try {
comment|//			store.put(sci);
comment|//			fail("put() should have thrown an exception!");
comment|//		} catch (IllegalArgumentException e) {
comment|//
comment|//		}
comment|//TODO: null must be title field.
name|SolrContentItem
name|sci
init|=
name|store
operator|.
name|create
argument_list|(
name|data
argument_list|,
name|id
argument_list|,
name|title
argument_list|,
name|mimeType
argument_list|,
name|constraints
argument_list|)
decl_stmt|;
name|String
name|sciID
init|=
name|store
operator|.
name|put
argument_list|(
name|sci
argument_list|)
decl_stmt|;
name|sci
operator|=
operator|(
name|SolrContentItem
operator|)
name|store
operator|.
name|get
argument_list|(
name|sciID
argument_list|)
expr_stmt|;
comment|//TODO: constraint {author_t=[meric]}
comment|//		assertEqualItem(sci);
name|store
operator|.
name|deleteById
argument_list|(
name|sciID
argument_list|)
expr_stmt|;
name|assertNull
argument_list|(
literal|"Returned a non-exist SolrContentItem"
argument_list|,
name|store
operator|.
name|get
argument_list|(
name|sciID
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
