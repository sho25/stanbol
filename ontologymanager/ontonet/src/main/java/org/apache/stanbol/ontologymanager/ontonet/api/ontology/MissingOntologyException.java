begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
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

begin_comment
comment|/**  * Thrown whenever an attempt to modify an ontology within an ontology space  * that does not contain it is detected.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|MissingOntologyException
extends|extends
name|OntologyCollectorModificationException
block|{
specifier|public
name|MissingOntologyException
parameter_list|(
name|OntologySpace
name|space
parameter_list|,
name|IRI
name|ontologyId
parameter_list|)
block|{
name|super
argument_list|(
name|space
argument_list|)
expr_stmt|;
name|this
operator|.
name|ontologyId
operator|=
name|ontologyId
expr_stmt|;
block|}
comment|/** 	 * Returns the unique identifier of the ontology whose removal was denied. 	 *  	 * @return the ID of the ontology that was not removed. 	 */
specifier|public
name|IRI
name|getOntologyId
parameter_list|()
block|{
return|return
name|ontologyId
return|;
block|}
specifier|protected
name|IRI
name|ontologyId
decl_stmt|;
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
block|}
end_class

end_unit

