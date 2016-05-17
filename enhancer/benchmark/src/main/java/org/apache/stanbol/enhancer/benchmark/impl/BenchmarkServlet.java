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
name|benchmark
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|AccessController
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|PrivilegedAction
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|PrivilegedActionException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|PrivilegedExceptionAction
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|Properties
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServlet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|ImmutableGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|serializedform
operator|.
name|Serializer
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
name|io
operator|.
name|IOUtils
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
name|io
operator|.
name|LineIterator
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
name|lang
operator|.
name|StringEscapeUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Reference
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
name|enhancer
operator|.
name|benchmark
operator|.
name|Benchmark
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
name|enhancer
operator|.
name|benchmark
operator|.
name|BenchmarkParser
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|Chain
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|ChainManager
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentItemFactory
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|EnhancementJobManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|velocity
operator|.
name|Template
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|velocity
operator|.
name|VelocityContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|velocity
operator|.
name|app
operator|.
name|VelocityEngine
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|velocity
operator|.
name|runtime
operator|.
name|resource
operator|.
name|loader
operator|.
name|ClasspathResourceLoader
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|velocity
operator|.
name|tools
operator|.
name|generic
operator|.
name|EscapeTool
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|http
operator|.
name|HttpService
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|http
operator|.
name|NamespaceException
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
comment|/** Front-end servlet for the benchmark module */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|false
argument_list|)
annotation|@
name|SuppressWarnings
argument_list|(
literal|"serial"
argument_list|)
specifier|public
class|class
name|BenchmarkServlet
extends|extends
name|HttpServlet
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
name|Reference
specifier|private
name|HttpService
name|httpService
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|BenchmarkParser
name|parser
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|EnhancementJobManager
name|jobManager
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|ContentItemFactory
name|ciFactory
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|Serializer
name|graphSerializer
decl_stmt|;
comment|/**      * Needed to lookup active enhancement changes as parsed by {@link ChainState}      */
annotation|@
name|Reference
specifier|private
name|ChainManager
name|chainManager
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_CONTENT
init|=
literal|"content"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|PARAM_CHAIN
init|=
literal|"chain"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_MOUNT_PATH
init|=
literal|"/benchmark"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_BENCHMARK
init|=
literal|"default.txt"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|EXAMPLE_BENCHMARKS_RESOURCE_ROOT
init|=
literal|"/examples"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
name|DEFAULT_MOUNT_PATH
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|MOUNT_PATH_PROPERTY
init|=
literal|"mount.path"
decl_stmt|;
specifier|private
name|String
name|mountPath
decl_stmt|;
specifier|private
specifier|final
name|VelocityEngine
name|velocity
init|=
operator|new
name|VelocityEngine
argument_list|()
decl_stmt|;
comment|// Formatter for benchmark graphs
specifier|public
specifier|static
class|class
name|GraphFormatter
block|{
specifier|private
specifier|final
name|Serializer
name|serializer
decl_stmt|;
name|GraphFormatter
parameter_list|(
name|Serializer
name|s
parameter_list|)
block|{
name|serializer
operator|=
name|s
expr_stmt|;
block|}
specifier|public
name|String
name|format
parameter_list|(
name|ImmutableGraph
name|g
parameter_list|,
name|String
name|mimeType
parameter_list|)
throws|throws
name|UnsupportedEncodingException
block|{
specifier|final
name|ByteArrayOutputStream
name|bos
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|serializer
operator|.
name|serialize
argument_list|(
name|bos
argument_list|,
name|g
argument_list|,
name|mimeType
argument_list|)
expr_stmt|;
return|return
name|bos
operator|.
name|toString
argument_list|(
literal|"UTF-8"
argument_list|)
return|;
block|}
block|}
empty_stmt|;
comment|/** Register with HttpService when activated */
specifier|public
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|ServletException
throws|,
name|NamespaceException
block|{
name|mountPath
operator|=
operator|(
name|String
operator|)
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|MOUNT_PATH_PROPERTY
argument_list|)
expr_stmt|;
if|if
condition|(
name|mountPath
operator|==
literal|null
condition|)
block|{
name|mountPath
operator|=
name|DEFAULT_MOUNT_PATH
expr_stmt|;
block|}
if|if
condition|(
name|mountPath
operator|.
name|endsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
block|{
name|mountPath
operator|=
name|mountPath
operator|.
name|substring
argument_list|(
name|mountPath
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|httpService
operator|.
name|registerServlet
argument_list|(
name|mountPath
argument_list|,
name|this
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Servlet mounted at {}"
argument_list|,
name|mountPath
argument_list|)
expr_stmt|;
specifier|final
name|Properties
name|config
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|config
operator|.
name|put
argument_list|(
literal|"class.resource.loader.description"
argument_list|,
literal|"Velocity Classpath RDFTerm Loader"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
literal|"class.resource.loader.class"
argument_list|,
literal|"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
literal|"resource.loader"
argument_list|,
literal|"class"
argument_list|)
expr_stmt|;
name|velocity
operator|.
name|init
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|httpService
operator|.
name|unregister
argument_list|(
name|mountPath
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getServletInfo
parameter_list|()
block|{
return|return
literal|"Apache Stanbol Enhancer Benchmarks"
return|;
block|}
specifier|private
name|VelocityContext
name|getVelocityContext
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|String
name|pageTitle
parameter_list|)
block|{
specifier|final
name|VelocityContext
name|ctx
init|=
operator|new
name|VelocityContext
argument_list|()
decl_stmt|;
name|ctx
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|getServletInfo
argument_list|()
operator|+
literal|" - "
operator|+
name|pageTitle
argument_list|)
expr_stmt|;
name|ctx
operator|.
name|put
argument_list|(
literal|"contextPath"
argument_list|,
name|request
operator|.
name|getContextPath
argument_list|()
argument_list|)
expr_stmt|;
name|ctx
operator|.
name|put
argument_list|(
literal|"cssPath"
argument_list|,
name|request
operator|.
name|getContextPath
argument_list|()
operator|+
name|mountPath
operator|+
literal|"/benchmark.css"
argument_list|)
expr_stmt|;
name|ctx
operator|.
name|put
argument_list|(
literal|"esc"
argument_list|,
operator|new
name|EscapeTool
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ctx
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|doGet
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
specifier|final
name|String
name|path
init|=
name|request
operator|.
name|getPathInfo
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|request
operator|.
name|getPathInfo
argument_list|()
decl_stmt|;
if|if
condition|(
name|path
operator|.
name|endsWith
argument_list|(
literal|".css"
argument_list|)
condition|)
block|{
comment|// Serve our css
specifier|final
name|Template
name|t
init|=
name|getTemplate
argument_list|(
literal|"/velocity/benchmark.css"
argument_list|)
decl_stmt|;
name|response
operator|.
name|setContentType
argument_list|(
literal|"text/css"
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCharacterEncoding
argument_list|(
literal|"UTF-8"
argument_list|)
expr_stmt|;
name|t
operator|.
name|merge
argument_list|(
name|getVelocityContext
argument_list|(
name|request
argument_list|,
literal|null
argument_list|)
argument_list|,
name|response
operator|.
name|getWriter
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|path
operator|.
name|length
argument_list|()
operator|<
literal|2
condition|)
block|{
comment|// No benchmark specified -> redirect to default
name|response
operator|.
name|sendRedirect
argument_list|(
name|getExampleBenchmarkPath
argument_list|(
name|request
argument_list|,
name|DEFAULT_BENCHMARK
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Benchmark input form pre-filled with selected example
specifier|final
name|Template
name|t
init|=
name|getTemplate
argument_list|(
literal|"/velocity/benchmark-input.html"
argument_list|)
decl_stmt|;
specifier|final
name|VelocityContext
name|ctx
init|=
name|getVelocityContext
argument_list|(
name|request
argument_list|,
literal|"Benchmark Input"
argument_list|)
decl_stmt|;
name|ctx
operator|.
name|put
argument_list|(
literal|"formAction"
argument_list|,
name|request
operator|.
name|getContextPath
argument_list|()
operator|+
name|mountPath
argument_list|)
expr_stmt|;
name|ctx
operator|.
name|put
argument_list|(
literal|"benchmarkText"
argument_list|,
name|getBenchmarkText
argument_list|(
name|path
argument_list|)
argument_list|)
expr_stmt|;
name|ctx
operator|.
name|put
argument_list|(
literal|"benchmarkPaths"
argument_list|,
name|getExampleBenchmarkPaths
argument_list|(
name|request
argument_list|)
argument_list|)
expr_stmt|;
name|ctx
operator|.
name|put
argument_list|(
literal|"currentBenchmarkPath"
argument_list|,
name|path
argument_list|)
expr_stmt|;
name|response
operator|.
name|setContentType
argument_list|(
literal|"text/html"
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCharacterEncoding
argument_list|(
literal|"UTF-8"
argument_list|)
expr_stmt|;
name|t
operator|.
name|merge
argument_list|(
name|ctx
argument_list|,
name|response
operator|.
name|getWriter
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @return      */
specifier|private
name|Template
name|getTemplate
parameter_list|(
name|String
name|templatePath
parameter_list|)
block|{
specifier|final
name|Template
name|t
decl_stmt|;
name|ClassLoader
name|tcl
init|=
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getContextClassLoader
argument_list|()
decl_stmt|;
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|setContextClassLoader
argument_list|(
name|BenchmarkServlet
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|t
operator|=
name|velocity
operator|.
name|getTemplate
argument_list|(
name|templatePath
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|setContextClassLoader
argument_list|(
name|tcl
argument_list|)
expr_stmt|;
block|}
return|return
name|t
return|;
block|}
specifier|private
name|String
name|getExampleBenchmarkPath
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|String
name|name
parameter_list|)
block|{
return|return
name|request
operator|.
name|getContextPath
argument_list|()
operator|+
name|mountPath
operator|+
literal|"/"
operator|+
name|name
return|;
block|}
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|getExampleBenchmarkPaths
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|IOException
block|{
comment|// TODO how to enumerate bundle resources?
specifier|final
name|String
name|list
init|=
name|getBenchmarkText
argument_list|(
literal|"/LIST.txt"
argument_list|)
decl_stmt|;
specifier|final
name|LineIterator
name|it
init|=
operator|new
name|LineIterator
argument_list|(
operator|new
name|StringReader
argument_list|(
name|list
argument_list|)
argument_list|)
decl_stmt|;
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|LinkedList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|getExampleBenchmarkPath
argument_list|(
name|request
argument_list|,
name|it
operator|.
name|nextLine
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
comment|/** Return example benchmark text from our class resources */
specifier|private
name|String
name|getBenchmarkText
parameter_list|(
name|String
name|path
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|InputStream
name|is
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|EXAMPLE_BENCHMARKS_RESOURCE_ROOT
operator|+
name|path
argument_list|)
decl_stmt|;
if|if
condition|(
name|is
operator|==
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
try|try
block|{
return|return
name|IOUtils
operator|.
name|toString
argument_list|(
name|is
argument_list|)
return|;
block|}
finally|finally
block|{
name|is
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|void
name|doPost
parameter_list|(
specifier|final
name|HttpServletRequest
name|request
parameter_list|,
specifier|final
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
specifier|final
name|String
name|content
init|=
name|request
operator|.
name|getParameter
argument_list|(
name|PARAM_CONTENT
argument_list|)
decl_stmt|;
if|if
condition|(
name|content
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ServletException
argument_list|(
literal|"Missing "
operator|+
name|PARAM_CONTENT
operator|+
literal|" parameter"
argument_list|)
throw|;
block|}
name|String
name|chainName
init|=
name|request
operator|.
name|getParameter
argument_list|(
name|PARAM_CHAIN
argument_list|)
decl_stmt|;
specifier|final
name|Template
name|t
init|=
name|AccessController
operator|.
name|doPrivileged
argument_list|(
operator|new
name|PrivilegedAction
argument_list|<
name|Template
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Template
name|run
parameter_list|()
block|{
return|return
name|getTemplate
argument_list|(
literal|"/velocity/benchmark-results.html"
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
specifier|final
name|VelocityContext
name|ctx
init|=
name|getVelocityContext
argument_list|(
name|request
argument_list|,
literal|"Benchmark Results"
argument_list|)
decl_stmt|;
name|ctx
operator|.
name|put
argument_list|(
literal|"contentItemFactory"
argument_list|,
name|ciFactory
argument_list|)
expr_stmt|;
name|ctx
operator|.
name|put
argument_list|(
literal|"jobManager"
argument_list|,
name|jobManager
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|?
extends|extends
name|Benchmark
argument_list|>
name|benchmarks
init|=
name|parser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|content
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|chainName
operator|!=
literal|null
operator|&&
operator|!
name|chainName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Chain
name|chain
init|=
name|chainManager
operator|.
name|getChain
argument_list|(
name|chainName
argument_list|)
decl_stmt|;
if|if
condition|(
name|chain
operator|==
literal|null
condition|)
block|{
name|response
operator|.
name|setStatus
argument_list|(
literal|404
argument_list|)
expr_stmt|;
name|PrintWriter
name|w
init|=
name|response
operator|.
name|getWriter
argument_list|()
decl_stmt|;
name|w
operator|.
name|println
argument_list|(
literal|"Unable to perform benchmark on EnhancementChain '"
operator|+
name|StringEscapeUtils
operator|.
name|escapeHtml
argument_list|(
name|chainName
argument_list|)
operator|+
literal|"' because no chain with that name is active!"
argument_list|)
expr_stmt|;
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|w
argument_list|)
expr_stmt|;
return|return;
block|}
for|for
control|(
name|Benchmark
name|benchmark
range|:
name|benchmarks
control|)
block|{
name|benchmark
operator|.
name|setChain
argument_list|(
name|chain
argument_list|)
expr_stmt|;
block|}
block|}
name|ctx
operator|.
name|put
argument_list|(
literal|"benchmarks"
argument_list|,
name|benchmarks
argument_list|)
expr_stmt|;
name|ctx
operator|.
name|put
argument_list|(
literal|"graphFormatter"
argument_list|,
operator|new
name|GraphFormatter
argument_list|(
name|graphSerializer
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setContentType
argument_list|(
literal|"text/html"
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCharacterEncoding
argument_list|(
literal|"UTF-8"
argument_list|)
expr_stmt|;
try|try
block|{
name|AccessController
operator|.
name|doPrivileged
argument_list|(
operator|new
name|PrivilegedExceptionAction
argument_list|<
name|Object
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Object
name|run
parameter_list|()
throws|throws
name|IOException
block|{
name|t
operator|.
name|merge
argument_list|(
name|ctx
argument_list|,
name|response
operator|.
name|getWriter
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PrivilegedActionException
name|pae
parameter_list|)
block|{
name|Exception
name|e
init|=
name|pae
operator|.
name|getException
argument_list|()
decl_stmt|;
if|if
condition|(
name|e
operator|instanceof
name|IOException
condition|)
block|{
throw|throw
operator|(
name|IOException
operator|)
name|e
throw|;
block|}
else|else
block|{
throw|throw
name|RuntimeException
operator|.
name|class
operator|.
name|cast
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
block|}
block|}
end_class

end_unit

