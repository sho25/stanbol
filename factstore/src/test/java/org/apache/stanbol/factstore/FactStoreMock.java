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
package|;
end_package

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
name|commons
operator|.
name|jsonld
operator|.
name|JsonLdCommon
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
name|factstore
operator|.
name|api
operator|.
name|FactStore
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
name|factstore
operator|.
name|model
operator|.
name|Fact
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
name|factstore
operator|.
name|model
operator|.
name|FactSchema
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
name|factstore
operator|.
name|model
operator|.
name|Query
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
name|factstore
operator|.
name|model
operator|.
name|FactResultSet
import|;
end_import

begin_class
specifier|public
class|class
name|FactStoreMock
implements|implements
name|FactStore
block|{
specifier|public
name|void
name|createFactSchema
parameter_list|(
name|String
name|factSchemaURN
parameter_list|,
name|JsonLdCommon
name|jsonLd
parameter_list|)
throws|throws
name|Exception
block|{}
specifier|public
name|boolean
name|existsFactSchema
parameter_list|(
name|String
name|factSchemaURN
parameter_list|)
throws|throws
name|Exception
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getMaxFactSchemaURNLength
parameter_list|()
block|{
return|return
literal|96
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|createFactSchema
parameter_list|(
name|FactSchema
name|factSchema
parameter_list|)
throws|throws
name|Exception
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|FactSchema
name|getFactSchema
parameter_list|(
name|String
name|factSchemaURN
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|addFact
parameter_list|(
name|Fact
name|fact
parameter_list|)
throws|throws
name|Exception
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|void
name|addFacts
parameter_list|(
name|Set
argument_list|<
name|Fact
argument_list|>
name|factSet
parameter_list|)
throws|throws
name|Exception
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|FactResultSet
name|query
parameter_list|(
name|Query
name|query
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

