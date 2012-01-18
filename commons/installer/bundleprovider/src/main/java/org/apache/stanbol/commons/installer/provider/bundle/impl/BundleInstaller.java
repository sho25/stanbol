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
name|commons
operator|.
name|installer
operator|.
name|provider
operator|.
name|bundle
operator|.
name|impl
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|installer
operator|.
name|provider
operator|.
name|bundle
operator|.
name|BundleInstallerConstants
operator|.
name|BUNDLE_INSTALLER_HEADER
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|installer
operator|.
name|provider
operator|.
name|bundle
operator|.
name|BundleInstallerConstants
operator|.
name|PROVIDER_SCHEME
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

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
name|Dictionary
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|FilenameUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|sling
operator|.
name|installer
operator|.
name|api
operator|.
name|InstallableResource
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|sling
operator|.
name|installer
operator|.
name|api
operator|.
name|OsgiInstaller
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|sling
operator|.
name|installer
operator|.
name|api
operator|.
name|tasks
operator|.
name|ResourceTransformer
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
name|installer
operator|.
name|provider
operator|.
name|bundle
operator|.
name|BundleInstallerConstants
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
name|BundleEvent
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
name|FrameworkEvent
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
comment|/**  * Installs resources within bundles by using the Apache Sling Installer  * framework.  *<p>  * NOTE that currently installed resources are not removed if the bundle is  * deactivated because it is not clear if this is a good thing to do. maybe one  * should use {@link Bundle#UNINSTALLED} for that. However this needs some  * additional testing.  *<p>  * The OSGi extender pattern (as described at [1]) is used. The value of the  * {@link BundleInstallerConstants#BUNDLE_INSTALLER_HEADER}  * ({@value BundleInstallerConstants#BUNDLE_INSTALLER_HEADER}) is used as relative path within  * the bundle to search for installable  resources. Also files in sub folders  * are considered as installable resources.<p>  * The files are installed in the order as returned by  * {@link Bundle#findEntries(String, String, boolean)}. Directories are  * ignored.<p>  * All resources installed by this provider do use  * {@link BundleInstallerConstants#PROVIDER_SCHEME} ({@value BundleInstallerConstants#PROVIDER_SCHEME}) as  * scheme and the path additional to the value of  * {@link BundleInstallerConstants#BUNDLE_INSTALLER_HEADER}.<p>  * To give an example:<p>  * If the Bundle header notes<br>  *<pre><code>  *     {@value BundleInstallerConstants#BUNDLE_INSTALLER_HEADER}=resources  *</pre></code><br>  * and the bundle contains the resources:<br>  *<pre><code>  *     resources/bundles/10/myBundle.jar  *     resources/config/myComponent.cfg  *     resoruces/data/myIndex.solrondex.zip  *</pre></code><br>  * then the following resources will be installed:  *<pre><code>  *     {@value BundleInstallerConstants#PROVIDER_SCHEME}:bundles/10/myBundle.jar  *     {@value BundleInstallerConstants#PROVIDER_SCHEME}:config/myComponent.cfg  *     {@value BundleInstallerConstants#PROVIDER_SCHEME}:data/myIndex.solrondex.zip  *</pre></code>  *<p>  * This means that {@link ResourceTransformer}s can both use the original name  * of the resource and the path relative to the install folder.  *<p>  * [1]<a href="http://www.aqute.biz/Snippets/Extender"> The OSGi extender  pattern</a>  *<p>  *  * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|BundleInstaller
implements|implements
name|BundleListener
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|BundleInstaller
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * The scheme we use to register our resources.      */
specifier|private
specifier|final
name|OsgiInstaller
name|installer
decl_stmt|;
specifier|private
specifier|final
name|BundleContext
name|context
decl_stmt|;
comment|/**      * contains all active bundles as key and the path to the config directory      * as value. A<code>null</code> value indicates that this bundle needs not      * to be processed.      */
specifier|private
specifier|final
name|Map
argument_list|<
name|Bundle
argument_list|,
name|String
argument_list|>
name|activated
init|=
operator|new
name|HashMap
argument_list|<
name|Bundle
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|BundleInstaller
parameter_list|(
name|OsgiInstaller
name|installer
parameter_list|,
name|BundleContext
name|context
parameter_list|)
block|{
if|if
condition|(
name|installer
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The OsgiInstaller service MUST NOT be NULL"
argument_list|)
throw|;
block|}
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The BundleContext MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|installer
operator|=
name|installer
expr_stmt|;
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
name|this
operator|.
name|context
operator|.
name|addBundleListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|//register the already active bundles
name|registerActive
argument_list|(
name|this
operator|.
name|context
argument_list|)
expr_stmt|;
block|}
comment|/**      * Uses the parsed bundle context to register the already active (and currently      * starting) bundles.      */
specifier|private
name|void
name|registerActive
parameter_list|(
name|BundleContext
name|context
parameter_list|)
block|{
for|for
control|(
name|Bundle
name|bundle
range|:
name|context
operator|.
name|getBundles
argument_list|()
control|)
block|{
if|if
condition|(
operator|(
name|bundle
operator|.
name|getState
argument_list|()
operator|&
operator|(
name|Bundle
operator|.
name|STARTING
operator||
name|Bundle
operator|.
name|ACTIVE
operator|)
operator|)
operator|!=
literal|0
condition|)
block|{
name|register
argument_list|(
name|bundle
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|bundleChanged
parameter_list|(
name|BundleEvent
name|event
parameter_list|)
block|{
switch|switch
condition|(
name|event
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|BundleEvent
operator|.
name|STARTED
case|:
name|register
argument_list|(
name|event
operator|.
name|getBundle
argument_list|()
argument_list|)
expr_stmt|;
break|break;
comment|//use uninstalled instead of stopped so that unregister is not called
comment|//when the OSGI environment closes
case|case
name|BundleEvent
operator|.
name|UNINSTALLED
case|:
comment|//STOPPED:
name|unregister
argument_list|(
name|event
operator|.
name|getBundle
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|BundleEvent
operator|.
name|UPDATED
case|:
name|unregister
argument_list|(
name|event
operator|.
name|getBundle
argument_list|()
argument_list|)
expr_stmt|;
name|register
argument_list|(
name|event
operator|.
name|getBundle
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Registers the bundle to the {@link #activated} map.      *      * @param bundle the bundle to register      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|private
name|void
name|register
parameter_list|(
name|Bundle
name|bundle
parameter_list|)
block|{
synchronized|synchronized
init|(
name|activated
init|)
block|{
if|if
condition|(
name|activated
operator|.
name|containsKey
argument_list|(
name|bundle
argument_list|)
condition|)
block|{
return|return;
block|}
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Register Bundle {} with BundleInstaller"
argument_list|,
name|bundle
operator|.
name|getSymbolicName
argument_list|()
argument_list|)
expr_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|headers
init|=
operator|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|bundle
operator|.
name|getHeaders
argument_list|()
decl_stmt|;
comment|//        log.info("With Headers:");
comment|//        for(Enumeration<String> keys = headers.keys();keys.hasMoreElements();){
comment|//            String key = keys.nextElement();
comment|//            log.info("> "+key+"="+headers.get(key));
comment|//        }
name|String
name|path
init|=
operator|(
name|String
operator|)
name|headers
operator|.
name|get
argument_list|(
name|BUNDLE_INSTALLER_HEADER
argument_list|)
decl_stmt|;
name|activated
operator|.
name|put
argument_list|(
name|bundle
argument_list|,
name|path
argument_list|)
expr_stmt|;
if|if
condition|(
name|path
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|" ... process configuration within path {} for bundle {}"
argument_list|,
name|path
argument_list|,
name|bundle
operator|.
name|getSymbolicName
argument_list|()
argument_list|)
expr_stmt|;
name|Enumeration
argument_list|<
name|URL
argument_list|>
name|resources
init|=
operator|(
name|Enumeration
argument_list|<
name|URL
argument_list|>
operator|)
name|bundle
operator|.
name|findEntries
argument_list|(
name|path
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|resources
operator|!=
literal|null
condition|)
block|{
name|ArrayList
argument_list|<
name|InstallableResource
argument_list|>
name|updated
init|=
operator|new
name|ArrayList
argument_list|<
name|InstallableResource
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|resources
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|URL
name|url
init|=
name|resources
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|url
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"> installable Resource {}"
argument_list|,
name|url
argument_list|)
expr_stmt|;
name|InstallableResource
name|resource
init|=
name|createInstallableResource
argument_list|(
name|bundle
argument_list|,
name|path
argument_list|,
name|url
argument_list|)
decl_stmt|;
if|if
condition|(
name|resource
operator|!=
literal|null
condition|)
block|{
name|updated
operator|.
name|add
argument_list|(
name|resource
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|installer
operator|.
name|updateResources
argument_list|(
name|PROVIDER_SCHEME
argument_list|,
name|updated
operator|.
name|toArray
argument_list|(
operator|new
name|InstallableResource
index|[
name|updated
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|,
operator|new
name|String
index|[]
block|{}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|" ... no Entries found in path '{}' configured for Bundle '{}' with Manifest header field '{}'!"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|path
block|,
name|bundle
operator|.
name|getSymbolicName
argument_list|()
block|,
name|BUNDLE_INSTALLER_HEADER
block|}
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"  ... no Configuration to process"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Creates an {@link InstallableResource} for {@link URL}s of files within      * the parsed bundle.      *      * @param bundle the bundle containing the parsed resource      * @param bundleResource a resource within the bundle that need to be installed      *      * @return the installable resource or<code>null</code> in case of an error      */
specifier|private
name|InstallableResource
name|createInstallableResource
parameter_list|(
name|Bundle
name|bundle
parameter_list|,
name|String
name|path
parameter_list|,
name|URL
name|bundleResource
parameter_list|)
block|{
comment|//define the id
name|String
name|relPath
init|=
name|getInstallableResourceId
argument_list|(
name|path
argument_list|,
name|bundleResource
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|FilenameUtils
operator|.
name|getName
argument_list|(
name|relPath
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
comment|//ignore directories!
block|}
name|InstallableResource
name|resource
decl_stmt|;
try|try
block|{
comment|/*              * Notes:              *  - use<relativepath> as id              *  - parse null as type to enable autodetection for configs as              *    implemented by InternalReseouce.create(..)              *  - we use the symbolic name and the modification date of the bundle as digest              *  - the Dictionary will be ignored if an input stream is present              *    so it is best to parse null              *  - No idea how the priority is used by the Sling Installer. For              *    now parse null than the default priority is used.              */
name|resource
operator|=
operator|new
name|InstallableResource
argument_list|(
name|relPath
argument_list|,
name|bundleResource
operator|.
name|openStream
argument_list|()
argument_list|,
literal|null
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|bundle
operator|.
name|getSymbolicName
argument_list|()
operator|+
name|bundle
operator|.
name|getLastModified
argument_list|()
argument_list|)
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|" ... found installable resource "
operator|+
name|bundleResource
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to process configuration File %s from Bundle %s"
argument_list|,
name|bundleResource
argument_list|,
name|bundle
operator|.
name|getSymbolicName
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
return|return
name|resource
return|;
block|}
comment|/**      * @param path      * @param bundleResource      * @return      */
specifier|private
name|String
name|getInstallableResourceId
parameter_list|(
name|String
name|path
parameter_list|,
name|URL
name|bundleResource
parameter_list|)
block|{
name|String
name|id
init|=
name|bundleResource
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|relPath
init|=
name|id
operator|.
name|substring
argument_list|(
name|id
operator|.
name|lastIndexOf
argument_list|(
name|path
argument_list|)
operator|+
name|path
operator|.
name|length
argument_list|()
argument_list|,
name|id
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|relPath
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|private
name|void
name|unregister
parameter_list|(
name|Bundle
name|bundle
parameter_list|)
block|{
name|String
name|path
decl_stmt|;
synchronized|synchronized
init|(
name|activated
init|)
block|{
if|if
condition|(
operator|!
name|activated
operator|.
name|containsKey
argument_list|(
name|bundle
argument_list|)
condition|)
block|{
return|return;
block|}
name|path
operator|=
name|activated
operator|.
name|remove
argument_list|(
name|bundle
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|path
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|" ... remove configurations within path {}"
argument_list|,
name|path
argument_list|)
expr_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|removedResources
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Enumeration
argument_list|<
name|URL
argument_list|>
name|resources
init|=
operator|(
name|Enumeration
argument_list|<
name|URL
argument_list|>
operator|)
name|bundle
operator|.
name|findEntries
argument_list|(
name|path
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
init|;
name|resources
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|String
name|installableResourceId
init|=
name|getInstallableResourceId
argument_list|(
name|path
argument_list|,
name|resources
operator|.
name|nextElement
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|installableResourceId
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"  ... remove Installable Resource {}"
argument_list|,
name|installableResourceId
argument_list|)
expr_stmt|;
name|removedResources
operator|.
name|add
argument_list|(
name|installableResourceId
argument_list|)
expr_stmt|;
block|}
block|}
name|installer
operator|.
name|updateResources
argument_list|(
name|PROVIDER_SCHEME
argument_list|,
literal|null
argument_list|,
name|removedResources
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|removedResources
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"  ... no Configuration to process"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * removes the bundle listener      */
specifier|public
name|void
name|close
parameter_list|()
block|{
name|context
operator|.
name|removeBundleListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

