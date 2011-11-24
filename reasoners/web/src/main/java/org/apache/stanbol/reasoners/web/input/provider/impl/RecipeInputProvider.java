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
name|reasoners
operator|.
name|web
operator|.
name|input
operator|.
name|provider
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|reasoners
operator|.
name|servicesapi
operator|.
name|ReasoningServiceInputProvider
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|NoSuchRecipeException
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|Recipe
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|RuleStore
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|util
operator|.
name|RuleList
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
name|apibinding
operator|.
name|OWLManager
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
name|IRI
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
name|SWRLRule
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Input provider which binds the reasoners input to the Rule module.  *   * TODO Waiting for STANBOL-186, this provider only supports OWLApi reasoning services.  *   * @author enridaga  *  */
end_comment

begin_class
specifier|public
class|class
name|RecipeInputProvider
implements|implements
name|ReasoningServiceInputProvider
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RecipeInputProvider
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|RuleStore
name|store
decl_stmt|;
specifier|private
name|String
name|recipeId
decl_stmt|;
comment|/**      * Constructor      *       * @param store      * @param recipeId      */
specifier|public
name|RecipeInputProvider
parameter_list|(
name|RuleStore
name|store
parameter_list|,
name|String
name|recipeId
parameter_list|)
block|{
name|this
operator|.
name|store
operator|=
name|store
expr_stmt|;
name|this
operator|.
name|recipeId
operator|=
name|recipeId
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
parameter_list|<
name|T
parameter_list|>
name|Iterator
argument_list|<
name|T
argument_list|>
name|getInput
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
operator|!
name|type
operator|.
name|isAssignableFrom
argument_list|(
name|SWRLRule
operator|.
name|class
argument_list|)
condition|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Cannot adapt to this type {}"
argument_list|,
name|type
operator|.
name|getCanonicalName
argument_list|()
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Cannot adapt to "
operator|+
name|type
operator|.
name|getCanonicalName
argument_list|()
argument_list|)
throw|;
block|}
name|List
argument_list|<
name|SWRLRule
argument_list|>
name|rules
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|recipeId
operator|!=
literal|null
condition|)
block|{
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"[start] Prepare rules for OWLApi "
argument_list|)
expr_stmt|;
comment|// If recipe exists, return it as a list of SWRL rules
name|rules
operator|=
operator|new
name|ArrayList
argument_list|<
name|SWRLRule
argument_list|>
argument_list|()
expr_stmt|;
try|try
block|{
name|Recipe
name|recipe
init|=
literal|null
decl_stmt|;
synchronized|synchronized
init|(
name|store
init|)
block|{
name|recipe
operator|=
name|store
operator|.
name|getRecipe
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|recipeId
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Recipe is: {}"
argument_list|,
name|recipe
argument_list|)
expr_stmt|;
name|RuleList
name|ruleList
init|=
name|recipe
operator|.
name|getkReSRuleList
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"RuleList is: {}"
argument_list|,
name|ruleList
argument_list|)
expr_stmt|;
for|for
control|(
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|Rule
name|r
range|:
name|ruleList
control|)
block|{
name|SWRLRule
name|swrl
init|=
name|r
operator|.
name|toSWRL
argument_list|(
name|OWLManager
operator|.
name|getOWLDataFactory
argument_list|()
argument_list|)
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Prepared rule: {}"
argument_list|,
name|swrl
argument_list|)
expr_stmt|;
name|rules
operator|.
name|add
argument_list|(
name|swrl
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|NoSuchRecipeException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Recipe {} does not exists"
argument_list|,
name|recipeId
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IOException
argument_list|(
name|e
argument_list|)
throw|;
block|}
name|long
name|end
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"[end] Prepared {} rules for OWLApi in {} ms."
argument_list|,
name|rules
operator|.
name|size
argument_list|()
argument_list|,
operator|(
name|end
operator|-
name|start
operator|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|rules
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"No rules have been loaded"
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IOException
argument_list|(
literal|"No rules loaded"
argument_list|)
throw|;
block|}
specifier|final
name|Iterator
argument_list|<
name|SWRLRule
argument_list|>
name|iterator
init|=
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|rules
argument_list|)
operator|.
name|iterator
argument_list|()
decl_stmt|;
return|return
operator|new
name|Iterator
argument_list|<
name|T
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|iterator
operator|.
name|hasNext
argument_list|()
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|public
name|T
name|next
parameter_list|()
block|{
return|return
operator|(
name|T
operator|)
name|iterator
operator|.
name|next
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|()
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Cannot remove items from this iterator. This may be cused by an error in the program"
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Cannot remove items from this iterator"
argument_list|)
throw|;
block|}
block|}
return|;
block|}
annotation|@
name|Override
specifier|public
parameter_list|<
name|T
parameter_list|>
name|boolean
name|adaptTo
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|.
name|isAssignableFrom
argument_list|(
name|SWRLRule
operator|.
name|class
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit
