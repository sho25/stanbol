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
name|Set
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
name|dependency
operator|.
name|DependencyFeatures
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
name|dependency
operator|.
name|DependencyRelation
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
name|dependency
operator|.
name|GrammaticalRelation
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
name|dependency
operator|.
name|GrammaticalRelationTag
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
name|Span
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
name|Span
operator|.
name|SpanTypeEnum
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
name|DependencyFeaturesSupport
operator|.
name|TYPE_VALUE
argument_list|)
specifier|public
class|class
name|DependencyFeaturesSupport
implements|implements
name|ValueTypeParser
argument_list|<
name|DependencyFeatures
argument_list|>
implements|,
name|ValueTypeSerializer
argument_list|<
name|DependencyFeatures
argument_list|>
block|{
specifier|public
specifier|static
specifier|final
name|String
name|TYPE_VALUE
init|=
literal|"org.apache.stanbol.enhancer.nlp.dependency.DependencyFeatures"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|RELATION_TYPE_TAG
init|=
literal|"tag"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|RELATION_STANBOL_TYPE_TAG
init|=
literal|"relationType"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|RELATION_IS_DEPENDEE_TAG
init|=
literal|"isDependent"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|RELATION_PARTNER_TYPE_TAG
init|=
literal|"type"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|RELATION_PARTNER_START_TAG
init|=
literal|"start"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|RELATION_PARTNER_END_TAG
init|=
literal|"end"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|RELATIONS_TAG
init|=
literal|"relations"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|ROOT_TAG
init|=
literal|"ROOT"
decl_stmt|;
annotation|@
name|Override
specifier|public
name|ObjectNode
name|serialize
parameter_list|(
name|ObjectMapper
name|mapper
parameter_list|,
name|DependencyFeatures
name|value
parameter_list|)
block|{
name|ObjectNode
name|jDependencyFeature
init|=
name|mapper
operator|.
name|createObjectNode
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|DependencyRelation
argument_list|>
name|relations
init|=
name|value
operator|.
name|getRelations
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|relations
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ArrayNode
name|jRelations
init|=
name|mapper
operator|.
name|createArrayNode
argument_list|()
decl_stmt|;
for|for
control|(
name|DependencyRelation
name|relation
range|:
name|relations
control|)
block|{
name|ObjectNode
name|jRelation
init|=
name|mapper
operator|.
name|createObjectNode
argument_list|()
decl_stmt|;
name|GrammaticalRelationTag
name|gramRelTag
init|=
name|relation
operator|.
name|getGrammaticalRelationTag
argument_list|()
decl_stmt|;
name|jRelation
operator|.
name|put
argument_list|(
name|RELATION_TYPE_TAG
argument_list|,
name|gramRelTag
operator|.
name|getTag
argument_list|()
argument_list|)
expr_stmt|;
name|jRelation
operator|.
name|put
argument_list|(
name|RELATION_STANBOL_TYPE_TAG
argument_list|,
name|gramRelTag
operator|.
name|getGrammaticalRelation
argument_list|()
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|jRelation
operator|.
name|put
argument_list|(
name|RELATION_IS_DEPENDEE_TAG
argument_list|,
operator|(
name|relation
operator|.
name|isDependent
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
operator|)
argument_list|)
expr_stmt|;
name|Span
name|partner
init|=
name|relation
operator|.
name|getPartner
argument_list|()
decl_stmt|;
if|if
condition|(
name|partner
operator|!=
literal|null
condition|)
block|{
name|jRelation
operator|.
name|put
argument_list|(
name|RELATION_PARTNER_TYPE_TAG
argument_list|,
name|partner
operator|.
name|getType
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|jRelation
operator|.
name|put
argument_list|(
name|RELATION_PARTNER_START_TAG
argument_list|,
name|partner
operator|.
name|getStart
argument_list|()
argument_list|)
expr_stmt|;
name|jRelation
operator|.
name|put
argument_list|(
name|RELATION_PARTNER_END_TAG
argument_list|,
name|partner
operator|.
name|getEnd
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|jRelation
operator|.
name|put
argument_list|(
name|RELATION_PARTNER_TYPE_TAG
argument_list|,
literal|"ROOT"
argument_list|)
expr_stmt|;
name|jRelation
operator|.
name|put
argument_list|(
name|RELATION_PARTNER_START_TAG
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|jRelation
operator|.
name|put
argument_list|(
name|RELATION_PARTNER_END_TAG
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
name|jRelations
operator|.
name|add
argument_list|(
name|jRelation
argument_list|)
expr_stmt|;
block|}
name|jDependencyFeature
operator|.
name|put
argument_list|(
name|RELATIONS_TAG
argument_list|,
name|jRelations
argument_list|)
expr_stmt|;
block|}
return|return
name|jDependencyFeature
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|DependencyFeatures
argument_list|>
name|getType
parameter_list|()
block|{
return|return
name|DependencyFeatures
operator|.
name|class
return|;
block|}
annotation|@
name|Override
specifier|public
name|DependencyFeatures
name|parse
parameter_list|(
name|ObjectNode
name|jDependencyFeature
parameter_list|,
name|AnalysedText
name|at
parameter_list|)
block|{
name|DependencyFeatures
name|dependencyFeature
init|=
operator|new
name|DependencyFeatures
argument_list|()
decl_stmt|;
name|JsonNode
name|node
init|=
name|jDependencyFeature
operator|.
name|path
argument_list|(
name|RELATIONS_TAG
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
name|jRelations
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
name|jRelations
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
name|jRelations
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
name|jRelation
init|=
operator|(
name|ObjectNode
operator|)
name|member
decl_stmt|;
name|JsonNode
name|tag
init|=
name|jRelation
operator|.
name|path
argument_list|(
name|RELATION_TYPE_TAG
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|tag
operator|.
name|isTextual
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to parse GrammaticalRelationTag. The value of the "
operator|+
literal|"'tag' field MUST have a textual value (json: "
operator|+
name|jDependencyFeature
operator|+
literal|")"
argument_list|)
throw|;
block|}
name|GrammaticalRelation
name|grammaticalRelation
init|=
name|GrammaticalRelation
operator|.
name|class
operator|.
name|getEnumConstants
argument_list|()
index|[
name|jRelation
operator|.
name|path
argument_list|(
name|RELATION_STANBOL_TYPE_TAG
argument_list|)
operator|.
name|asInt
argument_list|()
index|]
decl_stmt|;
name|GrammaticalRelationTag
name|gramRelTag
init|=
operator|new
name|GrammaticalRelationTag
argument_list|(
name|tag
operator|.
name|getTextValue
argument_list|()
argument_list|,
name|grammaticalRelation
argument_list|)
decl_stmt|;
name|JsonNode
name|isDependent
init|=
name|jRelation
operator|.
name|path
argument_list|(
name|RELATION_IS_DEPENDEE_TAG
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|isDependent
operator|.
name|isBoolean
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Field 'isDependent' must have a true/false format"
argument_list|)
throw|;
block|}
name|Span
name|partnerSpan
init|=
literal|null
decl_stmt|;
name|String
name|typeString
init|=
name|jRelation
operator|.
name|path
argument_list|(
name|RELATION_PARTNER_TYPE_TAG
argument_list|)
operator|.
name|getTextValue
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|typeString
operator|.
name|equals
argument_list|(
name|ROOT_TAG
argument_list|)
condition|)
block|{
name|SpanTypeEnum
name|spanType
init|=
name|SpanTypeEnum
operator|.
name|valueOf
argument_list|(
name|jRelation
operator|.
name|path
argument_list|(
name|RELATION_PARTNER_TYPE_TAG
argument_list|)
operator|.
name|getTextValue
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|spanStart
init|=
name|jRelation
operator|.
name|path
argument_list|(
name|RELATION_PARTNER_START_TAG
argument_list|)
operator|.
name|asInt
argument_list|()
decl_stmt|;
name|int
name|spanEnd
init|=
name|jRelation
operator|.
name|path
argument_list|(
name|RELATION_PARTNER_END_TAG
argument_list|)
operator|.
name|asInt
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|spanType
condition|)
block|{
case|case
name|Chunk
case|:
name|partnerSpan
operator|=
name|at
operator|.
name|addChunk
argument_list|(
name|spanStart
argument_list|,
name|spanEnd
argument_list|)
expr_stmt|;
break|break;
case|case
name|Sentence
case|:
case|case
name|Text
case|:
case|case
name|TextSection
case|:
break|break;
case|case
name|Token
case|:
name|partnerSpan
operator|=
name|at
operator|.
name|addToken
argument_list|(
name|spanStart
argument_list|,
name|spanEnd
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
name|DependencyRelation
name|relation
init|=
operator|new
name|DependencyRelation
argument_list|(
name|gramRelTag
argument_list|,
name|isDependent
operator|.
name|asBoolean
argument_list|()
argument_list|,
name|partnerSpan
argument_list|)
decl_stmt|;
name|dependencyFeature
operator|.
name|addRelation
argument_list|(
name|relation
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|dependencyFeature
return|;
block|}
block|}
end_class

end_unit
