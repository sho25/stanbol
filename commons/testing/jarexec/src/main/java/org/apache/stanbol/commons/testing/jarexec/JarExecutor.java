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
name|jarexec
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|CommandLine
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
name|DefaultExecutor
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
name|ExecuteException
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
name|ExecuteResultHandler
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
name|commons
operator|.
name|exec
operator|.
name|ShutdownHookProcessDestroyer
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
name|util
operator|.
name|StringUtils
import|;
end_import

begin_comment
comment|/** Start a runnable jar by forking a JVM process,  *  and terminate the process when this VM exits.  */
end_comment

begin_class
specifier|public
class|class
name|JarExecutor
block|{
specifier|private
specifier|static
name|JarExecutor
name|instance
decl_stmt|;
specifier|private
specifier|final
name|File
name|jarToExecute
decl_stmt|;
specifier|private
specifier|final
name|String
name|javaExecutable
decl_stmt|;
specifier|private
specifier|final
name|int
name|serverPort
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_PORT
init|=
literal|8765
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_JAR_FOLDER
init|=
literal|"target/dependency"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_JAR_NAME_REGEXP
init|=
literal|"org.apache.stanbol.*full.*jar$"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROP_PREFIX
init|=
literal|"jar.executor."
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROP_SERVER_PORT
init|=
name|PROP_PREFIX
operator|+
literal|"server.port"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROP_JAR_FOLDER
init|=
name|PROP_PREFIX
operator|+
literal|"jar.folder"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROP_JAR_NAME_REGEXP
init|=
name|PROP_PREFIX
operator|+
literal|"jar.name.regexp"
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"serial"
argument_list|)
specifier|public
specifier|static
class|class
name|ExecutorException
extends|extends
name|Exception
block|{
name|ExecutorException
parameter_list|(
name|String
name|reason
parameter_list|)
block|{
name|super
argument_list|(
name|reason
argument_list|)
expr_stmt|;
block|}
name|ExecutorException
parameter_list|(
name|String
name|reason
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|reason
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|int
name|getServerPort
parameter_list|()
block|{
return|return
name|serverPort
return|;
block|}
specifier|public
specifier|static
name|JarExecutor
name|getInstance
parameter_list|(
name|Properties
name|config
parameter_list|)
throws|throws
name|ExecutorException
block|{
if|if
condition|(
name|instance
operator|==
literal|null
condition|)
block|{
synchronized|synchronized
init|(
name|JarExecutor
operator|.
name|class
init|)
block|{
if|if
condition|(
name|instance
operator|==
literal|null
condition|)
block|{
name|instance
operator|=
operator|new
name|JarExecutor
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|instance
return|;
block|}
comment|/** Build a JarExecutor, locate the jar to run, etc */
specifier|private
name|JarExecutor
parameter_list|(
name|Properties
name|config
parameter_list|)
throws|throws
name|ExecutorException
block|{
specifier|final
name|boolean
name|isWindows
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"os.name"
argument_list|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|contains
argument_list|(
literal|"windows"
argument_list|)
decl_stmt|;
name|String
name|portStr
init|=
name|config
operator|.
name|getProperty
argument_list|(
name|PROP_SERVER_PORT
argument_list|)
decl_stmt|;
name|serverPort
operator|=
name|portStr
operator|==
literal|null
condition|?
name|DEFAULT_PORT
else|:
name|Integer
operator|.
name|valueOf
argument_list|(
name|portStr
argument_list|)
expr_stmt|;
name|javaExecutable
operator|=
name|isWindows
condition|?
literal|"java.exe"
else|:
literal|"java"
expr_stmt|;
name|String
name|jarFolderPath
init|=
name|config
operator|.
name|getProperty
argument_list|(
name|PROP_JAR_FOLDER
argument_list|)
decl_stmt|;
name|jarFolderPath
operator|=
name|jarFolderPath
operator|==
literal|null
condition|?
name|DEFAULT_JAR_FOLDER
else|:
name|jarFolderPath
expr_stmt|;
specifier|final
name|File
name|jarFolder
init|=
operator|new
name|File
argument_list|(
name|jarFolderPath
argument_list|)
decl_stmt|;
name|String
name|jarNameRegexp
init|=
name|config
operator|.
name|getProperty
argument_list|(
name|PROP_JAR_NAME_REGEXP
argument_list|)
decl_stmt|;
name|jarNameRegexp
operator|=
name|jarNameRegexp
operator|==
literal|null
condition|?
name|DEFAULT_JAR_NAME_REGEXP
else|:
name|jarNameRegexp
expr_stmt|;
specifier|final
name|Pattern
name|jarPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|jarNameRegexp
argument_list|)
decl_stmt|;
comment|// Find executable jar
specifier|final
name|String
index|[]
name|candidates
init|=
name|jarFolder
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|candidates
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ExecutorException
argument_list|(
literal|"No files found in jar folder specified by "
operator|+
name|PROP_JAR_FOLDER
operator|+
literal|" property: "
operator|+
name|jarFolder
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
throw|;
block|}
name|File
name|f
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|candidates
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|filename
range|:
name|candidates
control|)
block|{
if|if
condition|(
name|jarPattern
operator|.
name|matcher
argument_list|(
name|filename
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
block|{
name|f
operator|=
operator|new
name|File
argument_list|(
name|jarFolder
argument_list|,
name|filename
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|f
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ExecutorException
argument_list|(
literal|"Executable jar matching '"
operator|+
name|jarPattern
operator|+
literal|"' not found in "
operator|+
name|jarFolder
operator|.
name|getAbsolutePath
argument_list|()
operator|+
literal|", candidates are "
operator|+
name|Arrays
operator|.
name|asList
argument_list|(
name|candidates
argument_list|)
argument_list|)
throw|;
block|}
name|jarToExecute
operator|=
name|f
expr_stmt|;
block|}
comment|/** Start the jar if not done yet, and setup runtime hook      *  to stop it.      */
specifier|public
name|void
name|start
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|ExecuteResultHandler
name|h
init|=
operator|new
name|ExecuteResultHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onProcessFailed
parameter_list|(
name|ExecuteException
name|ex
parameter_list|)
block|{
name|info
argument_list|(
literal|"Process execution failed:"
operator|+
name|ex
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onProcessComplete
parameter_list|(
name|int
name|result
parameter_list|)
block|{
name|info
argument_list|(
literal|"Process execution complete, exit code="
operator|+
name|result
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
specifier|final
name|String
name|vmOptions
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"jar.executor.vm.options"
argument_list|)
decl_stmt|;
specifier|final
name|Executor
name|e
init|=
operator|new
name|DefaultExecutor
argument_list|()
decl_stmt|;
specifier|final
name|CommandLine
name|cl
init|=
operator|new
name|CommandLine
argument_list|(
name|javaExecutable
argument_list|)
decl_stmt|;
if|if
condition|(
name|vmOptions
operator|!=
literal|null
operator|&&
name|vmOptions
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
comment|// TODO: this will fail if one of the vm options as a quoted value with a space in it, but this is
comment|// not the case for common usage patterns
for|for
control|(
name|String
name|option
range|:
name|StringUtils
operator|.
name|split
argument_list|(
name|vmOptions
argument_list|,
literal|" "
argument_list|)
control|)
block|{
name|cl
operator|.
name|addArgument
argument_list|(
name|option
argument_list|)
expr_stmt|;
block|}
block|}
name|cl
operator|.
name|addArgument
argument_list|(
literal|"-jar"
argument_list|)
expr_stmt|;
name|cl
operator|.
name|addArgument
argument_list|(
name|jarToExecute
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
name|cl
operator|.
name|addArgument
argument_list|(
literal|"-p"
argument_list|)
expr_stmt|;
name|cl
operator|.
name|addArgument
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|serverPort
argument_list|)
argument_list|)
expr_stmt|;
name|info
argument_list|(
literal|"Executing "
operator|+
name|cl
argument_list|)
expr_stmt|;
name|e
operator|.
name|setProcessDestroyer
argument_list|(
operator|new
name|ShutdownHookProcessDestroyer
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|execute
argument_list|(
name|cl
argument_list|,
name|h
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|info
parameter_list|(
name|String
name|msg
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|": "
operator|+
name|msg
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

