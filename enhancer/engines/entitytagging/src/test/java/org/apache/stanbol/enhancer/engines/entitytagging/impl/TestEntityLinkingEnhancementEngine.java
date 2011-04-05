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
name|rdf
operator|.
name|Properties
operator|.
name|*
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
name|Date
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
name|SimpleMGraph
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
name|helper
operator|.
name|RdfEntityFactory
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
name|TestEntityLinkingEnhancementEngine
block|{
comment|/**      * The context for the tests (same as in TestOpenNLPEnhancementEngine)      */
specifier|public
specifier|static
specifier|final
name|String
name|CONTEXT
init|=
literal|"Dr. Patrick Marshall (1869 - November 1950) was a"
operator|+
literal|" geologist who lived in New Zealand and worked at the University of Otago."
decl_stmt|;
comment|/**      * The person for the tests (same as in TestOpenNLPEnhancementEngine)      */
specifier|public
specifier|static
specifier|final
name|String
name|PERSON
init|=
literal|"Patrick Marshall"
decl_stmt|;
comment|/**      * The organisation for the tests (same as in TestOpenNLPEnhancementEngine)      */
specifier|public
specifier|static
specifier|final
name|String
name|ORGANISATION
init|=
literal|"University of Otago"
decl_stmt|;
comment|/**      * The place for the tests (same as in TestOpenNLPEnhancementEngine)      */
specifier|public
specifier|static
specifier|final
name|String
name|PLACE
init|=
literal|"New Zealand"
decl_stmt|;
specifier|static
name|ReferencedSiteEntityTaggingEnhancementEngine
name|entityLinkingEngine
init|=
operator|new
name|ReferencedSiteEntityTaggingEnhancementEngine
argument_list|()
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
block|{     }
annotation|@
name|Before
specifier|public
name|void
name|bindServices
parameter_list|()
throws|throws
name|IOException
block|{     }
annotation|@
name|After
specifier|public
name|void
name|unbindServices
parameter_list|()
block|{     }
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|shutdownServices
parameter_list|()
block|{     }
specifier|public
specifier|static
name|ContentItem
name|getContentItem
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
name|UriRef
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
name|testAnnotation
init|=
name|factory
operator|.
name|getProxy
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"urn:iks-project:enhancer:test:text-annotation:person"
argument_list|)
argument_list|,
name|TextAnnotation
operator|.
name|class
argument_list|)
decl_stmt|;
name|testAnnotation
operator|.
name|setCreator
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"urn:iks-project:enhancer:test:dummyEngine"
argument_list|)
argument_list|)
expr_stmt|;
name|testAnnotation
operator|.
name|setCreated
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|testAnnotation
operator|.
name|setSelectedText
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|testAnnotation
operator|.
name|setSelectionContext
argument_list|(
name|context
argument_list|)
expr_stmt|;
name|testAnnotation
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
operator|(
name|int
operator|)
name|Math
operator|.
name|random
argument_list|()
operator|*
literal|100
expr_stmt|;
block|}
name|testAnnotation
operator|.
name|setStart
argument_list|(
name|start
argument_list|)
expr_stmt|;
name|testAnnotation
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
comment|//TODO: adapt this test to work with this engine
comment|// -> here the problem is mainly to fake the needed infrastructure
return|return;
comment|//        //create a content item
comment|//        ContentItem ci = getContentItem("urn:iks-project:enhancer:text:content-item:person", CONTEXT);
comment|//        //add three text annotations to be consumed by this test
comment|//        getTextAnnotation(ci, PERSON, CONTEXT, OntologicalClasses.DBPEDIA_PERSON);
comment|//        getTextAnnotation(ci, ORGANISATION, CONTEXT, OntologicalClasses.DBPEDIA_ORGANISATION);
comment|//        getTextAnnotation(ci, PLACE, CONTEXT, OntologicalClasses.DBPEDIA_PLACE);
comment|//        //perform the computation of the enhancements
comment|//        entityLinkingEngine.computeEnhancements(ci);
comment|//        int entityAnnotationCount = checkAllEntityAnnotations(ci.getMetadata());
comment|//        assertEquals(2, entityAnnotationCount);
block|}
comment|/*      * -----------------------------------------------------------------------      * Helper Methods to check Text and EntityAnnotations      * -----------------------------------------------------------------------      */
specifier|private
name|int
name|checkAllEntityAnnotations
parameter_list|(
name|MGraph
name|g
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|entityAnnotationIterator
init|=
name|g
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
name|UriRef
name|entityAnnotation
init|=
operator|(
name|UriRef
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
name|checkEntityAnnotation
argument_list|(
name|g
argument_list|,
name|entityAnnotation
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
comment|/**      * Checks if an entity annotation is valid.      */
specifier|private
name|void
name|checkEntityAnnotation
parameter_list|(
name|MGraph
name|g
parameter_list|,
name|UriRef
name|entityAnnotation
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|relationToTextAnnotationIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|entityAnnotation
argument_list|,
name|DC_RELATION
argument_list|,
literal|null
argument_list|)
decl_stmt|;
comment|// check if the relation to the text annotation is set
name|assertTrue
argument_list|(
name|relationToTextAnnotationIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|relationToTextAnnotationIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
comment|// test if the referred annotations are text annotations
name|UriRef
name|referredTextAnnotation
init|=
operator|(
name|UriRef
operator|)
name|relationToTextAnnotationIterator
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|g
operator|.
name|filter
argument_list|(
name|referredTextAnnotation
argument_list|,
name|RDF_TYPE
argument_list|,
name|ENHANCER_TEXTANNOTATION
argument_list|)
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// test if an entity is referred
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|entityReferenceIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|entityAnnotation
argument_list|,
name|ENHANCER_ENTITY_REFERENCE
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|entityReferenceIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|// test if the reference is an URI
name|assertTrue
argument_list|(
name|entityReferenceIterator
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
operator|instanceof
name|UriRef
argument_list|)
expr_stmt|;
comment|// test if there is only one entity referred
name|assertFalse
argument_list|(
name|entityReferenceIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|// finally test if the entity label is set
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|entityLabelIterator
init|=
name|g
operator|.
name|filter
argument_list|(
name|entityAnnotation
argument_list|,
name|ENHANCER_ENTITY_LABEL
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|entityLabelIterator
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

