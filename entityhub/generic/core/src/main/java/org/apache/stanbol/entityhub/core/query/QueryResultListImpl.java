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
name|query
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|Iterator
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
name|List
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
name|query
operator|.
name|FieldQuery
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
name|query
operator|.
name|QueryResultList
import|;
end_import

begin_class
specifier|public
class|class
name|QueryResultListImpl
parameter_list|<
name|T
parameter_list|>
implements|implements
name|Iterable
argument_list|<
name|T
argument_list|>
implements|,
name|QueryResultList
argument_list|<
name|T
argument_list|>
block|{
specifier|private
specifier|final
name|Collection
argument_list|<
name|T
argument_list|>
name|results
decl_stmt|;
specifier|private
specifier|final
name|FieldQuery
name|query
decl_stmt|;
specifier|private
name|Class
argument_list|<
name|T
argument_list|>
name|type
decl_stmt|;
comment|/**      * Constructs an QueryResultList by iterating over all elements in the parsed      * {@link Iterator} and storing all elements that are NOT<code>null</code>.      * @param query The query uses to select the results      * @param resultIterator The Iterator containing the results of the Query      * @throws IllegalArgumentException if the parsed {@link FieldQuery} is<code>null</code>      */
specifier|public
name|QueryResultListImpl
parameter_list|(
name|FieldQuery
name|query
parameter_list|,
name|Iterator
argument_list|<
name|T
argument_list|>
name|resultIterator
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
comment|//        if(query == null){
comment|//            throw new IllegalArgumentException("Query MUST NOT be NULL");
comment|//        }
name|this
operator|.
name|query
operator|=
name|query
expr_stmt|;
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
literal|"The type of the results MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
if|if
condition|(
name|resultIterator
operator|==
literal|null
operator|||
operator|!
name|resultIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|this
operator|.
name|results
operator|=
name|Collections
operator|.
name|emptyList
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|List
argument_list|<
name|T
argument_list|>
name|resultList
init|=
operator|new
name|ArrayList
argument_list|<
name|T
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|resultIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|resultList
operator|.
name|add
argument_list|(
name|resultIterator
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|results
operator|=
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|resultList
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|Class
argument_list|<
name|T
argument_list|>
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
comment|/**      * Constructs an QueryResultList with the parsed Query and Results      * @param query The query uses to select the results      * @param results The results of the query      * @throws IllegalArgumentException if the parsed {@link FieldQuery} is<code>null</code>      */
specifier|public
name|QueryResultListImpl
parameter_list|(
name|FieldQuery
name|query
parameter_list|,
name|Collection
argument_list|<
name|T
argument_list|>
name|results
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
comment|//        if(query == null){
comment|//            throw new IllegalArgumentException("Query MUST NOT be NULL");
comment|//        }
name|this
operator|.
name|query
operator|=
name|query
expr_stmt|;
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
literal|"The type of the results MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
if|if
condition|(
name|results
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|results
operator|=
name|Collections
operator|.
name|emptyList
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|results
operator|=
name|Collections
operator|.
name|unmodifiableCollection
argument_list|(
name|results
argument_list|)
expr_stmt|;
block|}
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.entityhub.core.query.ResultList#getQuery()      */
annotation|@
name|Override
specifier|public
specifier|final
name|FieldQuery
name|getQuery
parameter_list|()
block|{
return|return
name|query
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.entityhub.core.query.ResultList#getSelectedFields()      */
annotation|@
name|Override
specifier|public
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|getSelectedFields
parameter_list|()
block|{
return|return
name|query
operator|.
name|getSelectedFields
argument_list|()
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.entityhub.core.query.ResultList#iterator()      */
annotation|@
name|Override
specifier|public
specifier|final
name|Iterator
argument_list|<
name|T
argument_list|>
name|iterator
parameter_list|()
block|{
return|return
name|results
operator|.
name|iterator
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|T
argument_list|>
name|results
parameter_list|()
block|{
return|return
name|results
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.entityhub.core.query.ResultList#isEmpty()      */
specifier|public
specifier|final
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|results
operator|.
name|isEmpty
argument_list|()
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.entityhub.core.query.ResultList#size()      */
specifier|public
specifier|final
name|int
name|size
parameter_list|()
block|{
return|return
name|results
operator|.
name|size
argument_list|()
return|;
comment|//not supported :(
block|}
block|}
end_class

end_unit

