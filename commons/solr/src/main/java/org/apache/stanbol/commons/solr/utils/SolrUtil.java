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
name|commons
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
name|regex
operator|.
name|Pattern
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
literal|"[\\\\+\\-\\!\\(\\)\\:\\^\\[\\]\\{\\}\\~\\*\\?\\\"]"
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
name|WILDCARD_ESCAPE_CHARS
init|=
literal|"[\\\\+\\-\\!\\(\\)\\:\\^\\[\\]\\{\\}\\~\\\"]"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Pattern
name|WILDCARD_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|WILDCARD_ESCAPE_CHARS
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
name|String
name|escaped
init|=
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
decl_stmt|;
return|return
name|escaped
return|;
block|}
comment|/**      * Escapes all Solr special chars except the '*' and '?' as used for Wildcard      * searches      * @param string the string representing a wildcard search that needs to      * be escaped      * @return the escaped version of the wildcard search      */
specifier|public
specifier|static
name|String
name|escapeWildCardString
parameter_list|(
name|String
name|string
parameter_list|)
block|{
name|String
name|escaped
init|=
name|string
operator|!=
literal|null
condition|?
name|WILDCARD_PATTERN
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
decl_stmt|;
return|return
name|escaped
return|;
block|}
block|}
end_class

end_unit

