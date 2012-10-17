begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreemnets.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License. You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|opennlp
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
name|opennlp
operator|.
name|tools
operator|.
name|tokenize
operator|.
name|Tokenizer
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|util
operator|.
name|Span
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|util
operator|.
name|StringUtil
import|;
end_import

begin_comment
comment|/**  * Performs tokenization using the character class whitespace. Will create  * seperate tokens for punctation at the end of words.   * Intended to be used to extract alphanumeric  * keywords from texts  *   * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|KeywordTokenizer
implements|implements
name|Tokenizer
block|{
specifier|public
specifier|static
specifier|final
name|KeywordTokenizer
name|INSTANCE
decl_stmt|;
static|static
block|{
name|INSTANCE
operator|=
operator|new
name|KeywordTokenizer
argument_list|()
expr_stmt|;
block|}
specifier|private
name|KeywordTokenizer
parameter_list|()
block|{}
specifier|public
name|String
index|[]
name|tokenize
parameter_list|(
name|String
name|s
parameter_list|)
block|{
return|return
name|Span
operator|.
name|spansToStrings
argument_list|(
name|tokenizePos
argument_list|(
name|s
argument_list|)
argument_list|,
name|s
argument_list|)
return|;
block|}
specifier|public
name|Span
index|[]
name|tokenizePos
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|boolean
name|isWhitespace
decl_stmt|;
name|List
argument_list|<
name|Span
argument_list|>
name|tokens
init|=
operator|new
name|ArrayList
argument_list|<
name|Span
argument_list|>
argument_list|()
decl_stmt|;
name|int
name|sl
init|=
name|s
operator|.
name|length
argument_list|()
decl_stmt|;
name|int
name|start
init|=
operator|-
literal|1
decl_stmt|;
name|char
name|pc
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|ci
init|=
literal|0
init|;
name|ci
operator|<=
name|sl
condition|;
name|ci
operator|++
control|)
block|{
name|char
name|c
init|=
name|ci
operator|<
name|sl
condition|?
name|s
operator|.
name|charAt
argument_list|(
name|ci
argument_list|)
else|:
literal|' '
decl_stmt|;
name|isWhitespace
operator|=
name|StringUtil
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|isWhitespace
operator|&
name|start
operator|<
literal|0
condition|)
block|{
comment|// new token starts
name|start
operator|=
name|ci
expr_stmt|;
block|}
if|if
condition|(
name|isWhitespace
operator|&&
name|start
operator|>=
literal|0
condition|)
block|{
comment|// end of token
comment|// limited support for punctations at the end of words
if|if
condition|(
name|start
operator|<
name|ci
operator|-
literal|1
operator|&&
operator|(
name|pc
operator|==
literal|'.'
operator|||
name|pc
operator|==
literal|','
operator|||
name|pc
operator|==
literal|'!'
operator|||
name|pc
operator|==
literal|'?'
operator|||
name|pc
operator|==
literal|';'
operator|||
name|pc
operator|==
literal|':'
operator|)
condition|)
block|{
name|tokens
operator|.
name|add
argument_list|(
operator|new
name|Span
argument_list|(
name|start
argument_list|,
name|ci
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|tokens
operator|.
name|add
argument_list|(
operator|new
name|Span
argument_list|(
name|ci
operator|-
literal|1
argument_list|,
name|ci
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|tokens
operator|.
name|add
argument_list|(
operator|new
name|Span
argument_list|(
name|start
argument_list|,
name|ci
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|start
operator|=
operator|-
literal|1
expr_stmt|;
block|}
block|}
return|return
operator|(
name|Span
index|[]
operator|)
name|tokens
operator|.
name|toArray
argument_list|(
operator|new
name|Span
index|[
name|tokens
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
block|}
end_class

end_unit

