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
comment|/**  * Default MultiThreadedTest tool. Supports the use of System properties to  * configure the test. See the<a href="http://stanbol.apache.org/docs/trunk/utils/enhancerstresstest">  * Stanbol Enhancer Stress Test Utility</a> documentation for details  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|MultiThreadedTest
extends|extends
name|MultiThreadedTestBase
block|{
specifier|public
name|MultiThreadedTest
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMultipleParallelRequests
parameter_list|()
throws|throws
name|Exception
block|{
name|performTest
argument_list|(
name|TestSettings
operator|.
name|fromSystemProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

