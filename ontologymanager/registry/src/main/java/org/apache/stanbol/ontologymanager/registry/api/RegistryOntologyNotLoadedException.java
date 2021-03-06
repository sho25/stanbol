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
name|RegistryItem
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

begin_comment
comment|/**  * Thrown whenever there is a request for the raw OWL version of a registry ontology which has not been loaded  * yet (e.g. due to lazy loading policies). Developers who catch this exception may, for example, decide to  * load the ontology.<br/>  *<br/>  * Note that this exception is independent from calls to  * {@link RegistryContentListener#registryContentRequested(RegistryItem)}, although it can be expected to be  * thrown thereafter.  *   * @author alexdma  */
end_comment

begin_class
specifier|public
class|class
name|RegistryOntologyNotLoadedException
extends|extends
name|RegistryContentException
block|{
comment|/**      *       */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|6336128674251128796L
decl_stmt|;
specifier|private
name|RegistryOntology
name|ontology
decl_stmt|;
comment|/**      * Creates a new instance of {@link RegistryOntologyNotLoadedException}.      *       * @param library      *            the ontology that caused the exception.      */
specifier|public
name|RegistryOntologyNotLoadedException
parameter_list|(
name|RegistryOntology
name|ontology
parameter_list|)
block|{
name|super
argument_list|(
name|ontology
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|ontology
operator|=
name|ontology
expr_stmt|;
block|}
comment|/**      * Returns the requested ontology that is not loaded yet.      *       * @return the ontology that caused the exception.      */
specifier|public
name|RegistryOntology
name|getOntology
parameter_list|()
block|{
return|return
name|ontology
return|;
block|}
block|}
end_class

end_unit

