begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|manager
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
name|BundleActivator
import|;
end_import

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
comment|/**  * Called upon OSGi bundle startup and shutdown, it constructs and releases the  * resources required by the KReS Ontology Network Manager during its activity.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|Activator
implements|implements
name|BundleActivator
block|{
annotation|@
name|Override
specifier|public
name|void
name|start
parameter_list|(
name|BundleContext
name|context
parameter_list|)
throws|throws
name|Exception
block|{
comment|// context.addBundleListener(new BundleListener() {
comment|//
comment|// @Override
comment|// public void bundleChanged(BundleEvent event) {
comment|// System.err.println("BundleEvent has fired");
comment|//
comment|// }
comment|// });
comment|// context.addFrameworkListener(new FrameworkListener() {
comment|//
comment|// @Override
comment|// public void frameworkEvent(FrameworkEvent event) {
comment|// System.err.println("FrameworkEvent has fired");
comment|//
comment|// }
comment|// });
comment|// context.addServiceListener(new ServiceListener() {
comment|//
comment|// @Override
comment|// public void serviceChanged(ServiceEvent event) {
comment|// System.err.println("ServiceEvent has fired");
comment|//
comment|// }
comment|// });
comment|// context.getBundle().getLocation();
comment|// Instantiate the static context for the KReS ONM
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|this
operator|.
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"KReS :: Instantiating ONM static context..."
argument_list|)
expr_stmt|;
if|if
condition|(
name|ONManager
operator|.
name|get
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"KReS :: ONM static context instantiated."
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"KReS :: Ontology Network Manager set up."
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|stop
parameter_list|(
name|BundleContext
name|context
parameter_list|)
throws|throws
name|Exception
block|{
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|this
operator|.
name|getClass
argument_list|()
argument_list|)
operator|.
name|info
argument_list|(
literal|"KReS :: Ontology Network Manager brought down."
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

