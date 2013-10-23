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
name|commons
operator|.
name|indexedgraph
package|;
end_package

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
name|NonLiteral
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
name|Triple
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
name|impl
operator|.
name|AbstractGraph
import|;
end_import

begin_comment
comment|/**  * {@link Graph} implementation that internally uses a {@link IndexedTripleCollection}  * to hold the RDF graph.  * @author rwesten  *  */
end_comment

begin_class
specifier|public
class|class
name|IndexedGraph
extends|extends
name|AbstractGraph
implements|implements
name|Graph
block|{
specifier|private
specifier|final
name|TripleCollection
name|tripleCollection
decl_stmt|;
comment|/**      * Creates a graph with the triples in tripleCollection      *       * @param tripleCollection the collection of triples this Graph shall consist of      */
specifier|public
name|IndexedGraph
parameter_list|(
name|TripleCollection
name|tripleCollection
parameter_list|)
block|{
name|this
operator|.
name|tripleCollection
operator|=
operator|new
name|IndexedTripleCollection
argument_list|(
name|tripleCollection
argument_list|)
expr_stmt|;
block|}
comment|/**      * Create a graph with the triples provided by the Iterator      * @param tripleIter the iterator over the triples      */
specifier|public
name|IndexedGraph
parameter_list|(
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|tripleIter
parameter_list|)
block|{
name|this
operator|.
name|tripleCollection
operator|=
operator|new
name|IndexedTripleCollection
argument_list|(
name|tripleIter
argument_list|)
expr_stmt|;
block|}
comment|//    /**
comment|//     * Create a read-only {@link Graph} wrapper over the provided
comment|//     * {@link TripleCollection}
comment|//     * @param tripleCollection the indexed triple collection create a read-only
comment|//     * wrapper around
comment|//     */
comment|//    protected IndexedGraph(IndexedTripleCollection tripleCollection){
comment|//        this.tripleCollection = tripleCollection;
comment|//    }
annotation|@
name|Override
specifier|protected
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|performFilter
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|UriRef
name|predicate
parameter_list|,
name|Resource
name|object
parameter_list|)
block|{
return|return
name|tripleCollection
operator|.
name|filter
argument_list|(
name|subject
argument_list|,
name|predicate
argument_list|,
name|object
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|tripleCollection
operator|.
name|size
argument_list|()
return|;
block|}
block|}
end_class

end_unit

