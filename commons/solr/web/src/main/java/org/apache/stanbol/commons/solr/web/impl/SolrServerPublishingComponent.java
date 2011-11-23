begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

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
name|web
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
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|Dictionary
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
name|Hashtable
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
name|javax
operator|.
name|servlet
operator|.
name|Filter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletException
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
name|http
operator|.
name|api
operator|.
name|ExtHttpService
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
name|Reference
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
name|solr
operator|.
name|servlet
operator|.
name|SolrDispatchFilter
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
name|SolrConstants
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
name|utils
operator|.
name|ServiceReferenceRankingComparator
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
name|web
operator|.
name|dispatch
operator|.
name|DelegatingSolrDispatchFilter
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
name|ServiceReference
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
name|service
operator|.
name|http
operator|.
name|HttpService
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
comment|/**  * Component that publishes all Solr {@link CoreContainer} (registered as  * OSGI service) where  * {@link SolrConstants#PROPERTY_SERVER_PUBLISH_REST} is enabled via a  * {@link SolrDispatchFilter} under<code>/{global-prefix}/{server-name}</code>  * where<ul>  *<li><code>global-prefix</code> is the configured {@link #GLOBAL_PREFIX} value  *<li><code>server-name</code> is the {@link SolrConstants#PROPERTY_SERVER_NAME}  * property of the {@link ServiceReference} for the {@link CoreContainer}.  *<p>  * Note that {@link CoreContainer} without a value for the   * {@link SolrConstants#PROPERTY_SERVER_NAME server name} properties are not  * published.<p>  * To publish specific {@link CoreContainer} at specific paths one can use the  * {@link SolrDispatchFilterComponent}.  * @see SolrDispatchFilterComponent  * @author Rupert Westenthaler  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|metatype
operator|=
literal|true
argument_list|,
name|immediate
operator|=
literal|true
argument_list|)
specifier|public
class|class
name|SolrServerPublishingComponent
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
name|SolrServerPublishingComponent
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * The default value for {@link #GLOBAL_PREFIX}      */
specifier|private
specifier|static
specifier|final
name|String
name|DEFAUTL_GLOBAL_PREFIX
init|=
literal|"/solr/"
decl_stmt|;
comment|/**      * The prefix used as prefix in front of the       * {@link SolrConstants#PROPERTY_SERVER_NAME}.<p>      *<pre>      *     http://{host}:{port}/{stanbol-prefix}/{global-prefix}/{solr-server-name}/{solr-core-name}      *</pre>      */
annotation|@
name|Property
argument_list|(
name|value
operator|=
name|DEFAUTL_GLOBAL_PREFIX
argument_list|)
specifier|private
specifier|static
specifier|final
name|String
name|GLOBAL_PREFIX
init|=
literal|"org.apache.stanbol.commons.solr.web.dispatchfilter.prefix"
decl_stmt|;
comment|/**      * The key is the name and the value represents a sorted list of      * {@link ServiceReference} to {@link CoreContainer} based on the      * {@link SolrConstants#PROPERTY_SERVER_RANKING} value.<p>      * Used to synchronise calls from the {@link #tracker} (so that not to      * update two Filters at the same time.      */
specifier|protected
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|solrServerRefMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
comment|/**      * The map with the registered Filter for a name.      */
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Filter
argument_list|>
name|published
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Filter
argument_list|>
argument_list|()
decl_stmt|;
comment|/**      * Will only work within Apache Felix. Registration of servlet {@link Filter}s      * is not part of the standard OSGI {@link HttpService} and therefore each      * OSGI implementation currently defines its own Interface to allow this.<p>      * To make this available in every OSGI Environment we would need to implement      * an own service with different implementation for supported OSGI      * Environments - or to wait until the standard is updated to also support      * filters.      */
annotation|@
name|Reference
specifier|protected
name|ExtHttpService
name|extHttpService
decl_stmt|;
specifier|protected
name|ComponentContext
name|context
decl_stmt|;
specifier|private
name|ServiceTracker
name|tracker
decl_stmt|;
comment|/**      * the global prefix with an '/' at the begin and end      */
specifier|private
name|String
name|gloablPrefix
decl_stmt|;
comment|/**      * Customiser for the {@link ServiceTracker} that tracks all {@link CoreContainer}      * services. This needs to evaluate<ul>      *<li> The {@link SolrConstants#PROPERTY_SERVER_NAME} to determine the path      * under that the {@link CoreContainer} is registered. If no name is defined      * the core will not be published!      *<li> The {@link SolrConstants#PROPERTY_SERVER_PUBLISH_REST} to determine      * if the Solr RESTful API of a {@link CoreContainer} should be published to       * the {@link ExtHttpService} via a servlet {@link Filter}      *<li> The {@link SolrConstants#PROPERTY_SERVER_RANKING} to publish the      * {@link CoreContainer} with the highest ranking in case two       * {@link CoreContainer} do use the same       * {@link SolrConstants#PROPERTY_SERVER_NAME} value      *</ul>      */
specifier|private
name|ServiceTrackerCustomizer
name|trackerCustomizer
init|=
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
name|ref
parameter_list|,
name|Object
name|service
parameter_list|)
block|{
if|if
condition|(
name|isPublished
argument_list|(
name|ref
argument_list|)
condition|)
block|{
comment|//this ensures also a valid name
name|remove
argument_list|(
name|ref
operator|.
name|getProperty
argument_list|(
name|PROPERTY_SERVER_NAME
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|,
name|ref
argument_list|)
expr_stmt|;
block|}
comment|//else nothing to do
name|context
operator|.
name|getBundleContext
argument_list|()
operator|.
name|ungetService
argument_list|(
name|ref
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
name|ref
parameter_list|,
name|Object
name|service
parameter_list|)
block|{
if|if
condition|(
name|isPublished
argument_list|(
name|ref
argument_list|)
condition|)
block|{
name|addOrUpdate
argument_list|(
name|ref
operator|.
name|getProperty
argument_list|(
name|PROPERTY_SERVER_NAME
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|,
name|ref
argument_list|,
operator|(
name|CoreContainer
operator|)
name|service
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//the config might have changed to set publishREST to false
name|Object
name|value
init|=
name|ref
operator|.
name|getProperty
argument_list|(
name|PROPERTY_SERVER_NAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|remove
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|,
name|ref
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|void
name|addOrUpdate
parameter_list|(
name|String
name|name
parameter_list|,
name|ServiceReference
name|ref
parameter_list|,
name|CoreContainer
name|server
parameter_list|)
block|{
synchronized|synchronized
init|(
name|solrServerRefMap
init|)
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|refs
init|=
name|solrServerRefMap
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|ServiceReference
name|oldBest
init|=
name|refs
operator|!=
literal|null
operator|&&
operator|!
name|refs
operator|.
name|isEmpty
argument_list|()
condition|?
name|refs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
else|:
literal|null
decl_stmt|;
comment|//maybe publishREST was set to TRUE or a name was added
comment|//so modified might also mean adding in this context
if|if
condition|(
name|refs
operator|==
literal|null
condition|)
block|{
comment|//maybe this is even the first for this name
name|refs
operator|=
operator|new
name|ArrayList
argument_list|<
name|ServiceReference
argument_list|>
argument_list|(
literal|3
argument_list|)
expr_stmt|;
name|solrServerRefMap
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|refs
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|refs
operator|.
name|contains
argument_list|(
name|ref
argument_list|)
condition|)
block|{
comment|//check if we need to add the ref
name|refs
operator|.
name|add
argument_list|(
name|ref
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|refs
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
comment|//if more than one
comment|//a change to the serviceRanking config might change
comment|//the order modified
name|Collections
operator|.
name|sort
argument_list|(
name|refs
argument_list|,
name|ServiceReferenceRankingComparator
operator|.
name|INSTANCE
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|oldBest
operator|==
literal|null
operator|||
operator|!
name|refs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
name|oldBest
argument_list|)
condition|)
block|{
comment|//the first entry changed ... update the filter
name|updateFilter
argument_list|(
name|name
argument_list|,
name|ref
argument_list|,
name|server
argument_list|)
expr_stmt|;
block|}
comment|//else ... no change needed
block|}
block|}
specifier|private
name|void
name|remove
parameter_list|(
name|String
name|name
parameter_list|,
name|ServiceReference
name|ref
parameter_list|)
block|{
synchronized|synchronized
init|(
name|solrServerRefMap
init|)
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|refs
init|=
name|solrServerRefMap
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|refs
operator|!=
literal|null
condition|)
block|{
name|ServiceReference
name|best
init|=
name|refs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|refs
operator|.
name|remove
argument_list|(
name|ref
argument_list|)
expr_stmt|;
if|if
condition|(
name|refs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|solrServerRefMap
operator|.
name|remove
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|updateFilter
argument_list|(
name|name
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|refs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
name|best
argument_list|)
condition|)
block|{
comment|//here the equals check based on ServiceReferencees is OK
comment|//because we only check if the first element in the list
comment|//changed ... so even an instance check would be OK
name|updateFilter
argument_list|(
name|name
argument_list|,
name|refs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
comment|// no cores for that name ... nothing to remove
block|}
block|}
annotation|@
name|Override
specifier|public
name|Object
name|addingService
parameter_list|(
name|ServiceReference
name|ref
parameter_list|)
block|{
name|CoreContainer
name|service
init|=
operator|(
name|CoreContainer
operator|)
name|context
operator|.
name|getBundleContext
argument_list|()
operator|.
name|getService
argument_list|(
name|ref
argument_list|)
decl_stmt|;
if|if
condition|(
name|isPublished
argument_list|(
name|ref
argument_list|)
condition|)
block|{
name|addOrUpdate
argument_list|(
name|ref
operator|.
name|getProperty
argument_list|(
name|PROPERTY_SERVER_NAME
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|,
name|ref
argument_list|,
name|service
argument_list|)
expr_stmt|;
block|}
return|return
name|context
operator|.
name|getBundleContext
argument_list|()
operator|.
name|getService
argument_list|(
name|ref
argument_list|)
return|;
block|}
comment|/**          * checks if the parsed {@link ServiceReference} refers to a           * {@link SolrCore} that needs to be published.          * @param ref the ServiceReference          * @return<code>true</code> if the referenced {@link CoreContainer}          * needs to be published and defines the required          * {@link SolrConstants#PROPERTY_SERVER_NAME server name} property.          */
specifier|private
name|boolean
name|isPublished
parameter_list|(
name|ServiceReference
name|ref
parameter_list|)
block|{
name|Object
name|value
init|=
name|ref
operator|.
name|getProperty
argument_list|(
name|PROPERTY_SERVER_PUBLISH_REST
argument_list|)
decl_stmt|;
name|boolean
name|publishState
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|Boolean
condition|)
block|{
name|publishState
operator|=
operator|(
operator|(
name|Boolean
operator|)
name|value
operator|)
operator|.
name|booleanValue
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|publishState
operator|=
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|publishState
operator|=
name|SolrConstants
operator|.
name|DEFAULT_PUBLISH_REST
expr_stmt|;
block|}
if|if
condition|(
name|publishState
condition|)
block|{
comment|//if publishing is enabled also check for a valid name
name|value
operator|=
name|ref
operator|.
name|getProperty
argument_list|(
name|PROPERTY_SERVER_NAME
argument_list|)
expr_stmt|;
return|return
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
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
decl_stmt|;
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
throws|,
name|ServletException
block|{
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
name|BundleContext
name|bc
init|=
name|context
operator|.
name|getBundleContext
argument_list|()
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
name|GLOBAL_PREFIX
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|gloablPrefix
operator|=
name|value
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|gloablPrefix
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|!=
literal|'/'
condition|)
block|{
name|gloablPrefix
operator|=
literal|'/'
operator|+
name|gloablPrefix
expr_stmt|;
block|}
if|if
condition|(
name|gloablPrefix
operator|.
name|charAt
argument_list|(
name|gloablPrefix
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|!=
literal|'/'
condition|)
block|{
name|gloablPrefix
operator|=
name|gloablPrefix
operator|+
literal|'/'
expr_stmt|;
block|}
block|}
else|else
block|{
name|gloablPrefix
operator|=
name|DEFAUTL_GLOBAL_PREFIX
expr_stmt|;
block|}
name|tracker
operator|=
operator|new
name|ServiceTracker
argument_list|(
name|bc
argument_list|,
name|CoreContainer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|trackerCustomizer
argument_list|)
expr_stmt|;
comment|//now start tracking! ...
comment|//  ... as soon as the first CoreContainer is tracked the Filter will
comment|//      be created and added to the ExtHttpService
name|tracker
operator|.
name|open
argument_list|()
expr_stmt|;
block|}
comment|/**      * A change was made to the tracked CoreContainer (adding ,removal, ranking change).      * This removes and re-add the Servlet filter to apply such changes.      * @param name The name of the filter to be updated      * @param ref The serviceReference for the new CoreContainer to be added for      * the parsed name.<code>null</code> if the filter for that name needs only      * to be removed      * @param server The {@link CoreContainer} may be parsed in cases a reference      * is already available. If not the {@link #tracker} is used to look it up      * based on the parsed reference. This is basically a workaround for the      * fact that if the call originates form       * {@link ServiceTrackerCustomizer#addingService(ServiceReference)} the      * {@link CoreContainer} is not yet available via the tracker.      */
specifier|protected
name|void
name|updateFilter
parameter_list|(
name|String
name|name
parameter_list|,
name|ServiceReference
name|ref
parameter_list|,
name|CoreContainer
name|server
parameter_list|)
block|{
name|String
name|serverPrefix
init|=
name|gloablPrefix
operator|+
name|name
decl_stmt|;
name|Filter
name|filter
init|=
name|published
operator|.
name|remove
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|filter
operator|!=
literal|null
condition|)
block|{
name|extHttpService
operator|.
name|unregisterFilter
argument_list|(
name|filter
argument_list|)
expr_stmt|;
name|filter
operator|=
literal|null
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"removed ServletFilter for SolrServer {} and prefix {}"
argument_list|,
name|name
argument_list|,
name|serverPrefix
argument_list|)
expr_stmt|;
block|}
comment|// else no current filter for that name
if|if
condition|(
name|ref
operator|!=
literal|null
operator|||
name|server
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|server
operator|==
literal|null
condition|)
block|{
name|server
operator|=
operator|(
name|CoreContainer
operator|)
name|tracker
operator|.
name|getService
argument_list|(
name|ref
argument_list|)
expr_stmt|;
block|}
name|filter
operator|=
operator|new
name|SolrFilter
argument_list|(
name|server
argument_list|)
expr_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|filterPrpoerties
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
name|filterPrpoerties
operator|.
name|put
argument_list|(
literal|"path-prefix"
argument_list|,
name|serverPrefix
argument_list|)
expr_stmt|;
name|String
name|filterPrefix
init|=
name|serverPrefix
operator|+
literal|"/.*"
decl_stmt|;
try|try
block|{
name|extHttpService
operator|.
name|registerFilter
argument_list|(
name|filter
argument_list|,
name|filterPrefix
argument_list|,
name|filterPrpoerties
argument_list|,
literal|0
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ServletException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to register SolrDispatchFilter for"
operator|+
literal|"CoreContainer with name"
operator|+
name|name
operator|+
literal|" (prefix: "
operator|+
name|filterPrefix
operator|+
literal|"| properties: "
operator|+
name|filterPrpoerties
operator|+
literal|")."
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"added ServletFilter for SolrServer {} and prefix {}"
argument_list|,
name|name
argument_list|,
name|serverPrefix
argument_list|)
expr_stmt|;
block|}
comment|// else no new filter to add
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
comment|//stop tracking
name|tracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|tracker
operator|=
literal|null
expr_stmt|;
comment|//remove the filters
synchronized|synchronized
init|(
name|solrServerRefMap
init|)
block|{
comment|//copy keys to an array to avoid concurrent modifications
for|for
control|(
name|String
name|name
range|:
name|published
operator|.
name|keySet
argument_list|()
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|published
operator|.
name|size
argument_list|()
index|]
argument_list|)
control|)
block|{
name|updateFilter
argument_list|(
name|name
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
comment|//remove all active filters
block|}
block|}
name|published
operator|.
name|clear
argument_list|()
expr_stmt|;
name|solrServerRefMap
operator|.
name|clear
argument_list|()
expr_stmt|;
name|context
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * Simple {@link DelegatingSolrDispatchFilter} implementation that uses the      * {@link CoreContainer} parsed within the constructor      * @author Rupert Westenthaler      */
specifier|private
class|class
name|SolrFilter
extends|extends
name|DelegatingSolrDispatchFilter
block|{
specifier|private
name|CoreContainer
name|server
decl_stmt|;
specifier|protected
name|SolrFilter
parameter_list|(
name|CoreContainer
name|server
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|server
operator|=
name|server
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|CoreContainer
name|getCoreContainer
parameter_list|()
block|{
return|return
name|server
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|ungetCoreContainer
parameter_list|()
block|{
name|server
operator|=
literal|null
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

