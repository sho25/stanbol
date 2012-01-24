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
name|serviceapi
operator|.
name|helper
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
name|ENHANCER_RELATED_CONTENT_ITEM
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
name|ENHANCER_EXTRACTION
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
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|EnhancementEngine
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
name|helper
operator|.
name|InMemoryContentItem
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
name|EnhancementEngineHelperTest
block|{
specifier|public
specifier|static
class|class
name|MyEngine
implements|implements
name|EnhancementEngine
block|{
specifier|public
name|int
name|canEnhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
return|return
literal|0
return|;
block|}
specifier|public
name|void
name|computeEnhancements
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
comment|// do nothing
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
return|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEnhancementEngineHelper
parameter_list|()
throws|throws
name|Exception
block|{
name|ContentItem
name|ci
init|=
operator|new
name|InMemoryContentItem
argument_list|(
operator|new
name|UriRef
argument_list|(
literal|"urn:test:contentItem"
argument_list|)
argument_list|,
literal|"There is content"
argument_list|,
literal|"text/plain"
argument_list|)
decl_stmt|;
name|EnhancementEngine
name|engine
init|=
operator|new
name|MyEngine
argument_list|()
decl_stmt|;
name|UriRef
name|extraction
init|=
name|EnhancementEngineHelper
operator|.
name|createNewExtraction
argument_list|(
name|ci
argument_list|,
name|engine
argument_list|)
decl_stmt|;
name|MGraph
name|metadata
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|metadata
operator|.
name|contains
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|extraction
argument_list|,
name|ENHANCER_RELATED_CONTENT_ITEM
argument_list|,
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
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|metadata
operator|.
name|contains
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|extraction
argument_list|,
name|RDF_TYPE
argument_list|,
name|ENHANCER_EXTRACTION
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// and so on
block|}
block|}
end_class

end_unit

