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
name|commons
operator|.
name|jsonld
package|;
end_package

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

begin_comment
comment|/**  * Class to serialize a JSON object structure whereby the JSON structure is defined by the basic data types  * Map and List.  *   * @author Fabian Christ  */
end_comment

begin_class
specifier|public
class|class
name|JsonSerializer
block|{
specifier|public
specifier|static
name|String
name|toString
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|jsonMap
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|appendJsonMap
argument_list|(
name|jsonMap
argument_list|,
name|sb
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|removeOddChars
argument_list|(
name|sb
argument_list|,
literal|0
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|String
name|toString
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|jsonMap
parameter_list|,
name|int
name|indent
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|appendJsonMap
argument_list|(
name|jsonMap
argument_list|,
name|sb
argument_list|,
name|indent
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|removeOddChars
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|String
name|toString
parameter_list|(
name|List
argument_list|<
name|Object
argument_list|>
name|jsonArray
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|appendList
argument_list|(
name|jsonArray
argument_list|,
name|sb
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|String
name|toString
parameter_list|(
name|List
argument_list|<
name|Object
argument_list|>
name|jsonArray
parameter_list|,
name|int
name|indent
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|appendList
argument_list|(
name|jsonArray
argument_list|,
name|sb
argument_list|,
name|indent
argument_list|,
literal|0
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|void
name|appendJsonMap
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|jsonMap
parameter_list|,
name|StringBuffer
name|sb
parameter_list|,
name|int
name|indent
parameter_list|,
name|int
name|level
parameter_list|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'{'
argument_list|)
expr_stmt|;
name|level
operator|=
name|increaseIndentationLevel
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|,
name|level
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|key
range|:
name|jsonMap
operator|.
name|keySet
argument_list|()
control|)
block|{
name|appendIndentation
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|,
name|level
argument_list|)
expr_stmt|;
name|appendQuoted
argument_list|(
name|key
argument_list|,
name|sb
argument_list|)
expr_stmt|;
if|if
condition|(
name|indent
operator|==
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|':'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|": "
argument_list|)
expr_stmt|;
block|}
name|appendValueOf
argument_list|(
name|jsonMap
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|,
name|sb
argument_list|,
name|indent
argument_list|,
name|level
argument_list|)
expr_stmt|;
block|}
name|removeOddChars
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|)
expr_stmt|;
name|level
operator|=
name|decreaseIndentationLevel
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|,
name|level
argument_list|)
expr_stmt|;
name|appendIndentation
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|,
name|level
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'}'
argument_list|)
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|appendLinefeed
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|)
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|private
specifier|static
name|void
name|appendValueOf
parameter_list|(
name|Object
name|object
parameter_list|,
name|StringBuffer
name|sb
parameter_list|,
name|int
name|indent
parameter_list|,
name|int
name|level
parameter_list|)
block|{
if|if
condition|(
name|object
operator|==
literal|null
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|object
operator|instanceof
name|String
condition|)
block|{
name|String
name|strValue
init|=
operator|(
name|String
operator|)
name|object
decl_stmt|;
name|appendQuoted
argument_list|(
name|strValue
argument_list|,
name|sb
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|appendLinefeed
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|object
operator|instanceof
name|Map
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|mapValue
init|=
operator|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|object
decl_stmt|;
name|appendJsonMap
argument_list|(
name|mapValue
argument_list|,
name|sb
argument_list|,
name|indent
argument_list|,
name|level
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|object
operator|instanceof
name|List
argument_list|<
name|?
argument_list|>
condition|)
block|{
name|List
argument_list|<
name|Object
argument_list|>
name|lstValue
init|=
operator|(
name|List
argument_list|<
name|Object
argument_list|>
operator|)
name|object
decl_stmt|;
name|appendList
argument_list|(
name|lstValue
argument_list|,
name|sb
argument_list|,
name|indent
argument_list|,
name|level
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|appendLinefeed
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|object
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|appendLinefeed
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|void
name|appendList
parameter_list|(
name|List
argument_list|<
name|Object
argument_list|>
name|jsonArray
parameter_list|,
name|StringBuffer
name|sb
parameter_list|,
name|int
name|indent
parameter_list|,
name|int
name|level
parameter_list|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'['
argument_list|)
expr_stmt|;
name|level
operator|=
name|increaseIndentationLevel
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|,
name|level
argument_list|)
expr_stmt|;
for|for
control|(
name|Object
name|object
range|:
name|jsonArray
control|)
block|{
name|appendIndentation
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|,
name|level
argument_list|)
expr_stmt|;
name|appendValueOf
argument_list|(
name|object
argument_list|,
name|sb
argument_list|,
name|indent
argument_list|,
name|level
argument_list|)
expr_stmt|;
block|}
name|removeOddChars
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|)
expr_stmt|;
name|level
operator|=
name|decreaseIndentationLevel
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|,
name|level
argument_list|)
expr_stmt|;
name|appendIndentation
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|,
name|level
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|']'
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|void
name|appendQuoted
parameter_list|(
name|String
name|string
parameter_list|,
name|StringBuffer
name|sb
parameter_list|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'"'
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|string
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|char
name|ch
init|=
name|string
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|ch
condition|)
block|{
case|case
literal|'\\'
case|:
case|case
literal|'"'
case|:
name|sb
operator|.
name|append
argument_list|(
literal|'\\'
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|ch
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'/'
case|:
name|sb
operator|.
name|append
argument_list|(
literal|'\\'
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|ch
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'\b'
case|:
name|sb
operator|.
name|append
argument_list|(
literal|"\\b"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'\t'
case|:
name|sb
operator|.
name|append
argument_list|(
literal|"\\t"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'\n'
case|:
name|sb
operator|.
name|append
argument_list|(
literal|"\\n"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'\f'
case|:
name|sb
operator|.
name|append
argument_list|(
literal|"\\f"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'\r'
case|:
name|sb
operator|.
name|append
argument_list|(
literal|"\\r"
argument_list|)
expr_stmt|;
break|break;
default|default:
if|if
condition|(
name|ch
operator|<
literal|' '
condition|)
block|{
name|String
name|str
init|=
literal|"000"
operator|+
name|Integer
operator|.
name|toHexString
argument_list|(
name|ch
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"\\u"
operator|+
name|str
operator|.
name|substring
argument_list|(
name|str
operator|.
name|length
argument_list|()
operator|-
literal|4
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|ch
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|sb
operator|.
name|append
argument_list|(
literal|'"'
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|void
name|appendIndentation
parameter_list|(
name|StringBuffer
name|sb
parameter_list|,
name|int
name|indent
parameter_list|,
name|int
name|level
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
operator|(
name|indent
operator|*
name|level
operator|)
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|int
name|decreaseIndentationLevel
parameter_list|(
name|StringBuffer
name|sb
parameter_list|,
name|int
name|indent
parameter_list|,
name|int
name|level
parameter_list|)
block|{
if|if
condition|(
name|indent
operator|>
literal|0
condition|)
block|{
name|appendLinefeed
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|)
expr_stmt|;
name|level
operator|--
expr_stmt|;
block|}
return|return
name|level
return|;
block|}
specifier|private
specifier|static
name|int
name|increaseIndentationLevel
parameter_list|(
name|StringBuffer
name|sb
parameter_list|,
name|int
name|indent
parameter_list|,
name|int
name|level
parameter_list|)
block|{
if|if
condition|(
name|indent
operator|>
literal|0
condition|)
block|{
name|appendLinefeed
argument_list|(
name|sb
argument_list|,
name|indent
argument_list|)
expr_stmt|;
name|level
operator|++
expr_stmt|;
block|}
return|return
name|level
return|;
block|}
specifier|private
specifier|static
name|void
name|appendLinefeed
parameter_list|(
name|StringBuffer
name|sb
parameter_list|,
name|int
name|indent
parameter_list|)
block|{
if|if
condition|(
name|indent
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * During the serialization there are added ',' and line breaks '\n' by default that need to be deleted      * when not needed, e.g. at the end of a list.      *       * @param sb      * @param indent      */
specifier|private
specifier|static
name|void
name|removeOddChars
parameter_list|(
name|StringBuffer
name|sb
parameter_list|,
name|int
name|indent
parameter_list|)
block|{
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|2
condition|)
block|{
name|sb
operator|.
name|deleteCharAt
argument_list|(
name|sb
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|indent
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|deleteCharAt
argument_list|(
name|sb
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

