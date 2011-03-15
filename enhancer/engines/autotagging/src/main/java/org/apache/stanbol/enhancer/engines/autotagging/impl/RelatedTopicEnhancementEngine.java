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
name|autotagging
operator|.
name|impl
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
name|List
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
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Reference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|autotagging
operator|.
name|Autotagger
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
name|autotagging
operator|.
name|TagInfo
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
name|autotagging
operator|.
name|AutotaggerProvider
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
name|InvalidContentException
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

begin_comment
comment|/**  * OSGi wrapper for the iks-autotagging library. Uses a lucene index of DBpedia  * to suggest related related topics out of the text content of the  * content item.  *  * @author ogrisel  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|RelatedTopicEnhancementEngine
implements|implements
name|EnhancementEngine
block|{
specifier|protected
specifier|static
specifier|final
name|String
name|TEXT_PLAIN_MIMETYPE
init|=
literal|"text/plain"
decl_stmt|;
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
name|RelatedTopicEnhancementEngine
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// TODO: make me configurable through an OSGi property
specifier|protected
name|String
name|type
init|=
literal|"http://www.w3.org/2004/02/skos/core#Concept"
decl_stmt|;
annotation|@
name|Reference
name|AutotaggerProvider
name|autotaggerProvider
decl_stmt|;
specifier|public
name|void
name|setType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
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
name|Autotagger
name|autotagger
init|=
name|autotaggerProvider
operator|.
name|getAutotagger
argument_list|()
decl_stmt|;
if|if
condition|(
name|autotagger
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|" is deactivated: cannot process content item: "
operator|+
name|ci
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
name|String
name|text
decl_stmt|;
try|try
block|{
name|text
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
throw|throw
operator|new
name|InvalidContentException
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
name|e
argument_list|)
throw|;
block|}
if|if
condition|(
name|text
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
comment|// TODO: make the length of the data a field of the ContentItem
comment|// interface to be able to filter out empty items in the canEnhance
comment|// method
name|log
operator|.
name|warn
argument_list|(
literal|"nothing to extract a topic from"
argument_list|)
expr_stmt|;
return|return;
block|}
name|MGraph
name|graph
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|UriRef
name|contentItemId
init|=
operator|new
name|UriRef
argument_list|(
name|ci
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|List
argument_list|<
name|TagInfo
argument_list|>
name|suggestions
init|=
name|autotagger
operator|.
name|suggestForType
argument_list|(
name|text
argument_list|,
name|type
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|NonLiteral
argument_list|>
name|noRelatedEnhancements
init|=
name|Collections
operator|.
name|emptyList
argument_list|()
decl_stmt|;
for|for
control|(
name|TagInfo
name|tag
range|:
name|suggestions
control|)
block|{
name|EnhancementRDFUtils
operator|.
name|writeEntityAnnotation
argument_list|(
name|this
argument_list|,
name|literalFactory
argument_list|,
name|graph
argument_list|,
name|contentItemId
argument_list|,
name|noRelatedEnhancements
argument_list|,
name|tag
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|EngineException
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|public
name|int
name|canEnhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
block|{
name|String
name|mimeType
init|=
name|ci
operator|.
name|getMimeType
argument_list|()
operator|.
name|split
argument_list|(
literal|";"
argument_list|,
literal|2
argument_list|)
index|[
literal|0
index|]
decl_stmt|;
if|if
condition|(
name|TEXT_PLAIN_MIMETYPE
operator|.
name|equalsIgnoreCase
argument_list|(
name|mimeType
argument_list|)
condition|)
block|{
return|return
name|ENHANCE_SYNCHRONOUS
return|;
block|}
return|return
name|CANNOT_ENHANCE
return|;
block|}
specifier|public
name|void
name|bindAutotaggerProvider
parameter_list|(
name|AutotaggerProvider
name|autotaggerProvider
parameter_list|)
block|{
name|this
operator|.
name|autotaggerProvider
operator|=
name|autotaggerProvider
expr_stmt|;
block|}
block|}
end_class

end_unit

