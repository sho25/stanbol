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

begin_comment
comment|/**  * It is not literally an ontology<i>collection</i>, in that it only collects references to ontologies, not  * the ontologies themselves. Unless implementations specify a different behaviour, removing ontologies from  * the collector does not delete them from their persistence system.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|OntologyCollector
extends|extends
name|OntologyInputSourceHandler
block|{
comment|/**      * Registers a new listener for changes in this ontology space. Has no effect if the same listener is      * already registered with this ontology space.      *       * @param listener      *            the ontology space listener to be added.      */
name|void
name|addListener
parameter_list|(
name|OntologyCollectorListener
name|listener
parameter_list|)
function_decl|;
comment|/**      * Adds the given ontology to the ontology space.      *       * @param ontology      *            the ontology to be added      * @throws UnmodifiableOntologyCollectorException      *             if the ontology space is read-only      */
name|void
name|addOntology
parameter_list|(
name|OntologyInputSource
argument_list|<
name|?
argument_list|>
name|ontologySource
parameter_list|)
throws|throws
name|UnmodifiableOntologyCollectorException
function_decl|;
comment|/**      * Removes all ontology space listeners registered with this space.      */
name|void
name|clearListeners
parameter_list|()
function_decl|;
comment|/**      * Returns a Unique Resource Identifier (URI) that identifies this ontology space. For instance, this URI      * could be the parent of (some/most of) the base URIs for the ontologies within this space.<br/>      *<br/>      * A possible way to construct these IDs is by concatenating<code>/{spacetype}</code> (e.g.      *<code>/custom</code>) to the scope IRI. However, this is implementation-dependent.      *       * @return the URI that identifies this ontology space      */
name|String
name|getID
parameter_list|()
function_decl|;
comment|/**      * Returns all the listeners registered with this ontology space. Whether the collection reflects or not      * the order in which they were registered depends on the implementation.      *       * @return the registered ontology space listeners.      */
name|Collection
argument_list|<
name|OntologyCollectorListener
argument_list|>
name|getListeners
parameter_list|()
function_decl|;
comment|/**      * The namespace can change dynamically (e.g. if the ontology network is relocated), so it is not part of      * the scope identifier (although its concatenation with the identifier will still be unique).      *       * @param namespace      */
name|IRI
name|getNamespace
parameter_list|()
function_decl|;
comment|/**      * Returns the ontologies managed by this ontology space.      *       * @param withClosure      *            if true, also the ontologies imported by those directly managed by this space will be      *            included.      * @return the set of ontologies in the ontology space      */
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|(
name|boolean
name|withClosure
parameter_list|)
function_decl|;
comment|/**      * Returns the ontology identified by the supplied<i>logical</i> IRI, if such an ontology has been loaded      * in this space.<br>      *<br>      * Note that ontologies are not identified by physical IRI here. There's no need to ask KReS for      * ontologies by physical IRI, use a browser or some other program instead!      *       * @param ontologyIri      *            the<i>logical</i> identifier of the ontology to query for.      *       * @return the requested ontology, or null if no ontology with this ID has been loaded.      */
name|OWLOntology
name|getOntology
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|)
function_decl|;
comment|/**      * Determines if the ontology identified by the supplied<i>logical</i> IRI has been loaded in this space.<br>      *<br>      * Note that ontologies are not identified by physical IRI here. There's no need to ask KReS for      * ontologies by physical IRI, use a browser or some other program instead!      *       * @param ontologyIri      *            the<i>logical</i> identifier of the ontology to query for.      *       * @return true if an ontology with this ID has been loaded in this space.      */
name|boolean
name|hasOntology
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|)
function_decl|;
comment|/**      * Unregisters the supplied for changes in this ontology space. Has no effect if the same listener was not      * registered with this ontology space.      *       * @param listener      *            the ontology space listener to be removed.      */
name|void
name|removeListener
parameter_list|(
name|OntologyCollectorListener
name|listener
parameter_list|)
function_decl|;
comment|/**      * Removes the given ontology from the ontology space, if the ontology is a direct child of the top      * ontology.<br/>      *<u>Note</u> that this will NOT delete the ontology from the store! This method simply states that the      * ontology is no longer managed by this space and its axioms will no longer appear when the space is      * serialized as an ontology. To delete the ontology itself, please use the Ontology Manager Store.      *       * @param ontologyIri      *            the identifier of this ontology.      */
name|void
name|removeOntology
parameter_list|(
name|IRI
name|ontologyId
parameter_list|)
throws|throws
name|OntologyCollectorModificationException
function_decl|;
comment|/**      * The namespace can be changed dynamically (e.g. if the ontology network is relocated).      *       * @param namespace      *            Must end with a slash character. If the IRI ends with a has, and      *            {@link IllegalArgumentException} will be thrown. If it ends with neither, a slash will be      *            added.      */
name|void
name|setNamespace
parameter_list|(
name|IRI
name|namespace
parameter_list|)
function_decl|;
comment|/**      * Bootstraps the ontology space. In some cases (such as with core and custom spaces) this also implies      * write-locking its ontologies.      */
name|void
name|setUp
parameter_list|()
function_decl|;
comment|/**      * Performs all required operations for disposing of an ontology space and releasing its resources (e.g.      * removing the writelock).      */
name|void
name|tearDown
parameter_list|()
function_decl|;
block|}
end_interface

end_unit
