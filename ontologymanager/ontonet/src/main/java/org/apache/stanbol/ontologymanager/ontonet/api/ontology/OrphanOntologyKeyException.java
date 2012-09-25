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
name|OWLOntologyID
import|;
end_import

begin_comment
comment|/**  * Thrown when no match is found between a public key of an ontology that was supposed to be stored and the  * stored graphs. This public key is then called<i>orphan</i>.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|OrphanOntologyKeyException
extends|extends
name|RuntimeException
block|{
comment|/**      *       */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|599697367795290663L
decl_stmt|;
specifier|private
name|OWLOntologyID
name|orphan
decl_stmt|;
comment|/**      * Creates a new instance of {@link OrphanOntologyKeyException} with the supplied orphan key.      *       * @param key      *            the orphan ontology key.      */
specifier|public
name|OrphanOntologyKeyException
parameter_list|(
name|OWLOntologyID
name|key
parameter_list|)
block|{
name|this
operator|.
name|orphan
operator|=
name|key
expr_stmt|;
block|}
comment|/**      * Returns the public key of the ontology which was supposed to exist in the triple store but is not.      *       * @return the orphan ontology key.      */
specifier|public
name|OWLOntologyID
name|getOrphanPublicKey
parameter_list|()
block|{
return|return
name|orphan
return|;
block|}
block|}
end_class

end_unit

