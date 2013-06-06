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
name|knife
package|;
end_package

begin_comment
comment|/**  * Collector接收Knife切割文本得到的词语。  *<p>  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *   * @see Knife  *   * @since 1.0  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|Collector
block|{
comment|/** 	 * 当Knife从文本流中获取一个词语时，本方法被调用。<br> 	 * 调用的顺序与词语在文本流中的顺序是否一致视不同实现可能有不同的策略。 	 *<p> 	 *  	 * 如当Knife收到“中国当代社会现象”文本流中的“社会”时，传入的参数分别将是：(“社会”, 4, 6) 	 *  	 * @param word 	 *            接收到的词语 	 * @param offset 	 *            该词语在文本流中的偏移位置 	 * @param end 	 *            该词语在文本流中的结束位置(词语不包括文本流end位置的字符)，end-offset是为word的长度 	 *  	 *          	 */
specifier|public
name|void
name|collect
parameter_list|(
name|String
name|word
parameter_list|,
name|int
name|offset
parameter_list|,
name|int
name|end
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

