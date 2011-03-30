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
name|store
operator|.
name|tdb
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
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
name|Hashtable
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
name|Bundle
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
name|osgi
operator|.
name|framework
operator|.
name|BundleException
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
name|BundleListener
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
name|Filter
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
name|FrameworkListener
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
name|InvalidSyntaxException
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
name|ServiceListener
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
name|ServiceReference
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
name|ServiceRegistration
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
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentInstance
import|;
end_import

begin_class
specifier|public
class|class
name|DummyComponentContext
implements|implements
name|ComponentContext
block|{
specifier|private
name|Dictionary
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|props
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|DummyComponentContext
parameter_list|()
block|{}
annotation|@
name|Override
specifier|public
name|void
name|disableComponent
parameter_list|(
name|String
name|arg0
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|void
name|enableComponent
parameter_list|(
name|String
name|arg0
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|BundleContext
name|getBundleContext
parameter_list|()
block|{
return|return
operator|new
name|DummyBundleContext
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|ComponentInstance
name|getComponentInstance
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Dictionary
name|getProperties
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|ServiceReference
name|getServiceReference
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Bundle
name|getUsingBundle
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|locateService
parameter_list|(
name|String
name|arg0
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|locateService
parameter_list|(
name|String
name|arg0
parameter_list|,
name|ServiceReference
name|arg1
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
index|[]
name|locateServices
parameter_list|(
name|String
name|arg0
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|private
class|class
name|DummyBundleContext
implements|implements
name|BundleContext
block|{
annotation|@
name|Override
specifier|public
name|void
name|addBundleListener
parameter_list|(
name|BundleListener
name|listener
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|void
name|addFrameworkListener
parameter_list|(
name|FrameworkListener
name|listener
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|void
name|addServiceListener
parameter_list|(
name|ServiceListener
name|listener
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|void
name|addServiceListener
parameter_list|(
name|ServiceListener
name|listener
parameter_list|,
name|String
name|filter
parameter_list|)
throws|throws
name|InvalidSyntaxException
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|Filter
name|createFilter
parameter_list|(
name|String
name|filter
parameter_list|)
throws|throws
name|InvalidSyntaxException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|ServiceReference
index|[]
name|getAllServiceReferences
parameter_list|(
name|String
name|clazz
parameter_list|,
name|String
name|filter
parameter_list|)
throws|throws
name|InvalidSyntaxException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Bundle
name|getBundle
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Bundle
name|getBundle
parameter_list|(
name|long
name|id
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Bundle
index|[]
name|getBundles
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|File
name|getDataFile
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
literal|"target/testDir"
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
name|file
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
name|file
operator|.
name|mkdir
argument_list|()
expr_stmt|;
return|return
name|file
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getProperty
parameter_list|(
name|String
name|key
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|ServiceReference
name|getServiceReference
parameter_list|(
name|String
name|clazz
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|ServiceReference
index|[]
name|getServiceReferences
parameter_list|(
name|String
name|clazz
parameter_list|,
name|String
name|filter
parameter_list|)
throws|throws
name|InvalidSyntaxException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Bundle
name|installBundle
parameter_list|(
name|String
name|location
parameter_list|)
throws|throws
name|BundleException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Bundle
name|installBundle
parameter_list|(
name|String
name|location
parameter_list|,
name|InputStream
name|input
parameter_list|)
throws|throws
name|BundleException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|ServiceRegistration
name|registerService
parameter_list|(
name|String
index|[]
name|clazzes
parameter_list|,
name|Object
name|service
parameter_list|,
name|Dictionary
name|properties
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|ServiceRegistration
name|registerService
parameter_list|(
name|String
name|clazz
parameter_list|,
name|Object
name|service
parameter_list|,
name|Dictionary
name|properties
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeBundleListener
parameter_list|(
name|BundleListener
name|listener
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeFrameworkListener
parameter_list|(
name|FrameworkListener
name|listener
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeServiceListener
parameter_list|(
name|ServiceListener
name|listener
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|ungetService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

