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
name|sources
operator|.
name|clerezza
package|;
end_package

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
name|commons
operator|.
name|rdf
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
name|commons
operator|.
name|rdf
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
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
name|TcManager
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
name|stanbol
operator|.
name|ontologymanager
operator|.
name|servicesapi
operator|.
name|io
operator|.
name|OntologyInputSource
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
name|servicesapi
operator|.
name|io
operator|.
name|Origin
import|;
end_import

begin_comment
comment|/**  * An {@link OntologyInputSource} that gets ontologies from either a stored {@link Graph}, or its  * identifier and an optionally supplied triple collection manager.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|GraphSource
extends|extends
name|AbstractClerezzaGraphInputSource
block|{
comment|/**      * Creates a new input source by querying the default triple collection manager for a graph named with the      * supplied<code>graphId</code>. A {@link IRI} that represents the graph name will also be set as the      * graph origin.      *       * @param graphId      *            the graph ID.      * @throws NullPointerException      *             if there is no default triple collection manager available.      * @throws org.apache.clerezza.rdf.core.access.NoSuchEntityException      *             if no such graph can be found.      */
specifier|public
name|GraphSource
parameter_list|(
name|String
name|graphId
parameter_list|)
block|{
name|this
argument_list|(
operator|new
name|IRI
argument_list|(
name|graphId
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Wraps the supplied<code>graph</code> into a new input source. No origin will be set.      *       * @param graph      *            the RDF graph      * @throws IllegalArgumentException      *             if<code>graph</code> is neither a {@link ImmutableGraph} nor a {@link Graph}.      */
specifier|public
name|GraphSource
parameter_list|(
name|Graph
name|graph
parameter_list|)
block|{
if|if
condition|(
name|graph
operator|instanceof
name|ImmutableGraph
condition|)
name|bindRootOntology
argument_list|(
name|graph
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|graph
operator|instanceof
name|Graph
condition|)
name|bindRootOntology
argument_list|(
operator|(
operator|(
name|Graph
operator|)
name|graph
operator|)
operator|.
name|getImmutableGraph
argument_list|()
argument_list|)
expr_stmt|;
else|else
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"GraphSource supports only ImmutableGraph and Graph types. "
operator|+
name|graph
operator|.
name|getClass
argument_list|()
operator|+
literal|" is not supported."
argument_list|)
throw|;
name|bindPhysicalOrigin
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new input source by querying the default triple collection manager for a graph named with the      * supplied<code>graphId</code>. The supplied ID will also be set as the graph origin.      *       * @param graphId      *            the graph ID.      * @throws NullPointerException      *             if there is no default triple collection manager available.      * @throws org.apache.clerezza.rdf.core.access.NoSuchEntityException      *             if no such graph can be found.      */
specifier|public
name|GraphSource
parameter_list|(
name|IRI
name|graphId
parameter_list|)
block|{
name|this
argument_list|(
name|graphId
argument_list|,
name|TcManager
operator|.
name|getInstance
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new input source by querying the supplied triple collection provider for a graph named with      * the supplied<code>graphId</code>. The supplied ID will also be set as the graph origin.      *       * @param graphId      *            the graph ID.      * @throws NullPointerException      *             if<code>tcProvider</code> is null.      * @throws org.apache.clerezza.rdf.core.access.NoSuchEntityException      *             if no such graph can be found in<code>tcProvider</code>.      */
specifier|public
name|GraphSource
parameter_list|(
name|IRI
name|graphId
parameter_list|,
name|TcProvider
name|tcProvider
parameter_list|)
block|{
name|this
argument_list|(
name|tcProvider
operator|.
name|getGraph
argument_list|(
name|graphId
argument_list|)
argument_list|)
expr_stmt|;
name|bindPhysicalOrigin
argument_list|(
name|Origin
operator|.
name|create
argument_list|(
name|graphId
argument_list|)
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
literal|"GRAPH<"
operator|+
name|rootOntology
operator|.
name|getClass
argument_list|()
operator|+
literal|","
operator|+
name|getOrigin
argument_list|()
operator|+
literal|">"
return|;
block|}
block|}
end_class

end_unit

