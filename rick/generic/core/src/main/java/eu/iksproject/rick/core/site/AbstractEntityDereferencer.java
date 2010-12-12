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
name|site
package|;
end_package

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
name|java
operator|.
name|util
operator|.
name|RandomAccess
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
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|site
operator|.
name|ConfiguredSite
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
name|site
operator|.
name|EntityDereferencer
import|;
end_import

begin_class
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityDereferencer
operator|.
name|ACCESS_URI
argument_list|,
name|label
operator|=
literal|"%dereference.baseUri.name"
argument_list|,
name|description
operator|=
literal|"%dereference.baseUri.description"
argument_list|)
specifier|public
specifier|abstract
class|class
name|AbstractEntityDereferencer
implements|implements
name|EntityDereferencer
block|{
specifier|protected
specifier|final
name|Logger
name|log
decl_stmt|;
specifier|protected
name|AbstractEntityDereferencer
parameter_list|(
name|Logger
name|log
parameter_list|)
block|{
name|this
operator|.
name|log
operator|=
name|log
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"create instance of "
operator|+
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|private
name|String
name|baseUri
decl_stmt|;
specifier|private
name|Dictionary
argument_list|<
name|String
argument_list|,
name|?
argument_list|>
name|config
decl_stmt|;
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|prefixes
decl_stmt|;
specifier|private
name|ComponentContext
name|context
decl_stmt|;
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getAccessUri
parameter_list|()
block|{
return|return
name|baseUri
return|;
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
literal|"in "
operator|+
name|AbstractEntityDereferencer
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|" activate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
comment|// TODO handle updates to the configuration
if|if
condition|(
name|context
operator|!=
literal|null
operator|&&
name|context
operator|.
name|getProperties
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|?
argument_list|>
name|properties
init|=
name|context
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|Object
name|baseUri
init|=
name|properties
operator|.
name|get
argument_list|(
name|EntityDereferencer
operator|.
name|ACCESS_URI
argument_list|)
decl_stmt|;
if|if
condition|(
name|baseUri
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|baseUri
operator|=
name|baseUri
operator|.
name|toString
argument_list|()
expr_stmt|;
comment|//now set the new config
name|this
operator|.
name|config
operator|=
name|properties
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The property "
operator|+
name|EntityDereferencer
operator|.
name|ACCESS_URI
operator|+
literal|" must be defined"
argument_list|)
throw|;
block|}
comment|//TODO: I am sure, there is some Utility, that supports getting multiple
comment|//      values from a OSGI Dictionary
name|Object
name|prefixObject
init|=
name|properties
operator|.
name|get
argument_list|(
name|ConfiguredSite
operator|.
name|ENTITY_PREFIX
argument_list|)
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|prefixes
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|prefixObject
operator|==
literal|null
condition|)
block|{
name|prefixes
operator|=
literal|null
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prefixObject
operator|.
name|getClass
argument_list|()
operator|.
name|isArray
argument_list|()
condition|)
block|{
name|prefixes
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|(
name|String
index|[]
operator|)
name|prefixObject
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prefixObject
operator|instanceof
name|Collection
argument_list|<
name|?
argument_list|>
condition|)
block|{
name|prefixes
operator|.
name|addAll
argument_list|(
operator|(
name|Collection
argument_list|<
name|String
argument_list|>
operator|)
name|prefixObject
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//assuming a single value
name|prefixes
operator|.
name|add
argument_list|(
name|prefixObject
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|prefixes
argument_list|)
expr_stmt|;
comment|//sort the prefixes List
name|this
operator|.
name|prefixes
operator|=
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|prefixes
argument_list|)
expr_stmt|;
comment|//use an unmodifiable wrapper for the member variable
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The property "
operator|+
name|EntityDereferencer
operator|.
name|ACCESS_URI
operator|+
literal|" must be defined"
argument_list|)
throw|;
block|}
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
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|AbstractEntityDereferencer
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|" deactivate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
name|this
operator|.
name|config
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|prefixes
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|baseUri
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * Default Implementation based on the configured EntityPrefixes that      * executes in O(log n) by using a binary search      * {@link Collections#binarySearch(List, Object)} in the list of prefixes      * and than checking if the parsed uri starts with the prefix of the      * "insertion point-1" as returned by the binary search      */
annotation|@
name|Override
specifier|public
name|boolean
name|canDereference
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
comment|//binary search for the uri returns "insertion point-1"
name|int
name|pos
init|=
name|Collections
operator|.
name|binarySearch
argument_list|(
name|prefixes
argument_list|,
name|uri
argument_list|)
decl_stmt|;
comment|//This site can dereference the URI if
comment|//  - the pos>=0 (parsed uri> than the first element in the list AND
comment|//  - the returned index is an prefix of the parsed uri
return|return
name|pos
operator|>=
literal|0
operator|&&
name|uri
operator|.
name|startsWith
argument_list|(
name|prefixes
operator|.
name|get
argument_list|(
name|pos
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * The prefixes as configured for this dereferencer as unmodifiable and      * sorted list guaranteed to implementing {@link RandomAccess}.      *      * @return the prefixes in an unmodifiable and sorted list that implements      *         {@link RandomAccess}.      */
specifier|protected
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|getPrefixes
parameter_list|()
block|{
return|return
name|prefixes
return|;
block|}
comment|/**      * The OSGI configuration as provided by the activate method      *      * @return      */
specifier|protected
specifier|final
name|Dictionary
argument_list|<
name|String
argument_list|,
name|?
argument_list|>
name|getSiteConfiguration
parameter_list|()
block|{
return|return
name|config
return|;
block|}
specifier|protected
specifier|final
name|ComponentContext
name|getComponentContext
parameter_list|()
block|{
return|return
name|context
return|;
block|}
block|}
end_class

end_unit

