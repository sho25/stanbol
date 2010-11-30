begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|jersey
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
name|MockComponentContext
implements|implements
name|ComponentContext
block|{
specifier|protected
specifier|final
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
decl_stmt|;
specifier|public
name|MockComponentContext
parameter_list|()
block|{
name|properties
operator|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
expr_stmt|;
block|}
specifier|public
name|MockComponentContext
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
parameter_list|)
block|{
name|this
operator|.
name|properties
operator|=
name|properties
expr_stmt|;
block|}
specifier|public
name|void
name|disableComponent
parameter_list|(
name|String
name|name
parameter_list|)
block|{     }
specifier|public
name|void
name|enableComponent
parameter_list|(
name|String
name|name
parameter_list|)
block|{     }
specifier|public
name|BundleContext
name|getBundleContext
parameter_list|()
block|{
return|return
operator|new
name|BundleContext
argument_list|()
block|{
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
return|return
literal|false
return|;
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
block|{             }
annotation|@
name|Override
specifier|public
name|void
name|removeFrameworkListener
parameter_list|(
name|FrameworkListener
name|listener
parameter_list|)
block|{             }
annotation|@
name|Override
specifier|public
name|void
name|removeBundleListener
parameter_list|(
name|BundleListener
name|listener
parameter_list|)
block|{             }
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
return|return
literal|null
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
return|return
operator|new
name|File
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"java.io.tmpdir"
argument_list|)
argument_list|)
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
return|return
literal|null
return|;
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
return|return
literal|null
return|;
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
block|{              }
annotation|@
name|Override
specifier|public
name|void
name|addServiceListener
parameter_list|(
name|ServiceListener
name|listener
parameter_list|)
block|{             }
annotation|@
name|Override
specifier|public
name|void
name|addFrameworkListener
parameter_list|(
name|FrameworkListener
name|listener
parameter_list|)
block|{             }
annotation|@
name|Override
specifier|public
name|void
name|addBundleListener
parameter_list|(
name|BundleListener
name|listener
parameter_list|)
block|{             }
block|}
return|;
block|}
specifier|public
name|ComponentInstance
name|getComponentInstance
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getProperties
parameter_list|()
block|{
return|return
name|properties
return|;
block|}
specifier|public
name|ServiceReference
name|getServiceReference
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|Bundle
name|getUsingBundle
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|Object
name|locateService
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|Object
name|locateService
parameter_list|(
name|String
name|name
parameter_list|,
name|ServiceReference
name|reference
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|Object
index|[]
name|locateServices
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

