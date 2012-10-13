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
name|utils
operator|.
name|SiteUtils
operator|.
name|extractSiteMetadata
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
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
name|Map
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
name|mapping
operator|.
name|DefaultFieldMapperImpl
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
name|mapping
operator|.
name|FieldMappingUtils
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
name|mapping
operator|.
name|ValueConverterFactory
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
name|model
operator|.
name|EntityImpl
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
name|model
operator|.
name|InMemoryValueFactory
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
name|query
operator|.
name|QueryResultListImpl
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
name|EntityhubException
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
name|mapping
operator|.
name|FieldMapper
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
name|mapping
operator|.
name|FieldMapping
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
name|model
operator|.
name|ValueFactory
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
name|rdf
operator|.
name|RdfResourceEnum
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
name|FieldQueryFactory
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
name|ManagedSiteException
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
name|SiteException
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
name|util
operator|.
name|AdaptingIterator
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
comment|/**  * Generic implementation of the {@link ManagedSite} interface on top of a  * {@link Yard} instance.<p>  * This is expected to be a private class that needs not to be extended.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|YardSite
implements|implements
name|ManagedSite
block|{
specifier|public
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|YardSite
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Yard
name|yard
decl_stmt|;
specifier|private
name|SiteConfiguration
name|config
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|siteMetadata
decl_stmt|;
specifier|private
name|FieldMapper
name|fieldMapper
init|=
operator|new
name|DefaultFieldMapperImpl
argument_list|(
name|ValueConverterFactory
operator|.
name|getDefaultInstance
argument_list|()
argument_list|)
decl_stmt|;
specifier|public
name|YardSite
parameter_list|(
name|Yard
name|yard
parameter_list|,
name|SiteConfiguration
name|config
parameter_list|)
block|{
name|this
operator|.
name|yard
operator|=
name|yard
expr_stmt|;
name|this
operator|.
name|config
operator|=
name|config
expr_stmt|;
name|this
operator|.
name|siteMetadata
operator|=
name|extractSiteMetadata
argument_list|(
name|config
argument_list|,
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
argument_list|)
expr_stmt|;
comment|//all entities of managed sites are locally cached - so we can add this
comment|//to the site metadata
name|this
operator|.
name|siteMetadata
operator|.
name|put
argument_list|(
name|RdfResourceEnum
operator|.
name|isChached
operator|.
name|getUri
argument_list|()
argument_list|,
operator|(
name|Object
operator|)
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
name|fieldMapper
operator|=
operator|new
name|DefaultFieldMapperImpl
argument_list|(
name|ValueConverterFactory
operator|.
name|getDefaultInstance
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|config
operator|.
name|getFieldMappings
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"> Initialise configured field mappings"
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|configuredMapping
range|:
name|config
operator|.
name|getFieldMappings
argument_list|()
control|)
block|{
name|FieldMapping
name|mapping
init|=
name|FieldMappingUtils
operator|.
name|parseFieldMapping
argument_list|(
name|configuredMapping
argument_list|)
decl_stmt|;
if|if
condition|(
name|mapping
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"   - add FieldMapping {}"
argument_list|,
name|mapping
argument_list|)
expr_stmt|;
name|fieldMapper
operator|.
name|addMapping
argument_list|(
name|mapping
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|config
operator|.
name|getId
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|findReferences
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|ManagedSiteException
block|{
try|try
block|{
return|return
name|getYard
argument_list|()
operator|.
name|findReferences
argument_list|(
name|query
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ManagedSiteException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|find
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|ManagedSiteException
block|{
try|try
block|{
return|return
name|getYard
argument_list|()
operator|.
name|find
argument_list|(
name|query
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ManagedSiteException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|QueryResultList
argument_list|<
name|Entity
argument_list|>
name|findEntities
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|ManagedSiteException
block|{
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
name|getYard
argument_list|()
operator|.
name|findRepresentation
argument_list|(
name|query
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ManagedSiteException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
return|return
operator|new
name|QueryResultListImpl
argument_list|<
name|Entity
argument_list|>
argument_list|(
name|results
operator|.
name|getQuery
argument_list|()
argument_list|,
operator|new
name|AdaptingIterator
argument_list|<
name|Representation
argument_list|,
name|Entity
argument_list|>
argument_list|(
name|results
operator|.
name|iterator
argument_list|()
argument_list|,
operator|new
name|AdaptingIterator
operator|.
name|Adapter
argument_list|<
name|Representation
argument_list|,
name|Entity
argument_list|>
argument_list|()
block|{
specifier|private
specifier|final
name|String
name|siteId
init|=
name|config
operator|.
name|getId
argument_list|()
decl_stmt|;
annotation|@
name|Override
specifier|public
name|Entity
name|adapt
parameter_list|(
name|Representation
name|value
parameter_list|,
name|Class
argument_list|<
name|Entity
argument_list|>
name|type
parameter_list|)
block|{
name|Entity
name|entity
init|=
operator|new
name|EntityImpl
argument_list|(
name|siteId
argument_list|,
name|value
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|SiteUtils
operator|.
name|initEntityMetadata
argument_list|(
name|entity
argument_list|,
name|siteMetadata
argument_list|,
literal|null
argument_list|)
expr_stmt|;
return|return
name|entity
return|;
block|}
block|}
argument_list|,
name|Entity
operator|.
name|class
argument_list|)
argument_list|,
name|Entity
operator|.
name|class
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Entity
name|getEntity
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|ManagedSiteException
block|{
name|Representation
name|rep
decl_stmt|;
try|try
block|{
name|rep
operator|=
name|getYard
argument_list|()
operator|.
name|getRepresentation
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ManagedSiteException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
if|if
condition|(
name|rep
operator|!=
literal|null
condition|)
block|{
name|Entity
name|entity
init|=
operator|new
name|EntityImpl
argument_list|(
name|config
operator|.
name|getId
argument_list|()
argument_list|,
name|rep
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|SiteUtils
operator|.
name|initEntityMetadata
argument_list|(
name|entity
argument_list|,
name|siteMetadata
argument_list|,
literal|null
argument_list|)
expr_stmt|;
return|return
name|entity
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Stores the parsed representation to the Yard and also applies the      * configured {@link #getFieldMapper() FieldMappings}.      * @param The representation to store      */
annotation|@
name|Override
specifier|public
name|void
name|store
parameter_list|(
name|Representation
name|representation
parameter_list|)
throws|throws
name|ManagedSiteException
block|{
try|try
block|{
name|Yard
name|yard
init|=
name|getYard
argument_list|()
decl_stmt|;
name|fieldMapper
operator|.
name|applyMappings
argument_list|(
name|representation
argument_list|,
name|representation
argument_list|,
name|yard
operator|.
name|getValueFactory
argument_list|()
argument_list|)
expr_stmt|;
name|yard
operator|.
name|store
argument_list|(
name|representation
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ManagedSiteException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|/**      * Stores the parsed representations to the Yard and also applies the      * configured {@link #getFieldMapper() FieldMappings}.      * @param The representations to store      */
annotation|@
name|Override
specifier|public
name|void
name|store
parameter_list|(
specifier|final
name|Iterable
argument_list|<
name|Representation
argument_list|>
name|representations
parameter_list|)
throws|throws
name|ManagedSiteException
block|{
try|try
block|{
name|Yard
name|yard
init|=
name|getYard
argument_list|()
decl_stmt|;
specifier|final
name|ValueFactory
name|vf
init|=
name|yard
operator|.
name|getValueFactory
argument_list|()
decl_stmt|;
name|yard
operator|.
name|store
argument_list|(
operator|new
name|Iterable
argument_list|<
name|Representation
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Iterator
argument_list|<
name|Representation
argument_list|>
name|iterator
parameter_list|()
block|{
return|return
operator|new
name|Iterator
argument_list|<
name|Representation
argument_list|>
argument_list|()
block|{
name|Iterator
argument_list|<
name|Representation
argument_list|>
name|it
init|=
name|representations
operator|.
name|iterator
argument_list|()
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|it
operator|.
name|hasNext
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Representation
name|next
parameter_list|()
block|{
name|Representation
name|next
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|fieldMapper
operator|.
name|applyMappings
argument_list|(
name|next
argument_list|,
name|next
argument_list|,
name|vf
argument_list|)
expr_stmt|;
return|return
name|next
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|()
block|{
name|it
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
return|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ManagedSiteException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|delete
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|ManagedSiteException
block|{
try|try
block|{
name|getYard
argument_list|()
operator|.
name|remove
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ManagedSiteException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|deleteAll
parameter_list|()
throws|throws
name|ManagedSiteException
block|{
try|try
block|{
name|getYard
argument_list|()
operator|.
name|removeAll
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ManagedSiteException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|InputStream
name|getContent
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|contentType
parameter_list|)
throws|throws
name|ManagedSiteException
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Content retrieval not supported"
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|FieldMapper
name|getFieldMapper
parameter_list|()
block|{
return|return
name|fieldMapper
return|;
block|}
annotation|@
name|Override
specifier|public
name|FieldQueryFactory
name|getQueryFactory
parameter_list|()
block|{
return|return
name|getYard
argument_list|()
operator|.
name|getQueryFactory
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|SiteConfiguration
name|getConfiguration
parameter_list|()
block|{
return|return
name|config
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|supportsLocalMode
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|supportsSearch
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
comment|/**      * Retruns the {@link Yard} or an {@link IllegalStateException} if the      * instance is already {@link #close() closed}      * @return the yard      * @throws IllegalStateException if the site was already {@link #close() closed}      */
specifier|protected
name|Yard
name|getYard
parameter_list|()
block|{
name|Yard
name|yard
init|=
name|this
operator|.
name|yard
decl_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"This ManagedSite is no longer active"
argument_list|)
throw|;
block|}
return|return
name|yard
return|;
block|}
specifier|public
name|void
name|close
parameter_list|()
block|{
name|this
operator|.
name|yard
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|config
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_class

end_unit

