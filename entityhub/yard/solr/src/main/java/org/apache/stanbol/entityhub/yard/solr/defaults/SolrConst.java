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
name|defaults
package|;
end_package

begin_comment
comment|/**  * Defines the defaults used to encode the fields for the index.<p>  * The configuration provided by this class MUST be in agreement with the  * schema.xml configured for the Solr Server  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|SolrConst
block|{
specifier|private
name|SolrConst
parameter_list|()
block|{}
comment|/**      * Char used to mark special fields. Special fields are internally used      * fields that do not represent a value that was present in the original      * resource. They are used to store configurations, to collect values of      * different fields (e.g. labels with different languages)      */
specifier|public
specifier|static
specifier|final
name|char
name|SPECIAL_FIELD_PREFIX
init|=
literal|'_'
decl_stmt|;
comment|/**      * The Char used to separate prefix, path elements and suffix.      * Currently there is no support for escaping path elements. Therefore      * there is only the possibility to use '/' or '#' because they do not      * appear in prefixes or suffixes and are replaced by prefixes for the      * path elements.      */
specifier|public
specifier|static
specifier|final
name|char
name|PATH_SEPERATOR
init|=
literal|'/'
decl_stmt|;
comment|/**      * All fields indicating a language start with this character.      * Special fields that indicate a language start with the      * {@link #SPECIAL_FIELD_PREFIX} followed by this one.<p>      * Examples:<ul>      *<li>@en ... for a field storing English text      *<li>@ ... for a field storing text without a language      *           (meaning that this text is valid in any language)      *<li>_!@ ... for a field that index all labels in any language. This field      *    uses the {@link #SPECIAL_FIELD_PREFIX},{@link #MERGER_INDICATOR} and      *    the {@link #LANG_INDICATOR}      *</ul>      */
specifier|public
specifier|static
specifier|final
name|char
name|LANG_INDICATOR
init|=
literal|'@'
decl_stmt|;
comment|/**      * Merger Fields are fields that collect different values already indexed      * in some other fields. This fields are usually configured as      *<code>store=false</code> and<code>multiValue=true</code> in the index      * and are used for queries.<p>      * The most used merger field is the {@link #LANG_MERGER_FIELD} that contains      * all natural language values of all languages!      */
specifier|public
specifier|static
specifier|final
name|char
name|MERGER_INDICATOR
init|=
literal|'!'
decl_stmt|;
comment|/**      * Field that stores all natural language text values of a field -      * regardless of the language of the text.      */
specifier|public
specifier|static
specifier|final
name|String
name|LANG_MERGER_FIELD
init|=
literal|""
operator|+
name|SPECIAL_FIELD_PREFIX
operator|+
name|MERGER_INDICATOR
operator|+
name|LANG_INDICATOR
decl_stmt|;
comment|/**      * The name of the field used to store the unique id of the Documents (usually      * the URI of the resource)      */
specifier|public
specifier|static
specifier|final
name|String
name|DOCUMENT_ID_FIELD
init|=
literal|"uri"
decl_stmt|;
comment|/**      * This is used as field name to store all URIs a document      * refers to. If a document is deleted from the index, than all other documents      * that refer to this URI need to be updated      */
specifier|public
specifier|static
specifier|final
name|String
name|REFERRED_DOCUMENT_FIELD
init|=
name|SPECIAL_FIELD_PREFIX
operator|+
literal|"ref"
decl_stmt|;
comment|/**      * This is used as field name to store all URIs a document uses in one of      * it's paths.<p> If a document in the index is changed, than all documents      * that are dependent on this one need to be updated.      */
specifier|public
specifier|static
specifier|final
name|String
name|DEPENDENT_DOCUMENT_FIELD
init|=
name|SPECIAL_FIELD_PREFIX
operator|+
literal|"dep"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SPECIAL_CONFIG_FIELD
init|=
name|SPECIAL_FIELD_PREFIX
operator|+
literal|"config"
decl_stmt|;
comment|/**      * The name of the field that indicates the domain of a document.<p>      * Within the schema.xml this field is usually configures as      *<code>multiValued=false stored=false indexed=true</code>.      * NOTE: that the two domains sharing the same SolrIndex MUST NOT add      * Documents with the same ID (equal values for {@link #DOCUMENT_ID_FIELD})      */
specifier|public
specifier|static
specifier|final
name|String
name|DOMAIN_FIELD
init|=
name|SPECIAL_FIELD_PREFIX
operator|+
literal|"domain"
decl_stmt|;
comment|/**      * The field name used by Solr for the score of query results      */
specifier|public
specifier|static
specifier|final
name|String
name|SCORE_FIELD
init|=
literal|"score"
decl_stmt|;
block|}
end_class

end_unit

