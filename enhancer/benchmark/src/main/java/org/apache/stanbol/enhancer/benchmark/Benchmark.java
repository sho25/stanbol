begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements. See the NOTICE file distributed with this  * work for additional information regarding copyright ownership. The ASF  * licenses this file to You under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the  * License for the specific language governing permissions and limitations under  * the License.  */
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
name|benchmark
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Graph
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|Chain
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentItemFactory
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|EnhancementException
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|EnhancementJobManager
import|;
end_import

begin_comment
comment|/** A Benchmark is a named List of {@link TripleMatcherGroup} */
end_comment

begin_interface
specifier|public
interface|interface
name|Benchmark
extends|extends
name|List
argument_list|<
name|TripleMatcherGroup
argument_list|>
block|{
comment|/** Benchmark name */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/** Benchmark input text */
name|String
name|getInputText
parameter_list|()
function_decl|;
comment|/** The enhancement chain used to execute the test */
name|Chain
name|getChain
parameter_list|()
function_decl|;
comment|/** Execute the benchmark and return results       *  @return null */
name|List
argument_list|<
name|BenchmarkResult
argument_list|>
name|execute
parameter_list|(
name|EnhancementJobManager
name|jobManager
parameter_list|,
name|ContentItemFactory
name|ciFactory
parameter_list|)
throws|throws
name|EnhancementException
function_decl|;
comment|/** Return the enhanced Graph of our input text */
name|Graph
name|getGraph
parameter_list|(
name|EnhancementJobManager
name|jobManager
parameter_list|,
name|ContentItemFactory
name|ciFactory
parameter_list|)
throws|throws
name|EnhancementException
function_decl|;
comment|/**      * Setter for the chain      * @param chain the chain      */
name|void
name|setChain
parameter_list|(
name|Chain
name|chain
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

