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
name|utils
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

begin_class
specifier|public
specifier|final
class|class
name|SolrUtil
block|{
specifier|private
name|SolrUtil
parameter_list|()
block|{}
specifier|private
specifier|static
specifier|final
name|String
name|LUCENE_ESCAPE_CHARS
init|=
literal|"[\\\\+\\-\\!\\(\\)\\:\\^\\[\\]\\{\\}\\~\\*\\?]"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Pattern
name|LUCENE_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|LUCENE_ESCAPE_CHARS
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|REPLACEMENT_STRING
init|=
literal|"\\\\$0"
decl_stmt|;
comment|/**      * Escapes all special chars in an string (field name or constraint) to be used in an SolrQuery.      *       * @param string      *            the string to be escaped      * @return the escaped string      */
specifier|public
specifier|static
name|String
name|escapeSolrSpecialChars
parameter_list|(
name|String
name|string
parameter_list|)
block|{
return|return
name|string
operator|!=
literal|null
condition|?
name|LUCENE_PATTERN
operator|.
name|matcher
argument_list|(
name|string
argument_list|)
operator|.
name|replaceAll
argument_list|(
name|REPLACEMENT_STRING
argument_list|)
else|:
literal|null
return|;
block|}
comment|/**      * This method encodes a parsed index value as needed for queries.      *<p>      * In case of TXT it is assumed that a whitespace tokenizer is used by the index. Therefore values with      * multiple words need to be treated and connected with AND to find only values that contain all. In case      * of STR no whitespace is assumed. Therefore spaces need to be replaced with '+' to search for tokens      * with the exact name. In all other cases the string need not to be converted.      *       * Note also that text queries are converted to lower case      *       * @param value      *            the index value      * @return the (possible multiple) values that need to be connected with AND      */
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
block|}
end_class

end_unit

