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
name|enhancer
operator|.
name|nlp
operator|.
name|json
operator|.
name|valuetype
package|;
end_package

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
name|ByteArrayOutputStream
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
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|EnumSet
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
operator|.
name|Entry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|nlp
operator|.
name|json
operator|.
name|AnalyzedTextParser
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
name|nlp
operator|.
name|json
operator|.
name|AnalyzedTextSerializer
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
name|nlp
operator|.
name|model
operator|.
name|AnalysedText
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
name|nlp
operator|.
name|model
operator|.
name|AnalysedTextFactory
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
name|nlp
operator|.
name|model
operator|.
name|Span
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
name|nlp
operator|.
name|model
operator|.
name|SpanTypeEnum
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
name|nlp
operator|.
name|model
operator|.
name|annotation
operator|.
name|Value
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
name|impl
operator|.
name|StringSource
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

begin_class
specifier|public
specifier|abstract
class|class
name|ValueTypeSupportTest
block|{
comment|/**      * The line separator used by the Environment running this test      */
specifier|protected
specifier|static
specifier|final
name|String
name|LINE_SEPARATOR
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
decl_stmt|;
comment|/**      * Empty AnalysedText instance created before each test      */
specifier|protected
specifier|static
name|AnalysedText
name|at
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
name|AnalysedTextFactory
name|atFactory
init|=
name|AnalysedTextFactory
operator|.
name|getDefaultInstance
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|ContentItem
name|ci
decl_stmt|;
specifier|private
specifier|static
name|Entry
argument_list|<
name|IRI
argument_list|,
name|Blob
argument_list|>
name|textBlob
decl_stmt|;
specifier|protected
specifier|static
name|void
name|setupAnalysedText
parameter_list|(
name|String
name|text
parameter_list|)
throws|throws
name|IOException
block|{
name|ci
operator|=
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
expr_stmt|;
name|textBlob
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
name|at
operator|=
name|atFactory
operator|.
name|createAnalysedText
argument_list|(
name|textBlob
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|String
name|getSerializedString
parameter_list|()
throws|throws
name|IOException
block|{
name|ByteArrayOutputStream
name|bout
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|AnalyzedTextSerializer
name|serializer
init|=
name|AnalyzedTextSerializer
operator|.
name|getDefaultInstance
argument_list|()
decl_stmt|;
name|serializer
operator|.
name|serialize
argument_list|(
name|at
argument_list|,
name|bout
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|byte
index|[]
name|data
init|=
name|bout
operator|.
name|toByteArray
argument_list|()
decl_stmt|;
return|return
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
name|AnalysedText
name|getParsedAnalysedText
parameter_list|(
name|String
name|serializedData
parameter_list|)
throws|throws
name|IOException
block|{
name|AnalyzedTextParser
name|parser
init|=
name|AnalyzedTextParser
operator|.
name|getDefaultInstance
argument_list|()
decl_stmt|;
name|byte
index|[]
name|bytes
init|=
name|serializedData
operator|.
name|getBytes
argument_list|()
decl_stmt|;
return|return
name|parser
operator|.
name|parse
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|bytes
argument_list|)
argument_list|,
literal|null
argument_list|,
name|atFactory
operator|.
name|createAnalysedText
argument_list|(
name|textBlob
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
name|void
name|assertAnalysedTextEquality
parameter_list|(
name|AnalysedText
name|parsedAt
parameter_list|)
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|at
argument_list|,
name|parsedAt
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|Span
argument_list|>
name|origSpanIt
init|=
name|at
operator|.
name|getEnclosed
argument_list|(
name|EnumSet
operator|.
name|allOf
argument_list|(
name|SpanTypeEnum
operator|.
name|class
argument_list|)
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Span
argument_list|>
name|parsedSpanIt
init|=
name|parsedAt
operator|.
name|getEnclosed
argument_list|(
name|EnumSet
operator|.
name|allOf
argument_list|(
name|SpanTypeEnum
operator|.
name|class
argument_list|)
argument_list|)
decl_stmt|;
while|while
condition|(
name|origSpanIt
operator|.
name|hasNext
argument_list|()
operator|&&
name|parsedSpanIt
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Span
name|orig
init|=
name|origSpanIt
operator|.
name|next
argument_list|()
decl_stmt|;
name|Span
name|parsed
init|=
name|parsedSpanIt
operator|.
name|next
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|orig
argument_list|,
name|parsed
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|origKeys
init|=
name|orig
operator|.
name|getKeys
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|parsedKeys
init|=
name|parsed
operator|.
name|getKeys
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|origKeys
argument_list|,
name|parsedKeys
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|key
range|:
name|origKeys
control|)
block|{
name|List
argument_list|<
name|Value
argument_list|<
name|?
argument_list|>
argument_list|>
name|origValues
init|=
name|orig
operator|.
name|getValues
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Value
argument_list|<
name|?
argument_list|>
argument_list|>
name|parsedValues
init|=
name|parsed
operator|.
name|getValues
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|origValues
argument_list|,
name|parsedValues
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

