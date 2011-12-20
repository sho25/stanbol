begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|helper
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
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
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|Blob
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
name|helper
operator|.
name|InMemoryBlob
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
name|InMemoryBlobTest
extends|extends
name|AbstractBlobTest
block|{
specifier|private
specifier|static
specifier|final
name|Charset
name|UTF8
init|=
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
decl_stmt|;
comment|/*      * Override to test InMemoryBlob instead of AbstractBlob      * @see org.apache.stanbol.enhancer.serviceapi.helper.BlobMimeTypeHandlingTest#getBlobToTestMimetypeHandling(java.lang.String)      */
annotation|@
name|Override
specifier|protected
name|Blob
name|getBlobToTestMimetypeHandling
parameter_list|(
name|String
name|mimeType
parameter_list|)
block|{
return|return
operator|new
name|InMemoryBlob
argument_list|(
literal|"dummy"
operator|.
name|getBytes
argument_list|(
name|UTF8
argument_list|)
argument_list|,
name|mimeType
argument_list|)
return|;
block|}
comment|/**      * Tests correct handling of strings and the DEFAULT mimeType for strings      * "text/plain"      * @throws IOException      */
annotation|@
name|Test
specifier|public
name|void
name|testString
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|test
init|=
literal|"Exámplê"
decl_stmt|;
name|Blob
name|blob
init|=
operator|new
name|InMemoryBlob
argument_list|(
name|test
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"text/plain"
argument_list|,
name|blob
operator|.
name|getMimeType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|blob
operator|.
name|getParameter
argument_list|()
operator|.
name|containsKey
argument_list|(
literal|"charset"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|UTF8
operator|.
name|name
argument_list|()
argument_list|,
name|blob
operator|.
name|getParameter
argument_list|()
operator|.
name|get
argument_list|(
literal|"charset"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|value
init|=
operator|new
name|String
argument_list|(
name|IOUtils
operator|.
name|toByteArray
argument_list|(
name|blob
operator|.
name|getStream
argument_list|()
argument_list|)
argument_list|,
name|UTF8
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|test
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests that any parsed Charset is replaced by UTF-8 actually used to      * convert the String into bytes.      * @throws IOException      */
annotation|@
name|Test
specifier|public
name|void
name|testStringWithCharset
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|test
init|=
literal|"Exámplê"
decl_stmt|;
name|Blob
name|blob
init|=
operator|new
name|InMemoryBlob
argument_list|(
name|test
argument_list|,
literal|"text/plain;charset=ISO-8859-4"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"text/plain"
argument_list|,
name|blob
operator|.
name|getMimeType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|blob
operator|.
name|getParameter
argument_list|()
operator|.
name|containsKey
argument_list|(
literal|"charset"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|UTF8
operator|.
name|name
argument_list|()
argument_list|,
name|blob
operator|.
name|getParameter
argument_list|()
operator|.
name|get
argument_list|(
literal|"charset"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests the default mimeType "application/octet-stream" for binary data.      * @throws IOException      */
annotation|@
name|Test
specifier|public
name|void
name|testDefaultBinaryMimeType
parameter_list|()
throws|throws
name|IOException
block|{
name|Blob
name|blob
init|=
operator|new
name|InMemoryBlob
argument_list|(
literal|"dummy"
operator|.
name|getBytes
argument_list|(
name|UTF8
argument_list|)
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"application/octet-stream"
argument_list|,
name|blob
operator|.
name|getMimeType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|blob
operator|.
name|getParameter
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|blob
operator|=
operator|new
name|InMemoryBlob
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
literal|"dummy"
operator|.
name|getBytes
argument_list|(
name|UTF8
argument_list|)
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"application/octet-stream"
argument_list|,
name|blob
operator|.
name|getMimeType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|blob
operator|.
name|getParameter
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

