begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reasoners
operator|.
name|web
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
name|Reference
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ONManager
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|session
operator|.
name|SessionManager
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
name|reasoners
operator|.
name|web
operator|.
name|resources
operator|.
name|JobsResource
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
name|reasoners
operator|.
name|web
operator|.
name|resources
operator|.
name|ReasoningServiceTaskResource
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
name|reasoners
operator|.
name|web
operator|.
name|resources
operator|.
name|ReasoningServicesResource
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
name|reasoners
operator|.
name|web
operator|.
name|writers
operator|.
name|JenaModelWriter
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|RuleStore
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

begin_comment
comment|/**  * Implementation of WebFragment for the Stanbol Reasoner end-point.  *   * @author Alberto Musetti  *   */
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
literal|false
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
name|ReasonersFragment
implements|implements
name|WebFragment
block|{
specifier|private
specifier|static
specifier|final
name|String
name|NAME
init|=
literal|"reasoners"
decl_stmt|;
annotation|@
name|Reference
name|ONManager
name|onm
decl_stmt|;
annotation|@
name|Reference
name|SessionManager
name|sessionManager
decl_stmt|;
annotation|@
name|Reference
name|RuleStore
name|kresRuleStore
decl_stmt|;
specifier|private
name|BundleContext
name|bundleContext
decl_stmt|;
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
comment|// Reasoner
comment|// classes.add(ReasonersResource.class);
name|classes
operator|.
name|add
argument_list|(
name|ReasoningServicesResource
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|ReasoningServiceTaskResource
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|JobsResource
operator|.
name|class
argument_list|)
expr_stmt|;
comment|// Writer
name|classes
operator|.
name|add
argument_list|(
name|JenaModelWriter
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
literal|"css/reasoners.css"
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
literal|"reasoners"
argument_list|,
literal|"/reasoners"
argument_list|,
literal|"/imports/reasonersDescription.ftl"
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
block|}
end_class

end_unit

