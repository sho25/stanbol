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
operator|.
name|impl
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
name|benchmark
operator|.
name|BenchmarkResult
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
name|benchmark
operator|.
name|TripleMatcherGroup
import|;
end_import

begin_class
specifier|public
class|class
name|BenchmarkResultImpl
implements|implements
name|BenchmarkResult
block|{
specifier|private
specifier|final
name|TripleMatcherGroup
name|tmg
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|successful
decl_stmt|;
specifier|private
name|String
name|info
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|IRI
argument_list|>
name|matchingSubjects
decl_stmt|;
name|BenchmarkResultImpl
parameter_list|(
name|TripleMatcherGroup
name|tmg
parameter_list|,
name|ImmutableGraph
name|graph
parameter_list|)
block|{
name|this
operator|.
name|tmg
operator|=
name|tmg
expr_stmt|;
name|matchingSubjects
operator|=
name|tmg
operator|.
name|getMatchingSubjects
argument_list|(
name|graph
argument_list|)
expr_stmt|;
if|if
condition|(
name|tmg
operator|.
name|isExpectGroup
argument_list|()
condition|)
block|{
if|if
condition|(
name|matchingSubjects
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|successful
operator|=
literal|true
expr_stmt|;
name|info
operator|=
literal|"EXPECT OK"
expr_stmt|;
block|}
else|else
block|{
name|successful
operator|=
literal|false
expr_stmt|;
name|info
operator|=
literal|"EXPECT FAILED"
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|matchingSubjects
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|successful
operator|=
literal|false
expr_stmt|;
name|info
operator|=
literal|"COMPLAINT TRIGGERED"
expr_stmt|;
block|}
else|else
block|{
name|successful
operator|=
literal|true
expr_stmt|;
name|info
operator|=
literal|"NO COMPLAINT"
expr_stmt|;
block|}
block|}
name|info
operator|+=
literal|", matchingSubjects="
operator|+
name|matchingSubjects
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|BenchmarkResult
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|" "
operator|+
operator|(
name|successful
condition|?
literal|"SUCCESSFUL"
else|:
literal|"**FAILED**"
operator|)
operator|+
literal|" ("
operator|+
name|info
operator|+
literal|"): "
operator|+
name|tmg
operator|.
name|getDescription
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|TripleMatcherGroup
name|getTripleMatcherGroup
parameter_list|()
block|{
return|return
name|tmg
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|successful
parameter_list|()
block|{
return|return
name|successful
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getInfo
parameter_list|()
block|{
return|return
name|info
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|IRI
argument_list|>
name|getMatchingSubjects
parameter_list|()
block|{
return|return
name|matchingSubjects
return|;
block|}
block|}
end_class

end_unit

