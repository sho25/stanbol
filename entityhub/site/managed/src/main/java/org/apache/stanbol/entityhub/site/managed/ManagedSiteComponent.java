begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

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
name|site
operator|.
name|managed
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
name|servicesapi
operator|.
name|site
operator|.
name|Site
operator|.
name|PROHIBITED_SITE_IDS
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
name|site
operator|.
name|SiteConfiguration
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
name|site
operator|.
name|SiteConfiguration
operator|.
name|ENTITY_PREFIX
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
name|site
operator|.
name|SiteConfiguration
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
name|site
operator|.
name|SiteConfiguration
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
name|servicesapi
operator|.
name|site
operator|.
name|SiteConfiguration
operator|.
name|SITE_FIELD_MAPPINGS
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
name|stanbol
operator|.
name|entityhub
operator|.
name|core
operator|.
name|utils
operator|.
name|SiteUtils
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
name|site
operator|.
name|ManagedSite
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
name|site
operator|.
name|ManagedSiteConfiguration
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
name|site
operator|.
name|Site
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
name|site
operator|.
name|SiteConfiguration
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
name|YardException
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
name|site
operator|.
name|managed
operator|.
name|impl
operator|.
name|YardSite
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
name|Filter
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
name|osgi
operator|.
name|util
operator|.
name|tracker
operator|.
name|ServiceTracker
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|util
operator|.
name|tracker
operator|.
name|ServiceTrackerCustomizer
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
comment|/**  * Default {@link ManagedSite} component. This allows to configure a  * {@link ManagedSite} on top of an {@link Yard} service referenced by its  * {@link Yard#ID}. The required {@link Yard} is tracked using {@link ServiceTracker}.  * If the {@link Yard} service becomes available the {@link ManagedSite} will  * be initialised and registered as {@link BundleContext#registerService(String, Object, Dictionary) OSGI service}.  * As soon as the Yard goes away also the {@link ManagedSite} is unregistered.<p>  * This {@link ManagedSiteComponent} will be active even if the {@link ManagedSite}  * is not registered. This allows to decouples the lifecycle of the {@link ManagedSite}  * with the one of the configuration.<p>  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|name
operator|=
literal|"org.apache.stanbol.entityhub.site.managed.YardSite"
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
comment|//the baseUri is required!
name|specVersion
operator|=
literal|"1.1"
argument_list|,
name|metatype
operator|=
literal|true
argument_list|,
name|immediate
operator|=
literal|true
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
name|ID
argument_list|,
name|value
operator|=
literal|"changeme"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|NAME
argument_list|,
name|value
operator|=
literal|"change me"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|DESCRIPTION
argument_list|,
name|value
operator|=
literal|"optional description"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|ENTITY_PREFIX
argument_list|,
name|cardinality
operator|=
literal|1000
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|ManagedSiteConfiguration
operator|.
name|YARD_ID
argument_list|,
name|value
operator|=
literal|"yardId"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SITE_FIELD_MAPPINGS
argument_list|,
name|cardinality
operator|=
literal|1000
argument_list|,
name|value
operator|=
block|{
literal|"skos:prefLabel> rdfs:label"
block|,
literal|"skos:altLabel> rdfs:label"
block|,
literal|"foaf:name> rdfs:label"
block|,
literal|"dc:title> rdfs:label"
block|,
literal|"dc-elements:title> rdfs:label"
block|,
literal|"schema:name> rdfs:label"
block|}
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|ManagedSiteComponent
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ManagedSiteComponent
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|ManagedSiteConfiguration
name|siteConfiguration
decl_stmt|;
specifier|private
specifier|final
name|Object
name|yardReferenceLock
init|=
operator|new
name|Object
argument_list|()
decl_stmt|;
specifier|private
name|ServiceReference
name|yardReference
decl_stmt|;
specifier|private
name|ServiceTracker
name|yardTracker
decl_stmt|;
specifier|private
name|YardSite
name|managedSite
decl_stmt|;
specifier|private
name|BundleContext
name|bundleContext
decl_stmt|;
specifier|private
name|ServiceRegistration
name|managedSiteRegistration
decl_stmt|;
comment|//    @Reference(cardinality=ReferenceCardinality.OPTIONAL_UNARY)
comment|//    protected ConfigurationAdmin configAdmin;
comment|/**      * Activates this {@link ManagedSiteComponent}. This might be overridden to      * perform additional configuration. In such cases super should be called      * before the additional configuration steps.      * @param context      * @throws ConfigurationException      * @throws YardException      * @throws InvalidSyntaxException      */
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
specifier|final
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|ConfigurationException
throws|,
name|YardException
throws|,
name|InvalidSyntaxException
block|{
name|this
operator|.
name|bundleContext
operator|=
name|context
operator|.
name|getBundleContext
argument_list|()
expr_stmt|;
comment|//NOTE that the constructor also validation of the parsed configuration
name|this
operator|.
name|siteConfiguration
operator|=
operator|new
name|ManagedSiteConfigurationImpl
argument_list|(
name|context
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|PROHIBITED_SITE_IDS
operator|.
name|contains
argument_list|(
name|siteConfiguration
operator|.
name|getId
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|SiteConfiguration
operator|.
name|ID
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"The ID '%s' of this Referenced Site is one of the following "
operator|+
literal|"prohibited IDs: {} (case insensitive)"
argument_list|,
name|siteConfiguration
operator|.
name|getId
argument_list|()
argument_list|,
name|PROHIBITED_SITE_IDS
argument_list|)
argument_list|)
throw|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"> initialise Managed Site {}"
argument_list|,
name|siteConfiguration
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|SiteUtils
operator|.
name|extractSiteMetadata
argument_list|(
name|siteConfiguration
argument_list|)
expr_stmt|;
comment|//Initialise the Yard
specifier|final
name|String
name|yardId
init|=
name|siteConfiguration
operator|.
name|getYardId
argument_list|()
decl_stmt|;
name|String
name|yardFilterString
init|=
name|String
operator|.
name|format
argument_list|(
literal|"(&(%s=%s)(%s=%s))"
argument_list|,
name|Constants
operator|.
name|OBJECTCLASS
argument_list|,
name|Yard
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|Yard
operator|.
name|ID
argument_list|,
name|yardId
argument_list|)
decl_stmt|;
name|Filter
name|yardFilter
init|=
name|bundleContext
operator|.
name|createFilter
argument_list|(
name|yardFilterString
argument_list|)
decl_stmt|;
name|yardTracker
operator|=
operator|new
name|ServiceTracker
argument_list|(
name|bundleContext
argument_list|,
name|yardFilter
argument_list|,
operator|new
name|ServiceTrackerCustomizer
argument_list|()
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
synchronized|synchronized
init|(
name|yardReferenceLock
init|)
block|{
if|if
condition|(
name|reference
operator|.
name|equals
argument_list|(
name|yardReference
argument_list|)
condition|)
block|{
name|deactivateManagedSite
argument_list|()
expr_stmt|;
name|yardReference
operator|=
literal|null
expr_stmt|;
block|}
name|bundleContext
operator|.
name|ungetService
argument_list|(
name|reference
argument_list|)
expr_stmt|;
block|}
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
block|{                 }
annotation|@
name|Override
specifier|public
name|Object
name|addingService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|)
block|{
name|Yard
name|yard
init|=
operator|(
name|Yard
operator|)
name|bundleContext
operator|.
name|getService
argument_list|(
name|reference
argument_list|)
decl_stmt|;
synchronized|synchronized
init|(
name|yardReferenceLock
init|)
block|{
if|if
condition|(
name|yardReference
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|yard
operator|!=
literal|null
condition|)
block|{
name|activateManagedSite
argument_list|(
name|yard
argument_list|)
expr_stmt|;
name|yardReference
operator|=
name|reference
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to addService for ServiceReference because"
operator|+
literal|"unable to obtain referenced Yard via the BundleContext!"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Tracking two Yard instances with the Yard ID '{}' "
operator|+
literal|"configured for ManagedSite '{}'"
argument_list|,
name|yardId
argument_list|,
name|siteConfiguration
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"used  : {}"
argument_list|,
name|yardReference
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_PID
argument_list|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"unused: {}"
argument_list|,
name|reference
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_PID
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|yard
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|yardTracker
operator|.
name|open
argument_list|()
expr_stmt|;
comment|//will be moved to a Solr specific implementation
comment|//        //chaeck if we are allowed to init an yard with the provided id
comment|//        boolean allowInit = false;
comment|//        if(configAdmin!= null){
comment|//            Configuration[] configs;
comment|//            try {
comment|//                String yardIdFilter = String.format("(%s=%s)",
comment|//                    Yard.ID,yardId);
comment|//                configs = configAdmin.listConfigurations(yardIdFilter);
comment|//                if(configs == null || configs.length< 1){
comment|//                    allowInit = true;
comment|//                }
comment|//            } catch (IOException e) {
comment|//                log.warn("Unable to access ManagedService configurations ",e);
comment|//            }
comment|//        } else if (yardTracker.getService() == null){
comment|//            log.warn("Unable to check for Yard configuration of ManagedSite {} "
comment|//                + "Because the ConfigurationAdmin service is not available");
comment|//            log.warn(" -> unable to create YardConfiguration");
comment|//        }
comment|//        if(allowInit){
comment|//            //TODO: This has SolrYard specific code - this needs to be refactored
comment|//            String factoryPid = "org.apache.stanbol.entityhub.yard.solr.impl.SolrYard";
comment|//            try {
comment|//                Configuration config = configAdmin.createFactoryConfiguration(factoryPid,null);
comment|//                //configure the required properties
comment|//                Dictionary<String,Object> yardConfig = new Hashtable<String,Object>();
comment|//                yardConfig.put(Yard.ID, siteConfiguration.getYardId());
comment|//                yardConfig.put(Yard.NAME, siteConfiguration.getYardId());
comment|//                yardConfig.put(Yard.DESCRIPTION, "Yard for the ManagedSite "+siteConfiguration.getId());
comment|//                yardConfig.put("org.apache.stanbol.entityhub.yard.solr.solrUri", siteConfiguration.getId());
comment|//                yardConfig.put("org.apache.stanbol.entityhub.yard.solr.useDefaultConfig", true);
comment|//                config.update(yardConfig); //this create the solrYard
comment|//            } catch (IOException e) {
comment|//                log.warn("Unable to create SolrYard configuration for MnagedSite "+siteConfiguration.getId(),e);
comment|//            }
comment|//        }
block|}
comment|/**      * Deactivates the component and unregisters the {@link ManagedSite} service      * if currently active.<p>      * If overridden calls to super typically should be made after deactivating      * specific things of the sub-class.      * @param context      */
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|BundleContext
name|context
parameter_list|)
block|{
if|if
condition|(
name|yardTracker
operator|!=
literal|null
condition|)
block|{
name|yardTracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|yardTracker
operator|=
literal|null
expr_stmt|;
block|}
name|yardTracker
operator|.
name|close
argument_list|()
expr_stmt|;
synchronized|synchronized
init|(
name|yardReferenceLock
init|)
block|{
name|yardReference
operator|=
literal|null
expr_stmt|;
block|}
name|deactivateManagedSite
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|activateManagedSite
parameter_list|(
name|Yard
name|yard
parameter_list|)
block|{
name|managedSite
operator|=
operator|new
name|YardSite
argument_list|(
name|yard
argument_list|,
name|siteConfiguration
argument_list|)
expr_stmt|;
name|managedSiteRegistration
operator|=
name|bundleContext
operator|.
name|registerService
argument_list|(
operator|new
name|String
index|[]
block|{
name|ManagedSite
operator|.
name|class
operator|.
name|getName
argument_list|()
block|,
name|Site
operator|.
name|class
operator|.
name|getName
argument_list|()
block|}
argument_list|,
name|managedSite
argument_list|,
name|siteConfiguration
operator|.
name|getConfiguration
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|deactivateManagedSite
parameter_list|()
block|{
name|managedSiteRegistration
operator|.
name|unregister
argument_list|()
expr_stmt|;
name|managedSite
operator|.
name|close
argument_list|()
expr_stmt|;
name|managedSite
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_class

end_unit

