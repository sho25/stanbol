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
name|collector
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
name|OWLOntologyID
import|;
end_import

begin_comment
comment|/**  * Objects that react to the addition or removal of ontologies to or from an ontology collector will implement  * this interface.<br>  *<br>  * TODO add "before" methods and provide a way to abort the corresponding operation.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|OntologyCollectorListener
block|{
comment|/**      * Fired<i>after</i> an ontology was successfully added to an ontology collector.      *       * @param collectorId      *            the ontology collector identifier.      * @param addedOntology      *            the added ontology identifier.      */
name|void
name|onOntologyAdded
parameter_list|(
name|OntologyCollector
name|collector
parameter_list|,
name|OWLOntologyID
name|addedOntology
parameter_list|)
function_decl|;
comment|/**      * Fired<i>after</i> an ontology was successfully removed from an ontology collector.      *       * @param collectorId      *            the ontology collector identifier.      * @param removedOntology      *            the removed ontology identifier.      */
name|void
name|onOntologyRemoved
parameter_list|(
name|OntologyCollector
name|collector
parameter_list|,
name|OWLOntologyID
name|removedOntology
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

