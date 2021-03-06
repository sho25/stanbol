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
import|import static
name|java
operator|.
name|lang
operator|.
name|System
operator|.
name|exit
import|;
end_import

begin_import
import|import static
name|java
operator|.
name|lang
operator|.
name|System
operator|.
name|out
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedWriter
import|;
end_import

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
name|FileOutputStream
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
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStreamWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|StringTokenizer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ArrayBlockingQueue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|BlockingQueue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|zip
operator|.
name|GZIPInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|zip
operator|.
name|GZIPOutputStream
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
name|commons
operator|.
name|compress
operator|.
name|compressors
operator|.
name|bzip2
operator|.
name|BZip2CompressorInputStream
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
name|compress
operator|.
name|compressors
operator|.
name|bzip2
operator|.
name|BZip2CompressorOutputStream
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
name|FilenameUtils
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
comment|/**  * Implemented to allow importing the Musicbrainz dump to Jena TDB.<p>  *   * The problem is that RDF dumps with blank nodes do require to store a  * lookup table for the black nodes IDs during import.<p> In case a dump  * contains millions of such nodes this table does no longer fit into  * memory. This makes importing Dumps to with the Jena RDF parser impossible.  *<p>  * This Utility can replaces nodes that start with "_:{id}" with  * "<{prefix}{id}>. The prefix must be set with the "-p" parameter.  *<p>  * This tool supports "gz" and "bz2" compressed files. If the output will use  * the same compression as the input. It uses two threads for "reading/processing"   * and "writing". It could also process multiple files in parallel. However this  * feature is not yet activated as the Musicbrainz dump comes in a single file.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|Urify
implements|implements
name|Runnable
block|{
specifier|private
specifier|static
specifier|final
name|String
name|EOF_INDICATOR
init|=
literal|"__EOF_INDICATOR"
decl_stmt|;
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|Urify
operator|.
name|class
argument_list|)
decl_stmt|;
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
literal|"p"
argument_list|,
literal|"prefix"
argument_list|,
literal|true
argument_list|,
literal|"The URI prefix used for wrapping the bNode Id"
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
literal|"e"
argument_list|,
literal|"encoding"
argument_list|,
literal|true
argument_list|,
literal|"the char encodinf (default: UTF-8)"
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
literal|"o"
argument_list|,
literal|"outputFilePrefix"
argument_list|,
literal|true
argument_list|,
literal|"The prefix to add to output files, defaults to \"uf_\""
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
name|IOException
throws|,
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
condition|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"Processes RDF files to translate blank nodes into prefixed URI nodes."
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"-h/--help: Print this help and exit."
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"-p/--prefix: Required: The prefix to add to blank nodes to make them URIs."
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"-e/--encoding: The text encoding to expect in the RDF, defaults to UTF-8."
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"-o/--outputFilePrefix: The prefix to add to output files, defaults to \"uf_\"."
argument_list|)
expr_stmt|;
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|line
operator|.
name|hasOption
argument_list|(
literal|'p'
argument_list|)
condition|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Missing parameter 'prefix' ('p)!"
argument_list|)
expr_stmt|;
name|exit
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
name|String
name|prefix
init|=
literal|"<"
operator|+
name|line
operator|.
name|getOptionValue
argument_list|(
literal|'p'
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Using prefix: {} "
argument_list|,
name|line
operator|.
name|getOptionValue
argument_list|(
literal|'p'
argument_list|)
argument_list|)
expr_stmt|;
name|Charset
name|charset
decl_stmt|;
if|if
condition|(
name|line
operator|.
name|hasOption
argument_list|(
literal|'e'
argument_list|)
condition|)
block|{
name|charset
operator|=
name|Charset
operator|.
name|forName
argument_list|(
name|line
operator|.
name|getOptionValue
argument_list|(
literal|'e'
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|charset
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unsupported encoding '{}'!"
argument_list|,
name|line
operator|.
name|getOptionValue
argument_list|(
literal|'e'
argument_list|)
argument_list|)
expr_stmt|;
name|exit
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|charset
operator|=
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"charset: {} "
argument_list|,
name|charset
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|Urify
name|urify
init|=
operator|new
name|Urify
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|args
argument_list|)
argument_list|,
name|charset
argument_list|,
name|prefix
argument_list|,
name|line
operator|.
name|hasOption
argument_list|(
literal|'o'
argument_list|)
condition|?
name|line
operator|.
name|getOptionValue
argument_list|(
literal|'o'
argument_list|)
else|:
literal|"uf_"
argument_list|)
decl_stmt|;
name|urify
operator|.
name|run
argument_list|()
expr_stmt|;
comment|//TODO: this could support processing multiple files in parallel
block|}
specifier|private
specifier|final
name|Charset
name|charset
decl_stmt|;
specifier|private
specifier|final
name|String
name|prefix
decl_stmt|;
specifier|private
specifier|final
name|String
name|outputFilePrefix
decl_stmt|;
specifier|protected
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
specifier|protected
name|long
name|uf_count
init|=
literal|0
decl_stmt|;
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|resources
decl_stmt|;
specifier|public
name|Urify
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|resources
parameter_list|,
name|Charset
name|charset
parameter_list|,
name|String
name|prefix
parameter_list|,
specifier|final
name|String
name|outputFilePrefix
parameter_list|)
throws|throws
name|IOException
block|{
name|this
operator|.
name|charset
operator|=
name|charset
expr_stmt|;
name|this
operator|.
name|prefix
operator|=
name|prefix
expr_stmt|;
name|this
operator|.
name|outputFilePrefix
operator|=
name|outputFilePrefix
expr_stmt|;
name|this
operator|.
name|resources
operator|=
name|Collections
operator|.
name|synchronizedList
argument_list|(
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
name|resources
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
name|String
name|source
decl_stmt|;
do|do
block|{
synchronized|synchronized
init|(
name|resources
init|)
block|{
if|if
condition|(
name|resources
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|source
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|source
operator|=
name|resources
operator|.
name|remove
argument_list|(
literal|0
argument_list|)
expr_stmt|;
try|try
block|{
name|urify
argument_list|(
name|source
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to Urify "
operator|+
name|resources
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
do|while
condition|(
name|source
operator|!=
literal|null
condition|)
do|;
block|}
specifier|private
name|void
name|urify
parameter_list|(
name|String
name|resource
parameter_list|)
throws|throws
name|IOException
block|{
name|File
name|source
init|=
operator|new
name|File
argument_list|(
name|resource
argument_list|)
decl_stmt|;
if|if
condition|(
name|source
operator|.
name|isFile
argument_list|()
condition|)
block|{
name|String
name|path
init|=
name|FilenameUtils
operator|.
name|getFullPathNoEndSeparator
argument_list|(
name|resource
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|FilenameUtils
operator|.
name|getName
argument_list|(
name|resource
argument_list|)
decl_stmt|;
name|File
name|target
init|=
operator|new
name|File
argument_list|(
name|path
argument_list|,
name|outputFilePrefix
operator|+
name|name
argument_list|)
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|target
operator|.
name|exists
argument_list|()
condition|)
block|{
name|i
operator|++
expr_stmt|;
name|target
operator|=
operator|new
name|File
argument_list|(
name|path
argument_list|,
literal|"uf"
operator|+
name|i
operator|+
literal|"_"
operator|+
name|name
argument_list|)
expr_stmt|;
block|}
name|InputStream
name|is
init|=
operator|new
name|FileInputStream
argument_list|(
name|source
argument_list|)
decl_stmt|;
name|OutputStream
name|os
init|=
operator|new
name|FileOutputStream
argument_list|(
name|target
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"RDFTerm: {}"
argument_list|,
name|resource
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Target  : {}"
argument_list|,
name|target
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"gz"
operator|.
name|equalsIgnoreCase
argument_list|(
name|FilenameUtils
operator|.
name|getExtension
argument_list|(
name|name
argument_list|)
argument_list|)
condition|)
block|{
name|is
operator|=
operator|new
name|GZIPInputStream
argument_list|(
name|is
argument_list|)
expr_stmt|;
name|os
operator|=
operator|new
name|GZIPOutputStream
argument_list|(
name|os
argument_list|)
expr_stmt|;
name|name
operator|=
name|FilenameUtils
operator|.
name|removeExtension
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"   - from GZIP Archive"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"bz2"
operator|.
name|equalsIgnoreCase
argument_list|(
name|FilenameUtils
operator|.
name|getExtension
argument_list|(
name|name
argument_list|)
argument_list|)
condition|)
block|{
name|is
operator|=
operator|new
name|BZip2CompressorInputStream
argument_list|(
name|is
argument_list|)
expr_stmt|;
name|os
operator|=
operator|new
name|BZip2CompressorOutputStream
argument_list|(
name|os
argument_list|)
expr_stmt|;
name|name
operator|=
name|FilenameUtils
operator|.
name|removeExtension
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"   - from BZip2 Archive"
argument_list|)
expr_stmt|;
block|}
comment|// TODO: No Zip File support
comment|//else no complression
name|BlockingQueue
argument_list|<
name|String
argument_list|>
name|queue
init|=
operator|new
name|ArrayBlockingQueue
argument_list|<
name|String
argument_list|>
argument_list|(
literal|1000
argument_list|)
decl_stmt|;
name|ReaderDaemon
name|reader
init|=
operator|new
name|ReaderDaemon
argument_list|(
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|is
argument_list|,
name|charset
argument_list|)
argument_list|)
argument_list|,
name|queue
argument_list|)
decl_stmt|;
name|WriterDaemon
name|writer
init|=
operator|new
name|WriterDaemon
argument_list|(
operator|new
name|BufferedWriter
argument_list|(
operator|new
name|OutputStreamWriter
argument_list|(
name|os
argument_list|,
name|charset
argument_list|)
argument_list|)
argument_list|,
name|queue
argument_list|)
decl_stmt|;
name|Thread
name|readerDaemon
init|=
operator|new
name|Thread
argument_list|(
name|reader
argument_list|,
name|name
operator|+
literal|" reader"
argument_list|)
decl_stmt|;
name|Thread
name|writerDaemon
init|=
operator|new
name|Thread
argument_list|(
name|writer
argument_list|,
name|name
operator|+
literal|" writer"
argument_list|)
decl_stmt|;
name|readerDaemon
operator|.
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|writerDaemon
operator|.
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|writerDaemon
operator|.
name|start
argument_list|()
expr_stmt|;
name|readerDaemon
operator|.
name|start
argument_list|()
expr_stmt|;
name|Object
name|notifier
init|=
name|writer
operator|.
name|getNotifier
argument_list|()
decl_stmt|;
synchronized|synchronized
init|(
name|notifier
init|)
block|{
comment|//wait until processed
if|if
condition|(
operator|!
name|writer
operator|.
name|completed
argument_list|()
condition|)
block|{
try|try
block|{
name|notifier
operator|.
name|wait
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
comment|/*ignore*/
block|}
block|}
block|}
if|if
condition|(
name|reader
operator|.
name|getError
argument_list|()
operator|!=
literal|null
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Error while reading source "
operator|+
name|source
argument_list|,
name|reader
operator|.
name|getError
argument_list|()
argument_list|)
throw|;
block|}
if|if
condition|(
name|writer
operator|.
name|getError
argument_list|()
operator|!=
literal|null
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Error while writing resource "
operator|+
name|target
argument_list|,
name|writer
operator|.
name|getError
argument_list|()
argument_list|)
throw|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|" ... completed resource {}"
argument_list|,
name|resource
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|FileNotFoundException
argument_list|(
literal|"Parsed File "
operator|+
name|resource
operator|+
literal|" does not exist or is not a File!"
argument_list|)
throw|;
block|}
block|}
specifier|private
class|class
name|ReaderDaemon
implements|implements
name|Runnable
block|{
specifier|private
specifier|final
name|BufferedReader
name|reader
decl_stmt|;
specifier|private
specifier|final
name|BlockingQueue
argument_list|<
name|String
argument_list|>
name|queue
decl_stmt|;
specifier|private
name|Exception
name|error
decl_stmt|;
specifier|protected
name|ReaderDaemon
parameter_list|(
name|BufferedReader
name|reader
parameter_list|,
name|BlockingQueue
argument_list|<
name|String
argument_list|>
name|queue
parameter_list|)
block|{
name|this
operator|.
name|reader
operator|=
name|reader
expr_stmt|;
name|this
operator|.
name|queue
operator|=
name|queue
expr_stmt|;
block|}
specifier|public
name|Exception
name|getError
parameter_list|()
block|{
return|return
name|error
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|String
name|triple
decl_stmt|;
try|try
block|{
while|while
condition|(
operator|(
name|triple
operator|=
name|reader
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|StringTokenizer
name|st
init|=
operator|new
name|StringTokenizer
argument_list|(
name|triple
argument_list|,
literal|" \t"
argument_list|)
decl_stmt|;
while|while
condition|(
name|st
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|String
name|node
init|=
name|st
operator|.
name|nextToken
argument_list|()
decl_stmt|;
if|if
condition|(
name|node
operator|.
name|startsWith
argument_list|(
literal|"_:"
argument_list|)
condition|)
block|{
comment|//convert to uri
name|sb
operator|.
name|append
argument_list|(
name|prefix
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|node
operator|.
name|substring
argument_list|(
literal|2
argument_list|,
name|node
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"> "
argument_list|)
expr_stmt|;
name|uf_count
operator|++
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|node
argument_list|)
expr_stmt|;
if|if
condition|(
name|node
operator|.
name|length
argument_list|()
operator|>
literal|1
condition|)
block|{
comment|//the final '.' is also a node
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|queue
operator|.
name|put
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|error
operator|=
name|e
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|error
operator|=
name|e
expr_stmt|;
block|}
finally|finally
block|{
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|reader
argument_list|)
expr_stmt|;
try|try
block|{
name|queue
operator|.
name|put
argument_list|(
name|EOF_INDICATOR
argument_list|)
expr_stmt|;
comment|//indicates finished
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to put EOF to queue!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|private
class|class
name|WriterDaemon
implements|implements
name|Runnable
block|{
specifier|private
specifier|final
name|BufferedWriter
name|writer
decl_stmt|;
specifier|private
specifier|final
name|BlockingQueue
argument_list|<
name|String
argument_list|>
name|queue
decl_stmt|;
specifier|private
specifier|final
name|Object
name|notifier
init|=
operator|new
name|Object
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|completed
init|=
literal|false
decl_stmt|;
specifier|private
name|Exception
name|error
decl_stmt|;
specifier|protected
name|WriterDaemon
parameter_list|(
name|BufferedWriter
name|writer
parameter_list|,
name|BlockingQueue
argument_list|<
name|String
argument_list|>
name|queue
parameter_list|)
block|{
name|this
operator|.
name|writer
operator|=
name|writer
expr_stmt|;
name|this
operator|.
name|queue
operator|=
name|queue
expr_stmt|;
block|}
specifier|public
name|Exception
name|getError
parameter_list|()
block|{
return|return
name|error
return|;
block|}
specifier|public
name|Object
name|getNotifier
parameter_list|()
block|{
return|return
name|notifier
return|;
block|}
specifier|public
name|boolean
name|completed
parameter_list|()
block|{
return|return
name|completed
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|String
name|triple
decl_stmt|;
name|long
name|count
init|=
literal|0
decl_stmt|;
name|boolean
name|first
init|=
literal|true
decl_stmt|;
try|try
block|{
while|while
condition|(
operator|!
name|EOF_INDICATOR
operator|.
name|equals
argument_list|(
operator|(
name|triple
operator|=
name|queue
operator|.
name|take
argument_list|()
operator|)
argument_list|)
condition|)
block|{
if|if
condition|(
name|count
operator|%
literal|1000000
operator|==
literal|0
condition|)
block|{
comment|//NOTE: urified will not be correct as it is counted
comment|//      by an other thread, but for logging ...
name|long
name|end
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"processed {} | urified: {} (batch: {}sec)"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|count
block|,
name|uf_count
block|,
operator|(
call|(
name|double
call|)
argument_list|(
name|end
operator|-
name|start
argument_list|)
operator|)
operator|/
literal|1000
block|}
argument_list|)
expr_stmt|;
name|start
operator|=
name|end
expr_stmt|;
block|}
name|count
operator|++
expr_stmt|;
if|if
condition|(
name|first
condition|)
block|{
name|first
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|writer
operator|.
name|write
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
name|writer
operator|.
name|write
argument_list|(
name|triple
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|this
operator|.
name|error
operator|=
name|e
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|this
operator|.
name|error
operator|=
name|e
expr_stmt|;
block|}
finally|finally
block|{
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|writer
argument_list|)
expr_stmt|;
name|this
operator|.
name|completed
operator|=
literal|true
expr_stmt|;
synchronized|synchronized
init|(
name|notifier
init|)
block|{
name|notifier
operator|.
name|notifyAll
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

