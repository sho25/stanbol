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
name|ontonet
operator|.
name|impl
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
comment|/**  * Called upon OSGi bundle startup and shutdown, it constructs and releases the resources required by the KReS  * Ontology Network Manager during its activity.  *   * @author alessandro  *   */
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
name|log
operator|.
name|info
argument_list|(
literal|"KReS :: Ontology Network Manager set up."
argument_list|)
expr_stmt|;
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

