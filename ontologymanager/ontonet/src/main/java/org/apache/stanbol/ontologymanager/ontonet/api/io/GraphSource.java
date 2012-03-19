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
name|TcManager
import|;
end_import

begin_comment
comment|/**  * An {@link OntologyInputSource} that gets ontologies from either a Clerezza {@link Graph} (or {@link MGraph}  * ), or its identifier and an optionally supplied triple collection manager.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|GraphSource
extends|extends
name|AbstractClerezzaGraphInputSource
block|{
specifier|public
name|GraphSource
parameter_list|(
name|TripleCollection
name|graph
parameter_list|)
block|{
if|if
condition|(
name|graph
operator|instanceof
name|Graph
condition|)
name|bindRootOntology
argument_list|(
operator|(
name|Graph
operator|)
name|graph
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|graph
operator|instanceof
name|MGraph
condition|)
name|bindRootOntology
argument_list|(
operator|(
operator|(
name|MGraph
operator|)
name|graph
operator|)
operator|.
name|getGraph
argument_list|()
argument_list|)
expr_stmt|;
else|else
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"GraphSource supports only Graph and MGraph types. "
operator|+
name|graph
operator|.
name|getClass
argument_list|()
operator|+
literal|" is not supported."
argument_list|)
throw|;
name|bindPhysicalIri
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|GraphSource
parameter_list|(
name|UriRef
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
comment|/**      * This constructor can be used to hijack ontologies using a physical IRI other than their default one.      *       * @param rootOntology      * @param phyicalIRI      */
specifier|public
name|GraphSource
parameter_list|(
name|UriRef
name|graphId
parameter_list|,
name|TcManager
name|tcManager
parameter_list|)
block|{
name|this
argument_list|(
name|tcManager
operator|.
name|getTriples
argument_list|(
name|graphId
argument_list|)
argument_list|)
expr_stmt|;
name|bindTriplesProvider
argument_list|(
name|tcManager
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
operator|+
literal|">"
return|;
block|}
block|}
end_class

end_unit

