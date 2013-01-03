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
name|analyzer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
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
name|Constants
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
name|analyzer
operator|.
name|estimate
operator|.
name|TryPaodingAnalyzer
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
name|knife
operator|.
name|Knife
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
name|knife
operator|.
name|Paoding
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
name|knife
operator|.
name|PaodingMaker
import|;
end_import

begin_comment
comment|/**  * PaodingAnalyzer是基于“庖丁解牛”框架的Lucene词语分析器，是“庖丁解牛”框架对Lucene的适配器。  *<p>  *   * PaodingAnalyzer是线程安全的：并发情况下使用同一个PaodingAnalyzer实例是可行的。<br>  * PaodingAnalyzer是可复用的：推荐多次同一个PaodingAnalyzer实例。  *<p>  *   * PaodingAnalyzer自动读取类路径下的paoding-analysis.properties属性文件，装配PaodingAnalyzer  *<p>  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *   * @see PaodingAnalyzerBean  *   * @since 1.0  *   */
end_comment

begin_class
specifier|public
class|class
name|PaodingAnalyzer
extends|extends
name|PaodingAnalyzerBean
block|{
comment|/** 	 * 根据类路径下的paoding-analysis.properties构建一个PaodingAnalyzer对象 	 *<p> 	 * 在一个JVM中，可多次创建，而并不会多次读取属性文件，不会重复读取字典。 	 */
specifier|public
name|PaodingAnalyzer
parameter_list|()
block|{
name|this
argument_list|(
name|PaodingMaker
operator|.
name|DEFAULT_PROPERTIES_PATH
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * @param propertiesPath null表示使用类路径下的paoding-analysis.properties 	 */
specifier|public
name|PaodingAnalyzer
parameter_list|(
name|String
name|propertiesPath
parameter_list|)
block|{
name|init
argument_list|(
name|propertiesPath
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|init
parameter_list|(
name|String
name|propertiesPath
parameter_list|)
block|{
comment|// 根据PaodingMaker说明，
comment|// 1、多次调用getProperties()，返回的都是同一个properties实例(只要属性文件没发生过修改)
comment|// 2、相同的properties实例，PaodingMaker也将返回同一个Paoding实例
comment|// 根据以上1、2点说明，在此能够保证多次创建PaodingAnalyzer并不会多次装载属性文件和词典
if|if
condition|(
name|propertiesPath
operator|==
literal|null
condition|)
block|{
name|propertiesPath
operator|=
name|PaodingMaker
operator|.
name|DEFAULT_PROPERTIES_PATH
expr_stmt|;
block|}
name|Properties
name|properties
init|=
name|PaodingMaker
operator|.
name|getProperties
argument_list|(
name|propertiesPath
argument_list|)
decl_stmt|;
name|String
name|mode
init|=
name|Constants
operator|.
name|getProperty
argument_list|(
name|properties
argument_list|,
name|Constants
operator|.
name|ANALYZER_MODE
argument_list|)
decl_stmt|;
name|Paoding
name|paoding
init|=
name|PaodingMaker
operator|.
name|make
argument_list|(
name|properties
argument_list|)
decl_stmt|;
name|setKnife
argument_list|(
name|paoding
argument_list|)
expr_stmt|;
name|setMode
argument_list|(
name|mode
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * 本方法为PaodingAnalyzer附带的测试评估方法。<br> 	 * 执行之可以查看分词效果。以下任选一种方式进行: 	 *<p> 	 *  	 * java net...PaodingAnalyzer<br> 	 * java net...PaodingAnalyzer --help<br> 	 * java net...PaodingAnalyzer 中华人民共和国<br> 	 * java net...PaodingAnalyzer -m max 中华人民共和国<br> 	 * java net...PaodingAnalyzer -f c:/text.txt<br> 	 * java net...PaodingAnalyzer -f c:/text.txt -c utf-8<br> 	 *  	 * @param args 	 */
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
if|if
condition|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"paoding.try.app"
argument_list|)
operator|==
literal|null
condition|)
block|{
name|System
operator|.
name|setProperty
argument_list|(
literal|"paoding.try.app"
argument_list|,
literal|"PaodingAnalyzer"
argument_list|)
expr_stmt|;
name|System
operator|.
name|setProperty
argument_list|(
literal|"paoding.try.cmd"
argument_list|,
literal|"java PaodingAnalyzer"
argument_list|)
expr_stmt|;
block|}
name|TryPaodingAnalyzer
operator|.
name|main
argument_list|(
name|args
argument_list|)
expr_stmt|;
block|}
comment|// --------------------------------------------------
comment|/** 	 * @param knife 	 * @param default_mode 	 * @deprecated 	 */
specifier|public
name|PaodingAnalyzer
parameter_list|(
name|Knife
name|knife
parameter_list|,
name|int
name|mode
parameter_list|)
block|{
name|super
argument_list|(
name|knife
argument_list|,
name|mode
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * 等价于maxMode() 	 *  	 * @param knife 	 * @return 	 * @deprecated 	 */
specifier|public
specifier|static
name|PaodingAnalyzer
name|queryMode
parameter_list|(
name|Knife
name|knife
parameter_list|)
block|{
return|return
name|maxMode
argument_list|(
name|knife
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param knife 	 * @return 	 * @deprecated 	 */
specifier|public
specifier|static
name|PaodingAnalyzer
name|defaultMode
parameter_list|(
name|Knife
name|knife
parameter_list|)
block|{
return|return
operator|new
name|PaodingAnalyzer
argument_list|(
name|knife
argument_list|,
name|MOST_WORDS_MODE
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param knife 	 * @return 	 * @deprecated 	 */
specifier|public
specifier|static
name|PaodingAnalyzer
name|maxMode
parameter_list|(
name|Knife
name|knife
parameter_list|)
block|{
return|return
operator|new
name|PaodingAnalyzer
argument_list|(
name|knife
argument_list|,
name|MAX_WORD_LENGTH_MODE
argument_list|)
return|;
block|}
comment|/** 	 * 等价于defaultMode() 	 *  	 * @param knife 	 * @return 	 * @deprecated 	 *  	 */
specifier|public
specifier|static
name|PaodingAnalyzer
name|writerMode
parameter_list|(
name|Knife
name|knife
parameter_list|)
block|{
return|return
name|defaultMode
argument_list|(
name|knife
argument_list|)
return|;
block|}
block|}
end_class

end_unit

