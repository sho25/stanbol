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
name|factstore
operator|.
name|model
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
name|List
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
name|commons
operator|.
name|jsonld
operator|.
name|JsonLd
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
name|commons
operator|.
name|jsonld
operator|.
name|JsonLdResource
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
class|class
name|FactResultSet
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|private
specifier|static
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|FactResultSet
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|header
decl_stmt|;
specifier|private
name|List
argument_list|<
name|FactResult
argument_list|>
name|rows
decl_stmt|;
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getHeader
parameter_list|()
block|{
return|return
name|header
return|;
block|}
specifier|public
name|List
argument_list|<
name|FactResult
argument_list|>
name|getRows
parameter_list|()
block|{
return|return
name|rows
return|;
block|}
specifier|public
name|void
name|setHeader
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|header
parameter_list|)
block|{
name|this
operator|.
name|header
operator|=
name|header
expr_stmt|;
block|}
specifier|public
name|void
name|addFactResult
parameter_list|(
name|FactResult
name|result
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|rows
operator|==
literal|null
condition|)
block|{
name|rows
operator|=
operator|new
name|ArrayList
argument_list|<
name|FactResult
argument_list|>
argument_list|()
expr_stmt|;
block|}
name|rows
operator|.
name|add
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|toJSON
parameter_list|()
block|{
name|JsonLd
name|root
init|=
operator|new
name|JsonLd
argument_list|()
decl_stmt|;
if|if
condition|(
name|rows
operator|!=
literal|null
operator|&&
operator|!
name|rows
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|int
name|rowCount
init|=
literal|0
decl_stmt|;
for|for
control|(
name|FactResult
name|result
range|:
name|rows
control|)
block|{
name|rowCount
operator|++
expr_stmt|;
name|JsonLdResource
name|subject
init|=
operator|new
name|JsonLdResource
argument_list|()
decl_stmt|;
name|subject
operator|.
name|setSubject
argument_list|(
literal|"R"
operator|+
name|rowCount
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|header
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|subject
operator|.
name|putProperty
argument_list|(
name|header
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|,
name|result
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|root
operator|.
name|put
argument_list|(
name|subject
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|root
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

