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
name|topic
operator|.
name|api
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang
operator|.
name|StringUtils
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
name|Collection
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

begin_comment
comment|/**  * Data transfer object for the individual topic classification results.  */
end_comment

begin_class
specifier|public
class|class
name|TopicSuggestion
block|{
comment|/**      * The URI of the concept in the hierarchical conceptual scheme (that holds the broader relationship)      */
specifier|public
specifier|final
name|String
name|conceptUri
decl_stmt|;
comment|/**      * Reference to the broader concepts of this suggestion.      */
specifier|public
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|broader
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|/**      * The (optional) URI of a resource that grounds this concepts in the real world. Can be null.      */
specifier|public
specifier|final
name|String
name|primaryTopicUri
decl_stmt|;
comment|/**      * The (positive) score of the suggestion: higher is better. Zero would mean unrelated. The absolute value      * is meaningless: suggestions scores cannot be compared across different input text documents nor      * distinct concept schemes.      */
specifier|public
specifier|final
name|float
name|score
decl_stmt|;
specifier|public
name|TopicSuggestion
parameter_list|(
name|String
name|conceptUri
parameter_list|,
name|String
name|primaryTopicUri
parameter_list|,
name|Collection
argument_list|<
name|?
extends|extends
name|Object
argument_list|>
name|broader
parameter_list|,
name|float
name|score
parameter_list|)
block|{
name|this
operator|.
name|conceptUri
operator|=
name|conceptUri
expr_stmt|;
name|this
operator|.
name|primaryTopicUri
operator|=
name|primaryTopicUri
expr_stmt|;
if|if
condition|(
name|broader
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Object
name|broaderConcept
range|:
name|broader
control|)
block|{
name|this
operator|.
name|broader
operator|.
name|add
argument_list|(
name|broaderConcept
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|this
operator|.
name|score
operator|=
name|score
expr_stmt|;
block|}
specifier|public
name|TopicSuggestion
parameter_list|(
name|String
name|conceptUri
parameter_list|,
name|float
name|score
parameter_list|)
block|{
name|this
argument_list|(
name|conceptUri
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|score
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
name|String
operator|.
name|format
argument_list|(
literal|"TopicSuggestion(\"%s\", [%s], %f)"
argument_list|,
name|conceptUri
argument_list|,
name|StringUtils
operator|.
name|join
argument_list|(
name|broader
argument_list|,
literal|"\", \""
argument_list|)
argument_list|,
name|score
argument_list|)
return|;
block|}
block|}
end_class

end_unit

