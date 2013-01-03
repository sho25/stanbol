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
comment|/**  * Dictionary是一个只读字典，用于查找是否包含某个词语，以及相关信息。  *<p>  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *   * @see BinaryDictionary  * @see HashBinaryDictionary  *   * @since 1.0  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|Dictionary
block|{
comment|/** 	 * 返回字典中词语数>=0 	 *  	 * @return 	 */
specifier|public
name|int
name|size
parameter_list|()
function_decl|;
comment|/** 	 * 返回给定位置的词语 	 *  	 * @param index 	 *            0,1,2,...,size-1 	 * @return 	 */
specifier|public
name|Word
name|get
parameter_list|(
name|int
name|index
parameter_list|)
function_decl|;
comment|/** 	 * 搜索词典是否收集input[offset]到input[offset+count-1]之间字符串(包含边界)的词。<br> 	 * 搜索结果以非空Hit对象给出。 	 *<p> 	 * @param input 要搜索的字符串是其中连续的一部分 	 * @param offset 要搜索的字符串开始位置相对input的偏移 	 * @param count 要搜索的字符串字符个数 	 * @return 返回的Hit对象非空，程序通过hit对象提供的方法判断搜索结果 	 *  	 * @see Hit 	 */
specifier|public
name|Hit
name|search
parameter_list|(
name|CharSequence
name|input
parameter_list|,
name|int
name|offset
parameter_list|,
name|int
name|count
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

