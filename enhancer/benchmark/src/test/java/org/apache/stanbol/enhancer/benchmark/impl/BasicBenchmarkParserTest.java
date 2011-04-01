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
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertFalse
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

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
name|enhancer
operator|.
name|benchmark
operator|.
name|Benchmark
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

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_class
specifier|public
class|class
name|BasicBenchmarkParserTest
block|{
specifier|public
specifier|static
specifier|final
name|String
name|RESOURCE_ENCODING
init|=
literal|"UTF-8"
decl_stmt|;
specifier|private
name|List
argument_list|<
name|?
extends|extends
name|Benchmark
argument_list|>
name|benchmarks
decl_stmt|;
annotation|@
name|Before
specifier|public
name|void
name|parse
parameter_list|()
throws|throws
name|IOException
block|{
name|benchmarks
operator|=
operator|new
name|BenchmarkParserImpl
argument_list|()
operator|.
name|parse
argument_list|(
name|getTestBenchmark
argument_list|(
literal|"/benchmarks/benchmark1.txt"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|private
name|Reader
name|getTestBenchmark
parameter_list|(
name|String
name|path
parameter_list|)
block|{
specifier|final
name|InputStream
name|is
init|=
name|BasicBenchmarkParserTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|path
argument_list|)
decl_stmt|;
if|if
condition|(
name|is
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Cannot read resource ["
operator|+
name|path
operator|+
literal|"]"
argument_list|)
throw|;
block|}
try|try
block|{
return|return
operator|new
name|InputStreamReader
argument_list|(
name|is
argument_list|,
name|RESOURCE_ENCODING
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Error
argument_list|(
literal|"Unsupported encoding?? "
operator|+
name|RESOURCE_ENCODING
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testStructureAndDescriptions
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
literal|"Number of benchmarks"
argument_list|,
literal|2
argument_list|,
name|benchmarks
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|Iterator
argument_list|<
name|?
extends|extends
name|Benchmark
argument_list|>
name|bit
init|=
name|benchmarks
operator|.
name|iterator
argument_list|()
decl_stmt|;
block|{
specifier|final
name|Benchmark
name|b
init|=
name|bit
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Bob Marley was born in Kingston, Jamaica."
argument_list|,
name|b
operator|.
name|getInputText
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"First benchmark group count"
argument_list|,
literal|2
argument_list|,
name|b
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|Iterator
argument_list|<
name|TripleMatcherGroup
argument_list|>
name|git
init|=
name|b
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Kingston must be found"
argument_list|,
name|git
operator|.
name|next
argument_list|()
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Bob Marley must be found as a musical artist"
argument_list|,
name|git
operator|.
name|next
argument_list|()
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|git
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|{
specifier|final
name|Benchmark
name|b
init|=
name|bit
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Paris Hilton might live in Paris, but she prefers New York."
argument_list|,
name|b
operator|.
name|getInputText
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Second benchmark group count"
argument_list|,
literal|5
argument_list|,
name|b
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|Iterator
argument_list|<
name|TripleMatcherGroup
argument_list|>
name|git
init|=
name|b
operator|.
name|iterator
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<=
literal|5
condition|;
name|i
operator|++
control|)
block|{
name|assertEquals
argument_list|(
literal|"Second benchmark group "
operator|+
name|i
argument_list|,
name|git
operator|.
name|next
argument_list|()
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|assertFalse
argument_list|(
name|git
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testExpectComplain
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
literal|"Number of benchmarks"
argument_list|,
literal|2
argument_list|,
name|benchmarks
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|Iterator
argument_list|<
name|?
extends|extends
name|Benchmark
argument_list|>
name|bit
init|=
name|benchmarks
operator|.
name|iterator
argument_list|()
decl_stmt|;
block|{
comment|// First benchmark has two expect groups and no complains
specifier|final
name|Iterator
argument_list|<
name|TripleMatcherGroup
argument_list|>
name|git
init|=
name|bit
operator|.
name|next
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|git
operator|.
name|next
argument_list|()
operator|.
name|isExpectGroup
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|git
operator|.
name|next
argument_list|()
operator|.
name|isExpectGroup
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|git
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|{
comment|// Second benchmark has 3 expect groups and 2 complains
specifier|final
name|Iterator
argument_list|<
name|TripleMatcherGroup
argument_list|>
name|git
init|=
name|bit
operator|.
name|next
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|git
operator|.
name|next
argument_list|()
operator|.
name|isExpectGroup
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|git
operator|.
name|next
argument_list|()
operator|.
name|isExpectGroup
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|git
operator|.
name|next
argument_list|()
operator|.
name|isExpectGroup
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|git
operator|.
name|next
argument_list|()
operator|.
name|isExpectGroup
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|git
operator|.
name|next
argument_list|()
operator|.
name|isExpectGroup
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|git
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMatcherCount
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
literal|"Number of benchmarks"
argument_list|,
literal|2
argument_list|,
name|benchmarks
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|Iterator
argument_list|<
name|?
extends|extends
name|Benchmark
argument_list|>
name|bit
init|=
name|benchmarks
operator|.
name|iterator
argument_list|()
decl_stmt|;
comment|// Number of matchers in each group of each benchmark
specifier|final
name|int
index|[]
index|[]
name|matchersCount
init|=
block|{
block|{
literal|2
block|,
literal|3
block|}
block|,
block|{
literal|1
block|,
literal|2
block|,
literal|3
block|,
literal|2
block|,
literal|1
block|}
block|}
decl_stmt|;
for|for
control|(
name|int
index|[]
name|counts
range|:
name|matchersCount
control|)
block|{
specifier|final
name|Iterator
argument_list|<
name|TripleMatcherGroup
argument_list|>
name|git
init|=
name|bit
operator|.
name|next
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|count
range|:
name|counts
control|)
block|{
name|assertTrue
argument_list|(
literal|"Iterator has more data at count="
operator|+
name|count
argument_list|,
name|git
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|TripleMatcherGroup
name|g
init|=
name|git
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Matchers count matches for "
operator|+
name|g
argument_list|,
name|count
argument_list|,
name|g
operator|.
name|getMatchers
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

