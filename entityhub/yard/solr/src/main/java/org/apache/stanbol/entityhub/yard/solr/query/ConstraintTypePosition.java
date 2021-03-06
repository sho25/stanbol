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

begin_comment
comment|/**  * The position of a constraint type within the constraint for an index field.  *<p>  * This position consists of two parts  *<ol>  *<li>the {@link PositionType} defining if general position of the constraint  *<li>an integer that defines the ordering of constraints within one position. This is e.g. needed when  * encoding range constraints because both the lower and upper bound need to be encoded in the  * {@link PositionType#value} category, the lower bound need to be encoded in front of the upper bound.  *</ol>  *   * @author Rupert Westenthaler  *   */
end_comment

begin_class
specifier|public
class|class
name|ConstraintTypePosition
implements|implements
name|Comparable
argument_list|<
name|ConstraintTypePosition
argument_list|>
block|{
comment|/**      * The possible positions of constraints within a Index Constraint.      *<p>      * The ordinal number of the elements is used to sort the constraints in the      * {@link EncodedConstraintParts}. So ensure, that the ordering in this enumeration corresponds with the      * ordering in a constraint within the index      *       * @author Rupert Westenthaler      *       */
specifier|public
enum|enum
name|PositionType
block|{
name|prefix
block|,
name|field
block|,
name|suffux
block|,
name|assignment
block|,
name|value
block|}
specifier|private
name|PositionType
name|type
decl_stmt|;
specifier|private
name|int
name|pos
decl_stmt|;
specifier|public
name|ConstraintTypePosition
parameter_list|(
name|PositionType
name|type
parameter_list|)
block|{
name|this
argument_list|(
name|type
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ConstraintTypePosition
parameter_list|(
name|PositionType
name|type
parameter_list|,
name|int
name|pos
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
literal|"The ConstraintPosition MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
name|this
operator|.
name|pos
operator|=
name|pos
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|ConstraintTypePosition
name|other
parameter_list|)
block|{
return|return
name|type
operator|==
name|other
operator|.
name|type
condition|?
name|pos
operator|-
name|other
operator|.
name|pos
else|:
name|type
operator|.
name|ordinal
argument_list|()
operator|-
name|other
operator|.
name|type
operator|.
name|ordinal
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|type
operator|.
name|hashCode
argument_list|()
operator|+
name|pos
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|obj
operator|instanceof
name|ConstraintTypePosition
operator|&&
operator|(
operator|(
name|ConstraintTypePosition
operator|)
name|obj
operator|)
operator|.
name|type
operator|==
name|type
operator|&&
operator|(
operator|(
name|ConstraintTypePosition
operator|)
name|obj
operator|)
operator|.
name|pos
operator|==
name|pos
return|;
block|}
specifier|public
name|String
name|getPos
parameter_list|()
block|{
return|return
name|type
operator|.
name|ordinal
argument_list|()
operator|+
literal|"."
operator|+
name|pos
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|type
operator|.
name|name
argument_list|()
operator|+
literal|'.'
operator|+
name|pos
return|;
block|}
block|}
end_class

end_unit

