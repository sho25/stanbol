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
name|opennlp
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
name|ReferencePolicy
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
name|opennlp
operator|.
name|OpenNLP
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

begin_comment
comment|/**  * Apache Stanbol Enhancer Named Entity Recognition enhancement engine based on opennlp's Maximum Entropy  * models.  */
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
name|inherit
operator|=
literal|true
argument_list|,
name|configurationFactory
operator|=
literal|true
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|OPTIONAL
argument_list|,
name|specVersion
operator|=
literal|"1.1"
argument_list|,
name|label
operator|=
literal|"%stanbol.NamedEntityExtractionEnhancementEngine.name"
argument_list|,
name|description
operator|=
literal|"%stanbol.NamedEntityExtractionEnhancementEngine.description"
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
literal|"ner"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|NamedEntityExtractionEnhancementEngine
operator|.
name|PROCESSED_LANGUAGES
argument_list|,
name|value
operator|=
literal|""
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|NamedEntityExtractionEnhancementEngine
operator|.
name|DEFAULT_LANGUAGE
argument_list|,
name|value
operator|=
literal|""
argument_list|)
block|,
comment|//set the ranking of the default config to a negative value (ConfigurationPolicy.OPTIONAL)
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
operator|-
literal|100
argument_list|)
block|}
argument_list|)
annotation|@
name|Reference
argument_list|(
name|name
operator|=
literal|"openNLP"
argument_list|,
name|referenceInterface
operator|=
name|OpenNLP
operator|.
name|class
argument_list|,
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|MANDATORY_UNARY
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|STATIC
argument_list|)
specifier|public
class|class
name|NamedEntityExtractionEnhancementEngine
extends|extends
name|NEREngineCore
implements|implements
name|EnhancementEngine
implements|,
name|ServiceProperties
block|{
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_DATA_OPEN_NLP_MODEL_LOCATION
init|=
literal|"org/apache/stanbol/defaultdata/opennlp"
decl_stmt|;
comment|/**      * Allows to define the default language assumed for parsed Content if no language      * detection is available. If<code>null</code> or empty this engine will not      * process content with an unknown language      */
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_LANGUAGE
init|=
literal|"stanbol.NamedEntityExtractionEnhancementEngine.defaultLanguage"
decl_stmt|;
comment|/**      * Allows to restrict the list of languages processed by this engine. if      *<code>null</code> or empty content of any language where a NER model is      * available via {@link OpenNLP} will be processed.<p>      * This property allows to configure multiple instances of this engine that      * do only process specific languages. The default is a single instance that      * processes all languages.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROCESSED_LANGUAGES
init|=
literal|"stanbol.NamedEntityExtractionEnhancementEngine.processedLanguages"
decl_stmt|;
comment|/**      * The default value for the Execution of this Engine. Currently set to      * {@link ServiceProperties#ORDERING_CONTENT_EXTRACTION}      */
specifier|public
specifier|static
specifier|final
name|Integer
name|defaultOrder
init|=
name|ORDERING_CONTENT_EXTRACTION
decl_stmt|;
comment|/**      * Bind method of {@link NEREngineCore#openNLP}      * @param openNlp      */
specifier|protected
name|void
name|bindOpenNLP
parameter_list|(
name|OpenNLP
name|openNlp
parameter_list|)
block|{
name|this
operator|.
name|openNLP
operator|=
name|openNlp
expr_stmt|;
block|}
comment|/**      * Unbind method of {@link NEREngineCore#openNLP}      * @param openNLP      */
specifier|protected
name|void
name|unbindOpenNLP
parameter_list|(
name|OpenNLP
name|openNLP
parameter_list|)
block|{
name|this
operator|.
name|openNLP
operator|=
literal|null
expr_stmt|;
block|}
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
name|config
operator|=
operator|new
name|NEREngineConfig
argument_list|()
expr_stmt|;
comment|// Need to register the default data before loading the models
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
name|DEFAULT_LANGUAGE
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
condition|)
block|{
name|config
operator|.
name|setDefaultLanguage
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//else no default language
name|value
operator|=
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|PROCESSED_LANGUAGES
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|String
index|[]
condition|)
block|{
name|config
operator|.
name|getProcessedLanguages
argument_list|()
operator|.
name|addAll
argument_list|(
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
argument_list|)
expr_stmt|;
name|config
operator|.
name|getProcessedLanguages
argument_list|()
operator|.
name|remove
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|//remove null
name|config
operator|.
name|getProcessedLanguages
argument_list|()
operator|.
name|remove
argument_list|(
literal|""
argument_list|)
expr_stmt|;
comment|//remove empty
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
for|for
control|(
name|Object
name|o
operator|:
operator|(
operator|(
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
name|value
operator|)
control|)
block|{
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
name|config
operator|.
name|getProcessedLanguages
argument_list|()
operator|.
name|add
argument_list|(
name|o
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|config
operator|.
name|getProcessedLanguages
argument_list|()
operator|.
name|remove
argument_list|(
literal|""
argument_list|)
expr_stmt|;
comment|//remove empty
block|}
elseif|else
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
condition|)
block|{
comment|//if a single String is parsed we support ',' as seperator
name|String
index|[]
name|languageArray
init|=
name|value
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
name|config
operator|.
name|getProcessedLanguages
argument_list|()
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|languageArray
argument_list|)
argument_list|)
expr_stmt|;
name|config
operator|.
name|getProcessedLanguages
argument_list|()
operator|.
name|remove
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|//remove null
name|config
operator|.
name|getProcessedLanguages
argument_list|()
operator|.
name|remove
argument_list|(
literal|""
argument_list|)
expr_stmt|;
comment|//remove empty
block|}
comment|//else no configuration
if|if
condition|(
operator|!
name|config
operator|.
name|getProcessedLanguages
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
name|config
operator|.
name|getDefaultLanguage
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|config
operator|.
name|getProcessedLanguages
argument_list|()
operator|.
name|contains
argument_list|(
name|config
operator|.
name|getDefaultLanguage
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROCESSED_LANGUAGES
argument_list|,
literal|"The list of"
operator|+
literal|"processed Languages "
operator|+
name|config
operator|.
name|getProcessedLanguages
argument_list|()
operator|+
literal|" MUST CONTAIN the"
operator|+
literal|"configured default language '"
operator|+
name|config
operator|.
name|getDefaultLanguage
argument_list|()
operator|+
literal|"'!"
argument_list|)
throw|;
block|}
block|}
end_class

begin_function
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|config
operator|=
literal|null
expr_stmt|;
name|super
operator|.
name|deactivate
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
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
end_function

begin_comment
comment|//    @Override
end_comment

begin_comment
comment|//    public int canEnhance(ContentItem ci) throws EngineException {
end_comment

begin_comment
comment|//        checkCore();
end_comment

begin_comment
comment|//        return engineCore.canEnhance(ci);
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//    @Override
end_comment

begin_comment
comment|//    public void computeEnhancements(ContentItem ci) throws EngineException {
end_comment

begin_comment
comment|//        checkCore();
end_comment

begin_comment
comment|//        engineCore.computeEnhancements(ci);
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//    private void checkCore() {
end_comment

begin_comment
comment|//        if(engineCore == null) {
end_comment

begin_comment
comment|//            throw new IllegalStateException("EngineCore not initialized");
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

unit|}
end_unit

