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
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_comment
comment|/**  *  * @author anuzzolese  * @author alberto.musetti  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RefactorEnhancementEngineConf
block|{
comment|/**      * The OntoNet scope that the engine should use.      */
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"schema.org"
argument_list|)
name|String
name|SCOPE
init|=
literal|"engine.refactor.scope"
decl_stmt|;
comment|/**      * The location from which the recipe is loaded.      */
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|""
argument_list|)
name|String
name|RECIPE_LOCATION
init|=
literal|"engine.refactor.recipe"
decl_stmt|;
comment|/**      * The ID used for identifying the recipe in the RuleStore.      */
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|""
argument_list|)
name|String
name|RECIPE_ID
init|=
literal|"engine.refactor.recipe.id"
decl_stmt|;
comment|/**      * The set of ontology URIs that should be loaded in the core space of the scope.      */
annotation|@
name|Property
argument_list|(
name|value
operator|=
block|{
literal|"http://ontologydesignpatterns.org/ont/iks/kres/dbpedia_demo.owl"
block|,
literal|""
block|}
argument_list|)
name|String
name|SCOPE_CORE_ONTOLOGY
init|=
literal|"engine.refactor.scope.core.ontology"
decl_stmt|;
comment|/**      * If true: the previously generated RDF is deleted and substituted with the new one.       * If false: the new one is appended to the old RDF.       * Possible value in the configuration: true or false.      */
annotation|@
name|Property
argument_list|(
name|boolValue
operator|=
literal|true
argument_list|,
name|description
operator|=
literal|"If true: the previously generated RDF is deleted and substituted with the new one. If false: the new one is appended to the old RDF. Possible value: true or false."
argument_list|)
name|String
name|APPEND_OTHER_ENHANCEMENT_GRAPHS
init|=
literal|"engine.refactor.append.graphs"
decl_stmt|;
comment|/**      * If true: entities are fetched via the EntityHub.       * If false: entities are fetched on-line.       * Possible value in the configuration: true or false.      */
annotation|@
name|Property
argument_list|(
name|boolValue
operator|=
literal|true
argument_list|,
name|description
operator|=
literal|"If true: entities are fetched via the EntityHub. If false: entities are fetched on-line. Possible value: true or false."
argument_list|)
name|String
name|USE_ENTITY_HUB
init|=
literal|"engine.refactor.entityhub"
decl_stmt|;
specifier|public
name|String
name|getScope
parameter_list|()
function_decl|;
specifier|public
name|void
name|setScope
parameter_list|(
name|String
name|scopeId
parameter_list|)
function_decl|;
specifier|public
name|String
name|getRecipeLocation
parameter_list|()
function_decl|;
specifier|public
name|void
name|setRecipeLocation
parameter_list|(
name|String
name|recipeLocation
parameter_list|)
function_decl|;
specifier|public
name|String
name|getRecipeId
parameter_list|()
function_decl|;
specifier|public
name|void
name|setRecipeId
parameter_list|(
name|String
name|recipeId
parameter_list|)
function_decl|;
specifier|public
name|String
index|[]
name|getScopeCoreOntology
parameter_list|()
function_decl|;
specifier|public
name|void
name|setScopeCoreOntology
parameter_list|(
name|String
index|[]
name|coreOntologyIRI
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|isInGraphAppendMode
parameter_list|()
function_decl|;
specifier|public
name|void
name|setInGraphAppendMode
parameter_list|(
name|boolean
name|b
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|isEntityHubUsed
parameter_list|()
function_decl|;
specifier|public
name|void
name|setEntityHubUsed
parameter_list|(
name|boolean
name|b
parameter_list|)
function_decl|;
block|}
end_interface

end_unit
