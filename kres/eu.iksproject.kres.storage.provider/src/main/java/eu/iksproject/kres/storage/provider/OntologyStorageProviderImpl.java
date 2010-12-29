begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|storage
operator|.
name|provider
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|storage
operator|.
name|OntologyStorage
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|storage
operator|.
name|OntologyStoreProvider
import|;
end_import

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
argument_list|(
name|OntologyStoreProvider
operator|.
name|class
argument_list|)
specifier|public
class|class
name|OntologyStorageProviderImpl
implements|implements
name|OntologyStoreProvider
block|{
specifier|public
specifier|static
specifier|final
name|String
name|_ACTIVE_STORAGE_DEFAULT
init|=
literal|"eu.iksproject.kres.storage.ClerezzaStorage"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
name|_ACTIVE_STORAGE_DEFAULT
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|ACTIVE_STORAGE
init|=
literal|"activeStorage"
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
name|OntologyStorageProviderImpl
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|OntologyStorage
name|activeOntologyStorage
decl_stmt|;
comment|/* 	 * For safety in non-OSGi environments, we initially set this variable to 	 * its default value. 	 */
specifier|private
name|String
name|activeStorage
init|=
name|_ACTIVE_STORAGE_DEFAULT
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|String
argument_list|,
name|OntologyStorage
argument_list|>
name|registeredStorages
decl_stmt|;
comment|/** 	 * This default constructor is<b>only</b> intended to be used by the OSGI 	 * environment with Service Component Runtime support. 	 *<p> 	 * DO NOT USE to manually create instances - the OntologyStorageProviderImpl 	 * instances do need to be configured! YOU NEED TO USE 	 * {@link #OntologyStorageProviderImpl(Dictionary)} or its overloads, to 	 * parse the configuration and then initialise the rule store if running 	 * outside a OSGI environment. 	 */
specifier|public
name|OntologyStorageProviderImpl
parameter_list|()
block|{ 	 	}
comment|/** 	 * Basic constructor to be used if outside of an OSGi environment. Invokes 	 * default constructor. 	 *  	 * @param configuration 	 */
specifier|public
name|OntologyStorageProviderImpl
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
comment|/** 	 * Used to configure an instance within an OSGi container. 	 */
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
literal|"in "
operator|+
name|OntologyStorageProviderImpl
operator|.
name|class
operator|+
literal|" activate with context "
operator|+
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
comment|/** 	 * Internally used to configure an instance (within and without an OSGi 	 * container. 	 *  	 * @param configuration 	 */
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
name|String
name|tStorage
init|=
operator|(
name|String
operator|)
name|configuration
operator|.
name|get
argument_list|(
name|ACTIVE_STORAGE
argument_list|)
decl_stmt|;
if|if
condition|(
name|tStorage
operator|!=
literal|null
condition|)
name|this
operator|.
name|activeStorage
operator|=
name|tStorage
expr_stmt|;
name|registeredStorages
operator|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|OntologyStorage
argument_list|>
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|activateOntologyStorage
parameter_list|(
name|OntologyStorage
name|ontologyStorage
parameter_list|)
block|{
name|activeOntologyStorage
operator|=
name|ontologyStorage
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
name|registeredStorages
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|deactivateOntologyStorage
parameter_list|()
block|{
name|activeOntologyStorage
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|OntologyStorage
name|getActiveOntologyStorage
parameter_list|()
block|{
return|return
name|activeOntologyStorage
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isActiveOntologyStorage
parameter_list|(
name|OntologyStorage
name|ontologyStorage
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|OntologyStorage
argument_list|>
name|listOntologyStorages
parameter_list|()
block|{
return|return
name|registeredStorages
operator|.
name|values
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|registerOntologyStorage
parameter_list|(
name|OntologyStorage
name|ontologyStorage
parameter_list|)
block|{
name|String
name|storageClass
init|=
name|ontologyStorage
operator|.
name|getClass
argument_list|()
operator|.
name|getCanonicalName
argument_list|()
decl_stmt|;
name|registeredStorages
operator|.
name|put
argument_list|(
name|storageClass
argument_list|,
name|ontologyStorage
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Registerd "
operator|+
name|registeredStorages
operator|.
name|size
argument_list|()
operator|+
literal|" storages -> "
operator|+
name|storageClass
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Active storage class is "
operator|+
name|activeStorage
argument_list|)
expr_stmt|;
if|if
condition|(
name|storageClass
operator|.
name|equals
argument_list|(
name|activeStorage
argument_list|)
condition|)
block|{
name|activeOntologyStorage
operator|=
name|ontologyStorage
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Setted active storage"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|unregisterOntologyStorage
parameter_list|(
name|Class
argument_list|<
name|?
extends|extends
name|OntologyStorage
argument_list|>
name|ontologyStorage
parameter_list|)
block|{
name|registeredStorages
operator|.
name|remove
argument_list|(
name|ontologyStorage
operator|.
name|getCanonicalName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

