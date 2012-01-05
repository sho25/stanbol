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
name|engine
operator|.
name|topic
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
name|FileOutputStream
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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|ParserConfigurationException
import|;
end_import

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|TestCase
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
name|FileUtils
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
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|embedded
operator|.
name|EmbeddedSolrServer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|core
operator|.
name|CoreContainer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_class
specifier|public
class|class
name|BaseTestWithSolrCore
block|{
specifier|public
specifier|static
specifier|final
name|String
name|TEST_CORE_ID
init|=
literal|"test"
decl_stmt|;
comment|/**      * Create single core test solr server with it's own folder hierarchy.      */
specifier|public
name|EmbeddedSolrServer
name|makeEmptyEmbeddedSolrServer
parameter_list|(
name|File
name|rootFolder
parameter_list|,
name|String
name|solrServerId
parameter_list|,
name|String
name|testCoreConfig
parameter_list|)
throws|throws
name|IOException
throws|,
name|ParserConfigurationException
throws|,
name|SAXException
block|{
name|File
name|solrFolder
init|=
operator|new
name|File
argument_list|(
name|rootFolder
argument_list|,
name|solrServerId
argument_list|)
decl_stmt|;
name|FileUtils
operator|.
name|deleteQuietly
argument_list|(
name|solrFolder
argument_list|)
expr_stmt|;
name|solrFolder
operator|.
name|mkdir
argument_list|()
expr_stmt|;
comment|// solr conf file
name|File
name|solrFile
init|=
operator|new
name|File
argument_list|(
name|solrFolder
argument_list|,
literal|"solr.xml"
argument_list|)
decl_stmt|;
name|InputStream
name|is
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"/"
operator|+
name|testCoreConfig
operator|+
literal|"/solr.xml"
argument_list|)
decl_stmt|;
name|TestCase
operator|.
name|assertNotNull
argument_list|(
literal|"missing test solr.xml file"
argument_list|,
name|is
argument_list|)
expr_stmt|;
name|IOUtils
operator|.
name|copy
argument_list|(
name|is
argument_list|,
operator|new
name|FileOutputStream
argument_list|(
name|solrFile
argument_list|)
argument_list|)
expr_stmt|;
comment|// solr conf folder with schema
name|File
name|solrCoreFolder
init|=
operator|new
name|File
argument_list|(
name|solrFolder
argument_list|,
name|TEST_CORE_ID
argument_list|)
decl_stmt|;
name|solrCoreFolder
operator|.
name|mkdir
argument_list|()
expr_stmt|;
name|File
name|solrConfFolder
init|=
operator|new
name|File
argument_list|(
name|solrCoreFolder
argument_list|,
literal|"conf"
argument_list|)
decl_stmt|;
name|solrConfFolder
operator|.
name|mkdir
argument_list|()
expr_stmt|;
name|File
name|schemaFile
init|=
operator|new
name|File
argument_list|(
name|solrConfFolder
argument_list|,
literal|"schema.xml"
argument_list|)
decl_stmt|;
name|is
operator|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"/"
operator|+
name|testCoreConfig
operator|+
literal|"/schema.xml"
argument_list|)
expr_stmt|;
name|TestCase
operator|.
name|assertNotNull
argument_list|(
literal|"missing test solr schema.xml file"
argument_list|,
name|is
argument_list|)
expr_stmt|;
name|IOUtils
operator|.
name|copy
argument_list|(
name|is
argument_list|,
operator|new
name|FileOutputStream
argument_list|(
name|schemaFile
argument_list|)
argument_list|)
expr_stmt|;
name|File
name|solrConfigFile
init|=
operator|new
name|File
argument_list|(
name|solrConfFolder
argument_list|,
literal|"solrconfig.xml"
argument_list|)
decl_stmt|;
name|is
operator|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"/"
operator|+
name|testCoreConfig
operator|+
literal|"/solrconfig.xml"
argument_list|)
expr_stmt|;
name|TestCase
operator|.
name|assertNotNull
argument_list|(
literal|"missing test solrconfig.xml file"
argument_list|,
name|is
argument_list|)
expr_stmt|;
name|IOUtils
operator|.
name|copy
argument_list|(
name|is
argument_list|,
operator|new
name|FileOutputStream
argument_list|(
name|solrConfigFile
argument_list|)
argument_list|)
expr_stmt|;
comment|// create the embedded server
name|CoreContainer
name|coreContainer
init|=
operator|new
name|CoreContainer
argument_list|(
name|solrFolder
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
name|solrFile
argument_list|)
decl_stmt|;
return|return
operator|new
name|EmbeddedSolrServer
argument_list|(
name|coreContainer
argument_list|,
name|TEST_CORE_ID
argument_list|)
return|;
block|}
block|}
end_class

end_unit
