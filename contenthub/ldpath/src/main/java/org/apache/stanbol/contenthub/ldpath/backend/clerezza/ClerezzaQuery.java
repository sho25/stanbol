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
name|contenthub
operator|.
name|ldpath
operator|.
name|backend
operator|.
name|clerezza
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
name|io
operator|.
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|MGraph
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
name|Resource
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
name|impl
operator|.
name|SimpleMGraph
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
name|Parser
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
name|SupportedFormat
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
name|jena
operator|.
name|parser
operator|.
name|JenaParserProvider
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
name|cli
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
name|cli
operator|.
name|CommandLineParser
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
name|cli
operator|.
name|HelpFormatter
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
name|cli
operator|.
name|Option
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
name|cli
operator|.
name|OptionBuilder
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
name|cli
operator|.
name|OptionGroup
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
name|cli
operator|.
name|Options
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
name|cli
operator|.
name|ParseException
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
name|cli
operator|.
name|PosixParser
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
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|LDPath
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|backend
operator|.
name|RDFBackend
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|exception
operator|.
name|LDPathParseException
import|;
end_import

begin_comment
comment|/**  * This class provides a main function for executing an LDProgram on {@link ClerezzaBackend} ,which is  * Clerezza based implementation of {@link RDFBackend}, populated with RDF data.  *   * @author suat  *   */
end_comment

begin_class
specifier|public
class|class
name|ClerezzaQuery
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ClerezzaQuery
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Parser
name|clerezzaRDFParser
decl_stmt|;
comment|/**      * This executable method provides execution of an LDPath program over an RDF data and prints out the      * obtained results. Passed RDF data should be in<b>RDF/XML</b> format.<br>      *       * Usage of this main method is as follows:<br>      * ClerezzaQuery -context<pre>&lt;uri></pre> -filePath<pre>&lt;filePath></pre> -path<pre>&lt;path></pre> | -program<pre>&lt;file></pre></br></br>       *       *<b><code>context</code>:</b> URI of the context node to start from<br>      *<b><code>rdfData</code>:</b> File system path of the file holding RDF data<br>      *<b><code>path</code>:</b> LD Path to evaluate on the file starting from the<code>context</code><br>      *<b><code>program</code>:</b> LD Path program to evaluate on the file starting from the      *<code>context</code><br>      *       * @param args      *            Collection of<code>context</code>,<code>rdfData</code>,<code>path</code> and<code>program</code> parameters      *       */
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|Options
name|options
init|=
name|buildOptions
argument_list|()
decl_stmt|;
name|CommandLineParser
name|parser
init|=
operator|new
name|PosixParser
argument_list|()
decl_stmt|;
try|try
block|{
name|CommandLine
name|cmd
init|=
name|parser
operator|.
name|parse
argument_list|(
name|options
argument_list|,
name|args
argument_list|)
decl_stmt|;
name|ClerezzaBackend
name|clerezzaBackend
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|cmd
operator|.
name|hasOption
argument_list|(
literal|"rdfData"
argument_list|)
condition|)
block|{
name|clerezzaRDFParser
operator|=
operator|new
name|Parser
argument_list|()
expr_stmt|;
name|clerezzaRDFParser
operator|.
name|bindParsingProvider
argument_list|(
operator|new
name|JenaParserProvider
argument_list|()
argument_list|)
expr_stmt|;
name|MGraph
name|mGraph
init|=
operator|new
name|SimpleMGraph
argument_list|(
name|clerezzaRDFParser
operator|.
name|parse
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|cmd
operator|.
name|getOptionValue
argument_list|(
literal|"rdfData"
argument_list|)
argument_list|)
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|)
argument_list|)
decl_stmt|;
name|clerezzaBackend
operator|=
operator|new
name|ClerezzaBackend
argument_list|(
name|mGraph
argument_list|)
expr_stmt|;
block|}
name|Resource
name|context
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|cmd
operator|.
name|hasOption
argument_list|(
literal|"context"
argument_list|)
condition|)
block|{
name|context
operator|=
name|clerezzaBackend
operator|.
name|createURI
argument_list|(
name|cmd
operator|.
name|getOptionValue
argument_list|(
literal|"context"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|clerezzaBackend
operator|!=
literal|null
operator|&&
name|context
operator|!=
literal|null
condition|)
block|{
name|LDPath
argument_list|<
name|Resource
argument_list|>
name|ldpath
init|=
operator|new
name|LDPath
argument_list|<
name|Resource
argument_list|>
argument_list|(
name|clerezzaBackend
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmd
operator|.
name|hasOption
argument_list|(
literal|"path"
argument_list|)
condition|)
block|{
name|String
name|path
init|=
name|cmd
operator|.
name|getOptionValue
argument_list|(
literal|"path"
argument_list|)
decl_stmt|;
for|for
control|(
name|Resource
name|r
range|:
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|context
argument_list|,
name|path
argument_list|,
literal|null
argument_list|)
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|r
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|cmd
operator|.
name|hasOption
argument_list|(
literal|"program"
argument_list|)
condition|)
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|cmd
operator|.
name|getOptionValue
argument_list|(
literal|"program"
argument_list|)
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|result
init|=
name|ldpath
operator|.
name|programQuery
argument_list|(
name|context
argument_list|,
operator|new
name|FileReader
argument_list|(
name|file
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|result
operator|.
name|keySet
argument_list|()
control|)
block|{
name|StringBuilder
name|line
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|line
operator|.
name|append
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|line
operator|.
name|append
argument_list|(
literal|" = "
argument_list|)
expr_stmt|;
name|line
operator|.
name|append
argument_list|(
literal|"{"
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|?
argument_list|>
name|it
init|=
name|result
operator|.
name|get
argument_list|(
name|field
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|line
operator|.
name|append
argument_list|(
name|it
operator|.
name|next
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|line
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
name|line
operator|.
name|append
argument_list|(
literal|"}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"invalid arguments"
argument_list|)
expr_stmt|;
name|HelpFormatter
name|formatter
init|=
operator|new
name|HelpFormatter
argument_list|()
decl_stmt|;
name|formatter
operator|.
name|printHelp
argument_list|(
literal|"ClerezzaQuery"
argument_list|,
name|options
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|LDPathParseException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"path or program could not be parsed"
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"file or program could not be found"
argument_list|)
expr_stmt|;
name|HelpFormatter
name|formatter
init|=
operator|new
name|HelpFormatter
argument_list|()
decl_stmt|;
name|formatter
operator|.
name|printHelp
argument_list|(
literal|"ClerezzaQuery"
argument_list|,
name|options
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|Options
name|buildOptions
parameter_list|()
block|{
name|Options
name|result
init|=
operator|new
name|Options
argument_list|()
decl_stmt|;
name|OptionGroup
name|query
init|=
operator|new
name|OptionGroup
argument_list|()
decl_stmt|;
name|OptionBuilder
operator|.
name|withArgName
argument_list|(
literal|"path"
argument_list|)
expr_stmt|;
name|OptionBuilder
operator|.
name|hasArg
argument_list|()
expr_stmt|;
name|OptionBuilder
operator|.
name|withDescription
argument_list|(
literal|"LD Path to evaluate on the file starting from the context"
argument_list|)
expr_stmt|;
name|Option
name|path
init|=
name|OptionBuilder
operator|.
name|create
argument_list|(
literal|"path"
argument_list|)
decl_stmt|;
name|OptionBuilder
operator|.
name|withArgName
argument_list|(
literal|"file"
argument_list|)
expr_stmt|;
name|OptionBuilder
operator|.
name|hasArg
argument_list|()
expr_stmt|;
name|OptionBuilder
operator|.
name|withDescription
argument_list|(
literal|"LD Path program to evaluate on the file starting from the context"
argument_list|)
expr_stmt|;
name|Option
name|program
init|=
name|OptionBuilder
operator|.
name|create
argument_list|(
literal|"program"
argument_list|)
decl_stmt|;
name|query
operator|.
name|addOption
argument_list|(
name|path
argument_list|)
expr_stmt|;
name|query
operator|.
name|addOption
argument_list|(
name|program
argument_list|)
expr_stmt|;
name|query
operator|.
name|setRequired
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|result
operator|.
name|addOptionGroup
argument_list|(
name|query
argument_list|)
expr_stmt|;
name|OptionBuilder
operator|.
name|withArgName
argument_list|(
literal|"rdfData"
argument_list|)
expr_stmt|;
name|OptionBuilder
operator|.
name|hasArg
argument_list|()
expr_stmt|;
name|OptionBuilder
operator|.
name|withDescription
argument_list|(
literal|"File system path of the file holding RDF data"
argument_list|)
expr_stmt|;
name|Option
name|filePath
init|=
name|OptionBuilder
operator|.
name|create
argument_list|(
literal|"rdfData"
argument_list|)
decl_stmt|;
name|filePath
operator|.
name|setRequired
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|result
operator|.
name|addOption
argument_list|(
name|filePath
argument_list|)
expr_stmt|;
name|OptionBuilder
operator|.
name|withArgName
argument_list|(
literal|"uri"
argument_list|)
expr_stmt|;
name|OptionBuilder
operator|.
name|hasArg
argument_list|()
expr_stmt|;
name|OptionBuilder
operator|.
name|withDescription
argument_list|(
literal|"URI of the context node to start from"
argument_list|)
expr_stmt|;
name|Option
name|context
init|=
name|OptionBuilder
operator|.
name|create
argument_list|(
literal|"context"
argument_list|)
decl_stmt|;
name|context
operator|.
name|setRequired
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|result
operator|.
name|addOption
argument_list|(
name|context
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

