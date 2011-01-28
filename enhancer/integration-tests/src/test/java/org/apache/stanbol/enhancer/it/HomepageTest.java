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
comment|/** Simple test of the enhancer homepage, mostly  *  to show how simple a test can be */
end_comment

begin_class
specifier|public
class|class
name|HomepageTest
extends|extends
name|StanbolTestBase
block|{
annotation|@
name|Test
specifier|public
name|void
name|testHomepageHasCss
parameter_list|()
throws|throws
name|Exception
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildRequest
argument_list|(
literal|"/"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentContains
argument_list|(
literal|"/static/style/stanbol.css"
argument_list|,
literal|"The RESTful Semantic Engine"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

