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
name|jersey
operator|.
name|fragment
package|;
end_package

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
name|Service
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
name|web
operator|.
name|base
operator|.
name|NavigationLink
import|;
end_import

begin_comment
comment|/**  * The {@link NavigationLink} to the Entityhub component  */
end_comment

begin_class
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|NavigationLink
operator|.
name|class
argument_list|)
specifier|public
class|class
name|EntityhubMenueItem
extends|extends
name|NavigationLink
block|{
specifier|private
specifier|static
specifier|final
name|String
name|htmlDescription
init|=
literal|"The<strong>Entityhub</strong> componnet allows users to manage "
operator|+
literal|"knowledge of the domain of interest.<strong>Referenced Sites"
operator|+
literal|"</strong> allow to refer remote datasets and/or to provide fast "
operator|+
literal|"local indexes for such datasets (e.g. as needed for entity linking "
operator|+
literal|"with the Stanbol Enhancer.<strong>Managed Sites</strong> provide "
operator|+
literal|"a full CRUD interface for managing data sets."
decl_stmt|;
specifier|public
name|EntityhubMenueItem
parameter_list|()
block|{
name|super
argument_list|(
literal|"entityhub"
argument_list|,
literal|"/entityhub"
argument_list|,
name|htmlDescription
argument_list|,
literal|30
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

