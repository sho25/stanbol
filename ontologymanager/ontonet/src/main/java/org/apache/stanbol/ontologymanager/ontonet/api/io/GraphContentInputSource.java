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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|io
package|;
end_package

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
name|Collections
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
name|Graph
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
name|TripleCollection
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
name|UriRef
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
name|access
operator|.
name|TcProvider
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
name|UnsupportedFormatException
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
name|indexedgraph
operator|.
name|IndexedMGraph
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|OntologyLoadingException
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|impl
operator|.
name|util
operator|.
name|OntologyUtils
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
comment|/**  * An ontology input source that returns a Clerezza {@link TripleCollection} ({@link Graph} or {@link MGraph})  * after parsing its serialized content from an input stream.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|GraphContentInputSource
extends|extends
name|AbstractClerezzaGraphInputSource
block|{
specifier|protected
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
comment|/**      * Creates a new graph input source by parsing<code>content</code>. Every supported format will be tried      * until one is parsed successfully. The resulting graph is created in-memory, and its triples will have      * to be manually added to a stored graph if necessary.      *       * @param content      *            the serialized graph content.      */
specifier|public
name|GraphContentInputSource
parameter_list|(
name|InputStream
name|content
parameter_list|)
block|{
name|this
argument_list|(
name|content
argument_list|,
operator|(
name|String
operator|)
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new graph input source by parsing<code>content</code> assuming it has the given format. The      * resulting graph is created in-memory, and its triples will have to be manually added to a stored graph      * if necessary.      *       * @param content      *            the serialized graph content.      * @param formatIdentifier      *            the format to parse the content as.      */
specifier|public
name|GraphContentInputSource
parameter_list|(
name|InputStream
name|content
parameter_list|,
name|String
name|formatIdentifier
parameter_list|)
block|{
name|this
argument_list|(
name|content
argument_list|,
name|formatIdentifier
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new graph input source by parsing<code>content</code> into a graph created using the      * supplied {@link TcProvider}, assuming it has the given format.      *       * @param content      *            the serialized graph content.      * @param formatIdentifier      *            the format to parse the content as.      * @param tcProvider      *            the provider that will create the graph where the triples will be stored.      */
specifier|public
name|GraphContentInputSource
parameter_list|(
name|InputStream
name|content
parameter_list|,
name|String
name|formatIdentifier
parameter_list|,
name|TcProvider
name|tcProvider
parameter_list|)
block|{
name|this
argument_list|(
name|content
argument_list|,
name|formatIdentifier
argument_list|,
name|tcProvider
argument_list|,
name|Parser
operator|.
name|getInstance
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new graph input source by parsing<code>content</code> (using the supplied {@link Parser})      * into a graph created using the supplied {@link TcProvider}, assuming it has the given format. An      * {@link OntologyLoadingException} will be thrown if the parser fails.      *       * @param content      *            the serialized graph content.      * @param formatIdentifier      *            the format to parse the content as. Blank or null values are allowed, but could cause      *            exceptions to be thrown if the supplied input stream cannot be reset.      * @param tcProvider      *            the provider that will create the graph where the triples will be stored. If null, an      *            in-memory graph will be created, in which case any ontology collectors using this input      *            source will most likely have to copy it to persistent storage.      * @param parser      *            the parser to use for creating the graph. If null, the default one will be used. * @deprecated      */
specifier|public
name|GraphContentInputSource
parameter_list|(
name|InputStream
name|content
parameter_list|,
name|String
name|formatIdentifier
parameter_list|,
name|TcProvider
name|tcProvider
parameter_list|,
name|Parser
name|parser
parameter_list|)
block|{
name|long
name|before
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
if|if
condition|(
name|content
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"No content supplied"
argument_list|)
throw|;
if|if
condition|(
name|parser
operator|==
literal|null
condition|)
name|parser
operator|=
name|Parser
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|boolean
name|loaded
init|=
literal|false
decl_stmt|;
comment|// Check if we can make multiple attempts at parsing the data stream.
if|if
condition|(
name|content
operator|.
name|markSupported
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Stream mark/reset supported. Can try multiple formats if necessary."
argument_list|)
expr_stmt|;
name|content
operator|.
name|mark
argument_list|(
name|Integer
operator|.
name|MAX_VALUE
argument_list|)
expr_stmt|;
block|}
comment|// Check for supported formats to try.
name|Collection
argument_list|<
name|String
argument_list|>
name|formats
decl_stmt|;
if|if
condition|(
name|formatIdentifier
operator|==
literal|null
operator|||
literal|""
operator|.
name|equals
argument_list|(
name|formatIdentifier
operator|.
name|trim
argument_list|()
argument_list|)
condition|)
name|formats
operator|=
name|OntologyUtils
operator|.
name|getPreferredSupportedFormats
argument_list|(
name|parser
operator|.
name|getSupportedFormats
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|formats
operator|=
name|Collections
operator|.
name|singleton
argument_list|(
name|formatIdentifier
argument_list|)
expr_stmt|;
comment|// TODO guess/lookahead the ontology ID and use it in the graph name.
name|UriRef
name|name
init|=
operator|new
name|UriRef
argument_list|(
literal|"ontonet"
operator|+
literal|"::"
operator|+
name|getClass
argument_list|()
operator|.
name|getCanonicalName
argument_list|()
operator|+
literal|"-"
operator|+
name|System
operator|.
name|currentTimeMillis
argument_list|()
argument_list|)
decl_stmt|;
name|TripleCollection
name|graph
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|tcProvider
operator|!=
literal|null
operator|&&
name|tcProvider
operator|!=
literal|null
condition|)
block|{
comment|// Graph directly stored in the TcProvider prior to using the source
name|graph
operator|=
name|tcProvider
operator|.
name|createMGraph
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|bindPhysicalOrigin
argument_list|(
name|Origin
operator|.
name|create
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
comment|// XXX if addition fails, should rollback procedures also delete the graph?
block|}
else|else
block|{
comment|// In memory graph, will most likely have to be copied afterwards.
name|graph
operator|=
operator|new
name|IndexedMGraph
argument_list|()
expr_stmt|;
name|bindPhysicalOrigin
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|Iterator
argument_list|<
name|String
argument_list|>
name|itf
init|=
name|formats
operator|.
name|iterator
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|itf
operator|.
name|hasNext
argument_list|()
condition|)
throw|throw
operator|new
name|OntologyLoadingException
argument_list|(
literal|"No suitable format found or defined."
argument_list|)
throw|;
do|do
block|{
name|String
name|f
init|=
name|itf
operator|.
name|next
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Parsing with format {}"
argument_list|,
name|f
argument_list|)
expr_stmt|;
try|try
block|{
name|parser
operator|.
name|parse
argument_list|(
operator|(
name|MGraph
operator|)
name|graph
argument_list|,
name|content
argument_list|,
name|f
argument_list|)
expr_stmt|;
name|loaded
operator|=
literal|true
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Graph parsed, has {} triples"
argument_list|,
name|graph
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedFormatException
name|e
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Parsing format {} failed."
argument_list|,
name|f
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
name|debug
argument_list|(
literal|"Error parsing format "
operator|+
name|f
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
if|if
condition|(
operator|!
name|loaded
operator|&&
name|content
operator|.
name|markSupported
argument_list|()
condition|)
try|try
block|{
name|content
operator|.
name|reset
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Failed to reset data stream while parsing format {}."
argument_list|,
name|f
argument_list|)
expr_stmt|;
block|}
block|}
block|}
do|while
condition|(
operator|!
name|loaded
operator|&&
name|itf
operator|.
name|hasNext
argument_list|()
condition|)
do|;
if|if
condition|(
name|loaded
condition|)
block|{
name|bindRootOntology
argument_list|(
name|graph
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Root ontology is a {}."
argument_list|,
name|getRootOntology
argument_list|()
operator|.
name|getClass
argument_list|()
operator|.
name|getCanonicalName
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Rollback graph creation, if any
if|if
condition|(
name|tcProvider
operator|!=
literal|null
operator|&&
name|tcProvider
operator|!=
literal|null
condition|)
name|tcProvider
operator|.
name|deleteTripleCollection
argument_list|(
name|name
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|OntologyLoadingException
argument_list|(
literal|"All parsers failed. Giving up."
argument_list|)
throw|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Input source initialization completed in {} ms."
argument_list|,
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|before
operator|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new graph input source by parsing<code>content</code> into a graph created using the      * supplied {@link TcProvider}. Every supported format will be tried until one is parsed successfully.      *       * @param content      *            the serialized graph content.      * @param tcProvider      *            the provider that will create the graph where the triples will be stored.      *       */
specifier|public
name|GraphContentInputSource
parameter_list|(
name|InputStream
name|content
parameter_list|,
name|TcProvider
name|tcProvider
parameter_list|)
block|{
name|this
argument_list|(
name|content
argument_list|,
literal|null
argument_list|,
name|tcProvider
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"<GRAPH_CONTENT>"
operator|+
name|getOrigin
argument_list|()
return|;
block|}
block|}
end_class

end_unit

