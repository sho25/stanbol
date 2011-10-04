begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|execution
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|engine
operator|.
name|SearchEngine
import|;
end_import

begin_comment
comment|/**  * The interface to represent a single query keyword and its related resources. This is a regular  * {@link Keyword} in addition with the related keywords. Related keywords are attached to the  * {@link QueryKeyword} as the {@link SearchEngine}s execute.  *   * @author cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|QueryKeyword
extends|extends
name|Keyword
block|{
comment|/**      * If any {@link SearchEngine} attaches a {@link Keyword} to this {@link QueryKeyword}, this function      * returns a list of them.      *       * @return A list of {@link Keyword}.      */
name|List
argument_list|<
name|Keyword
argument_list|>
name|getRelatedKeywords
parameter_list|()
function_decl|;
comment|/**      * Adds a {@link Keyword} as related with this {@link QueryKeyword}. A {@link SearchEngine}      *       * @param keyword      *            The {@link Keyword} to be added as a related keyword to this {@link QueryKeyword}.      */
name|void
name|addRelatedKeyword
parameter_list|(
name|Keyword
name|keyword
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

