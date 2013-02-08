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
name|opennlp
operator|.
name|impl
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
name|validateAllTextAnnotations
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|MGraph
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
name|commons
operator|.
name|opennlp
operator|.
name|OpenNLP
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
name|commons
operator|.
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|DataFileProvider
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

begin_class
specifier|public
class|class
name|TestNamedEntityExtractionEnhancementEngine
extends|extends
name|Assert
block|{
specifier|public
specifier|static
specifier|final
name|String
name|SINGLE_SENTENCE
init|=
literal|"Dr Patrick Marshall (1869 - November 1950) was a"
operator|+
literal|" geologist who lived in New Zealand and worked at the University of Otago."
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SINGLE_SENTENCE_WITH_CONTROL_CHARS
init|=
literal|"Dr Patrick Marshall (1869 - November 1950) was a"
operator|+
literal|" \u0014geologist\u0015 who lived in New\tZealand and worked at the University\nof Otago."
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|MULTI_SENTENCES
init|=
literal|"The life of Patrick Marshall\n\n"
operator|+
literal|"Dr Patrick Marshall (1869 - November 1950) was a"
operator|+
literal|" geologist who lived in New Zealand and worked at the"
operator|+
literal|" University of Otago. This is another unrelated sentence"
operator|+
literal|" without any name.\n"
operator|+
literal|"A new paragraph is being written. This paragraph has two sentences."
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|EHEALTH
init|=
literal|"Whereas activation of the HIV-1 enhancer following T-cell "
operator|+
literal|"stimulation is mediated largely through binding of the transcription factor NF-kappa "
operator|+
literal|"B to two adjacent kappa B sites in the HIV-1 long terminal repeat, activation of the "
operator|+
literal|"HIV-2 enhancer in monocytes and T cells is dependent on four cis-acting elements : a "
operator|+
literal|"single kappa B site, two purine-rich binding sites , PuB1 and PuB2 , and a pets site ."
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
name|NEREngineCore
name|nerEngine
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|FAKE_BUNDLE_SYMBOLIC_NAME
init|=
literal|"FAKE_BUNDLE_SYMBOLIC_NAME"
decl_stmt|;
specifier|public
specifier|static
name|OpenNLP
name|openNLP
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|initDataFileProvicer
parameter_list|()
block|{
name|DataFileProvider
name|dataFileProvider
init|=
operator|new
name|ClasspathDataFileProvider
argument_list|(
name|FAKE_BUNDLE_SYMBOLIC_NAME
argument_list|)
decl_stmt|;
name|openNLP
operator|=
operator|new
name|OpenNLP
argument_list|(
name|dataFileProvider
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|setUpServices
parameter_list|()
throws|throws
name|IOException
block|{
name|nerEngine
operator|=
operator|new
name|NEREngineCore
argument_list|(
name|openNLP
argument_list|,
operator|new
name|NEREngineConfig
argument_list|()
argument_list|)
block|{}
expr_stmt|;
block|}
specifier|public
specifier|static
name|ContentItem
name|wrapAsContentItem
parameter_list|(
specifier|final
name|String
name|id
parameter_list|,
specifier|final
name|String
name|text
parameter_list|,
name|String
name|language
parameter_list|)
throws|throws
name|IOException
block|{
name|ContentItem
name|ci
init|=
name|ciFactory
operator|.
name|createContentItem
argument_list|(
operator|new
name|UriRef
argument_list|(
name|id
argument_list|)
argument_list|,
operator|new
name|StringSource
argument_list|(
name|text
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|language
operator|!=
literal|null
condition|)
block|{
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
name|language
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ci
return|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testPersonNamesExtraction
parameter_list|()
block|{
name|Collection
argument_list|<
name|String
argument_list|>
name|names
init|=
name|nerEngine
operator|.
name|extractPersonNames
argument_list|(
name|SINGLE_SENTENCE
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|names
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|names
operator|.
name|contains
argument_list|(
literal|"Patrick Marshall"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testPersonNameOccurrencesExtraction
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|NameOccurrence
argument_list|>
argument_list|>
name|nameOccurrences
init|=
name|nerEngine
operator|.
name|extractPersonNameOccurrences
argument_list|(
name|MULTI_SENTENCES
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|nameOccurrences
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|NameOccurrence
argument_list|>
name|pmOccurrences
init|=
name|nameOccurrences
operator|.
name|get
argument_list|(
literal|"Patrick Marshall"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|pmOccurrences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|pmOccurrences
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|NameOccurrence
name|firstOccurrence
init|=
name|pmOccurrences
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Patrick Marshall"
argument_list|,
name|firstOccurrence
operator|.
name|name
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|12
argument_list|,
name|firstOccurrence
operator|.
name|start
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|28
argument_list|,
name|firstOccurrence
operator|.
name|end
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0.998
argument_list|,
name|firstOccurrence
operator|.
name|confidence
argument_list|,
literal|0.05
argument_list|)
expr_stmt|;
name|NameOccurrence
name|secondOccurrence
init|=
name|pmOccurrences
operator|.
name|get
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Patrick Marshall"
argument_list|,
name|secondOccurrence
operator|.
name|name
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|33
argument_list|,
name|secondOccurrence
operator|.
name|start
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|49
argument_list|,
name|secondOccurrence
operator|.
name|end
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0.997
argument_list|,
name|secondOccurrence
operator|.
name|confidence
argument_list|,
literal|0.05
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testPersonNameOccurrencesExtractionWithControlChars
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|NameOccurrence
argument_list|>
argument_list|>
name|nameOccurrences
init|=
name|nerEngine
operator|.
name|extractPersonNameOccurrences
argument_list|(
name|SINGLE_SENTENCE_WITH_CONTROL_CHARS
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|nameOccurrences
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|NameOccurrence
argument_list|>
name|pmOccurrences
init|=
name|nameOccurrences
operator|.
name|get
argument_list|(
literal|"Patrick Marshall"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|pmOccurrences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|pmOccurrences
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|NameOccurrence
name|firstOccurrence
init|=
name|pmOccurrences
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Patrick Marshall"
argument_list|,
name|firstOccurrence
operator|.
name|name
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|firstOccurrence
operator|.
name|context
operator|.
name|contains
argument_list|(
literal|"\u0014"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|firstOccurrence
operator|.
name|context
operator|.
name|contains
argument_list|(
literal|"\t"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|firstOccurrence
operator|.
name|context
operator|.
name|contains
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testLocationNamesExtraction
parameter_list|()
block|{
name|Collection
argument_list|<
name|String
argument_list|>
name|names
init|=
name|nerEngine
operator|.
name|extractLocationNames
argument_list|(
name|SINGLE_SENTENCE
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|names
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|names
operator|.
name|contains
argument_list|(
literal|"New Zealand"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testComputeEnhancements
parameter_list|()
throws|throws
name|EngineException
throws|,
name|IOException
block|{
name|ContentItem
name|ci
init|=
name|wrapAsContentItem
argument_list|(
literal|"urn:test:content-item:single:sentence"
argument_list|,
name|SINGLE_SENTENCE
argument_list|,
literal|"en"
argument_list|)
decl_stmt|;
name|nerEngine
operator|.
name|computeEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|Map
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
name|nerEngine
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|//adding null as expected for confidence makes it a required property
name|expectedValues
operator|.
name|put
argument_list|(
name|Properties
operator|.
name|ENHANCER_CONFIDENCE
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|MGraph
name|g
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|int
name|textAnnotationCount
init|=
name|validateAllTextAnnotations
argument_list|(
name|g
argument_list|,
name|SINGLE_SENTENCE
argument_list|,
name|expectedValues
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|textAnnotationCount
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testCustomModel
parameter_list|()
throws|throws
name|EngineException
throws|,
name|IOException
block|{
name|ContentItem
name|ci
init|=
name|wrapAsContentItem
argument_list|(
literal|"urn:test:content-item:single:sentence"
argument_list|,
name|EHEALTH
argument_list|,
literal|"en"
argument_list|)
decl_stmt|;
comment|//this test does not use default models
name|nerEngine
operator|.
name|config
operator|.
name|getDefaultModelTypes
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|//but instead a custom model provided by the test data
name|nerEngine
operator|.
name|config
operator|.
name|addCustomNameFinderModel
argument_list|(
literal|"en"
argument_list|,
literal|"bionlp2004-DNA-en.bin"
argument_list|)
expr_stmt|;
name|nerEngine
operator|.
name|config
operator|.
name|setMappedType
argument_list|(
literal|"DNA"
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://www.bootstrep.eu/ontology/GRO#DNA"
argument_list|)
argument_list|)
expr_stmt|;
name|nerEngine
operator|.
name|computeEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|Map
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
name|nerEngine
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|//adding null as expected for confidence makes it a required property
name|expectedValues
operator|.
name|put
argument_list|(
name|Properties
operator|.
name|ENHANCER_CONFIDENCE
argument_list|,
literal|null
argument_list|)
expr_stmt|;
comment|//and dc:type values MUST be the URI set as mapped type
name|expectedValues
operator|.
name|put
argument_list|(
name|Properties
operator|.
name|DC_TYPE
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://www.bootstrep.eu/ontology/GRO#DNA"
argument_list|)
argument_list|)
expr_stmt|;
name|MGraph
name|g
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|int
name|textAnnotationCount
init|=
name|validateAllTextAnnotations
argument_list|(
name|g
argument_list|,
name|EHEALTH
argument_list|,
name|expectedValues
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|7
argument_list|,
name|textAnnotationCount
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
