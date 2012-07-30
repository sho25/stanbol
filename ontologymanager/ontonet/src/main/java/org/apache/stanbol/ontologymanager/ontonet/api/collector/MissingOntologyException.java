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
comment|/**  * Thrown whenever an attempt to modify an ontology within an ontology collector that does not contain it is  * detected.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|MissingOntologyException
extends|extends
name|OntologyCollectorModificationException
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|3449667155191079302L
decl_stmt|;
specifier|protected
name|OWLOntologyID
name|publicKey
decl_stmt|;
comment|/**      *       * @param collector      * @param ontologyId      */
specifier|public
name|MissingOntologyException
parameter_list|(
name|OntologyCollector
name|collector
parameter_list|,
name|OWLOntologyID
name|publicKey
parameter_list|)
block|{
name|super
argument_list|(
name|collector
argument_list|)
expr_stmt|;
name|this
operator|.
name|publicKey
operator|=
name|publicKey
expr_stmt|;
block|}
comment|/**      * Returns the unique identifier of the ontology whose removal was denied.      *       * @return the ID of the ontology that was not removed.      */
specifier|public
name|OWLOntologyID
name|getOntologyKey
parameter_list|()
block|{
return|return
name|publicKey
return|;
block|}
block|}
end_class

end_unit

