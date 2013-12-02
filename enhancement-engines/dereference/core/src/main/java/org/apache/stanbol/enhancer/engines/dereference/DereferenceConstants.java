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
block|}
end_interface

end_unit

