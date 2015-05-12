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
name|jersey
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
name|jersey
operator|.
name|utils
operator|.
name|RequestPropertiesHelper
operator|.
name|REQUEST_PROPERTIES_URI
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
name|jersey
operator|.
name|utils
operator|.
name|RequestPropertiesHelper
operator|.
name|OUTPUT_CONTENT
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
name|jersey
operator|.
name|utils
operator|.
name|RequestPropertiesHelper
operator|.
name|OUTPUT_CONTENT_PART
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
name|jersey
operator|.
name|utils
operator|.
name|RequestPropertiesHelper
operator|.
name|PARSED_CONTENT_URIS
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
name|jersey
operator|.
name|utils
operator|.
name|RequestPropertiesHelper
operator|.
name|RDF_FORMAT
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
name|jersey
operator|.
name|utils
operator|.
name|RequestPropertiesHelper
operator|.
name|getOutputContent
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
name|jersey
operator|.
name|utils
operator|.
name|RequestPropertiesHelper
operator|.
name|getParsedContentURIs
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
name|helper
operator|.
name|ContentItemHelper
operator|.
name|initRequestPropertiesContentPart
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
name|helper
operator|.
name|ExecutionMetadataHelper
operator|.
name|initExecutionMetadata
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
name|helper
operator|.
name|ExecutionMetadataHelper
operator|.
name|initExecutionMetadataContentPart
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
name|helper
operator|.
name|ExecutionPlanHelper
operator|.
name|createExecutionPlan
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
name|helper
operator|.
name|ExecutionPlanHelper
operator|.
name|writeExecutionNode
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
name|ExecutionMetadata
operator|.
name|CHAIN_EXECUTION
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
name|HashSet
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
name|Map
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
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|HttpHeaders
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MultivaluedHashMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MultivaluedMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|ext
operator|.
name|RuntimeDelegate
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
name|NonLiteral
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|serializedform
operator|.
name|Parser
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
name|serializedform
operator|.
name|Serializer
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
name|ontologies
operator|.
name|RDF
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|writers
operator|.
name|JsonLdSerializerProvider
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
name|jersey
operator|.
name|reader
operator|.
name|ContentItemReader
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
name|jersey
operator|.
name|writers
operator|.
name|ContentItemWriter
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
name|ExecutionMetadata
import|;
end_import

begin_import
import|import
name|org
operator|.
name|glassfish
operator|.
name|jersey
operator|.
name|internal
operator|.
name|RuntimeDelegateImpl
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

begin_class
specifier|public
class|class
name|ContentItemReaderWriterTest
block|{
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
name|ContentItemReaderWriterTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|ContentItem
name|contentItem
decl_stmt|;
specifier|private
specifier|static
name|ContentItemWriter
name|ciWriter
decl_stmt|;
specifier|private
specifier|static
name|ContentItemReader
name|ciReader
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
comment|/**      * @return      */
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|createTestContentItem
parameter_list|()
throws|throws
name|IOException
block|{
name|contentItem
operator|=
name|ciFactory
operator|.
name|createContentItem
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"urn:test"
argument_list|)
argument_list|,
operator|new
name|StringSource
argument_list|(
literal|"<html>\n"
operator|+
literal|"<body>\n"
operator|+
literal|"    This is a<b>ContentItem</b> to<i>Mime Multipart</i> test!\n"
operator|+
literal|"</body>\n"
operator|+
literal|"</html>"
argument_list|,
literal|"text/html"
argument_list|)
argument_list|)
expr_stmt|;
name|RuntimeDelegate
operator|.
name|setInstance
argument_list|(
operator|new
name|RuntimeDelegateImpl
argument_list|()
argument_list|)
expr_stmt|;
name|contentItem
operator|.
name|addPart
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"run:text:text"
argument_list|)
argument_list|,
name|ciFactory
operator|.
name|createBlob
argument_list|(
operator|new
name|StringSource
argument_list|(
literal|"This is a ContentItem to Mime Multipart test!"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|contentItem
operator|.
name|getMetadata
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"urn:test"
argument_list|)
argument_list|,
name|RDF
operator|.
name|type
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"urn:types:Document"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//mark the main content as parsed and also that all
comment|//contents and contentparts should be included
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
init|=
name|initRequestPropertiesContentPart
argument_list|(
name|contentItem
argument_list|)
decl_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|PARSED_CONTENT_URIS
argument_list|,
name|Collections
operator|.
name|singleton
argument_list|(
name|contentItem
operator|.
name|getPartUri
argument_list|(
literal|0
argument_list|)
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|OUTPUT_CONTENT
argument_list|,
name|Collections
operator|.
name|singleton
argument_list|(
literal|"*/*"
argument_list|)
argument_list|)
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|OUTPUT_CONTENT_PART
argument_list|,
name|Collections
operator|.
name|singleton
argument_list|(
literal|"*"
argument_list|)
argument_list|)
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|RDF_FORMAT
argument_list|,
literal|"application/rdf+xml"
argument_list|)
expr_stmt|;
name|MGraph
name|em
init|=
name|initExecutionMetadataContentPart
argument_list|(
name|contentItem
argument_list|)
decl_stmt|;
name|NonLiteral
name|ep
init|=
name|createExecutionPlan
argument_list|(
name|em
argument_list|,
literal|"testChain"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|writeExecutionNode
argument_list|(
name|em
argument_list|,
name|ep
argument_list|,
literal|"testEngine"
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|initExecutionMetadata
argument_list|(
name|em
argument_list|,
name|em
argument_list|,
name|contentItem
operator|.
name|getUri
argument_list|()
argument_list|,
literal|"testChain"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|ciWriter
operator|=
operator|new
name|ContentItemWriter
argument_list|(
name|Serializer
operator|.
name|getInstance
argument_list|()
argument_list|)
expr_stmt|;
name|ciReader
operator|=
operator|new
name|ContentItemReader
argument_list|()
block|{
annotation|@
name|Override
specifier|protected
name|Parser
name|getParser
parameter_list|()
block|{
return|return
name|Parser
operator|.
name|getInstance
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|protected
name|ContentItemFactory
name|getContentItemFactory
parameter_list|()
block|{
return|return
name|ciFactory
return|;
block|}
block|}
expr_stmt|;
block|}
comment|/**      * @param out      * @return      * @throws IOException      */
specifier|private
name|MediaType
name|serializeContentItem
parameter_list|(
name|ByteArrayOutputStream
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|MultivaluedMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|headers
init|=
operator|new
name|MultivaluedHashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|ciWriter
operator|.
name|writeTo
argument_list|(
name|contentItem
argument_list|,
name|ContentItem
operator|.
name|class
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|MediaType
operator|.
name|MULTIPART_FORM_DATA_TYPE
argument_list|,
name|headers
argument_list|,
name|out
argument_list|)
expr_stmt|;
comment|//check the returned content type
name|String
name|contentTypeString
init|=
operator|(
name|String
operator|)
name|headers
operator|.
name|getFirst
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|contentTypeString
argument_list|)
expr_stmt|;
name|MediaType
name|contentType
init|=
name|MediaType
operator|.
name|valueOf
argument_list|(
name|contentTypeString
argument_list|)
decl_stmt|;
return|return
name|contentType
return|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testWriter
parameter_list|()
throws|throws
name|Exception
block|{
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|MediaType
name|contentType
init|=
name|serializeContentItem
argument_list|(
name|out
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|MediaType
operator|.
name|MULTIPART_FORM_DATA_TYPE
operator|.
name|isCompatible
argument_list|(
name|contentType
argument_list|)
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|contentType
operator|.
name|getParameters
argument_list|()
operator|.
name|get
argument_list|(
literal|"boundary"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|contentType
operator|.
name|getParameters
argument_list|()
operator|.
name|get
argument_list|(
literal|"boundary"
argument_list|)
argument_list|,
name|ContentItemWriter
operator|.
name|CONTENT_ITEM_BOUNDARY
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|contentType
operator|.
name|getParameters
argument_list|()
operator|.
name|get
argument_list|(
literal|"charset"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|contentType
operator|.
name|getParameters
argument_list|()
operator|.
name|get
argument_list|(
literal|"charset"
argument_list|)
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
comment|//check the serialised multipart MIME
name|String
name|multipartMime
init|=
operator|new
name|String
argument_list|(
name|out
operator|.
name|toByteArray
argument_list|()
argument_list|,
name|Charset
operator|.
name|forName
argument_list|(
name|contentType
operator|.
name|getParameters
argument_list|()
operator|.
name|get
argument_list|(
literal|"charset"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Multipart MIME content:\n{}\n"
argument_list|,
name|multipartMime
argument_list|)
expr_stmt|;
name|String
index|[]
name|tests
init|=
operator|new
name|String
index|[]
block|{
literal|"--"
operator|+
name|ContentItemWriter
operator|.
name|CONTENT_ITEM_BOUNDARY
block|,
literal|"Content-Disposition: form-data; name=\"metadata\"; filename=\"urn:test\""
block|,
literal|"Content-Type: application/rdf+xml; charset=UTF-8"
block|,
literal|"<rdf:type rdf:resource=\"urn:types:Document\"/>"
block|,
literal|"--"
operator|+
name|ContentItemWriter
operator|.
name|CONTENT_ITEM_BOUNDARY
block|,
literal|"Content-Disposition: form-data; name=\"content\""
block|,
literal|"Content-Type: multipart/alternate; boundary="
operator|+
name|ContentItemWriter
operator|.
name|CONTENT_PARTS_BOUNDERY
block|,
literal|"--"
operator|+
name|ContentItemWriter
operator|.
name|CONTENT_PARTS_BOUNDERY
block|,
literal|"Content-Disposition: form-data; name=\"urn:test_main\""
block|,
literal|"Content-Type: text/html; charset=UTF-8"
block|,
literal|"This is a<b>ContentItem</b> to<i>Mime Multipart</i> test!"
block|,
literal|"--"
operator|+
name|ContentItemWriter
operator|.
name|CONTENT_PARTS_BOUNDERY
block|,
literal|"Content-Disposition: form-data; name=\"run:text:text\""
block|,
literal|"Content-Type: text/plain; charset=UTF-8"
block|,
literal|"This is a ContentItem to Mime Multipart test!"
block|,
literal|"--"
operator|+
name|ContentItemWriter
operator|.
name|CONTENT_PARTS_BOUNDERY
operator|+
literal|"--"
block|,
literal|"--"
operator|+
name|ContentItemWriter
operator|.
name|CONTENT_ITEM_BOUNDARY
block|,
literal|"Content-Disposition: form-data; name=\""
operator|+
name|REQUEST_PROPERTIES_URI
operator|.
name|getUnicodeString
argument_list|()
operator|+
literal|"\""
block|,
literal|"Content-Type: application/json; charset=UTF-8"
block|,
literal|"--"
operator|+
name|ContentItemWriter
operator|.
name|CONTENT_ITEM_BOUNDARY
block|,
literal|"Content-Disposition: form-data; name=\""
operator|+
name|CHAIN_EXECUTION
operator|.
name|getUnicodeString
argument_list|()
operator|+
literal|"\""
block|,
literal|"Content-Type: application/rdf+xml; charset=UTF-8"
block|,
literal|"<rdf:type rdf:resource=\"http://stanbol.apache.org/ontology/enhancer/executionplan#ExecutionNode\"/>"
block|,
literal|"--"
operator|+
name|ContentItemWriter
operator|.
name|CONTENT_ITEM_BOUNDARY
operator|+
literal|"--"
block|}
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> Validate Multipart Mime:"
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|test
range|:
name|tests
control|)
block|{
name|int
name|index
init|=
name|multipartMime
operator|.
name|indexOf
argument_list|(
name|test
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"Unable to find: '"
operator|+
name|test
operator|+
literal|"' in multipart mime!"
argument_list|,
name|index
operator|>=
literal|0
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|" - found '{}'"
argument_list|,
name|test
argument_list|)
expr_stmt|;
name|multipartMime
operator|=
name|multipartMime
operator|.
name|substring
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testReader
parameter_list|()
throws|throws
name|Exception
block|{
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|MediaType
name|contentType
init|=
name|serializeContentItem
argument_list|(
name|out
argument_list|)
decl_stmt|;
name|ByteArrayInputStream
name|in
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|out
operator|.
name|toByteArray
argument_list|()
argument_list|)
decl_stmt|;
name|ContentItem
name|ci
init|=
name|ciReader
operator|.
name|readFrom
argument_list|(
name|ContentItem
operator|.
name|class
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|contentType
argument_list|,
literal|null
argument_list|,
name|in
argument_list|)
decl_stmt|;
comment|//assert ID
name|assertEquals
argument_list|(
name|contentItem
operator|.
name|getUri
argument_list|()
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
expr_stmt|;
comment|//assert metadata
name|MGraph
name|copy
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|copy
operator|.
name|addAll
argument_list|(
name|contentItem
operator|.
name|getMetadata
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|copy
operator|.
name|removeAll
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|copy
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
comment|//assert Blob
name|assertEquals
argument_list|(
name|contentItem
operator|.
name|getBlob
argument_list|()
operator|.
name|getMimeType
argument_list|()
argument_list|,
name|ci
operator|.
name|getBlob
argument_list|()
operator|.
name|getMimeType
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|content
init|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|contentItem
operator|.
name|getStream
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|String
name|readContent
init|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|ci
operator|.
name|getStream
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|content
argument_list|,
name|readContent
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|Blob
argument_list|>
argument_list|>
name|contentItemBlobsIt
init|=
name|ContentItemHelper
operator|.
name|getContentParts
argument_list|(
name|contentItem
argument_list|,
name|Blob
operator|.
name|class
argument_list|)
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|Blob
argument_list|>
argument_list|>
name|ciBlobsIt
init|=
name|ContentItemHelper
operator|.
name|getContentParts
argument_list|(
name|ci
argument_list|,
name|Blob
operator|.
name|class
argument_list|)
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|expectedParsedContentIds
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|//later used to validate enhancementMetadata
while|while
condition|(
name|contentItemBlobsIt
operator|.
name|hasNext
argument_list|()
operator|&&
name|ciBlobsIt
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|Blob
argument_list|>
name|contentItemBlobPart
init|=
name|contentItemBlobsIt
operator|.
name|next
argument_list|()
decl_stmt|;
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|Blob
argument_list|>
name|ciBlobPart
init|=
name|ciBlobsIt
operator|.
name|next
argument_list|()
decl_stmt|;
name|expectedParsedContentIds
operator|.
name|add
argument_list|(
name|ciBlobPart
operator|.
name|getKey
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|contentItemBlobPart
operator|.
name|getKey
argument_list|()
argument_list|,
name|ciBlobPart
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|partContentType
init|=
name|contentItemBlobPart
operator|.
name|getValue
argument_list|()
operator|.
name|getMimeType
argument_list|()
decl_stmt|;
name|String
name|readPartContentType
init|=
name|ciBlobPart
operator|.
name|getValue
argument_list|()
operator|.
name|getMimeType
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|partContentType
argument_list|,
name|readPartContentType
argument_list|)
expr_stmt|;
name|String
name|partContent
init|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|contentItemBlobPart
operator|.
name|getValue
argument_list|()
operator|.
name|getStream
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|String
name|readPartContent
init|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|ciBlobPart
operator|.
name|getValue
argument_list|()
operator|.
name|getStream
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|partContent
argument_list|,
name|readPartContent
argument_list|)
expr_stmt|;
block|}
comment|//validate ExecutionMetadata
name|MGraph
name|executionMetadata
init|=
name|contentItem
operator|.
name|getPart
argument_list|(
name|ExecutionMetadata
operator|.
name|CHAIN_EXECUTION
argument_list|,
name|MGraph
operator|.
name|class
argument_list|)
decl_stmt|;
name|MGraph
name|readExecutionMetadata
init|=
name|ci
operator|.
name|getPart
argument_list|(
name|ExecutionMetadata
operator|.
name|CHAIN_EXECUTION
argument_list|,
name|MGraph
operator|.
name|class
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|executionMetadata
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|readExecutionMetadata
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|executionMetadata
operator|.
name|size
argument_list|()
argument_list|,
name|readExecutionMetadata
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
comment|//validate EnhancemetnProperties
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|reqProp
init|=
name|ContentItemHelper
operator|.
name|getRequestPropertiesContentPart
argument_list|(
name|ci
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|reqProp
argument_list|)
expr_stmt|;
comment|//the parsed value MUST BE overridden by the two content parts parsed
name|assertEquals
argument_list|(
name|expectedParsedContentIds
argument_list|,
name|getParsedContentURIs
argument_list|(
name|reqProp
argument_list|)
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|outputContent
init|=
name|getOutputContent
argument_list|(
name|reqProp
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|outputContent
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|outputContent
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|,
literal|"*/*"
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|outputContentPart
init|=
name|Collections
operator|.
name|singleton
argument_list|(
literal|"*"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|outputContentPart
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|outputContentPart
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|,
literal|"*"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

