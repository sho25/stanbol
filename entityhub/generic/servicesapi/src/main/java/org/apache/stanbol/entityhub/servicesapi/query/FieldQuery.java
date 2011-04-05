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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
import|;
end_import

begin_comment
comment|/**  * Simple query interface that allows to search for representations based on  * fields and there values.<p>  * Currently it is only possible to set a single constraint per field. Therefore  * it is not possible to combine an range constraint with an language constraint.  * e.g. searching for all labels form a-f in a list of given languages.  * TODO: This shortcoming needs to be reevaluated. The intension was to ease the  * implementation and the usage of this interface.  * TODO: Implementation need to be able to throw UnsupportedConstraintExceptions  *       for specific combinations of Constraints e.g. Regex or case insensitive ...  * TODO: Would be nice if an Implementation could also announce the list of supported  *       constraints (e.g. via Capability levels ...)  * @author Rupert Westenthaler  */
end_comment

begin_interface
specifier|public
interface|interface
name|FieldQuery
extends|extends
name|Query
extends|,
name|Iterable
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|Constraint
argument_list|>
argument_list|>
block|{
comment|/**      * The value used as result for {@link Query#getQueryType()} of this query      * type.      */
name|String
name|TYPE
init|=
literal|"fieldQuery"
decl_stmt|;
comment|/**      * Adds Fields to be selected by this Query      * @param fields the fields to be selected by this query      */
name|void
name|addSelectedField
parameter_list|(
name|String
name|field
parameter_list|)
function_decl|;
comment|/**      * Adds Fields to be selected by this Query      * @param fields the fields to be selected by this query      */
name|void
name|addSelectedFields
parameter_list|(
name|Collection
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|)
function_decl|;
comment|/**      * Removes Fields to be selected by this Query      * @param fields the fields to be selected by this query      */
name|void
name|removeSelectedField
parameter_list|(
name|String
name|fields
parameter_list|)
function_decl|;
comment|/**      * Removes Fields to be selected by this Query      * @param fields the fields to be selected by this query      */
name|void
name|removeSelectedFields
parameter_list|(
name|Collection
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|)
function_decl|;
comment|/**      * Unmodifiable set with all the fields to be selected by this query      * @return the fields to be selected by this query      */
name|Set
argument_list|<
name|String
argument_list|>
name|getSelectedFields
parameter_list|()
function_decl|;
comment|/**      * Sets/replaces the constraint for a field of the representation. If      *<code>null</code> is parsed as constraint this method removes any existing      * constraint for the field      * @param field the field      * @param constraint the Constraint      */
name|void
name|setConstraint
parameter_list|(
name|String
name|field
parameter_list|,
name|Constraint
name|constraint
parameter_list|)
function_decl|;
comment|/**      * Removes the constraint for the parse field      * @param field      */
name|void
name|removeConstraint
parameter_list|(
name|String
name|field
parameter_list|)
function_decl|;
comment|/**      * Checks if there is a constraint for the given field      * @param field the field      * @return the state      */
name|boolean
name|isConstraint
parameter_list|(
name|String
name|field
parameter_list|)
function_decl|;
comment|/**      * Getter for the Constraint of a field      * @param field the field      * @return the constraint or<code>null</code> if none is defined.      */
name|Constraint
name|getConstraint
parameter_list|(
name|String
name|field
parameter_list|)
function_decl|;
comment|/**      * Getter for the unmodifiable list of query elements for the given Path. Use      * the add/remove constraint methods to change query elements for an path      * @param path the path      * @return the list of query elements for a path      */
name|Set
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|Constraint
argument_list|>
argument_list|>
name|getConstraints
parameter_list|()
function_decl|;
comment|/**      * Removes all constraints form the query      */
name|void
name|removeAllConstraints
parameter_list|()
function_decl|;
comment|/**      * Removes all selected fields      */
name|void
name|removeAllSelectedFields
parameter_list|()
function_decl|;
comment|/**      * Copies the state of this instance to the parsed one      * @param<T> the {@link FieldQuery} implementation      * @param copyTo the instance to copy the state      * @return the parsed instance with the exact same state as this one      */
parameter_list|<
name|T
extends|extends
name|FieldQuery
parameter_list|>
name|T
name|copyTo
parameter_list|(
name|T
name|copyTo
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

