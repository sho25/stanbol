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
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_interface
specifier|public
interface|interface
name|QueryResultList
parameter_list|<
name|T
parameter_list|>
extends|extends
name|Iterable
argument_list|<
name|T
argument_list|>
block|{
comment|/**      * Getter for the query of this result set.      * TODO: Change return Value to {@link Query}      * @return the query used to create this result set      */
name|FieldQuery
name|getQuery
parameter_list|()
function_decl|;
comment|/**      * The selected fields of this query      * @return      */
name|Set
argument_list|<
name|String
argument_list|>
name|getSelectedFields
parameter_list|()
function_decl|;
comment|/**      * Iterator over the results of this query      */
name|Iterator
argument_list|<
name|T
argument_list|>
name|iterator
parameter_list|()
function_decl|;
comment|/**      * Unmodifiable collection of the results      * @return the resutls      */
name|Collection
argument_list|<
name|?
extends|extends
name|T
argument_list|>
name|results
parameter_list|()
function_decl|;
comment|/**      *<code>true</code> if the result set is empty      * @return<code>true</code> if the result set is empty. Otherwise<code>false</code>      */
name|boolean
name|isEmpty
parameter_list|()
function_decl|;
comment|/**      * The size of this result set      * @return      */
name|int
name|size
parameter_list|()
function_decl|;
comment|/**      * The type of the results in the list      * @return the type      */
name|Class
argument_list|<
name|T
argument_list|>
name|getType
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

