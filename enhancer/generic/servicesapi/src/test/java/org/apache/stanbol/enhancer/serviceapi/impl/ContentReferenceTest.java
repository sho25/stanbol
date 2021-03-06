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
name|enhancer
operator|.
name|serviceapi
operator|.
name|impl
package|;
end_package

begin_import
import|import static
name|junit
operator|.
name|framework
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|junit
operator|.
name|framework
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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentReference
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentSource
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|impl
operator|.
name|UrlReference
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

begin_class
specifier|public
class|class
name|ContentReferenceTest
block|{
specifier|private
specifier|static
specifier|final
name|String
name|TEST_RESOURCE_NAME
init|=
literal|"contentreferecetest.txt"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|TEST_RESOURCE_CONTENT
init|=
literal|"Used to test ContentReference!"
decl_stmt|;
specifier|private
specifier|static
name|URL
name|testURL
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|initURL
parameter_list|()
block|{
name|testURL
operator|=
name|ContentReferenceTest
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResource
argument_list|(
name|TEST_RESOURCE_NAME
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
literal|"Unable to load test resource '"
operator|+
name|TEST_RESOURCE_NAME
operator|+
literal|" via Classpath!"
argument_list|,
name|testURL
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
name|missingReferenceString
parameter_list|()
block|{
operator|new
name|UrlReference
argument_list|(
operator|(
name|String
operator|)
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
name|missingReferenceURL
parameter_list|()
block|{
operator|new
name|UrlReference
argument_list|(
operator|(
name|URL
operator|)
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
name|emptyReferenceString
parameter_list|()
block|{
operator|new
name|UrlReference
argument_list|(
literal|""
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
name|relativeReferenceString
parameter_list|()
block|{
operator|new
name|UrlReference
argument_list|(
literal|"relative/path/to/some.resource"
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
name|unknownProtocolReferenceString
parameter_list|()
block|{
operator|new
name|UrlReference
argument_list|(
literal|"unknownProt://test.example.org/some.resource"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testUrlReference
parameter_list|()
throws|throws
name|IOException
block|{
name|ContentReference
name|ref
init|=
operator|new
name|UrlReference
argument_list|(
name|testURL
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|ref
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|ref
operator|.
name|getReference
argument_list|()
argument_list|,
name|testURL
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|ContentSource
name|source
init|=
name|ref
operator|.
name|dereference
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|source
argument_list|)
expr_stmt|;
name|String
name|content
init|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|source
operator|.
name|getStream
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|TEST_RESOURCE_CONTENT
argument_list|,
name|content
argument_list|)
expr_stmt|;
comment|//same as above, but by using ContentSource.getData() instead of
comment|//ContentSource.getStream()
name|ref
operator|=
operator|new
name|UrlReference
argument_list|(
name|testURL
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|ref
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|ref
operator|.
name|getReference
argument_list|()
argument_list|,
name|testURL
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|source
operator|=
name|ref
operator|.
name|dereference
argument_list|()
expr_stmt|;
name|assertNotNull
argument_list|(
name|source
argument_list|)
expr_stmt|;
name|content
operator|=
operator|new
name|String
argument_list|(
name|source
operator|.
name|getData
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|TEST_RESOURCE_CONTENT
argument_list|,
name|content
argument_list|)
expr_stmt|;
comment|//test the constructor that takes a String
name|ref
operator|=
operator|new
name|UrlReference
argument_list|(
name|testURL
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|ref
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|ref
operator|.
name|getReference
argument_list|()
argument_list|,
name|testURL
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|source
operator|=
name|ref
operator|.
name|dereference
argument_list|()
expr_stmt|;
name|assertNotNull
argument_list|(
name|source
argument_list|)
expr_stmt|;
name|content
operator|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|source
operator|.
name|getStream
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|TEST_RESOURCE_CONTENT
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

