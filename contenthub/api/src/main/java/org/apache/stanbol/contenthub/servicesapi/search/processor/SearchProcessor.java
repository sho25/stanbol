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
name|processor
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
name|execution
operator|.
name|QueryKeyword
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
name|execution
operator|.
name|SearchContext
import|;
end_import

begin_comment
comment|/**  * The processor interface executes the search operation by running allowed search engines and passing  * {@link SearchContext} to each of them.  *   * @author cihan  */
end_comment

begin_interface
specifier|public
interface|interface
name|SearchProcessor
block|{
comment|/**      * Retrieves the available engines in the Stanbol environment. If a {@link SearchEngine} is actively      * running in the OSGi environment of Stanbol, then it is available.      *       * @return A lisrt of {@link SearchEngine}s.      */
name|List
argument_list|<
name|SearchEngine
argument_list|>
name|listEngines
parameter_list|()
function_decl|;
comment|/**      * Processes the semantic search. It passes the {@link SearchContext} to every {@link SearchEngine}. Each      * {@link SearchEngine} manipulates the {@link SearchContext} one by one.      *       * @param context      *            Initial {@link SearchContext}. This context includes {@link QueryKeyword}s only.      */
name|void
name|processQuery
parameter_list|(
name|SearchContext
name|context
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

