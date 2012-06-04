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
name|enhancer
operator|.
name|engines
operator|.
name|celi
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|engines
operator|.
name|celi
operator|.
name|langid
operator|.
name|impl
operator|.
name|CeliLanguageIdentifierEnhancementEngineTest
operator|.
name|CELI_LANGID_SERVICE_URL
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
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|soap
operator|.
name|SOAPException
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
name|engines
operator|.
name|celi
operator|.
name|langid
operator|.
name|impl
operator|.
name|LanguageIdentifierClientHTTP
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
name|CeliHttpTest
block|{
comment|/**      * None Existing user account should throw an IOException with       * HTTP Error 401 Unauthorized.      *       * @throws IOException      * @throws SOAPException      */
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IOException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testNonExistentAccountAuthentification
parameter_list|()
throws|throws
name|IOException
throws|,
name|SOAPException
block|{
name|LanguageIdentifierClientHTTP
name|testClient
init|=
operator|new
name|LanguageIdentifierClientHTTP
argument_list|(
operator|new
name|URL
argument_list|(
name|CELI_LANGID_SERVICE_URL
argument_list|)
argument_list|,
literal|"nonexistent:useraccount"
argument_list|)
decl_stmt|;
name|testClient
operator|.
name|guessQueryLanguage
argument_list|(
literal|"This is a dummy request"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Also illegal formatted user account are expected to throw a IOException      * with a HTTP status code 4** (Bad Request)      *       * @throws IOException      * @throws SOAPException      */
comment|//@Test(expected=IOException.class) Currently fails
specifier|public
name|void
name|testIllegalFormattedAuthentification
parameter_list|()
throws|throws
name|IOException
throws|,
name|SOAPException
block|{
name|LanguageIdentifierClientHTTP
name|testClient
init|=
operator|new
name|LanguageIdentifierClientHTTP
argument_list|(
operator|new
name|URL
argument_list|(
name|CELI_LANGID_SERVICE_URL
argument_list|)
argument_list|,
literal|"illeagalFormatted"
argument_list|)
decl_stmt|;
name|testClient
operator|.
name|guessQueryLanguage
argument_list|(
literal|"This is a dummy request"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

