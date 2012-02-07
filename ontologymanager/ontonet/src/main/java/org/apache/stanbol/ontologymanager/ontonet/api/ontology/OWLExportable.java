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
comment|/**  * An object that can be represented as an {@link OWLOntology} instance.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|OWLExportable
block|{
comment|/**      * Returns the OWL ontology form of this object.      *       * @deprecated use the method {@link #export(Class, boolean)} instead, with the first argument set as      *             {@link OWLOntology.class}.      *       * @param merge      *            if true, all imported ontologies will be merged and no import statements will appear.      * @return the OWL ontology that represents this object.      *       */
name|OWLOntology
name|asOWLOntology
parameter_list|(
name|boolean
name|merge
parameter_list|)
function_decl|;
comment|/**      * Returns an ontological form of this object of the specified return type, if supported. If the supplied      * class is not a supported return type, an {@link UnsupportedOperationException} is thrown.<br>      *<br>      * TODO replace merge parameter with integer for merge level (-1 for infinite).      *       * @param returnType      *            the desired class of the returned object.      * @param merge      *            if true, all imported ontologies will be merged and no import statements will appear.      * @return the ontology that represents this object.      */
parameter_list|<
name|O
parameter_list|>
name|O
name|export
parameter_list|(
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
name|IRI
name|getDocumentIRI
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

