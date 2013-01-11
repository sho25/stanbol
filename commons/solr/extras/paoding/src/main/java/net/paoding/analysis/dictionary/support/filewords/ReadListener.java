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
operator|.
name|support
operator|.
name|filewords
package|;
end_package

begin_comment
comment|/**  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *   * @since 1.0  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ReadListener
block|{
specifier|public
name|boolean
name|onFileBegin
parameter_list|(
name|String
name|file
parameter_list|)
function_decl|;
specifier|public
name|void
name|onFileEnd
parameter_list|(
name|String
name|file
parameter_list|)
function_decl|;
specifier|public
name|void
name|onWord
parameter_list|(
name|String
name|word
parameter_list|)
function_decl|;
block|}
end_interface

end_unit
