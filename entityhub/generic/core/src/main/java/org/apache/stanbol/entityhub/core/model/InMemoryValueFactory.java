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
name|core
operator|.
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
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
name|Reference
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
name|Text
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
name|ValueFactory
import|;
end_import

begin_class
specifier|public
class|class
name|InMemoryValueFactory
implements|implements
name|ValueFactory
block|{
specifier|private
specifier|static
name|InMemoryValueFactory
name|instance
decl_stmt|;
specifier|public
specifier|static
name|InMemoryValueFactory
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|instance
operator|==
literal|null
condition|)
block|{
name|instance
operator|=
operator|new
name|InMemoryValueFactory
argument_list|()
expr_stmt|;
block|}
return|return
name|instance
return|;
block|}
specifier|protected
name|InMemoryValueFactory
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Reference
name|createReference
parameter_list|(
name|Object
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed value MUST NOT be NULL"
argument_list|)
throw|;
block|}
return|return
operator|new
name|ReferenceImpl
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Text
name|createText
parameter_list|(
name|Object
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed value MUST NOT be NULL"
argument_list|)
throw|;
block|}
return|return
name|createText
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Text
name|createText
parameter_list|(
name|String
name|text
parameter_list|,
name|String
name|language
parameter_list|)
block|{
return|return
operator|new
name|TextImpl
argument_list|(
name|text
argument_list|,
name|language
argument_list|)
return|;
block|}
specifier|protected
specifier|static
class|class
name|ReferenceImpl
implements|implements
name|Reference
implements|,
name|Serializable
implements|,
name|Cloneable
block|{
comment|/**          * serialVersionUID          */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|2571082550530948667L
decl_stmt|;
specifier|private
specifier|final
name|String
name|value
decl_stmt|;
specifier|protected
name|ReferenceImpl
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The value of the reference MUST NOT be NULL"
argument_list|)
throw|;
block|}
if|if
condition|(
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The value of the reference MUST NOT be empty"
argument_list|)
throw|;
block|}
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
specifier|public
specifier|final
name|String
name|getReference
parameter_list|()
block|{
return|return
name|value
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
name|value
operator|.
name|hashCode
argument_list|()
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
name|ReferenceImpl
operator|&&
operator|(
operator|(
name|ReferenceImpl
operator|)
name|obj
operator|)
operator|.
name|value
operator|.
name|equals
argument_list|(
name|value
argument_list|)
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
name|value
return|;
block|}
annotation|@
name|Override
specifier|public
name|ReferenceImpl
name|clone
parameter_list|()
throws|throws
name|CloneNotSupportedException
block|{
return|return
operator|new
name|ReferenceImpl
argument_list|(
name|value
argument_list|)
return|;
block|}
block|}
specifier|protected
specifier|static
class|class
name|TextImpl
implements|implements
name|Text
implements|,
name|Serializable
implements|,
name|Cloneable
block|{
comment|/**          * serialVersionUID          */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|5646936810374934435L
decl_stmt|;
specifier|private
specifier|final
name|String
name|value
decl_stmt|;
specifier|private
specifier|final
name|String
name|language
decl_stmt|;
specifier|protected
name|TextImpl
parameter_list|(
name|String
name|value
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|this
argument_list|(
name|value
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|TextImpl
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|language
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|super
argument_list|()
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The value of the Text MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
name|this
operator|.
name|language
operator|=
name|language
expr_stmt|;
block|}
specifier|public
specifier|final
name|String
name|getText
parameter_list|()
block|{
return|return
name|value
return|;
block|}
specifier|public
specifier|final
name|String
name|getLanguage
parameter_list|()
block|{
return|return
name|language
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
name|value
operator|.
name|hashCode
argument_list|()
operator|+
operator|(
name|language
operator|!=
literal|null
condition|?
name|language
operator|.
name|hashCode
argument_list|()
else|:
literal|0
operator|)
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
name|value
operator|+
operator|(
name|language
operator|!=
literal|null
operator|&&
operator|!
name|language
operator|.
name|isEmpty
argument_list|()
condition|?
operator|(
literal|'@'
operator|+
name|language
operator|)
else|:
literal|""
operator|)
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
if|if
condition|(
name|obj
operator|instanceof
name|TextImpl
operator|&&
operator|(
operator|(
name|TextImpl
operator|)
name|obj
operator|)
operator|.
name|value
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
block|{
if|if
condition|(
operator|(
operator|(
name|TextImpl
operator|)
name|obj
operator|)
operator|.
name|language
operator|==
literal|null
condition|)
block|{
return|return
name|language
operator|==
literal|null
return|;
block|}
else|else
block|{
return|return
operator|(
operator|(
name|TextImpl
operator|)
name|obj
operator|)
operator|.
name|language
operator|.
name|equals
argument_list|(
name|language
argument_list|)
return|;
block|}
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|Object
name|clone
parameter_list|()
throws|throws
name|CloneNotSupportedException
block|{
return|return
operator|new
name|TextImpl
argument_list|(
name|value
argument_list|,
name|language
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Representation
name|createRepresentation
parameter_list|(
name|String
name|id
parameter_list|)
block|{
if|if
condition|(
name|id
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed id MUST NOT be NULL!"
argument_list|)
throw|;
block|}
elseif|else
if|if
condition|(
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed id MUST NOT be empty!"
argument_list|)
throw|;
block|}
else|else
block|{
return|return
operator|new
name|InMemoryRepresentation
argument_list|(
name|id
argument_list|)
return|;
block|}
block|}
comment|//    @Override
comment|//    public Object createValue(String dataTypeUri, Object value) throws UnsupportedTypeException, UnsupportedDataTypeException {
comment|//
comment|//        return null;
comment|//    }
block|}
end_class

end_unit

