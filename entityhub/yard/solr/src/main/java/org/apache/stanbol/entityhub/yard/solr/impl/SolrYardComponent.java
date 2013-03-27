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
name|entityhub
operator|.
name|core
operator|.
name|yard
operator|.
name|AbstractYard
operator|.
name|DEFAULT_QUERY_RESULT_NUMBER
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
name|entityhub
operator|.
name|core
operator|.
name|yard
operator|.
name|AbstractYard
operator|.
name|MAX_QUERY_RESULT_NUMBER
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|yard
operator|.
name|Yard
operator|.
name|DESCRIPTION
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|yard
operator|.
name|Yard
operator|.
name|ID
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|yard
operator|.
name|Yard
operator|.
name|NAME
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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYardConfig
operator|.
name|ALLOW_INITIALISATION_STATE
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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYardConfig
operator|.
name|DEFAULT_ALLOW_INITIALISATION_STATE
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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYardConfig
operator|.
name|DEFAULT_MAX_BOOLEAN_CLAUSES
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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYardConfig
operator|.
name|DEFAULT_SOLR_INDEX_CONFIGURATION_NAME
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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYardConfig
operator|.
name|MAX_BOOLEAN_CLAUSES
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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYardConfig
operator|.
name|MULTI_YARD_INDEX_LAYOUT
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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYardConfig
operator|.
name|SOLR_INDEX_CONFIGURATION_NAME
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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYardConfig
operator|.
name|SOLR_SERVER_LOCATION
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
name|Enumeration
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
name|Reference
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
name|ReferenceCardinality
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
name|ReferencePolicy
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
name|ReferenceStrategy
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
name|impl
operator|.
name|HttpSolrServer
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
name|namespaceprefix
operator|.
name|NamespacePrefixService
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
name|IndexReference
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
name|RegisteredSolrServerTracker
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
name|managed
operator|.
name|IndexMetadata
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
name|managed
operator|.
name|ManagedIndexState
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
name|managed
operator|.
name|ManagedSolrServer
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
name|yard
operator|.
name|Yard
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|BundleContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Constants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|InvalidSyntaxException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|ServiceReference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|ServiceRegistration
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
comment|/**  * OSGI Component that configures and registers the {@link SolrYard} service  * as soon as all services referenced by the Configuration are available<p>  *<b>NOTE:</b> This component uses the name<code>org.apache.stanbol.entityhub.yard.solr.impl.SolrYard</code>  * to make it backward compatible with SolrYard configurations of version  *<code>0.11.0</code>!  * @author Rupert Westenthaler  * @since 0.12.0  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
comment|//set the name to the old class name of the SolrYard so that old
comment|//configurations do still work
name|name
operator|=
literal|"org.apache.stanbol.entityhub.yard.solr.impl.SolrYard"
argument_list|,
name|metatype
operator|=
literal|true
argument_list|,
name|immediate
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
name|Properties
argument_list|(
name|value
operator|=
block|{
comment|// NOTE: Added propertied from AbstractYard to fix ordering!
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|ID
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|NAME
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|DESCRIPTION
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|DEFAULT_QUERY_RESULT_NUMBER
argument_list|,
name|intValue
operator|=
operator|-
literal|1
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|MAX_QUERY_RESULT_NUMBER
argument_list|,
name|intValue
operator|=
operator|-
literal|1
argument_list|)
block|,
comment|// BEGIN SolrYard specific Properties
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SOLR_SERVER_LOCATION
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|ALLOW_INITIALISATION_STATE
argument_list|,
name|boolValue
operator|=
name|DEFAULT_ALLOW_INITIALISATION_STATE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SOLR_INDEX_CONFIGURATION_NAME
argument_list|,
name|value
operator|=
name|DEFAULT_SOLR_INDEX_CONFIGURATION_NAME
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|MULTI_YARD_INDEX_LAYOUT
argument_list|,
name|boolValue
operator|=
literal|false
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|MAX_BOOLEAN_CLAUSES
argument_list|,
name|intValue
operator|=
name|DEFAULT_MAX_BOOLEAN_CLAUSES
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|SolrYardComponent
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
name|SolrYardComponent
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|BundleContext
name|context
decl_stmt|;
specifier|private
name|SolrYardConfig
name|config
decl_stmt|;
comment|/**      * The SolrServer used for the registered SorYard      */
specifier|private
name|SolrServer
name|solrServer
decl_stmt|;
comment|/**      * The {@link ServiceRegistration} for the {@link SolrYard}      */
specifier|private
name|ServiceRegistration
name|yardRegistration
decl_stmt|;
comment|/**      * Optionally a {@link ManagedSolrServer} that is used to create new       * Solr indexes based on parsed configurations.      */
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_UNARY
argument_list|,
name|bind
operator|=
literal|"bindManagedSolrServer"
argument_list|,
name|unbind
operator|=
literal|"unbindManagedSolrServer"
argument_list|,
name|strategy
operator|=
name|ReferenceStrategy
operator|.
name|EVENT
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|)
specifier|private
name|ManagedSolrServer
name|managedSolrServer
decl_stmt|;
specifier|private
name|RegisteredSolrServerTracker
name|registeredServerTracker
decl_stmt|;
specifier|protected
name|void
name|bindManagedSolrServer
parameter_list|(
name|ManagedSolrServer
name|manager
parameter_list|)
block|{
name|SolrYardConfig
name|config
init|=
operator|(
name|SolrYardConfig
operator|)
name|this
operator|.
name|config
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|" ... bind ManagedSolrServer '{}' to SolrYardConfig '{}'"
argument_list|,
name|manager
operator|.
name|getServerName
argument_list|()
argument_list|,
name|config
operator|!=
literal|null
condition|?
name|config
operator|.
name|getId
argument_list|()
else|:
literal|"<not yet activated>"
argument_list|)
expr_stmt|;
name|this
operator|.
name|managedSolrServer
operator|=
name|manager
expr_stmt|;
if|if
condition|(
name|config
operator|!=
literal|null
condition|)
block|{
comment|//if activated
try|try
block|{
name|initManagedSolrIndex
argument_list|(
name|manager
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Exception while checking SolrIndex '"
operator|+
name|config
operator|.
name|getSolrServerLocation
argument_list|()
operator|+
literal|"' on ManagedSolrServer '"
operator|+
name|manager
operator|.
name|getServerName
argument_list|()
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|protected
name|void
name|unbindManagedSolrServer
parameter_list|(
name|ManagedSolrServer
name|manager
parameter_list|)
block|{
name|SolrYardConfig
name|config
init|=
operator|(
name|SolrYardConfig
operator|)
name|this
operator|.
name|config
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|" ... unbind ManagedSolrServer '{}' from SolrYard '{}'"
argument_list|,
name|manager
operator|.
name|getServerName
argument_list|()
argument_list|,
name|config
operator|!=
literal|null
condition|?
name|config
operator|.
name|getId
argument_list|()
else|:
literal|"<not yet activated>"
argument_list|)
expr_stmt|;
name|this
operator|.
name|managedSolrServer
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * Optionally a {@link NamespacePrefixService} that is set to the SolrYard      */
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_UNARY
argument_list|,
name|bind
operator|=
literal|"bindNamespacePrefixService"
argument_list|,
name|unbind
operator|=
literal|"unbindNamespacePrefixService"
argument_list|,
name|strategy
operator|=
name|ReferenceStrategy
operator|.
name|EVENT
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|)
specifier|private
name|NamespacePrefixService
name|nsPrefixService
decl_stmt|;
specifier|private
name|SolrYard
name|yard
decl_stmt|;
specifier|protected
name|void
name|bindNamespacePrefixService
parameter_list|(
name|NamespacePrefixService
name|nsPrefixService
parameter_list|)
block|{
name|this
operator|.
name|nsPrefixService
operator|=
name|nsPrefixService
expr_stmt|;
name|updateSolrYardRegistration
argument_list|(
name|solrServer
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|unbindNamespacePrefixService
parameter_list|(
name|NamespacePrefixService
name|nsPrefixService
parameter_list|)
block|{
name|this
operator|.
name|nsPrefixService
operator|=
literal|null
expr_stmt|;
name|updateSolrYardRegistration
argument_list|(
name|solrServer
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|this
operator|.
name|context
operator|=
name|ctx
operator|.
name|getBundleContext
argument_list|()
expr_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|SolrYardConfig
name|config
init|=
operator|new
name|SolrYardConfig
argument_list|(
operator|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|ctx
operator|.
name|getProperties
argument_list|()
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"activate {} (name:{})"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|config
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|indexLocation
init|=
name|config
operator|.
name|getSolrServerLocation
argument_list|()
decl_stmt|;
if|if
condition|(
name|indexLocation
operator|.
name|startsWith
argument_list|(
literal|"http"
argument_list|)
operator|&&
name|indexLocation
operator|.
name|indexOf
argument_list|(
literal|"://"
argument_list|)
operator|>
literal|0
condition|)
block|{
name|solrServer
operator|=
operator|new
name|HttpSolrServer
argument_list|(
name|indexLocation
argument_list|)
expr_stmt|;
comment|//directly register configs that use a remote server
name|updateSolrYardRegistration
argument_list|(
name|solrServer
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//locally managed Server
name|IndexReference
name|solrServerRef
init|=
name|IndexReference
operator|.
name|parse
argument_list|(
name|config
operator|.
name|getSolrServerLocation
argument_list|()
argument_list|)
decl_stmt|;
comment|//We do not (yet) support creating SolrIndexes on ManagedSolrServers other than the
comment|//default
if|if
condition|(
name|config
operator|.
name|isAllowInitialisation
argument_list|()
operator|&&
name|solrServerRef
operator|.
name|getServer
argument_list|()
operator|!=
literal|null
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|SolrYardConfig
operator|.
name|SOLR_SERVER_LOCATION
argument_list|,
literal|"The SolrServerLocation ({server-name}:{index-name}) MUST NOT define a "
operator|+
literal|"{server-name} if '"
operator|+
name|SolrYardConfig
operator|.
name|ALLOW_INITIALISATION_STATE
operator|+
literal|"' is enabled. Change the cofiguration to use just a {index-name}"
argument_list|)
throw|;
block|}
comment|//check if we need to init the SolrIndex
name|initManagedSolrIndex
argument_list|(
name|managedSolrServer
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
comment|//globally set the config
name|this
operator|.
name|config
operator|=
name|config
expr_stmt|;
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"deactivate {} (name:{})"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|config
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|registeredServerTracker
operator|!=
literal|null
condition|)
block|{
name|registeredServerTracker
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
name|updateSolrYardRegistration
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
comment|//unregister
name|this
operator|.
name|config
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|context
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * initialise ManagedSolrIndex and that starts tracking for the {@link SolrCore}      * @param managedServer the managedServer to init the SolrCore (if necessary)      * @param config the {@link SolrYardConfig}      * @throws IllegalStateException if the initialization fails      */
specifier|private
name|void
name|initManagedSolrIndex
parameter_list|(
specifier|final
name|ManagedSolrServer
name|managedServer
parameter_list|,
specifier|final
name|SolrYardConfig
name|config
parameter_list|)
block|{
if|if
condition|(
name|managedServer
operator|==
literal|null
operator|||
name|config
operator|==
literal|null
condition|)
block|{
comment|//this means that either no ManagedSolrServer is present or this
comment|//component was not yet activated ... will be called again
return|return;
block|}
name|IndexReference
name|solrIndexRef
init|=
name|IndexReference
operator|.
name|parse
argument_list|(
name|config
operator|.
name|getSolrServerLocation
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|config
operator|.
name|isAllowInitialisation
argument_list|()
condition|)
block|{
comment|//are we allowed to create the SolrServer
comment|//get the name of the config to be used (default: default.solrindex.zip")
name|String
name|configName
init|=
name|config
operator|.
name|getIndexConfigurationName
argument_list|()
decl_stmt|;
name|IndexMetadata
name|metadata
init|=
name|managedServer
operator|.
name|getIndexMetadata
argument_list|(
name|solrIndexRef
operator|.
name|getIndex
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|metadata
operator|==
literal|null
condition|)
block|{
comment|//create a new index
name|log
operator|.
name|info
argument_list|(
literal|" ... creating Managed SolrIndex {} (configName: {}) on Server {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|solrIndexRef
operator|.
name|getIndex
argument_list|()
block|,
name|configName
block|,
name|managedServer
operator|.
name|getServerName
argument_list|()
block|}
argument_list|)
expr_stmt|;
try|try
block|{
name|metadata
operator|=
name|managedServer
operator|.
name|createSolrIndex
argument_list|(
name|solrIndexRef
operator|.
name|getIndex
argument_list|()
argument_list|,
name|configName
argument_list|,
literal|null
argument_list|)
expr_stmt|;
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
literal|"Unable to create Managed SolrIndex "
operator|+
name|solrIndexRef
operator|.
name|getIndex
argument_list|()
operator|+
literal|" (configName: "
operator|+
name|configName
operator|+
literal|") on Server "
operator|+
name|managedServer
operator|.
name|getServerName
argument_list|()
operator|+
literal|"!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
elseif|else
if|if
condition|(
name|metadata
operator|.
name|getState
argument_list|()
operator|!=
name|ManagedIndexState
operator|.
name|ACTIVE
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|" ... activating Managed SolrIndex {} on Server {} (current state: {})"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|solrIndexRef
operator|.
name|getIndex
argument_list|()
block|,
name|managedServer
operator|.
name|getServerName
argument_list|()
block|,
name|metadata
operator|.
name|getState
argument_list|()
block|}
argument_list|)
expr_stmt|;
try|try
block|{
name|managedServer
operator|.
name|activateIndex
argument_list|(
name|metadata
operator|.
name|getIndexName
argument_list|()
argument_list|)
expr_stmt|;
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
literal|"Unable to activate Managed SolrIndex "
operator|+
name|solrIndexRef
operator|.
name|getIndex
argument_list|()
operator|+
literal|" (configName: "
operator|+
name|configName
operator|+
literal|") on Server "
operator|+
name|managedServer
operator|.
name|getServerName
argument_list|()
operator|+
literal|"!"
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
literal|"Unable to activate Managed SolrIndex "
operator|+
name|solrIndexRef
operator|.
name|getIndex
argument_list|()
operator|+
literal|" (configName: "
operator|+
name|configName
operator|+
literal|") on Server "
operator|+
name|managedServer
operator|.
name|getServerName
argument_list|()
operator|+
literal|"!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|//else already active nothing todo
name|solrIndexRef
operator|=
name|metadata
operator|.
name|getIndexReference
argument_list|()
expr_stmt|;
block|}
comment|//else the SolrServer will be supplied (e.g. created by installing a full index)
try|try
block|{
name|registeredServerTracker
operator|=
operator|new
name|RegisteredSolrServerTracker
argument_list|(
name|context
argument_list|,
name|solrIndexRef
argument_list|,
literal|null
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|void
name|removedService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|,
name|Object
name|service
parameter_list|)
block|{
name|updateSolrYardRegistration
argument_list|(
name|registeredServerTracker
operator|.
name|getService
argument_list|()
argument_list|,
name|config
argument_list|)
expr_stmt|;
name|super
operator|.
name|removedService
argument_list|(
name|reference
argument_list|,
name|service
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|modifiedService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|,
name|Object
name|service
parameter_list|)
block|{
name|updateSolrYardRegistration
argument_list|(
name|registeredServerTracker
operator|.
name|getService
argument_list|()
argument_list|,
name|config
argument_list|)
expr_stmt|;
name|super
operator|.
name|modifiedService
argument_list|(
name|reference
argument_list|,
name|service
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|SolrServer
name|addingService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|)
block|{
name|SolrServer
name|server
init|=
name|super
operator|.
name|addingService
argument_list|(
name|reference
argument_list|)
decl_stmt|;
if|if
condition|(
name|solrServer
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Multiple SolrServer for IndexLocation {} available!"
argument_list|,
name|config
operator|.
name|getSolrServerLocation
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|updateSolrYardRegistration
argument_list|(
name|server
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
return|return
name|server
return|;
block|}
block|}
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|" ... start tracking for SolrCore based on {}"
argument_list|,
name|solrIndexRef
argument_list|)
expr_stmt|;
name|registeredServerTracker
operator|.
name|open
argument_list|()
expr_stmt|;
comment|//start tracking
block|}
catch|catch
parameter_list|(
name|InvalidSyntaxException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to track Managed SolrIndex "
operator|+
name|solrIndexRef
operator|.
name|getIndex
argument_list|()
operator|+
literal|"on Server "
operator|+
name|managedServer
operator|.
name|getServerName
argument_list|()
operator|+
literal|"!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|protected
specifier|synchronized
name|void
name|updateSolrYardRegistration
parameter_list|(
name|SolrServer
name|solrServer
parameter_list|,
name|SolrYardConfig
name|config
parameter_list|)
block|{
if|if
condition|(
name|solrServer
operator|==
literal|null
operator|||
name|config
operator|==
literal|null
condition|)
block|{
return|return;
comment|//ignore call
block|}
if|if
condition|(
name|yardRegistration
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|" ... unregister SolrYard (name:{})"
argument_list|,
name|config
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|yardRegistration
operator|.
name|unregister
argument_list|()
expr_stmt|;
name|yardRegistration
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|yard
operator|!=
literal|null
condition|)
block|{
name|yard
operator|.
name|close
argument_list|()
expr_stmt|;
name|yard
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|solrServer
operator|!=
literal|null
operator|&&
name|config
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|" ... register SolrYard (name:{})"
argument_list|,
name|config
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|yard
operator|=
operator|new
name|SolrYard
argument_list|(
name|solrServer
argument_list|,
name|config
argument_list|,
name|nsPrefixService
argument_list|)
expr_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
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
comment|//copy over configuration from the component (to make it easier to filter)
for|for
control|(
name|Enumeration
argument_list|<
name|String
argument_list|>
name|keys
init|=
name|config
operator|.
name|getDictionary
argument_list|()
operator|.
name|keys
argument_list|()
init|;
name|keys
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|String
name|key
init|=
name|keys
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|key
operator|.
name|contains
argument_list|(
literal|"stanbol.entityhub.yard"
argument_list|)
operator|||
name|Constants
operator|.
name|SERVICE_RANKING
operator|.
name|equals
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|properties
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|config
operator|.
name|getDictionary
argument_list|()
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|yardRegistration
operator|=
name|context
operator|.
name|registerService
argument_list|(
name|Yard
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|yard
argument_list|,
name|properties
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
