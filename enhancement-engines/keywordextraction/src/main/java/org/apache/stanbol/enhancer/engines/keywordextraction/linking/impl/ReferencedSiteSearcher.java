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
name|enhancer
operator|.
name|engines
operator|.
name|keywordextraction
operator|.
name|linking
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
name|Collection
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
name|Iterator
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
name|Set
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
name|enhancer
operator|.
name|engines
operator|.
name|keywordextraction
operator|.
name|linking
operator|.
name|EntitySearcher
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
name|model
operator|.
name|Entity
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
name|model
operator|.
name|Representation
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
name|query
operator|.
name|FieldQuery
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
name|query
operator|.
name|QueryResultList
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
name|site
operator|.
name|SiteException
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

begin_class
specifier|public
specifier|final
class|class
name|ReferencedSiteSearcher
extends|extends
name|TrackingEntitySearcher
argument_list|<
name|Site
argument_list|>
implements|implements
name|EntitySearcher
block|{
specifier|private
specifier|final
name|String
name|siteId
decl_stmt|;
specifier|private
specifier|final
name|Integer
name|limit
decl_stmt|;
specifier|public
name|ReferencedSiteSearcher
parameter_list|(
name|BundleContext
name|context
parameter_list|,
name|String
name|siteId
parameter_list|,
name|Integer
name|limit
parameter_list|)
block|{
name|super
argument_list|(
name|context
argument_list|,
name|Site
operator|.
name|class
argument_list|,
name|Collections
operator|.
name|singletonMap
argument_list|(
name|SiteConfiguration
operator|.
name|ID
argument_list|,
name|siteId
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|siteId
operator|=
name|siteId
expr_stmt|;
name|this
operator|.
name|limit
operator|=
name|limit
operator|!=
literal|null
operator|&&
name|limit
operator|>
literal|0
condition|?
name|limit
else|:
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Representation
name|get
parameter_list|(
name|String
name|id
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|includeFields
parameter_list|)
block|{
if|if
condition|(
name|id
operator|==
literal|null
operator|||
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
name|Entity
name|entity
decl_stmt|;
name|Site
name|site
init|=
name|getSearchService
argument_list|()
decl_stmt|;
if|if
condition|(
name|site
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"ReferencedSite "
operator|+
name|siteId
operator|+
literal|" is currently not available"
argument_list|)
throw|;
block|}
try|try
block|{
name|entity
operator|=
name|site
operator|.
name|getEntity
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SiteException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Exception while getting "
operator|+
name|id
operator|+
literal|" from the ReferencedSite "
operator|+
name|site
operator|.
name|getId
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
return|return
name|entity
operator|==
literal|null
condition|?
literal|null
else|:
name|entity
operator|.
name|getRepresentation
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|lookup
parameter_list|(
name|String
name|field
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|includeFields
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|search
parameter_list|,
name|String
modifier|...
name|languages
parameter_list|)
throws|throws
name|IllegalStateException
block|{
comment|//build the query and than return the result
name|Site
name|site
init|=
name|getSearchService
argument_list|()
decl_stmt|;
if|if
condition|(
name|site
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"ReferencedSite "
operator|+
name|siteId
operator|+
literal|" is currently not available"
argument_list|)
throw|;
block|}
name|FieldQuery
name|query
init|=
name|EntitySearcherUtils
operator|.
name|createFieldQuery
argument_list|(
name|site
operator|.
name|getQueryFactory
argument_list|()
argument_list|,
name|field
argument_list|,
name|includeFields
argument_list|,
name|search
argument_list|,
name|languages
argument_list|)
decl_stmt|;
if|if
condition|(
name|limit
operator|!=
literal|null
condition|)
block|{
name|query
operator|.
name|setLimit
argument_list|(
name|limit
argument_list|)
expr_stmt|;
block|}
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|results
decl_stmt|;
try|try
block|{
name|results
operator|=
name|site
operator|.
name|find
argument_list|(
name|query
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SiteException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Exception while searchign for "
operator|+
name|search
operator|+
literal|'@'
operator|+
name|Arrays
operator|.
name|toString
argument_list|(
name|languages
argument_list|)
operator|+
literal|"in the ReferencedSite "
operator|+
name|site
operator|.
name|getId
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
return|return
name|results
operator|.
name|results
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|supportsOfflineMode
parameter_list|()
block|{
name|Site
name|site
init|=
name|getSearchService
argument_list|()
decl_stmt|;
comment|//Do not throw an exception here if the site is not available. Just return false
return|return
name|site
operator|==
literal|null
condition|?
literal|false
else|:
name|site
operator|.
name|supportsLocalMode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Integer
name|getLimit
parameter_list|()
block|{
return|return
name|limit
return|;
block|}
block|}
end_class

end_unit

