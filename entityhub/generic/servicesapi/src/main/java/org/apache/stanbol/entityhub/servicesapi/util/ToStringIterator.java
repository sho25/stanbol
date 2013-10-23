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
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_comment
comment|/**  * Implementation of an Iterator over {@link String} values, that uses the   * {@link Object#toString()} on elements of the parent Iterator for the   * conversion.<p>  * This Implementation does not use {@link AdaptingIterator}s implementation,   * because the {@link Object#toString()} method can be used to create a string  * representation for every object and therefore there is no need for the  * filtering functionality provided by the {@link AdaptingIterator}.  *   * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|ToStringIterator
implements|implements
name|Iterator
argument_list|<
name|String
argument_list|>
block|{
specifier|private
specifier|final
name|Iterator
argument_list|<
name|?
argument_list|>
name|it
decl_stmt|;
comment|/**      * Creates an string iterator over parsed parent      * @param it the parent iterator      * @throws NullPointerException if<code>null</code> is parsed as parent      * iterator      */
specifier|public
name|ToStringIterator
parameter_list|(
name|Iterator
argument_list|<
name|?
argument_list|>
name|it
parameter_list|)
throws|throws
name|NullPointerException
block|{
if|if
condition|(
name|it
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parsed iterator MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|it
operator|=
name|it
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|void
name|remove
parameter_list|()
block|{
name|it
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|next
parameter_list|()
block|{
name|Object
name|next
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
return|return
name|next
operator|!=
literal|null
condition|?
name|next
operator|.
name|toString
argument_list|()
else|:
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|it
operator|.
name|hasNext
argument_list|()
return|;
block|}
block|}
end_class

end_unit

