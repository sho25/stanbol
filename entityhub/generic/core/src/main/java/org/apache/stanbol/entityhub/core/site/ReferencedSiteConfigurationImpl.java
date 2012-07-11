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
name|core
operator|.
name|site
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
name|ManagedEntityState
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
name|MappingState
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
name|EntityDereferencer
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
name|site
operator|.
name|ReferencedSiteConfiguration
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
name|CacheStrategy
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
name|ComponentFactory
import|;
end_import

begin_class
specifier|public
class|class
name|ReferencedSiteConfigurationImpl
extends|extends
name|SiteConfigurationImpl
implements|implements
name|ReferencedSiteConfiguration
block|{
specifier|public
name|ReferencedSiteConfigurationImpl
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|super
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|validateConfiguration
parameter_list|()
throws|throws
name|ConfigurationException
block|{
name|super
operator|.
name|validateConfiguration
argument_list|()
expr_stmt|;
comment|//check if all the Enumerated values are valid strings and convert them
comment|//to enumeration instances
try|try
block|{
name|setCacheStrategy
argument_list|(
name|getCacheStrategy
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|CACHE_STRATEGY
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"Unknown CachStrategy (%s=%s) for Site %s! Valid values are %s "
argument_list|,
name|CACHE_STRATEGY
argument_list|,
name|config
operator|.
name|get
argument_list|(
name|CACHE_STRATEGY
argument_list|)
argument_list|,
name|getId
argument_list|()
argument_list|,
name|Arrays
operator|.
name|toString
argument_list|(
name|CacheStrategy
operator|.
name|values
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
argument_list|)
throw|;
block|}
comment|//check that a cacheId is set if the CacheStrategy != none
if|if
condition|(
name|CacheStrategy
operator|.
name|none
operator|!=
name|getCacheStrategy
argument_list|()
operator|&&
name|getCacheId
argument_list|()
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|CACHE_ID
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"The CacheID (%s) MUST NOT be NULL nor empty if the the CacheStrategy != %s"
argument_list|,
name|CACHE_ID
argument_list|,
name|CacheStrategy
operator|.
name|none
argument_list|)
argument_list|)
throw|;
block|}
comment|//check that a accessUri and an entity dereferencer is set if the
comment|//cacheStrategy != CacheStrategy.all
if|if
condition|(
name|CacheStrategy
operator|.
name|all
operator|!=
name|getCacheStrategy
argument_list|()
condition|)
block|{
if|if
condition|(
name|getAccessUri
argument_list|()
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|ACCESS_URI
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"An AccessUri (%s) MUST be configured if the CacheStrategy != %s"
argument_list|,
name|ACCESS_URI
argument_list|,
name|CacheStrategy
operator|.
name|all
argument_list|)
argument_list|)
throw|;
block|}
if|if
condition|(
name|getEntityDereferencerType
argument_list|()
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|ENTITY_DEREFERENCER_TYPE
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"An EntityDereferencer (%s) MUST be configured if the CacheStrategy != %s"
argument_list|,
name|ENTITY_DEREFERENCER_TYPE
argument_list|,
name|CacheStrategy
operator|.
name|all
argument_list|)
argument_list|)
throw|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getAccessUri
parameter_list|()
block|{
name|Object
name|accessUri
init|=
name|config
operator|.
name|get
argument_list|(
name|ACCESS_URI
argument_list|)
decl_stmt|;
return|return
name|accessUri
operator|==
literal|null
condition|?
literal|null
else|:
name|accessUri
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      *       * @param uri      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getAccessUri()      */
specifier|public
specifier|final
name|void
name|setAccessUri
parameter_list|(
name|String
name|uri
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|uri
operator|==
literal|null
operator|||
name|uri
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|ACCESS_URI
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|ACCESS_URI
argument_list|,
name|uri
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getCacheId
parameter_list|()
block|{
name|Object
name|id
init|=
name|config
operator|.
name|get
argument_list|(
name|CACHE_ID
argument_list|)
decl_stmt|;
return|return
name|id
operator|==
literal|null
operator|||
name|id
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|id
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      *       * @param id      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getCacheId()      */
specifier|public
specifier|final
name|void
name|setCacheId
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|UnsupportedOperationException
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
name|config
operator|.
name|remove
argument_list|(
name|CACHE_ID
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|CACHE_ID
argument_list|,
name|id
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|CacheStrategy
name|getCacheStrategy
parameter_list|()
block|{
name|Object
name|cacheStrategy
init|=
name|config
operator|.
name|get
argument_list|(
name|CACHE_STRATEGY
argument_list|)
decl_stmt|;
if|if
condition|(
name|cacheStrategy
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
elseif|else
if|if
condition|(
name|cacheStrategy
operator|instanceof
name|CacheStrategy
condition|)
block|{
return|return
operator|(
name|CacheStrategy
operator|)
name|cacheStrategy
return|;
block|}
else|else
block|{
return|return
name|CacheStrategy
operator|.
name|valueOf
argument_list|(
name|cacheStrategy
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
block|}
comment|/**      *       * @param strategy      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getCacheStrategy()      */
specifier|public
specifier|final
name|void
name|setCacheStrategy
parameter_list|(
name|CacheStrategy
name|strategy
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|strategy
operator|==
literal|null
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|CACHE_STRATEGY
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|CACHE_STRATEGY
argument_list|,
name|strategy
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getEntityDereferencerType
parameter_list|()
block|{
name|Object
name|dereferencer
init|=
name|config
operator|.
name|get
argument_list|(
name|ENTITY_DEREFERENCER_TYPE
argument_list|)
decl_stmt|;
return|return
name|dereferencer
operator|==
literal|null
operator|||
name|dereferencer
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|dereferencer
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Setter for the type of the {@link EntityDereferencer} to be used by      * this site or<code>null</code> to remove the current configuration.<p>      * Note that the {@link EntityDereferencer} is only initialised of a valid      * {@link #getAccessUri() access URI} is configured. If the dereferencer is      * set to<code>null</code> dereferencing Entities will not be supported by      * this site. Entities might still be available form a local      * {@link #getCacheId() cache}.      * @param entityDereferencerType the key (OSGI name) of the component used      * to dereference Entities. This component must have an {@link ComponentFactory}      * and provide the {@link EntityDereferencer} service-      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getEntityDereferencerType()      */
specifier|public
specifier|final
name|void
name|setEntityDereferencerType
parameter_list|(
name|String
name|entityDereferencerType
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|entityDereferencerType
operator|==
literal|null
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|entityDereferencerType
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|ENTITY_DEREFERENCER_TYPE
argument_list|,
name|entityDereferencerType
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getEntitySearcherType
parameter_list|()
block|{
name|Object
name|type
init|=
name|config
operator|.
name|get
argument_list|(
name|ENTITY_SEARCHER_TYPE
argument_list|)
decl_stmt|;
return|return
name|type
operator|==
literal|null
operator|||
name|type
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|type
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Setter for the type of the {@link EntitySearcher} used to query for      * Entities by accessing a external service available at       * {@link #getQueryUri()}.<p>      * Note that the {@link EntitySearcher} will only be initialised of the      * {@link #getQueryUri() Query URI} is defined.      * @param entitySearcherType The string representing the {@link EntitySearcher}      * (the name of the OSGI component) or<code>null</code> to remove this      * configuration. The referenced component MUST have an {@link ComponentFactory}      * and provide the {@link EntitySearcher} service.      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getEntitySearcherType()      */
specifier|public
specifier|final
name|void
name|setEntitySearcherType
parameter_list|(
name|String
name|entitySearcherType
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|entitySearcherType
operator|==
literal|null
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|ENTITY_SEARCHER_TYPE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|ENTITY_SEARCHER_TYPE
argument_list|,
name|entitySearcherType
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQueryUri
parameter_list|()
block|{
name|Object
name|uri
init|=
name|config
operator|.
name|get
argument_list|(
name|QUERY_URI
argument_list|)
decl_stmt|;
return|return
name|uri
operator|==
literal|null
operator|||
name|uri
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|uri
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Setter for the uri of the remote service used to query for entities. If      * set to<code>null</code> this indicates that no such external service is      * available for this referenced site      * @param queryUri the uri of the external service used to query for entities      * or<code>null</code> if none.      * @throws UnsupportedOperationException in case this configuration is {@link #readonly}      * @see #getQueryUri()      */
specifier|public
specifier|final
name|void
name|setQueryUri
parameter_list|(
name|String
name|queryUri
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
if|if
condition|(
name|queryUri
operator|==
literal|null
operator|||
name|queryUri
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|QUERY_URI
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|QUERY_URI
argument_list|,
name|queryUri
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
