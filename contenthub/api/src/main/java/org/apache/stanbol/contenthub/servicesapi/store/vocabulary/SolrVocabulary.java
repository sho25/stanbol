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
name|contenthub
operator|.
name|servicesapi
operator|.
name|store
operator|.
name|vocabulary
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_comment
comment|/**  * Vocabulary class which provides constant properties to be used in the communication with Solr. Most of  * these properties point to the fields defined in the<b>schema.xml</b> of Solr.  *   * @author anil.sinaci  *   */
end_comment

begin_class
specifier|public
class|class
name|SolrVocabulary
block|{
comment|/**      * Name of the unique ID field.      */
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_FIELD_NAME_ID
init|=
literal|"id"
decl_stmt|;
comment|/**      * Name of the field which holds the actual content.      */
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_FIELD_NAME_CONTENT
init|=
literal|"content"
decl_stmt|;
comment|/**      * Name of the field which holds the mime type (content type) of the content.      */
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_FIELD_NAME_MIMETYPE
init|=
literal|"mimeType"
decl_stmt|;
comment|/**      * Ending characters for dynamic fields of {@link String} type.      */
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_DYNAMIC_FIELD_TEXT
init|=
literal|"_t"
decl_stmt|;
comment|/**      * Ending characters for dynamic fields of {@link Long} type.      */
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_DYNAMIC_FIELD_LONG
init|=
literal|"_l"
decl_stmt|;
comment|/**      * Ending characters for dynamic fields of {@link Double} type.      */
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_DYNAMIC_FIELD_DOUBLE
init|=
literal|"_d"
decl_stmt|;
comment|/**      * Ending characters for dynamic fields of {@link Date} type.      */
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_DYNAMIC_FIELD_DATE
init|=
literal|"_dt"
decl_stmt|;
comment|/**      * "OR" keyword for Solr queries.      */
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_OR
init|=
literal|" OR "
decl_stmt|;
block|}
end_class

end_unit

