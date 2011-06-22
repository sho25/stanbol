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
name|jsonld
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
name|assertEquals
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
name|JsonLdProfileTest
block|{
annotation|@
name|Test
specifier|public
name|void
name|testDefineProfile
parameter_list|()
block|{
name|JsonLdProfile
name|profile
init|=
operator|new
name|JsonLdProfile
argument_list|()
decl_stmt|;
name|profile
operator|.
name|addNamespacePrefix
argument_list|(
literal|"http://iks-project.eu/ont/"
argument_list|,
literal|"iks"
argument_list|)
expr_stmt|;
name|profile
operator|.
name|addType
argument_list|(
literal|"person"
argument_list|,
literal|"iks:person"
argument_list|)
expr_stmt|;
name|profile
operator|.
name|addType
argument_list|(
literal|"organization"
argument_list|,
literal|"iks:organization"
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|profile
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|expected
init|=
literal|"{\"@context\":{\"iks\":\"http:\\/\\/iks-project.eu\\/ont\\/\",\"#types\":{\"organization\":\"iks:organization\",\"person\":\"iks:person\"}}}"
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testDefineProfileNoNS
parameter_list|()
block|{
name|JsonLdProfile
name|profile
init|=
operator|new
name|JsonLdProfile
argument_list|()
decl_stmt|;
name|profile
operator|.
name|addType
argument_list|(
literal|"person"
argument_list|,
literal|"http://iks-project.eu/ont/person"
argument_list|)
expr_stmt|;
name|profile
operator|.
name|addType
argument_list|(
literal|"organization"
argument_list|,
literal|"http://iks-project.eu/ont/organization"
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|profile
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|expected
init|=
literal|"{\"@context\":{\"#types\":{\"organization\":\"http:\\/\\/iks-project.eu\\/ont\\/organization\",\"person\":\"http:\\/\\/iks-project.eu\\/ont\\/person\"}}}"
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testDefineProfileNoNSMultiTypes
parameter_list|()
block|{
name|JsonLdProfile
name|profile
init|=
operator|new
name|JsonLdProfile
argument_list|()
decl_stmt|;
name|profile
operator|.
name|addType
argument_list|(
literal|"person"
argument_list|,
literal|"http://iks-project.eu/ont/person"
argument_list|)
expr_stmt|;
name|profile
operator|.
name|addType
argument_list|(
literal|"person"
argument_list|,
literal|"http://www.schema.org/Person"
argument_list|)
expr_stmt|;
name|profile
operator|.
name|addType
argument_list|(
literal|"organization"
argument_list|,
literal|"http://iks-project.eu/ont/organization"
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|profile
operator|.
name|toString
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|String
name|expected
init|=
literal|"{\"@context\":{\"#types\":{\"organization\":\"http:\\/\\/iks-project.eu\\/ont\\/organization\",\"person\":[\"http:\\/\\/iks-project.eu\\/ont\\/person\",\"http:\\/\\/www.schema.org\\/Person\"]}}}"
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|private
name|void
name|toConsole
parameter_list|(
name|String
name|actual
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|actual
argument_list|)
expr_stmt|;
name|String
name|s
init|=
name|actual
decl_stmt|;
name|s
operator|=
name|s
operator|.
name|replaceAll
argument_list|(
literal|"\\\\"
argument_list|,
literal|"\\\\\\\\"
argument_list|)
expr_stmt|;
name|s
operator|=
name|s
operator|.
name|replace
argument_list|(
literal|"\""
argument_list|,
literal|"\\\""
argument_list|)
expr_stmt|;
name|s
operator|=
name|s
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|"\\n"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

