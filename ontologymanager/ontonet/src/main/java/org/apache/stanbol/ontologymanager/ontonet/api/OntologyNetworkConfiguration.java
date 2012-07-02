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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  * Contains ownership and collector information on all ontology networks currently configured.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|OntologyNetworkConfiguration
block|{
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|String
argument_list|>
argument_list|>
name|coreOntologiesForScopes
decl_stmt|,
name|customOntologiesForScopes
decl_stmt|,
name|ontologiesForSessions
decl_stmt|;
specifier|public
name|OntologyNetworkConfiguration
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|String
argument_list|>
argument_list|>
name|coreOntologiesForScopes
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|String
argument_list|>
argument_list|>
name|customOntologiesForScopes
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|String
argument_list|>
argument_list|>
name|ontologiesForSessions
parameter_list|)
block|{
name|this
operator|.
name|coreOntologiesForScopes
operator|=
name|coreOntologiesForScopes
expr_stmt|;
name|this
operator|.
name|customOntologiesForScopes
operator|=
name|customOntologiesForScopes
expr_stmt|;
name|this
operator|.
name|ontologiesForSessions
operator|=
name|ontologiesForSessions
expr_stmt|;
block|}
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|getCoreOntologyKeysForScope
parameter_list|(
name|String
name|scopeId
parameter_list|)
block|{
return|return
name|coreOntologiesForScopes
operator|.
name|get
argument_list|(
name|scopeId
argument_list|)
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|getCustomOntologyKeysForScope
parameter_list|(
name|String
name|scopeId
parameter_list|)
block|{
return|return
name|customOntologiesForScopes
operator|.
name|get
argument_list|(
name|scopeId
argument_list|)
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|getOntologyKeysForSession
parameter_list|(
name|String
name|sessionId
parameter_list|)
block|{
return|return
name|ontologiesForSessions
operator|.
name|get
argument_list|(
name|sessionId
argument_list|)
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|getScopeIDs
parameter_list|()
block|{
return|return
name|coreOntologiesForScopes
operator|.
name|keySet
argument_list|()
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|getSessionIDs
parameter_list|()
block|{
return|return
name|ontologiesForSessions
operator|.
name|keySet
argument_list|()
return|;
block|}
block|}
end_class

end_unit

