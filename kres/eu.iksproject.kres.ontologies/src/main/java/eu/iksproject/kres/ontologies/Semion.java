begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|ontologies
package|;
end_package

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

begin_class
specifier|public
class|class
name|Semion
block|{
specifier|public
specifier|static
specifier|final
name|String
name|URI
init|=
literal|"http://andriry.altervista.org/tesiSpecialistica/semion.owl"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|NS
init|=
literal|"http://andriry.altervista.org/tesiSpecialistica/semion.owl#"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|DataSource
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"DataSource"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasDataSourceType
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasDataSourceType"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasDataSourceURI
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasDataSourceURI"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

