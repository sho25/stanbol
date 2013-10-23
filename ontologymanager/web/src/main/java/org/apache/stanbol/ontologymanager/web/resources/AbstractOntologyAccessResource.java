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
name|web
operator|.
name|resources
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|resource
operator|.
name|BaseStanbolResource
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
name|servicesapi
operator|.
name|util
operator|.
name|OntologyUtils
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
name|OWLOntologyID
import|;
end_import

begin_comment
comment|/**  * RESTful resources intended for performing CRUD operations on ontologies with respect to their storage  * facilities (i.e. operations that manipulate the content of one ontology at a time) should specialize this  * class.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|AbstractOntologyAccessResource
extends|extends
name|BaseStanbolResource
block|{
comment|/**      * The ontology this resource was created after, and represents.      */
specifier|protected
name|OWLOntologyID
name|submitted
decl_stmt|;
comment|/**      * Returns a public key of the ontology this resource was created after.      *       * @return the key of the ontology represented by this resource.      */
specifier|public
name|OWLOntologyID
name|getRepresentedOntologyKey
parameter_list|()
block|{
return|return
name|submitted
return|;
block|}
comment|/**      * Returns a canonicalized string form of a public key.      *       * @param ontologyID      *            the public key      * @return the canonical form of the submitted public key.      */
specifier|public
name|String
name|stringForm
parameter_list|(
name|OWLOntologyID
name|ontologyID
parameter_list|)
block|{
return|return
name|OntologyUtils
operator|.
name|encode
argument_list|(
name|ontologyID
argument_list|)
return|;
block|}
block|}
end_class

end_unit

