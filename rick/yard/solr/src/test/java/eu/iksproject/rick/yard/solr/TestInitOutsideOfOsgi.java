begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|yard
operator|.
name|solr
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
name|assertNull
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
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
name|MalformedURLException
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
name|SolrServer
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
name|SolrServerException
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
name|impl
operator|.
name|CommonsHttpSolrServer
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
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
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
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Text
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|yard
operator|.
name|YardException
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYard
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYardConfig
import|;
end_import

begin_class
specifier|public
class|class
name|TestInitOutsideOfOsgi
block|{
specifier|public
specifier|static
specifier|final
name|String
name|solrServer
init|=
literal|"http://localhost:8181/solr/test"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|testYardId
init|=
literal|"testYard"
decl_stmt|;
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|TestInitOutsideOfOsgi
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|URL
name|solrServerUrl
decl_stmt|;
specifier|private
specifier|static
name|boolean
name|solrServerAvailable
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|init
parameter_list|()
throws|throws
name|MalformedURLException
block|{
name|solrServerUrl
operator|=
operator|new
name|URL
argument_list|(
name|solrServer
argument_list|)
expr_stmt|;
comment|//test if the SolrServer needed for the test is available!
name|SolrServer
name|server
init|=
operator|new
name|CommonsHttpSolrServer
argument_list|(
name|solrServerUrl
argument_list|)
decl_stmt|;
try|try
block|{
name|server
operator|.
name|ping
argument_list|()
expr_stmt|;
name|solrServerAvailable
operator|=
literal|true
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SolrServerException
name|e
parameter_list|)
block|{
name|solrServerAvailable
operator|=
literal|false
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"No SolrServer available for URL %s. Will skip test that require a Solr Server!"
argument_list|,
name|solrServerUrl
argument_list|)
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|solrServerAvailable
operator|=
literal|false
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"No SolrServer available for URL %s. Will skip test that require a Solr Server!"
argument_list|,
name|solrServerUrl
argument_list|)
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|server
operator|=
literal|null
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
name|testSolrYardConfigInitWithNullParams
parameter_list|()
block|{
operator|new
name|SolrYardConfig
argument_list|(
literal|null
argument_list|,
literal|null
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
name|testSolrYardConfigInitWithNullUrl
parameter_list|()
block|{
operator|new
name|SolrYardConfig
argument_list|(
name|testYardId
argument_list|,
literal|null
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
name|testSolrYardConfigInitWithNullID
parameter_list|()
block|{
operator|new
name|SolrYardConfig
argument_list|(
literal|null
argument_list|,
name|solrServerUrl
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMinimalSolrYardConfigInit
parameter_list|()
block|{
operator|new
name|SolrYardConfig
argument_list|(
name|testYardId
argument_list|,
name|solrServerUrl
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSolrYardInitWithEmptyConfig
parameter_list|()
throws|throws
name|YardException
throws|,
name|IllegalArgumentException
block|{
if|if
condition|(
operator|!
name|solrServerAvailable
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"> Skip testSolrYardInitWithEmptyConfig because no SolrServer is available"
argument_list|)
expr_stmt|;
return|return;
block|}
comment|//create a yard, create a representation, add a value, store the
comment|//representation, check the value, remove the representation
comment|// -> this is not to test the Yard implementation, but only check the
comment|//    correct initialisation without an OSGI framework
name|SolrYardConfig
name|config
init|=
operator|new
name|SolrYardConfig
argument_list|(
name|testYardId
argument_list|,
name|solrServerUrl
argument_list|)
decl_stmt|;
name|SolrYard
name|yard
init|=
operator|new
name|SolrYard
argument_list|(
name|config
argument_list|)
decl_stmt|;
name|String
name|testRepId
init|=
literal|"urn:test.outsideOsgiEnv:test.1"
decl_stmt|;
name|String
name|testField
init|=
name|NamespaceEnum
operator|.
name|dcTerms
operator|+
literal|"title"
decl_stmt|;
name|String
name|testFieldValue
init|=
literal|"test1"
decl_stmt|;
name|String
name|testFieldLanguage
init|=
literal|"en"
decl_stmt|;
name|Representation
name|test1
init|=
name|yard
operator|.
name|create
argument_list|(
name|testRepId
argument_list|)
decl_stmt|;
name|test1
operator|.
name|addNaturalText
argument_list|(
name|testField
argument_list|,
name|testFieldValue
argument_list|,
name|testFieldLanguage
argument_list|)
expr_stmt|;
name|yard
operator|.
name|store
argument_list|(
name|test1
argument_list|)
expr_stmt|;
name|test1
operator|=
literal|null
expr_stmt|;
name|test1
operator|=
name|yard
operator|.
name|getRepresentation
argument_list|(
name|testRepId
argument_list|)
expr_stmt|;
name|Text
name|value
init|=
name|test1
operator|.
name|getFirst
argument_list|(
name|testField
argument_list|,
name|testFieldLanguage
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|value
operator|!=
literal|null
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|testFieldValue
operator|.
name|equals
argument_list|(
name|value
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|testFieldLanguage
operator|.
name|equals
argument_list|(
name|value
operator|.
name|getLanguage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|yard
operator|.
name|remove
argument_list|(
name|testRepId
argument_list|)
expr_stmt|;
name|test1
operator|=
literal|null
expr_stmt|;
name|test1
operator|=
name|yard
operator|.
name|getRepresentation
argument_list|(
name|testRepId
argument_list|)
expr_stmt|;
name|assertNull
argument_list|(
name|test1
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

