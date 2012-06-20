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
name|semanticweb
operator|.
name|owlapi
operator|.
name|apibinding
operator|.
name|OWLManager
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
name|IRI
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
name|OWLOntologyCreationException
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
name|OWLOntologyManager
import|;
end_import

begin_comment
comment|/**  * An input source that provides the OWL Ontology loaded from the supplied physical IRI, as well as the  * physical IRI itself for consumers that need to load the ontology themselves. This means that in order to  * use the {@link RootOntologyIRISource},<i>one</i> the following requirements must be met:  *<ol>  *<li>Stanbol is in online mode and all imports can be resolved recursively;  *<li>Stanbol is in offline mode and the ontology has no recursive import statements.  *</ol>  * If Stanbol is in offline mode and it is known that all imported ontologies are in the same local directory  * as the root ontology, one should use a {@link ParentPathInputSource} instead.<br>  *<br>  * For convenience, an existing OWL ontology manager can be supplied for loading the ontology.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|RootOntologyIRISource
extends|extends
name|AbstractOWLOntologyInputSource
block|{
specifier|public
name|RootOntologyIRISource
parameter_list|(
name|IRI
name|rootOntologyIri
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
name|this
argument_list|(
name|rootOntologyIri
argument_list|,
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|RootOntologyIRISource
parameter_list|(
name|IRI
name|rootPhysicalIri
parameter_list|,
name|OWLOntologyManager
name|manager
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
name|this
argument_list|(
name|rootPhysicalIri
argument_list|,
name|manager
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|RootOntologyIRISource
parameter_list|(
name|IRI
name|rootPhysicalIri
parameter_list|,
name|OWLOntologyManager
name|manager
parameter_list|,
name|boolean
name|ignoreIriMappers
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
name|bindPhysicalIri
argument_list|(
name|rootPhysicalIri
argument_list|)
expr_stmt|;
name|bindRootOntology
argument_list|(
name|ignoreIriMappers
condition|?
name|manager
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|rootPhysicalIri
argument_list|)
else|:
name|manager
operator|.
name|loadOntology
argument_list|(
name|rootPhysicalIri
argument_list|)
argument_list|)
expr_stmt|;
name|bindTriplesProvider
argument_list|(
name|manager
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
literal|"ROOT_ONT_IRI<"
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

