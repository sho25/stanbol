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
name|entityhub
operator|.
name|query
operator|.
name|clerezza
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
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|junit
operator|.
name|Test
import|;
end_import

begin_class
specifier|public
class|class
name|SparqlQueryUtilsTest
block|{
annotation|@
name|Test
specifier|public
name|void
name|testCreateFullTextQueryString
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|keywords
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"test"
argument_list|,
literal|"keyword"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"\"test\" OR \"keyword\""
argument_list|,
name|SparqlQueryUtils
operator|.
name|createFullTextQueryString
argument_list|(
name|keywords
argument_list|)
argument_list|)
expr_stmt|;
name|keywords
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"test keyword"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"(\"test\" AND \"keyword\")"
argument_list|,
name|SparqlQueryUtils
operator|.
name|createFullTextQueryString
argument_list|(
name|keywords
argument_list|)
argument_list|)
expr_stmt|;
name|keywords
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"'test' \"keyword\""
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"(\"'test'\" AND \"\\\"keyword\\\"\")"
argument_list|,
name|SparqlQueryUtils
operator|.
name|createFullTextQueryString
argument_list|(
name|keywords
argument_list|)
argument_list|)
expr_stmt|;
name|keywords
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"1 Alpha ? Numeric Test ."
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"(\"1\" AND \"Alpha\" AND \"Numeric\" AND \"Test\")"
argument_list|,
name|SparqlQueryUtils
operator|.
name|createFullTextQueryString
argument_list|(
name|keywords
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

