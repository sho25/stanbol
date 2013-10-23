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
name|rules
operator|.
name|manager
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
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
name|Rule
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
name|RuleAtom
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
name|AtomList
import|;
end_import

begin_comment
comment|/**  *   * A concrete implementation of a {@link Rule}.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|RuleImpl
implements|implements
name|Rule
block|{
specifier|private
name|UriRef
name|ruleID
decl_stmt|;
specifier|private
name|String
name|ruleName
decl_stmt|;
specifier|private
name|String
name|rule
decl_stmt|;
specifier|private
name|AtomList
name|head
decl_stmt|;
specifier|private
name|AtomList
name|body
decl_stmt|;
specifier|protected
name|Recipe
name|recipe
decl_stmt|;
specifier|protected
name|String
name|description
decl_stmt|;
specifier|public
name|RuleImpl
parameter_list|(
name|UriRef
name|ruleID
parameter_list|,
name|String
name|ruleName
parameter_list|,
name|AtomList
name|body
parameter_list|,
name|AtomList
name|head
parameter_list|)
block|{
name|this
operator|.
name|ruleID
operator|=
name|ruleID
expr_stmt|;
name|this
operator|.
name|ruleName
operator|=
name|ruleName
expr_stmt|;
name|this
operator|.
name|head
operator|=
name|head
expr_stmt|;
name|this
operator|.
name|body
operator|=
name|body
expr_stmt|;
block|}
specifier|public
name|String
name|getRuleName
parameter_list|()
block|{
return|return
name|ruleName
return|;
block|}
specifier|public
name|void
name|setRuleName
parameter_list|(
name|String
name|ruleName
parameter_list|)
block|{
name|this
operator|.
name|ruleName
operator|=
name|ruleName
expr_stmt|;
block|}
specifier|public
name|String
name|getRule
parameter_list|()
block|{
return|return
name|rule
return|;
block|}
specifier|public
name|void
name|setRule
parameter_list|(
name|String
name|rule
parameter_list|)
block|{
name|this
operator|.
name|rule
operator|=
name|rule
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|prettyPrint
parameter_list|()
block|{
name|String
name|rule
init|=
literal|null
decl_stmt|;
name|String
name|tab
init|=
literal|"       "
decl_stmt|;
if|if
condition|(
name|head
operator|!=
literal|null
operator|&&
name|body
operator|!=
literal|null
condition|)
block|{
name|boolean
name|addAnd
init|=
literal|false
decl_stmt|;
name|rule
operator|=
literal|"RULE "
operator|+
name|ruleName
operator|+
literal|" ASSERTS THAT "
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
expr_stmt|;
name|rule
operator|+=
literal|"IF"
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
expr_stmt|;
for|for
control|(
name|RuleAtom
name|atom
range|:
name|body
control|)
block|{
name|rule
operator|+=
name|tab
expr_stmt|;
if|if
condition|(
name|addAnd
condition|)
block|{
name|rule
operator|+=
literal|"AND "
expr_stmt|;
block|}
else|else
block|{
name|addAnd
operator|=
literal|true
expr_stmt|;
block|}
name|rule
operator|+=
name|atom
operator|.
name|toString
argument_list|()
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
expr_stmt|;
block|}
name|rule
operator|+=
literal|"IMPLIES"
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
expr_stmt|;
name|addAnd
operator|=
literal|false
expr_stmt|;
for|for
control|(
name|RuleAtom
name|atom
range|:
name|head
control|)
block|{
name|rule
operator|+=
name|tab
expr_stmt|;
if|if
condition|(
name|addAnd
condition|)
block|{
name|rule
operator|+=
literal|"AND "
expr_stmt|;
block|}
else|else
block|{
name|addAnd
operator|=
literal|true
expr_stmt|;
block|}
name|rule
operator|+=
name|atom
operator|.
name|toString
argument_list|()
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|rule
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|StringBuilder
name|stringBuilder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
name|ruleName
argument_list|)
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
literal|"["
argument_list|)
expr_stmt|;
name|boolean
name|firstLoop
init|=
literal|true
decl_stmt|;
comment|// add the rule body
for|for
control|(
name|RuleAtom
name|atom
range|:
name|body
control|)
block|{
if|if
condition|(
operator|!
name|firstLoop
condition|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
literal|" . "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|firstLoop
operator|=
literal|false
expr_stmt|;
block|}
name|stringBuilder
operator|.
name|append
argument_list|(
name|atom
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// add the rule head
if|if
condition|(
name|head
operator|!=
literal|null
condition|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
literal|" -> "
argument_list|)
expr_stmt|;
name|firstLoop
operator|=
literal|true
expr_stmt|;
for|for
control|(
name|RuleAtom
name|atom
range|:
name|head
control|)
block|{
if|if
condition|(
operator|!
name|firstLoop
condition|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
literal|" . "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|firstLoop
operator|=
literal|false
expr_stmt|;
block|}
name|stringBuilder
operator|.
name|append
argument_list|(
name|atom
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|stringBuilder
operator|.
name|append
argument_list|(
literal|"]"
argument_list|)
expr_stmt|;
return|return
name|stringBuilder
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|AtomList
name|getBody
parameter_list|()
block|{
return|return
name|body
return|;
block|}
annotation|@
name|Override
specifier|public
name|AtomList
name|getHead
parameter_list|()
block|{
return|return
name|head
return|;
block|}
annotation|@
name|Override
specifier|public
name|UriRef
name|getRuleID
parameter_list|()
block|{
return|return
name|ruleID
return|;
block|}
specifier|protected
name|void
name|bindToRecipe
parameter_list|(
name|Recipe
name|recipe
parameter_list|)
block|{
name|this
operator|.
name|recipe
operator|=
name|recipe
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Recipe
name|getRecipe
parameter_list|()
block|{
return|return
name|recipe
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|description
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
block|{
name|this
operator|.
name|description
operator|=
name|description
expr_stmt|;
block|}
block|}
end_class

end_unit

