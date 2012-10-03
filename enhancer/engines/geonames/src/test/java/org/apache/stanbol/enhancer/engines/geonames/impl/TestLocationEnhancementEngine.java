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
name|geonames
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
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
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
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
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
name|SocketTimeoutException
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
name|Date
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
name|RemoteServiceHelper
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
name|TestLocationEnhancementEngine
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
name|TestLocationEnhancementEngine
operator|.
name|class
argument_list|)
decl_stmt|;
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
specifier|static
name|LocationEnhancementEngine
name|locationEnhancementEngine
init|=
operator|new
name|LocationEnhancementEngine
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
name|PROPERTY_NAME
argument_list|,
literal|"geonames"
argument_list|)
expr_stmt|;
comment|// use the anonymous service for the unit tests
name|properties
operator|.
name|put
argument_list|(
name|LocationEnhancementEngine
operator|.
name|GEONAMES_USERNAME
argument_list|,
literal|"\u0073\u0074\u0061\u006E\u0062\u006F\u006C"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|LocationEnhancementEngine
operator|.
name|GEONAMES_TOKEN
argument_list|,
literal|"\u0073\u0074\u006E\u0062\u006C\u002E\u0075\u0074"
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
name|locationEnhancementEngine
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
name|locationEnhancementEngine
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
throws|throws
name|IOException
block|{
return|return
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
literal|"urn:org.apache:stanbol.enhancer:test:text-annotation:person"
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
literal|"urn:org.apache:stanbol.enhancer:test:dummyEngine"
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
name|testLocationEnhancementEngine
parameter_list|()
throws|throws
name|IOException
throws|,
name|EngineException
block|{
comment|//create a content item
name|ContentItem
name|ci
init|=
name|getContentItem
argument_list|(
literal|"urn:org.apache:stanbol.enhancer:text:content-item:person"
argument_list|,
name|CONTEXT
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
comment|//perform the computation of the enhancements
try|try
block|{
name|locationEnhancementEngine
operator|.
name|computeEnhancements
argument_list|(
name|ci
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|EngineException
name|e
parameter_list|)
block|{
name|RemoteServiceHelper
operator|.
name|checkServiceUnavailable
argument_list|(
name|e
argument_list|,
literal|"overloaded with requests"
argument_list|)
expr_stmt|;
return|return;
block|}
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
name|locationEnhancementEngine
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
comment|/*          * Note:          *  - Expected results depend on the geonames.org data. So if the test          *    fails it may also mean that the data provided by geonames.org have          *    changed          */
name|int
name|entityAnnotationCount
init|=
name|validateAllEntityAnnotations
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|expectedValues
argument_list|)
decl_stmt|;
comment|//two suggestions for New Zealand and one hierarchy entry for the first
comment|//suggestion
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|entityAnnotationCount
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

