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
name|metaxa
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
name|BNode
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
name|TripleImpl
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
name|engines
operator|.
name|metaxa
operator|.
name|MetaxaEngine
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
name|ontoware
operator|.
name|aifbcommons
operator|.
name|collection
operator|.
name|ClosableIterator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|Model
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|Statement
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|node
operator|.
name|BlankNode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|node
operator|.
name|Variable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|node
operator|.
name|impl
operator|.
name|URIImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|extractor
operator|.
name|ExtractorException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|vocabulary
operator|.
name|NMO
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

begin_comment
comment|/**  * {@link TestMetaxaCore} is a test class for {@link MetaxaCore}.  *  * @author Joerg Steffen, DFKI  * @version $Id$  */
end_comment

begin_class
specifier|public
class|class
name|TestMetaxaCore
block|{
comment|/**      * This contains the logger.      */
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
name|TestMetaxaCore
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * This contains the Metaxa extractor to test.      */
specifier|private
specifier|static
name|MetaxaCore
name|extractor
decl_stmt|;
comment|/**      * This initializes the Aperture extractor.      */
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
name|extractor
operator|=
operator|new
name|MetaxaCore
argument_list|(
literal|"extractionregistry.xml"
argument_list|)
expr_stmt|;
block|}
comment|/**      * This tests the pdf extraction.      *      * @throws ExtractorException if there is an error during extraction      * @throws IOException if there is an error when reading the document      */
annotation|@
name|Test
specifier|public
name|void
name|testPdfExtraction
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|testFile
init|=
literal|"test.pdf"
decl_stmt|;
name|String
name|testResultFile
init|=
literal|"pdf-res.txt"
decl_stmt|;
comment|// extract text from pdf
name|InputStream
name|in
init|=
name|getResourceAsStream
argument_list|(
name|testFile
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"failed to load resource "
operator|+
name|testFile
argument_list|,
name|in
argument_list|)
expr_stmt|;
name|Model
name|m
init|=
name|extractor
operator|.
name|extract
argument_list|(
name|in
argument_list|,
operator|new
name|URIImpl
argument_list|(
literal|"file://"
operator|+
name|testFile
argument_list|)
argument_list|,
literal|"application/pdf"
argument_list|)
decl_stmt|;
name|String
name|text
init|=
name|MetaxaCore
operator|.
name|getText
argument_list|(
name|m
argument_list|)
decl_stmt|;
comment|// get expected result
name|InputStream
name|in2
init|=
name|getResourceAsStream
argument_list|(
name|testResultFile
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"failed to load resource "
operator|+
name|testResultFile
argument_list|,
name|in2
argument_list|)
expr_stmt|;
name|String
name|expectedText
init|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|in2
argument_list|,
literal|"utf-8"
argument_list|)
decl_stmt|;
comment|// test
name|assertEquals
argument_list|(
name|cleanup
argument_list|(
name|expectedText
argument_list|)
argument_list|,
name|cleanup
argument_list|(
name|text
argument_list|)
argument_list|)
expr_stmt|;
comment|// show triples
name|int
name|tripleCounter
init|=
name|this
operator|.
name|printTriples
argument_list|(
name|m
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|11
argument_list|,
name|tripleCounter
argument_list|)
expr_stmt|;
block|}
comment|/**      * This tests the html extraction.      *      * @throws ExtractorException if there is an error during extraction      * @throws IOException if there is an error when reading the document      */
annotation|@
name|Test
specifier|public
name|void
name|testHtmlExtraction
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|testFile
init|=
literal|"test.html"
decl_stmt|;
name|String
name|testResultFile
init|=
literal|"html-res.txt"
decl_stmt|;
comment|// extract text from html
name|InputStream
name|in
init|=
name|getResourceAsStream
argument_list|(
name|testFile
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"failed to load resource "
operator|+
name|testFile
argument_list|,
name|in
argument_list|)
expr_stmt|;
name|Model
name|m
init|=
name|extractor
operator|.
name|extract
argument_list|(
name|in
argument_list|,
operator|new
name|URIImpl
argument_list|(
literal|"file://"
operator|+
name|testFile
argument_list|)
argument_list|,
literal|"text/html"
argument_list|)
decl_stmt|;
name|String
name|text
init|=
name|MetaxaCore
operator|.
name|getText
argument_list|(
name|m
argument_list|)
decl_stmt|;
comment|// get expected result
name|InputStream
name|in2
init|=
name|getResourceAsStream
argument_list|(
name|testResultFile
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"failed to load resource "
operator|+
name|testResultFile
argument_list|,
name|in2
argument_list|)
expr_stmt|;
name|String
name|expectedText
init|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|in2
argument_list|,
literal|"utf-8"
argument_list|)
decl_stmt|;
comment|// test
name|assertEquals
argument_list|(
name|cleanup
argument_list|(
name|expectedText
argument_list|)
argument_list|,
name|cleanup
argument_list|(
name|text
argument_list|)
argument_list|)
expr_stmt|;
comment|// show triples
name|int
name|tripleCounter
init|=
name|this
operator|.
name|printTriples
argument_list|(
name|m
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|28
argument_list|,
name|tripleCounter
argument_list|)
expr_stmt|;
block|}
comment|/**      * This tests the html extraction.      *      * @throws ExtractorException if there is an error during extraction      * @throws IOException if there is an error when reading the document      */
annotation|@
name|Test
specifier|public
name|void
name|testRdfaExtraction
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|testFile
init|=
literal|"test-rdfa.html"
decl_stmt|;
name|String
name|testResultFile
init|=
literal|"rdfa-res.txt"
decl_stmt|;
comment|// extract text from RDFa annotated html
name|InputStream
name|in
init|=
name|getResourceAsStream
argument_list|(
name|testFile
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"failed to load resource "
operator|+
name|testFile
argument_list|,
name|in
argument_list|)
expr_stmt|;
name|Model
name|m
init|=
name|extractor
operator|.
name|extract
argument_list|(
name|in
argument_list|,
operator|new
name|URIImpl
argument_list|(
literal|"file://"
operator|+
name|testFile
argument_list|)
argument_list|,
literal|"text/html"
argument_list|)
decl_stmt|;
name|String
name|text
init|=
name|MetaxaCore
operator|.
name|getText
argument_list|(
name|m
argument_list|)
decl_stmt|;
comment|// get expected result
name|InputStream
name|in2
init|=
name|getResourceAsStream
argument_list|(
name|testResultFile
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"failed to load resource "
operator|+
name|testResultFile
argument_list|,
name|in2
argument_list|)
expr_stmt|;
name|String
name|expectedText
init|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|in2
argument_list|,
literal|"utf-8"
argument_list|)
decl_stmt|;
comment|// test
name|assertEquals
argument_list|(
name|cleanup
argument_list|(
name|expectedText
argument_list|)
argument_list|,
name|cleanup
argument_list|(
name|text
argument_list|)
argument_list|)
expr_stmt|;
comment|// show triples
name|int
name|tripleCounter
init|=
name|this
operator|.
name|printTriples
argument_list|(
name|m
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|10
argument_list|,
name|tripleCounter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMailExtraction
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|testFile
init|=
literal|"mail-multipart-test.eml"
decl_stmt|;
name|InputStream
name|in
init|=
name|getResourceAsStream
argument_list|(
name|testFile
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"failed to load resource "
operator|+
name|testFile
argument_list|,
name|in
argument_list|)
expr_stmt|;
name|Model
name|m
init|=
name|extractor
operator|.
name|extract
argument_list|(
name|in
argument_list|,
operator|new
name|URIImpl
argument_list|(
literal|"file://"
operator|+
name|testFile
argument_list|)
argument_list|,
literal|"message/rfc822"
argument_list|)
decl_stmt|;
name|boolean
name|textContained
init|=
name|m
operator|.
name|contains
argument_list|(
name|Variable
operator|.
name|ANY
argument_list|,
name|NMO
operator|.
name|plainTextMessageContent
argument_list|,
name|Variable
operator|.
name|ANY
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|textContained
argument_list|)
expr_stmt|;
block|}
comment|/**      * This prints out the Stanbol Enhancer triples that would be created for the metadata      * contained in the given model.      *      * @param m a {@link Model}      *      * @return an {@code int} with the number of added triples      */
specifier|private
name|int
name|printTriples
parameter_list|(
name|Model
name|m
parameter_list|)
block|{
name|int
name|tripleCounter
init|=
literal|0
decl_stmt|;
name|HashMap
argument_list|<
name|BlankNode
argument_list|,
name|BNode
argument_list|>
name|blankNodeMap
init|=
operator|new
name|HashMap
argument_list|<
name|BlankNode
argument_list|,
name|BNode
argument_list|>
argument_list|()
decl_stmt|;
name|ClosableIterator
argument_list|<
name|Statement
argument_list|>
name|it
init|=
name|m
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Statement
name|oneStmt
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|NonLiteral
name|subject
init|=
operator|(
name|NonLiteral
operator|)
name|MetaxaEngine
operator|.
name|asClerezzaResource
argument_list|(
name|oneStmt
operator|.
name|getSubject
argument_list|()
argument_list|,
name|blankNodeMap
argument_list|)
decl_stmt|;
name|UriRef
name|predicate
init|=
operator|(
name|UriRef
operator|)
name|MetaxaEngine
operator|.
name|asClerezzaResource
argument_list|(
name|oneStmt
operator|.
name|getPredicate
argument_list|()
argument_list|,
name|blankNodeMap
argument_list|)
decl_stmt|;
name|Resource
name|object
init|=
name|MetaxaEngine
operator|.
name|asClerezzaResource
argument_list|(
name|oneStmt
operator|.
name|getObject
argument_list|()
argument_list|,
name|blankNodeMap
argument_list|)
decl_stmt|;
if|if
condition|(
literal|null
operator|!=
name|subject
operator|&&
literal|null
operator|!=
name|predicate
operator|&&
literal|null
operator|!=
name|object
condition|)
block|{
name|Triple
name|t
init|=
operator|new
name|TripleImpl
argument_list|(
name|subject
argument_list|,
name|predicate
argument_list|,
name|object
argument_list|)
decl_stmt|;
name|LOG
operator|.
name|debug
argument_list|(
literal|"adding "
operator|+
name|t
argument_list|)
expr_stmt|;
name|tripleCounter
operator|++
expr_stmt|;
block|}
else|else
block|{
name|LOG
operator|.
name|debug
argument_list|(
literal|"skipped "
operator|+
name|oneStmt
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|it
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|tripleCounter
return|;
block|}
comment|/**      * Cleanup strings for comparison, by removing non-printable chars.      *      * @param txt a {@link String} with the text to clean      *      * @return a {@link String} with the result      */
specifier|private
name|String
name|cleanup
parameter_list|(
name|String
name|txt
parameter_list|)
block|{
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|txt
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
specifier|final
name|char
name|c
init|=
name|txt
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|>=
literal|' '
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
name|InputStream
name|getResourceAsStream
parameter_list|(
name|String
name|testResultFile
parameter_list|)
block|{
return|return
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
name|testResultFile
argument_list|)
return|;
block|}
block|}
end_class

end_unit

