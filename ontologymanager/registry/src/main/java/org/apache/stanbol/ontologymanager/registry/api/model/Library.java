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
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|OntologyProvider
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
comment|/**  * An ontology library references one or more ontologies.  *   * @author alexdma  */
end_comment

begin_interface
specifier|public
interface|interface
name|Library
extends|extends
name|RegistryItem
block|{
comment|/**      * The type of this registry item is {@link Type#LIBRARY}.      */
specifier|final
name|Type
name|type
init|=
name|Type
operator|.
name|LIBRARY
decl_stmt|;
comment|/**      * Returns the OWL ontology manager that this library is using as a cache of its ontologies.      *       * @return the ontology manager that is used as a cache.      */
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|getCache
parameter_list|()
function_decl|;
comment|/**      * Returns the OWL ontologies that have been loaded in this library, if any, otherwise an exception is      * thrown.<br/>      *<br/>      * Upon invocation, this method immediately fires a registry content request event on itself. Note,      * however, that this method is in general not synchronized. Therefore, any listeners that react by      * invoking a load method may or may not cause the content to be available to this method before it      * returns.      *       * @return the set of loaded OWL ontologies.      * @throws RegistryContentException      *             if the requested ontologies have not been loaded.      */
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|()
throws|throws
name|RegistryContentException
function_decl|;
name|OWLOntology
name|getOntology
parameter_list|(
name|IRI
name|id
parameter_list|)
throws|throws
name|RegistryContentException
function_decl|;
comment|/**      * Returns a string that can be encoded as a IRI/URI/UriRef etc. for retrieving the raw ontology.      *       * @param ontologyId      * @return      */
name|String
name|getOntologyReference
parameter_list|(
name|IRI
name|ontologyId
parameter_list|)
throws|throws
name|RegistryContentException
function_decl|;
comment|/**      * Determines if the contents of this library have been loaded and are up-to-date.      *       * @return true if the contents are loaded and up-to-date, false otherwise.      */
name|boolean
name|isLoaded
parameter_list|()
function_decl|;
comment|/**      * Causes all the ontologies referenced by this library to be loaded, so that when      * {@link RegistryOntology#getRawOntology(IRI)} is invoked on one of its children, it will return the      * corresponding OWL ontology, if a valid one was parsed from its location.      *       * @param mgr      *            the OWL ontology manager to use for loading the ontologies in the library. It must not be      *            null, lest an {@link IllegalArgumentException} be thrown.      */
name|void
name|loadOntologies
parameter_list|(
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|cache
parameter_list|)
function_decl|;
comment|/**      * Sets the OWL ontology manager that this library will use as a cache of its ontologies. If null, if will      * create its own.      *       * @param cache      *            the ontology manager to be used as a cache.      */
name|void
name|setCache
parameter_list|(
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|cache
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

