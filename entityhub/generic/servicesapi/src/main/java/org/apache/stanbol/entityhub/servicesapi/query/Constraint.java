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

begin_comment
comment|/**  * Abstract base class for all types of Constraints.  *   * @author Rupert Westenthaler  *   */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|Constraint
block|{
specifier|private
specifier|static
name|Double
name|ONE
init|=
name|Double
operator|.
name|valueOf
argument_list|(
literal|1.0
argument_list|)
decl_stmt|;
comment|/**      * The boost allows to define the relative importance of a constraint for      * ranking of the query results. Values MUST BE<code>&gt;= 0</code>      */
specifier|private
name|Double
name|boost
init|=
name|ONE
decl_stmt|;
comment|/**      * Defines the enumeration of available Constraints. TODO Maybe we need a more "extensible" way to define      * different constraints in future      *       * @author Rupert Westenthaler      *       */
specifier|public
enum|enum
name|ConstraintType
block|{
comment|// NOTE (2010-Nov-09,rw) Because a reference constraint is now a special
comment|// kind of
comment|// a value constraint.
comment|// /**
comment|// * Constraints a field to have a specific reference
comment|// */
comment|// reference,
comment|/**          * Constraints the value and possible the dataType          */
name|value
block|,
comment|/**          * Constraints a field to have a value within a range          */
name|range
block|,
comment|/**          * Constraints a field to have a lexical value          */
name|text
block|,
comment|/**          * Constraints a field to have a lexical value along with statistics to be able to compute a          * similarity metric (e.g. using the MoreLikeThis Solr handler)          */
name|similarity
block|}
specifier|private
specifier|final
name|ConstraintType
name|type
decl_stmt|;
specifier|protected
name|Constraint
parameter_list|(
name|ConstraintType
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The ConstraintType MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
comment|/**      * Getter for the type of the constraint.      *       * @return The type of the constraint      */
specifier|public
specifier|final
name|ConstraintType
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
comment|/**      * The boost      * @return the boost or<code>null</code> if not set      */
specifier|public
specifier|final
name|Double
name|getBoost
parameter_list|()
block|{
return|return
name|boost
return|;
block|}
comment|/**      * Setter for the boost.      * @param boost the boost or<code>null</code> to unset the boost      * @throws IllegalArgumentException it the boost is lower than zero      */
specifier|public
specifier|final
name|void
name|setBoost
parameter_list|(
name|Double
name|boost
parameter_list|)
block|{
if|if
condition|(
name|boost
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|boost
operator|=
literal|null
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|boost
operator|<=
literal|0f
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Boost '{}' MUST NOT be<= zero!"
argument_list|)
throw|;
block|}
else|else
block|{
name|this
operator|.
name|boost
operator|=
name|boost
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"Constraint [type :: %s][class :: %s]"
argument_list|,
name|type
argument_list|,
name|getClass
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

