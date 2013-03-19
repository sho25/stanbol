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
name|celi
operator|.
name|sentimentanalysis
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
name|nlp
operator|.
name|utils
operator|.
name|NlpEngineHelper
operator|.
name|getAnalysedText
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
name|nlp
operator|.
name|utils
operator|.
name|NlpEngineHelper
operator|.
name|getLanguage
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
name|nlp
operator|.
name|utils
operator|.
name|NlpEngineHelper
operator|.
name|isLangaugeConfigured
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
name|URL
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
name|List
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
name|javax
operator|.
name|xml
operator|.
name|soap
operator|.
name|SOAPException
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
name|Activate
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
name|Deactivate
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
name|celi
operator|.
name|CeliConstants
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
name|celi
operator|.
name|utils
operator|.
name|Utils
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
name|nlp
operator|.
name|NlpAnnotations
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
name|nlp
operator|.
name|NlpProcessingRole
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
name|nlp
operator|.
name|NlpServiceProperties
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
name|nlp
operator|.
name|model
operator|.
name|AnalysedText
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
name|nlp
operator|.
name|model
operator|.
name|Token
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
name|nlp
operator|.
name|model
operator|.
name|annotation
operator|.
name|Value
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
name|nlp
operator|.
name|utils
operator|.
name|LanguageConfiguration
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
literal|"celiSentiment"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|CeliConstants
operator|.
name|CELI_LICENSE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|CeliConstants
operator|.
name|CELI_TEST_ACCOUNT
argument_list|,
name|boolValue
operator|=
literal|false
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|CeliConstants
operator|.
name|CELI_CONNECTION_TIMEOUT
argument_list|,
name|intValue
operator|=
name|CeliConstants
operator|.
name|DEFAULT_CONECTION_TIMEOUT
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|CeliAnalyzedTextSentimentAnalysisEngine
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
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|SERVICE_PROPERTIES
decl_stmt|;
static|static
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|props
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
name|props
operator|.
name|put
argument_list|(
name|ServiceProperties
operator|.
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|,
name|ServiceProperties
operator|.
name|ORDERING_POST_PROCESSING
argument_list|)
expr_stmt|;
name|props
operator|.
name|put
argument_list|(
name|NlpServiceProperties
operator|.
name|ENHANCEMENT_ENGINE_NLP_ROLE
argument_list|,
name|NlpProcessingRole
operator|.
name|SentimentTagging
argument_list|)
expr_stmt|;
name|SERVICE_PROPERTIES
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|props
argument_list|)
expr_stmt|;
block|}
comment|/**      * This ensures that no connections to external services are made if Stanbol is started in offline mode as the OnlineMode service will only be available if OfflineMode is deactivated.      */
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
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"http://linguagrid.org/LSGrid/ws/sentiment-analysis"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|SERVICE_URL
init|=
literal|"org.apache.stanbol.enhancer.engines.celi.celiSentiment.url"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
block|{
literal|"it"
block|,
literal|"fr"
block|}
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_SUPPORTED_LANGUAGES
init|=
literal|"org.apache.stanbol.enhancer.engines.celi.celiSentiment.languages"
decl_stmt|;
specifier|private
name|LanguageConfiguration
name|languageConfig
init|=
operator|new
name|LanguageConfiguration
argument_list|(
name|PROPERTY_SUPPORTED_LANGUAGES
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"it"
block|,
literal|"fr"
block|}
argument_list|)
decl_stmt|;
specifier|private
name|String
name|licenseKey
decl_stmt|;
specifier|private
name|URL
name|serviceURL
decl_stmt|;
specifier|private
name|SentimentAnalysisServiceClientHttp
name|client
decl_stmt|;
annotation|@
name|Override
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|IOException
throws|,
name|ConfigurationException
block|{
name|super
operator|.
name|activate
argument_list|(
name|ctx
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
name|ctx
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|this
operator|.
name|licenseKey
operator|=
name|Utils
operator|.
name|getLicenseKey
argument_list|(
name|properties
argument_list|,
name|ctx
operator|.
name|getBundleContext
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|url
init|=
operator|(
name|String
operator|)
name|properties
operator|.
name|get
argument_list|(
name|SERVICE_URL
argument_list|)
decl_stmt|;
if|if
condition|(
name|url
operator|==
literal|null
operator|||
name|url
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|SERVICE_URL
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"%s : please configure the URL of the CELI Web Service (e.g. by"
operator|+
literal|"using the 'Configuration' tab of the Apache Felix Web Console)."
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
name|this
operator|.
name|serviceURL
operator|=
operator|new
name|URL
argument_list|(
name|url
argument_list|)
expr_stmt|;
comment|//parse the parsed language configuration
name|languageConfig
operator|.
name|setConfiguration
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|int
name|connectionTimeout
init|=
name|Utils
operator|.
name|getConnectionTimeout
argument_list|(
name|properties
argument_list|,
name|ctx
operator|.
name|getBundleContext
argument_list|()
argument_list|)
decl_stmt|;
name|this
operator|.
name|client
operator|=
operator|new
name|SentimentAnalysisServiceClientHttp
argument_list|(
name|this
operator|.
name|serviceURL
argument_list|,
name|this
operator|.
name|licenseKey
argument_list|,
name|connectionTimeout
argument_list|)
expr_stmt|;
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
name|String
name|language
init|=
name|getLanguage
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|language
operator|==
literal|null
condition|)
block|{
return|return
name|CANNOT_ENHANCE
return|;
block|}
if|if
condition|(
operator|!
name|isLangaugeConfigured
argument_list|(
name|this
argument_list|,
name|languageConfig
argument_list|,
name|language
argument_list|,
literal|false
argument_list|)
condition|)
block|{
return|return
name|CANNOT_ENHANCE
return|;
block|}
if|if
condition|(
name|getAnalysedText
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
literal|false
argument_list|)
operator|==
literal|null
condition|)
block|{
return|return
name|CANNOT_ENHANCE
return|;
block|}
comment|// default enhancement is synchronous enhancement
return|return
name|ENHANCE_ASYNC
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
name|AnalysedText
name|at
init|=
name|getAnalysedText
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|String
name|language
init|=
name|getLanguage
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|isLangaugeConfigured
argument_list|(
name|this
argument_list|,
name|languageConfig
argument_list|,
name|language
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|SentimentExpression
argument_list|>
name|seList
decl_stmt|;
try|try
block|{
name|seList
operator|=
name|this
operator|.
name|client
operator|.
name|extractSentimentExpressions
argument_list|(
name|at
operator|.
name|getSpan
argument_list|()
argument_list|,
name|language
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
literal|"Error while calling the CELI Sentiment Analysis service (configured URL: "
operator|+
name|serviceURL
operator|+
literal|")!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|SOAPException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"Error wile encoding/decoding the request/response to the CELI Sentiment Analysis service!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
for|for
control|(
name|SentimentExpression
name|se
range|:
name|seList
control|)
block|{
comment|//Add the Sentiment Expression as Token to the Text. NOTE that if a Token with the same start/end positions already exist this
comment|//Method returns the existing instance
name|Token
name|token
init|=
name|at
operator|.
name|addToken
argument_list|(
name|se
operator|.
name|getStartSnippet
argument_list|()
argument_list|,
name|se
operator|.
name|getEndSnippet
argument_list|()
argument_list|)
decl_stmt|;
name|token
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|SENTIMENT_ANNOTATION
argument_list|,
operator|new
name|Value
argument_list|<
name|Double
argument_list|>
argument_list|(
name|se
operator|.
name|getSentimentPolarityAsDoubleValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ce
parameter_list|)
block|{
name|languageConfig
operator|.
name|setDefault
argument_list|()
expr_stmt|;
name|super
operator|.
name|deactivate
argument_list|(
name|ce
argument_list|)
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
name|SERVICE_PROPERTIES
return|;
block|}
block|}
end_class

end_unit

