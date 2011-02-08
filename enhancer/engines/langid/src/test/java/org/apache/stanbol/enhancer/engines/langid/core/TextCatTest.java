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
name|langid
operator|.
name|core
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
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|IOUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|BeforeClass
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

begin_import
import|import
name|org
operator|.
name|knallgrau
operator|.
name|utils
operator|.
name|textcat
operator|.
name|TextCategorizer
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

begin_comment
comment|/**  * {@link TextCatTest} is a test class for {@link TextCategorizer}.  *  * @author Joerg Steffen, DFKI  * @version $Id$  */
end_comment

begin_class
specifier|public
class|class
name|TextCatTest
block|{
comment|/**      * This contains the text categorizer to test.      */
specifier|private
specifier|static
name|TextCategorizer
name|tc
decl_stmt|;
specifier|private
specifier|static
name|Properties
name|langMap
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
comment|/**      * This initializes the text categorizer.      */
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|oneTimeSetUp
parameter_list|()
throws|throws
name|IOException
block|{
name|tc
operator|=
operator|new
name|TextCategorizer
argument_list|()
expr_stmt|;
name|InputStream
name|in
init|=
name|tc
operator|.
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"languageLabelsMap.txt"
argument_list|)
decl_stmt|;
name|langMap
operator|.
name|load
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
comment|/**      * This test the language identification.      *      * @throws IOException      *             if there is an error when reading the text      */
annotation|@
name|Test
specifier|public
name|void
name|testTextCat
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|testFileName
init|=
literal|"en.txt"
decl_stmt|;
name|InputStream
name|in
init|=
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|testFileName
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"failed to load resource "
operator|+
name|testFileName
argument_list|,
name|in
argument_list|)
expr_stmt|;
name|String
name|text
init|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|in
argument_list|)
decl_stmt|;
name|String
name|language
init|=
name|tc
operator|.
name|categorize
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"en"
argument_list|,
name|langMap
operator|.
name|getProperty
argument_list|(
name|language
argument_list|,
name|language
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

