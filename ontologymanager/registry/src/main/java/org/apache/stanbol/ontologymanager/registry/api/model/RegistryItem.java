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
name|registry
operator|.
name|api
operator|.
name|model
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
name|registry
operator|.
name|api
operator|.
name|RegistryContentException
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
name|registry
operator|.
name|api
operator|.
name|RegistryContentListener
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
comment|/**  * A member of an ontology registry, possibly even the registry itself.  */
end_comment

begin_interface
specifier|public
interface|interface
name|RegistryItem
block|{
comment|/**      * The allowed types of registry item that a registry manager can handle.      */
specifier|public
enum|enum
name|Type
block|{
comment|/**          * An ontology library. Contains ontologies, is contained by ontology registries.          */
name|LIBRARY
block|,
comment|/**          * An ontology. Contained by libraries.          */
name|ONTOLOGY
block|,
comment|/**          * An ontology registry. Contains ontology libraries.          */
name|REGISTRY
block|;     }
comment|/**      * Sets a registry item as a child of this registry item. Also sets itself as a parent of the supplied      * item. Note that cycles are not allowed.      *       * @param child      *            the registry item to be added as a child.      * @throws RegistryContentException      *             if a cycle is detected or one of the registry items is invalid.      */
name|void
name|addChild
parameter_list|(
name|RegistryItem
name|child
parameter_list|)
throws|throws
name|RegistryContentException
function_decl|;
comment|/**      * Sets a registry item as a parent of this registry item. Also sets itself as a child of the supplied      * item. Note that cycles are not allowed.      *       * @param parent      *            the registry item to be added as a parent.      * @throws RegistryContentException      *             if a cycle is detected or one of the registry items is invalid.      */
name|void
name|addParent
parameter_list|(
name|RegistryItem
name|parent
parameter_list|)
throws|throws
name|RegistryContentException
function_decl|;
comment|/**      * Adds the supplied listener to the set of registry content listeners. If the listener is already      * registered with this registry item, this method has no effect.      *       * @param listener      *            the listener to be added.      */
name|void
name|addRegistryContentListener
parameter_list|(
name|RegistryContentListener
name|listener
parameter_list|)
function_decl|;
comment|/**      * Removes all children from this item. Also remove this item from the parents of all its former children.      */
name|void
name|clearChildren
parameter_list|()
function_decl|;
comment|/**      * Removes all parents from this item. Also remove this item from the children of all its former parents.      */
name|void
name|clearParents
parameter_list|()
function_decl|;
comment|/**      * Clears the set of registry content listeners.      */
name|void
name|clearRegistryContentListeners
parameter_list|()
function_decl|;
comment|/**      * Returns the child items of this registry item that has the supplied it if present. Note that this      * method will return null if an item with this id does not exist, or exists but is not registered as a      * child of this item, even if it is registered as its parent.      *       * @return the child item, or null if not present or not a child.      */
name|RegistryItem
name|getChild
parameter_list|(
name|IRI
name|id
parameter_list|)
function_decl|;
comment|/**      * Returns the child items of this registry item if present.      *       * @return the child items, or an empty array if there are none.      */
name|RegistryItem
index|[]
name|getChildren
parameter_list|()
function_decl|;
comment|/**      * Returns the unique identifier of this registry item. In some cases, such as for ontologies, this also      * denotes their physical locations.      *       * @return the identifier of this registry item.      */
name|IRI
name|getIRI
parameter_list|()
function_decl|;
comment|/**      * Returns the short name of this registry item. It may or may not be a suffix of its ID.      *       * @return the short name of this registry item.      */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * Returns the parent items of this registry item that has the supplied it if present. Note that this      * method will return null if an item with this id does not exist, or exists but is not registered as a      * parent of this item, even if it is registered as its child.      *       * @return the parent item, or null if not present or not a parent.      */
name|RegistryItem
name|getParent
parameter_list|(
name|IRI
name|id
parameter_list|)
function_decl|;
comment|/**      * Returns the parent items of this registry item if present.      *       * @return the parent items, or an empty array if there are none.      */
name|RegistryItem
index|[]
name|getParents
parameter_list|()
function_decl|;
comment|/**      * Clears the set of objects to be notified any changes to this registry item.      *       * @return the set of registry content listeners registered with this item.      */
name|Set
argument_list|<
name|RegistryContentListener
argument_list|>
name|getRegistryContentListeners
parameter_list|()
function_decl|;
comment|/**      * Returns the type of this registry item.      *       * @return the type of this registry item.      */
name|Type
name|getType
parameter_list|()
function_decl|;
comment|/**      * Determines if this registry item has any child items. It is a shortcut to {@link #getChildren()}      *<code>.isEmpty()</code>.      *       * @return true if this registry item has children, false otherwise.      */
name|boolean
name|hasChildren
parameter_list|()
function_decl|;
comment|/**      * Determines if this registry item has any parent items. It is a shortcut to {@link #getParents()}      *<code>.isEmpty()</code>.      *       * @return true if this registry item has parents, false otherwise.      */
name|boolean
name|hasParents
parameter_list|()
function_decl|;
comment|/**      * Releases all the parent and child references of this item. If no objects other than the former parents      * and children are referencing it, this object is left stranded for garbage collection.      */
name|void
name|prune
parameter_list|()
function_decl|;
comment|/**      * Removes a registry item from the children of this registry item. Also removes itself from the parents      * of the supplied item. Note that cycles will result in no effect.      *       * @param child      *            the child registry item to be removed.      */
name|void
name|removeChild
parameter_list|(
name|RegistryItem
name|child
parameter_list|)
function_decl|;
comment|/**      * Removes a registry item from the parents of this registry item. Also removes itself from the children      * of the supplied item. Note that cycles will result in no effect.      *       * @param parent      *            the parent registry item to be removed.      */
name|void
name|removeParent
parameter_list|(
name|RegistryItem
name|parent
parameter_list|)
function_decl|;
comment|/**      * Removes the supplied listener from the set of registry content listeners. If the listener was not      * previously registered with this registry item, this method has no effect.      *       * @param listener      *            the listener to be removed.      */
name|void
name|removeRegistryContentListener
parameter_list|(
name|RegistryContentListener
name|listener
parameter_list|)
function_decl|;
comment|/**      * Sets the name of this registry item.      *       * @param name      *            the name of this registry item.      */
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

