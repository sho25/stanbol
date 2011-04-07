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
name|ENHANCER_END
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
name|ENHANCER_SELECTED_TEXT
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
name|ENHANCER_SELECTION_CONTEXT
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
name|ENHANCER_START
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
name|RDF_TYPE
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
name|TechnicalClasses
operator|.
name|ENHANCER_TEXTANNOTATION
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|Literal
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
name|Triple
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
name|TypedLiteral
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
name|SimpleMGraph
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
name|EngineException
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
specifier|static
name|EngineCore
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
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setUpServices
parameter_list|()
throws|throws
name|IOException
block|{
name|nerEngine
operator|=
operator|new
name|EngineCore
argument_list|(
operator|new
name|ClasspathDataFileProvider
argument_list|(
name|FAKE_BUNDLE_SYMBOLIC_NAME
argument_list|)
argument_list|,
name|FAKE_BUNDLE_SYMBOLIC_NAME
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
name|id
parameter_list|,
specifier|final
name|String
name|text
parameter_list|)
block|{
return|return
operator|new
name|ContentItem
argument_list|()
block|{
name|SimpleMGraph
name|metadata
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
specifier|public
name|InputStream
name|getStream
parameter_list|()
block|{
return|return
operator|new
name|ByteArrayInputStream
argument_list|(
name|text
operator|.
name|getBytes
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getMimeType
parameter_list|()
block|{
return|return
literal|"text/plain"
return|;
block|}
specifier|public
name|MGraph
name|getMetadata
parameter_list|()
block|{
return|return
name|metadata
return|;
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
block|}
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
literal|0.005
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
literal|0.005
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
block|{
name|ContentItem
name|ci
init|=
name|wrapAsContentItem
argument_list|(
literal|"my doc id"
argument_list|,
name|SINGLE_SENTENCE
argument_list|)
decl_stmt|;
name|nerEngine
operator|.
name|computeEnhancements
argument_list|(
name|ci
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
name|checkAllTextAnnotations
argument_list|(
name|g
argument_list|,
name|SINGLE_SENTENCE
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|textAnnotationCount
argument_list|)
expr_stmt|;
comment|//This Engine dose no longer create entityAnnotations
comment|//        int entityAnnotationCount = checkAllEntityAnnotations(g);
comment|//        assertEquals(2, entityAnnotationCount);
block|}
comment|/*      * -----------------------------------------------------------------------      * Helper Methods to check Text and EntityAnnotations      * -----------------------------------------------------------------------      */
specifier|private
name|int
name|checkAllTextAnnotations
parameter_list|(
name|MGraph
name|g
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|textAnnotationIterator
init|=
name|g
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|RDF_TYPE
argument_list|,
name|ENHANCER_TEXTANNOTATION
argument_list|)
decl_stmt|;
comment|// test if a textAnnotation is present
name|assertTrue
argument_list|(
name|textAnnotationIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|textAnnotationCount
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|textAnnotationIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|UriRef
name|textAnnotation
init|=
operator|(
name|UriRef
operator|)
name|textAnnotationIterator
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
decl_stmt|;
comment|// test if selected Text is added
name|checkTextAnnotation
argument_list|(
name|g
argument_list|,
name|textAnnotation
argument_list|,
name|content
argument_list|)
expr_stmt|;
name|textAnnotationCount
operator|++
expr_stmt|;
block|}
return|return
name|textAnnotationCount
return|;
block|}
comment|/**      * Checks if a text annotation is valid      */
specifier|private
name|void
name|checkTextAnnotation
parameter_list|(
name|MGraph
name|g
parameter_list|,
name|UriRef
name|textAnnotation
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|selectedTextIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_SELECTED_TEXT
argument_list|,
literal|null
argument_list|)
decl_stmt|;
comment|// check if the selected text is added
name|assertTrue
argument_list|(
name|selectedTextIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|// test if the selected text is part of the TEXT_TO_TEST
name|Resource
name|object
init|=
name|selectedTextIterator
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|object
operator|instanceof
name|Literal
argument_list|)
expr_stmt|;
name|Literal
name|selectedText
init|=
operator|(
name|Literal
operator|)
name|object
decl_stmt|;
name|object
operator|=
literal|null
expr_stmt|;
name|assertTrue
argument_list|(
name|SINGLE_SENTENCE
operator|.
name|contains
argument_list|(
name|selectedText
operator|.
name|getLexicalForm
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// test if context is added
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|selectionContextIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_SELECTION_CONTEXT
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|selectionContextIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|// test if the selected text is part of the TEXT_TO_TEST
name|object
operator|=
name|selectionContextIterator
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
expr_stmt|;
name|assertTrue
argument_list|(
name|object
operator|instanceof
name|Literal
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|SINGLE_SENTENCE
operator|.
name|contains
argument_list|(
operator|(
operator|(
name|Literal
operator|)
name|object
operator|)
operator|.
name|getLexicalForm
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|object
operator|=
literal|null
expr_stmt|;
comment|//test start/end if present
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|startPosIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_START
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|endPosIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_END
argument_list|,
literal|null
argument_list|)
decl_stmt|;
comment|//start end is optional, but if start is present, that also end needs to be set
if|if
condition|(
name|startPosIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Resource
name|resource
init|=
name|startPosIterator
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
comment|//only a single start position is supported
name|assertTrue
argument_list|(
operator|!
name|startPosIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|resource
operator|instanceof
name|TypedLiteral
argument_list|)
expr_stmt|;
name|TypedLiteral
name|startPosLiteral
init|=
operator|(
name|TypedLiteral
operator|)
name|resource
decl_stmt|;
name|resource
operator|=
literal|null
expr_stmt|;
name|int
name|start
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createObject
argument_list|(
name|Integer
operator|.
name|class
argument_list|,
name|startPosLiteral
argument_list|)
decl_stmt|;
name|startPosLiteral
operator|=
literal|null
expr_stmt|;
comment|//now get the end
comment|//end must be defined if start is present
name|assertTrue
argument_list|(
name|endPosIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|resource
operator|=
name|endPosIterator
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
expr_stmt|;
comment|//only a single end position is supported
name|assertTrue
argument_list|(
operator|!
name|endPosIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|resource
operator|instanceof
name|TypedLiteral
argument_list|)
expr_stmt|;
name|TypedLiteral
name|endPosLiteral
init|=
operator|(
name|TypedLiteral
operator|)
name|resource
decl_stmt|;
name|resource
operator|=
literal|null
expr_stmt|;
name|int
name|end
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createObject
argument_list|(
name|Integer
operator|.
name|class
argument_list|,
name|endPosLiteral
argument_list|)
decl_stmt|;
name|endPosLiteral
operator|=
literal|null
expr_stmt|;
comment|//check for equality of the selected text and the text on the selected position in the content
comment|//System.out.println("TA ["+start+"|"+end+"]"+selectedText.getLexicalForm()+"<->"+content.substring(start,end));
name|assertEquals
argument_list|(
name|content
operator|.
name|substring
argument_list|(
name|start
argument_list|,
name|end
argument_list|)
argument_list|,
name|selectedText
operator|.
name|getLexicalForm
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//if no start position is present, there must also be no end position defined
name|assertTrue
argument_list|(
operator|!
name|endPosIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

