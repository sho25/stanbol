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
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrServer
import|;
end_import

begin_comment
comment|/**  * The main function of this provider interface is to allow, that moving the  * support for an embedded {@link SolrServer} can be realised by an own bundle.<p>  * The reason for that is, that this requires to include a lot of dependencies  * to the Solr core and all the Lucene libraries one might not need in most of  * the useage cases of the SolrYard because typically one might want to run the  * SolrServer in an own virtual machine or even on an own server.<p>  * However for some usage scenarios and especially for testing it is very  * useful to have the possibility to use Solr as embedded service.  *   * @author Rupert Westenthaler  */
end_comment

begin_interface
annotation|@
name|Service
specifier|public
interface|interface
name|SolrServerProvider
block|{
comment|/**      * SolrServer types defined here to avoid java dependencies to the according      * java classes      * @author Rupert Westenthaler      *      */
specifier|public
specifier|static
enum|enum
name|Type
block|{
comment|/**          * Uses an embedded SolrServer that runs within the same virtual machine          */
name|EMBEDDED
block|,
comment|/**          * The default type that can be used for query and updates          */
name|HTTP
block|,
comment|/**          * This server is preferable used for updates          */
name|STREAMING
block|,
comment|/**          * This allows to use load balancing on multiple SolrServers via a round          * robin algorithm.          */
name|LOAD_BALANCE
block|}
comment|/**      * Getter for the supported types of this Provider      * @return the Types supported by this Provider      */
name|Set
argument_list|<
name|Type
argument_list|>
name|supportedTypes
parameter_list|()
function_decl|;
comment|/**      * Getter for the {@link SolrServer} instance for the provided URI or path      * (in case of an embedded server)      * @param type The type of the requested SolrServer instance or<code>null</code>      * for the default type      * @param uriOrPath the URI (in case of an remote SolrServer that is accessed      * via RESTfull services) or the Path (in case of an embedded SolrServer)      * @param additional This allows to parse additional SolrServers. This may be      * ignored if the requested type does not support the usage of multiple      * servers.      * @return the configured SolrServer client for the parsed parameter      * @throws NullPointerException       * @throws IllegalArgumentException if<code>null</code> is parsed as uriOrPath      * or if the parsed URI or path is not valid for the requested {@link Type}       * or the parsed type is not supported by this provider      */
name|SolrServer
name|getSolrServer
parameter_list|(
name|Type
name|type
parameter_list|,
name|String
name|uriOrPath
parameter_list|,
name|String
modifier|...
name|additional
parameter_list|)
throws|throws
name|IllegalArgumentException
function_decl|;
block|}
end_interface

end_unit

