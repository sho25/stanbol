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
name|it
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|entity
operator|.
name|InputStreamEntity
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
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
comment|/**  * Tests sending EnhancementRequests to single Engines  *  */
end_comment

begin_class
specifier|public
class|class
name|EngineEnhancementRequestTest
extends|extends
name|EnhancerTestBase
block|{
specifier|public
name|EngineEnhancementRequestTest
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/**      * Tests an normal enhancement request directed to the tika engine       * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testTikaMetadata
parameter_list|()
throws|throws
name|Exception
block|{
name|InputStream
name|in
init|=
name|EngineEnhancementRequestTest
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"testJPEG_EXIF.jpg"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
literal|"Unable to find test resource 'testJPEG_EXIF.jpg'"
argument_list|,
name|in
argument_list|)
expr_stmt|;
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
literal|"/engine/tika"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/rdf+nt"
argument_list|)
operator|.
name|withEntity
argument_list|(
operator|new
name|InputStreamEntity
argument_list|(
name|in
argument_list|,
operator|-
literal|1
argument_list|)
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentRegexp
argument_list|(
comment|//we need not test the extraction results here
comment|//only that the Enhancer REST API works also with engines!
literal|"<http://purl.org/dc/terms/format> \"image/jpeg\""
argument_list|,
literal|"<http://www.w3.org/ns/ma-ont#hasKeyword> \"serbor\" ."
argument_list|,
literal|"<http://www.semanticdesktop.org/ontologies/2007/05/10/nexif#isoSpeedRatings> \"400\""
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests plain text extraction for an request directly sent to the tika       * engine       * @throws Exception      */
annotation|@
name|Test
specifier|public
name|void
name|testPlainTextExtraction
parameter_list|()
throws|throws
name|Exception
block|{
name|InputStream
name|in
init|=
name|EngineEnhancementRequestTest
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"test.pdf"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
literal|"Unable to find test resource 'test.pdf'"
argument_list|,
name|in
argument_list|)
expr_stmt|;
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
literal|"/engine/tika?omitMetadata=true"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/plain"
argument_list|)
operator|.
name|withEntity
argument_list|(
operator|new
name|InputStreamEntity
argument_list|(
name|in
argument_list|,
operator|-
literal|1
argument_list|)
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentRegexp
argument_list|(
comment|//we need not test the extraction results here
comment|//only that the Enhancer REST API works also with engines!
literal|"The Apache Stanbol Enhancer"
argument_list|,
literal|"The Stanbol enhancer can detect famous cities such as Paris"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
