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
package|;
end_package

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
name|NamespaceMappingUtils
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
name|NamespaceMappingsUtilsTest
block|{
annotation|@
name|Test
specifier|public
name|void
name|testPrefixCheck
parameter_list|()
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|"12"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|"ab"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|"AB"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|"a1B"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|"a-b"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|"a_b"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|"a#"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|"a/"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|"a:"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|" a"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|"a "
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|"å"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
literal|"?"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testNamespaceCheck
parameter_list|()
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"http://www.example.com/"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"http://www.example.com"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"http://www.example.com/test/"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"http://www.example.com/test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"http://www.example.com/test#"
argument_list|)
argument_list|)
expr_stmt|;
comment|//for uris no ':'
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"http://www.example.com/test:"
argument_list|)
argument_list|)
expr_stmt|;
comment|//for urn's only : is allowd
name|Assert
operator|.
name|assertTrue
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"urn:example.text:"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"urn:example.text"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"urn:example.text/"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"urn:example.text#"
argument_list|)
argument_list|)
expr_stmt|;
comment|//no relative URI namespaces
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"test/example/"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"test/example#"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"test/example:"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
literal|"test/example:test/"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetNamespace
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.com/test#"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getNamespace
argument_list|(
literal|"http://www.example.com/test#"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.com/test#"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getNamespace
argument_list|(
literal|"http://www.example.com/test#localname"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.com/test/"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getNamespace
argument_list|(
literal|"http://www.example.com/test/localname"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.com/test/"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getNamespace
argument_list|(
literal|"http://www.example.com/test/"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.com/"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getNamespace
argument_list|(
literal|"http://www.example.com/test:localname"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.com/"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getNamespace
argument_list|(
literal|"http://www.example.com/test?param=test&param1=test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|NamespaceMappingUtils
operator|.
name|getNamespace
argument_list|(
literal|"http://www.example.com"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"urn:example:"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getNamespace
argument_list|(
literal|"urn:example:"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"urn:example:"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getNamespace
argument_list|(
literal|"urn:example:localname"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"urn:example:"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getNamespace
argument_list|(
literal|"urn:example:localname/other"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"urn:example:"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getNamespace
argument_list|(
literal|"urn:example:localname#other"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetPrefix
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
literal|"localname"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
literal|"test:localname"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
literal|"test:localname:test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
literal|"test:localname/test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
literal|"test:localname#test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
literal|"urn:test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
literal|"urn:test:localname"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
literal|"urn:test:localname#test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
literal|"http://www.example.com"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
literal|"http://www.example.com/"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
literal|"http://www.example.com/test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
literal|"http://www.example.com/test#test"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

