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
name|engines
operator|.
name|paoding
operator|.
name|token
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
name|initAnalysedText
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
name|security
operator|.
name|AccessController
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|PrivilegedActionException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|PrivilegedExceptionAction
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
name|net
operator|.
name|paoding
operator|.
name|analysis
operator|.
name|analyzer
operator|.
name|PaodingAnalyzer
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
name|input
operator|.
name|CharSequenceReader
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
name|ConfigurationPolicy
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
name|lucene
operator|.
name|analysis
operator|.
name|TokenStream
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|tokenattributes
operator|.
name|OffsetAttribute
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|util
operator|.
name|Version
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
name|AnalysedTextFactory
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
name|NlpEngineHelper
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
name|framework
operator|.
name|Constants
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
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|OPTIONAL
argument_list|)
comment|//create a default instance with the default configuration
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
literal|"paoding-token"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|,
name|intValue
operator|=
literal|0
argument_list|)
comment|//give the default instance a ranking< 0
block|}
argument_list|)
specifier|public
class|class
name|PaodingTokenizerEngine
extends|extends
name|AbstractEnhancementEngine
argument_list|<
name|RuntimeException
argument_list|,
name|RuntimeException
argument_list|>
implements|implements
name|ServiceProperties
block|{
specifier|private
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|PaodingTokenizerEngine
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/*      * Analyzer configuration constants      */
specifier|private
specifier|static
specifier|final
name|String
name|LUCENE_VERSION
init|=
name|Version
operator|.
name|LUCENE_36
operator|.
name|toString
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|CHAR_FILTER_FACTORY_CONFIG
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|TOKENIZER_FACTORY_CONFIG
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
static|static
block|{
name|CHAR_FILTER_FACTORY_CONFIG
operator|.
name|put
argument_list|(
literal|"luceneMatchVersion"
argument_list|,
name|LUCENE_VERSION
argument_list|)
expr_stmt|;
name|CHAR_FILTER_FACTORY_CONFIG
operator|.
name|put
argument_list|(
literal|"mapping"
argument_list|,
literal|"gosen-mapping-japanese.txt"
argument_list|)
expr_stmt|;
name|TOKENIZER_FACTORY_CONFIG
operator|.
name|put
argument_list|(
literal|"luceneMatchVersion"
argument_list|,
name|LUCENE_VERSION
argument_list|)
expr_stmt|;
block|}
comment|/**      * Service Properties of this Engine      */
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
name|ORDERING_NLP_TOKENIZING
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
name|Tokenizing
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
annotation|@
name|Reference
specifier|protected
name|AnalysedTextFactory
name|analysedTextFactory
decl_stmt|;
annotation|@
name|Override
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|super
operator|.
name|activate
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
comment|//init the Solr ResourceLoader used for initialising the components
block|}
annotation|@
name|Override
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|super
operator|.
name|deactivate
argument_list|(
name|ctx
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
comment|// check if content is present
name|Map
operator|.
name|Entry
argument_list|<
name|UriRef
argument_list|,
name|Blob
argument_list|>
name|entry
init|=
name|NlpEngineHelper
operator|.
name|getPlainText
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
name|entry
operator|==
literal|null
operator|||
name|entry
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
name|CANNOT_ENHANCE
return|;
block|}
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
literal|"zh"
operator|.
name|equals
argument_list|(
name|language
argument_list|)
operator|||
operator|(
name|language
operator|!=
literal|null
operator|&&
name|language
operator|.
name|startsWith
argument_list|(
literal|"zh-"
argument_list|)
operator|)
condition|)
block|{
name|log
operator|.
name|trace
argument_list|(
literal|"> can enhance ContentItem {} with language {}"
argument_list|,
name|ci
argument_list|,
name|language
argument_list|)
expr_stmt|;
return|return
name|ENHANCE_ASYNC
return|;
block|}
else|else
block|{
return|return
name|CANNOT_ENHANCE
return|;
block|}
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
specifier|final
name|AnalysedText
name|at
init|=
name|initAnalysedText
argument_list|(
name|this
argument_list|,
name|analysedTextFactory
argument_list|,
name|ci
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
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
operator|(
literal|"zh"
operator|.
name|equals
argument_list|(
name|language
argument_list|)
operator|||
operator|(
name|language
operator|!=
literal|null
operator|&&
name|language
operator|.
name|startsWith
argument_list|(
literal|"zh-"
argument_list|)
operator|)
operator|)
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"The detected language is NOT 'zh'! "
operator|+
literal|"As this is also checked within the #canEnhance(..) method this "
operator|+
literal|"indicates an Bug in the used EnhancementJobManager implementation. "
operator|+
literal|"Please report this on the dev@apache.stanbol.org or create an "
operator|+
literal|"JIRA issue about this."
argument_list|)
throw|;
block|}
name|PaodingAnalyzer
name|pa
decl_stmt|;
try|try
block|{
name|pa
operator|=
name|AccessController
operator|.
name|doPrivileged
argument_list|(
operator|new
name|PrivilegedExceptionAction
argument_list|<
name|PaodingAnalyzer
argument_list|>
argument_list|()
block|{
specifier|public
name|PaodingAnalyzer
name|run
parameter_list|()
throws|throws
name|Exception
block|{
return|return
operator|new
name|PaodingAnalyzer
argument_list|()
return|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PrivilegedActionException
name|pae
parameter_list|)
block|{
name|Exception
name|e
init|=
name|pae
operator|.
name|getException
argument_list|()
decl_stmt|;
name|log
operator|.
name|error
argument_list|(
literal|"Unable to initialise PoadingAnalyzer"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"Unable to initialise PoadingAnalyzer"
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|TokenStream
name|ts
init|=
name|pa
operator|.
name|tokenStream
argument_list|(
literal|"dummy"
argument_list|,
operator|new
name|CharSequenceReader
argument_list|(
name|at
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|lastEnd
init|=
literal|0
decl_stmt|;
try|try
block|{
name|ts
operator|.
name|reset
argument_list|()
expr_stmt|;
while|while
condition|(
name|ts
operator|.
name|incrementToken
argument_list|()
condition|)
block|{
name|OffsetAttribute
name|offset
init|=
name|ts
operator|.
name|addAttribute
argument_list|(
name|OffsetAttribute
operator|.
name|class
argument_list|)
decl_stmt|;
comment|//when tokenizing labels we need to preserve all chars
if|if
condition|(
name|offset
operator|.
name|startOffset
argument_list|()
operator|>
name|lastEnd
condition|)
block|{
comment|//add token for stopword
name|at
operator|.
name|addToken
argument_list|(
name|lastEnd
argument_list|,
name|offset
operator|.
name|startOffset
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|at
operator|.
name|addToken
argument_list|(
name|offset
operator|.
name|startOffset
argument_list|()
argument_list|,
name|offset
operator|.
name|endOffset
argument_list|()
argument_list|)
expr_stmt|;
name|lastEnd
operator|=
name|offset
operator|.
name|endOffset
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"IOException while reading the parsed Text"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"IOException while reading the parsed Text"
argument_list|,
name|e
argument_list|)
throw|;
block|}
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

