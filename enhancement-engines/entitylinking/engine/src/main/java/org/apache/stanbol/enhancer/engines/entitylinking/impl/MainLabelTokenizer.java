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
name|impl
package|;
end_package

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
name|BundleContext
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
name|framework
operator|.
name|ServiceReference
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
name|osgi
operator|.
name|util
operator|.
name|tracker
operator|.
name|ServiceTracker
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|util
operator|.
name|tracker
operator|.
name|ServiceTrackerCustomizer
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
argument_list|)
annotation|@
name|Service
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
comment|//Ensure that his LabelTokenizer is highest priority
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
name|Integer
operator|.
name|MAX_VALUE
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|MainLabelTokenizer
implements|implements
name|LabelTokenizer
block|{
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|DEFAULT_LANG_CONF
init|=
operator|new
name|String
index|[]
block|{
literal|"*"
block|}
decl_stmt|;
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|MainLabelTokenizer
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|ServiceTracker
name|labelTokenizerTracker
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|ServiceReference
argument_list|,
name|LanguageConfiguration
argument_list|>
name|ref2LangConfig
init|=
name|Collections
operator|.
name|synchronizedMap
argument_list|(
operator|new
name|HashMap
argument_list|<
name|ServiceReference
argument_list|,
name|LanguageConfiguration
argument_list|>
argument_list|()
argument_list|)
decl_stmt|;
comment|/**      * Lazily initialized keys based on requested languages.      * Cleared every time when {@link #ref2LangConfig} changes.      */
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
name|langTokenizers
init|=
name|Collections
operator|.
name|synchronizedMap
argument_list|(
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|ServiceReference
argument_list|>
argument_list|>
argument_list|()
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
block|{
specifier|final
name|BundleContext
name|bundleContext
init|=
name|ctx
operator|.
name|getBundleContext
argument_list|()
decl_stmt|;
specifier|final
name|String
name|managerServicePid
init|=
operator|(
name|String
operator|)
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|SERVICE_PID
argument_list|)
decl_stmt|;
name|labelTokenizerTracker
operator|=
operator|new
name|ServiceTracker
argument_list|(
name|bundleContext
argument_list|,
name|LabelTokenizer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
operator|new
name|ServiceTrackerCustomizer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Object
name|addingService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|)
block|{
if|if
condition|(
name|managerServicePid
operator|.
name|equals
argument_list|(
name|reference
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_PID
argument_list|)
argument_list|)
condition|)
block|{
return|return
literal|null
return|;
comment|//do not track this manager!
block|}
name|LanguageConfiguration
name|langConf
init|=
operator|new
name|LanguageConfiguration
argument_list|(
name|SUPPORTED_LANUAGES
argument_list|,
name|DEFAULT_LANG_CONF
argument_list|)
decl_stmt|;
try|try
block|{
name|langConf
operator|.
name|setConfiguration
argument_list|(
name|reference
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ConfigurationException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to track ServiceReference {} becuase of invalid LanguageConfiguration("
operator|+
name|SUPPORTED_LANUAGES
operator|+
literal|"="
operator|+
name|reference
operator|.
name|getProperty
argument_list|(
name|SUPPORTED_LANUAGES
argument_list|)
operator|+
literal|")!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|Object
name|service
init|=
name|bundleContext
operator|.
name|getService
argument_list|(
name|reference
argument_list|)
decl_stmt|;
if|if
condition|(
name|service
operator|!=
literal|null
condition|)
block|{
name|ref2LangConfig
operator|.
name|put
argument_list|(
name|reference
argument_list|,
name|langConf
argument_list|)
expr_stmt|;
name|langTokenizers
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
return|return
name|service
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|modifiedService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|,
name|Object
name|service
parameter_list|)
block|{
if|if
condition|(
name|managerServicePid
operator|.
name|equals
argument_list|(
name|reference
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_PID
argument_list|)
argument_list|)
condition|)
block|{
return|return;
comment|//ignore this service!
block|}
name|LanguageConfiguration
name|langConf
init|=
operator|new
name|LanguageConfiguration
argument_list|(
name|SUPPORTED_LANUAGES
argument_list|,
name|DEFAULT_LANG_CONF
argument_list|)
decl_stmt|;
try|try
block|{
name|langConf
operator|.
name|setConfiguration
argument_list|(
name|reference
argument_list|)
expr_stmt|;
name|ref2LangConfig
operator|.
name|put
argument_list|(
name|reference
argument_list|,
name|langConf
argument_list|)
expr_stmt|;
name|langTokenizers
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ConfigurationException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to track ServiceReference {} becuase of invalid LanguageConfiguration("
operator|+
name|SUPPORTED_LANUAGES
operator|+
literal|"="
operator|+
name|reference
operator|.
name|getProperty
argument_list|(
name|SUPPORTED_LANUAGES
argument_list|)
operator|+
literal|")!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
if|if
condition|(
name|ref2LangConfig
operator|.
name|remove
argument_list|(
name|reference
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|langTokenizers
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|removedService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|,
name|Object
name|service
parameter_list|)
block|{
if|if
condition|(
name|managerServicePid
operator|.
name|equals
argument_list|(
name|reference
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_PID
argument_list|)
argument_list|)
condition|)
block|{
return|return;
comment|//ignore this service
block|}
name|bundleContext
operator|.
name|ungetService
argument_list|(
name|reference
argument_list|)
expr_stmt|;
if|if
condition|(
name|ref2LangConfig
operator|.
name|remove
argument_list|(
name|reference
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|langTokenizers
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|labelTokenizerTracker
operator|.
name|open
argument_list|()
expr_stmt|;
block|}
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
if|if
condition|(
name|labelTokenizerTracker
operator|!=
literal|null
condition|)
block|{
name|labelTokenizerTracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|labelTokenizerTracker
operator|=
literal|null
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the Servcice based on a Service Refernece      * @param ref      * @return      */
specifier|public
name|LabelTokenizer
name|getService
parameter_list|(
name|ServiceReference
name|ref
parameter_list|)
block|{
return|return
operator|(
name|LabelTokenizer
operator|)
name|labelTokenizerTracker
operator|.
name|getService
argument_list|()
return|;
block|}
comment|/**      * Getter for the list of {@link ServiceReference}s for all      * tracked {@link LabelTokenizer} supporting the parsed language.      * Entries in the List are sorted by "service.ranking"      * @param language      * @return      */
specifier|public
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|getTokenizers
parameter_list|(
name|String
name|language
parameter_list|)
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|langTokenizers
init|=
name|this
operator|.
name|langTokenizers
operator|.
name|get
argument_list|(
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|langTokenizers
operator|==
literal|null
condition|)
block|{
name|langTokenizers
operator|=
name|initTokenizers
argument_list|(
name|language
argument_list|)
expr_stmt|;
block|}
return|return
name|langTokenizers
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|private
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|initTokenizers
parameter_list|(
name|String
name|language
parameter_list|)
block|{
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|tokenizers
init|=
operator|new
name|ArrayList
argument_list|<
name|ServiceReference
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|labelTokenizerTracker
operator|.
name|getServiceReferences
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|ServiceReference
name|ref
range|:
name|labelTokenizerTracker
operator|.
name|getServiceReferences
argument_list|()
control|)
block|{
name|LanguageConfiguration
name|langConf
init|=
name|ref2LangConfig
operator|.
name|get
argument_list|(
name|ref
argument_list|)
decl_stmt|;
if|if
condition|(
name|langConf
operator|!=
literal|null
operator|&&
name|langConf
operator|.
name|isLanguage
argument_list|(
name|language
argument_list|)
condition|)
block|{
name|tokenizers
operator|.
name|add
argument_list|(
name|ref
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|tokenizers
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|tokenizers
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|langTokenizers
operator|.
name|put
argument_list|(
name|language
argument_list|,
name|tokenizers
argument_list|)
expr_stmt|;
return|return
name|tokenizers
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.enhancer.engines.keywordextraction.impl.LabelTokenizerManager#tokenize(java.lang.String, java.lang.String)      */
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
for|for
control|(
name|ServiceReference
name|ref
range|:
name|getTokenizers
argument_list|(
name|language
argument_list|)
control|)
block|{
name|LabelTokenizer
name|tokenizer
init|=
operator|(
name|LabelTokenizer
operator|)
name|labelTokenizerTracker
operator|.
name|getService
argument_list|(
name|ref
argument_list|)
decl_stmt|;
if|if
condition|(
name|tokenizer
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|trace
argument_list|(
literal|"> use Tokenizer {} for language {}"
argument_list|,
name|tokenizer
operator|.
name|getClass
argument_list|()
argument_list|,
name|language
argument_list|)
expr_stmt|;
name|String
index|[]
name|tokens
init|=
name|tokenizer
operator|.
name|tokenize
argument_list|(
name|label
argument_list|,
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|tokens
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|log
operator|.
name|isTraceEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|trace
argument_list|(
literal|"   - tokenized {} -> {}"
argument_list|,
name|label
argument_list|,
name|Arrays
operator|.
name|toString
argument_list|(
name|tokens
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|tokens
return|;
block|}
block|}
block|}
name|log
operator|.
name|warn
argument_list|(
literal|"No LabelTokenizer availabel for language {} -> return null"
argument_list|,
name|language
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

