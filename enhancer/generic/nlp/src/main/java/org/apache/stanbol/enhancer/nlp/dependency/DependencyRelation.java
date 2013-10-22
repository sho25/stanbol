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
name|nlp
operator|.
name|dependency
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Span
import|;
end_import

begin_comment
comment|/**  * Represents the grammatical relation that a {@link Token} can have with  * another {@link Token} from the same {@link Sentence}  *   * @author Cristian Petroaca  *   */
end_comment

begin_class
specifier|public
class|class
name|DependencyRelation
block|{
comment|/** 	 * The actual grammatical relation tag 	 */
specifier|private
name|GrammaticalRelationTag
name|grammaticalRelationTag
decl_stmt|;
comment|/** 	 * Denotes whether the {@link Token} which has this relation is dependent in 	 * the relation 	 */
specifier|private
name|boolean
name|isDependent
decl_stmt|;
comment|/** 	 * The {@link Token} with which the relation is made. 	 */
specifier|private
name|Span
name|partner
decl_stmt|;
specifier|public
name|DependencyRelation
parameter_list|()
block|{ 	}
specifier|public
name|DependencyRelation
parameter_list|(
name|GrammaticalRelationTag
name|grammaticalRelationTag
parameter_list|)
block|{
name|this
operator|.
name|grammaticalRelationTag
operator|=
name|grammaticalRelationTag
expr_stmt|;
block|}
specifier|public
name|DependencyRelation
parameter_list|(
name|GrammaticalRelationTag
name|grammaticalRelationTag
parameter_list|,
name|boolean
name|isDependent
parameter_list|,
name|Span
name|partner
parameter_list|)
block|{
name|this
argument_list|(
name|grammaticalRelationTag
argument_list|)
expr_stmt|;
name|this
operator|.
name|isDependent
operator|=
name|isDependent
expr_stmt|;
name|this
operator|.
name|partner
operator|=
name|partner
expr_stmt|;
block|}
specifier|public
name|GrammaticalRelationTag
name|getGrammaticalRelationTag
parameter_list|()
block|{
return|return
name|grammaticalRelationTag
return|;
block|}
specifier|public
name|void
name|setGrammaticalRelationTag
parameter_list|(
name|GrammaticalRelationTag
name|grammaticalRelationTag
parameter_list|)
block|{
name|this
operator|.
name|grammaticalRelationTag
operator|=
name|grammaticalRelationTag
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDependent
parameter_list|()
block|{
return|return
name|isDependent
return|;
block|}
specifier|public
name|void
name|setDependent
parameter_list|(
name|boolean
name|isDependent
parameter_list|)
block|{
name|this
operator|.
name|isDependent
operator|=
name|isDependent
expr_stmt|;
block|}
specifier|public
name|Span
name|getPartner
parameter_list|()
block|{
return|return
name|this
operator|.
name|partner
return|;
block|}
specifier|public
name|void
name|setPartner
parameter_list|(
name|Span
name|partner
parameter_list|)
block|{
name|this
operator|.
name|partner
operator|=
name|partner
expr_stmt|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|grammaticalRelationTag
operator|.
name|hashCode
argument_list|()
operator|+
operator|(
operator|(
name|partner
operator|!=
literal|null
operator|)
condition|?
name|partner
operator|.
name|hashCode
argument_list|()
else|:
literal|0
operator|)
operator|+
operator|+
operator|(
name|isDependent
condition|?
literal|1
else|:
literal|0
operator|)
return|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|super
operator|.
name|equals
argument_list|(
name|obj
argument_list|)
operator|&&
operator|(
name|obj
operator|instanceof
name|DependencyRelation
operator|)
operator|&&
operator|(
name|this
operator|.
name|grammaticalRelationTag
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|DependencyRelation
operator|)
name|obj
operator|)
operator|.
name|getGrammaticalRelationTag
argument_list|()
argument_list|)
operator|)
operator|&&
operator|(
name|this
operator|.
name|isDependent
operator|==
operator|(
operator|(
name|DependencyRelation
operator|)
name|obj
operator|)
operator|.
name|isDependent
argument_list|()
operator|)
operator|&&
operator|(
name|this
operator|.
name|partner
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|DependencyRelation
operator|)
name|obj
operator|)
operator|.
name|getPartner
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

