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
name|dbpspotlight
operator|.
name|annotate
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PARAM_CONFIDENCE
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PARAM_DISAMBIGUATOR
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PARAM_RESTRICTION
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PARAM_SPARQL
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PARAM_SPOTTER
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PARAM_SUPPORT
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|PARAM_URL_KEY
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|Constants
operator|.
name|UTF8
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|utils
operator|.
name|SpotlightEngineUtils
operator|.
name|getConnectionTimeout
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|utils
operator|.
name|XMLParser
operator|.
name|loadXMLFromInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedWriter
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
name|OutputStreamWriter
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
name|net
operator|.
name|HttpURLConnection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLEncoder
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
name|Language
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
name|Properties
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
name|commons
operator|.
name|stanboltools
operator|.
name|offline
operator|.
name|OfflineMode
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
name|stanboltools
operator|.
name|offline
operator|.
name|OnlineMode
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
name|dbpspotlight
operator|.
name|model
operator|.
name|Annotation
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
name|dbpspotlight
operator|.
name|model
operator|.
name|SurfaceForm
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
name|dbpspotlight
operator|.
name|utils
operator|.
name|SpotlightEngineUtils
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

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_comment
comment|/**  * {@link DBPSpotlightAnnotateEnhancementEngine} provides functionality to  * enhance a document using the DBpedia Spotlight /annotate REST endpoint  *   * @author Iavor Jelev, Babelmonkeys (GzEvD)  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|metatype
operator|=
literal|true
argument_list|,
name|immediate
operator|=
literal|true
argument_list|,
name|label
operator|=
literal|"%stanbol.DBPSpotlightAnnotateEnhancementEngine.name"
argument_list|,
name|description
operator|=
literal|"%stanbol.DBPSpotlightAnnotateEnhancementEngine.description"
argument_list|)
annotation|@
name|Service
annotation|@
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
literal|"dbpspotlightannotate"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PARAM_URL_KEY
argument_list|,
name|value
operator|=
literal|"http://spotlight.dbpedia.org/rest/annotate"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PARAM_SPOTTER
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PARAM_DISAMBIGUATOR
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PARAM_RESTRICTION
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PARAM_SPARQL
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PARAM_SUPPORT
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|PARAM_CONFIDENCE
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|DBPSpotlightAnnotateEnhancementEngine
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
comment|/** 	 * Ensures this engine is deactivated in {@link OfflineMode} 	 */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
annotation|@
name|Reference
specifier|private
name|OnlineMode
name|onlineMode
decl_stmt|;
comment|/** 	 * The default value for the Execution of this Engine. 	 */
specifier|public
specifier|static
specifier|final
name|Integer
name|defaultOrder
init|=
name|ORDERING_CONTENT_EXTRACTION
operator|-
literal|27
decl_stmt|;
comment|/** holds the logger. */
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
name|DBPSpotlightAnnotateEnhancementEngine
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/** holds the url of the Spotlight REST endpoint */
specifier|private
name|URL
name|spotlightUrl
decl_stmt|;
comment|/** holds the chosen of spotter to be used */
specifier|private
name|String
name|spotlightSpotter
decl_stmt|;
comment|/** holds the chosen of disambiguator to be used */
specifier|private
name|String
name|spotlightDisambiguator
decl_stmt|;
comment|/** holds the type restriction for the results, if the user wishes one */
specifier|private
name|String
name|spotlightTypesRestriction
decl_stmt|;
comment|/** holds the chosen minimal support value */
specifier|private
name|String
name|spotlightSupport
decl_stmt|;
comment|/** holds the chosen minimal confidence value */
specifier|private
name|String
name|spotlightConfidence
decl_stmt|;
comment|/** holds the sparql restriction for the results, if the user wishes one */
specifier|private
name|String
name|spotlightSparql
decl_stmt|;
specifier|private
name|int
name|connectionTimeout
decl_stmt|;
comment|/** 	 * Default constructor used by OSGI. Expects {@link #activate(ComponentContext)} 	 * to be called before the instance is used. 	 */
specifier|public
name|DBPSpotlightAnnotateEnhancementEngine
parameter_list|()
block|{}
comment|/** 	 * Constructor intended to be used by unit tests 	 * @param spotlightUrl 	 */
specifier|protected
name|DBPSpotlightAnnotateEnhancementEngine
parameter_list|(
name|URL
name|spotlightUrl
parameter_list|,
name|int
name|connectionTimeout
parameter_list|)
block|{
name|this
operator|.
name|spotlightUrl
operator|=
name|spotlightUrl
expr_stmt|;
name|this
operator|.
name|connectionTimeout
operator|=
name|connectionTimeout
expr_stmt|;
block|}
comment|/** 	 * Initialize all parameters from the configuration panel, or with their 	 * default values 	 *  	 * @param ce 	 *            the {@link ComponentContext} 	 */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
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
name|spotlightUrl
operator|=
name|SpotlightEngineUtils
operator|.
name|parseSpotlightServiceURL
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|connectionTimeout
operator|=
name|getConnectionTimeout
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|spotlightSpotter
operator|=
name|properties
operator|.
name|get
argument_list|(
name|PARAM_SPOTTER
argument_list|)
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|String
operator|)
name|properties
operator|.
name|get
argument_list|(
name|PARAM_SPOTTER
argument_list|)
expr_stmt|;
name|spotlightDisambiguator
operator|=
name|properties
operator|.
name|get
argument_list|(
name|PARAM_DISAMBIGUATOR
argument_list|)
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|String
operator|)
name|properties
operator|.
name|get
argument_list|(
name|PARAM_DISAMBIGUATOR
argument_list|)
expr_stmt|;
name|spotlightTypesRestriction
operator|=
name|properties
operator|.
name|get
argument_list|(
name|PARAM_RESTRICTION
argument_list|)
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|String
operator|)
name|properties
operator|.
name|get
argument_list|(
name|PARAM_RESTRICTION
argument_list|)
expr_stmt|;
name|spotlightSparql
operator|=
name|properties
operator|.
name|get
argument_list|(
name|PARAM_SPARQL
argument_list|)
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|String
operator|)
name|properties
operator|.
name|get
argument_list|(
name|PARAM_SPARQL
argument_list|)
expr_stmt|;
name|spotlightSupport
operator|=
name|properties
operator|.
name|get
argument_list|(
name|PARAM_SUPPORT
argument_list|)
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|String
operator|)
name|properties
operator|.
name|get
argument_list|(
name|PARAM_SUPPORT
argument_list|)
expr_stmt|;
name|spotlightConfidence
operator|=
name|properties
operator|.
name|get
argument_list|(
name|PARAM_CONFIDENCE
argument_list|)
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|String
operator|)
name|properties
operator|.
name|get
argument_list|(
name|PARAM_CONFIDENCE
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Check if the content can be enhanced 	 *  	 * @param ci 	 *            the {@link ContentItem} 	 */
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
name|SpotlightEngineUtils
operator|.
name|canProcess
argument_list|(
name|ci
argument_list|)
condition|?
name|ENHANCE_ASYNC
else|:
name|CANNOT_ENHANCE
return|;
block|}
comment|/** 	 * Calculate the enhancements by doing a POST request to the DBpedia 	 * Spotlight endpoint and processing the results 	 *  	 * @param ci 	 *            the {@link ContentItem} 	 */
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
name|Language
name|language
init|=
name|SpotlightEngineUtils
operator|.
name|getContentLanguage
argument_list|(
name|ci
argument_list|)
decl_stmt|;
name|String
name|text
init|=
name|SpotlightEngineUtils
operator|.
name|getPlainContent
argument_list|(
name|ci
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|Annotation
argument_list|>
name|dbpslGraph
init|=
name|doPostRequest
argument_list|(
name|text
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|SurfaceForm
argument_list|,
name|UriRef
argument_list|>
name|surfaceForm2TextAnnotation
init|=
operator|new
name|HashMap
argument_list|<
name|SurfaceForm
argument_list|,
name|UriRef
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|dbpslGraph
operator|!=
literal|null
condition|)
block|{
comment|// Acquire a write lock on the ContentItem when adding the
comment|// enhancements
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
name|createEnhancements
argument_list|(
name|dbpslGraph
argument_list|,
name|ci
argument_list|,
name|text
argument_list|,
name|language
argument_list|,
name|surfaceForm2TextAnnotation
argument_list|)
expr_stmt|;
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|Serializer
name|serializer
init|=
name|Serializer
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|ByteArrayOutputStream
name|debugStream
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|serializer
operator|.
name|serialize
argument_list|(
name|debugStream
argument_list|,
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
literal|"application/rdf+xml"
argument_list|)
expr_stmt|;
try|try
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"DBPedia Spotlight Enhancements:\n{}"
argument_list|,
name|debugStream
operator|.
name|toString
argument_list|(
literal|"UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
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
block|}
comment|/** 	 * This generates enhancement structures for the entities from DBPedia 	 * Spotlight and adds them to the content item's metadata. For each entity a 	 * TextAnnotation and an EntityAnnotation are created. An EntityAnnotation 	 * can relate to several TextAnnotations. 	 *  	 * @param occs 	 *            a Collection of entity information 	 * @param ci 	 *            the content item 	 */
specifier|protected
name|void
name|createEnhancements
parameter_list|(
name|Collection
argument_list|<
name|Annotation
argument_list|>
name|occs
parameter_list|,
name|ContentItem
name|ci
parameter_list|,
name|String
name|text
parameter_list|,
name|Language
name|language
parameter_list|,
name|Map
argument_list|<
name|SurfaceForm
argument_list|,
name|UriRef
argument_list|>
name|surfaceForm2TextAnnotation
parameter_list|)
block|{
for|for
control|(
name|Annotation
name|occ
range|:
name|occs
control|)
block|{
name|UriRef
name|textAnnotation
init|=
name|surfaceForm2TextAnnotation
operator|.
name|get
argument_list|(
name|occ
operator|.
name|surfaceForm
argument_list|)
decl_stmt|;
if|if
condition|(
name|textAnnotation
operator|==
literal|null
condition|)
block|{
comment|//not yet written ... create a new
name|textAnnotation
operator|=
name|SpotlightEngineUtils
operator|.
name|createTextEnhancement
argument_list|(
name|occ
operator|.
name|surfaceForm
argument_list|,
name|this
argument_list|,
name|ci
argument_list|,
name|text
argument_list|,
name|language
argument_list|)
expr_stmt|;
name|surfaceForm2TextAnnotation
operator|.
name|put
argument_list|(
name|occ
operator|.
name|surfaceForm
argument_list|,
name|textAnnotation
argument_list|)
expr_stmt|;
block|}
name|SpotlightEngineUtils
operator|.
name|createEntityAnnotation
argument_list|(
name|occ
argument_list|,
name|this
argument_list|,
name|ci
argument_list|,
name|textAnnotation
argument_list|,
name|language
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 * Sends a POST request to the DBpediaSpotlight endpoint. 	 *  	 * @param text 	 *            a<code>String</code> with the text to be analyzed 	 * @param contentItemUri 	 *            the URI of the content item (only used for logging) 	 * @return a<code>Collection<DBPSLAnnotation></code> with the server 	 *         response 	 * @throws EngineException 	 *             if the request cannot be sent 	 */
specifier|protected
name|Collection
argument_list|<
name|Annotation
argument_list|>
name|doPostRequest
parameter_list|(
name|String
name|text
parameter_list|,
name|UriRef
name|contentItemUri
parameter_list|)
throws|throws
name|EngineException
block|{
name|HttpURLConnection
name|connection
init|=
literal|null
decl_stmt|;
name|BufferedWriter
name|wr
init|=
literal|null
decl_stmt|;
try|try
block|{
name|connection
operator|=
operator|(
name|HttpURLConnection
operator|)
name|spotlightUrl
operator|.
name|openConnection
argument_list|()
expr_stmt|;
name|connection
operator|.
name|setRequestMethod
argument_list|(
literal|"POST"
argument_list|)
expr_stmt|;
name|connection
operator|.
name|setRequestProperty
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/x-www-form-urlencoded"
argument_list|)
expr_stmt|;
name|connection
operator|.
name|setRequestProperty
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/xml"
argument_list|)
expr_stmt|;
comment|//set ConnectionTimeout (if configured)
if|if
condition|(
name|connectionTimeout
operator|>
literal|0
condition|)
block|{
name|connection
operator|.
name|setConnectTimeout
argument_list|(
name|connectionTimeout
operator|*
literal|1000
argument_list|)
expr_stmt|;
name|connection
operator|.
name|setReadTimeout
argument_list|(
name|connectionTimeout
operator|*
literal|1000
argument_list|)
expr_stmt|;
block|}
name|connection
operator|.
name|setUseCaches
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|connection
operator|.
name|setDoInput
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|connection
operator|.
name|setDoOutput
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// Send request
name|wr
operator|=
operator|new
name|BufferedWriter
argument_list|(
operator|new
name|OutputStreamWriter
argument_list|(
name|connection
operator|.
name|getOutputStream
argument_list|()
argument_list|,
name|UTF8
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|wr
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"Unable to open connection to "
operator|+
name|spotlightUrl
argument_list|,
name|e
argument_list|)
throw|;
block|}
try|try
block|{
if|if
condition|(
name|spotlightSpotter
operator|!=
literal|null
operator|&&
operator|!
name|spotlightSpotter
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|wr
operator|.
name|write
argument_list|(
literal|"spotter="
argument_list|)
expr_stmt|;
name|wr
operator|.
name|write
argument_list|(
name|URLEncoder
operator|.
name|encode
argument_list|(
name|spotlightSpotter
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
name|wr
operator|.
name|write
argument_list|(
literal|'&'
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|spotlightDisambiguator
operator|!=
literal|null
operator|&&
operator|!
name|spotlightDisambiguator
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|wr
operator|.
name|write
argument_list|(
literal|"disambiguator="
argument_list|)
expr_stmt|;
name|wr
operator|.
name|write
argument_list|(
name|URLEncoder
operator|.
name|encode
argument_list|(
name|spotlightDisambiguator
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
name|wr
operator|.
name|write
argument_list|(
literal|'&'
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|spotlightTypesRestriction
operator|!=
literal|null
operator|&&
operator|!
name|spotlightTypesRestriction
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|wr
operator|.
name|write
argument_list|(
literal|"types="
argument_list|)
expr_stmt|;
name|wr
operator|.
name|write
argument_list|(
name|URLEncoder
operator|.
name|encode
argument_list|(
name|spotlightTypesRestriction
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
name|wr
operator|.
name|write
argument_list|(
literal|'&'
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|spotlightSupport
operator|!=
literal|null
operator|&&
operator|!
name|spotlightSupport
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|wr
operator|.
name|write
argument_list|(
literal|"support="
argument_list|)
expr_stmt|;
name|wr
operator|.
name|write
argument_list|(
name|URLEncoder
operator|.
name|encode
argument_list|(
name|spotlightSupport
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
name|wr
operator|.
name|write
argument_list|(
literal|'&'
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|spotlightConfidence
operator|!=
literal|null
operator|&&
operator|!
name|spotlightConfidence
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|wr
operator|.
name|write
argument_list|(
literal|"confidence="
argument_list|)
expr_stmt|;
name|wr
operator|.
name|write
argument_list|(
name|URLEncoder
operator|.
name|encode
argument_list|(
name|spotlightConfidence
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
name|wr
operator|.
name|write
argument_list|(
literal|'&'
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|spotlightSparql
operator|!=
literal|null
operator|&&
operator|!
name|spotlightSparql
operator|.
name|isEmpty
argument_list|()
operator|&&
name|spotlightTypesRestriction
operator|==
literal|null
condition|)
block|{
name|wr
operator|.
name|write
argument_list|(
literal|"sparql="
argument_list|)
expr_stmt|;
name|wr
operator|.
name|write
argument_list|(
name|URLEncoder
operator|.
name|encode
argument_list|(
name|spotlightSparql
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
name|wr
operator|.
name|write
argument_list|(
literal|'&'
argument_list|)
expr_stmt|;
block|}
name|wr
operator|.
name|write
argument_list|(
literal|"text="
argument_list|)
expr_stmt|;
name|wr
operator|.
name|write
argument_list|(
name|URLEncoder
operator|.
name|encode
argument_list|(
name|text
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
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
literal|"The platform does not support encoding "
operator|+
name|UTF8
operator|.
name|name
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
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
literal|"Unable to write 'plain/text' content "
operator|+
literal|"for ContentItem "
operator|+
name|contentItemUri
operator|+
literal|" to "
operator|+
name|spotlightUrl
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|wr
argument_list|)
expr_stmt|;
block|}
name|InputStream
name|is
init|=
literal|null
decl_stmt|;
name|Document
name|xmlDoc
decl_stmt|;
try|try
block|{
comment|// Get Response
name|is
operator|=
name|connection
operator|.
name|getInputStream
argument_list|()
expr_stmt|;
name|xmlDoc
operator|=
name|loadXMLFromInputStream
argument_list|(
name|is
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
name|EngineException
argument_list|(
literal|"Unable to spot Entities with"
operator|+
literal|"Dbpedia Spotlight Annotate RESTful Serice running at "
operator|+
name|spotlightUrl
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|SAXException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"Unable to parse Response from "
operator|+
literal|"Dbpedia Spotlight Annotate RESTful Serice running at "
operator|+
name|spotlightUrl
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|is
argument_list|)
expr_stmt|;
block|}
return|return
name|Annotation
operator|.
name|parseAnnotations
argument_list|(
name|xmlDoc
argument_list|)
return|;
block|}
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
block|}
end_class

end_unit

