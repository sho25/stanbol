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
operator|.
name|classification
operator|.
name|impl
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
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|DC_LANGUAGE
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
name|validateAllTopicAnnotations
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
name|UnknownHostException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Dictionary
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
name|Hashtable
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
name|celi
operator|.
name|CeliConstants
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
name|testutils
operator|.
name|MockComponentContext
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
name|testutils
operator|.
name|TestUtils
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
name|AfterClass
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

begin_class
specifier|public
class|class
name|CeliClassificationEnhancementEngineTest
block|{
specifier|static
name|CeliClassificationEnhancementEngine
name|classificationEngine
init|=
operator|new
name|CeliClassificationEnhancementEngine
argument_list|()
decl_stmt|;
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
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|CeliClassificationEnhancementEngine
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|TEXT
init|=
literal|"Brigitte Bardot, née  le 28 septembre "
operator|+
literal|"1934 à Paris, est une actrice de cinéma et chanteuse française."
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setUpServices
parameter_list|()
throws|throws
name|IOException
throws|,
name|ConfigurationException
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|,
literal|"celiClassification"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|CeliConstants
operator|.
name|CELI_TEST_ACCOUNT
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|CeliClassificationEnhancementEngine
operator|.
name|SERVICE_URL
argument_list|,
literal|"http://linguagrid.org/LSGrid/ws/dbpedia-classification"
argument_list|)
expr_stmt|;
name|MockComponentContext
name|context
init|=
operator|new
name|MockComponentContext
argument_list|(
name|properties
argument_list|)
decl_stmt|;
name|classificationEngine
operator|.
name|activate
argument_list|(
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|shutdownServices
parameter_list|()
block|{
name|classificationEngine
operator|.
name|deactivate
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|ContentItem
name|wrapAsContentItem
parameter_list|(
specifier|final
name|String
name|text
parameter_list|)
throws|throws
name|IOException
block|{
return|return
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
return|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|tesetEngine
parameter_list|()
throws|throws
name|Exception
block|{
name|ContentItem
name|ci
init|=
name|wrapAsContentItem
argument_list|(
name|TEXT
argument_list|)
decl_stmt|;
try|try
block|{
comment|//add a simple triple to statically define the language of the test
comment|//content
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
name|DC_LANGUAGE
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
literal|"fr"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//unit test should not depend on each other (if possible)
comment|//CeliLanguageIdentifierEnhancementEngineTest.addEnanchements(ci);
name|classificationEngine
operator|.
name|computeEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|TestUtils
operator|.
name|logEnhancements
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
name|classificationEngine
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
name|textAnnoNum
init|=
name|EnhancementStructureHelper
operator|.
name|validateAllTextAnnotations
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|TEXT
argument_list|,
name|expectedValues
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Only a single fise:TextAnnotation is expeted"
argument_list|,
literal|1
argument_list|,
name|textAnnoNum
argument_list|)
expr_stmt|;
name|int
name|numTopicAnnotations
init|=
name|validateAllTopicAnnotations
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|expectedValues
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"No TpocisAnnotations found"
argument_list|,
name|numTopicAnnotations
operator|>
literal|0
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|EngineException
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getCause
argument_list|()
operator|!=
literal|null
operator|&&
name|e
operator|.
name|getCause
argument_list|()
operator|instanceof
name|UnknownHostException
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Celi Service not reachable -> offline? -> deactivate test"
argument_list|)
expr_stmt|;
return|return;
block|}
throw|throw
name|e
throw|;
block|}
block|}
block|}
end_class

end_unit

