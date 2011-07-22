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
name|registry
operator|.
name|models
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
name|registry
operator|.
name|models
operator|.
name|RegistryItem
operator|.
name|Type
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
comment|/**  * An ontology registry can reference zero or more ontology libraries.  */
end_comment

begin_interface
specifier|public
interface|interface
name|Registry
extends|extends
name|RegistryItem
block|{
comment|/**      * The type of this registry item is {@link Type#REGISTRY}.      */
specifier|final
name|Type
name|type
init|=
name|Type
operator|.
name|REGISTRY
decl_stmt|;
comment|/**      * Returns the OWL ontology manager that this registry is using as a cache of its ontologies.      *       * @return the ontology manager that is used as a cache.      */
name|OWLOntologyManager
name|getCache
parameter_list|()
function_decl|;
comment|/**      * Sets the OWL ontology manager that this registry will use as a cache of its ontologies. If null, if      * will create its own.      *       * @param cache      *            the ontology manager to be used as a cache.      */
name|void
name|setCache
parameter_list|(
name|OWLOntologyManager
name|cache
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

