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
name|entityhub
operator|.
name|jersey
operator|.
name|writers
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
name|Collection
import|;
end_import

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
name|Set
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
name|stanbol
operator|.
name|entityhub
operator|.
name|ldpath
operator|.
name|query
operator|.
name|LDPathSelect
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|defaults
operator|.
name|DataTypeEnum
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|defaults
operator|.
name|NamespaceEnum
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|Constraint
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|FieldQuery
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|RangeConstraint
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|ReferenceConstraint
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|TextConstraint
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|ValueConstraint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONArray
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONObject
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
specifier|final
class|class
name|FieldQueryToJSON
block|{
specifier|private
name|FieldQueryToJSON
parameter_list|()
block|{
comment|/* do not create instances of utility classes */
block|}
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|FieldQueryToJSON
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Converts a {@link FieldQuery} to it's JSON representation      *      * @param query the Query      * @return the {@link JSONObject}      * @throws JSONException      */
specifier|static
name|JSONObject
name|toJSON
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONObject
name|jQuery
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
name|jQuery
operator|.
name|put
argument_list|(
literal|"selected"
argument_list|,
operator|new
name|JSONArray
argument_list|(
name|query
operator|.
name|getSelectedFields
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|JSONArray
name|constraints
init|=
operator|new
name|JSONArray
argument_list|()
decl_stmt|;
name|jQuery
operator|.
name|put
argument_list|(
literal|"constraints"
argument_list|,
name|constraints
argument_list|)
expr_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|Constraint
argument_list|>
name|fieldConstraint
range|:
name|query
control|)
block|{
name|JSONObject
name|jFieldConstraint
init|=
name|convertConstraintToJSON
argument_list|(
name|fieldConstraint
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
name|jFieldConstraint
operator|.
name|put
argument_list|(
literal|"field"
argument_list|,
name|fieldConstraint
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
comment|//add the field
name|constraints
operator|.
name|put
argument_list|(
name|jFieldConstraint
argument_list|)
expr_stmt|;
comment|//add fieldConstraint
block|}
if|if
condition|(
name|query
operator|.
name|getLimit
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|jQuery
operator|.
name|put
argument_list|(
literal|"limit"
argument_list|,
name|query
operator|.
name|getLimit
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//if(query.getOffset() != 0){
name|jQuery
operator|.
name|put
argument_list|(
literal|"offset"
argument_list|,
name|query
operator|.
name|getOffset
argument_list|()
argument_list|)
expr_stmt|;
comment|//}
if|if
condition|(
name|query
operator|instanceof
name|LDPathSelect
operator|&&
operator|(
operator|(
name|LDPathSelect
operator|)
name|query
operator|)
operator|.
name|getLDPathSelect
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
operator|(
operator|(
name|LDPathSelect
operator|)
name|query
operator|)
operator|.
name|getLDPathSelect
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|jQuery
operator|.
name|put
argument_list|(
literal|"ldpath"
argument_list|,
operator|(
operator|(
name|LDPathSelect
operator|)
name|query
operator|)
operator|.
name|getLDPathSelect
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|jQuery
return|;
block|}
comment|/**      * Converts a {@link Constraint} to JSON      *      * @param constraint the {@link Constraint}      * @return the JSON representation      * @throws JSONException      */
specifier|private
specifier|static
name|JSONObject
name|convertConstraintToJSON
parameter_list|(
name|Constraint
name|constraint
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONObject
name|jConstraint
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
name|jConstraint
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
name|constraint
operator|.
name|getType
argument_list|()
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|constraint
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|value
case|:
comment|//both ValueConstraint and ReferenceConstraint
name|ValueConstraint
name|valueConstraint
init|=
operator|(
operator|(
name|ValueConstraint
operator|)
name|constraint
operator|)
decl_stmt|;
if|if
condition|(
name|valueConstraint
operator|.
name|getValue
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|jConstraint
operator|.
name|put
argument_list|(
literal|"value"
argument_list|,
name|valueConstraint
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|constraint
operator|instanceof
name|ReferenceConstraint
condition|)
block|{
comment|//the type "reference" is not present in the ConstraintType
comment|//enum, because internally ReferenceConstraints are just a
comment|//ValueConstraint with a predefined data type, but "reference"
comment|//is still a valid value of the type property in JSON
name|jConstraint
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
literal|"reference"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// valueConstraint
name|jConstraint
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
name|constraint
operator|.
name|getType
argument_list|()
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
comment|//for valueConstraints we need to add also the dataType(s)
name|Collection
argument_list|<
name|String
argument_list|>
name|dataTypes
init|=
name|valueConstraint
operator|.
name|getDataTypes
argument_list|()
decl_stmt|;
if|if
condition|(
name|dataTypes
operator|!=
literal|null
operator|&&
operator|!
name|dataTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|dataTypes
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|jConstraint
operator|.
name|put
argument_list|(
literal|"datatype"
argument_list|,
name|NamespaceEnum
operator|.
name|getShortName
argument_list|(
name|dataTypes
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
else|else
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|dataTypeValues
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
name|dataTypes
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|dataType
range|:
name|dataTypes
control|)
block|{
name|dataTypeValues
operator|.
name|add
argument_list|(
name|NamespaceEnum
operator|.
name|getShortName
argument_list|(
name|dataType
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|jConstraint
operator|.
name|put
argument_list|(
literal|"datatype"
argument_list|,
name|dataTypeValues
argument_list|)
expr_stmt|;
block|}
block|}
block|}
break|break;
case|case
name|text
case|:
name|TextConstraint
name|textConstraint
init|=
operator|(
name|TextConstraint
operator|)
name|constraint
decl_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|languages
init|=
name|textConstraint
operator|.
name|getLanguages
argument_list|()
decl_stmt|;
if|if
condition|(
name|languages
operator|!=
literal|null
operator|&&
operator|!
name|languages
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|languages
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|jConstraint
operator|.
name|put
argument_list|(
literal|"language"
argument_list|,
name|languages
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|jConstraint
operator|.
name|put
argument_list|(
literal|"language"
argument_list|,
operator|new
name|JSONArray
argument_list|(
name|languages
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|jConstraint
operator|.
name|put
argument_list|(
literal|"patternType"
argument_list|,
name|textConstraint
operator|.
name|getPatternType
argument_list|()
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|textConstraint
operator|.
name|getTexts
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|textConstraint
operator|.
name|getTexts
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|textConstraint
operator|.
name|getTexts
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
comment|//write a string
name|jConstraint
operator|.
name|put
argument_list|(
literal|"text"
argument_list|,
name|textConstraint
operator|.
name|getTexts
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//write an array
name|jConstraint
operator|.
name|put
argument_list|(
literal|"text"
argument_list|,
name|textConstraint
operator|.
name|getTexts
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|textConstraint
operator|.
name|isCaseSensitive
argument_list|()
condition|)
block|{
name|jConstraint
operator|.
name|put
argument_list|(
literal|"caseSensitive"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
comment|//else default is false
break|break;
case|case
name|range
case|:
name|RangeConstraint
name|rangeConstraint
init|=
operator|(
name|RangeConstraint
operator|)
name|constraint
decl_stmt|;
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
name|dataTypes
init|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|DataTypeEnum
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|rangeConstraint
operator|.
name|getLowerBound
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|jConstraint
operator|.
name|put
argument_list|(
literal|"lowerBound"
argument_list|,
name|rangeConstraint
operator|.
name|getLowerBound
argument_list|()
argument_list|)
expr_stmt|;
name|dataTypes
operator|.
name|addAll
argument_list|(
name|DataTypeEnum
operator|.
name|getPrimaryDataTypes
argument_list|(
name|rangeConstraint
operator|.
name|getLowerBound
argument_list|()
operator|.
name|getClass
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|rangeConstraint
operator|.
name|getUpperBound
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|jConstraint
operator|.
name|put
argument_list|(
literal|"upperBound"
argument_list|,
name|rangeConstraint
operator|.
name|getUpperBound
argument_list|()
argument_list|)
expr_stmt|;
name|dataTypes
operator|.
name|addAll
argument_list|(
name|DataTypeEnum
operator|.
name|getPrimaryDataTypes
argument_list|(
name|rangeConstraint
operator|.
name|getUpperBound
argument_list|()
operator|.
name|getClass
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|jConstraint
operator|.
name|put
argument_list|(
literal|"inclusive"
argument_list|,
name|rangeConstraint
operator|.
name|isInclusive
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|dataTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|jConstraint
operator|.
name|put
argument_list|(
literal|"datatype"
argument_list|,
name|dataTypes
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|.
name|getShortName
argument_list|()
argument_list|)
expr_stmt|;
block|}
default|default:
comment|//unknown constraint type
name|log
operator|.
name|warn
argument_list|(
literal|"Unsupported Constriant Type "
operator|+
name|constraint
operator|.
name|getType
argument_list|()
operator|+
literal|" (implementing class="
operator|+
name|constraint
operator|.
name|getClass
argument_list|()
operator|+
literal|"| toString="
operator|+
name|constraint
operator|+
literal|") -> skiped"
argument_list|)
expr_stmt|;
break|break;
block|}
return|return
name|jConstraint
return|;
block|}
block|}
end_class

end_unit

