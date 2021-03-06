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
name|enhancer
operator|.
name|engines
operator|.
name|entitycomention
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
import|;
end_import

begin_interface
specifier|public
interface|interface
name|CoMentionConstants
block|{
comment|/**      * The {@link EntityLinkerConfig#NAME_FIELD} uri internally used by the      * {@link EntityCoMentionEngine}.      */
name|IRI
name|CO_MENTION_LABEL_FIELD
init|=
operator|new
name|IRI
argument_list|(
literal|"urn:org.stanbol:enhander.engine.entitycomention:co-mention-label"
argument_list|)
decl_stmt|;
comment|/**      * The {@link EntityLinkerConfig#TYPE_FIELD} uri internally used by the      * {@link EntityCoMentionEngine}.      */
name|IRI
name|CO_MENTION_TYPE_FIELD
init|=
operator|new
name|IRI
argument_list|(
literal|"urn:org.stanbol:enhander.engine.entitycomention:co-mention-type"
argument_list|)
decl_stmt|;
block|}
end_interface

end_unit

