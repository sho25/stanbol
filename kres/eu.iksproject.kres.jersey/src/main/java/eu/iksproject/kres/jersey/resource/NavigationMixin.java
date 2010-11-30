begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|jersey
operator|.
name|resource
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
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
name|List
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Context
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|UriInfo
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|LineIterator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang
operator|.
name|ArrayUtils
import|;
end_import

begin_class
specifier|public
class|class
name|NavigationMixin
block|{
annotation|@
name|Context
specifier|protected
name|UriInfo
name|uriInfo
decl_stmt|;
specifier|public
name|URI
name|getPublicBaseUri
parameter_list|()
block|{
return|return
name|uriInfo
operator|.
name|getBaseUri
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|MenuItem
argument_list|>
name|getMainMenuItems
parameter_list|()
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|MenuItem
argument_list|(
literal|"Home"
argument_list|,
literal|"/kres"
argument_list|,
literal|"homeMenu"
argument_list|,
name|uriInfo
argument_list|,
literal|null
argument_list|)
argument_list|,
operator|new
name|MenuItem
argument_list|(
literal|"Usage"
argument_list|,
literal|"javascript:expandMenu('usageMenu')"
argument_list|,
literal|"usageMenu"
argument_list|,
name|uriInfo
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|MenuItem
argument_list|(
literal|"Documentation"
argument_list|,
literal|"/kres/documentation"
argument_list|,
literal|"documentationMenu"
argument_list|,
name|uriInfo
argument_list|,
literal|null
argument_list|)
argument_list|,
operator|new
name|MenuItem
argument_list|(
literal|"RESTful services"
argument_list|,
literal|"/kres/documentation/restful"
argument_list|,
literal|"restfulMenu"
argument_list|,
name|uriInfo
argument_list|,
literal|null
argument_list|)
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
class|class
name|MenuItem
block|{
specifier|public
name|MenuItem
parameter_list|(
name|String
name|label
parameter_list|,
name|String
name|link
parameter_list|,
name|String
name|id
parameter_list|,
name|UriInfo
name|uriInfo
parameter_list|,
name|List
argument_list|<
name|MenuItem
argument_list|>
name|subMenu
parameter_list|)
block|{
name|this
operator|.
name|label
operator|=
name|label
expr_stmt|;
name|this
operator|.
name|link
operator|=
name|link
expr_stmt|;
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
name|this
operator|.
name|subMenu
operator|=
name|subMenu
expr_stmt|;
name|cssClass
operator|=
name|uriInfo
operator|.
name|getPath
argument_list|()
operator|.
name|startsWith
argument_list|(
name|link
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|)
condition|?
literal|"current"
else|:
literal|"unselected"
expr_stmt|;
block|}
specifier|protected
specifier|final
name|String
name|label
decl_stmt|;
specifier|protected
specifier|final
name|String
name|id
decl_stmt|;
specifier|protected
specifier|final
name|String
name|link
decl_stmt|;
specifier|protected
specifier|final
name|List
argument_list|<
name|MenuItem
argument_list|>
name|subMenu
decl_stmt|;
specifier|protected
specifier|final
name|String
name|cssClass
decl_stmt|;
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|label
return|;
block|}
specifier|public
name|String
name|getLink
parameter_list|()
block|{
return|return
name|link
return|;
block|}
specifier|public
name|String
name|getCssClass
parameter_list|()
block|{
return|return
name|cssClass
return|;
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
specifier|public
name|List
argument_list|<
name|MenuItem
argument_list|>
name|getSubMenu
parameter_list|()
block|{
return|return
name|subMenu
return|;
block|}
block|}
block|}
end_class

end_unit

