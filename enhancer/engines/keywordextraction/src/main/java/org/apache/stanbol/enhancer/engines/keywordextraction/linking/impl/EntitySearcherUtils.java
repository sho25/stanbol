begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|engines
operator|.
name|keywordextraction
operator|.
name|linking
operator|.
name|impl
package|;
end_package

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
name|enhancer
operator|.
name|engines
operator|.
name|keywordextraction
operator|.
name|linking
operator|.
name|EntitySearcher
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
name|FieldQueryFactory
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
name|TextConstraint
import|;
end_import

begin_class
specifier|public
class|class
name|EntitySearcherUtils
block|{
comment|/**      * Validated the parsed parameter as parsed to       * {@link EntitySearcher#lookup(String, Set, List, String...)}      * and creates a fieldQuery for the parsed parameter      * @param field      * @param includeFields      * @param search      * @param languages      * @return      */
specifier|public
specifier|final
specifier|static
name|FieldQuery
name|createFieldQuery
parameter_list|(
name|FieldQueryFactory
name|factory
parameter_list|,
name|String
name|field
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|includeFields
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|search
parameter_list|,
name|String
modifier|...
name|languages
parameter_list|)
block|{
if|if
condition|(
name|field
operator|==
literal|null
operator|||
name|field
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed search field MUST NOT be NULL nor empty"
argument_list|)
throw|;
block|}
if|if
condition|(
name|search
operator|==
literal|null
operator|||
name|search
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed list of search strings MUST NOT be NULL nor empty"
argument_list|)
throw|;
block|}
comment|//build the query and than return the result
name|FieldQuery
name|query
init|=
name|factory
operator|.
name|createFieldQuery
argument_list|()
decl_stmt|;
if|if
condition|(
name|includeFields
operator|==
literal|null
condition|)
block|{
name|query
operator|.
name|addSelectedField
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|includeFields
operator|.
name|contains
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|query
operator|.
name|addSelectedField
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|select
range|:
name|includeFields
control|)
block|{
name|query
operator|.
name|addSelectedField
argument_list|(
name|select
argument_list|)
expr_stmt|;
block|}
block|}
name|query
operator|.
name|setLimit
argument_list|(
literal|20
argument_list|)
expr_stmt|;
comment|//TODO make configurable
name|query
operator|.
name|setConstraint
argument_list|(
name|field
argument_list|,
operator|new
name|TextConstraint
argument_list|(
name|search
argument_list|,
name|languages
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|query
return|;
block|}
block|}
end_class

end_unit

