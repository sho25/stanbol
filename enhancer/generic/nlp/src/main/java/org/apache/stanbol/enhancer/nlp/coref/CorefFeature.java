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
name|coref
package|;
end_package

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
name|Set
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
comment|/**  * Represents a coreference resolution feature attached to a {@link Token}. It  * contains information about other {@link Token}s which refer to the  * aforementioned {@link Token}.  *   * @author Cristian Petroaca  *   */
end_comment

begin_class
specifier|public
class|class
name|CorefFeature
block|{
comment|/** 	 * Shows whether the {@link Token} to which this object is attached is the 	 * representative mention in the chain. 	 */
specifier|private
name|boolean
name|isRepresentative
decl_stmt|;
comment|/** 	 * A set of {@link Token}s representing metions of the {@link Token} to 	 * which this object is attached. 	 */
specifier|private
name|Set
argument_list|<
name|Span
argument_list|>
name|mentions
decl_stmt|;
specifier|public
name|CorefFeature
parameter_list|(
name|boolean
name|isRepresentative
parameter_list|,
name|Set
argument_list|<
name|Span
argument_list|>
name|mentions
parameter_list|)
block|{
if|if
condition|(
name|mentions
operator|==
literal|null
operator|||
name|mentions
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The mentions set cannot be null or empty"
argument_list|)
throw|;
block|}
name|this
operator|.
name|isRepresentative
operator|=
name|isRepresentative
expr_stmt|;
name|this
operator|.
name|mentions
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|mentions
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Getter whether the {@link Token} to which this object is attached is the 	 * representative mention in the chain. 	 *  	 * @return the representative state 	 */
specifier|public
name|boolean
name|isRepresentative
parameter_list|()
block|{
return|return
name|this
operator|.
name|isRepresentative
return|;
block|}
comment|/** 	 * Getter for the set of {@link Token}s representing mentions of the 	 * {@link Token} to which this object is attached. 	 *  	 * @return 	 */
specifier|public
name|Set
argument_list|<
name|Span
argument_list|>
name|getMentions
parameter_list|()
block|{
return|return
name|this
operator|.
name|mentions
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
specifier|final
name|int
name|prime
init|=
literal|31
decl_stmt|;
name|int
name|result
init|=
literal|1
decl_stmt|;
name|result
operator|=
name|prime
operator|*
name|result
operator|+
operator|(
name|isRepresentative
condition|?
literal|1231
else|:
literal|1237
operator|)
expr_stmt|;
name|result
operator|=
name|prime
operator|*
name|result
operator|+
name|mentions
operator|.
name|hashCode
argument_list|()
expr_stmt|;
return|return
name|result
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
if|if
condition|(
name|this
operator|==
name|obj
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|obj
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getClass
argument_list|()
operator|!=
name|obj
operator|.
name|getClass
argument_list|()
condition|)
return|return
literal|false
return|;
name|CorefFeature
name|other
init|=
operator|(
name|CorefFeature
operator|)
name|obj
decl_stmt|;
return|return
operator|(
name|isRepresentative
operator|==
name|other
operator|.
name|isRepresentative
operator|)
operator|&&
operator|(
name|mentions
operator|.
name|equals
argument_list|(
name|other
operator|.
name|mentions
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

