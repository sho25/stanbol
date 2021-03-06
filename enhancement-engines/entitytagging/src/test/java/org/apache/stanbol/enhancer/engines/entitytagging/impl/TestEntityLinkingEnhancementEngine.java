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
name|entitytagging
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
name|helper
operator|.
name|EnhancementEngineHelper
operator|.
name|randomUUID
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
name|OntologicalClasses
operator|.
name|DBPEDIA_ORGANISATION
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
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
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
name|OntologicalClasses
operator|.
name|DBPEDIA_PLACE
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
name|DC_CREATOR
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
name|DC_RELATION
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
name|ENHANCER_CONFIDENCE
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
name|ENHANCER_ENTITY_LABEL
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
name|ENHANCER_ENTITY_REFERENCE
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
name|ENHANCER_EXTRACTED_FROM
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
name|ENHANCER_ENTITYANNOTATION
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
name|validateEntityAnnotation
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
name|assertTrue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|commons
operator|.
name|rdf
operator|.
name|impl
operator|.
name|utils
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
name|commons
operator|.
name|rdf
operator|.
name|impl
operator|.
name|utils
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
name|rdfentities
operator|.
name|RdfEntityFactory
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
name|rdfentities
operator|.
name|fise
operator|.
name|TextAnnotation
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
name|OntologicalClasses
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
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|Entityhub
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|rdf
operator|.
name|RdfResourceEnum
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
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
name|TestEntityLinkingEnhancementEngine
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
name|TestEntityLinkingEnhancementEngine
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CONTEXT
init|=
literal|"In March 2009, Condoleezza Rice returned "
operator|+
literal|"to Stanford University near Palo Alto."
decl_stmt|;
comment|//The old text replaced by STANBOL-1163
comment|//    public static final String CONTEXT = "Dr. Patrick Marshall (1869 - November 1950) was a"
comment|//        + " geologist who lived in New Zealand and worked at the University of Otago.";
comment|/**      * The person for the tests       */
specifier|public
specifier|static
specifier|final
name|String
name|PERSON
init|=
literal|", Condoleezza Rice"
decl_stmt|;
comment|/**      * The organisation for the tests (same as in TestOpenNLPEnhancementEngine)      */
specifier|public
specifier|static
specifier|final
name|String
name|ORGANISATION
init|=
literal|"Stanford University"
decl_stmt|;
comment|/**      * The place for the tests (same as in TestOpenNLPEnhancementEngine)      */
specifier|public
specifier|static
specifier|final
name|String
name|PLACE
init|=
literal|"Palo Alto"
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
name|String
name|userDir
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.dir"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Entityhub
name|entityhub
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
comment|//TODO: set user.dir to /target/test-files
name|File
name|testFiles
init|=
operator|new
name|File
argument_list|(
literal|"./target/test-files"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|testFiles
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|testFiles
operator|.
name|mkdirs
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Unable to create directory for test files "
operator|+
name|testFiles
argument_list|)
throw|;
block|}
block|}
name|String
name|testRootDir
init|=
name|testFiles
operator|.
name|getCanonicalPath
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Test 'user.dir' folder {}"
argument_list|,
name|testRootDir
argument_list|)
expr_stmt|;
name|System
operator|.
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"user.dir"
argument_list|,
name|testRootDir
argument_list|)
expr_stmt|;
name|entityhub
operator|=
operator|new
name|MockEntityhub
argument_list|()
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
name|System
operator|.
name|getProperties
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"user.dir"
argument_list|,
name|userDir
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|NamedEntityTaggingEngine
name|initEngine
parameter_list|(
name|boolean
name|person
parameter_list|,
name|boolean
name|organisation
parameter_list|,
name|boolean
name|place
parameter_list|)
block|{
name|NamedEntityTaggingEngine
name|entityLinkingEngine
init|=
operator|new
name|NamedEntityTaggingEngine
argument_list|()
decl_stmt|;
comment|//instead of calling activate we directly set the required fields
comment|//we need a data source for linking
name|entityLinkingEngine
operator|.
name|entityhub
operator|=
name|entityhub
expr_stmt|;
name|entityLinkingEngine
operator|.
name|personState
operator|=
name|person
expr_stmt|;
name|entityLinkingEngine
operator|.
name|personType
operator|=
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
operator|.
name|getUnicodeString
argument_list|()
expr_stmt|;
name|entityLinkingEngine
operator|.
name|orgState
operator|=
name|organisation
expr_stmt|;
name|entityLinkingEngine
operator|.
name|orgType
operator|=
name|OntologicalClasses
operator|.
name|DBPEDIA_ORGANISATION
operator|.
name|getUnicodeString
argument_list|()
expr_stmt|;
name|entityLinkingEngine
operator|.
name|placeState
operator|=
name|place
expr_stmt|;
name|entityLinkingEngine
operator|.
name|placeType
operator|=
name|OntologicalClasses
operator|.
name|DBPEDIA_PLACE
operator|.
name|getUnicodeString
argument_list|()
expr_stmt|;
name|entityLinkingEngine
operator|.
name|nameField
operator|=
name|Properties
operator|.
name|RDFS_LABEL
operator|.
name|getUnicodeString
argument_list|()
expr_stmt|;
comment|//not implemented
name|entityLinkingEngine
operator|.
name|dereferenceEntities
operator|=
literal|false
expr_stmt|;
return|return
name|entityLinkingEngine
return|;
block|}
comment|/**      * Creates and initialises a new content item using {@link #CONTEXT} as      * content and       * @return      * @throws IOException      */
specifier|private
name|ContentItem
name|initContentItem
parameter_list|()
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
name|IRI
argument_list|(
literal|"urn:iks-project:enhancer:text:content-item:person"
argument_list|)
argument_list|,
operator|new
name|StringSource
argument_list|(
name|CONTEXT
argument_list|)
argument_list|)
decl_stmt|;
comment|//add three text annotations to be consumed by this test
name|getTextAnnotation
argument_list|(
name|ci
argument_list|,
name|PERSON
argument_list|,
name|CONTEXT
argument_list|,
name|DBPEDIA_PERSON
argument_list|)
expr_stmt|;
name|getTextAnnotation
argument_list|(
name|ci
argument_list|,
name|ORGANISATION
argument_list|,
name|CONTEXT
argument_list|,
name|DBPEDIA_ORGANISATION
argument_list|)
expr_stmt|;
name|getTextAnnotation
argument_list|(
name|ci
argument_list|,
name|PLACE
argument_list|,
name|CONTEXT
argument_list|,
name|DBPEDIA_PLACE
argument_list|)
expr_stmt|;
comment|//add the language
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
return|return
name|ci
return|;
block|}
specifier|public
specifier|static
name|void
name|getTextAnnotation
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|context
parameter_list|,
name|IRI
name|type
parameter_list|)
block|{
name|String
name|content
decl_stmt|;
try|try
block|{
name|content
operator|=
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
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|//should never happen anyway!
name|content
operator|=
literal|""
expr_stmt|;
block|}
name|RdfEntityFactory
name|factory
init|=
name|RdfEntityFactory
operator|.
name|createInstance
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|)
decl_stmt|;
name|TextAnnotation
name|textAnnotation
init|=
name|factory
operator|.
name|getProxy
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"urn:iks-project:enhancer:test:text-annotation:"
operator|+
name|randomUUID
argument_list|()
argument_list|)
argument_list|,
name|TextAnnotation
operator|.
name|class
argument_list|)
decl_stmt|;
name|textAnnotation
operator|.
name|setCreator
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"urn:iks-project:enhancer:test:dummyEngine"
argument_list|)
argument_list|)
expr_stmt|;
name|textAnnotation
operator|.
name|setCreated
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|textAnnotation
operator|.
name|setSelectedText
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|textAnnotation
operator|.
name|setSelectionContext
argument_list|(
name|context
argument_list|)
expr_stmt|;
name|textAnnotation
operator|.
name|getDcType
argument_list|()
operator|.
name|add
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|Integer
name|start
init|=
name|content
operator|.
name|indexOf
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|start
operator|<
literal|0
condition|)
block|{
comment|//if not found in the content
comment|//set some random numbers for start/end
name|start
operator|=
call|(
name|int
call|)
argument_list|(
name|Math
operator|.
name|random
argument_list|()
operator|*
literal|100
argument_list|)
expr_stmt|;
block|}
name|textAnnotation
operator|.
name|setStart
argument_list|(
name|start
argument_list|)
expr_stmt|;
name|textAnnotation
operator|.
name|setEnd
argument_list|(
name|start
operator|+
name|name
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEntityLinkingEnhancementEngine
parameter_list|()
throws|throws
name|Exception
block|{
comment|//create a content item
name|ContentItem
name|ci
init|=
name|initContentItem
argument_list|()
decl_stmt|;
name|NamedEntityTaggingEngine
name|entityLinkingEngine
init|=
name|initEngine
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
decl_stmt|;
comment|//perform the computation of the enhancements
name|entityLinkingEngine
operator|.
name|computeEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|int
name|entityAnnotationCount
init|=
name|validateAllEntityAnnotations
argument_list|(
name|entityLinkingEngine
argument_list|,
name|ci
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|entityAnnotationCount
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testPersonLinking
parameter_list|()
throws|throws
name|Exception
block|{
comment|//create a content item
name|ContentItem
name|ci
init|=
name|initContentItem
argument_list|()
decl_stmt|;
name|NamedEntityTaggingEngine
name|entityLinkingEngine
init|=
name|initEngine
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|//perform the computation of the enhancements
name|entityLinkingEngine
operator|.
name|computeEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|int
name|entityAnnotationCount
init|=
name|validateAllEntityAnnotations
argument_list|(
name|entityLinkingEngine
argument_list|,
name|ci
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entityAnnotationCount
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testOrganizationLinking
parameter_list|()
throws|throws
name|Exception
block|{
comment|//create a content item
name|ContentItem
name|ci
init|=
name|initContentItem
argument_list|()
decl_stmt|;
name|NamedEntityTaggingEngine
name|entityLinkingEngine
init|=
name|initEngine
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|//perform the computation of the enhancements
name|entityLinkingEngine
operator|.
name|computeEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|int
name|entityAnnotationCount
init|=
name|validateAllEntityAnnotations
argument_list|(
name|entityLinkingEngine
argument_list|,
name|ci
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entityAnnotationCount
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testLocationLinking
parameter_list|()
throws|throws
name|Exception
block|{
comment|//create a content item
name|ContentItem
name|ci
init|=
name|initContentItem
argument_list|()
decl_stmt|;
name|NamedEntityTaggingEngine
name|entityLinkingEngine
init|=
name|initEngine
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
decl_stmt|;
comment|//perform the computation of the enhancements
name|entityLinkingEngine
operator|.
name|computeEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|int
name|entityAnnotationCount
init|=
name|validateAllEntityAnnotations
argument_list|(
name|entityLinkingEngine
argument_list|,
name|ci
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entityAnnotationCount
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|int
name|validateAllEntityAnnotations
parameter_list|(
name|NamedEntityTaggingEngine
name|entityLinkingEngine
parameter_list|,
name|ContentItem
name|ci
parameter_list|)
block|{
name|Map
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
name|DC_CREATOR
argument_list|,
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createTypedLiteral
argument_list|(
name|entityLinkingEngine
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|entityAnnotationIterator
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|RDF_TYPE
argument_list|,
name|ENHANCER_ENTITYANNOTATION
argument_list|)
decl_stmt|;
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
name|int
name|entityAnnotationCount
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|entityAnnotationIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|IRI
name|entityAnnotation
init|=
operator|(
name|IRI
operator|)
name|entityAnnotationIterator
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
decl_stmt|;
comment|// test if selected Text is added
name|validateEntityAnnotation
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|entityAnnotation
argument_list|,
name|expectedValues
argument_list|)
expr_stmt|;
comment|//fise:confidence now checked by EnhancementStructureHelper (STANBOL-630)
comment|//            Iterator<Triple> confidenceIterator = ci.getMetadata().filter(entityAnnotation, ENHANCER_CONFIDENCE, null);
comment|//            assertTrue("Expected fise:confidence value is missing (entityAnnotation "
comment|//                    +entityAnnotation+")",confidenceIterator.hasNext());
comment|//            Double confidence = LiteralFactory.getInstance().createObject(Double.class,
comment|//                (TypedLiteral)confidenceIterator.next().getObject());
comment|//            assertTrue("fise:confidence MUST BE<= 1 (value= '"+confidence
comment|//                    + "',entityAnnotation " +entityAnnotation+")",
comment|//                    1.0>= confidence.doubleValue());
comment|//            assertTrue("fise:confidence MUST BE>= 0 (value= '"+confidence
comment|//                    +"',entityAnnotation "+entityAnnotation+")",
comment|//                    0.0<= confidence.doubleValue());
comment|//Test the entityhub:site property (STANBOL-625)
name|IRI
name|ENTITYHUB_SITE
init|=
operator|new
name|IRI
argument_list|(
name|RdfResourceEnum
operator|.
name|site
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|entitySiteIterator
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
operator|.
name|filter
argument_list|(
name|entityAnnotation
argument_list|,
name|ENTITYHUB_SITE
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"Expected entityhub:site value is missing (entityAnnotation "
operator|+
name|entityAnnotation
operator|+
literal|")"
argument_list|,
name|entitySiteIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|RDFTerm
name|siteResource
init|=
name|entitySiteIterator
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
literal|"entityhub:site values MUST BE Literals"
argument_list|,
name|siteResource
operator|instanceof
name|Literal
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"'dbpedia' is expected as entityhub:site value"
argument_list|,
literal|"dbpedia"
argument_list|,
operator|(
operator|(
name|Literal
operator|)
name|siteResource
operator|)
operator|.
name|getLexicalForm
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
literal|"entityhub:site MUST HAVE only a single value"
argument_list|,
name|entitySiteIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|entityAnnotationCount
operator|++
expr_stmt|;
block|}
return|return
name|entityAnnotationCount
return|;
block|}
block|}
end_class

end_unit

