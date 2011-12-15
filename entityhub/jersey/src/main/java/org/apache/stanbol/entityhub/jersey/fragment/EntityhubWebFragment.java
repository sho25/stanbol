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
name|entityhub
operator|.
name|jersey
operator|.
name|fragment
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
name|entityhub
operator|.
name|jersey
operator|.
name|parsers
operator|.
name|FieldQueryReader
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
name|entityhub
operator|.
name|jersey
operator|.
name|parsers
operator|.
name|RepresentationReader
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
name|entityhub
operator|.
name|jersey
operator|.
name|resource
operator|.
name|EntityhubRootResource
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
name|entityhub
operator|.
name|jersey
operator|.
name|resource
operator|.
name|ReferencedSiteRootResource
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
name|entityhub
operator|.
name|jersey
operator|.
name|resource
operator|.
name|SiteManagerRootResource
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
name|entityhub
operator|.
name|jersey
operator|.
name|writers
operator|.
name|JettisonWriter
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
name|entityhub
operator|.
name|jersey
operator|.
name|writers
operator|.
name|QueryResultListWriter
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
name|entityhub
operator|.
name|jersey
operator|.
name|writers
operator|.
name|RepresentationWriter
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
name|entityhub
operator|.
name|jersey
operator|.
name|writers
operator|.
name|SignWriter
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
name|EntityhubWebFragment
implements|implements
name|WebFragment
block|{
specifier|private
specifier|static
specifier|final
name|String
name|NAME
init|=
literal|"entityhub"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|STATIC_RESOURCE_PATH
init|=
literal|"/org/apache/stanbol/entityhub/jersey/static"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|TEMPLATE_PATH
init|=
literal|"/org/apache/stanbol/entityhub/jersey/templates"
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
name|BundleContext
name|getBundleContext
parameter_list|()
block|{
return|return
name|this
operator|.
name|bundleContext
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
comment|// resources
name|classes
operator|.
name|add
argument_list|(
name|EntityhubRootResource
operator|.
name|class
argument_list|)
expr_stmt|;
comment|//classes.add(EntityMappingResource.class);
name|classes
operator|.
name|add
argument_list|(
name|ReferencedSiteRootResource
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|SiteManagerRootResource
operator|.
name|class
argument_list|)
expr_stmt|;
comment|//classes.add(SymbolResource.class);
comment|// message body readers and writers
name|classes
operator|.
name|add
argument_list|(
name|FieldQueryReader
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|QueryResultListWriter
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|SignWriter
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|RepresentationWriter
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|FieldQueryReader
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|RepresentationReader
operator|.
name|class
argument_list|)
expr_stmt|;
comment|//TODO: somehow writing of Json has not worked because of
comment|//      A message body writer for Java class org.codehaus.jettison.json.JSONArray,
comment|//     and Java type class org.codehaus.jettison.json.JSONArray, and MIME media
comment|//     type application/json was not found
comment|//     As a workaround I have implemented this small workaround!
name|classes
operator|.
name|add
argument_list|(
name|JettisonWriter
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
literal|"style/entityhub.css"
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
name|navList
init|=
operator|new
name|ArrayList
argument_list|<
name|NavigationLink
argument_list|>
argument_list|()
decl_stmt|;
name|navList
operator|.
name|add
argument_list|(
operator|new
name|NavigationLink
argument_list|(
literal|"entityhub"
argument_list|,
literal|"/entityhub"
argument_list|,
literal|null
argument_list|,
literal|30
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|navList
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
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
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
block|}
end_class

end_unit

