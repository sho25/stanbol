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
name|commons
operator|.
name|namespaceprefix
operator|.
name|impl
package|;
end_package

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
name|FileInputStream
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
name|URISyntaxException
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
name|java
operator|.
name|util
operator|.
name|LinkedHashMap
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
name|stanbol
operator|.
name|commons
operator|.
name|namespaceprefix
operator|.
name|NamespacePrefixProvider
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
name|service
operator|.
name|StanbolNamespacePrefixServiceTest
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

begin_class
specifier|public
class|class
name|NamespacePrefixProviderImplTest
block|{
specifier|private
specifier|static
name|File
name|mappingFile
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|init
parameter_list|()
throws|throws
name|IOException
block|{
name|URL
name|mappingURL
init|=
name|StanbolNamespacePrefixServiceTest
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"testnamespaceprefix.mappings"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|mappingURL
argument_list|)
expr_stmt|;
try|try
block|{
name|mappingFile
operator|=
operator|new
name|File
argument_list|(
name|mappingURL
operator|.
name|toURI
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|mappingFile
operator|=
operator|new
name|File
argument_list|(
name|mappingURL
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Assert
operator|.
name|assertTrue
argument_list|(
name|mappingFile
operator|.
name|isFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testReadingFromFile
parameter_list|()
throws|throws
name|IOException
block|{
name|NamespacePrefixProvider
name|provider
init|=
operator|new
name|NamespacePrefixProviderImpl
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|mappingFile
argument_list|)
argument_list|)
decl_stmt|;
comment|//this tests the namespaces defined in namespaceprefix.mappings file
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.org/test#"
argument_list|,
name|provider
operator|.
name|getNamespace
argument_list|(
literal|"test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.org/test-1/"
argument_list|,
name|provider
operator|.
name|getNamespace
argument_list|(
literal|"test-1"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"urn:example.text:"
argument_list|,
name|provider
operator|.
name|getNamespace
argument_list|(
literal|"urn_test"
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|prefixes
init|=
name|provider
operator|.
name|getPrefixes
argument_list|(
literal|"http://www.example.org/test#"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|prefixes
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|prefixes
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test2"
argument_list|,
name|prefixes
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.org/test#"
argument_list|,
name|provider
operator|.
name|getNamespace
argument_list|(
literal|"test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.org/test#"
argument_list|,
name|provider
operator|.
name|getNamespace
argument_list|(
literal|"test2"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testReadingFromMap
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|mappings
init|=
operator|new
name|LinkedHashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|mappings
operator|.
name|put
argument_list|(
literal|"test"
argument_list|,
literal|"http://www.example.org/test#"
argument_list|)
expr_stmt|;
name|mappings
operator|.
name|put
argument_list|(
literal|"test-1"
argument_list|,
literal|"http://www.example.org/test-1/"
argument_list|)
expr_stmt|;
name|mappings
operator|.
name|put
argument_list|(
literal|"urn_test"
argument_list|,
literal|"urn:example.text:"
argument_list|)
expr_stmt|;
name|mappings
operator|.
name|put
argument_list|(
literal|"test2"
argument_list|,
literal|"http://www.example.org/test#"
argument_list|)
expr_stmt|;
comment|//add some invalid mappings
name|mappings
operator|.
name|put
argument_list|(
literal|"test:3"
argument_list|,
literal|"http://www.example.org/test#"
argument_list|)
expr_stmt|;
name|mappings
operator|.
name|put
argument_list|(
literal|"test3"
argument_list|,
literal|"http://www.example.org/test"
argument_list|)
expr_stmt|;
name|NamespacePrefixProvider
name|provider
init|=
operator|new
name|NamespacePrefixProviderImpl
argument_list|(
name|mappings
argument_list|)
decl_stmt|;
comment|//this tests the namespaces defined in namespaceprefix.mappings file
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.org/test#"
argument_list|,
name|provider
operator|.
name|getNamespace
argument_list|(
literal|"test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.org/test-1/"
argument_list|,
name|provider
operator|.
name|getNamespace
argument_list|(
literal|"test-1"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"urn:example.text:"
argument_list|,
name|provider
operator|.
name|getNamespace
argument_list|(
literal|"urn_test"
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|prefixes
init|=
name|provider
operator|.
name|getPrefixes
argument_list|(
literal|"http://www.example.org/test#"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|prefixes
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|prefixes
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test2"
argument_list|,
name|prefixes
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.org/test#"
argument_list|,
name|provider
operator|.
name|getNamespace
argument_list|(
literal|"test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.org/test#"
argument_list|,
name|provider
operator|.
name|getNamespace
argument_list|(
literal|"test2"
argument_list|)
argument_list|)
expr_stmt|;
comment|//test that illegal mappings are not imported
name|Assert
operator|.
name|assertNull
argument_list|(
name|provider
operator|.
name|getNamespace
argument_list|(
literal|"test:3"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|provider
operator|.
name|getNamespace
argument_list|(
literal|"test3"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

