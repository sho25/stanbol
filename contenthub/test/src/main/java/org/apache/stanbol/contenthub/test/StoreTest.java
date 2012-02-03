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
name|store
operator|.
name|solr
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
name|solr
operator|.
name|SolrStore
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
name|store
operator|.
name|solr
operator|.
name|util
operator|.
name|ContentItemIDOrganizer
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
name|StoreTest
block|{
annotation|@
name|TestReference
specifier|private
name|SolrStore
name|solrStore
decl_stmt|;
specifier|private
specifier|static
name|byte
index|[]
name|content
decl_stmt|;
specifier|private
specifier|static
name|String
name|id
decl_stmt|,
name|title
decl_stmt|,
name|contentType
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
init|=
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
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|beforeClass
parameter_list|()
block|{
name|content
operator|=
literal|"I love Paris"
operator|.
name|getBytes
argument_list|()
expr_stmt|;
name|id
operator|=
literal|"ab41c32a"
expr_stmt|;
name|title
operator|=
literal|"Paris"
expr_stmt|;
name|contentType
operator|=
literal|"text/plain"
expr_stmt|;
name|List
argument_list|<
name|Object
argument_list|>
name|value
init|=
operator|new
name|ArrayList
argument_list|<
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|value
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
name|value
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testCreate
parameter_list|()
block|{
name|SolrContentItem
name|sci
init|=
name|solrStore
operator|.
name|create
argument_list|(
name|content
argument_list|,
name|id
argument_list|,
name|title
argument_list|,
name|contentType
argument_list|,
name|constraints
argument_list|)
decl_stmt|;
comment|//		assertEquals(content, sci.getStream());
name|assertEquals
argument_list|(
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
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|title
argument_list|,
name|sci
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|contentType
argument_list|,
name|sci
operator|.
name|getMimeType
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|constraints
argument_list|,
name|sci
operator|.
name|getConstraints
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
