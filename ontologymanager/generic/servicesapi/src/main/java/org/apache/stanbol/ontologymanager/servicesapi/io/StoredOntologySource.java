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
name|io
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
comment|/**  * An reference source for an ontology already stored by Stanbol with the given public key. Handlers are free  * to decide whether to obtain an ontology object from it , or simply pass the reference along.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|StoredOntologySource
extends|extends
name|AbstractOntologyReferenceSource
block|{
comment|/**      * Creates a new instance of {@link StoredOntologySource}.      *       * @param publicKey      *            the public key of the stored ontology.      */
specifier|public
name|StoredOntologySource
parameter_list|(
name|OWLOntologyID
name|publicKey
parameter_list|)
block|{
name|super
argument_list|(
name|Origin
operator|.
name|create
argument_list|(
name|publicKey
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"PUBLIC-KEY"
operator|+
name|origin
operator|.
name|getReference
argument_list|()
return|;
block|}
block|}
end_class

end_unit

