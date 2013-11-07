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
name|Collections
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
name|coref
operator|.
name|CorefTag
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
name|CorefTagSupport
operator|.
name|TYPE_VALUE
argument_list|)
specifier|public
class|class
name|CorefTagSupport
implements|implements
name|ValueTypeParser
argument_list|<
name|CorefTag
argument_list|>
implements|,
name|ValueTypeSerializer
argument_list|<
name|CorefTag
argument_list|>
block|{
specifier|public
specifier|static
specifier|final
name|String
name|TYPE_VALUE
init|=
literal|"org.apache.stanbol.enhancer.nlp.coref.CorefTag"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|IS_REPRESENTATIVE_TAG
init|=
literal|"isRepresentative"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|MENTIONS_TAG
init|=
literal|"mentions"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|MENTION_TYPE_TAG
init|=
literal|"type"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|MENTION_START_TAG
init|=
literal|"start"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|MENTION_END_TAG
init|=
literal|"end"
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
name|CorefTag
name|coref
parameter_list|)
block|{
name|ObjectNode
name|jCoref
init|=
name|mapper
operator|.
name|createObjectNode
argument_list|()
decl_stmt|;
name|jCoref
operator|.
name|put
argument_list|(
name|IS_REPRESENTATIVE_TAG
argument_list|,
name|coref
operator|.
name|isRepresentative
argument_list|()
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|Span
argument_list|>
name|mentions
init|=
name|coref
operator|.
name|getMentions
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|mentions
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ArrayNode
name|jMentions
init|=
name|mapper
operator|.
name|createArrayNode
argument_list|()
decl_stmt|;
for|for
control|(
name|Span
name|mention
range|:
name|mentions
control|)
block|{
name|ObjectNode
name|jMention
init|=
name|mapper
operator|.
name|createObjectNode
argument_list|()
decl_stmt|;
name|jMention
operator|.
name|put
argument_list|(
name|MENTION_TYPE_TAG
argument_list|,
name|mention
operator|.
name|getType
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|jMention
operator|.
name|put
argument_list|(
name|MENTION_START_TAG
argument_list|,
name|mention
operator|.
name|getStart
argument_list|()
argument_list|)
expr_stmt|;
name|jMention
operator|.
name|put
argument_list|(
name|MENTION_END_TAG
argument_list|,
name|mention
operator|.
name|getEnd
argument_list|()
argument_list|)
expr_stmt|;
name|jMentions
operator|.
name|add
argument_list|(
name|jMention
argument_list|)
expr_stmt|;
block|}
name|jCoref
operator|.
name|put
argument_list|(
name|MENTIONS_TAG
argument_list|,
name|jMentions
argument_list|)
expr_stmt|;
block|}
return|return
name|jCoref
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|CorefTag
argument_list|>
name|getType
parameter_list|()
block|{
return|return
name|CorefTag
operator|.
name|class
return|;
block|}
annotation|@
name|Override
specifier|public
name|CorefTag
name|parse
parameter_list|(
name|ObjectNode
name|jCoref
parameter_list|,
name|AnalysedText
name|at
parameter_list|)
block|{
name|JsonNode
name|jIsRepresentative
init|=
name|jCoref
operator|.
name|path
argument_list|(
name|IS_REPRESENTATIVE_TAG
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|jIsRepresentative
operator|.
name|isBoolean
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Field 'isRepresentative' must have a true/false format"
argument_list|)
throw|;
block|}
name|JsonNode
name|node
init|=
name|jCoref
operator|.
name|path
argument_list|(
name|MENTIONS_TAG
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Span
argument_list|>
name|mentions
init|=
name|Collections
operator|.
expr|<
name|Span
operator|>
name|emptySet
argument_list|()
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
name|jMentions
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
name|jMentions
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
name|jMentions
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
name|jMention
init|=
operator|(
name|ObjectNode
operator|)
name|member
decl_stmt|;
name|SpanTypeEnum
name|spanType
init|=
name|SpanTypeEnum
operator|.
name|valueOf
argument_list|(
name|jMention
operator|.
name|path
argument_list|(
name|MENTION_TYPE_TAG
argument_list|)
operator|.
name|getTextValue
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|spanStart
init|=
name|jMention
operator|.
name|path
argument_list|(
name|MENTION_START_TAG
argument_list|)
operator|.
name|asInt
argument_list|()
decl_stmt|;
name|int
name|spanEnd
init|=
name|jMention
operator|.
name|path
argument_list|(
name|MENTION_END_TAG
argument_list|)
operator|.
name|asInt
argument_list|()
decl_stmt|;
name|Span
name|mentionedSpan
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|spanType
condition|)
block|{
case|case
name|Chunk
case|:
name|mentionedSpan
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
name|mentionedSpan
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
name|mentions
operator|.
name|add
argument_list|(
name|mentionedSpan
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
operator|new
name|CorefTag
argument_list|(
name|jIsRepresentative
operator|.
name|asBoolean
argument_list|()
argument_list|,
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|mentions
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

