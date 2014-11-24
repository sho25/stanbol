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
operator|.
name|source
operator|.
name|jenatdb
package|;
end_package

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
name|jena
operator|.
name|atlas
operator|.
name|lib
operator|.
name|Tuple
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
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|graph
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|graph
operator|.
name|Triple
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|sparql
operator|.
name|core
operator|.
name|Quad
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|tdb
operator|.
name|TDBException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|tdb
operator|.
name|TDBLoader
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|tdb
operator|.
name|solver
operator|.
name|stats
operator|.
name|Stats
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|tdb
operator|.
name|solver
operator|.
name|stats
operator|.
name|StatsCollector
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|tdb
operator|.
name|store
operator|.
name|DatasetGraphTDB
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|tdb
operator|.
name|store
operator|.
name|TripleTable
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|tdb
operator|.
name|store
operator|.
name|bulkloader
operator|.
name|BulkLoader
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|tdb
operator|.
name|store
operator|.
name|bulkloader
operator|.
name|BulkStreamRDF
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|tdb
operator|.
name|store
operator|.
name|bulkloader
operator|.
name|LoadMonitor
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|tdb
operator|.
name|store
operator|.
name|bulkloader
operator|.
name|LoaderNodeTupleTable
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|tdb
operator|.
name|sys
operator|.
name|Names
import|;
end_import

begin_comment
comment|/**  * Special version of an {@link BulkStreamRDF} that stores Triples to the  * {@link TripleTable} of the parsed {@link DatasetGraphTDB}. Even  * {@link Quad}s and {@link Tuple}s with>= 3 nodes are converted to triples.  *<p>  * This code is based on the DestinationGraph implementation private to the   * {@link TDBLoader} class.  *<p>  * In addition this implementation supports an {@link RdfImportFilter} that  * can be used to filter RDF triples read from RDF files before adding them  * to the RDF TripleStore.   *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
class|class
name|DestinationTripleGraph
implements|implements
name|BulkStreamRDF
block|{
comment|/**      * ImportFilter that accepts all triples. This is used in case       *<code>null</code> is parsed as {@link RdfImportFilter} to the constructor      */
specifier|private
specifier|static
specifier|final
name|RdfImportFilter
name|NO_FILTER
init|=
operator|new
name|RdfImportFilter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|setConfiguration
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
block|{}
annotation|@
name|Override
specifier|public
name|boolean
name|needsInitialisation
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|initialise
parameter_list|()
block|{}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
block|{}
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|Node
name|s
parameter_list|,
name|Node
name|p
parameter_list|,
name|Node
name|o
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
decl_stmt|;
specifier|final
specifier|private
name|DatasetGraphTDB
name|dsg
decl_stmt|;
specifier|final
specifier|private
name|LoadMonitor
name|monitor
decl_stmt|;
specifier|final
specifier|private
name|LoaderNodeTupleTable
name|loaderTriples
decl_stmt|;
specifier|final
specifier|private
name|boolean
name|startedEmpty
decl_stmt|;
specifier|private
name|long
name|count
init|=
literal|0
decl_stmt|;
specifier|private
name|long
name|filteredCount
init|=
literal|0
decl_stmt|;
specifier|private
name|StatsCollector
name|stats
decl_stmt|;
specifier|private
name|RdfImportFilter
name|importFilter
decl_stmt|;
specifier|private
specifier|final
name|Logger
name|importLog
decl_stmt|;
name|DestinationTripleGraph
parameter_list|(
specifier|final
name|DatasetGraphTDB
name|dsg
parameter_list|,
name|RdfImportFilter
name|importFilter
parameter_list|,
name|Logger
name|log
parameter_list|)
block|{
name|this
operator|.
name|dsg
operator|=
name|dsg
expr_stmt|;
name|startedEmpty
operator|=
name|dsg
operator|.
name|isEmpty
argument_list|()
expr_stmt|;
name|monitor
operator|=
operator|new
name|LoadMonitor
argument_list|(
name|dsg
argument_list|,
name|log
argument_list|,
literal|"triples"
argument_list|,
name|BulkLoader
operator|.
name|DataTickPoint
argument_list|,
name|BulkLoader
operator|.
name|IndexTickPoint
argument_list|)
expr_stmt|;
name|loaderTriples
operator|=
operator|new
name|LoaderNodeTupleTable
argument_list|(
name|dsg
operator|.
name|getTripleTable
argument_list|()
operator|.
name|getNodeTupleTable
argument_list|()
argument_list|,
literal|"triples"
argument_list|,
name|monitor
argument_list|)
expr_stmt|;
if|if
condition|(
name|importFilter
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|importFilter
operator|=
name|NO_FILTER
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|importFilter
operator|=
name|importFilter
expr_stmt|;
block|}
name|this
operator|.
name|importLog
operator|=
name|log
expr_stmt|;
block|}
annotation|@
name|Override
specifier|final
specifier|public
name|void
name|startBulk
parameter_list|()
block|{
name|loaderTriples
operator|.
name|loadStart
argument_list|()
expr_stmt|;
name|loaderTriples
operator|.
name|loadDataStart
argument_list|()
expr_stmt|;
name|this
operator|.
name|stats
operator|=
operator|new
name|StatsCollector
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|triple
parameter_list|(
name|Node
name|s
parameter_list|,
name|Node
name|p
parameter_list|,
name|Node
name|o
parameter_list|)
block|{
if|if
condition|(
name|importFilter
operator|.
name|accept
argument_list|(
name|s
argument_list|,
name|p
argument_list|,
name|o
argument_list|)
condition|)
block|{
name|loaderTriples
operator|.
name|load
argument_list|(
name|s
argument_list|,
name|p
argument_list|,
name|o
argument_list|)
expr_stmt|;
name|stats
operator|.
name|record
argument_list|(
literal|null
argument_list|,
name|s
argument_list|,
name|p
argument_list|,
name|o
argument_list|)
expr_stmt|;
name|count
operator|++
expr_stmt|;
block|}
else|else
block|{
name|filteredCount
operator|++
expr_stmt|;
if|if
condition|(
name|filteredCount
operator|%
literal|100000
operator|==
literal|0
condition|)
block|{
name|importLog
operator|.
name|info
argument_list|(
literal|"Filtered: {} triples ({}%)"
argument_list|,
name|filteredCount
argument_list|,
operator|(
operator|(
name|double
operator|)
name|filteredCount
operator|*
literal|100
operator|/
call|(
name|double
call|)
argument_list|(
name|filteredCount
operator|+
name|count
argument_list|)
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|final
specifier|public
name|void
name|triple
parameter_list|(
name|Triple
name|triple
parameter_list|)
block|{
name|triple
argument_list|(
name|triple
operator|.
name|getSubject
argument_list|()
argument_list|,
name|triple
operator|.
name|getPredicate
argument_list|()
argument_list|,
name|triple
operator|.
name|getObject
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|final
specifier|public
name|void
name|finishBulk
parameter_list|()
block|{
name|loaderTriples
operator|.
name|loadDataFinish
argument_list|()
expr_stmt|;
name|loaderTriples
operator|.
name|loadIndexStart
argument_list|()
expr_stmt|;
name|loaderTriples
operator|.
name|loadIndexFinish
argument_list|()
expr_stmt|;
name|loaderTriples
operator|.
name|loadFinish
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|dsg
operator|.
name|getLocation
argument_list|()
operator|.
name|isMem
argument_list|()
operator|&&
name|startedEmpty
condition|)
block|{
name|String
name|filename
init|=
name|dsg
operator|.
name|getLocation
argument_list|()
operator|.
name|getPath
argument_list|(
name|Names
operator|.
name|optStats
argument_list|)
decl_stmt|;
name|Stats
operator|.
name|write
argument_list|(
name|filename
argument_list|,
name|stats
operator|.
name|results
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|forceSync
argument_list|(
name|dsg
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|start
parameter_list|()
block|{}
annotation|@
name|Override
specifier|public
name|void
name|quad
parameter_list|(
name|Quad
name|quad
parameter_list|)
block|{
name|triple
argument_list|(
name|quad
operator|.
name|getSubject
argument_list|()
argument_list|,
name|quad
operator|.
name|getPredicate
argument_list|()
argument_list|,
name|quad
operator|.
name|getObject
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|tuple
parameter_list|(
name|Tuple
argument_list|<
name|Node
argument_list|>
name|tuple
parameter_list|)
block|{
if|if
condition|(
name|tuple
operator|.
name|size
argument_list|()
operator|>=
literal|3
condition|)
block|{
name|triple
argument_list|(
name|tuple
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|,
name|tuple
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|,
name|tuple
operator|.
name|get
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|TDBException
argument_list|(
literal|"Tuple with< 3 Nodes encountered while loading a single graph"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|base
parameter_list|(
name|String
name|base
parameter_list|)
block|{}
annotation|@
name|Override
specifier|public
name|void
name|prefix
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|iri
parameter_list|)
block|{ }
comment|// TODO
annotation|@
name|Override
specifier|public
name|void
name|finish
parameter_list|()
block|{}
specifier|static
name|void
name|forceSync
parameter_list|(
name|DatasetGraphTDB
name|dsg
parameter_list|)
block|{
comment|// Force sync - we have been bypassing DSG tables.
comment|// THIS DOES NOT WORK IF modules check for SYNC necessity.
name|dsg
operator|.
name|getTripleTable
argument_list|()
operator|.
name|getNodeTupleTable
argument_list|()
operator|.
name|getNodeTable
argument_list|()
operator|.
name|sync
argument_list|()
expr_stmt|;
name|dsg
operator|.
name|getQuadTable
argument_list|()
operator|.
name|getNodeTupleTable
argument_list|()
operator|.
name|getNodeTable
argument_list|()
operator|.
name|sync
argument_list|()
expr_stmt|;
name|dsg
operator|.
name|getQuadTable
argument_list|()
operator|.
name|getNodeTupleTable
argument_list|()
operator|.
name|getNodeTable
argument_list|()
operator|.
name|sync
argument_list|()
expr_stmt|;
name|dsg
operator|.
name|getPrefixes
argument_list|()
operator|.
name|getNodeTupleTable
argument_list|()
operator|.
name|getNodeTable
argument_list|()
operator|.
name|sync
argument_list|()
expr_stmt|;
comment|// This is not enough -- modules check whether sync needed.
name|dsg
operator|.
name|sync
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

