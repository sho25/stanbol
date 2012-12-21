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
name|opennlp
package|;
end_package

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
name|opennlp
operator|.
name|OpenNLP
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
name|engines
operator|.
name|entitylinking
operator|.
name|LabelTokenizer
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
name|OpenNlpLabelTokenizerTest
block|{
specifier|private
specifier|static
name|OpenNlpLabelTokenizer
name|tokenizer
decl_stmt|;
specifier|private
name|String
name|label
init|=
literal|"This is only a Test"
decl_stmt|;
specifier|private
name|String
index|[]
name|expected
init|=
name|label
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
specifier|final
name|void
name|init
parameter_list|()
throws|throws
name|ConfigurationException
block|{
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
name|config
operator|.
name|put
argument_list|(
name|LabelTokenizer
operator|.
name|SUPPORTED_LANUAGES
argument_list|,
literal|"*"
argument_list|)
expr_stmt|;
name|ComponentContext
name|cc
init|=
operator|new
name|MockComponentContext
argument_list|(
name|config
argument_list|)
decl_stmt|;
name|tokenizer
operator|=
operator|new
name|OpenNlpLabelTokenizer
argument_list|()
expr_stmt|;
name|tokenizer
operator|.
name|openNlp
operator|=
operator|new
name|OpenNLP
argument_list|(
operator|new
name|ClasspathDataFileProvider
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|tokenizer
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
name|tokenizer
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
name|String
index|[]
name|tokens
init|=
name|tokenizer
operator|.
name|tokenize
argument_list|(
name|label
argument_list|,
literal|null
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
block|}
annotation|@
name|Test
specifier|public
name|void
name|testTokenizer
parameter_list|()
block|{
name|String
index|[]
name|tokens
init|=
name|tokenizer
operator|.
name|tokenize
argument_list|(
name|label
argument_list|,
literal|"en"
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
name|tokenizer
operator|.
name|tokenize
argument_list|(
literal|""
argument_list|,
literal|"en"
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
name|tokenizer
operator|.
name|deactivate
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

