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
name|yard
operator|.
name|solr
operator|.
name|model
operator|.
name|IndexDataType
import|;
end_import

begin_comment
comment|/**  * Constraint Types defined for IndexFields  *<p>  * This could be replaced by a more flexible way to register supported constraint types in future versions  *   * @author Rupert Westenthaler  */
end_comment

begin_enum
specifier|public
enum|enum
name|IndexConstraintTypeEnum
block|{
comment|/**      * constraints the DataType of values      *       * @see IndexDataType      */
name|DATATYPE
block|,
comment|/**      * Constraints the language of values      */
name|LANG
block|,
comment|/**      * Constraints the field      */
name|FIELD
block|,
comment|/**      * Constraints the Value      */
name|EQ
block|,
comment|/**      * REGEX based filter on values      */
name|REGEX
block|,
comment|/**      * Wildcard based filter on values      */
name|WILDCARD
block|,
comment|/**      * Greater than constraint      */
name|GT
block|,
comment|/**      * Lower than constraint      */
name|LT
block|,
comment|/**      * Greater else constraint      */
name|GE
block|,
comment|/**      * Lower else constraint      */
name|LE
block|, }
end_enum

end_unit

