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
name|commons
operator|.
name|ldpath
operator|.
name|clerezza
package|;
end_package

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
name|BufferedInputStream
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
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|math
operator|.
name|BigDecimal
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
name|HashMap
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
name|commons
operator|.
name|rdf
operator|.
name|Graph
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
name|marmotta
operator|.
name|ldpath
operator|.
name|LDPath
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|marmotta
operator|.
name|ldpath
operator|.
name|exception
operator|.
name|LDPathParseException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|marmotta
operator|.
name|ldpath
operator|.
name|parser
operator|.
name|Configuration
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
name|IndexedGraph
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
name|ClerezzaBackendTest
block|{
comment|/**      * Avoids that the parser closes the {@link ZipInputStream} after the      * first entry      */
specifier|private
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
comment|//private Logger log = LoggerFactory.getLogger(ClerezzaBackendTest.class);
specifier|private
specifier|static
specifier|final
name|String
name|NS_SKOS
init|=
literal|"http://www.w3.org/2004/02/skos/core#"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|NS_DBP
init|=
literal|"http://dbpedia.org/property/"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|NS_DBO
init|=
literal|"http://dbpedia.org/ontology/"
decl_stmt|;
comment|//private static final IRI SKOS_CONCEPT = new IRI(NS_SKOS+"Concept");
specifier|private
specifier|static
name|Graph
name|graph
decl_stmt|;
specifier|private
name|ClerezzaBackend
name|backend
decl_stmt|;
specifier|private
name|LDPath
argument_list|<
name|RDFTerm
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
name|ParsingProvider
name|parser
init|=
operator|new
name|JenaParserProvider
argument_list|()
decl_stmt|;
comment|//NOTE(rw): the new third parameter is the base URI used to resolve relative paths
name|graph
operator|=
operator|new
name|IndexedGraph
argument_list|()
expr_stmt|;
name|InputStream
name|in
init|=
name|ClerezzaBackendTest
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"testdata.rdf.zip"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
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
name|graph
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
name|graph
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
name|ClerezzaBackend
argument_list|(
name|graph
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
name|Configuration
argument_list|<
name|RDFTerm
argument_list|>
name|config
init|=
operator|new
name|Configuration
argument_list|<
name|RDFTerm
argument_list|>
argument_list|()
decl_stmt|;
name|config
operator|.
name|addNamespace
argument_list|(
literal|"dbp-prop"
argument_list|,
name|NS_DBP
argument_list|)
expr_stmt|;
name|config
operator|.
name|addNamespace
argument_list|(
literal|"dbp-ont"
argument_list|,
name|NS_DBO
argument_list|)
expr_stmt|;
name|ldpath
operator|=
operator|new
name|LDPath
argument_list|<
name|RDFTerm
argument_list|>
argument_list|(
name|backend
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testUriAndListImplemetnation
parameter_list|()
throws|throws
name|LDPathParseException
block|{
name|IRI
name|nationalChampionship
init|=
operator|new
name|IRI
argument_list|(
literal|"http://cv.iptc.org/newscodes/subjectcode/15073031"
argument_list|)
decl_stmt|;
comment|//this program tests:
comment|// * IRI transformers
comment|// * #listSubjects(..) implementation
comment|// * #listObjects(..)  implementation
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|results
init|=
name|ldpath
operator|.
name|programQuery
argument_list|(
name|nationalChampionship
argument_list|,
name|getReader
argument_list|(
literal|"skos:broaderTransitive = (skos:broaderTransitive | ^skos:narrowerTransitive)+;"
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|RDFTerm
argument_list|>
name|expected
init|=
operator|new
name|HashSet
argument_list|<
name|RDFTerm
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://cv.iptc.org/newscodes/subjectcode/15000000"
argument_list|)
argument_list|,
operator|new
name|IRI
argument_list|(
literal|"http://cv.iptc.org/newscodes/subjectcode/15073000"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|?
argument_list|>
name|broaderTransitive
init|=
name|results
operator|.
name|get
argument_list|(
name|NS_SKOS
operator|+
literal|"broaderTransitive"
argument_list|)
decl_stmt|;
for|for
control|(
name|Object
name|concept
range|:
name|broaderTransitive
control|)
block|{
name|assertNotNull
argument_list|(
name|concept
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|concept
operator|instanceof
name|IRI
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|expected
operator|.
name|remove
argument_list|(
name|concept
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
literal|"missing: "
operator|+
name|expected
argument_list|,
name|expected
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
name|testStringTransformer
parameter_list|()
throws|throws
name|LDPathParseException
block|{
name|IRI
name|nationalChampionship
init|=
operator|new
name|IRI
argument_list|(
literal|"http://cv.iptc.org/newscodes/subjectcode/15073031"
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|results
init|=
name|ldpath
operator|.
name|programQuery
argument_list|(
name|nationalChampionship
argument_list|,
name|getReader
argument_list|(
literal|"label = skos:prefLabel[@en-GB] :: xsd:string;"
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|expected
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
literal|"national championship 1st level"
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|?
argument_list|>
name|broaderTransitive
init|=
name|results
operator|.
name|get
argument_list|(
literal|"label"
argument_list|)
decl_stmt|;
for|for
control|(
name|Object
name|concept
range|:
name|broaderTransitive
control|)
block|{
name|assertNotNull
argument_list|(
name|concept
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|concept
operator|instanceof
name|String
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|expected
operator|.
name|remove
argument_list|(
name|concept
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|expected
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
name|testDataTypes
parameter_list|()
throws|throws
name|LDPathParseException
block|{
name|IRI
name|hallein
init|=
operator|new
name|IRI
argument_list|(
literal|"http://dbpedia.org/resource/Hallein"
argument_list|)
decl_stmt|;
name|StringBuilder
name|program
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|program
operator|.
name|append
argument_list|(
literal|"@prefix dbp-prop :<"
argument_list|)
operator|.
name|append
argument_list|(
name|NS_DBP
argument_list|)
operator|.
name|append
argument_list|(
literal|">;"
argument_list|)
expr_stmt|;
name|program
operator|.
name|append
argument_list|(
literal|"@prefix dbp-ont :<"
argument_list|)
operator|.
name|append
argument_list|(
name|NS_DBO
argument_list|)
operator|.
name|append
argument_list|(
literal|">;"
argument_list|)
expr_stmt|;
name|program
operator|.
name|append
argument_list|(
literal|"doubleTest = dbp-ont:areaTotal :: xsd:double;"
argument_list|)
expr_stmt|;
comment|//Double
name|program
operator|.
name|append
argument_list|(
literal|"decimalTest = dbp-ont:areaTotal :: xsd:decimal;"
argument_list|)
expr_stmt|;
comment|//BigDecimal
name|program
operator|.
name|append
argument_list|(
literal|"intTest = dbp-prop:areaCode :: xsd:int;"
argument_list|)
expr_stmt|;
comment|//Integer
name|program
operator|.
name|append
argument_list|(
literal|"longTest = dbp-prop:population :: xsd:long;"
argument_list|)
expr_stmt|;
comment|//Long
name|program
operator|.
name|append
argument_list|(
literal|"uriTest = foaf:homepage :: xsd:anyURI;"
argument_list|)
expr_stmt|;
comment|//xsd:anyUri
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|expected
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|expected
operator|.
name|put
argument_list|(
literal|"doubleTest"
argument_list|,
operator|new
name|Double
argument_list|(
literal|2.698E7
argument_list|)
argument_list|)
expr_stmt|;
name|expected
operator|.
name|put
argument_list|(
literal|"decimalTest"
argument_list|,
operator|new
name|BigDecimal
argument_list|(
literal|"2.698E7"
argument_list|)
argument_list|)
expr_stmt|;
name|expected
operator|.
name|put
argument_list|(
literal|"intTest"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|6245
argument_list|)
argument_list|)
expr_stmt|;
name|expected
operator|.
name|put
argument_list|(
literal|"longTest"
argument_list|,
operator|new
name|Long
argument_list|(
literal|19473L
argument_list|)
argument_list|)
expr_stmt|;
name|expected
operator|.
name|put
argument_list|(
literal|"uriTest"
argument_list|,
literal|"http://www.hallein.gv.at"
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|results
init|=
name|ldpath
operator|.
name|programQuery
argument_list|(
name|hallein
argument_list|,
name|getReader
argument_list|(
name|program
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|results
argument_list|)
expr_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|resultEntry
range|:
name|results
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|assertNotNull
argument_list|(
name|resultEntry
argument_list|)
expr_stmt|;
name|Object
name|expectedResult
init|=
name|expected
operator|.
name|get
argument_list|(
name|resultEntry
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|resultEntry
operator|.
name|getKey
argument_list|()
operator|+
literal|" is not an expected key"
argument_list|,
name|expectedResult
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|resultEntry
operator|.
name|getValue
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
argument_list|)
expr_stmt|;
name|Object
name|resultValue
init|=
name|resultEntry
operator|.
name|getValue
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|resultValue
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|expectedResult
operator|.
name|getClass
argument_list|()
operator|.
name|isAssignableFrom
argument_list|(
name|resultValue
operator|.
name|getClass
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|resultValue
argument_list|,
name|expectedResult
argument_list|)
expr_stmt|;
block|}
block|}
comment|//    @Test
comment|//    public void testTest(){
comment|//        for(Iterator<Triple> it = graph.filter(null, RDF.type, SKOS_CONCEPT);it.hasNext();){
comment|//            log.info("Concept: {}",it.next().getSubject());
comment|//        }
comment|//    }
specifier|public
specifier|static
specifier|final
name|Reader
name|getReader
parameter_list|(
name|String
name|string
parameter_list|)
block|{
if|if
condition|(
name|string
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed string MUST NOT be NULL!"
argument_list|)
throw|;
block|}
try|try
block|{
return|return
operator|new
name|InputStreamReader
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|string
operator|.
name|getBytes
argument_list|(
literal|"utf-8"
argument_list|)
argument_list|)
argument_list|,
literal|"utf-8"
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Encoding 'utf-8' is not supported by this system!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

