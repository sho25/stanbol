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
name|Reader
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

begin_interface
specifier|public
interface|interface
name|BenchmarkParser
block|{
comment|// Marker strings in the benchmark text input
specifier|static
specifier|final
name|String
name|COMMENT_MARKER
init|=
literal|"#"
decl_stmt|;
specifier|static
specifier|final
name|String
name|FIELD_SEPARATOR
init|=
literal|":"
decl_stmt|;
specifier|static
specifier|final
name|String
name|INPUT_SECTION_MARKER
init|=
literal|"= INPUT ="
decl_stmt|;
specifier|static
specifier|final
name|String
name|EXPECT_SECTION_MARKER
init|=
literal|"= EXPECT ="
decl_stmt|;
specifier|static
specifier|final
name|String
name|COMPLAIN_SECTION_MARKER
init|=
literal|"= COMPLAIN ="
decl_stmt|;
specifier|static
specifier|final
name|String
name|DESCRIPTION_FIELD
init|=
literal|"Description"
decl_stmt|;
comment|/** Parse the supplied text in a List of Benchmark      *       *  @param r is closed by this method after parsing or failure      *  @throws IOException on I/O or syntax error      */
name|List
argument_list|<
name|?
extends|extends
name|Benchmark
argument_list|>
name|parse
parameter_list|(
name|Reader
name|r
parameter_list|)
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

