begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|service
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
name|namespaceprefix
operator|.
name|service
operator|.
name|StanbolNamespacePrefixService
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
name|StanbolNamespacePrefixServiceTest
block|{
specifier|private
specifier|static
name|NamespacePrefixService
name|service
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
name|File
name|mappingFile
decl_stmt|;
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
name|service
operator|=
operator|new
name|StanbolNamespacePrefixService
argument_list|(
name|mappingFile
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testInitialisation
parameter_list|()
block|{
comment|//this tests the namespaces defined in namespaceprefix.mappings file
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.org/test#"
argument_list|,
name|service
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
name|service
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
name|service
operator|.
name|getNamespace
argument_list|(
literal|"urn_test"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMultiplePrefixesForNamespace
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|prefixes
init|=
name|service
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
name|service
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
name|service
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
name|testConversionMethods
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.org/test#localname"
argument_list|,
name|service
operator|.
name|getFullName
argument_list|(
literal|"test:localname"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.org/test#localname:test"
argument_list|,
name|service
operator|.
name|getFullName
argument_list|(
literal|"test:localname:test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://www.example.org/test#localname"
argument_list|,
name|service
operator|.
name|getFullName
argument_list|(
literal|"http://www.example.org/test#localname"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"urn:example.text:localname"
argument_list|,
name|service
operator|.
name|getFullName
argument_list|(
literal|"urn_test:localname"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"urn:example.text:localname/test"
argument_list|,
name|service
operator|.
name|getFullName
argument_list|(
literal|"urn_test:localname/test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"urn:example.text:localname"
argument_list|,
name|service
operator|.
name|getFullName
argument_list|(
literal|"urn:example.text:localname"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|service
operator|.
name|getFullName
argument_list|(
literal|"nonExistentPrefix:localname"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

