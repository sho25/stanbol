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
name|query
operator|.
name|sparql
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
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
specifier|public
specifier|final
class|class
name|SparqlFieldQueryFactory
implements|implements
name|FieldQueryFactory
block|{
specifier|private
specifier|static
name|SparqlFieldQueryFactory
name|instance
decl_stmt|;
specifier|public
specifier|static
name|SparqlFieldQueryFactory
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
name|SparqlFieldQueryFactory
argument_list|()
expr_stmt|;
block|}
return|return
name|instance
return|;
block|}
specifier|private
name|SparqlFieldQueryFactory
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|SparqlFieldQuery
name|createFieldQuery
parameter_list|()
block|{
return|return
operator|new
name|SparqlFieldQuery
argument_list|()
return|;
block|}
comment|/**      * Utility Method to create an {@link SparqlFieldQuery} based on the parse {@link FieldQuery}      *       * @param parsedQuery      *            the parsed Query      */
specifier|public
specifier|static
name|SparqlFieldQuery
name|getSparqlFieldQuery
parameter_list|(
name|FieldQuery
name|parsedQuery
parameter_list|)
block|{
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SparqlFieldQueryFactory
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|parsedQuery
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|trace
argument_list|(
literal|"Parsed query is null."
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
elseif|else
if|if
condition|(
name|parsedQuery
operator|instanceof
name|SparqlFieldQuery
condition|)
block|{
name|logger
operator|.
name|trace
argument_list|(
literal|"Parsed query is a [SparqlFieldQuery]."
argument_list|)
expr_stmt|;
return|return
operator|(
name|SparqlFieldQuery
operator|)
name|parsedQuery
return|;
block|}
else|else
block|{
name|logger
operator|.
name|trace
argument_list|(
literal|"Parsed query is a [{}]."
argument_list|,
name|parsedQuery
operator|.
name|getClass
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|parsedQuery
operator|.
name|copyTo
argument_list|(
operator|new
name|SparqlFieldQuery
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit
