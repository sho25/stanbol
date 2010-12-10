begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|engines
operator|.
name|autotagging
package|;
end_package

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

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|autotagging
operator|.
name|Autotagger
import|;
end_import

begin_comment
comment|/**  * Interface to fetch a configured instance of an autotagger.  */
end_comment

begin_interface
specifier|public
interface|interface
name|AutotaggerProvider
block|{
comment|/**      * @return the autotagger instance or null if none is configured      */
name|Autotagger
name|getAutotagger
parameter_list|()
function_decl|;
name|BundleContext
name|getBundleContext
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

