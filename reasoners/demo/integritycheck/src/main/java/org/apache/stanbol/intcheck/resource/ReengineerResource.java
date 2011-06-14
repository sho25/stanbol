begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * To change this template, choose Tools | Templates  * and open the template in the editor.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|intcheck
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
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
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

begin_class
annotation|@
name|Path
argument_list|(
literal|"/reengineer"
argument_list|)
annotation|@
name|ImplicitProduces
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
operator|+
literal|";qs=2"
argument_list|)
specifier|public
class|class
name|ReengineerResource
extends|extends
name|NavigationMixin
block|{      }
end_class

end_unit

