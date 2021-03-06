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
name|servicesapi
operator|.
name|ontology
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
name|servicesapi
operator|.
name|collector
operator|.
name|OntologyCollector
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
name|collector
operator|.
name|OntologyCollectorListener
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
name|scope
operator|.
name|ScopeEventListener
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
name|session
operator|.
name|SessionListener
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
name|OWLOntologyID
import|;
end_import

begin_comment
comment|/**  * The object that "knows" the relationships between stored graphs and their usage in ontology spaces or  * sessions.  *   * @author alexdma.  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|Multiplexer
extends|extends
name|OntologyCollectorListener
extends|,
name|ScopeEventListener
extends|,
name|SessionListener
block|{
comment|/**      * Liberates the selected ontology from all its bindings with other ontologies, excluding ontology      * collectors. As a consequence, no import statements other than those pointing to ontology collectors, if      * any, should appear when exporting the ontology.      *       * @param dependent      *            the ontology to be cleared of dependencies.      */
name|void
name|clearDependencies
parameter_list|(
name|OWLOntologyID
name|dependent
parameter_list|)
function_decl|;
comment|/**      * Returns all the ontologies that the supplied ontology depends on, if any.      *       * @param dependent      *            the public key of the depending ontology.      * @return the set of dependencies (possibly empty).      */
name|Set
argument_list|<
name|OWLOntologyID
argument_list|>
name|getDependencies
parameter_list|(
name|OWLOntologyID
name|dependent
parameter_list|)
function_decl|;
comment|/**      * Returns all the ontologies that depend on the supplied ontology, if any.      *       * @param dependency      *            the public key of the ontology that other ontologies could depend on.      * @return the set of ontologies it depends on (possibly empty).      */
name|Set
argument_list|<
name|OWLOntologyID
argument_list|>
name|getDependents
parameter_list|(
name|OWLOntologyID
name|dependency
parameter_list|)
function_decl|;
comment|/**      * Not just the IDs because spaces and sessions can share identifiers.      *       * @param publicKey      * @return      */
name|Set
argument_list|<
name|OntologyCollector
argument_list|>
name|getHandles
parameter_list|(
name|OWLOntologyID
name|publicKey
parameter_list|)
function_decl|;
comment|/**      * An utility method that computes the public key of an ontology given a canonical string form. The result      * also depends on the stored ontologies, hence the inclusion of this non-static method with this class.      *       * @param stringForm      *            the string form of the public key.      * @return the public key.      */
name|OWLOntologyID
name|getPublicKey
parameter_list|(
name|String
name|stringForm
parameter_list|)
function_decl|;
comment|/**      * Returns the public keys of all the stored ontologies it is aware of.      *       * @return the public key set.      */
name|Set
argument_list|<
name|OWLOntologyID
argument_list|>
name|getPublicKeys
parameter_list|()
function_decl|;
comment|/**      * Returns the size in triples of the ontology with the supplied public key. Depending on the      * implementation, the size can be cached or computed on-the-fly.      *       * @param publicKey      *            the public key of the ontology      * @return the size in triples of the ontology.      */
name|int
name|getSize
parameter_list|(
name|OWLOntologyID
name|publicKey
parameter_list|)
function_decl|;
comment|/**      * Removes any claim that stored ontology<code>dependent</code> has a dependency on stored ontology      *<code>dependency</code>. As a consquence, no related import statement should figure in the serialized      *<code>dependant</code>. Note that this operation can be overridden by a call to      * {@link #setDependency(OWLOntologyID, OWLOntologyID)}.      *       * @param dependent      *            the depending ontology.      * @param dependency      *            the ontology it depends on.      */
name|void
name|removeDependency
parameter_list|(
name|OWLOntologyID
name|dependent
parameter_list|,
name|OWLOntologyID
name|dependency
parameter_list|)
function_decl|;
comment|/**      * States that stored ontology<code>dependant</code> has a dependency on stored ontology      *<code>dependency</code>, e.g. because the former imports the latter. As a consequence, various      * restrictions could apply, e.g.<code>dependency</code> cannot be deleted as long as it is a dependency.      *       * @param dependent      *            the depending ontology.      * @param dependency      *            the ontology it depends on.      */
name|void
name|setDependency
parameter_list|(
name|OWLOntologyID
name|dependent
parameter_list|,
name|OWLOntologyID
name|dependency
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

