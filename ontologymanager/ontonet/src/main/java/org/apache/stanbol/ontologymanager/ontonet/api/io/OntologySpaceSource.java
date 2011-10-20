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
name|util
operator|.
name|Set
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
name|ontology
operator|.
name|OntologySpace
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
name|ontology
operator|.
name|UnmodifiableOntologyCollectorException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntology
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

begin_class
specifier|public
class|class
name|OntologySpaceSource
extends|extends
name|AbstractOWLOntologyInputSource
block|{
specifier|private
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
specifier|protected
name|OntologySpace
name|space
decl_stmt|;
specifier|public
name|OntologySpaceSource
parameter_list|(
name|OntologySpace
name|space
parameter_list|)
block|{
name|this
argument_list|(
name|space
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|OntologySpaceSource
parameter_list|(
name|OntologySpace
name|space
parameter_list|,
name|Set
argument_list|<
name|OntologyInputSource
argument_list|>
name|subtrees
parameter_list|)
block|{
name|this
operator|.
name|space
operator|=
name|space
expr_stmt|;
if|if
condition|(
name|subtrees
operator|!=
literal|null
condition|)
try|try
block|{
for|for
control|(
name|OntologyInputSource
name|st
range|:
name|subtrees
control|)
name|appendSubtree
argument_list|(
name|st
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologyCollectorException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Could not add subtrees to unmodifiable ontology space {}. Input source will have no additions."
argument_list|,
name|e
operator|.
name|getOntologyCollector
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|bindRootOntology
argument_list|(
name|space
operator|.
name|asOWLOntology
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|appendSubtree
parameter_list|(
name|OntologyInputSource
name|subtree
parameter_list|)
throws|throws
name|UnmodifiableOntologyCollectorException
block|{
name|space
operator|.
name|addOntology
argument_list|(
name|subtree
argument_list|)
expr_stmt|;
block|}
specifier|public
name|OntologySpace
name|asOntologySpace
parameter_list|()
block|{
return|return
name|space
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getImports
parameter_list|(
name|boolean
name|recursive
parameter_list|)
block|{
return|return
name|space
operator|.
name|getOntologies
argument_list|(
name|recursive
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"SCOPE_ONT_IRI<"
operator|+
name|getPhysicalIRI
argument_list|()
operator|+
literal|">"
return|;
block|}
block|}
end_class

end_unit
