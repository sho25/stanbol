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
name|OWLOntology
import|;
end_import

begin_comment
comment|/**  * An ontology space identifies the set of OWL ontologies that should be "active" in a given context, e.g. for  * a certain user session or a specific reasoning service. Each ontology space has an ID and a top ontology  * that can be used as a shared resource for mutual exclusion and locking strategies.  */
end_comment

begin_interface
specifier|public
interface|interface
name|OntologySpace
extends|extends
name|OntologyCollector
extends|,
name|OWLExportable
extends|,
name|Lockable
block|{
comment|/**      * Returns the ontology that serves as a root module for this ontology space.      *       * @deprecated Please use the inherited method {@link OWLExportable#asOWLOntology(boolean)}. Calls to the      *             current method are equivalent to asOWLOntology(false).      * @return the OWL form of this ontology space.      */
name|OWLOntology
name|asOWLOntology
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

