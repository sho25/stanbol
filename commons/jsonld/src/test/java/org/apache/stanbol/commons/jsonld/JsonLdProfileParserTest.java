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
name|commons
operator|.
name|jsonld
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
name|assertNotNull
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
name|JsonLdProfileParserTest
block|{
annotation|@
name|Test
specifier|public
name|void
name|testParseProfile
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|jsonldInput
init|=
literal|"{\"@context\":{\"iks\":\"http://iks-project.eu/ont/\",\"#types\":{\"person\":\"iks:person\",\"organization\":\"iks:organization\"}}}"
decl_stmt|;
name|JsonLdProfile
name|profile
init|=
name|JsonLdProfileParser
operator|.
name|parseProfile
argument_list|(
name|jsonldInput
argument_list|)
decl_stmt|;
name|String
name|actual
init|=
name|profile
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|expected
init|=
literal|"{\"@context\":{\"iks\":\"http:\\/\\/iks-project.eu\\/ont\\/\",\"#types\":{\"organization\":\"iks:organization\",\"person\":\"iks:person\"}}}"
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|profile
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testParseProfileMultiType
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|jsonldInput
init|=
literal|"{\"@context\":{\"#types\":{\"organization\":\"http:\\/\\/iks-project.eu\\/ont\\/organization\",\"person\":[\"http:\\/\\/iks-project.eu\\/ont\\/person\",\"http:\\/\\/www.schema.org\\/Person\"]}}}"
decl_stmt|;
name|JsonLdProfile
name|profile
init|=
name|JsonLdProfileParser
operator|.
name|parseProfile
argument_list|(
name|jsonldInput
argument_list|)
decl_stmt|;
name|String
name|expected
init|=
literal|"{\"@context\":{\"#types\":{\"organization\":\"http:\\/\\/iks-project.eu\\/ont\\/organization\",\"person\":[\"http:\\/\\/iks-project.eu\\/ont\\/person\",\"http:\\/\\/www.schema.org\\/Person\"]}}}"
decl_stmt|;
name|String
name|actual
init|=
name|profile
operator|.
name|toString
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|profile
argument_list|)
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|private
name|void
name|toConsole
parameter_list|(
name|String
name|actual
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|actual
argument_list|)
expr_stmt|;
name|String
name|s
init|=
name|actual
decl_stmt|;
name|s
operator|=
name|s
operator|.
name|replaceAll
argument_list|(
literal|"\\\\"
argument_list|,
literal|"\\\\\\\\"
argument_list|)
expr_stmt|;
name|s
operator|=
name|s
operator|.
name|replace
argument_list|(
literal|"\""
argument_list|,
literal|"\\\""
argument_list|)
expr_stmt|;
name|s
operator|=
name|s
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|"\\n"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

