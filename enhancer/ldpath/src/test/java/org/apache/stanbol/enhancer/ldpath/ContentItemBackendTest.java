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
name|ldpath
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
name|junit
operator|.
name|framework
operator|.
name|Assert
operator|.
name|assertFalse
import|;
end_import

begin_import
import|import static
name|junit
operator|.
name|framework
operator|.
name|Assert
operator|.
name|assertNotNull
import|;
end_import

begin_import
import|import static
name|junit
operator|.
name|framework
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
name|BufferedInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FilterInputStream
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
name|Arrays
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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|zip
operator|.
name|ZipEntry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|zip
operator|.
name|ZipInputStream
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
name|TripleCollection
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
name|ParsingProvider
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
name|SupportedFormat
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
name|jena
operator|.
name|parser
operator|.
name|JenaParserProvider
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
name|indexedgraph
operator|.
name|IndexedMGraph
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
name|ldpath
operator|.
name|backend
operator|.
name|ContentItemBackend
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
name|ByteArraySource
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

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|LDPath
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|exception
operator|.
name|LDPathParseException
import|;
end_import

begin_class
specifier|public
class|class
name|ContentItemBackendTest
block|{
comment|/**      * Avoids that the parser closes the {@link ZipInputStream} after the      * first entry      */
specifier|protected
specifier|static
class|class
name|UncloseableStream
extends|extends
name|FilterInputStream
block|{
specifier|public
name|UncloseableStream
parameter_list|(
name|InputStream
name|in
parameter_list|)
block|{
name|super
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
throws|throws
name|IOException
block|{         }
block|}
specifier|private
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ContentItemBackendTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Charset
name|UTF8
init|=
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
decl_stmt|;
comment|//private static LiteralFactory lf = LiteralFactory.getInstance();
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
specifier|static
name|String
name|textContent
decl_stmt|;
specifier|private
specifier|static
name|String
name|htmlContent
decl_stmt|;
specifier|private
specifier|static
name|ContentItem
name|ci
decl_stmt|;
specifier|private
name|ContentItemBackend
name|backend
decl_stmt|;
specifier|private
name|LDPath
argument_list|<
name|Resource
argument_list|>
name|ldpath
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|readTestData
parameter_list|()
throws|throws
name|IOException
block|{
comment|//add the metadata
name|ParsingProvider
name|parser
init|=
operator|new
name|JenaParserProvider
argument_list|()
decl_stmt|;
comment|//create the content Item with the HTML content
name|MGraph
name|rdfData
init|=
name|parseRdfData
argument_list|(
name|parser
argument_list|,
literal|"metadata.rdf.zip"
argument_list|)
decl_stmt|;
name|UriRef
name|contentItemId
init|=
literal|null
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|rdfData
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|Properties
operator|.
name|ENHANCER_EXTRACTED_FROM
argument_list|,
literal|null
argument_list|)
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Resource
name|r
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|contentItemId
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|r
operator|instanceof
name|UriRef
condition|)
block|{
name|contentItemId
operator|=
operator|(
name|UriRef
operator|)
name|r
expr_stmt|;
block|}
block|}
else|else
block|{
name|assertEquals
argument_list|(
literal|"multiple ContentItems IDs contained in the RDF test data"
argument_list|,
name|contentItemId
argument_list|,
name|r
argument_list|)
expr_stmt|;
block|}
block|}
name|assertNotNull
argument_list|(
literal|"RDF data doe not contain an Enhancement extracted form "
operator|+
literal|"the content item"
argument_list|,
name|contentItemId
argument_list|)
expr_stmt|;
name|InputStream
name|in
init|=
name|getTestResource
argument_list|(
literal|"content.html"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"HTML content not found"
argument_list|,
name|in
argument_list|)
expr_stmt|;
name|byte
index|[]
name|htmlData
init|=
name|IOUtils
operator|.
name|toByteArray
argument_list|(
name|in
argument_list|)
decl_stmt|;
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|ci
operator|=
name|ciFactory
operator|.
name|createContentItem
argument_list|(
name|contentItemId
argument_list|,
operator|new
name|ByteArraySource
argument_list|(
name|htmlData
argument_list|,
literal|"text/html; charset=UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
name|htmlContent
operator|=
operator|new
name|String
argument_list|(
name|htmlData
argument_list|,
name|UTF8
argument_list|)
expr_stmt|;
comment|//create a Blob with the text content
name|in
operator|=
name|getTestResource
argument_list|(
literal|"content.txt"
argument_list|)
expr_stmt|;
name|byte
index|[]
name|textData
init|=
name|IOUtils
operator|.
name|toByteArray
argument_list|(
name|in
argument_list|)
decl_stmt|;
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
literal|"Plain text content not found"
argument_list|,
name|in
argument_list|)
expr_stmt|;
name|ci
operator|.
name|addPart
argument_list|(
operator|new
name|UriRef
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
operator|+
literal|"_text"
argument_list|)
argument_list|,
name|ciFactory
operator|.
name|createBlob
argument_list|(
operator|new
name|ByteArraySource
argument_list|(
name|textData
argument_list|,
literal|"text/plain; charset=UTF-8"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|textContent
operator|=
operator|new
name|String
argument_list|(
name|textData
argument_list|,
name|UTF8
argument_list|)
expr_stmt|;
comment|//add the metadata
name|ci
operator|.
name|getMetadata
argument_list|()
operator|.
name|addAll
argument_list|(
name|rdfData
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param parser      * @return      * @throws IOException      */
specifier|protected
specifier|static
name|MGraph
name|parseRdfData
parameter_list|(
name|ParsingProvider
name|parser
parameter_list|,
name|String
name|name
parameter_list|)
throws|throws
name|IOException
block|{
name|MGraph
name|rdfData
init|=
operator|new
name|IndexedMGraph
argument_list|()
decl_stmt|;
name|InputStream
name|in
init|=
name|getTestResource
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"File '"
operator|+
name|name
operator|+
literal|"' not found"
argument_list|,
name|in
argument_list|)
expr_stmt|;
name|ZipInputStream
name|zipIn
init|=
operator|new
name|ZipInputStream
argument_list|(
operator|new
name|BufferedInputStream
argument_list|(
name|in
argument_list|)
argument_list|)
decl_stmt|;
name|InputStream
name|uncloseable
init|=
operator|new
name|UncloseableStream
argument_list|(
name|zipIn
argument_list|)
decl_stmt|;
name|ZipEntry
name|entry
decl_stmt|;
while|while
condition|(
operator|(
name|entry
operator|=
name|zipIn
operator|.
name|getNextEntry
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|entry
operator|.
name|getName
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".rdf"
argument_list|)
condition|)
block|{
name|parser
operator|.
name|parse
argument_list|(
name|rdfData
argument_list|,
name|uncloseable
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
name|assertTrue
argument_list|(
name|rdfData
operator|.
name|size
argument_list|()
operator|>
literal|0
argument_list|)
expr_stmt|;
name|zipIn
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|rdfData
return|;
block|}
comment|/**      * @return      */
specifier|protected
specifier|static
name|InputStream
name|getTestResource
parameter_list|(
name|String
name|resourceName
parameter_list|)
block|{
name|InputStream
name|in
init|=
name|ContentItemBackendTest
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|resourceName
argument_list|)
decl_stmt|;
return|return
name|in
return|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|initBackend
parameter_list|()
block|{
if|if
condition|(
name|backend
operator|==
literal|null
condition|)
block|{
name|backend
operator|=
operator|new
name|ContentItemBackend
argument_list|(
name|ci
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ldpath
operator|==
literal|null
condition|)
block|{
name|ldpath
operator|=
operator|new
name|LDPath
argument_list|<
name|Resource
argument_list|>
argument_list|(
name|backend
argument_list|,
name|EnhancerLDPath
operator|.
name|getConfig
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testContent
parameter_list|()
throws|throws
name|LDPathParseException
block|{
name|Collection
argument_list|<
name|Resource
argument_list|>
name|result
init|=
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
literal|"fn:content(\"text/plain\")"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|result
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result
operator|.
name|size
argument_list|()
operator|==
literal|1
argument_list|)
expr_stmt|;
name|Resource
name|r
init|=
name|result
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|r
operator|instanceof
name|Literal
argument_list|)
expr_stmt|;
name|String
name|content
init|=
operator|(
operator|(
name|Literal
operator|)
name|r
operator|)
operator|.
name|getLexicalForm
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|content
argument_list|,
name|textContent
argument_list|)
expr_stmt|;
name|result
operator|=
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
literal|"fn:content(\"text/html\")"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|result
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result
operator|.
name|size
argument_list|()
operator|==
literal|1
argument_list|)
expr_stmt|;
name|r
operator|=
name|result
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
expr_stmt|;
name|assertTrue
argument_list|(
name|r
operator|instanceof
name|Literal
argument_list|)
expr_stmt|;
name|content
operator|=
operator|(
operator|(
name|Literal
operator|)
name|r
operator|)
operator|.
name|getLexicalForm
argument_list|()
expr_stmt|;
name|assertEquals
argument_list|(
name|content
argument_list|,
name|htmlContent
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testContentWithAdditionalMetadata
parameter_list|()
throws|throws
name|IOException
throws|,
name|LDPathParseException
block|{
name|byte
index|[]
name|content
init|=
literal|"text content"
operator|.
name|getBytes
argument_list|()
decl_stmt|;
name|UriRef
name|uri
init|=
name|ContentItemHelper
operator|.
name|makeDefaultUrn
argument_list|(
name|content
argument_list|)
decl_stmt|;
name|ContentItem
name|contentItem
init|=
name|ciFactory
operator|.
name|createContentItem
argument_list|(
name|uri
argument_list|,
operator|new
name|ByteArraySource
argument_list|(
name|content
argument_list|,
literal|"text/plain; charset=UTF-8"
argument_list|)
argument_list|)
decl_stmt|;
name|TripleCollection
name|tc
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|TypedLiteral
name|literal
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createTypedLiteral
argument_list|(
literal|"Michael Jackson"
argument_list|)
decl_stmt|;
name|UriRef
name|subject
init|=
operator|new
name|UriRef
argument_list|(
literal|"dummyUri"
argument_list|)
decl_stmt|;
name|tc
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|subject
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://xmlns.com/foaf/0.1/givenName"
argument_list|)
argument_list|,
name|literal
argument_list|)
argument_list|)
expr_stmt|;
name|contentItem
operator|.
name|addPart
argument_list|(
operator|new
name|UriRef
argument_list|(
name|uri
operator|.
name|getUnicodeString
argument_list|()
operator|+
literal|"_additionalMetadata"
argument_list|)
argument_list|,
name|tc
argument_list|)
expr_stmt|;
name|ContentItemBackend
name|ciBackend
init|=
operator|new
name|ContentItemBackend
argument_list|(
name|contentItem
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|LDPath
argument_list|<
name|Resource
argument_list|>
name|ldPath
init|=
operator|new
name|LDPath
argument_list|<
name|Resource
argument_list|>
argument_list|(
name|ciBackend
argument_list|,
name|EnhancerLDPath
operator|.
name|getConfig
argument_list|()
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|Resource
argument_list|>
name|result
init|=
name|ldPath
operator|.
name|pathQuery
argument_list|(
name|subject
argument_list|,
literal|"foaf:givenName"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"Additional metadata cannot be found"
argument_list|,
name|result
operator|.
name|contains
argument_list|(
name|literal
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testTextAnnotationFunction
parameter_list|()
throws|throws
name|LDPathParseException
block|{
name|String
name|path
init|=
literal|"fn:textAnnotation(.)/fise:selected-text"
decl_stmt|;
name|Collection
argument_list|<
name|Resource
argument_list|>
name|result
init|=
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|path
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|result
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result
operator|.
name|size
argument_list|()
operator|==
literal|2
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|expectedValues
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Bob Marley"
argument_list|,
literal|"Paris"
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|Resource
name|r
range|:
name|result
control|)
block|{
name|assertTrue
argument_list|(
name|r
operator|instanceof
name|Literal
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|expectedValues
operator|.
name|remove
argument_list|(
operator|(
operator|(
name|Literal
operator|)
name|r
operator|)
operator|.
name|getLexicalForm
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|expectedValues
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
comment|//test with a filter for the type
comment|//same as the 1st example bat rather using an ld-path construct for
comment|//filtering for TextAnnotations representing persons
name|path
operator|=
literal|"fn:textAnnotation(.)[dc:type is dbpedia-ont:Person]/fise:selected-text"
expr_stmt|;
name|result
operator|=
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|path
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|result
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result
operator|.
name|size
argument_list|()
operator|==
literal|1
argument_list|)
expr_stmt|;
name|Resource
name|r
init|=
name|result
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|r
operator|instanceof
name|Literal
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
operator|(
operator|(
name|Literal
operator|)
name|r
operator|)
operator|.
name|getLexicalForm
argument_list|()
argument_list|,
literal|"Bob Marley"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEntityAnnotation
parameter_list|()
throws|throws
name|LDPathParseException
block|{
name|String
name|path
init|=
literal|"fn:entityAnnotation(.)/fise:entity-reference"
decl_stmt|;
name|Collection
argument_list|<
name|Resource
argument_list|>
name|result
init|=
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|path
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|result
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result
operator|.
name|size
argument_list|()
operator|==
literal|4
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|UriRef
argument_list|>
name|expectedValues
init|=
operator|new
name|HashSet
argument_list|<
name|UriRef
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://dbpedia.org/resource/Paris"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://dbpedia.org/resource/Bob_Marley"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://dbpedia.org/resource/Centre_Georges_Pompidou"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://dbpedia.org/resource/Paris,_Texas"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|Resource
name|r
range|:
name|result
control|)
block|{
name|assertTrue
argument_list|(
name|r
operator|instanceof
name|UriRef
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Entity: {}"
argument_list|,
name|r
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|expectedValues
operator|.
name|remove
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|expectedValues
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
comment|//and with a filter
name|path
operator|=
literal|"fn:entityAnnotation(.)[fise:entity-type is dbpedia-ont:Person]/fise:entity-reference"
expr_stmt|;
name|result
operator|=
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|path
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|result
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result
operator|.
name|size
argument_list|()
operator|==
literal|1
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result
operator|.
name|contains
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://dbpedia.org/resource/Bob_Marley"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEnhancements
parameter_list|()
throws|throws
name|LDPathParseException
block|{
name|String
name|path
init|=
literal|"fn:enhancement(.)"
decl_stmt|;
name|Collection
argument_list|<
name|Resource
argument_list|>
name|result
init|=
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|path
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|result
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result
operator|.
name|size
argument_list|()
operator|==
literal|7
argument_list|)
expr_stmt|;
for|for
control|(
name|Resource
name|r
range|:
name|result
control|)
block|{
name|assertTrue
argument_list|(
name|r
operator|instanceof
name|UriRef
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Entity: {}"
argument_list|,
name|r
argument_list|)
expr_stmt|;
block|}
comment|//and with a filter
name|path
operator|=
literal|"fn:enhancement(.)[rdf:type is fise:TextAnnotation]"
expr_stmt|;
name|result
operator|=
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|path
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|result
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result
operator|.
name|size
argument_list|()
operator|==
literal|3
argument_list|)
expr_stmt|;
comment|//        assertTrue(result.contains(new UriRef("http://dbpedia.org/resource/Bob_Marley")));
name|path
operator|=
literal|"fn:enhancement(.)/dc:language"
expr_stmt|;
name|result
operator|=
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|path
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|result
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result
operator|.
name|size
argument_list|()
operator|==
literal|1
argument_list|)
expr_stmt|;
name|Resource
name|r
init|=
name|result
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|r
operator|instanceof
name|Literal
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"en"
argument_list|,
operator|(
operator|(
name|Literal
operator|)
name|r
operator|)
operator|.
name|getLexicalForm
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEntitySuggestions
parameter_list|()
throws|throws
name|LDPathParseException
block|{
comment|//NOTE: Sort while supported by fn:suggestion is currently not
comment|//      supported by LDPath. Therefore the sort of fn:suggestion can
comment|//      currently only ensure the the top most {limit} entities are
comment|//      selected if the "limit" parameter is set.
comment|// Because this test checks first that all three suggestions for Paris
comment|// are returned and later that a limit of 2 only returns the two top
comment|// most.
name|String
name|path
init|=
literal|"fn:textAnnotation(.)[dc:type is dbpedia-ont:Place]/fn:suggestion(.)"
decl_stmt|;
name|Collection
argument_list|<
name|Resource
argument_list|>
name|result
init|=
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|path
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|result
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result
operator|.
name|size
argument_list|()
operator|==
literal|3
argument_list|)
expr_stmt|;
name|Double
name|lowestConfidence
init|=
literal|null
decl_stmt|;
comment|//stores the lowest confidence suggestion for the 2nd part of this test
name|UriRef
name|lowestConfidenceSuggestion
init|=
literal|null
decl_stmt|;
name|path
operator|=
literal|"fise:confidence :: xsd:double"
expr_stmt|;
for|for
control|(
name|Resource
name|r
range|:
name|result
control|)
block|{
name|assertTrue
argument_list|(
name|r
operator|instanceof
name|UriRef
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"confidence: {}"
argument_list|,
name|r
argument_list|)
expr_stmt|;
name|Double
name|current
init|=
operator|(
name|Double
operator|)
name|ldpath
operator|.
name|pathTransform
argument_list|(
name|r
argument_list|,
name|path
argument_list|,
literal|null
argument_list|)
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|current
argument_list|)
expr_stmt|;
if|if
condition|(
name|lowestConfidence
operator|==
literal|null
operator|||
name|lowestConfidence
operator|>
name|current
condition|)
block|{
name|lowestConfidence
operator|=
name|current
expr_stmt|;
name|lowestConfidenceSuggestion
operator|=
operator|(
name|UriRef
operator|)
name|r
expr_stmt|;
block|}
block|}
name|assertNotNull
argument_list|(
name|lowestConfidenceSuggestion
argument_list|)
expr_stmt|;
name|path
operator|=
literal|"fn:textAnnotation(.)[dc:type is dbpedia-ont:Place]/fn:suggestion(.,\"2\")"
expr_stmt|;
name|Collection
argument_list|<
name|Resource
argument_list|>
name|result2
init|=
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|path
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|result2
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|result2
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result2
operator|.
name|size
argument_list|()
operator|==
literal|2
argument_list|)
expr_stmt|;
comment|//first check that all results of the 2nd query are also part of the first
name|assertTrue
argument_list|(
name|result
operator|.
name|containsAll
argument_list|(
name|result2
argument_list|)
argument_list|)
expr_stmt|;
comment|//secondly check that the lowest confidence suggestion is now missing
name|assertFalse
argument_list|(
name|result2
operator|.
name|contains
argument_list|(
name|lowestConfidenceSuggestion
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSuggestedEntity
parameter_list|()
throws|throws
name|LDPathParseException
block|{
comment|//The suggestedEntity function can be used for twi usecases
comment|//(1) get the {limit} top rated linked Entities per parsed context
comment|//    In this example we parse all TextAnnotations
comment|//NOTE: '.' MUST BE used as first argument in this case
name|String
name|path
init|=
literal|"fn:textAnnotation(.)/fn:suggestedEntity(.,\"1\")"
decl_stmt|;
name|Collection
argument_list|<
name|Resource
argument_list|>
name|result
init|=
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|path
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|result
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result
operator|.
name|size
argument_list|()
operator|==
literal|2
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|UriRef
argument_list|>
name|expectedValues
init|=
operator|new
name|HashSet
argument_list|<
name|UriRef
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://dbpedia.org/resource/Paris"
argument_list|)
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://dbpedia.org/resource/Bob_Marley"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|Resource
name|r
range|:
name|result
control|)
block|{
name|assertTrue
argument_list|(
name|r
operator|instanceof
name|UriRef
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Entity: {}"
argument_list|,
name|r
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|expectedValues
operator|.
name|remove
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|expectedValues
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
comment|//(2) get the {limit} top rated Entities for all Annotations parsed
comment|//    as the first argument
comment|//NOTE: the selector parsing all Annotations MUST BE used as first
comment|//      argument
name|path
operator|=
literal|"fn:suggestedEntity(fn:textAnnotation(.),\"1\")"
expr_stmt|;
name|result
operator|=
name|ldpath
operator|.
name|pathQuery
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|path
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|result
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|result
operator|.
name|size
argument_list|()
operator|==
literal|1
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"http://dbpedia.org/resource/Paris"
argument_list|)
argument_list|,
name|result
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

