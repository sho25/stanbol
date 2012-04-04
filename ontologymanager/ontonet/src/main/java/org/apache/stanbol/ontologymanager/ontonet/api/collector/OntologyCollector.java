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
name|collector
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
name|NamedResource
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
name|OntologyInputSourceHandler
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
comment|/**  * It is not literally an ontology<i>collection</i>, in that it only collects references to ontologies, not  * the ontologies themselves. Unless implementations specify a different behaviour, removing ontologies from  * the collector does not delete them from their persistence system.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|OntologyCollector
extends|extends
name|NamedResource
extends|,
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
comment|/**      * Adds the given ontology to the ontology space. If the supplied ontology is not already present in      * storage and does not have an OWL version IRI of its own, this ontology collector will 'claim ownership'      * of the ontology by setting its own logical ID as the version IRI of the new ontology.      *       * @param ontology      *            the ontology to be added      * @return the key that can be used for accessing the stored ontology directly      */
name|String
name|addOntology
parameter_list|(
name|OntologyInputSource
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|ontologySource
parameter_list|)
function_decl|;
comment|/**      * Removes all ontology space listeners registered with this space.      */
name|void
name|clearListeners
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
comment|/**      * Returns the ontologies managed by this ontology space. This is a shortcut method for iterating      * {@link #getOntology(IRI, Class)} calls over {@link #listManagedOntologies()}.      *       * @param withClosure      *            if true, also the ontologies imported by those directly managed by this space will be      *            included.      * @return the set of ontologies in the ontology space      */
parameter_list|<
name|O
parameter_list|>
name|Set
argument_list|<
name|O
argument_list|>
name|getManagedOntologies
parameter_list|(
name|Class
argument_list|<
name|O
argument_list|>
name|returnType
parameter_list|,
name|boolean
name|withClosure
parameter_list|)
function_decl|;
comment|/**      * TODO replace with Ontology IDs      *       * @return      */
name|Set
argument_list|<
name|IRI
argument_list|>
name|listManagedOntologies
parameter_list|()
function_decl|;
parameter_list|<
name|O
parameter_list|>
name|O
name|getOntology
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|,
name|Class
argument_list|<
name|O
argument_list|>
name|returnType
parameter_list|)
function_decl|;
comment|/**      * TODO replace merge parameter with integer for merge level (-1 for infinite).      *       * @param ontologyIri      * @param returnType      * @param merge      * @return      */
parameter_list|<
name|O
parameter_list|>
name|O
name|getOntology
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|,
name|Class
argument_list|<
name|O
argument_list|>
name|returnType
parameter_list|,
name|boolean
name|merge
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
function_decl|;
comment|/**      * Bootstraps the ontology space. In some cases (such as with core and custom spaces) this also implies      * write-locking its ontologies.      *       * XXX make it a protected, non-interface method ?      */
name|void
name|setUp
parameter_list|()
function_decl|;
comment|/**      * Performs all required operations for disposing of an ontology space and releasing its resources (e.g.      * removing the writelock).      *       * XXX make it a protected, non-interface method ?      */
name|void
name|tearDown
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

