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
name|solr
operator|.
name|managed
operator|.
name|impl
package|;
end_package

begin_import
import|import static
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
name|SolrConstants
operator|.
name|PROPERTY_SERVER_DIR
import|;
end_import

begin_import
import|import static
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
name|SolrConstants
operator|.
name|PROPERTY_SERVER_NAME
import|;
end_import

begin_import
import|import static
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
name|SolrConstants
operator|.
name|PROPERTY_SERVER_PUBLISH_REST
import|;
end_import

begin_import
import|import static
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
name|SolrConstants
operator|.
name|PROPERTY_SERVER_RANKING
import|;
end_import

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
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ConfigurationPolicy
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Deactivate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|apache
operator|.
name|solr
operator|.
name|core
operator|.
name|SolrCore
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
name|SolrServerAdapter
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
name|SolrServerAdapter
operator|.
name|SolrServerProperties
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
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_comment
comment|/**  * Allows to init a {@link CoreContainer} for a Solr directory on the local  * file system and register the {@link CoreContainer} as well as all the   * {@link SolrCore}s as OSGI services.<p>  * The CoreContainer is initialised on   * @author westei  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|,
name|configurationFactory
operator|=
literal|true
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|REQUIRE
argument_list|,
name|specVersion
operator|=
literal|"1.1"
argument_list|)
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|ReferencedSolrServer
operator|.
name|class
argument_list|)
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PROPERTY_SERVER_NAME
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PROPERTY_SERVER_DIR
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PROPERTY_SERVER_RANKING
argument_list|,
name|intValue
operator|=
literal|0
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PROPERTY_SERVER_PUBLISH_REST
argument_list|,
name|boolValue
operator|=
literal|false
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|ReferencedSolrServer
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ReferencedSolrServer
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Takes care of manageing the {@link CoreContainer} and its {@link SolrCore}s      * as OSGI services      */
specifier|protected
name|SolrServerAdapter
name|server
decl_stmt|;
comment|/*      * NOTE: one could here also get the properties of the parsed       * ComponentContext and directly parse the values to the constructor of the      * SolrServerAdapter. However here the configured values are all checkted      * to generate meaningful error messages      */
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Activate {}: "
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|)
expr_stmt|;
name|SolrServerProperties
name|properties
init|=
literal|null
decl_stmt|;
name|Object
name|value
init|=
name|context
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|PROPERTY_SERVER_DIR
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_SERVER_DIR
argument_list|,
literal|"The Server directory is a "
operator|+
literal|"required configuration and MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
else|else
block|{
name|File
name|solrServerDir
init|=
operator|new
name|File
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|solrServerDir
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"> solrDir = {}"
argument_list|,
name|solrServerDir
argument_list|)
expr_stmt|;
name|properties
operator|=
operator|new
name|SolrServerProperties
argument_list|(
name|solrServerDir
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_SERVER_DIR
argument_list|,
literal|"The parsed Solr Server directpry '"
operator|+
name|value
operator|+
literal|"' does not exist or is not a directory!"
argument_list|)
throw|;
block|}
block|}
name|value
operator|=
name|context
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|PROPERTY_SERVER_NAME
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_SERVER_NAME
argument_list|,
literal|"The Server Name is a required"
operator|+
literal|"Configuration and MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
else|else
block|{
name|properties
operator|.
name|setServerName
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"> Name = {}"
argument_list|,
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|value
operator|=
name|context
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|PROPERTY_SERVER_RANKING
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|Number
condition|)
block|{
name|properties
operator|.
name|setServerRanking
argument_list|(
operator|(
operator|(
name|Number
operator|)
name|value
operator|)
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|properties
operator|.
name|setServerRanking
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"> Ranking = {}"
argument_list|,
name|properties
operator|.
name|getServerRanking
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_SERVER_RANKING
argument_list|,
literal|"The configured Server Ranking '"
operator|+
name|value
operator|+
literal|" can not be converted to an Integer!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|//else not present or empty string -> do not set a ranking!
name|value
operator|=
name|properties
operator|.
name|get
argument_list|(
name|PROPERTY_SERVER_PUBLISH_REST
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|instanceof
name|Boolean
condition|)
block|{
name|properties
operator|.
name|setPublishREST
argument_list|(
operator|(
name|Boolean
operator|)
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|properties
operator|.
name|setPublishREST
argument_list|(
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"> publisRest = {}"
argument_list|,
name|properties
operator|.
name|isPublishREST
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|server
operator|=
operator|new
name|SolrServerAdapter
argument_list|(
name|context
operator|.
name|getBundleContext
argument_list|()
argument_list|,
name|properties
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParserConfigurationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to initialise the XML parser "
operator|+
literal|"for parsing the SolrServer Configuration for Server '"
operator|+
name|properties
operator|.
name|getServerName
argument_list|()
operator|+
literal|"' (dir="
operator|+
name|properties
operator|.
name|getServerDir
argument_list|()
operator|+
literal|")!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_SERVER_DIR
argument_list|,
literal|"Unable to initialise "
operator|+
literal|"a SolrServer based on the Directory '"
operator|+
name|properties
operator|.
name|getServerDir
argument_list|()
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|SAXException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_SERVER_DIR
argument_list|,
literal|"Unable to initialise "
operator|+
literal|"a SolrServer based on the Directory '"
operator|+
name|properties
operator|.
name|getServerDir
argument_list|()
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|" ... SolrServer successfully initialised!"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|" ... deactivate referenced SolrServer "
operator|+
name|server
operator|.
name|getServerName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|server
operator|!=
literal|null
condition|)
block|{
name|server
operator|.
name|shutdown
argument_list|()
expr_stmt|;
name|server
operator|=
literal|null
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

