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
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedHashSet
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
name|defaults
operator|.
name|DataTypeEnum
import|;
end_import

begin_class
specifier|public
class|class
name|ReferenceConstraint
extends|extends
name|ValueConstraint
block|{
comment|/**      * The references. Same as returned by {@link ValueConstraint#getValues()}      * but in a Set of generic type string      */
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|references
decl_stmt|;
specifier|public
name|ReferenceConstraint
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|this
argument_list|(
name|reference
operator|!=
literal|null
condition|?
name|Collections
operator|.
name|singleton
argument_list|(
name|reference
argument_list|)
else|:
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ReferenceConstraint
parameter_list|(
name|Collection
argument_list|<
name|String
argument_list|>
name|references
parameter_list|)
block|{
name|this
argument_list|(
name|references
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ReferenceConstraint
parameter_list|(
name|Collection
argument_list|<
name|String
argument_list|>
name|references
parameter_list|,
name|MODE
name|mode
parameter_list|)
block|{
name|super
argument_list|(
name|references
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|DataTypeEnum
operator|.
name|Reference
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|,
name|mode
argument_list|)
expr_stmt|;
if|if
condition|(
name|references
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parsed Reference(s) MUST NOT be NULL"
argument_list|)
throw|;
block|}
comment|//we need to copy the values over, because in Java one can not cast
comment|//Set<Object> to Set<String>
name|Set
argument_list|<
name|String
argument_list|>
name|r
init|=
operator|new
name|LinkedHashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|getValues
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Object
name|value
range|:
name|getValues
argument_list|()
control|)
block|{
name|r
operator|.
name|add
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|references
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
comment|//    /**
comment|//     * Getter for the first parsed Reference
comment|//     * @return the reference
comment|//     */
comment|//    public String getReference() {
comment|//        return (String)getValue();
comment|//    }
comment|/**      * Getter for the Reference      * @return the reference      */
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getReferences
parameter_list|()
block|{
return|return
name|references
return|;
block|}
block|}
end_class

end_unit

