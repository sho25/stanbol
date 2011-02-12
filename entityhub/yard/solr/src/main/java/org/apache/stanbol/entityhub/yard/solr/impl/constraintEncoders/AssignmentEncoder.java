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
name|impl
operator|.
name|constraintEncoders
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|yard
operator|.
name|solr
operator|.
name|model
operator|.
name|IndexValue
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
name|yard
operator|.
name|solr
operator|.
name|model
operator|.
name|IndexValueFactory
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
name|yard
operator|.
name|solr
operator|.
name|query
operator|.
name|ConstraintTypePosition
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
name|yard
operator|.
name|solr
operator|.
name|query
operator|.
name|EncodedConstraintParts
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
name|yard
operator|.
name|solr
operator|.
name|query
operator|.
name|IndexConstraintTypeEncoder
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
name|yard
operator|.
name|solr
operator|.
name|query
operator|.
name|IndexConstraintTypeEnum
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
name|yard
operator|.
name|solr
operator|.
name|query
operator|.
name|ConstraintTypePosition
operator|.
name|PositionType
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
name|yard
operator|.
name|solr
operator|.
name|utils
operator|.
name|SolrUtil
import|;
end_import

begin_comment
comment|/**  * Encodes the Assignment of the field to an value. If a value is parsed, than  * it encodes that the field must be equals to this value.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|AssignmentEncoder
implements|implements
name|IndexConstraintTypeEncoder
argument_list|<
name|Object
argument_list|>
block|{
specifier|private
specifier|final
specifier|static
name|ConstraintTypePosition
name|POS
init|=
operator|new
name|ConstraintTypePosition
argument_list|(
name|PositionType
operator|.
name|assignment
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|String
name|EQ
init|=
literal|":"
decl_stmt|;
specifier|private
specifier|final
name|IndexValueFactory
name|indexValueFactory
decl_stmt|;
specifier|public
name|AssignmentEncoder
parameter_list|(
name|IndexValueFactory
name|indexValueFactory
parameter_list|)
block|{
if|if
condition|(
name|indexValueFactory
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The indexValueFactory MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|indexValueFactory
operator|=
name|indexValueFactory
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|encode
parameter_list|(
name|EncodedConstraintParts
name|constraint
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|IndexValue
name|indexValue
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|indexValue
operator|=
literal|null
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|IndexValue
condition|)
block|{
name|indexValue
operator|=
operator|(
name|IndexValue
operator|)
name|value
expr_stmt|;
block|}
else|else
block|{
name|indexValue
operator|=
name|indexValueFactory
operator|.
name|createIndexValue
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
name|String
name|eqConstraint
init|=
name|EQ
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|String
name|escapedValue
init|=
name|SolrUtil
operator|.
name|escapeSolrSpecialChars
argument_list|(
name|indexValue
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
comment|//now we need to replace spaces with '+' because only than the query
comment|//is treated as EQUALS by solr
name|eqConstraint
operator|=
name|EQ
operator|+
name|escapedValue
operator|.
name|replace
argument_list|(
literal|' '
argument_list|,
literal|'+'
argument_list|)
expr_stmt|;
block|}
name|constraint
operator|.
name|addEncoded
argument_list|(
name|POS
argument_list|,
name|eqConstraint
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|supportsDefault
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|IndexConstraintTypeEnum
argument_list|>
name|dependsOn
parameter_list|()
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
name|IndexConstraintTypeEnum
operator|.
name|FIELD
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|IndexConstraintTypeEnum
name|encodes
parameter_list|()
block|{
return|return
name|IndexConstraintTypeEnum
operator|.
name|EQ
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Object
argument_list|>
name|acceptsValueType
parameter_list|()
block|{
return|return
name|Object
operator|.
name|class
return|;
block|}
block|}
end_class

end_unit

