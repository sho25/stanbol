begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_package
package|package
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
name|home
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
name|commons
operator|.
name|web
operator|.
name|home
operator|.
name|resource
operator|.
name|StanbolRootResource
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
comment|/**  * Statically define the list of available resources and providers to be contributed to the the Stanbol JAX-RS  * Endpoint.  */
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
specifier|public
class|class
name|HomeWebFragment
implements|implements
name|WebFragment
block|{
specifier|private
specifier|static
specifier|final
name|String
name|NAME
init|=
literal|"home"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|STATIC_RESOURCE_PATH
init|=
literal|"/org/apache/stanbol/commons/web/home/static"
decl_stmt|;
specifier|private
name|BundleContext
name|bundleContext
decl_stmt|;
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
literal|"style/stanbol.css"
argument_list|,
name|this
argument_list|,
literal|0
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
literal|"scripts/jquery-1.4.2.js"
argument_list|,
name|this
argument_list|,
literal|0
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
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
end_class

end_unit

