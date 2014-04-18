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
name|entitylinking
operator|.
name|labeltokenizer
operator|.
name|lucene
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|CharArrayReader
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
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Constructor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationTargetException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
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
name|ReferenceCardinality
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
name|CharFilter
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
name|analysis
operator|.
name|util
operator|.
name|AbstractAnalysisFactory
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
name|util
operator|.
name|CharFilterFactory
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
name|util
operator|.
name|ResourceLoader
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
name|util
operator|.
name|ResourceLoaderAware
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
name|util
operator|.
name|TokenFilterFactory
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
name|util
operator|.
name|TokenizerFactory
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
name|solr
operator|.
name|common
operator|.
name|SolrException
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
name|solr
operator|.
name|utils
operator|.
name|StanbolResourceLoader
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
name|entitylinking
operator|.
name|LabelTokenizer
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
name|Service
annotation|@
name|Component
argument_list|(
name|configurationFactory
operator|=
literal|true
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|REQUIRE
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
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
name|LuceneLabelTokenizer
operator|.
name|PROPERTY_CHAR_FILTER_FACTORY
argument_list|,
name|value
operator|=
name|LuceneLabelTokenizer
operator|.
name|DEFAULT_CLASS_NAME_CONFIG
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|LuceneLabelTokenizer
operator|.
name|PROPERTY_TOKENIZER_FACTORY
argument_list|,
name|value
operator|=
name|LuceneLabelTokenizer
operator|.
name|DEFAULT_CLASS_NAME_CONFIG
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|LuceneLabelTokenizer
operator|.
name|PROPERTY_TOKEN_FILTER_FACTORY
argument_list|,
name|cardinality
operator|=
name|Integer
operator|.
name|MAX_VALUE
argument_list|,
name|value
operator|=
name|LuceneLabelTokenizer
operator|.
name|DEFAULT_CLASS_NAME_CONFIG
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|LabelTokenizer
operator|.
name|SUPPORTED_LANUAGES
argument_list|,
name|value
operator|=
literal|"{lang1},{lang2},!{lang3},{*}"
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
block|}
argument_list|)
specifier|public
class|class
name|LuceneLabelTokenizer
implements|implements
name|LabelTokenizer
block|{
specifier|private
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|LuceneLabelTokenizer
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|EMPTY
init|=
operator|new
name|String
index|[]
block|{}
decl_stmt|;
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_UNARY
argument_list|)
specifier|private
name|ResourceLoader
name|parentResourceLoader
decl_stmt|;
specifier|protected
name|ResourceLoader
name|resourceLoader
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_CHAR_FILTER_FACTORY
init|=
literal|"enhancer.engine.linking.labeltokenizer.lucene.charFilterFactory"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_TOKENIZER_FACTORY
init|=
literal|"enhancer.engine.linking.labeltokenizer.lucene.tokenizerFactory"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_TOKEN_FILTER_FACTORY
init|=
literal|"enhancer.engine.linking.labeltokenizer.lucene.tokenFilterFactory"
decl_stmt|;
specifier|static
specifier|final
name|String
name|DEFAULT_CLASS_NAME_CONFIG
init|=
literal|"{full-qualified-class-name}"
decl_stmt|;
specifier|private
name|CharFilterFactory
name|charFilterFactory
decl_stmt|;
specifier|private
name|TokenizerFactory
name|tokenizerFactory
decl_stmt|;
specifier|private
name|List
argument_list|<
name|TokenFilterFactory
argument_list|>
name|filterFactories
init|=
operator|new
name|ArrayList
argument_list|<
name|TokenFilterFactory
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|LanguageConfiguration
name|langConf
init|=
operator|new
name|LanguageConfiguration
argument_list|(
name|SUPPORTED_LANUAGES
argument_list|,
operator|new
name|String
index|[]
block|{}
argument_list|)
decl_stmt|;
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
name|ConfigurationException
block|{
comment|//init the Solr ResourceLoader used for initialising the components
name|resourceLoader
operator|=
operator|new
name|StanbolResourceLoader
argument_list|(
name|parentResourceLoader
argument_list|)
expr_stmt|;
comment|//init the Solr CharFilterFactory (optional)
name|Object
name|value
init|=
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|PROPERTY_CHAR_FILTER_FACTORY
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|DEFAULT_CLASS_NAME_CONFIG
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
block|{
name|Entry
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
name|charFilterConfig
init|=
name|parseConfigLine
argument_list|(
name|PROPERTY_CHAR_FILTER_FACTORY
argument_list|,
name|value
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|charFilterFactory
operator|=
name|initAnalyzer
argument_list|(
name|PROPERTY_CHAR_FILTER_FACTORY
argument_list|,
name|charFilterConfig
operator|.
name|getKey
argument_list|()
argument_list|,
name|CharFilterFactory
operator|.
name|class
argument_list|,
name|charFilterConfig
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|charFilterFactory
operator|=
literal|null
expr_stmt|;
block|}
comment|//now initialise the TokenizerFactory (required)
name|value
operator|=
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|PROPERTY_TOKENIZER_FACTORY
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
name|DEFAULT_CLASS_NAME_CONFIG
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_TOKENIZER_FACTORY
argument_list|,
literal|"The class name of the Lucene Tokemizer MUST BE configured"
argument_list|)
throw|;
block|}
name|Entry
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
name|tokenizerConfig
init|=
name|parseConfigLine
argument_list|(
name|PROPERTY_CHAR_FILTER_FACTORY
argument_list|,
name|value
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|tokenizerFactory
operator|=
name|initAnalyzer
argument_list|(
name|PROPERTY_TOKENIZER_FACTORY
argument_list|,
name|tokenizerConfig
operator|.
name|getKey
argument_list|()
argument_list|,
name|TokenizerFactory
operator|.
name|class
argument_list|,
name|tokenizerConfig
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
comment|//initialise the list of Token Filters
name|Collection
argument_list|<
name|String
argument_list|>
name|values
decl_stmt|;
name|value
operator|=
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|PROPERTY_TOKEN_FILTER_FACTORY
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|values
operator|=
name|Collections
operator|.
name|emptyList
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|Collection
argument_list|<
name|?
argument_list|>
condition|)
block|{
name|values
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
operator|(
operator|(
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
name|value
operator|)
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Object
name|v
operator|:
operator|(
name|Collection
argument_list|<
name|Object
argument_list|>
operator|)
name|value
control|)
block|{
name|values
operator|.
name|add
argument_list|(
name|v
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|String
index|[]
condition|)
block|{
name|values
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|(
name|String
index|[]
operator|)
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|String
condition|)
block|{
name|values
operator|=
name|Collections
operator|.
name|singleton
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_TOKEN_FILTER_FACTORY
argument_list|,
literal|"The type '"
operator|+
name|value
operator|.
name|getClass
argument_list|()
operator|+
literal|"' of the parsed value is not supported (supported are "
operator|+
literal|"Collections, String[] and String values)!"
argument_list|)
throw|;
block|}
for|for
control|(
name|String
name|filterConfigLine
range|:
name|values
control|)
block|{
if|if
condition|(
name|filterConfigLine
operator|==
literal|null
operator|||
name|filterConfigLine
operator|.
name|isEmpty
argument_list|()
operator|||
name|DEFAULT_CLASS_NAME_CONFIG
operator|.
name|equals
argument_list|(
name|filterConfigLine
argument_list|)
condition|)
block|{
continue|continue;
comment|//ignore null, empty and the default value
block|}
name|Entry
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
name|filterConfig
init|=
name|parseConfigLine
argument_list|(
name|PROPERTY_CHAR_FILTER_FACTORY
argument_list|,
name|filterConfigLine
argument_list|)
decl_stmt|;
name|TokenFilterFactory
name|tff
init|=
name|initAnalyzer
argument_list|(
name|PROPERTY_TOKEN_FILTER_FACTORY
argument_list|,
name|filterConfig
operator|.
name|getKey
argument_list|()
argument_list|,
name|TokenFilterFactory
operator|.
name|class
argument_list|,
name|filterConfig
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
name|filterFactories
operator|.
name|add
argument_list|(
name|tff
argument_list|)
expr_stmt|;
block|}
comment|//init the language configuration
name|value
operator|=
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|LabelTokenizer
operator|.
name|SUPPORTED_LANUAGES
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|LabelTokenizer
operator|.
name|SUPPORTED_LANUAGES
argument_list|,
literal|"The language "
operator|+
literal|"configuration MUST BE present!"
argument_list|)
throw|;
block|}
name|langConf
operator|.
name|setConfiguration
argument_list|(
name|ctx
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
end_class

begin_function
specifier|private
specifier|static
name|void
name|addLuceneMatchVersionIfNotPresent
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|config
parameter_list|)
block|{
if|if
condition|(
operator|!
name|config
operator|.
name|containsKey
argument_list|(
literal|"luceneMatchVersion"
argument_list|)
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
literal|"luceneMatchVersion"
argument_list|,
name|Version
operator|.
name|LUCENE_44
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_function

begin_function
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|resourceLoader
operator|=
literal|null
expr_stmt|;
name|charFilterFactory
operator|=
literal|null
expr_stmt|;
name|tokenizerFactory
operator|=
literal|null
expr_stmt|;
name|filterFactories
operator|.
name|clear
argument_list|()
expr_stmt|;
name|langConf
operator|.
name|setDefault
argument_list|()
expr_stmt|;
block|}
end_function

begin_function
annotation|@
name|Override
specifier|public
name|String
index|[]
name|tokenize
parameter_list|(
name|String
name|label
parameter_list|,
name|String
name|language
parameter_list|)
block|{
if|if
condition|(
name|label
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed label MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
operator|(
name|language
operator|==
literal|null
operator|&&
name|langConf
operator|.
name|useWildcard
argument_list|()
operator|)
operator|||
name|langConf
operator|.
name|isLanguage
argument_list|(
name|language
argument_list|)
condition|)
block|{
if|if
condition|(
name|label
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|EMPTY
return|;
block|}
name|Reader
name|reader
init|=
operator|new
name|StringReader
argument_list|(
name|label
argument_list|)
decl_stmt|;
name|TokenStream
name|tokenizer
decl_stmt|;
if|if
condition|(
name|charFilterFactory
operator|!=
literal|null
condition|)
block|{
name|tokenizer
operator|=
name|tokenizerFactory
operator|.
name|create
argument_list|(
name|charFilterFactory
operator|.
name|create
argument_list|(
name|reader
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|tokenizer
operator|=
name|tokenizerFactory
operator|.
name|create
argument_list|(
name|reader
argument_list|)
expr_stmt|;
block|}
comment|//build the analysing chain
for|for
control|(
name|TokenFilterFactory
name|filterFactory
range|:
name|filterFactories
control|)
block|{
name|tokenizer
operator|=
name|filterFactory
operator|.
name|create
argument_list|(
name|tokenizer
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|tokens
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
literal|8
argument_list|)
decl_stmt|;
try|try
block|{
name|tokenizer
operator|.
name|reset
argument_list|()
expr_stmt|;
while|while
condition|(
name|tokenizer
operator|.
name|incrementToken
argument_list|()
condition|)
block|{
name|OffsetAttribute
name|offset
init|=
name|tokenizer
operator|.
name|addAttribute
argument_list|(
name|OffsetAttribute
operator|.
name|class
argument_list|)
decl_stmt|;
name|tokens
operator|.
name|add
argument_list|(
name|label
operator|.
name|substring
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
argument_list|)
expr_stmt|;
block|}
name|tokenizer
operator|.
name|end
argument_list|()
expr_stmt|;
name|tokenizer
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"IOException while reading from a StringReader :("
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
return|return
name|tokens
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|tokens
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
else|else
block|{
name|log
operator|.
name|trace
argument_list|(
literal|"Language {} not configured to be supported"
argument_list|,
name|language
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
end_function

begin_comment
comment|/**      * Parses the configured component including parameters formatted like      *<code><pre>      *     {component};{param}={value};{param1}={value1};      *</pre></code>      * This can be used to parse the same configuration as within the XML schema      *      * @param property      * @param line      * @return      * @throws ConfigurationException      */
end_comment

begin_function
specifier|private
name|Entry
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
name|parseConfigLine
parameter_list|(
name|String
name|property
parameter_list|,
name|String
name|line
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|line
operator|=
name|line
operator|.
name|trim
argument_list|()
expr_stmt|;
name|int
name|sepIndex
init|=
name|line
operator|.
name|indexOf
argument_list|(
literal|';'
argument_list|)
decl_stmt|;
name|String
name|component
init|=
name|sepIndex
operator|<
literal|0
condition|?
name|line
else|:
name|line
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|sepIndex
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|component
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"The component name MUST NOT be NULL "
operator|+
literal|"(illegal formatted line: '"
operator|+
name|line
operator|+
literal|"')!"
argument_list|)
throw|;
block|}
return|return
name|Collections
operator|.
name|singletonMap
argument_list|(
name|component
argument_list|,
name|sepIndex
operator|>=
literal|0
operator|&&
name|sepIndex
operator|<
name|line
operator|.
name|length
argument_list|()
operator|-
literal|2
condition|?
name|parseParameters
argument_list|(
name|property
argument_list|,
name|line
operator|.
name|substring
argument_list|(
name|sepIndex
operator|+
literal|1
argument_list|,
name|line
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
else|:
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
argument_list|)
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
return|;
block|}
end_function

begin_comment
comment|/**      * Parses optional parameters<code>{key}[={value}];{key2}[={value2}]</code>. Using      * the same key multiple times will override the previouse value      * @param property the proeprty (used for throwing {@link ConfigurationException})      * @param paramString the parameter string      * @return      * @throws ConfigurationException      */
end_comment

begin_function
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parseParameters
parameter_list|(
name|String
name|property
parameter_list|,
name|String
name|paramString
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|params
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
for|for
control|(
name|String
name|param
range|:
name|paramString
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
control|)
block|{
name|param
operator|=
name|param
operator|.
name|trim
argument_list|()
expr_stmt|;
name|int
name|equalsPos
init|=
name|param
operator|.
name|indexOf
argument_list|(
literal|'='
argument_list|)
decl_stmt|;
if|if
condition|(
name|equalsPos
operator|==
literal|0
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"Parameter '"
operator|+
name|param
operator|+
literal|"' has empty key!"
argument_list|)
throw|;
block|}
name|String
name|key
init|=
name|equalsPos
operator|>
literal|0
condition|?
name|param
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|equalsPos
argument_list|)
operator|.
name|trim
argument_list|()
else|:
name|param
decl_stmt|;
name|String
name|value
decl_stmt|;
if|if
condition|(
name|equalsPos
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|equalsPos
operator|<
name|param
operator|.
name|length
argument_list|()
operator|-
literal|2
condition|)
block|{
name|value
operator|=
name|param
operator|.
name|substring
argument_list|(
name|equalsPos
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|value
operator|=
literal|""
expr_stmt|;
block|}
block|}
else|else
block|{
name|value
operator|=
literal|null
expr_stmt|;
block|}
name|params
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
return|return
name|params
operator|.
name|isEmpty
argument_list|()
condition|?
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
else|:
name|params
return|;
block|}
end_function

begin_function
specifier|private
parameter_list|<
name|T
parameter_list|>
name|T
name|initAnalyzer
parameter_list|(
name|String
name|property
parameter_list|,
name|String
name|analyzerName
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|config
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Class
argument_list|<
name|?
extends|extends
name|T
argument_list|>
name|analyzerClass
decl_stmt|;
try|try
block|{
name|analyzerClass
operator|=
name|resourceLoader
operator|.
name|findClass
argument_list|(
name|analyzerName
argument_list|,
name|type
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SolrException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_CHAR_FILTER_FACTORY
argument_list|,
literal|"Unable find "
operator|+
name|type
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|" '"
operator|+
name|analyzerName
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|Constructor
argument_list|<
name|?
extends|extends
name|T
argument_list|>
name|constructor
decl_stmt|;
try|try
block|{
name|constructor
operator|=
name|analyzerClass
operator|.
name|getConstructor
argument_list|(
name|Map
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|e1
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_CHAR_FILTER_FACTORY
argument_list|,
literal|"Unable find "
operator|+
name|type
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"constructor with parameter Map<String,String> "
operator|+
literal|"for class "
operator|+
name|analyzerClass
operator|+
literal|" (analyzer: '"
operator|+
name|analyzerName
operator|+
literal|"') !"
argument_list|)
throw|;
block|}
name|addLuceneMatchVersionIfNotPresent
argument_list|(
name|config
argument_list|)
expr_stmt|;
name|T
name|analyzer
decl_stmt|;
try|try
block|{
name|analyzer
operator|=
name|constructor
operator|.
name|newInstance
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"Unable to instantiate "
operator|+
name|type
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|' '
operator|+
name|analyzerClass
operator|+
literal|" (analyzer: "
operator|+
name|analyzerName
operator|+
literal|"') !"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|InstantiationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"Unable to instantiate "
operator|+
name|type
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|' '
operator|+
name|analyzerClass
operator|+
literal|" (analyzer: "
operator|+
name|analyzerName
operator|+
literal|"') !"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IllegalAccessException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"Unable to instantiate "
operator|+
name|type
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|' '
operator|+
name|analyzerClass
operator|+
literal|" (analyzer: "
operator|+
name|analyzerName
operator|+
literal|"') !"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|InvocationTargetException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"Unable to instantiate "
operator|+
name|type
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|' '
operator|+
name|analyzerClass
operator|+
literal|" (analyzer: "
operator|+
name|analyzerName
operator|+
literal|"') !"
argument_list|,
name|e
argument_list|)
throw|;
block|}
if|if
condition|(
name|analyzer
operator|instanceof
name|ResourceLoaderAware
condition|)
block|{
try|try
block|{
operator|(
operator|(
name|ResourceLoaderAware
operator|)
name|analyzer
operator|)
operator|.
name|inform
argument_list|(
name|resourceLoader
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
name|ConfigurationException
argument_list|(
name|PROPERTY_CHAR_FILTER_FACTORY
argument_list|,
literal|"Could not load configuration"
argument_list|)
throw|;
block|}
block|}
return|return
name|analyzer
return|;
block|}
end_function

unit|}
end_unit

