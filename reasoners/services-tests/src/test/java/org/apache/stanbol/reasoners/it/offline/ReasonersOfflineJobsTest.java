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
name|it
operator|.
name|offline
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
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
name|List
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
name|exec
operator|.
name|Executor
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
name|testing
operator|.
name|http
operator|.
name|Request
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
name|it
operator|.
name|offline
operator|.
name|ReasonersOfflineTest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|BeforeClass
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
comment|/**  * Tests background jobs  *  */
end_comment

begin_class
specifier|public
class|class
name|ReasonersOfflineJobsTest
extends|extends
name|ReasonersOfflineTest
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|prepare
parameter_list|()
throws|throws
name|URISyntaxException
block|{
name|ReasonersOfflineTest
operator|.
name|prepare
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|setupMultipart
parameter_list|()
block|{
name|super
operator|.
name|setupMultipart
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSubsequentJobs
parameter_list|()
throws|throws
name|Exception
block|{
name|log
operator|.
name|info
argument_list|(
literal|"testSubsequentJobs()"
argument_list|)
expr_stmt|;
comment|// We send a file to a job then we ping it, we do this for all services and tasks
for|for
control|(
name|String
name|s
range|:
name|allServices
argument_list|()
control|)
block|{
for|for
control|(
name|String
name|t
range|:
name|TASKS
control|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|REASONERS_PATH
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|s
argument_list|)
operator|.
name|append
argument_list|(
name|t
argument_list|)
operator|.
name|append
argument_list|(
literal|"/job"
argument_list|)
expr_stmt|;
name|Request
name|request
init|=
name|buildMultipartRequest
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|,
name|multiPart
argument_list|)
decl_stmt|;
name|executeAndPingSingleJob
argument_list|(
name|request
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSubsequentJobs2
parameter_list|()
throws|throws
name|Exception
block|{
name|log
operator|.
name|info
argument_list|(
literal|"testSubsequentJobs2()"
argument_list|)
expr_stmt|;
comment|// We start all jobs and the we ping all
name|List
argument_list|<
name|String
argument_list|>
name|locations
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
name|String
name|s
range|:
name|allServices
argument_list|()
control|)
block|{
for|for
control|(
name|String
name|t
range|:
name|TASKS
control|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|REASONERS_PATH
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|s
argument_list|)
operator|.
name|append
argument_list|(
name|t
argument_list|)
operator|.
name|append
argument_list|(
literal|"/job"
argument_list|)
expr_stmt|;
name|Request
name|request
init|=
name|buildMultipartRequest
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|,
name|multiPart
argument_list|)
decl_stmt|;
name|String
name|location
init|=
name|createJob
argument_list|(
name|request
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Started job {}"
argument_list|,
name|location
argument_list|)
expr_stmt|;
name|locations
operator|.
name|add
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
block|}
comment|// We ping all in sequence
for|for
control|(
name|String
name|l
range|:
name|locations
control|)
block|{
name|pingSingleJob
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

