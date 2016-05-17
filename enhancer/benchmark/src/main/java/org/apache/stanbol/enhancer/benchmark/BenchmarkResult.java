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
name|Set
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
name|commons
operator|.
name|rdf
operator|.
name|ImmutableGraph
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
import|;
end_import

begin_comment
comment|/** Benchmark result for a single TripleMatcherGroup */
end_comment

begin_interface
specifier|public
interface|interface
name|BenchmarkResult
block|{
comment|/** The TripleMatcherGroup that was evaluated */
name|TripleMatcherGroup
name|getTripleMatcherGroup
parameter_list|()
function_decl|;
comment|/** Was the benchmark successful? */
name|boolean
name|successful
parameter_list|()
function_decl|;
comment|/** More info about success or failure */
name|String
name|getInfo
parameter_list|()
function_decl|;
comment|/** Set of subjects that match our TripleMatcherGroup */
name|Set
argument_list|<
name|IRI
argument_list|>
name|getMatchingSubjects
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

