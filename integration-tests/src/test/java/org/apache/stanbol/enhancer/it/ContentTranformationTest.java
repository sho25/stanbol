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
name|it
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
name|it
operator|.
name|MultipartContentItemTestUtils
operator|.
name|getHTMLContent
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
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_comment
comment|/**  * This tests RESTful API extensions to the Stanbol Enhancer as described by  * STANBOL-481  */
end_comment

begin_class
specifier|public
class|class
name|ContentTranformationTest
extends|extends
name|EnhancerTestBase
block|{
specifier|public
specifier|static
specifier|final
name|String
index|[]
name|TEXT_CONTENT
init|=
operator|new
name|String
index|[]
block|{
literal|"Stanbol Content Transformation"
block|,
literal|"The multipart content API of Apache Stanbol allows to directly "
operator|+
literal|"request transformed content by adding the \"omitMetadata=true\" "
operator|+
literal|"query parameter and setting the \"Accept\" header to the target"
operator|+
literal|"content type."
block|,
literal|"This feature can be used with any enhancement chain that "
operator|+
literal|"incudles an Engine that provides the required content transcoding"
operator|+
literal|"functionality. However because extracted metadata are omitted by"
operator|+
literal|"such requests it is best used with enhancement chains that only"
operator|+
literal|"contains such engines."
block|}
decl_stmt|;
specifier|public
name|ContentTranformationTest
parameter_list|()
block|{
comment|//for now use the language chain to test transforming
name|super
argument_list|(
name|getChainEndpoint
argument_list|(
literal|"language"
argument_list|)
argument_list|,
literal|"tika"
argument_list|,
literal|"langid"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testHtml2PlainText
parameter_list|()
throws|throws
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
name|getEndpoint
argument_list|()
operator|+
literal|"?omitMetadata=true"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/plain"
argument_list|)
operator|.
name|withContent
argument_list|(
name|getHTMLContent
argument_list|(
name|TEXT_CONTENT
argument_list|)
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentType
argument_list|(
literal|"text/plain"
argument_list|)
operator|.
name|assertContentContains
argument_list|(
name|TEXT_CONTENT
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

