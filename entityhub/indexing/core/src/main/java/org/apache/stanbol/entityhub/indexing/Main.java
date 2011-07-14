begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
package|;
end_package

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
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|Indexer
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|IndexerFactory
import|;
end_import

begin_comment
comment|/**  * Command Line Utility for indexing. If not other specified the configuration  * is expected under {workingdir}/indexing.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|Main
block|{
specifier|private
specifier|static
specifier|final
name|Options
name|options
decl_stmt|;
static|static
block|{
name|options
operator|=
operator|new
name|Options
argument_list|()
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
literal|"h"
argument_list|,
literal|"help"
argument_list|,
literal|false
argument_list|,
literal|"display this help and exit"
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
literal|"c"
argument_list|,
literal|"chunksize"
argument_list|,
literal|true
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"the number of documents stored to the Yard in one chunk (default: %s)"
argument_list|,
name|Indexer
operator|.
name|DEFAULT_CHUNK_SIZE
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param args      * @throws ParseException       */
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
throws|throws
name|ParseException
block|{
name|CommandLineParser
name|parser
init|=
operator|new
name|PosixParser
argument_list|()
decl_stmt|;
name|CommandLine
name|line
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
name|args
operator|=
name|line
operator|.
name|getArgs
argument_list|()
expr_stmt|;
if|if
condition|(
name|line
operator|.
name|hasOption
argument_list|(
literal|'h'
argument_list|)
operator|||
name|args
operator|.
name|length
operator|<=
literal|0
condition|)
block|{
name|printHelp
argument_list|()
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
name|Indexer
name|indexer
decl_stmt|;
name|IndexerFactory
name|factory
init|=
name|IndexerFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|String
name|path
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|args
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|path
operator|=
name|args
index|[
literal|1
index|]
expr_stmt|;
block|}
if|if
condition|(
literal|"init"
operator|.
name|equalsIgnoreCase
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
operator|||
literal|"index"
operator|.
name|equalsIgnoreCase
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
if|if
condition|(
name|path
operator|!=
literal|null
condition|)
block|{
name|indexer
operator|=
name|factory
operator|.
name|create
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|indexer
operator|=
name|factory
operator|.
name|create
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|line
operator|.
name|hasOption
argument_list|(
literal|'c'
argument_list|)
condition|)
block|{
name|int
name|cunckSize
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|line
operator|.
name|getOptionValue
argument_list|(
literal|'c'
argument_list|)
argument_list|)
decl_stmt|;
name|indexer
operator|.
name|setChunkSize
argument_list|(
name|cunckSize
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"index"
operator|.
name|equalsIgnoreCase
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
name|indexer
operator|.
name|index
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Unknown command "
operator|+
name|args
index|[
literal|0
index|]
operator|+
literal|" (supported: init,index)\n\n"
argument_list|)
expr_stmt|;
name|printHelp
argument_list|()
expr_stmt|;
block|}
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
comment|/**      *       */
specifier|private
specifier|static
name|void
name|printHelp
parameter_list|()
block|{
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
literal|"java -Xmx{size} -jar org.apache.stanbol.indexing.core-*"
operator|+
literal|"-jar-with-dependencies.jar [options] init|index [configDir]"
argument_list|,
literal|"Indexing Commandline Utility: \n"
operator|+
literal|"  size:  Heap requirements depend on the dataset and the configuration.\n"
operator|+
literal|"         1024m should be a reasonable default.\n"
operator|+
literal|"  init:  Initialise the configuration with the defaults \n"
operator|+
literal|"  index: Needed to start the indexing process\n"
operator|+
literal|"  configDir: the path to the configuration directory (default:"
operator|+
literal|" user.dir)"
argument_list|,
name|options
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

