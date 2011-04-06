begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|web
operator|.
name|resource
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|view
operator|.
name|ImplicitProduces
import|;
end_import

begin_comment
comment|/**  *   * @author andrea.nuzzolese  *  */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/"
argument_list|)
annotation|@
name|ImplicitProduces
argument_list|(
literal|"text/html"
argument_list|)
specifier|public
class|class
name|RootResource
extends|extends
name|NavigationMixin
block|{  }
end_class

end_unit

