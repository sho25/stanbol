begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  * Copyright 2007 The Apache Software Foundation  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|net
operator|.
name|paoding
operator|.
name|analysis
operator|.
name|dictionary
package|;
end_package

begin_comment
comment|/**  * Dictionary的二叉查找实现。  *<p>  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *   * @since 1.0  *   */
end_comment

begin_class
specifier|public
class|class
name|BinaryDictionary
implements|implements
name|Dictionary
block|{
comment|// -------------------------------------------------
specifier|private
name|Word
index|[]
name|ascWords
decl_stmt|;
specifier|private
specifier|final
name|int
name|start
decl_stmt|;
specifier|private
specifier|final
name|int
name|end
decl_stmt|;
specifier|private
specifier|final
name|int
name|count
decl_stmt|;
comment|// -------------------------------------------------
comment|/** 	 * 以一组升序排列的词语构造二叉查找字典 	 *<p> 	 *  	 * @param ascWords 	 *            升序排列词语 	 */
specifier|public
name|BinaryDictionary
parameter_list|(
name|Word
index|[]
name|ascWords
parameter_list|)
block|{
name|this
argument_list|(
name|ascWords
argument_list|,
literal|0
argument_list|,
name|ascWords
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
specifier|public
name|BinaryDictionary
parameter_list|(
name|Word
index|[]
name|ascWords
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|)
block|{
name|this
operator|.
name|ascWords
operator|=
name|ascWords
expr_stmt|;
name|this
operator|.
name|start
operator|=
name|start
expr_stmt|;
name|this
operator|.
name|end
operator|=
name|end
expr_stmt|;
name|this
operator|.
name|count
operator|=
name|end
operator|-
name|start
expr_stmt|;
block|}
comment|// -------------------------------------------------
specifier|public
name|Word
name|get
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
name|ascWords
index|[
name|start
operator|+
name|index
index|]
return|;
block|}
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|count
return|;
block|}
specifier|public
name|Hit
name|search
parameter_list|(
name|CharSequence
name|input
parameter_list|,
name|int
name|begin
parameter_list|,
name|int
name|count
parameter_list|)
block|{
name|int
name|left
init|=
name|this
operator|.
name|start
decl_stmt|;
name|int
name|right
init|=
name|this
operator|.
name|end
operator|-
literal|1
decl_stmt|;
name|int
name|pointer
init|=
literal|0
decl_stmt|;
name|Word
name|word
init|=
literal|null
decl_stmt|;
name|int
name|relation
decl_stmt|;
comment|//
while|while
condition|(
name|left
operator|<=
name|right
condition|)
block|{
name|pointer
operator|=
operator|(
name|left
operator|+
name|right
operator|)
operator|>>
literal|1
expr_stmt|;
name|word
operator|=
name|ascWords
index|[
name|pointer
index|]
expr_stmt|;
name|relation
operator|=
name|compare
argument_list|(
name|input
argument_list|,
name|begin
argument_list|,
name|count
argument_list|,
name|word
argument_list|)
expr_stmt|;
if|if
condition|(
name|relation
operator|==
literal|0
condition|)
block|{
comment|// System.out.println(new String(input,begin, count)+"***" +
comment|// word);
name|int
name|nextWordIndex
init|=
name|pointer
operator|+
literal|1
decl_stmt|;
if|if
condition|(
name|nextWordIndex
operator|>=
name|ascWords
operator|.
name|length
condition|)
block|{
return|return
operator|new
name|Hit
argument_list|(
name|pointer
argument_list|,
name|word
argument_list|,
literal|null
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|Hit
argument_list|(
name|pointer
argument_list|,
name|word
argument_list|,
name|ascWords
index|[
name|nextWordIndex
index|]
argument_list|)
return|;
block|}
block|}
if|if
condition|(
name|relation
operator|<
literal|0
condition|)
name|right
operator|=
name|pointer
operator|-
literal|1
expr_stmt|;
else|else
name|left
operator|=
name|pointer
operator|+
literal|1
expr_stmt|;
block|}
comment|//
if|if
condition|(
name|left
operator|>=
name|ascWords
operator|.
name|length
condition|)
block|{
return|return
name|Hit
operator|.
name|UNDEFINED
return|;
block|}
comment|//
name|boolean
name|asPrex
init|=
literal|true
decl_stmt|;
name|Word
name|nextWord
init|=
name|ascWords
index|[
name|left
index|]
decl_stmt|;
if|if
condition|(
name|nextWord
operator|.
name|length
argument_list|()
operator|<
name|count
condition|)
block|{
name|asPrex
operator|=
literal|false
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
name|begin
init|,
name|j
init|=
literal|0
init|;
name|asPrex
operator|&&
name|j
operator|<
name|count
condition|;
name|i
operator|++
operator|,
name|j
operator|++
control|)
block|{
if|if
condition|(
name|input
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|!=
name|nextWord
operator|.
name|charAt
argument_list|(
name|j
argument_list|)
condition|)
block|{
name|asPrex
operator|=
literal|false
expr_stmt|;
block|}
block|}
return|return
name|asPrex
condition|?
operator|new
name|Hit
argument_list|(
name|Hit
operator|.
name|UNCLOSED_INDEX
argument_list|,
literal|null
argument_list|,
name|nextWord
argument_list|)
else|:
name|Hit
operator|.
name|UNDEFINED
return|;
block|}
specifier|public
specifier|static
name|int
name|compare
parameter_list|(
name|CharSequence
name|one
parameter_list|,
name|int
name|begin
parameter_list|,
name|int
name|count
parameter_list|,
name|CharSequence
name|theOther
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
name|begin
init|,
name|j
init|=
literal|0
init|;
name|i
operator|<
name|one
operator|.
name|length
argument_list|()
operator|&&
name|j
operator|<
name|Math
operator|.
name|min
argument_list|(
name|theOther
operator|.
name|length
argument_list|()
argument_list|,
name|count
argument_list|)
condition|;
name|i
operator|++
operator|,
name|j
operator|++
control|)
block|{
if|if
condition|(
name|one
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|>
name|theOther
operator|.
name|charAt
argument_list|(
name|j
argument_list|)
condition|)
block|{
return|return
literal|1
return|;
block|}
elseif|else
if|if
condition|(
name|one
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|<
name|theOther
operator|.
name|charAt
argument_list|(
name|j
argument_list|)
condition|)
block|{
return|return
operator|-
literal|1
return|;
block|}
block|}
return|return
name|count
operator|-
name|theOther
operator|.
name|length
argument_list|()
return|;
block|}
block|}
end_class

end_unit
