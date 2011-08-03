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
name|ontology
package|;
end_package

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
name|io
operator|.
name|OntologyInputSource
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

begin_comment
comment|/**  * An ontology space factory is responsible for the creation of new, readily specialized ontology spaces from  * supplied ontology input sources.  *   * Implementations should not call the setup method of the ontology space once it is created, so that it is  * not locked from editing since creation time.  */
end_comment

begin_interface
specifier|public
interface|interface
name|OntologySpaceFactory
block|{
comment|/**      * Creates and sets up a default core ontology space. Equivalent to calling      *<code>createOntologySpace(IRI, SpaceTypes.CORE, OntologyInputSource...)</code>.      *       * @param scopeId      *            the unique identifier of the ontology scope that will reference this space. It can be used      *            for generating the identifier for this ontology space.      * @param coreSources      *            the sources of the optional ontologies to be immediately loaded upon space creation.      * @return the generated ontology space.      */
name|CoreOntologySpace
name|createCoreOntologySpace
parameter_list|(
name|IRI
name|scopeId
parameter_list|,
name|OntologyInputSource
modifier|...
name|coreSources
parameter_list|)
function_decl|;
comment|/**      * Creates and sets up a default custom ontology space. Equivalent to calling      *<code>createOntologySpace(IRI, SpaceTypes.CUSTOM, OntologyInputSource...)</code>.      *       *       * @param scopeId      *            the unique identifier of the ontology scope that will reference this space. It can be used      *            for generating the identifier for this ontology space.      * @param customSources      *            the sources of the optional ontologies to be immediately loaded upon space creation.      * @return the generated ontology space.      */
name|CustomOntologySpace
name|createCustomOntologySpace
parameter_list|(
name|IRI
name|scopeId
parameter_list|,
name|OntologyInputSource
modifier|...
name|customSources
parameter_list|)
function_decl|;
comment|/**      * Creates an ontology space of the specified type.      *       * @param scopeId      *            the unique identifier of the ontology scope that will reference this space. It can be used      *            for generating the identifier for this ontology space.      * @param type      *            the space type.      * @param ontologySources      *            the sources of the optional ontologies to be immediately loaded upon space creation.      * @return the generated ontology space.      */
name|OntologySpace
name|createOntologySpace
parameter_list|(
name|IRI
name|scopeId
parameter_list|,
name|SpaceType
name|type
parameter_list|,
name|OntologyInputSource
modifier|...
name|ontologySources
parameter_list|)
function_decl|;
comment|/**      * Creates and sets up a default session ontology space. Equivalent to calling      *<code>createOntologySpace(IRI, SpaceTypes.SESSION, OntologyInputSource...)</code>.      *       * @param scopeId      *            the unique identifier of the ontology scope that will reference this space. It can be used      *            for generating the identifier for this ontology space.      * @param sessionSources      *            the sources of the optional ontologies to be immediately loaded upon space creation.      * @return the generated ontology space.      */
name|SessionOntologySpace
name|createSessionOntologySpace
parameter_list|(
name|IRI
name|scopeId
parameter_list|,
name|OntologyInputSource
modifier|...
name|sessionSources
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

