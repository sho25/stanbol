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
name|nlp
operator|.
name|utils
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
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|ContentItemHelper
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
name|junit
operator|.
name|framework
operator|.
name|Assert
import|;
end_import

begin_class
specifier|public
class|class
name|NIFHelperTest
block|{
specifier|static
name|UriRef
name|base
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://stanbol.apache.org/test/nif/nif-helper"
argument_list|)
decl_stmt|;
specifier|static
name|String
name|text
init|=
literal|"This is a test for the NLP Interchange format!"
decl_stmt|;
annotation|@
name|Test
specifier|public
name|void
name|testFragmentURI
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
operator|new
name|UriRef
argument_list|(
name|base
operator|.
name|getUnicodeString
argument_list|()
operator|+
literal|"#char=23,26"
argument_list|)
argument_list|,
name|NIFHelper
operator|.
name|getNifFragmentURI
argument_list|(
name|base
argument_list|,
literal|23
argument_list|,
literal|26
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testOffsetURI
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|base
operator|.
name|getUnicodeString
argument_list|()
operator|+
literal|"#offset_23_26"
argument_list|,
name|NIFHelper
operator|.
name|getNifOffsetURI
argument_list|(
name|base
argument_list|,
literal|23
argument_list|,
literal|26
argument_list|)
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testHashURI
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|selected
init|=
name|text
operator|.
name|substring
argument_list|(
literal|23
argument_list|,
literal|26
argument_list|)
decl_stmt|;
name|String
name|context
init|=
name|text
operator|.
name|substring
argument_list|(
literal|13
argument_list|,
literal|23
argument_list|)
operator|+
literal|'('
operator|+
name|selected
operator|+
literal|')'
operator|+
name|text
operator|.
name|substring
argument_list|(
literal|26
argument_list|,
literal|36
argument_list|)
decl_stmt|;
name|byte
index|[]
name|contextData
init|=
name|context
operator|.
name|getBytes
argument_list|(
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF8"
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|md5
init|=
name|ContentItemHelper
operator|.
name|streamDigest
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|contextData
argument_list|)
argument_list|,
literal|null
argument_list|,
literal|"MD5"
argument_list|)
decl_stmt|;
name|UriRef
name|expected
init|=
operator|new
name|UriRef
argument_list|(
name|base
operator|.
name|getUnicodeString
argument_list|()
operator|+
literal|"#hash_10_3_"
operator|+
name|md5
operator|+
literal|"_NLP"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|NIFHelper
operator|.
name|getNifHashURI
argument_list|(
name|base
argument_list|,
literal|23
argument_list|,
literal|26
argument_list|,
name|text
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

