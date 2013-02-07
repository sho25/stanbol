begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
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
name|entitylinking
package|;
end_package

begin_comment
comment|/**  * Interface used by the {@link EntityhubLinkingEngine} to tokenize labels of  * Entities suggested by the EntitySearcher  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|LabelTokenizer
block|{
comment|/**      * Key used to configure the languages supported for tokenizing labels.      * If not present the assumption is that the tokenizer supports all languages.      */
name|String
name|SUPPORTED_LANUAGES
init|=
literal|"enhancer.engines.entitylinking.labeltokenizer.languages"
decl_stmt|;
comment|/**      * Tokenizes the parsed label in the parsed language      * @param label the label      * @param language the language of the lable or<code>null</code> if      * not known      * @return the tokenized label      */
name|String
index|[]
name|tokenize
parameter_list|(
name|String
name|label
parameter_list|,
name|String
name|language
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

