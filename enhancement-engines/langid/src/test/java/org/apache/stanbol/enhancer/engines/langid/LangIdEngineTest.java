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
package|;
end_package

begin_import
import|import static
name|junit
operator|.
name|framework
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
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|test
operator|.
name|helper
operator|.
name|EnhancementStructureHelper
operator|.
name|validateAllEntityAnnotations
import|;
end_import

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
name|test
operator|.
name|helper
operator|.
name|EnhancementStructureHelper
operator|.
name|validateAllTextAnnotations
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
name|HashMap
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
name|rdf
operator|.
name|core
operator|.
name|LiteralFactory
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
name|RDFTerm
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
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|contentitem
operator|.
name|inmemory
operator|.
name|InMemoryContentItemFactory
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
name|langid
operator|.
name|LangIdEnhancementEngine
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
name|servicesapi
operator|.
name|ContentItem
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
name|servicesapi
operator|.
name|ContentItemFactory
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
name|servicesapi
operator|.
name|EngineException
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
name|servicesapi
operator|.
name|EnhancementEngine
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
name|servicesapi
operator|.
name|helper
operator|.
name|EnhancementEngineHelper
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
name|servicesapi
operator|.
name|impl
operator|.
name|StringSource
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
name|servicesapi
operator|.
name|rdf
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
name|tika
operator|.
name|language
operator|.
name|LanguageIdentifier
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
name|osgi
operator|.
name|service
operator|.
name|cm
operator|.
name|ConfigurationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
import|;
end_import

begin_comment
comment|/**  * {@link LangIdEngineTest} is a test class for {@link TextCategorizer}.  *  * @author Joerg Steffen, DFKI  * @version $Id: LangIdTest.java 1145590 2011-07-12 13:26:39Z wkasper $  */
end_comment

begin_class
specifier|public
class|class
name|LangIdEngineTest
block|{
specifier|private
specifier|static
specifier|final
name|ContentItemFactory
name|ciFactory
init|=
name|InMemoryContentItemFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|TEST_FILE_NAME
init|=
literal|"en.txt"
decl_stmt|;
comment|/**      * This contains the text used for testing      */
specifier|private
specifier|static
name|String
name|text
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
name|LanguageIdentifier
operator|.
name|initProfiles
argument_list|()
expr_stmt|;
name|InputStream
name|in
init|=
name|LangIdEngineTest
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|TEST_FILE_NAME
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"failed to load resource "
operator|+
name|TEST_FILE_NAME
argument_list|,
name|in
argument_list|)
expr_stmt|;
name|text
operator|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests the language identification.      *      * @throws IOException if there is an error when reading the text      */
annotation|@
name|Test
specifier|public
name|void
name|testLangId
parameter_list|()
throws|throws
name|IOException
block|{
name|LanguageIdentifier
name|tc
init|=
operator|new
name|LanguageIdentifier
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|String
name|language
init|=
name|tc
operator|.
name|getLanguage
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"en"
argument_list|,
name|language
argument_list|)
expr_stmt|;
block|}
comment|/**      * Test the engine and validates the created enhancements      * @throws EngineException      * @throws IOException      * @throws ConfigurationException      */
annotation|@
name|Test
specifier|public
name|void
name|testEngine
parameter_list|()
throws|throws
name|EngineException
throws|,
name|IOException
throws|,
name|ConfigurationException
block|{
name|LangIdEnhancementEngine
name|langIdEngine
init|=
operator|new
name|LangIdEnhancementEngine
argument_list|()
decl_stmt|;
name|ComponentContext
name|context
init|=
operator|new
name|MockComponentContext
argument_list|()
decl_stmt|;
name|context
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|,
literal|"langid"
argument_list|)
expr_stmt|;
name|langIdEngine
operator|.
name|activate
argument_list|(
name|context
argument_list|)
expr_stmt|;
name|ContentItem
name|ci
init|=
name|ciFactory
operator|.
name|createContentItem
argument_list|(
operator|new
name|StringSource
argument_list|(
name|text
argument_list|)
argument_list|)
decl_stmt|;
name|langIdEngine
operator|.
name|computeEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|RDFTerm
argument_list|>
name|expectedValues
init|=
operator|new
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|RDFTerm
argument_list|>
argument_list|()
decl_stmt|;
name|expectedValues
operator|.
name|put
argument_list|(
name|Properties
operator|.
name|ENHANCER_EXTRACTED_FROM
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
expr_stmt|;
name|expectedValues
operator|.
name|put
argument_list|(
name|Properties
operator|.
name|DC_CREATOR
argument_list|,
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createTypedLiteral
argument_list|(
name|langIdEngine
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|textAnnotationCount
init|=
name|validateAllTextAnnotations
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|text
argument_list|,
name|expectedValues
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"A single TextAnnotation is expected"
argument_list|,
literal|1
argument_list|,
name|textAnnotationCount
argument_list|)
expr_stmt|;
comment|//even through this tests do not validate service quality but rather
comment|//the correct integration of the CELI service as EnhancementEngine
comment|//we expect the "en" is detected for the parsed text
name|assertEquals
argument_list|(
literal|"The detected language for text '"
operator|+
name|text
operator|+
literal|"' MUST BE 'en'"
argument_list|,
literal|"en"
argument_list|,
name|EnhancementEngineHelper
operator|.
name|getLanguage
argument_list|(
name|ci
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|entityAnnoNum
init|=
name|validateAllEntityAnnotations
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|expectedValues
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"No EntityAnnotations are expected"
argument_list|,
literal|0
argument_list|,
name|entityAnnoNum
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

