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
name|htmlextractor
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
name|Collections
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
name|SimpleMGraph
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
name|Property
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
name|enhancer
operator|.
name|engines
operator|.
name|htmlextractor
operator|.
name|impl
operator|.
name|BundleURIResolver
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
name|htmlextractor
operator|.
name|impl
operator|.
name|ClerezzaRDFUtils
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
name|htmlextractor
operator|.
name|impl
operator|.
name|ExtractorException
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
name|htmlextractor
operator|.
name|impl
operator|.
name|HtmlExtractionRegistry
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
name|htmlextractor
operator|.
name|impl
operator|.
name|HtmlExtractor
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
name|htmlextractor
operator|.
name|impl
operator|.
name|HtmlParser
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
name|htmlextractor
operator|.
name|impl
operator|.
name|InitializationException
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
name|ServiceProperties
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
name|AbstractEnhancementEngine
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
name|NamespaceEnum
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|BundleContext
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
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
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
comment|/**  *  * @author<a href="mailto:kasper@dfki.de">Walter Kasper</a>  *   */
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
argument_list|,
name|inherit
operator|=
literal|true
argument_list|)
annotation|@
name|Service
annotation|@
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
name|Properties
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|,
name|value
operator|=
literal|"htmlextractor"
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|HtmlExtractorEngine
extends|extends
name|AbstractEnhancementEngine
argument_list|<
name|IOException
argument_list|,
name|RuntimeException
argument_list|>
implements|implements
name|EnhancementEngine
implements|,
name|ServiceProperties
block|{
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
name|HtmlExtractorEngine
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * The default charset      */
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
comment|/**      * The default value for the Execution of this Engine. Currently set to      * {@link ServiceProperties#ORDERING_PRE_PROCESSING}      */
specifier|public
specifier|static
specifier|final
name|Integer
name|defaultOrder
init|=
name|ORDERING_PRE_PROCESSING
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|DEFAULT_HTML_EXTRACTOR_REGISTRY
init|=
literal|"htmlextractors.xml"
decl_stmt|;
comment|/**      * name of a file that defines the set of extractors for HTML documents. By default, the builtin file 'htmlextractors.xml' is used."      */
annotation|@
name|Property
argument_list|(
name|value
operator|=
name|HtmlExtractorEngine
operator|.
name|DEFAULT_HTML_EXTRACTOR_REGISTRY
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|HTML_EXTRACTOR_REGISTRY
init|=
literal|"org.apache.stanbol.enhancer.engines.htmlextractor.htmlextractors"
decl_stmt|;
comment|/**      * Internally used to create additional {@link Blob} for transformed      * versions af the original content      */
annotation|@
name|Reference
specifier|private
name|ContentItemFactory
name|ciFactory
decl_stmt|;
name|BundleContext
name|bundleContext
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|supportedMimeTypes
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
operator|new
name|String
index|[]
block|{
literal|"text/html"
block|,
literal|"application/xhtml+xml"
block|}
argument_list|)
argument_list|)
decl_stmt|;
specifier|private
name|HtmlExtractionRegistry
name|htmlExtractorRegistry
decl_stmt|;
specifier|private
name|HtmlParser
name|htmlParser
decl_stmt|;
specifier|private
name|boolean
name|singleRootRdf
init|=
literal|true
decl_stmt|;
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ce
parameter_list|)
throws|throws
name|ConfigurationException
throws|,
name|IOException
block|{
name|super
operator|.
name|activate
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|this
operator|.
name|bundleContext
operator|=
name|ce
operator|.
name|getBundleContext
argument_list|()
expr_stmt|;
name|BundleURIResolver
operator|.
name|BUNDLE
operator|=
name|this
operator|.
name|bundleContext
operator|.
name|getBundle
argument_list|()
expr_stmt|;
name|String
name|htmlExtractors
init|=
name|DEFAULT_HTML_EXTRACTOR_REGISTRY
decl_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
init|=
name|ce
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|String
name|confFile
init|=
operator|(
name|String
operator|)
name|properties
operator|.
name|get
argument_list|(
name|HTML_EXTRACTOR_REGISTRY
argument_list|)
decl_stmt|;
if|if
condition|(
name|confFile
operator|!=
literal|null
operator|&&
name|confFile
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|htmlExtractors
operator|=
name|confFile
expr_stmt|;
block|}
try|try
block|{
name|this
operator|.
name|htmlExtractorRegistry
operator|=
operator|new
name|HtmlExtractionRegistry
argument_list|(
name|htmlExtractors
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InitializationException
name|e
parameter_list|)
block|{
name|LOG
operator|.
name|error
argument_list|(
literal|"Registry Initialization Error: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IOException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
name|this
operator|.
name|htmlParser
operator|=
operator|new
name|HtmlParser
argument_list|()
expr_stmt|;
block|}
comment|/**      * The deactivate method.      *      * @param ce the {@link ComponentContext}      */
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ce
parameter_list|)
block|{
name|super
operator|.
name|deactivate
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|this
operator|.
name|htmlParser
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|htmlExtractorRegistry
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getServiceProperties
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|Collections
operator|.
name|singletonMap
argument_list|(
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|,
operator|(
name|Object
operator|)
name|defaultOrder
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
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
name|LOG
operator|.
name|info
argument_list|(
literal|"MimeType: {}"
argument_list|,
name|ci
operator|.
name|getMimeType
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|isSupported
argument_list|(
name|ci
operator|.
name|getMimeType
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|ENHANCE_ASYNC
return|;
block|}
return|return
name|CANNOT_ENHANCE
return|;
block|}
annotation|@
name|Override
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
name|HtmlExtractor
name|extractor
init|=
operator|new
name|HtmlExtractor
argument_list|(
name|htmlExtractorRegistry
argument_list|,
name|htmlParser
argument_list|)
decl_stmt|;
name|MGraph
name|model
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|extractor
operator|.
name|extract
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
name|ci
operator|.
name|getStream
argument_list|()
argument_list|,
literal|null
argument_list|,
name|ci
operator|.
name|getMimeType
argument_list|()
argument_list|,
name|model
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ExtractorException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"Error while processing ContentItem "
operator|+
name|ci
operator|.
name|getUri
argument_list|()
operator|+
literal|" with HtmlExtractor"
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
name|ClerezzaRDFUtils
operator|.
name|urifyBlankNodes
argument_list|(
name|model
argument_list|)
expr_stmt|;
comment|// make the model single rooted
if|if
condition|(
name|singleRootRdf
condition|)
block|{
name|ClerezzaRDFUtils
operator|.
name|makeConnected
argument_list|(
name|model
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|nie
operator|+
literal|"contains"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//add the extracted triples to the metadata of the ContentItem
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|LOG
operator|.
name|info
argument_list|(
literal|"Model: {}"
argument_list|,
name|model
argument_list|)
expr_stmt|;
name|ci
operator|.
name|getMetadata
argument_list|()
operator|.
name|addAll
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|model
operator|=
literal|null
expr_stmt|;
block|}
finally|finally
block|{
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
specifier|private
name|boolean
name|isSupported
parameter_list|(
name|String
name|mimeType
parameter_list|)
block|{
return|return
name|this
operator|.
name|supportedMimeTypes
operator|.
name|contains
argument_list|(
name|mimeType
argument_list|)
return|;
block|}
block|}
end_class

end_unit
