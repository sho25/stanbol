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
name|servicesapi
operator|.
name|site
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|servicesapi
operator|.
name|query
operator|.
name|QueryResultList
import|;
end_import

begin_comment
comment|/**  * Interface used to provide service/technology specific implementation of the  * search interface provided by {@link Site}.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntitySearcher
block|{
comment|/**      * The key used to define the baseUri of the query service used for the      * implementation of this interface.<br>      * This constants actually uses the value of {@link SiteConfiguration#QUERY_URI}      */
name|String
name|QUERY_URI
init|=
name|ReferencedSiteConfiguration
operator|.
name|QUERY_URI
decl_stmt|;
comment|/**      * Searches for Entities based on the parsed {@link FieldQuery}      * @param query the query      * @return the result of the query      */
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|findEntities
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Searches for Entities based on the parsed {@link FieldQuery} and returns      * for each entity an Representation over the selected fields and values      * @param query the query      * @return the found entities as representation containing only the selected      * fields and there values.      */
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|find
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

