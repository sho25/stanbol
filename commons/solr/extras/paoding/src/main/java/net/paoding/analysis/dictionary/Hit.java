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
comment|/**  * Hit是检索字典时返回的结果。检索字典时，总是返回一个非空的Hit对象表示可能的各种情况。  *<p>  *   * Hit对象包含2类判断信息：  *<li>要检索的词语是否存在于词典中: {@link #isHit()}</li>  *<li>词典是否含有以给定字符串开头的其他词语: {@link #isUnclosed()}</li>  *<br>  * 如果上面2个信息都是否定的，则 {@link #isUndefined()}返回true，否则返回false.<br>  *<br>  *   * 如果{@link #isHit()}返回true，则{@link #getWord()}返回查找结果，{@link #getNext()}返回下一个词语。<br>  * 如果{@link #isHit()}返回false，但{@link #isUnclosed()}返回true，{@link #getNext()}返回以所查询词语开头的位置最靠前的词语。  *<p>  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *   * @see Dictionary  * @see BinaryDictionary  * @see HashBinaryDictionary  *   * @since 1.0  *   */
end_comment

begin_class
specifier|public
class|class
name|Hit
block|{
comment|// -------------------------------------------------
specifier|public
specifier|final
specifier|static
name|int
name|UNCLOSED_INDEX
init|=
operator|-
literal|1
decl_stmt|;
specifier|public
specifier|final
specifier|static
name|int
name|UNDEFINED_INDEX
init|=
operator|-
literal|2
decl_stmt|;
specifier|public
specifier|final
specifier|static
name|Hit
name|UNDEFINED
init|=
operator|new
name|Hit
argument_list|(
name|UNDEFINED_INDEX
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
comment|// -------------------------------------------------
comment|/** 	 * 目标词语在词典中的位置，或者在字典没有该词语是表示其他意思(参见以上静态变量定义的情况) 	 */
specifier|private
name|int
name|index
decl_stmt|;
comment|/** 	 * 查找命中时，词典中相应的词 	 */
specifier|private
name|Word
name|word
decl_stmt|;
comment|/** 	 * 词典中命中词的下一个单词，或{@link #isUnclosed()}为true时最接近的下一个词(参见本类的注释) 	 */
specifier|private
name|Word
name|next
decl_stmt|;
comment|// -------------------------------------------------
comment|/** 	 *  	 * @param index 	 *            目标词语在词典中的位置，或者在字典没有该词语是表示其他意思(参见以上静态变量定义的情况) 	 * @param word 	 *            查找命中时，词典中相应的词 	 * @param next 	 *            词典中命中词的下一个单词，或{@link #isUnclosed()}为true时最接近的下一个词(参见本类的注释) 	 */
specifier|public
name|Hit
parameter_list|(
name|int
name|index
parameter_list|,
name|Word
name|word
parameter_list|,
name|Word
name|next
parameter_list|)
block|{
name|this
operator|.
name|index
operator|=
name|index
expr_stmt|;
name|this
operator|.
name|word
operator|=
name|word
expr_stmt|;
name|this
operator|.
name|next
operator|=
name|next
expr_stmt|;
block|}
comment|// -------------------------------------------------
comment|/** 	 * 查找命中时，词典中相应的词 	 */
specifier|public
name|Word
name|getWord
parameter_list|()
block|{
return|return
name|word
return|;
block|}
comment|/** 	 * 目标词语在词典中的位置，或者在字典没有该词语是表示其他意思(参见以上静态变量定义的情况) 	 * @return 	 */
specifier|public
name|int
name|getIndex
parameter_list|()
block|{
return|return
name|index
return|;
block|}
comment|/** 	 * 词典中命中词的下一个单词，或{@link #isUnclosed()}为true时最接近的下一个词(参见本类的注释) 	 * @return 	 */
specifier|public
name|Word
name|getNext
parameter_list|()
block|{
return|return
name|next
return|;
block|}
comment|/** 	 * 是否在字典中检索到要检索的词语 	 * @return 	 */
specifier|public
name|boolean
name|isHit
parameter_list|()
block|{
return|return
name|this
operator|.
name|index
operator|>=
literal|0
return|;
block|}
comment|/** 	 * 是否有以当前检索词语开头的词语 	 * @return 	 */
specifier|public
name|boolean
name|isUnclosed
parameter_list|()
block|{
return|return
name|UNCLOSED_INDEX
operator|==
name|this
operator|.
name|index
operator|||
operator|(
name|this
operator|.
name|next
operator|!=
literal|null
operator|&&
name|this
operator|.
name|next
operator|.
name|length
argument_list|()
operator|>=
name|this
operator|.
name|word
operator|.
name|length
argument_list|()
operator|&&
name|this
operator|.
name|next
operator|.
name|startsWith
argument_list|(
name|word
argument_list|)
operator|)
return|;
block|}
comment|/** 	 * 字典中没有当前检索的词语，或以其开头的词语 	 * @return 	 */
specifier|public
name|boolean
name|isUndefined
parameter_list|()
block|{
return|return
name|UNDEFINED
operator|.
name|index
operator|==
name|this
operator|.
name|index
return|;
block|}
comment|// -------------------------------------------------
name|void
name|setIndex
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|this
operator|.
name|index
operator|=
name|index
expr_stmt|;
block|}
name|void
name|setWord
parameter_list|(
name|Word
name|key
parameter_list|)
block|{
name|this
operator|.
name|word
operator|=
name|key
expr_stmt|;
block|}
name|void
name|setNext
parameter_list|(
name|Word
name|next
parameter_list|)
block|{
name|this
operator|.
name|next
operator|=
name|next
expr_stmt|;
block|}
comment|// -------------------------------------------------
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
specifier|final
name|int
name|PRIME
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
name|PRIME
operator|*
name|result
operator|+
operator|(
operator|(
name|word
operator|==
literal|null
operator|)
condition|?
literal|0
else|:
name|word
operator|.
name|hashCode
argument_list|()
operator|)
expr_stmt|;
name|result
operator|=
name|PRIME
operator|*
name|result
operator|+
name|index
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
specifier|final
name|Hit
name|other
init|=
operator|(
name|Hit
operator|)
name|obj
decl_stmt|;
if|if
condition|(
name|word
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|word
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
block|}
elseif|else
if|if
condition|(
operator|!
name|word
operator|.
name|equals
argument_list|(
name|other
operator|.
name|word
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|index
operator|!=
name|other
operator|.
name|index
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
if|if
condition|(
name|isUnclosed
argument_list|()
condition|)
block|{
return|return
literal|"[UNCLOSED]"
return|;
block|}
elseif|else
if|if
condition|(
name|isUndefined
argument_list|()
condition|)
block|{
return|return
literal|"[UNDEFINED]"
return|;
block|}
return|return
literal|"["
operator|+
name|index
operator|+
literal|']'
operator|+
name|word
return|;
block|}
block|}
end_class

end_unit

