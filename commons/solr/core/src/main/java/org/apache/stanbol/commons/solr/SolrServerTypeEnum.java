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
name|commons
operator|.
name|solr
package|;
end_package

begin_comment
comment|/**  * SolrServer types defined here to avoid java dependencies to the according java classes  *   * @author Rupert Westenthaler  *   */
end_comment

begin_enum
specifier|public
enum|enum
name|SolrServerTypeEnum
block|{
comment|/**      * Uses an embedded SolrServer that runs within the same virtual machine      */
name|EMBEDDED
block|,
comment|/**      * The default type that can be used for query and updates      */
name|HTTP
block|,
comment|/**      * This server is preferable used for updates      */
name|STREAMING
block|,
comment|/**      * This allows to use load balancing on multiple SolrServers via a round robin algorithm.      */
name|LOAD_BALANCE
block|}
end_enum

end_unit

