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
name|model
operator|.
name|Library
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
name|model
operator|.
name|Registry
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
name|model
operator|.
name|RegistryOntology
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
name|OWLNamedObject
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
comment|/**  * A factory that creates the basic elements of the ontology registry metamodel from OWL objects that are  * required not to be anonymous.<br/>  *<br/>  *<b>Note that implementations should not be aggressive</b>, in that they should<b>not</b> recursively  * create and/or append the parents and children of any generated object. Refer to  * {@link RegistryManager#createModel(Set)} to recursively populate a registry item starting from a set of  * registries.  *   * @author alexdma  */
end_comment

begin_interface
specifier|public
interface|interface
name|RegistryItemFactory
block|{
comment|/**      * Creates a new {@link Library} object named after the ID of the supplied individual.      *       * @param ind      *            the named individual to extract the library model from.      * @return the library model.      */
name|Library
name|createLibrary
parameter_list|(
name|OWLNamedObject
name|ind
parameter_list|)
function_decl|;
comment|/**      * Creates a new {@link Registry} object named after the ID of the supplied ontology.      *       * @param o      *            the ontology to extract the registry model from. Should be a named ontology, lest the method      *            return null.      * @return the registry model, or null if<code>o</code> is anonymous.      */
name|Registry
name|createRegistry
parameter_list|(
name|OWLOntology
name|o
parameter_list|)
function_decl|;
comment|/**      * Creates a new {@link RegistryOntology} object named after the ID of the supplied individual.      *       * @param ind      *            the named individual to extract the ontology model from.      * @return the ontology model.      */
name|RegistryOntology
name|createRegistryOntology
parameter_list|(
name|OWLNamedObject
name|ind
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

