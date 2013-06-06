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
name|Iterator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|Token
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
name|Collector
import|;
end_import

begin_comment
comment|/**  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *  * @since 1.1  */
end_comment

begin_interface
specifier|public
interface|interface
name|TokenCollector
extends|extends
name|Collector
block|{
comment|/** 	 *  	 * @return 	 */
specifier|public
name|Iterator
argument_list|<
name|Token
argument_list|>
name|iterator
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

