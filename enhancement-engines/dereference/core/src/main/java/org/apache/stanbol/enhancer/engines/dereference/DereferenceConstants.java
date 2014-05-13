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
name|engines
operator|.
name|dereference
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Language
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|PlainLiteral
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Triple
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
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
import|;
end_import

begin_comment
comment|/**  * Define configuration parameters for Dereference engines  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|DereferenceConstants
block|{
comment|/**      * Property that allows to enable/disable the filtering of {@link Triple}s      * with {@link PlainLiteral} {@link Triple#getObject() objects} based on      * their {@link Language}. Languages that need to be dereferenced are      * parsed to the {@link EntityDereferencer} via the      * {@link DereferenceContext#getContentLanguages()}. If empty no languages      * MUST BE filtered.<p>      * If both this and {@link #FILTER_ACCEPT_LANGUAGES} are enabled the filter      * should use the union of the two sets available via       * {@link DereferenceContext#getLanguages()}.      */
name|String
name|FILTER_CONTENT_LANGUAGES
init|=
literal|"enhancer.engine.dereference.filterContentlanguages"
decl_stmt|;
comment|/**      * By default {@link #FILTER_CONTENT_LANGUAGES} is deactivated      */
name|boolean
name|DEFAULT_FILTER_CONTENT_LANGUAGES
init|=
literal|false
decl_stmt|;
comment|/**      * Property that allows to enable/disable the filtering of {@link Triple}s      * with {@link PlainLiteral} {@link Triple#getObject() objects} based on      * their {@link Language}. Languages that need to be dereferenced are      * parsed to the {@link EntityDereferencer} via the      * {@link DereferenceContext#getAcceptLanguages()}. If empty no languages      * MUST BE filtered.<p>      * If both this and {@link #FILTER_CONTENT_LANGUAGES} are enabled the filter      * should use the union of the two sets available via       * {@link DereferenceContext#getLanguages()}.      */
name|String
name|FILTER_ACCEPT_LANGUAGES
init|=
literal|"enhancer.engine.dereference.filterAcceptlanguages"
decl_stmt|;
comment|/**      * By default {@link #FILTER_ACCEPT_LANGUAGES} is activated      */
name|boolean
name|DEFAULT_FILTER_ACCEPT_LANGUAGES
init|=
literal|true
decl_stmt|;
comment|/**      * Property used to configure the properties linking entities. If not present      * the {@link Properties#ENHANCER_ENTITY_REFERENCE} will be used. If present      * this property is not automatically added.<p>      * @since 0.12.1 (<a href="https://issues.apache.org/jira/browse/STANBOL-1334">STANBOL-1334</a>)      */
name|String
name|ENTITY_REFERENCES
init|=
literal|"enhancer.engines.dereference.references"
decl_stmt|;
comment|/**      * Property used to configure the fields that should be dereferenced.<p>      * DereferenceEngines need to support a list of URIs but may also support more      * complex syntax (such as the Entityhub FiedMapping). However parsing a      * list of properties URIs MUST BE still valid.<p>      * Support for Namespace prefixes via the Stanbol Namespace Prefix Service      * is optional. If unknown prefixes are used or prefixes are not supported      * the Engine is expected to throw a       * {@link org.osgi.service.cm.ConfigurationException} during activation      */
name|String
name|DEREFERENCE_ENTITIES_FIELDS
init|=
literal|"enhancer.engines.dereference.fields"
decl_stmt|;
comment|/**      * Property used to configure LDPath statements. Those are applied using      * each referenced Entity as Context.<p>      * DereferenceEngines that can not support LDPath are expected to throw a      * {@link org.osgi.service.cm.ConfigurationException} if values are set      * for this property.      */
name|String
name|DEREFERENCE_ENTITIES_LDPATH
init|=
literal|"enhancer.engines.dereference.ldpath"
decl_stmt|;
comment|/** 	 * A URI prefix checked for entity URIs. Only entities that do match any of the 	 * parsed prefixes or {@link #URI_PATTERN} will be dereferenced. If no  	 * pattern nor prefixes are configured all entities will be dereferenced.  	 * This has lower priority as {@link #FALLBACK_MODE}. 	 * @see #FALLBACK_MODE 	 */
name|String
name|URI_PREFIX
init|=
literal|"enhancer.engines.dereference.uriPrefix"
decl_stmt|;
comment|/** 	 * Regex pattern applied to entity URIs. Only entities that do match any of 	 * the configured {@link #URI_PREFIX} or pattern will be dereferenced.  	 * If no pattern nor prefixes are configured all entities will be dereferenced. 	 * This has lower priority as {@link #FALLBACK_MODE}. 	 * @see #FALLBACK_MODE 	 */
name|String
name|URI_PATTERN
init|=
literal|"enhancer.engines.dereference.uriPattern"
decl_stmt|;
comment|/**      * If fallback mode is activated a dereference engine will not try to      * dereference entities for those there are already triples added to the      * enhancement results.      */
name|String
name|FALLBACK_MODE
init|=
literal|"enhancer.engines.dereference.fallback"
decl_stmt|;
comment|/**      * By default {@link #FALLBACK_MODE} is enabled      */
name|boolean
name|DEFAULT_FALLBACK_MODE
init|=
literal|true
decl_stmt|;
comment|/**      * Parameter allowing users to define a list of languages to be dereferenced.      * An empty string is used for literals that do not have an language tag.      */
name|String
name|DEREFERENCE_ENTITIES_LANGUAGES
init|=
literal|"enhancer.engines.dereference.languages"
decl_stmt|;
comment|/**      * Language key used for configuring literals without language tag      */
name|String
name|NO_LANGUAGE_KEY
init|=
literal|"none"
decl_stmt|;
block|}
end_interface

end_unit

