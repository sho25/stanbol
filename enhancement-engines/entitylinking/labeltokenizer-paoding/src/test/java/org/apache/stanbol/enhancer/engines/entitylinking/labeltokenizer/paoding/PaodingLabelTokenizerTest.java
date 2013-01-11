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
name|engines
operator|.
name|entitylinking
operator|.
name|labeltokenizer
operator|.
name|paoding
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
name|util
operator|.
name|Dictionary
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|extras
operator|.
name|paoding
operator|.
name|Activator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|AfterClass
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
name|osgi
operator|.
name|service
operator|.
name|cm
operator|.
name|ConfigurationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
import|;
end_import

begin_class
specifier|public
class|class
name|PaodingLabelTokenizerTest
block|{
specifier|protected
specifier|static
specifier|final
name|String
name|TEST_PAODING_DIC_PATH
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
literal|"paoding-dict"
decl_stmt|;
specifier|private
specifier|static
name|PaodingLabelTokenizer
name|labelTokenizer
decl_stmt|;
specifier|private
specifier|static
name|File
name|paodingDict
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|init
parameter_list|()
throws|throws
name|ConfigurationException
throws|,
name|IOException
block|{
name|String
name|baseDir
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
name|System
operator|.
name|getProperty
argument_list|(
literal|"basedir"
argument_list|)
decl_stmt|;
name|paodingDict
operator|=
operator|new
name|File
argument_list|(
name|baseDir
argument_list|,
name|TEST_PAODING_DIC_PATH
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|paodingDict
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|Activator
operator|.
name|initPaodingDictionary
argument_list|(
name|paodingDict
argument_list|,
name|PaodingLabelTokenizerTest
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|Activator
operator|.
name|DICT_ARCHIVE
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Activator
operator|.
name|initPaodingDictHomeProperty
argument_list|(
name|paodingDict
argument_list|)
expr_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|ComponentContext
name|cc
init|=
operator|new
name|MockComponentContext
argument_list|(
name|config
argument_list|)
decl_stmt|;
name|labelTokenizer
operator|=
operator|new
name|PaodingLabelTokenizer
argument_list|()
expr_stmt|;
name|labelTokenizer
operator|.
name|activate
argument_list|(
name|cc
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
name|testNullLabel
parameter_list|()
block|{
name|labelTokenizer
operator|.
name|tokenize
argument_list|(
literal|null
argument_list|,
literal|"en"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testNullLanguate
parameter_list|()
block|{
name|Assert
operator|.
name|assertNull
argument_list|(
name|labelTokenizer
operator|.
name|tokenize
argument_list|(
literal|"test"
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testUnsupportedLanguage
parameter_list|()
block|{
name|Assert
operator|.
name|assertNull
argument_list|(
name|labelTokenizer
operator|.
name|tokenize
argument_list|(
literal|"test"
argument_list|,
literal|"de"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testLuceneLabelTokenizer
parameter_list|()
block|{
comment|//BBC
name|String
name|label
init|=
literal|"英国广播公司"
decl_stmt|;
name|String
index|[]
name|expected
init|=
operator|new
name|String
index|[]
block|{
literal|"英国"
block|,
literal|"广播"
block|,
literal|"公司"
block|}
decl_stmt|;
name|String
index|[]
name|tokens
init|=
name|labelTokenizer
operator|.
name|tokenize
argument_list|(
name|label
argument_list|,
literal|"zh"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|tokens
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected
argument_list|,
name|tokens
argument_list|)
expr_stmt|;
comment|//Yellow Sea (one word??)
name|label
operator|=
literal|"黄海"
expr_stmt|;
name|expected
operator|=
operator|new
name|String
index|[]
block|{
literal|"黄海"
block|}
expr_stmt|;
name|tokens
operator|=
name|labelTokenizer
operator|.
name|tokenize
argument_list|(
name|label
argument_list|,
literal|"zh"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|tokens
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected
argument_list|,
name|tokens
argument_list|)
expr_stmt|;
comment|//Barack Obama
name|label
operator|=
literal|"贝拉克·奥巴马"
expr_stmt|;
name|expected
operator|=
operator|new
name|String
index|[]
block|{
literal|"贝拉"
block|,
literal|"克"
block|,
literal|"·"
block|,
literal|"奥"
block|,
literal|"巴马"
block|}
expr_stmt|;
name|tokens
operator|=
name|labelTokenizer
operator|.
name|tokenize
argument_list|(
name|label
argument_list|,
literal|"zh"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|tokens
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected
argument_list|,
name|tokens
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEmptyLabel
parameter_list|()
block|{
name|String
index|[]
name|tokens
init|=
name|labelTokenizer
operator|.
name|tokenize
argument_list|(
literal|""
argument_list|,
literal|"zh"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|tokens
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|tokens
operator|.
name|length
operator|==
literal|0
argument_list|)
expr_stmt|;
block|}
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|close
parameter_list|()
block|{
if|if
condition|(
name|labelTokenizer
operator|!=
literal|null
condition|)
block|{
name|labelTokenizer
operator|.
name|deactivate
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
