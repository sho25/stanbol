begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|source
operator|.
name|jenatdb
package|;
end_package

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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|impl
operator|.
name|NamespacePrefixProviderImpl
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|defaults
operator|.
name|NamespaceEnum
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
name|Before
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
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|graph
operator|.
name|Node
import|;
end_import

begin_class
specifier|public
class|class
name|PropertyPrefixFilterTest
block|{
specifier|private
specifier|static
specifier|final
name|String
name|FB
init|=
literal|"http://rdf.freebase.com/ns/"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|TEST_CONFIG
init|=
literal|"prefix.config"
decl_stmt|;
specifier|private
specifier|static
name|NamespacePrefixProvider
name|nsPrefixProvider
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|nsMappings
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
static|static
block|{
name|nsMappings
operator|.
name|put
argument_list|(
literal|"fb"
argument_list|,
name|FB
argument_list|)
expr_stmt|;
name|nsMappings
operator|.
name|put
argument_list|(
literal|"rdf"
argument_list|,
name|NamespaceEnum
operator|.
name|rdf
operator|.
name|getNamespace
argument_list|()
argument_list|)
expr_stmt|;
name|nsMappings
operator|.
name|put
argument_list|(
literal|"rdfs"
argument_list|,
name|NamespaceEnum
operator|.
name|rdfs
operator|.
name|getNamespace
argument_list|()
argument_list|)
expr_stmt|;
name|nsMappings
operator|.
name|put
argument_list|(
literal|"skos"
argument_list|,
name|NamespaceEnum
operator|.
name|skos
operator|.
name|getNamespace
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|configLines
decl_stmt|;
specifier|private
name|RdfImportFilter
name|importFilter
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
name|nsPrefixProvider
operator|=
operator|new
name|NamespacePrefixProviderImpl
argument_list|(
name|nsMappings
argument_list|)
expr_stmt|;
name|InputStream
name|in
init|=
name|PropertyPrefixFilterTest
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|TEST_CONFIG
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
literal|"Unable to read test config"
argument_list|,
name|in
argument_list|)
expr_stmt|;
name|configLines
operator|=
operator|(
name|List
argument_list|<
name|String
argument_list|>
operator|)
name|IOUtils
operator|.
name|readLines
argument_list|(
name|in
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|createImportFilter
parameter_list|()
block|{
name|importFilter
operator|=
operator|new
name|PropertyPrefixFilter
argument_list|(
name|nsPrefixProvider
argument_list|,
name|configLines
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMappings
parameter_list|()
block|{
name|Node
name|subject
init|=
name|Node
operator|.
name|createURI
argument_list|(
literal|"urn:subject"
argument_list|)
decl_stmt|;
name|Node
name|value
init|=
name|Node
operator|.
name|createURI
argument_list|(
literal|"urn:value"
argument_list|)
decl_stmt|;
name|Node
name|rdfType
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|NamespaceEnum
operator|.
name|rdf
operator|+
literal|"type"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|importFilter
operator|.
name|accept
argument_list|(
name|subject
argument_list|,
name|rdfType
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|Node
name|rdfsLabel
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|NamespaceEnum
operator|.
name|rdfs
operator|+
literal|"label"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|importFilter
operator|.
name|accept
argument_list|(
name|subject
argument_list|,
name|rdfsLabel
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|Node
name|guid
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|FB
operator|+
literal|"type.object.guid"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|importFilter
operator|.
name|accept
argument_list|(
name|subject
argument_list|,
name|guid
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|Node
name|permission
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|FB
operator|+
literal|"type.object.permission"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|importFilter
operator|.
name|accept
argument_list|(
name|subject
argument_list|,
name|permission
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|Node
name|name
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|FB
operator|+
literal|"type.object.name"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|importFilter
operator|.
name|accept
argument_list|(
name|subject
argument_list|,
name|name
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|Node
name|description
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|FB
operator|+
literal|"type.object.description"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|importFilter
operator|.
name|accept
argument_list|(
name|subject
argument_list|,
name|description
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|Node
name|dummy
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|FB
operator|+
literal|"type.dummy"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|importFilter
operator|.
name|accept
argument_list|(
name|subject
argument_list|,
name|dummy
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|Node
name|typePlain
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|FB
operator|+
literal|"type"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|importFilter
operator|.
name|accept
argument_list|(
name|subject
argument_list|,
name|typePlain
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|Node
name|other
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|NamespaceEnum
operator|.
name|cc
operator|+
literal|"license"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|importFilter
operator|.
name|accept
argument_list|(
name|subject
argument_list|,
name|other
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
