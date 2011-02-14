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
name|stanbol
package|;
end_package

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
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|HttpEntity
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|HttpResponse
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|methods
operator|.
name|HttpGet
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|conn
operator|.
name|HttpHostConnectException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|impl
operator|.
name|client
operator|.
name|DefaultHttpClient
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|util
operator|.
name|EntityUtils
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
name|RequestBuilder
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
name|RequestExecutor
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
name|jarexec
operator|.
name|JarExecutor
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
comment|/** Base class for Stanbol integration tests - starts the runnable jar  *  to test if needed, and waits until server is ready before executing  *  the tests.  */
end_comment

begin_class
specifier|public
class|class
name|StanbolTestBase
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
name|StanbolTestBase
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TEST_SERVER_URL_PROP
init|=
literal|"test.server.url"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SERVER_READY_TIMEOUT_PROP
init|=
literal|"server.ready.timeout.seconds"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SERVER_READY_PROP_PREFIX
init|=
literal|"server.ready.path"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|KEEP_JAR_RUNNING_PROP
init|=
literal|"keepJarRunning"
decl_stmt|;
specifier|private
specifier|static
name|boolean
name|serverReady
decl_stmt|;
specifier|protected
specifier|static
name|String
name|serverBaseUrl
decl_stmt|;
specifier|protected
specifier|static
name|RequestBuilder
name|builder
decl_stmt|;
specifier|protected
specifier|static
name|DefaultHttpClient
name|httpClient
init|=
operator|new
name|DefaultHttpClient
argument_list|()
decl_stmt|;
specifier|protected
specifier|static
name|RequestExecutor
name|executor
init|=
operator|new
name|RequestExecutor
argument_list|(
name|httpClient
argument_list|)
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|startRunnableJar
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|String
name|configuredUrl
init|=
name|System
operator|.
name|getProperty
argument_list|(
name|TEST_SERVER_URL_PROP
argument_list|)
decl_stmt|;
if|if
condition|(
name|configuredUrl
operator|!=
literal|null
condition|)
block|{
name|serverBaseUrl
operator|=
name|configuredUrl
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
name|TEST_SERVER_URL_PROP
operator|+
literal|" is set: not starting server jar ("
operator|+
name|serverBaseUrl
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
specifier|final
name|JarExecutor
name|j
init|=
name|JarExecutor
operator|.
name|getInstance
argument_list|(
name|System
operator|.
name|getProperties
argument_list|()
argument_list|)
decl_stmt|;
name|j
operator|.
name|start
argument_list|()
expr_stmt|;
name|serverBaseUrl
operator|=
literal|"http://localhost:"
operator|+
name|j
operator|.
name|getServerPort
argument_list|()
expr_stmt|;
comment|// Optionally block here so that the runnable jar stays up - we can
comment|// then run tests against it from another VM
if|if
condition|(
literal|"true"
operator|.
name|equals
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
name|KEEP_JAR_RUNNING_PROP
argument_list|)
argument_list|)
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
name|KEEP_JAR_RUNNING_PROP
operator|+
literal|" set to true - entering infinite loop"
operator|+
literal|" so that runnable jar stays up. Kill this process to exit."
argument_list|)
expr_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
name|Thread
operator|.
name|sleep
argument_list|(
literal|1000L
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|builder
operator|=
operator|new
name|RequestBuilder
argument_list|(
name|serverBaseUrl
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|waitForServerReady
parameter_list|()
throws|throws
name|Exception
block|{
if|if
condition|(
name|serverReady
condition|)
block|{
return|return;
block|}
comment|// Timeout for readiness test
specifier|final
name|String
name|sec
init|=
name|System
operator|.
name|getProperty
argument_list|(
name|SERVER_READY_TIMEOUT_PROP
argument_list|)
decl_stmt|;
specifier|final
name|int
name|timeoutSec
init|=
name|sec
operator|==
literal|null
condition|?
literal|60
else|:
name|Integer
operator|.
name|valueOf
argument_list|(
name|sec
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Will wait up to "
operator|+
name|timeoutSec
operator|+
literal|" seconds for server to become ready"
argument_list|)
expr_stmt|;
specifier|final
name|long
name|endTime
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|+
name|timeoutSec
operator|*
literal|1000L
decl_stmt|;
comment|// Get the list of paths to test and expected content regexps
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|testPaths
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|final
name|TreeSet
argument_list|<
name|Object
argument_list|>
name|propertyNames
init|=
operator|new
name|TreeSet
argument_list|<
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|propertyNames
operator|.
name|addAll
argument_list|(
name|System
operator|.
name|getProperties
argument_list|()
operator|.
name|keySet
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Object
name|o
range|:
name|propertyNames
control|)
block|{
specifier|final
name|String
name|key
init|=
operator|(
name|String
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|key
operator|.
name|startsWith
argument_list|(
name|SERVER_READY_PROP_PREFIX
argument_list|)
condition|)
block|{
name|testPaths
operator|.
name|add
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Consider the server ready if it responds to a GET on each of
comment|// our configured request paths with a 200 result and content
comment|// that matches the regexp supplied with the path
name|long
name|sleepTime
init|=
literal|50
decl_stmt|;
name|readyLoop
label|:
while|while
condition|(
operator|!
name|serverReady
operator|&&
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|<
name|endTime
condition|)
block|{
comment|// Wait a bit between checks, to let the server come up
name|Thread
operator|.
name|sleep
argument_list|(
name|sleepTime
argument_list|)
expr_stmt|;
name|sleepTime
operator|=
name|Math
operator|.
name|min
argument_list|(
literal|2000L
argument_list|,
name|sleepTime
operator|*
literal|2
argument_list|)
expr_stmt|;
comment|// A test path is in the form path:substring or just path, in which case
comment|// we don't check that the content contains the substring
for|for
control|(
name|String
name|p
range|:
name|testPaths
control|)
block|{
specifier|final
name|String
index|[]
name|s
init|=
name|p
operator|.
name|split
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
specifier|final
name|String
name|path
init|=
name|s
index|[
literal|0
index|]
decl_stmt|;
specifier|final
name|String
name|substring
init|=
operator|(
name|s
operator|.
name|length
operator|>
literal|0
condition|?
name|s
index|[
literal|1
index|]
else|:
literal|null
operator|)
decl_stmt|;
specifier|final
name|String
name|url
init|=
name|serverBaseUrl
operator|+
name|path
decl_stmt|;
specifier|final
name|HttpGet
name|get
init|=
operator|new
name|HttpGet
argument_list|(
name|url
argument_list|)
decl_stmt|;
name|HttpResponse
name|response
init|=
literal|null
decl_stmt|;
name|HttpEntity
name|entity
init|=
literal|null
decl_stmt|;
try|try
block|{
name|response
operator|=
name|httpClient
operator|.
name|execute
argument_list|(
name|get
argument_list|)
expr_stmt|;
name|entity
operator|=
name|response
operator|.
name|getEntity
argument_list|()
expr_stmt|;
specifier|final
name|int
name|status
init|=
name|response
operator|.
name|getStatusLine
argument_list|()
operator|.
name|getStatusCode
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|!=
literal|200
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Got "
operator|+
name|status
operator|+
literal|" at "
operator|+
name|url
operator|+
literal|" - will retry"
argument_list|)
expr_stmt|;
continue|continue
name|readyLoop
continue|;
block|}
if|if
condition|(
name|substring
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|entity
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"No entity returned for "
operator|+
name|url
operator|+
literal|" - will retry"
argument_list|)
expr_stmt|;
continue|continue
name|readyLoop
continue|;
block|}
specifier|final
name|String
name|content
init|=
name|EntityUtils
operator|.
name|toString
argument_list|(
name|entity
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|content
operator|.
name|contains
argument_list|(
name|substring
argument_list|)
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Returned content for "
operator|+
name|url
operator|+
literal|" does not contain "
operator|+
name|substring
operator|+
literal|" - will retry"
argument_list|)
expr_stmt|;
continue|continue
name|readyLoop
continue|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|HttpHostConnectException
name|e
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Got HttpHostConnectException at "
operator|+
name|url
operator|+
literal|" - will retry"
argument_list|)
expr_stmt|;
continue|continue
name|readyLoop
continue|;
block|}
finally|finally
block|{
if|if
condition|(
name|entity
operator|!=
literal|null
condition|)
block|{
name|entity
operator|.
name|consumeContent
argument_list|()
expr_stmt|;
block|}
block|}
block|}
name|serverReady
operator|=
literal|true
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Got expected content for all configured requests, server is ready"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|serverReady
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Server not ready after "
operator|+
name|timeoutSec
operator|+
literal|" seconds"
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

