begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|core
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
name|HashMap
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
name|rick
operator|.
name|servicesapi
operator|.
name|yard
operator|.
name|Cache
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
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
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|yard
operator|.
name|YardManager
import|;
end_import

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
name|YardManagerImpl
implements|implements
name|YardManager
block|{
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|YardManagerImpl
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_MULTIPLE
argument_list|,
name|referenceInterface
operator|=
name|Yard
operator|.
name|class
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
argument_list|,
name|bind
operator|=
literal|"bindYard"
argument_list|,
name|unbind
operator|=
literal|"unbindYard"
argument_list|)
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Yard
argument_list|>
name|yards
init|=
name|Collections
operator|.
name|emptyMap
argument_list|()
decl_stmt|;
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_MULTIPLE
argument_list|,
name|referenceInterface
operator|=
name|Cache
operator|.
name|class
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
argument_list|,
name|bind
operator|=
literal|"bindCache"
argument_list|,
name|unbind
operator|=
literal|"unbindCache"
argument_list|)
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Cache
argument_list|>
name|caches
init|=
name|Collections
operator|.
name|emptyMap
argument_list|()
decl_stmt|;
comment|// stat with a empty map!
comment|//    private ComponentContext context;
comment|//    @Activate
comment|//    protected void activate(ComponentContext context){
comment|//        log.debug("activating "+getClass()+" with "+context);
comment|//        //nothing to do for now!
comment|//        this.context = context;
comment|//    }
comment|//    @Deactivate
comment|//    protected void deactivate(ComponentContext context){
comment|//        context = null;
comment|//    }
specifier|protected
name|void
name|bindYard
parameter_list|(
name|Yard
name|yard
parameter_list|)
block|{
if|if
condition|(
name|yard
operator|!=
literal|null
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Yard
argument_list|>
name|tmp
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Yard
argument_list|>
argument_list|(
name|yards
argument_list|)
decl_stmt|;
name|tmp
operator|.
name|put
argument_list|(
name|yard
operator|.
name|getId
argument_list|()
argument_list|,
name|yard
argument_list|)
expr_stmt|;
name|this
operator|.
name|yards
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|tmp
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|unbindYard
parameter_list|(
name|Yard
name|yard
parameter_list|)
block|{
if|if
condition|(
name|yard
operator|!=
literal|null
operator|&&
name|yards
operator|.
name|containsKey
argument_list|(
name|yard
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Yard
argument_list|>
name|tmp
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Yard
argument_list|>
argument_list|(
name|yards
argument_list|)
decl_stmt|;
name|tmp
operator|.
name|remove
argument_list|(
name|yard
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|yards
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|tmp
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|bindCache
parameter_list|(
name|Cache
name|cache
parameter_list|)
block|{
if|if
condition|(
name|cache
operator|!=
literal|null
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Cache
argument_list|>
name|tmp
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Cache
argument_list|>
argument_list|(
name|caches
argument_list|)
decl_stmt|;
name|tmp
operator|.
name|put
argument_list|(
name|cache
operator|.
name|getId
argument_list|()
argument_list|,
name|cache
argument_list|)
expr_stmt|;
name|this
operator|.
name|caches
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|tmp
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|unbindCache
parameter_list|(
name|Cache
name|cache
parameter_list|)
block|{
if|if
condition|(
name|cache
operator|!=
literal|null
operator|&&
name|caches
operator|.
name|containsKey
argument_list|(
name|cache
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Cache
argument_list|>
name|tmp
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Cache
argument_list|>
argument_list|(
name|caches
argument_list|)
decl_stmt|;
name|tmp
operator|.
name|remove
argument_list|(
name|cache
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|caches
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|tmp
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Yard
name|getYard
parameter_list|(
name|String
name|id
parameter_list|)
block|{
return|return
name|yards
operator|.
name|get
argument_list|(
name|id
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|getYardIDs
parameter_list|()
block|{
return|return
name|yards
operator|.
name|keySet
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isYard
parameter_list|(
name|String
name|id
parameter_list|)
block|{
return|return
name|yards
operator|.
name|containsKey
argument_list|(
name|id
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Cache
name|getCache
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|Cache
name|cache
init|=
name|caches
operator|.
name|get
argument_list|(
name|id
argument_list|)
decl_stmt|;
return|return
name|cache
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|getCacheIDs
parameter_list|()
block|{
return|return
name|caches
operator|.
name|keySet
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isCache
parameter_list|(
name|String
name|id
parameter_list|)
block|{
return|return
name|caches
operator|.
name|containsKey
argument_list|(
name|id
argument_list|)
return|;
block|}
block|}
end_class

end_unit

