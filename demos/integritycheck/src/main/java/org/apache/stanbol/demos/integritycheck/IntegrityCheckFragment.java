begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|demos
operator|.
name|integritycheck
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|Set
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
name|Activate
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
name|LinkResource
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
name|ScriptResource
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
name|WebFragment
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
name|demos
operator|.
name|integritycheck
operator|.
name|resources
operator|.
name|IntegrityCheckResource
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
name|service
operator|.
name|component
operator|.
name|ComponentContext
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|cache
operator|.
name|ClassTemplateLoader
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|cache
operator|.
name|TemplateLoader
import|;
end_import

begin_comment
comment|/**  *   * @author enridaga  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Service
argument_list|(
name|WebFragment
operator|.
name|class
argument_list|)
specifier|public
class|class
name|IntegrityCheckFragment
implements|implements
name|WebFragment
block|{
specifier|public
specifier|static
specifier|final
name|String
name|NAME
init|=
literal|"integritycheck"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|STATIC_RESOURCE_PATH
init|=
literal|"/org/apache/stanbol/demos/integritycheck/static"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TEMPLATE_PATH
init|=
literal|"/org/apache/stanbol/demos/integritycheck/templates"
decl_stmt|;
specifier|private
name|BundleContext
name|bundleContext
decl_stmt|;
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|this
operator|.
name|bundleContext
operator|=
name|ctx
operator|.
name|getBundleContext
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|NAME
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getStaticResourceClassPath
parameter_list|()
block|{
return|return
name|STATIC_RESOURCE_PATH
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|getJaxrsResourceClasses
parameter_list|()
block|{
name|Set
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|classes
init|=
operator|new
name|HashSet
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|IntegrityCheckResource
operator|.
name|class
argument_list|)
expr_stmt|;
return|return
name|classes
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|Object
argument_list|>
name|getJaxrsResourceSingletons
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|emptySet
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|TemplateLoader
name|getTemplateLoader
parameter_list|()
block|{
return|return
operator|new
name|ClassTemplateLoader
argument_list|(
name|getClass
argument_list|()
argument_list|,
name|TEMPLATE_PATH
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|LinkResource
argument_list|>
name|getLinkResources
parameter_list|()
block|{
name|List
argument_list|<
name|LinkResource
argument_list|>
name|resources
init|=
operator|new
name|ArrayList
argument_list|<
name|LinkResource
argument_list|>
argument_list|()
decl_stmt|;
name|resources
operator|.
name|add
argument_list|(
operator|new
name|LinkResource
argument_list|(
literal|"stylesheet"
argument_list|,
literal|"integritycheck.css"
argument_list|,
name|this
argument_list|,
literal|10
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|resources
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|ScriptResource
argument_list|>
name|getScriptResources
parameter_list|()
block|{
name|List
argument_list|<
name|ScriptResource
argument_list|>
name|resources
init|=
operator|new
name|ArrayList
argument_list|<
name|ScriptResource
argument_list|>
argument_list|()
decl_stmt|;
name|resources
operator|.
name|add
argument_list|(
operator|new
name|ScriptResource
argument_list|(
literal|"text/javascript"
argument_list|,
literal|"jquery.rdfquery.core-1.0.js"
argument_list|,
name|this
argument_list|,
literal|10
argument_list|)
argument_list|)
expr_stmt|;
name|resources
operator|.
name|add
argument_list|(
operator|new
name|ScriptResource
argument_list|(
literal|"text/javascript"
argument_list|,
literal|"jquery.cookie.js"
argument_list|,
name|this
argument_list|,
literal|10
argument_list|)
argument_list|)
expr_stmt|;
name|resources
operator|.
name|add
argument_list|(
operator|new
name|ScriptResource
argument_list|(
literal|"text/javascript"
argument_list|,
literal|"integritycheck.js"
argument_list|,
name|this
argument_list|,
literal|20
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|resources
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|NavigationLink
argument_list|>
name|getNavigationLinks
parameter_list|()
block|{
name|List
argument_list|<
name|NavigationLink
argument_list|>
name|links
init|=
operator|new
name|ArrayList
argument_list|<
name|NavigationLink
argument_list|>
argument_list|()
decl_stmt|;
name|links
operator|.
name|add
argument_list|(
operator|new
name|NavigationLink
argument_list|(
literal|"integritycheck"
argument_list|,
literal|"/integritycheck"
argument_list|,
literal|"/imports/integritycheckDescription.ftl"
argument_list|,
literal|50
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|links
return|;
block|}
annotation|@
name|Override
specifier|public
name|BundleContext
name|getBundleContext
parameter_list|()
block|{
return|return
name|bundleContext
return|;
block|}
block|}
end_class

end_unit

