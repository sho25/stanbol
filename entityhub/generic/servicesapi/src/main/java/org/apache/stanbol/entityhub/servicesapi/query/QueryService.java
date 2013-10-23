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
name|query
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
name|java
operator|.
name|util
operator|.
name|Collection
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
name|model
operator|.
name|Entity
import|;
end_import

begin_comment
comment|/**  * This interface defines the Java API for searching Entities.  * @author Rupert Westenthaler  * TODO Currently unused  */
end_comment

begin_interface
specifier|public
interface|interface
name|QueryService
block|{
comment|/**      * Searches for entities based on the parsed {@link FieldQuery} and returns      * the references (ids). Note that selected fields of the query are ignored.      * @param query the query      * @return the references of the found entities      * @throws IOException If the referenced Site is not accessible      * @throws UnsupportedQueryTypeException if the type of the parsed query is      * not supported by this query service. Or in other words if the value of      * {@link Query#getQueryType()} is not part of the collections returned by      * {@link QueryService#getSupportedQueryTypes()}.      */
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|findReferences
parameter_list|(
name|Query
name|query
parameter_list|)
throws|throws
name|IOException
throws|,
name|UnsupportedQueryTypeException
function_decl|;
comment|/**      * Searches for entities based on the parsed {@link FieldQuery} and returns      * representations as defined by the selected fields of the query. Note that      * if the query defines also {@link Constraint}s for selected fields, that      * the returned representation will only contain values selected by such      * constraints.      * @param query the query      * @return the found entities as representation containing only the selected      * fields and there values.      * @throws IOException If the referenced Site is not accessible      * @throws UnsupportedQueryTypeException if the type of the parsed query is      * not supported by this query service. Or in other words if the value of      * {@link Query#getQueryType()} is not part of the collections returned by      * {@link QueryService#getSupportedQueryTypes()}.      */
name|QueryResultList
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|find
parameter_list|(
name|Query
name|query
parameter_list|)
throws|throws
name|IOException
throws|,
name|UnsupportedQueryTypeException
function_decl|;
comment|/**      * Searches for Signs based on the parsed {@link FieldQuery} and returns      * the selected Signs including the whole representation. Note that selected      * fields of the query are ignored.      * @param query the query      * @return All Entities selected by the Query.      * @throws IOException If the referenced Site is not accessible      * @throws UnsupportedQueryTypeException if the type of the parsed query is      * not supported by this query service. Or in other words if the value of      * {@link Query#getQueryType()} is not part of the collections returned by      * {@link QueryService#getSupportedQueryTypes()}.      */
name|QueryResultList
argument_list|<
name|?
extends|extends
name|Entity
argument_list|>
name|findSigns
parameter_list|(
name|Query
name|query
parameter_list|)
throws|throws
name|IOException
throws|,
name|UnsupportedQueryTypeException
function_decl|;
comment|/**      * Getter for the types of queries supported by this implementation.      * {@link Query#getQueryType()} is used to check if a query is supported.      * @return the ids of the supported query types.      */
name|Collection
argument_list|<
name|String
argument_list|>
name|getSupportedQueryTypes
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

