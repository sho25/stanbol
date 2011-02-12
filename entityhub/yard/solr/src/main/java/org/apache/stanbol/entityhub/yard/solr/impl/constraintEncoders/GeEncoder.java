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

begin_class
specifier|public
class|class
name|GeEncoder
implements|implements
name|IndexConstraintTypeEncoder
argument_list|<
name|Object
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|ConstraintTypePosition
name|POS
init|=
operator|new
name|ConstraintTypePosition
argument_list|(
name|PositionType
operator|.
name|value
argument_list|,
literal|1
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|DEFAULT
init|=
literal|"*"
decl_stmt|;
specifier|private
name|IndexValueFactory
name|indexValueFactory
decl_stmt|;
specifier|public
name|GeEncoder
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
literal|"The parsed IndexValueFactory MUST NOT be NULL!"
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
comment|//default value
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
name|geConstraint
init|=
name|String
operator|.
name|format
argument_list|(
literal|"[%s "
argument_list|,
name|indexValue
operator|!=
literal|null
operator|&&
name|indexValue
operator|.
name|getValue
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|indexValue
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
name|indexValue
operator|.
name|getValue
argument_list|()
else|:
name|DEFAULT
argument_list|)
decl_stmt|;
name|constraint
operator|.
name|addEncoded
argument_list|(
name|POS
argument_list|,
name|geConstraint
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
name|EQ
argument_list|,
name|IndexConstraintTypeEnum
operator|.
name|LE
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
name|GE
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

