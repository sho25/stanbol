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
name|disambiguate
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
name|XMLParser
operator|.
name|loadXMLFromInputStream
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
name|ENHANCER_ENTITY_TYPE
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
name|ENHANCER_SELECTED_TEXT
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
name|ENHANCER_START
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
name|Hashtable
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
name|TechnicalClasses
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
comment|/**  * {@link DBPSpotlightDisambiguateEnhancementEngine} provides functionality to  * enhance document with their language.  *   * @author Iavor Jelev, Babelmonkeys (GzEvD)  */
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
literal|"%stanbol.DBPSpotlightDisambiguateEnhancementEngine.name"
argument_list|,
name|description
operator|=
literal|"%stanbol.DBPSpotlightDisambiguateEnhancementEngine.description"
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
literal|"dbpspotlightdisambiguate"
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
name|PARAM_DISAMBIGUATOR
argument_list|,
name|value
operator|=
literal|"Document"
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
name|DBPSpotlightDisambiguateEnhancementEngine
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
comment|/** 	 * The default value for the Execution of this Engine. Currently set to 	 * {@link ServiceProperties#ORDERING_PRE_PROCESSING} 	 */
specifier|public
specifier|static
specifier|final
name|Integer
name|defaultOrder
init|=
name|ORDERING_CONTENT_EXTRACTION
operator|-
literal|31
decl_stmt|;
comment|/** This contains the logger. */
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
name|DBPSpotlightDisambiguateEnhancementEngine
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/** holds the url of the Spotlight REST endpoint */
specifier|private
name|URL
name|spotlightUrl
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
comment|/** 	 * holds the existing TextAnnotations, which are used as input for DBpedia 	 * Spotlight, and later for linking of the results 	 */
specifier|private
name|Hashtable
argument_list|<
name|String
argument_list|,
name|UriRef
argument_list|>
name|textAnnotationsMap
decl_stmt|;
comment|/** 	 * Default constructor used by OSGI. It is expected that 	 * {@link #activate(ComponentContext)} is called before 	 * using the instance. 	 */
specifier|public
name|DBPSpotlightDisambiguateEnhancementEngine
parameter_list|()
block|{}
comment|/** 	 * Constructor intended to be used for unit tests 	 * @param serviceURL 	 */
specifier|protected
name|DBPSpotlightDisambiguateEnhancementEngine
parameter_list|(
name|URL
name|serviceURL
parameter_list|)
block|{
name|this
operator|.
name|spotlightUrl
operator|=
name|serviceURL
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
literal|"-1"
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
literal|"-1"
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
comment|// Retrieve the existing text annotations (requires read lock)
name|MGraph
name|graph
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|String
name|xmlTextAnnotations
init|=
name|this
operator|.
name|getSpottedXml
argument_list|(
name|text
argument_list|,
name|graph
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
name|xmlTextAnnotations
argument_list|,
name|ci
operator|.
name|getUri
argument_list|()
argument_list|)
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
name|language
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
literal|"DBpedia Enhancements:\n{}"
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
comment|/** 	 * The method adds the returned DBpedia Spotlight annotations to the content 	 * item's metadata. For each DBpedia resource an EntityAnnotation is created 	 * and linked to the according TextAnnotation. 	 *  	 * @param occs 	 *            a Collection of entity information 	 * @param ci 	 *            the content item 	 */
specifier|public
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
name|Language
name|language
parameter_list|)
block|{
name|HashMap
argument_list|<
name|Resource
argument_list|,
name|UriRef
argument_list|>
name|entityAnnotationMap
init|=
operator|new
name|HashMap
argument_list|<
name|Resource
argument_list|,
name|UriRef
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Annotation
name|occ
range|:
name|occs
control|)
block|{
if|if
condition|(
name|textAnnotationsMap
operator|.
name|get
argument_list|(
name|occ
operator|.
name|surfaceForm
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|UriRef
name|textAnnotation
init|=
name|textAnnotationsMap
operator|.
name|get
argument_list|(
name|occ
operator|.
name|surfaceForm
argument_list|)
decl_stmt|;
name|MGraph
name|model
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|UriRef
name|entityAnnotation
init|=
name|EnhancementEngineHelper
operator|.
name|createEntityEnhancement
argument_list|(
name|ci
argument_list|,
name|this
argument_list|)
decl_stmt|;
name|entityAnnotationMap
operator|.
name|put
argument_list|(
name|occ
operator|.
name|uri
argument_list|,
name|entityAnnotation
argument_list|)
expr_stmt|;
name|Literal
name|label
init|=
operator|new
name|PlainLiteralImpl
argument_list|(
name|occ
operator|.
name|surfaceForm
operator|.
name|name
argument_list|,
name|language
argument_list|)
decl_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|DC_RELATION
argument_list|,
name|textAnnotation
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|ENHANCER_ENTITY_LABEL
argument_list|,
name|label
argument_list|)
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|t
init|=
name|occ
operator|.
name|getTypeNames
argument_list|()
decl_stmt|;
if|if
condition|(
name|t
operator|!=
literal|null
condition|)
block|{
name|Iterator
argument_list|<
name|String
argument_list|>
name|it
init|=
name|t
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
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|ENHANCER_ENTITY_TYPE
argument_list|,
operator|new
name|UriRef
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|ENHANCER_ENTITY_REFERENCE
argument_list|,
name|occ
operator|.
name|uri
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/** 	 * Sends a POST request to the DBpediaSpotlight url. 	 *  	 * @param text 	 *            a<code>String</code> with the text to be analyzed 	 * @param xmlTextAnnotations 	 * @param textAnnotations 	 * @param contentItemUri the URI of the {@link ContentItem} (only 	 * used for logging in case of an error) 	 * @return a<code>String</code> with the server response 	 * @throws EngineException 	 *             if the request cannot be sent 	 */
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
name|String
name|xmlTextAnnotations
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
name|wr
operator|.
name|write
argument_list|(
literal|"spotter=SpotXmlParser&"
argument_list|)
expr_stmt|;
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
name|xmlTextAnnotations
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
specifier|private
name|String
name|getSpottedXml
parameter_list|(
name|String
name|text
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|StringBuilder
name|xml
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|textAnnotationsMap
operator|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|UriRef
argument_list|>
argument_list|()
expr_stmt|;
name|xml
operator|.
name|append
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"<annotation text=\"%s\">"
argument_list|,
name|text
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
for|for
control|(
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|graph
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|RDF_TYPE
argument_list|,
name|TechnicalClasses
operator|.
name|ENHANCER_TEXTANNOTATION
argument_list|)
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
comment|// Triple tAnnotation = it.next();
name|UriRef
name|uri
init|=
operator|(
name|UriRef
operator|)
name|it
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|String
name|surfaceForm
init|=
name|EnhancementEngineHelper
operator|.
name|getString
argument_list|(
name|graph
argument_list|,
name|uri
argument_list|,
name|ENHANCER_SELECTED_TEXT
argument_list|)
decl_stmt|;
if|if
condition|(
name|surfaceForm
operator|!=
literal|null
condition|)
block|{
name|String
name|offset
init|=
name|EnhancementEngineHelper
operator|.
name|getString
argument_list|(
name|graph
argument_list|,
name|uri
argument_list|,
name|ENHANCER_START
argument_list|)
decl_stmt|;
name|textAnnotationsMap
operator|.
name|put
argument_list|(
name|surfaceForm
argument_list|,
name|uri
argument_list|)
expr_stmt|;
name|xml
operator|.
name|append
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"<surfaceForm name=\"%s\" offset=\"%s\"/>"
argument_list|,
name|surfaceForm
argument_list|,
name|offset
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|xml
operator|.
name|append
argument_list|(
literal|"</annotation>"
argument_list|)
operator|.
name|toString
argument_list|()
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

