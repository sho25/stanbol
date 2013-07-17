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
name|nlp
operator|.
name|json
operator|.
name|valuetype
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
name|EnumSet
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
name|nlp
operator|.
name|json
operator|.
name|JsonUtils
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
name|json
operator|.
name|valuetype
operator|.
name|ValueTypeParser
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
name|json
operator|.
name|valuetype
operator|.
name|ValueTypeParserRegistry
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
name|json
operator|.
name|valuetype
operator|.
name|ValueTypeSerializer
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
name|json
operator|.
name|valuetype
operator|.
name|ValueTypeSerializerRegistry
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
name|morpho
operator|.
name|Case
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
name|morpho
operator|.
name|CaseTag
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
name|morpho
operator|.
name|Definitness
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
name|morpho
operator|.
name|Gender
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
name|morpho
operator|.
name|GenderTag
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
name|morpho
operator|.
name|MorphoFeatures
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
name|morpho
operator|.
name|NumberFeature
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
name|morpho
operator|.
name|NumberTag
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
name|morpho
operator|.
name|Person
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
name|morpho
operator|.
name|Tense
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
name|morpho
operator|.
name|TenseTag
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
name|morpho
operator|.
name|VerbMood
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
name|morpho
operator|.
name|VerbMoodTag
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
name|pos
operator|.
name|PosTag
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|JsonNode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|map
operator|.
name|ObjectMapper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|node
operator|.
name|ArrayNode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|node
operator|.
name|ObjectNode
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
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|IGNORE
argument_list|)
annotation|@
name|Service
argument_list|(
name|value
operator|=
block|{
name|ValueTypeParser
operator|.
name|class
block|,
name|ValueTypeSerializer
operator|.
name|class
block|}
argument_list|)
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|ValueTypeParser
operator|.
name|PROPERTY_TYPE
argument_list|,
name|value
operator|=
name|MorphoFeaturesSupport
operator|.
name|TYPE_VALUE
argument_list|)
specifier|public
class|class
name|MorphoFeaturesSupport
implements|implements
name|ValueTypeParser
argument_list|<
name|MorphoFeatures
argument_list|>
implements|,
name|ValueTypeSerializer
argument_list|<
name|MorphoFeatures
argument_list|>
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|MorphoFeaturesSupport
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TYPE_VALUE
init|=
literal|"org.apache.stanbol.enhancer.nlp.morpho.MorphoFeatures"
decl_stmt|;
annotation|@
name|Reference
specifier|protected
name|ValueTypeSerializerRegistry
name|serializerRegistry
decl_stmt|;
annotation|@
name|Reference
specifier|protected
name|ValueTypeParserRegistry
name|parserRegistry
decl_stmt|;
specifier|protected
name|ValueTypeSerializer
argument_list|<
name|PosTag
argument_list|>
name|getPosTagSerializer
parameter_list|()
block|{
if|if
condition|(
name|serializerRegistry
operator|==
literal|null
condition|)
block|{
name|serializerRegistry
operator|=
name|ValueTypeSerializerRegistry
operator|.
name|getInstance
argument_list|()
expr_stmt|;
block|}
return|return
name|serializerRegistry
operator|.
name|getSerializer
argument_list|(
name|PosTag
operator|.
name|class
argument_list|)
return|;
block|}
specifier|protected
name|ValueTypeParser
argument_list|<
name|PosTag
argument_list|>
name|getPosTagParser
parameter_list|()
block|{
if|if
condition|(
name|parserRegistry
operator|==
literal|null
condition|)
block|{
name|parserRegistry
operator|=
name|ValueTypeParserRegistry
operator|.
name|getInstance
argument_list|()
expr_stmt|;
block|}
return|return
name|parserRegistry
operator|.
name|getParser
argument_list|(
name|PosTag
operator|.
name|class
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|MorphoFeatures
argument_list|>
name|getType
parameter_list|()
block|{
return|return
name|MorphoFeatures
operator|.
name|class
return|;
block|}
annotation|@
name|Override
specifier|public
name|ObjectNode
name|serialize
parameter_list|(
name|ObjectMapper
name|mapper
parameter_list|,
name|MorphoFeatures
name|morpho
parameter_list|)
block|{
name|ObjectNode
name|jMorpho
init|=
name|mapper
operator|.
name|createObjectNode
argument_list|()
decl_stmt|;
name|jMorpho
operator|.
name|put
argument_list|(
literal|"lemma"
argument_list|,
name|morpho
operator|.
name|getLemma
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|CaseTag
argument_list|>
name|caseList
init|=
name|morpho
operator|.
name|getCaseList
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|caseList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ArrayNode
name|jCases
init|=
name|mapper
operator|.
name|createArrayNode
argument_list|()
decl_stmt|;
for|for
control|(
name|CaseTag
name|caseTag
range|:
name|caseList
control|)
block|{
name|ObjectNode
name|jCase
init|=
name|mapper
operator|.
name|createObjectNode
argument_list|()
decl_stmt|;
name|jCase
operator|.
name|put
argument_list|(
literal|"tag"
argument_list|,
name|caseTag
operator|.
name|getTag
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|caseTag
operator|.
name|getCase
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|jCase
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
name|caseTag
operator|.
name|getCase
argument_list|()
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|jCases
operator|.
name|add
argument_list|(
name|jCase
argument_list|)
expr_stmt|;
block|}
name|jMorpho
operator|.
name|put
argument_list|(
literal|"case"
argument_list|,
name|jCases
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|Definitness
argument_list|>
name|definitnesses
init|=
name|morpho
operator|.
name|getDefinitnessList
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|definitnesses
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|definitnesses
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|jMorpho
operator|.
name|put
argument_list|(
literal|"definitness"
argument_list|,
name|definitnesses
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ArrayNode
name|jDefinitnesses
init|=
name|mapper
operator|.
name|createArrayNode
argument_list|()
decl_stmt|;
for|for
control|(
name|Definitness
name|d
range|:
name|definitnesses
control|)
block|{
name|jDefinitnesses
operator|.
name|add
argument_list|(
name|d
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|jMorpho
operator|.
name|put
argument_list|(
literal|"definitness"
argument_list|,
name|jDefinitnesses
argument_list|)
expr_stmt|;
block|}
block|}
name|List
argument_list|<
name|GenderTag
argument_list|>
name|genderList
init|=
name|morpho
operator|.
name|getGenderList
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|genderList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ArrayNode
name|jGenders
init|=
name|mapper
operator|.
name|createArrayNode
argument_list|()
decl_stmt|;
for|for
control|(
name|GenderTag
name|genderTag
range|:
name|genderList
control|)
block|{
name|ObjectNode
name|jGender
init|=
name|mapper
operator|.
name|createObjectNode
argument_list|()
decl_stmt|;
name|jGender
operator|.
name|put
argument_list|(
literal|"tag"
argument_list|,
name|genderTag
operator|.
name|getTag
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|genderTag
operator|.
name|getGender
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|jGender
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
name|genderTag
operator|.
name|getGender
argument_list|()
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|jGenders
operator|.
name|add
argument_list|(
name|jGender
argument_list|)
expr_stmt|;
block|}
name|jMorpho
operator|.
name|put
argument_list|(
literal|"gender"
argument_list|,
name|jGenders
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|NumberTag
argument_list|>
name|numberList
init|=
name|morpho
operator|.
name|getNumberList
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|numberList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ArrayNode
name|jNumbers
init|=
name|mapper
operator|.
name|createArrayNode
argument_list|()
decl_stmt|;
for|for
control|(
name|NumberTag
name|numberTag
range|:
name|numberList
control|)
block|{
name|ObjectNode
name|jNumber
init|=
name|mapper
operator|.
name|createObjectNode
argument_list|()
decl_stmt|;
name|jNumber
operator|.
name|put
argument_list|(
literal|"tag"
argument_list|,
name|numberTag
operator|.
name|getTag
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|numberTag
operator|.
name|getNumber
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|jNumber
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
name|numberTag
operator|.
name|getNumber
argument_list|()
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|jNumbers
operator|.
name|add
argument_list|(
name|jNumber
argument_list|)
expr_stmt|;
block|}
name|jMorpho
operator|.
name|put
argument_list|(
literal|"number"
argument_list|,
name|jNumbers
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|Person
argument_list|>
name|persons
init|=
name|morpho
operator|.
name|getPersonList
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|persons
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|persons
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|jMorpho
operator|.
name|put
argument_list|(
literal|"person"
argument_list|,
name|persons
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ArrayNode
name|jPersons
init|=
name|mapper
operator|.
name|createArrayNode
argument_list|()
decl_stmt|;
for|for
control|(
name|Person
name|d
range|:
name|persons
control|)
block|{
name|jPersons
operator|.
name|add
argument_list|(
name|d
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|jMorpho
operator|.
name|put
argument_list|(
literal|"person"
argument_list|,
name|jPersons
argument_list|)
expr_stmt|;
block|}
block|}
name|List
argument_list|<
name|PosTag
argument_list|>
name|posList
init|=
name|morpho
operator|.
name|getPosList
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|posList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ArrayNode
name|jPosTags
init|=
name|mapper
operator|.
name|createArrayNode
argument_list|()
decl_stmt|;
for|for
control|(
name|PosTag
name|posTag
range|:
name|posList
control|)
block|{
name|jPosTags
operator|.
name|add
argument_list|(
name|getPosTagSerializer
argument_list|()
operator|.
name|serialize
argument_list|(
name|mapper
argument_list|,
name|posTag
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|jMorpho
operator|.
name|put
argument_list|(
literal|"pos"
argument_list|,
name|jPosTags
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|TenseTag
argument_list|>
name|tenseList
init|=
name|morpho
operator|.
name|getTenseList
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|tenseList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ArrayNode
name|jTenses
init|=
name|mapper
operator|.
name|createArrayNode
argument_list|()
decl_stmt|;
for|for
control|(
name|TenseTag
name|tenseTag
range|:
name|tenseList
control|)
block|{
name|ObjectNode
name|jTense
init|=
name|mapper
operator|.
name|createObjectNode
argument_list|()
decl_stmt|;
name|jTense
operator|.
name|put
argument_list|(
literal|"tag"
argument_list|,
name|tenseTag
operator|.
name|getTag
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|tenseTag
operator|.
name|getTense
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|jTense
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
name|tenseTag
operator|.
name|getTense
argument_list|()
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|jTenses
operator|.
name|add
argument_list|(
name|jTense
argument_list|)
expr_stmt|;
block|}
name|jMorpho
operator|.
name|put
argument_list|(
literal|"tense"
argument_list|,
name|jTenses
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|VerbMoodTag
argument_list|>
name|verbMoodList
init|=
name|morpho
operator|.
name|getVerbMoodList
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|verbMoodList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ArrayNode
name|jMoods
init|=
name|mapper
operator|.
name|createArrayNode
argument_list|()
decl_stmt|;
for|for
control|(
name|VerbMoodTag
name|verbMoodTag
range|:
name|verbMoodList
control|)
block|{
name|ObjectNode
name|jMood
init|=
name|mapper
operator|.
name|createObjectNode
argument_list|()
decl_stmt|;
name|jMood
operator|.
name|put
argument_list|(
literal|"tag"
argument_list|,
name|verbMoodTag
operator|.
name|getTag
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|verbMoodTag
operator|.
name|getVerbForm
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|jMood
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
name|verbMoodTag
operator|.
name|getVerbForm
argument_list|()
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|jMoods
operator|.
name|add
argument_list|(
name|jMood
argument_list|)
expr_stmt|;
block|}
name|jMorpho
operator|.
name|put
argument_list|(
literal|"verb-mood"
argument_list|,
name|jMoods
argument_list|)
expr_stmt|;
block|}
return|return
name|jMorpho
return|;
block|}
annotation|@
name|Override
specifier|public
name|MorphoFeatures
name|parse
parameter_list|(
name|ObjectNode
name|jMorpho
parameter_list|)
block|{
name|JsonNode
name|jLemma
init|=
name|jMorpho
operator|.
name|path
argument_list|(
literal|"lemma"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|jLemma
operator|.
name|isTextual
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Field 'lemma' MUST provide a String value (parsed JSON: "
operator|+
name|jMorpho
argument_list|)
throw|;
block|}
name|MorphoFeatures
name|morpho
init|=
operator|new
name|MorphoFeatures
argument_list|(
name|jLemma
operator|.
name|asText
argument_list|()
argument_list|)
decl_stmt|;
name|JsonNode
name|node
init|=
name|jMorpho
operator|.
name|path
argument_list|(
literal|"case"
argument_list|)
decl_stmt|;
if|if
condition|(
name|node
operator|.
name|isArray
argument_list|()
condition|)
block|{
name|ArrayNode
name|jCases
init|=
operator|(
name|ArrayNode
operator|)
name|node
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|jCases
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|JsonNode
name|member
init|=
name|jCases
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|member
operator|.
name|isObject
argument_list|()
condition|)
block|{
name|ObjectNode
name|jCase
init|=
operator|(
name|ObjectNode
operator|)
name|member
decl_stmt|;
name|JsonNode
name|tag
init|=
name|jCase
operator|.
name|path
argument_list|(
literal|"tag"
argument_list|)
decl_stmt|;
if|if
condition|(
name|tag
operator|.
name|isTextual
argument_list|()
condition|)
block|{
name|EnumSet
argument_list|<
name|Case
argument_list|>
name|type
init|=
name|JsonUtils
operator|.
name|parseEnum
argument_list|(
name|jCase
argument_list|,
literal|"type"
argument_list|,
name|Case
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|morpho
operator|.
name|addCase
argument_list|(
operator|new
name|CaseTag
argument_list|(
name|tag
operator|.
name|getTextValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|morpho
operator|.
name|addCase
argument_list|(
operator|new
name|CaseTag
argument_list|(
name|tag
operator|.
name|getTextValue
argument_list|()
argument_list|,
name|type
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse CaseTag becuase 'tag' value is "
operator|+
literal|"missing or is not a String (json: "
operator|+
name|jCase
operator|.
name|toString
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse CaseTag from "
operator|+
name|member
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|node
operator|.
name|isMissingNode
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse CaseTags (Json Array expected as value for field 'case' but was "
operator|+
name|node
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|jMorpho
operator|.
name|has
argument_list|(
literal|"definitness"
argument_list|)
condition|)
block|{
for|for
control|(
name|Definitness
name|d
range|:
name|JsonUtils
operator|.
name|parseEnum
argument_list|(
name|jMorpho
argument_list|,
literal|"definitness"
argument_list|,
name|Definitness
operator|.
name|class
argument_list|)
control|)
block|{
name|morpho
operator|.
name|addDefinitness
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
block|}
name|node
operator|=
name|jMorpho
operator|.
name|path
argument_list|(
literal|"gender"
argument_list|)
expr_stmt|;
if|if
condition|(
name|node
operator|.
name|isArray
argument_list|()
condition|)
block|{
name|ArrayNode
name|jGenders
init|=
operator|(
name|ArrayNode
operator|)
name|node
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|jGenders
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|JsonNode
name|member
init|=
name|jGenders
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|member
operator|.
name|isObject
argument_list|()
condition|)
block|{
name|ObjectNode
name|jGender
init|=
operator|(
name|ObjectNode
operator|)
name|member
decl_stmt|;
name|JsonNode
name|tag
init|=
name|jGender
operator|.
name|path
argument_list|(
literal|"tag"
argument_list|)
decl_stmt|;
if|if
condition|(
name|tag
operator|.
name|isTextual
argument_list|()
condition|)
block|{
name|EnumSet
argument_list|<
name|Gender
argument_list|>
name|type
init|=
name|JsonUtils
operator|.
name|parseEnum
argument_list|(
name|jGender
argument_list|,
literal|"type"
argument_list|,
name|Gender
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|morpho
operator|.
name|addGender
argument_list|(
operator|new
name|GenderTag
argument_list|(
name|tag
operator|.
name|getTextValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|morpho
operator|.
name|addGender
argument_list|(
operator|new
name|GenderTag
argument_list|(
name|tag
operator|.
name|getTextValue
argument_list|()
argument_list|,
name|type
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse GenderTag becuase 'tag' value is "
operator|+
literal|"missing or is not a String (json: "
operator|+
name|jGender
operator|.
name|toString
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse GenderTag from "
operator|+
name|member
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|node
operator|.
name|isMissingNode
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse GenderTag (Json Array expected as value for field 'case' but was "
operator|+
name|node
argument_list|)
expr_stmt|;
block|}
name|node
operator|=
name|jMorpho
operator|.
name|path
argument_list|(
literal|"number"
argument_list|)
expr_stmt|;
if|if
condition|(
name|node
operator|.
name|isArray
argument_list|()
condition|)
block|{
name|ArrayNode
name|jNumbers
init|=
operator|(
name|ArrayNode
operator|)
name|node
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|jNumbers
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|JsonNode
name|member
init|=
name|jNumbers
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|member
operator|.
name|isObject
argument_list|()
condition|)
block|{
name|ObjectNode
name|jNumber
init|=
operator|(
name|ObjectNode
operator|)
name|member
decl_stmt|;
name|JsonNode
name|tag
init|=
name|jNumber
operator|.
name|path
argument_list|(
literal|"tag"
argument_list|)
decl_stmt|;
if|if
condition|(
name|tag
operator|.
name|isTextual
argument_list|()
condition|)
block|{
name|EnumSet
argument_list|<
name|NumberFeature
argument_list|>
name|type
init|=
name|JsonUtils
operator|.
name|parseEnum
argument_list|(
name|jNumber
argument_list|,
literal|"type"
argument_list|,
name|NumberFeature
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|morpho
operator|.
name|addNumber
argument_list|(
operator|new
name|NumberTag
argument_list|(
name|tag
operator|.
name|getTextValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|morpho
operator|.
name|addNumber
argument_list|(
operator|new
name|NumberTag
argument_list|(
name|tag
operator|.
name|getTextValue
argument_list|()
argument_list|,
name|type
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse NumberTag becuase 'tag' value is "
operator|+
literal|"missing or is not a String (json: "
operator|+
name|jNumber
operator|.
name|toString
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse NumberTag from "
operator|+
name|member
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|node
operator|.
name|isMissingNode
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse NumberTag (Json Array expected as value for field 'case' but was "
operator|+
name|node
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|jMorpho
operator|.
name|has
argument_list|(
literal|"person"
argument_list|)
condition|)
block|{
for|for
control|(
name|Person
name|p
range|:
name|JsonUtils
operator|.
name|parseEnum
argument_list|(
name|jMorpho
argument_list|,
literal|"person"
argument_list|,
name|Person
operator|.
name|class
argument_list|)
control|)
block|{
name|morpho
operator|.
name|addPerson
argument_list|(
name|p
argument_list|)
expr_stmt|;
block|}
block|}
name|node
operator|=
name|jMorpho
operator|.
name|path
argument_list|(
literal|"pos"
argument_list|)
expr_stmt|;
if|if
condition|(
name|node
operator|.
name|isArray
argument_list|()
condition|)
block|{
name|ArrayNode
name|jPosTags
init|=
operator|(
name|ArrayNode
operator|)
name|node
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|jPosTags
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|JsonNode
name|member
init|=
name|jPosTags
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|member
operator|.
name|isObject
argument_list|()
condition|)
block|{
name|ObjectNode
name|jPosTag
init|=
operator|(
name|ObjectNode
operator|)
name|member
decl_stmt|;
name|morpho
operator|.
name|addPos
argument_list|(
name|getPosTagParser
argument_list|()
operator|.
name|parse
argument_list|(
name|jPosTag
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse PosTag from "
operator|+
name|member
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|node
operator|.
name|isMissingNode
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse PosTag (Json Array expected as value for field 'case' but was "
operator|+
name|node
argument_list|)
expr_stmt|;
block|}
name|node
operator|=
name|jMorpho
operator|.
name|path
argument_list|(
literal|"tense"
argument_list|)
expr_stmt|;
if|if
condition|(
name|node
operator|.
name|isArray
argument_list|()
condition|)
block|{
name|ArrayNode
name|jTenses
init|=
operator|(
name|ArrayNode
operator|)
name|node
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|jTenses
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|JsonNode
name|member
init|=
name|jTenses
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|member
operator|.
name|isObject
argument_list|()
condition|)
block|{
name|ObjectNode
name|jTense
init|=
operator|(
name|ObjectNode
operator|)
name|member
decl_stmt|;
name|JsonNode
name|tag
init|=
name|jTense
operator|.
name|path
argument_list|(
literal|"tag"
argument_list|)
decl_stmt|;
if|if
condition|(
name|tag
operator|.
name|isTextual
argument_list|()
condition|)
block|{
name|EnumSet
argument_list|<
name|Tense
argument_list|>
name|type
init|=
name|JsonUtils
operator|.
name|parseEnum
argument_list|(
name|jTense
argument_list|,
literal|"type"
argument_list|,
name|Tense
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|morpho
operator|.
name|addTense
argument_list|(
operator|new
name|TenseTag
argument_list|(
name|tag
operator|.
name|getTextValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|morpho
operator|.
name|addTense
argument_list|(
operator|new
name|TenseTag
argument_list|(
name|tag
operator|.
name|getTextValue
argument_list|()
argument_list|,
name|type
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse TenseTag becuase 'tag' value is "
operator|+
literal|"missing or is not a String (json: "
operator|+
name|jTense
operator|.
name|toString
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse TenseTag from "
operator|+
name|member
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|node
operator|.
name|isMissingNode
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse TenseTag (Json Array expected as value for field 'case' but was "
operator|+
name|node
argument_list|)
expr_stmt|;
block|}
name|node
operator|=
name|jMorpho
operator|.
name|path
argument_list|(
literal|"verb-mood"
argument_list|)
expr_stmt|;
if|if
condition|(
name|node
operator|.
name|isArray
argument_list|()
condition|)
block|{
name|ArrayNode
name|jVerbMoods
init|=
operator|(
name|ArrayNode
operator|)
name|node
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|jVerbMoods
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|JsonNode
name|member
init|=
name|jVerbMoods
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|member
operator|.
name|isObject
argument_list|()
condition|)
block|{
name|ObjectNode
name|jVerbMood
init|=
operator|(
name|ObjectNode
operator|)
name|member
decl_stmt|;
name|JsonNode
name|tag
init|=
name|jVerbMood
operator|.
name|path
argument_list|(
literal|"tag"
argument_list|)
decl_stmt|;
if|if
condition|(
name|tag
operator|.
name|isTextual
argument_list|()
condition|)
block|{
name|EnumSet
argument_list|<
name|VerbMood
argument_list|>
name|type
init|=
name|JsonUtils
operator|.
name|parseEnum
argument_list|(
name|jVerbMood
argument_list|,
literal|"type"
argument_list|,
name|VerbMood
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|morpho
operator|.
name|addVerbForm
argument_list|(
operator|new
name|VerbMoodTag
argument_list|(
name|tag
operator|.
name|getTextValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|morpho
operator|.
name|addVerbForm
argument_list|(
operator|new
name|VerbMoodTag
argument_list|(
name|tag
operator|.
name|getTextValue
argument_list|()
argument_list|,
name|type
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse VerbMoodTag becuase 'tag' value is "
operator|+
literal|"missing or is not a String (json: "
operator|+
name|jVerbMood
operator|.
name|toString
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse VerbMoodTag from "
operator|+
name|member
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|node
operator|.
name|isMissingNode
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse VerbMoodTag (Json Array expected as value for field 'case' but was "
operator|+
name|node
argument_list|)
expr_stmt|;
block|}
return|return
name|morpho
return|;
block|}
block|}
end_class

end_unit

