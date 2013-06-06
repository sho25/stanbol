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

begin_import
import|import
name|net
operator|.
name|paoding
operator|.
name|analysis
operator|.
name|dictionary
operator|.
name|Dictionary
import|;
end_import

begin_import
import|import
name|net
operator|.
name|paoding
operator|.
name|analysis
operator|.
name|dictionary
operator|.
name|support
operator|.
name|detection
operator|.
name|DifferenceListener
import|;
end_import

begin_comment
comment|/**  * 中文字典缓存根据地,为{@link CJKKnife}所用。<br>  * 从本对象可以获取中文需要的相关字典。包括词汇表、姓氏表、计量单位表、忽略的词或单字等。  *<p>  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *   * @see CJKKnife  *   * @since 1.0  */
end_comment

begin_interface
specifier|public
interface|interface
name|Dictionaries
block|{
comment|/** 	 * 词汇表字典 	 *  	 * @return 	 */
specifier|public
name|Dictionary
name|getVocabularyDictionary
parameter_list|()
function_decl|;
comment|/** 	 * 姓氏字典 	 *  	 * @return 	 */
specifier|public
name|Dictionary
name|getConfucianFamilyNamesDictionary
parameter_list|()
function_decl|;
comment|/** 	 * 忽略的词语 	 *  	 * @return 	 */
specifier|public
name|Dictionary
name|getNoiseCharactorsDictionary
parameter_list|()
function_decl|;
comment|/** 	 * 忽略的单字 	 *  	 * @return 	 */
specifier|public
name|Dictionary
name|getNoiseWordsDictionary
parameter_list|()
function_decl|;
comment|/** 	 * 计量单位 	 *  	 * @return 	 */
specifier|public
name|Dictionary
name|getUnitsDictionary
parameter_list|()
function_decl|;
comment|/** 	 * lantin+cjk, num+cjk 	 * @return 	 */
specifier|public
name|Dictionary
name|getCombinatoricsDictionary
parameter_list|()
function_decl|;
comment|/** 	 *  	 * @param l 	 */
specifier|public
name|void
name|startDetecting
parameter_list|(
name|int
name|interval
parameter_list|,
name|DifferenceListener
name|l
parameter_list|)
function_decl|;
specifier|public
name|void
name|stopDetecting
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

