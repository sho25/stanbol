begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_package
package|package
name|net
operator|.
name|paoding
operator|.
name|analysis
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_comment
comment|/**  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *   * @since 2.0.0  */
end_comment

begin_class
specifier|public
class|class
name|Constants
block|{
comment|/** 	 * "词典目录安装目录"配置的优先级别 	 *<p> 	 * "system-env"以及其他非"this"的配置，表示优先从环境变量PAODING_DIC_HOME的值找词典目录安装环境 	 * "this"表示优先从本配置文件的paoding.dic.home配置项找<br> 	 * 只有在高优先级没有配置，才会找低优先级的配置。 默认环境变量的优先级别高于paoding-analysis.properties属性文件配置。 	 */
specifier|public
specifier|static
specifier|final
name|String
name|DIC_HOME_CONFIG_FIRST
init|=
literal|"paoding.dic.home.config-first"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DIC_HOME_CONFIG_FIRST_DEFAULT
init|=
literal|"system-env"
decl_stmt|;
comment|/** 	 * 词典安装目录环境变量名 	 */
specifier|public
specifier|static
specifier|final
name|String
name|ENV_PAODING_DIC_HOME
init|=
literal|"PAODING_DIC_HOME"
decl_stmt|;
comment|// -------------------------------------------------------------
comment|/** 	 * 词典安装目录 	 *<p> 	 * 默认值为null，以在环境变量和配置文件都没有配置paoding.dic.home的情况下，让PaodingMaker尝试从当前工作目录下、类路径下探索是否存在dic目录 	 */
specifier|public
specifier|static
specifier|final
name|String
name|DIC_HOME
init|=
literal|"paoding.dic.home"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DIC_HOME_DEFAULT
init|=
literal|null
decl_stmt|;
comment|// -------------------------------------------------------------
comment|//
specifier|public
specifier|static
specifier|final
name|String
name|DIC_CHARSET
init|=
literal|"paoding.dic.charset"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DIC_CHARSET_DEFAULT
init|=
literal|"UTF-8"
decl_stmt|;
comment|// dictionary word length limit
specifier|public
specifier|static
specifier|final
name|String
name|DIC_MAXWORDLEN
init|=
literal|"paoding.dic.maxWordLen"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DIC_MAXWORDLEN_DEFAULT
init|=
literal|"0"
decl_stmt|;
comment|// -------------------------------------------------------------
comment|// dictionaries which are skip
specifier|public
specifier|static
specifier|final
name|String
name|DIC_SKIP_PREFIX
init|=
literal|"paoding.dic.skip.prefix"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DIC_SKIP_PREFIX_DEFAULT
init|=
literal|"x-"
decl_stmt|;
comment|// -------------------------------------------------------------
comment|// chinese/cjk charactors that will not token
specifier|public
specifier|static
specifier|final
name|String
name|DIC_NOISE_CHARACTOR
init|=
literal|"paoding.dic.noise-charactor"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DIC_NOISE_CHARACTOR_DEFAULT
init|=
literal|"x-noise-charactor"
decl_stmt|;
comment|// -------------------------------------------------------------
comment|// chinese/cjk words that will not token
specifier|public
specifier|static
specifier|final
name|String
name|DIC_NOISE_WORD
init|=
literal|"paoding.dic.noise-word"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DIC_NOISE_WORD_DEFAULT
init|=
literal|"x-noise-word"
decl_stmt|;
comment|// -------------------------------------------------------------
comment|// unit words, like "ge", "zhi", ...
specifier|public
specifier|static
specifier|final
name|String
name|DIC_UNIT
init|=
literal|"paoding.dic.unit"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DIC_UNIT_DEFAULT
init|=
literal|"x-unit"
decl_stmt|;
comment|// -------------------------------------------------------------
comment|// like "Wang", "Zhang", ...
specifier|public
specifier|static
specifier|final
name|String
name|DIC_CONFUCIAN_FAMILY_NAME
init|=
literal|"paoding.dic.confucian-family-name"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DIC_CONFUCIAN_FAMILY_NAME_DEFAULT
init|=
literal|"x-confucian-family-name"
decl_stmt|;
comment|// -------------------------------------------------------------
comment|// like
specifier|public
specifier|static
specifier|final
name|String
name|DIC_FOR_COMBINATORICS
init|=
literal|"paoding.dic.for-combinatorics"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DIC_FOR_COMBINATORICS_DEFAULT
init|=
literal|"x-for-combinatorics"
decl_stmt|;
comment|// -------------------------------------------------------------
comment|// like
specifier|public
specifier|static
specifier|final
name|String
name|DIC_DETECTOR_INTERVAL
init|=
literal|"paoding.dic.detector.interval"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DIC_DETECTOR_INTERVAL_DEFAULT
init|=
literal|"60"
decl_stmt|;
comment|// -------------------------------------------------------------
comment|// like "default", "max", ...
specifier|public
specifier|static
specifier|final
name|String
name|ANALYZER_MODE
init|=
literal|"paoding.analyzer.mode"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ANALYZER_MOE_DEFAULT
init|=
literal|"most-words"
decl_stmt|;
comment|// -------------------------------------------------------------
comment|//
specifier|public
specifier|static
specifier|final
name|String
name|ANALYZER_DICTIONARIES_COMPILER
init|=
literal|"paoding.analyzer.dictionaries.compiler"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ANALYZER_DICTIONARIES_COMPILER_DEFAULT
init|=
literal|null
decl_stmt|;
comment|// -------------------------------------------------------------
specifier|private
specifier|static
specifier|final
name|Map
comment|/*<String, String> */
name|map
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
static|static
block|{
name|map
operator|.
name|put
argument_list|(
name|DIC_HOME_CONFIG_FIRST
argument_list|,
name|DIC_HOME_CONFIG_FIRST_DEFAULT
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|DIC_HOME
argument_list|,
name|DIC_HOME_DEFAULT
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|DIC_CHARSET
argument_list|,
name|DIC_CHARSET_DEFAULT
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|DIC_MAXWORDLEN
argument_list|,
name|DIC_MAXWORDLEN_DEFAULT
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|DIC_SKIP_PREFIX
argument_list|,
name|DIC_SKIP_PREFIX_DEFAULT
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|DIC_NOISE_CHARACTOR
argument_list|,
name|DIC_NOISE_CHARACTOR_DEFAULT
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|DIC_NOISE_WORD
argument_list|,
name|DIC_NOISE_WORD_DEFAULT
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|DIC_UNIT
argument_list|,
name|DIC_UNIT_DEFAULT
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|DIC_CONFUCIAN_FAMILY_NAME
argument_list|,
name|DIC_CONFUCIAN_FAMILY_NAME_DEFAULT
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|DIC_FOR_COMBINATORICS
argument_list|,
name|DIC_FOR_COMBINATORICS_DEFAULT
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|DIC_DETECTOR_INTERVAL
argument_list|,
name|DIC_DETECTOR_INTERVAL_DEFAULT
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|ANALYZER_MODE
argument_list|,
name|ANALYZER_MOE_DEFAULT
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|ANALYZER_DICTIONARIES_COMPILER
argument_list|,
name|ANALYZER_DICTIONARIES_COMPILER_DEFAULT
argument_list|)
expr_stmt|;
block|}
comment|//
specifier|public
specifier|static
specifier|final
name|String
name|KNIFE_CLASS
init|=
literal|"paoding.knife.class."
decl_stmt|;
specifier|public
specifier|static
name|String
name|getProperty
parameter_list|(
name|Properties
name|p
parameter_list|,
name|String
name|name
parameter_list|)
block|{
return|return
name|p
operator|.
name|getProperty
argument_list|(
name|name
argument_list|,
operator|(
name|String
operator|)
name|map
operator|.
name|get
argument_list|(
name|name
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

