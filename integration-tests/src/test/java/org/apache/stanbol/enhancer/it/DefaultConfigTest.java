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
name|enhancer
operator|.
name|it
package|;
end_package

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
comment|/** Verify that the example config of STANBOL-110 is present */
end_comment

begin_class
specifier|public
class|class
name|DefaultConfigTest
extends|extends
name|EnhancerTestBase
block|{
annotation|@
name|Test
specifier|public
name|void
name|testDefaultConfig
parameter_list|()
throws|throws
name|Exception
block|{
comment|// AFAIK there's no way to get the config in machine
comment|// format from the webconsole, so we just grep the
comment|// text config output
specifier|final
name|String
name|path
init|=
literal|"/system/console/config/configuration-status-20110304-1743+0100.txt"
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
operator|.
name|withCredentials
argument_list|(
literal|"admin"
argument_list|,
literal|"admin"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentRegexp
argument_list|(
literal|"PID.*org.apache.stanbol.examples.ExampleBootstrapConfig"
argument_list|,
literal|"anotherValue.*This is AnotherValue."
argument_list|,
literal|"message.*This test config should be loaded at startup"
argument_list|,
literal|"org.apache.stanbol.examples.ExampleBootstrapConfig.*launchpad:resources/config/org.apache.stanbol.examples.ExampleBootstrapConfig.cfg"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

