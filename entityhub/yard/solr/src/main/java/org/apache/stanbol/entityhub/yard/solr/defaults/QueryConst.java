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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|defaults
package|;
end_package

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
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYard
import|;
end_import

begin_comment
comment|/**  * Defines parameters used by the {@link FieldQuery} implementation of the  * SolrYard. Some of those might also be supported by the {@link SolrYard}  * configuration to set default values<p>  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|QueryConst
block|{
specifier|private
name|QueryConst
parameter_list|()
block|{
comment|/*do not allow instances*/
block|}
comment|/**      * Property allowing to enable/disable the generation of Phrase queries for      * otional query terms (without wildcards). Values are expected to be      * {@link Boolean}      */
specifier|public
specifier|static
specifier|final
name|String
name|PHRASE_QUERY_STATE
init|=
literal|"stanbol.entityhub.yard.solr.query.phraseQuery"
decl_stmt|;
comment|/**      * The default state for the {@link #PHRASE_QUERY_STATE} (default: false)      */
specifier|public
specifier|static
specifier|final
name|Boolean
name|DEFAULT_PHRASE_QUERY_STATE
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
comment|/**      * Property allowing to set a query time boost for certain query terms.      * Values are expected to be floating point values grater than zero.      */
specifier|public
specifier|static
specifier|final
name|String
name|QUERY_BOOST
init|=
literal|"stanbol.entityhub.yard.solr.query.boost"
decl_stmt|;
block|}
end_class

end_unit

