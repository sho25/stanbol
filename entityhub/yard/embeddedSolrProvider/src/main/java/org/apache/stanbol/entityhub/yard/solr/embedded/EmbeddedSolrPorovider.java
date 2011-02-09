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
name|yard
operator|.
name|solr
operator|.
name|embedded
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
name|FilenameFilter
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
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|java
operator|.
name|util
operator|.
name|Set
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
name|commons
operator|.
name|collections
operator|.
name|map
operator|.
name|ReferenceMap
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
name|apache
operator|.
name|solr
operator|.
name|core
operator|.
name|CoreDescriptor
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
name|yard
operator|.
name|solr
operator|.
name|provider
operator|.
name|SolrServerProvider
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
comment|/**  * Support for the use of {@link EmbeddedSolrPorovider} in combination with the  * SolrYard implementation. This implements the {@link SolrServerProvider}  * interface for the {@link Type#EMBEDDED}.<p>  * TODO: Describe the configuration of the embedded SolrServer  * @author Rupert Westenthaler  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|EmbeddedSolrPorovider
implements|implements
name|SolrServerProvider
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
name|EmbeddedSolrPorovider
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * internally used to keep track of active {@link CoreContainer}s for      * requested paths.      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|protected
name|Map
argument_list|<
name|String
argument_list|,
name|CoreContainer
argument_list|>
name|coreContainers
init|=
operator|new
name|ReferenceMap
argument_list|()
decl_stmt|;
annotation|@
name|Property
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_HOME
init|=
literal|"solr.solr.home"
decl_stmt|;
specifier|public
name|EmbeddedSolrPorovider
parameter_list|()
block|{     }
annotation|@
name|Override
specifier|public
name|SolrServer
name|getSolrServer
parameter_list|(
name|Type
name|type
parameter_list|,
name|String
name|uriOrPath
parameter_list|,
name|String
modifier|...
name|additional
parameter_list|)
throws|throws
name|NullPointerException
throws|,
name|IllegalArgumentException
block|{
name|log
operator|.
name|debug
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"getSolrServer Request for %s and path %s"
argument_list|,
name|type
argument_list|,
name|uriOrPath
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|uriOrPath
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NullPointerException
argument_list|(
literal|"The Path to the Index MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|File
name|index
init|=
operator|new
name|File
argument_list|(
name|uriOrPath
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|index
operator|.
name|exists
argument_list|()
condition|)
block|{
try|try
block|{
name|URI
name|fileUri
init|=
operator|new
name|URI
argument_list|(
name|uriOrPath
argument_list|)
decl_stmt|;
name|index
operator|=
operator|new
name|File
argument_list|(
name|fileUri
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
comment|//also not an URI -> ignore
block|}
if|if
condition|(
operator|!
name|index
operator|.
name|exists
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The parsed Index Path %s does not exist"
argument_list|,
name|uriOrPath
argument_list|)
argument_list|)
throw|;
block|}
block|}
if|if
condition|(
name|index
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|File
name|solr
init|=
name|getFile
argument_list|(
name|index
argument_list|,
literal|"solr.xml"
argument_list|)
decl_stmt|;
name|String
name|coreName
decl_stmt|;
if|if
condition|(
name|solr
operator|!=
literal|null
condition|)
block|{
comment|//in that case we assume that this is a single core installation
name|coreName
operator|=
literal|""
expr_stmt|;
block|}
else|else
block|{
name|solr
operator|=
name|getFile
argument_list|(
name|index
operator|.
name|getParentFile
argument_list|()
argument_list|,
literal|"solr.xml"
argument_list|)
expr_stmt|;
if|if
condition|(
name|solr
operator|!=
literal|null
condition|)
block|{
comment|//assume this is a multi core
name|coreName
operator|=
name|index
operator|.
name|getName
argument_list|()
expr_stmt|;
name|index
operator|=
name|index
operator|.
name|getParentFile
argument_list|()
expr_stmt|;
comment|//set the index dir to the parent
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The parsed Index Path %s is not an Solr "
operator|+
literal|"Index nor a Core of an Multi Core Configuration "
operator|+
literal|"(no \"solr.xml\" was found in this nor the parent directory!)"
argument_list|,
name|uriOrPath
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|//now init the EmbeddedSolrServer
name|log
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Create EmbeddedSolrServer for index %s and core %s"
argument_list|,
name|index
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
name|coreName
argument_list|)
argument_list|)
expr_stmt|;
return|return
operator|new
name|EmbeddedSolrServer
argument_list|(
name|getCoreContainer
argument_list|(
name|index
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
name|solr
argument_list|)
argument_list|,
name|coreName
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The parsed Index Path %s is no Directory"
argument_list|,
name|uriOrPath
argument_list|)
argument_list|)
throw|;
block|}
block|}
specifier|protected
specifier|final
name|CoreContainer
name|getCoreContainer
parameter_list|(
name|String
name|solrDir
parameter_list|,
name|File
name|solrConf
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|IllegalStateException
block|{
name|CoreContainer
name|container
init|=
name|coreContainers
operator|.
name|get
argument_list|(
name|solrDir
argument_list|)
decl_stmt|;
if|if
condition|(
name|container
operator|==
literal|null
condition|)
block|{
name|container
operator|=
operator|new
name|CoreContainer
argument_list|(
name|solrDir
argument_list|)
expr_stmt|;
name|coreContainers
operator|.
name|put
argument_list|(
name|solrDir
argument_list|,
name|container
argument_list|)
expr_stmt|;
comment|/*              * NOTE:              * We need to reset the ContextClassLoader to the one used for this              * Bundle, because Solr uses this ClassLoader to load all the              * plugins configured in the solr.xml and schema.xml.              * The finally block resets the context class loader to the previous              * value. (Rupert Westenthaler 20010209)              */
name|ClassLoader
name|loader
init|=
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getContextClassLoader
argument_list|()
decl_stmt|;
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|setContextClassLoader
argument_list|(
name|EmbeddedSolrPorovider
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|container
operator|.
name|load
argument_list|(
name|solrDir
argument_list|,
name|solrConf
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
literal|"Unable to parse Solr Configuration"
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
name|IllegalStateException
argument_list|(
literal|"Unable to access Solr Configuration"
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
name|IllegalStateException
argument_list|(
literal|"Unable to parse Solr Configuration"
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|setContextClassLoader
argument_list|(
name|loader
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|container
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|Type
argument_list|>
name|supportedTypes
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|singleton
argument_list|(
name|Type
operator|.
name|EMBEDDED
argument_list|)
return|;
block|}
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{     }
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
comment|//should we remove the coreContainers -> currently I don't because
comment|// (1) activate deactivate do not have any affect
comment|// (2) it are soft references anyway.
block|}
comment|/**      * Checks if the parsed directory contains a file that starts with the parsed      * name. Parsing "hallo" will find "hallo.all", "hallo.ween" as well as "hallo".      * @param dir the Directory. This assumes that the parsed File is not      *<code>null</code>, exists and is an directory      * @param name the name. If<code>null</code> any file is accepted, meaning      * that this will return true if the directory contains any file       * @return the state      */
specifier|private
name|boolean
name|hasFile
parameter_list|(
name|File
name|dir
parameter_list|,
name|String
name|name
parameter_list|)
block|{
return|return
name|dir
operator|.
name|listFiles
argument_list|(
operator|new
name|SimpleFileNameFilter
argument_list|(
name|name
argument_list|)
argument_list|)
operator|.
name|length
operator|>
literal|0
return|;
block|}
comment|/**      * Returns the first file that matches the parsed name.      * Parsing "hallo" will find "hallo.all", "hallo.ween" as well as "hallo".      * @param dir the Directory. This assumes that the parsed File is not      *<code>null</code>, exists and is an directory.      * @param name the name. If<code>null</code> any file is accepted, meaning      * that this will return true if the directory contains any file       * @return the first file matching the parsed name.      */
specifier|private
name|File
name|getFile
parameter_list|(
name|File
name|dir
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|File
index|[]
name|files
init|=
name|dir
operator|.
name|listFiles
argument_list|(
operator|new
name|SimpleFileNameFilter
argument_list|(
name|name
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|files
operator|.
name|length
operator|>
literal|0
condition|?
name|files
index|[
literal|0
index|]
else|:
literal|null
return|;
block|}
comment|/**      * Could not find a simple implementation of {@link FilenameFilter} that      * can be used if a file exists. If someone knows one, feel free to replace      * this one!       * @author Rupert Westenthaler      *      */
specifier|private
specifier|static
class|class
name|SimpleFileNameFilter
implements|implements
name|FilenameFilter
block|{
specifier|private
name|String
name|name
decl_stmt|;
specifier|public
name|SimpleFileNameFilter
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|dir
parameter_list|,
name|String
name|name
parameter_list|)
block|{
return|return
name|this
operator|.
name|name
operator|==
literal|null
condition|?
literal|true
else|:
name|name
operator|.
name|startsWith
argument_list|(
name|this
operator|.
name|name
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

