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
name|yard
operator|.
name|solr
operator|.
name|query
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
name|HashSet
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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|SolrUtil
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
name|yard
operator|.
name|solr
operator|.
name|defaults
operator|.
name|IndexDataTypeEnum
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
name|yard
operator|.
name|solr
operator|.
name|model
operator|.
name|IndexValue
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
name|yard
operator|.
name|solr
operator|.
name|model
operator|.
name|IndexValueFactory
import|;
end_import

begin_class
specifier|public
specifier|final
class|class
name|QueryUtils
block|{
specifier|private
name|QueryUtils
parameter_list|()
block|{}
comment|/**      * This method encodes a parsed index value as needed for queries.      *<p>      * In case of TXT it is assumed that a whitespace tokenizer is used by the index. Therefore values with      * multiple words need to be treated and connected with AND to find only values that contain all. In case      * of STR no whitespace is assumed. Therefore spaces need to be replaced with '+' to search for tokens      * with the exact name. In all other cases the string need not to be converted.      *       * Note also that text queries are converted to lower case      *       * @param value      *            the index value      * @param escape if<code>true</code> all Solr special chars are escaped if      *<code>false</code> than '*' and '?' as used for wildcard searches are      * not escaped.      * @return the (possible multiple) values that need to be connected with AND      */
specifier|public
specifier|static
name|String
index|[]
name|encodeQueryValue
parameter_list|(
name|IndexValue
name|indexValue
parameter_list|,
name|boolean
name|escape
parameter_list|)
block|{
if|if
condition|(
name|indexValue
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|String
index|[]
name|queryConstraints
decl_stmt|;
name|String
name|value
init|=
name|indexValue
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|escape
condition|)
block|{
name|value
operator|=
name|SolrUtil
operator|.
name|escapeSolrSpecialChars
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|value
operator|=
name|SolrUtil
operator|.
name|escapeWildCardString
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|IndexDataTypeEnum
operator|.
name|TXT
operator|.
name|getIndexType
argument_list|()
operator|.
name|equals
argument_list|(
name|indexValue
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|value
operator|=
name|value
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|tokens
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|value
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|tokens
operator|.
name|remove
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|queryConstraints
operator|=
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
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|IndexDataTypeEnum
operator|.
name|STR
operator|.
name|getIndexType
argument_list|()
operator|.
name|equals
argument_list|(
name|indexValue
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|value
operator|=
name|value
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|queryConstraints
operator|=
operator|new
name|String
index|[]
block|{
name|value
operator|.
name|replace
argument_list|(
literal|' '
argument_list|,
literal|'+'
argument_list|)
block|}
expr_stmt|;
block|}
else|else
block|{
name|queryConstraints
operator|=
operator|new
name|String
index|[]
block|{
name|value
block|}
expr_stmt|;
block|}
return|return
name|queryConstraints
return|;
block|}
comment|/**      * Utility Method that extracts IndexValues form an parsed {@link Object}.      * This checks for {@link IndexValue}, {@link Iterable}s and values      * @param indexValueFactory The indexValueFactory used to create indexValues if necessary      * @param value the value to parse      * @return A set with the parsed values. The returned Set is guaranteed       * not to be<code>null</code> and contains at least a single element.       * If no IndexValue could be parsed from the parsed value than a set containing      * the<code>null</code> value is returned.      */
specifier|public
specifier|static
name|Set
argument_list|<
name|IndexValue
argument_list|>
name|parseIndexValues
parameter_list|(
name|IndexValueFactory
name|indexValueFactory
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|Set
argument_list|<
name|IndexValue
argument_list|>
name|indexValues
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|indexValues
operator|=
name|Collections
operator|.
name|singleton
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|IndexValue
condition|)
block|{
name|indexValues
operator|=
name|Collections
operator|.
name|singleton
argument_list|(
operator|(
name|IndexValue
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
name|Iterable
argument_list|<
name|?
argument_list|>
condition|)
block|{
name|indexValues
operator|=
operator|new
name|HashSet
argument_list|<
name|IndexValue
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Object
name|o
operator|:
operator|(
name|Iterable
argument_list|<
name|?
argument_list|>
operator|)
name|value
control|)
block|{
if|if
condition|(
name|o
operator|instanceof
name|IndexValue
condition|)
block|{
name|indexValues
operator|.
name|add
argument_list|(
operator|(
name|IndexValue
operator|)
name|o
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
name|indexValues
operator|.
name|add
argument_list|(
name|indexValueFactory
operator|.
name|createIndexValue
argument_list|(
name|o
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|indexValues
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|indexValues
operator|.
name|add
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|//add null element instead of an empty set
block|}
block|}
else|else
block|{
name|indexValues
operator|=
name|Collections
operator|.
name|singleton
argument_list|(
name|indexValueFactory
operator|.
name|createIndexValue
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|indexValues
return|;
block|}
end_class

unit|}
end_unit

