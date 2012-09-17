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
name|dbpspotlight
operator|.
name|spot
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
name|servicesapi
operator|.
name|EnhancementEngine
operator|.
name|ENHANCE_ASYNC
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
name|assertFalse
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
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
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
name|rdf
operator|.
name|core
operator|.
name|Resource
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
name|UriRef
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
name|impl
operator|.
name|PlainLiteralImpl
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
name|impl
operator|.
name|TripleImpl
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
name|dbpspotlight
operator|.
name|Constants
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
name|dbpspotlight
operator|.
name|model
operator|.
name|SurfaceForm
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
name|Blob
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
name|helper
operator|.
name|ContentItemHelper
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
name|stanbol
operator|.
name|enhancer
operator|.
name|test
operator|.
name|helper
operator|.
name|EnhancementStructureHelper
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
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * This class provides a JUnit test for DBpedia Spotlight Spot  * EnhancementEngine.  *   * @author Iavor Jelev, babelmonkeys / GzEvD  */
end_comment

begin_class
specifier|public
class|class
name|DBPSpotlightSpotEnhancementTest
block|{
comment|/** 	 * This contains the logger. 	 */
specifier|private
specifier|static
specifier|final
name|Logger
name|LOG
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|DBPSpotlightSpotEnhancementTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|String
name|SPL_URL
init|=
name|System
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|PARAM_URL_KEY
argument_list|)
operator|==
literal|null
condition|?
literal|"http://spotlight.dbpedia.org/rest/spot"
else|:
operator|(
name|String
operator|)
name|System
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|PARAM_URL_KEY
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|String
name|TEST_TEXT
init|=
literal|"President Obama is meeting Angela Merkel in Berlin on Monday"
decl_stmt|;
specifier|private
specifier|static
name|DBPSpotlightSpotEnhancementEngine
name|dbpslight
decl_stmt|;
specifier|private
specifier|static
name|ContentItemFactory
name|ciFactory
init|=
name|InMemoryContentItemFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|private
name|ContentItem
name|ci
decl_stmt|;
specifier|private
specifier|static
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|Blob
argument_list|>
name|textContentPart
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|oneTimeSetup
parameter_list|()
throws|throws
name|Exception
block|{
comment|//and the enhancement engine instance
name|dbpslight
operator|=
operator|new
name|DBPSpotlightSpotEnhancementEngine
argument_list|(
operator|new
name|URL
argument_list|(
name|SPL_URL
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|initTest
parameter_list|()
throws|throws
name|IOException
block|{
comment|//create the contentItem for testing
name|ci
operator|=
name|ciFactory
operator|.
name|createContentItem
argument_list|(
operator|new
name|StringSource
argument_list|(
name|TEST_TEXT
argument_list|)
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|textContentPart
operator|=
name|ContentItemHelper
operator|.
name|getBlob
argument_list|(
name|ci
argument_list|,
name|Collections
operator|.
name|singleton
argument_list|(
literal|"text/plain"
argument_list|)
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|textContentPart
argument_list|)
expr_stmt|;
comment|//add the language of the text
name|ci
operator|.
name|getMetadata
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|Properties
operator|.
name|DC_LANGUAGE
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
literal|"en"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
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
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEntityExtraction
parameter_list|()
throws|throws
name|Exception
block|{
name|Collection
argument_list|<
name|SurfaceForm
argument_list|>
name|entities
decl_stmt|;
name|entities
operator|=
name|dbpslight
operator|.
name|doPostRequest
argument_list|(
name|TEST_TEXT
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
expr_stmt|;
name|LOG
operator|.
name|info
argument_list|(
literal|"Found entities: {}"
argument_list|,
name|entities
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|LOG
operator|.
name|debug
argument_list|(
literal|"Entities:\n{}"
argument_list|,
name|entities
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
literal|"No entities were found!"
argument_list|,
name|entities
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testCanEnhance
parameter_list|()
throws|throws
name|EngineException
block|{
name|assertEquals
argument_list|(
name|ENHANCE_ASYNC
argument_list|,
name|dbpslight
operator|.
name|canEnhance
argument_list|(
name|ci
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Validates the Enhancements created by this engine 	 * @throws EngineException 	 */
annotation|@
name|Test
specifier|public
name|void
name|testEnhancement
parameter_list|()
throws|throws
name|EngineException
block|{
name|dbpslight
operator|.
name|computeEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|HashMap
argument_list|<
name|UriRef
argument_list|,
name|Resource
argument_list|>
name|expectedValues
init|=
operator|new
name|HashMap
argument_list|<
name|UriRef
argument_list|,
name|Resource
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
name|dbpslight
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|EnhancementStructureHelper
operator|.
name|validateAllTextAnnotations
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|TEST_TEXT
argument_list|,
name|expectedValues
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

