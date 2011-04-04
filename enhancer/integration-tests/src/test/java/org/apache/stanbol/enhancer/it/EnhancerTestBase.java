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
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|fail
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
name|RetryLoop
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
name|Before
import|;
end_import

begin_comment
comment|/** Inherit from this to wait for all default enhancement   *  engines to be up before running tests.  */
end_comment

begin_class
specifier|public
class|class
name|EnhancerTestBase
extends|extends
name|StanbolTestBase
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
comment|// TODO configurable via system properties??
specifier|public
specifier|static
specifier|final
name|int
name|ENGINES_TIMEOUT_SECONDS
init|=
literal|60
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|WAIT_BETWEEN_TRIES_MSEC
init|=
literal|1000
decl_stmt|;
specifier|static
name|boolean
name|enginesReady
decl_stmt|;
specifier|static
name|boolean
name|timedOut
decl_stmt|;
annotation|@
name|Before
specifier|public
name|void
name|checkEnginesReady
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Check only once per test run
if|if
condition|(
name|enginesReady
condition|)
block|{
return|return;
block|}
comment|// If we timed out previously, don't waste time checking again
if|if
condition|(
name|timedOut
condition|)
block|{
name|fail
argument_list|(
literal|"Timeout in previous check of enhancement engines, cannot run tests"
argument_list|)
expr_stmt|;
block|}
comment|// We'll retry the check for all engines to be ready
comment|// for up to ENGINES_TIMEOUT_SECONDS
specifier|final
name|RetryLoop
operator|.
name|Condition
name|c
init|=
operator|new
name|RetryLoop
operator|.
name|Condition
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|isTrue
parameter_list|()
throws|throws
name|Exception
block|{
comment|/*  List of expected engines could be made configurable via system                  *  properties, but we don't expect it to change often.                   */
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
literal|"/engines"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/html"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentType
argument_list|(
literal|"text/html"
argument_list|)
operator|.
name|assertContentRegexp
argument_list|(
literal|"org.apache.stanbol.*MetaxaEngine"
argument_list|,
literal|"org.apache.stanbol.*LangIdEnhancementEngine"
argument_list|,
literal|"org.apache.stanbol.*NamedEntityExtractionEnhancementEngine"
argument_list|,
literal|"org.apache.stanbol.*OpenCalaisEngine"
argument_list|,
literal|"org.apache.stanbol.*EntityMentionEnhancementEngine"
argument_list|,
literal|"org.apache.stanbol.*RelatedTopicEnhancementEngine"
argument_list|,
literal|"org.apache.stanbol.*CachingDereferencerEngine"
argument_list|,
comment|//added by rwesten
literal|"org.apache.stanbol.*ReferencedSiteEntityTaggingEnhancementEngine"
argument_list|)
expr_stmt|;
comment|/*  List of expected referencedSites could also be made                   *  configurable via system properties, but we don't expect it                   *  to change often.                   */
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
literal|"/entityhub/sites/referenced"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentType
argument_list|(
literal|"application/json"
argument_list|)
operator|.
name|assertContentRegexp
argument_list|(
literal|"http:\\\\/\\\\/.*\\\\/entityhub\\\\/site\\\\/dbpedia\\\\/"
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Enhancement engines checked, all present"
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
literal|"Checking that all enhancement engines are ready"
return|;
block|}
block|}
decl_stmt|;
operator|new
name|RetryLoop
argument_list|(
name|c
argument_list|,
name|ENGINES_TIMEOUT_SECONDS
argument_list|,
name|WAIT_BETWEEN_TRIES_MSEC
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|void
name|reportException
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Exception in RetryLoop, will retry for up to "
operator|+
name|getRemainingTimeSeconds
argument_list|()
operator|+
literal|" seconds: "
operator|+
name|t
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|onTimeout
parameter_list|()
block|{
name|timedOut
operator|=
literal|true
expr_stmt|;
block|}
block|}
expr_stmt|;
name|enginesReady
operator|=
literal|true
expr_stmt|;
block|}
block|}
end_class

end_unit

