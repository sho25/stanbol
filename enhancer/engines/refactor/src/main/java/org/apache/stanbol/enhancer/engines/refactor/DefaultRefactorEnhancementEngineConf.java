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
name|enhancer
operator|.
name|engines
operator|.
name|refactor
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Dictionary
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
comment|/**  * Default configuration of the Refactor Enhancement Engine.<br/>  * The default configuration load an instance of Refactor Engine which maps named-entities recognized by  * other Enhancement Engines to Schema.org.  *    * @author anuzzolese  * @author alberto.musetti  *  */
end_comment

begin_class
specifier|public
class|class
name|DefaultRefactorEnhancementEngineConf
implements|implements
name|RefactorEnhancementEngineConf
block|{
specifier|private
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|conf
decl_stmt|;
comment|/* 	public DefaultRefactorEnhancementEngineConf() { 		 	} 	*/
specifier|public
name|DefaultRefactorEnhancementEngineConf
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|map
parameter_list|)
block|{
name|this
operator|.
name|conf
operator|=
name|map
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getScope
parameter_list|()
block|{
return|return
operator|(
name|String
operator|)
name|conf
operator|.
name|get
argument_list|(
name|SCOPE
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setScope
parameter_list|(
name|String
name|scopeId
parameter_list|)
block|{
name|conf
operator|.
name|put
argument_list|(
name|SCOPE
argument_list|,
name|scopeId
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getRecipeLocation
parameter_list|()
block|{
return|return
operator|(
name|String
operator|)
name|conf
operator|.
name|get
argument_list|(
name|RECIPE_LOCATION
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setRecipeLocation
parameter_list|(
name|String
name|recipeLocation
parameter_list|)
block|{
name|conf
operator|.
name|put
argument_list|(
name|RECIPE_LOCATION
argument_list|,
name|recipeLocation
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getRecipeId
parameter_list|()
block|{
return|return
operator|(
name|String
operator|)
name|conf
operator|.
name|get
argument_list|(
name|RECIPE_ID
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setRecipeId
parameter_list|(
name|String
name|recipeId
parameter_list|)
block|{
name|conf
operator|.
name|put
argument_list|(
name|RECIPE_ID
argument_list|,
name|recipeId
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
index|[]
name|getScopeCoreOntology
parameter_list|()
block|{
return|return
operator|(
name|String
index|[]
operator|)
name|conf
operator|.
name|get
argument_list|(
name|SCOPE_CORE_ONTOLOGY
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setScopeCoreOntology
parameter_list|(
name|String
index|[]
name|coreOntologyURI
parameter_list|)
block|{
name|conf
operator|.
name|put
argument_list|(
name|SCOPE_CORE_ONTOLOGY
argument_list|,
name|coreOntologyURI
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isInGraphAppendMode
parameter_list|()
block|{
return|return
operator|(
operator|(
name|Boolean
operator|)
name|conf
operator|.
name|get
argument_list|(
name|APPEND_OTHER_ENHANCEMENT_GRAPHS
argument_list|)
operator|)
operator|.
name|booleanValue
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setInGraphAppendMode
parameter_list|(
name|boolean
name|b
parameter_list|)
block|{
name|conf
operator|.
name|put
argument_list|(
name|APPEND_OTHER_ENHANCEMENT_GRAPHS
argument_list|,
name|b
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isEntityHubUsed
parameter_list|()
block|{
return|return
operator|(
operator|(
name|Boolean
operator|)
name|conf
operator|.
name|get
argument_list|(
name|USE_ENTITY_HUB
argument_list|)
operator|)
operator|.
name|booleanValue
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setEntityHubUsed
parameter_list|(
name|boolean
name|b
parameter_list|)
block|{
name|conf
operator|.
name|put
argument_list|(
name|USE_ENTITY_HUB
argument_list|,
name|b
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

