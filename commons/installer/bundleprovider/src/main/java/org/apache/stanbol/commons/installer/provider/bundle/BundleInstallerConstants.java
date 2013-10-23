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
package|;
end_package

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

begin_comment
comment|/**  * Constants used by the BundleInstaller.  *  * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|BundleInstallerConstants
block|{
comment|/**      * The name of the header field used for the       *<a href="http://www.aqute.biz/Snippets/Extender"> The OSGi extender       * pattern</a>.      */
specifier|public
specifier|static
specifier|final
name|String
name|BUNDLE_INSTALLER_HEADER
init|=
literal|"Install-Path"
decl_stmt|;
comment|/**      * The schema used for {@link InstallableResource}s created by the      * bundle provider.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROVIDER_SCHEME
init|=
literal|"bundleinstall"
decl_stmt|;
block|}
end_class

end_unit

