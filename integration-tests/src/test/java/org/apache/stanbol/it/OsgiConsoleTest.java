begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements. See the NOTICE file distributed with this  * work for additional information regarding copyright ownership. The ASF  * licenses this file to You under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the  * License for the specific language governing permissions and limitations under  * the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|it
package|;
end_package

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
name|testing
operator|.
name|stanbol
operator|.
name|StanbolTestBase
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_comment
comment|/** Test access to the OSGi console */
end_comment

begin_class
specifier|public
class|class
name|OsgiConsoleTest
extends|extends
name|StanbolTestBase
block|{
annotation|@
name|Override
specifier|protected
name|String
name|getCredentials
parameter_list|()
block|{
return|return
literal|"admin:admin"
return|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testDefaultConsolePaths
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|String
index|[]
name|subpaths
init|=
block|{
literal|"bundles"
block|,
literal|"components"
block|,
literal|"configMgr"
block|,
comment|//"config", No longer available with Felix Webconsole 4.2.2
literal|"licenses"
block|,
literal|"logs"
block|,
literal|"memoryusage"
block|,
literal|"services"
block|,
comment|//"shell", No longer available with Felix Webconsole 4.0.0
literal|"stanbol_datafileprovider"
block|,
literal|"osgi-installer"
block|,
literal|"slinglog"
block|,
literal|"depfinder"
block|,
literal|"vmstat"
block|}
decl_stmt|;
for|for
control|(
name|String
name|subpath
range|:
name|subpaths
control|)
block|{
specifier|final
name|String
name|path
init|=
literal|"/system/console/"
operator|+
name|subpath
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|path
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

