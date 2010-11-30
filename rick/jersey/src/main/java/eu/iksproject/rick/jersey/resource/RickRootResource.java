begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|jersey
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
comment|/**  * Root JAX-RS resource. The HTML view is implicitly rendered by a freemarker  * template to be found in the META-INF/templates folder.  */
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
name|RickRootResource
extends|extends
name|NavigationMixin
block|{
comment|// TODO: add here some controllers to provide some stats on the usage of the
comment|// FISE instances: np of content items in the store, nb of registered
comment|// engines, nb of extracted enhancements, ...
comment|// Also disable some of the features in the HTML view if the store, sparql
comment|// engine, engines are not registered.
block|}
end_class

end_unit

