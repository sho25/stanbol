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
name|dispatch
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
name|Comparator
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
name|impl
operator|.
name|SolrDispatchFilterComponent
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

begin_comment
comment|/**  * Rather than using directly a {@link ServiceReference} to parse the  * {@link CoreContainer} to be used for the dispatch filter this allows to  * parse the {@link SolrConstants#PROPERTY_CORE_NAME name} or an own  * {@link Filter} that is used to {@link ServiceTracker track} {@link CoreContainer}  * instances registered in the OSGI environment. In case of the empty  * Constructor a simple Class filter is used to track for CoreContainer services.<p>  * The CoreContainer to be used for the dispatch filter is searched during the  * the execution of the Servlets   * {@link javax.servlet.Filter#init(javax.servlet.FilterConfig)}.<p>  * This implementation does NOT remove the Filter or change the {@link CoreContainer}  * on any change in the OSGI environment. See {@link SolrDispatchFilterComponent}  * if you need this functionality.  * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|SolrServiceDispatchFilter
extends|extends
name|DelegatingSolrDispatchFilter
block|{
specifier|private
specifier|static
specifier|final
name|Comparator
argument_list|<
name|ServiceReference
argument_list|>
name|SERVICE_REFERENCE_COMPARATOR
init|=
name|ServiceReferenceRankingComparator
operator|.
name|INSTANCE
decl_stmt|;
specifier|private
name|BundleContext
name|context
decl_stmt|;
specifier|private
name|ServiceReference
name|coreContainerRef
decl_stmt|;
specifier|private
name|Filter
name|filter
decl_stmt|;
comment|/**      * Creates a tracking Solr dispatch filter for the CoreContainer with the      * parsed {@link SolrConstants#PROPERTY_SERVER_NAME} value      * @param context the context      * @param solrServerName the name of the CoreContainer (value of the {@link SolrConstants#PROPERTY_SERVER_NAME})      * @param stc An optional {@link ServiceTrackerCustomizer} used for the tracking      * the {@link CoreContainer}      * @throws InvalidSyntaxException if the created {@link Filter} for the parsed name is invalid      */
specifier|public
name|SolrServiceDispatchFilter
parameter_list|(
name|BundleContext
name|context
parameter_list|,
name|String
name|solrServerName
parameter_list|,
name|ServiceTrackerCustomizer
name|stc
parameter_list|)
throws|throws
name|InvalidSyntaxException
block|{
name|super
argument_list|()
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
name|IllegalArgumentException
argument_list|(
literal|"The parsed BundleContext MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
if|if
condition|(
name|solrServerName
operator|==
literal|null
operator|||
name|solrServerName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed SolrServer name MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|String
name|filterString
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
name|CoreContainer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|SolrConstants
operator|.
name|PROPERTY_SERVER_NAME
argument_list|,
name|solrServerName
argument_list|)
decl_stmt|;
name|filter
operator|=
name|context
operator|.
name|createFilter
argument_list|(
name|filterString
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a tracking Solr dispatch filter using the parsed filter to select      * services. Note that the filter MUST assure that all tracked services are      * {@link CoreContainer} instances!      * @param context the context      * @param filter the Filter that selects the {@link CoreContainer} service      * to be used for Request dispatching.      * the {@link CoreContainer}      */
specifier|public
name|SolrServiceDispatchFilter
parameter_list|(
name|BundleContext
name|context
parameter_list|,
name|Filter
name|filter
parameter_list|)
block|{
name|super
argument_list|()
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
name|IllegalArgumentException
argument_list|(
literal|"The parsed BundleContext MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
if|if
condition|(
name|filter
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Filter for tracking CoreContainer instances MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|filter
operator|=
name|filter
expr_stmt|;
block|}
comment|/**      * Creates a Dispatch filter for CoreContainer registered as OSGI services.      * In case more than one {@link CoreContainer} is available the one with the      * highest {@link Constants#SERVICE_RANKING} will be used. Instances with       * no or the same Service rank are not sorted.      * @param context the context used to look for the CoreContainer      */
specifier|public
name|SolrServiceDispatchFilter
parameter_list|(
name|BundleContext
name|context
parameter_list|)
block|{
name|super
argument_list|()
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
name|IllegalArgumentException
argument_list|(
literal|"The parsed BundleContext MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
name|this
operator|.
name|filter
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|CoreContainer
name|getCoreContainer
parameter_list|()
block|{
name|ungetCoreContainer
argument_list|()
expr_stmt|;
comment|//unget the previouse used service
name|ServiceReference
index|[]
name|references
decl_stmt|;
try|try
block|{
name|references
operator|=
name|filter
operator|==
literal|null
condition|?
name|context
operator|.
name|getServiceReferences
argument_list|(
name|CoreContainer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|null
argument_list|)
else|:
name|context
operator|.
name|getServiceReferences
argument_list|(
operator|(
name|String
operator|)
literal|null
argument_list|,
name|filter
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InvalidSyntaxException
name|e
parameter_list|)
block|{
name|references
operator|=
literal|null
expr_stmt|;
comment|//can not be happen, because we created the filter already in the
comment|//constructor and only need to parse it again because BundleContext
comment|//is missing a Method to parse a Filter object when getting
comment|//ServiceReferences
block|}
if|if
condition|(
name|references
operator|==
literal|null
operator|||
name|references
operator|.
name|length
operator|==
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to find CoreContainer instance "
operator|+
operator|(
name|filter
operator|!=
literal|null
condition|?
operator|(
literal|"for filter"
operator|+
name|filter
operator|.
name|toString
argument_list|()
operator|)
else|:
literal|""
operator|)
operator|+
literal|"!"
argument_list|)
throw|;
block|}
else|else
block|{
if|if
condition|(
name|references
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|Arrays
operator|.
name|sort
argument_list|(
name|references
argument_list|,
name|ServiceReferenceRankingComparator
operator|.
name|INSTANCE
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|coreContainerRef
operator|=
name|references
index|[
literal|0
index|]
expr_stmt|;
block|}
name|Object
name|service
init|=
name|context
operator|.
name|getService
argument_list|(
name|coreContainerRef
argument_list|)
decl_stmt|;
if|if
condition|(
name|service
operator|instanceof
name|CoreContainer
condition|)
block|{
return|return
operator|(
name|CoreContainer
operator|)
name|service
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"The parsed Filter '"
operator|+
name|filter
operator|.
name|toString
argument_list|()
operator|+
literal|" selected a service '"
operator|+
name|service
operator|+
literal|"'(class: "
operator|+
name|service
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|") that is not compatiple with "
operator|+
name|CoreContainer
operator|.
name|class
operator|.
name|getName
argument_list|()
operator|+
literal|"!"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|void
name|ungetCoreContainer
parameter_list|()
block|{
if|if
condition|(
name|coreContainerRef
operator|!=
literal|null
condition|)
block|{
name|context
operator|.
name|ungetService
argument_list|(
name|coreContainerRef
argument_list|)
expr_stmt|;
block|}
name|coreContainerRef
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_class

end_unit

