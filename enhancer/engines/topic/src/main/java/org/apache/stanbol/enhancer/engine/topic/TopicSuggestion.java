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
name|engine
operator|.
name|topic
package|;
end_package

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
specifier|public
specifier|final
name|String
name|uri
decl_stmt|;
specifier|public
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|paths
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
specifier|final
name|double
name|score
decl_stmt|;
specifier|public
name|TopicSuggestion
parameter_list|(
name|String
name|uri
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|paths
parameter_list|,
name|double
name|score
parameter_list|)
block|{
name|this
operator|.
name|uri
operator|=
name|uri
expr_stmt|;
if|if
condition|(
name|paths
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|paths
operator|.
name|addAll
argument_list|(
name|paths
argument_list|)
expr_stmt|;
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
name|uri
parameter_list|,
name|double
name|score
parameter_list|)
block|{
name|this
argument_list|(
name|uri
argument_list|,
literal|null
argument_list|,
name|score
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
