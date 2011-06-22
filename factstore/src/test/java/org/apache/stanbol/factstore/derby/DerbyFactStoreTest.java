begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|factstore
operator|.
name|derby
package|;
end_package

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
name|commons
operator|.
name|codec
operator|.
name|binary
operator|.
name|Base64
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
name|jsonld
operator|.
name|JsonLdProfile
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
name|factstore
operator|.
name|model
operator|.
name|FactSchema
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
name|DerbyFactStoreTest
block|{
annotation|@
name|Test
specifier|public
name|void
name|testToSQLFromProfile
parameter_list|()
throws|throws
name|Exception
block|{
name|DerbyFactStore
name|fs
init|=
operator|new
name|DerbyFactStore
argument_list|()
decl_stmt|;
name|JsonLdProfile
name|jsonLd
init|=
operator|new
name|JsonLdProfile
argument_list|()
decl_stmt|;
name|jsonLd
operator|.
name|addNamespacePrefix
argument_list|(
literal|"http://iks-project.eu/ont/"
argument_list|,
literal|"iks"
argument_list|)
expr_stmt|;
name|jsonLd
operator|.
name|addType
argument_list|(
literal|"person"
argument_list|,
literal|"iks:person"
argument_list|)
expr_stmt|;
name|jsonLd
operator|.
name|addType
argument_list|(
literal|"organization"
argument_list|,
literal|"iks:organization"
argument_list|)
expr_stmt|;
name|String
name|profile
init|=
literal|"http://iks-project.eu/ont/employeeOf"
decl_stmt|;
name|String
name|profileB64
init|=
name|Base64
operator|.
name|encodeBase64URLSafeString
argument_list|(
name|profile
operator|.
name|getBytes
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|expected
init|=
literal|"CREATE TABLE aHR0cDovL2lrcy1wcm9qZWN0LmV1L29udC9lbXBsb3llZU9m (id INT GENERATED ALWAYS AS IDENTITY, person VARCHAR(1024), organization VARCHAR(1024))"
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|sqls
init|=
name|fs
operator|.
name|toSQLfromSchema
argument_list|(
name|profileB64
argument_list|,
name|FactSchema
operator|.
name|fromJsonLdProfile
argument_list|(
name|profile
argument_list|,
name|jsonLd
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|sqls
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|sqls
operator|.
name|get
argument_list|(
literal|0
argument_list|)
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

