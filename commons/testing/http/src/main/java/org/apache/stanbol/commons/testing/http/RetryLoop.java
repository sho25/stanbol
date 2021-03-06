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
name|commons
operator|.
name|testing
operator|.
name|http
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

begin_comment
comment|/**  * Convenience class for retrying tests  * until timeout or success.  */
end_comment

begin_class
specifier|public
class|class
name|RetryLoop
block|{
specifier|private
specifier|final
name|long
name|timeout
decl_stmt|;
comment|/**      * Interface for conditions to check, isTrue will be called      * repeatedly until success or timeout      */
specifier|public
interface|interface
name|Condition
block|{
comment|/**          * Used in failure messages to describe what was expected          */
name|String
name|getDescription
parameter_list|()
function_decl|;
comment|/**          * If true we stop retrying. The RetryLoop retries on AssertionError,          * so if tests fail in this method they are not reported as          * failures but retried.          */
name|boolean
name|isTrue
parameter_list|()
throws|throws
name|Exception
function_decl|;
block|}
comment|/**      * Retry Condition c until it returns true or timeout. See {@link Condition}      * for isTrue semantics.      */
specifier|public
name|RetryLoop
parameter_list|(
name|Condition
name|c
parameter_list|,
name|int
name|timeoutSeconds
parameter_list|,
name|int
name|intervalBetweenTriesMsec
parameter_list|)
block|{
name|timeout
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|+
name|timeoutSeconds
operator|*
literal|1000L
expr_stmt|;
while|while
condition|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|<
name|timeout
condition|)
block|{
try|try
block|{
if|if
condition|(
name|c
operator|.
name|isTrue
argument_list|()
condition|)
block|{
return|return;
block|}
block|}
catch|catch
parameter_list|(
name|AssertionError
name|ae
parameter_list|)
block|{
comment|// Retry JUnit tests failing in the condition as well
name|reportException
argument_list|(
name|ae
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|reportException
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|Thread
operator|.
name|sleep
argument_list|(
name|intervalBetweenTriesMsec
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|ignore
parameter_list|)
block|{             }
block|}
name|onTimeout
argument_list|()
expr_stmt|;
name|fail
argument_list|(
literal|"RetryLoop failed, condition is false after "
operator|+
name|timeoutSeconds
operator|+
literal|" seconds: "
operator|+
name|c
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Can be overridden to report Exceptions that happen in the retry loop      */
specifier|protected
name|void
name|reportException
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{     }
comment|/**      * Called if the loop times out without success, just before failing      */
specifier|protected
name|void
name|onTimeout
parameter_list|()
block|{     }
specifier|protected
name|long
name|getRemainingTimeSeconds
parameter_list|()
block|{
return|return
name|Math
operator|.
name|max
argument_list|(
literal|0L
argument_list|,
operator|(
name|timeout
operator|-
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|)
operator|/
literal|1000L
argument_list|)
return|;
block|}
block|}
end_class

end_unit

