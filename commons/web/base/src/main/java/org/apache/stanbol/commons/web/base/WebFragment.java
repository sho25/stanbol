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
name|base
package|;
end_package

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
name|osgi
operator|.
name|framework
operator|.
name|BundleContext
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
comment|/**  * Interface to be implemented by bundles that want to customize the stanbol web interface and REST API by  * contributing static resources, JAX-RS resources and Freemarker views.  *   * TODO: add some kind of ordering information  */
end_comment

begin_interface
specifier|public
interface|interface
name|WebFragment
block|{
comment|/**      * Name of the fragment. Should be a lowercase short name without any kind of special character, so as to      * be used as a path component in the URL of the static resources.      */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * Set of JAX-RS resources provided as classes.      *<p>      * Note: those classes should be visible: use the Export-Package bundle declaration to export their      * packages.      */
name|Set
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|getJaxrsResourceClasses
parameter_list|()
function_decl|;
comment|/**      * Set of JAX-RS resources provided as singleton instances.      *<p>      * Note: those objects should be visible: use the Export-Package bundle declaration to export their      * packages.      */
name|Set
argument_list|<
name|Object
argument_list|>
name|getJaxrsResourceSingletons
parameter_list|()
function_decl|;
comment|/**      * CSS and favicon resources to be linked in the head of all HTML pages controlled by the NavigationMixin      * abstract resource. The resources will be published under:      *       * ${it.staticRootUrl}/${link.fragmentName}/${link.relativePath}      */
name|List
argument_list|<
name|LinkResource
argument_list|>
name|getLinkResources
parameter_list|()
function_decl|;
comment|/**      * Javascript resources to be linked in the head of all HTML pages controlled by the NavigationMixin      * abstract resource. The resources will be published under:      *       * ${it.staticRootUrl}/${script.fragmentName}/${script.relativePath}      */
name|List
argument_list|<
name|ScriptResource
argument_list|>
name|getScriptResources
parameter_list|()
function_decl|;
comment|/**      * List of link descriptions to contribute to the main navigation menu.      */
name|List
argument_list|<
name|NavigationLink
argument_list|>
name|getNavigationLinks
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

