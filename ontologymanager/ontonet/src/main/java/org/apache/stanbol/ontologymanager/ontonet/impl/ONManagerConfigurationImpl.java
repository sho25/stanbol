begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|ontonet
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|List
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
name|stanbol
operator|.
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ONManagerConfiguration
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

begin_comment
comment|/**  * Default implementation of the {@link ONManagerConfiguration}.  *   * @author alessandro  *   */
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
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|ONManagerConfigurationImpl
implements|implements
name|ONManagerConfiguration
block|{
specifier|public
specifier|static
specifier|final
name|String
name|_CONFIG_ONTOLOGY_PATH_DEFAULT
init|=
literal|""
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|_ID_DEFAULT
init|=
literal|"ontonet"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|_ONTOLOGY_NETWORK_NS_DEFAULT
init|=
literal|"http://kres.iksproject.eu/"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|ONManagerConfiguration
operator|.
name|CONFIG_ONTOLOGY_PATH
argument_list|,
name|value
operator|=
name|_CONFIG_ONTOLOGY_PATH_DEFAULT
argument_list|)
specifier|private
name|String
name|configPath
decl_stmt|;
specifier|protected
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
comment|/**      * TODO how do you use array initializers in Property annotations without causing compile errors?      */
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|ONManagerConfiguration
operator|.
name|ONTOLOGY_PATHS
argument_list|,
name|value
operator|=
block|{
literal|"."
block|,
literal|"/ontologies"
block|}
argument_list|)
specifier|private
name|String
index|[]
name|ontologyDirs
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|ONManagerConfiguration
operator|.
name|ID
argument_list|,
name|value
operator|=
name|_ID_DEFAULT
argument_list|)
specifier|private
name|String
name|ontonetID
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|ONManagerConfiguration
operator|.
name|ONTOLOGY_NETWORK_NS
argument_list|,
name|value
operator|=
name|_ONTOLOGY_NETWORK_NS_DEFAULT
argument_list|)
specifier|private
name|String
name|ontonetNS
decl_stmt|;
comment|/**      * This default constructor is<b>only</b> intended to be used by the OSGI environment with Service      * Component Runtime support.      *<p>      * DO NOT USE to manually create instances - the ONManagerConfigurationImpl instances do need to be configured!      * YOU NEED TO USE {@link #ONManagerConfigurationImpl(Dictionary)} or its overloads, to parse the      * configuration and then initialise the rule store if running outside an OSGI environment.      */
specifier|public
name|ONManagerConfigurationImpl
parameter_list|()
block|{}
comment|/**      * To be invoked by non-OSGi environments.      *       * @param configuration      */
specifier|public
name|ONManagerConfigurationImpl
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
block|{
name|this
argument_list|()
expr_stmt|;
name|activate
argument_list|(
name|configuration
argument_list|)
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"in {} activate with context {}"
argument_list|,
name|getClass
argument_list|()
argument_list|,
name|context
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"No valid"
operator|+
name|ComponentContext
operator|.
name|class
operator|+
literal|" parsed in activate!"
argument_list|)
throw|;
block|}
name|activate
argument_list|(
operator|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|context
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|activate
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
block|{
comment|// Parse configuration.
name|ontonetID
operator|=
operator|(
name|String
operator|)
name|configuration
operator|.
name|get
argument_list|(
name|ONManagerConfiguration
operator|.
name|ID
argument_list|)
expr_stmt|;
if|if
condition|(
name|ontonetID
operator|==
literal|null
condition|)
name|ontonetID
operator|=
name|_ID_DEFAULT
expr_stmt|;
name|ontologyDirs
operator|=
operator|(
name|String
index|[]
operator|)
name|configuration
operator|.
name|get
argument_list|(
name|ONManagerConfiguration
operator|.
name|ONTOLOGY_PATHS
argument_list|)
expr_stmt|;
if|if
condition|(
name|ontologyDirs
operator|==
literal|null
condition|)
name|ontologyDirs
operator|=
operator|new
name|String
index|[]
block|{
literal|"."
block|,
literal|"/ontologies"
block|}
expr_stmt|;
name|ontonetNS
operator|=
operator|(
name|String
operator|)
name|configuration
operator|.
name|get
argument_list|(
name|ONManagerConfiguration
operator|.
name|ONTOLOGY_NETWORK_NS
argument_list|)
expr_stmt|;
if|if
condition|(
name|ontonetNS
operator|==
literal|null
condition|)
name|ontonetNS
operator|=
name|_ONTOLOGY_NETWORK_NS_DEFAULT
expr_stmt|;
name|configPath
operator|=
operator|(
name|String
operator|)
name|configuration
operator|.
name|get
argument_list|(
name|ONManagerConfiguration
operator|.
name|CONFIG_ONTOLOGY_PATH
argument_list|)
expr_stmt|;
if|if
condition|(
name|configPath
operator|==
literal|null
condition|)
name|configPath
operator|=
name|_CONFIG_ONTOLOGY_PATH_DEFAULT
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
name|ontonetID
operator|=
literal|null
expr_stmt|;
name|ontologyDirs
operator|=
literal|null
expr_stmt|;
name|ontonetNS
operator|=
literal|null
expr_stmt|;
name|configPath
operator|=
literal|null
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"in {} deactivate with context {}"
argument_list|,
name|getClass
argument_list|()
argument_list|,
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getID
parameter_list|()
block|{
return|return
name|ontonetID
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getOntologyNetworkConfigurationPath
parameter_list|()
block|{
return|return
name|configPath
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getOntologyNetworkNamespace
parameter_list|()
block|{
return|return
name|ontonetNS
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getOntologySourceDirectories
parameter_list|()
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
name|ontologyDirs
argument_list|)
return|;
block|}
block|}
end_class

end_unit

