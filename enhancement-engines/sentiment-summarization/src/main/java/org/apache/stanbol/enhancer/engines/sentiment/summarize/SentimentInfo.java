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
name|sentiment
operator|.
name|summarize
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
name|Section
import|;
end_import

begin_comment
comment|/**  * Holds the information about the Sentiment tags found for a  * {@link Section}.  * Values returned by the getters are the geometric mean normalised by the  * number of tags with an sentiment value. By this words with an high sentiment  * value will have much more influence as those with lower values  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|SentimentInfo
block|{
specifier|private
name|Section
name|section
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Sentiment
argument_list|>
name|sentiments
decl_stmt|;
name|SentimentInfo
parameter_list|(
name|Section
name|section
parameter_list|)
block|{
if|if
condition|(
name|section
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Section MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|section
operator|=
name|section
expr_stmt|;
block|}
specifier|public
name|Double
name|getPositive
parameter_list|()
block|{
if|if
condition|(
name|sentiments
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|double
name|positive
init|=
literal|0
decl_stmt|;
name|int
name|num
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Sentiment
name|s
range|:
name|sentiments
control|)
block|{
name|double
name|v
init|=
name|s
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|v
operator|>
literal|0
condition|)
block|{
name|positive
operator|=
name|positive
operator|+
operator|(
name|v
operator|*
name|v
operator|)
expr_stmt|;
name|num
operator|++
expr_stmt|;
block|}
block|}
return|return
name|positive
operator|>
literal|0.0
condition|?
name|Math
operator|.
name|sqrt
argument_list|(
name|positive
operator|/
operator|(
name|double
operator|)
name|num
argument_list|)
else|:
literal|null
return|;
block|}
specifier|public
name|Double
name|getNegative
parameter_list|()
block|{
if|if
condition|(
name|sentiments
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|double
name|negative
init|=
literal|0
decl_stmt|;
name|int
name|num
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Sentiment
name|s
range|:
name|sentiments
control|)
block|{
name|double
name|v
init|=
name|s
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|v
operator|<
literal|0
condition|)
block|{
name|negative
operator|=
name|negative
operator|+
operator|(
name|v
operator|*
name|v
operator|)
expr_stmt|;
name|num
operator|++
expr_stmt|;
block|}
block|}
return|return
name|negative
operator|>
literal|0.0
condition|?
name|Math
operator|.
name|sqrt
argument_list|(
name|negative
operator|/
operator|(
name|double
operator|)
name|num
argument_list|)
operator|*
operator|-
literal|1
else|:
literal|null
return|;
block|}
specifier|public
name|Double
name|getSentiment
parameter_list|()
block|{
if|if
condition|(
name|sentiments
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|Double
name|pos
init|=
name|getPositive
argument_list|()
decl_stmt|;
name|Double
name|neg
init|=
name|getNegative
argument_list|()
decl_stmt|;
if|if
condition|(
name|pos
operator|==
literal|null
operator|&&
name|neg
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|double
name|sum
init|=
name|pos
operator|!=
literal|null
condition|?
name|pos
else|:
literal|0.0
decl_stmt|;
name|sum
operator|=
name|neg
operator|!=
literal|null
condition|?
name|sum
operator|+
name|neg
else|:
name|sum
expr_stmt|;
return|return
name|sum
return|;
block|}
specifier|public
name|boolean
name|hasSentiment
parameter_list|()
block|{
return|return
name|sentiments
operator|!=
literal|null
return|;
block|}
name|void
name|addSentiment
parameter_list|(
name|Sentiment
name|sentiment
parameter_list|)
block|{
if|if
condition|(
name|sentiments
operator|==
literal|null
condition|)
block|{
name|sentiments
operator|=
operator|new
name|ArrayList
argument_list|<
name|Sentiment
argument_list|>
argument_list|(
literal|4
argument_list|)
expr_stmt|;
block|}
name|sentiments
operator|.
name|add
argument_list|(
name|sentiment
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Section
name|getSection
parameter_list|()
block|{
return|return
name|section
return|;
block|}
specifier|public
name|List
argument_list|<
name|Sentiment
argument_list|>
name|getSentiments
parameter_list|()
block|{
return|return
name|sentiments
return|;
block|}
block|}
end_class

end_unit

