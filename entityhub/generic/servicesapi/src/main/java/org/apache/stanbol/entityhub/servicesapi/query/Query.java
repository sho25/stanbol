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
operator|.
name|Site
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
name|yard
operator|.
name|Yard
import|;
end_import

begin_comment
comment|/**  * Common interfaces for all supported queries. The idea is that some query  * types MUST BE supported by the Entityhub. However some {@link Site} and  * {@link Yard} implementations may support additional query types.<p>  *  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Query
extends|extends
name|Cloneable
block|{
comment|/**      * The type of the Query (e.g. "fieldQuery" or "entityQuery"). Typically the      * type should be defines as String constant in the java interface of the      * query type.      * @return return the name of the query (usually the the value as      *     returned by the {@link Class#getSimpleName()} class object of the      *     Java Interface for the query type but with a      *     {@link Character#toLowerCase(char)} for the first character.      */
name|String
name|getQueryType
parameter_list|()
function_decl|;
comment|/**      * Getter for the maximum number of results for this query.      * @return the maximum number of results or<code>null</code> if no limit      *    is defined. MUST never return a number<code>&lt;= 1</code>      */
name|Integer
name|getLimit
parameter_list|()
function_decl|;
comment|/**      * Setter for the maximum number of results or<code>null</code> if no      * limit is defined.      * @param limit the limit as positive integer or<code>null</code> to define      *    that no limit is defined. Parsing a negative number results in setting      *    the limit to<code>null</code>.      */
name|void
name|setLimit
parameter_list|(
name|Integer
name|limit
parameter_list|)
function_decl|;
comment|/**      * Getter for the offset for the first result of this query.      * @return the offset for this query. MUST NOT return a value<code>&lt; 0</code>      */
name|int
name|getOffset
parameter_list|()
function_decl|;
comment|/**      * Setter for the offset of the query. Setting the offset to any value      *<code>&lt; 0</code> MUST result in setting the offset to<code>0</code>      * @param offset the offset, a positive natural number including<code>0</code>.      *    Parsing a negative number results to setting the offset to<code>0</code>      */
name|void
name|setOffset
parameter_list|(
name|int
name|offset
parameter_list|)
function_decl|;
comment|/**      * Clones the query.      * @return the clone      */
name|Query
name|clone
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

